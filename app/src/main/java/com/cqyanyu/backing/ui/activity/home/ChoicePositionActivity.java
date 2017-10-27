package com.cqyanyu.backing.ui.activity.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
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
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.cqyanyu.backing.R;
import com.cqyanyu.backing.ui.activity.base.BaseMapActivity;
import com.cqyanyu.backing.ui.adapter.SuggestionAdapter;
import com.cqyanyu.backing.ui.dialog.MapDialog;
import com.cqyanyu.backing.ui.entity.home.MapChoiceBean;
import com.cqyanyu.backing.ui.mvpview.home.ChoicePositionView;
import com.cqyanyu.backing.ui.presenter.home.ChoicePositionPresenter;
import com.cqyanyu.mvpframework.utils.XToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择位置
 * Created by Administrator on 2017/7/14.
 */
public class ChoicePositionActivity extends BaseMapActivity<ChoicePositionPresenter> implements ChoicePositionView {

    private double latitude;
    private double longitude;
    private MapDialog mapDialog;
    private MapChoiceBean mapChoiceBean;
    private MapChoiceBean locationInfo;
    /**
     * 在线建议查询
     */
    private SuggestionSearch keyWordsPoiSearch;
    /**
     * 在线建议查询结果集
     */
    private List<SuggestionResult.SuggestionInfo> keyWordPoiData;

    /**
     * 用于判断EditText是否获取了焦点
     */
    private boolean isFocus;
    private EditText location_name;
    private LinearLayout inputPoiSearchLayout;
    private LinearLayout layout;
    private ListView inputPoiListView;
    private SuggestionAdapter suggestAdapter;
    private MapChoiceBean result;
    private MapChoiceBean suggestBean;

    @Override
    protected ChoicePositionPresenter createPresenter() {
        return new ChoicePositionPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_choice_position;
    }

    @Override
    protected void initView() {
        mMapView = (TextureMapView) findViewById(R.id.map_view);
        mapDialog = new MapDialog(this);
        layout = (LinearLayout) findViewById(R.id.address_title_layout);
        location_name = (EditText) findViewById(R.id.location_name);
        inputPoiSearchLayout = (LinearLayout) findViewById(R.id.edit_search_poi);
        inputPoiListView = (ListView) findViewById(R.id.edit_search_poi_list);

        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);
        layout.requestFocus();
        isFocus = false;
        location_name.addTextChangedListener(watcher);
    }

    @Override
    protected void initListener() {
        /**
         * 为EditText设置焦点事件
         * */
        location_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputPoiSearchLayout.setVisibility(View.VISIBLE);
                } else {
                    inputPoiSearchLayout.setVisibility(View.GONE);
                }
                isFocus = hasFocus;
            }
        });
        inputPoiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SuggestionResult.SuggestionInfo suggestionInfo = (SuggestionResult.SuggestionInfo) parent.getItemAtPosition(position);
                suggestBean = new MapChoiceBean(suggestionInfo.pt.latitude, suggestionInfo.pt.longitude, suggestionInfo.city, suggestionInfo.key, true);
                addMarkOnMap(new LatLng(suggestionInfo.pt.latitude, suggestionInfo.pt.longitude));
                inputPoiSearchLayout.setVisibility(View.GONE);
                LatLng ll = new LatLng(suggestionInfo.pt.latitude, suggestionInfo.pt.longitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(15.0f);
                mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                mapChoiceBean = null;
                locationInfo = null;
                if (mapDialog.getList() != null) mapDialog.getList().clear();
                ((InputMethodManager) location_name.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        mContext.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    protected void initData() {
        keyWordPoiData = new ArrayList<>();
    }

    @Override
    public void setTextRight(TextView textRight) {
        textRight.setText("确定");
        textRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                result = getResult();
                if (result != null) {
                    EventBus.getDefault().postSticky(result);
                    finish();
                } else if (mapChoiceBean != null) {
                    EventBus.getDefault().postSticky(mapChoiceBean);
                    finish();
                } else if (suggestBean != null) {
                    EventBus.getDefault().postSticky(suggestBean);
                    finish();
                } else {
                    EventBus.getDefault().postSticky(locationInfo);
                    finish();
                }
                break;
        }
    }

    /**
     * 获取选择结果
     *
     * @return 选择结果
     */
    private MapChoiceBean getResult() {
        List<MapChoiceBean> list = mapDialog.getList();
        if (list != null) {
            for (MapChoiceBean bean : list) {
                if (bean.isChecked()) {
                    return bean;
                }
            }
        }
        return null;
    }

    @Override
    protected void onDefaultLocation(@NonNull final BDLocation location) {
        if (mBaiDuMap != null) {
            double latitude = 0;
            double longitude = 0;
            if (getIntent().getDoubleExtra("latitude", location.getLatitude()) == 0 && getIntent().getDoubleExtra("longitude", location.getLongitude()) == 0) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {
                latitude = getIntent().getDoubleExtra("latitude", location.getLatitude());
                longitude = getIntent().getDoubleExtra("longitude", location.getLongitude());
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(latitude)
                    .longitude(longitude).build();
            mBaiDuMap.setMyLocationData(locData);
            /**构建地图*/
            MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.mipmap.ic_dizhi_xxx);
            mBaiDuMap.setMyLocationConfigeration(new MyLocationConfiguration(
                    mCurrentMode, true, mCurrentMarker
            ));
            LatLng ll = new LatLng(locData.latitude, locData.longitude);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(15.0f);
            mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            if (locData != null) {
                locationInfo = new MapChoiceBean(locData.latitude, locData.longitude,
                        TextUtils.isEmpty(getIntent().getStringExtra("position")) ? location.getAddrStr() : getIntent().getStringExtra("position"),
                        TextUtils.isEmpty(getIntent().getStringExtra("position")) ? location.getLocationDescribe() : getIntent().getStringExtra("position"), true);
            } else {
                locationInfo = new MapChoiceBean(location.getLatitude(), location.getLongitude(), location.getAddrStr(), location.getLocationDescribe(), true);
            }
            //设置百度地图点击监听方法，通过手指点击地图确定目标位置
            mBaiDuMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                @Override
                public boolean onMapPoiClick(MapPoi arg0) {
                    mapChoiceBean = new MapChoiceBean(arg0.getPosition().latitude, arg0.getPosition().longitude,
                            arg0.getName(), arg0.getName(), true);
                    // TODO Auto-generated method stub
                    //在mark标记上添加文本显示信息
                    addMarkOnMap(arg0.getPosition());
                    //在mark标记上添加文本显示信息
                    //  addTextOnMark(arg0.getName(), arg0.getPosition());
                    if (mapDialog.getList() != null) mapDialog.getList().clear();
                    return false;
                }

                //此方法就是点击地图监听
                @Override
                public void onMapClick(LatLng latLng) {
                    getAddressData(latLng);
                    //在mark标记上添加文本显示信息
                    addMarkOnMap(latLng);
                    //在mark标记上添加文本显示信息
                    //addTextOnMark("显示周边", latLng);
                    if (mapDialog.getList() != null) mapDialog.getList().clear();
                }
            });
        }
    }

    /**
     * 在地图上添加mark标记
     *
     * @param latLng 位置信息
     */
    private void addMarkOnMap(LatLng latLng) {
        //获取经纬度
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        //先清除图层
        mBaiDuMap.clear();
        // 定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        // 构建MarkerOption，用于在地图上添加Marker
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_dizhi_xx);
        MarkerOptions options = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        // 在地图上添加Marker，并显示
        mBaiDuMap.addOverlay(options);
    }

    /**
     * 在mark标记上添加文本显示信息
     *
     * @param latLng 位置信息
     */
    private void addTextOnMark(String positionName, final LatLng latLng) {
        //显示一个弹出框
        View viewCache = getLayoutInflater().inflate(R.layout.maker, null);
        TextView mapName = (TextView) viewCache.findViewById(R.id.tv_maker);
        mapName.setText(positionName);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(viewCache);
        // LatLng pointTwo = new LatLng(latitude, longitude);
        InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                onLoadData("房子", 1000, latLng.latitude, latLng.longitude);
            }
        };
        InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -40, listener);
        mBaiDuMap.showInfoWindow(infoWindow);
    }

    @Override
    public void onGetPoiResultInfo(String searchKey, List<PoiInfo> list) {
        if (mapDialog != null) {
            mapDialog.show(list);
        }
    }

    /**
     * 根据经纬度获取地址
     *
     * @param ll
     * @return
     */
    private MapChoiceBean getAddressData(final LatLng ll) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    mapChoiceBean = new MapChoiceBean(ll.latitude, ll.longitude, "暂无地址", "暂无地址", true);
                }
                mapChoiceBean = new MapChoiceBean(ll.latitude, ll.longitude, result.getAddress(), result.getAddress(), true);
            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {

                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    mapChoiceBean = new MapChoiceBean(ll.latitude, ll.longitude, "暂无地址", "暂无地址", true);
                }
                mapChoiceBean = new MapChoiceBean(ll.latitude, ll.longitude, result.getAddress(), result.getAddress(), true);
            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
        return mapChoiceBean;
    }

    /**
     * 监听onKeyDown事件
     * 目的是判断当前页面是地图显示页面还是在线建议查询页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isFocus) {
                inputPoiSearchLayout.setVisibility(View.GONE);
                location_name.setText("");
                location_name.clearFocus();
                keyWordPoiData.clear();
                layout.setFocusable(true);
                layout.setFocusableInTouchMode(true);
                layout.requestFocus();
                isFocus = false;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            inputPoiSearchLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String searchKey = location_name.getText().toString();
            if (TextUtils.isEmpty(searchKey)) {
                return;
            }
            suggestAdapter = null;
            /**
             * 在线建议查询对象实例化+设置监听
             * @在线建议查询： 根据城市和关键字搜索出相应的位置信息(模糊查询)
             * */
            keyWordsPoiSearch = SuggestionSearch.newInstance();
            keyWordsPoiSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
                @Override
                public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                    keyWordPoiData.clear();
                    if (suggestionResult.getAllSuggestions() == null) {
                        XToastUtil.showToast("暂无数据信息");
                    } else {
                        keyWordPoiData = suggestionResult.getAllSuggestions();
                        //设置Adapter结束
                        if (suggestAdapter == null) {
                            suggestAdapter = new SuggestionAdapter(mContext, keyWordPoiData);
                            inputPoiListView.setAdapter(suggestAdapter);
                        } else {
                            suggestAdapter.setList(keyWordPoiData);
                        }
                    }
                }
            });
            keyWordsPoiSearch.requestSuggestion((new SuggestionSearchOption()).keyword(searchKey).city(searchKey));

        }
    };
}
