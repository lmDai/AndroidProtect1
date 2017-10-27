package com.pictureselect.camerapicker.camera;

/**
 * Created by Lin on 2016/12/20.
 */

import java.io.Serializable;

/**
 * 模式
 */
public enum Mode implements Serializable {
    /**
     * 系统相机
     */SYSTEM_CAMERA,
    /**
     * 系统图库
     */SYSTEM_IMGCAPTRUE,
    /**
     * 类似微信图库
     */AS_WEIXIN_IMGCAPTRUE
}