package com.a21zhewang.constructionapp.ui.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WP-PC on 2019/8/12.
 */

public class Act_RankMoreDetail extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public static boolean NEEDREFRESH;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
//    List<String> tabTitles = new ArrayList<>(Arrays.asList("考核排名","企业排名","人员排名"));
//    List<String> tabId = new ArrayList<>(Arrays.asList("1","2","3"));
    List<String> tabTitles = new ArrayList<>(Arrays.asList("考核","重点检查排名"));
    List<String> tabId = new ArrayList<>(Arrays.asList("1","4"));
    private FragmentPagerAdapter pagerAdapter;
    private String id;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_rank_more_detail;
    }

    @Override
    public void initViews() {
        viewpager.setOffscreenPageLimit(3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearspValues();
                Act_RankMoreDetail.this.finish();
            }
        });
    }
    public void initVisible(){

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
                ((RankMoreDetailFragment) (fragmentList.get(position))).initData();
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
        id = getIntent().getStringExtra("id");
        if("4".equals(id)){
            title.setText("重点检查排名");
        }
        if("5".equals(id)){
            title.setText("整改率排名");
        }
        if("6".equals(id)){
            title.setText("人员参与率排名");
        }
        RankMoreDetailFragment fragment = new RankMoreDetailFragment();
        Bundle b = new Bundle();
        b.putString("id", id);
        fragment.setArguments(b);
        fragmentList.add(fragment);
        mIndicator.setTabItemTitles(tabTitles);
        pagerAdapter.notifyDataSetChanged();
        mIndicator.setViewPager(viewpager, 0);
        ((RankMoreDetailFragment) fragmentList.get(0)).initData();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (NEEDREFRESH && fragmentList.size() > 0) {
//            ((RankMoreDetailFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
//            NEEDREFRESH = false;
//        }
//    }
    @Override
    public void processClick(View v) {

    }
}
