package com.a21zhewang.constructionapp.ui.jdsb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.JDBean;
import com.a21zhewang.constructionapp.bean.ProjectPhase;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JDSBXQActivity extends BaseActivity {

    @BindView(R.id.tv_pvname)
    TextView tvPvname;
    @BindView(R.id.tv_addtime)
    TextView tvAddtime;
    @BindView(R.id.tv_dw)
    TextView tvDw;
    @BindView(R.id.tv_tbry)
    TextView tvTbry;
    @BindView(R.id.tv_zczwcb)
    TextView tvZczwcb;
    @BindView(R.id.tv_ydczwcb)
    TextView tvYdczwcb;
    @BindView(R.id.rv_jdlist)
    RecyclerView rvJdlist;
    private BaseQuickAdapter<ProjectPhase, BaseViewHolder> baseQuickAdapter;

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
        return R.layout.activity_jdsbxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        JDBean data = getIntent().getParcelableExtra("data");
        if (data==null)return;
        tvPvname.setText(data.getProShortName());
        tvAddtime.setText(data.getCreateTime());
        tvDw.setText(data.getEtpShortName());
        tvTbry.setText(data.getCreateUserName());
        tvZczwcb.setText(data.getTotalPercentage());
        tvYdczwcb.setText(data.getMonthPercentage());

        rvJdlist.setLayoutManager(new LinearLayoutManager(this));
        /**
         * Implement this method and use the helper to adapt the view to the given item.
         *
         * @param helper A fully initialized helper.
         * @param item   The item that needs to be displayed.
         */
        baseQuickAdapter = new BaseQuickAdapter<ProjectPhase, BaseViewHolder>(R.layout.jdsbxq_item, data.getProjectPhase()) {
            /**
             * Implement this method and use the helper to adapt the view to the given item.
             *
             * @param helper A fully initialized helper.
             * @param item   The item that needs to be displayed.
             */
            @Override
            protected void convert(BaseViewHolder helper, ProjectPhase item) {
                helper.setText(R.id.tv_jdname, item.getDicName());
                helper.setText(R.id.tv_double, item.getCompletionRate());
                helper.setText(R.id.tv_day, item.getLaggingdays());
                helper.setText(R.id.tv_content, item.getRecordDesc());
                helper.setText(R.id.tv_jh, item.getPlanStartDate() + "~" + item.getPlanEndDate());
                helper.setText(R.id.tv_sj, item.getActuallyStartDate() + "~" + item.getActuallyEndDate());


            }
        };

        //jdlist.setHasFixedSize(true);
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvJdlist.setNestedScrollingEnabled(false);
        rvJdlist.setAdapter(baseQuickAdapter);
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
