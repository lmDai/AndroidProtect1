package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.UnitEntity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.holder.home.ProvinceHolder;
import com.cqyanyu.backing.ui.mvpview.home.ProvinceView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.XLog;
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


    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**设置RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new ProvinceHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        if (getView().getFirstIn()) {
            paramsMap.put("oid", CommonInfo.getInstance().getUserInfo().getUnitid());
        } else {
            paramsMap.put("pid", getView().getPid());
        }
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        if (getView().getFirstIn()) {
            return ConstHost.GET_UNIT_INFO_URL;
        } else {
            return ConstHost.GET_UNITS_LIST;
        }
    }

    @Override
    protected Class getClazz() {
        if (getView().getFirstIn()) {
            return UnitManageEntity.class;
        } else {
            return UnitEntity.class;
        }
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
                List mList;
                int total = 0;
                int count = 0;
                if (getView().getFirstIn()) {
                    mList = JSON.parseArray(result, getClazz());
                } else {
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    total = object.optInt("total");
                    count = object.optInt("count");
                    String trueResult = object.optString("rows");
                    mList = JSON.parseArray(trueResult, getClazz());
                }
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
        if (pageindex == 0) onLoadNoData();
    }

    @Override
    protected void setData(List mList) {
        List<UnitEntity> list = new ArrayList();
        if (getView().getFirstIn()) {
            UnitManageEntity entity = (UnitManageEntity) mList.get(0);
            UnitEntity unitEntity = new UnitEntity();
            unitEntity.setOid(entity.getOid());
            unitEntity.setChildbuildcount(entity.getChilddevicecount() + "");
            unitEntity.setChildunitcount(entity.getChildunitcount());
            unitEntity.setPid(entity.getPid());
            unitEntity.setUnitstr(entity.getUnitstr());
            unitEntity.setTypestr(entity.getTypestr());
            unitEntity.setTypeid(entity.getTypeid());
            entity.isHide = true;
            list.add(unitEntity);
        } else {
            //选择上级单位
            if (TextUtils.equals(getView().getLabel(), LABEL_VALUE_PRESENT) || TextUtils.equals(getView().getLabel(), LABEL_VALUE_USER)) {
                for (UnitEntity unitManageEntity : (List<UnitEntity>) mList) {
//                    if (TextUtils.equals(unitManageEntity.getChildbuildcount(), "0") && TextUtils.equals(unitManageEntity.getChildunitcount(), "0")) {
//                        if (!TextUtils.equals(unitManageEntity.getTypeid(), "12")) {
//                            list.add(unitManageEntity);
//                        }
//                    } else {
                        list.add(unitManageEntity);
//                    }
                }
            } else if (TextUtils.equals(getView().getLabel(), LABEL_VALUE_BUILD)) {//选择所属建筑
                for (UnitEntity unitManageEntity : (List<UnitEntity>) mList) {
//                    if (TextUtils.equals(unitManageEntity.getChildbuildcount(), "0") && TextUtils.equals(unitManageEntity.getChildunitcount(), "0")) {
//                        if (TextUtils.equals(unitManageEntity.getTypeid(), "12")) {
//                            list.add(unitManageEntity);
//                        }
//                    } else {
                        list.add(unitManageEntity);
//                    }
                }
            }
        }
        if (pageindex == 0) {
            mRecyclerView.getAdapter().setData(0, list);
        } else {
            mRecyclerView.getAdapter().addDataAll(0, list);
        }
    }
}
