package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 增加设备接口view
 * Created by Administrator on 2017/7/11.
 */
public interface AddChildSetView extends IBaseView {

    String getLabel();

    String getOid();

    void setLan(double latitude, double longitude);

    String getUnitId();

    String getBuild();

    String getType();

    void setType(String type);

    String getNum();

    void setNum(String num);

    String getPosition();

    void setPosition(String position);

    String getFloor();

    void setFloor(String floor);

    String getMaxValue();

    void setMaxValue(String maxValue);

    String getMinValue();

    void setMinValue(String minValue);

    double getLatitude();

    double getLongitude();

    List<String> getPictureList();
}
