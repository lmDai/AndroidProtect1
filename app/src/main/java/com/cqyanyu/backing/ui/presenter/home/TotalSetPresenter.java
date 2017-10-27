package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.TotalSetEntity;
import com.cqyanyu.backing.ui.holder.home.TotalSetHolder;
import com.cqyanyu.backing.ui.mvpview.home.TotalSetView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 设备总数逻辑处理
 * Created by Administrator on 2017/7/10.
 */
public class TotalSetPresenter extends XPagePresenter<TotalSetView> {
    private int pagecount = 20;
    private int pageindex = 0;
    private int total = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new TotalSetHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        if (getView().getTypeId() != null) {
            paramsMap.put("pid", getView().getTypeId());
        } else {
            paramsMap.put("pid", "3");
        }
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_TOTAL_DEVICE_CLASSIFY;
    }

    @Override
    protected Class getClazz() {
        return TotalSetEntity.class;
    }

    @Override
    public void refresh() {
        pageindex = 0;
        total = 0;
        super.refresh();
    }

    @Override
    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
//                JSONObject object = new JSONObject(result);
//                String trueResult = object.optString("rows");
                List mList = JSON.parseArray(result, getClazz());
                if (mList != null && mList.size() > 0) {
                    /**设置当前页数*/
                    setData(mList);
                    /**显示数据*/
                    if (getView() != null) {
                        getView().hasShowData(true);
                    }
//                    page = page + 1;
//                    /**设置是否可以下拉加载*/
//                    isLoad = page * pageSize < mList.size();
//                    if (page * pageSize > mList.size()) {
//                        setData(mList.subList((page - 1) * pageSize, mList.size()));
//                    } else {
//                        setData(mList.subList((page - 1) * pageSize, page * pageSize));
//                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (total == 0) onLoadNoData();
    }

    @Override
    protected void setData(List mList) {
        List<TotalSetEntity> list = mList;
        mRecyclerView.getAdapter().setData(0, list);
    }
}
