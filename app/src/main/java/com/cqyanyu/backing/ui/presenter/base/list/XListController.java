package com.cqyanyu.backing.ui.presenter.base.list;

import com.alibaba.fastjson.JSON;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;
import com.cqyanyu.mvpframework.utils.http.XCallback;
import com.cqyanyu.mvpframework.view.recyclerView.XRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * 扩展的列表控制器
 * Created by Administrator on 2017/7/6.
 */
public class XListController implements BGARefreshLayout.BGARefreshLayoutDelegate {

    //属性
    private XRecyclerView mRecyclerView;
    private XListPresenter mPresenter;
    private int page = 1;
    private int total = 1;

    public XListController(XListPresenter mPresenter, XRecyclerView xRecyclerView) {
        this.mPresenter = mPresenter;
        this.mRecyclerView = xRecyclerView;
        this.mRecyclerView.setDelegate(this);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadData(1);
        mRecyclerView.getRefreshViewHolder().showLoadingMoreImg();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (page >= total) {
            mRecyclerView.getRefreshViewHolder().hideLoadingMoreImg();
            return false;
        } else {
            loadData(page + 1);
            return true;
        }
    }

    /**
     * 按页数加载数据
     *
     * @param nextPage 加载的页数
     */
    private void loadData(final int nextPage) {
        if (mPresenter != null && nextPage > 0) {
            mPresenter.loadMore(nextPage);
            //请求网络数据
            ParamsMap paramsMap = mPresenter.getParamsMap();
            if (paramsMap == null) paramsMap = new ParamsMap();
            paramsMap.put("page", "" + nextPage);
            mPresenter.fetch(paramsMap, mPresenter.getUrl(), new XCallback.XCallbackJson() {
                @Override
                public void onSuccess(JSONObject data) {
                    if (data.optInt("code") == 200) {
                        //此处处理数据
                        if (mPresenter != null) {
                            JSONObject objectA = data.optJSONObject("data");
                            JSONArray arrayA = data.optJSONArray("data");
                            if (objectA != null) {
                                if (objectA.optInt("page") > 0) {
                                    page = objectA.optInt("page");
                                    total = objectA.optInt("totalPage");
                                    if (page == 1) {
                                        mRecyclerView.getAdapter().removeAll();
                                    }
                                    JSONArray array = objectA.optJSONArray("data");
                                    if (array != null && array.length() > 0) {
                                        List list = JSON.parseArray(array.toString(), mPresenter.getClazz());
                                        mPresenter.loadData(list);
                                    } else {
                                        //刷新无数据
                                        if (nextPage == 1) mPresenter.hasData(false);
                                    }
                                } else {
                                    mPresenter.loadData(objectA);
                                }
                            } else if (arrayA != null) {
                                if (arrayA.length() > 0) {
                                    if (page == 1) {
                                        mRecyclerView.getAdapter().removeAll();
                                    }
                                    List list = JSON.parseArray(arrayA.toString(), mPresenter.getClazz());
                                    mPresenter.loadData(list);
                                } else {
                                    //刷新无数据
                                    if (nextPage == 1) mPresenter.hasData(false);
                                }
                            } else {
                                //刷新无数据
                                if (nextPage == 1) mPresenter.hasData(false);
                            }
                        }
                    } else {
                        XToastUtil.showToast(data.optString("msg"));
                        if (nextPage == 1 && mPresenter != null) {
                            //刷新无数据
                            mPresenter.hasData(false);
                        }
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("请刷新重试!");
                    if (nextPage == 1 && mPresenter != null) {
                        //刷新无数据
                        mPresenter.hasData(false);
                    }
                }

                @Override
                public void onFinished() {
                    if (mRecyclerView != null) {
                        //刷新和加载完成
                        mRecyclerView.endRefreshing();
                        mRecyclerView.endLoadingMore();
                        //更新数据
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
