package com.a21zhewang.constructionapp.ui.video;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class Act_Surveillance extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.report_xlistview)
    XListView mListView;
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private int page = 0;
    private String keyword="";
    private JSONArray reportArray = new JSONArray();
    private MyBaseAdapter adapter;
    private int totalPage = 1;
    private Dialog loadingDialog;
    public  final static int RESQUST_CODE_SELECT_TYPE = 500;  // 安全，质量，文明
    private String BaseUrl = "";
    private String VideoListUrl = "";
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_search_project;
    }

    @Override
    public void initViews() {
        title.setText("视频监控");
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))){
            BaseUrl = "https://whdc-safe.telsafe.com.cn:9003/";
        }else if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            BaseUrl = "https://data-cqjg.telsafe.com.cn:9001/";
        }else {
            BaseUrl = "https://data-cqjg.telsafe.com.cn:9001/";
        }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_Surveillance.this, "加载中...");
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                final JSONObject object = reportArray.optJSONObject(position);
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(
                            R.layout.act_searchproject_item_layout, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView name = MViewHolder.get(arg1, R.id.risktype_name);
                if(object.length()>0){
                    name.setText(object.optString("ProjectName"));
                }
                arg1.setTag(R.id.tag_first, object);
                return arg1;
            }
            @Override
            public int getCount() {
                return reportArray.length();
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final JSONObject object = reportArray.optJSONObject(position-1);
                startActivity(new Intent(Act_Surveillance.this,Act_SurveillanceDetail.class)
                        .putExtra("ProjectID",object.optString("ProjectID"))
                        .putExtra("BaseUrl",BaseUrl));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                if (reportArray==null)return;
                reportArray = new JSONArray();
                adapter.notifyDataSetChanged();
                page=0;
                keyword=edittext;
//                getlistdata(0,10);
                loadData();
            }
        });
    }

    @Override
    public void initData() {
    }
    public void loadData(){
        loadingDialog.show();
        /**
         * UserID = 18995551347
         */
        if("null".equals(keyword) || "".equals(keyword)){
            VideoListUrl = BaseUrl+"v1/bigscreen/video/Get_GvsCameraProject?userId="+PublicUtils.UserID+"&pageSize=10&pageIndex="+page;
//            VideoListUrl = "https://data-sj.telsafe.com.cn:9009/v1/bigscreen/video/Get_GvsCameraProject?userId=13554277692&pageSize=10&pageIndex="+page;
        }else {
            VideoListUrl = BaseUrl+"v1/bigscreen/video/Get_GvsCameraProject?userId="+PublicUtils.UserID+"&pageSize=10&pageIndex="+page+"&strFilter="+keyword;
//            VideoListUrl = "https://data-sj.telsafe.com.cn:9009/v1/bigscreen/video/Get_GvsCameraProject?userId=13554277692&pageSize=10&pageIndex="+page+"&strFilter="+keyword;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                VideoListUrl,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject object) {
                loadingDialog.dismiss();
                try {
                    Object tempobject = object.get("Data");
                    if(tempobject != null || !"null".equals(tempobject)){
                        JSONObject object1 = new JSONObject(tempobject.toString());
                        if (page == 0) {
                            reportArray = new JSONArray();
                            mListView.stopLoadMore();
                            mListView.stopRefresh();
                        }
                        totalPage = object1.optInt("totalPage");
                        JSONArray jsonArray = object1.optJSONArray("data");
                        if(jsonArray.length() == 0){
//                            PublicUtils.toast("暂无结果");
                            mListView.stopLoadMore();
                            mListView.stopRefresh();
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            reportArray.put(jsonArray.optJSONObject(i));
                        }
                        if (totalPage > 0) {
                            mListView.setPullLoadEnable(true);
                        } else {
                            mListView.setPullLoadEnable(false);
                        }
                        if (page > totalPage) {
                            page = totalPage;
                            mListView.stopLoadMore();
                            mListView.stopRefresh();
                            mListView.setRefreshTime(" " + new SimpleDateFormat("yyyy-MM-dd HH:mm")
                                    .format(new Date()));
                            Toast.makeText(getApplicationContext(), "已无更多数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (reportArray.length() == 0) {
                            placeHolder.setVisibility(View.VISIBLE);
                        } else {
                            placeHolder.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                 } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("请求失败");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyAppCon.getHttpQueue().add(request);

    }

    @Override
    public void processClick(View v) {

    }
}
