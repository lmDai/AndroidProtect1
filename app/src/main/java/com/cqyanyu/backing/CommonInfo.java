package com.cqyanyu.backing;

import android.content.Context;
import android.text.TextUtils;

import com.cqyanyu.backing.ui.entity.login.UserInfo;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.bean.UserInfoEntity;

/**
 * 常用数据信息
 * Created by Administrator on 2017/5/16 0016.
 */

public class CommonInfo {

    private static CommonInfo commonInfo;

    private Context context;

    private CommonInfo(Context context) {
        this.context = context;
    }

    public static CommonInfo init(Context context) {
        if (commonInfo == null)
            commonInfo = new CommonInfo(context);
        return commonInfo;
    }

    public static CommonInfo getInstance() {
        return commonInfo;
    }

    /**
     * 获取图片的真实地址
     *
     * @param url 当前地址
     * @return 真实地址
     */
    public String getImageUrl(String url) {
        return TextUtils.isEmpty(url) ? "" : ((url.startsWith("http") || url.startsWith("/storage")) ? url : (ConstHost.IMAGE + url));
    }

    /**
     * 获取pdf文档的真实地址
     *
     * @param url 当前地址
     * @return 真实地址
     */
    public String getWordUrl(String url) {
        return TextUtils.isEmpty(url) ? "" : ((url.startsWith("http") || url.startsWith("/storage")) ? url : (ConstHost.WORD + url));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public UserInfo getUserInfo() {
        UserInfo info = null;
        UserInfoEntity infoEntity = X.user().getUserInfo();
        if (infoEntity != null && infoEntity instanceof UserInfo) {
            info = (UserInfo) infoEntity;
        }
        if (info == null)
            info = new UserInfo();
        return info;
    }
}
