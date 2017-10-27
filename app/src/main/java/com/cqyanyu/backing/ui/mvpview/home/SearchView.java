package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * 搜索接口
 * Created by Administrator on 2017/7/11.
 */
public interface SearchView extends XPageView {
    String getLabel();
    String getSearchText();
}
