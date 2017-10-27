package com.cqyanyu.backing.ui.mvpview.warn;

import com.cqyanyu.mvpframework.view.IBaseView;

/**
 * 告警地址处理
 * Created by Administrator on 2017/7/7.
 */
public interface WarnPositionView extends IBaseView {

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


    String getOid();

    String getBuildStr();

    String getPosition();

    double getLatitude();

    double getLongitude();
}
