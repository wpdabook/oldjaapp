package com.a21zhewang.constructionapp.ui.notice;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NoticeDetailBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WP-PC on 2019/6/26.
 */

public class Act_NoticeDetail extends BaseActivity {

    private String id;
    private int readStatus;
    private NoticeDetailBean noticeDetailBean;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.create_user_name)
    TextView create_user_name;
    @BindView(R.id.creat_time)
    TextView creat_time;
    @BindView(R.id.receive_company)
    TextView receive_company;
    @BindView(R.id.activity_notice_tv_nrms)
    TextView activityTzggxqTvNrms;
    private List<NoticeDetailBean.FilesBean> imgfiles;
    @BindView(R.id.activity_notice_RecyclerView_imgs)
    RecyclerView activityNoticeRecyclerViewImgs;
    private CommonAdapter<NoticeDetailBean.FilesBean> imgsAdapter;
    @BindView(R.id.activity_Notice_ImageView_cyjls)
    ImageView activityNoticeImageViewCyjls;
    @BindView(R.id.activity_basexq_LinearLayout_cyjls)
    LinearLayout activity_basexq_LinearLayout_cyjls;
    private String CreateUserId = "";
    private List<NoticeDetailBean.ReadLogsBean> readLogs;
    @BindView(R.id.activity_notice_MyListView_cyjl)
    MyListView activityNoticeMyListViewcyjl;
    private com.zhy.adapter.abslistview.CommonAdapter<NoticeDetailBean.ReadLogsBean> readLogsBeanCommonAdapter;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_notice_detail;
    }

    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
        CreateUserId = getIntent().getStringExtra("createUserId");
        righttext.setTextSize(16);
        readStatus = getIntent().getIntExtra("readStatus",0);
        //图片列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityNoticeRecyclerViewImgs.setLayoutManager(linearLayoutManager);
        imgfiles = new ArrayList<>();
        imgsAdapter = new CommonAdapter<NoticeDetailBean.FilesBean>(this, R.layout.faqigtxt_imglist_item3, imgfiles) {

            @Override
            protected void convert(ViewHolder holder, NoticeDetailBean.FilesBean s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img3);
                Glide.with(Act_NoticeDetail.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<NoticeDetailBean.FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<NoticeDetailBean.FilesBean>() {

                            @Override
                            public String getstrs(int index, NoticeDetailBean.FilesBean obj) {
                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, NoticeDetailBean.FilesBean obj) {
                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(Act_NoticeDetail.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                startActivityForResult(new Intent(Act_NoticeDetail.this,Act_NoticeReply.class)
                                                        .putExtra("id",id)
                                                ,Act_NoticeReply.RESQUST_CODE_REPLY);
                            }
        });
        readLogs = new ArrayList<>();
        //查阅纪录初始化
        View cyjlheadview = LayoutInflater.from(this).inflate(R.layout.gtxyxq_cyjl_list_headview, null, false);
        activityNoticeMyListViewcyjl.addHeaderView(cyjlheadview);
        readLogsBeanCommonAdapter = new com.zhy.adapter.abslistview.CommonAdapter<NoticeDetailBean.ReadLogsBean>(this
                , R.layout.gtxtxq_cyjl_list_item, readLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final NoticeDetailBean.ReadLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getCompanyName());
                if(item.getStatus() == 1){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "发起");
                    viewHolder.setTextColor(R.id.gtxtxq_cyjl_list_item_tv_status, mContext.getResources().getColor(R.color.color_666666));
                }else if(item.getStatus() == 2){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "未读");
                    viewHolder.setTextColor(R.id.gtxtxq_cyjl_list_item_tv_status, mContext.getResources().getColor(R.color.color_666666));
                }else if(item.getStatus() == 4){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "已回复");
                    viewHolder.setTextColor(R.id.gtxtxq_cyjl_list_item_tv_status, mContext.getResources().getColor(R.color.color_theme));
                }else {
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "已读");
                    viewHolder.setTextColor(R.id.gtxtxq_cyjl_list_item_tv_status, mContext.getResources().getColor(R.color.color_666666));
                }

                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, item.getReadTime().substring(5,16));
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //PublicUtils.call(item.getUserId(), Act_NoticeDetail.this);
//                        PublicUtils.call("", Act_NoticeDetail.this);
                        if(item.getStatus() == 4){
                               startActivity(new Intent(Act_NoticeDetail.this, Act_NoticeDetailReply.class)
                                .putExtra("id",id).putExtra("replyUserId",item.getUserId()));
                        }
                    }
                });
            }
        };
        activityNoticeMyListViewcyjl.setAdapter(readLogsBeanCommonAdapter);
        activity_basexq_LinearLayout_cyjls.setOnClickListener(new View.OnClickListener() {
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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"readUserId\":\"" + PublicUtils.UserID + "\"}"), "EditJGNotice", new MyCallBack() {

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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\"}"), "GetJGNoticeDetail", new MyCallBack() {

            @Override
            public void onResule(String res) {
                noticeDetailBean = JsonUtils.getbean(res, NoticeDetailBean.class);
                title.setText(noticeDetailBean.getTitle());
                create_user_name.setText(noticeDetailBean.getCreateUserName());
                creat_time.setText(noticeDetailBean.getCreateDate());
                receive_company.setText(noticeDetailBean.getCompanyJson());
                activityTzggxqTvNrms.setText(noticeDetailBean.getContent());
                List<NoticeDetailBean.FilesBean> files = noticeDetailBean.getItemFileList();
                imgfiles.clear();
                if (files != null && files.size() > 0) {
                    activityNoticeRecyclerViewImgs.setVisibility(View.VISIBLE);
                    imgfiles.addAll(files);
                } else {
                    activityNoticeRecyclerViewImgs.setVisibility(View.GONE);
                }
                readLogs.clear();
                List<NoticeDetailBean.ReadLogsBean> rLogs = noticeDetailBean.getItemReadList();
                if (rLogs != null && rLogs.size() > 0) {
                    readLogs.addAll(rLogs);
                }
                if(!PublicUtils.UserID.equals(CreateUserId)){
                       righttext.setVisibility(View.VISIBLE);
                     }else {
                       righttext.setVisibility(View.GONE);
                }
                activityNoticeRecyclerViewImgs.setAdapter(imgsAdapter);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK && requestCode == Act_NoticeReply.RESQUST_CODE_REPLY && data != null) {
                    getdata();
                }
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
