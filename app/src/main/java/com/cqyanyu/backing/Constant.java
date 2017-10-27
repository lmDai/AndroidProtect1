package com.cqyanyu.backing;

import android.text.TextUtils;

import com.cqyanyu.mvpframework.utils.XToastUtil;

/**
 * 常量类
 * Created by Administrator on 2017/5/16 0016.
 */
public class Constant {
    public static final String CODE = "code";
    public static final String MSG = "msg";
    private static final int code = 200;

    public static final String KEY_USERNAME = "username";
    public static final String KEY_REMEMBER = "remember_password";
    public static final int PushServer_CMD_Push = 143;
    public static final int PushServer_Type_Login = 129;
    public static final int PushServer_Type_Hearbeat = 130;
    public static final int PushServer_Type_UserLogout = 131;
    public static final int PushServer_Type_Alarm = 133;
    public static final int PushServer_Type_Duty = 132;
    public static final int PushServer_Type_Inspense = 134;//任务单
    public static final int PushServer_Type_ReportPosition=135;
    /**
     * 是否是有效的网络请求码
     *
     * @return true：请求成功
     */
    public static boolean isValidCode(int codeValue, String msg) {
        if (codeValue == code) {
            if (!TextUtils.equals(msg, "成功")) {
                XToastUtil.showToast(msg);
            }
            return true;
        } else {
            XToastUtil.showToast(msg);
            return false;
        }
    }
}
