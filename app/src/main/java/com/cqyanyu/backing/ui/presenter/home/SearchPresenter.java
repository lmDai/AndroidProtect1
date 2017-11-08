package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.activity.home.SystemManagementActivity;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.entity.warn.HostWarnEntity;
import com.cqyanyu.backing.ui.entity.warn.WaterSystemEntity;
import com.cqyanyu.backing.ui.fragment.alarm.AlarmFragment;
import com.cqyanyu.backing.ui.holder.home.SetManageHolder;
import com.cqyanyu.backing.ui.holder.home.UnitManageHolder;
import com.cqyanyu.backing.ui.holder.home.UserManageHolder;
import com.cqyanyu.backing.ui.holder.warn.HostWarnHolder;
import com.cqyanyu.backing.ui.holder.warn.WaterSystemHolder;
import com.cqyanyu.backing.ui.mvpview.home.SearchView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 列表搜索逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class SearchPresenter extends XPagePresenter<SearchView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**设置RecyclerView*/
            setRecyclerView();
            /**为mRecyclerView绑定数据*/
            switch (getView().getLabel()) {
                case SystemManagementActivity.LABEL_VALUE_SEARCH_UNIT:
                    //单位管理
                    mRecyclerView.getAdapter().bindHolder(new UnitManageHolder());
                    break;
                case SystemManagementActivity.LABEL_VALUE_SEARCH_SET:
                    //设备管理
                    mRecyclerView.getAdapter().bindHolder(new SetManageHolder());
                    break;
                case SystemManagementActivity.LABEL_VALUE_SEARCH_USER:
                    //用户管理
                    mRecyclerView.getAdapter().bindHolder(new UserManageHolder());
                    break;
                case AlarmFragment.LABEL_VALUE_HOST://主机
                    mRecyclerView.getAdapter().bindHolder(new HostWarnHolder());
                    break;
                case AlarmFragment.LABEL_VALUE_WATER_SYSTEM://水系统
                    mRecyclerView.getAdapter().bindHolder(new WaterSystemHolder());
                    break;
            }
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("mcondition", getView().getSearchText());
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        switch (getView().getLabel()) {
            case SystemManagementActivity.LABEL_VALUE_SEARCH_UNIT://获取下级单位列表
                //单位管理
                return ConstHost.GET_UNIT_PAGE_URL;
            case SystemManagementActivity.LABEL_VALUE_SEARCH_SET:
                //设备管理
                return ConstHost.GET_UNIT_DEVICE_PAGE_URL;
            case SystemManagementActivity.LABEL_VALUE_SEARCH_USER:
                //用户管理
                return ConstHost.GET_UNIT_USER_PAGE_URL;
            case AlarmFragment.LABEL_VALUE_HOST://主机
                return ConstHost.GET_HOST_ALARM_URL;
            case AlarmFragment.LABEL_VALUE_WATER_SYSTEM://水系统
                return ConstHost.GET_WARN_SYSTEM_URL;
        }
        return "";
    }

    @Override
    protected Class getClazz() {
        switch (getView().getLabel()) {
            case SystemManagementActivity.LABEL_VALUE_SEARCH_UNIT:
                //单位管理
                return UnitManageEntity.class;
            case SystemManagementActivity.LABEL_VALUE_SEARCH_SET:
                //设备管理
                return SetManageEntity.class;
            case SystemManagementActivity.LABEL_VALUE_SEARCH_USER:
                //用户管理
                return UserManageEntity.class;
            case AlarmFragment.LABEL_VALUE_HOST://主机
                return HostWarnEntity.class;
            case AlarmFragment.LABEL_VALUE_WATER_SYSTEM://水系统
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
