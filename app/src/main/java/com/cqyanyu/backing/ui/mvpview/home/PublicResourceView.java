package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.entity.home.MapDeviceInfo;
import com.cqyanyu.backing.ui.entity.home.MapUnitInfo;
import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 统计分析接口view
 * Created by Administrator on 2017/7/7.
 */
public interface PublicResourceView extends IBaseView {

    /**
     * 在地图上设置mark标记
     *
     * @param type      类型
     * @param name      名称
     * @param describe  描述
     * @param latitude  维度
     * @param longitude 经度
     */
    void setMark(int type, String name, String describe, double latitude, final double longitude);

    /**
     * 在mark上添加标记
     *
     * @param name      名称
     * @param describe  描述
     * @param latitude  维度
     * @param longitude 经度
     */
    void addTextOnMark(String name, String describe, double latitude, final double longitude);


    void setLoation(double latitude, double longitude);

    void setUnitList(List<MapUnitInfo> mListUnit);

    void setDeviceList(List<MapDeviceInfo> mList);
}
