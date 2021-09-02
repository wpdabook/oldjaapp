package com.a21zhewang.constructionapp.ui.report;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.check.ProceduralFragment;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReport;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReportAdd;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReportDetail;
import com.a21zhewang.constructionapp.ui.tab.Act_RankDetail2;
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
import java.util.List;

import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class Act_ProgressReport extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.activity_index_img_sys)
    ImageView img_add;
    @BindView(R.id.report_xlistview)
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
        return R.layout.act_progress_report_layout;
    }

    @Override
    public void initViews() {
        try{
            List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
            if (buttons != null) {
                for (ButtonBean btn : buttons) {
                    if (btn.getBtnID().equals("ProgressReportAddBtn")) {
                        img_add.setVisibility(View.VISIBLE);
                        img_add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Act_ProgressReport.this, Act_ProgressReportAdd.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        title.setText("进度上报");
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Act_ProgressReport.this, Act_ProgressReportAdd.class);
                startActivity(intent);
            }
        });
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
                            R.layout.act_report_progress_layout_item, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView name = MViewHolder.get(arg1, R.id.project_name);
                TextView manager = MViewHolder.get(arg1, R.id.project_manager);
                TextView time = MViewHolder.get(arg1, R.id.time);
                if(object.length()>0){
                    name.setText(object.optString("Title"));
                    manager.setText("来自："+object.optString("CreateUserName"));
                    time.setText(object.optString("CreateDate"));
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
                if(!TextUtils.isEmpty(object.optString("PID"))){
                    startActivity(new Intent(Act_ProgressReport.this,Act_ProgressReportDetail.class)
                            .putExtra("PID",object.optString("PID")));
                }
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
        JSONObject object = JsonUtils.getjsonobj("{\"timeOrderBy\":\"" + "desc" + "\"," + "\"pageIndex\":\"" + String.valueOf(page)  + "\"," +
                "\"pageSize\":\"" + 10 + "\",\"proShortName\":\"" + keyword + "\"}");
        XUtil.postjsondata(object, "GetProgressReportList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if(object.length()>0) {
                        if (page == 0) {
                            reportArray = new JSONArray();
                        }
                        totalPage = object.optInt("pageCount");
                        JSONArray jsonArray = object.optJSONArray("recordList");
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
                            Toast.makeText(getApplicationContext(), "已无更多数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (reportArray.length() == 0) {
                            placeHolder.setVisibility(View.VISIBLE);
                        } else {
                            placeHolder.setVisibility(View.GONE);
                        }
                    }
                    adapter.notifyDataSetChanged();
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
