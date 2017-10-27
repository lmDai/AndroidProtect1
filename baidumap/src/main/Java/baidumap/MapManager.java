package baidumap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import baidumap.widget.DrivingRouteOverlay;

import static com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.WGS84;

/**
 * 地图管理器
 * Created by Administrator on 2017/7/25.
 */
public class MapManager {
    private Context context;

    public MapManager(Context context) {
        this.context = context;
    }

    //===========================================================定位服务===========================================================//
    private LocationClient mLocationClient;

    /**
     * 初始化定位
     */
    public void initLocation(BDLocationListener listener) {
        mLocationClient = new LocationClient(context);
        //声明LocationClient类
        if (listener != null) {
            mLocationClient.registerLocationListener(listener);
        } else {
            mLocationClient.registerLocationListener(new BDLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    setBDLocationResult(bdLocation);
                }

                @Override
                public void onConnectHotSpotMessage(String s, int i) {

                }
            });
        }
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    /**
     * 设置百度地位的结果
     *
     * @param bdLocation 定位结果
     */
    public void setBDLocationResult(BDLocation bdLocation) {
        LocationInfo.getInstance().setLocation(bdLocation);
        //获取定位结果
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(bdLocation.getTime());    //获取定位时间
        sb.append("\nerror code : ");
        sb.append(bdLocation.getLocType());    //获取类型类型
        sb.append("\nlatitude : ");
        sb.append(bdLocation.getLatitude());    //获取纬度信息
        sb.append("\nlontitude : ");
        sb.append(bdLocation.getLongitude());    //获取经度信息
        sb.append("\nradius : ");
        sb.append(bdLocation.getRadius());    //获取定位精准度
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
            // GPS定位结果
            sb.append("\nspeed : ");
            sb.append(bdLocation.getSpeed());    // 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(bdLocation.getSatelliteNumber());    //获取卫星数
            sb.append("\nheight : ");
            sb.append(bdLocation.getAltitude());    //获取海拔高度信息，单位米
            sb.append("\ndirection : ");
            sb.append(bdLocation.getDirection());    //获取方向信息，单位度
            sb.append("\naddr : ");
            sb.append(bdLocation.getAddrStr());    //获取地址信息
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            // 网络定位结果
            sb.append("\naddr : ");
            sb.append(bdLocation.getAddrStr());    //获取地址信息
            sb.append("\noperationers : ");
            sb.append(bdLocation.getOperators());    //获取运营商信息
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {
            // 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(bdLocation.getLocationDescribe());    //位置语义化信息
        List<Poi> list = bdLocation.getPoiList();    // POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.i("BaiduLocationApiDem", sb.toString());
    }

    //===========================================================地图服务===========================================================//
    private BaiduMap mBaiDuMap;
    private int zoom = 16;

    /**
     * 初始化百度地图
     *
     * @param mapView 地图
     */
    public void initBaiDuMap(TextureMapView mapView) {
        if (mapView != null) {
            mBaiDuMap = mapView.getMap();
        }
    }

    /**
     * 初始化百度地图
     *
     * @param mapView 地图
     */
    public void initBaiDuMap(MapView mapView) {
        if (mapView != null) {
            mBaiDuMap = mapView.getMap();
        }
    }

    /**
     * 移动到当前位置
     */
    private void myLocation() {
        BDLocation mLocation = LocationInfo.getInstance().getLocation();
        if (mLocationClient != null && mBaiDuMap != null && mLocation != null) {
            mLocationClient.stop();
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(mLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(mLocation.getLatitude())
                    .longitude(mLocation.getLongitude()).build();
            // 设置定位数据
            mBaiDuMap.setMyLocationData(locData);
            LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            moveLocation(latLng);
        }
    }

    /**
     * 移动到指定位置
     *
     * @param latLng 移动到指定位置
     */
    public void moveLocation(LatLng latLng) {
        if (mBaiDuMap != null) {
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(zoom)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiDuMap.setMapStatus(mMapStatusUpdate);
        }
    }

    //===========================================================收搜服务===========================================================//
    private RoutePlanSearch routePlanSearch;
    private SuggestionSearch mSuggestionSearch;
    private PoiSearch mPoiSearch;
    private DrivingRouteOverlay overlay;

    /**
     * 初始化规划路线收搜
     *
     * @param listener 监听
     */
    public void initPlanSearch(OnGetRoutePlanResultListener listener) {
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(listener);
    }

    /**
     * 初始化收搜
     *
     * @param listener 监听
     */
    public void initSuggestSearch(OnGetSuggestionResultListener listener) {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
    }

    /**
     * 初始化收搜
     *
     * @param listener 监听
     */
    public void initPoiSearch(OnGetPoiSearchResultListener listener) {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
    }

    /**
     * 线路规划(从用户定位点为起点)
     *
     * @param endAddress 终点位置
     */
    public void searchRoad(LatLng endAddress) {
        BDLocation location = LocationInfo.getInstance().getLocation();
        if (routePlanSearch != null && location != null) {
            PlanNode stNode = PlanNode.withLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            PlanNode enNode = PlanNode.withLocation(endAddress);
            routePlanSearch.drivingSearch(new DrivingRoutePlanOption().policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_FEE_FIRST).from(stNode).to(enNode));
        }
    }

    /**
     * 关键字收搜
     *
     * @param searchKey 关键字
     */
    public void searchSuggest(String searchKey) {
        BDLocation location = LocationInfo.getInstance().getLocation();
        if (mSuggestionSearch != null && location != null && !TextUtils.isEmpty(searchKey))
            mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                    .keyword(searchKey)
                    .city(location.getCity()));
    }

    /**
     * POI收搜关键字
     *
     * @param searchKey 关键字
     */
    public void searchPoi(String searchKey) {
        BDLocation location = LocationInfo.getInstance().getLocation();
        if (mPoiSearch != null && location != null) {

        }
    }

    /**
     * 在地图上显示一条路线
     *
     * @param result 结果
     */
    public void setRouteOverLay(DrivingRouteResult result) {
        if (context != null && mBaiDuMap != null) {
            //驾车路径规划
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                if (overlay != null)
                    overlay.removeFromMap();
                overlay = new DrivingRouteOverlay(mBaiDuMap);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
    }

    //===========================================================导航服务===========================================================//
    private BNRoutePlanNode sNode;
    private BNRoutePlanNode eNode;
    private LatLng endLat;
    private Activity activity;
    //权限
    private final String authBaseArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
    private final String authComArr[] = {Manifest.permission.READ_PHONE_STATE};
    //请求码
    private final int authBaseRequestCode = 1;
    private final int authComRequestCode = 2;
    //App在SD卡中的目录名
    private static final String APP_FOLDER_NAME = "intvehapp";
    //SD卡的路径
    private String mSDCardPath;
    private BaiduNaviManager.RoutePlanListener listener;
    private BaiduNaviManager.NaviInitListener NaviListener;
    private boolean hasRequestComAuth = false;
    //语音播报
    private Handler ttsHandler;

    public void initNav(Activity context) {
        this.activity = context;
    }

    /**
     * 初始化SD卡，在SD卡路径下新建文件夹：App目录名，文件中包含了很多东西，比如log、cache等等
     *
     * @return
     */
    public boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 获取SD卡的根目录
     *
     * @return
     */
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * 检查百度基础权限
     *
     * @return
     */
    private boolean hasBasePhoneAuth() {
        boolean b = true;
        if (Build.VERSION.SDK_INT > 22) {
            for (String auth : authBaseArr) {
                if (activity.checkSelfPermission(auth) != PackageManager.PERMISSION_GRANTED) {
                    b = false;
                }
            }
        }
        return b;
    }

    /**
     * 检查百度手机权限
     *
     * @return
     */
    private boolean hasCompletePhoneAuth() {
        if (Build.VERSION.SDK_INT > 22) {
            for (String auth : authComArr) {
                if (activity.checkSelfPermission(auth) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 使用SDK前，先进行百度服务授权和引擎初始化
     */
    public void initNavi(Handler ttsHandler, BaiduNaviManager.NaviInitListener listener) {
        if (listener != null) {
            this.NaviListener = listener;
            this.ttsHandler = ttsHandler;
            // 申请权限
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                if (!hasBasePhoneAuth()) {
                    activity.requestPermissions(authBaseArr, authBaseRequestCode);
                    return;
                }
            }
            BaiduNaviManager.getInstance().init(activity, mSDCardPath, APP_FOLDER_NAME, NaviListener, null, ttsHandler, null);
        }
    }

    /**
     * 初始化导航
     */
    public void initSetting() {
        /** 设置全程路况显示*/
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        /** 设置语音播报模式*/
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Novice);
        /** 设置实时路况条*/
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "9943683");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    /**
     * 算路设置起、终点，算路偏好，是否模拟导航等参数，然后在回调函数中设置跳转至诱导。
     *
     * @param end 终点
     */
    public void routePlanToNav(LatLng end, BaiduNaviManager.RoutePlanListener listener) {
        this.endLat = end;
        this.listener = listener;
        BDLocation location = LocationInfo.getInstance().getLocation();
        if (location != null && endLat != null) {
            // 权限申请
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                // 保证导航功能完备
                if (!hasCompletePhoneAuth()) {
                    if (!hasRequestComAuth) {
                        hasRequestComAuth = true;
                        activity.requestPermissions(authComArr, authComRequestCode);
                        return;
                    } else {
                        Toast.makeText(context, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            sNode = new BNRoutePlanNode(location.getLongitude(), location.getLatitude(), "", null, WGS84);
            eNode = new BNRoutePlanNode(endLat.longitude, endLat.latitude, "", null, WGS84);
            List<BNRoutePlanNode> list = new ArrayList<>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager
                    .getInstance()
                    .launchNavigator(
                            activity,                        //建议是应用的主Activity
                            list,                            //传入的算路节点，顺序是起点、途经点、终点，其中途经点最多三个
                            1,                               //算路偏好 1:推荐 8:少收费 2:高速优先 4:少走高速 16:躲避拥堵
                            true,                            //true表示真实GPS导航，false表示模拟导航
                            listener                         //开始导航回调监听器，在该监听器里一般是进入导航过程页面
                    );
        }
    }

    /**
     * 权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(context, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi(ttsHandler, NaviListener);
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routePlanToNav(endLat, listener);
        }
    }

    public BNRoutePlanNode geteNode() {
        return eNode;
    }
}
