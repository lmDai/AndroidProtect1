package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * 省市接口view
 * Created by Administrator on 2017/7/12.
 */
public interface ProvinceView extends XPageView {
    String getLabel();

    String getPid();

    /**
     * 是否必须选择最后一级
     *
     * @return 是否
     */
    boolean isMustSelectLastGrade();

    boolean isMineId();
}
