package com.cqyanyu.backing.ui.mvpview.home;

import android.app.Fragment;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统管理接口view
 * Created by Administrator on 2017/7/10.
 */
public interface SystemManagementView extends IBaseView {

    void setViewPage(List<String> titles, ArrayList<Fragment> xFragment);
}
