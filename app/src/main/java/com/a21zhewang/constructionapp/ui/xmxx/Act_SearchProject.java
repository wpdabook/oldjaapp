package com.a21zhewang.constructionapp.ui.xmxx;

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

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class Act_SearchProject extends BaseActivity {
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
    private int SJ_TAG;  //0:区站  1：//市局
    public  final static int RESQUST_CODE_SELECT_TYPE = 500;  // 安全，质量，文明
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_search_project;
    }

    @Override
    public void initViews() {
        title.setText("选择项目");
        SJ_TAG = getIntent().getIntExtra("SJ_TAG",0);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_SearchProject.this, "加载中...");
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
                    name.setText(object.optString("projectName"));
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
                Intent data = new Intent();
                data.putExtra("projectId",object.optString("projectID"));
                data.putExtra("projectName",object.optString("projectName"));
                data.putExtra("area",object.optString("area"));
                setResult(RESULT_OK, data);
                finish();
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
        JSONObject object = JsonUtils.getjsonobj("{\"etpID\":\"" + "" + "\"," + "\"pageIndex\":\"" + String.valueOf(page)  + "\"," +
                "\"pageSize\":\"" + 15 + "\",\"projectName\":\"" + keyword + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject object = new JSONObject(res);
                    if(object.length()>0) {
                        if (page == 0) {
                            reportArray = new JSONArray();
                        }
                        totalPage = object.optInt("PageCount");
                        JSONArray jsonArray = object.optJSONArray("recordList");
                        if(jsonArray.length() == 0){
                            PublicUtils.toast("暂无结果");
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
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                super.onFinished();
                mListView.stopRefresh();
                mListView.stopLoadMore();
                mListView.setRefreshTime(" " + new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .format(new Date()));
            }
        };
        if(SJ_TAG == 0){
            XUtil.postjsondata(object,"GetProjectList", callback);
        }else {
            XUtil.postjsondatasj(object,"GetProjectList", callback);
        }
    }

    @Override
    public void processClick(View v) {

    }
}
