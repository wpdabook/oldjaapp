package com.a21zhewang.constructionapp.ui.tab;

import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class Act_CustomTab extends Act_BaseTabImpl implements View.OnClickListener {

    public int selectId = R.id.img_a;
    public int selectColorId = R.drawable.tab_icon_a;
    private TextView textview1;
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;

    /**
     * 服务器返回值
     * 修改元素个数可查看效果-根据元素个数动态创建相等数量菜单，加载指定Activity
     */
    private ArrayList<String> statisticsList = new ArrayList<>();
//    private ArrayList<String> statisticsList = new ArrayList<>(Arrays.asList("1","2","4"));
    /**
     * 显示的四个Activity的class
     */
    private Class<?> activities[] = {Act_Focus.class, Act_RankDetail.class, Act_Inspection.class, Act_Rank.class};

    /**
     * 菜单的三个LinearLayout
     */
    private LinearLayout [] layouts;

    /**
     * 当前的选择
     */
    private int currentSelect = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_custom_tab);
        setContainer(R.id.fl_container);
        initView();
    }
    public void initView(){
//        statisticsList = getIntent().getStringArrayListExtra("statisticsList");
        getIndexModulDate();
        layouts= new LinearLayout[statisticsList.size()];
        textview1 = (TextView) findViewById(R.id.text_a);
        textview2 = (TextView) findViewById(R.id.text_b);
        textview3 = (TextView) findViewById(R.id.text_c);
        textview4 = (TextView) findViewById(R.id.text_d);
        initFirstActivityDate(statisticsList.get(0));
    }

    /**
     * 步骤一：默认加载首项(动态数据绑定的Activity页）
     */
    public void initFirstActivityDate(String str){

        if ("1".equals(str)) {
            initViewVisibility(0);
        }
        if ("2".equals(str)) {
            initViewVisibility(1);
        }
        if ("3".equals(str)) {
            initViewVisibility(2);
        }
        if ("4".equals(str)) {
            initViewVisibility(3);
        }
    }

    /**
     * 步骤二：默认加载首项(动态数据绑定的Activity页默认选中的背景图标）
     * @param i
     */
    public void initViewVisibility(int i){
        showActivity(activities[i]);
        if(i == 0){
            setBtnBackground(R.id.img_a, R.drawable.tab_icon_a_pressed, R.drawable.tab_icon_a);
        }else if(i == 1){
            setBtnBackground(R.id.img_b, R.drawable.tab_icon_b_pressed, R.drawable.tab_icon_b);
        }else if(i == 2){
            setBtnBackground(R.id.img_c, R.drawable.tab_icon_c_pressed, R.drawable.tab_icon_c);
        }else if(i == 3){
            setBtnBackground(R.id.img_d, R.drawable.tab_icon_d_pressed, R.drawable.tab_icon_d);
        }
        bindLinearLayoutAndOnClick();
    }

    /**
     * 步骤三：根据服务端数据与已知LinearLayout布局动态创建View，并添加点击事件
     */
    public void bindLinearLayoutAndOnClick(){
        for (int i = 0; i < statisticsList.size(); i++) {
            for (int j = 0; j < layouts.length; j++) {
                if ("1".equals(statisticsList.get(i))) {
                    layouts[j] = (LinearLayout) findViewById(R.id.footer_a);
                    layouts[j].setVisibility(View.VISIBLE);
                    layouts[j].setOnClickListener(this);
                }
                if ("2".equals(statisticsList.get(i))) {
                    layouts[j] = (LinearLayout) findViewById(R.id.footer_b);
                    layouts[j].setVisibility(View.VISIBLE);
                    layouts[j].setOnClickListener(this);
                }
                if ("3".equals(statisticsList.get(i))) {
                    layouts[j] = (LinearLayout) findViewById(R.id.footer_c);
                    layouts[j].setVisibility(View.VISIBLE);
                    layouts[j].setOnClickListener(this);
                }
                if ("4".equals(statisticsList.get(i))) {
                    layouts[j] = (LinearLayout) findViewById(R.id.footer_d);
                    layouts[j].setVisibility(View.VISIBLE);
                    layouts[j].setOnClickListener(this);
                }
            }
        }
    }

    /**
     * 动态布局点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.footer_a:
                //currentSelect = 0;
                initViewVisibility(0);
                break;
            case R.id.footer_b:
                initViewVisibility(1);
                break;
            case R.id.footer_c:
                initViewVisibility(2);
                break;
            case R.id.footer_d:
                initViewVisibility(3);
                break;
        }
    }
    /**
     * 附二：按钮背景处理
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
                textview1.setTextColor(getResources().getColor(R.color.tilebar_color));
                textview2.setTextColor(getResources().getColor(R.color.foot_color));
                textview3.setTextColor(getResources().getColor(R.color.foot_color));
                textview4.setTextColor(getResources().getColor(R.color.foot_color));
                break;
            case R.id.img_b:
                textview1.setTextColor(getResources().getColor(R.color.foot_color));
                textview2.setTextColor(getResources().getColor(R.color.tilebar_color));
                textview3.setTextColor(getResources().getColor(R.color.foot_color));
                textview4.setTextColor(getResources().getColor(R.color.foot_color));
                break;
            case R.id.img_c:
                textview1.setTextColor(getResources().getColor(R.color.foot_color));
                textview2.setTextColor(getResources().getColor(R.color.foot_color));
                textview3.setTextColor(getResources().getColor(R.color.tilebar_color));
                textview4.setTextColor(getResources().getColor(R.color.foot_color));
                break;
            case R.id.img_d:
                textview1.setTextColor(getResources().getColor(R.color.foot_color));
                textview2.setTextColor(getResources().getColor(R.color.foot_color));
                textview3.setTextColor(getResources().getColor(R.color.foot_color));
                textview4.setTextColor(getResources().getColor(R.color.tilebar_color));
                break;
            default:
                break;
        }
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
