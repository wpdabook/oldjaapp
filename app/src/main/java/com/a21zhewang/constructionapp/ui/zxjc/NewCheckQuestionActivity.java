package com.a21zhewang.constructionapp.ui.zxjc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.utils.FileUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCheckQuestionActivity extends BaseActivity implements OnFragmentInteractionListener {

   // private static final String[] CHANNELS = new String[]{"安全", "隐患"};
    private List<String> mDataList = new ArrayList<>();
    @BindView(R.id.activity_new_check_qestion_sava)
    RelativeLayout activityNewCheckQestionSava;
    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.activity_new_check_qestion)
    LinearLayout activityNeCheckQestion;
    @BindView(R.id.MyTitleBar)
    MyTitleBar myTitleBar;
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
        return R.layout.activity_new_check_qestion;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        int isSafe = getIntent().getIntExtra("isSafe", -1);
        final List<Fragment> fragments=new ArrayList<>();
        if (isSafe==0){
            myTitleBar.setTitlenametv("新增安全信息");
            mDataList.add("安全");
            fragments.add(ADDAQDKFragment.newInstance("",""));
            magicIndicator.setVisibility(View.GONE);
        }else if (isSafe==1){
            mDataList.add("隐患");
            magicIndicator.setVisibility(View.GONE);
            fragments.add(ADDYHWTFragment.newInstance("",""));
        }else{
            mDataList.add("安全");
            mDataList.add("隐患");
            fragments.add(ADDAQDKFragment.newInstance("",""));
            fragments.add(ADDYHWTFragment.newInstance("",""));
        }


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        initMagicIndicator();

    }
    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.parseColor("#F9FBFC"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);

                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#FF2D2D2D"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#FF0084CF"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#FF0084CF"));
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setLineHeight(4);
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        // titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        // titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        // titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, viewPager);
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



    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }


    @Override
    public void onFragmentInteraction(Object obj) {
        activityNewCheckQestionSava.setOnClickListener((View.OnClickListener) obj);
    }
}
