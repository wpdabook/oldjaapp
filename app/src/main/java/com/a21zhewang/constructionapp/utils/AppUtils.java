package com.a21zhewang.constructionapp.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by WP-PC on 2019/11/20.
 */

public class AppUtils {

    /**

     * 获取应用程序名称

     */

    public static synchronized String getAppName(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(

                    context.getPackageName(), 0);

            int labelRes = packageInfo.applicationInfo.labelRes;

            return context.getResources().getString(labelRes);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }



    /**

     * [获取应用程序版本名称信息]

     * @param context

     * @return 当前应用的版本名称

     */

    public static synchronized String getVersionName(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(

                    context.getPackageName(), 0);

            return packageInfo.versionName;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }





    @TargetApi(28)
    public static long getVersionCode(Context context) {
        PackageManager packageManager =context.getPackageManager();

        try {
            PackageInfo packageInfo  = packageManager.getPackageInfo(context.getPackageName(),0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { //Android P
                return packageInfo.getLongVersionCode();
            }else {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    };

    public static void installApk(Activity activity, File apkFile) {

        //TODO N FileProvider
        //Todo O INSTALL PERMISSION的适配
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(activity,activity.getPackageName()+".provider",apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }else {
            uri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    /**
     * 完整性检验MD5(服务器APK与本地是否一致)
     * @param targetFile
     * @return
     */
    public static String getFileMD5(File targetFile) {
        if(targetFile == null || !targetFile.isFile()){
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(targetFile);
            while ((len = in.read(buffer)) != -1){
                digest.update(buffer,0,len);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        byte[] result = digest.digest();
        BigInteger bigInt = new BigInteger(1,result);
        return bigInt.toString(16);
    }





    /**

     * [获取应用程序版本名称信息]

     * @param context

     * @return 当前应用的版本名称

     */

    public static synchronized String getPackageName(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();

            PackageInfo packageInfo = packageManager.getPackageInfo(

                    context.getPackageName(), 0);

            return packageInfo.packageName;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;

    }





    /**

     * 获取图标 bitmap

     * @param context

     */

    public static synchronized Bitmap getBitmap(Context context) {

        PackageManager packageManager = null;

        ApplicationInfo applicationInfo = null;

        try {

            packageManager = context.getApplicationContext()

                    .getPackageManager();

            applicationInfo = packageManager.getApplicationInfo(

                    context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

            applicationInfo = null;

        }

        Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable

        BitmapDrawable bd = (BitmapDrawable) d;

        Bitmap bm = bd.getBitmap();

        return bm;

    }


}
