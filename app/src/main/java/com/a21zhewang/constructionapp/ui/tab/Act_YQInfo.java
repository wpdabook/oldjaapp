package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.ui.check.Act_ProceduralDetail;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import butterknife.BindView;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;

/**
 * Created by WP-PC on 2020/2/26.
 */

public class Act_YQInfo extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;//子下拉
    @BindView(R.id.yiqing_xlistview)
    XListView mListView;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    @BindView(R.id.yiqing_search)
    LinearLayout yiqing_search;
    @BindView(R.id.yq_rg)
    RadioGroup yq_rg;
    @BindView(R.id.yq_rb1)
    RadioButton yq_rb1;
    @BindView(R.id.yq_rb2)
    RadioButton yq_rb2;
    private int type;//0.未申请项目 2.已申请项目 1.检查通过项目
    private int page = 0;
    private int totalPage = 0;
    private int  pageSize = 10;
    private String keyword = "";
    private JSONArray dataArray = new JSONArray();
    private MyBaseAdapter adapter;
    private String sortType = "asc"; //正序 desc-倒序
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_yqact_layout;
    }

    @Override
    public void initViews() {
        type = getIntent().getIntExtra("type",0);
        if(type == 0){
            title.setText("疫情复工未申请项目");
            yiqing_search.setVisibility(View.GONE);
        }else if(type == 1){
            title.setText("疫情复工检查通过项目");
            yiqing_search.setVisibility(View.GONE);
        }else {
            title.setText("疫情复工已申请项目");
            yiqing_search.setVisibility(View.VISIBLE);
        }
        yq_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.yq_rb1) {
                    sortType = "asc";
                    page = 0;
                    loadData(true,sortType,keyword);
                }else if(checkedId == R.id.yq_rb2){
                    sortType = "desc";
                    page = 0;
                    loadData(true,sortType,keyword);
                }
            }
        });
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData(false,sortType,keyword);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData(false,sortType,keyword);
            }
        });
        adapter = new MyBaseAdapter() {
            @Override
            public int getCount() {
                return dataArray.length();
            }

            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                final JSONObject object = dataArray.optJSONObject(position);
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(R.layout.yiqing_list_item_layout, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView name = MViewHolder.get(arg1, R.id.project_name);
                TextView people = MViewHolder.get(arg1, R.id.people);
                TextView phone = MViewHolder.get(arg1, R.id.phone);
                TextView tv_sort = MViewHolder.get(arg1, R.id.tv_sort_one);
                Button btn_passed = MViewHolder.get(arg1,R.id.btn_passed);
                Button btn_no_passed = MViewHolder.get(arg1,R.id.btn_no_passed);
                if(object.length()>0){
                    int sortnum = position + 1;
                    tv_sort.setText(sortnum+"");
                    if(type == 0 || type == 1 ){
                        name.setText(object.optString("projectName"));
                        btn_passed.setVisibility(View.GONE);
                        btn_no_passed.setVisibility(View.GONE);
                        if(!TextUtils.isEmpty(object.optString("manager"))){
                            people.setVisibility(View.VISIBLE);
                            people.setText(object.optString("manager"));
                        }else {
                            people.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(object.optString("managerPhone"))){
                            phone.setVisibility(View.VISIBLE);
                            phone.setText(object.optString("managerPhone"));
                        }else {
                            phone.setVisibility(View.GONE);
                        }
                    }else {
                        name.setText(object.optString("dataType"));
                        people.setVisibility(View.GONE);
                        phone.setText(object.optString("createTime").substring(5,11));
                        btn_passed.setVisibility(View.VISIBLE);
                        btn_no_passed.setVisibility(View.VISIBLE);
                    }
                    btn_passed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnPassed(object.optString("projectID"),1);
                        }
                    });
                    btn_no_passed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnPassed(object.optString("projectID"),2);
                        }
                    });
                }
                arg1.setTag(R.id.tag_first, object);
                arg1.setOnClickListener(noDoubleClickListener);
                return arg1;
            }

        };
        mListView.setAdapter(adapter);
    }

    /**
     * 审批按钮
     */
    public void  btnPassed(String projectId,int status){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"status\":\"" + status + "\"}"),
                "updepidemicProject", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        loadData(true,sortType,keyword);
                    }

                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    @Override
                    public void onWrong() {

                    }
                });
    }
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        void onNoDoubleClick(View v) {
            JSONObject object = (JSONObject) v.getTag(R.id.tag_first);
            if(type == 2){
                Intent i = new Intent();
                i.putExtra("status","3");
                i.putExtra("projectID",object.optString("projectID"));
                i.putExtra("proShortName",object.optString("proShortName"));
                i.putExtra("recordId",object.optString("recordID"));
                i.putExtra("creatorUnit",object.optString("creatorUnit"));
                i.putExtra("creatorName",object.optString("creatorName"));
                i.putExtra("dataType",object.optString("dataType"));
                i.putExtra("riskCount",object.optInt("riskCount"));
                i.putExtra("inspectionProgress",object.optInt("inspectionProgress"));
                i.setClass(Act_YQInfo.this, Act_ProceduralDetail.class);
                startActivity(i);
            }else {
                startActivity(new Intent(Act_YQInfo.this,Act_RankDetail2.class)
                        .putExtra("tag",1)
                        .putExtra("projectId",object.optString("projectID")));
            }
        }
    };
    public abstract class NoDoubleClickListener implements View.OnClickListener {

        public static final int MIN_CLICK_DELAY_TIME = 500;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }
        abstract void onNoDoubleClick(View v);
    }
    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog, String sortType,String keyword) {
        if(type == 0 || type == 1){
            XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + type + "\",\"orderBy\":\"" + sortType +"\",\"pageSize\":\"" + pageSize + "\",\"keyword\":\"" + keyword +
                    "\",\"pageIndex\":\"" +  String.valueOf(page) + "\"}"), "getUnappliedAndAcceptedProject", new MyCallBack() {

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResule(String res) {
                    if(!"{}".equals(res)){
                        try {
                            JSONObject object = new JSONObject(res);
                            if (page == 0) {
                                dataArray = new JSONArray();
                            }
                            totalPage = object.optInt("total");
                            if (totalPage > 0) {
                                mListView.setPullLoadEnable(true);
                            } else {
                                mListView.setPullLoadEnable(false);
                            }
                            if (page > totalPage) {
                                page = totalPage;
                                mListView.stopLoadMore();
                                Toast.makeText(Act_YQInfo.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = object.optJSONArray("secrecunre_secList");
                            for (int i = 0; i < jsonArray.length(); i++) {//
                                dataArray.put(jsonArray.optJSONObject(i));
                            }
                            if (dataArray.length() == 0) {
                                placeHolder.setVisibility(View.VISIBLE);
                            } else {
                                placeHolder.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(adapter != null){
                            adapter.notifyDataSetChanged();
                        }
                    }
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
                public void onFail(ObjBean getbean) {

                }

                @Override
                public void onWrong() {

                }
            });
        }else {
            XUtil.postjsondata(JsonUtils.getjsonobj("{\"pageSize\":\"" +  pageSize + "\",\"orderBy\":\"" + sortType +"\",\"keyword\":\"" + keyword +
                    "\",\"pageIndex\":\"" +  String.valueOf(page) + "\"}"), "getAppliedProject", new MyCallBack() {

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResule(String res) {
                    if(!"{}".equals(res)){
                        try {
                            JSONObject object = new JSONObject(res);
                            if (page == 0) {
                                dataArray = new JSONArray();
                            }
                            totalPage = object.optInt("total");
                            if (totalPage > 0) {
                                mListView.setPullLoadEnable(true);
                            } else {
                                mListView.setPullLoadEnable(false);
                            }
                            if (page > totalPage) {
                                page = totalPage;
                                mListView.stopLoadMore();
                                Toast.makeText(Act_YQInfo.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = object.optJSONArray("recordList");
                            for (int i = 0; i < jsonArray.length(); i++) {//
                                dataArray.put(jsonArray.optJSONObject(i));
                            }
                            if (dataArray.length() == 0) {
                                placeHolder.setVisibility(View.VISIBLE);
                            } else {
                                placeHolder.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(adapter != null){
                            adapter.notifyDataSetChanged();
                        }
                    }
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
                public void onFail(ObjBean getbean) {

                }

                @Override
                public void onWrong() {

                }
            });
        }

    }
    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                if (dataArray==null)return;
                dataArray = new JSONArray();
                adapter.notifyDataSetChanged();
                page=0;
                keyword=edittext;
                loadData(true,sortType,keyword);

            }
        });
    }

    @Override
    public void initData() {
             loadData(true,sortType,keyword);
    }

    @Override
    public void processClick(View v) {

    }
}
