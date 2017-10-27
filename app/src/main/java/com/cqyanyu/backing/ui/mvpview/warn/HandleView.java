package com.cqyanyu.backing.ui.mvpview.warn;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 处理告警接口view
 * Created by Administrator on 2017/7/18.
 */
public interface HandleView extends IBaseView {

    void setProblemList(List<String> listName, List<String> listId);

    String getLabel();

    String getType();

    String getOid();

    String getPid();

    String getDeviceID();

    String getBuildId();

    String getAffairID();

    String getDate();

    String getUnitId();

    String getHandle();

    String getRemark();
}
