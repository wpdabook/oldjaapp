package com.a21zhewang.constructionapp.update;

import java.io.File;

/**
 * Created by Administrator on 2021/7/18.
 */

public interface INetDownLoadCallBack {

    void success(File apkFile);

    void progress(int progresss);

    void failed(Throwable throwable);
}
