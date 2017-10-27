package com.cqyanyu.backing.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseFragment;
import com.cqyanyu.backing.ui.activity.home.FireKnowledgeActivity;
import com.cqyanyu.backing.ui.activity.home.OnLineAnswerActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.view.ItemOptionView;


/**
 * 在线培训
 * Created by Administrator on 2017/7/18.
 */

public class OnlineTrainingFragment extends BaseFragment {
    private ItemOptionView iovOnlineAnswer;
    private ItemOptionView iovFireKnowledge;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        iovOnlineAnswer = (ItemOptionView) view.findViewById(R.id.iov_online_answer);
        iovFireKnowledge = (ItemOptionView) view.findViewById(R.id.iov_fire_knowledge);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_online_training;
    }

    @Override
    protected void initListener() {
        iovOnlineAnswer.setOnClickListener(this);
        iovFireKnowledge.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iov_online_answer:
                if (InfoManger.getInstance().isPermission("55")) {
                    startActivity(new Intent(mContext, OnLineAnswerActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
            case R.id.iov_fire_knowledge:
                if (InfoManger.getInstance().isPermission("54")) {
                    startActivity(new Intent(mContext, FireKnowledgeActivity.class));
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
        }
    }
}
