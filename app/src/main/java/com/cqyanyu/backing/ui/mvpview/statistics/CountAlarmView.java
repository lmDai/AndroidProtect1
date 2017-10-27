package com.cqyanyu.backing.ui.mvpview.statistics;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * Created by Administrator on 2017/7/11.
 */
public interface CountAlarmView extends XPageView {
    String getLabel();

    int getType();

    String getStartDate();

    String getEndDate();
}
