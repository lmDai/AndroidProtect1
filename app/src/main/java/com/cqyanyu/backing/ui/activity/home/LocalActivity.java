package com.cqyanyu.backing.ui.activity.home;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseMapActivity;
import com.cqyanyu.backing.ui.entity.home.DutyStatusEntity;
import com.cqyanyu.backing.ui.mvpview.home.LocalView;
import com.cqyanyu.backing.ui.presenter.home.LocalPresenter;
import com.cqyanyu.mvpframework.utils.XLog;

/**
 * 实时定位
 * Created by Administrator on 2017/7/18.
 */
public class LocalActivity extends BaseMapActivity<LocalPresenter> implements LocalView {
    private Button btnFinishLocal;

    @Override
    protected LocalPresenter createPresenter() {
        return new LocalPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_local;
    }

    @Override
    protected void initView() {
        mMapView = (TextureMapView) findViewById(R.id.map_view);
        btnFinishLocal = (Button) findViewById(R.id.btn_finish_local);
    }

    @Override
    protected void initListener() {
        btnFinishLocal.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish_local:
                finish();
                break;
        }
    }

    @Override
    public void onSetMarkOnMap(String name, double latitude, double longitude) {
        try {
            View viewCache = getLayoutInflater().inflate(R.layout.maker_name, null);
            TextView mapName = (TextView) viewCache.findViewById(R.id.tv_maker);
            mapName.setText(name);
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromView(viewCache);
            if (bitmap != null) {
                /**添加marker标记*/
                LatLng point = new LatLng(latitude, longitude);
                MarkerOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                Marker marker = (Marker) mBaiDuMap.addOverlay(option);
            }
        } catch (Exception e) {
            XLog.e("百度地图--->标记marker异常");
            e.getStackTrace();
        }
    }

    @Override
    public void setLocation(DutyStatusEntity dutyStatusEntity) {
        BDLocation location = new BDLocation();
        location.setLatitude(dutyStatusEntity.getLastanswerlatitude());
        location.setLongitude(dutyStatusEntity.getLastanswerlongitude());
        super.onDefaultLocation(location);
    }
}
