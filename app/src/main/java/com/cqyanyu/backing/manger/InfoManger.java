package com.cqyanyu.backing.manger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.EntryEntity;
import com.cqyanyu.backing.ui.entity.home.ListBean;
import com.cqyanyu.backing.ui.entity.home.ProblemEntity;
import com.cqyanyu.backing.ui.entity.home.SetManageEntity;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.entity.login.UserInfo;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackEntity;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.backing.utils.TerminalUtils;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息管理
 * Created by Administrator on 2017/7/21.
 */
public class InfoManger {
    private static final boolean isUsePermission = true;//是否使用后台权限
    public static final String KEY_ENTRY_UNIT_TYPE = "单位类型";
    public static final String KEY_ENTRY_ROLE = "用户角色";
    public static final String KEY_ENTRY_SET_TYPE = "设备类型";
    public static final String KEY_ENTRY_REGULATORY = "监管等级";
    public static final String KEY_ENTRY_INSPECTION_WAY = "巡检方式";
    public static final String KEY_ENTRY_INSPECTION_UNIT = "维保单位";
    private static final String KEY_ENTRY_F = "告警处理";
    private static final String KEY_ENTRY_FIRE_HANDLE = "火警处理";
    private static final String KEY_ENTRY_FAULT_HANDLE = "故障处理";
    private static final String KEY_ENTRY_WARNING_HANDLE = "预警处理";
    private static final String KEY_ENTRY_OTHER_HANDLE = "其他告警处理";
    private static final String KEY_ENTRY_G = "巡检问题提交";
    private static final String KEY_ENTRY_H = "巡检问题处理";
    public static final String KEY_ENTRY_FIRE_HYDRANT = "消防栓";
    private static final String KEY_IMAGE_KEEP = "本地图片地址保存";
    private static final InfoManger manger = new InfoManger();
    private String allEntryStr;//保存着所有词条的信息(不分类)
    private String myUnitSrt;//保存着本单位的单位信息
    private String subUnitSrt;//保存着下单位所有单位的信息
    private String setSrt;//保存着本单位的设备信息
    private String myUserSrt;//保存着本单位的个人信息
    private String subUserSrt;//保存着下单位所有个人的信息
    private String pushXG;//信鸽推送token
    private String pushHw;//华为推送设备IMEI
    private String pushXM;//小米推送
    private String pushMZ;//魅族推送
    private String allProblem;//保存所有问题

    private InfoManger() {
    }

    public static InfoManger getInstance() {
        return manger;
    }

    /**
     * 获取指定pid的
     *
     * @param key 特定字段
     * @return pid
     */
    public String getEntryPid(String key) {
        switch (key) {
            case KEY_ENTRY_UNIT_TYPE:
                return "1";
            case KEY_ENTRY_ROLE:
                return "2";
            case KEY_ENTRY_SET_TYPE:
                return "3";
            case KEY_ENTRY_REGULATORY:
                return "4";
            case KEY_ENTRY_INSPECTION_WAY:
                return "49";
            case KEY_ENTRY_F:
                return "69";
            case KEY_ENTRY_G:
                return "73";
            case KEY_ENTRY_H:
                return "75";
            case KEY_ENTRY_INSPECTION_UNIT:
                return "126";
            case KEY_ENTRY_FIRE_HYDRANT:
                return "22";
            case KEY_ENTRY_FIRE_HANDLE:
                return "70";
            case KEY_ENTRY_FAULT_HANDLE:
                return "71";
            case KEY_ENTRY_WARNING_HANDLE:
                return "72";
            case KEY_ENTRY_OTHER_HANDLE:
                return "153";
        }
        return "";
    }

    //FIXME 1.词条处理类====================================================================================================================================================//

    /**
     * 从网络中获取所有词条
     *
     * @param context 环境
     */
    public void getEntry(Context context) {
        if (context != null) {
            XHttpUtils.post(context, new ParamsMap(), ConstHost.GET_ENTRY_URL, new XICallbackList<EntryEntity>() {

                @Override
                public void onSuccess(List<EntryEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        /**1.数据不分类处理*/
                        allEntryStr = JSON.toJSONString(mList);
                        /**2.为数据分类处理(暂不分类)*/
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("词条获取失败!");
                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return EntryEntity.class;
                }
            });
        }
    }

    /**
     * 获得所有词条列表
     *
     * @param context 环境
     * @return 所有词条列表
     */
    private List<EntryEntity> getAllEntryList(Context context) {
        List<EntryEntity> entry = null;
        if (!TextUtils.isEmpty(allEntryStr)) {
            try {
                /**解析数据*/
                entry = JSON.parseArray(allEntryStr, EntryEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
            XLog.e("获取词条信息-->");
        }
        return entry;
    }

    /**
     * 获取弹出框数据，数据是来自于词典
     *
     * @param context 环境
     * @param key     父级词条的id 相对应的key
     * @return 弹出框数据
     */
    public List<ListBean> getListBeanFromEntryOfKey(Context context, String key) {
        return getListBeanFromEntry(context, getEntryPid(key));
    }

    /**
     * 获取弹出框数据，数据是来自于词典
     *
     * @param context 环境
     * @param pid     父级词条的id
     * @return 弹出框数据
     */
    private List<ListBean> getListBeanFromEntry(Context context, String pid) {
        List<EntryEntity> entry = getEntryListOfPid(context, pid);
        if (entry != null && entry.size() > 0) {
            List<ListBean> mList = new ArrayList<>();
            for (EntryEntity entity : entry) {
                mList.add(new ListBean(entity.getOid(), entity.getName()));
            }
            return mList;
        }
        return null;
    }

    /**
     * 通过oid获取词条的名称
     *
     * @param oid 词条的id
     * @return 词条的名称
     */
    public String getEntryName(Context context, String oid) {
        List<EntryEntity> entry = getAllEntryList(context);
        if (entry != null) {
            for (EntryEntity entity : entry) {
                if (TextUtils.equals(entity.getOid(), oid)) {
                    return entity.getName();
                }
            }
        }
        return oid;
    }

    /**
     * 通过oid获取词条的父id
     *
     * @param oid 词条的id
     * @return 词条的父id
     */
    public String getEntryPid(Context context, String oid) {
        List<EntryEntity> entry = getAllEntryList(context);
        if (entry != null) {
            for (EntryEntity entity : entry) {
                if (TextUtils.equals(entity.getOid(), oid)) {
                    return entity.getPid();
                }
            }
        }
        return oid;
    }

    /**
     * 根据pid 获取词条列表
     *
     * @param context 环境
     * @param pid     父词条的id
     * @return 词条列表
     */
    public List<EntryEntity> getEntryListOfPid(Context context, String pid) {
        List<EntryEntity> allEntryList = getAllEntryList(context);
        List<EntryEntity> entryList = new ArrayList<>();
        if (allEntryList != null) {
            for (EntryEntity entry : allEntryList) {
                if (TextUtils.equals(entry.getPid(), pid)) {
                    entryList.add(entry);
                }
            }
        }
        return entryList;
    }

    //FIXME 2.单位处理类====================================================================================================================================================//

    /**
     * 从网络中获取本单位信息
     *
     * @param context 环境
     */
    public void getMyUnitOnNet(Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("oid", CommonInfo.getInstance().getUserInfo().getUnitid());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_INFO_URL, new XICallbackList<UnitManageEntity>() {
                @Override
                public void onSuccess(List<UnitManageEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        UnitManageEntity entity = mList.get(0);
                        if (entity != null) {
                            //保存本单位信息
                            myUnitSrt = JSON.toJSONString(entity);
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("单位获取失败11!");
                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return UnitManageEntity.class;
                }
            });
        }
    }

    /**
     * 从网络中获取下级单位信息(TODO 此处应该获取所有的单位信息)
     *
     * @param context 环境
     */
    public void getSubUnitOnNet(Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("pid", CommonInfo.getInstance().getUserInfo().getUnitid());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_PAGE_URL, new XICallbackString() {

                @Override
                public void onSuccess(String result) {
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            JSONObject object = new JSONObject(result);
                            int total = object.optInt("total");
                            int count = object.optInt("count");
                            if (total > 0) {
                                subUnitSrt = object.optString("rows");
                            }
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("单位获取失败22!");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 获取本单位列表
     *
     * @param context 环境
     * @return 本单位列表
     */
    private List<UnitManageEntity> getSubUnitList(Context context) {
        List<UnitManageEntity> mList = null;
        if (!TextUtils.isEmpty(subUnitSrt)) {
            try {
                /**解析数据*/
                mList = JSON.parseArray(subUnitSrt, UnitManageEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
            XLog.e("获取单位信息-->");
        }
        return mList;
    }

    /**
     * 获取本用户信息
     *
     * @param context 环境
     * @return 本用户信息
     */
    public UnitManageEntity getMyUnit(Context context) {
        UnitManageEntity entity = null;
        if (!TextUtils.isEmpty(myUnitSrt)) {
            try {
                /**解析数据*/
                entity = JSON.parseObject(myUnitSrt, UnitManageEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
            XLog.e("获取本用户信息-->");
        }
        return entity;
    }

    /**
     * 通过oid获取单位名称
     *
     * @param context 环境
     * @param oid     单位id
     * @return 单位名称
     */
    public String getUnitNameOfOid(Context context, String oid) {
        List<UnitManageEntity> unitList = getSubUnitList(context);
        if (unitList != null) {
            for (UnitManageEntity entity : unitList) {
                if (TextUtils.equals(entity.getOid(), oid)) {
                    return entity.getUnitstr();
                }
            }
        }
        //再查找本人是否符合
        UnitManageEntity entity = getMyUnit(context);
        if (entity != null) {
            if (TextUtils.equals(entity.getOid(), oid)) {
                return entity.getUnitstr();
            }
        }
        return oid;
    }

    /**
     * 通过pid获取上级单位名称
     *
     * @param context 环境
     * @param pid     上级单位pid
     * @return 上级单位名称
     */
    public String getUnitNameOfPid(Context context, String pid) {
        List<UnitManageEntity> unitList = getSubUnitList(context);
        if (unitList != null) {
            for (UnitManageEntity entity : unitList) {
                if (TextUtils.equals(entity.getPid(), pid)) {
                    return entity.getName();
                }
            }
        }
        //再查找本人是否符合
        UnitManageEntity entity = getMyUnit(context);
        if (entity != null) {
            if (TextUtils.equals(entity.getOid(), pid)) {
                return entity.getName();
            }
        }
        return pid;
    }

    //FIXME 3.设备处理类====================================================================================================================================================//

    /**
     * 从网络中获取设备信息(TODO 此处应该获取所有的单位信息)
     *
     * @param context 环境
     */
    public void getSetListOnNet(Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("pid", CommonInfo.getInstance().getUserInfo().getUnitid());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_DEVICE_PAGE_URL, new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            JSONObject object = new JSONObject(result);
                            int total = object.optInt("total");
                            int count = object.optInt("count");
                            if (total > 0) {
                                setSrt = object.optString("rows");
                            }
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("单位获取失败33!");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 获取单位所有的设备
     *
     * @param context 环境
     * @return 本单位设备列表
     */
    private List<SetManageEntity> getAllSetList(Context context) {
        List<SetManageEntity> mList = null;
        if (!TextUtils.isEmpty(setSrt)) {
            try {
                /**解析数据*/
                mList = JSON.parseArray(setSrt, SetManageEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
        }
        return mList;
    }

    /**
     * 通过设备id从设备列表中获取位置信息
     *
     * @param context 环境
     * @param setId   设备id
     * @return 位置信息
     */
    public String getPositionOfSet(Context context, String setId) {
        List<SetManageEntity> mList = getAllSetList(context);
        if (mList != null) {
            for (SetManageEntity entity : mList) {
                if (TextUtils.equals(entity.getOid(), setId)) {
                    return entity.getPosition();
                }
            }
        }
        return "";
    }

    /**
     * 通过设备id从设备列表中获取设备名称
     *
     * @param context 环境
     * @param setId   设备id
     * @return 设备名称
     */
    public String getSetName(Context context, String setId) {
        List<SetManageEntity> mList = getAllSetList(context);
        if (mList != null) {
            for (SetManageEntity entity : mList) {
                if (TextUtils.equals(entity.getOid(), setId)) {
                    return getEntryName(context, entity.getTypeid());
                }
            }
        }
        return "";
    }

    /**
     * 通过设备id从设备列表中获取设备编号
     *
     * @param context 环境
     * @param setId   设备id
     * @return 设备名称
     */
    public String getSetSN(Context context, String setId) {
        List<SetManageEntity> mList = getAllSetList(context);
        if (mList != null) {
            for (SetManageEntity entity : mList) {
                if (TextUtils.equals(entity.getOid(), setId)) {
                    return getEntryName(context, entity.getSn());
                }
            }
        }
        return "";
    }

    //FIXME 4.用户处理类====================================================================================================================================================//

    /**
     * 从网络中获取本信息
     *
     * @param context 环境
     */
    public void getMyUserInfoOnNet(Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("userstr", CommonInfo.getInstance().getUserInfo().getPhone());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_USER_URL, new XICallbackList<UserManageEntity>() {
                @Override
                public void onSuccess(List<UserManageEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        UserManageEntity entity = mList.get(0);
                        if (entity != null) {
                            //保存本单位信息
                            myUserSrt = JSON.toJSONString(entity);
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("用户信息获取失败!");
                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return UserManageEntity.class;
                }
            });
        }
    }

    /**
     * 从网络中获取下级单位用户信息
     *
     * @param context 环境
     */
    public void getSubUserInfoOnNet(Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_USER_PAGE_URL, new XICallbackString() {

                @Override
                public void onSuccess(String result) {
                    try {
                        if (!TextUtils.isEmpty(result)) {
                            JSONObject object = new JSONObject(result);
                            int total = object.optInt("total");
                            int count = object.optInt("count");
                            if (total > 0) {
                                subUserSrt = object.optString("rows");
                            }
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("用户信息获取失败!");
                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    /**
     * 获取所有用户信息
     *
     * @param context 环境
     * @return 本单位列表
     */
    private List<UserManageEntity> getSubUserInfo(Context context) {
        List<UserManageEntity> mList = null;
        if (!TextUtils.isEmpty(subUserSrt)) {
            try {
                /**解析数据*/
                mList = JSON.parseArray(subUserSrt, UserManageEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
        }
        return mList;
    }

    /**
     * 获取本用户信息
     *
     * @param context 环境
     * @return 本用户信息
     */
    public UserManageEntity getMyUserInfo(Context context) {
        UserManageEntity entity = null;
        if (!TextUtils.isEmpty(myUserSrt)) {
            try {
                /**解析数据*/
                entity = JSON.parseObject(myUserSrt, UserManageEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
        }
        return entity;
    }

    /**
     * 通告oid获取用户名称
     *
     * @param context 环境
     * @param oid     用户id
     * @return 用户名称
     */
    public String getUserName(Context context, String oid) {
        //先查找下级用户
        List<UserManageEntity> unitList = getSubUserInfo(context);
        if (unitList != null) {
            for (UserManageEntity entity : unitList) {
                if (TextUtils.equals(entity.getOid(), oid)) {
                    return entity.getName();
                }
            }
        }
        //再查找本人是否符合
        UserManageEntity entity = getMyUserInfo(context);
        if (entity != null) {
            if (TextUtils.equals(entity.getOid(), oid)) {
                return entity.getName();
            }
        }
        return "";
    }

    //FIXME 5.过期处理类====================================================================================================================================================//

    /**
     * 重新登录
     *
     * @param context 环境
     */
    public void reLogin(Context context) {
        if (context != null) {
            ParamsMap paramsMap = new ParamsMap();
            //设置用户名和密码
            final String phone = CommonInfo.getInstance().getUserInfo().getPhone();
            final String password = CommonInfo.getInstance().getUserInfo().getPassword();
            paramsMap.put("loginname", phone);//用户登录名或手机号
            paramsMap.put("loginpwd", password);//用户登录密码
            paramsMap.put("terminalname", TerminalUtils.getTerminalName());//用户终端型号
            paramsMap.put("terminalcode", TerminalUtils.getDeviceUnique());//用户终端唯一码
            paramsMap.put("terminaltype", TerminalUtils.getTerminalType());//用户终端类型
            XHttpUtils.post(context, paramsMap, ConstHost.LOGIN_URL, new XICallbackEntity<UserInfo>() {
                @Override
                public void onSuccess(UserInfo entity) {
                    if (entity != null) {
                        if (entity.isResult()) {
                            //保存用户信息
                            entity.setPhone(phone);
                            entity.setPassword(password);
                            X.user().setUserInfo(entity);
                            return;
                        }
                    }
                }

                @Override
                public void onFail(String msg) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return UserInfo.class;
                }
            });
        }

    }

    //FIXME 5.权限查询类====================================================================================================================================================//

    /**
     * 通过权限id判断改功能是否拥有权限
     *
     * @param menuId 权限id
     * @return 是否有权限
     */
    public boolean isPermission(String menuId) {
        /**判断是否使用网络权限*/
        if (isUsePermission) {
            /**获取权限信息*/
            List<UserInfo.PermissionBean> permissionList = CommonInfo.getInstance().getUserInfo().getPermission();
            for (UserInfo.PermissionBean permission : permissionList) {
                if (TextUtils.equals(permission.getMenuid(), menuId)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    //FIXME 6.图片查询类====================================================================================================================================================//

    /**
     * 图片地址保存
     *
     * @param context 环境
     * @param url     网络地址
     * @param path    本地地址
     */
    public void putImageUrl(Context context, String url, String path) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(path) && context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_IMAGE_KEEP, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(url, path);
            edit.apply();
        }
    }

    /**
     * 图片地址获取
     *
     * @param context 环境
     * @param url     网络地址
     * @return 本地地址
     */
    public String getImagePath(Context context, String url) {
        if (!TextUtils.isEmpty(url) && context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_IMAGE_KEEP, Context.MODE_PRIVATE);
            return sharedPreferences.getString(url, "");
        }
        return "";
    }

    //FIXME 7.推送查询类====================================================================================================================================================//

    /**
     * 获取本设备的token
     *
     * @return 唯一标识
     */
    public String getPushToken() {
        switch (getPushType()) {
            case "1"://信鸽推送
                return pushXG;
            case "2"://华为推送
                return pushHw;
            case "3"://小米推送
                return pushXM;
            case "4"://魅族推送
                return pushMZ;
            default:
                return "";
        }
    }

    /**
     * 设置推送的token
     *
     * @param token 唯一标识
     */
    public void setPushToken(String token) {
        switch (getPushType()) {
            case "1"://信鸽推送
                pushXG = token;
                break;
            case "2"://华为推送
                pushHw = token;
                break;
            case "3"://小米推送
                pushXM = token;
                break;
            case "4"://魅族推送
                pushMZ = token;
                break;
        }
    }

    /**
     * 获取推送类型
     *
     * @return type:1 信鸽推送,2 华为推送,3 小米推送,4 魅族推送
     */
    public String getPushType() {
        /**判断手机厂商并转换为大写字母*/
        String brand = Build.BRAND;
        String newBrand = brand.toUpperCase();
        switch (newBrand) {
            case "HUAWEI"://华为推送
                return "2";
            case "XIAOMI"://小米推送
                return "3";
            case "MEIZU"://魅族推送
                return "4";
            default://信鸽推送
                return "1";
        }
    }

    /**
     * 根据pid 获取词条列表
     *
     * @param context 环境
     * @return 词条列表
     */
    public List<ProblemEntity> getProblemOfId(Context context) {
        List<ProblemEntity> allEntryList = getAllProblems(context);
        return allEntryList;
    }

    /**
     * 获得所有词条列表
     *
     * @param context 环境
     * @return 所有词条列表
     */
    private List<ProblemEntity> getAllProblems(Context context) {
        List<ProblemEntity> entry = null;
        if (!TextUtils.isEmpty(allProblem)) {
            try {
                /**解析数据*/
                entry = JSON.parseArray(allProblem, ProblemEntity.class);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            //初始化数据
            context.sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                    .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_INIT)
            );
        }
        return entry;
    }

    /**
     * 从网络中获取所有词条
     *
     * @param context 环境
     */
    public void getProblem(Context context) {
        if (context != null) {
            XHttpUtils.post(context, new ParamsMap(), ConstHost.GET_ALL_PROBLEM, new XICallbackList<ProblemEntity>() {

                @Override
                public void onSuccess(List<ProblemEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        /**1.数据不分类处理*/
                        allProblem = JSON.toJSONString(mList);
                        /**2.为数据分类处理(暂不分类)*/
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("词条获取失败!");
                }

                @Override
                public void onFinished() {

                }

                @Override
                public Class getClazz() {
                    return ProblemEntity.class;
                }
            });
        }
    }
}
