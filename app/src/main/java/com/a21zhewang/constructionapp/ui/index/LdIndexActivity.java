package com.a21zhewang.constructionapp.ui.index;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
@SuppressLint("JavascriptInterface")
public class LdIndexActivity extends BaseActivity {


    @BindView(R.id.lin_LdIndexActivity_root)
    LinearLayout linLdIndexActivityRoot;
    private WebView webView;

    private Dialog loadingDialog;

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_ld_index;
    }

    /**
     * 初始化界面
     */
    @SuppressLint("JavascriptInterface")
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        webView = new WebView(this);
        linLdIndexActivityRoot.addView(webView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebSettings setting = webView.getSettings();
        setting.setAppCacheEnabled(true);
        setting.setDatabaseEnabled(true);
        setting.setDomStorageEnabled(true);

        //开启DOM缓存，关闭的话H5自身的一些操作是无效的
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);

        //去除横竖滚动条
        webView.setHorizontalScrollBarEnabled(false); //水平不显示
        webView.setVerticalScrollBarEnabled(false);   //垂直不显示

        // 设置支持JavaScript脚本
        setting.setJavaScriptEnabled(true);

        //屏幕自适应
        setting.setUseWideViewPort(true);

        //适应全屏 39适应竖屏 57适应横屏 (达到和PC上浏览器显示的效果完全一样)
        webView.setInitialScale(39);
        //提高渲染的优先级,把图片加载放在最后来加载渲染
        setting.setBlockNetworkImage(false);
        //设置WebView可触摸放大缩小
        setting.setBuiltInZoomControls(false);

        //不调用系统浏览器
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                webView.setVisibility(View.GONE);
                loadingDialog.show();
                //把网页加载的进度传给我们的进度条
                if (newProgress == 100){
                    webView.setVisibility(View.VISIBLE);
                    WeiboDialogUtils.closeDialog(loadingDialog);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl(PublicUtils.HTMLURL+"/pages/app/web/index.html?userid="+PublicUtils.userBean.getUserID());

    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView!=null)
            webView.onPause();
    }

    @Override
    public void resume() {
        super.resume();
        if (webView!=null)
            webView.onResume();
    }

    @Override
    public void destroy() {
        if (webView != null) {
            //从视图中移除
            ((ViewGroup) webView.getParent()).removeView(webView);
            //停止加载
            webView.stopLoading();
            webView.onPause();
            //清除历史
            webView.clearHistory();
            //清除缓存
            webView.clearCache(true);
            webView.clearFormData();
            webView.clearSslPreferences();
            webView.destroyDrawingCache();
            //移除WebView上子view
            webView.removeAllViews();
            //最后再去销毁WebView自身
            webView.destroy();
        }
    }
}
