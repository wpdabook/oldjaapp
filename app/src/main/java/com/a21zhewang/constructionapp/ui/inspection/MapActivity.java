package com.a21zhewang.constructionapp.ui.inspection;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import butterknife.BindView;

/**
 * Created by YyyyQ on 2020/3/26
 * https://www.cnblogs.com/YyyyQ/p/12573001.html
 * 获取当前定位,地图选点,获取当前和选择的位置信息
 */
public class MapActivity extends BaseActivity implements LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnMapClickListener {
    @BindView(R.id.title_righttextview)
    TextView righttext;
    //回显位置信息的TextView
    @BindView(R.id.location_coordinate)
    TextView locationCoordinate;
    @BindView(R.id.location_info)
    TextView locationInfo;
    @BindView(R.id.me_location_map)
    MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    //定位服务
    private LocationSource.OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationClientOption;
    //地理编码
    private GeocodeSearch geocodeSearch;
    //当前地图上的marker
    private Marker marker;
    public  final static int MAP_RESQUST_CODE = 566;
    private StringBuffer ardess_sb;
    private String Address = "";
    private String Province = "";
    private String City = "";
    private String Area = "";
    private Double latitude;
    private Double longitude;
    private String projectID ="";
    private boolean isUpDateAdress = false;
    @Override
    public int getLayoutId() {
        return R.layout.map_act_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        righttext.setTextSize(16);
        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            //设置地图属性
            setMapAttribute();
        }

    }
    @Override
    public void initViews() {
        isUpDateAdress = getIntent().getBooleanExtra("isUpDateAdress",true);
        projectID = getIntent().getStringExtra("projectID");
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUpDateAdress == true){
                    UpdateAdress();
                }else {
                    Intent data = new Intent();
                    data.putExtra("address",Address);
                    data.putExtra("province",Province);
                    data.putExtra("city",City);
                    data.putExtra("area",Area);
                    data.putExtra("latitude",latitude);
                    data.putExtra("longitude",longitude);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
    private void UpdateAdress(){ //XPoint 经度   YPoint 维度
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectID\":\"" + projectID + "\",\"address\":\"" + Address + "\",\"XPoint\":\"" + longitude + "\",\"YPoint\":\"" + latitude + "\"}"),
                "UpdateProjectInfoById", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        Intent data = new Intent();
                        data.putExtra("address",Address);
                        data.putExtra("province",Province);
                        data.putExtra("city",City);
                        data.putExtra("area",Area);
                        data.putExtra("latitude",latitude);
                        data.putExtra("longitude",longitude);
                        setResult(RESULT_OK, data);
                        finish();
                    }

                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    @Override
                    public void onWrong() {

                    }
                });

    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }



    private void setMapAttribute() {
        //设置默认缩放级别
        aMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        //隐藏的右下角缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        //显示右上角定位按钮
        uiSettings.setMyLocationButtonEnabled(false);
        //设置定位监听
        aMap.setLocationSource(this);
        //可触发定位并显示当前位置
        aMap.setMyLocationEnabled(true);
        //定位一次，且将视角移动到地图中心点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        //隐藏定位点外圈圆的颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        //设置地理编码查询
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        //设置地图点击事件
        aMap.setOnMapClickListener(this);
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if (locationClient == null) {
            //初始化定位
            locationClient = new AMapLocationClient(this);
            //初始化定位参数
            locationClientOption = new AMapLocationClientOption();
            //设置定位回调监听
            locationClient.setLocationListener(this);
            //高精度定位模式
            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //单定位模式
            locationClientOption.setOnceLocation(true);
            //设置定位参数
            locationClient.setLocationOption(locationClientOption);
            //启动定位
            locationClient.startLocation();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //显示定位圆点
                StringBuffer sb = new StringBuffer();
                onLocationChangedListener.onLocationChanged(aMapLocation);
//                locationCoordinate.setText("当前纬度:" + aMapLocation.getLatitude() + "当前经度" + aMapLocation.getLongitude());
                sb.append("当前经度：" + aMapLocation.getLongitude() + "\n");
                sb.append("当前纬度：" + aMapLocation.getLatitude() + "\n");
                locationCoordinate.setText(sb.toString());
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                Province = aMapLocation.getProvince();
                City = aMapLocation.getCity();
                Area = aMapLocation.getDistrict();
                //根据当前经纬度查询地址
                LatLonPoint latLonPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
            } else {
                Log.e("YyyyQ", "定位失败" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo());
                Toast.makeText(getApplication(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
    }

    /**
     * 根据坐标转换地址信息
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        StringBuffer ardess_sb = new StringBuffer();
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            ardess_sb.append("当前位置信息：" + "\n");
            Address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            ardess_sb.append(regeocodeResult.getRegeocodeAddress().getFormatAddress());
//            locationInfo.setText("当前位置信息:" + regeocodeResult.getRegeocodeAddress().getFormatAddress());
            locationInfo.setText(ardess_sb.toString());
        } else {
            Toast.makeText(getApplication(), "获取当前位置信息失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 地址转坐标
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 地图点击事件
     */
    @Override
    public void onMapClick(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        StringBuffer sb = new StringBuffer();
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_icon));
        markerOptions.position(latLng);
        marker = aMap.addMarker(markerOptions);
        //根据点击地图的点位获取详细信息
//        locationCoordinate.setText("当前纬度:" + latLng.latitude + "当前经度" + latLng.longitude);
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        sb.append("当前经度：" + latLng.longitude + "\n");
        sb.append("当前纬度：" + latLng.latitude + "\n");
        locationCoordinate.setText(sb.toString());
        //根据当前经纬度查询地址
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }
    @Override
    public void beforesetContentView() {

    }

}