package com.cqyanyu.backing.ui.mvpview.my;

import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * Created by Administrator on 2017/7/11.
 */

public interface ModifyPasswordView extends IBaseView {
    String getOldPwd();//获取原密码

    String getNewPwd();//获取新密码

    String getConPwd();//确认新密码

    String getUserStr();

    void requestSuccess();
}
