package com.a21zhewang.constructionapp.utils;

import android.text.TextUtils;

import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;

import org.xutils.common.Callback;

/**
 * Created by zhewang_ljw on 2016/11/23.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public abstract class MyCallBack implements Callback.CommonCallback<byte[]> {
    public boolean istoast = true;

    public void setIstoast(boolean istoast) {
        this.istoast = istoast;
    }

    /**
     * 请求成功code 等于 0回调
     *
     * @param res
     */
    public abstract void onResule(String res);

    /**
     * 请求成功code 不等于 0回调
     *
     * @param getbean
     */
    public abstract void onFail(ObjBean getbean);

    /**
     * 联网失败回调
     *
     * @param
     */
    public abstract void onWrong();

    /**
     * 取消请求回调
     */
    public void onCancel(CancelledException cex) {

    }

    ;

    /**
     * @param result 请求成功时调用
     */
    @Override
    public void onSuccess(byte[] result) {
        try {
            String decompressForGzip = GzipUtils.decompressForGzip(result);
            if(PublicUtils.IS_DEBUG_MODE){
                PublicUtils.log("请求结果：" + decompressForGzip);
            }
            ObjBean getbean = (ObjBean) JsonUtils.getbean(decompressForGzip, ObjBean.class);
            String msg = getbean.getMsg();
            if (getbean.getCode() != 0) {
                PublicUtils.log(new String(result));
                PublicUtils.toastFalse(msg);
//                PublicUtils.log("请求失败：" + msg);
//                PublicUtils.log("请求失败：" + decompressForGzip);
                onFail(getbean);
                return;
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    if (istoast)
                        PublicUtils.toast(msg);
                }
            }

            //  Object data = getbean.getData();
            // String objtojson= data.toString();

            Object data = getbean.getData();
            String objtojson = JsonUtils.objtojson(data);

//            PublicUtils.log("请求成功：" + objtojson);
            onResule(objtojson);


//可以根据公司的需求进行统一的请求成功的逻辑处理
        } catch (Exception e) {
//            PublicUtils.toastFalse("连网成功，处理数据出错！");
              e.printStackTrace();

        }
    }

    /**
     * @param cex 取消时调用的方法
     */
    @Override
    public void onCancelled(CancelledException cex) {
        onCancel(cex);
    }

    /**
     * @param ex           联网错误时调用的方法
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
//        PublicUtils.toastFalse("网络连接失败！");
//        PublicUtils.log("网络连接失败！" + ex.toString());
        onWrong();
    }

    /**
     * 总是回调用的方法
     */
    @Override
    public void onFinished() {

    }
}