package com.cqyanyu.backing.ui.presenter.alarm;

import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.manger.EntryFactory;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.alarm.HandleActivity;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.ProblemEntity;
import com.cqyanyu.backing.ui.mvpview.warn.HandleView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理告警逻辑处理类
 * Created by Administrator on 2017/7/18.
 */
public class HandlePresenter extends BasePresenter<HandleView> {

    public void init() {
        if (getView() != null) {
//            List<EntryEntity> entryListOfPid = null;
//            List<String> listName = new ArrayList<>();
//            List<String> listId = new ArrayList<>();
//            switch (getView().getLabel()) {
//                case HandleActivity.LABEL_VALUE_FIRE://火警处理
//                    entryListOfPid = InfoManger.getInstance().getEntryListOfPid(context, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_FIRE_HANDLE));
//                    break;
//                case HandleActivity.LABEL_VALUE_FAULT://故障处理
//                    entryListOfPid = InfoManger.getInstance().getEntryListOfPid(context, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_FAULT_HANDLE));
//                    break;
//                case HandleActivity.LABEL_VALUE_WARNING://预警处理
//                    entryListOfPid = InfoManger.getInstance().getEntryListOfPid(context, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_WARNING_HANDLE));
//                    break;
//                case HandleActivity.LABEL_VALUE_OTHER://其他处理
//                    entryListOfPid = InfoManger.getInstance().getEntryListOfPid(context, InfoManger.getInstance().getEntryPid(InfoManger.KEY_ENTRY_OTHER_HANDLE));
//                    break;
//            }
//            if (entryListOfPid != null && entryListOfPid.size() > 0) {
//                for (EntryEntity entity : entryListOfPid) {
//                    if (entity != null) {
//                        listName.add(entity.getName());
//                        listId.add(entity.getOid());
//                    }
//                }
//                getView().setProblemList(listName, listId);
//            }

            switch (getView().getLabel()) {
                case HandleActivity.LABEL_VALUE_FIRE://火警处理
                    getProblemId("2", "");
                    //getHandleProblem("2");
                    break;
                case HandleActivity.LABEL_VALUE_FAULT://故障处理
                    getProblemId("3", "");
                    break;
                case HandleActivity.LABEL_VALUE_WARNING://预警处理
                    getProblemId("4", "");
                    break;
                case HandleActivity.LABEL_VALUE_OTHER://其他处理
                    getProblemId("5", "");
                    break;
            }
        }
    }

    /**
     * 告警处理
     */
    public void commitHandle() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            String temp = getView().getType();
            if (temp != null) {
                paramsMap.put("typeid", temp);//消息类型 1：主机 1：水系统
            }
            temp = getView().getOid();
            if (temp != null) {
                paramsMap.put("oid", temp);//消息 ID
            }
            temp = getView().getPid();
            if (temp != null) {
                paramsMap.put("pid", temp);//统计 ID
            }
            temp = getView().getDeviceID();
            if (temp != null) {
                paramsMap.put("deviceid", temp);//设备ID
            }
            temp = getView().getBuildId();
            if (temp != null) {
                paramsMap.put("buildid", temp);// 建筑 ID
            }
            temp = getView().getUnitId();
            if (temp != null) {
                paramsMap.put("unitid", temp);// 建筑 ID
            }
            temp = getView().getAffairID();
            if (temp != null) {
                paramsMap.put("affairid", temp);// 建筑 ID
            }
            temp = getView().getDate();
            if (temp != null) {
                paramsMap.put("date", temp);//上报时间
            }
            paramsMap.put("handlemanid", CommonInfo.getInstance().getUserInfo().getUserid());//处理人 ID
            paramsMap.put("handletypeid", getView().getHandle());// 原因类型
            temp = getView().getRemark();
            if (temp != null && temp.length() > 0) {
                paramsMap.put("handleremark", temp);//处理备注
            }
            XHttpUtils.post(context, paramsMap, ConstHost.WARN_HANLDE_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    if (getView() != null) {
                        if (!TextUtils.isEmpty(result)) {
                            if (TextUtils.equals(result, "0")) {
                                XToastUtil.showToast("处理失败");
                            } else {
                                XToastUtil.showToast("处理成功");
                                //更新上一个页面
                                EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.FireActivity, ItemEvent.ACTION.refreshing));
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

    /**
     * 通过设备id获取问题id
     *
     * @param setId 设备id
     */
    private void getProblemId(final String type, final String setId) {
        EntryFactory.getProblemId(context, type, setId, new EntryFactory.OnResultProblemListener() {
            @Override
            public void onResult(List<ProblemEntity> mList) {
                if (mList.size() > 0) {
                    for (ProblemEntity entity : mList) {
                        if (entity != null && TextUtils.equals(entity.getTypeid(), type)) {
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
            }
        }
    }
}
