package com.cqyanyu.backing.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * app 信息管理
 * Created by Administrator on 2017/4/11 0011.
 */
public class XAppUtils {
    private static XAppUtils instance;
    //储存根目录
    private String PhoneRoot;
    //应用名称
    private String AppName;
    //应用的根目录
    private String AppRoot;
    //下载图片保存目录
    private String ImageKeep;
    //下载文件保存目录
    private String FileKeep;

    private XAppUtils() {

    }

    /**
     * 获取App工具的实例
     *
     * @return 实例
     */
    public static XAppUtils instance() {
        if (instance == null) {
            instance = new XAppUtils();
        }
        return instance;
    }

    /**
     * 初始化工具
     *
     * @param context MyApplication
     */
    private void init(Context context) {
        initAppStorage(context);
    }

    /**
     * 初始化储存卡
     *
     * @param context MyApplication
     */
    private void initAppStorage(Context context) {
        if (AppName == null) {
            /**获取应用名称*/
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
                AppName = (String) packageManager.getApplicationLabel(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {
                AppName = null;
            }
        }
        if (AppName != null && TextUtils.isEmpty(AppRoot)) {
            File root;
            /**SD卡是否能用*/
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                root = new File(context.getApplicationContext().getFilesDir().getAbsolutePath());
            } else {
                root = Environment.getExternalStorageDirectory();
            }
            PhoneRoot = root.getPath();

            /**创建应用的根目录*/
            File downloadDir = new File(root.getAbsolutePath() + "/" + AppName);
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }
            AppRoot = downloadDir.getPath();

            /**创建图片保存目录*/
            if (!TextUtils.isEmpty(AppRoot)) {
                File photoFile = new File(AppRoot + "/image");
                if (!photoFile.exists()) {
                    photoFile.mkdirs();
                }
                ImageKeep = photoFile.getPath();
            }

            /**创建文件保存目录*/
            if (!TextUtils.isEmpty(AppRoot)) {
                File fileFile = new File(AppRoot + "/file");
                if (!fileFile.exists()) {
                    fileFile.mkdirs();
                }
                FileKeep = fileFile.getPath();
            }
        }
    }

    /**
     * 获取手机根目录
     *
     * @param context Context
     * @return 手机根目录
     */
    public String getPhoneRoot(Context context) {
        if (TextUtils.isEmpty(PhoneRoot)) init(context);
        return PhoneRoot;
    }

    /**
     * 获取图片保存目录
     *
     * @param context Context
     * @return 图片保存目录
     */
    public String getImageKeep(Context context) {
        if (TextUtils.isEmpty(ImageKeep) || !new File(ImageKeep).exists()) init(context);
        return ImageKeep;
    }

    /**
     * 获取文件保存目录
     *
     * @param context Context
     * @return 文件保存目录
     */
    public String getFileKeep(Context context) {
        if (TextUtils.isEmpty(FileKeep) || !new File(FileKeep).exists()) init(context);
        return FileKeep;
    }

    /**
     * 获取应用名称
     *
     * @param context Context
     * @return 应用名称
     */
    public String getAppName(Context context) {
        if (TextUtils.isEmpty(AppName)) init(context);
        return AppName;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    public String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssMS", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());// 系统当前时间
        return formatter.format(curDate);
    }

    /**
     * 获取时间戳（秒）
     *
     * @return 时间戳
     */
    public String getTimeStampMS() {
        return "" + new Date().getTime();
    }
}
