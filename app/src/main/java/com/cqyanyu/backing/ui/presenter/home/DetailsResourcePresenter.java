package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.home.DetailsResourceActivity;
import com.cqyanyu.backing.ui.entity.home.DetailsResourceEntity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.holder.home.ResourceHolder;
import com.cqyanyu.backing.ui.mvpview.home.DetailsResourceView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.backing.utils.DistanceUtils;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 详细资源逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class DetailsResourcePresenter extends XPagePresenter<DetailsResourceView> {
    private double latitude;
    private double longitude;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**获取本人的经纬度*/
            BDLocation bdLocation = EventBus.getDefault().getStickyEvent(BDLocation.class);
            if (bdLocation != null) {
                latitude = bdLocation.getLatitude();
                longitude = bdLocation.getLongitude();
            }
            /**设置RecyclerView*/
            setRecyclerView();
        }
    }

    @Override
    protected void refreshing() {
        if (mRecyclerView != null) {
            /**为mRecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new ResourceHolder());
        }
        super.refreshing();
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("pid", CommonInfo.getInstance().getUserInfo().getUnitid());
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_UNIT_DEVICE_PAGE_URL;
    }

    @Override
    public void refresh() {
        if (TextUtils.equals(getView().getLabel(), DetailsResourceActivity.LABEL_VALUE_OTHER)) {
            /**筛选百度地图搜索到的资源信息*/
            List<DetailsResourceEntity> otherResource = getView().getOtherResource();
            if (otherResource != null && otherResource.size() > 0) {
                addData(otherResource);
            } else {
                onLoadNoData();
            }
            onXFinished();
        } else if (TextUtils.equals(getView().getLabel(), DetailsResourceActivity.LABEL_VALUE_WATER)) {
            /**获取设备信息*/
            super.refresh();
        }

    }

    @Override
    protected Class getClazz() {
        return SetManageEntity.class;
    }

    @Override
    protected void onXSuccess(String result) {
        if (getView() != null) {
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
                        return;
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
            /**清空数据*/
            mRecyclerView.getAdapter().setData(0, new ArrayList());
            /**显示加载失败*/
            if (getView() != null) getView().hasShowData(false);
        }
    }

    @Override
    protected void setData(List mList) {
        addWaterData(mList);
    }

    /**
     * 添加水源数据
     *
     * @param list 数据
     */
    private void addWaterData(List<SetManageEntity> list) {
        if (TextUtils.equals(getView().getLabel(), DetailsResourceActivity.LABEL_VALUE_WATER)) {
            List<DetailsResourceEntity> dList = new ArrayList<>();
            /**获取消防栓*/
            String entryPid = InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_FIRE_HYDRANT);
            List<EntryEntity> entry = InfoManger.getInstance().getEntryListOfPid(context, entryPid);
            /**筛选获取的父设备信息*/
            for (int i = 0; i < list.size(); i++) {
                SetManageEntity entity = list.get(i);
                if (entity != null) {
                    /** 判断是否是消防栓*/
                    for (EntryEntity eEntity : entry) {
                        if (TextUtils.equals(entity.getTypeid(), eEntity.getOid())
                                || TextUtils.equals(entity.getTypeid(), entryPid)) {
                            dList.add(new DetailsResourceEntity(DistanceUtils.getDistance(longitude, latitude, entity.getLongitude(), entity.getLatitude()),
                                    InfoManger.getInstance().getUnitNameOfOid(context, entity.getOid()),
                                    entity.getPosition()));
                        }
                    }
                }
            }
            addData(dList);
        }
    }

    /**
     * 添加数据
     *
     * @param mList 未筛选数据
     */
    private void addData(List<DetailsResourceEntity> mList) {
        for (int i = (mList.size() - 1); i >= 0; i--) {
            DetailsResourceEntity entity = mList.get(i);
            if (entity != null) {
                /**判断距离是否有效*/
                if (getView().getDistance() < entity.getDistance()) {
                    mList.remove(i);
                }
            } else {
                mList.remove(i);
            }
        }
        if (mList.size() > 0) {
            /**显示数据*/
            if (getView() != null) getView().hasShowData(true);
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            onLoadNoData();
        }
    }
}
