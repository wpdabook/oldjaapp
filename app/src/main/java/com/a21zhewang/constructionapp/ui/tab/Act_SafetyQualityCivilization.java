package com.a21zhewang.constructionapp.ui.tab;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.HiddenBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskHidden;
import com.a21zhewang.constructionapp.bean.RiskHiddenBean;
import com.a21zhewang.constructionapp.bean.SQCBean;
import com.a21zhewang.constructionapp.bean.SafetyListBean;
import com.a21zhewang.constructionapp.customview.MyViewPager;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 风险隐患-安全、质量、文明详情页面
 */

public class Act_SafetyQualityCivilization extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView title_back_img;
    @BindView(R.id.indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public static boolean NEEDREFRESH;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> tabTitles = new ArrayList<>();
    private FragmentPagerAdapter pagerAdapter;
    private String projectId;
    private String projectName;
    private int rectType;
    private SQCBean sqcBean;
    private List<SafetyListBean> safetyListBeanList = new ArrayList<>();
    protected List<RiskHiddenBean> ridkhiddenBeanList = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        if (NEEDREFRESH && fragmentList.size() > 0) {
            ((QualityCivilizationFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
            NEEDREFRESH = false;
        }
    }
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_safetyqualitycivilization;
    }

    @Override
    public void initViews() {
        projectId = getIntent().getStringExtra("projectId");
        projectName = getIntent().getStringExtra("projectName");
        rectType = getIntent().getIntExtra("rectType",0);
//        viewpager.setOffscreenPageLimit(4);
        title.setText(projectName);
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void initListener() {
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragmentList.get(arg0);
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        viewpager.setAdapter(pagerAdapter);
        mIndicator.setOnPageChangeListener(new ViewPagerIndicator.PageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ((QualityCivilizationFragment) (fragmentList.get(position))).initData();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 3 + "\",\"projectId\":\"" + projectId + "\",\"rectType\":\"" + rectType + "\"}"),
                "GetDataTotal2_rectCheckFloorTotal", new MyCallBack() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onResule(String res) {
                        if("{}".equals(res)){
                            mIndicator.setVisibility(View.GONE);
                            return;
                        }else {
                            try {
                                sqcBean = JsonUtils.getbean(res, SQCBean.class);
                                safetyListBeanList = sqcBean.getSafetyList();
                                for(int i=0;i<safetyListBeanList.size();i++){
                                    tabTitles.add(safetyListBeanList.get(i).getTypeName());
                                    QualityCivilizationFragment fragment = new QualityCivilizationFragment();
                                    Bundle b = new Bundle();
                                    b.putString("areaName", safetyListBeanList.get(i).getTypeName());
                                    ridkhiddenBeanList = safetyListBeanList.get(i).getData();
                                    b.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) ridkhiddenBeanList);
                                    b.putInt("dateType", 3);//今日隐患数据
                                    b.putInt("rectType", rectType);
                                    b.putString("projectId", projectId);
                                    fragment.setArguments(b);
                                    fragmentList.add(fragment);
                                }
                                mIndicator.setTabItemTitles(tabTitles);
                                pagerAdapter.notifyDataSetChanged();
                                mIndicator.setViewPager(viewpager, 0);
                                ((QualityCivilizationFragment) fragmentList.get(0)).initData();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
    @Override
    public void processClick(View v) {

    }
}
