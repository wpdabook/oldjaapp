package com.a21zhewang.constructionapp.ui.wdjc;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;

import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ZxjcBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTimeSpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;

import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;


public class WdjcActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.activity_zxjc_MyTitleBar)
    MyTitleBar activityZxjcMyTitleBar;
    @BindView(R.id.activity_zxjc_RelativeLayout2)
    RelativeLayout activityZxjcRelativeLayout;
    @BindView(R.id.activity_zxjc_MySpinner_jclb)
    MySpinner activityZxjcMySpinnerJclb;
    @BindView(R.id.activity_zxjc_MyTimeSpinner_jcsj)
    MyTimeSpinner activityZxjcMyTimeSpinnerJcsj;
    @BindView(R.id.activity_zxjc_activity_zxjc_SWPullRecyclerLayout)
    RecyclerView activityZxjcActivityZxjcSWPullRecyclerLayout;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;
    @BindView(R.id.title_righttextview)
    TextView title_righttext;

    private BaseQuickAdapter<ZxjcBean.RecordListBean, BaseViewHolder> adapter;
    int pageindex = 0;
    String time = "";
    String etpID = "";
    private int onClickIndex=-1;
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
        return R.layout.activity_wdjc;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        /**
         *
         ????????????????????????????????????????????????
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals("KeyExaminationAddBtn")) {
                    activityZxjcRelativeLayout.setVisibility(View.VISIBLE);
                }
           }
        }*/
        initList();
    }

    /**
     * ???????????????
     */
    private void initList() {
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.tilebar_color));
        swipeLayout.setOnRefreshListener(this);
        adapter = new BaseQuickAdapter<ZxjcBean.RecordListBean, BaseViewHolder>(R.layout.wdjc_list_item, null) {
            @Override
            protected void convert(final BaseViewHolder helper, final ZxjcBean.RecordListBean item) {
                helper
                        .setText(R.id.zxjc_list_item_tv_pjtitle, item.getTitle())      //????????????
                        .setText(R.id.zxjc_list_item_tv_pjname, item.getProShortName()) //????????????
                        .setText(R.id.zxjc_list_item_tv_checktime, item.getCheckRegionName()) //????????????
                        .setText(R.id.zxjc_list_item_tv_checkname, item.getPlanTime()) //????????????
                        .setText(R.id.zxjc_list_item_tv_checkdept, item.getCheckTime()); //????????????
//                        .setText(R.id.tv_status_type,item.getCheckRate() + item.getCheckType()+"%");
                        //????????????100%  -?????????????????????/??????????????? ???x 100% = ?????????
//                 .setText(R.id.tv_status_type,item.getCheckType() +" "+item.getCheckRate()+"%");
                if(item.getCheckType()== 1){
                  helper.setText(R.id.tv_status_type,"?????????" +" "+item.getCheckRate()+"%");
                }else if(item.getCheckType()== 2){
                    helper.setText(R.id.tv_status_type,"?????????" +" "+item.getCheckRate()+"%");
                }else if(item.getCheckType()== 3){
                    helper.setText(R.id.tv_status_type,"?????????" +" "+item.getCheckRate()+"%");
                }
                /***
                 * ????????????????????????
                 * ???????????????????????????????????????????????????????????????????????????
                 * ????????????????????????????????????????????????
                 */
                         if("1".equals(item.getStatus())){
                            helper.setText(R.id.zxjc_list_item_tv_zgl, "?????????");
                            helper.setTextColor(R.id.zxjc_list_item_tv_zgl, Color.rgb(245, 36, 55));
                             helper.setBackgroundRes(R.id.tv_status_type,R.drawable.checkitem_shape_left_red);
                            helper.setVisible(R.id.checktime_layout,false);
                         }else if("2".equals(item.getStatus())){
                            helper.setText(R.id.zxjc_list_item_tv_zgl, "?????????");
                            helper.setTextColor(R.id.zxjc_list_item_tv_zgl,Color.rgb(0, 132, 207));
                            helper.setBackgroundRes(R.id.tv_status_type,R.drawable.checkitem_shape_left_blue);
//                             helper.setBackgroundRes(R.id.zxjc_list_item_tv_zgl,R.drawable.checkitem_shape_blue);
                            helper.setVisible(R.id.checktime_layout,true);
                         }else if("3".equals(item.getStatus())){
                            helper.setText(R.id.zxjc_list_item_tv_zgl, "?????????");
                            helper.setTextColor(R.id.zxjc_list_item_tv_zgl,Color.rgb(24, 182, 129));
                            helper.setBackgroundRes(R.id.tv_status_type,R.drawable.checkitem_shape_left);
                            helper.setVisible(R.id.checktime_layout,true);
                       }
                        helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=helper.getLayoutPosition();
                        Intent intent=new Intent(WdjcActivity.this,WdjcxqActivity.class);
                        intent.putExtra("recordID",item.getRecordID());
                        startActivity(intent);
                    }
                });
            }
        };
//        ???????????????????????????
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                OnLoading();
            }
        }, activityZxjcActivityZxjcSWPullRecyclerLayout);
        activityZxjcActivityZxjcSWPullRecyclerLayout.setLayoutManager(new LinearLayoutManager(this));
        activityZxjcActivityZxjcSWPullRecyclerLayout.setAdapter(adapter);
        adapter.disableLoadMoreIfNotFullPage();
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
        adapter.setEnableLoadMore(false);
        pageindex = 0;
        adapter.setNewData(null);
        getdata();
    }

    private void getdata(){
        getdata(pageindex,10);
    }
    /**
     * ??????????????????
     */
    private void getdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"beginTime\":\"" + time + "\",\"projectID\":\"\",\"endTime\":\"\",\"pageSize\":\""+pageSize+"\",\"etpID\":\"" + etpID + "\",\"pageIndex\":\"" + pageindex + "\",\"keyword\":\""+keyword+"\"}");
        XUtil.postjsondata(getjsonobj, "GetListKeyExamination", new MyCallBack() {
            @Override
            public void onResule(String res) {

                ZxjcBean zxjcBean = JsonUtils.getbean(res, ZxjcBean.class);
                List<ZxjcBean.RecordListBean> recordList = zxjcBean.getRecordList();
                if (recordList != null && recordList.size() > 0) {
                    //PublicUtils.toast(recordList.size()+"");
                    adapter.addData(recordList);
                    adapter.loadMoreComplete();

                } else {
                    adapter.loadMoreEnd();
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
                startActivity(new Intent(WdjcActivity.this, WdjcxqActivity.class));
            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {

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
