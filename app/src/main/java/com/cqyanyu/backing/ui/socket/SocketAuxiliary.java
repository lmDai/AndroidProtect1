package com.cqyanyu.backing.ui.socket;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * 使用socket通讯辅助工具
 * Created by Administrator on 2017/7/28.
 */
public class SocketAuxiliary {
    private static final SocketAuxiliary instance = new SocketAuxiliary();

    private SocketAuxiliary() {

    }

    public static SocketAuxiliary getInstance() {
        return instance;
    }

    /**
     * 开启socket服务
     *
     * @param context 环境
     */
    public void start(Context context) {
        if (!isServiceWork(context, "com.cqyanyu.backing.ui.socket.SocketServer")) {
            /**如果服务没有在运行，便启动服务*/
            context.startService(new Intent(context, SocketServer.class));
        }
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
