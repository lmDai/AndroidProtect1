package com.cqyanyu.backing.ui.mvpview.login;

import android.app.Fragment;

import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cqyanyu.backing.ui.entity.home.FireKnowledgeEntity;
import com.cqyanyu.backing.ui.entity.home.VersionEntity;
import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.ArrayList;

/**
 * 主页接口view
 * Created by Administrator on 2017/7/6.
 */
public interface MainView extends IBaseView {
    void setAllInspectionRate(float rate);

    void setViewPager(ArrayList<Fragment> fList);

    void setNavigationBar(ArrayList<BottomNavigationItem> bList);

    void returnUpdate(VersionEntity entity);

    void getVoice(FireKnowledgeEntity entity);
}
