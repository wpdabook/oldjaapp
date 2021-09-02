package com.a21zhewang.constructionapp.ui.tab;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
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
 * Created by WP-PC on 2019/8/12.
 */

public class Act_RiskHiddenDanger extends BaseActivity {
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
    private ArrayList<String> tabTitles = new ArrayList<>(Arrays.asList("安全检查","质量隐患","文明施工"));
//    List<String> tabId = new ArrayList<>(Arrays.asList("1","2","3"));
    List<Integer> tabId = new ArrayList<>(Arrays.asList(1,2,3));
    private FragmentPagerAdapter pagerAdapter;
    private String projectId;
    private String projectName;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_riskhiddendanger;
    }

    @Override
    public void initViews() {
        projectId = getIntent().getStringExtra("projectId");
        projectName = getIntent().getStringExtra("projectName");
        viewpager.setOffscreenPageLimit(3);
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
                ((RiskHiddenDangerFragment) (fragmentList.get(position))).initData();
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
        for(int i=0;i<tabId.size();i++){
            RiskHiddenDangerFragment fragment = new RiskHiddenDangerFragment();
            Bundle b = new Bundle();
            b.putInt("id", tabId.get(i));
            b.putString("projectId", projectId);
            fragment.setArguments(b);
            fragmentList.add(fragment);
        }
        mIndicator.setTabItemTitles(tabTitles);
        pagerAdapter.notifyDataSetChanged();
        mIndicator.setViewPager(viewpager, 0);
        ((RiskHiddenDangerFragment) fragmentList.get(0)).initData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (NEEDREFRESH && fragmentList.size() > 0) {
            ((RiskHiddenDangerFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
            NEEDREFRESH = false;
        }
    }
    @Override
    public void processClick(View v) {

    }
}
