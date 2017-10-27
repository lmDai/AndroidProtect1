package com.cqyanyu.backing.ui.mvpview.home;

import com.cqyanyu.backing.ui.entity.home.DetailsResourceEntity;
import com.cqyanyu.backing.ui.presenter.base.XPageView;

import java.util.List;

/**
 * 详细资源接口view
 * Created by Administrator on 2017/7/11.
 */
public interface DetailsResourceView extends XPageView {
    String getLabel();

    float getDistance();

    List<DetailsResourceEntity> getOtherResource();
}
