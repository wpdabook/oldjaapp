package com.a21zhewang.constructionapp.utils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.a21zhewang.constructionapp.publicContent.PublicUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by zhewang_ljw on 2016/11/23.
 */

public class XUtil {
    /**
     * 发送get请求
     * @param url
     * @return
     * @param map
     * @param callback
     * @param <T>
     */
    public static <T> Cancelable Get(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60000);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 发送post请求
     * @param url
     * @return
     * @param map
     * @param callback
     * @param <T>
     */
    public static <T> Cancelable Post(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60000);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 上传文件
     *
     * @param url
     * @return
     * @param map
     * @param callback
     * @param <T>
     */
    public static <T> Cancelable UpLoadFile(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60000);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 下载文件
     *
     * @param url
     *
     * @param filepath
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Cancelable DownLoadFile(String url, String filepath, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60000);
//设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * post上传
     *
     * @param url
     * @return
     * @param file
     * @param filename
     * @param callback
     * @param <T>
     */
    public static <T> Cancelable updata(String url, File file, String filename, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60000);
        params.addQueryStringParameter("name", filename);
        params.addQueryStringParameter("version","2");
        params.addBodyParameter("file", file);
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**

     *
     * @param url      请求地址
     * @param file     上传的文件
     * @param json     上传的json数据
     * @param method   请求参数
     * @param callback 回调方法
     * @param <T>
     * @return
     */


    public static <T> Cancelable updatas(String url, File file, String json,String method, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(60000);
        params.addQueryStringParameter("method",method);
        params.addQueryStringParameter("version","2");
        params.addBodyParameter("json", GzipUtils.compressForGzip(json), "application/octet-stream", "JsonData");
        params.addBodyParameter("file", GzipUtils.compressForGzip(file),"application/octet-stream",file.getName());
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }
    /***
     *
     ** post up json and files
     * @param obj     上传json对象
     * @param files  存放文件路径的集合
     * @param method  请求参数
     * @param callback
     * @return*/
    public static <T> Cancelable updatas(Object obj, List<String> files,String method, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(PublicUtils.URL);
        params.setConnectTimeout(60000);
        String tojson = tojson(obj, method);
//        PublicUtils.log("请求："+tojson);
        params.addBodyParameter("json", GzipUtils.compressForGzip(tojson), "application/octet-stream", "JsonData");
        if (files!=null&&files.size()>0){
        for (int i=0;i<files.size();i++){
            File value = new File(files.get(i));
                params.addBodyParameter("file"+i, GzipUtils.compressForGzip(value) ,"application/octet-stream",value.getName());
        }}
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }
    /***
     *  通知文件-需调市局接口
     ** post up json and files
     * @param obj     上传json对象
     * @param files  存放文件路径的集合
     * @param method  请求参数
     * @param callback
     * @return*/
    public static <T> Cancelable updatassj(Object obj, List<String> files,String method, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(PublicUtils.URL_SJ);

        params.setConnectTimeout(60000);
        String tojson = tojson(obj, method);
        Log.i("请求：",tojson);
        params.addBodyParameter("json", GzipUtils.compressForGzip(tojson), "application/octet-stream", "JsonData");
        if (files!=null&&files.size()>0){
            for (int i=0;i<files.size();i++){
                File value = new File(files.get(i));

                params.addBodyParameter("file"+i, GzipUtils.compressForGzip(value) ,"application/octet-stream",value.getName());

            }}
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /***
     * @param obj      请求对象
     * @param method   请求方法
     * @param callback 回调对象
     * @return
     */
    public static <T> Cancelable postjsondata(Object obj, String method,CommonCallback<T> callback) {
        RequestParams params = new RequestParams(PublicUtils.URL);
        params.setConnectTimeout(60000);
        String tojson = tojson(obj, method);
        if(PublicUtils.IS_DEBUG_MODE){
            PublicUtils.log("request>>"+PublicUtils.URL+" + "+tojson);
        }
        params.addBodyParameter("json", GzipUtils.compressForGzip(tojson), "application/octet-stream", "JsonData");
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }
    /**
     * 通知文件-需调市局服务器
     * @param obj
     * @param method
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Cancelable postjsondatasj(Object obj, String method,CommonCallback<T> callback) {
        RequestParams params = new RequestParams(PublicUtils.URL_SJ);
        params.setConnectTimeout(60000);
        String tojson = tojson(obj, method);
//        API.log("请求："+tojson);
        if(PublicUtils.IS_DEBUG_MODE){
            Log.i("request_sj>>",PublicUtils.URL_SJ+" + "+tojson);
        }
//        Logger.json(PublicUtils.URL + tojson);
        params.addBodyParameter("json", GzipUtils.compressForGzip(tojson), "application/octet-stream", "JsonData");
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }
    /***
     * 地产控股版本安全-文明-施工请求构造方法类
     * @param obj      请求对象
     * @param method   请求方法
     * @param callback 回调对象
     * @return
     */
    public static <T> Cancelable postjsondataother(Object obj, String URL,String method,CommonCallback<T> callback) {
        RequestParams params = new RequestParams(URL);
        params.setConnectTimeout(60000);
        String tojson = tojson(obj, method);
        if(PublicUtils.IS_DEBUG_MODE){
            PublicUtils.log("other_request>>"+URL+" + "+tojson);
        }
        params.addBodyParameter("json", GzipUtils.compressForGzip(tojson), "application/octet-stream", "JsonData");
        params.setMultipart(true);
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 转换为请求对象
     * @param obj
     * @param method
     * @return
     */
    public static String tojson(Object obj,String method){

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("Method",method);
            jsonObject.put("UserID",PublicUtils.getspstring("user"));
            jsonObject.put("Ver",PublicUtils.Ver);
            jsonObject.put("From","Android");
            //获取手机厂商、手机型号、手机系统版本号
            jsonObject.put("Phone", PublicUtils.UserID+"-"+Build.MANUFACTURER + Build.MODEL + "-"+ Build.VERSION.RELEASE);
            jsonObject.put("Sign", PublicUtils.sign);
            if (!(obj instanceof String)){
                jsonObject.put("Data", obj);
            }
        } catch (JSONException e) {
            PublicUtils.toast("json生成出错");
//            e.printStackTrace();
        }
        return jsonObject.toString();

    }
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     *	pxתdp
     */
    public static int px2dip(Context ctx,float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}