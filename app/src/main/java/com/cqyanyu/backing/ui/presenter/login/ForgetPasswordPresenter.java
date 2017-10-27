package com.cqyanyu.backing.ui.presenter.login;

import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.mvpview.login.ForgetPasswordView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

/**
 * 忘记密码逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class ForgetPasswordPresenter extends BasePresenter<ForgetPasswordView> {

    public void requestCode() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("", getView().getTel());
            //TODO 忘记密码获取验证码接口
            XHttpUtils.post(context, paramsMap, "", new XICallbackString() {
                @Override
                public void onSuccess(String result) {

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

    public void resetPassword() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("", getView().getTel());
            //TODO 重置密码接口
            XHttpUtils.post(context, paramsMap, "", NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                @Override
                public void onSuccess(String result) {

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
