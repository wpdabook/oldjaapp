package com.a21zhewang.constructionapp.download;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.VersionBean;
import com.a21zhewang.constructionapp.customview.CustomDownDialog;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.IndexActivity;
import com.a21zhewang.constructionapp.ui.IndexActivity2;
import com.a21zhewang.constructionapp.ui.SettingsActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.DzDialog;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONObject;
import java.io.File;
import java.text.DecimalFormat;

/**
 * APP 更新工具类
 * Created by WUPENG on 2018/6/1.
 */

public class UpDateAppUtils {
    private CustomDownDialog downDialog;
    private static UpDateAppUtils util;
    private DownloadElement mDownloadElement;
    private int mVersion_code;
    private String mVersion_name;
    private String mVersion_desc;
    private String mVersion_path;
    private Context mContext;

//    public UpDateAppUtils(Context context){
//        mContext = context;
//    }

    public UpDateAppUtils getInstance() {
        if (util == null) {
            util = new UpDateAppUtils();
        }
        return util;
    }
    /**
     * 检测软件是否更新
     */
    public void checkUpdate(final Activity activity){
        JSONObject getjsonobj = JsonUtils.getjsonobj("{}");
        String method = "GetNewVersion";
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_WHDCKG_YZ\"}");  //地产控股
        }
        else if("com.a21zhewang.constructionapp.wc".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_WC_YZ\"}");  //武昌建安
        }
        else if("com.a21zhewang.constructionapp.jh".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_JH_YZ\"}");  // 江汉建安
        }
        else if("com.a21zhewang.constructionapp.ja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_JA_YZ\"}");  //  江岸建安
        }
        else if("com.a21zhewang.constructionapp.qk".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_QK_YZ\"}");  //  硚口建安
        }
        else if("com.a21zhewang.constructionapp.hyzj".equals(AppUtils.getPackageName(activity))){
            method = "GetJAVersion";
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"建安版Android\"}");            //汉阳建安
        }
        else if("com.a21zhewang.constructionapp.hs".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_HS_YZ\"}");  //洪山建安
        }
        else if("com.a21zhewang.constructionapp.qsja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_QS_YZ\"}");  //青山建安
        }
        else if("com.a21zhewang.constructionapp.dxh".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_DXH_YZ\"}");  //东西湖建安
        }
        else if("com.a21zhewang.constructionapp.cdja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_CD_YZ\"}");   //蔡甸建安
        }
        else if("com.a21zhewang.constructionapp.jxja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_JX_YZ\"}");   //江夏建安
        }
        else if("com.a21zhewang.constructionapp.hp".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_HP_YZ\"}");   //黄陂建安
        }
        else if("com.a21zhewang.constructionapp.xz".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_XZ_YZ\"}");   //新洲建安
        }
        else if("com.a21zhewang.constructionapp.hn".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_HN_YZ\"}");    //经开（汉南）建安
        }
        else if("com.a21zhewang.constructionapp.dhgx".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_DHGX_YZ\"}"); //东新建安
        }
        else if("com.a21zhewang.constructionapp.saqz".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_SZ_YZ\"}");   //武汉建安
        }
        else if("com.a21zhewang.constructionapp.gdjc".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_GD_YZ\"}");   //广电巡检
        }
        else if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_CQJG_YZ\"}");   //重庆建工
        }
        else if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_QT_YZ\"}");   //重庆建工
        }
        else if("com.a21zhewang.constructionapp.vipa".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_VIP1_YZ\"}"); //安卓建安-V1
        }
        else if("com.a21zhewang.constructionapp.vipb".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_VIP2_YZ\"}");   //安卓建安-V2
        }else {
            return;
        }
        XUtil.postjsondata(getjsonobj, method, new MyCallBack() {

            @Override
            public void onResule(String res) {
                boolean isForceUpdate = false;
                String content = "";
                try {
                    VersionBean verbean = JsonUtils.getbean(res, VersionBean.class);
                   if("com.a21zhewang.constructionapp.hyzj".equals(AppUtils.getPackageName(activity))) {
                       mVersion_code = verbean.getVersion_code();
                       mVersion_name = verbean.getVersion_name();
                       mVersion_desc = verbean.getVersion_desc();
                       content = verbean.getVersion_desc().toString();
                       mVersion_path = verbean.getVersion_path();
                    }else {
                       /*业主版（市安全站版本开启）*/
                       mVersion_code = (new Double(verbean.getVersion())).intValue();
                       mVersion_path = verbean.getVerPath();
                       content = verbean.getVerDisc().toString();
                   }
                    PublicUtils.putspint("serverVersion",mVersion_code);
                    if (isUpdate(activity)) {
                        isForceUpdate = true;
                        updateAPKDialog(content, mVersion_path, activity, isForceUpdate);
                    }else {
                        isForceUpdate = false;
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFail(ObjBean getbean) {

            }
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
     * 与本地版本比较判断是否需要更新
     * 高版本不可用：localVersion = activity.getPackageManager()
     * .getPackageInfo("com.a21zhewang.constructionapp.saqz", 0).versionCode;
     */
    public boolean isUpdate(final  Activity activity){
        PackageManager pm = activity.getPackageManager();
            //获取包的详细信息
        PackageInfo info = null;
            try {
              info = pm.getPackageInfo(activity.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
              e.printStackTrace();
            }
             //获取版本号和版本名称
            int localVersion = 1;
            localVersion = info.versionCode;
            if(mVersion_code > localVersion){
                return true;
            }else {
                return false;
            }
    }
    /**
     * 检查权限-更新必备
     * @param activity
     */
    public void  checkPermission(final Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
            }
        }
    }
    /**
     * 系统提示打开通知栏权限（关系极光推送功能必须开启）
     * @param activity
     */
    public void  OpenSystemNotification(final Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            //showToast("为保证系统正常运行，请打开权限");
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                }, 1);
            }
            if (!NotificationsUtils.isNotificationEnabled(activity)) {
                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                dialog.show();

                View view = View.inflate(activity, R.layout.tabmain_dialog, null);
                dialog.setContentView(view);

                TextView context = (TextView) view.findViewById(R.id.message);
                context.setText("检测到您没有打开通知权限，是否去打开");

                Button confirm = (Button) view.findViewById(R.id.positiveButton);
                confirm.setText("确定");
                confirm.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings",
                                    "com.android.settings.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName",
                                    activity.getPackageName());
                        }
                        activity.startActivity(localIntent);
                    }
                });

                Button cancel = (Button) view.findViewById(R.id.negativeButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        }
    }

    /**
     * 下载操作
     */
    public void updateAPKDialog(String updateContentString, final String url,
                                final Activity activty, final boolean isForceUpdate) {
        Dialog dialog;
        DzDialog.Builder customBuilder = new DzDialog.Builder(activty);
        customBuilder.setTitle(isForceUpdate ? "版本已过期，请尽快升级" : "有新的版本");
        customBuilder.setMessage(updateContentString);
        customBuilder.setNegativeButton(isForceUpdate ? "退出" : "暂不升级",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (isForceUpdate) {
                            activty.finish();
                        }
                    }
                }).setPositiveButton("升级",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermission(activty);
                        dialog.dismiss();
             //==============第1种方式（开启自定义线程下载）=====================================================//
                        if (activty instanceof IndexActivity || activty instanceof IndexActivity2) {
                            if (downDialog == null) {
                                downDialog = new CustomDownDialog(activty);
                            }
                            downDialog.show();
                            mDownloadElement = new DownloadElement(url, new UpDateAppHandler(activty), url);
                            mDownloadElement.start();
                        }
                        //((Act_MainMenu3) activty).showDownloadDialog(url,isForceUpdate);
                    }
                });

            //==============第2种方式（跳转到系统浏览器下载）======================================================//
                        /*Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        activty.startActivity(intent);*/

            //==============第3种方式（跳转到应用宝下载）=========================================================//
                        /*PackageInfo packageInfo = null;
                        PackageManager packageManager = activty.getPackageManager();
                        try {
                            packageInfo = packageManager.getPackageInfo(activty.getPackageName(), 0);
                        } catch (NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        try{
                            launchAppDetail(activty, packageInfo.packageName, "com.tencent.android.qqdownloader");
                        }catch (Exception e){
                            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + packageInfo.packageName);
                            Intent it = new Intent(Intent.ACTION_VIEW, uri);
                            activty.startActivity(it);
                        }*/

           //==============废弃的自定义线程下载（下载缓慢）=========================================================//
                        //new DownloadFileThread(activty, url, isForceUpdate).start();

        dialog = customBuilder.create(R.layout.dialog);
        if (isForceUpdate) {
            dialog.setCancelable(false);
        }
        dialog.show();
    }
    class UpDateAppHandler extends Handler {
        private Activity activity;

        UpDateAppHandler(Activity activity) {
            super(Looper.getMainLooper());
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PublicUtils.IS_DOWNLOADING:
                    //更新下载进度
                    downDialog.setProgressBar(msg.arg1);
                    break;

                case PublicUtils.DOWNLOAD_CANCEL:
                    //取消下载
                    deleteApk();
                    break;

                case PublicUtils.DOWNLOAD_FINISH:
                    //下载完成
                    downDialog.dismiss();
                    File file = (File) msg.obj;
                    if (file.exists()) {
                        installApk(activity, file, true);
                        activity.finish();
                    }
                    break;
                case PublicUtils.DOWNLOAD_ERROR:
                    //下载出错
                    downDialog.dismiss();
                    if (downDialog.isShowing()) {
                        downDialog.dismiss();
                    }
                    break;
            }
        }
    }
    public String getClientVserion(Context context) {
        // 创建包信息的对象
        PackageInfo packageInfo = null;
        // 初始化程序包的对象
        PackageManager packageManager = context.getPackageManager();
        // 获取包的信息
        try {
            packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageInfo.versionName + "";
    }
    /**
     * 通过Intent安装APK文件
     *
     */
    /**
     * 通过Intent安装APK文件
     *
     */
    public void installApk(Context context, File apkfile, boolean isMust) {
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            if(Build.VERSION.SDK_INT >= 24){
                String provider = AppUtils.getPackageName(context)+"."+"provider";
                Uri apkUri = FileProvider.getUriForFile(context, provider,apkfile);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.setDataAndType(apkUri,"application/vnd.android.package-archive");
            }else {
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                        "application/vnd.android.package-archive");
            }
            context.startActivity(i);
            if (isMust) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }catch(ActivityNotFoundException e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    /**
     * 删除下载还没完成的文件
     *
     */
    public void deleteApk() {
//        File apk = new File(SPUtil.get(Act_Base.XML_LOGININFO, "mInstallApkUrl", ""));
        File apk = new File(PublicUtils.getspstring("mInstallApkUrl"));
        DownLoadFileUtils.deleteFile(apk);
    }

    /**
     * 取消下载
     */
    private void cancelDownload() {
        if(mDownloadElement != null)
        {
            mDownloadElement.cancel();
            downDialog.setProgressBar(0);
        }
    }

}
