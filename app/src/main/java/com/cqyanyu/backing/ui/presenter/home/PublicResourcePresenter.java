package com.cqyanyu.backing.ui.presenter.home;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.MapDeviceInfo;
import com.cqyanyu.backing.ui.entity.home.MapUnitInfo;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.mvpview.home.PublicResourceView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.json.JSONException;

import java.util.List;

/**
 * 公共资源逻辑处理类
 * Created by Administrator on 2017/7/7.
 */
public class PublicResourcePresenter extends BasePresenter<PublicResourceView> {

    public void init() {
        getMapTableData();
        getMyUnitOnNet();
    }

    /**
     * 获取地图资源
     */
    private void getMapTableData() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            XHttpUtils.post(context, paramsMap, ConstHost.GET_MAP_TABLE_DATA, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        org.json.JSONObject object = new org.json.JSONObject(result);
                        String trueDevice = object.optString("device");
                        String trueUnit = object.optString("build");
                        List<MapUnitInfo> mListUnit = JSON.parseArray(trueUnit, MapUnitInfo.class);
                        List<MapDeviceInfo> mList = JSON.parseArray(trueDevice, MapDeviceInfo.class);
                        if (mListUnit.size() > 0) {
                            getView().setUnitList(mListUnit);
                            for (MapUnitInfo entity : mListUnit) {
                                getView().setMark(1, entity.getUnitName(), entity.getPosition(), entity.getLatitude(), entity.getLongitude());
                            }
                        }
                        if (mList.size() > 0) {
                            for (MapDeviceInfo entity : mList) {
                                getView().setMark(4, "", "", entity.getLatitude(), entity.getLongitude());
                            }
                            getView().setDeviceList(mList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String msg) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 从网络中获取本单位信息
     */
    public void getMyUnitOnNet() {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("oid", CommonInfo.getInstance().getUserInfo().getUnitid());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_INFO_URL, new XICallbackList<UnitManageEntity>() {
                @Override
                public void onSuccess(List<UnitManageEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        UnitManageEntity entity = mList.get(0);
                        getView().setLoation(entity.getLatitude(), entity.getLongitude());
                    }
                }

                @Override
                public void onFail(String msg) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return UnitManageEntity.class;
                }
            });
        }
    }

}
