package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * 增加用户接口view
 * Created by Administrator on 2017/7/11.
 */
public interface AddUserView extends IBaseView {

    String getLabel();

    String getNameUP();

    String getPhoneUP();

    String getUnitStr();

    void setUnit(String unit, String unitstr);

    String getUsername();

    void setUsername(String name);

    String getUnit();

    String getRole();

    void setRole(String role);

    String getTelephone();

    void setTelephone(String phone);


}
