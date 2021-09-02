package com.a21zhewang.constructionapp.ui.menu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseLazyFragment;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.ui.tab.TotalRankFragment;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wp-pc on 2020/5/6.
 */

public class MenuRankFragment extends BaseLazyFragment{

    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private String mTitle;
    private Dialog loadingDialog;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> tabTitles = new ArrayList<>(Arrays.asList("考核排名","重点检查排名"));
    private List<String> tabId = new ArrayList<>(Arrays.asList("1","4"));
    private FragmentPagerAdapter pagerAdapter;
    public static MenuRankFragment getInstance(String title) {
        MenuRankFragment sf = new MenuRankFragment();
        sf.mTitle = title;
        return sf;
    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View v = inflater.inflate(R.layout.act_rank, null);
        ButterKnife.bind(this,v);
        title.setText(mTitle);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        viewpager.setOffscreenPageLimit(3);
        return v;
    }

    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                getActivity().finish();
            }
        });
        pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

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
                ((TotalRankFragment) (fragmentList.get(position))).initData();
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
    protected void loadData() {
        for(int i=0;i<tabId.size();i++){
            TotalRankFragment fragment = new TotalRankFragment();
            Bundle b = new Bundle();
            b.putString("id", tabId.get(i).toString());
            fragment.setArguments(b);
            fragmentList.add(fragment);
        }
        mIndicator.setTabItemTitles(tabTitles);
        pagerAdapter.notifyDataSetChanged();
        mIndicator.setViewPager(viewpager, 0);
        ((TotalRankFragment) fragmentList.get(0)).initData();
    }
}
