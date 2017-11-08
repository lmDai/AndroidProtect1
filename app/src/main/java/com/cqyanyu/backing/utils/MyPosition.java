package com.cqyanyu.backing.utils;

import com.cqyanyu.backing.CommonInfo;

/**
 * 格式化位置信息
 * Created by Administrator on 2017/4/21.
 */
public class MyPosition {

    public static String formatPosition(String unitstr, String buildstr, String position) {
        boolean isnode = CommonInfo.getInstance().getUserInfo().isnode();
        String mPosition = "";
        if (!isnode && (unitstr != null && unitstr.length() != 0)) {
            mPosition += unitstr + "@";
        }
        if (buildstr != null && buildstr.length() != 0) {
            mPosition += buildstr;
        }

        if (position != null && position.length() != 0) {
            if (mPosition != null && mPosition.length() > 0) {
                mPosition += "@";
            }
            mPosition += position;
        }
        return mPosition;
    }
}
