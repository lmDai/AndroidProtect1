package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.mvpview.home.EntryView;
import com.cqyanyu.backing.ui.presenter.home.EntryPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

/**
 * 词条
 * Created by Administrator on 2017/7/19.
 */
public class EntryActivity extends BaseActivity<EntryPresenter> implements EntryView {

    public static final String LABEL = "label";
    public static final String KEY_OID = "父词条id";

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;

    /*
    startActivity(new Intent(mContext, EntryActivity.class)
                        .putExtra(EntryActivity.LABEL, )
                        .putExtra(EntryActivity.KEY_OID, )
                );
     */

    @Override
    protected EntryPresenter createPresenter() {
        return new EntryPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sort_choice;
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
        setTitle(getLabel());
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(getOid())) finish();
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
        return getIntent().getStringExtra(KEY_OID);
    }

    @Override
    protected void onResume() {
        /**下级选择完成，便可以取到值*/
        EntryEntity event = EventBus.getDefault().getStickyEvent(EntryEntity.class);
        if (event != null) {
            finish();
        }
        super.onResume();
    }

    /**
     * 前往下级页面
     *
     * @param entity  单位名称
     * @param hasNext 是否有下级页面
     */
    public void gotoNext(EntryEntity entity, boolean hasNext) {
        if (hasNext) {
            startActivity(new Intent(mContext, EntryActivity.class)
                    .putExtra(ProvinceActivity.LABEL, getLabel())
                    .putExtra(KEY_OID, entity.getOid())
            );
        } else {
            entity.result = getLabel();
            EventBus.getDefault().postSticky(entity);
            finish();
        }
    }
}
