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

    public static final int STATUS_FORCE_KILLED = -1;//应用放在后台被强杀了
    public static final int STATUS_NORMAL = 2; //APP正常态//intent到MainActivity区分跳转目的
    public static final String KEY_HOME_ACTION = "key_home_action";//返回到主页面
    public static final int ACTION_BACK_TO_HOME = 0;//默认值
    public static final int ACTION_RESTART_APP = 1;//被强杀

    public static final String KEY_USERNAME = "username";
    public static final String KEY_REMEMBER = "remember_password";
    public static final int PushServer_CMD_Push = 143;
    public static final int PushServer_Type_Login = 129;
    public static final int PushServer_Type_Hearbeat = 130;
    public static final int PushServer_Type_UserLogout = 131;
    public static final int PushServer_Type_Alarm = 133;
    public static final int PushServer_Type_Duty = 132;
    public static final int PushServer_Type_Inspense = 134;//任务单
    public static final int PushServer_Type_ReportPosition = 135;
    public static final int PushServer_Type_Inspection = 136;//巡检提交
    public static final int PushServer_Type_DeviceUpdate = 137;//设备状态信息改变
    public static final int PushServer_Type_NoticeHandleAlarm = 138;//通知处理告警消息

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
