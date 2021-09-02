package com.a21zhewang.constructionapp.ui.index;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;

/**
 * Created by 10430 on 2018/1/19.
 */

public class JsInterface {
   private Context context;
    public JsInterface( Context context) {
        this.context=context;
    }
    @JavascriptInterface
    public void stratIntent(String what){
        Intent intent=new Intent();
        if ("zl".equals(what)){
            intent.putExtra("method","GetQualityMsgType");
        }else if ("aq".equals(what)){
            intent.putExtra("method","GetSafetyMsgType");
        }else if ("wm".equals(what)){
            intent.putExtra("method","GetCivilizedMsgType");
        }
        intent.setClass(context,LdActivity.class);

        context.startActivity(intent);
    }

}
