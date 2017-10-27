package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.ChildAlarmView;
import com.cqyanyu.backing.ui.presenter.home.ChildAlarmPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 *
 */
public class ChildAlarmActivity extends BaseActivity<ChildAlarmPresenter> implements ChildAlarmView {
    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    public static final String LABEL = "label";

    @Override
    protected ChildAlarmPresenter createPresenter() {
        return new ChildAlarmPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_child_alarm;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
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
        setTopTitle("消防报警主机分类");
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra("oid");
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
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

    /**
     * 获取输入框的数据
     *
     * @return
     */
    @Override
    public String getSearchText() {
        return null;
    }

    @Override
    public String getType() {
        return getIntent().getStringExtra("oid");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
