package com.cqyanyu.backing.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.cqyanyu.backing.ui.entity.home.MarkInfo;
import com.cqyanyu.mvpframework.presenter.BasePresenter;
import com.cqyanyu.mvpframework.utils.XLog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import baidumap.GuideActivity;
import baidumap.MapManager;

/**
 * 基本地图activity
 * Created by Administrator on 2017/7/23.
 */
public abstract class BaseMapActivity<T extends BasePresenter> extends BaseActivity<T> {
    //百度地图
    protected TextureMapView mMapView;
    protected BaiduMap mBaiDuMap;
    protected double myLongitude, myLatitude;
    private MarkInfo startMarkInfo;
    //地图管理器
    private MapManager mapManager;
    //百度导航部分===================================================================================================//
    //语音播报
    private Handler ttsHandler;
    private Marker marker;

    //百度地图部分===================================================================================================//

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mapManager = new MapManager(mContext);
        super.onCreate(savedInstanceState);
        initMapView();
    }

    /**
     * 初始化百度地图
     */
    private void initMapView() {
        if (mMapView != null) {
            //初始化百度地图
            mBaiDuMap = mMapView.getMap();
            //开启定位我的位置
            mBaiDuMap.setMyLocationEnabled(true);
            //获取定位信息
            BDLocation bdLocation = EventBus.getDefault().getStickyEvent(BDLocation.class);
            //修改默认地区
            if (bdLocation != null) {
                myLongitude = bdLocation.getLongitude();
                myLatitude = bdLocation.getLatitude();
                startMarkInfo = new MarkInfo(bdLocation.getSemaAptag(), bdLocation.getAddrStr(), myLatitude, myLongitude);
                onDefaultLocation(bdLocation);
            }
        }
    }

    /**
     * 修改默认地区
     *
     * @param location 位置信息
     */
    protected void onDefaultLocation(@NonNull BDLocation location) {
        if (mBaiDuMap != null) {
            /**在百度地图中描出自己的位置*/
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiDuMap.setMyLocationData(locData);
            /**构建地图*/
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(15.0f);
            mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            /**mark点击监听*/
            mBaiDuMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    return onMarkerClicked(marker);
                }
            });
        }
    }

    /**
     * 百度地图上marker的点击事件
     *
     * @param marker Marker
     * @return 拦截
     */
    protected boolean onMarkerClicked(Marker marker) {
        return false;
    }

    /**
     * 在百度地图上设置标记
     *
     * @param latitude  经度
     * @param longitude 维度
     * @param resId     标记的图片id
     */
    protected void onSetMarkOnMap(double latitude, final double longitude, int resId, MarkInfo markInfo) {
        try {
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(resId);
            if (bitmap != null) {
                /**添加marker标记*/
                LatLng point = new LatLng(latitude, longitude);
                MarkerOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                marker = (Marker) mBaiDuMap.addOverlay(option);
                /**给marker添加信息*/
                if (markInfo != null) {
                    Bundle bundle = new Bundle();
                    //info必须实现序列化接口
                    bundle.putSerializable("info", markInfo);
                    marker.setExtraInfo(bundle);
                }
            }
        } catch (Exception e) {
            XLog.e("百度地图--->标记marker异常");
            e.getStackTrace();
        }
    }

    public Marker getMarker() {
        return marker;
    }

    /**
     * 以关键字检索周边标志
     *
     * @param searchKey 检索关键字
     * @param radius    检索半径
     * @param latitude  检索中心点的经度
     * @param longitude 检索中心点的维度
     */
    protected void onLoadData(final String searchKey, final int radius, final double latitude, final double longitude) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
//                getPoiList(new LatLng(latitude, longitude));
                poi(searchKey, radius, latitude, longitude);
                Looper.loop();
            }
        }).start();
    }

    /**
     * POI检索
     *
     * @param searchKey 检索关键字
     * @param radius    检索半径
     * @param latitude  检索中心点的经度
     * @param longitude 检索中心点的维度
     */
    private void poi(final String searchKey, int radius, double latitude, double longitude) {
        PoiSearch search = PoiSearch.newInstance();
        search.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                // 获取POI检索结果
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果

                } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    // 检索结果正常返回
                    final List<PoiInfo> list = result.getAllPoi();
                    if (list != null && list.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新UI
                                onGetPoiResultInfo(searchKey, list);
                            }
                        });
                    }
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword(searchKey);//检索关键字
        option.location(new LatLng(latitude, longitude));//检索中心
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.pageCapacity(10);//检索分页，每页18条
        option.radius(radius);//检索半径，默认单位为米
        search.searchNearby(option);
    }

    /**
     * 获取到的POI检索结果
     *
     * @param list 检索结果列表
     */
    protected void onGetPoiResultInfo(String searchKey, List<PoiInfo> list) {

    }

    /**
     * 初始化导航
     */
    protected void initNavi() {
        createHandler();
        mapManager.initNav(this);
        if (mapManager.initDirs()) {
            mapManager.initNavi(ttsHandler, new BaiduNaviManager.NaviInitListener() {
                @Override
                public void onAuthResult(int status, String s) {
                    if (0 == status) {
                        Log.d("yykj", "key校验成功!");
                    } else {
                        Log.e("yykj", "key校验失败!");
                    }
                }

                @Override
                public void initStart() {

                }

                @Override
                public void initSuccess() {
                    mapManager.initSetting();
                }

                @Override
                public void initFailed() {

                }
            });
        }
    }

    /**
     * 创造内部TTS播报状态回传handler
     */
    private void createHandler() {
        ttsHandler = new Handler() {
            public void handleMessage(Message msg) {
                int type = msg.what;
                switch (type) {
                    case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                        break;
                    }
                    case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                        break;
                    }
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 前往导航页面
     *
     * @param endLatLng 终点坐标
     */
    protected void routePlanToNav(LatLng endLatLng) {
        mapManager.routePlanToNav(endLatLng, new BaiduNaviManager.RoutePlanListener() {
            @Override
            public void onJumpToNavigator() {
                Intent intent = new Intent(mContext, GuideActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(GuideActivity.ROUTE_PLAN_NODE, mapManager.geteNode());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFailed() {
                Toast.makeText(mContext, "算路失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mapManager != null) {
            mapManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        mBaiDuMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    /**
     * 根据经纬度获取地址
     *
     * @param ll
     * @return
     */
    private void getPoiList(final LatLng ll) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                // 获取POI检索结果
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果

                } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    // 检索结果正常返回
                    final List<PoiInfo> list = result.getPoiList();
                    if (list != null && list.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新UI
                                onGetPoiResultInfo("", list);
                            }
                        });
                    }
                }
            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
    }
}
