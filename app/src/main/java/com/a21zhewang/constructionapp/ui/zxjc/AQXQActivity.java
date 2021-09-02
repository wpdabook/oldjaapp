package com.a21zhewang.constructionapp.ui.zxjc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AQXQActivity extends BaseActivity {

    @BindView(R.id.activity_basexq_tv_title)
    TextView activityBasexqTvTitle;
    @BindView(R.id.activity_basexq_tv_nrms)
    TextView activityBasexqTvNrms;
    @BindView(R.id.activity_basexq_RecyclerView_imgs)
    RecyclerView activityBasexqRecyclerViewImgs;
    public List<FilesBean> imgfiles=new ArrayList<>();
    public CommonAdapter<FilesBean> imgsAdapter;
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
        return R.layout.activity_aqxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        String checkContent = getIntent().getStringExtra("checkContent");
        imgfiles.addAll((List< FilesBean>) getIntent().getSerializableExtra("files"));
        activityBasexqTvNrms.setText(checkContent);
        //图片列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityBasexqRecyclerViewImgs.setLayoutManager(linearLayoutManager);
        imgsAdapter = new CommonAdapter<FilesBean>(this, R.layout.faqigtxt_imglist_item, imgfiles) {

            @Override
            protected void convert(ViewHolder holder, FilesBean s, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(AQXQActivity.this).load(s.getUrl()).into(imageView);
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
                        PhotoViewActivity.statPhotoViewActivity(AQXQActivity.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        activityBasexqRecyclerViewImgs.setAdapter(imgsAdapter);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {

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



}
