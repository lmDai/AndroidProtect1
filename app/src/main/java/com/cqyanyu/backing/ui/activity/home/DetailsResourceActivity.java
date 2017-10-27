package com.cqyanyu.backing.ui.activity.home;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.DetailsResourceEntity;
import com.cqyanyu.backing.ui.mvpview.home.DetailsResourceView;
import com.cqyanyu.backing.ui.presenter.home.DetailsResourcePresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import java.util.List;

/**
 * 详细资源
 * Created by Administrator on 2017/7/11.
 */
public class DetailsResourceActivity extends BaseActivity<DetailsResourcePresenter> implements DetailsResourceView {
    public static final String LABEL = "label";
    public static final String LABEL_VALUE_WATER = "水源";
    public static final String LABEL_VALUE_OTHER = "其他资源";

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private RadioGroup rgDistance;

    @Override
    protected DetailsResourcePresenter createPresenter() {
        return new DetailsResourcePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_details_resource;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        rgDistance = (RadioGroup) findViewById(R.id.rg_distance);
    }

    @Override
    protected void initListener() {
        setTopTitle(getLabel());
        rgDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (mPresenter != null) mPresenter.refresh();
            }
        });
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.initPresenter();
    }

    @Override
    public String getLabel() {
        return getIntent().getStringExtra(LABEL);
    }

    @Override
    public float getDistance() {
        switch (rgDistance.getCheckedRadioButtonId()) {
            case R.id.rb_left:
                return 0.5f;
            case R.id.rb_center:
                return 1f;
            case R.id.rb_right:
                return 3f;
            default:
                return 0;
        }
    }

    @Override
    public List<DetailsResourceEntity> getOtherResource() {
        try {
            String other = getIntent().getStringExtra("other_resource");
            return JSON.parseArray(other, DetailsResourceEntity.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }
}
