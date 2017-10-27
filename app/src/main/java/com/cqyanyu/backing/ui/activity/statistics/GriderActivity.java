package com.cqyanyu.backing.ui.activity.statistics;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.statistics.GriderView;
import com.cqyanyu.backing.ui.presenter.statistics.GriderPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 联网用户（网格员）
 */
public class GriderActivity extends BaseActivity<GriderPresenter> implements GriderView {
    /**
     * 页面布局控件相关
     */
    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected GriderPresenter createPresenter() {
        return new GriderPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_grid_satistics;
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
        setTopTitle("联网用户");
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
        if (!has) recyclerView.getAdapter().setData(0, new ArrayList());//不为空时，设置数据
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);//为空视图的显示、隐藏
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void itemEvent(ItemEvent itemEvent) {
        if (itemEvent != null) {
            if (itemEvent.getActivity() == ItemEvent.ACTIVITY.SystemManagementFragment) {
                if (itemEvent.getAction() == ItemEvent.ACTION.refreshing) {
                    if (mPresenter != null) mPresenter.refresh();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
