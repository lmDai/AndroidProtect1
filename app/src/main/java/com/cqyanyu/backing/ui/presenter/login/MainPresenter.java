package com.cqyanyu.backing.ui.presenter.login;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.entity.home.CheckRateEntity;
import com.cqyanyu.backing.ui.entity.home.FireKnowledgeEntity;
import com.cqyanyu.backing.ui.entity.home.VersionEntity;
import com.cqyanyu.backing.ui.fragment.HomeFragment;
import com.cqyanyu.backing.ui.fragment.WarnFragment;
import com.cqyanyu.backing.ui.fragment.my.MyFragment;
import com.cqyanyu.backing.ui.fragment.statistics.CountFragment;
import com.cqyanyu.backing.ui.mvpview.login.MainView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackEntity;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.backing.utils.Utils;
import com.cqyanyu.backing.utils.XAppUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.cqyanyu.mvpframework.utils.http.XCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页逻辑处理
 * Created by Administrator on 2017/7/6.
 */
public class MainPresenter extends BasePresenter<MainView> {
    private String myInspectionRate;//保存本人的任务巡检率的
    private File logPath;

    private static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            ArrayList<Fragment> fList = new ArrayList<>();
            ArrayList<BottomNavigationItem> bList = new ArrayList<>();

            /**初始化每个fragment和底部按钮*/
            if (InfoManger.getInstance().isPermission("16")) {
                fList.add(new HomeFragment());
                bList.add(new BottomNavigationItem(R.mipmap.ic_home_click, "首页").setInactiveIconResource(R.mipmap.ic_home_nor));
            }
            if (InfoManger.getInstance().isPermission("17")) {
                fList.add(new WarnFragment());
                bList.add(new BottomNavigationItem(R.mipmap.ic_gaojing_click, "告警").setInactiveIconResource(R.mipmap.ic_gaojing_nor));
            }
            if (InfoManger.getInstance().isPermission("18")) {
                fList.add(new CountFragment());
                bList.add(new BottomNavigationItem(R.mipmap.ic_tj_click, "统计").setInactiveIconResource(R.mipmap.ic_tj_nor));
            }
            if (InfoManger.getInstance().isPermission("19")) {
                fList.add(new MyFragment());
                bList.add(new BottomNavigationItem(R.mipmap.ic_my_click, "我的").setInactiveIconResource(R.mipmap.ic_my_nor));
            }
            /**初始化fragment*/
            getView().setViewPager(fList);
            /**初始化底部按钮*/
            getView().setNavigationBar(bList);
        }
    }

    /**
     * 获取最新的巡检率
     */
    public void getInspectionRate() {
        if (getView() != null) {
            long sec = (MyDate.getTodayZero() / 1000);
            long startdate = sec;
            long enddate = startdate + 86400;
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("type", "1");//类型  1：日巡 2：月检
            paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
            paramsMap.put("startdate", startdate + "");
            paramsMap.put("enddate", enddate + "");
//            ParamsMap paramsMap = new ParamsMap();
//            paramsMap.put("type", "1");//类型  1：日巡
//            paramsMap.put("inspectionmanid", CommonInfo.getInstance().getUserInfo().getUserid());//用户id
//            paramsMap.put("date", (MyDate.getTodayZero() / 1000) + "");//今天凌晨0点整
//            paramsMap.put("startdate", startdate + "");
//            paramsMap.put("enddate", enddate + "");
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_TASK_Page_URL, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            org.json.JSONObject object = new org.json.JSONObject(result);
                            int count = object.optInt("count");
                            String trueResult = object.optString("rows");
                            List<CheckRateEntity> mList = JSON.parseArray(trueResult, CheckRateEntity.class);
                            int i;
                            int devicetotal = 0;
                            int inspectiontotal = 0;
                            int problemtotal = 0;
                            for (i = 0; i < count; i++) {
                                devicetotal += mList.get(i).getDevicecount();
                                inspectiontotal += mList.get(i).getInspectioncount();
                                problemtotal += mList.get(i).getProblemcount();
                            }
                            /**设置巡检率*/
                            getView().setAllInspectionRate(Float.parseFloat(NumberUtils.setDecimalFloat((inspectiontotal * 100.0f / devicetotal))));
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

    /**
     * 获取最新的安装包
     */
    public void checkAppUpdate() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("ver", getVersionName(context));//版本号
            XHttpUtils.post(context, paramsMap, ConstHost.UPDATE_VERSION, new XICallbackEntity<VersionEntity>() {

                @Override
                public void onFail(String msg) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public void onSuccess(VersionEntity entity) {
                    getView().returnUpdate(entity);
                }

                @Override
                public Class getClazz() {
                    return VersionEntity.class;
                }
            });
        }
    }

    /**
     * 获取最新的声音
     */
    public void getVoice() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("typeid", "3");
            XHttpUtils.post(context, paramsMap, ConstHost.GET_VOICE, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    List<FireKnowledgeEntity> mList = JSON.parseArray(result, FireKnowledgeEntity.class);
                    if (mList.size() > 0) getView().getVoice(mList.get(0));
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
     * 日志上传
     */
    public void uploadLog() {
        File path = new File(XAppUtils.instance().getFileKeep(context));
        File[] files = path.listFiles();
        if (files != null) { // 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.getName().endsWith(".log")) {//格式为log文件
                    logPath = new File(file.getAbsolutePath());
                }
            }
        }
        if (Utils.fileIsExists(logPath.getAbsolutePath())) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("userName", "r6007");
            X.http().upload(context, paramsMap, ConstHost.UPLOAD_LOG, logPath, new XCallback.XCallbackUpload<String>() {
                @Override
                public void onLoading(long total, long current, boolean isDownloading) {

                }

                @Override
                public void onSuccess(int code, String msg, String data) {
                    XLog.i(msg);
                }

                @Override
                public Class getTClass() {
                    return String.class;
                }

                @Override
                public void onFail(String msg) {
                    XLog.i(msg);
                }

                @Override
                public void onFinished() {
                    logPath.delete();
                }
            });
        }
    }
}
