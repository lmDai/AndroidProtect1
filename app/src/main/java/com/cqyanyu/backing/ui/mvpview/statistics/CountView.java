package com.cqyanyu.backing.ui.mvpview.statistics;

import com.cqyanyu.backing.ui.entity.statistics.CountBean;
import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public interface CountView extends IBaseView {
    void setCountItem(List<CountBean> hList);

    void setAlarmTotal(List<Float> alarmTotals, List<Float> handleTotals);

    void updateData();

    void setWarnCount(int warnCount);
}
