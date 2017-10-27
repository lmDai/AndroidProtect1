package com.cqyanyu.backing.ui.server;

import android.content.Context;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.cqyanyu.backing.ui.socket.SocketAuxiliary;
import com.cqyanyu.mvpframework.utils.XLog;

import java.util.Map;

/**
 * @author: 正纬
 * @since: 15/4/9
 * @version: 1.1
 * @feature: 用于接收推送的通知和消息
 */
public class MyMessageReceiver extends MessageReceiver {

    /**
     * 推送通知的回调方法
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        if (null != extraMap) {
            for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                XLog.i("@Get diy param : Key=" + entry.getKey() + " , Value=" + entry.getValue());
            }
        } else {
            XLog.i("@收到通知 && 自定义消息为空");
        }
        XLog.i("onNotification收到一条推送通知 ： " + title);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        XLog.i("onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
    }

    /**
     * 推送消息的回调方法    *
     *
     * @param context
     * @param cPushMessage
     */
    @Override
    public void onMessage(Context context, final CPushMessage cPushMessage) {
        try {
            XLog.i("收到一条推送消息-->" + cPushMessage.getTitle());
            // 持久化推送的消息到数据库
        } catch (Exception e) {
            XLog.i(e.toString());
        }
        SocketAuxiliary.getInstance().start(context);
    }

    /**
     * 从通知栏打开通知的扩展处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
//        cloudPushService.setNotificationSoundFilePath();
        XLog.i("onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
    }

    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        XLog.i("onNotificationRemoved ： " + messageId);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        XLog.i("onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
    }
}