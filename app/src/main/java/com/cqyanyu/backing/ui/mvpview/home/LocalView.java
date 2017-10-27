package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.entity.home.DutyStatusEntity;
import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * 实时定位接口view
 * Created by Administrator on 2017/7/20.
 */
public interface LocalView extends IBaseView {

    /**
     * 在地图上添加mark标记
     *
     * @param name      名称
     * @param latitude  纬度
     * @param longitude 经度
     */
    void onSetMarkOnMap(String name, double latitude, double longitude);

    void setLocation(DutyStatusEntity dutyStatusEntity);
}
