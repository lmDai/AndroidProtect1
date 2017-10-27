package com.cqyanyu.backing.ui.activity.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.login.ForgetPasswordView;
import com.cqyanyu.backing.ui.presenter.login.ForgetPasswordPresenter;
import com.cqyanyu.backing.utils.TimeCountUtils;

/**
 * 忘记密码
 * Created by Administrator on 2017/7/10.
 */
public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordView {

    private EditText etTel;
    private EditText etCode;
    private TextView tvCode;
    private Button btnCommit;
    private TimeCountUtils timeCountUtils;

    @Override
    protected ForgetPasswordPresenter createPresenter() {
        return new ForgetPasswordPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        etTel = (EditText) findViewById(R.id.et_tel);
        etCode = (EditText) findViewById(R.id.et_code);
        tvCode = (TextView) findViewById(R.id.tv_code);
        btnCommit = (Button) findViewById(R.id.btn_commit);

    }

    @Override
    protected void initListener() {
        tvCode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        timeCountUtils = new TimeCountUtils(this)
                .setTimeAttribute(60 * 1000)
                .setSaveTime(true)
                .initFinish(tvCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_code://请求验证码
                timeCountUtils.start();
                if (mPresenter != null) mPresenter.requestCode();
                break;

            case R.id.btn_commit://重置密码
                if (mPresenter != null) mPresenter.resetPassword();
                break;
        }
    }

    @Override
    public String getTel() {
        return etTel.getText().toString();
    }

    @Override
    public String getCode() {
        return etCode.getText().toString();
    }

    @Override
    public void requestSuccess() {
        timeCountUtils.start();
    }
}
