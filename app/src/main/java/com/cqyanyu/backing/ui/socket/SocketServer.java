package com.cqyanyu.backing.ui.socket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.Constant;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.EventBadgeItem;
import com.cqyanyu.backing.ui.activity.login.LoginActivity;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.ui.socket.entity.EntityFrameJson;
import com.cqyanyu.backing.ui.socket.entity.ResponseAlarmEntity;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.Utils;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XPreferenceUtil;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.xdandroid.hellodaemon.AbsWorkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import baidumap.LocationInfo;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Socket通讯服务
 * Created by Administrator on 2017/7/28.
 */
public class SocketServer extends AbsWorkService {

    private int notificationId = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.i(TAG, "SocketServer onCreate");
        ServiceStart();
        super.onCreate();
    }

    //是否 任务完成, 不再需要服务运行?
    public static boolean sShouldStopService;
    public static Disposable sDisposable;

    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return sShouldStopService;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        sDisposable = Flowable.interval(3, TimeUnit.SECONDS)
                .doOnCancel(new Action() {
                    @Override
                    public void run() throws Exception {
                        cancelJobAlarmSub();
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong > 0 && aLong % 18 == 0)
                            System.out.println("保存数据到磁盘。 saveCount = " + (aLong / 18 - 1));
                    }
                });
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        stopService();
    }

    public static void stopService() {
        //我们现在不再需要服务运行了, 将标志位置为 true
        sShouldStopService = true;
        //取消对任务的订阅
        if (sDisposable != null) sDisposable.dispose();
        //取消 Job / Alarm / Subscription
        cancelJobAlarmSub();
    }

    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return sDisposable != null && !sDisposable.isDisposed();
    }

    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {
        System.out.println("保存数据到磁盘。");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "SocketServer onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    private final String TAG = "socketServer";
    private static final String REMOTE_IP = "119.23.51.26";//正式ip
    //    private static final String REMOTE_IP = "192.168.0.2";//测试ip
    private static final int REMOTE_PORT = 12001;
    private Socket serverSocket = null;
    private InputStream rfd = null;
    private OutputStream wfd = null;
    private boolean networkStatus = false;
    private boolean networkThreadLoop = true;
    public static boolean isLogin = false;
    private static int ioBufferSize = 51200;

    private int repeatHeratbeatCount = 0;
    private long repeatInterval = 5;//重复发送间隔
    private long intervalTime = 60;//心跳间隔
    private long reportIntervalTime = 900;//上报位置间隔
    private long lastCommSec = MyDate.GetSystemSeconds() + intervalTime;//最后通信时间
    private long lastReportPositionSec = MyDate.GetSystemSeconds() + reportIntervalTime;//最后上报位置时间
    private Thread serviceThread;
    private Thread heartbeatThread;

    public void ServiceStart() {
        serviceThread = new Thread(new Runnable() {

            @Override
            public void run() {
                networkThreadLoop = true;
                serviceLoop();
            }

        });
        serviceThread.start();
        heartbeatThread = new Thread(new Runnable() {

            @Override
            public void run() {
                networkThreadLoop = true;
                heartbeatLoop();
            }

        });
        heartbeatThread.start();
    }

    public static void Sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * socket 初始化
     *
     * @return
     */
    private Socket SocketConnect() {
        Socket fd = null;
        try {
            fd = new Socket();
            SocketAddress socAddress = new InetSocketAddress(REMOTE_IP, REMOTE_PORT);
            fd.connect(socAddress, 5000);
            // fd = new Socket(clientIp, clientPort);
        } catch (UnknownHostException e) {
            Log.i(TAG, "MasterServer UnknownHostException!" + e.getMessage());
            return null;
        } catch (Exception e) {
            Log.i(TAG, "MasterServer IOException!" + e.getMessage());
            return null;
        }

        try {
            if (fd != null) {
                rfd = fd.getInputStream();
                wfd = fd.getOutputStream();
                JSONObject login = MyJson.makeLoginJson();
                JSONObject msg = MyJson.makePushParameterJsonNode(++msgSN, login);
                byte[] frame = MyJson.makeJsonFrame(Constant.PushServer_CMD_Push, Constant.PushServer_Type_Login,
                        msg);
                wfd.write(frame, 0, frame.length);
                Log.i(TAG, "send login info");
            }

        } catch (IOException e) {
            Log.i(TAG, "MasterServer Get Stream Error!" + e.getMessage());
            try {
                fd.close();
            } catch (Exception e1) {
                Log.i(TAG, "MasterServer Get Stream Error1!" + e1.getMessage());
            }
            return null;
        }
        return fd;
    }

    private void SocketClose() {
        networkStatus = false;
        try {
            isLogin = false;
            rfd.close();
            rfd = null;
            wfd.close();
            wfd = null;
            serverSocket.close();
            serverSocket = null;
            repeatHeratbeatCount = 0;

        } catch (Exception e1) {
            Log.i(TAG, "MasterServer Get Stream Error1!" + e1.getMessage());
        }
        // RefreshNetworkStr(MyTag.errorInfo);
    }

    private int SocketRecv(byte[] dest, int doffset, int size) {
        if (size < 0)
            return -1;
        int nRet = 0;

        try {
            nRet = rfd.read(dest, 0, size);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return nRet;
    }

    public boolean SocketSend(final byte[] data, final int length) {
        if (serverSocket == null)
            return false;
        if (wfd == null)
            return false;
        if (data == null || networkStatus == false || length <= 0)
            return false;
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    wfd.write(data, 0, length);
                } catch (Exception e) {
                    Log.i(TAG, "MasterServer DataSend Error: " + e.getMessage());
                }
            }

        }).start();

        return true;
    }

    private int FrameRecv(byte[] dest) {
        if (dest == null)
            return 0;

        int readlen = 1024;
        int nRet = SocketRecv(dest, 0, readlen);

        return nRet;
    }

    private int msgSN = 0;

    private void doJsonFrame(byte[] data, int length) {
        String[] jframe = MyJson.isJsonFrame(data, length);
        int count = jframe.length;
        if (count == 0)
            return;
        int i;
//        String date = MyDate.GetTime();
        lastCommSec = MyDate.GetSystemSeconds() + intervalTime;
        for (i = 0; i < count; i++) {
            EntityFrameJson node = new EntityFrameJson(jframe[i]);
            Log.i(TAG, jframe[i]);
            doAswFrame(node);
        }
    }

    private void doAswFrame(EntityFrameJson node) {
        boolean ret = false;
        int msgsn = 0;
        int type = node.type;
        long sec = MyDate.GetSystemSeconds();
        lastCommSec = sec + intervalTime;
        if (type == Constant.PushServer_Type_Hearbeat) {
            repeatHeratbeatCount = 0;
            Log.i(TAG, "Recv HreatBeat!");
            return;
        }
        JSONObject obj = new JSONObject();
        //Log.i(TAG, "Recv:" + node.obj.toString());
        switch (type) {
            case Constant.PushServer_Type_Login:
                lastReportPositionSec = MyDate.GetSystemSeconds() + repeatInterval;
                isLogin = true;
                break;
            case Constant.PushServer_Type_Alarm:
                ret = true;
                try {
                    if (node.obj != null) {
                        JSONObject mobj = node.obj.getJSONObject("msg");
                        if (mobj != null)
                            alarmResponse(mobj.toString());
                    } else {
                        ret = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constant.PushServer_Type_Duty:
                ret = true;//replyDuty(String oid, String launchManId, String launchUnitId)
                try {
                    if (node.obj != null) {
                        JSONObject mobj = node.obj.getJSONObject("msg");
                        replyDuty(mobj.get("oid").toString(), mobj.get("launchmanid").toString(), mobj.get("launchunitid").toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constant.PushServer_Type_ReportPosition:
                lastReportPositionSec = sec + reportIntervalTime;
                break;
            case Constant.PushServer_Type_NoticeHandleAlarm:
                JSONObject mobj = null;
                int userid = 0;
                try {
                    if (node.obj != null) {
                        mobj = node.obj.getJSONObject("msg");
                        userid = mobj.getInt("handlemanid");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notification("警情处理都督", "你有一条告警很久没有处理了，请及时处理!", "user_" + userid);
                ret = true;
                break;
        }
        if (!ret)
            return;
        try {
            if (node.obj != null)
                msgsn = node.obj.getInt("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            obj.put("code", "200");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject pobj = MyJson.makePushParameterJsonNode(msgsn, obj);
        byte[] frame = MyJson.makeJsonFrame(Constant.PushServer_CMD_Push, type, pobj);
        SocketSend(frame, frame.length);
    }

    private void serviceLoop() {
        int size = 0;
        final byte[] ioBuffer = new byte[ioBufferSize];
        while (networkThreadLoop) {
            if (networkStatus == false) {
                serverSocket = SocketConnect();
                if (serverSocket != null) {
                    networkStatus = true;
                }
            }
            size = FrameRecv(ioBuffer);
            if (size > 8) {
                final int bufferSize = size;
                doJsonFrame(ioBuffer, bufferSize);
            } else if (size < 0) {
                Log.i(TAG, "MasterServer Socket Reset ");
                SocketClose();
                Sleep(3000);
            }
            Sleep(100);
        }
        Log.i(TAG, "serviceLoop Exit");
    }

    private void heartbeatLoop() {
        while (networkThreadLoop) {
            long now = MyDate.GetSystemSeconds();
            if (now >= lastReportPositionSec) {
                lastReportPositionSec = now + repeatInterval;
                lastCommSec = now + repeatInterval - 2;//上报位置时延后3秒上报心跳
                JSONObject pobj = MyJson.makeReportJson();
                JSONObject msg = MyJson.makePushParameterJsonNode(++msgSN, pobj);
                byte[] frame = MyJson.makeJsonFrame(Constant.PushServer_CMD_Push, Constant.PushServer_Type_ReportPosition, msg);
                SocketSend(frame, frame.length);
            }
            if (isLogin && now >= lastCommSec) {
                byte[] frame = MyJson.makeJsonFrame(Constant.PushServer_CMD_Push, Constant.PushServer_Type_Hearbeat, null);
                SocketSend(frame, frame.length);
                lastCommSec = now + repeatInterval;
                repeatHeratbeatCount++;
                if (repeatHeratbeatCount > 5) {
                    SocketClose();
                }
            }
            Sleep(1000);
        }
        Log.i(TAG, "heartbeatLoop Exit");
    }


    /**
     * 是否是告警响应,并处理告警响应( type=133 )
     *
     * @param message 响应信息
     */
    private void alarmResponse(String message) {
        if (!TextUtils.isEmpty(message)) {
            ResponseAlarmEntity entity = JSON.parseObject(message, ResponseAlarmEntity.class);
            //告警信息显示
            notification(entity.getAffairstr(),
                    entity.getPosition(),
                    "warn_" + entity.getAffairid());
        } else {
            XLog.i("messag 为null");
        }
    }

/*********************************************************************************************/
    /**
     * 回复查岗
     *
     * @param oid          查岗记录ID
     * @param launchManId  发起人 ID
     * @param launchUnitId 发起人单位ＩＤ
     */
    private void replyDuty(String oid, String launchManId, String launchUnitId) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("oid", oid);//查岗记录ID(TODO 来自于推送)
        paramsMap.put("launchmanid", launchManId);//发起人 ID
        paramsMap.put("launchunitid", launchUnitId);//发起人单位ＩＤ
        paramsMap.put("answermanid", CommonInfo.getInstance().getUserInfo().getUserid());//回复人ＩＤ
        paramsMap.put("answerunitid", CommonInfo.getInstance().getUserInfo().getUnitid());//回复人单位ＩＤ
        paramsMap.put("answerlongitude", LocationInfo.getInstance().getMyLongitude() + "");//回复时所在经度
        paramsMap.put("answerlatitude", LocationInfo.getInstance().getMyLatitude() + "");//回复时所在纬度
        paramsMap.put("answerposition", LocationInfo.getInstance().getMyAddrStr());//回复时所在位置
        paramsMap.put("answerdate", MyDate.getNowTime());//回复时间
        paramsMap.put("answerremark", "rete");//备注
        XHttpUtils.post(getApplication(), paramsMap, ConstHost.GET_DUTY_ANSWER_URL, new XICallbackString() {
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)) {
                    if (TextUtils.equals(result, "0")) {
                        //提交失败
                        XToastUtil.showToast("提交失败");
                    }
                    //提交成功
                }
            }

            @Override
            public void onFail(String msg) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 发送通知
     *
     * @param title 标题
     * @param msg   信息
     * @param type  类型
     */
    private void notification(String title, String msg, String type) {
        EventBadgeItem.getInstance().post("1");
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        /**创建消息通知*/
        Notification builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                // icon)
                .setTicker(title)// 设置在status
                // bar上显示的提示文字
                .setContentTitle(title)// 设置在下拉status
                // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentIntent(getPendingIntent(type))//点击事件
                .setContentText(msg)// TextView中显示的详细内容
                .getNotification(); // 需要注意build()是在API level
        //点击取消
        builder.flags |= Notification.FLAG_AUTO_CANCEL;
        //使用默认的声音
//        builder.defaults |= Notification.DEFAULT_SOUND;
        builder.defaults = Notification.DEFAULT_LIGHTS;
        if (Utils.fileIsExists(XPreferenceUtil.getInstance().getString("voice"))) {
            builder.sound = Uri.parse("file://" + XPreferenceUtil.getInstance().getString("voice"));
        } else {
            builder.sound = Uri.parse("android.resource://" + getPackageName()
                    + "/" + R.raw.fire);
        }
        //使用默认的震动
        builder.defaults |= Notification.DEFAULT_VIBRATE;
        //使用默认的声音、振动、闪光
//        builder.defaults = Notification.DEFAULT_ALL;
        mNotificationManager.notify(notificationId++, builder);
        //修改id
        if (notificationId > 10000000) notificationId = 0;
    }

    /**
     * 获取notification点击事件
     *
     * @param type
     * @return
     */
    private PendingIntent getPendingIntent(String type) {
        //设置通知的点击事件(此处为跳转到ResultActivity)
        Intent intent = null;
        switch (type) {
            case "warn_null"://预警
                intent = new Intent(this, NotificationWarnReceiver.class);
                break;
            case "warn_1"://火警
                intent = new Intent(this, NotificationFireReceiver.class);
                break;
            case "warn_2"://故障
                intent = new Intent(this, NotificationWFaultReceiver.class);
                break;
            case "warn_3"://其他
                intent = new Intent(this, NotificationOtherReceiver.class);
                break;
            default:
                intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        //使用PendingIntent包装Intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//这行代码会解决此问题
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        SocketClose();
        Log.i(TAG, "SocketServer onDestroy");
        networkThreadLoop = false;
        try {
            serviceThread.join(10000);
            heartbeatThread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            serviceThread.stop();
            heartbeatThread.stop();
        }
        stopForeground(true);
        Intent intent = new Intent("com.cqyanyu.backing.ui.socket.BootReceiver");
        intent.setAction("com.cqyanyu.backing.ui.socket.SocketServer");
        sendBroadcast(intent);
        super.onDestroy();
    }
}
