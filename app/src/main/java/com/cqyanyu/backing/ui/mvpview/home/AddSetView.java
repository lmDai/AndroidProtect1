package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 增加设备接口view
 * Created by Administrator on 2017/7/11.
 */
public interface AddSetView extends IBaseView {

    String getLabel();

    String getOid();

    void setStartData(String data);

    String getInspectionUnitStr();

    void setPicture(String picture);

    void setLan(double latitude, double longitude);

    String getUnitId();

    String getStartDate();

    String getBuild();

    void setBuild(String build, String buildStr);

    String getType();

    void setType(String type, String typeStr);

    String getNum();

    void setNum(String num);

    String getPosition();

    void setPosition(String position);

    String getFactory();

    void setFactory(String factory);

    String getBrand();

    void setBrand(String brand);

    String getGuarantee();

    void setGuarantee(String guarantee);

    String getInspectionWay();

    void setInspectionWay(String way);

    String getInspectionUnit();

    void setInspectionUnit(String unit);

    String getSize();

    void setSize(String size);

    String getFloor();

    void setFloor(String floor);

    double getLatitude();

    double getLongitude();

    List<String> getPictureList();

    void setUnitID(String unitid);

    void setInspectionUnitName(String unitstr);
}
