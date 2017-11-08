package com.cqyanyu.backing.ui.presenter.home;

import android.app.Fragment;

import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.fragment.home.ExerciseFragment;
import com.cqyanyu.backing.ui.fragment.home.OnlineTrainingFragment;
import com.cqyanyu.backing.ui.mvpview.home.PropagandaTrainingView;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 宣传培训逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class PropagandaTrainingPresenter extends BasePresenter<PropagandaTrainingView> {

    private ArrayList<Fragment> xFragment;
    private List<String> titles;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            xFragment = new ArrayList<>();
            titles = new ArrayList<>();
            if (InfoManger.getInstance().isPermission("55")) {//在线培训
                OnlineTrainingFragment onlineTrainingFragment = new OnlineTrainingFragment();
                xFragment.add(onlineTrainingFragment);
                titles.add("在线培训");
            }
            if (InfoManger.getInstance().isPermission("56")) {//仿真演练
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                xFragment.add(exerciseFragment);
                titles.add("仿真演练");
            }
            getView().setViewPage(titles, xFragment);
        }
    }
}
