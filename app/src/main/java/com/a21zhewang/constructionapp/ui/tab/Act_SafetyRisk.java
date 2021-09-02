package com.a21zhewang.constructionapp.ui.tab;

import android.app.Dialog;
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
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.SafeLevel;
import com.a21zhewang.constructionapp.bean.SafeRank;
import com.a21zhewang.constructionapp.bean.SaveStatus;
import com.a21zhewang.constructionapp.bean.StatusList;
import com.a21zhewang.constructionapp.bean.TotalLevel;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 安全风险-详情
 */
public class Act_SafetyRisk extends BaseActivity {
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
    List<JSONArray> arrays = new ArrayList<>();
    private FragmentPagerAdapter pagerAdapter;
    private  int  level = 0;
    private SafeLevel levelbean;
    private List<SaveStatus> safeList = new ArrayList<>();
    private Dialog loadingDialog;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_safetyrisk;
    }

    @Override
    public void initViews() {
        title.setText("安全风险");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        viewpager.setOffscreenPageLimit(2);
        level = getIntent().getIntExtra("level",0);
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
                ((SafetyRankFragment) (fragmentList.get(position))).initData();
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
        JSONObject object = new JSONObject();
        loadingDialog.show();
        XUtil.postjsondata(object, "GetDataTotal2_rectProjectRectLevelTotal", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        levelbean = JsonUtils.getbean(object.toString(),SafeLevel.class);
                        safeList = levelbean.getTotalLevel();
                        tabTitles.add(object.optString("statusName"));
                        SafetyRankFragment fragment = new SafetyRankFragment();
                        Bundle b = new Bundle();
                        b.putString("statusName",object.optString("statusName"));
                        b.putInt("dateType",2); //月
                        b.putParcelableArrayList("safeList", (ArrayList<? extends Parcelable>) safeList);
                        b.putInt("level",level);
                        fragment.setArguments(b);
                        fragmentList.add(fragment);
                    }
                    mIndicator.setTabItemTitles(tabTitles);
                    pagerAdapter.notifyDataSetChanged();
                    mIndicator.setViewPager(viewpager, 0);
                    ((SafetyRankFragment) fragmentList.get(0)).initData();
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
//        XUtil.postjsondata(object, "GetDataTotal2_rectProjectRectLevelTotal", new MyCallBack() {
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResule(String res) {
//                try {
//                    JSONArray array = new JSONArray(res);
//                    for(int i=0;i<array.length();i++){
//                        JSONObject object = array.optJSONObject(i);
//                        tabTitles.add(object.optString("statusName"));
//                        arrays.add(object.optJSONArray("totalLevel"));
//                        int num = object.optJSONArray("totalLevel").length();
//                        SafetyRankFragment fragment = new SafetyRankFragment();
//                        Bundle b = new Bundle();
//                        b.putString("statusName", object.optString("statusName"));
//                        b.putInt("levelTotalNum",num);
//                        b.putInt("level",level);
//                        fragment.setArguments(b);
//                        fragmentList.add(fragment);
//                    }
//                    mIndicator.setTabItemTitles(tabTitles);
//                    pagerAdapter.notifyDataSetChanged();
//                    mIndicator.setViewPager(viewpager, 0);
//                    ((SafetyRankFragment) fragmentList.get(0)).initData();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFail(ObjBean getbean) {
//
//            }
//
//            @Override
//            public void onWrong() {
//
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NEEDREFRESH && fragmentList.size() > 0) {
            ((SafetyRankFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
            NEEDREFRESH = false;
        }
    }

    @Override
    public void processClick(View v) {

    }
}
