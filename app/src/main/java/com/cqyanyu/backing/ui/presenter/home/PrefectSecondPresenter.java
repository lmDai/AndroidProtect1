package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.PerfectSecondEntity;
import com.cqyanyu.backing.ui.holder.home.PerfectSecondHolder;
import com.cqyanyu.backing.ui.mvpview.home.PrefectSecondView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 完好率逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class PrefectSecondPresenter extends XPagePresenter<PrefectSecondView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new PerfectSecondHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("mproblemid", getView().getmTypeId());
        paramsMap.put("mcondition", getView().getmCondition());
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    public void refresh() {
        pageindex = 0;
        super.refresh();
    }

    @Override
    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                org.json.JSONObject object = new org.json.JSONObject(result);
                int total = object.optInt("total");
                int count = object.optInt("count");
                String trueResult = object.optString("rows");
                List mList = JSON.parseArray(trueResult, getClazz());
                if (mList != null && mList.size() > 0) {
                    /**显示数据*/
                    if (getView() != null) getView().hasShowData(true);
                    setData(mList);
                    /**设置当前页数*/
                    pageindex += count;
                    /**设置是否可以下拉加载*/
                    isLoad = pageindex < total;
                    return;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (pageindex == 0) onLoadNoData();
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_PERFECT_SECOND_RATE;
    }

    @Override
    protected Class getClazz() {
        return PerfectSecondEntity.class;
    }

    @Override
    protected void setData(List mList) {
        /**通告 获取父设备接口的oid 和获取任务的deviceid 合并数据 要isproblem*/
        if (pageindex == 0) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }
}
