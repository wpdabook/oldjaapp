package com.a21zhewang.constructionapp.ui.check;

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
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.receiver.PushMessageReceiver;
import com.a21zhewang.constructionapp.ui.IndexActivity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcFragment;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
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
public class Act_Procedural extends BaseActivity {
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
    private List<TypeList> typeLists = new ArrayList<>();
    private List<StatusList> statusLists = new ArrayList<>();
    @BindView(R.id.SearchView_top_bar)
    SearchView SearchView_top;
    private String keyword="";
    private boolean isAddSendButton = false;

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
        return R.layout.act_procedural;
    }

    @Override
    public void initViews() {
        title.setText("程序检查");
        viewpager.setOffscreenPageLimit(2);
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals("ProgExaminationAddBtn")) {
                    mIndicator.setVisibility(View.VISIBLE);
                    isAddSendButton = true;
                }
            } }
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                keyword = edittext;
                setKeyWord(keyword);
                ((ProceduralFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
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
                ((ProceduralFragment) (fragmentList.get(position))).refresh();
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
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "progStatus" + "\"}");
        XUtil.postjsondata(object, "ProgExaminationType", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    titlebean = JsonUtils.getbean(res,ProceduralTitle.class);
                    typeLists = titlebean.getTypeList();
                    statusLists = titlebean.getStatusList();
                    for(int i=0;i<typeLists.size();i++){
                        ProceduralFragment fragment = new ProceduralFragment();
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
                    ((ProceduralFragment) fragmentList.get(0)).initData();
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
        MobclickAgent.onResume(this);
        if (0 != PushMessageReceiver.count) {
            //角标清空
            PushMessageReceiver.count = 0;
            AppShortCutUtil.setCount(PushMessageReceiver.count, Act_Procedural.this);
        }
        if (fragmentList.size() > 0) {
            ((ProceduralFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
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
