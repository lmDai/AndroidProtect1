package com.cqyanyu.backing.ui.mvpview.warn;

import android.app.Fragment;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.ArrayList;

/**
 * 警告页面接口view
 * Created by Administrator on 2017/7/7.
 */
public interface WarnView extends IBaseView {

    void setViewPage(ArrayList<String> titles, ArrayList<Fragment> xFragment);
}
