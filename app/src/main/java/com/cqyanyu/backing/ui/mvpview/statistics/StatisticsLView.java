package com.cqyanyu.backing.ui.mvpview.statistics;

import com.cqyanyu.backing.ui.presenter.base.XPageView;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 统计碎片接口view
 * Created by Administrator on 2017/7/11.
 */
public interface StatisticsLView extends XPageView {
    String getLabel();//获取标签

    XRecyclerView getRecyclerView();//获取recyclerview

    int getType();
}
