package com.cqyanyu.backing.ui.fragment.my;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.activity.login.LoginActivity;
import com.cqyanyu.backing.ui.activity.my.DownLoadActivity;
import com.cqyanyu.backing.ui.activity.my.ModifyPasswordActivity;
import com.cqyanyu.backing.ui.activity.my.MsgCenterActivity;
import com.cqyanyu.backing.ui.mvpview.my.MyView;
import com.cqyanyu.backing.ui.presenter.my.MyPresenter;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.ui.socket.SocketServer;
import com.cqyanyu.backing.ui.widget.app.XImageView;
import com.cqyanyu.backing.ui.widget.app.XTextView;
import com.cqyanyu.backing.utils.cache.ACache;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.ItemOptionView;

/**
 * 我的
 * Created by Administrator on 2017/6/9 0009.
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MyView {
    private XImageView ivAvatar;
    private XTextView tvNickname;
    private XTextView tvAccount;
    private XTextView tvUnitName;
    private XTextView tvPhone;
    private ItemOptionView iovOrganization;
    private ItemOptionView iovTelNumber;
    private ItemOptionView iovModifyPassword;
    private ItemOptionView iovAboutUs;
    private ItemOptionView iovMsgCenter;
    private Button btnExit;
    private TextView btnRight;

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter();
    }

    @Override
    protected int getLayout() {
        showBack = false;
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        if (textCancel != null) textCancel.setVisibility(View.GONE);
        ivAvatar = (XImageView) rootView.findViewById(R.id.iv_avatar);
        tvNickname = (XTextView) rootView.findViewById(R.id.tv_nickname);
        tvAccount = (XTextView) rootView.findViewById(R.id.tv_account);
        tvUnitName = (XTextView) rootView.findViewById(R.id.tv_unit_name);
        tvPhone = (XTextView) rootView.findViewById(R.id.tv_phone);
        iovOrganization = (ItemOptionView) rootView.findViewById(R.id.iov_organization);
        iovTelNumber = (ItemOptionView) rootView.findViewById(R.id.iov_tel_number);
        iovModifyPassword = (ItemOptionView) rootView.findViewById(R.id.iov_modify_password);
        iovAboutUs = (ItemOptionView) rootView.findViewById(R.id.iov_about_us);
        iovMsgCenter = (ItemOptionView) rootView.findViewById(R.id.iov_msg_center);
        btnExit = (Button) rootView.findViewById(R.id.btn_exit);
        btnRight = (TextView) rootView.findViewById(R.id.btn_right);
    }

    @Override
    protected void initListener() {
        setTopTitle("我的");
        iovMsgCenter.setOnClickListener(this);
        iovModifyPassword.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        iovAboutUs.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_wenhao);
        drawable.setBounds(0, 0, 0, 0);
        btnRight.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iov_modify_password:
                if (InfoManger.getInstance().isPermission("79")) {//修改密码
                    startActivity(new Intent(mContext, ModifyPasswordActivity.class)
                            .putExtra("name", tvNickname.getText().toString()));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
            case R.id.iov_msg_center:
                if (InfoManger.getInstance().isPermission("80")) {//消息中心
                    startActivity(new Intent(mContext, MsgCenterActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
            case R.id.btn_exit://退出登录
                if (mPresenter != null) mPresenter.requestSignOut();
                break;
//            case R.id.iov_about_us://关于我们
//                if (InfoManger.getInstance().isPermission("80")) {
//                    startActivity(new Intent(mContext, AboutUsActivity.class));
//                } else {
//                    XToastUtil.showToast("暂不拥有该权限！");
//                }
//                break;
            case R.id.btn_right:
                startActivity(new Intent(mContext, DownLoadActivity.class));
                break;
        }
    }

    /**
     * 退出成功，返回登录页面
     */
    @Override
    public void requestSuccess() {
        mContext.stopService(new Intent(mContext, MyServer.class));
        mContext.stopService(new Intent(mContext, SocketServer.class));
        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
        cloudPushService.turnOffPushChannel(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                XLog.i("turnOffPushChannel-->阿里推送");
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
        mContext.stopService(new Intent(mContext, CloudPushService.class));
        ACache mAache = ACache.get(mContext);
        mAache.clear();
        startActivity(new Intent(mContext, LoginActivity.class).putExtra("autoLogin", false));
        mContext.finish();
    }

    @Override
    public void setNickName(String name) {
        tvNickname.setXText(name);
    }

    @Override
    public void setAccount(String phone) {
        tvAccount.setXText(phone);
    }

    @Override
    public void setUnitName(String unitstr) {
        tvUnitName.setXText(unitstr);
    }

    @Override
    public void setPhone(String phone) {
        tvPhone.setXText(phone);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {  //相当于Fragment的onResume
            if (mPresenter != null) mPresenter.getMyUserInfoOnNet();
        } else {
            //相当于Fragment的onPause
        }
    }
}
