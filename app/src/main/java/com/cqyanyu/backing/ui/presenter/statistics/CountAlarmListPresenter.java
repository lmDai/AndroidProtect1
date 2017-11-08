package com.cqyanyu.backing.ui.presenter.statistics;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.activity.statistics.CountFaultActivity;
import com.cqyanyu.backing.ui.activity.statistics.CountFireActivity;
import com.cqyanyu.backing.ui.entity.home.AlarmNHEntity;
import com.cqyanyu.backing.ui.entity.warn.WaterSystemEntity;
import com.cqyanyu.backing.ui.holder.statistics.CountAlarmHolder;
import com.cqyanyu.backing.ui.holder.statistics.CountWarnHolder;
import com.cqyanyu.backing.ui.mvpview.statistics.CountAlarmView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 火警列表逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class CountAlarmListPresenter extends XPagePresenter<CountAlarmView> implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            switch (getView().getType()) {
                case 1://火警
                    mRecyclerView.getAdapter().bindHolder(new CountAlarmHolder());
                    break;
                case 2://故障
                    mRecyclerView.getAdapter().bindHolder(new CountAlarmHolder());
                    break;
                case 3://预警
                    mRecyclerView.getAdapter().bindHolder(new CountWarnHolder());
                    break;
                case 4://其他
                    mRecyclerView.getAdapter().bindHolder(new CountAlarmHolder());
                    break;
            }
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        switch (getView().getType()) {
            //消息类型 0：主机 1：预警
            case 1: //火警
                if (!TextUtils.equals(getView().getLabel(), CountFireActivity.HANDLE)) {
                    paramsMap.put("nohandle", "1");
                }
                paramsMap.put("affairid", "1");
                break;
            case 2: //故障
                if (!TextUtils.equals(getView().getLabel(), CountFaultActivity.HANDLE)) {
                    paramsMap.put("nohandle", "1");
                }
                paramsMap.put("affairid", "2");
                break;
            case 4: //其他
                if (!TextUtils.equals(getView().getLabel(), CountFireActivity.HANDLE)) {
                    paramsMap.put("nohandle", "1");
                }
                paramsMap.put("affairid", "3");
                break;
            case 3://预警

                break;
        }
        paramsMap.put("enddate", getView().getEndDate());
        paramsMap.put("startdate", getView().getStartDate());
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        switch (getView().getType()) {
            case 1: //火警
            case 2: //故障
            case 4: //其他
                return ConstHost.GET_ALARM_URL;
            case 3://预警
                return ConstHost.GET_WARN_New_URL;
        }
        return "";
    }

    @Override
    protected Class getClazz() {
        switch (getView().getType()) {
            case 1: //火警
            case 2: //故障
            case 4: //其他
                return AlarmNHEntity.class;
            case 3://预警
                return WaterSystemEntity.class;
        }
        return null;
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
    protected void setData(List mList) {
        if (pageindex == 0) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }
}