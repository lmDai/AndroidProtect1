package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.holder.home.ProvinceHolder;
import com.cqyanyu.backing.ui.mvpview.home.ProvinceView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.List;

import static com.cqyanyu.backing.ui.activity.home.ProvinceActivity.LABEL_VALUE_BUILD;
import static com.cqyanyu.backing.ui.activity.home.ProvinceActivity.LABEL_VALUE_PRESENT;
import static com.cqyanyu.backing.ui.activity.home.ProvinceActivity.LABEL_VALUE_USER;

/**
 * 省市逻辑处理类
 * Created by Administrator on 2017/7/12.
 */
public class ProvincePresenter extends XPagePresenter<ProvinceView> {
    private int pagecount = 20;
    private int pageindex = 0;
    private UnitManageEntity entity;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**设置RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new ProvinceHolder());
            if (getView().isMineId() && !getView().isMustSelectLastGrade()) {
                getMyUnitOnNet();
            }
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        if (getView() != null) {
            paramsMap.put("pid", getView().getPid());
        }
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_UNITS_LIST;
    }

    @Override
    protected Class getClazz() {
        return UnitEntity.class;
    }

    @Override
    public void refresh() {
        pageindex = 0;
        super.refresh();
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
                        entity = mList.get(0);
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
                    if (getView() != null) getView().hasShowData(true);
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
        if (mRecyclerView.getAdapter().getData(0).size() == 0) onLoadNoData();
    }

    @Override
    protected void onLoadNoData() {
        if (getView().isMineId() && entity != null) {
            setData(new ArrayList<UnitEntity>());
        } else {
            super.onLoadNoData();
        }

    }

    @Override
    protected void setData(List mList) {
        List<UnitEntity> list = new ArrayList();
        //选择上级单位
        if (TextUtils.equals(getView().getLabel(), LABEL_VALUE_PRESENT) || TextUtils.equals(getView().getLabel(), LABEL_VALUE_USER)) {
            for (UnitEntity unitManageEntity : (List<UnitEntity>) mList) {
                if (TextUtils.equals(unitManageEntity.getChildbuildcount(), "0") && TextUtils.equals(unitManageEntity.getChildunitcount(), "0")) {
                    if (!TextUtils.equals(unitManageEntity.getTypeid(), "12")) {
                        list.add(unitManageEntity);
                    }
                } else {
                    list.add(unitManageEntity);
                }
            }
        } else if (TextUtils.equals(getView().getLabel(), LABEL_VALUE_BUILD)) {//选择所属建筑
            for (UnitEntity unitManageEntity : (List<UnitEntity>) mList) {
                if (TextUtils.equals(unitManageEntity.getChildbuildcount(), "0") && TextUtils.equals(unitManageEntity.getChildunitcount(), "0")) {
                    if (TextUtils.equals(unitManageEntity.getTypeid(), "12")) {
                        list.add(unitManageEntity);
                    }
                } else {
                    list.add(unitManageEntity);
                }
            }
        }
//        if (list.size() == 0) {
//            load();
//        } else {
            if (pageindex == 0) {
                if (getView().isMineId() && entity != null) {
                    UnitEntity unitEntity = new UnitEntity();
                    unitEntity.setOid(entity.getOid());
                    unitEntity.setChildbuildcount(entity.getChilddevicecount() + "");
                    unitEntity.setChildunitcount(entity.getChildunitcount());
                    unitEntity.setPid(entity.getPid());
                    unitEntity.setUnitstr(entity.getUnitstr());
                    unitEntity.setTypestr(entity.getTypestr());
                    unitEntity.setTypeid(entity.getTypeid());
                    entity.isHide = true;
                    list.add(0, unitEntity);
                }
                mRecyclerView.getAdapter().setData(0, list);
            } else {
                mRecyclerView.getAdapter().addDataAll(0, list);
            }
//        }
    }
}
