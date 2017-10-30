package com.cqyanyu.backing.ui.presenter.home;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.CommonInfo;
import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.ui.entity.home.MyTaskEntity;
import com.cqyanyu.backing.ui.holder.home.MyTaskHolder;
import com.cqyanyu.backing.ui.mvpview.home.MyTaskView;
import com.cqyanyu.backing.ui.presenter.base.XPagePresenter;
import com.cqyanyu.backing.utils.MyDate;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 我的任务逻辑处理类
 * Created by Administrator on 2017/7/10.
 */
public class MyTaskPresenter extends XPagePresenter<MyTaskView> {
    private int page;
    private int pageSize = 20;

    @Override
    public void initPresenter() {
        if (getView() != null) {
            /**初始化RecyclerView*/
            setRecyclerView();
            /**为RecyclerView绑定数据*/
            mRecyclerView.getAdapter().bindHolder(new MyTaskHolder());
        }
    }

    @Override
    protected ParamsMap getParamsMap() {
        ParamsMap paramsMap = new ParamsMap();
        paramsMap.put("type", getView().getType());//类型  1：日巡 2：月检 3:年检
        paramsMap.put("inspectionmanid", CommonInfo.getInstance().getUserInfo().getUserid());//用户id
        paramsMap.put("date", (MyDate.getTodayZero() / 1000) + "");
        return paramsMap;
    }

    @Override
    public void refresh() {
        page = 0;
        super.refresh();
    }


    @Override
    protected String getURL() {
        return ConstHost.GET_USER_TASK_URL;
    }

    @Override
    protected Class getClazz() {
        return MyTaskEntity.class;
    }

    @Override
    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                List mList = JSON.parseArray(result, getClazz());
                if (mList != null && mList.size() > 0) {
                    /**设置当前页数*/
                    page = page + 1;
                    /**设置是否可以下拉加载*/
                    isLoad = page * pageSize < mList.size();
                    if (page * pageSize > mList.size()) {
                        setData(mList.subList((page - 1) * pageSize, mList.size()));
                    } else {
                        setData(mList.subList((page - 1) * pageSize, page * pageSize));
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        if (page == 1 || page == 0) onLoadNoData();
    }

    @Override
    protected void setData(List mList) {
        if (page == 1) {
            mRecyclerView.getAdapter().setData(0, mList);
        } else if (page > 1) {
            mRecyclerView.getAdapter().addDataAll(0, mList);
        }
    }
}
