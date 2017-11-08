package com.cqyanyu.backing.ui.presenter.my;

import android.app.Fragment;
import android.os.Bundle;

import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.fragment.home.ExerciseFragment;
import com.cqyanyu.backing.ui.fragment.my.MsgCenterFragment;
import com.cqyanyu.backing.ui.mvpview.my.MsgCenterView;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * 消息中心碎片管理
 * Created by Administrator on 2017/7/11.
 */

public class MsgCenterPresenter extends BasePresenter<MsgCenterView> {
    private ArrayList<Fragment> xFragment;
    private ArrayList<String> titles;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            xFragment = new ArrayList<>();
            titles = new ArrayList<>();
            if (InfoManger.getInstance().isPermission("82")){//查岗记录
                addListFragment(MsgCenterFragment.LABEL_VALUE_RECORDS);
            }
            if (InfoManger.getInstance().isPermission("83")) {//通知通告
                //假页面，占位使用
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                xFragment.add(exerciseFragment);
                titles.add(MsgCenterFragment.LABEL_VALUE_NOTIFICATION);
            }
            // addListFragment(MsgCenterFragment.LABEL_VALUE_NOTIFICATION);
            getView().setViewPage(titles, xFragment);
        }
    }

    /**
     * 添加碎片
     */
    private void addListFragment(String label) {
        MsgCenterFragment mFragment = new MsgCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MsgCenterFragment.LABEL, label);
        mFragment.setArguments(bundle);
        xFragment.add(mFragment);
        titles.add(label);
    }
}
