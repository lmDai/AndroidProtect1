package com.cqyanyu.backing.ui.fragment.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.mvpview.my.MsgCenterListView;
import com.cqyanyu.backing.ui.presenter.my.MsgCenterListPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 消息中心碎片管理
 * Created by Administrator on 2017/7/13.
 */

public class MsgCenterFragment extends BaseFragment<MsgCenterListPresenter> implements MsgCenterListView {

    public static final String LABEL = "label";
    public static final String LABEL_VALUE_RECORDS = "查岗记录";
    public static final String LABEL_VALUE_CONSULATION = "消防资讯";
    public static final String LABEL_VALUE_SYSTEMINFO = "系统消息";
    public static final String LABEL_VALUE_NOTIFICATION = "通知通告";

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected MsgCenterListPresenter createPresenter() {
        return new MsgCenterListPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_msg_center;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        recyclerView = (XRecyclerView) rootView.findViewById(R.id.recycler_view);
        ivNotData = (ImageView) rootView.findViewById(R.id.iv_not_data);
        tvNotData = (TextView) rootView.findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) rootView.findViewById(R.id.layout_not_data);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public String getLabel() {
        return getArguments().getString(LABEL);
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
