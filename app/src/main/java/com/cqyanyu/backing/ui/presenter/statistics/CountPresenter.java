package com.cqyanyu.backing.ui.presenter.statistics;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.AlarEntity;
import com.cqyanyu.backing.ui.entity.statistics.CountBean;
import com.cqyanyu.backing.ui.mvpview.statistics.CountView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class CountPresenter extends BasePresenter<CountView> {
    private List<CountBean> hList = new ArrayList<>();

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            getMessageAlar();
            getHomeList();
        }
    }

    //获取：统计功能列表信息
    private void getHomeList() {
        //设置item数据
        if (getView() != null) {
            getCount();
            hList.add(new CountBean(1, "设备", "总数", R.mipmap.ic_shebeizongshu, "0"));
            hList.add(new CountBean(2, "单位", "总数", R.mipmap.ic_nianwangdanwei, "0"));
            hList.add(new CountBean(3, "告警", "总数", R.mipmap.ic_gaojingtongji, "0"));
            hList.add(new CountBean(4, "警情", "处置率", R.mipmap.ic_yuqingfenxi, "0"));
            hList.add(new CountBean(5, "巡检", "巡检率", R.mipmap.ic_sheshixunjian, "0"));
            hList.add(new CountBean(6, "用户", "总数", R.mipmap.ic_lianwangyonghu, "0"));
            getView().setCountItem(hList);
        }
    }

    //获取：火警、预警、故障
    public void getMessageAlar() {
        if (getView() != null) {
            //http://222.181.144.111:8088/hdInterface/alarm/getMessageStatisticsList?unitid=3366&type=1&enddate=1501603200&startdate=1501516800
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("type", "1");//类型 1：当天 2：当月 3：总计
            //paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
            paramsMap.put("startdate", MyDate.getTodayZero() / 1000 - 24 * 60 * 60 + "");
            paramsMap.put("enddate", MyDate.getTodayZero() / 1000 + "");
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_WARN_INFO_URL, new XICallbackList<AlarEntity>() {
                @Override
                public void onSuccess(List<AlarEntity> mList) {
                    if (getView() != null) {
                        if (mList != null && mList.size() > 0) {
                            AlarEntity entity;
                            if (mList.size() == 1) {
                                entity = mList.get(0);
                            } else {
                                entity = mList.get(1);
                            }
                            List<Float> alarmTotals = new ArrayList<Float>();
                            alarmTotals.add((float) entity.getAlarmcount());/**设置火警总数*/
                            alarmTotals.add((float) entity.getWarncount());/**设置预警总数**/
                            alarmTotals.add((float) entity.getProblemcount()); /**设置故障总数*/
                            alarmTotals.add((float) entity.getOthercount());  /**设置其他总数*/
                            List<Float> handleTotals = new ArrayList<Float>();
                            handleTotals.add((float) entity.getAlarmhandle());    /**设置火警处理总数*/
                            handleTotals.add((float) entity.getWarnhandle());/**设置预警处理总数**/
                            handleTotals.add((float) entity.getProblemhandle()); /**设置故障处理总数*/
                            handleTotals.add((float) entity.getOtherhandle());  /**设置其他处理总数*/
                            getView().setAlarmTotal(alarmTotals, handleTotals);
                            getView().setWarnCount(entity.getWarncount());
                        } else {
                            List<Float> alarmTotals = new ArrayList<Float>();
                            alarmTotals.add((float) 0);
                            alarmTotals.add((float) 0);
                            alarmTotals.add((float) 0);
                            alarmTotals.add((float) 0);
                            List<Float> handleTotals = new ArrayList<Float>();
                            handleTotals.add((float) 0);
                            handleTotals.add((float) 0);
                            handleTotals.add((float) 0);
                            handleTotals.add((float) 0);
                            getView().setAlarmTotal(alarmTotals, handleTotals);
                            getView().setWarnCount(0);
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    List<Float> alarmTotals = new ArrayList<Float>();
                    alarmTotals.add((float) 0);
                    alarmTotals.add((float) 0);
                    alarmTotals.add((float) 0);
                    alarmTotals.add((float) 0);
                    List<Float> handleTotals = new ArrayList<Float>();
                    handleTotals.add((float) 0);
                    handleTotals.add((float) 0);
                    handleTotals.add((float) 0);
                    handleTotals.add((float) 0);
                    getView().setAlarmTotal(alarmTotals, handleTotals);
                }

                @Override
                public void onFinished() {
                }

                @Override
                public Class getClazz() {
                    return AlarEntity.class;
                }
            });
        }
    }

    //统计
    public void getCount() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            XHttpUtils.post(context, paramsMap, ConstHost.GET_COUNT_URL, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        org.json.JSONObject object = new org.json.JSONObject(result);
                        int deviceTotalCount = object.optInt("deviceTotalCount");
                        int alarmCount = object.optInt("alarmCount");
                        int unitCount = object.optInt("unitCount");
                        int userCount = object.optInt("userCount");
                        int disPosalRate = object.optInt("disposalRate");
                        String sInsRate = object.getString("sInsRate");
                        hList.set(0, new CountBean(1, "设备", "总数", R.mipmap.ic_shebeizongshu, deviceTotalCount + ""));
                        hList.set(1, new CountBean(2, "单位", "总数", R.mipmap.ic_nianwangdanwei, unitCount + ""));
                        hList.set(2, new CountBean(3, "告警", "总数", R.mipmap.ic_gaojingtongji, alarmCount + ""));
                        hList.set(3, new CountBean(4, "警情", "处置率", R.mipmap.ic_yuqingfenxi, disPosalRate + "%"));
                        hList.set(4, new CountBean(5, "设施", "巡检率", R.mipmap.ic_sheshixunjian, sInsRate + "%"));
                        hList.set(5, new CountBean(6, "用户", "总数", R.mipmap.ic_lianwangyonghu, userCount + ""));
                        getView().updateData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        hList.set(0, new CountBean(1, "设备", "总数", R.mipmap.ic_shebeizongshu, "0"));
                        hList.set(1, new CountBean(2, "单位", "总数", R.mipmap.ic_nianwangdanwei, "0"));
                        hList.set(2, new CountBean(3, "告警", "总数", R.mipmap.ic_gaojingtongji, "0"));
                        hList.set(3, new CountBean(4, "警情", "处置率", R.mipmap.ic_yuqingfenxi, "0"));
                        hList.set(4, new CountBean(5, "设施", "巡检率", R.mipmap.ic_sheshixunjian, "0"));
                        hList.set(5, new CountBean(6, "用户", "总数", R.mipmap.ic_lianwangyonghu, "0"));
                        getView().updateData();
                    }
                }

                @Override
                public void onFail(String msg) {
                    hList.set(0, new CountBean(1, "设备", "总数", R.mipmap.ic_shebeizongshu, "0"));
                    hList.set(1, new CountBean(2, "单位", "总数", R.mipmap.ic_nianwangdanwei, "0"));
                    hList.set(2, new CountBean(3, "告警", "总数", R.mipmap.ic_gaojingtongji, "0"));
                    hList.set(3, new CountBean(4, "警情", "处置率", R.mipmap.ic_yuqingfenxi, "0"));
                    hList.set(4, new CountBean(5, "设施", "巡检率", R.mipmap.ic_sheshixunjian, "0"));
                    hList.set(5, new CountBean(6, "用户", "总数", R.mipmap.ic_lianwangyonghu, "0"));
                    getView().updateData();
                }

                @Override
                public void onFinished() {
                }
            });
        }
    }
}
