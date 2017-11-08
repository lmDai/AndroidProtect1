package com.cqyanyu.backing.ui.net;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.utils.XAppUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.io.File;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 网络请求
 * Created by Administrator on 2017/7/13.
 */
public class XHttpUtils {

    //延迟时间(ms)
    private static int DELAY_TIME = 500;

    /**
     * 网络请求
     *
     * @param context
     * @param paramsMap
     * @param url
     * @param dialog
     * @param callback
     */
    private static void httpPostRequest(final Context context, final ParamsMap paramsMap, final String url, final Dialog dialog, final XICallback callback) {
        final RequestParams requestParams = initParams(url, paramsMap);
        if (dialog != null) dialog.show();
        x.http().request(HttpMethod.GET, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                if (X.DEBUG) {
                    /**打印请求日志*/
                    if (requestParams != null) {
                        XLog.i("请求地址：" + url + "\n" + "请求参数：" + requestParams.toString() + "\n" + "请求结果：" + result);
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null) dialog.dismiss();
                        /**处理请求结果*/
                        parseData(context, result, callback);
                    }
                }, DELAY_TIME);
            }

            @Override
            public void onError(final Throwable ex, boolean isOnCallback) {
                if (X.DEBUG) {
                    /**打印请求日志*/
                    if (requestParams != null) {
                        XLog.i("请求地址：" + url + "\n" + "请求参数：" + requestParams.toString() + "\n" + "请求结果：" + ex.toString());
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null) dialog.dismiss();
                        XToastUtil.showToast("网络错误");
                        callback.onFail(ex.toString());
                    }
                }, DELAY_TIME);
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                XLog.e("错误信息：网络请求取消!");
                callback.onFail("网络请求取消!");
            }

            @Override
            public void onFinished() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        callback.onFinished();
                    }
                }, DELAY_TIME);

            }
        });
    }

    /**
     * 初始化请求参数
     *
     * @param url
     * @param paramsMap
     * @return
     */
    private static RequestParams initParams(String url, ParamsMap paramsMap) {
        /**创建请求参数*/
        RequestParams requestParams = new RequestParams(url);
        /**设置使用cookie*/
        requestParams.setUseCookie(true);
        /**设置请求的编码格式*/
        requestParams.setCharset("utf-8");
        /**设置请求方式*/
        requestParams.setMethod(HttpMethod.POST);
        /**设置超时时长*/
        requestParams.setConnectTimeout(10 * 1000);
        /**设置请求参数*/
        if (null != paramsMap && paramsMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                requestParams.addParameter(key, value);
            }
        }
        return requestParams;
    }

    /**
     * post请求
     *
     * @param context
     * @param paramsMap
     * @param url
     * @param dialog
     * @param callback
     */
    public static void post(final Context context, ParamsMap paramsMap, String url, Dialog dialog, final XICallback callback) {
        httpPostRequest(context, paramsMap, url, dialog, callback);
    }

    /**
     * post请求
     *
     * @param context
     * @param paramsMap
     * @param url
     * @param callback
     */
    public static void post(final Context context, ParamsMap paramsMap, String url, final XICallback callback) {
        httpPostRequest(context, paramsMap, url, null, callback);
    }

    /**
     * 解析数据
     *
     * @param context
     * @param result
     * @param callback
     */
    private static void parseData(Context context, String result, final XICallback callback) {
        if (TextUtils.equals(result, "{code=5}") || TextUtils.equals(result, "{\"code\":5}")) {
            //重新登录
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOGIN)
            );
        } else {
            if (callback instanceof XICallbackList) {
                XICallbackList xCallback = (XICallbackList) callback;
                try {
                    List list = JSON.parseArray(result, xCallback.getClazz());
                    xCallback.onSuccess(list);
                } catch (Exception e) {
                    XLog.e("数据解析错误!");
                    callback.onFail("数据解析错误!");
                    e.getStackTrace();
                }
            } else if (callback instanceof XICallbackEntity) {
                XICallbackEntity xCallback = (XICallbackEntity) callback;
                try {
                    Object object = JSON.parseObject(result, xCallback.getClazz());
                    xCallback.onSuccess(object);
                } catch (Exception e) {
                    XLog.e("数据解析错误!");
                    callback.onFail("数据解析错误!");
                    e.getStackTrace();
                }
            } else if (callback instanceof XICallbackString) {
                XICallbackString xCallback = (XICallbackString) callback;
                xCallback.onSuccess(result);
            }
        }
    }

    /**
     * 获取带有请求Cookie
     *
     * @param url
     * @return
     */
    public static GlideUrl getGlideUrl(String url) {
        /**获取cookies*/
        DbCookieStore instance = DbCookieStore.INSTANCE;
        final List<HttpCookie> cookies = instance.getCookies();
        if (cookies != null && cookies.size() > 0) {
            return new GlideUrl(url, new LazyHeaders.Builder()
                    .setHeader("Cookie", "JSESSIONID=" + cookies.get(0).getValue())
                    .build());
        }
        return new GlideUrl(url);
    }

    /**
     * 文件下载
     *
     * @param context  环境
     * @param url      网络文件地址
     * @param endName  文件后缀名
     * @param callback 回调
     */
    public static void onDownloadFile(final Activity context, final String url, String endName, XIDownCallback callback) {
        if (downPermission(context, true)) {
            try {
                if (!TextUtils.isEmpty(url)) {
                    /**url中的汉字转译*/
                    String fileNames = url;
                    try {
                        int i = url.lastIndexOf("/");
                        String decode = URLEncoder.encode(url.substring(i + 1, url.length()), "UTF-8"); //URLDecoder.decode(url, "utf-8");
                        fileNames = url.substring(0, i + 1) + decode;
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    /**获取真实的网络地址*/
                    String wordUrl = CommonInfo.getInstance().getWordUrl(fileNames);
                    if (!TextUtils.isEmpty(wordUrl)) {
                        if (wordUrl.startsWith("/storage")) {
                            /**加载本地文件*/
                            if (callback != null) callback.onSuccess(wordUrl);
                        } else if (wordUrl.startsWith("http")) {
                            String imagePath = InfoManger.getInstance().getImagePath(context, wordUrl);
                            if (TextUtils.isEmpty(imagePath) || !new File(imagePath).exists()) {
                                /**本地不曾记录过的文件或者本地已经不存在此文件*/
                                String fileKeep = XAppUtils.instance().getFileKeep(context);
                                String timeStamp = XAppUtils.instance().getTimeStampMS();
                                /**加载网络文件（使用先下载后加载的方式）*/
                                String names = fileNames.substring(fileNames.lastIndexOf("/") + 1, fileNames.length());
                                if (!TextUtils.isEmpty(fileKeep) && !TextUtils.isEmpty(timeStamp) && new File(fileKeep).exists()) {
                                    downloadFile(context, wordUrl, fileKeep + "/down_" + names, callback);
                                } else {
                                    /**只能加载网络文件*/
                                    if (callback != null) callback.onFail("system error");
                                }
                            } else {
                                /**本地曾记录过的文件,而且现在还保存在本地*/
                                callback.onSuccess(imagePath);
                            }
                        }
                    } else {
                        /**只能加载网络文件*/
                        if (callback != null) callback.onFail("url is empty");
                    }
                }
            } catch (Exception e) {
                e.getStackTrace();
                /**只能加载网络文件*/
                if (callback != null) callback.onFail(e.getMessage());
            }
        }
    }

    /**
     * 文件现在
     *
     * @param context  环境
     * @param url      网络文件地址
     * @param path     本地文件地址
     * @param callback 回调
     */
    private static void downloadFile(final Context context, final String url, final String path, final XIDownCallback callback) {
        final RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(path);
        XLog.i("downloadFile：" + url + "---->" + path);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
                XLog.i("downloadFile---->onWaiting");
            }

            @Override
            public void onStarted() {
                XLog.i("downloadFile---->onStarted");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                XLog.i("downloadFile---->onLoading:" + current + "/" + total);
            }

            @Override
            public void onSuccess(File result) {
                XLog.i("downloadFile---->onSuccess" + result.getAbsolutePath());
                InfoManger.getInstance().putImageUrl(context, url, result.getAbsolutePath());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XLog.i("downloadFile---->onError");
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                XLog.i("downloadFile---->onCancelled");
            }

            @Override
            public void onFinished() {
                XLog.i("downloadFile---->onFinished");
                if (callback != null) callback.onSuccess(path);
            }
        });
    }

    /**
     * 是否用户读写权限，没有权限获取权限
     *
     * @param activity  Activity
     * @param isRequest 是否请求权限
     * @return 是否拥有权限
     */
    public static boolean downPermission(Activity activity, boolean isRequest) {
        if (Build.VERSION.SDK_INT > 22) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                XToastUtil.showToast("请授予该权限！否则将无法使用该功能");
                if (isRequest) {
                    activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 0xedc);
                }
                return false;
            }
        }
        return true;
    }
}
