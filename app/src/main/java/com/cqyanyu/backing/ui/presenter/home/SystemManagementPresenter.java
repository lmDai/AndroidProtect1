package com.cqyanyu.backing.ui.presenter.home;

import android.app.Fragment;
import android.os.Bundle;

import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.fragment.alarm.AlarmFragment;
import com.cqyanyu.backing.ui.fragment.home.SystemManagementFragment;
import com.cqyanyu.backing.ui.mvpview.home.SystemManagementView;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统管理逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class SystemManagementPresenter extends BasePresenter<SystemManagementView> {

    private ArrayList<Fragment> xFragment;
    private List<String> titles;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            xFragment = new ArrayList<>();
            titles = new ArrayList<>();
            if (InfoManger.getInstance().isPermission("39")) {
                addListFragment(SystemManagementFragment.LABEL_VALUE_UNIT);
            }
            if (InfoManger.getInstance().isPermission("42")) {
                addListFragment(SystemManagementFragment.LABEL_VALUE_SET);
            }
            if (InfoManger.getInstance().isPermission("41")) {
                addListFragment(SystemManagementFragment.LABEL_VALUE_USER);
            }
            getView().setViewPage(titles, xFragment);
        }
    }

    private void addListFragment(String label) {
        SystemManagementFragment mFragment = new SystemManagementFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AlarmFragment.LABEL, label);
        mFragment.setArguments(bundle);
        xFragment.add(mFragment);
        titles.add(label);
    }
}
