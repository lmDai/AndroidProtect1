package com.cqyanyu.backing.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 调用相机/单选相册工具类
 * Created by Administrator on 2017/5/30 0030.
 */
public class CameraUtil {
    private Activity activity; //环境(必须在Activity当中调用)
    private CallCameraAlbum call;//调用相机相册
    private OnResultListener listener; //图片路径返回监听
    private int mRequestCode; //必需的请求码
    private boolean isSecondRequestPermissions;//是否第二次请求权限
    private boolean isCallCamera;//是否调用相机
    private boolean isCallAlbum;//是否调用相册
    private boolean isCompress; //是否压缩图片(默认是不压缩)
    private boolean isCrop; //是否裁剪图片(默认是不压缩)
    private int compressWidth;//压缩图片的宽
    private int compressHeight;//压缩图片的高
    private int compressSize;//压缩图片的大小
    private int compressQuality;//压缩图片的质量
    private int outputX;//裁剪图片输出的水平尺寸
    private int outputY;//裁剪图片输出的垂直尺寸
    private Dialog PermissionDialog;//强制获取权限弹出框
    private Dialog ChoiceDialog; //选择弹出框

    /**
     * 构造方法
     *
     * @param context Activity
     */
    public CameraUtil(Activity context) {
        /**初始化环境*/
        activity = context;
        /**请求权限*/
        isPermission(true);
        /**初始化调用相机相册*/
        call = new CallCameraAlbum();
        /**初始化权限dialog*/
        initPermissionDialog(0xff444444, 0xff9999ff, 0xffffffff, 0xffdddddd);
        /**初始化选择dialog*/
        initChoiceDialog(0xff444444, 0xff9999ff, 0xffffffff, 0xffdddddd);
    }

    /**
     * 单独调用相机
     *
     * @return CameraUtil
     */
    public CameraUtil callCamera() {
        isCallCamera = true;
        isCallAlbum = false;
        initParams();
        return this;
    }

    /**
     * 单独调用相册
     *
     * @return CameraUtil
     */
    public CameraUtil callAlbum() {
        isCallCamera = false;
        isCallAlbum = true;
        initParams();
        return this;
    }

    /**
     * 调用Dialog选择
     *
     * @return CameraUtil
     */
    public CameraUtil callDialog() {
        isCallCamera = true;
        isCallAlbum = true;
        initParams();
        return this;
    }

    /**
     * 设置是否压缩(默认是不压缩)
     *
     * @param compress 是否压缩
     * @return CameraUtil
     */
    public CameraUtil setCompress(boolean compress) {
        isCompress = compress;
        return this;
    }

    /**
     * 设置是否裁剪(默认是不裁剪)
     *
     * @param crop 是否裁剪
     * @return CameraUtil
     */
    public CameraUtil setCrop(boolean crop) {
        isCrop = crop;
        return this;
    }

    /**
     * 设置压缩参数
     *
     * @param width   宽像素
     * @param height  高像素
     * @param size    压缩大小
     * @param quality 压缩质量
     * @return CameraUtil
     */
    public CameraUtil setCompressParams(int width, int height, int size, int quality) {
        compressWidth = width;
        compressHeight = height;
        compressSize = size;
        compressQuality = quality;
        return this;
    }

    /**
     * 设置裁剪参数
     *
     * @param outputX 裁剪输出的水平宽度
     * @param outputY 裁剪输出的垂直宽度
     * @return CameraUtil
     */
    public CameraUtil setCropParams(int outputX, int outputY) {
        this.outputX = outputX;
        this.outputY = outputY;
        return this;
    }

    /**
     * 设置选择Dialog的样式
     *
     * @param textNormalColor       选择弹出框按钮文字正常颜色
     * @param textPressColor        选择弹出框按钮文字按压颜色
     * @param backgroundNormalColor 选择弹出框按钮背景正常颜色
     * @param backgroundPressColor  选择弹出框按钮背景按压颜色
     * @return CameraUtil
     */
    public CameraUtil setChioceDialogStyle(int textNormalColor, int textPressColor, int backgroundNormalColor, int backgroundPressColor) {
        initChoiceDialog(textNormalColor, textPressColor, backgroundNormalColor, backgroundPressColor);
        return this;
    }

    /**
     * 设置权限Dialog的样式
     *
     * @param textNormalColor       选择弹出框按钮文字正常颜色
     * @param textPressColor        选择弹出框按钮文字按压颜色
     * @param backgroundNormalColor 选择弹出框按钮背景正常颜色
     * @param backgroundPressColor  选择弹出框按钮背景按压颜色
     * @return CameraUtil
     */
    public CameraUtil setPermissionDialogStyle(int textNormalColor, int textPressColor, int backgroundNormalColor, int backgroundPressColor) {
        initPermissionDialog(textNormalColor, textPressColor, backgroundNormalColor, backgroundPressColor);
        return this;
    }

    /**
     * 开始
     *
     * @param requestCode 必需的请求码
     */
    public void start(int requestCode) {
        if (call != null) {
            mRequestCode = requestCode;
            if (isCallCamera && isCallAlbum) {
                /**调用Dialog*/
                if (ChoiceDialog != null &&
                        !ChoiceDialog.isShowing()) {
                    ChoiceDialog.show();
                }
            } else if (isCallCamera) {
                /**单独调用相机*/
                call.camera();
            } else if (isCallAlbum) {
                /**单独调用相册*/
                call.album();
            }
        }
    }

    /**
     * 结果返回处理(必须到Activity的onActivityResult方法中去调用)
     *
     * @param requestCode 来源于Activity的请求码
     * @param resultCode  来源于Activity的返回码
     * @param data        来源于Activity的请求值
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (call != null) {
            /**判断是否剪切*/
            if (isCrop) {
                call.onResultCropImage(requestCode, resultCode, data);
            } else {
                call.onResultImage(requestCode, resultCode, data);
            }
        }
    }

    /**
     * 权限回调
     *
     * @param requestCode 权限回调
     */
    public void onRequestPermissionsResult(int requestCode) {
        if (requestCode == 0xedc) {
            /**判断所有的权限是否请求完成*/
            isPermission(false);
        }
    }

    /**
     * 设置图片路径返回监听接口
     *
     * @param listener 监听接口
     */
    public void setOnResultListener(OnResultListener listener) {
        this.listener = listener;
    }

    /**
     * 初始化获取权限弹出框
     *
     * @param textNormalColor       选择弹出框按钮文字正常颜色
     * @param textPressColor        选择弹出框按钮文字按压颜色
     * @param backgroundNormalColor 选择弹出框按钮背景正常颜色
     * @param backgroundPressColor  选择弹出框按钮背景按压颜色
     * @return CameraUtil
     */
    private void initPermissionDialog(int textNormalColor, int textPressColor, int backgroundNormalColor, int backgroundPressColor) {
        PermissionDialog = new Dialog(activity);
        Window window = PermissionDialog.getWindow();
        if (window != null) {
            // styles 不显示标题
            PermissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //去阴影，这里指的是一般对话框之外的部分有个灰色的接近透明的阴影层，设置这个相当于去掉这个阴影层
            //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams wmParams = window.getAttributes();
            wmParams.format = PixelFormat.TRANSPARENT;  //内容全透明
            //wmParams.format = PixelFormat.TRANSLUCENT;//内容半透明
            wmParams.alpha = 1.0f;//调节透明度，1.0最大
            //dialog设置各种属性
            window.setAttributes(wmParams);
            window.setBackgroundDrawable(getDrawableList(0xffffffff, 0xffffffff, dp2px(5), dp2px(5), dp2px(5), dp2px(5)));

            // xml 布局
            RelativeLayout rootView = new RelativeLayout(activity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootView.setLayoutParams(layoutParams);
            rootView.setBackgroundDrawable(getDrawableList(0xffffffff, 0xffffffff, dp2px(5), dp2px(5), dp2px(5), dp2px(5)));

            LinearLayout childView = new LinearLayout(activity);
            childView.setOrientation(LinearLayout.VERTICAL);
            RelativeLayout.LayoutParams layoutParamsView = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            childView.setLayoutParams(layoutParamsView);
            childView.setBackgroundColor(Color.TRANSPARENT);

            /**top*/
            TextView context = new TextView(activity);
            context.setMinHeight(dp2px(96));
            context.setBackgroundColor(Color.TRANSPARENT);
            context.setGravity(Gravity.CENTER);
            context.setText("您需要权限才能使用此功能！\n是否前往？");
            context.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            context.setTextColor(0xff444444);

            View lineTop = new View(activity);
            lineTop.setBackgroundColor(0xffdddddd);

            /**bottom*/
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button cancel = new Button(activity);
            cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            cancel.setText("取消");
            cancel.setTextColor(getColorList(textNormalColor, textPressColor));
            cancel.setBackgroundDrawable(getDrawableList(backgroundNormalColor, backgroundPressColor, 0, 0, 0, dp2px(5)));
            LinearLayout.LayoutParams layoutParamsCancel = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(48));
            layoutParamsCancel.weight = 1;
            cancel.setLayoutParams(layoutParamsCancel);

            View lineMiddle = new View(activity);
            lineMiddle.setBackgroundColor(0xffdddddd);

            Button sure = new Button(activity);
            sure.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            sure.setText("确定");
            sure.setTextColor(getColorList(textNormalColor, textPressColor));
            sure.setBackgroundDrawable(getDrawableList(backgroundNormalColor, backgroundPressColor, 0, 0, dp2px(5), 0));
            LinearLayout.LayoutParams layoutParamsSure = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(48));
            layoutParamsSure.weight = 1;
            sure.setLayoutParams(layoutParamsSure);

            /**add*/
            linearLayout.addView(cancel);
            linearLayout.addView(lineMiddle, new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.addView(sure);
            childView.addView(context, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            childView.addView(lineTop, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            childView.addView(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(48)));
            rootView.addView(childView);
            window.setContentView(rootView);
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PermissionDialog.dismiss();
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                    activity.startActivity(intent);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PermissionDialog.dismiss();
                }
            });
        }
    }

    /**
     * 初始化选择弹出框
     *
     * @param textNormalColor       选择弹出框按钮文字正常颜色
     * @param textPressColor        选择弹出框按钮文字按压颜色
     * @param backgroundNormalColor 选择弹出框按钮背景正常颜色
     * @param backgroundPressColor  选择弹出框按钮背景按压颜色
     * @return CameraUtil
     */
    private void initChoiceDialog(int textNormalColor, int textPressColor, int backgroundNormalColor, int backgroundPressColor) {
        ChoiceDialog = new Dialog(activity);
        ChoiceDialog.setCancelable(true);
        ChoiceDialog.setCanceledOnTouchOutside(false);
        Window window = ChoiceDialog.getWindow();
        if (window != null) {
            // styles 不显示标题
            ChoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //去阴影，这里指的是一般对话框之外的部分有个灰色的接近透明的阴影层，设置这个相当于去掉这个阴影层
            //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams wmParams = window.getAttributes();
            wmParams.format = PixelFormat.TRANSPARENT;  //内容全透明
            //wmParams.format = PixelFormat.TRANSLUCENT;//内容半透明
            wmParams.alpha = 1.0f;//调节透明度，1.0最大
            //dialog设置各种属性
            window.setAttributes(wmParams);
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(getDrawableList(0x00ffffff, 0x00ffffff, dp2px(5), dp2px(5), dp2px(5), dp2px(5)));

            // xml布局
            RelativeLayout rootView = new RelativeLayout(activity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootView.setLayoutParams(layoutParams);
            rootView.setBackgroundColor(0x00ffffff);

            LinearLayout childView = new LinearLayout(activity);
            childView.setOrientation(LinearLayout.VERTICAL);
            RelativeLayout.LayoutParams layoutParamsView = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            childView.setLayoutParams(layoutParamsView);
            childView.setBackgroundColor(0x00ffffff);

            Button btnCamera = new Button(activity);
            LinearLayout.LayoutParams layoutParamsCamera = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(48));
            btnCamera.setLayoutParams(layoutParamsCamera);
            btnCamera.setText("拍照");
            btnCamera.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btnCamera.setTextColor(getColorList(textNormalColor, textPressColor));
            btnCamera.setBackgroundDrawable(getDrawableList(backgroundNormalColor, backgroundPressColor, dp2px(5), dp2px(5), 0, 0));

            RelativeLayout line = new RelativeLayout(activity);
            LinearLayout.LayoutParams layoutParamsLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            line.setLayoutParams(layoutParamsLine);
            line.setBackgroundColor(0xffdddddd);

            Button btnAlbum = new Button(activity);
            LinearLayout.LayoutParams layoutParamsAlbum = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(48));
            btnAlbum.setLayoutParams(layoutParamsAlbum);
            btnAlbum.setText("相册");
            btnAlbum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btnAlbum.setTextColor(getColorList(textNormalColor, textPressColor));
            btnAlbum.setBackgroundDrawable(getDrawableList(backgroundNormalColor, backgroundPressColor, 0, 0, dp2px(5), dp2px(5)));

            Button btnCancel = new Button(activity);
            LinearLayout.LayoutParams layoutParamsCancel = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(48));
            layoutParamsCancel.topMargin = dp2px(10);
            layoutParamsCancel.bottomMargin = dp2px(10);
            btnCancel.setLayoutParams(layoutParamsCancel);
            btnCancel.setText("取消");
            btnCancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btnCancel.setTextColor(getColorList(textNormalColor, textPressColor));
            btnCancel.setBackgroundDrawable(getDrawableList(backgroundNormalColor, backgroundPressColor, dp2px(5), dp2px(5), dp2px(5), dp2px(5)));

            childView.addView(btnCamera);
            childView.addView(line);
            childView.addView(btnAlbum);
            childView.addView(btnCancel);
            rootView.addView(childView);
            window.setContentView(rootView);
            //拍照选择
            btnCamera.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChoiceDialog.dismiss();
                    if (call != null) call.camera();
                }
            });
            // 从相册中选择照片
            btnAlbum.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChoiceDialog.dismiss();
                    if (call != null) call.album();
                }
            });
            //取消
            btnCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChoiceDialog.dismiss();
                }
            });
        }
    }

    /**
     * 带有背景变化的 Drawable
     *
     * @param colorNormal       正常状态下的颜色
     * @param colorPress        按压状态下的颜色
     * @param radiusLeftTop     左上角半径
     * @param radiusRightTop    右上角半径
     * @param radiusLeftBottom  左下角半径
     * @param radiusRightBottom 右下角半径
     * @return Drawable
     */
    private Drawable getDrawableList(int colorNormal, int colorPress, int radiusLeftTop, int radiusRightTop, int radiusLeftBottom, int radiusRightBottom) {
        StateListDrawable drawable = new StateListDrawable();
        /**正常状态的GradientDrawable*/
        GradientDrawable mDrawableNormal = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{colorNormal, colorNormal});
        mDrawableNormal.setShape(GradientDrawable.RECTANGLE);//设置形状为矩形
        mDrawableNormal.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mDrawableNormal.setCornerRadii(new float[]{
                radiusLeftTop, radiusLeftTop,
                radiusRightTop, radiusRightTop,
                radiusLeftBottom, radiusLeftBottom,
                radiusRightBottom, radiusRightBottom});//设置4角的圆角半径值
        /**按压状态的GradientDrawable*/
        GradientDrawable mDrawablePress = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{colorPress, colorPress});
        mDrawablePress.setShape(GradientDrawable.RECTANGLE);//设置形状为矩形
        mDrawablePress.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mDrawablePress.setCornerRadii(new float[]{
                radiusLeftTop, radiusLeftTop,
                radiusRightTop, radiusRightTop,
                radiusLeftBottom, radiusLeftBottom,
                radiusRightBottom, radiusRightBottom});//设置4角的圆角半径值

        /**按压或选中状态下*/
        drawable.addState(new int[]{android.R.attr.state_pressed}, mDrawablePress);
        drawable.addState(new int[]{android.R.attr.state_selected}, mDrawablePress);
        /**正常状态下*/
        drawable.addState(new int[]{}, mDrawableNormal);
        return drawable;
    }

    /**
     * 获取按压颜色变化
     *
     * @param colorNormal 正常状态下的颜色
     * @param colorPress  按压状态下的颜色
     * @return
     */
    private ColorStateList getColorList(int colorNormal, int colorPress) {
        int[] colors = new int[]{colorPress, colorPress, colorNormal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        return new ColorStateList(states, colors);
    }

    /**
     * 初始化各个参数
     */
    private void initParams() {
        /**初始化压缩参数*/
        isCompress = false;
        compressWidth = 1024;
        compressHeight = 1024;
        compressSize = 0;
        compressQuality = 0;
        /**初始化裁剪参数*/
        isCrop = false;
        outputX = 300;
        outputY = 300;
    }

    /**
     * 判断是否拥有权限
     *
     * @param isRequestPermission 是否请求权限
     */
    private boolean isPermission(boolean isRequestPermission) {
        if (Build.VERSION.SDK_INT > 22) {
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (isRequestPermission) {
                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    }, 0xedc);
                } else {
                    if (isSecondRequestPermissions) {
                        if (PermissionDialog != null && !PermissionDialog.isShowing()) {
                            PermissionDialog.show();
                        }
                    } else {
                        isSecondRequestPermissions = true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 尺寸dip
     * @return 像素值
     */
    private int dp2px(float dpValue) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 监听接口
     */
    public interface OnResultListener {
        void onResult(String path, int requestCode);
    }

    /**
     * 调用相机相册
     */
    private class CallCameraAlbum {
        //相机请求码
        private final int CAMERA = 0xe02;
        //相册请求码
        private final int ALBUM = 0xe03;
        //裁剪请求码
        private final int CROP = 0xe04;
        //拍照保存的文件
        private File photoFile;
        //图片剪裁保存文件
        private File saveCropFile;
        //拍照保存的路径
        private String photoPath;
        //保存文件的路径
        private String savePath;
        //是否剪裁返回bitmap形式
        private boolean isBitmapCrop;

        CallCameraAlbum() {
            /**获取拍照保存的路径*/
            photoPath = XAppUtils.instance().getPhoneRoot(activity) + "/DCIM/Camera";
            /**获取保存文件的路径*/
            savePath = XAppUtils.instance().getImageKeep(activity);
        }

        //调用拍照
        private void camera() {
            if (isPermission(true)) {
                photoFile = null;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                /**获取时间戳,并以时间戳作为照片的文件名*/
                String fileName = getCurrentTime();
                /**获取照片保存的路径*/
                File file = new File(photoPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                photoFile = new File(file, fileName + ".png");

                /**调用相机拍照*/
                if (Build.VERSION.SDK_INT < 24) {
                    /**API 小于24的旧版使用此方式调用相机*/
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    activity.startActivityForResult(intent, CAMERA);
                } else {
                    /**API 大于等于24的新版使用使用共享文件的形式调用相机*/
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                    Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    activity.startActivityForResult(intent.putExtra(MediaStore.EXTRA_OUTPUT, uri), CAMERA);
                }
            }
        }

        //调用相册
        private void album() {
            if (isPermission(true)) {
                /**API 大于等于23 需要读写两项权限*/
                if (Build.VERSION.SDK_INT < 19) {
                    /**API 小于19的使用调用相册*/
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    activity.startActivityForResult(intent, ALBUM);
                } else {
                    /**API 大于等于19的调用相册*/
                    Intent i = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activity.startActivityForResult(i, ALBUM);
                }
            }
        }

        //获取时间戳,并以时间戳作为照片的文件名
        private String getCurrentTime() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssMS", Locale.getDefault());
            Date curDate = new Date(System.currentTimeMillis());// 系统当前时间
            return formatter.format(curDate);
        }

        /**
         * 原始图片
         *
         * @param requestCode
         * @param resultCode
         * @param data
         * @return
         */
        private boolean onResultImage(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case CAMERA:
                        if (photoFile != null) {
                            /**返回的结果数据处理*/
                            Uri uri = Uri.fromFile(new File(photoFile
                                    .getAbsolutePath()));
                            String srcPath = uri.getPath();
                            if (new File(srcPath).exists()) {
                                /**先写入数据库*/
                                //writeDataBase(srcPath);
                                if (isCompress) {
                                    /**非原文件压缩*/
                                    compressImage(srcPath, compressWidth, compressHeight, compressSize, compressQuality, true);
                                } else {
                                    /**图片文件不压缩*/
                                    if (listener != null) listener.onResult(srcPath, mRequestCode);
                                }
                            }
                            return true;
                        }
                        break;
                    case ALBUM:
                        if (data != null) {
                            /**返回的结果数据处理*/
                            Uri uri = data.getData();
                            String srcPath = uri.getPath();
                            if (!new File(srcPath).exists()) {
                                srcPath = getPath(activity, uri);
                            }
                            if (isCompress) {
                                /**非原文件压缩*/
                                compressImage(srcPath, compressWidth, compressHeight, compressSize, compressQuality, true);
                            } else {
                                /**图片文件不压缩*/
                                if (listener != null) listener.onResult(srcPath, mRequestCode);
                            }
                            return true;
                        }
                        break;
                }
            }
            return false;
        }

        /**
         * 裁剪图片
         *
         * @param requestCode
         * @param resultCode
         * @param data
         * @return
         */
        private boolean onResultCropImage(int requestCode, int resultCode, Intent data) {
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case CAMERA:
                        if (photoFile != null && photoFile.exists()) {
                            /**先要更新数据库*/
                            writeDataBase(photoFile.getAbsolutePath());
                            Uri uri = getUriFromFile(activity, photoFile);
                            if (uri != null) {
                                /**文件根目录是否存在，不存在必须创建*/
                                File file = new File(savePath);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                /**重新创建图片剪裁保存路径*/
                                saveCropFile = new File(savePath + "/" + getCurrentTime() + ".png");
                                cropImage(activity, uri, saveCropFile, outputX, outputY);
                            }
                        }
                        break;
                    case ALBUM:
                        if (data != null) {
                            Uri uri = data.getData();
                            String srcPath = uri.getPath();
                            /**有可能有些手机获取不到地址，需要解析uri获取地址*/
                            if (!new File(srcPath).exists()) {
                                srcPath = getPath(activity, uri);
                                if (!TextUtils.isEmpty(srcPath)) {
                                    uri = getUriFromFile(activity, new File(srcPath));
                                }
                            }
                            if (uri != null) {
                                /**文件根目录是否存在，不存在必须创建*/
                                File file = new File(savePath);
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                /**重新创建图片剪裁保存路径*/
                                saveCropFile = new File(savePath + "/" + getCurrentTime() + ".png");
                                cropImage(activity, uri, saveCropFile, outputX, outputY);
                            }
                        }
                        break;
                    case CROP:
                        if (data != null) {
                            if (isBitmapCrop) {
                                String imgPath = setCropImage(data);
                                if (listener != null) listener.onResult(imgPath, mRequestCode);
                                return true;
                            } else {
                                if (saveCropFile != null) {
                                    String imgPath = saveCropFile.getPath();
                                    /**原文件压缩*/
                                    compressImage(imgPath, compressWidth, compressHeight, compressSize, compressQuality, false);
                                    return true;
                                }
                            }
                        }
                        break;
                }
            }
            return false;
        }

        /**
         * 压缩图片
         *
         * @param srcPath   图片资源地址
         * @param width     压缩宽度
         * @param height    压缩高度
         * @param size      压缩最大值
         * @param quality   压缩质量(大概85)
         * @param isNewFile 是否使用新文件夹装压缩文件
         */
        private void compressImage(String srcPath, int width, int height, int size, int quality, boolean isNewFile) {
            if (quality <= 0) quality = 90;
            if (size <= 0) size = (width * height) / 4;//大概需要压缩至1/4尺寸
            if (!TextUtils.isEmpty(srcPath)) {
                File file = new File(srcPath);
                if (file.exists() && file.length() > size) {
                    // 对图片进行尺寸压缩
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(srcPath, options);
                    int w = options.outWidth;
                    int h = options.outHeight;
                    if (w > width && w > h) {
                        options.inSampleSize = w / width;
                    } else if (h > height && h > w) {
                        options.inSampleSize = h / height;
                    }
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);

                    /**如果使用新文件夹来装压缩文件*/
                    String newSrcPath = null;
                    if (isNewFile) {
                        /**文件根目录是否存在，不存在必须创建*/
                        File fileSave = new File(savePath);
                        if (!fileSave.exists()) {
                            fileSave.mkdirs();
                        }
                        newSrcPath = savePath + "/compressimagecopy.png";
                    }
                    // 对图片进行质量压缩
                    if (bitmap != null) {
                        try {
                            OutputStream os;
                            if (newSrcPath != null) {
                                os = new FileOutputStream(newSrcPath);
                            } else {
                                os = new FileOutputStream(srcPath);
                            }
                            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    if (isNewFile) {
                        if (listener != null) listener.onResult(newSrcPath, mRequestCode);
                    } else {
                        if (listener != null) listener.onResult(srcPath, mRequestCode);
                    }
                } else {
                    if (listener != null) listener.onResult(srcPath, mRequestCode);
                }
            }
        }

        /**
         * 相册裁剪
         *
         * @param activity Activity
         * @param uri      Uri
         * @param saveFile 文件保存路径
         * @param outputX  输出宽
         * @param outputY  输出高
         */
        private void cropImage(Activity activity, Uri uri, File saveFile, int outputX, int outputY) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            //剪裁的类型
            intent.setDataAndType(uri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");

            // 裁剪框的比例
            intent.putExtra("aspectX", outputX / MaxDivisor(outputX, outputY));
            intent.putExtra("aspectY", outputY / MaxDivisor(outputX, outputY));

            // 裁剪后输出图片的尺寸大小
            intent.putExtra("outputX", outputX);
            intent.putExtra("outputY", outputY);

            //设置输出的格式
            intent.putExtra("outputFormat", "PNG");//Bitmap.CompressFormat.JPEG.toString()
            //取消人脸识别
            intent.putExtra("noFaceDetection", true);

            if (outputX <= 300 && outputY <= 300) {
                //设置了true的话直接返回bitmap，可能会很占内存
                intent.putExtra("return-data", true);
                //剪裁返回bitmap形式
                isBitmapCrop = true;
            } else {
                //设置了true的话直接返回bitmap，可能会很占内存
                intent.putExtra("return-data", false);
                //设置输出的地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFromFile(activity, saveFile));
                //剪裁不返回bitmap形式
                isBitmapCrop = false;
            }
            activity.startActivityForResult(intent, CROP);
        }

        /**
         * @param data
         * @return
         */
        private String setCropImage(Intent data) {
            Bitmap bitmap = null;
            Uri photoUri = data.getData();
            if (photoUri != null) {
                bitmap = BitmapFactory.decodeFile(photoUri.getPath());
            }
            String newfilename = "";
            if (bitmap == null) {
                Bundle extra = data.getExtras();
                if (extra != null) {
                    bitmap = (Bitmap) extra.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    /**文件根目录是否存在，不存在必须创建*/
                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    newfilename = savePath + "/" + getCurrentTime() + ".png";
                    if (bitmap != null) {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] picData = stream.toByteArray();
                        try {
                            writeByteArrayToSD(newfilename, picData, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return newfilename;
        }

        /**
         * 描述：将byte数组写入文件.
         *
         * @param path    the path
         * @param content the content
         * @param create  the create
         */
        private void writeByteArrayToSD(String path, byte[] content, boolean create) {
            FileOutputStream fos = null;
            try {
                File file = new File(path);
                //SD卡是否存在
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }
                //文件是否存在
                if (!file.exists()) {
                    if (create) {
                        File parent = file.getParentFile();
                        if (!parent.exists()) {
                            parent.mkdirs();
                            file.createNewFile();
                        }
                    } else {
                        return;
                    }
                }
                fos = new FileOutputStream(path);
                fos.write(content);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {
                    }
                }
            }
        }

        /**
         * 获取Uri
         *
         * @param context   Context
         * @param imageFile 图片文件
         * @return Uri
         */
        private Uri getUriFromFile(Context context, @NonNull File imageFile) {
            if (Build.VERSION.SDK_INT >= 24) {
                /**android7.0 以上需要用文件共享的方式*/
                String filePath = imageFile.getAbsolutePath();
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media._ID},
                        MediaStore.Images.Media.DATA + "=? ",
                        new String[]{filePath}, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.MediaColumns._ID));
                    Uri baseUri = Uri.parse("content://media/external/images/media");
                    return Uri.withAppendedPath(baseUri, "" + id);
                } else {
                    if (imageFile.exists()) {
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.DATA, filePath);
                        return context.getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    } else {
                        return null;
                    }
                }
            } else {
                /**android7.0 以下需要此方式*/
                return Uri.fromFile(imageFile);
            }
        }

        /**
         * 向数据库中写入拍照信息
         *
         * @param srcPath 路径
         */
        private void writeDataBase(String srcPath) {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("_data", srcPath);
            localContentValues.put("description", "save image ---");
            localContentValues.put("mime_type", "image/jpeg");
            ContentResolver localContentResolver = activity.getContentResolver();
            Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            localContentResolver.insert(localUri, localContentValues);
        }

        /**
         * 递归法求最大公约数
         *
         * @param m 第一个数
         * @param n 第二个数
         * @return 最大公约数
         */
        private int MaxDivisor(int m, int n) {
            int max = Math.max(m, n);
            int min = Math.min(m, n);
            if (max % min == 0) {// 若余数为0,返回最大公约数
                return min;
            } else { // 否则,进行递归,把n赋给m,把余数赋给n
                return MaxDivisor(min, max % min);
            }
        }

        // UriUtils
        @SuppressLint("NewApi")
        private String getPath(final Context context, final Uri uri) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (DocumentsContract.isDocumentUri(context, uri)) {// DocumentProvider
                    switch (uri.getAuthority()) {
                        case "com.android.externalstorage.documents": // ExternalStorageProvider
                            final String docId = DocumentsContract.getDocumentId(uri);
                            final String[] split = docId.split(":");
                            final String type = split[0];
                            if ("primary".equalsIgnoreCase(type)) {
                                return Environment.getExternalStorageDirectory() + "/" + split[1];
                            }
                            break;
                        case "com.android.providers.downloads.documents": // DownloadsProvider
                            final String id = DocumentsContract.getDocumentId(uri);
                            final Uri contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                            return getDataColumn(context, contentUri, null, null);
                        case "com.android.providers.media.documents": // MediaProvider
                            final String docIds = DocumentsContract.getDocumentId(uri);
                            final String[] splits = docIds.split(":");
                            final String types = splits[0];

                            Uri contentUris = null;
                            if ("image".equals(types)) {
                                contentUris = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            } else if ("video".equals(types)) {
                                contentUris = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            } else if ("audio".equals(types)) {
                                contentUris = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            }

                            final String selection = "_id=?";
                            final String[] selectionArgs = new String[]{
                                    splits[1]
                            };
                            return getDataColumn(context, contentUris, selection, selectionArgs);
                    }
                } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
                    return getDataColumn(context, uri, null, null);
                } else if ("file".equalsIgnoreCase(uri.getScheme())) { // File
                    return uri.getPath();
                }
            }
            return null;
        }

        private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
            String data = null;
            Cursor cursor = null;
            final String[] projection = {"_data"};
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow("_data");
                    data = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                if (cursor != null) cursor.close();
            }
            return data;
        }
    }
}