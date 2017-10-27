package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.home.AddUnitActivity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.mvpview.home.AddUnitView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
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
import java.util.Date;
import java.util.List;

/**
 * 增加单位逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class AddUnitPresenter extends BasePresenter<AddUnitView> {
    private UnitManageEntity entity;
    private String paths;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            if (!TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_EDIT)) {
                getLastInfo();
            } else {
                getUnitInfo();
            }
        }
    }

    /**
     * 获取上次输入的数据
     */
    private void getLastInfo() {
        String slatitude = (String) SharedPreferencesUtils.getParam(context, "latitude", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String slongitude = (String) SharedPreferencesUtils.getParam(context, "longitude", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        double dlatitude = 0;
        double dlongitude = 0;
        if (slatitude != null && slatitude.length() > 0) {
            dlatitude = Double.parseDouble(slatitude);
        }
        if (slongitude != null && slatitude.length() > 0) {
            dlongitude = Double.parseDouble(slongitude);
        }
        getView().setLan(dlatitude, dlongitude);//经度//纬度
        String linkPhone = (String) SharedPreferencesUtils.getParam(context, "linkPhone", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String userName = (String) SharedPreferencesUtils.getParam(context, "userName", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String unitName = (String) SharedPreferencesUtils.getParam(context, "unitName", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String presentUnit = (String) SharedPreferencesUtils.getParam(context, "presentUnit", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String pUnitStr = (String) SharedPreferencesUtils.getParam(context, "pUnitStr", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String regulatoryLevel = (String) SharedPreferencesUtils.getParam(context, "regulatoryLevel", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String seleveStr = (String) SharedPreferencesUtils.getParam(context, "seleveStr", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String unitType = (String) SharedPreferencesUtils.getParam(context, "unitType", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String typeStr = (String) SharedPreferencesUtils.getParam(context, "typeStr", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        String unitPosition = (String) SharedPreferencesUtils.getParam(context, "unitPosition", "", SharedPreferencesUtils.FILE_NAME_UNIT);
        getView().setUnitName(unitName);
        getView().setContactTel(linkPhone);
        getView().setContact(userName);
        getView().setPresentUnit(presentUnit, pUnitStr);
        getView().setRegulatoryLevel(regulatoryLevel, seleveStr);
        getView().setUnitSort(unitType, typeStr);
        getView().setUnitPosition(unitPosition);
    }

    //    获取单位信息
    private void getUnitInfo() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_EDIT)) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("oid", getView().getOid());
                XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_INFO_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackList<UnitManageEntity>() {
                    @Override
                    public void onSuccess(List<UnitManageEntity> mList) {
                        if (getView() != null) {
                            if (mList != null && mList.size() > 0) {
                                entity = mList.get(0);
//                                if (entity != null) {
//                                    /** 设置上级单位*/
//                                    getView().setPresentUnit(entity.getPid());
//                                    /** 设置监管等级*/
//                                    getView().setRegulatoryLevel(entity.getSelevelid(), entity.getSelevelstr());
//                                    /** 设置单位类型*/
//                                    getView().setUnitSort(entity.getTypeid(), entity.getTypestr());
//                                    /**设置单位名称*/
//                                    getView().setUnitName(entity.getName());
//                                    /**设置单位位置*/
//                                    getView().setUnitPosition(entity.getPosition());
//                                    /**设置联系人*/
//                                    getView().setContact(entity.getLinkman());
//                                    /**设置联系电话*/
//                                    getView().setContactTel(entity.getLinkphone());
//                                    /**设置经纬度*/
//                                    getView().setLan(entity.getLatitude(), entity.getLongitude());
//                                    /** 设置设备图片*/
//                                    getView().setPicture(entity.getPicpath());
//                                    if (entity.getChilddevicecount() > 0) {
//                                        getView().setIsNull(false);
//                                    } else {
//                                        getView().setIsNull(true);
//                                    }
//                                }
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
                        return UnitManageEntity.class;
                    }
                });
            } else {
                entity = new UnitManageEntity();
            }
        }
    }

    //添加单位
    private void addUnit() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_ADD)) {
                //当前页面是添加页面时
                if (TextUtils.isEmpty(getView().getUnitType()) || TextUtils.isEmpty(getView().getUnitName()) ||
                        TextUtils.isEmpty(getView().getRegulatoryLevel()) || TextUtils.isEmpty(getView().getUnitName())
                        || TextUtils.isEmpty(getView().getUnitPosition()) || TextUtils.isEmpty(getView().getContact())
                        || TextUtils.isEmpty(getView().getContactTel())) {
                    XToastUtil.showToast("填写信息不完整！");
                } else {
                    ParamsMap paramsMap = new ParamsMap();
                    paramsMap.put("typeid", getView().getUnitType());//单位类型
                    paramsMap.put("pid", getView().getPresentUnit());//上级单位 ID
                    paramsMap.put("selevelid", getView().getRegulatoryLevel());//监管等级
                    paramsMap.put("name", getView().getUnitName());//单位名称
                    paramsMap.put("position", getView().getUnitPosition());//单位位置
                    paramsMap.put("createmanid", CommonInfo.getInstance().getUserInfo().getUserid());//创建人 ID
                    paramsMap.put("createdate", getNowTime());//创建日期，单位：秒
                    paramsMap.put("linkman", getView().getContact());//联系人
                    paramsMap.put("linkphone", getView().getContactTel());//联系电话
                    paramsMap.put("longitude", "" + getView().getLongitude());//经度
                    paramsMap.put("latitude", "" + getView().getLatitude());//纬度
                    paramsMap.put("picpath", paths);//图片路径，多个图片用“;”隔开
                    //paramsMap.put("area", "");//单位面积
                    //paramsMap.put("remark", "");//备注
                    XHttpUtils.post(context, paramsMap, ConstHost.ADD_UNIT_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
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

    //修改单位
    private void modifyUnit() {
        if (getView() != null && entity != null) {
            if (TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_EDIT) && entity != null) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("typeid", getView().getUnitType());//单位类型
                paramsMap.put("oid", getView().getOid());//本单位 ID
                paramsMap.put("pid", getView().getPresentUnit());//上级单位 ID
                paramsMap.put("selevelid", getView().getRegulatoryLevel());//监管等级
                paramsMap.put("name", getView().getUnitName());//单位名称
                paramsMap.put("position", getView().getUnitPosition());//单位位置
                paramsMap.put("createmanid", entity.getCreatemanid());//创建人 ID
                paramsMap.put("createdate", entity.getCreatedate());//创建日期，单位：秒
                paramsMap.put("picpath", paths);//图片路径，多个图片用“;”隔开
                paramsMap.put("linkman", getView().getContact());//联系人
                paramsMap.put("linkphone", getView().getContactTel());//联系电话
                paramsMap.put("longitude", "" + getView().getLongitude());//经度
                paramsMap.put("latitude", "" + getView().getLatitude());//纬度
                //paramsMap.put("area", entity.getArea());//单位面积
                //paramsMap.put("remark", entity.getRemark());//备注
                XHttpUtils.post(context, paramsMap, ConstHost.MODIFY_UNIT_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
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

    //删除单位
    public void deleteUnit() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_EDIT) && entity != null) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("statusid", "1");
                paramsMap.put("pid", entity.getPid());//上级单位 ID
                paramsMap.put("oid", entity.getOid());//本单位 ID
                paramsMap.put("name", entity.getName());//本单位名字
                XHttpUtils.post(context, paramsMap, ConstHost.DELETE_UNIT_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
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

    //获取当前系统时间秒数
    private String getNowTime() {
        Date date = new Date();
        long minute = date.getTime() / 1000;
        return minute + "";
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
        if (TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_ADD)) {
            addUnit();
        } else if (TextUtils.equals(getView().getLabel(), AddUnitActivity.LABEL_VALUE_EDIT)) {
            modifyUnit();
        }
    }
}
