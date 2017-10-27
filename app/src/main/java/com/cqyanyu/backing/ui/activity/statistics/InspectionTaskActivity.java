package com.cqyanyu.backing.ui.activity.statistics;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.statistics.TypeEntity;
import com.cqyanyu.backing.ui.mvpview.statistics.InspectionTaskView;
import com.cqyanyu.backing.ui.presenter.statistics.InspectionTaskPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 设施巡检（巡检任务）
 */
public class InspectionTaskActivity extends BaseActivity<InspectionTaskPresenter> implements InspectionTaskView {
    public static int LABEL_TYPE = 0;
    /**
     * 页面布局控件相关
     */
    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private Spinner spinSelector;

    @Override
    protected InspectionTaskPresenter createPresenter() {
        return new InspectionTaskPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_inspection_task;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        spinSelector = (Spinner) findViewById(R.id.spin_time);
        setSpinner();
    }

    @Override
    protected void initListener() {
        setTopTitle("设施巡检");
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

    /**
     * 下拉选择列表
     */
    private void setSpinner() {
        ArrayList<TypeEntity> list = new ArrayList<>();
        TypeEntity month = new TypeEntity();
        month.setContent("月");
        month.setType(2);
        TypeEntity date = new TypeEntity();
        date.setContent("日");
        date.setType(1);
        list.add(month);
        list.add(date);
        //为下拉列表定义一个适配器
        final ArrayAdapter<TypeEntity> adapter = new ArrayAdapter<>(mContext, R.layout.item_spinner, list);
        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到下拉列表上
        spinSelector.setAdapter(adapter);
        spinSelector.setSelection(1, true);
        spinSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public int getType() {
        TypeEntity typeEntity = (TypeEntity) spinSelector.getSelectedItem();
        LABEL_TYPE = typeEntity.getType();
        return typeEntity.getType();
    }
}