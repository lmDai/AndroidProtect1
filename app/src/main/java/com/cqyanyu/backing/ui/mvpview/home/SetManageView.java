package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.presenter.base.XPageView;

/**
 * 系统管理碎片接口view
 * Created by Administrator on 2017/7/11.
 */
public interface SetManageView extends XPageView {
    String getLabel();//获取类型

    String getSearchText();//输入内容

    String getType();
}
