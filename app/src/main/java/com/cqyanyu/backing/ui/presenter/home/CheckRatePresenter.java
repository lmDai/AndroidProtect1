package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.CheckRateEntity;
import com.cqyanyu.backing.ui.holder.home.CheckRateHolder;
import com.cqyanyu.backing.ui.mvpview.home.CheckRateView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 巡检率逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class CheckRatePresenter extends XPagePresenter<CheckRateView> {
    private int pagecount = 20;
    private int pageindex = 0;
    private String inspectionRate = "0%";//巡检率
    private String intactRate = "100%";//完好率

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new CheckRateHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        long sec = (MyDate.getTodayZero() / 1000);
        long startdate = sec - 86400;
        long enddate = startdate + 1;
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", "1");//类型  1：日巡 2：月检
        paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
        paramsMap.put("startdate", startdate + "");
        paramsMap.put("enddate", enddate + "");
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
    protected String getURL() {
        return ConstHost.GET_UNIT_TASK_Page_URL;
    }

    @Override
    protected Class getClazz() {
        return CheckRateEntity.class;
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
                    getInspectionRate(mList);
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

    private void getInspectionRate(List mList) {
        int count = mList.size();
        int i;
        List<CheckRateEntity> list = mList;
        int devicetotal = 0;
        int inspectiontotal = 0;
        int problemtotal = 0;
        for (i = 0; i < count; i++) {
            devicetotal += list.get(i).getDevicecount();
            inspectiontotal += list.get(i).getInspectioncount();
            problemtotal += list.get(i).getProblemcount();
        }
        inspectionRate = inspectiontotal * 100.0f / devicetotal + "";
        intactRate = (devicetotal - problemtotal) * 100.0f / devicetotal + "";
        return;
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
