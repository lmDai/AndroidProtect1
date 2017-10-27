package com.cqyanyu.backing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Administrator on 2017/8/8.
 */

/**
 * 作者：Yzp on 2017-03-20 15:28
 * 邮箱：15111424807@163.com
 * QQ: 486492302
 * 用户账户信息保存工具类
 */
public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME_USER = "ADD_USER";
    public static final String FILE_NAME_SET = "ADD_SET";
    public static final String FILE_NAME_UNIT = "ADD_UNIT";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object, String FILE_NAME) {
        if (object != null) {
            String type = object.getClass().getSimpleName();
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            if ("String".equals(type)) {
                editor.putString(key, (String) object);
            } else if ("Integer".equals(type)) {
                editor.putInt(key, (Integer) object);
            } else if ("Boolean".equals(type)) {
                editor.putBoolean(key, (Boolean) object);
            } else if ("Float".equals(type)) {
                editor.putFloat(key, (Float) object);
            } else if ("Long".equals(type)) {
                editor.putLong(key, (Long) object);
            }

            editor.commit();
        }
    }

    public static void setParam(Context context, String key, String object, String FILE_NAME,int mode) {
        if (object != null) {
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME, mode);
            SharedPreferences.Editor editor = sp.edit();
            String history = sp.getString(key, "");
            //先判断是否已经存过一样的文字
            if (!TextUtils.isEmpty(object) && !history.contains(object + ",")) {
                StringBuilder sb = new StringBuilder(history);
                sb.insert(0, object + ",");//插入到第一个,代表最新
                sp.edit().putString(key, sb.toString()).apply();
            }
            editor.commit();
        }
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject, String FILE_NAME) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context, String FILE_NAME) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    /**
     * 清除指定数据
     *
     * @param context
     */
    public static void clearAll(Context context, String FILE_NAME) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("定义的键名");
        editor.commit();
    }

}
