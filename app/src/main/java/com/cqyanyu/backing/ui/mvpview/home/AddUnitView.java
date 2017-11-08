package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 增加单位接口view
 * Created by Administrator on 2017/7/11.
 */
public interface AddUnitView extends IBaseView {

    String getLabel();

    String getOid();

    void setUnitSort(String sort, String typeStr);

    void setLan(double latitude, double longitude);

    String getPresentUnit();

    void setPresentUnit(String present, String pUnitStr);

    String getRegulatoryLevel();

    void setRegulatoryLevel(String level, String regulatory);

    String getUnitName();

    void setUnitName(String name);

    String getUnitType();

    String getUnitPosition();

    void setUnitPosition(String position);

    String getContact();

    void setContact(String contact);

    String getContactTel();

    void setContactTel(String tel);

    double getLatitude();

    double getLongitude();

    void setIsNull(boolean b);

    List<String> getPictureList();

    void setPicture(String picture);
}
