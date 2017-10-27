package com.cqyanyu.backing.ui.mvpview.my;

import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * 我的页面接口view
 * Created by Administrator on 2017/7/7.
 */
public interface MyView extends IBaseView {
    void requestSuccess();//退出登录请求成功

    void setNickName(String name);

    void setAccount(String phone);

    void setUnitName(String unitstr);

    void setPhone(String phone);
}
