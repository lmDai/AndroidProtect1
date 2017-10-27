package com.cqyanyu.backing.utils;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 数字操作
 * Created by Administrator on 2017/1/23.
 */
public class NumberUtils {
    private static final String[] UNITS = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千",};
    private static final String[] NUMS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九",};
    private static boolean hasFocus = true;

    /**
     * 数字转换成中文汉字
     *
     * @param value 转换的数字
     * @return 返回数字转后的汉字字符串
     */
    public static String translate(int value) {
        // 转译结果
        String result = "";
        for (int i = String.valueOf(value).length() - 1; i >= 0; i--) {
            int r = (int) (value / Math.pow(10, i));
            result += NUMS[r % 10] + UNITS[i];
        }
        result = result.replaceAll("零[十, 百, 千]", "零");
        result = result.replaceAll("零+", "零");
        result = result.replaceAll("零([万, 亿])", "$1");
        result = result.replaceAll("亿万", "亿"); // 亿万位拼接时发生的特殊情况
        if (result.startsWith("一十"))
            result = result.substring(1);
        if (result.endsWith("零"))
            result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 隐藏中间数字的只显示前面三位后面各四位
     * <主要用于手机号>
     *
     * @param number
     * @return
     */
    public static String hideType(String number) {
        if (!TextUtils.isEmpty(number)) {
            return number.length() < 7 ? number : (number.substring(0, 3) + "****" + number.substring(number.length() - 4, number.length()));
        } else {
            return "";
        }
    }

    /**
     * 隐藏中间数字的只显示前面三位后面各四位(带颜色改变)
     * <主要用于手机号>
     *
     * @param number
     * @return
     */
    public static Spanned hideType(String number, String color) {
        if (!TextUtils.isEmpty(number)) {
            return Html.fromHtml(number.length() < 7 ? number : (number.substring(0, 3) + "<font color=" + color + ">****</font>" + number.substring(number.length() - 4, number.length())));
        } else {
            return Html.fromHtml("");
        }
    }

    /**
     * 隐藏前面的数字只显示后四位数字
     * <主要用于银行卡号>
     *
     * @param number
     * @return
     */
    public static String hideBefore(String number) {
        if (!TextUtils.isEmpty(number)) {
            return number.length() < 5 ? number : ("****" + number.substring(number.length() - 4, number.length()));
        } else {
            return "";
        }
    }

    /**
     * 隐藏前面的数字只显示后四位数字(带颜色改变)
     * <主要用于银行卡号>
     *
     * @param number
     * @return
     */
    public static Spanned hideBefore(String number, String color) {
        if (!TextUtils.isEmpty(number)) {
            return Html.fromHtml(number.length() < 5 ? number : ("<font color=" + color + ">****</font>" + number.substring(number.length() - 4, number.length())));
        } else {
            return Html.fromHtml("");
        }
    }

    /**
     * 小数点构造
     *
     * @param params
     * @return
     */
    public static String setDecimalFloat(float params) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(params);//format 返回的是字符串
    }

    /**
     * 小数点构造
     *
     * @param params
     * @return
     */
    public static String setDecimalDouble(double params) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(params);//format 返回的是字符串
    }
    /**
     * 小数点构造
     *
     * @param params
     * @return
     */
    public static String setDecimal(float params) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(params);//format 返回的是字符串
    }
    /**
     * 限制EditText输入数值到小数点后几位
     *
     * @param editText
     * @param s
     */
    public static void limitDecimalLength(EditText editText, CharSequence s) {
        if (editText.getOnFocusChangeListener() == null) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    NumberUtils.hasFocus = hasFocus;
                }
            });
        }
        if (NumberUtils.hasFocus) {
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    editText.setText(s);
                    editText.setSelection(s.length());
                }
            }
            if (TextUtils.equals(s.toString().trim().substring(0), ".")) {
                s = "0" + s;
                editText.setText(s);
                editText.setSelection(2);
            }

            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    editText.setText(s.subSequence(0, 1));
                    editText.setSelection(1);
                    return;
                }
            }
        }
    }

    /**
     * 四舍五入取值
     *
     * @param f
     * @param length
     * @return
     */
    public static float rounding(float f, int length) {
        f = new BigDecimal(f).setScale(length, BigDecimal.ROUND_HALF_UP)
                .floatValue();
        return f;
    }

    /**
     * float类型精度的加减法
     * BigDecimal add(BigDecimal add) 加法运算
     * BigDecimal subtract(BigDecimal subtrahend) 减法运算
     * BigDecimal multiply(BigDecimal multiplicand) 乘法运算
     * BigDecimal divide(BigDecimal divisor) 除法运算
     *
     * @param m    first number
     * @param c    second number
     * @param type operation type
     * @return operation value
     */
    public static float operation(float m, float c, int type) {
        BigDecimal b1 = new BigDecimal(Float.toString(m));
        BigDecimal b2 = new BigDecimal(Float.toString(c));
        if (type == 1) {//加法
            return b1.add(b2).floatValue();
        } else if (type == 2) {//减法
            return b1.subtract(b2).floatValue();
        } else if (type == 3) {//乘法
            return b1.multiply(b2).floatValue();
        } else if (type == 4) {//除法
            try {
                return b1.divide(b2, MathContext.DECIMAL128).floatValue();
            } catch (ArithmeticException a) {
                return 0.0f;
            }
        } else {
            return 0.0f;
        }
    }

    /**
     * 设置 editText 是否聚焦编辑
     *
     * @param editText
     * @param able
     */
    public static void editable(EditText editText, boolean able) {
        if (able) {
            //设置为可编辑
            editText.setEnabled(true);
            editText.setFocusable(true);//获取焦点
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();//请求焦点
            editText.requestFocusFromTouch();
        } else {
            //设置为不可编辑
            editText.setFocusable(false);
            editText.setEnabled(false);
        }
    }

    /**
     * 两个字符串相加结果四舍五入并保留两位有效数字
     *
     * @param one
     * @param two
     * @return
     */
    public static String addStringNumber(String one, String two) {
        BigDecimal b1;
        BigDecimal b2;
        if (!TextUtils.isEmpty(one)) {
            if (one.startsWith("￥")) {
                if (one.length() > 1) {
                    one = (one.split("￥"))[1];
                } else {
                    one = "0.00";
                }
            }
            if (one.endsWith("元")) {
                if (one.length() > 1) {
                    one = (one.split("元"))[1];
                } else {
                    one = "0.00";
                }
            }
            b1 = new BigDecimal(one);
        } else {
            b1 = new BigDecimal("0.00");
        }
        if (!TextUtils.isEmpty(two)) {
            if (two.startsWith("￥")) {
                if (two.length() > 1) {
                    two = (two.split("￥"))[1];
                } else {
                    two = "0.00";
                }
            }
            if (two.endsWith("元")) {
                if (two.length() > 1) {
                    two = (two.split("元"))[1];
                } else {
                    two = "0.00";
                }
            }
            b2 = new BigDecimal(two);
        } else {
            b2 = new BigDecimal("0.00");
        }
        float value = b1.add(b2).floatValue();//精度加法
        value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//四舍五入
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(value);
    }

    /**
     * 两个字符串相加结果四舍五入并保留两位有效数字
     *
     * @param one
     * @param two
     * @return
     */
    public static float addSFNumber(String one, String two) {
        BigDecimal b1;
        BigDecimal b2;
        if (!TextUtils.isEmpty(one)) {
            if (one.startsWith("￥")) {
                if (one.length() > 1) {
                    one = (one.split("￥"))[1];
                } else {
                    one = "0.00";
                }
            }
            if (one.endsWith("元")) {
                if (one.length() > 1) {
                    one = (one.split("元"))[1];
                } else {
                    one = "0.00";
                }
            }
            b1 = new BigDecimal(one);
        } else {
            b1 = new BigDecimal("0.00");
        }
        if (!TextUtils.isEmpty(two)) {
            if (two.startsWith("￥")) {
                if (two.length() > 1) {
                    two = (two.split("￥"))[1];
                } else {
                    two = "0.00";
                }
            }
            if (two.endsWith("元")) {
                if (two.length() > 1) {
                    two = (two.split("元"))[1];
                } else {
                    two = "0.00";
                }
            }
            b2 = new BigDecimal(two);
        } else {
            b2 = new BigDecimal("0.00");
        }
        float value = b1.add(b2).floatValue();//精度加法
        value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();//四舍五入
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(value);
        return Float.parseFloat(format);
    }

    /**
     * 将最大以小时的时间换算成毫秒
     *
     * @param time 时间格式字符串
     * @return 毫秒格式的时间
     */
    public static long formatTimeToMillions(String time) {
        Log.d("time_time", time);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        long millionSeconds;
        try {
            millionSeconds = sdf.parse(time).getTime();//毫秒
        } catch (ParseException e) {
            millionSeconds = 0;
        }
        Log.d("time_time", "" + millionSeconds);
        return millionSeconds;
    }

    /**
     * 将最大以小时的时间换算成毫秒
     *
     * @param time 时间格式字符串
     * @return 毫秒格式的时间
     */
    public static int formatTimeToMillion(String time) {
        Log.d("time_time", time);
        int millionSeconds;
        if (!TextUtils.isEmpty(time) && !TextUtils.equals(time, "0")) {
            if (time.contains(":") && time.length() == 8) {
                int hour;
                int million;
                int second;
                String hours = time.substring(0, 2);
                String millions = time.substring(3, 5);
                String seconds = time.substring(6, 8);
                if (hours.contains("00")) {
                    hour = 0;
                } else if (hours.contains("0")) {
                    hour = Integer.parseInt(hours.substring(1, 2));
                } else {
                    hour = Integer.parseInt(hours);
                }
                if (millions.contains("00")) {
                    million = 0;
                } else if (millions.contains("0")) {
                    million = Integer.parseInt(millions.substring(1, 2));
                } else {
                    million = Integer.parseInt(millions);
                }
                if (seconds.contains("00")) {
                    second = 0;
                } else if (seconds.contains("0")) {
                    second = Integer.parseInt(seconds.substring(1, 2));
                } else {
                    second = Integer.parseInt(seconds);
                }
                millionSeconds = hour * 60 * 60 * 1000 + million * 60 * 1000 + second * 1000;
            } else {
                millionSeconds = 0;
            }
        } else {
            millionSeconds = 0;
        }
        Log.d("time_time", "" + millionSeconds);
        return millionSeconds;
    }

    /**
     * 毫秒转化时分秒毫秒
     *
     * @param ms 毫秒格式的时间
     * @return 时间格式字符串
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
//        if(day > 0) {
//            sb.append(day+"天");
//        }
        if (hour > 0) {
            sb.append(hour + ":");
        } else {
            sb.append("00:");
        }
        if (minute > 0) {
            sb.append(minute + ":");
        } else {
            sb.append("00:");
        }
        if (second >= 10) {
            sb.append(second);
        } else if (second >= 0 && second < 10) {
            sb.append("0" + second);
        }
//        if(milliSecond > 0) {
//            sb.append(milliSecond+"毫秒");
//        }
        return sb.toString();
    }
}
