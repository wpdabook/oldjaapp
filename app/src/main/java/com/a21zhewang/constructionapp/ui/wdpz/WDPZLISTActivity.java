package com.a21zhewang.constructionapp.ui.wdpz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.JDBean;
import com.a21zhewang.constructionapp.bean.ListBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProjectSynopsis;
import com.a21zhewang.constructionapp.bean.WDlistBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.jdsb.ADDJDActivity;
import com.a21zhewang.constructionapp.ui.jdsb.JDSBXQActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WDPZLISTActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.activity_zxjc_MyTitleBar)
    MyTitleBar activityZxjcMyTitleBar;
    @BindView(R.id.activity_zxjc_RelativeLayout)
    RelativeLayout activityZxjcRelativeLayout;
    @BindView(R.id.MySpinner_xm)
    MySpinner MySpinner_xm;
    @BindView(R.id.MySpinner_type)
    MySpinner MySpinner_type;
    @BindView(R.id.activity_zxjc_activity_zxjc_SWPullRecyclerLayout)
    RecyclerView activityZxjcActivityZxjcSWPullRecyclerLayout;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;
    private BaseQuickAdapter<WDlistBean, BaseViewHolder> adapter;
    int pageindex = 0;
    String status = "";
    String pjid = "";
    private NamesSpinnerAdpater<ProjectSynopsis> pjadapter;
    int onClickIndex=-1;
    private String keyword="";

    /**
     * setContentView????????????
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return ??????????????????
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_wdpzlist;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {

        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals("ExaminationAddBtn")) {
                    activityZxjcRelativeLayout.setVisibility(View.VISIBLE);
                }
            } }
        //??????lietview
        initList();
        //getdata();
        MySpinner_xm.setPopHeight(400);
    }

    /**
     * ???????????????
     */
    private void initList() {


        swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.tilebar_color));
        swipeLayout.setOnRefreshListener(this);
        adapter = new BaseQuickAdapter<WDlistBean, BaseViewHolder>(R.layout.jdsb_item, null) {
            @Override
            protected void convert(BaseViewHolder helper, final WDlistBean item) {
                helper.setText(R.id.tv_title, item.getRecordTitle())
                        .setText(R.id.tv_name, "????????????"+item.getCreateUserName())
                        .setText(R.id.tv_time, "?????????"+item.getCreateTime());


            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onClickIndex=position;
                Intent intent=new Intent(WDPZLISTActivity.this,PZWDXQActivity.class);
                intent.putExtra("id", WDPZLISTActivity.this.adapter.getItem(position).getProjectID());
                intent.putExtra("status", WDPZLISTActivity.this.adapter.getItem(position).getStatus());
                intent.putExtra("projectName", WDPZLISTActivity.this.adapter.getItem(position).getProjectName());
                intent.putExtra("checkRegionId",WDPZLISTActivity.this.adapter.getItem(position).getCheckRegionId());
                intent.putExtra("checkRegionName",WDPZLISTActivity.this.adapter.getItem(position).getCheckRegionName());
                intent.putExtra("dicID",WDPZLISTActivity.this.adapter.getItem(position).getDicID());
                intent.putExtra("dicName",WDPZLISTActivity.this.adapter.getItem(position).getDicName());
                startActivity(intent);
            }
        });
//        ???????????????????????????
//        adapter.isFirstOnly(false);
        //  adapter.openLoadAnimation();
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                OnLoading();
            }
        }, activityZxjcActivityZxjcSWPullRecyclerLayout);
        activityZxjcActivityZxjcSWPullRecyclerLayout.setLayoutManager(new LinearLayoutManager(this));
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#EDEDED"))
                .sizeResId(R.dimen.y2)
                .marginResId(R.dimen.x20, R.dimen.x20).build();
        activityZxjcActivityZxjcSWPullRecyclerLayout.addItemDecoration(decoration);
        activityZxjcActivityZxjcSWPullRecyclerLayout.setAdapter(adapter);
        //adapter.disableLoadMoreIfNotFullPage();
    }
private void getdata(){
    getdata(pageindex,10);
}
    /**
     * ??????????????????
     */
    private void getdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"projectID\":\""+pjid+"\",\"status\":\""+status+"\",\"pageSize\":\""+pageSize+"\",\"pageIndex\":\"" + pageindex + "\",\"keyword\":\""+keyword+"\"}");
        XUtil.postjsondata(getjsonobj, "GetListProjectRiskConfig", new MyCallBack() {
            @Override
            public void onResule(String res) {

                JSONObject zxjcBean = JsonUtils.getjsonobj(res);
                try {
                    JSONArray recordList = zxjcBean.getJSONArray("recordList");

                List<WDlistBean> List = JsonUtils.jsonToArrayList(recordList.toString(), WDlistBean.class);
                if (List != null ) {
                    adapter.addData(List);
                    if (List.size() <10){
                        adapter.loadMoreEnd();
                    }else{
                        adapter.loadMoreComplete();
                    }
                    //PublicUtils.toast(recordList.size()+"");


                } else {
                    adapter.loadMoreEnd();
                }
                } catch (JSONException e) {
                    // e.printStackTrace();
                }
            }

            @Override
            public void onFail(ObjBean getbean) {
                adapter.loadMoreFail();
            }

            @Override
            public void onWrong() {
                adapter.loadMoreFail();
            }

            @Override
            public void onFinished() {
                swipeLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
                swipeLayout.setEnabled(true);
if (onClickIndex!=-1){
    activityZxjcActivityZxjcSWPullRecyclerLayout.scrollToPosition(onClickIndex);
    onClickIndex=-1;
}
            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {
        activityZxjcRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=0;
                startActivity(new Intent(WDPZLISTActivity.this, PZWDActivity.class));
            }
        });

        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                adapter.setEnableLoadMore(false);
                pageindex = 0;
                adapter.setNewData(null);
                keyword=edittext;
                getdata();

            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {
        pjadapter = new NamesSpinnerAdpater<ProjectSynopsis>(this,
                new ArrayList<ProjectSynopsis>()) {
            @Override
            public void setinit(ProjectSynopsis name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getShortName());
            }
        };
        MySpinner_xm.setmyspinnerlistadpater(pjadapter);
        MySpinner_xm.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectSynopsis item = pjadapter.getItem(position);
                MySpinner_xm.setTextViewtext(item.getShortName());
                pjid=item.getProjectID();
                 adapter.setEnableLoadMore(false);
                pageindex = 0;
                adapter.setNewData(null);
                getdata();
               // getProject(item.getProjectID());
                MySpinner_xm.popdismiss();
            }
        });
        final NamesSpinnerAdpater<String> typeAdpater = new NamesSpinnerAdpater<String>(this,
                new ArrayList<String>()) {
            @Override
            public void setinit(String name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name);
            }
        };
        typeAdpater.adddata("??????");
        typeAdpater.adddata("??????");
        typeAdpater.adddata("??????");
        MySpinner_type.setmyspinnerlistadpater(typeAdpater);
        MySpinner_type.setTextViewtext("??????");
        MySpinner_type.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0)status="2";
                else if (position==1)status="1";
                else status="";
                MySpinner_type.setTextViewtext(typeAdpater.getItem(position));
                adapter.setEnableLoadMore(false);
                pageindex = 0;
                adapter.setNewData(null);
                getdata();
                MySpinner_type.popdismiss();
            }
        });
        XUtil.postjsondata(
                JsonUtils.getjsonobj("{\"pageIndex\":\"0\",\"pageSize\":\"100\",\"etpID\":\"\"}"),
                "GetProjectList", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        ListBean projectSynopsisListBean= JsonUtils.getbean(res, ListBean.class);
                        List<ProjectSynopsis> recordList = projectSynopsisListBean.getRecordList();
                        ProjectSynopsis e = new ProjectSynopsis();
                        e.setProjectID("");
                        e.setShortName("??????");
                        recordList.add(e);
                        pjadapter.replcedata(recordList);
                        if (recordList!=null&&recordList.size()>0){
                          //  pjadapter.replcedata(recordList);
                            ProjectSynopsis item = pjadapter.getItem(recordList.size()-1);
                            pjid=item.getProjectID();
                            MySpinner_xm.setTextViewtext(item.getShortName());
                          //  getProject(item.getProjectID());
                           // getdata();
                            MySpinner_xm.popdismiss();
                        }
                    }

                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    @Override
                    public void onWrong() {

                    }
                });
        swipeLayout.setRefreshing(true);
        onRefresh();
    }

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }


    /**
     * ??????
     */

    public void OnRefreshing() {
        adapter.setEnableLoadMore(false);
        pageindex = 0;
        adapter.setNewData(null);
        keyword="";
        getdata();
    }

    /**
     * ??????
     */

    public void OnLoading() {
        swipeLayout.setEnabled(false);
        pageindex++;
        getdata();
    }

    @Override
    public void resume() {
        if (onClickIndex!=-1){
            adapter.setNewData(null);
            getdata(0,(pageindex+1)*10);
        }
        // getdata();
    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */
    @Override
    public void onRefresh() {
        //PublicUtils.toast("?????????");
        OnRefreshing();
    }
}

