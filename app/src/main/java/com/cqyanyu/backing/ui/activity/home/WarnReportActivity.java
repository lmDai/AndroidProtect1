package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.WarnReportView;
import com.cqyanyu.backing.ui.presenter.home.WarnReportPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

/**
 * 告警详细上报记录
 */
public class WarnReportActivity extends BaseActivity<WarnReportPresenter> implements WarnReportView {


    public static final String LABEL = "label";
    public static final String LABEL_VALUE_WARN = "水系统预警";
    public static final String LABEL_VALUE_HOST = "主机预警";
    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    @Override
    protected WarnReportPresenter createPresenter() {
        return new WarnReportPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_warn_report;
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
        setTopTitle("历史记录");
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
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public String getOid() {
        return getIntent().getStringExtra("oid");
    }
}
