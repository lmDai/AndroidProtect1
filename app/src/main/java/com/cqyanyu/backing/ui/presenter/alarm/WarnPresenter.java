package com.cqyanyu.backing.ui.presenter.alarm;

import android.app.Fragment;
import android.os.Bundle;

import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.fragment.alarm.AlarmFragment;
import com.cqyanyu.backing.ui.mvpview.warn.WarnView;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * 警告逻辑处理类
 * Created by Administrator on 2017/7/7.
 */
public class WarnPresenter extends BasePresenter<WarnView> {
    private ArrayList<Fragment> xFragment;
    private ArrayList<String> titles;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            xFragment = new ArrayList<>();
            titles = new ArrayList<>();
            if (InfoManger.getInstance().isPermission("161")) {//消防主机
                addListFragment(AlarmFragment.LABEL_VALUE_HOST);
            }
            if (InfoManger.getInstance().isPermission("162")) {//水系统
                addListFragment(AlarmFragment.LABEL_VALUE_WATER_SYSTEM);
            }
            getView().setViewPage(titles, xFragment);
        }
    }

    private void addListFragment(String label) {
        AlarmFragment mFragment = new AlarmFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AlarmFragment.LABEL, label);
        mFragment.setArguments(bundle);
        xFragment.add(mFragment);
        titles.add(label);
    }
}
