package com.a21zhewang.constructionapp.ui.xmxx;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.Inf.ISetData;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BaseAddSearchActivity;
import com.a21zhewang.constructionapp.bean.FbBean;
import com.a21zhewang.constructionapp.bean.ListBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProjectSynopsis;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.NoScrollViewPager;
import com.a21zhewang.constructionapp.ui.xmxx.fragment.FbFragment;
import com.a21zhewang.constructionapp.ui.xmxx.fragment.JbFragment;
import com.a21zhewang.constructionapp.ui.xmxx.fragment.zongbaoFragment;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class XmxxActivity extends BaseActivity {


    @BindView(R.id.activity_xmxx_RadioButton1)
    RadioButton activityXmxxRadioButton1;
    @BindView(R.id.activity_xmxx_RadioButton2)
    RadioButton activityXmxxRadioButton2;
    @BindView(R.id.activity_xmxx_RadioButton3)
    RadioButton activityXmxxRadioButton3;
    @BindView(R.id.activity_xmxx_RadioGroup)
    RadioGroup activityXmxxRadioGroup;
    @BindView(R.id.activity_xmxx_NoScrollViewPager)
    NoScrollViewPager activityXmxxNoScrollViewPager;
    @BindView(R.id.activity_xmxx_ImageView_img)
    ImageView activityXmxxImageViewImg;
    @BindView(R.id.activity_xmxx_tv_xmmc)
    MySpinner activityXmxxTvXmmc;
    @BindView(R.id.activity_xmxx_tv_xmdz)
    TextView activityXmxxTvXmdz;
    @BindView(R.id.activity_xmxx_tv_fzr)
    TextView activityXmxxTvFzr;
    @BindView(R.id.activity_xmxx_tv_lxdh)
    TextView activityXmxxTvLxdh;
    private List<Fragment> fragments;
    private FbFragment fbFragment;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout rightlinlayout;
    @BindView(R.id.title_TB_name)
    TextView title;
    private NamesSpinnerAdpater<ProjectSynopsis> adapter;
    private String projectID = "";
    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
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
        return R.layout.activity_xmxx;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        title.setText("项目信息");
        rightlinlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(XmxxActivity.this,Act_SearchProject.class),Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
            }
        });
        fragments = new ArrayList<>();
        fbFragment = FbFragment.newInstance();
        fragments.add(JbFragment.newInstance());
        fragments.add(zongbaoFragment.newInstance());//
        fragments.add(fbFragment);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        activityXmxxNoScrollViewPager.setAdapter(adapter);
        activityXmxxNoScrollViewPager.setOffscreenPageLimit(3);
        activityXmxxNoScrollViewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    activityXmxxRadioGroup.check(R.id.activity_xmxx_RadioButton1);
                } else if (position == 1) {
                    activityXmxxRadioGroup.check(R.id.activity_xmxx_RadioButton2);
                } else if (position == 2) {
                    activityXmxxRadioGroup.check(R.id.activity_xmxx_RadioButton3);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        activityXmxxRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.activity_xmxx_RadioButton1) {
                    activityXmxxNoScrollViewPager.setCurrentItem(0, false);
                } else if (checkedId == R.id.activity_xmxx_RadioButton2) {
                    activityXmxxNoScrollViewPager.setCurrentItem(1, false);
                } else if (checkedId == R.id.activity_xmxx_RadioButton3) {
                    activityXmxxNoScrollViewPager.setCurrentItem(2, false);

                }
            }
        });
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
        adapter = new NamesSpinnerAdpater<ProjectSynopsis>(this,
                new ArrayList<ProjectSynopsis>()) {
            @Override
            public void setinit(ProjectSynopsis name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getShortName());
            }
        };
        activityXmxxTvXmmc.setVisibility(View.GONE);
        activityXmxxTvXmmc.setmyspinnerlistadpater(adapter);
        activityXmxxTvXmmc.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectSynopsis item = adapter.getItem(position);
                activityXmxxTvXmmc.setTextViewtext(item.getShortName());

                getProject(item.getProjectID());
                activityXmxxTvXmmc.popdismiss();
            }
        });
        XUtil.postjsondata(
                JsonUtils.getjsonobj("{\"pageIndex\":\"0\",\"pageSize\":\"500\",\"etpID\":\"\"}"),
                "GetProjectList", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        ListBean projectSynopsisListBean= JsonUtils.getbean(res, ListBean.class);
                        List<ProjectSynopsis> recordList = projectSynopsisListBean.getRecordList();
                        if (recordList!=null&&recordList.size()>0){
                            adapter.replcedata(recordList);
                            ProjectSynopsis item = adapter.getItem(0);
                            activityXmxxTvXmmc.setTextViewtext(item.getShortName());
                            getProject(item.getProjectID());
                            projectID = item.getProjectID();
                            setProjectID(projectID);
                            activityXmxxTvXmmc.popdismiss();
                        }
                    }

                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    @Override
                    public void onWrong() {

                    }
                });


    }


    private void getProject(String projectID) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectID\":\"" + projectID + "\"}"),
                "GetProjectDetails", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        if ("无数据".equals(res)) return;
                        FbBean fbBean = JsonUtils.getbean(res, FbBean.class);
                        setData(fbBean);
                    }

                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    @Override
                    public void onWrong() {

                    }
                });
    }

    private void setData(FbBean fbBean) {
        String shortName = fbBean.getShortName();
        if (shortName != null)
            //  activityXmxxTvXmmc.setText(shortName);

            activityXmxxTvXmdz.setText("项目地址:" + fbBean.getAddress());
        activityXmxxTvFzr.setText("负责人:" + fbBean.getManager());
        activityXmxxTvLxdh.setText("联系电话:" + fbBean.getManagerPhone());
        String zongbaoTitleName = fbBean.getZongbaoTitleName();
        activityXmxxRadioButton2.setText(zongbaoTitleName);
        String fenbaoTitleName = fbBean.getFenbaoTitleName();
        activityXmxxRadioButton3.setText(fenbaoTitleName);
        String url = fbBean.getUrl();
        if (url != null)
            Glide.with(XmxxActivity.this)
                    .load(url)
                    .into(activityXmxxImageViewImg);
        for (int i = 0; i < fragments.size(); i++) {
            ((ISetData)fragments.get(i)).setData(fbBean);
        }
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Act_SearchProject.RESQUST_CODE_SELECT_TYPE

                && resultCode == RESULT_OK
                && data != null) {
            String projectId = data.getStringExtra("projectId");
            String projectName = data.getStringExtra("projectName");
            activityXmxxTvXmmc.setTextViewtext(projectName);
            getProject(projectId);
        }

    }

}
