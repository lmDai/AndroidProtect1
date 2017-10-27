package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.DutyStatusEntity;
import com.cqyanyu.backing.ui.mvpview.home.LocalView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 实时定位逻辑处理类
 * Created by Administrator on 2017/7/20.
 */
public class LocalPresenter extends BasePresenter<LocalView> {

    public void init() {
        getUserLocalInfo();
    }

    private void getUserLocalInfo() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("type", "1");//类型 0:发起 1:回复
            XHttpUtils.post(context, paramsMap, ConstHost.GET_DUTY_TOTAL_Page_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            org.json.JSONObject object = new org.json.JSONObject(result);
                            int total = object.optInt("total");
                            int count = object.optInt("count");
                            String trueResult = object.optString("rows");
                            List<DutyStatusEntity> mList = JSON.parseArray(trueResult, DutyStatusEntity.class);
                            List list = new ArrayList();
                            if (getView() != null) {
                                if (mList != null && mList.size() > 0) {
                                    for (DutyStatusEntity entity : mList) {
                                        if (entity != null) {
                                            String userName = entity.getUserstr();//+"\t"+entity.getUnitstr();
                                            double latitude = entity.getLastanswerlatitude();
                                            double longitude = entity.getLastanswerlongitude();
                                            list.add(entity.getLastoperationdate());
                                            /** 坐标*/
                                            getView().onSetMarkOnMap(userName, latitude, longitude);
                                        }
                                    }
                                    Collections.sort(mList, new Comparator<DutyStatusEntity>() {
                                        @Override
                                        public int compare(DutyStatusEntity compare, DutyStatusEntity bycompare) {
                                            if (bycompare.getLastoperationdate() > compare.getLastoperationdate()) {
                                                return 1;
                                            }
                                            if (bycompare.getLastoperationdate() < compare.getLastoperationdate()) {
                                                return -1;
                                            }
                                            return 0;
                                        }
                                    });
                                    getView().setLocation(mList.get(0));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
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
}
