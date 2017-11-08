package com.cqyanyu.backing.ui.presenter.home;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.entity.home.BannerEntity;
import com.cqyanyu.backing.ui.entity.home.HomeBean;
import com.cqyanyu.backing.ui.mvpview.home.HomeView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页逻辑处理类
 * Created by Administrator on 2017/7/7.
 */
public class HomePresenter extends BasePresenter<HomeView> {

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            getHomeList();
            getBannerList();
        }
    }

    /**
     * 刷新
     */
    public void refresh() {
        getTableData();
    }

    //获取：首页功能列表信息
    private void getHomeList() {
        //设置item数据
        if (getView() != null) {
            List<HomeBean> hList = new ArrayList<>();
            if (InfoManger.getInstance().isPermission("50")) {//我的任务
                hList.add(new HomeBean(3, "我的任务", R.mipmap.ic_souye_woderenwu));
            }
            if (InfoManger.getInstance().isPermission("51")) {//扫一扫
                hList.add(new HomeBean(5, "扫一扫", R.mipmap.ic_souye_shaoyishao));
            }
            if (InfoManger.getInstance().isPermission("36")) {//宣传培训
                hList.add(new HomeBean(2, "宣传培训", R.mipmap.ic_souye_xuanchuanpeixun));
            }
            if (InfoManger.getInstance().isPermission("35")) {//公共资源
                hList.add(new HomeBean(1, "公共资源", R.mipmap.ic_souye_gongzy110));
            }
            if (InfoManger.getInstance().isPermission("37")) {//当值状态
                hList.add(new HomeBean(4, "当值状态", R.mipmap.ic_souye_dangzhizhuangtai));
            }
            if (InfoManger.getInstance().isPermission("38")) {//系统管理
                hList.add(new HomeBean(6, "系统管理", R.mipmap.ic_souye_xitongguanli));
            }
            getView().setHomeItem(hList);
        }
    }

    //获取：广告banner信息
    private void getBannerList() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("typeid", "1");
            XHttpUtils.post(context, paramsMap, ConstHost.GET_HOME_BANNER_URL, new XICallbackList<BannerEntity>() {
                @Override
                public void onSuccess(List<BannerEntity> mList) {
                    if (getView() != null) {
                        if (mList != null && mList.size() > 0) {
                            List<String> bList = new ArrayList<>();
                            List<String> hList = new ArrayList<>();
                            for (BannerEntity entity : mList) {
                                bList.add(entity.getFilepath());
                                hList.add(entity.getFilename());
                            }
                            getView().setBanner(bList, hList);
                        }
                    }
                }

                @Override
                public Class getClazz() {
                    return BannerEntity.class;
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

    //获取：6个图块信息
    private void getTableData() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            //paramsMap.put("type", "3");//类型 1：当天 2：当月 3：总计
            //paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
            XHttpUtils.post(context, paramsMap, ConstHost.Home_Table_Data, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    if (getView() != null) {
                        try {
                            org.json.JSONObject object = new org.json.JSONObject(result);
                            int alarmCount = object.optInt("alarmCount");
                            int warnCount = object.optInt("warnCount");
                            int problemCount = object.optInt("problemCount");
                            int deviceCount = object.optInt("deviceTotalCount");
                            String sInsRate = object.getString("sInsRate");
                            String sIntRate = object.getString("sIntRate");
                            /**设置火警总数*/
                            getView().setFire("" + alarmCount);
                            /**设置预警总数*/
                            getView().setWarning("" + warnCount);
                            /**设置故障总数*/
                            getView().setFault("" + problemCount);
                            /**设置设备总数*/
                            getView().setTotal(getSetTotal(deviceCount));
                            /**设置巡检率*/
                            getView().setClickRate(sInsRate);
                            /**设置完好率*/
                            getView().setPrefectRate(sIntRate);
                        } catch (Exception e) {
                            /**设置火警总数*/
                            getView().setFire("0");
                            /**设置预警总数*/
                            getView().setWarning("0");
                            /**设置故障总数*/
                            getView().setFault("0");
                            /**设置设备总数*/
                            getView().setTotal("0");
                            /**设置巡检率*/
                            getView().setClickRate("100");
                            /**设置完好率*/
                            getView().setPrefectRate("100");
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    /**设置火警总数*/
                    getView().setFire("0");
                    /**设置预警总数*/
                    getView().setWarning("0");
                    /**设置故障总数*/
                    getView().setFault("0");
                    /**设置设备总数*/
                    getView().setTotal("0");
                    /**设置巡检率*/
                    getView().setClickRate("100");
                    /**设置完好率*/
                    getView().setPrefectRate("100");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    //获取设备总数
    private String getSetTotal(int total) {
        if (total > 0) {
            //通告数字的长度100,0000/1,0000
            String strTotal = String.valueOf(total);
            if (strTotal.length() > 7) {
                //千万级
                return NumberUtils.setDecimalDouble(total * 1.0 / 10000000) + "千万";
            } else if (strTotal.length() > 6) {
                //百万级
                return NumberUtils.setDecimalDouble(total * 1.0 / 1000000) + "百万";
            } else if (strTotal.length() > 4) {
                //万级
                return NumberUtils.setDecimalDouble(total * 1.0 / 10000) + "万";
            } else {
                //万级以下
                return strTotal;
            }
        }
        return "0";
    }
}
