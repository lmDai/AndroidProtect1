package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.fragment.home.SystemManagementFragment;
import com.cqyanyu.backing.ui.holder.home.SetManageHolder;
import com.cqyanyu.backing.ui.holder.home.UnitManageHolder;
import com.cqyanyu.backing.ui.holder.home.UserManageHolder;
import com.cqyanyu.backing.ui.mvpview.home.SystemManagementLView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 系统管理碎片逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class SystemManagementLPresenter extends XPagePresenter<SystemManagementLView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**设置RecyclerView*/
            setRecyclerView();
            /**为mRecyclerView绑定数据*/
            switch (getView().getLabel()) {
                case SystemManagementFragment.LABEL_VALUE_UNIT:
                    //单位管理
                    mRecyclerView.getAdapter().bindHolder(new UnitManageHolder());
                    break;
                case SystemManagementFragment.LABEL_VALUE_SET:
                    //设备管理
                    mRecyclerView.getAdapter().bindHolder(new SetManageHolder());
                    break;
                case SystemManagementFragment.LABEL_VALUE_USER:
                    //用户管理
                    mRecyclerView.getAdapter().bindHolder(new UserManageHolder());
                    break;
            }
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        switch (getView().getLabel()) {
            case SystemManagementFragment.LABEL_VALUE_UNIT:
                //单位管理
//                paramsMap.put("pid", CommonInfo.getInstance().getUserInfo().getUnitid());
                break;
            case SystemManagementFragment.LABEL_VALUE_SET:
                //设备管理
                paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
                break;
            case SystemManagementFragment.LABEL_VALUE_USER:
                //用户管理
                paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
                break;
        }
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        switch (getView().getLabel()) {
            case SystemManagementFragment.LABEL_VALUE_UNIT://获取下级单位列表
                //单位管理
                return ConstHost.GET_UNIT_PAGE_URL;
            case SystemManagementFragment.LABEL_VALUE_SET:
                //设备管理
                return ConstHost.GET_UNIT_DEVICE_PAGE_URL;
            case SystemManagementFragment.LABEL_VALUE_USER:
                //用户管理
                return ConstHost.GET_UNIT_USER_PAGE_URL;
        }
        return "";
    }

    @Override
    protected Class getClazz() {
        switch (getView().getLabel()) {
            case SystemManagementFragment.LABEL_VALUE_UNIT:
                //单位管理
                return UnitManageEntity.class;
            case SystemManagementFragment.LABEL_VALUE_SET:
                //设备管理
                return SetManageEntity.class;
            case SystemManagementFragment.LABEL_VALUE_USER:
                //用户管理
                return UserManageEntity.class;
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
