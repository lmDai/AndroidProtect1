package com.cqyanyu.backing.ui.activity.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.mvpframework.activity.base.XBaseFragment;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.PermissionUtil;
import com.cqyanyu.mvpframework.utils.StatusBarUtil;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.Toolbar;

import org.greenrobot.eventbus.EventBus;

/**
 * 基类fragment
 * Created by Administrator on 2017/4/20 0020.
 */
public abstract class BaseFragment<T extends BasePresenter> extends XBaseFragment<T> implements View.OnClickListener {

    protected boolean showBack = true;
    private Toolbar toolbar;
    protected TextView textCancel;
    //定位权限
    private String[] localPermissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            textCancel = (TextView) toolbar.findViewById(R.id.btn_left);
            if (textCancel != null) {
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackListener();
                    }
                });
            }
            ((AppCompatActivity) mContext).setSupportActionBar(toolbar);
            if (showBack) {
                final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
                upArrow.setColorFilter(getResources().getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP);
                ((AppCompatActivity) mContext).getSupportActionBar().setHomeAsUpIndicator(upArrow);
                ((AppCompatActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        // StatusBarUtil.setStatusBarColor(mContext, com.cqyanyu.mvpframework.R.color.transparent);
        initStatusBar();
        initView(view, savedInstanceState);
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        //StatusBarUtil.setStatusBarColor(this, R.color.color_main);
        if (Build.VERSION.SDK_INT >= 19 && toolbar != null) {
            StatusBarUtil.transparencyBar(getActivity());
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = layoutParams.height + getStatusBarHeight(getActivity());
            toolbar.setPadding(0, getStatusBarHeight(getActivity()), 0, 0);
            toolbar.setLayoutParams(layoutParams);
        }
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

    private void onBackListener() {
        mContext.finish();
    }

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view
     * @param savedInstanceState
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 设置标题
     *
     * @param str
     */
    protected void setTopTitle(String str) {
        TextView title = (TextView) mView.findViewById(R.id.bt_tv_title);
        title.setText(str);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

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
        if (PermissionUtil.initPermission(mContext, localPermissions)) {
            mContext.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOCAL)
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 60) {
            if (PermissionUtil.isPermission(mContext, permissions, "权限")) {
                mContext.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                        .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOCAL)
                );
            }
        }
    }
}
