package com.cqyanyu.backing.ui.presenter.login;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.Constant;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.login.UserInfo;
import com.cqyanyu.backing.ui.mvpview.login.LoginView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.backing.utils.TerminalUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XPreferenceUtil;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

/**
 * 登录逻辑处理类
 * Created by Administrator on 2017/7/6.
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    //初始化
    public void init(boolean autoLogin) {
        if (getView() != null) {
            /**自动登录*/
            if (XPreferenceUtil.getInstance().getBoolean(Constant.KEY_REMEMBER) && autoLogin) {
                /**用户选择记住密码就让用户自动登录*/
                String phone = XPreferenceUtil.getInstance().getString(Constant.KEY_USERNAME);
                String password = XPreferenceUtil.getInstance().getString(phone);
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
                    /**登录*/
                    login(phone, password);
                }
            }
        }
    }

    //登录请求
    public void requestLoin() {
        if (getView() != null) {
            if (!TextUtils.isEmpty(getView().getUsername())) {
                if (!TextUtils.isEmpty(getView().getPassword())) {
                    /**登录*/
                    login(getView().getUsername(), getView().getPassword());
                } else {
                    XToastUtil.showToast("请输入密码!");
                }
            } else {
                XToastUtil.showToast("请输入账户!");
            }
        }
    }

    /**
     * 登录请求
     *
     * @param phone    手机号
     * @param password 密码
     */
    private void login(final String phone, final String password) {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("loginname", phone);//用户登录名或手机号
            paramsMap.put("loginpwd", password);//用户登录密

            paramsMap.put("terminalname", TerminalUtils.getTerminalName());//用户终端型号
            paramsMap.put("terminalcode", TerminalUtils.getDeviceUnique());//用户终端唯一码
            paramsMap.put("terminaltype", TerminalUtils.getTerminalType());//用户终端类型

//            paramsMap.put("terminalname", "vivo[vivo X5M]");//用户终端型号
//            paramsMap.put("terminalcode", "a22ee08aa22ee08a");//用户终端唯一码
//            paramsMap.put("terminaltype", "1");//用户终端类型
//
//            paramsMap.put("terminalname", "Xiaomi[MI 2SC]");//用户终端型号
//            paramsMap.put("terminalcode", "6305795063057950");//用户终端唯一码
//            paramsMap.put("terminaltype", "1");//用户终端类型

//            paramsMap.put("terminalname", "iPhone[iPhone5,3]");//用户终端型号
//            paramsMap.put("terminalcode", "AEBB3CC8-5D58-43D2-96F6-5A08F4A859D4");//用户终端唯一码
//            paramsMap.put("terminaltype", "2");//用户终端类型
            XHttpUtils.post(context, paramsMap, ConstHost.LOGIN_URL, NetDialogUtil.showLoadDialog(context, R.string.text_logining), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    if (getView() != null) {
                        //数据解析
                        UserInfo userInfo = JSON.parseObject(result, UserInfo.class);
                        if (userInfo != null) {
                            if (userInfo.isResult()) {
                                //保存用户信息
                                userInfo.setPhone(phone);
                                userInfo.setPassword(password);
                                X.user().setUserInfo(userInfo);
                                //登录成功
                                getView().requestSuccess();
                            } else {
                                //登录失败
                                getView().loginFail("连接失败");
                            }
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    getView().loginFail("连接失败");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }
}
