package com.cqyanyu.backing.ui.presenter.home;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.FireKnowledgeEntity;
import com.cqyanyu.backing.ui.holder.home.FireKnowledgeHolder;
import com.cqyanyu.backing.ui.mvpview.home.FireKnowledgeView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 消防知识逻辑处理
 * Created by Administrator on 2017/7/18.
 */

public class FireKnowledgePresenter extends XPagePresenter<FireKnowledgeView> {

    @Override
    public void initPresenter() {
        if (getView() != null) {
            //初始化RecyclerView
            setRecyclerView();
            //为RecyclerView绑定数据
            mRecyclerView.getAdapter().bindHolder(new FireKnowledgeHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        return new ParamsMap();
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_TRAINING_URL;
    }

    @Override
    protected Class getClazz() {
        return FireKnowledgeEntity.class;
    }

    @Override
    protected void setData(List mList) {
        mRecyclerView.getAdapter().setData(0, mList);
    }
}