package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.entity.home.HomeBean;
import com.cqyanyu.mvpframework.view.IBaseView;

import java.util.List;

/**
 * 首页页面接口view
 * Created by Administrator on 2017/7/7.
 */
public interface HomeView extends IBaseView {
    /**
     * 设置banner
     *
     * @param mList 数据
     */
    void setBanner(List<String> mList, List<String> hearList);

    /**
     * 设置首页功能列表
     *
     * @param mList 列表
     */
    void setHomeItem(List<HomeBean> mList);

    void setTotal(String total);

    void setClickRate(String rate);

    void setPrefectRate(String rate);

    void setFire(String fire);

    void setWarning(String warning);

    void setFault(String fault);
}
