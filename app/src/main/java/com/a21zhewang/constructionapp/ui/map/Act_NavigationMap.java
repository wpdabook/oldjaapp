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
    //????????????
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
     * ????????????????????????
     */
    private String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE };
    //????????????
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
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
        righttext.setTextSize(16);
        if (aMap == null) {
            // ???????????????
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            //??????????????????
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
     * ????????????????????????????????????
     * https://lbs.amap.com/api/android-navi-sdk/guide/navi-component/basic-functions/?sug_index=0
     * 1.?????????????????????????????????
     * 2.??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1??????
     * 3.?????? POIId ??????????????????????????????????????????
     * 4.?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param clusterItems
     */
    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        if(clusterItems.size()==1){
            RegionItem regionItem=(RegionItem)clusterItems.get(0);
//            Toast.makeText(this, "????????????"+regionItem.getTitle(), Toast.LENGTH_SHORT).show();
            //??????
            Poi start = new Poi(Address, new LatLng(latitude,longitude), "");
            //??????
            double latitude = regionItem.getPosition().latitude;
            double longitude = regionItem.getPosition().longitude;
            Poi end = new Poi(regionItem.getTitle(), new LatLng(latitude,longitude), "");
            // ??????????????????
            AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
            // ????????????
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
     * ??????????????????
     */
    private void setMapAttribute() {
        //????????????????????????
        aMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        //??????????????????????????????
        uiSettings.setZoomControlsEnabled(false);
        //???????????????????????????
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);// ?????????????????????
        uiSettings.setRotateGesturesEnabled(false);// ?????????????????????
        //??????????????????
        aMap.setLocationSource(this);
        //????????????????????????????????????,?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???
        aMap.setMyLocationEnabled(true);
        //???????????????????????????????????????????????????
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        ////??????????????????????????????????????????????????????
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
        //????????????
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
     * ??????????????????????????? dp ????????? ????????? px(??????)
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
                    Double XPoi =  Double.valueOf(XPoint); //??????
                    Double YPoi= Double.valueOf(YPoint); //??????
//                    Poi start = new Poi(Address, new LatLng(latitude,longitude), "");
//                    //??????
//                    Poi end = new Poi(SAddress, new LatLng(YPoi,XPoi), "");
//                    // ??????????????????
//                    AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.ROUTE);
//                    // ????????????
//                    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), params, null);
                    CameraPosition cameraPosition = new CameraPosition(new LatLng(YPoi, XPoi), 15, 0, 30);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    aMap.moveCamera(cameraUpdate);
                    break;
            }
        }
    }

    /**
     * ???????????????????????????
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

    @Override
    public void deactivate() {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //??????????????????
                onLocationChangedListener.onLocationChanged(aMapLocation);
                //????????????
                latitude = aMapLocation.getLatitude();
                //????????????
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
     * ??????????????????????????????
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            Address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        } else {
            Toast.makeText(getApplication(), "??????????????????????????????", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * ????????????
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
        //??????????????????
        new Thread() {
            public void run() {
                List<ClusterItem> items = new ArrayList<ClusterItem>();
                //??????10000??????
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
