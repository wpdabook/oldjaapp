package com.a21zhewang.constructionapp.ui;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;


/**
 * Created by 56864 on 2019/4/21.
 */

public class Act_WebView extends BaseActivity{
    private WebView webview;
    private Dialog loadingDialog;
    private MyTitleBar myTitleBar;
    private String ADRESS,fromWhere;
    private String BASE_WEB_URL;


    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    public void initViews() {
        myTitleBar =(MyTitleBar)findViewById(R.id.myTitleBar);
        webview = (WebView)findViewById(R.id.res_webview);

        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        ADRESS = getIntent().getStringExtra("url");
//        BASE_WEB_URL = API.URL2+ADRESS;
        BASE_WEB_URL = ADRESS;
        fromWhere = getIntent().getStringExtra("fromWhere");
        if("a".equals(fromWhere)){
            myTitleBar.setTitlenametv("决策小屏");
        }else if("z".equals(fromWhere)){
            myTitleBar.setTitlenametv("今日小屏");
        }
    }

    @Override
    public void initListener() {
        WebSettings setting = webview.getSettings();
        setting.setAppCacheEnabled(true);
        setting.setDatabaseEnabled(true);
        setting.setDomStorageEnabled(true);

        //不开启DOM缓存，只从网络获取数据.
//        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM缓存，关闭的话H5自身的一些操作是无效的
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);

        //去除横竖滚动条
        webview.setHorizontalScrollBarEnabled(false); //水平不显示
        webview.setVerticalScrollBarEnabled(false);   //垂直不显示

        // 设置支持JavaScript脚本
        setting.setJavaScriptEnabled(true);

        //屏幕自适应
        setting.setUseWideViewPort(true);

        //适应全屏 39适应竖屏 57适应横屏 (达到和PC上浏览器显示的效果完全一样)
        webview.setInitialScale(39);
        //提高渲染的优先级,把图片加载放在最后来加载渲染
        setting.setBlockNetworkImage(false);
        //设置WebView可触摸放大缩小
        setting.setBuiltInZoomControls(false);

        //不调用系统浏览器
        webview.setWebViewClient(new WebViewClient());
        //webview.loadUrl("http://wpda.mobi/tsnews/index.html");
        webview.loadUrl(BASE_WEB_URL);
        //webview.loadUrl("http://www.myfuwu.com.cn/news_list.html");
        //加载进度条
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webview.setVisibility(View.GONE);
                loadingDialog.show();
                //把网页加载的进度传给我们的进度条
                if (newProgress == 100){
                    webview.setVisibility(View.VISIBLE);
                    WeiboDialogUtils.closeDialog(loadingDialog);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            //从视图中移除
            ((ViewGroup) webview.getParent()).removeView(webview);
            //停止加载
            webview.stopLoading();
            webview.onPause();
            //清除历史
            webview.clearHistory();
            //清除缓存
            webview.clearCache(true);
            webview.clearFormData();
            webview.clearSslPreferences();
            webview.destroyDrawingCache();
            //移除WebView上子view
            webview.removeAllViews();
            //最后再去销毁WebView自身
            webview.destroy();
        }
    }
}
