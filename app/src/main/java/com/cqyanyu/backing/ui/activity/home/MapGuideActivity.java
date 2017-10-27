package com.cqyanyu.backing.ui.activity.home;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseActivity;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XLog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 导航页面
 * Created by Administrator on 2017/7/24.
 */
public class MapGuideActivity extends BaseActivity {
    private static final String KEY_START_POINT = "起始点";
    private static final String KEY_END_POINT = "目的地";
    private static final int MSG_SHOW = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_RESET_NODE = 3;
    private RelativeLayout layout;
    private BaiduNaviCommonModule mBaiDuNavCommonModule;
    private BNRoutePlanNode mBNRoutePlanNodeStart;
    private BNRoutePlanNode mBNRoutePlanNodeEnd;
    private Handler handler;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_map_guide;
    }

    @Override
    protected void initView() {
        layout = (RelativeLayout) findViewById(R.id.layout);

        /**初始化导航模式*/
        View view = null;
        mBaiDuNavCommonModule = NaviModuleFactory.getNaviModuleManager().getNaviCommonModule(
                NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE, this,
                BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE,
                new BNRouteGuideManager.OnNavigationListener() {
                    @Override
                    public void onNaviGuideEnd() {
                        //退出导航
                        finish();
                    }

                    @Override
                    public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {
                        if (actionType == 0) {
                            //导航到达目的地 自动退出
                            XLog.i("notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
                        }
                        XLog.i("actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
                    }
                });
        /**初始化导航视图*/
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onCreate();
            view = mBaiDuNavCommonModule.getView();
        }
        /**添加导航视图*/
        if (view != null) {
            layout.addView(view);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Serializable serializableStart = getIntent().getSerializableExtra(KEY_START_POINT);
        Serializable serializableEnd = getIntent().getSerializableExtra(KEY_END_POINT);
        if (serializableStart != null) {
            mBNRoutePlanNodeStart = (BNRoutePlanNode) serializableStart;
        }
        if (serializableEnd != null) {
            mBNRoutePlanNodeEnd = (BNRoutePlanNode) serializableEnd;
        }
    }

    private void createHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MSG_SHOW) {
                        addCustomizedLayerItems();
                    } else if (msg.what == MSG_HIDE) {
                        BNRouteGuideManager.getInstance().showCustomizedLayer(false);
                    } else if (msg.what == MSG_RESET_NODE) {
                        BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                                new BNRoutePlanNode(116.21142, 40.85087, "百度大厦11", null, BNRoutePlanNode.CoordinateType.GCJ02));
                    }
                }
            };
        }
    }

    private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<>();
        BNRouteGuideManager.CustomizedLayerItem item1;
        if (mBNRoutePlanNodeStart != null) {
            item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNodeStart.getLongitude(), mBNRoutePlanNodeStart.getLatitude(),
                    mBNRoutePlanNodeStart.getCoordinateType(), getResources().getDrawable(R.mipmap.ic_launcher),
                    BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
            items.add(item1);

            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }

    @Override
    protected void onStart() {
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onStart();
        }
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mBaiDuNavCommonModule != null) {
            mBaiDuNavCommonModule.onBackPressed(true);
        }
        super.onBackPressed();
    }
}
