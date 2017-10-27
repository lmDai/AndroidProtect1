package com.cqyanyu.backing.ui.presenter.alarm;

import com.cqyanyu.backing.ConstHost;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.entity.home.UnitManageEntity;
import com.cqyanyu.backing.ui.mvpview.warn.WarnPositionView;
import com.cqyanyu.backing.ui.net.XHttpUtils;
import com.cqyanyu.backing.ui.net.XICallbackList;
import com.cqyanyu.backing.utils.NetDialogUtil;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;
import com.cqyanyu.mvpframework.utils.http.ParamsMap;

import java.util.List;

/**
 * 告警地址处理
 * Created by Administrator on 2017/7/7.
 */
public class PositionPresenter extends BasePresenter<WarnPositionView> {

    public void init() {
        getUnitList();
    }

    /**
     * 获取单位
     */
    private void getUnitList() {
        if (getView() != null) {
            ParamsMap paramsMap = new ParamsMap();
            paramsMap.put("oid", getView().getOid());
            XHttpUtils.post(context, paramsMap, ConstHost.GET_UNIT_INFO_URL, NetDialogUtil.showLoadDialog(context, R.string.text_request), new XICallbackList<UnitManageEntity>() {
                public void onSuccess(List<UnitManageEntity> mList) {
                    if (mList != null && mList.size() > 0) {
                        UnitManageEntity entity = mList.get(0);
                        getView().setMark(1,
                                entity.getUnitstr(),
                                entity.getPosition(),
                                entity.getLatitude(), entity.getLongitude());
                    }else {
                        getView().setMark(1,
                                getView().getBuildStr(),
                                getView().getPosition(),
                                getView().getLatitude(),  getView().getLongitude());
                    }
                }

                @Override
                public void onFail(String msg) {
                    XToastUtil.showToast("失败");
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
}
