package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.TotalSetView;
import com.cqyanyu.backing.ui.presenter.home.TotalSetPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import static com.cqyanyu.backing.ui.activity.home.ProvinceActivity.KEY_PID;

/**
 * 设备总数
 * Created by Administrator on 2017/7/10.
 */
public class TotalSetActivity extends BaseActivity<TotalSetPresenter> implements TotalSetView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private TextView tvLocal;

    @Override
    protected TotalSetPresenter createPresenter() {
        return new TotalSetPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_total_set;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        tvLocal = (TextView) findViewById(R.id.tv_local);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        BDLocation bdLocation = EventBus.getDefault().getStickyEvent(BDLocation.class);
//        //修改默认地区
//        if (bdLocation != null) {
//            tvLocal.setText(bdLocation.getSemaAptag());
//        } else {
//            XToastUtil.showToast("请先定位!否则将无法使用该功能");
//            finish();
//        }

        setTopTitle(TextUtils.isEmpty(getIntent().getStringExtra("typeStr")) ? "设备总数" : getIntent().getStringExtra("typeStr"));
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
    public String getTypeId() {
        return getIntent().getStringExtra(KEY_PID);
    }

    public void gotoNext(String typeId, String typeStr) {
        startActivity(new Intent(mContext, TotalSetActivity.class)
                .putExtra(KEY_PID, typeId)
                .putExtra("typeStr", typeStr));
    }

}
