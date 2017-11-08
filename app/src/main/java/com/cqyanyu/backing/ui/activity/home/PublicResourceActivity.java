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
import com.cqyanyu.backing.ui.activity.base.BaseMapActivity;
import com.cqyanyu.backing.ui.entity.home.MapDeviceInfo;
import com.cqyanyu.backing.ui.entity.home.MapUnitInfo;
import com.cqyanyu.backing.ui.entity.home.MarkInfo;
import com.cqyanyu.backing.ui.entity.home.PublicResourceEntity;
import com.cqyanyu.backing.ui.mvpview.home.PublicResourceView;
import com.cqyanyu.backing.ui.presenter.home.PublicResourcePresenter;
import com.cqyanyu.backing.ui.server.MyServer;

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
    private List<MapUnitInfo> mListUnit;
    private List<MapDeviceInfo> mList;

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
                startActivity(new Intent(mContext, DetailsResourceActivity.class)
                        .putExtra(DetailsResourceActivity.LABEL, DetailsResourceActivity.LABEL_VALUE_WATER)
                );
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
    public void setUnitList(List<MapUnitInfo> mListUnit) {
        this.mListUnit = mListUnit;
    }

    @Override
    public void setDeviceList(List<MapDeviceInfo> mList) {
        this.mList = mList;
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
                    bList.add(new PublicResourceEntity(2, poi.name, poi.address, poi.location.latitude, poi.location.longitude));
                } else if (TextUtils.equals(searchKey, "消防队")) {
                    setMark(3, poi.name, poi.address, poi.location.latitude, poi.location.longitude);
                    bList.add(new PublicResourceEntity(3, poi.name, poi.address, poi.location.latitude, poi.location.longitude));
                } else if (TextUtils.equals(searchKey, "卫生所")) {
                    setMark(5, poi.name, poi.address, poi.location.latitude, poi.location.longitude);
                    bList.add(new PublicResourceEntity(5, poi.name, poi.address, poi.location.latitude, poi.location.longitude));
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
                    getDeviceInfo();
                }
                if (btnBuild.isChecked()) {
                    getUnitInfo();
                }
                if (isChecked) {
                    if (bList != null && bList.size() > 0) {
                        getPoiList();
                    } else {
                        getPoi();
                    }
                }
                if (!btnFire.isChecked() && !btnBuild.isChecked()) {
                    getDeviceInfo();
                    getUnitInfo();
                }
                break;
            case R.id.btn_fire:
                mBaiDuMap.clear();
                if (btnOther.isChecked()) {
                    getPoiList();
                }
                if (btnBuild.isChecked()) {
                    getUnitInfo();
                }
                if (isChecked) {
                    getDeviceInfo();
                } else if (!btnOther.isChecked() && !btnBuild.isChecked()) {
                    getUnitInfo();
                    getDeviceInfo();
                }
                break;
            case R.id.btn_build:
                mBaiDuMap.clear();
                if (btnOther.isChecked()) {
                    getPoiList();
                }
                if (btnFire.isChecked()) {
                    getDeviceInfo();
                }
                if (isChecked) {
                    getUnitInfo();
                } else if (!btnOther.isChecked() && !btnFire.isChecked()) {
                    getUnitInfo();
                    getDeviceInfo();
                }
                break;
        }
    }

    public void getPoi() { //根据关键字获取百度poi
        if (location != null) {
            onLoadData("医院", 3000, location.getLatitude(), location.getLongitude());
            /**收搜周边加油站*/
            onLoadData("消防队", 3000, location.getLatitude(), location.getLongitude());
            onLoadData("卫生所", 3000, location.getLatitude(), location.getLongitude());
        }
    }

    /**
     * 获取周边
     */
    public void getPoiList() {
        if (bList != null && bList.size() > 0) {
            for (PublicResourceEntity entity : bList) {
                setMark(entity.getType(), entity.getName(), entity.getDescribe(), entity.getLatitude(), entity.getLongitude());
            }
        }
    }

    /**
     * 获取设备
     */
    public void getDeviceInfo() {
        if (mList != null && mList.size() > 0) {
            for (MapDeviceInfo entity : mList) {
                setMark(4, "", "", entity.getLatitude(), entity.getLongitude());
            }
        }
    }

    /**
     * 获取单位
     */
    public void getUnitInfo() {
        if (mListUnit != null && mListUnit.size() > 0) {
            for (MapUnitInfo entity : mListUnit) {
                setMark(1, entity.getUnitName(), entity.getPosition(), entity.getLatitude(), entity.getLongitude());
            }
        }
    }

}
