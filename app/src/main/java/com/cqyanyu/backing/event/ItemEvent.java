package com.cqyanyu.backing.event;

import android.os.Bundle;

/**
 * 信息通知
 * Created by Administrator on 2017/7/5 0005.
 */
public class ItemEvent {

    private ACTIVITY activity;
    private ACTION action;
    private Bundle bundle;

    public ItemEvent(ACTIVITY activity, ACTION action) {
        this.activity = activity;
        this.action = action;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public ACTIVITY getActivity() {
        return activity;
    }

    public void setActivity(ACTIVITY activity) {
        this.activity = activity;
    }

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
    }

    /**
     * 页面
     */
    public enum ACTIVITY {
        SystemManagementFragment,
        WarnListFragment,
        FireActivity,
        DutyStatusActivity
    }

    /**
     * 动作
     */
    public enum ACTION {
        refreshing
    }
}
