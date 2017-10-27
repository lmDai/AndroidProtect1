package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.WaterSystemReortEntity;
import com.cqyanyu.backing.ui.mvpview.home.WarnListReportView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XDateUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 水系统告警历史
 * Created by Administrator on 2017/8/1.
 */

public class WarnListReprotPresenter extends BasePresenter<WarnListReportView> {

    //获取：火警、预警、故障
    public void getWaterSystemReport() {
        if (getView() != null) {
            //http://222.181.144.111:8088/hdInterface/alarm/getMessageStatisticsList?unitid=3366&type=1&enddate=1501603200&startdate=1501516800
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("pid", getView().getPid());
            paramsMap.put("startdate", getView().getStartDate());
            paramsMap.put("enddate", getView().getEndDate());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_WARN_REPORT_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        org.json.JSONObject object = new org.json.JSONObject(result);
                        String trueResult = object.optString("rows");
                        List<WaterSystemReortEntity> mList = JSON.parseArray(trueResult, WaterSystemReortEntity.class);
                        if (getView() != null) {
                            if (mList != null && mList.size() > 0) {
                                List<Float> alarmTotals = new ArrayList<Float>();
                                List<String> xValue = new ArrayList<String>();
                                float shuiya = 0;
                                for (int i = 0; i < mList.size(); i++) {
                                    alarmTotals.add((float) mList.get(i).getVal());
                                    shuiya += mList.get(i).getVal();
                                    if (TextUtils.isEmpty(String.valueOf(mList.get(i).getReportdate()))) {
                                        xValue.add("00:00:00");
                                    } else {
                                        xValue.add(XDateUtil.getStringByFormatFromStr(String.valueOf(mList.get(i).getReportdate()), "HH:mm:ss"));
                                    }
                                }
                                getView().setTxtWaterHigh(NumberUtils.setDecimalFloat(Collections.max(alarmTotals)));
                                getView().setTxtWaterLow(NumberUtils.setDecimalFloat(Collections.min(alarmTotals)));
                                getView().setTxtShuiya(NumberUtils.setDecimalFloat(shuiya / mList.size()));
                                getView().setAlarmTotal(alarmTotals, xValue);
                            } else {
                                getView().setNoData(getView().getClickable());
                                getView().setTxtWaterHigh("0.00");
                                getView().setTxtWaterLow("0.00");
                                getView().setTxtShuiya("0.00");
                            }
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


}
