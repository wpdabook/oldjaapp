package com.a21zhewang.constructionapp.ui.wdjc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AqscxqBean;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.ui.check.Act_ProceduralHistoryDetail;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 重点检查-安全信息页
 * Created by WP-PC on 2020/3/17.
 */

public class SafeInfoActivity extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    private List<FilesBean> filelist;
    private String CheckContent;
    @BindView(R.id.item_detail_recyclerview)
    RecyclerView recyclerView;
    public CommonAdapter<FilesBean> imgsAdapter;
    @BindView(R.id.tv_content)
    TextView tv_content;
    //public List<AqscxqBean.FilesBean> imgfiles;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.safeinfo_layout;
    }

    @Override
    public void initViews() {
        title.setText("安全检查记录");
        //图片列表
        //imgfiles = new ArrayList<>();
        filelist = (List<FilesBean>) getIntent().getSerializableExtra("filelist");
        CheckContent = getIntent().getStringExtra("content");
        tv_content.setText(CheckContent);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        imgsAdapter = new CommonAdapter<FilesBean>(this, R.layout.faqigtxt_imglist_item, filelist) {


            @Override
            protected void convert(ViewHolder holder, FilesBean s, final int position) {
//                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(SafeInfoActivity.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<FilesBean>() {

                            @Override
                            public String getstrs(int index, FilesBean obj) {

                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, FilesBean obj) {

                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(SafeInfoActivity.this, position, filelist, pagerAdapterAPI);
                    }
                });
            }
        };
        recyclerView.setAdapter(imgsAdapter);
        if(filelist.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.GONE);
        }
        imgsAdapter.notifyDataSetChanged();
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
