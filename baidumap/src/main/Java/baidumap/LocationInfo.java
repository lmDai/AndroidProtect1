package baidumap;

import com.baidu.location.BDLocation;

/**
 * 定位信息
 * Created by Administrator on 2017/7/25.
 */
public class LocationInfo {
    private final static LocationInfo instance = new LocationInfo();
    private boolean isLocation;//是否已经定位
    private BDLocation location;//定位信息

    private LocationInfo() {

    }

    public static LocationInfo getInstance() {
        return instance;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(BDLocation location) {
        this.location = location;
        this.isLocation = true;
    }

    public BDLocation getLocation() {
        return location;
    }

    public double getMyLatitude() {
        if (location != null) {
            return location.getLatitude();
        }
        return 0;
    }

    public double getMyLongitude() {
        if (location != null) {
            return location.getLongitude();
        }
        return 0;
    }

    public String getMyAddrStr() {
        if (location != null) {
            return location.getAddrStr();
        }
        return "";
    }

    public float getRadius() {
        if (location != null) {
            return location.getRadius();
        }
        return 0;
    }
}
