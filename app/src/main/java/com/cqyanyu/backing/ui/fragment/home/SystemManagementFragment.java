package com.cqyanyu.backing.ui.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.mvpview.home.SystemManagementLView;
import com.cqyanyu.backing.ui.presenter.home.SystemManagementLPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 系统管理碎片
 * Created by Administrator on 2017/7/11.
 */
public class SystemManagementFragment extends BaseFragment<SystemManagementLPresenter> implements SystemManagementLView {
    private static final String LABEL = "label";
    public static final String LABEL_VALUE_UNIT = "单位管理";
    public static final String LABEL_VALUE_SET = "设备管理";
    public static final String LABEL_VALUE_USER = "用户管理";

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private boolean IS_LOADED = false;
    private static int mSerial = 0;
    private int mTabPos = 0;
    private boolean isFirst = true;

    @Override
    protected SystemManagementLPresenter createPresenter() {
        return new SystemManagementLPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_system_management;
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
//        if (mPresenter != null) mPresenter.initPresenter();
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
                    if (mTabPos == mSerial) {
                        if (mPresenter != null) mPresenter.refresh();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mTabPos = 0;
        mSerial = 0;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (!IS_LOADED) {
                IS_LOADED = true;
                //这里执行加载数据的操作
                if (mPresenter != null) mPresenter.initPresenter();
            }
            return;
        }
    };

    public void setTabPos(int mTabPos) {
        this.mTabPos = mTabPos;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFirst && mTabPos == mSerial) {
            isFirst = false;
            sendMessage(mSerial);
        }
    }

    public void sendMessage(int mSerial) {
        this.mSerial = mSerial;
        Message message = handler.obtainMessage();
        message.sendToTarget();
    }
}
