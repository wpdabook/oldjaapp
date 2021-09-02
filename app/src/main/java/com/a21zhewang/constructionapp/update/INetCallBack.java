package com.a21zhewang.constructionapp.update;

/**
 * Created by Administrator on 2021/7/18.
 */

public interface INetCallBack {

    void success(String response);

    void failed(Throwable throwable);

}
