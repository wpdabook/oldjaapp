package com.a21zhewang.constructionapp.ui.tab;

import android.app.ActivityGroup;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.TabView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.tab.Act_Focus;
import com.a21zhewang.constructionapp.ui.tab.Act_Inspection;
import com.a21zhewang.constructionapp.ui.tab.Act_Rank;
import com.a21zhewang.constructionapp.ui.tab.Act_RankDetail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 汇总统计主界面-ViewPager+TabView+Activity动态菜单
 * Created by 56864 on 2019/11/3.
 */

public class Act_Tab1 extends ActivityGroup{
    private ViewPager mVpMain;
    private TabView tb1;
    private TabView tb2;
    private TabView tb3;
    private TabView tb4;
    private TabView tb5;
//    private List<String> mTitles = new ArrayList<>(Arrays.asList("1","3","4"));
    private List<String> mTitles = new ArrayList<>();
    private List<View> mActivitys = new ArrayList<>();
    private List<TabView> mTabs = new ArrayList<>();
    private int mCurTabPos;
    private static final String BUNDLE_KEY_POS = "bundle_key_pos";
    private LinearLayout top_title_layout;
    private LinearLayout index_lin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report2);
        if (savedInstanceState != null){
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS,0);
        }
        initViews();
    }
    public void initViews() {
             getIndexModulDate();
             mVpMain = (ViewPager) findViewById(R.id.viewpager_main);
             tb1 = (TabView)findViewById(R.id.tab_view1);
             tb2 = (TabView)findViewById(R.id.tab_view2);
             tb3 = (TabView)findViewById(R.id.tab_view3);
             tb4 = (TabView)findViewById(R.id.tab_view4);
             tb5 = (TabView)findViewById(R.id.tab_view5);

             tb1.setIconAndText(R.drawable.tab_icon_a,R.drawable.tab_icon_a_pressed,"重点关注");
             tb2.setIconAndText(R.drawable.tab_icon_b,R.drawable.tab_icon_b_pressed,"项目情况");
             tb3.setIconAndText(R.drawable.tab_icon_c,R.drawable.tab_icon_c_pressed,"检查情况");
             tb4.setIconAndText(R.drawable.tab_icon_d,R.drawable.tab_icon_d_pressed,"综合排名");
             tb5.setIconAndText(R.drawable.tab_icon_e,R.drawable.tab_icon_e_pressed,"疫情复工");

             Intent intent1 = new Intent(Act_Tab1.this, Act_Focus.class);
             Intent intent2 = new Intent(Act_Tab1.this, Act_RankDetail2.class);
             Intent intent3 = new Intent(Act_Tab1.this, Act_Inspection.class);
             Intent intent4 = new Intent(Act_Tab1.this, Act_Rank.class);
             Intent intent5 = new Intent(Act_Tab1.this, Act_YiQingTab.class);

             for(int i=0;i<mTitles.size();i++){
                 if("1".equals(mTitles.get(i))){
                     mTabs.add(tb1);
                     tb1.setVisibility(View.VISIBLE);
                     mActivitys.add(getLocalActivityManager().startActivity("a", intent1).getDecorView());
                 }
                 if("2".equals(mTitles.get(i))){
                     mTabs.add(tb2);
                     tb2.setVisibility(View.VISIBLE);
                     mActivitys.add(getLocalActivityManager().startActivity("b", intent2).getDecorView());
                 }
                 if("3".equals(mTitles.get(i))){
                     mTabs.add(tb3);
                     tb3.setVisibility(View.VISIBLE);
                     mActivitys.add(getLocalActivityManager().startActivity("c", intent3).getDecorView());
                 }
                 if("4".equals(mTitles.get(i))){
                     mTabs.add(tb4);
                     tb4.setVisibility(View.VISIBLE);
                     mActivitys.add(getLocalActivityManager().startActivity("d", intent4).getDecorView());
                 }
                 if("5".equals(mTitles.get(i))){
                     mTabs.add(tb5);
                     tb5.setVisibility(View.VISIBLE);
                     mActivitys.add(getLocalActivityManager().startActivity("e", intent5).getDecorView());
                 }
             }
             setCurrentTab(mCurTabPos);
             initListener();
             initViewPagerAdapter();
    }
    public void initViewPagerAdapter(){
        mVpMain.setOffscreenPageLimit(mTitles.size());
        mVpMain.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mActivitys.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mActivitys.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mActivitys.get(position));
                return mActivitys.get(position);
            }
        });
        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(positionOffset > 0){
                    TabView left = mTabs.get(position);
                    TabView right = mTabs.get(position + 1);
                    left.setProgress((1 - positionOffset));
                    right.setProgress(positionOffset);
                }

            }
            @Override
            public void onPageSelected(int position) {

            }
            /**
             * 用户操作时触发
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 设置选中Tab
     * @param pos
     */
    private void setCurrentTab(int pos){
        for (int i = 0; i < mTabs.size();i++){
            TabView tabView = mTabs.get(i);
            if (i == pos){
                tabView.setProgress(1);
            }else {
                tabView.setProgress(0);
            }
        }
    }

    /**
     * Tab点击事件
     */
    public void initListener() {
        for (int i = 0; i < mTabs.size();i++){
            TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVpMain.setCurrentItem(finalI,false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }
        @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(BUNDLE_KEY_POS,mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * https://www.cnblogs.com/xushihai/p/4818608.html
     * @param event
     * @return
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            PublicUtils.putspstring("tab_tag_one","");
            PublicUtils.putspstring("tab_tag_two","");
            PublicUtils.putspstring("tab_tag_third","");
            PublicUtils.putspstring("tab_tag_four","");
            PublicUtils.putspstring("tab_tag_five","");
            PublicUtils.putspstring("tab_tag_six","");
            Act_Tab1.this.finish();
//            ResolveInfo launcherResolve=queryCurrentLauncher();
//            Intent intent=new Intent();
//            intent.addCategory(Intent.ACTION_MAIN);
//            ComponentName componentName=new ComponentName(launcherResolve.activityInfo.packageName,launcherResolve.activityInfo.name);
//            intent.setComponent(componentName);
//            startActivitySafely(intent);
//            Toast.makeText(this, "要开始切换了", Toast.LENGTH_SHORT).show();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
    private void startActivitySafely(Intent intent){
        try{
            startActivity(intent);
            Toast.makeText(this, "切换到后台成功", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "切换到后台失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private ResolveInfo queryCurrentLauncher() {
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_HOME);
        launcherIntent.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> launcherInfoList = getPackageManager().queryIntentActivities(launcherIntent, PackageManager.MATCH_DEFAULT_ONLY);

        ResolveInfo launcherResolveInfo = null;
        ActivityManager activityManager = (ActivityManager) getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(30);
        Iterator<ActivityManager.RunningTaskInfo> itInfo = tasks.iterator();
        while (itInfo.hasNext()) {
            ActivityManager.RunningTaskInfo info = itInfo.next();
            for (ResolveInfo resolveInfo : launcherInfoList) {
                String name1 = info.topActivity.getClassName();
                String namme2 = resolveInfo.activityInfo.name;
                if (name1.equals(namme2)) {
                    //获取当前使用的桌面
                    launcherResolveInfo = resolveInfo;
                    return launcherResolveInfo;
                }
            }
        }
        return launcherResolveInfo;
    }
    public void getIndexModulDate(){
        top_title_layout = (LinearLayout)findViewById(R.id.top_title_layout);
        index_lin =  (LinearLayout)findViewById(R.id.index_lin);
        top_title_layout.setVisibility(View.VISIBLE);
        index_lin.setVisibility(View.GONE);
        String one = PublicUtils.getspstring("tab_tag_one");
        String two = PublicUtils.getspstring("tab_tag_two");
        String third = PublicUtils.getspstring("tab_tag_third");
        String four = PublicUtils.getspstring("tab_tag_four");
        String five = PublicUtils.getspstring("tab_tag_five");
        if(!TextUtils.isEmpty(one)){
            mTitles.add(one);
            index_lin.setVisibility(View.VISIBLE);
            top_title_layout.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(two)){
            mTitles.add(two);
            index_lin.setVisibility(View.VISIBLE);
            top_title_layout.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(third)){
            mTitles.add(third);
            index_lin.setVisibility(View.VISIBLE);
            top_title_layout.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(four)){
            mTitles.add(four);
            index_lin.setVisibility(View.VISIBLE);
            top_title_layout.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(five)){
            mTitles.add(five);
            index_lin.setVisibility(View.VISIBLE);
            top_title_layout.setVisibility(View.GONE);
        }
    }
}
