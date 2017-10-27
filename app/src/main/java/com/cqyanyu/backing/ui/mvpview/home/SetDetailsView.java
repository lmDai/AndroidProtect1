package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 设备详情接口view
 * Created by Administrator on 2017/7/18.
 */
public interface SetDetailsView extends IBaseView {

    void setProblemList(List<String> listName, List<String> listId);

    void onTask();

    void setCode(String code);

    void setType(String type);

    void setInspectionState(String state);

    void setPosition(String position);

    String getScanResult();

    String getTaskIds();

    String getDescribe();

    String getProblemId();

    boolean getIsProblem();

    void setIsProblem(boolean b);

    List<String> getPictureList();

    void gotoScan();

    void setAllInspectionRate(float myInspectionRate);

    void setEnable(boolean b);

    void setPicture(String picpath);

    void setDescibe(String s);

    String getLable();

    void setResult(String statusstr);
}
