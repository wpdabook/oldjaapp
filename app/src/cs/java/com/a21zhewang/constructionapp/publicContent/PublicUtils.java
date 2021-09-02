package com.a21zhewang.constructionapp.publicContent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.mytoast.BToast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhewang_ljw on 2017/4/19.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class PublicUtils {

    //武汉测试
//    public final static String HTMLURL="http://106.15.92.195:9002";
//    public final static String URL="https://wuhan.21theone.cn:8100/API/SGGL.aspx";//全局url
//    public final static String URLload="https://wuhan.21theone.cn:8100";//全局urL
//    public final static String ALIAS="WHC_";

//    public final static String HTMLURL="http://106.15.92.195:9002";
//    public final static String URL="https://wuhan.21theone.cn:8001/API/SGGL.aspx";//全局url
//    public final static String URLload="https://wuhan.21theone.cn:8001";//全局urL
//    public final static String ALIAS="WHC_";
    public final static String HTMLURL="http://106.15.92.195:9002";
    public final static String URL="https://safetycore.telsafe.com.cn:8099/API/SGGL.aspx";//全局url
    public final static String URLload="https://safetycore.telsafe.com.cn:8099/";//全局urL
    public final static String ALIAS="WHC_";
    /**部分模块切换市局服务器*/
    public  static String URLload_SJ="https://data-sj.telsafe.com.cn:8088";//全局urL
    public  static String URL_SJ=URLload_SJ+"/API/SGGL.aspx";//全局url
    //上海测试服务器地址
//    public final static String HTMLURL="http://106.15.92.195:9001";
//    public final static String URL="http://106.15.92.195:9002/API/SGGL.aspx";//全局url
//    public final static String URLload="http://106.15.92.195:9002/";//全局urL
//    public final static String ALIAS="WHC_";


//    public final static String HTMLURL="http://106.15.92.195:9001";
//    public final static String URL="http://192.168.90.40:9002/API/SGGL.aspx";//全局url
//    public final static String URLload="http://192.168.90.40:9002/";//全局urL
//    public final static String ALIAS="WHC_";



//    public final static String HTMLURL="http://106.15.92.195:9001";
//    public final static String URL="http://192.168.90.40:9004/API/SGGL.aspx";//全局url
//    public final static String URLload="http://192.168.90.40:9004/";//全局urL
//    public final static String ALIAS="WHC_";


    //测试正式2
//    public final static String HTMLURL="https://wuhan.21theone.cn:8100";
//    public final static String URL="https://wuhan.21theone.cn:8100/API/SGGL.aspx";//全局url
//    public final static String URLload="https://wuhan.21theone.cn:8100/";//全局urL
//    public final static String ALIAS="WHC_";

//局域网测试
// public final static String URL="http://10.168.1.2:8001/API/SGGL.aspx";//全局url
// public final static String URLload="http://10.168.1.2:8001";//全局url
   // public final static String ALIAS="WHB_";

    //武汉正式
//   public final static String URL = "https://wuhan.21theone.cn:8889/API/SGGL.aspx";//全局url
//   public final static String URLload = "https://wuhan.21theone.cn:8889";//全局url
//   public final static String ALIAS="WHZ_";

    //长航
//    public final static String URL = "https://wuhan.21theone.cn:8879/API/SGGL.aspx";//全局url
//    public final static String URLload = "https://wuhan.21theone.cn:8879";//全局url
//    public final static String ALIAS="WHZ_";

    //地铁正式
   // public final static String URL = "http://106.14.167.156:9001/API/SGGL.aspx";//全局url
    //public final static String URLload = "http://106.14.167.156:9001";//全局url
   // public final static String ALIAS="WHD_";

    public static String sign = "";//签名
    public static String UserID = "";//用户id
    public static String Ver = "1.0";//版本号
    public static UserBean userBean = null;//每次登录都要更新！

    private static SharedPreferences mySharedPreferences;
    private static SharedPreferences.Editor myedit;
    //正在下载
    public static final int IS_DOWNLOADING = 1001;

    //取消更新
    public static final int DOWNLOAD_CANCEL = 1002;

    //更新完成
    public static final int DOWNLOAD_FINISH = 1003;

    //更新失败
    public static final int DOWNLOAD_ERROR = 1004;

    /** 打开文件
     * @param context
     * @param file
     */
    public static void openFile(Context context, File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**普通提示信息
     * @param str
     */
    public static void toast(String str) {
       if (str==null)return;
        BToast.showText(MyAppCon.appcontext,str);
    }

    /** 错误信息提示
     * @param str
     */
    public static void toastFalse(String str) {
        if (str==null)return;
        BToast.showText(MyAppCon.appcontext,str,false);
    }

    /** 正确信息提示
     * @param str
     */
    public static void toastTrue(String str) {
        if (str==null)return;
        BToast.showText(MyAppCon.appcontext,str,true);
    }

    /** 调试日志
     * @param log
     */
    public static void log(String log) {
        Log.i("MyLog", log);
    }
    public static void logUrl(String log) {
        Log.e("MyLog", log);
    }
    public static String getspstring(String key) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        return mySharedPreferences.getString(key, "");
    }

    public static int getspint(String key) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        return mySharedPreferences.getInt(key, 0);
    }

    public static boolean getspboolean(String key) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        return mySharedPreferences.getBoolean(key, false);
    }

    public static boolean getspboolean(String key, boolean def) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        return mySharedPreferences.getBoolean(key, def);
    }

    public static void putspstring(String key, String str) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        if (myedit == null) {
            myedit = mySharedPreferences.edit();
        }
        myedit.putString(key, str);
        myedit.commit();
    }

    public static void putspint(String key, int i) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        if (myedit == null) {
            myedit = mySharedPreferences.edit();
        }
        myedit.putInt(key, i);
        myedit.commit();
    }

    public static void putspboolean(String key, boolean b) {
        if (mySharedPreferences == null) {
            mySharedPreferences = MyAppCon.appcontext.getSharedPreferences("login", Activity.MODE_PRIVATE);
        }
        if (myedit == null) {
            myedit = mySharedPreferences.edit();
        }
        myedit.putBoolean(key, b);
        myedit.commit();
        ;
    }

    /** 判断是否是手机号码的方法
     * @param mobiles 手机号码
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        if (TextUtils.isEmpty(mobiles))return false;
        Pattern p = Pattern.compile("^-[0-9]+([.]{0,1}[0-9]+){0,1}$");
        Matcher m = p.matcher(mobiles);
        Pattern b = Pattern.compile("^[0-9]+([.]{0,1}[0-9]+){0,1}$");
        Matcher q = b.matcher(mobiles);
        if (m.matches()||q.matches()){
            return  true;
        }else{
            return false;
        }
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    public  static void  call(String phone,Context context) {
        if (TextUtils.isEmpty(phone)){
            PublicUtils.toast("电话号码为空！");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
