package com.cqyanyu.backing.ui.presenter.my;

import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.mvpview.my.MyView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 我的逻辑处理类
 * Created by Administrator on 2017/7/7.
 */
public class MyPresenter extends BasePresenter<MyView> {

    /**
     * 退出登录
     */
    public void requestSignOut() {
        ParamsMap paramsMap = new ParamsMap();
        XHttpUtils.post(context, paramsMap, ConstHost.EXIT_LOGIN_URL, new XICallbackString() {
            @Override
            public void onSuccess(String result) {
                //退出成功
                if (TextUtils.equals(result, "0")) {
                    getView().requestSuccess();
                } else {
                    XToastUtil.showToast("退出失败，请稍后再试！");
                }
            }

            @Override
            public void onFail(String msg) {
                XToastUtil.showToast("网络状况不佳，请稍后再试！");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 从网络中获取用户信息
     */
    public void getMyUserInfoOnNet() {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("userstr", CommonInfo.getInstance().getUserInfo().getPhone());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_USER_URL, new XICallbackList<UserManageEntity>() {
                @Override
                public void onSuccess(List<UserManageEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        UserManageEntity entity = mList.get(0);
                        if (entity != null) {
                            //保存本单位信息
                            getView().setNickName(entity.getUserstr());
                            getView().setAccount(entity.getPhone());
                            getView().setUnitName(entity.getUnitstr());
                            getView().setPhone(entity.getPhone());

                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("用户信息获取失败!");
                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return UserManageEntity.class;
                }
            });
        }
    }
}
