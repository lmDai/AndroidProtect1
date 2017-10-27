package com.cqyanyu.backing.ui.presenter.home;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.FaultEntity;
import com.cqyanyu.backing.ui.holder.home.AFaultHolder;
import com.cqyanyu.backing.ui.mvpview.home.FaultView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 故障逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class FaultPresenter extends XPagePresenter<FaultView> {
    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new AFaultHolder());
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
        return FaultEntity.class;
    }

    @Override
    protected void setData(List mList) {
        mRecyclerView.getAdapter().setData(0, mList);
    }

    @Override
    protected void onXSuccess(String result) {
        List<FaultEntity> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new FaultEntity());
        }
        mRecyclerView.getAdapter().setData(0, mList);
    }
}
