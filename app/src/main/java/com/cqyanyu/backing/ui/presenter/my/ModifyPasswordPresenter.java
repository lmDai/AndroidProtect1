package com.cqyanyu.backing.ui.presenter.my;

import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.mvpview.my.ModifyPasswordView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

/**
 * 修改密码
 * Created by Administrator on 2017/7/11.
 */

public class ModifyPasswordPresenter extends BasePresenter<ModifyPasswordView> {
    //修改密码请求
    public void requestChangePwd() {
        if (getView() != null) {
            if (!TextUtils.isEmpty(getView().getOldPwd())) {
                if (!TextUtils.isEmpty(getView().getNewPwd())) {
                    if (!TextUtils.isEmpty(getView().getConPwd())) {
                        if (getView().getNewPwd().equals(getView().getConPwd())) {
                            ParamsMap paramsMap = new ParamsMap();
                            paramsMap.put("oid", CommonInfo.getInstance().getUserInfo().getUserid());
                            // paramsMap.put("oid", InfoManger.getInstance().getMyUserInfo(context).getOid());
                            paramsMap.put("name", getView().getUserStr());
                            paramsMap.put("oldpwd", getView().getOldPwd());
                            paramsMap.put("newpwd", getView().getNewPwd());
                            XHttpUtils.post(context, paramsMap, ConstHost.MODIFY_USER_PASSWORD_URL, new XICallbackString() {
                                @Override
                                public void onSuccess(String result) {
                                    if (TextUtils.equals(result, "修改成功!")) {
                                        getView().requestSuccess();
                                    }
                                    XToastUtil.showToast(result);
                                }

                                @Override
                                public void onFail(String msg) {
                                    XToastUtil.showToast(msg);
                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        } else {
                            XToastUtil.showToast("两次密码输入不一致");
                        }
                    } else {
                        XToastUtil.showToast("请输入新密码");
                    }
                } else {
                    XToastUtil.showToast("请输入新密码!");
                }
            } else {
                XToastUtil.showToast("请输入原密码!");
            }
        }
    }
}
