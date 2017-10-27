package com.cqyanyu.backing.ui.activity.my;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cqyanyu.backing.Constant;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.activity.login.LoginActivity;
import com.cqyanyu.backing.ui.mvpview.my.ModifyPasswordView;
import com.cqyanyu.backing.ui.presenter.my.ModifyPasswordPresenter;
import com.cqyanyu.mvpframework.utils.XPreferenceUtil;

/**
 * 修改密码
 * Created by Administrator on 2017/7/11.
 */

public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenter> implements ModifyPasswordView {
    //控件相关
    private EditText editOldPwd;//旧密码
    private EditText editNewPwd;//新密码
    private EditText editConPwd;//确认新密码
    private Button btnChangePwd;//确认按钮

    @Override
    protected ModifyPasswordPresenter createPresenter() {
        return new ModifyPasswordPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        setTopTitle("修改密码");
        editOldPwd = (EditText) findViewById(R.id.edit_old_password);
        editNewPwd = (EditText) findViewById(R.id.edit_new_password);
        editConPwd = (EditText) findViewById(R.id.edit_confirm_password);
        btnChangePwd = (Button) findViewById(R.id.btn_change_password);
    }

    /**
     * 初始化点击事件
     */
    @Override
    protected void initListener() {
        btnChangePwd.setOnClickListener(this);//确认修改密码
    }

    @Override
    protected void initData() {

    }

    /**
     * 点击相关
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_password://修改密码
                mPresenter.requestChangePwd();
                break;
        }
    }

    @Override
    public String getOldPwd() {
        return editOldPwd.getText().toString();
    }

    @Override
    public String getNewPwd() {
        return editNewPwd.getText().toString();
    }

    @Override
    public String getConPwd() {
        return editConPwd.getText().toString();
    }

    @Override
    public String getUserStr() {
        return getIntent().getStringExtra("name");
    }

    /**
     * 退出成功
     */
    @Override
    public void requestSuccess() {
        XPreferenceUtil.getInstance().setBoolean(Constant.KEY_REMEMBER, false);
        XPreferenceUtil.getInstance().clear(XPreferenceUtil.getInstance().getString(Constant.KEY_USERNAME));
        startActivity(new Intent(this, LoginActivity.class).putExtra("autoLogin", false));//跳转登录页面
        finish();
    }
}
