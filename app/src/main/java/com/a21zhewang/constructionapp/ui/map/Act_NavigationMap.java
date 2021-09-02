package com.a21zhewang.constructionapp.ui.map;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.MapBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RegionItem;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.inspection.Act_AddInspection;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

//https://blog.csdn.net/qq_15037349/article/details/72722750
public class Act_NavigationMap extends BaseActivity implements ClusterRender, AMap.OnMapLoadedListener,
        ClusterClickListener ,AMap.OnMapClickListener,LocationSource,AMapLocationListener,GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.location_map)
    MapView mapView;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    private AMap aMap;
    private UiSettings uiSettings;
    private int clusterRadius = 100;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();
//    private ClusterOverlay mClusterOverlay;
    private MyClusterOverlay myClusterOverlay;
    private List<MapBean> mapBeenlist = new ArrayList<>();
    //定位服务
    private LocationSource.OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationClientOption;
    private String Address = "";
    private String Province = "";
    private String City = "";
    private String Area = "";
    private Double latitude;
    private Double longitude;
    private Dialog loadingDialog;
    /**
     * 定义所需要的权限
     */
    private String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE };
    //地理编码
    private GeocodeSearch geocodeSearch;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_map_navigation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView.onCreate(savedInstanceState);

    }
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        righttext.setTextSize(16);
        if (aMap == null) {
            // 初始化地图
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            //设置地图属性
            setMapAttribute();
//            aMap.setOnMapLoadedListener(this);
        }
//        chckPermission();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClusterOverlay.onDestroy();
                if(mapView != null){
                    mapView.onDestroy();
                }
                Act_NavigationMap.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        loadingDialog.show();
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"projectName\":\"\"}");
        XUtil.postjsondata(getjsonobj, "GetMapProjectData", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(res);
                    Object object = jsonObject.get("mapProjectLists");
                    List<MapBean> mapBeen = JsonUtils.jsonToList(object.toString(),MapBean[].class);
                    mapBeenlist.addAll(mapBeen);
                    for(int i=0;i<mapBeenlist.size();i++){
                        String XPoint = mapBeenlist.get(i).getXPoint();
                        String YPoint = mapBeenlist.get(i).getYPoint();
                        if("0".equals(XPoint) && "0".equals(YPoint) && TextUtils.isEmpty(XPoint) && TextUtils.isEmpty(YPoint)){
                            mapBeenlist.remove(i);
                        }
                    }
                    new Thread() {
                        public void run() {
                            List<ClusterItem> items = new ArrayList<ClusterItem>();
                            for(int i=0;i<mapBeenlist.size();i++){
                                double lat = Double.valueOf(mapBeenlist.get(i).getYPoint());
                                double lon = Double.valueOf(mapBeenlist.get(i).getXPoint());
                                LatLng latLng = new LatLng(lat, lon, false);
                                RegionItem regionItem = new RegionItem(latLng, mapBeenlist.get(i).getShortName());
                                items.add(regionItem);
                            }
                            myClusterOverlay = new MyClusterOverlay(aMap, items,
                                    dp2px(getApplicationContext(), clusterRadius),
                                    getApplicationContext());
                            myClusterOverlay.setOnClusterClickListener(Act_NavigationMap.this);
                        }
                    }.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }

    /**
     * 点击的聚合点开启导航组件
     * https://lbs.amap.com/api/android-navi-sdk/guide/navi-component/basic-functions/?sug_index=0
     * 1.经纬度数据为必填参数；
     * 2.名称是可选参数仅用于显示地点名称，如果设置了名称参数，会优先显示所设置的名称，如果不传名称将使用默认值，如“终点”、“途径点1”；
     * 3.高德 POIId 是可选参数，但强烈建议传入。
     * 4.当不设置起点信息时，会采用用户当前位置作为起点，并显示地点名称为“我的位置”。
     * @param clusterItems
     */
    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        if(clusterItems.size()==1){
            RegionItem regionItem=(RegionItem)clusterItems.get(0);
//            Toast.makeText(this, "点击的是"+regionItem.getTitle(), Toast.LENGTH_SHORT).show();
            //起点
            Poi start = new Poi(Address, new LatLng(latitude,longitude), "");
            //终点
            double latitude = regionItem.getPosition().latitude;
            double longitude = regionItem.getPosition().longitude;
            Poi end = new Poi(regionItem.getTitle(), new LatLng(latitude,longitude), "");
            // 组件参数配置
            AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
            // 启动组件
            AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
        }else{
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ClusterItem clusterItem : clusterItems) {
                builder.include(clusterItem.getPosition());
            }
            LatLngBounds latLngBounds = builder.build();
            aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
        }
    }

    @Override
    public Drawable getDrawAble(int clusterNum) {
        int radius = dp2px(getApplicationContext(), 80);
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                bitmapDrawable = getApplication().getResources().getDrawable(R.mipmap.map_icon_marka);
                mBackDrawAbles.put(1, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 5) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(159, 210, 154, 6)));
                mBackDrawAbles.put(2, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(199, 217, 114, 0)));
                mBackDrawAbles.put(3, bitmapDrawable);
            }

            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(235, 215, 66, 2)));
                mBackDrawAbles.put(4, bitmapDrawable);
            }

            return bitmapDrawable;
        }
    }
    /**
     * 设置地图属性
     */
    private void setMapAttribute() {
        //设置默认缩放级别
        aMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        //隐藏的右下角缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        //显示右上角定位按钮
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);// 禁用倾斜手势。
        uiSettings.setRotateGesturesEnabled(false);// 禁用旋转手势。
        //设置定位监听
        aMap.setLocationSource(this);
        //可触发定位并显示当前位置,设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
        //定位一次，且将视角移动到地图中心点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        ////定位一次，且将视角移动到地图中心点。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        //隐藏定位点外圈圆的颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        //设置地理编码查询
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        //设置地图点击事件
        //aMap.setOnMapClickListener(this);
    }
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        //销毁资源
        myClusterOverlay.onDestroy();
        if(mapView != null){
            mapView.onDestroy();
        }
    }
    private Bitmap drawCircle(int radius, int color) {
        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void initListener() {
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Act_NavigationMap.this,Act_MapSearch.class), Act_MapSearch.MAP_RESQUST_CODE_SELECT);
            }
        });
    }


    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Act_MapSearch.MAP_RESQUST_CODE_SELECT:
                    String XPoint = data.getStringExtra("XPoint");
                    String YPoint = data.getStringExtra("YPoint");
                    String SAddress = data.getStringExtra("address");
                    Double XPoi =  Double.valueOf(XPoint); //经度
                    Double YPoi= Double.valueOf(YPoint); //维度
//                    Poi start = new Poi(Address, new LatLng(latitude,longitude), "");
//                    //终点
//                    Poi end = new Poi(SAddress, new LatLng(YPoi,XPoi), "");
//                    // 组件参数配置
//                    AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
//                    // 启动组件
//                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
                    CameraPosition cameraPosition = new CameraPosition(new LatLng(YPoi, XPoi), 15, 0, 30);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    aMap.moveCamera(cameraUpdate);
                    break;
            }
        }
    }

    /**
     * 点击可以动态添加点
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
//        double lat = Math.random() + 39.474923;
//        double lon = Math.random() + 116.027116;
//        LatLng latLng1 = new LatLng(lat, lon, false);
//        RegionItem regionItem = new RegionItem(latLng1, "test");
//        myClusterOverlay.addClusterItem(regionItem);
    }

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

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //显示定位圆点
                onLocationChangedListener.onLocationChanged(aMapLocation);
                //当前纬度
                latitude = aMapLocation.getLatitude();
                //当前经度
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
     * 根据坐标转换地址信息
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            Address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        } else {
            Toast.makeText(getApplication(), "获取当前位置信息失败", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 检查权限
     */
    private void checkPermission(){
        if (EasyPermissions.hasPermissions(this, perms)) {
            setMapAttribute();
        } else {
           return;
        }
    }

    @Override
    public void onMapLoaded() {
        //添加测试数据
        new Thread() {
            public void run() {
                List<ClusterItem> items = new ArrayList<ClusterItem>();
                //随机10000个点
//                for (int i = 0; i < 10000; i++) {
//                    double lat = Math.random() + 39.474923;
//                    double lon = Math.random() + 116.027116;
//                    LatLng latLng = new LatLng(lat, lon, false);
//                    RegionItem regionItem = new RegionItem(latLng, "test" + i);
//                    items.add(regionItem);
//                }
                for(int i=0;i<mapBeenlist.size();i++){
                    double lat = Double.valueOf(mapBeenlist.get(i).getYPoint());
                    double lon = Double.valueOf(mapBeenlist.get(i).getXPoint());
                    LatLng latLng = new LatLng(lat, lon, false);
                    RegionItem regionItem = new RegionItem(latLng, mapBeenlist.get(i).getShortName());
                    items.add(regionItem);
                }

                myClusterOverlay = new MyClusterOverlay(aMap, items,
                        dp2px(getApplicationContext(), clusterRadius),
                        getApplicationContext());
//                mClusterOverlay.setClusterRenderer(Act_NavigationMap.this);
                myClusterOverlay.setOnClusterClickListener(Act_NavigationMap.this);
            }

        }.start();
    }
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
