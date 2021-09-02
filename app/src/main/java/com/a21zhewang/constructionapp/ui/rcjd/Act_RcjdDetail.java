package com.a21zhewang.constructionapp.ui.rcjd;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CompanyInfo;
import com.a21zhewang.constructionapp.bean.FileBean;
import com.a21zhewang.constructionapp.bean.ItemFileList;
import com.a21zhewang.constructionapp.bean.ItemReplyList;
import com.a21zhewang.constructionapp.bean.JdzfBeanDetail;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ReadInfo;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 56864 on 2019/5/22.
 * 更改状态，就传id和status,如果你要更改查阅人状态就只传id和readUserId
 * EditSupervise  id和readUserId  再 GetSuperviseDetail  id
 *
 */

public class Act_RcjdDetail extends BaseActivity {
    @BindView(R.id.jdzf_name)
    TextView name;
    @BindView(R.id.title_righttextview)
    TextView righttext;
//    @BindView(R.id.zf_company_name)
//    TextView zf_company_name;
    @BindView(R.id.jdzf_time)
    TextView time;
    @BindView(R.id.jdzf_title)
    TextView title;
    @BindView(R.id.jdzf_companys)
    TextView jdzf_companys;
    @BindView(R.id.read_list)
    MyListView myReadListView;
    @BindView(R.id.open_ico)
    ImageView open_ico;
    @BindView(R.id.jdzf_recyclerview_imgs)
    RecyclerView jdzf_recyclerview_imgs;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_reply_content)
    TextView reply_content;
    @BindView(R.id.tv_reply_people)
    TextView tv_reply_people;
    @BindView(R.id.reply_recyclerview_imgs)
    RecyclerView reply_recyclerview;
    @BindView(R.id.tv_reply_time)
    TextView tv_reply_time;
    @BindView(R.id.reply_more)
    ImageView reply_more;
    @BindView(R.id.reply_lin)
    LinearLayout reply_lin;
    @BindView(R.id.jdzf_status)
    TextView jdzf_status;
    @BindView(R.id.jdzf_reason)
    TextView jdzf_reason;
    @BindView(R.id.check_lin_reason)
    LinearLayout check_lin_reason;
    private String id;
    private MyTitleBar activityIndexTitleBar;
    private LinearLayout li_submie;
    private String str1;
    private int status;
    private int type;
    private TextView tv_status;
    private Button btn_submit;
    private JdzfBeanDetail jdzfBeanDetail;
    private List<CompanyInfo> companyInfoslist;
    private List<ReadInfo> readInfos;
    private List<ReadInfo> readInfoslist;
    private  StringBuffer sb;
    public com.zhy.adapter.abslistview.CommonAdapter<ReadInfo> readAdapter;
    public CommonAdapter<FileBean> imgsAdapter;
    public CommonAdapter<ItemFileList> replyAdapter;
    public List<FileBean> imgfiles;
    private List<FileBean> filesBeanList;
    private List<ItemReplyList> itemReplyList = new ArrayList<>();
    private List<ItemFileList> replyFilesList;
    @Override
    public void initViews() {
        activityIndexTitleBar = (MyTitleBar) findViewById(R.id.activity_index_titlebar_one);
        activityIndexTitleBar.setTitlenametv("日常监督");
        righttext.setVisibility(View.VISIBLE);
        righttext.setTextSize(16);
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Act_DxzfDetail.this,Act_Dxreply.class)
//                        .putExtra("id",jdzfBeanDetail.getId())
//                        .putExtra("proShortName",jdzfBeanDetail.getProjectName())
//                        .putExtra("createUserName",jdzfBeanDetail.getCreateName()));
                startActivityForResult(new Intent(Act_RcjdDetail.this,Act_Rcjdreply.class)
                        .putExtra("id",jdzfBeanDetail.getId())
                        .putExtra("proShortName",jdzfBeanDetail.getProjectName())
                        .putExtra("createUserName",jdzfBeanDetail.getCreateName())
                        ,Act_Rcjdreply.RESQUST_CODE_REPLY);
            }
        });
        li_submie = (LinearLayout)findViewById(R.id.li_submie);
        tv_status = (TextView)findViewById(R.id.tv_status);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        str1 = getIntent().getStringExtra("str");
        id = getIntent().getStringExtra("id");
        if("1".equals(str1)){
            li_submie.setVisibility(View.GONE);
        }else {
            li_submie.setVisibility(View.VISIBLE);
        }
        type = getIntent().getIntExtra("type",0);
        status = getIntent().getIntExtra("status",0);
        if(status == 2){
            btn_submit.setVisibility(View.GONE);
        }
        if(type == 1){ //限期整改
            tv_status.setText("限期整改");
            btn_submit.setText("已收到整改回复");
        }else if(type == 2){
            tv_status.setText("停工整改");
            btn_submit.setText("已复工");
        }else if(type == 3){
            tv_status.setText("简易处罚");
            btn_submit.setText("罚款已交");
        }else if(type == 4){
            tv_status.setText("立案处罚");
            btn_submit.setText("已结案");
        }
        else if(type == 5){
            tv_status.setText("扣分告知书");
            btn_submit.setVisibility(View.GONE);
        }
        readInfos = new ArrayList<ReadInfo>();
        imgfiles = new ArrayList<FileBean>();
        replyFilesList = new ArrayList<>();
        //查阅纪录初始化
        View cyjlheadview = LayoutInflater.from(this).inflate(R.layout.zfjd_detail_headview, null, false);
        myReadListView.addHeaderView(cyjlheadview);
        readAdapter = new com.zhy.adapter.abslistview.CommonAdapter<ReadInfo>(this
                , R.layout.zfjd_read_list_item, readInfos) {
            @Override
            protected void convert(ViewHolder viewHolder, final ReadInfo item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getCompanyName());
                //查阅记录里的status  --1、发起	2：未读	3：已读
                if(item.getStatus() == 1){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "发起");
                }else if(item.getStatus() == 2){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "未读");
//                    viewHolder.setVisible(R.id.gtxtxq_cyjl_list_item_tv_status, false);
                }else if(item.getStatus() == 3){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "已读");
                }
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, (item.getReadTime()).substring(5,16));
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        PublicUtils.call(item.getUserId(),Act_JdzfDetail.this);
                        PublicUtils.call("",Act_RcjdDetail.this);
                    }
                });
            }
        };
        myReadListView.setAdapter(readAdapter);
        //图片列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        jdzf_recyclerview_imgs.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        reply_recyclerview.setLayoutManager(linearLayoutManager2);
        imgsAdapter = new CommonAdapter<FileBean>(this, R.layout.act_jdzfdetail_imgitem, imgfiles) {

            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, FileBean fileBean, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms,fileBean.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(Act_RcjdDetail.this).load(fileBean.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<FileBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<FileBean>() {

                            @Override
                            public String getstrs(int index, FileBean obj) {
                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, FileBean obj) {
                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(Act_RcjdDetail.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        jdzf_recyclerview_imgs.setAdapter(imgsAdapter);
        reply_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RcjdDetail.this,Act_RcjdReplyDetail.class).putExtra("id",jdzfBeanDetail.getId()));
            }
        });
    }
    @OnClick({R.id.open_ico})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.open_ico:
                if (myReadListView.getVisibility()==View.VISIBLE){
                    myReadListView.setVisibility(View.GONE);
                    open_ico.setImageResource(R.mipmap.arrowdown);
                }else{
                    myReadListView.setVisibility(View.VISIBLE);
                    open_ico.setImageResource(R.mipmap.arrowup);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Act_Rcjdreply.RESQUST_CODE_REPLY && data != null) {
            getDate();
        }
    }

    @Override
    public void initListener() {
        li_submie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_RcjdDetail.this.finish();
            }
        });
        /**
         * 更新处理状态：1：处理中  2：已闭合
         */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpDate();
            }
        });
    }
    public void UpDate(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"status\":\"" + 2 + "\"}"), "EditSupervise", new MyCallBack() {
            @Override
            public void onResule(String res) {
                Act_RcjdDetail.this.finish();
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
    public int getLayoutId() {
        return R.layout.act_dxzfdetail;
    }


    @Override
    public void processClick(View v) {

    }
    @Override
    public void initData() {
              getDate();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void getDate(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"readUserId\":\"" + PublicUtils.UserID + "\"}"), "EditDaySuperviseManage", new MyCallBack() {
            @Override
            public void onResule(String res) {
                getdate();
            }
            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
        });
    }

    public void getdate() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\"}"), "GetDaySuperviseManageDetail", new MyCallBack() {
            @Override
            public void onResule(String res) {
                jdzfBeanDetail = JsonUtils.getbean(res,JdzfBeanDetail.class);
                companyInfoslist = new ArrayList<CompanyInfo>();
                itemReplyList = jdzfBeanDetail.getItemReplyList();
                readInfoslist = new ArrayList<ReadInfo>();
                name.setText(jdzfBeanDetail.getProjectName());
                time.setText(jdzfBeanDetail.getCreateDate());
                title.setText(jdzfBeanDetail.getTitle());
                if(jdzfBeanDetail.getOtherStatusReason() != null){
                    check_lin_reason.setVisibility(View.VISIBLE);
                    jdzf_reason.setText(jdzfBeanDetail.getOtherStatusReason());
                }else {
                    check_lin_reason.setVisibility(View.GONE);
                }
                if(jdzfBeanDetail.getOtherStatus() == 0){
                    jdzf_status.setVisibility(View.VISIBLE);
                    jdzf_status.setText("整改审核状态：通过");
                }else if(jdzfBeanDetail.getOtherStatus() == 1){
                    jdzf_status.setVisibility(View.VISIBLE);
                    jdzf_status.setText("整改审核状态：打回");
                }else if(jdzfBeanDetail.getOtherStatus() == 2){
                    jdzf_status.setVisibility(View.VISIBLE);
                    jdzf_status.setText("整改审核状态：升级");
                }else {
                    jdzf_status.setVisibility(View.GONE);
                }
                if(itemReplyList.size() != 0){
                    reply_lin.setVisibility(View.VISIBLE);
                    tv_reply_people.setText(itemReplyList.get(0).getUserName());
                    tv_reply_time.setText(itemReplyList.get(0).getReadTime());
                    reply_content.setText(itemReplyList.get(0).getContent());
                    replyFilesList = itemReplyList.get(0).getItemFileList();
                }else {
                    reply_lin.setVisibility(View.GONE);
                }
//                zf_company_name.setText(jdzfBeanDetail.getConManager());
                tv_content.setText(jdzfBeanDetail.getContent());
                companyInfoslist = jdzfBeanDetail.getItemCompanyList();
                filesBeanList = jdzfBeanDetail.getItemFileList();
                readInfoslist = jdzfBeanDetail.getItemReadList();
                sb = new StringBuffer();
                for(int i=0;i<companyInfoslist.size();i++){
                    sb.append(companyInfoslist.get(i).getCompanyName()+"，");
                }
                readInfos.clear();
                if (readInfoslist != null && readInfoslist.size() > 0) {
                    readInfos.addAll(readInfoslist);
                }
                imgfiles.clear();
                if (filesBeanList != null && filesBeanList.size() > 0) {
                    imgfiles.addAll(filesBeanList);
                }
                if(!PublicUtils.UserID.equals(jdzfBeanDetail.getCreateId())){
                    li_submie.setVisibility(View.GONE);
                }
                jdzf_companys.setText(sb.toString());
                readAdapter.notifyDataSetChanged();
                imgsAdapter.notifyDataSetChanged();
                replyAdapter = new CommonAdapter<ItemFileList>(Act_RcjdDetail.this, R.layout.act_jdzfdetail_imgitem, replyFilesList) {

                    @Override
                    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, ItemFileList fileBean, final int position) {
                        holder.setText(R.id.faqigtxt_imglist_item_ms,fileBean.getFileBrief());
                        ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                        Glide.with(Act_RcjdDetail.this).load(fileBean.getUrl()).into(imageView);
                        holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PhotoViewActivity.PagerAdapterAPI<ItemFileList> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<ItemFileList>() {

                                    @Override
                                    public String getstrs(int index, ItemFileList obj) {
                                        return obj.getUrl();
                                    }

                                    @Override
                                    public String getms(int index, ItemFileList obj) {
                                        return obj.getFileBrief();
                                    }
                                };
                                PhotoViewActivity.statPhotoViewActivity(Act_RcjdDetail.this, position, replyFilesList, pagerAdapterAPI);
                            }
                        });
                    }
                };
                reply_recyclerview.setAdapter(replyAdapter);
                replyAdapter.notifyDataSetChanged();
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
    public void beforesetContentView() {

    }
}
