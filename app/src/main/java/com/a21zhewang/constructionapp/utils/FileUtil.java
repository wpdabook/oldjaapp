package com.a21zhewang.constructionapp.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zhewang_ljw on 2017/10/9.
 *
 * @auto
 */

public class FileUtil {
    /**
     * 删除某个文件夹下的所有文件夹和文件
     * @param delpath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static boolean deleteFile(String delpath)
           {

        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
                System.out.println("1");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    File delfile = fileList[i];
                    if (!delfile.isDirectory()) {
                        System.out.println("相对路径=" + delfile.getPath());
                        System.out.println("绝对路径=" + delfile.getAbsolutePath());
                        System.out.println("文件全名=" + delfile.getName());
                        delfile.delete();
                        System.out.println("删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deleteFile(fileList[i].getPath());
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void deleteCaCheImage(){
        deleteFile(getSavaPath());
    }

    /**
     * @return 获取 保存路径
     */
    public static  String getSavaPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString() + "/ImageOfConAPP/";
    }
}
