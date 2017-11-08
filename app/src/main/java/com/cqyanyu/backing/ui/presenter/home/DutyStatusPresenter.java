package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.DutyStatusEntity;
import com.cqyanyu.backing.ui.holder.home.DutyStatusHolder;
import com.cqyanyu.backing.ui.mvpview.home.DutyStatusView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 当值状态逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class DutyStatusPresenter extends XPagePresenter<DutyStatusView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new DutyStatusHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        long sec = (MyDate.getTodayZero() / 1000);
        long startdate = sec - (86400 * 15);
        long enddate = sec;
        ParamsMap paramsMap = new ParamsMap();
        //paramsMap.put("startdate",startdate + "");
        //paramsMap.put("enddate",enddate + "");
        if (!TextUtils.isEmpty(getView().getmCondition())) {
            paramsMap.put("mcondition", getView().getmCondition());
        }
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        paramsMap.put("type", "1");//类型 0:发起 1:回复
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
                    if (getView() != null) {
                        getView().hasShowData(true);
                    }
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
        return ConstHost.GET_DUTY_TOTAL_Page_URL;
    }

    @Override
    protected Class getClazz() {
        return DutyStatusEntity.class;
    }

    @Override
    protected void setData(List mList) {
        if (pageindex == 0) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }
}
