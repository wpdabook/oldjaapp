package com.a21zhewang.constructionapp.ui.index;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AqscSpinnerBean;
import com.a21zhewang.constructionapp.bean.ListBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProjectSynopsis;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.theone.droplist.DropListView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LdActivity extends BaseActivity {


    @BindView(R.id.v4_drawerlayout)
    DrawerLayout v4Drawerlayout;
    @BindView(R.id.mume_RecyclerView)
    RecyclerView mumeRecyclerView;
    @BindView(R.id.tv_sx)
    TextView tvSx;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mView_pager;
    @BindView(R.id.tv_sjd)
    TextView tvSjd;
    @BindView(R.id.tv_fxdl)
    TextView tvFxdl;
    @BindView(R.id.tv_fxdj)
    TextView tvFxdj;
    @BindView(R.id.lin_root)
    LinearLayout linRoot;
    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.btn_sumbit)
    Button btnSumbit;
    @BindView(R.id.MyTitleBar)
    com.a21zhewang.constructionapp.customview.MyTitleBar MyTitleBar;
    private DropListView dropListView, dropListView_fxdl, dropListView_fxdj;
    private List<String> menus = new ArrayList<>();
    private CommonNavigator commonNavigator;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    private WebView webView;
    private SelectorAdapter<String> adapter;
    private SelectorAdapter<ProjectSynopsis> quickAdapter;
    private SelectorAdapter<MsgType> adapter_fxdl;
    private List<String> types, projects, levels;
    private String method;
    private JSONObject jsonObject = new JSONObject();
    private SelectorAdapter<String> adapter_fxdj;

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
        return R.layout.activity_ld;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        method = getIntent().getStringExtra("method");
        String what = "";
        if ("GetSafetyMsgType".equals(method)) {
            what = "安全";
            MyTitleBar.setTitlenametv("安全检查");
        } else if ("GetQualityMsgType".equals(method)) {
            what = "质量";
            MyTitleBar.setTitlenametv("质量检查");
        } else if ("GetCivilizedMsgType".equals(method)) {
            what = "文明";
            MyTitleBar.setTitlenametv("文明施工");
        }
        menus.add(what + "趋势");
        menus.add("风险类型");
        try {
            if ("GetSafetyMsgType".equals(method)) {
                menus.add("风险等级");
                tvFxdj.setVisibility(View.VISIBLE);
                jsonObject.put("getModule", "Safe");
            } else if ("GetQualityMsgType".equals(method)) {//质量
                jsonObject.put("getModule", "Quality");

            } else if ("GetCivilizedMsgType".equals(method))//文明
            {
                jsonObject.put("getModule", "Civilized");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        menus.add("整改率");
        initView();
        types = new ArrayList<>();
        projects = new ArrayList<>();
        levels = new ArrayList<>();
        levels.add("1");
        levels.add("2");
        levels.add("3");
        levels.add("4");
        levels.add("5");

    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        // String method= getIntent().getStringExtra("method");
        initMagicIndicator();
    }

    private void initMagicIndicator() {


        mView_pager.setAdapter(new ExamplePagerAdapter(menus));
        magicIndicator.setBackgroundColor(Color.parseColor("#0084CF"));
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return menus == null ? 0 : menus.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                // simplePagerTitleView.setTextSize(getResources().getDimensionPixelSize(R.dimen.x26));
                simplePagerTitleView.setText(menus.get(index));
                simplePagerTitleView.setNormalColor(Color.WHITE);
                simplePagerTitleView.setSelectedColor(Color.WHITE);
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimplePagerTitleView view = (SimplePagerTitleView) v;
                        String text = view.getText().toString();
                        try {

                            String what = "";
                            if ("GetSafetyMsgType".equals(method)) {
                                what = "安全";
                            } else if ("GetQualityMsgType".equals(method)) {
                                what = "质量";
                            } else if ("GetCivilizedMsgType".equals(method)) {
                                what = "文明";
                            }
                            if (text.equals(what + "趋势")) {
                                jsonObject.put("topTag", "trendChart");
                            } else if ("风险类型".equals(text)) {
                                jsonObject.put("topTag", "typeChart");

                            } else if ("风险等级".equals(text)) {
                                jsonObject.put("topTag", "levelChart");

                            } else if ("整改率".equals(text)) {
                                jsonObject.put("topTag", "percentageChart");

                            }
                            loadjs("iosCallJs", jsonObject);
                        } catch (Exception e) {

                        }
                        mView_pager.setCurrentItem(index);
                        // commonNavigator.onPageSelected(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.WHITE);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(getResources().getDimension(R.dimen.y4));
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mView_pager);
        //  mView_pager.setCurrentItem(0);
    }

    /**
     * 获取下拉列表数据
     */
    private void getspinnerdata(String Method) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectID\":\"\"}"), Method, new MyCallBack() {
            @Override
            public void onResule(String res) {
                AqscSpinnerBean spinnerBean = JsonUtils.getbean(res, AqscSpinnerBean.class);
                List<MsgType> msgTypes = spinnerBean.getMsgTypes();
                if (msgTypes != null && msgTypes.size() > 0) {
                    for (MsgType type : msgTypes) {
                        types.add(type.getTypeID());
                    }
                    adapter_fxdl.replaceData(msgTypes);
                }

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                try {
                    jsonObject.put("topTag", "trendChart");
                    jsonObject.put("week", TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
                    jsonObject.put("typeList", new JSONArray(JsonUtils.objtojson(types)));
                    jsonObject.put("projects", new JSONArray(JsonUtils.objtojson(projects)));
                    if ("GetSafetyMsgType".equals(method))
                        jsonObject.put("level", new JSONArray(JsonUtils.objtojson(levels)));
                } catch (JSONException e) {
                    // e.printStackTrace();
                }

                loadjs("iosCallJs", jsonObject);
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        webView = new WebView(this);
        linRoot.addView(webView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //     XUtil.tojson("","GetStatisticalSummaryDetails");
//                XUtil.postjsondata(
//                        JsonUtils.getjsonobj("{\"pageIndex\":\"0\",\"pageSize\":\"100\",\"etpID\":\"\"}"),
//                        "GetProjectList", new MyCallBack() {
//                            @Override
//                            public void onResule(String res) {
//                                ListBean projectSynopsisListBean = JsonUtils.getbean(res, ListBean.class);
//                                List<ProjectSynopsis> recordList = projectSynopsisListBean.getRecordList();
//
//                                if (recordList != null && recordList.size() > 0) {
//                                    for (ProjectSynopsis projectSynopsis : recordList) {
//                                        projects.add(projectSynopsis.getProjectID());
//                                    }
//                                    quickAdapter.replaceData(recordList);
//
//                                }
//                            }
//
//                            @Override
//                            public void onFail(ObjBean getbean) {
//
//                            }
//
//                            @Override
//                            public void onWrong() {
//
//                            }
//
//                            @Override
//                            public void onFinished() {
//
//                                getspinnerdata(method);
//                                super.onFinished();
//                            }
//                        });

            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
         webView.addJavascriptInterface(this,"Android");

        webView.loadUrl(PublicUtils.HTMLURL+"/pages/app/web/index.html?userid="+PublicUtils.userBean.getUserID());
    }

    public void loadjs(String method, JSONObject value) {
        String tojson = XUtil.tojson(value, "GetStatisticalSummaryDetails");
        PublicUtils.log(tojson);
        webView.loadUrl("javascript:" + method + "('" + tojson + "')");
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    private void initView() {
        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#F2F2F1"))
                .sizeResId(R.dimen.x1)
                .build();
        initDate();
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        List<String> strings = new ArrayList<>();
        strings.add("一周");
        strings.add("一月");
//        strings.add("一季度");
//        strings.add("一年");
        adapter = new SelectorAdapter<String>(strings) {
            @Override
            void imp(CheckBox helper, String item) {
                helper.setText(item);
            }


        };
        recyclerView.addItemDecoration(horizontalDividerItemDecoration);
        // adapter.setSelectors(true);
        recyclerView.setAdapter(adapter);

        dropListView = new DropListView(this, recyclerView);
        dropListView.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvSjd.setBackground(null);
                String bean = adapter.getBean();
                try {
                    String value = TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd"));
                    if ("一周".equals(bean)) {
                        jsonObject.put("month", "");
                        jsonObject.put("week", value);
                    } else {
                        jsonObject.put("week", "");
                        jsonObject.put("month", value);
                    }
                } catch (Exception e) {

                }

                loadjs("iosCallJs", jsonObject);
            }
        });
        tvSjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.WHITE);
                dropListView.showat(v);
            }
        });

        RecyclerView recyclerView_fxdl = new RecyclerView(this);
        recyclerView_fxdl.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_fxdl.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));

        adapter_fxdl = new SelectorAdapter<MsgType>(null) {
            @Override
            void imp(CheckBox helper, MsgType item) {
                helper.setText(item.getTypeName());
            }


        };
        adapter_fxdl.setSelectors(true);
        recyclerView_fxdl.addItemDecoration(horizontalDividerItemDecoration);
        // adapter.setSelectors(true);
        recyclerView_fxdl.setAdapter(adapter_fxdl);

        dropListView_fxdl = new DropListView(this, recyclerView_fxdl);
        dropListView_fxdl.setHeight((int) getResources().getDimension(R.dimen.x400));
        dropListView_fxdl.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvFxdl.setBackground(null);
                try {
                    JSONArray jsonArray = new JSONArray();
                    for (MsgType msgType : adapter_fxdl.getHashSet()) {
                        jsonArray.put(msgType.getTypeID());
                    }
                    jsonObject.put("typeList", jsonArray);
                } catch (Exception e) {

                }

                loadjs("iosCallJs", jsonObject);
            }
        });
        tvFxdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.WHITE);
                dropListView_fxdl.showat(v);
            }
        });


        RecyclerView recyclerView_fxdj = new RecyclerView(this);
        recyclerView_fxdj.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_fxdj.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        List<String> strings_fxdj = new ArrayList<>();
        strings_fxdj.add("一级");
        strings_fxdj.add("二级");
        strings_fxdj.add("三级");
        strings_fxdj.add("四级");
        strings_fxdj.add("五级");

        adapter_fxdj = new SelectorAdapter<String>(strings_fxdj) {
            @Override
            void imp(CheckBox helper, String item) {
                helper.setText(item);
            }


        };
        adapter_fxdj.setSelectors(true);
        recyclerView_fxdj.addItemDecoration(horizontalDividerItemDecoration);
        // adapter.setSelectors(true);
        recyclerView_fxdj.setAdapter(adapter_fxdj);

        dropListView_fxdj = new DropListView(this, recyclerView_fxdj);
        dropListView_fxdj.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                tvFxdj.setBackground(null);
                try {
                    JSONArray jsonArray = new JSONArray();
                    for (String s : adapter_fxdj.getHashSet()) {
                        if ("一级".equals(s)) {
                            jsonArray.put("1");
                        } else if ("二级".equals(s)) {
                            jsonArray.put("2");
                        } else if ("三级".equals(s)) {
                            jsonArray.put("3");
                        } else if ("四级".equals(s)) {
                            jsonArray.put("4");
                        } else if ("五级".equals(s)) {
                            jsonArray.put("5");
                        }
                    }
                    jsonObject.put("level", jsonArray);
                } catch (Exception e) {

                }
                loadjs("iosCallJs", jsonObject);
            }
        });
        tvFxdj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.WHITE);
                dropListView_fxdj.showat(v);
            }
        });

    }

    private void initDate() {

        mumeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /**
         * Implement this method and use the helper to adapt the view to the given item.
         *
         * @param helper A fully initialized helper.
         * @param item   The item that needs to be displayed.
         */quickAdapter = new SelectorAdapter<ProjectSynopsis>(R.layout.ld_mume_item, null) {

            @Override
            void imp(CheckBox view, ProjectSynopsis item) {
                // CheckBox itemView = (CheckBox) helper.itemView;
                view.setText(item.getShortName());
            }
        };
        quickAdapter.setSelectors(true);
        mumeRecyclerView.setAdapter(quickAdapter);

        tvSx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrawerLayout();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAdapter.getHashSet().clear();
                quickAdapter.notifyDataSetChanged();
            }
        });
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    HashSet<ProjectSynopsis> hashSet = quickAdapter.getHashSet();


                    JSONArray jsonArray = new JSONArray();
                    if (hashSet != null && hashSet.size() > 0)
                        for (ProjectSynopsis datum : hashSet) {
                            jsonArray.put(datum.getProjectID());
                        }
                    jsonObject.put("projects", jsonArray);
                    loadjs("iosCallJs", jsonObject);
                    showDrawerLayout();
                } catch (Exception e) {

                }
            }
        });
    }

    private void showDrawerLayout() {
        if (!v4Drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
            v4Drawerlayout.openDrawer(Gravity.RIGHT);
        } else {
            v4Drawerlayout.closeDrawer(Gravity.RIGHT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null)
            webView.onPause();
    }

    @Override
    public void resume() {
        super.resume();
        if (webView != null)
            webView.onResume();
    }

    @Override
    public void destroy() {
        super.destroy();
        if (webView != null)
            webView.destroy();
        webView = null;
    }


    @JavascriptInterface
    public void stratIntentxq(String id){
        Intent intent=new Intent();
        intent.putExtra("recordID",id);
        if ("GetSafetyMsgType".equals(method)){
            //  intent.putExtra("method","GetQualityMsgType");
            intent.setClass(this,AqscxqActivity.class);
        }else if ("GetQualityMsgType".equals(method)){
            //intent.putExtra("method","GetSafetyMsgType");
            intent.setClass(this,ZljdxqActivity.class);
        }else if ("GetCivilizedMsgType".equals(method)){
            // intent.putExtra("method","GetCivilizedMsgType");
            intent.setClass(this,WmsgxqActivity.class);
        }
      startActivity(intent);
    }

}
