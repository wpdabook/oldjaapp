package com.a21zhewang.constructionapp.ui.tab;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.IndexItem;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.constructionapp.customview.MyViewPager;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 2：自定义ViewPager + Activity[Tab] 固定菜单（非动态）
 * Created by WP-PC on 2019/8/12.
 */

public class Act_Tab2 extends ActivityGroup implements View.OnClickListener {
    private MyViewPager viewPager;
    private List<View> viewList;
    public int selectId = R.id.img_a;
    public int selectColorId = R.drawable.tab_icon_a;
    private TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;
    private LinearLayout focuslinear;
    private LinearLayout inspectionlinear;
    private LinearLayout ranklinear;
    private LinearLayout paimingliner;
    private TextView title;
    private ImageView back;
    private IndexItem item;
    private ArrayList<String> statisticsList = new ArrayList<>();
    private LinearLayout tab_footer_layout;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_table);
        getFindViews();
        initViews();
        setListerner();
    }

    public void initViews() {
        getIndexModulDate();
        tab_footer_layout = (LinearLayout) findViewById(R.id.tab_footer_layout);
//        statisticsList = getIntent().getStringArrayListExtra("statisticsList");
        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        viewList = new ArrayList<View>();
        Intent intent1 = new Intent(Act_Tab2.this, Act_Focus.class);
        Intent intent2 = new Intent(Act_Tab2.this, Act_RankDetail.class);
        Intent intent3 = new Intent(Act_Tab2.this, Act_Inspection.class);
        Intent intent4 = new Intent(Act_Tab2.this, Act_Rank.class);
        for(int i=0;i<statisticsList.size();i++){
            String str = statisticsList.get(i);
            if("1".equals(str)){
                focuslinear.setVisibility(View.VISIBLE);
                viewList.add(getLocalActivityManager().startActivity("a", intent1).getDecorView());
            }
            if("2".equals(str)){
                paimingliner.setVisibility(View.VISIBLE);
                viewList.add(getLocalActivityManager().startActivity("b", intent2).getDecorView());
            }
            if("3".equals(str)){
                inspectionlinear.setVisibility(View.VISIBLE);
                viewList.add(getLocalActivityManager().startActivity("c", intent3).getDecorView());
            }
            if("4".equals(str)){
                ranklinear.setVisibility(View.VISIBLE);
                viewList.add(getLocalActivityManager().startActivity("d", intent4).getDecorView());
            }
        }
//        viewList.add(getLocalActivityManager().startActivity("b", intent2).getDecorView());
//        viewList.add(getLocalActivityManager().startActivity("c", intent3).getDecorView());
//        viewList.add(getLocalActivityManager().startActivity("d", intent4).getDecorView());
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        setBtnBackground(R.id.img_a, R.drawable.tab_icon_a_pressed, R.drawable.tab_icon_a);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                switch (arg0) {
                    case 0:
                        setBtnBackground(R.id.img_a, R.drawable.tab_icon_a_pressed, R.drawable.tab_icon_a);
                        break;
                    case 1:
                        setBtnBackground(R.id.img_b, R.drawable.tab_icon_b_pressed, R.drawable.tab_icon_b);
                        break;
                    case 2:
                        setBtnBackground(R.id.img_c, R.drawable.tab_icon_c_pressed, R.drawable.tab_icon_c);
                        break;
                    case 3:
                        setBtnBackground(R.id.img_d, R.drawable.tab_icon_d_pressed, R.drawable.tab_icon_d);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

        });
    }
    /**
     * 按钮背景处理
     */

    public void setBtnBackground(int sltId, int sltColorId, int originalBgId) {
        // 取消上一个选中
        findViewById(selectId).setBackgroundResource(selectColorId);
        // 完成这次选中
        findViewById(sltId).setBackgroundResource(sltColorId);
        selectId = sltId;
        selectColorId = originalBgId;

        switch (sltId) {
            case R.id.img_a:
                title.setText("重点关注");
                textview1.setTextColor(getResources().getColor(R.color.tilebar_color));
                textview2.setTextColor(getResources().getColor(R.color.foot_color));
                textview3.setTextColor(getResources().getColor(R.color.foot_color));
                textview4.setTextColor(getResources().getColor(R.color.foot_color));
                break;
            case R.id.img_b:
                title.setText(PublicUtils.getspstring("rankProjectName0"));
                textview1.setTextColor(getResources().getColor(R.color.foot_color));
                textview2.setTextColor(getResources().getColor(R.color.tilebar_color));
                textview3.setTextColor(getResources().getColor(R.color.foot_color));
                textview4.setTextColor(getResources().getColor(R.color.foot_color));
                break;
            case R.id.img_c:
                title.setText("检查情况");
                textview1.setTextColor(getResources().getColor(R.color.foot_color));
                textview2.setTextColor(getResources().getColor(R.color.foot_color));
                textview3.setTextColor(getResources().getColor(R.color.tilebar_color));
                textview4.setTextColor(getResources().getColor(R.color.foot_color));
                break;
            case R.id.img_d:
                title.setText("综合排名");
                textview1.setTextColor(getResources().getColor(R.color.foot_color));
                textview2.setTextColor(getResources().getColor(R.color.foot_color));
                textview3.setTextColor(getResources().getColor(R.color.foot_color));
                textview4.setTextColor(getResources().getColor(R.color.tilebar_color));
                break;
            default:
                break;
        }
    }
    public void getFindViews(){
        title = (TextView)findViewById(R.id.title_TB_name);
        back = (ImageView)findViewById(R.id.title_back_img);
        textview1 = (TextView) findViewById(R.id.tab_text_a);
        textview2 = (TextView) findViewById(R.id.tab_text_b);
        textview3 = (TextView) findViewById(R.id.tab_text_c);
        textview4 = (TextView) findViewById(R.id.tab_text_d);

        focuslinear = (LinearLayout)findViewById(R.id.tab_a);
        paimingliner = (LinearLayout)findViewById(R.id.tab_b);
        inspectionlinear = (LinearLayout)findViewById(R.id.tab_c);
        ranklinear = (LinearLayout)findViewById(R.id.tab_d);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //重点关注
        if (id == R.id.tab_a) {
            viewPager.setCurrentItem(0, true);
        }
        //项目情况
        else if (id == R.id.tab_b) {
            viewPager.setCurrentItem(1, true);
        }//检查情况
        else if (id == R.id.tab_c) {
            viewPager.setCurrentItem(2, true);
        }//排名
        else if (id == R.id.tab_d) {
            viewPager.setCurrentItem(3, true);
        }
    }
    private void setListerner(){
       focuslinear.setOnClickListener(this);
       paimingliner.setOnClickListener(this);
       inspectionlinear.setOnClickListener(this);
       ranklinear.setOnClickListener(this);
       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_Tab2.this.finish();
            }
        });
    }

    public void getIndexModulDate(){
        String one = PublicUtils.getspstring("tab_tag_one");
        String two = PublicUtils.getspstring("tab_tag_two");
        String third = PublicUtils.getspstring("tab_tag_third");
        String four = PublicUtils.getspstring("tab_tag_four");
        String five = PublicUtils.getspstring("tab_tag_five");
        String six = PublicUtils.getspstring("tab_tag_six");
        if(!TextUtils.isEmpty(one)){
            statisticsList.add(one);
        }
        if(!TextUtils.isEmpty(two)){
            statisticsList.add(two);
        }
        if(!TextUtils.isEmpty(third)){
            statisticsList.add(third);
        }
        if(!TextUtils.isEmpty(four)){
            statisticsList.add(four);
        }
        if(!TextUtils.isEmpty(five)){
            statisticsList.add(five);
        }
        if(!TextUtils.isEmpty(six)){
            statisticsList.add(six);
        }
    }
}
