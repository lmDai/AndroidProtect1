package com.cqyanyu.backing.ui.mvpview.my;

import android.app.Fragment;

import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息中心碎片管理接口
 * Created by Administrator on 2017/7/11.
 */

public interface MsgCenterView extends IBaseView {
    void setViewPage(List<String> titles, ArrayList<Fragment> mFragments);
}
