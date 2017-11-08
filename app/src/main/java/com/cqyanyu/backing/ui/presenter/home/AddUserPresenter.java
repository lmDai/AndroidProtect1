package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.activity.home.AddUserActivity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.mvpview.home.AddUserView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.backing.utils.SharedPreferencesUtils;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

/**
 * 增加用户逻辑处理类
 * Created by Administrator on 2017/7/11.
 */
public class AddUserPresenter extends BasePresenter<AddUserView> {
    private UserManageEntity entity;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddUserActivity.LABEL_VALUE_EDIT)) {
                getUserInfo();
            } else {
                getLastUserInfo();
            }
        }
    }

    private void getLastUserInfo() {
        String userUnit = (String) SharedPreferencesUtils.getParam(context, "userUnit", "", SharedPreferencesUtils.FILE_NAME_USER);//所属单位
        String userRole = (String) SharedPreferencesUtils.getParam(context, "userRole", "", SharedPreferencesUtils.FILE_NAME_USER);//用户角色
        String phone = (String) SharedPreferencesUtils.getParam(context, "linkPhone", "", SharedPreferencesUtils.FILE_NAME_USER);//联系电话
        String name = (String) SharedPreferencesUtils.getParam(context, "userName", "", SharedPreferencesUtils.FILE_NAME_USER);//用户名
        String unitstr = (String) SharedPreferencesUtils.getParam(context, "unitstr", "", SharedPreferencesUtils.FILE_NAME_USER);//单位名称
        getView().setUnit(userUnit, unitstr);
        getView().setRole(userRole);
        getView().setUsername(name);
        getView().setTelephone(phone);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        if (getView() != null) {
            //初始化数据
            if (TextUtils.equals(getView().getLabel(), AddUserActivity.LABEL_VALUE_EDIT)) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("userstr", getView().getNameUP());
                XHttpUtils.post(context, paramsMap, ConstHost.GET_USER_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackList<UserManageEntity>() {
                    @Override
                    public void onSuccess(List<UserManageEntity> mList) {
                        if (mList != null && mList.size() > 0) {
                            entity = mList.get(0);
                            if (entity != null) {
                                /**设置所属单位*/
                                getView().setUnit(entity.getUnitid(), entity.getUnitstr());
                                /**设置用户角色*/
                                getView().setRole(entity.getRoleid());
                                /**设置用户名*/
                                getView().setUsername(entity.getUserstr());
                                /**设置联系电话*/
                                getView().setTelephone(entity.getPhone());
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
                        return UserManageEntity.class;
                    }
                });
            } else if (TextUtils.equals(getView().getLabel(), AddUserActivity.LABEL_VALUE_ADD)) {
                entity = new UserManageEntity();
            }
        }
    }

    //增加用户
    public void addUserInfo() {
        if (getView() != null) {
            if (TextUtils.equals(getView().getLabel(), AddUserActivity.LABEL_VALUE_ADD)) {
                //当前页面是添加页面时
                final String phone = getView().getTelephone();
                final String name = getView().getUsername();
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(name)) {
                    ParamsMap paramsMap = new ParamsMap();
                    paramsMap.put("roleid", getView().getRole());//角色 ID
                    paramsMap.put("unitid", getView().getUnit());//所属单位 ID
                    paramsMap.put("phone", phone);//用户电话
                    paramsMap.put("userstr", name);//用户昵称
                    XHttpUtils.post(context, paramsMap, ConstHost.ADD_USER_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                        @Override
                        public void onSuccess(String result) {
                            if (getView() != null) {
                                if (!TextUtils.isEmpty(result)) {
                                    XToastUtil.showToast(result);
                                    //更新上一个页面
//                                    context.startActivity(new Intent(context, SystemManagementActivity.class)
//                                            .putExtra("curTab", 2));
//                                    context.finish();
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                    context.finish();
                                }
                            }
                        }

                        @Override
                        public void onFail(String msg) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            } else {
                XToastUtil.showToast("填写信息不完整！");
            }
        }
    }

    //修改用户信息
    public void modifyUserInfo() {
        if (getView() != null && entity != null) {
            if (TextUtils.equals(getView().getLabel(), AddUserActivity.LABEL_VALUE_EDIT) && entity != null) {
                if (TextUtils.equals(entity.getRoleid(), getView().getRole()) && TextUtils.equals(entity.getUnitid(), getView().getUnit())
                        && TextUtils.equals(entity.getPhone(), getView().getTelephone()) && TextUtils.equals(entity.getUserstr(), getView().getUsername())) {
                    XToastUtil.showToast("暂无修改任何数据");
                } else {
                    //当前页面是编辑页面时
                    ParamsMap paramsMap = new ParamsMap();
                    paramsMap.put("oid", entity.getOid());//用户 ID
                    paramsMap.put("roleid", getView().getRole());
                    paramsMap.put("userstr", getView().getUsername());//创建人 ID
                    paramsMap.put("unitid", getView().getUnit());//单位
                    paramsMap.put("phone", getView().getTelephone());//用户电话
                    // paramsMap.put("remark", TextUtils.isEmpty(entity.getRemark()) ? null : entity.getRemark());//备注
                    XHttpUtils.post(context, paramsMap, ConstHost.MODIFY_USER_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                        @Override
                        public void onSuccess(String result) {
                            if (getView() != null) {
                                if (!TextUtils.isEmpty(result)) {
//                                if (TextUtils.equals(result, "0")) {
//                                    XToastUtil.showToast("修改失败");
//                                } else {
                                    XToastUtil.showToast(result);
                                    //更新上一个页面
//                                context.startActivity(new Intent(context, SystemManagementActivity.class)
//                                        .putExtra("curTab", 2));
//                                context.finish();
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                    context.finish();
//                                }
                                }
                            }
                        }

                        @Override
                        public void onFail(String msg) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
            }
        }
    }

    //删除用户信息
    public void deleteUserInfo() {
        if (getView() != null && entity != null) {
            if (TextUtils.equals(getView().getLabel(), AddUserActivity.LABEL_VALUE_EDIT) && entity != null) {
                //当前页面是编辑页面时
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("oid", entity.getOid());
                paramsMap.put("userstr", entity.getUserstr());
                paramsMap.put("unitid", entity.getUnitid());//所属单位 ID
                XHttpUtils.post(context, paramsMap, ConstHost.DELETE_USER_URL, NetDialogUtil.showLoadDialog(context, R.string.text_loading), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
//                                if (TextUtils.equals(result, "0")) {
//                                    XToastUtil.showToast("删除失败");
//                                } else {
                                XToastUtil.showToast(result);
                                //更新上一个页面
//                                context.startActivity(new Intent(context, SystemManagementActivity.class)
//                                        .putExtra("curTab", 2));
//                                context.finish();
                                EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.SystemManagementFragment, ItemEvent.ACTION.refreshing));
                                context.finish();
//                                }
                            }
                        }
                    }

                    @Override
                    public void onFail(String msg) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }
    }

    //获取当前系统时间秒数
    private String getNowTime() {
        Date date = new Date();
        long minute = date.getTime() / 1000;
        return minute + "";
    }
}
