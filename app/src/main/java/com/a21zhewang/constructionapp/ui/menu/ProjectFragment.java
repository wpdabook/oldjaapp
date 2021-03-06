package com.a21zhewang.constructionapp.ui.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseLazyFragment;
import com.a21zhewang.constructionapp.bean.ChatLineDate;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RankDetailBean;
import com.a21zhewang.constructionapp.customview.MySmallTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.tab.Act_RiskHiddenDanger;
import com.a21zhewang.constructionapp.ui.tab.Act_SafetyQualityCivilization;
import com.a21zhewang.constructionapp.ui.tab.Act_Supervisor;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * ????????????
 * Created by wp-pc on 2020/5/6.
 */

public class ProjectFragment extends BaseLazyFragment {
    @BindView(R.id.activity_MyTitleBar)
    MySmallTitleBar titlebar;
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView title_back_img;
    @BindView(R.id.linechart)
    LineChartView mchartview;
    @BindView(R.id.liner_saferank_second_layout)
    LinearLayout liner_saferank_second_layout;
    @BindView(R.id.totalscore)
    TextView totalscore;
    @BindView(R.id.cqtotalscore)
    TextView cqtotalscore;
    @BindView(R.id.tv_rank)
    TextView tv_rank;
    @BindView(R.id.rect)
    TextView rect;
    @BindView(R.id.focus_info_date_time)
    TextView focus_info_date_time;
    @BindView(R.id.risk_project_rg)
    RadioGroup risk_project_rg;
    @BindView(R.id.pj_rg_1)
    RadioButton rg1;
    @BindView(R.id.pj_rg_2)
    RadioButton rg2;
    @BindView(R.id.rank_detail_columnchart)
    ColumnChartView columnview;
    @BindView(R.id.chart1)
    PieChartView chart1;
    @BindView(R.id.chart2)
    PieChartView chart2;
    @BindView(R.id.chart3)
    PieChartView chart3;
    @BindView(R.id.key_check_rate)
    TextView key_check_rate;
    @BindView(R.id.hidden_troub)
    TextView hidden_troub;
    @BindView(R.id.risk_danger)
    TextView risk_danger;
    @BindView(R.id.total_risk_number)
    TextView total_risk_number;
    @BindView(R.id.risk_query_rate)
    TextView risk_query_rate;
    @BindView(R.id.today_risk_number)
    TextView today_risk_number;
    @BindView(R.id.look_presion)
    LinearLayout look_persion;
    @BindView(R.id.lin_cqjg_rank_title)
    LinearLayout lin_cqjg_rank_title;
    @BindView(R.id.lin_common_rank_title)
    LinearLayout lin_common_rank_title;
    @BindView(R.id.cqjg_look_presion)
    LinearLayout cqjg_look_presion;
    private String mTitle;
    private List<Column> columns = new ArrayList<Column>();
    private ColumnChartData data;
    private List<SubcolumnValue> values;
    private List<ChatLineDate> tableList = new ArrayList<ChatLineDate>();
    private List<PointValue> pointList = new ArrayList<PointValue>();
    private List<AxisValue> axisValues;
    private ArrayList<AxisValue> axisValuesY;//??????Y???????????????????????????
    private List<AxisValue> axisXValues;
    private Calendar calendar;
    private List<Double> columdate;
    private boolean hasAxesNames = true;
    private boolean hasAxes = true;
    private int year;
    private int month;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;
    private String projectId;
    private RankDetailBean bean;
    private int rbType = 1;
    private int tag = 0; //???????????????????????????????????????????????????????????????????????????
    private String projectName;
    private List<SliceValue> values1;
    private List<SliceValue> values2;
    private List<SliceValue> values3;
    private PieChartData data1;
    private PieChartData data2;
    private PieChartData data3;
    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    private Dialog loadingDialog;
    private AlertDialog.Builder builder;

    public static ProjectFragment getInstance(String title) {
        ProjectFragment sf = new ProjectFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View v = inflater.inflate(R.layout.act_rankdetail2, null);
        ButterKnife.bind(this, v);
        title.setText(mTitle);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "?????????...");
        return v;
    }

    @Override
    protected void loadData() {
        if (tag == 0) {
            title.setText(PublicUtils.getspstring("rankProjectName0"));
            projectId = PublicUtils.getspstring("tab_two_projectId");
        } else {
            projectId = getArguments().getString("projectId");
        }
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                getActivity().finish();
            }
        });
        focus_info_date_time.setText("???????????????" + DateUtils.getTodayDate() + "???");
        risk_project_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (tableList.size() > 0) {
                    tableList.clear();
                }
                if (pointList.size() > 0) {
                    pointList.clear();
                }
                if (rg1.isChecked()) {
                    rbType = 1;
                    getChatLineDate(rbType);
                }
                if (rg2.isChecked()) {
                    rbType = 2;
                    getChatLineDate(rbType);
                }
            }
        });
        getDate();
    }

    @Override
    public void initListener() {
        //tag = getArguments().getInt("tag");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "?????????...");
        if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getActivity()))){
            lin_cqjg_rank_title.setVisibility(View.VISIBLE);
            lin_common_rank_title.setVisibility(View.GONE);
        }else {
            lin_cqjg_rank_title.setVisibility(View.GONE);
            lin_common_rank_title.setVisibility(View.VISIBLE);
        }
        columnview.setOnValueTouchListener(new ValueTouchListener());
        liner_saferank_second_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Act_RiskHiddenDanger.class)
                        .putExtra("projectId", projectId).putExtra("projectName", projectName));
            }
        });
        cqjg_look_presion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Act_Supervisor.class)
                        .putExtra("projectId", projectId).putExtra("type", 2));//????????????
            }
        });
        look_persion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showList();
            }
        });

    }

    public void getDate() {
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\"}"), "GetDataTotal2_rectProjectdangerTotal", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    projectName = object.optString("projectName");
                    /*????????????????????????????????????????????????????????????????????????*/
                    if (tag == 0) {
                        PublicUtils.putspstring("rankProjectName0", projectName);
                    }
                    title.setText(projectName);
//                    totalscore.setText(object.optInt("total")+"???");
                    Double keyExamItemRateValue = object.optDouble("keyExamItemRate");
                    if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getActivity()))){
                        cqtotalscore.setText(keyExamItemRateValue+"%");
                    }else {
                        totalscore.setText(keyExamItemRateValue+"%");
                    }
                    columdate = new ArrayList<Double>();
                    columdate.add(object.optDouble("buildRate"));
                    columdate.add(object.optDouble("superRate"));
                    columdate.add(object.optDouble("constrRate"));
//                    columdate.add(object.optDouble("keyExamItemRate"));
                    generateDefaultData(columdate);
                    tv_rank.setText("???" + object.optInt("rank") + "???");
                    today_risk_number.setText(object.optInt("riskQueryTotal") + "");
                    rect.setText(object.optInt("rect") + "");
                    getChatPie1(object);
                    getChatPie2(object);
                    getChatPie3(object);
                    total_risk_number.setText(object.optInt("riskQueryCumTotal") + "");
                    BaseRateValues(key_check_rate, object.optDouble("keyCheckRate"));
//                    BaseRateValues(risk_query_rate,object.optDouble("riskQueryRate"));
                    risk_query_rate.setText(object.optInt("riskQueryRate") + "%");
                    hidden_troub.setText(object.optInt("hiddenTroub") + "");
                    risk_danger.setText(object.optInt("riskDanger") + "");
                    rect.setText(object.optInt("rect") + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getChatLineDate(rbType);
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }

    private void getChatLineDate(final int type) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\"}"), "GetDataTotal2_rectProjectDateTotal", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject tempobject = new JSONObject(res);
                    axisValues = new ArrayList<AxisValue>();
                    axisValuesY = new ArrayList<AxisValue>();
                    if (tempobject.length() > 0) {
                        JSONArray array = new JSONArray();
                        if (type == 1) {
                            array = tempobject.optJSONArray("userItem");
                        } else {
                            array = tempobject.optJSONArray("dangerItem");
                        }
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            ChatLineDate chatLineDate = new ChatLineDate();
                            int time = object.optInt("time");
                            int num = object.optInt("num");
                            chatLineDate.setTime(time);
                            chatLineDate.setCount(num);
                            tableList.add(chatLineDate);
                            PointValue point = new PointValue(i, num); // ?????????Y??????????????????
                            pointList.add(point);
                            int week = tableList.get(i).getTime();
                            axisValues.add(new AxisValue(i).setLabel((week + "???").toCharArray()));// ?????????X??????????????????
                            axisValuesY.add(new AxisValue(i).setValue(tableList.get(i).getCount()));// ??????Y?????????????????????i
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                generateData();
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }

    private void generateDefaultData(List<Double> list) {
        int numColumns = list.size();
        axisXValues = new ArrayList<>();
//            axisYValues = new ArrayList<>();
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
//            values.add(new SubcolumnValue((float) list.get(i).doubleValue(), getResources().getColor(R.color.color_theme)));
//                axisYValues.add(new AxisValue(i).setValue(list.get(i).getNum()));
            SubcolumnValue subcolumnValue = new SubcolumnValue((float) list.get(i).doubleValue(), getResources().getColor(R.color.color_theme));
            Double d = (double) Math.round(list.get(i) * 100) / 100;
            subcolumnValue.setLabel((float) d.doubleValue() + "%");
//            if(d == 0.0){
//                d = 100.00;
//                subcolumnValue.setValue(100);
//                subcolumnValue.setLabel((float) d.doubleValue()+"%?????????");
//            }else {
//                subcolumnValue.setLabel((float) d.doubleValue()+"%?????????");
//            }
//            subcolumnValue.setValue((float) d.doubleValue());
            values.add(subcolumnValue);
            if (i == 0) {
                axisXValues.add(new AxisValue(i).setLabel("????????????"));
            }
            if (i == 1) {
                axisXValues.add(new AxisValue(i).setLabel("????????????"));
            }
            if (i == 2) {
                axisXValues.add(new AxisValue(i).setLabel("????????????"));
            }
//            if(i == 3){
//                axisXValues.add(new AxisValue(i).setLabel("????????????"));
//            }
            Column column = new Column(values);
            column.setHasLabels(true); //??????Y??????
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);//?????????????????????Lable??????????????????false????????????????????????????????????????????????
            columns.add(column);
        }
        data = new ColumnChartData(columns);
        if (hasAxes) {
            Axis axisBottom = new Axis(axisXValues);
//                Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
//                    axisBottom.setName("????????????");
                //????????????X???????????????
                axisBottom.setHasLines(false);
                //???????????????
                axisBottom.setTextColor(getResources().getColor(R.color.color_666666));
                axisBottom.setLineColor(Color.parseColor("#ff0000"));
                //??????????????????????????????,?????????Y????????? (0-32??????)
                axisBottom.setMaxLabelChars(5);
                axisBottom.setHasSeparationLine(true);
//                axisBottom.setName("????????????????????????????????????");
//                    axisY.setValues(axisYValues);
                axisY.setHasLines(false);
                axisY.setTextColor(getResources().getColor(R.color.color_666666));
//                    axisY.setName("????????????");
            }
            //??????x??????????????????
            data.setAxisXBottom(axisBottom);
            data.setAxisYLeft(axisY);
            data.setFillRatio(0.40F);//???????????????????????????????????????????????????0???1??????
//            data.setValueLabelBackgroundEnabled(false);
            data.setValueLabelTypeface(Typeface.DEFAULT);
            data.setValueLabelsTextColor(Color.WHITE);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        columnview.setAlpha(0.8f);//???????????????
        columnview.setZoomEnabled(false);////????????????????????????
        columnview.setMinimumWidth(3);
        columnview.setZoomType(ZoomType.HORIZONTAL);
        columnview.setColumnChartData(data);
    }

    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line(pointList);
        line.setColor(getResources().getColor(R.color.tilebar_color));
        line.setShape(ValueShape.CIRCLE);// ????????????
        line.setCubic(true);// ?????????????????????????????????
        line.setFilled(true);// ????????????
        line.setHasLabels(true);// ????????????????????????????????????Y??????
        line.setHasLabelsOnlyForSelected(true);// ????????????????????????????????????????????????Y??????
        line.setHasLines(true);// ????????????
        line.setHasPoints(true);// ????????????
        line.setStrokeWidth(2);// ??????????????????DP??????
        lines.add(line);
        LineChartData data = new LineChartData(lines);
        Axis axisX = new Axis();
        axisX.setValues(axisValues);
        axisX.setHasTiltedLabels(false);  //X??????????????????????????????????????????????????????true???????????????
//        axisX.setMaxLabelChars(7); //????????????x???????????????????????????????????????X?????????????????????7<=x<=mAxisXValues.length
        Axis axisY = new Axis().setHasLines(true);
        axisY.setHasLines(false);
        axisY.setValues(axisValuesY);
//        axisX.setName("??????(" + year + "???" + (month + 1) + "???)");
        if (rbType == 1) {
            axisY.setName("????????????");
        } else {
            axisY.setName("????????????");
        }
//        axisX.setTextSize(10);//??????????????????
//        axisY.setMaxLabelChars(7); //????????????y???????????????????????????????????????X?????????????????????7<=y<=mAxisyValues.length
        data.setAxisXBottom(axisX); //x ????????????  ???????????????????????????????????????x??????
        data.setAxisYLeft(axisY);
        data.setValueLabelBackgroundEnabled(false);
        if (mchartview != null) {
            mchartview.setLineChartData(data);
            mchartview.setZoomType(ZoomType.HORIZONTAL);
            mchartview.setZoomEnabled(false);
        }
    }

    public void getChatPie1(JSONObject object) {
        values1 = new ArrayList<SliceValue>();
        int v1 = object.optInt("safetyTotal");
        int v2 = object.optInt("qualityTotal");
        int v3 = object.optInt("civilizedTotal");
        if (v1 != 0 || v2 != 0 || v3 != 0) {
            chart1.setOnValueTouchListener(new ChartValueTouchListener());
        }
        SliceValue sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
        SliceValue sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
        SliceValue sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
//        SliceValue sliceValue1 = new SliceValue(-1, getResources().getColor(R.color.pie_blue));
//        sliceValue1.setLabel(String.valueOf(0));
//        SliceValue sliceValue2 = new SliceValue(-1, getResources().getColor(R.color.pie_zi));
//        sliceValue2.setLabel(String.valueOf(0));
//        SliceValue sliceValue3 = new SliceValue(-1, getResources().getColor(R.color.pie_green));
//        sliceValue3.setLabel(String.valueOf(0));
//        if(v1 != 0){
//            sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
//            sliceValue1.setLabel(String.valueOf(v1));
//        }
//        if(v2 != 0){
//            sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
//            sliceValue2.setLabel(String.valueOf(v2));
//        }
//        if(v3 != 0){
//            sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
//            sliceValue3.setLabel(String.valueOf(v3));
//        }
        values1.add(sliceValue1);
        values1.add(sliceValue2);
        values1.add(sliceValue3);
        data1 = new PieChartData(values1);
        data1.setHasLabels(hasLabels);
        data1.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data1.setHasLabelsOutside(hasLabelsOutside);
        data1.setHasCenterCircle(hasCenterCircle);

        data1.setHasCenterCircle(true);//???????????????????????????????????????
        data1.setCenterCircleScale(0.35F);////?????????????????????????????????
        data1.setValueLabelBackgroundColor(Color.TRANSPARENT);//??????????????????????????????????????????
        data1.setValueLabelBackgroundEnabled(false);
        data1.setValueLabelTypeface(Typeface.DEFAULT);
        // data1.setCenterCircleColor(getResources().getColor(R.color.color_theme));//??????????????????????????????
        data1.setValueLabelsTextColor(getResources().getColor(R.color.jet));
        if (isExploded) {
            data1.setSlicesSpacing(10);
        }
        chart1.setPieChartData(data1);
        chart1.setAlpha(0.75f);//???????????????
        chart1.setCircleFillRatio(1F);//???????????????????????????
    }

    public void getChatPie2(JSONObject object) {
        values2 = new ArrayList<SliceValue>();
        int v1 = object.optInt("safetyCumTotal");
        int v2 = object.optInt("qualityCumTotal");
        int v3 = object.optInt("civilizedCumTotal");
        SliceValue sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
        SliceValue sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
        SliceValue sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
        //sliceValue1.setLabel(String.valueOf(v1));
        //sliceValue2.setLabel(String.valueOf(v2));
        //sliceValue3.setLabel(String.valueOf(0));
//        if(v1 != 0){
//            sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
//            sliceValue1.setLabel(String.valueOf(v1));
//        }
//        if(v2 != 0){
//            sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
//            sliceValue2.setLabel(String.valueOf(v2));
//        }
//        if(v3 != 0){
//            sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
//            sliceValue3.setLabel(String.valueOf(v3));
//        }
        values2.add(sliceValue1);
        values2.add(sliceValue2);
        values2.add(sliceValue3);
        data2 = new PieChartData(values2);
        data2.setHasLabels(hasLabels);
        data2.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data2.setHasLabelsOutside(hasLabelsOutside);
        data2.setHasCenterCircle(hasCenterCircle);
        data2.setHasCenterCircle(true);//???????????????????????????????????????
        data2.setCenterCircleScale(0.35F);////?????????????????????????????????
        // data2.setValueLabelBackgroundColor(Color.TRANSPARENT);//??????????????????????????????????????????
        data2.setValueLabelBackgroundEnabled(false);
        data2.setValueLabelTypeface(Typeface.DEFAULT);
        data2.setValueLabelsTextColor(getResources().getColor(R.color.jet));
        if (isExploded) {
            data2.setSlicesSpacing(10);
        }
        chart2.setPieChartData(data2);
        chart2.setAlpha(0.75f);//???????????????
        chart2.setCircleFillRatio(1F);//???????????????????????????
    }

    public void getChatPie3(JSONObject object) {
        values3 = new ArrayList<SliceValue>();
        int v1 = object.optInt("safetyQueryRate");
        int v2 = object.optInt("qualityQueryRate");
        int v3 = object.optInt("civilizedQueryRate");
        SliceValue sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
        sliceValue1.setLabel(String.valueOf(v1) + "%");
        SliceValue sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
        sliceValue2.setLabel(String.valueOf(v2) + "%");
        SliceValue sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
        sliceValue3.setLabel(String.valueOf(v3) + "%");
//        SliceValue sliceValue1 = new SliceValue(30, getResources().getColor(R.color.pie_blue));
//        sliceValue1.setLabel(String.valueOf(0));
//        SliceValue sliceValue2 = new SliceValue(30, getResources().getColor(R.color.pie_zi));
//        sliceValue2.setLabel(String.valueOf(0));
//        SliceValue sliceValue3 = new SliceValue(30, getResources().getColor(R.color.pie_green));
//        sliceValue3.setLabel(String.valueOf(0));
//        if(v1 != 0){
//            sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
//            sliceValue1.setLabel(String.valueOf(v1));
//        }
//        if(v2 != 0){
//            sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
//            sliceValue2.setLabel(String.valueOf(v2));
//        }
//        if(v3 != 0){
//            sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
//            sliceValue3.setLabel(String.valueOf(v3));
//        }
        values3.add(sliceValue1);
        values3.add(sliceValue2);
        values3.add(sliceValue3);
        data3 = new PieChartData(values3);
        data3.setHasLabels(hasLabels);
        data3.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data3.setHasLabelsOutside(hasLabelsOutside);
        data3.setHasCenterCircle(hasCenterCircle);
        data3.setHasCenterCircle(true);//???????????????????????????????????????
        data3.setCenterCircleScale(0.35F);////?????????????????????????????????
        data3.setValueLabelBackgroundColor(Color.TRANSPARENT);//??????????????????????????????????????????
        data3.setValueLabelBackgroundEnabled(false);
        data3.setValueLabelTypeface(Typeface.DEFAULT);
        data3.setValueLabelsTextColor(getResources().getColor(R.color.jet));
        if (isExploded) {
            data3.setSlicesSpacing(10);
        }
        chart3.setPieChartData(data3);
        chart3.setAlpha(0.75f);//???????????????
        chart3.setCircleFillRatio(1F);//???????????????????????????
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            int level = columnIndex + 1; //????????????
            if (level == 1) {
                startActivity(new Intent(getActivity(), Act_Supervisor.class)
                        .putExtra("projectId", projectId).putExtra("type", 1));//????????????
            }
            if (level == 2) {
                startActivity(new Intent(getActivity(), Act_Supervisor.class)
                        .putExtra("projectId", projectId).putExtra("type", 3));//????????????
            }
            if (level == 3) {
                startActivity(new Intent(getActivity(), Act_Supervisor.class)
                        .putExtra("projectId", projectId).putExtra("type", 2));//????????????
            }
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub
        }
    }

    private class ChartValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            int index = arcIndex;
            if (value.getValue() != -1) {
                if (index == 0) {
                    startActivity(new Intent(getActivity(), Act_SafetyQualityCivilization.class) //??????
                            .putExtra("projectName", projectName)
                            .putExtra("projectId", projectId)
                            .putExtra("rectType", 1));
                }
                if (index == 1) {
                    startActivity(new Intent(getActivity(), Act_SafetyQualityCivilization.class) //??????
                            .putExtra("projectName", projectName)
                            .putExtra("projectId", projectId)
                            .putExtra("rectType", 2));
                }
                if (index == 2) {
                    startActivity(new Intent(getActivity(), Act_SafetyQualityCivilization.class)//??????
                            .putExtra("projectName", projectName)
                            .putExtra("projectId", projectId)
                            .putExtra("rectType", 3));
                }
            }
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }
    }
    /**
     * ?????? dialog
     */
    private void showList() {
        final String[] items = {"????????????", "????????????", "????????????"};
        builder = new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int index = i+1;
                        if ( index == 1) {
                            startActivity(new Intent(getActivity(), Act_Supervisor.class)
                                    .putExtra("projectId", projectId).putExtra("type", 1));//????????????
                        }
                        if ( index == 2) {
                            startActivity(new Intent(getActivity(), Act_Supervisor.class)
                                    .putExtra("projectId", projectId).putExtra("type", 3));//????????????
                        }
                        if ( index == 3) {
                            startActivity(new Intent(getActivity(), Act_Supervisor.class)
                                    .putExtra("projectId", projectId).putExtra("type", 2));//????????????
                        }
                    }
                });
        builder.create().show();
    }
}
