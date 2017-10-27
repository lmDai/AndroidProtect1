package com.cqyanyu.backing.ui.activity.home;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

/**
 * Created by Administrator on 2017/7/18.
 */

public class OnLineAnswerActivity extends BaseActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_online_answer;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        setTopTitle("在线答题");
    }
}
