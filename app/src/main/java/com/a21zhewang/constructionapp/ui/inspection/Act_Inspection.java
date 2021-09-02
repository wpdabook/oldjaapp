package com.a21zhewang.constructionapp.ui.inspection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralTitle;
import com.a21zhewang.constructionapp.bean.StatusList;
import com.a21zhewang.constructionapp.bean.TypeList;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 安全风险-详情
 */
public class Act_Inspection extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.lin_add_project)
    RelativeLayout lin_add_project;
    public static boolean NEEDREFRESH;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    List<String> tabTitles = new ArrayList<>();
    private ProceduralTitle titlebean;
    private FragmentPagerAdapter pagerAdapter;
    private List<TypeList> typeLists = new ArrayList<>();
    private List<StatusList> statusLists = new ArrayList<>();

    @BindView(R.id.SearchView_top_bar)
    SearchView SearchView_top;
    private String keyword="";
    private boolean isAddSendButton = false;
    private boolean HiddentTitleTag = true;

    @Override
    public void beforesetContentView() {

    }
    public String getKeyWord() {
        return keyword;
    }

    public void setKeyWord(String keyword) {
        this.keyword = keyword;
    }
    @Override
    public int getLayoutId() {
        return R.layout.act_inspection_layout;
    }

    @Override
    public void initViews() {
        title.setText("专项巡查");
        viewpager.setOffscreenPageLimit(2);
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        HiddentTitleTag = getIntent().getBooleanExtra("HiddentTitleTag",true);
        if(HiddentTitleTag){
            if (buttons!=null&&buttons.size()>0){
                for (ButtonBean btn : buttons) {
                    if (btn.getBtnID().equals("BroadCastExaminationAddBtn")) {
                        mIndicator.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        isAddSendButton = true;
                    }
                } }
        }else {
            isAddSendButton = false;
            mIndicator.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
        }
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                keyword = edittext;
                setKeyWord(keyword);
                ((InspectionFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Inspection.this,Act_AddInspection2.class));
            }
        });
        lin_add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Inspection.this,ProjectRegisterActivity.class));
            }
        });
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
                ((InspectionFragment) (fragmentList.get(position))).refresh();
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
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "BrodCastStatus" + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    titlebean = JsonUtils.getbean(res,ProceduralTitle.class);
                    typeLists = titlebean.getTypeList();
                    statusLists = titlebean.getStatusList();
                    for(int i=0;i<typeLists.size();i++){
                        InspectionFragment fragment = new InspectionFragment();
                        tabTitles.add(typeLists.get(i).getStatusName());
                        Bundle b = new Bundle();
                        b.putParcelableArrayList("typeLists", (ArrayList<? extends Parcelable>) typeLists);
                        b.putParcelableArrayList("statusLists", (ArrayList<? extends Parcelable>) statusLists);
                        b.putString("statusName", typeLists.get(i).getStatusName());
                        if(isAddSendButton){
                            b.putString("status",typeLists.get(i).getStatus());
                        }else {
                            b.putString("status","3"); //默认检查记录
                        }
                        b.putString("keyword",keyword);
                        b.putInt("type",0);
                        fragment.setArguments(b);
                        fragmentList.add(fragment);
                    }
                    mIndicator.setTabItemTitles(tabTitles);
                    pagerAdapter.notifyDataSetChanged();
                    mIndicator.setViewPager(viewpager, 0);
                    ((InspectionFragment) fragmentList.get(0)).initData();
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (fragmentList.size() > 0) {
            ((InspectionFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void processClick(View v) {

    }
}
