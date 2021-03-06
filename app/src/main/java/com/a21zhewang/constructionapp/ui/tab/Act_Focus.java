package com.a21zhewang.constructionapp.ui.tab;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.Focus;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.CircularProgressView;
import com.a21zhewang.constructionapp.customview.CircularProgressView2;
import com.a21zhewang.constructionapp.customview.ViewPagerIndicator;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by WP-PC on 2019/8/12.
 */

public class Act_Focus extends BaseActivity{
    @BindView(R.id.indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.chart)
    PieChartView chart;
    @BindView(R.id.tv_leve1)
    TextView leve1;
    @BindView(R.id.tv_leve2)
    TextView leve2;
    @BindView(R.id.tv_leve3)
    TextView leve3;
    @BindView(R.id.liner_saferank_second_layout)
    LinearLayout liner_saferank_second_layout;
    @BindView(R.id.lin_onelevel1)
    LinearLayout lin_onelevel1;
    @BindView(R.id.lin_onelevel2)
    LinearLayout lin_onelevel2;
    @BindView(R.id.lin_onelevel3)
    LinearLayout lin_onelevel3;
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.circle_one)
    CircularProgressView circularProgressView1;
    @BindView(R.id.circle_two)
    CircularProgressView circularProgressView2;
    @BindView(R.id.circle_three)
    CircularProgressView circularProgressView3;
    @BindView(R.id.yq_onelevel1)
    LinearLayout yq_onelevel1;
    @BindView(R.id.yq_onelevel2)
    LinearLayout yq_onelevel2;
    @BindView(R.id.yq_onelevel3)
    LinearLayout yq_onelevel3;
    public static boolean NEEDREFRESH;
    private FragmentPagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    //    List<String> tabTitles = new ArrayList<>(Arrays.asList("????????????","?????????","????????????"));
    //    List<String> tabId = new ArrayList<>(Arrays.asList("1","2","3"));
    List<String> tabTitles = new ArrayList<>(Arrays.asList("????????????","?????????","???????????????"));
    List<String> tabId = new ArrayList<>(Arrays.asList("1","2","4"));
    /*??????*/
    private PieChartData data;
    private boolean hasLabels = false;
    private boolean hasLabelsOutside = true;
    private boolean hasCenterCircle = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    private Focus focus;
    private List<SliceValue> values;
    private Dialog loadingDialog;
    private TextView risk_number_1,risk_number_2;
    private LinearLayout history_linear;
    private LinearLayout lin_more;
    private int index = 0;
    @BindView(R.id.no_reported_view)
    CircularProgressView2 no_reported_view;
    @BindView(R.id.reported_view)
    CircularProgressView2 reported_view;
    @BindView(R.id.passed_view)
    CircularProgressView2 passed_view;
    @BindView(R.id.lin_yiqing)
    LinearLayout lin_yiqing;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_focus2;
    }

    @Override
    public void initViews() {
        title.setText("????????????");
        viewpager.setOffscreenPageLimit(3);
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals("EpidemicAddBtn")) {
                    lin_yiqing.setVisibility(View.VISIBLE);
                }
            } }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
//        chart.setOnValueTouchListener(new ValueTouchListener());
        risk_number_1 = (TextView) findViewById(R.id.risk_number_1);
        risk_number_2 = (TextView) findViewById(R.id.risk_number_2);
        history_linear = (LinearLayout)findView(R.id.history_linear);
        lin_more = (LinearLayout)findView(R.id.lin_more);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearspValues();
                Act_Focus.this.finish();
            }
        });
        history_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_RiskHistory.class));
            }
        });
        /**
         *  level ????????????1?????????
         */
        liner_saferank_second_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_SafetyRisk.class).putExtra("level",1));
            }
        });
        /**
         *  level ????????????1?????????
         */
        lin_onelevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_SafetyRisk.class).putExtra("level",1));
            }
        });
        /**
         *  level ????????????2?????????
         */
        lin_onelevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_SafetyRisk.class).putExtra("level",2));
            }
        });
        /**
         *  level ????????????3?????????
         */
        lin_onelevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_SafetyRisk.class).putExtra("level",3));
            }
        });
        yq_onelevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_YQInfo.class).putExtra("type",0));
            }
        });
        yq_onelevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_YQInfo.class).putExtra("type",2));
            }
        });
        yq_onelevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_Focus.this,Act_YQInfo.class).putExtra("type",1));
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
                index = position;
                ((RankFragment) (fragmentList.get(position))).initData();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        lin_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0){ //????????????
                    startActivity(new Intent(Act_Focus.this,Act_RankMoreDetail.class).putExtra("id","4"));
                }
                if(index == 1){ //?????????
                    startActivity(new Intent(Act_Focus.this,Act_RankMoreDetail.class).putExtra("id","5"));
                }
                if(index == 2){ //???????????????
                    startActivity(new Intent(Act_Focus.this,Act_RankMoreDetail.class).putExtra("id","6"));
                }
            }
        });
    }

    @Override
    public void initData() {
        for(int i=0;i<tabTitles.size();i++){
            RankFragment fragment = new RankFragment();
            Bundle b = new Bundle();
            b.putString("id", tabId.get(i).toString());
            fragment.setArguments(b);
            fragmentList.add(fragment);
        }
        mIndicator.setTabItemTitles(tabTitles);
        pagerAdapter.notifyDataSetChanged();
        mIndicator.setViewPager(viewpager, 0);
        ((RankFragment) fragmentList.get(0)).initData();
        toggleLabels();

    }

    @Override
    public void processClick(View v) {

    }
    private void toggleLabels() {
        hasLabels = !hasLabels;

        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);

            if (hasLabelsOutside) {
                chart.setCircleFillRatio(0.7f);
            } else {
                chart.setCircleFillRatio(1.0f);
            }
        }
        generateData();
    }

    /**
     * ????????????
     * 1:????????? 2????????????
     */
    private void generateData() {
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 2 + "\"}"),
                "GetDataTotal_riskscreencheckTotal", new MyCallBack() {
            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                try {
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("levelList");
                    for(int i=0;i<array.length();i++){
                        JSONObject leveobject = array.optJSONObject(0);
                        leve1.setText(leveobject.optInt("num")+"");
                        JSONObject leveobject2 = array.optJSONObject(1);
                        leve2.setText(leveobject2.optInt("num")+"");
                        JSONObject leveobject3 = array.optJSONObject(2);
                        leve3.setText(leveobject3.optInt("num")+"");

                        int a = leveobject.optInt("num")-leveobject.optInt("nRectNum");
                        int b = leveobject2.optInt("num")-leveobject2.optInt("nRectNum");
                        int c = leveobject3.optInt("num")-leveobject3.optInt("nRectNum");

                        circularProgressView1.setText("?????????",leveobject.optInt("nRectNum")+"");
//                        circularProgressView1.setText("??????",getPercentage(a,leveobject.optInt("num"))+"%");
                        circularProgressView2.setText("?????????",leveobject2.optInt("nRectNum")+"");
                        circularProgressView3.setText("?????????",leveobject3.optInt("nRectNum")+"");

                        //?????????
                        circularProgressView1.setProgress(getPercentage(a,leveobject.optInt("num")));
                        circularProgressView2.setProgress(getPercentage(b,leveobject2.optInt("num")));
                        circularProgressView3.setProgress(getPercentage(c,leveobject3.optInt("num")));

                        no_reported_view.setText(object.optInt("unappliedCount")+"");
                        reported_view.setText(object.optInt("appliedCount")+"");
                        passed_view.setText(object.optInt("acceptedCount")+"");
                        int d = object.optInt("unappliedCount");
                        int e = object.optInt("appliedCount");
                        int f = object.optInt("acceptedCount");
                        no_reported_view.setProgress(getPercentage(d,leveobject.optInt("projectCount")));
                        reported_view.setProgress(getPercentage(e,leveobject.optInt("projectCount")));
                        passed_view.setProgress(getPercentage(f,leveobject.optInt("projectCount")));
                    }
                    values = new ArrayList<SliceValue>();
//                    values.add(new SliceValue()); //??????
//                    values.add(new SliceValue(object.optInt("qualityCount"),
//                            getResources().getColor(R.color.pie_zi))); //??????
//                    values.add(new SliceValue(object.optInt("civilizedCount"),
//                            getResources().getColor(R.color.pie_green)));  //??????

                    SliceValue sliceValue1 = new SliceValue(object.optInt("safetyCount"),
                            getResources().getColor(R.color.pie_blue));
                    sliceValue1.setLabel(object.optInt("safetyCount")+"");


                    SliceValue sliceValue2 = new SliceValue(object.optInt("qualityCount"),
                            getResources().getColor(R.color.pie_zi));
                    sliceValue2.setLabel(object.optInt("qualityCount")+"");

                    SliceValue sliceValue3 = new SliceValue(object.optInt("civilizedCount"),
                            getResources().getColor(R.color.pie_green));
                    sliceValue3.setLabel(object.optInt("civilizedCount")+"");

                    values.add(sliceValue1);
                    values.add(sliceValue2);
                    values.add(sliceValue3);
//                    values.add(new SliceValue(90,
//                            getResources().getColor(R.color.pie_blue))); //??????
//                    values.add(new SliceValue(100,
//                            getResources().getColor(R.color.pie_zi))); //??????
//                    values.add(new SliceValue(120,
//                            getResources().getColor(R.color.pie_green)));  //??????
                    data = new PieChartData(values);
                    data.setHasLabels(hasLabels);
                    data.setHasLabelsOnlyForSelected(hasLabelForSelected);
                    data.setHasLabelsOutside(hasLabelsOutside);
                    data.setHasCenterCircle(hasCenterCircle);
                    data.setValueLabelBackgroundEnabled(false);
//                    data.setValueLabelTypeface(Typeface.DEFAULT);
                    data.setValueLabelsTextColor(getResources().getColor(R.color.color_theme));
                    if (isExploded) {
                        data.setSlicesSpacing(24);
                    }
                    chart.setPieChartData(data);
                    risk_number_1.setText(object.optInt("riskcheckCount")+"");
                    risk_number_2.setText(object.optInt("rectificationCount")+"");
                } catch (JSONException e) {
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
        if (NEEDREFRESH && fragmentList.size() > 0) {
            ((RankFragment) fragmentList.get(viewpager.getCurrentItem())).refresh();
            NEEDREFRESH = false;
        }
    }
    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(Act_Focus.this, "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
