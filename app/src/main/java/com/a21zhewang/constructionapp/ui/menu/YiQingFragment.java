package com.a21zhewang.constructionapp.ui.menu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.TabFragmentPagerAdapter;
import com.a21zhewang.constructionapp.base.BaseLazyFragment;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.ui.tab.Act_ReportProject;
import com.a21zhewang.constructionapp.ui.tab.Act_YiQingTab;
import com.a21zhewang.constructionapp.ui.tab.DangerFragment;
import com.a21zhewang.constructionapp.ui.tab.EquipmentInfoFragment;
import com.a21zhewang.constructionapp.ui.tab.ManagementNumberFragment;
import com.a21zhewang.constructionapp.ui.tab.MechanicalEquipmentFragment;
import com.a21zhewang.constructionapp.ui.tab.MemberInfoFragment;
import com.a21zhewang.constructionapp.ui.tab.StartAndResumeFragment;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wp-pc on 2020/5/6.
 */

public class YiQingFragment extends BaseLazyFragment {

    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.tv_one_number)
    TextView tv_one_number;
    @BindView(R.id.tv_two_number)
    TextView tv_two_number;
    @BindView(R.id.tv_three_number)
    TextView tv_three_number;
    @BindView(R.id.tv_four_number)
    TextView tv_four_number;
    @BindView(R.id.tv_one)
    TextView tv_one;
    @BindView(R.id.tv_two)
    TextView tv_two;
    @BindView(R.id.tv_three)
    TextView tv_three;
    @BindView(R.id.tv_four)
    TextView tv_four;
    @BindView(R.id.lin_one)
    LinearLayout lin_one;
    @BindView(R.id.lin_two)
    LinearLayout lin_two;
    @BindView(R.id.lin_three)
    LinearLayout lin_three;
    @BindView(R.id.lin_four)
    LinearLayout lin_four;
    @BindView(R.id.lin_five)
    LinearLayout lin_five;
    @BindView(R.id.lin_six)
    LinearLayout lin_six;
    @BindView(R.id.yq_top_viewpager)
    ViewPager TopViewPager;
    @BindView(R.id.yq_list)
    ListView listView;
    @BindView(R.id.yq__bottom_viewpager)
    ViewPager BottomViewpager;
    private TabFragmentPagerAdapter TopAdapter;
    private TabFragmentPagerAdapter bottomAdapter;
    private List<Fragment> list;
    private List<Fragment> bottomlist;
    private JSONArray dataArray = new JSONArray();
    private MyBaseAdapter listadapter;
    private String mTitle;
    private Dialog loadingDialog;
    private static int sOffScreenLimit = 0; //默认不设置数量的情况下预加载下一页。设置0和1是同样的效果


    public static YiQingFragment getInstance(String title) {
        YiQingFragment sf = new YiQingFragment();
        sf.mTitle = title;
        return sf;
    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View v = inflater.inflate(R.layout.act_yiqingtab_layout, null);
        ButterKnife.bind(this,v);
        title.setText(mTitle);
        sOffScreenLimit = 2;
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
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
        //绑定点击事件
        TopViewPager.setOnPageChangeListener(new MyPagerChangeListener()) ;
        BottomViewpager.setOnPageChangeListener(new MyBottomPagerChangeListener()) ;
        list = new ArrayList<>();
        bottomlist = new ArrayList<>();
        list.add(new StartAndResumeFragment2() );
        list.add(new ManagementNumberFragment2() );
        list.add(new MechanicalEquipmentFragment2());
        bottomlist.add(new MemberInfoFragment2());
        bottomlist.add(new EquipmentInfoFragment2());
        bottomlist.add(new DangerFragment2());
        TopAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), list);
        bottomAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), bottomlist);
        if(sOffScreenLimit >1){
            TopViewPager.setOffscreenPageLimit(sOffScreenLimit);
            BottomViewpager.setOffscreenPageLimit(sOffScreenLimit);
        }
        TopViewPager.setAdapter(TopAdapter);
        BottomViewpager.setAdapter(bottomAdapter);
//        ((StartAndResumeFragment) list.get(0)).initData();
//        ((MemberInfoFragment) bottomlist.get(0)).initData();
        listadapter = new MyBaseAdapter() {
            @Override
            public int getCount() {
                return dataArray.length();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final JSONObject object = dataArray.optJSONObject(position);
                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.act_yiqingtab_list_item, null);
                }
                view.setVisibility(View.VISIBLE);
                TextView report_type = MViewHolder.get(view, R.id.yq_report_type);
                TextView report_number = MViewHolder.get(view, R.id.yq_report_number);
                TextView no_report_number = MViewHolder.get(view, R.id.yq_no_report_number);
                TextView report_rote = MViewHolder.get(view, R.id.yq_report_rote);
                if(object.length()>0){
                    report_type.setText(object.optString("reportType"));
                    report_number.setText(object.optInt("subReportNum")+"");
                    no_report_number.setText(object.optInt("noSubReportNum")+"");
                    report_rote.setText(String.valueOf(object.optDouble("reportRate")+"%"));
                }
                report_number.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),Act_ReportProject.class).putExtra("type",2).putExtra("title","已上报项目信息"));
                    }
                });
                no_report_number.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(),Act_ReportProject.class).putExtra("type",1).putExtra("title","未上报项目信息"));
                    }
                });
                view.setTag(R.id.tag_first, object);
                return view;
            }
        };
        listView.setAdapter(listadapter);
    }

    @Override
    protected void loadData() {
        getOneData();
    }
    public void getOneData(){
        loadingDialog.show();
        JSONObject object = JsonUtils.getjsonobj("{\"dateTime\":\"" + DateUtils.getTodayDate() + "\"}");
        XUtil.postjsondata(object, "GetResumWorkProUserStatis", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(res);
                    tv_one_number.setText(object.optInt("projectCount")+"");
                    setTextCountDiff(tv_one,object.optInt("projectCountDiff"));
                    tv_two_number.setText(object.optInt("StartProjectCount")+"");
                    setTextCountDiff(tv_two,object.optInt("StartProjectCountDiff"));
                    tv_three_number.setText(object.optInt("totalUserCount")+"");
                    setTextCountDiff(tv_three,object.optInt("totalUserCountDiff"));
                    tv_four_number.setText(object.optInt("noUserCount")+"");
                    setTextCountDiff(tv_four,object.optInt("noUserCountDiff"));
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("proUserStatisList");
                        for (int i = 0; i < jsonArray.length(); i++) {//
                            dataArray.put(jsonArray.optJSONObject(i));
                        }
                    }
                    if(listadapter != null){
                        listadapter.notifyDataSetChanged();
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
    @OnClick({R.id.lin_one,
            R.id.lin_two,
            R.id.lin_three,
            R.id.lin_four,
            R.id.lin_five,
            R.id.lin_six
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_one:
                lin_one.setBackgroundColor(getResources().getColor(R.color.table_line));
                lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                TopViewPager.setCurrentItem(0);
                break;
            case R.id.lin_two:
                lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                lin_two.setBackgroundColor(getResources().getColor(R.color.table_line));
                lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                TopViewPager.setCurrentItem(1);
                break;
            case R.id.lin_three:
                lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                lin_three.setBackgroundColor(getResources().getColor(R.color.table_line));
                TopViewPager.setCurrentItem(2);
                break;
            case R.id.lin_four:
                lin_four.setBackgroundColor(getResources().getColor(R.color.table_line));
                lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                lin_six.setBackgroundColor(getResources().getColor(R.color.white));
                BottomViewpager.setCurrentItem(0);
                break;
            case R.id.lin_five:
                lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                lin_five.setBackgroundColor(getResources().getColor(R.color.table_line));
                lin_six.setBackgroundColor(getResources().getColor(R.color.white));
                BottomViewpager.setCurrentItem(1);
                break;
            case R.id.lin_six:
                lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                lin_six.setBackgroundColor(getResources().getColor(R.color.table_line));
                BottomViewpager.setCurrentItem(2);
                break;
        }
    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
//            list.get(arg0).initData();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    lin_one.setBackgroundColor(getResources().getColor(R.color.table_line));
                    lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_three.setBackgroundColor(getResources().getColor(R.color.white));
//                    ((StartAndResumeFragment2) list.get(0)).initData();
                    break;
                case 1:
                    lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_two.setBackgroundColor(getResources().getColor(R.color.table_line));
                    lin_three.setBackgroundColor(getResources().getColor(R.color.white));
//                    ((ManagementNumberFragment2) list.get(1)).initData();
                    break;
                case 2:
                    lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_three.setBackgroundColor(getResources().getColor(R.color.table_line));
//                    ((MechanicalEquipmentFragment2) list.get(2)).initData();
                    break;
            }

        }

    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyBottomPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
//            list.get(arg0).initData();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    lin_four.setBackgroundColor(getResources().getColor(R.color.table_line));
                    lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_six.setBackgroundColor(getResources().getColor(R.color.white));
//                    ((MemberInfoFragment) bottomlist.get(0)).initData();
                    break;
                case 1:
                    lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_five.setBackgroundColor(getResources().getColor(R.color.table_line));
                    lin_six.setBackgroundColor(getResources().getColor(R.color.white));
//                    ((EquipmentInfoFragment) bottomlist.get(1)).initData();
                    break;
                case 2:
                    lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_six.setBackgroundColor(getResources().getColor(R.color.table_line));
//                    ((DangerFragment) bottomlist.get(2)).initData();
                    break;
            }

        }

    }
    private void setTextCountDiff(TextView textView,int value){
        if(value == 0){
            textView.setText("较昨日"+"+"+value);
            textView.setTextColor(getResources().getColor(R.color.color_666666));
        }else if(value > 0){
            textView.setText("较昨日"+"+"+value);
            textView.setTextColor(getResources().getColor(R.color.color_qq));
        }else {
            textView.setText("较昨日"+value);
            textView.setTextColor(getResources().getColor(R.color.color_yyh));
        }
    }
}
