package com.cqyanyu.backing.ui.presenter.home;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.FireEntity;
import com.cqyanyu.backing.ui.holder.home.AFireHolder;
import com.cqyanyu.backing.ui.mvpview.home.FireView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 火警逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class FirePresenter extends XPagePresenter<FireView> {
    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new AFireHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.TEST;
    }

    @Override
    protected Class getClazz() {
        return FireEntity.class;
    }

    @Override
    protected void setData(List mList) {
        mRecyclerView.getAdapter().setData(0, mList);
    }

    @Override
    protected void onXSuccess(String result) {
        List<FireEntity> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new FireEntity());
        }
        mRecyclerView.getAdapter().setData(0, mList);
    }
}
