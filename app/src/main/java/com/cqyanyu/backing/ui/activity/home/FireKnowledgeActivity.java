package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.mvpview.home.FireKnowledgeView;
import com.cqyanyu.backing.ui.presenter.home.FireKnowledgePresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

public class FireKnowledgeActivity extends BaseActivity<FireKnowledgePresenter> implements FireKnowledgeView {
    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;


    @Override
    protected FireKnowledgePresenter createPresenter() {
        return new FireKnowledgePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_fire_knowledge;
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
    public XRecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void hasShowData(boolean has) {
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initData() {
        setTopTitle("消防安全常识");
        if (mPresenter != null) {
            mPresenter.initPresenter();
        }
    }
}
