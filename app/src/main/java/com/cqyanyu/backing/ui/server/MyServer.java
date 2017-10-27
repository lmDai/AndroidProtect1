package com.cqyanyu.backing.ui.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.cqyanyu.backing.manger.InfoManger;

import org.greenrobot.eventbus.EventBus;

import baidumap.MapManager;

/**
 * 信息服务
 * Created by Administrator on 2017/7/21.
 */
public class MyServer extends Service {
    public static final String FLAG = "flag";
    public static final int FLAG_VALUE_INIT = 1;
    public static final int FLAG_VALUE_LOGIN = 2;
    public static final int FLAG_VALUE_LOCAL = 3;
    private MyReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //注册广播
        if (receiver == null) {
            receiver = new MyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.backing.broad_call_service");
            registerReceiver(receiver, filter);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //初始化数据
    private void initData() {
        InfoManger.getInstance().getEntry(getApplicationContext());
//        InfoManger.getInstance().getMyUnitOnNet(getApplicationContext());
//        InfoManger.getInstance().getSubUnitOnNet(getApplicationContext());
//        InfoManger.getInstance().getSetListOnNet(getApplicationContext());
//        InfoManger.getInstance().getMyUserInfoOnNet(getApplicationContext());
//        InfoManger.getInstance().getSubUserInfoOnNet(getApplicationContext());
        InfoManger.getInstance().getProblem(getApplicationContext());
    }

    //重新登录
    private void reLogin() {
        InfoManger.getInstance().reLogin(getApplicationContext());
    }

    //定位我的位置
    private void localMy(Context context) {
        final MapManager mapManager = new MapManager(context);
        mapManager.initLocation(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
                    EventBus.getDefault().postSticky(bdLocation);
                    mapManager.setBDLocationResult(bdLocation);
                }
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });
        mapManager.startLocation();
    }

    //内部类实现广播接收者
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra(FLAG, 0)) {
                case FLAG_VALUE_INIT:
                    initData();
                    break;
                case FLAG_VALUE_LOGIN:
                    reLogin();
                    break;
                case FLAG_VALUE_LOCAL:
                    localMy(context);
                    break;
            }
        }
    }
}
