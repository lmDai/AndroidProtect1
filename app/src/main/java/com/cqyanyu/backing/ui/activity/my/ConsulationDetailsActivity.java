package com.cqyanyu.backing.ui.activity.my;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;

/**
 * 消防资讯
 * Created by Administrator on 2017/7/22.
 */

public class ConsulationDetailsActivity extends BaseActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_consulation_details;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        setTopTitle("消防资讯");
    }
}
