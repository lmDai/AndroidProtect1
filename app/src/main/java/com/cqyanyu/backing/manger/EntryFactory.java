package com.cqyanyu.backing.manger;

import android.content.Context;

import com.cqyanyu.backing.ui.entity.home.ProblemEntity;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class EntryFactory {
    /**
     * 获取问题
     *
     * @param type
     */
    public static void getProblemId(Context context, String type, final String setId, final OnResultProblemListener listener) {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", type);
        List<ProblemEntity> mList = InfoManger.getInstance().getProblemOfId(context);
        if (listener != null) listener.onResult(mList);
//
//        XHttpUtils.post(context, paramsMap, ConstHost.GET_PROBLEM_URL, NetDialogUtil.showLoadDialog(context, "正在获取问题..."), new XICallbackString() {
//            @Override
//            public void onSuccess(String result) {
//                List<ProblemEntity> mList = JSONArray.parseArray(result, ProblemEntity.class);
//                if (mList != null && mList.size() > 0) {
//                    if (!TextUtils.isEmpty(setId)) {
//                        for (ProblemEntity data : mList) {
//                            if (TextUtils.equals(data.getDevicetypeid(), setId)) {
//                                if (listener != null) listener.onResult(data.getProblempid());
//                            }
//                        }
//                    } else {
//                        if (listener != null) listener.onResult(mList.get(0).getProblempid());
//                    }
//                }
//            }
//
//            @Override
//            public void onFail(String msg) {
//                XLog.i(msg + "onFailed");
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    public interface OnResultProblemIdListener {
        void onResult(String problemId);
    }

    public interface OnResultProblemListener {
        void onResult(List<ProblemEntity> mList);
    }
}
