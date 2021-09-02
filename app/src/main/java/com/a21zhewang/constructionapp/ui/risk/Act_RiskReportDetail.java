package com.a21zhewang.constructionapp.ui.risk;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ItemReadList;
import com.a21zhewang.constructionapp.bean.ItemRiskList;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskDetailInfo;
import com.a21zhewang.constructionapp.bean.SubDivisionBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

/**
 * 风险上报详情
 * Created by WP-PC on 2019/6/26.
 */

public class Act_RiskReportDetail extends BaseActivity {

    private String id;
    private RiskDetailInfo riskDetailInfo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_project_name)
    TextView tv_project_name;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.tv_total_floor_s)
    TextView tv_total_floor_s;
    @BindView(R.id.tv_writetime)
    TextView tv_writetime;
    @BindView(R.id.tv_now_floor)
    TextView tv_now_floor;
    @BindView(R.id.risk_type_text)
    TextView risk_type_text;
    @BindView(R.id.tv_now_region)
    TextView tv_now_region;
    @BindView(R.id.tv_now_stage)
    TextView tv_now_stage;
    @BindView(R.id.pb1)
    ZzHorizontalProgressBar progressBar;
    private List<ItemRiskList> itemRiskLists;
    private List<SubDivisionBean> subDivisionBeanList;
    @BindView(R.id.activity_Notice_ImageView_cyjls)
    ImageView activityNoticeImageViewCyjls;
    private List<ItemReadList> readLogs;
    @BindView(R.id.activity_notice_MyListView_cyjl)
    MyListView activityNoticeMyListViewcyjl;
    @BindView(R.id.lin_jd)
    LinearLayout lin_jd;
    @BindView(R.id.lin_floor)
    LinearLayout lin_floor;
    @BindView(R.id.lin_total_floor)
    LinearLayout lin_total_floor;
    @BindView(R.id.no_passed_reason)
    TextView no_passed_reason;
    @BindView(R.id.lin_no_passed_reason)
    LinearLayout lin_no_passed_reason;
    @BindView(R.id.record_send)
    LinearLayout record_send;
    @BindView(R.id.lin_todo_floor)
    LinearLayout lin_todo_floor;
    @BindView(R.id.risk_subdivision_text)
    TextView risk_subdivision_text;
    @BindView(R.id.sub_lin)
    LinearLayout sub_lin;

    private int readStatus;
    private int status;
    private com.zhy.adapter.abslistview.CommonAdapter<ItemReadList> readLogsBeanCommonAdapter;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_riskreportdetail;
    }

    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
        status = getIntent().getIntExtra("status",0);
        itemRiskLists = new ArrayList<>();
        readLogs = new ArrayList<>();
        readStatus = getIntent().getIntExtra("readStatus",0);
        if(status == 0){ //审核未通过
            record_send.setVisibility(View.VISIBLE);
        }else {
            record_send.setVisibility(View.GONE);
        }
        record_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RiskReportDetail.this,Act_RiskReportAdd.class));
                Act_RiskReportDetail.this.finish();
            }
        });
        //查阅纪录初始化
        View cyjlheadview = LayoutInflater.from(this).inflate(R.layout.gtxyxq_cyjl_list_headview, null, false);
        activityNoticeMyListViewcyjl.addHeaderView(cyjlheadview);
        readLogsBeanCommonAdapter = new com.zhy.adapter.abslistview.CommonAdapter<ItemReadList>(this
                , R.layout.gtxtxq_cyjl_list_item, readLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final ItemReadList item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getCompanyName());
                if(item.getStatus() == 1){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "发起");
                }else if(item.getStatus() == 2){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "未读");
                }else {
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "已读");
                }

                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, item.getReadTime().substring(5,16));
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //PublicUtils.call(item.getUserId(), Act_NoticeDetail.this);
                        PublicUtils.call("", Act_RiskReportDetail.this);
                    }
                });
            }
        };
        activityNoticeMyListViewcyjl.setAdapter(readLogsBeanCommonAdapter);
        activityNoticeImageViewCyjls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityNoticeMyListViewcyjl.getVisibility()==View.VISIBLE){
                    activityNoticeMyListViewcyjl.setVisibility(View.GONE);
                    activityNoticeImageViewCyjls.setImageResource(R.mipmap.arrowdown);
                }else{
                    activityNoticeMyListViewcyjl.setVisibility(View.VISIBLE);
                    activityNoticeImageViewCyjls.setImageResource(R.mipmap.arrowup);
                }
            }
        });
    }
    private void postReaddata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"readUserId\":\"" + PublicUtils.UserID + "\"}"), "EditRiskReport", new MyCallBack() {

            @Override
            public void onResule(String res) {
                getdata();
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }
    private void getdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\"}"), "GetRiskReportDetail", new MyCallBack() {

            @Override
            public void onResule(String res) {
                riskDetailInfo = JsonUtils.getbean(res, RiskDetailInfo.class);
                title.setText(riskDetailInfo.getTitle());
                tv_project_name.setText(riskDetailInfo.getProjectName());
                tv_writetime.setText(riskDetailInfo.getCreateDate());
                tv_people.setText(riskDetailInfo.getCreateName());
                tv_now_floor.setText(riskDetailInfo.getCurrFloor());
                tv_now_region.setText(riskDetailInfo.getCheckRegionName());
                tv_now_stage.setText(riskDetailInfo.getDicName());
                if(!TextUtils.isEmpty(riskDetailInfo.getCurrFloor())){
                    tv_total_floor_s.setText(riskDetailInfo.getCurrFloor());
                    progressBar.setProgress(Integer.valueOf(riskDetailInfo.getCurrFloor()));
                }
                if(!TextUtils.isEmpty(riskDetailInfo.getTotalFloor())){
                    tv_total_floor_s.setText(riskDetailInfo.getTotalFloor());
                    progressBar.setMax(Integer.valueOf(riskDetailInfo.getTotalFloor()));
                }
                itemRiskLists = riskDetailInfo.getItemRiskList();
                subDivisionBeanList = riskDetailInfo.getDivisionList();

                if(!TextUtils.isEmpty(riskDetailInfo.getRiskReviewReson())){
                    lin_no_passed_reason.setVisibility(View.VISIBLE);
                    no_passed_reason.setVisibility(View.VISIBLE);
                    no_passed_reason.setText(riskDetailInfo.getRiskReviewReson());
                }else {
                    lin_no_passed_reason.setVisibility(View.GONE);
                    no_passed_reason.setVisibility(View.GONE);
                }
                if("1".equals(riskDetailInfo.getProjectType())){
                    lin_jd.setVisibility(View.VISIBLE);
                    lin_floor.setVisibility(View.VISIBLE);
                    lin_total_floor.setVisibility(View.VISIBLE);
                    lin_todo_floor.setVisibility(View.VISIBLE);
                    sub_lin.setVisibility(View.GONE);
                    risk_subdivision_text.setVisibility(View.GONE);
                }else if("2".equals(riskDetailInfo.getProjectType())){
                    lin_jd.setVisibility(View.GONE);
                    lin_floor.setVisibility(View.GONE);
                    lin_total_floor.setVisibility(View.GONE);
                    lin_todo_floor.setVisibility(View.GONE);
                    sub_lin.setVisibility(View.VISIBLE);
                    risk_subdivision_text.setVisibility(View.VISIBLE);
                }
                StringBuffer sb = new StringBuffer();
                /**相同名称的风险项合并*/
                for (int i = 0; i < itemRiskLists.size(); i++) {
                    for (int j = 0; j < itemRiskLists.size(); j++) {
                        if (i != j && (itemRiskLists.get(i).getDicName().equals(itemRiskLists.get(j).getDicName()))) {
                            itemRiskLists.remove(itemRiskLists.get(j));
                        }
                    }
                }
                for (int i=0;i<itemRiskLists.size();i++){
                    sb.append(itemRiskLists.get(i).getDicName()+"，");
//                    risk_type_text.setText(sb.toString());
                }
                risk_type_text.setText(sb.deleteCharAt(sb.length() - 1).toString());
                if(subDivisionBeanList != null ){
                    if(subDivisionBeanList.size() != 0){
                        StringBuffer tsb = new StringBuffer();
                        /**相同名称的风险项合并*/
                        for (int i = 0; i < subDivisionBeanList.size(); i++) {
                            for (int j = 0; j < subDivisionBeanList.size(); j++) {
                                if (i != j && (subDivisionBeanList.get(i).getDivisionName().equals(subDivisionBeanList.get(j).getDivisionName()))) {
                                    subDivisionBeanList.remove(subDivisionBeanList.get(j));
                                }
                            }
                        }
                        for (int i=0;i<subDivisionBeanList.size();i++){
                            tsb.append(subDivisionBeanList.get(i).getDivisionName()+"，");
                        }
                        risk_subdivision_text.setText(tsb.deleteCharAt(tsb.length() - 1).toString());
                    }
                }
                readLogs.clear();
                List<ItemReadList> rLogs = riskDetailInfo.getItemReadList();
                if (rLogs != null && rLogs.size() > 0) {
                    readLogs.addAll(rLogs);
                }
                readLogsBeanCommonAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 未读时，先调用EditJGNotice，在调用GetJGNoticeDetail展示页面。
         * 已读就直接调用GetJGNoticeDetail 展示页面
         */
        if(readStatus == 2){
            postReaddata();
        }else {
            getdata();
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
