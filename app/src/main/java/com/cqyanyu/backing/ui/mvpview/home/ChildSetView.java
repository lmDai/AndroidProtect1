package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * 子设备
 * Created by Administrator on 2017/7/11.
 */
public interface ChildSetView extends XPageView {
    String getLabel();

    int getType();
}
