package com.cqyanyu.backing.ui.presenter.home;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.WarningEntity;
import com.cqyanyu.backing.ui.holder.home.AWarningHolder;
import com.cqyanyu.backing.ui.mvpview.home.WarningView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 预警逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class WarningPresenter extends XPagePresenter<WarningView> {
    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new AWarningHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_WARN_INFO_URL;
    }

    @Override
    protected Class getClazz() {
        return WarningEntity.class;
    }

    @Override
    protected void setData(List mList) {
        mRecyclerView.getAdapter().setData(0, mList);
    }

    @Override
    protected void onXSuccess(String result) {
        List<WarningEntity> mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(new WarningEntity());
        }
        mRecyclerView.getAdapter().setData(0, mList);
    }
}
