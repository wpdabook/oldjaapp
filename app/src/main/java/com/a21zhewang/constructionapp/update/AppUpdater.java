package com.a21zhewang.constructionapp.update;


/**
 * 对外调用单例实体类
 * 1.独立更新功能 AppUpdater
 * 2.通过接口去屏蔽具体的实现
 * 3.https接口、OkHttp证书的配置、
 * 4.DialogFragment
 * 5.apk安装，N：FileProvider O:INSTALL PERMISSION
 * 6.ANDROID P禁止直接使用访问http的接口
 * 7.CanCle
 * Created by Administrator on 2021/7/18.
 *
 */

public class AppUpdater {

    /**单例模式*/
    private static AppUpdater sInstance = new AppUpdater();

    /**网络请求，下载的能力*/
    private INetManager mNetManager = new OkHttpNetManager();

    public INetManager getmNetManager() {
        return mNetManager;
    }


    public static AppUpdater getsInstance(){
        return sInstance;
    }

}
