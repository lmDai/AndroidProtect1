package com.cqyanyu.backing.ui.presenter.home;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.holder.home.EntryHolder;
import com.cqyanyu.backing.ui.mvpview.home.EntryView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 词条逻辑处理类
 * Created by Administrator on 2017/7/19.
 */
public class EntryPresenter extends XPagePresenter<EntryView> {

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**设置RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new EntryHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        if (getView() != null) {
            paramsMap.put("pid", getView().getOid());
        }
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_DICTION_LIST_URL;
    }

    @Override
    public Class getClazz() {
        return EntryEntity.class;
    }

    @Override
    public void setData(List mList) {
        mRecyclerView.getAdapter().setData(0, mList);
    }
}
