package com.cqyanyu.backing.ui.activity.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.Constant;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.login.MainActivity;
import com.cqyanyu.backing.ui.entity.login.UserInfo;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.utils.Utils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.activity.base.XBaseActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.PermissionUtil;
import com.cqyanyu.mvpframework.utils.StatusBarUtil;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.Toolbar;

import org.greenrobot.eventbus.EventBus;

/**
 * 基本Activity
 */
@SuppressWarnings("RestrictedApi")
public abstract class BaseActivity<T extends BasePresenter> extends XBaseActivity<T> implements View.OnClickListener {

    private boolean showBack = true;
    private Toolbar toolbar;
    private TextView textCancel;
    private TextView textRight;
    //定位权限
    private String[] localPermissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            UserInfo tempData = (UserInfo) savedInstanceState.getSerializable("userinfo");
            X.user().setUserInfo(tempData);
        }
//        switch(AppStatusManager.getInstance().getAppStatus()) {
//            case Constant.STATUS_FORCE_KILLED:
//            restartApp();
//            break;
//            case Constant.STATUS_NORMAL:
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            textRight = (TextView) toolbar.findViewById(R.id.btn_right);
            textCancel = (TextView) toolbar.findViewById(R.id.btn_left);
            if (textCancel != null) {
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackListener();
                    }
                });
            }
            if (textRight != null) {
                setTextRight(textRight);
            }
            setSupportActionBar(toolbar);
            if (showBack) {
                final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
                upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        //初始化titlebar
        initStatusBar();
        //初始化点击空白区域收回键盘
        initTakeBackKeyboard();
//                break;
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UserInfo userInfo = CommonInfo.getInstance().getUserInfo();
        outState.putSerializable("userinfo", userInfo);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        UserInfo userInfo = CommonInfo.getInstance().getUserInfo();
        savedInstanceState.putSerializable("userinfo", userInfo);
    }

    protected void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constant.KEY_HOME_ACTION, Constant.ACTION_RESTART_APP);
        startActivity(intent);
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.color_main);
//        if (Build.VERSION.SDK_INT >= 19 && toolbar != null) {
//            StatusBarUtil.transparencyBar(this);
//            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
//            layoutParams.height = layoutParams.height + getStatusBarHeight(this);
//            toolbar.setPadding(0, getStatusBarHeight(this), 0, 0);
//            toolbar.setLayoutParams(layoutParams);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackListener();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 设置标题
     *
     * @param str
     */
    protected void setTopTitle(String str) {
        TextView title = (TextView) findViewById(R.id.bt_tv_title);
        title.setText(str);
        setTitle("");
    }

    /**
     * 设置标题栏
     *
     * @param textRight
     */
    protected void setTextRight(TextView textRight) {

    }

    /**
     * 返回监听
     */
    private void onBackListener() {
        finish();
    }

    /**
     * 初始化点击空白区域收回键盘(不使用就重写覆盖掉)
     */
    private void initTakeBackKeyboard() {
        Utils.init(this);
    }

    private int getStatusBarHeight(Context context) {
        /**
         * 获取状态栏高度——方法2
         * */
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("WangJ", "状态栏-方法2:" + statusBarHeight);
        return statusBarHeight;
    }

    /**
     * 判断定位是否成功
     *
     * @return 是否成功
     */
    public boolean isLocal() {
        BDLocation bdLocation = EventBus.getDefault().getStickyEvent(BDLocation.class);
        if (bdLocation == null) {
            //如果没有定位
            openLocal();
            XToastUtil.showToast("请先定位！否则不支持使用该功能");
            return false;
        } else {
            return true;
        }
    }

    // 开启定位
    protected void openLocal() {
        if (PermissionUtil.initPermission(this, localPermissions)) {
            sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOCAL)
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 60) {
            if (PermissionUtil.isPermission(this, permissions, "权限")) {
                sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                        .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOCAL)
                );
            }
        }
    }
}
