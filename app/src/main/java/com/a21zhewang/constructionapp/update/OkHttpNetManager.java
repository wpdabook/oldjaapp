package com.a21zhewang.constructionapp.update;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2021/7/18.
 */

public class OkHttpNetManager implements INetManager{

    private static OkHttpClient sOkHttpClient;

    public static Handler sHandler = new Handler(Looper.getMainLooper());


    static {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(20, TimeUnit.SECONDS);
        sOkHttpClient = builder.build();
        /**
         * https 自签名的，OkHttp 握手的错误
         *       builder.sslSocketFactory();
         */

    }

    @Override
    public void get(String url, final INetCallBack callBack, Object tag) {
        /**
         * Request.Builder builder -> Request -> Call -> execute/enqueue
         */
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).get().tag(tag).build();
        Call call = sOkHttpClient.newCall(request);
        /**
         * 同步：在当前线程内直接执行
         * 异步：会返回一个callback进行处理
         */
        //Response response = call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //非UI线程
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                      callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.success(string);
                        }
                    });
                }catch (Throwable e){
                    e.printStackTrace();
                    callBack.failed(e);
                }

            }
        });


    }

    @Override
    public void download(String url, final File targetFile, final INetDownLoadCallBack callback, Object tag) {
            if(!targetFile.exists()){
                targetFile.getParentFile().mkdir();
            }
        Request.Builder builder = new Request.Builder();
        final Request request = builder.url(url).get().tag(tag).build();
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //非UI线程
                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                OutputStream os = null;
                try {
                    final long totalLen = response.body().contentLength();

                    is = response.body().byteStream();
                    os = new FileOutputStream(targetFile);

                    byte[] buffer = new byte[8*1024];
                    int curLen = 0;

                    int bufferLen = 0;
                    while (!call.isCanceled() && (bufferLen = is.read(buffer)) != -1){
                        os.write(buffer,0,bufferLen);
                        os.flush();
                        curLen += bufferLen;

                        final long finalCurLen = curLen;
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.progress((int) (finalCurLen * 1.0f / totalLen * 100));
                            }
                        });
                    }
                    if(call.isCanceled()){
                        return;
                    }
                    try {
                        targetFile.setExecutable(true,false); //可执行
                        targetFile.setReadable(true,false);   //可读
                        targetFile.setWritable(true,false);   //可写
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.success(targetFile);
                        }
                    });
                }catch (final Throwable e){
                    if(call.isCanceled()){
                        return;
                    }
                    e.printStackTrace();
                    sHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.failed(e);
                        }
                    });
                }finally {
                    if(is != null ){
                        is.close();
                    }
                    if(os != null){
                        os.close();
                    }
                }

            }
        });

    }

    @Override
    public void cancel(Object tag) {
        List<Call> queuedCalls = sOkHttpClient.dispatcher().queuedCalls();
        if(queuedCalls != null){
            for(Call call:queuedCalls){
                if(tag.equals(call.request().tag())){
                    Log.d("find1 call=", "find call="+tag);
                    call.cancel();
                }
            }
        }
        List<Call> runqueuedCalls = sOkHttpClient.dispatcher().runningCalls();
        if(runqueuedCalls != null){
            for(Call call:runqueuedCalls){
                if(tag.equals(call.request().tag())){
                    Log.d("find2 call=", "find call="+tag);
                    call.cancel();
                }
            }
        }
    }
}
