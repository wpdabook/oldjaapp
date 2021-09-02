package com.a21zhewang.constructionapp.update;

import java.io.File;

/**
 * 网络接口
 * Created by Administrator on 2021/7/18.
 */

public interface INetManager {

     void get(String url, INetCallBack callBack, Object tag);

     void download(String url, File targetfile, INetDownLoadCallBack callback, Object tag);

     /**
      * 下载过程中，点击返回键，停止下载
      * @param tag
      */
     void cancel(Object tag);
}
