package com.cqyanyu.backing;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.baidu.mapapi.SDKInitializer;
import com.cqyanyu.backing.ui.activity.login.LoginActivity;
import com.cqyanyu.backing.utils.XMetaUtil;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.XApplication;

/**
 * 入口
 */

public class MyApp extends XApplication {
    private static MyApp instance;

    public static MyApp getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSDK();
        initBaiDuMap();
        initCloudChannel(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

    /**
     * 初始化相关SDK
     */
    public void initSDK() {
        //初始化Meta
        XMetaUtil.Instance().init(this);
        //初始化登录
        X.setLoginActivity(LoginActivity.class);
        //初始化全局数据
        CommonInfo.init(getApplicationContext());
    }

    /**
     * 初始化百度地图
     */
    public void initBaiDuMap() {
        SDKInitializer.initialize(getApplicationContext());
    }

    /**
     * 初始化云推送通道
     *
     * @param context 环境
     */
    private void initCloudChannel(Context context) {
        PushServiceFactory.init(context);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(context, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("yykj_al", "init cloudChannel success");
                String deviceId = pushService.getDeviceId();
                Log.d("yykj_al", deviceId);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d("yykj_al", "init cloudChannel failed -- errorCode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        HuaWeiRegister.register(this);
        MiPushRegister.register(this, "2882303761517601846", "5371760177846");
    }
}
