package com.cqyanyu.backing.ui.presenter.statistics;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.holder.statistics.FireFacilitiesHolder;
import com.cqyanyu.backing.ui.mvpview.statistics.FireFacilitiesView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 消防设施
 * Created by Administrator on 2017/8/2.
 */

public class FireFacilitiesPresenter extends XPagePresenter<FireFacilitiesView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            setRecyclerView();
            //为mRecyclerView绑定数据
            mRecyclerView.getAdapter().bindHolder(new FireFacilitiesHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
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
        return ConstHost.GET_UNIT_DEVICE_PAGE_URL;
    }

    @Override
    protected Class getClazz() {
        return SetManageEntity.class;
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
    protected void setData(List mList) {
        if (pageindex == 0) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }
}
