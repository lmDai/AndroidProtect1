package com.cqyanyu.backing.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2017/7/6.
 */

public class XMetaUtil {
    private final static XMetaUtil instance = new XMetaUtil();
    private Bundle metaData;

    private XMetaUtil() {
    }

    public static XMetaUtil Instance() {
        return instance;
    }

    public void init(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("yykj", "获取AndroidManifest错误", e);
        }
        if (appInfo != null) {
            //获取AndroidManifest的数据
            metaData = appInfo.metaData;
        }
    }

    public int getInt(String key) {
        return metaData.getInt(key);
    }

    public int getInt(String key, int defaultValue) {
        return metaData.getInt(key, defaultValue);
    }

    public String getString(String key) {
        return metaData.getString(key);
    }

    public String getString(String key, String defaultValue) {
        return metaData.getString(key, defaultValue);
    }
}
