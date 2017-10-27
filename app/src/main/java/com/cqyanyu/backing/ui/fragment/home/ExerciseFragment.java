package com.cqyanyu.backing.ui.fragment.home;

import android.os.Bundle;
import android.view.View;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.mvpframework.presenter.BasePresenter;


/**
 * 仿真演练
 * Created by Administrator on 2017/7/18.
 */

public class ExerciseFragment extends BaseFragment {
    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_exercise;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
