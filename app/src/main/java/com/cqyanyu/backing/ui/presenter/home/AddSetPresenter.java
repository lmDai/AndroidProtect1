package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.home.AddSetActivity;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.mvpview.home.AddSetView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.cqyanyu.mvpframework.utils.http.XCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 增加设备逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class AddSetPresenter extends BasePresenter<AddSetView> {
    private SetManageEntity entity;
    private String paths;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT)) {
                getSetInfo();
            } else {
                getLastInfo();
            }
        }
    }

    /**
     * 获取上次输入
     */
    private void getLastInfo() {
        String slatitude = (String) SharedPreferencesUtils.getParam(context, "latitude", "", SharedPreferencesUtils.FILE_NAME_SET);
        String slongitude = (String) SharedPreferencesUtils.getParam(context, "longitude", "", SharedPreferencesUtils.FILE_NAME_SET);
        double dlatitude = 0;
        double dlongitude = 0;
        if (slatitude != null && slatitude.length() > 0) {
            dlatitude = Double.parseDouble(slatitude);
        }
        if (slongitude != null && slatitude.length() > 0) {
            dlongitude = Double.parseDouble(slongitude);
        }
        getView().setLan(dlatitude, dlongitude);//经度//纬度
        getView().setUnitID((String) SharedPreferencesUtils.getParam(context, "unitid", "", SharedPreferencesUtils.FILE_NAME_SET));
        getView().setBuild((String) SharedPreferencesUtils.getParam(context, "build", "", SharedPreferencesUtils.FILE_NAME_SET),
                (String) SharedPreferencesUtils.getParam(context, "buildStr", "", SharedPreferencesUtils.FILE_NAME_SET));//所属建筑
        getView().setType((String) SharedPreferencesUtils.getParam(context, "type", "", SharedPreferencesUtils.FILE_NAME_SET),
                (String) SharedPreferencesUtils.getParam(context, "typeStr", "", SharedPreferencesUtils.FILE_NAME_SET));//设备类型
        getView().setNum((String) SharedPreferencesUtils.getParam(context, "num", "", SharedPreferencesUtils.FILE_NAME_SET));//设备编号
        getView().setPosition((String) SharedPreferencesUtils.getParam(context, "position", "", SharedPreferencesUtils.FILE_NAME_SET));//详细地址
        //厂家
        String factory = (String) SharedPreferencesUtils.getParam(context, "factory", "", SharedPreferencesUtils.FILE_NAME_SET);//厂家
        String[] factoryArrays = factory.split(",");
        if (factoryArrays.length > 0) getView().setFactory(factoryArrays[0]);
        //品牌
        String brand = (String) SharedPreferencesUtils.getParam(context, "brand", "", SharedPreferencesUtils.FILE_NAME_SET);
        String[] brandArrays = brand.split(",");
        if (brandArrays.length > 0) getView().setBrand(brandArrays[0]);
        //设备型号
        String size = (String) SharedPreferencesUtils.getParam(context, "size", "", SharedPreferencesUtils.FILE_NAME_SET);
        String[] sizeArrays = size.split(",");
        if (sizeArrays.length > 0) getView().setSize(sizeArrays[0]);
        //楼层
        String floor = (String) SharedPreferencesUtils.getParam(context, "floor", "", SharedPreferencesUtils.FILE_NAME_SET);
        String[] floorArrays = floor.split(",");
        if (floorArrays.length > 0) getView().setFloor(floorArrays[0]);

        getView().setGuarantee((String) SharedPreferencesUtils.getParam(context, "guarantee", "", SharedPreferencesUtils.FILE_NAME_SET));//保质期
        getView().setInspectionWay((String) SharedPreferencesUtils.getParam(context, "inspectionWay", "", SharedPreferencesUtils.FILE_NAME_SET));//巡检方式
        getView().setInspectionUnit((String) SharedPreferencesUtils.getParam(context, "inspectionUnit", "", SharedPreferencesUtils.FILE_NAME_SET));//巡检单位
        getView().setInspectionUnitName((String) SharedPreferencesUtils.getParam(context, "inspectionUnitstr", "", SharedPreferencesUtils.FILE_NAME_SET));//巡检单位
        getView().setStartData((String) SharedPreferencesUtils.getParam(context, "startDate", "", SharedPreferencesUtils.FILE_NAME_SET));//开始使用时间


    }

    //获取设备信息
    private void getSetInfo() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT)) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("oid", getView().getOid());
                paramsMap.put("unitid", getView().getUnitId());
                XHttpUtils.post(context, paramsMap, ConstHost.GET_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackList<SetManageEntity>() {
                    @Override
                    public void onSuccess(List<SetManageEntity> mList) {
                        if (getView() != null) {
                            if (mList != null && mList.size() > 0) {
                                entity = mList.get(0);
                                if (entity != null) {
                                    /** 设置所属建筑*/
                                    getView().setBuild(entity.getBuildid(), entity.getBuildstr());
                                    /** 设置设备类型*/
                                    getView().setType(entity.getTypeid(), entity.getTypestr());
                                    /** 设置设备编号*/
                                    getView().setNum(entity.getSn());
                                    /** 设置设备位置*/
                                    getView().setPosition(entity.getPosition());
                                    /** 设置设备生产厂家*/
                                    getView().setFactory(entity.getFactory());
                                    /** 设置设备品牌*/
                                    getView().setBrand(entity.getBrand());
                                    /** 设置设备保质期*/
                                    getView().setGuarantee(entity.getEnddate());
                                    /** 设置设备巡检方式*/
                                    getView().setInspectionWay(entity.getInspectioninterval() + "");
                                    /** 设置设备巡检单位*/
                                    getView().setInspectionUnit(entity.getUnitid());
                                    getView().setInspectionUnitName(entity.getUnitstr());
                                    /** 设置设备型号*/
                                    getView().setStartData(entity.getStartdate());
                                    /** 设置设备型号*/
                                    getView().setSize(entity.getModel());
                                    /** 设置设备楼层*/
                                    getView().setFloor(entity.getFloor());
                                    /** 设置设备图片*/
                                    getView().setPicture(entity.getPicpath());
                                    /**设置经纬度*/
                                    getView().setLan(entity.getLatitude(), entity.getLongitude());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        XLog.e(msg);
                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public Class getClazz() {
                        return SetManageEntity.class;
                    }
                });
            } else {
                entity = new SetManageEntity();
            }
        }
    }

    //添加设备
    private void addSet() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_ADD)) {
                //当前页面是添加页面时
                final String build = getView().getBuild();
                final String type = getView().getType();
                final String position = getView().getPosition();
                final String factory = getView().getFactory();
                final String brand = getView().getBrand();
                final String guarantee = getView().getGuarantee();
                final String size = getView().getSize();
                final String floor = getView().getFloor();
                final String num = getView().getNum();
                final String startDate = getView().getStartDate();
                if (TextUtils.isEmpty(build) || TextUtils.isEmpty(type) || TextUtils.isEmpty(position)
                        || TextUtils.isEmpty(factory) || TextUtils.isEmpty(brand)
                        || TextUtils.isEmpty(guarantee) || TextUtils.isEmpty(size)
                        || TextUtils.isEmpty(floor) || TextUtils.isEmpty(num)
                        || TextUtils.isEmpty(startDate)) {
                    XToastUtil.showToast("信息填写不完整！");
                    return;
                } else {
                    ParamsMap paramsMap = new ParamsMap();
                    paramsMap.put("unitid", getView().getUnitId());//所属单位 ID
                    paramsMap.put("buildid", build);//所属建筑 ID
                    paramsMap.put("typeid", type);//设备类型 ID
                    paramsMap.put("sn", num);//设备编号
                    paramsMap.put("position", position);//详细位置
                    paramsMap.put("longitude", "" + getView().getLongitude());//经度
                    paramsMap.put("latitude", "" + getView().getLatitude());//纬度
                    paramsMap.put("floor", floor);//楼层
                    paramsMap.put("factory", factory);//厂家
                    paramsMap.put("brand", brand);//品牌
                    //paramsMap.put("videosource", "");//摄像头 IP+port
                    paramsMap.put("picpath", paths);//图片路径，多个图片用“;”隔开
                    paramsMap.put("startdate", startDate);//开始使用日期
                    paramsMap.put("enddate", guarantee);//保质期
                    paramsMap.put("inspectioninterval", getView().getInspectionWay());//巡检间隔
                    paramsMap.put("maintenanceid", getView().getInspectionUnit());//维护单位ＩＤ
                    paramsMap.put("createmanid", CommonInfo.getInstance().getUserInfo().getUserid());//创建人ＩＤ
                    paramsMap.put("model", size);//型号
                    paramsMap.put("createdate", MyDate.getNowTime());//创建日期，单位：秒
                    //paramsMap.put("remark", "");//备注
                    XHttpUtils.post(context, paramsMap, ConstHost.ADD_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                        @Override
                        public void onSuccess(String result) {
                            if (getView() != null) {
                                if (!TextUtils.isEmpty(result)) {
//                                    if (TextUtils.equals(result, "0")) {
//                                        XToastUtil.showToast("提交失败");
//                                    } else {
                                    XToastUtil.showToast(result);
                                    //更新上一个页面
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                    context.finish();
//                                    }
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
        }
    }

    //修改设备
    private void modifySet() {
        if (getView() != null && entity != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT) && entity != null) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("unitid", getView().getUnitId());//所属单位 ID
                paramsMap.put("buildid", getView().getBuild());//所属建筑 ID
                paramsMap.put("typeid", getView().getType());//设备类型 ID
                paramsMap.put("sn", getView().getNum());//设备编号
                paramsMap.put("oid", entity.getOid());
                paramsMap.put("position", getView().getPosition());//详细位置
                paramsMap.put("longitude", "" + getView().getLongitude());//经度
                paramsMap.put("latitude", "" + getView().getLatitude());//纬度
                paramsMap.put("floor", getView().getFloor());//楼层
                paramsMap.put("factory", getView().getFactory());//厂家
                paramsMap.put("brand", getView().getBrand());//品牌
                //  paramsMap.put("videosource", "");//摄像头 IP+port
                paramsMap.put("picpath", paths);//图片路径，多个图片用“;”隔开
                paramsMap.put("startdate", getView().getStartDate());//开始使用日期
                paramsMap.put("enddate", getView().getGuarantee());//保质期
                paramsMap.put("inspectioninterval", getView().getInspectionWay());//巡检间隔
                paramsMap.put("maintenanceid", getView().getInspectionUnit());//维护单位ＩＤ
                paramsMap.put("createmanid", entity.getCreatemanid());//创建人ＩＤ
                paramsMap.put("model", getView().getSize());//型号
                paramsMap.put("createdate", entity.getCreatedate());//创建日期，单位：秒
                // paramsMap.put("remark", entity.getRemark());//备注
                XHttpUtils.post(context, paramsMap, ConstHost.MODIFY_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
//                                if (TextUtils.equals(result, "0")) {
//                                    XToastUtil.showToast("修改失败");
//                                } else {
                                XToastUtil.showToast(result);
                                //更新上一个页面
                                EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                context.finish();
//                                }
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
    }

    //删除设备
    public void deleteSet() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT) && entity != null) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("unitid", entity.getUnitid());//所属单位 ID
                paramsMap.put("buildid", entity.getBuildid());//所属单位 ID
                paramsMap.put("oid", entity.getOid());//设备 ID
                paramsMap.put("sn", entity.getSn());//设备编号
                XHttpUtils.post(context, paramsMap, ConstHost.DELETE_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
//                                if (TextUtils.equals(result, "0")) {
//                                    XToastUtil.showToast("删除失败");
//                                } else {
                                XToastUtil.showToast(result);
                                //更新上一个页面
                                EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                context.finish();
//                                }
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
    }

    //获取巡检方式
    private String getInspectionWay() {
        if (getView() != null) {
            String way = getView().getInspectionWay();
            if (!TextUtils.isEmpty(way)) {
                int i;
                switch (way) {
                    case "日巡":
                        i = 24 * 60 * 60;
                        return "" + i;
                    case "月检":
                        i = 30 * 24 * 60 * 60;
                        return "" + i;
                    case "季检":
                        i = 3 * 30 * 24 * 60 * 60;
                        return "" + i;
                    case "年检":
                        i = 12 * 30 * 24 * 60 * 60;
                        return "" + i;
                }
            }
        }
        return "";
    }

    //获取巡检方式
    private String getInspectionWay(long time) {
        if (getView() != null) {
            if (time == 24 * 60 * 60) {
                return "日巡";
            } else if (time == 30 * 24 * 60 * 60) {
                return "月检";
            } else if (time == 3 * 30 * 24 * 60 * 60) {
                return "季检";
            } else if (time == 12 * 30 * 24 * 60 * 60) {
                return "年检";
            }
        }
        return "";
    }

    //提交图片
    public void commitPicture() {
        if (getView() != null) {
            paths = "";
            List<File> files = new ArrayList<>();
            List<String> pictureList = getView().getPictureList();
            if (pictureList != null) {
                for (String path : pictureList) {
                    if (!TextUtils.isEmpty(path)) {
                        if (path.startsWith("/storage")) {
                            files.add(new File(path));
                        } else {
                            paths = paths + ";" + path;
                        }
                    }
                }
            }
            if (files.size() > 0) {
                X.http().upload(context, new ParamsMap(), ConstHost.UPLOAD_IMAGE, NetDialogUtil.showLoadDialog(context, "请稍后..."), files, new XCallback.XCallbackUploads<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isDownloading, int currentFile, int totalFile) {

                    }

                    @Override
                    public void onAllFinish() {
                        commit();
                    }

                    @Override
                    public void onSuccess(int code, String msg, String data) {
                        if (getView() != null) {
                            if (code == 200) {
                                paths = paths + ";" + data;

                            } else {
                                XToastUtil.showToast(msg);
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
                commit();
            }
        }
    }

    //提交修改或添加
    private void commit() {
        if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_ADD)) {
            addSet();
        } else if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT)) {
            modifySet();
        }
    }

    //添加子设备
    public void addChildSet() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_ADD)) {
                //当前页面是添加页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("unitid", getView().getUnitId());//所属单位 ID
                paramsMap.put("buildid", getView().getBuild());//所属建筑 ID

                paramsMap.put("typeid", getView().getType());//设备类型 ID
                paramsMap.put("sn", getView().getNum());//设备编号
                paramsMap.put("position", getView().getPosition());//详细位置
                paramsMap.put("longitude", "" + getView().getLongitude());//经度
                paramsMap.put("latitude", "" + getView().getLatitude());//纬度
                paramsMap.put("floor", getView().getFloor());//楼层
                paramsMap.put("factory", getView().getFactory());//厂家
                paramsMap.put("brand", getView().getBrand());//品牌
                paramsMap.put("videosource", "");//摄像头 IP+port
                paramsMap.put("picparth", paths);//图片路径，多个图片用“;”隔开
                paramsMap.put("startdate", getView().getStartDate());//开始使用日期
                paramsMap.put("enddate", getView().getGuarantee());//保质期
                paramsMap.put("inspectioninterval", getView().getInspectionWay());//巡检间隔
                paramsMap.put("maintenanceid", getView().getInspectionUnit());//维护单位ＩＤ
                paramsMap.put("createmanid", CommonInfo.getInstance().getUserInfo().getUserid());//创建人ＩＤ
                paramsMap.put("model", getView().getSize());//型号
                paramsMap.put("createdate", MyDate.getNowTime());//创建日期，单位：秒
                //paramsMap.put("remark", "");//备注
                XHttpUtils.post(context, paramsMap, ConstHost.ADD_CHILD_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
                                if (TextUtils.equals(result, "0")) {
                                    XToastUtil.showToast("提交失败");
                                } else {
                                    XToastUtil.showToast("提交成功");
                                    //更新上一个页面
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                    context.finish();
                                }
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
    }

    //修改单位
    public void modifyChildUnit() {
        if (getView() != null && entity != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT)) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("unitid", getView().getUnitId());//所属单位 ID
                paramsMap.put("buildid", getView().getBuild());//所属建筑 ID
                paramsMap.put("typeid", getView().getType());//设备类型 ID
                paramsMap.put("sn", getView().getNum());//设备编号
                paramsMap.put("position", getView().getPosition());//详细位置
                paramsMap.put("longitude", "" + getView().getLongitude());//经度
                paramsMap.put("latitude", "" + getView().getLatitude());//纬度
                paramsMap.put("floor", getView().getFloor());//楼层
                paramsMap.put("factory", getView().getFactory());//厂家
                paramsMap.put("brand", getView().getBrand());//品牌
                paramsMap.put("videosource", "");//摄像头 IP+port
                paramsMap.put("picparth", paths);//图片路径，多个图片用“;”隔开
                paramsMap.put("startdate", getView().getStartDate());//开始使用日期
                paramsMap.put("enddate", getView().getGuarantee());//保质期
                paramsMap.put("inspectioninterval", getView().getInspectionWay());//巡检间隔
                paramsMap.put("maintenanceid", getView().getInspectionUnit());//维护单位ＩＤ
                paramsMap.put("createmanid", entity.getCreatemanid());//创建人ＩＤ
                paramsMap.put("model", getView().getSize());//型号
                paramsMap.put("createdate", entity.getCreatedate());//创建日期，单位：秒
                // paramsMap.put("remark", entity.getRemark());//备注
                XHttpUtils.post(context, paramsMap, ConstHost.MODIFY_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
                                if (TextUtils.equals(result, "0")) {
                                    XToastUtil.showToast("修改失败");
                                } else {
                                    XToastUtil.showToast("修改成功");
                                    //更新上一个页面
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                    context.finish();
                                }
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
    }

    //删除单位
    public void deleteChildUnit() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddSetActivity.LABEL_VALUE_EDIT) && entity != null) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("statusid", "1");
                paramsMap.put("unitid", entity.getUnitid());//所属单位 ID
                paramsMap.put("oid", entity.getOid());//设备 ID
                XHttpUtils.post(context, paramsMap, ConstHost.DELETE_DEVICE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
                                if (TextUtils.equals(result, "0")) {
                                    XToastUtil.showToast("删除失败");
                                } else {
                                    XToastUtil.showToast("删除成功");
                                    //更新上一个页面
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                    context.finish();
                                }
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
    }
}

