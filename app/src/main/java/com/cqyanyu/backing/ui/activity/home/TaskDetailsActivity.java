package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.TaskDetailsView;
import com.cqyanyu.backing.ui.presenter.home.TaskDetailsPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 任务详情
 * Created by Administrator on 2017/7/25.
 */
public class TaskDetailsActivity extends BaseActivity<TaskDetailsPresenter> implements TaskDetailsView {
    public static final String KEY_TASK_ID = "任务id";
    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected TaskDetailsPresenter createPresenter() {
        return new TaskDetailsPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_task_details;
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
    public String getTaskIds() {
        return getIntent().getStringExtra(KEY_TASK_ID);
    }
}
