package com.a21zhewang.constructionapp.ui.jdzf;

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

public class Act_JdzfDetail extends BaseActivity {
    @BindView(R.id.jdzf_name)
    TextView name;
    @BindView(R.id.zf_company_name)
    TextView zf_company_name;
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
    private String id;
    private MyTitleBar activityIndexTitleBar;
    private String str1;
    private int status;
    private int type;
    private TextView tv_status;
    private JdzfBeanDetail jdzfBeanDetail;
    private List<CompanyInfo> companyInfoslist;
    private List<ReadInfo> readInfos;
    private List<ReadInfo> readInfoslist;
    private  StringBuffer sb;
    public com.zhy.adapter.abslistview.CommonAdapter<ReadInfo> readAdapter;
    public CommonAdapter<FileBean> imgsAdapter;
    public List<FileBean> imgfiles;
    private List<FileBean> filesBeanList;
    @Override
    public void initViews() {
        activityIndexTitleBar = (MyTitleBar) findViewById(R.id.activity_index_titlebar_one);
        activityIndexTitleBar.setTitlenametv("执法详情");
        tv_status = (TextView)findViewById(R.id.tv_status);
        str1 = getIntent().getStringExtra("str");
        id = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type",0);
        status = getIntent().getIntExtra("status",0);
        if(type == 1){ //限期整改
            tv_status.setText("限期整改");
        }else if(type == 2){
            tv_status.setText("停工整改");
        }else if(type == 3){
            tv_status.setText("简易处罚");
        }else if(type == 4){
            tv_status.setText("立案处罚");
        }
        readInfos = new ArrayList<ReadInfo>();
        imgfiles = new ArrayList<FileBean>();
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
                }else if(item.getStatus() == 3){
                    viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, "已读");
                }
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, (item.getReadTime()).substring(5,16));
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        PublicUtils.call(item.getUserId(),Act_JdzfDetail.this);
                        PublicUtils.call("",Act_JdzfDetail.this);
                    }
                });
            }
        };
        myReadListView.setAdapter(readAdapter);
        //图片列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        jdzf_recyclerview_imgs.setLayoutManager(linearLayoutManager);
        imgsAdapter = new CommonAdapter<FileBean>(this, R.layout.act_jdzfdetail_imgitem, imgfiles) {

            @Override
            protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, FileBean fileBean, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms,fileBean.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(Act_JdzfDetail.this).load(fileBean.getUrl()).into(imageView);
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
                        PhotoViewActivity.statPhotoViewActivity(Act_JdzfDetail.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        jdzf_recyclerview_imgs.setAdapter(imgsAdapter);
    }
    @OnClick({R.id.activity_basexq_LinearLayout_cyjls})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.activity_basexq_LinearLayout_cyjls:
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
    public void initListener() {
    }
    public void UpDate(){

    }
    @Override
    public int getLayoutId() {
        return R.layout.act_jdzfdetail;
    }


    @Override
    public void processClick(View v) {

    }

    private void getdate(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\"}"), "GetSuperviseDetail", new MyCallBack() {
            @Override
            public void onResule(String res) {
                jdzfBeanDetail = JsonUtils.getbean(res,JdzfBeanDetail.class);
                companyInfoslist = new ArrayList<CompanyInfo>();
                readInfoslist = new ArrayList<ReadInfo>();
                name.setText(jdzfBeanDetail.getProjectName());
                time.setText(jdzfBeanDetail.getCreateDate());
                title.setText(jdzfBeanDetail.getTitle());
                zf_company_name.setText(jdzfBeanDetail.getConManager());
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
                jdzf_companys.setText(sb.toString());
                readAdapter.notifyDataSetChanged();
                imgsAdapter.notifyDataSetChanged();
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
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"readUserId\":\"" + PublicUtils.UserID + "\"}"), "EditSupervise", new MyCallBack() {
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
    @Override
    public void beforesetContentView() {

    }
}
