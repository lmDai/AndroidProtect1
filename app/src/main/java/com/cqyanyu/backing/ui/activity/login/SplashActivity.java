package com.cqyanyu.backing.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.sdk.android.push.CloudPushService;
import com.cqyanyu.backing.Constant;
import com.cqyanyu.backing.ui.mvpview.login.LoginView;
import com.cqyanyu.backing.ui.presenter.login.LoginPresenter;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.ui.socket.SocketServer;
import com.cqyanyu.mvpframework.activity.base.XBaseActivity;
import com.cqyanyu.mvpframework.utils.XPreferenceUtil;
import com.cqyanyu.mvpframework.utils.XToastUtil;

/**
 * 启动器
 */
public class SplashActivity extends XBaseActivity<LoginPresenter> implements LoginView {

    private String userName;//用户名
    private String passWord;//密码

    @Override
    public void onCreate(Bundle savedInstanceState) {
        startServer();
        super.onCreate(savedInstanceState);
    }

    /**
     * 开启服务
     */
    private void startServer() {
        startService(new Intent(this, MyServer.class));
        startService(new Intent(this, CloudPushService.class));
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {
        userName = XPreferenceUtil.getInstance().getString(Constant.KEY_USERNAME);
        if (!TextUtils.isEmpty(userName)) {
            if (XPreferenceUtil.getInstance().getBoolean(Constant.KEY_REMEMBER)) {
                passWord = XPreferenceUtil.getInstance().getString(userName);
            }
        }
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
                    mPresenter.requestLoin();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));//跳转到登陆界面
                    finish();
                }
            }
        }, 2000);
    }

    /**
     * 返回用户名
     *
     * @return
     */
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * 返回密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public void requestSuccess() {
        startService(new Intent(this, SocketServer.class));
        //初始化数据
        sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
        );
        startActivity(new Intent(SplashActivity.this, MainActivity.class));//跳转到首页
        finish();
    }

    @Override
    public void loginFail(String info) {
        XToastUtil.showToast(info);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}