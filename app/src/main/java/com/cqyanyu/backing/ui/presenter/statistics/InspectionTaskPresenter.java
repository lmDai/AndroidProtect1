package com.cqyanyu.backing.ui.presenter.statistics;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.statistics.InspectionTaskEntity;
import com.cqyanyu.backing.ui.holder.statistics.InspectionTaskHolder;
import com.cqyanyu.backing.ui.mvpview.statistics.InspectionTaskView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 巡检设施（巡检任务）
 * Created by Administrator on 2017/8/2.
 */

public class InspectionTaskPresenter extends XPagePresenter<InspectionTaskView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            setRecyclerView();
            //为mRecyclerView绑定数据
            mRecyclerView.getAdapter().bindHolder(new InspectionTaskHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", String.valueOf(getView().getType()));
        paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
        if (getView().getType() == 1) {//按日查询

            //paramsMap.put("startdate", MyDate.getFirstOfMonthZero() / 1000 + "");
            //paramsMap.put("enddate", (MyDate.getTodayZero() / 1000) + "");
        } else if (getView().getType() == 2) {//按月查询
            //paramsMap.put("startdate", MyDate.getFirstOfYearZero() / 1000 + "");
            //paramsMap.put("enddate", MyDate.getFirstOfMonthZero() / 1000 + "" + "");
        }
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_UNIT_TASK_Page_URL;
    }

    @Override
    protected Class getClazz() {
        return InspectionTaskEntity.class;
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
                    //getInspectionRate(mList);
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