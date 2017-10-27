package com.cqyanyu.backing.ui.mvpview.login;

import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * 忘记密码接口view
 * Created by Administrator on 2017/7/10.
 */
public interface ForgetPasswordView extends IBaseView {

    String getTel();

    String getCode();

    void requestSuccess();
}
