package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.activity.home.WarnReportActivity;
import com.cqyanyu.backing.ui.entity.warn.WaterReportEntity;
import com.cqyanyu.backing.ui.holder.warn.HostReportHolder;
import com.cqyanyu.backing.ui.holder.warn.WarnReportHolder;
import com.cqyanyu.backing.ui.mvpview.home.WarnReportView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 告警上报详情
 * Created by Administrator on 2017/7/11.
 */
public class WarnReportPresenter extends XPagePresenter<WarnReportView> implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            switch (getView().getLabel()) {
                case WarnReportActivity.LABEL_VALUE_HOST://主机告警历史记录
                    mRecyclerView.getAdapter().bindHolder(new HostReportHolder());
                    break;
                case WarnReportActivity.LABEL_VALUE_WARN:
                    mRecyclerView.getAdapter().bindHolder(new WarnReportHolder());
                    break;
            }
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        switch (getView().getLabel()) {
            case WarnReportActivity.LABEL_VALUE_HOST://主机告警
                paramsMap.put("pid", getView().getOid());
                break;
            case WarnReportActivity.LABEL_VALUE_WARN://水系统告警
                paramsMap.put("oid", getView().getOid());
                break;
        }
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        switch (getView().getLabel()) {
            case WarnReportActivity.LABEL_VALUE_WARN:
                return ConstHost.GET_WARN_REPORT_URL;
            case WarnReportActivity.LABEL_VALUE_HOST:
                return ConstHost.GET_HOST_ALARM_REPORT_URL;
        }
        return "";
    }

    @Override
    protected Class getClazz() {
        return WaterReportEntity.class;
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