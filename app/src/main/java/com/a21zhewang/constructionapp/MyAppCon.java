package com.a21zhewang.constructionapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.VersionBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.utils.dbUtils;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fenjuly.library.ArrowDownloadButton;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zhy.autolayout.config.AutoLayoutConifg;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.x;
import java.io.File;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by zhewang_ljw on 2017/4/17.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class MyAppCon extends MultiDexApplication {
    public static Context appcontext = null;
    public static AlertDialog alertDialog = null;
    public static View view = null;
    public static HashSet<String> tags;
    public static RequestQueue queue;
    private static int sequence = 1;
    //alias
    private String alias;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        handleSSLHandshake();//忽略https证书效验
        appcontext = getApplicationContext();
        queue = Volley.newRequestQueue(getApplicationContext());
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(false);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口5e1d6c8a570df3d83f00002f
        initUmConfigure();
        /**
         * 1.不支持页面自动采集
         * 2.需要onResume/onPause埋点;
         * 3.用onPageStart/onPageEnd对非Activity页面埋点
         * 4.Activity页面手动调用MobclickAgent.onResume/onPause接口统计;
         */
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode. LEGACY_MANUAL); // 兼容8.0.0及以上
        AutoLayoutConifg.getInstance().useDeviceSize();
        x.Ext.init(this);
        dbUtils.Instance();
        PublicUtils.Ver = getVersionName();
        if(PublicUtils.IS_DEBUG_MODE == false){
            JPushInterface.setDebugMode(false);
            JPushInterface.init(this);
        }
        // JPushInterface.setAlias(MyAppCon.appcontext, sequence++, PublicUtils.UserID);
        tags = new HashSet<>();
        tags.add(PublicUtils.URLload);

    }
    public void initUmConfigure(){
        if("com.a21zhewang.constructionapp.vipa".equals(getPackageName())){
            UMConfigure.init(this, "5f606ecda4ae0a7f7d05b316", "v3_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        if("com.a21zhewang.constructionapp3".equals(getPackageName())){
            UMConfigure.init(this, "5df98564cb23d251d1000a0d", "aqsc_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.wc".equals(getPackageName())){
            UMConfigure.init(this, "5ebca26e570df3adee0001a8", "wcja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.jh".equals(getPackageName())){
            UMConfigure.init(this, "5ebca6e6167eddc8f100008f", "jhja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.ja".equals(getPackageName())){
            UMConfigure.init(this, "5ebce82b895cca023400000f", "jaja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.qk".equals(getPackageName())){
            UMConfigure.init(this, "5ebce8d5570df3f8ff00022c", "qkja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.hyzj".equals(getPackageName())){
            UMConfigure.init(this, "5e1c042f4ca3571f3b0000eb", "hyja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.hs".equals(getPackageName())){
            UMConfigure.init(this, "5ebce99a570df31a8b00002b", "hsja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.qsja".equals(getPackageName())){
            UMConfigure.init(this, "5e8d3730895cca3aba0000ce", "qsja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.dxh".equals(getPackageName())){
            UMConfigure.init(this, "5ebcea45570df30f5300010a", "dxhja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.cdja".equals(getPackageName())){
            UMConfigure.init(this, "5ebceaa40cafb2108f000065", "cdja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.jxja".equals(getPackageName())){
            UMConfigure.init(this, "5eb25371895ccabf750001cf", "jxja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.hp".equals(getPackageName())){
            UMConfigure.init(this, "5ebceb5b570df3ed1a000385", "hpja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.xz".equals(getPackageName())){
            UMConfigure.init(this, "5ebcebca0cafb2e8e30003f3", "xzja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.hn".equals(getPackageName())){
            UMConfigure.init(this, "5ebcec1c895ccae08d0003e7", "hnja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.dhgx".equals(getPackageName())){
            UMConfigure.init(this, "5ebcec7b0cafb2e8e3000428", "dxja_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
        else if("com.a21zhewang.constructionapp.saqz".equals(getPackageName())){
            UMConfigure.init(this, "5e0b02eecb23d2a6a3000bc7", "saqz_app", UMConfigure.DEVICE_TYPE_PHONE, null);
        }
    }
    //新建一个方法返回请求队列
    public static RequestQueue getHttpQueue(){
        return queue;
    }

    //版本名
    public static String getVersionName() {
        return getPackageInfo(appcontext).versionName;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo(appcontext).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static void showup(final String version, String content, final Context appcontext) {
        if (view == null)
            view = View.inflate(appcontext, R.layout.popup, null);
        if (alertDialog == null)
            alertDialog = new AlertDialog.Builder(appcontext).setView(view).create();
        else{
            view = View.inflate(appcontext, R.layout.popup, null);
            alertDialog.dismiss();
            alertDialog= new AlertDialog.Builder(appcontext).setView(view).create();
        }
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final ArrowDownloadButton popup_ProgressBar = (ArrowDownloadButton) view.findViewById(R.id.popup_ProgressBar);
        final TextView texttishi = (TextView) view.findViewById(R.id.popup_version);
        texttishi.setText("是否升级到" + version + "版本？");
        final TextView popup_content = (TextView) view.findViewById(R.id.popup_content);
        popup_content.setText(content);
        final LinearLayout popup_lin = (LinearLayout) view.findViewById(R.id.popup_lin);
        final LinearLayout btn_lin = (LinearLayout) view.findViewById(R.id.btn_lin);
        Button bnt1 = (Button) view.findViewById(R.id.popup_btn1);//忽略
        bnt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicUtils.putspboolean("isup", false);
                PublicUtils.putspstring("version", version);
                alertDialog.dismiss();
            }
        });
        Button bnt2 = (Button) view.findViewById(R.id.popup_btn2);//下次再说
        bnt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        Button bnt3 = (Button) view.findViewById(R.id.popup_btn3);//立即更新
        bnt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttishi.setText("正在下载安装包，请不要退出！");
                popup_content.setVisibility(View.GONE);
                popup_ProgressBar.setVisibility(View.VISIBLE);
                popup_lin.setVisibility(View.GONE);
                btn_lin.setBackgroundColor(Color.parseColor("#1B9AF2"));
                // XUtil.DownLoadFile(PublicUtils.URLload + "/Upload/file/dt.apk",
                // Environment.getExternalStorageDirectory().getAbsolutePath() + "/dt.apk",
                String apk="/wuhan.apk";
                if (PublicUtils.URL.equals("http://106.14.167.156:9001/API/SGGL.aspx")){
                    apk="/dt.apk";
                }
                String url = PublicUtils.URLload + "/upload/file" + apk;
                Log.e("url",url);
                XUtil.DownLoadFile(url,
                        Environment.getExternalStorageDirectory().getAbsolutePath() + apk,
                        new Callback.ProgressCallback<File>() {
                            @Override
                            public void onSuccess(File result) {
                                Log.i("result", "onSuccess:" + result.getAbsolutePath());
                                popup_ProgressBar.setProgress(100);
                                if (alertDialog!=null){
                                    alertDialog.dismiss();
                                }
                                PublicUtils.openFile(appcontext, result);
                                PublicUtils.putspboolean("autologin", false);
                                ((Activity)appcontext).finish();
                                CloseActivityClass.exitClient();
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("result", "onError:" + ex.toString());
                                if (alertDialog!=null){
                                    alertDialog.dismiss();
                                }
                                PublicUtils.toastFalse("下载失败！");
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                                Log.i("result", "onCancelled:" + cex.toString());
                            }

                            @Override
                            public void onFinished() {
                                Log.i("result", "onFinished:");
                            }

                            @Override
                            public void onWaiting() {
                                Log.i("result", "onWaiting:");

                            }

                            @Override
                            public void onStarted() {
                                Log.i("result", "onStarted:");
                                popup_ProgressBar.startAnimating();

                            }

                            @Override
                            public void onLoading(long total, long current, boolean isDownloading) {
                                int jd = (int) (current / (total / 100));
                                popup_ProgressBar.setProgress(jd);
                            }
                        });
            }
        });
        alertDialog.show();

    }

    public static void getversion(final Context appcontext) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"Android\"}");
        XUtil.postjsondata(getjsonobj, "GetVersion", new MyCallBack() {


            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {

                VersionBean verbean = JsonUtils.getbean(res, VersionBean.class);
                String name = verbean.getVersion() + "";
                String content = verbean.getVerDisc();
                String Versionname = getVersionName();

                if (PublicUtils.getspboolean("isup", true) || !PublicUtils.getspstring("version").equals(name)) {
                    if (!name.equals(Versionname)) { //本地与服务器版本比较，不一致，下载服务器版本
                        showup(name, content, appcontext);
                    }
                }

            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }
    /**
     * 忽略https的证书校验
     * https://blog.csdn.net/MoonLoong/article/details/79760428
     */
    public static void handleSSLHandshake() {
        try{
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }catch (Exception ignored){

        }
    }

    public static void getversion1(final Context appcontext) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"Android\"}");
        XUtil.postjsondata(getjsonobj, "GetVersion", new MyCallBack() {


            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {

                VersionBean verbean = JsonUtils.getbean(res, VersionBean.class);
                String name = verbean.getVersion() + "";
                String content = verbean.getVerDisc();
                String Versionname = getVersionName();

                if (PublicUtils.getspboolean("isup", true) || !PublicUtils.getspstring("version").equals(name)) {
                    if (!name.equals(Versionname)) {
                        // PublicUtils.toast("准备更新！");
                        showup(name, content, appcontext);
                    }else{
                        PublicUtils.toast("已是最新版本！");
                    }
                }

            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }
    private void initImageLoader() {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

    }
}
