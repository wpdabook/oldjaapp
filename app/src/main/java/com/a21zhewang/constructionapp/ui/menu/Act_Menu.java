package com.a21zhewang.constructionapp.ui.menu;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.TabEntity;
import com.a21zhewang.constructionapp.bean.ViewFindUtils;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;
import java.util.ArrayList;
import java.util.Random;

public class Act_Menu extends BaseActivity{
    private ViewPager mViewPager;
    private View mDecorView;
    private CommonTabLayout commonTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconSelectIds = {R.drawable.tab_icon_a_pressed, R.drawable.tab_icon_b_pressed, R.drawable.tab_icon_c_pressed, R.drawable.tab_icon_d_pressed,R.drawable.tab_icon_e_pressed,R.drawable.tab_icon_f_pressed};
    private int[] mIconNoSelectIds = {R.drawable.tab_icon_a, R.drawable.tab_icon_b, R.drawable.tab_icon_c, R.drawable.tab_icon_d, R.drawable.tab_icon_e,R.drawable.tab_icon_f};
    private String[] mTitles = {"基础数据","重点关注", "项目情况", "检查情况", "综合排名","疫情复工"};
    private static int sOffScreenLimit = 0; //默认不设置数量的情况下预加载下一页。设置0和1是同样的效果
    private Random mRandom = new Random();

    private String areaKey;

    public String getAreaKey() {
        return areaKey;
    }

    public void setAreaKey(String title) {
        this.areaKey = title;
    }
    @Override
    public void initViews(){
        getIndexModulDate();
        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.viewpager);
        commonTabLayout = ViewFindUtils.find(mDecorView, R.id.tabview);
        sOffScreenLimit = mTitles.length - 1;
        overridePendingTransition(0,0);
//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconNoSelectIds[i]));
//        }
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        if(sOffScreenLimit >1){
            mViewPager.setOffscreenPageLimit(sOffScreenLimit);
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_menu;
    }

    public void initListener(){
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
//                if (position == 0) {
//                    commonTabLayout.showMsg(0, mRandom.nextInt(100) + 1);
//                }
            }
        });
        //setReadSign();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }
    public void initData(){

    }

    /**
     * 设置菜单显示-未读消息
     */
    public void setReadSign(){

        //显示未读红点
        commonTabLayout.showDot(2);
//
//        //两位数
        commonTabLayout.showMsg(0, 55);
        commonTabLayout.setMsgMargin(0, -20, 5);
//
//        //三位数
        commonTabLayout.showMsg(1, 100);
        commonTabLayout.setMsgMargin(1, -20, 5);
//
//        //设置未读消息红点
        commonTabLayout.showDot(2);
        MsgView rtv_2_2 = commonTabLayout.getMsgView(2);
        if (rtv_2_2 != null) {
            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
        }

        //设置未读消息背景
        commonTabLayout.showMsg(3, 5);
        commonTabLayout.setMsgMargin(3, -15, 5);
        MsgView rtv_2_3 = commonTabLayout.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
    protected int dp2px(float dp) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void beforesetContentView() {

    }
    public void getIndexModulDate(){
        String one = PublicUtils.getspstring("tab_tag_one");
        String two = PublicUtils.getspstring("tab_tag_two");
        String third = PublicUtils.getspstring("tab_tag_third");
        String four = PublicUtils.getspstring("tab_tag_four");
        String five = PublicUtils.getspstring("tab_tag_five");
        String six = PublicUtils.getspstring("tab_tag_six");
        if(!TextUtils.isEmpty(one)){
            mFragments.add(CityStationFragment.getInstance("基础数据"));
            mTabEntities.add(new TabEntity(mTitles[0], mIconSelectIds[0], mIconNoSelectIds[0]));
        }
        if(!TextUtils.isEmpty(two)){
            mFragments.add(FocusFragment.getInstance("重点关注"));
            mTabEntities.add(new TabEntity(mTitles[1], mIconSelectIds[1], mIconNoSelectIds[1]));
        }
        if(!TextUtils.isEmpty(third)){
            mFragments.add(ProjectFragment.getInstance("项目情况"));
            mTabEntities.add(new TabEntity(mTitles[2], mIconSelectIds[2], mIconNoSelectIds[2]));
        }
        if(!TextUtils.isEmpty(four)){
            mFragments.add(InspectionFragment.getInstance("检查情况"));
            mTabEntities.add(new TabEntity(mTitles[3], mIconSelectIds[3], mIconNoSelectIds[3]));
        }
        if(!TextUtils.isEmpty(five)){
            mFragments.add(MenuRankFragment.getInstance("综合排名"));
            mTabEntities.add(new TabEntity(mTitles[4], mIconSelectIds[4], mIconNoSelectIds[4]));
        }
        if(!TextUtils.isEmpty(six)){
            mFragments.add(YiQingFragment.getInstance("疫情复工"));
            mTabEntities.add(new TabEntity(mTitles[5], mIconSelectIds[5], mIconNoSelectIds[5]));
        }
    }
}
