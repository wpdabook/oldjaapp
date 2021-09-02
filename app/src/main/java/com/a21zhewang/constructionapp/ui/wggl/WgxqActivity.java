package com.a21zhewang.constructionapp.ui.wggl;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CompanyDate;
import com.a21zhewang.constructionapp.bean.DealLog;
import com.a21zhewang.constructionapp.bean.ElementDate;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TzggxqBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
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

public class WgxqActivity extends BaseActivity {

    @BindView(R.id.activity_tzggxq_MyTitleBar)
    MyTitleBar activityTzggxqTvMyTitleBar;
    @BindView(R.id.activity_tzggxq_tv_bt)
    TextView activityTzggxqTvBt;
    @BindView(R.id.activity_basexq_tv_wtbw)
    TextView activityBasexqTvWtbw;
    @BindView(R.id.activity_tzggxq_tv_tsr)
    TextView activityTzggxqTvTsr;
    @BindView(R.id.activity_basexq_tv_lctv)
    TextView activityBasexqTvLctv;
    @BindView(R.id.activity_tzggxq_tv_time)
    TextView activityTzggxqTvTime;
    @BindView(R.id.activity_tzggxq_tv_jsdw)
    TextView activityTzggxqTvJsdw;
    @BindView(R.id.activity_basexq_headerview)
    LinearLayout activityBasexqHeaderview;
    @BindView(R.id.activity_tzggxq_tv_nrms)
    TextView activityTzggxqTvNrms;
    @BindView(R.id.activity_tzggxq_RecyclerView_imgs)
    RecyclerView activityTzggxqRecyclerViewImgs;
    @BindView(R.id.activity_tzggxq_MyListView_cyjls)
    MyListView activityTzggxqMyListViewCyjls;
    private List<TzggxqBean.FilesBean> imgfiles;
    private CommonAdapter<TzggxqBean.FilesBean> imgsAdapter;
    private List<TzggxqBean.ReadLogsBean> readLogs;
    private com.zhy.adapter.abslistview.CommonAdapter<TzggxqBean.ReadLogsBean> readLogsBeanCommonAdapter;
    private com.zhy.adapter.abslistview.CommonAdapter<DealLog> dealLogsAdapter;
    private String recordID;
    private List<CompanyDate> companyDateList = new ArrayList<>();
    private List<ElementDate> itemElementList = new ArrayList<>();


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
        return R.layout.act_wgglxq_layout;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {

        Intent intent = getIntent();
        recordID = intent.getStringExtra("recordID");
        if (recordID == null)
            return;
        //图片列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityTzggxqRecyclerViewImgs.setLayoutManager(linearLayoutManager);
        imgfiles = new ArrayList<>();
        imgsAdapter = new CommonAdapter<TzggxqBean.FilesBean>(this, R.layout.faqigtxt_imglist_item, imgfiles) {

            @Override
            protected void convert(ViewHolder holder, TzggxqBean.FilesBean s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(WgxqActivity.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<TzggxqBean.FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<TzggxqBean.FilesBean>() {

                            @Override
                            public String getstrs(int index, TzggxqBean.FilesBean obj) {
                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, TzggxqBean.FilesBean obj) {
                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(WgxqActivity.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        activityTzggxqRecyclerViewImgs.setAdapter(imgsAdapter);
        readLogs = new ArrayList<>();
        //查阅纪录初始化
        View cyjlheadview = LayoutInflater.from(this).inflate(R.layout.gtxyxq_cyjl_list_headview, null, false);
        activityTzggxqMyListViewCyjls.addHeaderView(cyjlheadview);
        readLogsBeanCommonAdapter = new com.zhy.adapter.abslistview.CommonAdapter<TzggxqBean.ReadLogsBean>(this
                , R.layout.gtxtxq_cyjl_list_item, readLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final TzggxqBean.ReadLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, item.getStatus());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, item.getTime());
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PublicUtils.call(item.getUserPhone(), WgxqActivity.this);
                    }
                });
            }
        };
        activityTzggxqMyListViewCyjls.setAdapter(readLogsBeanCommonAdapter);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
    }

    public void postjsondata(String btnid) {
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }

    /**
     * 页面显示
     */
    @Override
    public void resume() {

        getdata();
    }

    private void getdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + recordID + "\"}"), "GetSocialSuperviseDetail", new MyCallBack() {

            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                TzggxqBean tzggxqBean = JsonUtils.getbean(res, TzggxqBean.class);
//                activityTzggxqTvMyTitleBar.setTitlenametv(tzggxqBean.getTitle());
                activityTzggxqTvMyTitleBar.setTitlenametv("网格员管理详情");
                activityTzggxqTvBt.setText(tzggxqBean.getTitle());
                activityTzggxqTvBt.setTextSize(12);
                activityTzggxqTvTsr.setText(tzggxqBean.getCreateName());
                activityTzggxqTvTime.setText(tzggxqBean.getCreateDate().substring(0,11));
                activityTzggxqTvNrms.setText(tzggxqBean.getRemark());
                companyDateList = tzggxqBean.getItemCompanyList();
                itemElementList = tzggxqBean.getItemElementList();
                for(int i=0;i<itemElementList.size();i++){
                    String companyName = itemElementList.get(i).getDicName();
                    activityTzggxqTvJsdw.setText(companyName);
                }
//                List<String> stringList = new ArrayList<String>();
//                for(int i=0;i<companyDateList.size();i++){
//                    String companyName = companyDateList.get(i).getCompanyName();
//                    stringList.add(companyName);
//                }
//                removeDuplicate(stringList);
//                StringBuffer sb = new StringBuffer();
//                for(int j=0;j<stringList.size();j++){
//                    sb.append(stringList.get(j)+"，");
//                }
//                if(sb != null && sb.length()>0){
//                    activityTzggxqTvJsdw.setText(sb.deleteCharAt(sb.length() - 1).toString());
//                }
                List<TzggxqBean.FilesBean> files = tzggxqBean.getFiles();
                imgfiles.clear();
                if (files != null && files.size() > 0) {
                    activityTzggxqRecyclerViewImgs.setVisibility(View.VISIBLE);
                    imgfiles.addAll(files);
                } else {
                    activityTzggxqRecyclerViewImgs.setVisibility(View.GONE);
                }
                readLogs.clear();
                List<TzggxqBean.ReadLogsBean> rLogs = tzggxqBean.getReadLogs();
                if (rLogs != null && rLogs.size() > 0) {
                    readLogs.addAll(rLogs);
                }

                imgsAdapter.notifyDataSetChanged();
                readLogsBeanCommonAdapter.notifyDataSetChanged();
            }

            /**
             * 请求成功code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }
        });
    }

    private static void removeDuplicate(List<String> list) {
        List<String> result = new ArrayList<String>(list.size());
        for (String str : list) {
            if (!result.contains(str)) {
                result.add(str);
            }
        }
        list.clear();
        list.addAll(result);
    }
}
