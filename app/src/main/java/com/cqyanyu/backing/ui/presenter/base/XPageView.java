package com.cqyanyu.backing.ui.presenter.base;

import com.cqyanyu.mvpframework.view.IBaseView;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 拓展的列表接口view
 * Created by Administrator on 2017/7/6.
 */
public interface XPageView extends IBaseView {

    /**
     * 获取XRecyclerView
     *
     * @return XRecyclerView
     */
    XRecyclerView getRecyclerView();

    /**
     * 设置是否拥有数据
     *
     * @param has 是否拥有数据
     */
    void hasShowData(boolean has);
}
