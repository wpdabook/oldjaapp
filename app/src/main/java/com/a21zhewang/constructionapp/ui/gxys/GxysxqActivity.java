package com.a21zhewang.constructionapp.ui.gxys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.NoScrollViewPager;
import com.a21zhewang.constructionapp.ui.gxys.fragment.GxysxqgxxxFragment;
import com.a21zhewang.constructionapp.ui.gxys.fragment.GxysxqzgjlFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GxysxqActivity extends BaseActivity {
    @BindView(R.id.activity_gxysxq_RadioButton1)
    RadioButton activityGxysxqRadioButton1;
    @BindView(R.id.activity_gxysxq_RadioButton2)
    RadioButton activityGxysxqRadioButton2;
    @BindView(R.id.activity_gxysxq_RadioGroup)
    RadioGroup activityGxysxqRadioGroup;
    @BindView(R.id.activity_gxysxq_NoScrollViewPager)
    NoScrollViewPager activityGxysxqNoScrollViewPager;
    private List<Fragment> fragments;

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
        return R.layout.activity_gxysxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        String recordID = getIntent().getStringExtra("recordID");
        fragments = new ArrayList<>();
        fragments.add(GxysxqgxxxFragment.newInstance(recordID));
        fragments.add(GxysxqzgjlFragment.newInstance(recordID));
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
        activityGxysxqNoScrollViewPager.setAdapter(adapter);
        activityGxysxqNoScrollViewPager.setOffscreenPageLimit(3);
        activityGxysxqNoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    activityGxysxqRadioGroup.check(R.id.activity_gxysxq_RadioButton1);
                } else if (position == 1) {
                    activityGxysxqRadioGroup.check(R.id.activity_gxysxq_RadioButton2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        activityGxysxqRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.activity_gxysxq_RadioButton1) {
                    activityGxysxqNoScrollViewPager.setCurrentItem(0, false);
                } else if (checkedId == R.id.activity_gxysxq_RadioButton2) {
                    activityGxysxqNoScrollViewPager.setCurrentItem(1, false);
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

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


}
