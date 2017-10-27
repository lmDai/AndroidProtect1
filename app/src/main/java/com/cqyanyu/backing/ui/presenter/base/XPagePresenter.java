package com.cqyanyu.backing.ui.presenter.base;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackString;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XLog;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 列表base
 * Created by Administrator on 2017/7/12.
 */
public abstract class XPagePresenter<T extends XPageView> extends BasePresenter<T> implements BGARefreshLayout.BGARefreshLayoutDelegate {
    protected XRecyclerView mRecyclerView;
    protected boolean isLoad;

    /**
     * 初始化
     */
    protected void setRecyclerView() {
        if (getView() != null) {
            if (mRecyclerView == null) {
                /**初始化mRecyclerView*/
                mRecyclerView = getView().getRecyclerView();
                /**为mRecyclerView设置监听*/
                mRecyclerView.setDelegate(this);
                refreshing();
            }
        }
    }

    /**
     * 刷新数据
     */
    protected void refreshing() {
        if (getView() != null) {
            /**开始加载数据*/
            mRecyclerView.beginRefreshing();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        //显示数据
        if (getView() != null) {
            refresh();
            mRecyclerView.getRefreshViewHolder().showLoadingMoreImg();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (getView() != null) {
            if (isLoad) {
                load();
                return true;
            }
            mRecyclerView.getRefreshViewHolder().hideLoadingMoreImg();
        }
        return false;
    }

    /**
     * 刷新数据
     */
    protected void refresh() {
        if (getView() != null) {
            getView().hasShowData(true);
            isLoad = false;
            load();
        }
    }

    /**
     * 加载
     */
    public void load() {
        //显示数据
        if (getView() != null) {
            XHttpUtils.post(context, getParamsMap(), getURL(), new XICallbackString() {
                @Override
                public void onSuccess(String result) {
                    if (getView() != null) onXSuccess(result);
                }

                @Override
                public void onFail(String msg) {
                    if (getView() != null) onXFail(msg);
                    XLog.e(msg);
                }

                @Override
                public void onFinished() {
                    if (getView() != null) onXFinished();
                }
            });
        }
    }

    protected void onXSuccess(String result) {
        try {
            if (!TextUtils.isEmpty(result)) {
                List mList = JSON.parseArray(result, getClazz());
                if (mList != null && mList.size() > 0) {
                    /**显示数据*/
                    if (getView() != null) getView().hasShowData(true);
                    setData(mList);
                    return;
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        onLoadNoData();
    }

    private void onXFail(String msg) {
        onLoadNoData();
        XLog.e(msg);
    }

    protected void onXFinished() {
        /**结束刷新*/
        if (getView() != null) {
            mRecyclerView.endRefreshing();
            mRecyclerView.endLoadingMore();
        }
    }

    protected void onLoadNoData() {
        /**清空数据*/
        mRecyclerView.getAdapter().setData(0, new ArrayList());
        /**显示加载失败*/
        getView().hasShowData(false);
    }

    public abstract void initPresenter();

    protected abstract ParamsMap getParamsMap();

    protected abstract String getURL();

    protected abstract Class getClazz();

    protected abstract void setData(List mList);
}
