package com.cqyanyu.backing.ui.socket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by personal on 2017/11/6.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.cqyanyu.backing.ui.socket.SocketServer")) {
            //TODO
            //在这里写重新启动service的相关操作
            startUploadService(context);
        }
    }

    private void startUploadService(Context context) {
        context.startService(new Intent(context, SocketServer.class));
    }
}
