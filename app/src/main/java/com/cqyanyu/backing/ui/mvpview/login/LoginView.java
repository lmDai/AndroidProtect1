package com.cqyanyu.backing.ui.mvpview.login;

import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * 登录接口view
 * Created by Administrator on 2017/7/6.
 */
public interface LoginView extends IBaseView {
    String getUsername();

    String getPassword();

    void requestSuccess();

    void loginFail(String info);
}
