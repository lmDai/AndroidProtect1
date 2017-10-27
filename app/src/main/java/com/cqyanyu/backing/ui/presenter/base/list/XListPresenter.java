package com.cqyanyu.backing.ui.presenter.base.list;

import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.json.JSONObject;

import java.util.List;

/**
 * 拓展的列表逻辑处理类
 * Created by Administrator on 2017/6/9 0009.
 */
public abstract class XListPresenter<T extends XListView> extends BasePresenter<T> {
    private XListController mController;
    private XRecyclerView mRecyclerView;

    /**
     * 初始化
     */
    public void init() {
        if (getView() != null) {
            if (mRecyclerView == null) {
                mRecyclerView = getView().getRecyclerView();
                mController = new XListController(this, mRecyclerView);
            }
            mRecyclerView.beginRefreshing();
        }
    }

    /**
     * 加载数据
     *
     * @param page 加载页数
     */
    public void loadMore(int page) {

    }

    /**
     * 加载数据
     *
     * @param data 数据
     */
    public void loadData(JSONObject data) {

    }

    /**
     * 加载数据
     *
     * @param data 数据
     */
    public void loadData(List data) {

    }

    /**
     * 是否有数据
     *
     * @param isHas 是否有数据
     */
    public void hasData(boolean isHas) {
        if (getView() != null) getView().setNoData(isHas);
    }

    /**
     * 获取请求地址
     *
     * @return 请求地址
     */
    public abstract String getUrl();

    /**
     * 获取请求参数
     *
     * @return 请求参数
     */
    public abstract ParamsMap getParamsMap();

    /**
     * 获取解析实体类
     *
     * @return 实体类
     */
    public abstract Class getClazz();
}
