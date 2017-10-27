package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.ChildSetView;
import com.cqyanyu.backing.ui.presenter.home.ChildSetPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 子设备列表
 */
public class ChildSetActivity extends BaseActivity<ChildSetPresenter> implements ChildSetView {


    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected ChildSetPresenter createPresenter() {
        return new ChildSetPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_child_set;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        setTopTitle("子设备");
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public int getType() {
        return 2;
    }
}
