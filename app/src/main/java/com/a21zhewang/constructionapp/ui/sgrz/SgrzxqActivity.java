package com.a21zhewang.constructionapp.ui.sgrz;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.SgrzBean;
import com.a21zhewang.constructionapp.bean.SgrzmsBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SgrzxqActivity extends BaseActivity {


    @BindView(R.id.sgrzxq_nrms_MyListView)
    MyListView sgrzxqNrmsMyListView;
    @BindView(R.id.activity_sgrzxq_tv_title)
    TextView activitySgrzxqTvTitle;
    @BindView(R.id.activity_sgrzxq_tv_pjname)
    TextView activitySgrzxqTvPjname;
    @BindView(R.id.activity_sgrzxq_tv_time)
    TextView activitySgrzxqTvTime;
    @BindView(R.id.activity_sgrzxq_tv_sgdw)
    TextView activitySgrzxqTvSgdw;
    @BindView(R.id.activity_sgrzxq_tv_addman)
    TextView activitySgrzxqTvAddman;
    @BindView(R.id.activity_sgrzxq_tv_tq)
    TextView activitySgrzxqTvTq;
    @BindView(R.id.activity_sgrzxq_tv_gzjl)
    TextView activitySgrzxqTvGzjl;
    @BindView(R.id.activity_sgrzxq_tv_bz)
    TextView activitySgrzxqTvBz;
    private List<SgrzmsBean> sgrzmsBeens;
    private CommonAdapter<SgrzmsBean> sgrzmsBeanCommonAdapter;
    private SgrzBean obj;

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
        return R.layout.activity_sgrzxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        obj = getIntent().getParcelableExtra("obj");
        if (obj == null) return;
        activitySgrzxqTvTitle.setText(obj.getProShortName()+ obj.getRecordTime()+"施工日志");
        activitySgrzxqTvPjname.setText(obj.getProShortName());
        activitySgrzxqTvAddman.setText(obj.getCreateUserName());
        activitySgrzxqTvSgdw.setText(obj.getEtpShortName());
        activitySgrzxqTvTq.setText(obj.getWeather());
        activitySgrzxqTvBz.setText(obj.getRemarks());
        activitySgrzxqTvGzjl.setText(obj.getContent());
        activitySgrzxqTvTime.setText(obj.getRecordTime());

        sgrzmsBeens = new ArrayList<>();
        sgrzmsBeanCommonAdapter = new CommonAdapter<SgrzmsBean>(this, R.layout.sgrzxq_nrms_list_item, sgrzmsBeens) {
            @Override
            protected void convert(ViewHolder viewHolder, SgrzmsBean item, int position) {
                viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_index,(position+1)+"");
                viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_ms,item.getRecordDesc());
                viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_qy,item.getProjectAreaName());
                if ("1".equals(item.getProjectType())){
                    String floor = item.getFloor();
                    if (!TextUtils.isEmpty(floor)) {

                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lcandbg,"楼层:");
                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lc,item.getFloor());
                    } else {
                        String elevation = item.getElevation();
                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lcandbg,"标高:");

                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lc, elevation);
                    }
                }else{
                    String floor = item.getFloor();
                    if (!TextUtils.isEmpty(floor)) {

                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lcandbg,"里程:");
                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lc,item.getFloor());
                    } else {
                        String elevation = item.getElevation();
                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lcandbg,"位置:");

                        viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_lc, elevation);
                    }
                }



                viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_gx,item.getSubProjectName());
                viewHolder.setText(R.id.sgrzxq_nrms_list_item_tv_rs,item.getNumber());
            }
        };
        sgrzxqNrmsMyListView.setAdapter(sgrzmsBeanCommonAdapter);
    }

    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        XUtil.postjsondata(JsonUtils.getjsonobj("{\"recordID\":\""+obj.getRecordID()+"\"}"),
                "GetDailyLogRecord", new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                List<SgrzmsBean> sgrzmsBeen = JsonUtils.jsonToList(res, SgrzmsBean[].class);
                if (sgrzmsBeen!=null&&sgrzmsBeen.size()>0){
                    sgrzmsBeens.addAll(sgrzmsBeen);
                    sgrzmsBeanCommonAdapter.notifyDataSetChanged();
                }
            }

            /**
             * code 不等于 0回调
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

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


}
