package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.mvpview.home.AddChildSetView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
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
public class AddChildSetPresenter extends BasePresenter<AddChildSetView> {
    private String paths;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            getLastInfo();
        }
    }

    /**
     * 输入记忆
     * 获取上次输入的内容
     */
    private void getLastInfo() {
        getView().setType((String) SharedPreferencesUtils.getParam(context, "childType", "", SharedPreferencesUtils.FILE_NAME_SET));//设备类型
        getView().setNum((String) SharedPreferencesUtils.getParam(context, "childNum", "", SharedPreferencesUtils.FILE_NAME_SET));//设备编号
        getView().setPosition((String) SharedPreferencesUtils.getParam(context, "childPosition", "", SharedPreferencesUtils.FILE_NAME_SET));//详细地址
        getView().setMaxValue((String) SharedPreferencesUtils.getParam(context, "maxValue", "", SharedPreferencesUtils.FILE_NAME_SET));//最大值
        getView().setMinValue((String) SharedPreferencesUtils.getParam(context, "minValue", "", SharedPreferencesUtils.FILE_NAME_SET));//最小值
        getView().setFloor((String) SharedPreferencesUtils.getParam(context, "childFloor", "", SharedPreferencesUtils.FILE_NAME_SET));//楼层
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
                        addChildSet();
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
                addChildSet();
            }
        }
    }

    //添加子设备
    private void addChildSet() {
        if (getView() != null) {
            if (TextUtils.isEmpty(getView().getNum())) {
                XToastUtil.showToast("请输入设备编号");
                return;
            }
            if (TextUtils.isEmpty(getView().getMaxValue())) {
                XToastUtil.showToast("请输入最大值");
                return;
            }
            if (TextUtils.isEmpty(getView().getMinValue())) {
                XToastUtil.showToast("请输入最小值");
                return;
            }
            //当前页面是添加页面时
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("sn", getView().getNum());//设备测量点
            paramsMap.put("position", getView().getPosition());//设备位置
            paramsMap.put("createdate", MyDate.getNowTime());//创建时间
            paramsMap.put("typeid", getView().getType());//设备类型
            paramsMap.put("minval", getView().getMinValue());//最大值(水压默认：300~1000；液位默认：100~5000)
            paramsMap.put("maxval", getView().getMaxValue());//最小值
            paramsMap.put("floor", getView().getFloor());//楼层
            paramsMap.put("picpath", paths);//图片路径
            paramsMap.put("oid", CommonInfo.getInstance().getUserInfo().getUserid());//自身 ID
            paramsMap.put("pid", getView().getOid());//父设备ＩＤ
            paramsMap.put("buildid", getView().getBuild());//所属建筑 ID
            paramsMap.put("unitid", getView().getUnitId());//所属单位 ID
            paramsMap.put("longitude", "" + getView().getLongitude());//经度
            paramsMap.put("latitude", "" + getView().getLatitude());//纬度

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
