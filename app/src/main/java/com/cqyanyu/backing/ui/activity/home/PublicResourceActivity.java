package com.cqyanyu.backing.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.manger.InfoManger;
import com.cqyanyu.backing.ui.activity.base.BaseMapActivity;
import com.cqyanyu.backing.ui.entity.home.MarkInfo;
import com.cqyanyu.backing.ui.entity.home.PublicResourceEntity;
import com.cqyanyu.backing.ui.mvpview.home.PublicResourceView;
import com.cqyanyu.backing.ui.presenter.home.PublicResourcePresenter;
import com.cqyanyu.backing.ui.server.MyServer;
import com.cqyanyu.mvpframework.utils.XToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 公共资源
 * Created by Administrator on 2017/7/7.
 */
public class PublicResourceActivity extends BaseMapActivity<PublicResourcePresenter> implements PublicResourceView, CompoundButton.OnCheckedChangeListener {
    private Button btnWater;
    private CheckBox btnOther, btnFire, btnBuild;
    private BDLocation location;
    private List<PublicResourceEntity> bList = new ArrayList<>();

    @Override
    protected PublicResourcePresenter createPresenter() {
        return new PublicResourcePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_public_resource;
    }

    @Override
    protected void initView() {
        btnWater = (Button) findViewById(R.id.btn_water);
        btnOther = (CheckBox) findViewById(R.id.btn_other);
        btnBuild = (CheckBox) findViewById(R.id.btn_build);
        btnFire = (CheckBox) findViewById(R.id.btn_fire);
        mMapView = (TextureMapView) findViewById(R.id.map_view);
        /**初始化导航*/
        initNavi();
    }

    @Override
    protected void initListener() {
        btnWater.setOnClickListener(this);
        btnOther.setOnCheckedChangeListener(this);
        btnBuild.setOnCheckedChangeListener(this);
        btnFire.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) mPresenter.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_water:
                if (InfoManger.getInstance().isPermission("52")) {
                    startActivity(new Intent(mContext, DetailsResourceActivity.class)
                            .putExtra(DetailsResourceActivity.LABEL, DetailsResourceActivity.LABEL_VALUE_WATER)
                    );
                } else {
                    XToastUtil.showToast("暂不拥有该权限！");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        //初始化数据
        sendBroadcast(new Intent().setAction("com.backing.broad_call_service")
                .putExtra(MyServer.FLAG, MyServer.FLAG_VALUE_LOCAL)
        );
        super.onResume();
    }

    @Override
    public void setMark(int type, String name, String describe, double latitude, final double longitude) {
        switch (type) {
            case 1: //单位标记
                super.onSetMarkOnMap(latitude, longitude, R.mipmap.ic_fangzi,
                        new MarkInfo(name, describe, latitude, longitude));
                break;
            case 2: //医院
                super.onSetMarkOnMap(latitude, longitude, R.mipmap.ic_yiyuan, new MarkInfo(name, describe, latitude, longitude));
                break;
            case 3://加油站
                super.onSetMarkOnMap(latitude, longitude, R.mipmap.ic_xiaofangzhongdui, new MarkInfo(name, describe, latitude, longitude));
                break;
            case 4://消防栓
                super.onSetMarkOnMap(latitude, longitude, R.mipmap.ic_xiaofangsuan,
                        new MarkInfo("消防栓", "消防栓", latitude, longitude));
                break;
            case 5:
                super.onSetMarkOnMap(latitude, longitude, R.mipmap.ic_yiyuan,
                        new MarkInfo(name, describe, latitude, longitude));
                break;
        }
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
    public void setLoation(double latitude, double longitude) {
        BDLocation location = new BDLocation();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        super.onDefaultLocation(location);
        mBaiDuMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                mBaiDuMap.hideInfoWindow();
                return false;
            }

            //此方法就是点击地图监听
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiDuMap.hideInfoWindow();
            }
        });
        this.location = location;
    }


    @Override
    public boolean onMarkerClicked(Marker marker) {
        /**获取百度地图上的marker里面包含的信息*/
        Bundle bundle = marker.getExtraInfo();
        Serializable info = bundle.getSerializable("info");
        if (info != null) {
            MarkInfo markInfo = (MarkInfo) info;
            /**点击响应弹出框*/
            addTextOnMark(markInfo.getName(), markInfo.getDescribe(), markInfo.getLatitude(), markInfo.getLongitude());
        }
        return true;
    }

    @Override
    public void onGetPoiResultInfo(String searchKey, List<PoiInfo> list) {
        for (PoiInfo poi : list) {
            if (poi != null && poi.location != null) {
                if (TextUtils.equals(searchKey, "医院")) {
                    setMark(2, poi.name, poi.address, poi.location.latitude, poi.location.longitude);
                } else if (TextUtils.equals(searchKey, "消防队")) {
                    setMark(3, poi.name, poi.address, poi.location.latitude, poi.location.longitude);
                } else if (TextUtils.equals(searchKey, "卫生所")) {
                    setMark(5, poi.name, poi.address, poi.location.latitude, poi.location.longitude);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btn_other:
                mBaiDuMap.clear();
                if (btnFire.isChecked()) {
                    mPresenter.getFire();
                }
                if (btnBuild.isChecked()) {
                    mPresenter.getBuild();
                }
                if (isChecked) {
                    getPoi();
                }
                if (!btnFire.isChecked() && !btnBuild.isChecked()) {
                    mPresenter.init();
                }
                break;
            case R.id.btn_fire:
                mBaiDuMap.clear();
                if (btnOther.isChecked()) {
                    getPoi();
                }
                if (btnBuild.isChecked()) {
                    mPresenter.getBuild();
                }
                if (isChecked) {
                    mPresenter.getFire();
                } else if (!btnOther.isChecked() && !btnBuild.isChecked()) {
                    mPresenter.init();
                }
                break;
            case R.id.btn_build:
                mBaiDuMap.clear();
                if (btnOther.isChecked()) {
                    getPoi();
                }
                if (btnFire.isChecked()) {
                    mPresenter.getFire();
                }
                if (isChecked) {
                    mPresenter.getBuild();
                } else if (!btnOther.isChecked() && !btnFire.isChecked()) {
                    mPresenter.init();
                }
                break;
        }
    }

    public void getPoi() {
        onLoadData("医院", 3000, location.getLatitude(), location.getLongitude());
        /**收搜周边加油站*/
        onLoadData("消防队", 3000, location.getLatitude(), location.getLongitude());
        onLoadData("卫生所", 3000, location.getLatitude(), location.getLongitude());
    }
}
