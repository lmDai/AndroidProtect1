package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.mvpview.home.CheckPostView;
import com.cqyanyu.backing.ui.presenter.home.CheckPostPresenter;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import java.util.List;

/**
 * 一键查岗
 * Created by Administrator on 2017/7/18.
 */
public class CheckPostActivity extends BaseActivity<CheckPostPresenter> implements CheckPostView {

    private XRecyclerView recyclerView;
    private ImageView ivNotData;
    private TextView tvNotData;
    private LinearLayout layoutNotData;
    private CheckBox cbChoiceAll;
    private Button btnSend;

    @Override
    protected CheckPostPresenter createPresenter() {
        return new CheckPostPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_check_post;
    }

    @Override
    protected void initView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recycler_view);
        ivNotData = (ImageView) findViewById(R.id.iv_not_data);
        tvNotData = (TextView) findViewById(R.id.tv_not_data);
        layoutNotData = (LinearLayout) findViewById(R.id.layout_not_data);
        cbChoiceAll = (CheckBox) findViewById(R.id.cb_choice_all);
        btnSend = (Button) findViewById(R.id.btn_send);
    }

    @Override
    protected void initListener() {
        btnSend.setOnClickListener(this);
        cbChoiceAll.setOnClickListener(this);
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
        layoutNotData.setVisibility(has ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                mPresenter.requestDutyLaunch();
                break;
            case R.id.cb_choice_all:
                //全选
                clickAll(cbChoiceAll.isChecked());
                break;
        }
    }

    /**
     * 单项选择
     */
    public void clickItem() {
        try {
            List<UserManageEntity> mList = recyclerView.getAdapter().getData(0);
            if (mList != null) {
                for (int i = 0; i < mList.size(); i++) {
                    if (!mList.get(i).isClickable()) {
                        cbChoiceAll.setChecked(false);
                        return;
                    }
                }
                cbChoiceAll.setChecked(true);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * 全选
     */
    private void clickAll(boolean isAll) {
        try {
            List<UserManageEntity> mList = recyclerView.getAdapter().getData(0);
            if (mList != null) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setClickable(isAll);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void setClickAll(boolean clickAll) {
        cbChoiceAll.setChecked(clickAll);
    }
}
