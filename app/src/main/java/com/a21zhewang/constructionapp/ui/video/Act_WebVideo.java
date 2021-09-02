package com.a21zhewang.constructionapp.ui.video;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import butterknife.BindView;

/**
 * 知识库
 * Created by WP-PC on 2019/3/5.
 */

public class Act_WebVideo extends BaseActivity {


    @BindView(R.id.video_webview)
    WebView webview;

    private Dialog loadingDialog;
    private String PlayUrl;
    private String title;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_webvideo_layout;
    }

    @Override
    public void initViews() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        title = getIntent().getStringExtra("title");
        PlayUrl = getIntent().getStringExtra("PlayUrl");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");

    }
    @Override
    public void initListener() {

    }

    public Act_WebVideo() {
    }

    @Override
    public void initData() {
        WebSettings setting = webview.getSettings();
        setting.setAppCacheEnabled(false);//开启缓存功能
        setting.setDatabaseEnabled(true);
        setting.setDomStorageEnabled(true);

        //开启DOM缓存，关闭的话H5自身的一些操作是无效的
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);

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
        webview.setWebViewClient(new WebViewClient());//
        webview.loadUrl(PlayUrl);
//        /**
//         * 设置为横屏
//         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //webview.loadUrl("http://wpda.mobi/tsnews/index.html");
//        webview.loadUrl("http://news.zhulong.com/prof_SG");
        //webview.loadUrl("http://106.15.92.195:9003"); //地产
//        webview.loadUrl("http://safetycore.telsafe.com.cn:8008"); //市站与汉阳等监管类项目知识库
        //webview.loadUrl("http://www.myfuwu.com.cn/news_list.html");
        //加载进度条
        if (Build.VERSION.SDK_INT < 21) {
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

    @Override
    protected void onResume() {
        if (webview != null) {
            webview.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (webview != null) {
            webview.reload();
        }
        super.onPause();
    }

    @Override
    public void processClick(View v) {

    }
}
