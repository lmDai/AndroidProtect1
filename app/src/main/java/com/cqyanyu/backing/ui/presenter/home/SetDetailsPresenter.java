package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.EntryFactory;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.home.MyTaskActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.MyTaskEntity;
import com.cqyanyu.backing.ui.entity.home.ProblemEntity;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.entity.home.TaskDetailsEntity;
import com.cqyanyu.backing.ui.mvpview.home.SetDetailsView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.backing.utils.NumberUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.cqyanyu.mvpframework.utils.http.XCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备详情逻辑处理类
 * Created by Administrator on 2017/7/18.
 */
public class SetDetailsPresenter extends BasePresenter<SetDetailsView> {
    private SetManageEntity setEntity;
    private TaskDetailsEntity taskEntity;
    private List<TaskDetailsEntity> list;
    private String paths;
    private String myInspectionRate;

    //初始化
    public void init() {
        if (TextUtils.equals(getView().getLable(), MyTaskActivity.class.getSimpleName())) {
            getMyTaskDetails();
        } else {
            getTaskDetailsOfAll();
        }
    }

    /**
     * 获取问题
     *
     * @param pid 问题id
     */
    private void getProblem(final String pid) {
        if (getView() != null) {
            List<EntryEntity> entryListOfPid = InfoManger.getInstance().
                    getEntryListOfPid(context, pid);
            List<String> listName = new ArrayList<>();
            List<String> listId = new ArrayList<>();
            if (entryListOfPid != null && entryListOfPid.size() > 0) {
                for (EntryEntity entity : entryListOfPid) {
                    if (entity != null) {
                        listName.add(entity.getName());
                        listId.add(entity.getOid());
                    }
                }
                getView().setProblemList(listName, listId);
            } else {
                XHttpUtils.post(context, new ParamsMap(), ConstHost.GET_ENTRY_URL, new XICallbackList<EntryEntity>() {
                    @Override
                    public void onSuccess(List<EntryEntity> mList) {
                        if (mList != null && mList.size() > 0) {
                            List<EntryEntity> entryList = new ArrayList<>();
                            for (EntryEntity entry : mList) {
                                if (TextUtils.equals(entry.getPid(), pid)) {
                                    entryList.add(entry);
                                }
                            }
                            List<String> listName = new ArrayList<>();
                            List<String> listId = new ArrayList<>();
                            for (EntryEntity entity : entryList) {
                                if (entity != null) {
                                    listName.add(entity.getName());
                                    listId.add(entity.getOid());
                                }
                                getView().setProblemList(listName, listId);
                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        XToastUtil.showToast("词条获取失败!");
                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public Class getClazz() {
                        return EntryEntity.class;
                    }
                });
            }
        }
    }

    //通过用户id获取任务详情
    private void getTaskDetailsOfAll() {
        if (getView() != null) {
            long sec = (MyDate.getTodayZero() / 1000);
            long startdata = sec;
            long enddate = sec + 1;
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("type", "1");//1：日巡 2：月检
            paramsMap.put("inspectionmanid", CommonInfo.getInstance().getUserInfo().getUserid());//任务 ID
            paramsMap.put("startdate", startdata + "");//单位 ID
            paramsMap.put("enddate", enddate + "");//单位 ID
            XHttpUtils.post(context, paramsMap, ConstHost.GET_MY_ALL_TASK_DETAILS_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackList<TaskDetailsEntity>() {
                @Override
                public void onSuccess(List<TaskDetailsEntity> mList) {
                    if (getView() != null) {
                        if (mList != null) {
                            list = mList;
                        } else {
                            XToastUtil.showToast("任务获取失败！请重新扫描");
                            XLog.i("getTaskDetailsOfAll" + "任务获取失败！请重新扫描");
                            context.finish();
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    XLog.e(msg);
                    if (getView() != null) {
                        XToastUtil.showToast("任务获取失败！请重新扫描");
                        XLog.i("getTaskDetailsOfAll" + "onFailed" + "任务获取失败！请重新扫描");
                        context.finish();
                    }
                }

                @Override
                public void onFinished() {
                    getSetInfo();
                }

                @Override
                public Class getClazz() {
                    return TaskDetailsEntity.class;
                }
            });
        }
    }

    //通过任务id获取任务详情
    private void getMyTaskDetails() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("type", "1");//1：日巡 2：月检
            paramsMap.put("statisticsid", getView().getTaskIds());//任务 ID
            paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());//单位 ID
            XHttpUtils.post(context, paramsMap, ConstHost.GET_TASK_DETAILS_Page_URL, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            org.json.JSONObject object = new org.json.JSONObject(result);
                            int total = object.optInt("total");
                            int count = object.optInt("count");
                            String trueResult = object.optString("rows");
                            list = JSON.parseArray(trueResult, TaskDetailsEntity.class);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

                @Override
                public void onFail(String msg) {
                    XLog.e(msg);
                    if (getView() != null) {
                        XToastUtil.showToast("任务获取失败！请重新扫描");
                        XLog.i("getTaskDetailsOfAll" + "onFailed" + "任务获取失败！请重新扫描");
                        context.finish();
                    }
                }

                @Override
                public void onFinished() {
                    getSetInfo();
                }
            });
        }
    }

    //获取设备信息
    private void getSetInfo() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("oid", "0");
            paramsMap.put("sn", getView().getScanResult());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_DEVICE_URL, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    if (!TextUtils.isEmpty(result)) {
                        if (getView() != null) {
                            List<SetManageEntity> mList = JSON.parseArray(result, SetManageEntity.class);
                            if (mList != null && mList.size() > 0) {
                                setEntity = mList.get(0);
                                if (setEntity != null) {
                                    /** 设置设备编号*/
                                    getView().setCode(setEntity.getSn());
                                    /** 设置设备类型*/
                                    getView().setType(setEntity.getTypestr());
                                    /** 设置设备位置*/
                                    getView().setPosition(setEntity.getPosition());
                                    setTaskDetails();
                                }
                            } else {
                                XToastUtil.showToast("设备获取失败！请重新扫描");
                                XLog.i("getSetInfo1" + "任务获取失败！请重新扫描");
                                getView().gotoScan();
                            }
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    XLog.e(msg);
                    if (getView() != null) {
                        XToastUtil.showToast("设备获取失败！请重新扫描");
                        XLog.i("getSetInfo" + "任务获取失败！请重新扫描");
                        context.finish();
                    }
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 通过设备id获取问题id
     *
     * @param setId 设备id
     */
    private void getProblemId(String setId) {
        EntryFactory.getProblemId(context, "1", setId, new EntryFactory.OnResultProblemListener() {
            @Override
            public void onResult(List<ProblemEntity> mList) {
                if (mList.size() > 0) {
                    for (ProblemEntity entity : mList) {
                        if (entity != null && TextUtils.equals(entity.getTypeid(), "1")) {
                            getProblem(entity.getProblempid());
                        }
                    }
                }
            }

//            @Override
//            public void onResult(String problemId) {
//               // getProblem(problemId);
//            }
        });
//        EntryFactory.getProblemId(context, "1", setId, new EntryFactory.OnResultProblemIdListener() {
//            @Override
//            public void onResult(String problemId) {
//                getProblem(problemId);
//            }
//        });
    }

    //设置任务详情
    private void setTaskDetails() {
        //遍历到相同的设备id时（oid=deviceid）
        for (TaskDetailsEntity task : list) {
            if (TextUtils.equals(task.getDeviceid(), setEntity.getOid())) {
                taskEntity = task;
                /** 设置巡检状态*/
                getView().setInspectionState(taskEntity.getIsinspection() == 0 ? "未巡检" : "已巡检");
                if (taskEntity.getIsinspection() == 0) {
                    /** 通过设备类型获取问题*/
                    getProblemId(setEntity.getTypeid());
                    getView().setIsProblem(false);
                } else {
                    getView().setResult(setEntity.getStatusstr());
                    getView().setIsProblem(false);
                    getView().setDescibe(setEntity.getRemark() == null ? "" : setEntity.getRemark());
                    getView().setPicture(setEntity.getPicpath());
                }
                getView().setEnable(taskEntity.getIsinspection() == 0);
                return;
            }
        }
        if (getView() != null) {
            getView().onTask();
        }
    }

    //提交任务
    private void commitTask() {
        if (getView() != null && setEntity != null && taskEntity != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("oid", taskEntity.getOid());//任务 ID
            paramsMap.put("buildid", setEntity.getBuildid());//建筑ＩＤ
            paramsMap.put("unitid", setEntity.getUnitid());//单位 ID
            paramsMap.put("deviceid", setEntity.getOid());//设备 ID
            paramsMap.put("statisticsid", taskEntity.getStatisticsid());//任务号
            paramsMap.put("eatsec", "0");//两次巡检时间差
            paramsMap.put("date", "" + (MyDate.getTodayZero() / 1000));//某天时间
            paramsMap.put("inspectiondate", MyDate.getNowTime());//巡检时间
            paramsMap.put("isinspection", "1");//是否巡检 0 未巡检 1 已巡检
            paramsMap.put("isproblem", getView().getIsProblem() ? "1" : "0");// 是否有问题 0 正常 (根据设备类型，从字典里面列出问题,问题可变)
            paramsMap.put("problemid", getView().getProblemId());// 问题ＩＤ (根据设备类型，从字典里面列出问题)
            paramsMap.put("inspectionmanid", CommonInfo.getInstance().getUserInfo().getUserid());//巡检人ＩＤ
            paramsMap.put("longitude", "" + setEntity.getLongitude());//巡检位置
            paramsMap.put("latitude", "" + setEntity.getLatitude());//巡检位置
            String remark = getView().getDescribe();
            if (remark != null && remark.length() > 0) {
                paramsMap.put("remark", remark);//巡检备注
            }
            if (paths != null && paths.length() > 0) {
                paramsMap.put("picpath", paths);// 图片路径
            }
            XHttpUtils.post(context, paramsMap, ConstHost.COMMIT_TASK_DETAILS_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    if (getView() != null) {
                        if (!TextUtils.isEmpty(result)) {
                            if (TextUtils.equals(result, "Successful inspection!")) {
                                getView().gotoScan();
                            }
                            XToastUtil.showToast(result);
                        }
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

    //提交图片
    private void commitPicture() {
        if (getView() != null) {
            List<File> files = new ArrayList<>();
            List<String> pictureList = getView().getPictureList();
            if (pictureList != null) {
                for (String path : pictureList) {
                    files.add(new File(path));
                }
            }
            if (files.size() > 0) {
                paths = "";
                X.http().upload(context, new ParamsMap(), ConstHost.UPLOAD_IMAGE, NetDialogUtil.showLoadDialog(context, "请稍后..."), files, new XCallback.XCallbackUploads<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isDownloading, int currentFile, int totalFile) {

                    }

                    @Override
                    public void onAllFinish() {
                        commitTask();
                    }

                    @Override
                    public void onSuccess(int code, String msg, String data) {
                        if (getView() != null) {
                            if (code == 200) {
                                paths = paths + ";" + data;
                            }
                        }
                    }

                    @Override
                    public Class getTClass() {
                        return String.class;
                    }

                    @Override
                    public void onFail(String msg) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            } else {
                XToastUtil.showToast("请选择图片!");
            }
        }
    }

    //提交
    public void commit() {
        if (getView() != null && taskEntity != null) {
            if (getView().getIsProblem()) {
                //有问题页面
                commitPicture();
            } else {
                //没有问题页面
                commitTask();
            }
        }
    }


    /**
     * 获取最新的巡检率
     */
    public void getInspectionRate() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("type", "1");//类型  1：日巡
            paramsMap.put("inspectionmanid", CommonInfo.getInstance().getUserInfo().getUserid());//用户id
            paramsMap.put("date", (MyDate.getTodayZero() / 1000) + "");//今天凌晨0点整
            XHttpUtils.post(context, paramsMap, ConstHost.GET_USER_TASK_URL, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    List<MyTaskEntity> mList = JSON.parseArray(result, MyTaskEntity.class);
                    if (getView() != null) {
                        if (mList != null && mList.size() > 0) {
                            int totalInspectionCount = 0;
                            int totalDeviceCount = 0;
                            for (int i = 0; i < mList.size(); i++) {
                                MyTaskEntity entity = mList.get(i);
                                if (TextUtils.equals(getView().getTaskIds(), entity.getOid())) {
                                    totalInspectionCount = entity.getInspectioncount();
                                    totalDeviceCount = entity.getDevicecount();
                                }
                            }
                            if (totalDeviceCount > 0) {
                                myInspectionRate = NumberUtils.setDecimalFloat(totalInspectionCount * 100.0f / totalDeviceCount);
                            }
                        }
                        if (myInspectionRate == null) {
                            getView().setAllInspectionRate(0);
                        } else {
                            getView().setAllInspectionRate(Float.parseFloat(myInspectionRate));
                        }
                    }

                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("我的任务获取失败!");
                    XLog.i("getInspectionRate" + "任务获取失败！请重新扫描");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }
}
