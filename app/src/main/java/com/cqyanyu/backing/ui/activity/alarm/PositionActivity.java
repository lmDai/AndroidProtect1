package com.cqyanyu.backing.ui.activity.alarm;

import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseMapActivity;
import com.cqyanyu.backing.ui.entity.home.MarkInfo;
import com.cqyanyu.backing.ui.mvpview.warn.WarnPositionView;
import com.cqyanyu.backing.ui.presenter.alarm.PositionPresenter;

/**
 * 告警位置地图标注
 * Created by Administrator on 2017/8/9.
 */

public class PositionActivity extends BaseMapActivity<PositionPresenter> implements WarnPositionView {
    @Override
    protected PositionPresenter createPresenter() {
        return new PositionPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_warn_position;
    }

    @Override
    protected void initView() {
        mMapView = (TextureMapView) findViewById(R.id.map_view);
        /**初始化导航*/
        initNavi();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.init();
    }


    @Override
    public void setMark(int type, String name, String describe, double latitude, double longitude) {
        super.onSetMarkOnMap(latitude, longitude, R.mipmap.ic_fangzi,
                new MarkInfo(name, describe, latitude, longitude));
        LatLng ll = new LatLng(latitude, longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15.0f);
        mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        addTextOnMark(name, describe, latitude, longitude);
    }

    @Override
    public void addTextOnMark(final String name, final String describe, final double latitude, final double longitude) {
        //显示一个弹出框
        View viewCache = getLayoutInflater().inflate(R.layout.maker_goto_here, null);
        TextView mapName = (TextView) viewCache.findViewById(R.id.tv_maker);
        mapName.setText(name);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(viewCache);
        LatLng latLng = new LatLng(latitude, longitude);
        InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                if (isLocal()) {
                    routePlanToNav(new LatLng(latitude, longitude));
                }
            }
        };
        InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -40, listener);
        mBaiDuMap.showInfoWindow(infoWindow);
    }

    @Override
    public String getOid() {
        return getIntent().getStringExtra("oid");
    }

    @Override
    public String getBuildStr() {
        return getIntent().getStringExtra("buildStr");
    }

    @Override
    public String getPosition() {
        return getIntent().getStringExtra("position");
    }

    @Override
    public double getLatitude() {
        return getIntent().getDoubleExtra("latitude", 0);
    }

    @Override
    public double getLongitude() {
        return getIntent().getDoubleExtra("longitude", 0);

    }
}
