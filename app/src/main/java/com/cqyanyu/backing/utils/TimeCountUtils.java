package com.cqyanyu.backing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 倒计时类(主要用于发送短信验证码)
 * Created by Administrator on 2017/3/27.
 */
public class TimeCountUtils {
    private final String NAME = "time_count_utils_save_time_count_util";
    private String key = "time_count_utils_save_time_count";
    private Activity mActivity;

    //TimeCountDown
    private TimeCountDown timeCountDown;
    private long totalTime;
    private long duringTime;

    //Attribute
    private String timeBeforeText;
    private String timeAfterText;
    private int beforeColor;
    private int timeColor;
    private int timeContentColor;
    private String timeContent;
    private int afterColor;
    private boolean isSave;
    private SimpleDateFormat format;

    //Content
    private TextView mTextView;
    private int colorOld;
    private String textOld;

    /**
     * 构造方法不能创建倒计时
     * 必须调用{@link #initFinish}
     *
     * @param activity Context
     */
    public TimeCountUtils(Activity activity) {
        key = key + activity.getLocalClassName();
        mActivity = activity;
        beforeColor = 0;
        afterColor = 0;
        timeColor = 0xff8f8f8f;
        timeContentColor = 0;
        timeBeforeText = "";
        timeAfterText = "秒后重新获取";
    }

    /**
     * 是否保存到下一次进来
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param isSave true为保存
     * @return TimeCountUtils
     */
    public TimeCountUtils setSaveTime(boolean isSave) {
        this.isSave = isSave;
        return this;
    }

    /**
     * 设置倒计时开始时倒计时的前面的文字
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param text  content
     * @param color color
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeBeforeText(String text, @ColorInt int color) {
        if (!TextUtils.isEmpty(text)) timeBeforeText = text;
        beforeColor = color == 0 ? 0 : color;
        return this;
    }

    /**
     * 设置倒计时开始时倒计时的前面的文字
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param text content
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeBeforeText(String text) {
        if (!TextUtils.isEmpty(text)) timeBeforeText = text;
        return this;
    }

    /**
     * 设置倒计时开始时倒计时的后面的文字
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param text  content
     * @param color color
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeAfterText(String text, @ColorInt int color) {
        if (!TextUtils.isEmpty(text)) timeAfterText = text;
        afterColor = color == 0 ? 0 : color;
        return this;
    }

    /**
     * 设置倒计时开始时倒计时的后面的文字
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param text content
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeAfterText(String text) {
        if (!TextUtils.isEmpty(text)) timeAfterText = text;
        return this;
    }

    /**
     * 设置倒计时开始时倒计时的颜色
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param color color
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeColor(@ColorInt int color) {
        timeColor = color == 0 ? 0xff8f8f8f : color;
        return this;
    }

    /**
     * 设置倒计时的内容
     *
     * @param content 内容
     * @param color   颜色
     * @return TimeCountUtils
     */
    public TimeCountUtils setContent(String content, @ColorInt int color) {
        timeContent = content;
        timeContentColor = color;
        return this;
    }

    /**
     * 设置倒计时开始时的时间类型是否是hh:mm:ss类型
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param isFormat true为是
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeFormat(SimpleDateFormat isFormat) {
        format = isFormat;
        return this;
    }

    /**
     * 设置倒计时开始时总时长和间隔时长
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param totalTime  总时长
     * @param duringTime 间隔时长
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeAttribute(long totalTime, long duringTime) {
        if (totalTime > 0) this.totalTime = totalTime;
        if (duringTime > 0) {
            this.duringTime = duringTime;
        } else {
            this.duringTime = 1000;
        }
        return this;
    }

    /**
     * 设置倒计时开始时总时长和间隔时长
     * 必须在{@link #initFinish}函数之前调用
     * 否则无效
     *
     * @param totalTime 总时长
     * @return TimeCountUtils
     */
    public TimeCountUtils setTimeAttribute(long totalTime) {
        if (totalTime > 0) this.totalTime = totalTime;
        this.duringTime = 1000;
        return this;
    }

    /**
     * 初始化TimeCountUtils
     *
     * @param textView 放置计时的TextView控件
     */
    public TimeCountUtils initFinish(TextView textView) {
        mTextView = textView;
        colorOld = mTextView.getCurrentTextColor();
        if (timeContentColor != 0) colorOld = timeContentColor;
        textOld = mTextView.getText().toString();
        if (!TextUtils.isEmpty(timeContent)) textOld = timeContent;
        beforeColor = beforeColor == 0 ? timeColor : beforeColor;
        afterColor = afterColor == 0 ? timeColor : afterColor;
        if (totalTime > 0) timeCountDown = new TimeCountDown(totalTime, duringTime);
        if (isSave && returnCutDownTime() > 0) {
            new TimeCountDown(returnCutDownTime(), duringTime).start();
        }
        return this;
    }

    //开始倒计时
    public void start() {
        if (timeCountDown != null) {
            if (isSave) keepCutDownTime();
            timeCountDown.start();
        } else {
            //此异常为故意抛出
            throw new IllegalStateException("you are not used initFinish()!");
        }
    }

    //保存当前的计时时间
    private void keepCutDownTime() {
        if (mActivity != null) {
            SharedPreferences preferences = mActivity.getSharedPreferences(NAME, Context.MODE_APPEND);
            long l = System.currentTimeMillis();
            SharedPreferences.Editor edit = preferences.edit();
            edit.putLong(key, l + totalTime);
            edit.apply();
        }
    }

    //返回自动保存计时
    private long returnCutDownTime() {
        if (mActivity != null) {
            SharedPreferences preferences = mActivity.getSharedPreferences(NAME, Context.MODE_APPEND);
            long l = System.currentTimeMillis();
            long aLong = preferences.getLong(key, 0);
            return (aLong - l) > 0 ? (aLong - l) : 0;
        } else {
            return 0;
        }
    }

    /**
     * 将int值转换为分钟和秒的格式
     *
     * @param value 时间
     * @return 时间格式
     */
    private String formatToString(long value) {
        //format = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());<例子>
        //需要减去时区差，否则计算结果不正确(中国时区会相差8个小时)
        value -= TimeZone.getDefault().getRawOffset();
        return format.format(value);
    }

    private class TimeCountDown extends CountDownTimer {

        public TimeCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //设置不能点击
            mTextView.setClickable(false);
            mTextView.setEnabled(false);
            //设置倒计时时间
            String time = format == null ? (millisUntilFinished / 1000 + "") : formatToString(millisUntilFinished);
            //设置按钮为灰色，这时是不能点击的
            mTextView.setTextColor(timeColor);
            //显示倒计时时间
            Spanned spanned = Html.fromHtml("<font color=" + beforeColor + ">" + timeBeforeText + "</font>" + time + "<font color=" + afterColor + ">" + timeAfterText + "</font>");
            mTextView.setText(spanned);
        }

        @Override
        public void onFinish() {
            //重新获得点击
            mTextView.setClickable(true);
            mTextView.setEnabled(true);
            //设置按钮为之前的内容，这时是可以能点击了
            mTextView.setText(textOld);
            //设置按钮为之前的颜色，这时是可以能点击了
            mTextView.setTextColor(colorOld);
        }
    }
}