package com.cqyanyu.backing.ui.mvpview.warn;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * 警告列表接口view
 * Created by Administrator on 2017/7/11.
 */
public interface HostWarnView extends XPageView {
    String getLabel();

    int getType();
}
