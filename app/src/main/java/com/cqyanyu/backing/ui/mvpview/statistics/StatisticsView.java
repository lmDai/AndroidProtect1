package com.cqyanyu.backing.ui.mvpview.statistics;

import android.app.Fragment;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计页面接口view
 * Created by Administrator on 2017/7/7.
 */
public interface StatisticsView extends IBaseView {
    void setViewPage(List<String> titles, ArrayList<Fragment> xFragment);
}
