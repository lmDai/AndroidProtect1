package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.event.ItemEvent;
import com.cqyanyu.backing.ui.entity.home.TargetEntity;
import com.cqyanyu.backing.ui.entity.home.UserManageEntity;
import com.cqyanyu.backing.ui.holder.home.CheckPostHolder;
import com.cqyanyu.backing.ui.mvpview.home.CheckPostView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.mvpframework.X;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 一键查岗逻辑处理类
 * Created by Administrator on 2017/7/18.
 */
public class CheckPostPresenter extends XPagePresenter<CheckPostView> {
    private int pagecount = 20;
    private int pageindex = 0;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new CheckPostHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("unitid", CommonInfo.getInstance().getUserInfo().getUnitid());
        paramsMap.put("pageindex", pageindex + "");
        paramsMap.put("count", "" + pagecount);
        return paramsMap;
    }

    @Override
    protected String getURL() {
        return ConstHost.GET_UNIT_USER_PAGE_URL;
    }

    @Override
    protected Class getClazz() {
        return UserManageEntity.class;
    }

    @Override
    protected void setData(List mList) {
        if (pageindex == 0) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }

    @Override
    public void refresh() {
        getView().setClickAll(false);
        pageindex = 0;
        super.refresh();
    }

    @Override
    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                org.json.JSONObject object = new org.json.JSONObject(result);
                int total = object.optInt("total");
                int count = object.optInt("count");
                String trueResult = object.optString("rows");
                List mList = JSON.parseArray(trueResult, getClazz());
                if (mList != null && mList.size() > 0) {
                    /**显示数据*/
                    if (getView() != null) {
                        getView().hasShowData(true);
                    }
                    setData(mList);
                    /**设置当前页数*/
                    pageindex += count;
                    /**设置是否可以下拉加载*/
                    isLoad = pageindex < total;
                    return;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (pageindex == 0) onLoadNoData();
    }

    /**
     * 一键查岗
     */
    public void requestDutyLaunch() {
        if (getView() != null) {
            String target = getTarget();
            if (!TextUtils.isEmpty(target)) {
                ParamsMap paramsMap = new ParamsMap();
                paramsMap.put("launchmanid", CommonInfo.getInstance().getUserInfo().getUserid());//发起人ID
                paramsMap.put("launchunitid", CommonInfo.getInstance().getUserInfo().getUnitid());//发起人单位ID
                paramsMap.put("launchdate", MyDate.getNowTime());//发起时间
                paramsMap.put("targetinfo", target);//查岗对象
                XHttpUtils.post(context, paramsMap, ConstHost.COMMIT_DUTY_LAUNCH_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackString() {
                    @Override
                    public void onSuccess(String result) {
                        if (getView() != null) {
                            if (!TextUtils.isEmpty(result)) {
                                if (TextUtils.equals(result, "0")) {
                                    XToastUtil.showToast("提交失败");
                                } else {
                                    XToastUtil.showToast("提交成功");
                                    //更新上一个页面
                                    EventBus.getDefault().post(new ItemEvent(ItemEvent.ACTIVITY.DutyStatusActivity, ItemEvent.ACTION.refreshing));
                                    context.finish();
                                }
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

    /**
     * 获取查岗对象
     *
     * @return 查岗对象
     */
    private String getTarget() {
        try {
            List<UserManageEntity> mList = mRecyclerView.getAdapter().getData(0);
            if (mList != null && mList.size() > 0) {
                List<TargetEntity> tList = new ArrayList<>();
                for (UserManageEntity entity : mList) {
                    if (entity.isClickable()) {
                        tList.add(new TargetEntity(entity.getOid(), entity.getUnitid()));
                    }
                }
                if (tList.size() > 0) {
                    return X.json().getJSONString(tList);
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        XToastUtil.showToast("请选择查询对象");
        return "";
    }
}
