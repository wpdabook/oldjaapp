package com.a21zhewang.constructionapp.ui.tab;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.check.ProceduralFragment;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class Act_ReportProject extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.report_project_xlistview)
    XListView mListView;
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String str_title;
    private int type;
    private int page = 0;
    private String keyword="";
    private JSONArray reportArray = new JSONArray();
    private MyBaseAdapter adapter;
    private int totalPage = 1;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_report_project;
    }

    @Override
    public void initViews() {
        type = getIntent().getIntExtra("type",1);
        str_title = getIntent().getStringExtra("title");
        title.setText(str_title);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData(type);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData(type);
            }
        });
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                final JSONObject object = reportArray.optJSONObject(position);
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(
                            R.layout.act_report_project_item, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView sort = MViewHolder.get(arg1, R.id.tv_sort_one);
                TextView name = MViewHolder.get(arg1, R.id.project_name);
                TextView manager = MViewHolder.get(arg1, R.id.project_manager);
                TextView time = MViewHolder.get(arg1, R.id.project_reportTime);
                if(object.length()>0){
                    sort.setText(object.optInt("index")+"");
                    name.setText(object.optString("projectName"));
                    if(TextUtils.isEmpty(object.optString("manager"))){
                        manager.setVisibility(View.GONE);
                    }else {
                        manager.setVisibility(View.VISIBLE);
                        manager.setText("项目负责人："+object.optString("manager"));
                    }
                    if(type == 1){ //1、未上报 2、已上报
                        if(TextUtils.isEmpty(object.optString("phone"))){
                            time.setVisibility(View.GONE);
                        }else {
                            time.setVisibility(View.VISIBLE);
                            time.setText("联系方式："+object.optString("phone"));
                        }
                    }else {
                        String stime = object.optString("reportTime").substring(5,16);
                        time.setText("上报时间："+stime);

                    }
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
                loadData(type);
            }
        });
    }

    @Override
    public void initData() {
        loadData(type);
    }
    public void loadData(final int type){
        JSONObject object = JsonUtils.getjsonobj("{\"type\":\"" + type + "\",\"dateTime\":\"" + DateUtils.getTodayDate() + "\"," +
                "\"pageIndex\":\"" + String.valueOf(page)  + "\",\"pageSize\":\"" + 10 + "\",\"key\":\"" + keyword + "\"}");
        XUtil.postjsondata(object, "GetResumWorkReportProject", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if(object.length()>0) {
                        if (page == 0) {
                            reportArray = new JSONArray();
                        }
                        if (type == 1) {
                            totalPage = object.optInt("noReportPageCount");
                            JSONArray jsonArray = object.optJSONArray("noReportList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                reportArray.put(jsonArray.optJSONObject(i));
                            }
                        } else {
                            totalPage = object.optInt("reportPageCount");
                            JSONArray jsonArray = object.optJSONArray("reportList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                reportArray.put(jsonArray.optJSONObject(i));
                            }
                        }
                        if (totalPage > 0) {
                            mListView.setPullLoadEnable(true);
                        } else {
                            mListView.setPullLoadEnable(false);
                        }
                        if (page > totalPage) {
                            page = totalPage;
                            mListView.stopLoadMore();
                            Toast.makeText(getApplicationContext(), "已无更多数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (reportArray.length() == 0) {
                            placeHolder.setVisibility(View.VISIBLE);
                        } else {
                            placeHolder.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onFinished() {
                super.onFinished();
                mListView.stopRefresh();
                mListView.stopLoadMore();
                mListView.setRefreshTime(" " + new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .format(new Date()));
            }

            @Override
            public void onWrong() {

            }
        });
    }

    @Override
    public void processClick(View v) {

    }
}
