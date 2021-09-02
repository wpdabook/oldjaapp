package com.a21zhewang.constructionapp.update;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.a21zhewang.constructionapp.bean.DownloadBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.CustomDownDialog;
import com.a21zhewang.constructionapp.download.DownloadElement;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONObject;

/**
 * APP 更新工具类
 * Created by WUPENG on 2018/6/1.
 */

public class UpDateUtils {
    private static UpDateUtils util;
    private int mVersion_code;

    public UpDateUtils getInstance() {
        if (util == null) {
            util = new UpDateUtils();
        }
        return util;
    }
    /**
     * 检测软件是否更新
     */
    public void Update(final FragmentActivity activity){
        JSONObject getjsonobj = JsonUtils.getjsonobj("{}");
        String method = "GetNewVersion";
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_WHDCKG_YZ\"}");  //地产控股
        }
        else if("com.a21zhewang.constructionapp.wc".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_WC_YZ\"}");  //武昌建安
        }
        else if("com.a21zhewang.constructionapp.jh".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_JH_YZ\"}");  // 江汉建安
        }
        else if("com.a21zhewang.constructionapp.ja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_JA_YZ\"}");  //  江岸建安
        }
        else if("com.a21zhewang.constructionapp.qk".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_QK_YZ\"}");  //  硚口建安
        }
        else if("com.a21zhewang.constructionapp.hyzj".equals(AppUtils.getPackageName(activity))){
            method = "GetJAVersion";
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"建安版Android\"}");            //汉阳建安
        }
        else if("com.a21zhewang.constructionapp.hs".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_HS_YZ\"}");  //洪山建安
        }
        else if("com.a21zhewang.constructionapp.qsja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_QS_YZ\"}");  //青山建安
        }
        else if("com.a21zhewang.constructionapp.dxh".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_DXH_YZ\"}");  //东西湖建安
        }
        else if("com.a21zhewang.constructionapp.cdja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_CD_YZ\"}");   //蔡甸建安
        }
        else if("com.a21zhewang.constructionapp.jxja".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_JX_YZ\"}");   //江夏建安
        }
        else if("com.a21zhewang.constructionapp.hp".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_HP_YZ\"}");   //黄陂建安
        }
        else if("com.a21zhewang.constructionapp.xz".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_XZ_YZ\"}");   //新洲建安
        }
        else if("com.a21zhewang.constructionapp.hn".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_HN_YZ\"}");    //经开（汉南）建安
        }
        else if("com.a21zhewang.constructionapp.dhgx".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_DHGX_YZ\"}"); //东新建安
        }
        else if("com.a21zhewang.constructionapp.saqz".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_SZ_YZ\"}");   //武汉建安
        }
        else if("com.a21zhewang.constructionapp.gdjc".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_GD_YZ\"}");   //广电巡检
        }
        else if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_CQJG_YZ\"}");   //重庆建工
        }
        else if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_QT_YZ\"}");   //重庆建工
        }
        else if("com.a21zhewang.constructionapp.vipa".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_VIP1_YZ\"}"); //安卓建安-V1
        }
        else if("com.a21zhewang.constructionapp.vipb".equals(AppUtils.getPackageName(activity))){
            getjsonobj = JsonUtils.getjsonobj("{\"AppType\":\"ANDROID_SSS_DATA_VIP2_YZ\"}");   //安卓建安-V2
        }else {
            return;
        }
        XUtil.postjsondata(getjsonobj, method, new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    DownloadBean verbean =  JsonUtils.getbean(res, DownloadBean.class);
                    if("com.a21zhewang.constructionapp.hyzj".equals(AppUtils.getPackageName(activity))) {
                        mVersion_code = verbean.version_code;
                        verbean.setVersionSign(0);
                    }else {
                       /*业主版（市安全站版本）*/
                        mVersion_code = (new Double(verbean.Version)).intValue();
                        verbean.setVersionSign(1);
                    }
                    PublicUtils.putspint("serverVersion",mVersion_code);
                    if (mVersion_code <= AppUtils.getVersionCode(activity)) {
                            return;
                    }else {
                        UpdateAppShowDialog.show(activity, verbean);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }

}
