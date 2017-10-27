package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * 词条接口view
 * Created by Administrator on 2017/7/19.
 */
public interface EntryView extends XPageView {
    String getLabel();

    String getOid();
}
