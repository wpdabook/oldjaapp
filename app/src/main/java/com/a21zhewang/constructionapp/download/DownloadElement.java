package com.a21zhewang.constructionapp.download;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by chw.zheng on 2018/4/4.
 */

public class DownloadElement extends Thread
{

    String updateUrl ; // 更新地址

    int progress ;

    boolean cancelUpdate ; // 取消更新

    boolean isError = false ;// 是否出现错误

    public Handler handler ;

    private String apkFile ; // apk下载的路径

    public DownloadElement(String updateUrl , Handler handler , String apkFile )
    {

        this.updateUrl = updateUrl ;
        this.handler = handler ;
        this.apkFile = apkFile ;
    }

    public void cancel ( )
    {

        cancelUpdate = true ;
    }

    @ Override
    public void run ( )
    {
        boolean isSuccessDownLoad = false ;
        File file = null ;
        try
        {
            // 判断SD卡是否存在，并且是否具有读写权限
            if ( Environment.getExternalStorageState ( ).equals ( Environment.MEDIA_MOUNTED ) )
            {
                URL url = new URL ( updateUrl ) ;
                // 创建连接
                HttpURLConnection conn = ( HttpURLConnection ) url.openConnection ( ) ;
                conn.connect ( ) ;
                if(conn.getContentLength()>0)
                {
                    // 获取文件大小
                    file = new File ( StorageUtils.getStorageFile ( ) , apkFile ) ;
                    DownLoadFileUtils.createFolder ( file.getParentFile ( ) ) ;
                    DownLoadFileUtils.createFile ( file ) ;
                    int length = conn.getContentLength ( ) ;
                    // 创建输入流
                    InputStream is = conn.getInputStream ( ) ;
                    FileOutputStream fos = new FileOutputStream ( file ) ;
                    int count = 0 ;
                    // 缓存
                    byte buf[] = new byte [ 1024 ] ;
                    // 写入到文件中
                    do
                    {
                        int numread = is.read ( buf ) ;
                        count += numread ;
                        if ( numread <= 0 )
                        {
                            isSuccessDownLoad = true ;
                            break ;
                        } else
                        {
                            // 计算进度条位置
                            progress = ( int ) ( ( float ) count / length * 100 ) ;
                            // 更新进度
                            Message message = Message.obtain ( ) ;
                            message.what = PublicUtils.IS_DOWNLOADING ;
                            message.arg1 = progress ;
                            handler.sendMessage ( message ) ;
                        }
                        // 写入文件
                        fos.write ( buf , 0 , numread ) ;
                    } while ( ! cancelUpdate ) ;// 点击取消就停止下载.
                    fos.close ( ) ;
                    is.close ( ) ;
                    if(cancelUpdate){
                        cancelUpdate = false;
                        handler.sendEmptyMessage(PublicUtils.DOWNLOAD_CANCEL);
                        return;
                    }
                }
                else{
                    conn.disconnect();
                    isError = true;
                }
            } else
            {

            }
        } catch ( MalformedURLException e )
        {
            e.printStackTrace ( ) ;
            isError = true ;
        } catch ( IOException e )
        {
            e.printStackTrace ( ) ;
            isError = true ;
        }
        Log.d("isSuccessDownLoad", "isSuccessDownLoad");
        if ( isSuccessDownLoad )
        {
            // 下载完成
            String path = MD5Utils.getMD5Str ( file.getName ( ) + "_finish" ) ;
            File renameFile = new File ( file.getParentFile ( ) , path ) ;
            file.renameTo ( renameFile ) ;
            Message message = Message.obtain ( ) ;
            message.what = PublicUtils.DOWNLOAD_FINISH ;
            message.obj = renameFile ;
            handler.sendMessage ( message ) ;
        } else
        {
            if ( file != null )
            {
                DownLoadFileUtils.deleteFile ( file ) ;
            }
            if ( isError )
            {
                // 下载错误
                handler.sendEmptyMessage ( PublicUtils.DOWNLOAD_ERROR ) ;
            } else if ( cancelUpdate )
            {
                // 什么也不做
            }
        }
    }
}
