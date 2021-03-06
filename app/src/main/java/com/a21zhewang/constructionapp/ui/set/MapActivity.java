package com.a21zhewang.constructionapp.ui.set;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.FbBean;
import com.a21zhewang.constructionapp.bean.ListBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProjectSynopsis;
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
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.util.List;

import butterknife.BindView;

/**
 * Created by YyyyQ on 2020/3/26
 * https://www.cnblogs.com/YyyyQ/p/12573001.html
 * ??????????????????,????????????,????????????????????????????????????
 */
public class MapActivity extends BaseActivity implements LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener,
        AMap.OnMapClickListener {
    @BindView(R.id.title_righttextview)
    TextView righttext;
    //?????????????????????TextView
    @BindView(R.id.location_coordinate)
    TextView locationCoordinate;
    @BindView(R.id.location_info)
    TextView locationInfo;
    @BindView(R.id.me_location_map)
    MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    //????????????
    private LocationSource.OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationClientOption;
    //????????????
    private GeocodeSearch geocodeSearch;
    //??????????????????marker
    private Marker marker;
    public  final static int MAP_RESQUST_CODE = 566;
    private StringBuffer ardess_sb;
    private String projectID ="";
    private String Address = "";
    private String Province = "";
    private String City = "";
    private String Area = "";
    private Double latitude;
    private Double longitude;
    private boolean isUpDateAdress = false;

    @Override
    public int getLayoutId() {
        return R.layout.map_act_layout;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        righttext.setTextSize(16);
        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            //??????????????????
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

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    private void UpdateAdress(){ //XPoint ??????   YPoint ??????
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

    private void setMapAttribute() {
        //????????????????????????
        aMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        //??????????????????????????????
        uiSettings.setZoomControlsEnabled(false);
        //???????????????????????????
        uiSettings.setMyLocationButtonEnabled(false);
        //??????????????????
        aMap.setLocationSource(this);
        //????????????????????????????????????
        aMap.setMyLocationEnabled(true);
        //???????????????????????????????????????????????????
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        //?????????????????????????????????
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        //????????????????????????
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        //????????????????????????
        aMap.setOnMapClickListener(this);
    }

    /**
     * ????????????
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if (locationClient == null) {
            //???????????????
            locationClient = new AMapLocationClient(this);
            //?????????????????????
            locationClientOption = new AMapLocationClientOption();
            //????????????????????????
            locationClient.setLocationListener(this);
            //?????????????????????
            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //???????????????
            locationClientOption.setOnceLocation(true);
            //??????????????????
            locationClient.setLocationOption(locationClientOption);
            //????????????
            locationClient.startLocation();
        }
    }

    /**
     * ???????????????????????????
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //??????????????????
                StringBuffer sb = new StringBuffer();
                onLocationChangedListener.onLocationChanged(aMapLocation);
//                locationCoordinate.setText("????????????:" + aMapLocation.getLatitude() + "????????????" + aMapLocation.getLongitude());
                sb.append("???????????????" + aMapLocation.getLongitude() + "\n");
                sb.append("???????????????" + aMapLocation.getLatitude() + "\n");
                locationCoordinate.setText(sb.toString());
                latitude = aMapLocation.getLatitude();
                longitude = aMapLocation.getLongitude();
                Province = aMapLocation.getProvince();
                City = aMapLocation.getCity();
                Area = aMapLocation.getDistrict();
                //?????????????????????????????????
                LatLonPoint latLonPoint = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);
            } else {
                Log.e("YyyyQ", "????????????" + aMapLocation.getErrorCode() + ":" + aMapLocation.getErrorInfo());
                Toast.makeText(getApplication(), "????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ????????????
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
     * ??????????????????????????????
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        StringBuffer ardess_sb = new StringBuffer();
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            ardess_sb.append("?????????????????????" + "\n");
            Address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
            ardess_sb.append(regeocodeResult.getRegeocodeAddress().getFormatAddress());
//            locationInfo.setText("??????????????????:" + regeocodeResult.getRegeocodeAddress().getFormatAddress());
            locationInfo.setText(ardess_sb.toString());
        } else {
            Toast.makeText(getApplication(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ???????????????
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * ??????????????????
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
        //?????????????????????????????????????????????
//        locationCoordinate.setText("????????????:" + latLng.latitude + "????????????" + latLng.longitude);
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        sb.append("???????????????" + latLng.longitude + "\n");
        sb.append("???????????????" + latLng.latitude + "\n");
        locationCoordinate.setText(sb.toString());
        //?????????????????????????????????
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }
    @Override
    public void beforesetContentView() {

    }

}