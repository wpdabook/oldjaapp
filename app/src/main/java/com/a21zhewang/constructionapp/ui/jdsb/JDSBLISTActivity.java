package com.a21zhewang.constructionapp.ui.jdsb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.a21zhewang.constructionapp.bean.ZxjcBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTimeSpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.zxjc.AddzxjcActivity;
import com.a21zhewang.constructionapp.ui.zxjc.ZxjcActivity;
import com.a21zhewang.constructionapp.ui.zxjc.ZxjcxqActivity;
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

public class JDSBLISTActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.activity_zxjc_MyTitleBar)
    MyTitleBar activityZxjcMyTitleBar;
    @BindView(R.id.activity_zxjc_RelativeLayout)
    RelativeLayout activityZxjcRelativeLayout;
    @BindView(R.id.MySpinner_xm)
    MySpinner MySpinner_xm;
    @BindView(R.id.activity_zxjc_activity_zxjc_SWPullRecyclerLayout)
    RecyclerView activityZxjcActivityZxjcSWPullRecyclerLayout;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;
    private BaseQuickAdapter<JDBean, BaseViewHolder> adapter;
    int pageindex = 0;
    String time = "";
    String pjid = "";
    private NamesSpinnerAdpater<ProjectSynopsis> pjadapter;
    private int onClickIndex=-1;
    private String keyword="";

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
        return R.layout.activity_jdsblist;
    }

    /**
     * 初始化界面
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
        //设置lietview
        initList();
        //getdata();
        MySpinner_xm.setPopHeight(400);
    }

    /**
     * 初始化列表
     */
    private void initList() {


        swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.tilebar_color));
        swipeLayout.setOnRefreshListener(this);
        adapter = new BaseQuickAdapter<JDBean, BaseViewHolder>(R.layout.jdsb_item, null) {
            @Override
            protected void convert(BaseViewHolder helper, final JDBean item) {
                helper.setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_name, "上报人："+item.getCreateUserName())
                        .setText(R.id.tv_time, "上报日期："+item.getCreateTime());


            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onClickIndex=position;
                Intent intent=new Intent(JDSBLISTActivity.this,JDSBXQActivity.class);
                  intent.putExtra("data",JDSBLISTActivity.this.adapter.getItem(position));
                  startActivity(intent);
            }
        });
//        是否只运行一次动画
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
     * 获取列表数据
     */
    private void getdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"beginTime\":\"" + time + "\",\"projectID\":\""+pjid+"\",\"endTime\":\"\",\"pageSize\":\""+pageSize+"\",\"pageIndex\":\"" + pageindex + "\",\"keyword\":\""+keyword+"\"}");
        XUtil.postjsondata(getjsonobj, "GetListScheduleReport", new MyCallBack() {
            @Override
            public void onResule(String res) {

                JSONObject zxjcBean = JsonUtils.getjsonobj(res);
                try {
                    JSONArray recordList = zxjcBean.getJSONArray("recordList");

                List<JDBean> List = JsonUtils.jsonToArrayList(recordList.toString(), JDBean.class);
                if (List != null ) {
                    if (List.size() <10){
                        adapter.addData(List);
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
     * 初始化事件
     */
    @Override
    public void initListener() {
        activityZxjcRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=0;
                startActivity(new Intent(JDSBLISTActivity.this, ADDJDActivity.class));
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
     * 初始化数据
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
        XUtil.postjsondata(
                JsonUtils.getjsonobj("{\"pageIndex\":\"0\",\"pageSize\":\"100\",\"etpID\":\"\"}"),
                "GetProjectList", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        ListBean projectSynopsisListBean= JsonUtils.getbean(res, ListBean.class);
                        List<ProjectSynopsis> recordList = projectSynopsisListBean.getRecordList();
                        ProjectSynopsis e = new ProjectSynopsis();
                        e.setProjectID("");
                        e.setShortName("全部");
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
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    /**
     * 刷新
     */

    public void OnRefreshing() {
        adapter.setEnableLoadMore(false);
        pageindex = 0;
        keyword="";
        adapter.setNewData(null);
        getdata();
    }

    /**
     * 加载
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
        //PublicUtils.toast("刷新了");
        OnRefreshing();
    }
}

