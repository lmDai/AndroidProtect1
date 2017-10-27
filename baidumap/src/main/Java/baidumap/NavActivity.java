package baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.util.LinkedList;
import java.util.List;

/**
 * 初始化导航页面
 * Created by Administrator on 2017/7/25.
 */
public class NavActivity extends Activity {
    public static List<Activity> activityList = new LinkedList<>();
    //Activity的实例
    private Activity context;
    //地图管理器
    private MapManager mapManager;
    //终点
    private LatLng endLatLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        double latitude = getIntent().getDoubleExtra("end_latitude", 0);
        double longitude = getIntent().getDoubleExtra("end_longitude", 0);
        if (latitude > 0 && longitude > 0) {
            endLatLng = new LatLng(latitude, longitude);
        }
    }

    private void initView() {
        context = this;
        mapManager = new MapManager(context);
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
                    routePlanToNav();
                }

                @Override
                public void initFailed() {

                }
            });
        }
    }

    private void routePlanToNav() {
        mapManager.routePlanToNav(endLatLng, new BaiduNaviManager.RoutePlanListener() {
            @Override
            public void onJumpToNavigator() {
                Intent intent = new Intent(context, GuideActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(GuideActivity.ROUTE_PLAN_NODE, mapManager.geteNode());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onRoutePlanFailed() {
                Toast.makeText(context, "算路失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mapManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
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
