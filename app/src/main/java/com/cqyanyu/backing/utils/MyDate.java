package com.cqyanyu.backing.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 时间处理类
 * Created by Administrator on 2017/7/18.
 */
public class MyDate {

    /**
     * 获取指定时间格式
     *
     * @param minute 分钟
     * @return 时间
     */
    public static String getFormatDate(SimpleDateFormat format, String minute) {
        try {
            long ss = Long.parseLong(minute);
            return getFormatDate(format, ss);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }

    /**
     * 获取指定时间格式
     *
     * @param minute 分钟
     * @return 时间
     */
    public static String getFormatDate(String minute) {
        try {
            long ss = Long.parseLong(minute);
            return getFormatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()), ss);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }

    /**
     * 获取指定时间格式
     *
     * @param minute 分钟
     * @return 时间
     */
    public static String getFormatDate(long minute) {
        return getFormatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()), minute);
    }

    /**
     * 获取指定时间格式
     *
     * @param minute 分钟
     * @return 时间
     */
    public static String getFormatDateX(long minute) {
        return getFormatDate(new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault()), minute);
    }

    /**
     * 获取指定时间格式
     *
     * @param minute 分钟
     * @return 时间
     */
    private static String getFormatDate(SimpleDateFormat format, long minute) {
        try {
            Date date = new Date();
            date.setTime(minute * 1000);
            return format.format(date);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }

    /**
     * 获取当前时间
     *
     * @return 时间
     */
    public static String getNowTime() {
        return "" + getTime();
    }

    /**
     * 获取当前时间
     *
     * @return 时间
     */
    private static long getTime() {
        Date date = new Date();
        return date.getTime() / 1000;
    }

    /**
     * 获取指定格式的时间
     *
     * @param format SimpleDateFormat
     * @param time   时间
     * @return long
     */
    public static String getTime(String format, String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            Date date = formatter.parse(time);
            long timeTwice = date.getTime() / 1000;
            return "" + timeTwice;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当天凌晨的时间
     *
     * @return 时间
     */
    public static long getTodayZero() {
        Date date = new Date();
        long l = 24 * 60 * 60 * 1000;
        //每天的毫秒数 //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，
        // 不过这个毫秒数是UTC+0时区的。 //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime() % l) - 8 * 60 * 60 * 1000);
    }

    /**
     * 获取本月第一天零点
     *
     * @return
     */
    public static long getFirstOfMonthZero() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static long getEndOfMonthZero() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DATE));//设置为1号,当前日期既为本月第一天
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至0
        c.set(Calendar.MINUTE, 59);
        //将秒至0
        c.set(Calendar.SECOND, 59);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获取本年第一天零点
     *
     * @return
     */
    public static long getFirstOfYearZero() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, 1);
        return c.getTimeInMillis();
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType);
        return date;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    /**
     * 某一个月第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -0);
        Date theDate = calendar.getTime();

        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }

    public static long GetSystemSeconds() {
        return getTime();
    }
}
