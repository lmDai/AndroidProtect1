package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.TaskDetailsEntity;
import com.cqyanyu.backing.ui.holder.home.TaskDetailsHolder;
import com.cqyanyu.backing.ui.mvpview.home.TaskDetailsView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 任务详情逻辑处理类
 * Created by Administrator on 2017/7/25.
 */
public class TaskDetailsPresenter extends XPagePresenter<TaskDetailsView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new TaskDetailsHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", "1");//1：日巡 2：月检
        paramsMap.put("statisticsid", getView().getTaskIds());//任务 ID
        paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
        return paramsMap;
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
    public void refresh() {
        pageindex = 0;
        super.refresh();
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_TASK_DETAILS_Page_URL;
    }

    @Override
    protected Class getClazz() {
        return TaskDetailsEntity.class;
    }

    @Override
    protected void setData(List mList) {
        mRecyclerView.getAdapter().setData(0, mList);
    }
}
