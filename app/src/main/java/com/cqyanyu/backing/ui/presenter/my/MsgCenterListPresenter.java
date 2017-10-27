package com.cqyanyu.backing.ui.presenter.my;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.my.CheckRecordsEntity;
import com.cqyanyu.backing.ui.entity.my.ConsulationEntity;
import com.cqyanyu.backing.ui.entity.my.NotificationInfoEntity;
import com.cqyanyu.backing.ui.entity.my.SystemInfoEntity;
import com.cqyanyu.backing.ui.fragment.my.MsgCenterFragment;
import com.cqyanyu.backing.ui.holder.my.CheckRecordsHolder;
import com.cqyanyu.backing.ui.holder.my.ConsulationHolder;
import com.cqyanyu.backing.ui.holder.my.NotificationHolder;
import com.cqyanyu.backing.ui.holder.my.SystemInfoHolder;
import com.cqyanyu.backing.ui.mvpview.my.MsgCenterListView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息中心碎片管理
 * Created by Administrator on 2017/7/13.
 */

public class MsgCenterListPresenter extends XPagePresenter<MsgCenterListView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            setRecyclerView();//设置RecyclerView
            //为mRecyclerView绑定数据
            switch (getView().getLabel()) {
                case MsgCenterFragment.LABEL_VALUE_RECORDS:
                    //查岗记录
                    mRecyclerView.getAdapter().bindHolder(new CheckRecordsHolder());
                    break;
                case MsgCenterFragment.LABEL_VALUE_CONSULATION:
                    //消防资讯
                    mRecyclerView.getAdapter().bindHolder(new ConsulationHolder());
                    break;
                case MsgCenterFragment.LABEL_VALUE_SYSTEMINFO:
                    //系统消息
                    mRecyclerView.getAdapter().bindHolder(new SystemInfoHolder());
                    break;
                case MsgCenterFragment.LABEL_VALUE_NOTIFICATION:
                    //通知消息
                    mRecyclerView.getAdapter().bindHolder(new NotificationHolder());
                    break;
            }
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        switch (getView().getLabel()) {
            case MsgCenterFragment.LABEL_VALUE_RECORDS:
                //查岗记录
                paramsMap.put("userstr", CommonInfo.getInstance().getUserInfo().getPhone());
                //paramsMap.put("unitstr", CommonInfo.getInstance().getUserInfo().getPhone());
                paramsMap.put("type", String.valueOf(1));
                break;
            case MsgCenterFragment.LABEL_VALUE_CONSULATION:
                //消防资讯
                paramsMap.put("pid", CommonInfo.getInstance().getUserInfo().getUnitid());
                break;
            case MsgCenterFragment.LABEL_VALUE_SYSTEMINFO:
                //系统消息
                paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
                break;
            case MsgCenterFragment.LABEL_VALUE_NOTIFICATION:
                //通知消息
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
            case MsgCenterFragment.LABEL_VALUE_RECORDS:
                //查岗记录
                return ConstHost.GET_DUTY_DETAILS_PAGE_URL;
            case MsgCenterFragment.LABEL_VALUE_CONSULATION:
                //消防资讯
                return "";
            case MsgCenterFragment.LABEL_VALUE_SYSTEMINFO:
                //系统消息
                return "";
            case MsgCenterFragment.LABEL_VALUE_NOTIFICATION:
                //通知消息
                return "";
        }
        return "";
    }

    @Override
    protected Class getClazz() {
        switch (getView().getLabel()) {
            case MsgCenterFragment.LABEL_VALUE_RECORDS:
                //查岗记录
                return CheckRecordsEntity.class;
            case MsgCenterFragment.LABEL_VALUE_CONSULATION:
                //消防资讯
                return ConsulationEntity.class;
            case MsgCenterFragment.LABEL_VALUE_SYSTEMINFO:
                //系统消息
                return SystemInfoEntity.class;
            case MsgCenterFragment.LABEL_VALUE_NOTIFICATION:
                //通知消息
                return NotificationInfoEntity.class;
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

    @Override
    protected void onLoadNoData() {
        if (TextUtils.equals(getView().getLabel(), MsgCenterFragment.LABEL_VALUE_CONSULATION)) {
            List<ConsulationEntity> mList = new ArrayList<>();
            ConsulationEntity consulationEntity = new ConsulationEntity(R.mipmap.zzz
                    , "重庆建设医院召开夏季消防安全工作部署会");
            mList.add(consulationEntity);
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            super.onLoadNoData();
        }
    }
}
