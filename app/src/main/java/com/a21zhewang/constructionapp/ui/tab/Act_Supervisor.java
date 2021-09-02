package com.a21zhewang.constructionapp.ui.tab;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralTitle;
import com.a21zhewang.constructionapp.bean.StatusList;
import com.a21zhewang.constructionapp.bean.TypeList;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 单项目-监理施工详情
 */
public class Act_Supervisor extends BaseActivity {
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
    List<String> tabTitles = new ArrayList<>();
    private ProceduralTitle titlebean;
    private FragmentPagerAdapter pagerAdapter;
    private String projectId;
    private int type = 0; //1：建设  2：施工 3：监理
    private List<TypeList> typeLists = new ArrayList<>();
    private List<StatusList> statusLists = new ArrayList<>();

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_supervisor;
    }

    @Override
    public void initViews() {
        type = getIntent().getIntExtra("type",0);
        if(type == 1){
            title.setText("建设单位人员使用情况");
        }
        if(type == 2){
            title.setText("施工单位人员使用情况");
        }
        if(type == 3){
            title.setText("监理单位人员使用情况");
        }
        projectId = getIntent().getStringExtra("projectId");
        viewpager.setOffscreenPageLimit(2);
        back.setOnClickListener(new View.OnClickListener() {
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
                if(fragmentList.size()>0){
                    ((SupervisorFragment) (fragmentList.get(position))).initData();
                }
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
        JSONObject object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"type\":\"" + type + "\"}");
        XUtil.postjsondata(object, "GetDataTotal2_rectProjectdangerTotal_EtpList", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("etpList");
                    if(array.length() == 0){
                        mIndicator.setVisibility(View.GONE);
                    }else {
                        mIndicator.setVisibility(View.VISIBLE);
                        for(int i=0;i<array.length();i++){
                            JSONObject tempobject = array.optJSONObject(i);
                            tabTitles.add(tempobject.optString("etpShortName"));
                            SupervisorFragment fragment = new SupervisorFragment();
                            Bundle b = new Bundle();
                            b.putString("etpId",tempobject.optString("etpID")) ;
                            b.putString("projectId",projectId);
                            fragment.setArguments(b);
                            fragmentList.add(fragment);
                        }
                        mIndicator.setTabItemTitles(tabTitles);
                        pagerAdapter.notifyDataSetChanged();
                        mIndicator.setViewPager(viewpager, 0);
                        ((SupervisorFragment) fragmentList.get(0)).initData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
    protected void onResume() {
        super.onResume();
        if (fragmentList.size() > 0) {
            ((SupervisorFragment) fragmentList.get(viewpager.getCurrentItem())).initData();
        }
    }

    @Override
    public void processClick(View v) {

    }
}
