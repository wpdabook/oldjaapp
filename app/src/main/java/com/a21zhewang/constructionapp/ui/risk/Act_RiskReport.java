package com.a21zhewang.constructionapp.ui.risk;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.NoticeBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskHomeBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.receiver.PushMessageReceiver;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WP-PC on 2019/6/26.
 */

public class Act_RiskReport extends BaseActivity implements OnTouchUpListener {
    @BindView(R.id.activity_tzgg_SWPullRecyclerLayout)
    SWPullRecyclerLayout activityTzggSWPullRecyclerLayout;
    @BindView(R.id.activity_tzgg_MyTitleBar)
    MyTitleBar myTitleBar;
    @BindView(R.id.activity_tzgg_ImageView_addimg)
    ImageView activityTzggImageViewAddimg;
    @BindView(R.id.activity_tzgg)
    LinearLayout activity_tzgg;
    @BindView(R.id.activity_tzgg_SearchView)
    SearchView activityTzggSearchView;
    private List<RiskHomeBean> riskHomeBeenlist;
    private CommonAdapter<RiskHomeBean> adapter;
    private int pageindex = 0;
    private String keyWord="";
    private int onClickIndex=-1;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_riskreport;
    }

    @Override
    public void initViews() {
        try{
            List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
            if (buttons != null) {
                for (ButtonBean btn : buttons) {
                    if (btn.getBtnID().equals("RiskReportAddBtn")) {
                        activityTzggImageViewAddimg.setVisibility(View.VISIBLE);
                        activityTzggImageViewAddimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickIndex=0;
                                Intent intent = new Intent(Act_RiskReport.this, Act_RiskReportAdd.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        myTitleBar.setTitlenametv("风险类型上报");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        riskHomeBeenlist = new ArrayList<>();
        int layoutid = R.layout.sgtx_list_item;
        activityTzggSearchView.setVisibility(View.VISIBLE);
        layoutid = R.layout.tzgg_list_item2;
        activity_tzgg.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);//4个参数按顺序分别是左上右下
        activityTzggSWPullRecyclerLayout.setLayoutParams(layoutParams);

        adapter = new CommonAdapter<RiskHomeBean>(this, layoutid, riskHomeBeenlist) {
            @Override
            protected void convert(ViewHolder holder, final RiskHomeBean riskHomeBean, final int position) {
                holder.setText(R.id.tzgg_list_item_tv_title, riskHomeBean.getTitle());
                holder.setText(R.id.tzgg_list_item_tv_from,  "来自："+riskHomeBean.getCreateName());
                holder.setText(R.id.tzgg_list_item_tv_time, riskHomeBean.getCreateDate());
                int status = riskHomeBean.getStatus();
                if( status == 0){
                    holder.setText(R.id.tzgg_list_item_status,"状态：未通过");
                    holder.setTextColor(R.id.tzgg_list_item_status,getResources().getColor(R.color.color_red));
                }else if(status == 1 ){
                    holder.setText(R.id.tzgg_list_item_status,"状态：审核通过");
                    holder.setTextColor(R.id.tzgg_list_item_status,getResources().getColor(R.color.color_green_chart));
                }else if(status == 2 ){
                    holder.setText(R.id.tzgg_list_item_status,"状态：已重新上报");
                    holder.setTextColor(R.id.tzgg_list_item_status,getResources().getColor(R.color.color_666666));
                }else if(status == 3){
                    holder.setText(R.id.tzgg_list_item_status,"状态：待审核");
                    holder.setTextColor(R.id.tzgg_list_item_status,getResources().getColor(R.color.color_theme));
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(Act_RiskReport.this, Act_RiskReportDetail.class);
                        intent.putExtra("id", riskHomeBean.getId());
                        intent.putExtra("status",riskHomeBean.getStatus());
                        intent.putExtra("readStatus",riskHomeBean.getReadStatus());
                        startActivity(intent);
                    }
                });
            }
        };
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
        activityTzggSWPullRecyclerLayout.addHeaderView(header, 100);
        activityTzggSWPullRecyclerLayout.addFooterView(footer, 100);
        activityTzggSWPullRecyclerLayout.setMyRecyclerView(linearLayoutManager, adapter);
        activityTzggSWPullRecyclerLayout.addOnTouchUpListener(this);
    }

    @Override
    public void initListener() {
        activityTzggSearchView.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String str) {
                keyWord=str;
                riskHomeBeenlist.clear();
                pageindex = 0;
                getdata();
            }
        });
    }
    private void getdata() {
        getdata(pageindex,10);
    }
    private void getdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"pageIndex\":\"" + pageindex + "\",\"key\":\"" + keyWord+ "\",\"pageSize\":\""+pageSize+"\"}");
        MyCallBack callback = new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    Object recordList = jsonObject.get("recordList");
                    if (recordList != null) {
                        List<RiskHomeBean> riskHomeBeen = JsonUtils.jsonToList(recordList.toString(), RiskHomeBean[].class);
                        if (riskHomeBeen != null && riskHomeBeen.size() > 0) {
                            riskHomeBeenlist.addAll(riskHomeBeen);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                activityTzggSWPullRecyclerLayout.refreshok();
                if (onClickIndex!=-1){
                    activityTzggSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        };
        callback.setIstoast(false);
        XUtil.postjsondata(getjsonobj, "GetRiskReportList", callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (riskHomeBeenlist!=null)
            riskHomeBeenlist.clear();
        if (adapter!=null)
            adapter.notifyDataSetChanged();
        if (0 != PushMessageReceiver.count) {
            //角标清空
            PushMessageReceiver.count = 0;
            AppShortCutUtil.setCount(PushMessageReceiver.count, Act_RiskReport.this);
        }
        pageindex = 0;
        getdata();
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void OnRefreshing() {
        keyWord="";
        pageindex = 0;
        adapter.getDatas().clear();
        adapter.notifyDataSetChanged();
        getdata();
    }

    @Override
    public void OnLoading() {
        pageindex++;
        getdata();
    }

    @Override
    public void getdataok() {

    }
}
