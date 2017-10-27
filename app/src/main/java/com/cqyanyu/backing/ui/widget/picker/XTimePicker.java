package com.cqyanyu.backing.ui.widget.picker;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import com.cqyanyu.backing.R;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 自定义时间选择器
 * Created by zj on 2017/7/4.
 */
public class XTimePicker extends FrameLayout implements NumberPicker.OnValueChangeListener {
    private int layoutId;
    private int minValue;
    private int maxValue;
    private int divideColor;
    private int textColor;
    private int utilId;
    private boolean showNow;
    private ViewGroup mContainer;
    private NumberPicker npYear;
    private NumberPicker npMonth;
    private NumberPicker npDay;
    private NumberPicker npHour;
    private NumberPicker npMinute;
    private OnSelectListener listener;
    private int[] choice;
    private String[] util;

    public XTimePicker(@NonNull Context context) {
        this(context, null);
    }

    public XTimePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        choice = new int[5];
        initAttrs(attrs);
        initView(context);
        initParams();
    }

    /**
     * 初始化属性
     *
     * @param attrs 属性
     */
    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.XTimePicker);
            layoutId = array.getResourceId(R.styleable.XTimePicker_x_layout, 0);
            divideColor = array.getColor(R.styleable.XTimePicker_x_divide_color, 0);
            textColor = array.getColor(R.styleable.XTimePicker_x_text_color, 0);
            maxValue = array.getInt(R.styleable.XTimePicker_x_max_value, 2400);
            minValue = array.getInt(R.styleable.XTimePicker_x_min_value, 1800);
            showNow = array.getBoolean(R.styleable.XTimePicker_x_now, true);
            utilId = array.getResourceId(R.styleable.XTimePicker_x_util, 0);
            array.recycle();
        }
        if (utilId > 0) util = getContext().getResources().getStringArray(utilId);
    }

    /**
     * 初始化视图
     *
     * @param context Context
     */
    private void initView(Context context) {
        if (layoutId > 0) {
            mContainer = (ViewGroup) LayoutInflater.from(context).inflate(context.getResources().getLayout(layoutId), null);
            if (mContainer != null) {
                try {
                    npYear = (NumberPicker) mContainer.findViewById(context.getResources().getIdentifier("x_year", "id", context.getPackageName()));
                } catch (Exception e) {
                    e.getStackTrace();
                }
                try {
                    npMonth = (NumberPicker) mContainer.findViewById(context.getResources().getIdentifier("x_month", "id", context.getPackageName()));
                } catch (Exception e) {
                    e.getStackTrace();
                }
                try {
                    npDay = (NumberPicker) mContainer.findViewById(context.getResources().getIdentifier("x_day", "id", context.getPackageName()));
                } catch (Exception e) {
                    e.getStackTrace();
                }
                try {
                    npHour = (NumberPicker) mContainer.findViewById(context.getResources().getIdentifier("x_hour", "id", context.getPackageName()));
                } catch (Exception e) {
                    e.getStackTrace();
                }
                try {
                    npMinute = (NumberPicker) mContainer.findViewById(context.getResources().getIdentifier("x_minute", "id", context.getPackageName()));
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
        if (mContainer != null) {
            addView(mContainer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        initPicker(npYear, 0, minValue, maxValue);
        initPicker(npMonth, 1, 1, 12);
        initPicker(npDay, 2, 1, 31);
        initPicker(npHour, 3, 0, 23);
        initPicker(npMinute, 4, 0, 59);
        //初始化时间
        if (showNow) {
            Calendar instance = Calendar.getInstance();
            setDate(instance.get(Calendar.YEAR),
                    instance.get(Calendar.MONTH) + 1,
                    instance.get(Calendar.DAY_OF_MONTH), 0, 0);
        } else {
            setDate(minValue, 1, 1, 0, 0);
        }
    }

    /**
     * 初始化 NumberPicker
     *
     * @param numberPicker NumberPicker
     * @param index        单位位置
     * @param minValue     最小值
     * @param maxValue     最大值
     */
    private void initPicker(NumberPicker numberPicker, int index, int minValue, int maxValue) {
        if (numberPicker != null && maxValue >= minValue) {
            /**初始化单位*/
            String utils = (util != null && util.length > index && !TextUtils.isEmpty(util[index])) ? util[index] : "";
            /**初始化数据*/
            List<String> mList = new ArrayList<>();
            for (int i = minValue; i <= maxValue; i++) {
                if (i < 10) {
                    mList.add("0" + (i + utils));
                } else if (i >= 10) {
                    mList.add(i + utils);
                }
            }
            /**初始化NumberPicker*/
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(mList.size() - 1);
            numberPicker.setDisplayedValues(mList.toArray(new String[mList.size()]));
            numberPicker.setOnValueChangedListener(this);
            /**禁止输入键盘弹开*/
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setWrapSelectorWheel(true);
            setDividerColor(numberPicker, divideColor);
            setTextColor(numberPicker, textColor);
        }
    }

    /**
     * 设置分割线颜色
     *
     * @param color         颜色
     * @param mNumberPicker NumberPicker
     */
    private void setDividerColor(@NonNull NumberPicker mNumberPicker, @ColorInt int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    /**设置分割线的颜色值*/
                    if (color != 0) {
                        pf.set(mNumberPicker, new ColorDrawable(color));
                    } else {
                        /**透明*/
                        pf.set(mNumberPicker, new ColorDrawable(Color.TRANSPARENT));
                    }
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置文本颜色
     *
     * @param color         颜色
     * @param mNumberPicker NumberPicker
     */
    private void setTextColor(@NonNull NumberPicker mNumberPicker, @ColorInt int color) {
        if (color != 0) {
            final int count = mNumberPicker.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = mNumberPicker.getChildAt(i);
                if (child instanceof EditText) {
                    try {
                        Field selectorWheelPaintField = mNumberPicker.getClass()
                                .getDeclaredField("mSelectorWheelPaint");
                        selectorWheelPaintField.setAccessible(true);
                        ((Paint) selectorWheelPaintField.get(mNumberPicker)).setColor(color);
                        ((EditText) child).setTextColor(color);
                        mNumberPicker.invalidate();
                    } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 重置NumberPicker各个属性
     */
    private void reset() {
        if (choice != null && choice.length == 5) {
            //重置日的最大值
            if (npDay != null) {
                npDay.setMaxValue(getTotalDaysOfMonth(choice[0], choice[1]) - 1);
                //判断时间是否超出范围
                if (choice[2] > (npDay.getMaxValue() + 1)) {
                    choice[2] = npDay.getMaxValue() + 1;
                }
            }
        }
    }

    /**
     * 获取某年某月的总天数
     *
     * @param year  年
     * @param month 月
     * @return 总天数
     */
    private int getTotalDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return ((year % 4 != 0) || (year % 100 == 0) && (year % 400 != 0)) ? 28 : 29;
            default:
                return 1;
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        if (choice != null && choice.length == 5) {
            if (numberPicker == npYear) {
                choice[0] = newVal + minValue;
                reset();
            } else if (numberPicker == npMonth) {
                choice[1] = newVal + 1;
                reset();
            } else if (numberPicker == npDay) {
                choice[2] = newVal + 1;
            } else if (numberPicker == npHour) {
                choice[3] = newVal;
            } else if (numberPicker == npMinute) {
                choice[4] = newVal;
            }

            if (listener != null) listener.onSelect(getDate());
        }
    }

    /**
     * 初始化时间
     *
     * @param data   时间
     * @param format 类型
     */
    public void setDate(String data, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            Date parse = dateFormat.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            setDate(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化时间
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     */
    private void setDate(int year, int month, int day, int hour, int minute) {
        if (choice != null && choice.length == 5) {
            if (npYear != null && year >= minValue && year <= maxValue) {
                npYear.setValue(year - minValue);
                choice[0] = year;
            }
            if (npMonth != null && month >= 1) {
                npMonth.setValue(month - 1);
                choice[1] = month;
            }
            if (npDay != null && day >= 1) {
                npDay.setValue(day - 1);
                choice[2] = day;
            }
            if (npHour != null && hour >= 0) {
                npHour.setValue(hour);
                choice[3] = hour;
            }
            if (npMinute != null && minute >= 0) {
                npMinute.setValue(minute);
                choice[4] = minute;
            }
            reset();
        }
    }

    /**
     * 设置最大值和最小值
     *
     * @param max 最大值
     * @param min 最小值
     */
    public void setMaxMinValue(int max, int min) {
        if (max >= 0 && min >= 0) {
            if (max > 0) maxValue = max;
            if (min > 0) minValue = min;
            initParams();
        }
    }

    /**
     * 获取选择的时间
     */
    public Date getDate() {
        if (choice != null && choice.length == 5) {
            Calendar instance = Calendar.getInstance();
            instance.set(choice[0], choice[1] - 1, choice[2], choice[3], choice[4]);
            return instance.getTime();
        }
        return null;
    }

    /**
     * 设置选择监听
     *
     * @param listener 监听
     */
    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSelectListener {
        void onSelect(Date date);
    }
}
