package com.cqyanyu.backing.ui.presenter.statistics;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.statistics.AlarmDataEntity;
import com.cqyanyu.backing.ui.holder.statistics.AlarmDataHolder;
import com.cqyanyu.backing.ui.mvpview.statistics.AlarmView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 告警统计
 * Created by Administrator on 2017/8/2.
 */

public class AlarmPresenter extends XPagePresenter<AlarmView> {
    private int page;
    private int pageSize = 20;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            setRecyclerView();
            //为mRecyclerView绑定数据
            mRecyclerView.getAdapter().bindHolder(new AlarmDataHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", String.valueOf(getView().getType()));
        // paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
//        if (getView().getType() == 1) {//按日查询
//            paramsMap.put("startdate", MyDate.getFirstOfMonthZero() / 1000 + "");
//            paramsMap.put("enddate", (MyDate.getTodayZero() / 1000) + "");
//        } else if (getView().getType() == 2) {//按月查询
//            paramsMap.put("startdate", MyDate.getFirstOfYearZero() / 1000 + "");
//            paramsMap.put("enddate", MyDate.getEndOfMonthZero() / 1000 + "" + "");
//        }
        return paramsMap;
    }

    @Override
    public void refresh() {
        page = 0;
        super.refresh();
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_UNIT_WARN_INFO_URL;
    }

    @Override
    protected Class getClazz() {
        return AlarmDataEntity.class;
    }

    @Override
    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                List mList = JSON.parseArray(result, getClazz());
                if (mList != null && mList.size() > 0) {
                    /**设置当前页数*/
                    page = page + 1;
                    /**设置是否可以下拉加载*/
                    isLoad = page * pageSize < mList.size();
                    if (page * pageSize > mList.size()) {
                        setData(mList.subList((page - 1) * pageSize, mList.size()));
                    } else {
                        setData(mList.subList((page - 1) * pageSize, page * pageSize));
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (page == 1 || page == 0) onLoadNoData();
    }

    @Override
    protected void setData(List mList) {
        if (page == 1) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else if (page > 1) {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }
}
