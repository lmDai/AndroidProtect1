package com.cqyanyu.backing.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.cqyanyu.mvpframework.utils.XToastUtil;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 用户终端工具类
 * Created by Administrator on 2017/7/11.
 */
public class TerminalUtils {

    /**
     * 获取终端IP地址
     *
     * @param context Context
     * @return 终端IP地址
     */
    public static String getTerminalIP(Context context) {
        if (context != null) {
            try {
                NetworkInfo info = ((ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                        try {
                            //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                                NetworkInterface intf = en.nextElement();
                                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                    InetAddress inetAddress = enumIpAddr.nextElement();
                                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                        return inetAddress.getHostAddress();
                                    }
                                }
                            }
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }

                    } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        //得到IPV4地址
                        return intIP2StringIP(wifiInfo.getIpAddress());
                    }
                } else {
                    //当前无网络连接,请在设置中打开网络
                    XToastUtil.showToast("没有网络！请打开网络");
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip int类型IP
     * @return String类型的IP
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 设备开通WiFi连接，通过wifiManager获取Mac地址
     *
     * @param context Context
     * @return Mac地址
     */
    public static String getTerminalMac(Context context) {
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo.State wifiState = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                if (wifiState == NetworkInfo.State.CONNECTED) {//判断当前是否使用wifi连接
                    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    if (!wifiManager.isWifiEnabled()) { //如果当前wifi不可用
                        wifiManager.setWifiEnabled(true);
                    }
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    String macAddress = wifiInfo.getMacAddress();
                    //将":"换成"-"
                    if (macAddress != null) {
                        return macAddress.replaceAll(":", "-");
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    /**
     * @return 用户终端名称
     */
    public static String getTerminalName() {
        //1. 获取手机厂商：
        String brand = android.os.Build.BRAND;
        //2. 获取手机型号：
        String model = android.os.Build.MODEL;
        return brand + "[" + model + "]";
    }

    /**
     * 获取手机唯一码
     *
     * @param context Context
     * @return 手机唯一码
     */
    public static String getDeviceId(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }

    /**
     * @return 用户终端类型 1.android; 2.ios;3.PC
     */
    public static String getTerminalType() {
        return "1";
    }

    /**
     * 获取手机的MAC地址(终极方式)
     *
     * @return
     */
    public static String getMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if ("".equals(macSerial)) {
            try {
                String mac = loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
                return mac.replaceAll(":", "-");
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        //将":"换成"-"
        return macSerial.replaceAll(":", "-");
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 获取手机唯一码
     *
     * @return 手机唯一码
     */
    public static String getDeviceUnique() {
        String mac = "";
        String[] propertys = {"ro.boot.serialno", "ro.serialno"};
        for (String key : propertys) {
            String v = getAndroidOsSystemProperties(key);
            Log.e("", "get " + key + " : " + v);
            mac += v;
        }
        return mac;
    }

    /**
     * 获取Android手机的唯一码
     *
     * @param key
     * @return
     */
    private static String getAndroidOsSystemProperties(String key) {
        String ret;
        try {
            Method systemProperties_get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            if ((ret = (String) systemProperties_get.invoke(null, key)) != null)
                return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return "";
    }
}
