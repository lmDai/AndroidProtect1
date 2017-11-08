package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public interface WarnListReportView extends IBaseView {
    String getPid();

    void setAlarmTotal(List<Float> alarmTotals, List<String> xValues);

    void setTxtUnit(String txtUnit);

    void setTxtSn(String txtSn);

    void setTxtValue(String txtValue);

    void setTxtType(String txtType);

    void setTxtDate(String txtDate);

    void setTxtShuiya(String txtShuiya);


    void setTxtCount(String txtCount);


    void setTxtWaterHigh(String txtWaterHigh);


    void setTxtWaterLow(String txtWaterLow);

    String getStartDate();

    String getEndDate();

    int getClickable();

    void setNoData(int clickable);
}
