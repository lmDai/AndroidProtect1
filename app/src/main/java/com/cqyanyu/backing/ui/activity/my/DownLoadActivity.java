package com.cqyanyu.backing.ui.activity.my;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

public class DownLoadActivity extends BaseActivity<BasePresenter> {


    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_down_load;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
