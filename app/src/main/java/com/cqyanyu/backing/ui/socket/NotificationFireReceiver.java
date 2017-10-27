package com.cqyanyu.backing.ui.socket;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.event.EventBadgeItem;
import com.cqyanyu.backing.ui.activity.login.MainActivity;
import com.cqyanyu.backing.ui.entity.login.UserInfo;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackEntity;
import com.cqyanyu.backing.ui.socket.entity.NotificationEntity;
import com.cqyanyu.backing.utils.TerminalUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 通知的点击监听
 * Created by Administrator on 2017/8/3.
 */
public class NotificationFireReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        reLogin(context);
    }

    /**
     * 重新登录
     *
     * @param context 环境
     */
    private void reLogin(final Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            final String phone = CommonInfo.getInstance().getUserInfo().getPhone();
            final String password = CommonInfo.getInstance().getUserInfo().getPassword();
            paramsMap.put("loginname", phone);//用户登录名或手机号
            paramsMap.put("loginpwd", password);//用户登录密码
            paramsMap.put("terminalname", TerminalUtils.getTerminalName());//用户终端型号
            paramsMap.put("terminalcode", TerminalUtils.getDeviceUnique());//用户终端唯一码
            paramsMap.put("terminaltype", TerminalUtils.getTerminalType());//用户终端类型
            XHttpUtils.post(context, paramsMap, ConstHost.LOGIN_URL, new XICallbackEntity<UserInfo>() {
                @Override
                public void onSuccess(UserInfo entity) {
                    if (entity != null) {
                        if (entity.isResult()) {
                            //保存用户信息
                            entity.setPhone(phone);
                            entity.setPassword(password);
                            X.user().setUserInfo(entity);
                            try {
                                EventBadgeItem.getInstance().post("1");
                                EventBus.getDefault().postSticky(new NotificationEntity(1));
                                context.startActivity(new Intent(context, MainActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                );
                            } catch (Exception e) {
                                e.getStackTrace();
                            }
                            return;
                        }
                    }
                    XToastUtil.showToast("重新登录失败");
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("重新登录失败!");
                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return UserInfo.class;
                }
            });
        }
    }

    /**
     * activity判断当前activity是不是前台显示的。
     *
     * @param context   环境
     * @param className 路径名称
     * @return d
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
