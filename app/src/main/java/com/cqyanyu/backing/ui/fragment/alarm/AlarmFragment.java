package com.cqyanyu.backing.ui.fragment.alarm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.mvpview.warn.HostWarnView;
import com.cqyanyu.backing.ui.presenter.home.WarnPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 警告列表
 * Created by Administrator on 2017/7/11.
 */
public class AlarmFragment extends BaseFragment<WarnPresenter> implements HostWarnView {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_HOST = "消防主机";
    public static final String LABEL_VALUE_WATER_SYSTEM = "水系统";

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
//    private WarnFragment warnFragment;//这是使用ViewPager的页面
//    private boolean is_load = false;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (!is_load) {
//                is_load = true;
//                if (warnFragment.openTabNum < warnFragment.totalTabNum) {
//                    warnFragment.openTabNum++;
//                }
//                if (mPresenter != null) mPresenter.initPresenter();//加载网络数据的方法;//加载网络数据的方法
//            }
//            return;
//        }
//    };

//    public void sendMessage() {
//        Message message = handler.obtainMessage();
//        message.sendToTarget();
//    }

    @Override
    protected WarnPresenter createPresenter() {
        return new WarnPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_warn_list;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        recyclerView = (XRecyclerView) view.findViewById(R.id.recycler_view);
        ivNotData = (ImageView) view.findViewById(R.id.iv_not_data);
        tvNotData = (TextView) view.findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) view.findViewById(R.id.layout_not_data);
//        this.warnFragment = (WarnFragment) getParentFragment();//因为这里是Fragment嵌套Fragment于是是getParentFragment
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
    public int getType() {
        return 0;
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
            if (itemEvent.getActivity() == ItemEvent.ACTIVITY.WarnListFragment) {
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
