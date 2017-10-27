package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.PerfectRateEntity;
import com.cqyanyu.backing.ui.holder.home.PrefectRateHolder;
import com.cqyanyu.backing.ui.mvpview.home.PrefectRateView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 完好率逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class PrefectRatePresenter extends XPagePresenter<PrefectRateView> {

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new PrefectRateHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
//        paramsMap.put("type", "1");//1：日巡 2：月检
//        paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
        return paramsMap;
    }

    @Override
    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                org.json.JSONObject object = new org.json.JSONObject(result);
                String trueResult = object.optString("problem");
                List mList = JSON.parseArray(trueResult, getClazz());
                if (mList != null && mList.size() > 0) {
                    setData(mList);
                    return;
                } else onLoadNoData();
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_PERFECT_RATE;
    }

    @Override
    protected Class getClazz() {
        return PerfectRateEntity.class;
    }

    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    protected void setData(List mList) {
        /**通告 获取父设备接口的oid 和获取任务的deviceid 合并数据 要isproblem*/
        mRecyclerView.getAdapter().setData(0, mList);
    }
}
