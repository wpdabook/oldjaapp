package com.a21zhewang.constructionapp.ui.tab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ChatLineDate;
import com.a21zhewang.constructionapp.bean.HiddenBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RankDetailBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.menu.ProjectFragment;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
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
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * 单项目详情
 */

public class Act_RankDetail2 extends BaseActivity {
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
    private List<Column> columns = new ArrayList<Column>();
    private ColumnChartData data;
    private List<SubcolumnValue> values;
    private List<ChatLineDate> tableList = new ArrayList<ChatLineDate>();
    private List<PointValue> pointList = new ArrayList<PointValue>();
    private List<AxisValue> axisValues;
    private ArrayList<AxisValue> axisValuesY;//定义Y轴刻度值的数据集合
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
    private int tag = 0; //用来处理该页面作为一级还是二级页面标题栏显示的逻辑
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

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_rankdetail2;
    }

    @Override
    public void initViews() {
        tag = getIntent().getIntExtra("tag",0);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            lin_cqjg_rank_title.setVisibility(View.VISIBLE);
            lin_common_rank_title.setVisibility(View.GONE);
        }else {
            lin_cqjg_rank_title.setVisibility(View.GONE);
            lin_common_rank_title.setVisibility(View.VISIBLE);
        }
        columnview.setOnValueTouchListener(new ValueTouchListener());
        if(tag == 0){
            title.setText(PublicUtils.getspstring("rankProjectName0"));
            projectId = PublicUtils.getspstring("tab_two_projectId");
        }else {
            projectId = getIntent().getStringExtra("projectId");
        }
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearspValues();
                finish();
            }
        });
        focus_info_date_time.setText("重点检查（"+ DateUtils.getTodayDate()+"）");
        risk_project_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(tableList.size()>0){
                    tableList.clear();
                }
                if(pointList.size()>0){
                    pointList.clear();
                }
                if(rg1.isChecked()){
                    rbType = 1;
                    getChatLineDate(rbType);
                }
                if(rg2.isChecked()){
                    rbType = 2;
                    getChatLineDate(rbType);
                }
            }
        });
        cqjg_look_presion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail2.this, Act_Supervisor.class)
                        .putExtra("projectId", projectId).putExtra("type", 2));//施工单位
            }
        });
        look_persion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });
        getDate();
    }

    public void getDate(){
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\"}"), "GetDataTotal2_rectProjectdangerTotal", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    projectName = object.optString("projectName");
                    /*用来处理该页面作为一级还是二级页面标题显示的逻辑*/
                    if(tag == 0){
                        PublicUtils.putspstring("rankProjectName0", projectName);
                    }
                    title.setText(projectName);
//                    totalscore.setText(object.optInt("total")+"分");
                    Double keyExamItemRateValue = object.optDouble("keyExamItemRate");
                    if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
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
                    tv_rank.setText("第"+object.optInt("rank")+"名");
                    today_risk_number.setText(object.optInt("riskQueryTotal")+"");
                    rect.setText(object.optInt("rect")+"");
                    getChatPie1(object);
                    getChatPie2(object);
                    getChatPie3(object);
                    total_risk_number.setText(object.optInt("riskQueryCumTotal")+"");
                    BaseRateValues(key_check_rate,object.optDouble("keyCheckRate"));
//                    BaseRateValues(risk_query_rate,object.optDouble("riskQueryRate"));
                    risk_query_rate.setText(object.optInt("riskQueryRate")+"%");
                    hidden_troub.setText(object.optInt("hiddenTroub")+"");
                    risk_danger.setText(object.optInt("riskDanger")+"");
                    rect.setText(object.optInt("rect")+"");
                }catch (Exception e){
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
    private void getChatLineDate(final int type){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\"}"), "GetDataTotal2_rectProjectDateTotal", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject tempobject = new JSONObject(res);
                    axisValues = new ArrayList<AxisValue>();
                    axisValuesY = new ArrayList<AxisValue>();
                    if(tempobject.length()>0){
                        JSONArray array = new JSONArray();
                        if(type == 1){
                            array = tempobject.optJSONArray("userItem");
                        }else {
                            array = tempobject.optJSONArray("dangerItem");
                        }
                        for(int i=0;i<array.length();i++){
                            JSONObject object = array.optJSONObject(i);
                            ChatLineDate chatLineDate = new ChatLineDate();
                            int time = object.optInt("time");
                            int num = object.optInt("num");
                            chatLineDate.setTime(time);
                            chatLineDate.setCount(num);
                            tableList.add(chatLineDate);
                            PointValue point = new PointValue(i,num); // 为每个Y轴点设置别名
                            pointList.add(point);
                            int week = tableList.get(i).getTime();
                            axisValues.add(new AxisValue(i).setLabel((week + "日").toCharArray()));// 为每个X轴点设置别名
                            axisValuesY.add(new AxisValue(i).setValue(tableList.get(i).getCount()));// 添加Y轴显示的刻度值i
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
    public void getChatPie1(JSONObject object){
        values1 = new ArrayList<SliceValue>();
        int v1 = object.optInt("safetyTotal");
        int v2 = object.optInt("qualityTotal");
        int v3 = object.optInt("civilizedTotal");
        if(v1!= 0 || v2 != 0 || v3!=0){
            chart1.setOnValueTouchListener(new  ChartValueTouchListener());
        }
        SliceValue sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
        SliceValue sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
        SliceValue sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
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

        data1.setHasCenterCircle(true);//设置饼图中间是否有第二个圈
        data1.setCenterCircleScale(0.35F);////设置第二个圈的大小比例
        data1.setValueLabelBackgroundColor(Color.TRANSPARENT);//此处设置坐标点旁边的文字背景
        data1.setValueLabelBackgroundEnabled(false);
        data1.setValueLabelTypeface(Typeface.DEFAULT);
        // data1.setCenterCircleColor(getResources().getColor(R.color.color_theme));//设置饼图中间圈的颜色
         data1.setValueLabelsTextColor(getResources().getColor(R.color.jet));
        if (isExploded) {
            data1.setSlicesSpacing(10);
        }
        chart1.setPieChartData(data1);
        chart1.setAlpha(0.75f);//设置透明度
        chart1.setCircleFillRatio(1F);//设置饼图其中的比例
    }
    public void getChatPie2(JSONObject object){
        values2 = new ArrayList<SliceValue>();
        int v1 = object.optInt("safetyCumTotal");
        int v2 = object.optInt("qualityCumTotal");
        int v3 = object.optInt("civilizedCumTotal");
        SliceValue sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
        SliceValue sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
        SliceValue sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
//        sliceValue1.setLabel(String.valueOf(0));
//        sliceValue2.setLabel(String.valueOf(0));
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
        values2.add(sliceValue1);
        values2.add(sliceValue2);
        values2.add(sliceValue3);
        data2 = new PieChartData(values2);
        data2.setHasLabels(hasLabels);
        data2.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data2.setHasLabelsOutside(hasLabelsOutside);
        data2.setHasCenterCircle(hasCenterCircle);
        data2.setHasCenterCircle(true);//设置饼图中间是否有第二个圈
        data2.setCenterCircleScale(0.35F);////设置第二个圈的大小比例
        data2.setValueLabelBackgroundColor(Color.TRANSPARENT);//此处设置坐标点旁边的文字背景
        data2.setValueLabelBackgroundEnabled(false);
        data2.setValueLabelTypeface(Typeface.DEFAULT);
        data2.setValueLabelsTextColor(getResources().getColor(R.color.jet));
        if (isExploded) {
            data2.setSlicesSpacing(10);
        }
        chart2.setPieChartData(data2);
        chart2.setAlpha(0.75f);//设置透明度
        chart2.setCircleFillRatio(1F);//设置饼图其中的比例
    }
    public void getChatPie3(JSONObject object){
        values3 = new ArrayList<SliceValue>();
        int v1 = object.optInt("safetyQueryRate");
        int v2 = object.optInt("qualityQueryRate");
        int v3 = object.optInt("civilizedQueryRate");
        SliceValue sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
        sliceValue1.setLabel(String.valueOf(v1)+"%");
        SliceValue sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
        sliceValue2.setLabel(String.valueOf(v2)+"%");
        SliceValue sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
        sliceValue3.setLabel(String.valueOf(v3)+"%");
//        if(v1 != 0){
//            sliceValue1 = new SliceValue(v1, getResources().getColor(R.color.pie_blue));
//            sliceValue1.setLabel(String.valueOf(v1)+"%");
//        }
//        if(v2 != 0){
//            sliceValue2 = new SliceValue(v2, getResources().getColor(R.color.pie_zi));
//            sliceValue2.setLabel(String.valueOf(v2)+"%");
//        }
//        if(v3 != 0){
//            sliceValue3 = new SliceValue(v3, getResources().getColor(R.color.pie_green));
//            sliceValue3.setLabel(String.valueOf(v3)+"%");
//        }
        values3.add(sliceValue1);
        values3.add(sliceValue2);
        values3.add(sliceValue3);
        data3 = new PieChartData(values3);
        data3.setHasLabels(hasLabels);
        data3.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data3.setHasLabelsOutside(hasLabelsOutside);
        data3.setHasCenterCircle(hasCenterCircle);
        data3.setHasCenterCircle(true);//设置饼图中间是否有第二个圈
        data3.setCenterCircleScale(0.35F);////设置第二个圈的大小比例
        data3.setValueLabelBackgroundColor(Color.TRANSPARENT);//此处设置坐标点旁边的文字背景
        data3.setValueLabelBackgroundEnabled(false);
        data3.setValueLabelTypeface(Typeface.DEFAULT);
        data3.setValueLabelsTextColor(getResources().getColor(R.color.jet));
        if (isExploded) {
            data3.setSlicesSpacing(10);
        }
        chart3.setPieChartData(data3);
        chart3.setAlpha(0.75f);//设置透明度
        chart3.setCircleFillRatio(1F);//设置饼图其中的比例
    }
    @Override
    public void initListener() {
        liner_saferank_second_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail2.this,Act_RiskHiddenDanger.class)
                        .putExtra("projectId",projectId).putExtra("projectName",projectName));
            }
        });
    }

    @Override
    public void initData() {
        calendar = Calendar.getInstance(Locale.CHINA);
        year = calendar.get(Calendar.YEAR); // 获取Calendar对象中的年
        month = calendar.get(Calendar.MONTH);// 获取Calendar对象中的月
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
            subcolumnValue.setLabel((float) d.doubleValue()+"%");
//            subcolumnValue.setValue((float) d.doubleValue());
            values.add(subcolumnValue);
            if(i == 0){
                axisXValues.add(new AxisValue(i).setLabel("建设单位"));
            }
            if(i == 1){
                axisXValues.add(new AxisValue(i).setLabel("监理单位"));
            }
            if(i == 2){
                axisXValues.add(new AxisValue(i).setLabel("施工单位"));
            }
//            if(i == 3){
//                axisXValues.add(new AxisValue(i).setLabel("重点检查"));
//            }
            Column column = new Column(values);
            column.setHasLabels(true); //显示Y轴值
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);//设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            columns.add(column);
        }
        data = new ColumnChartData(columns);
        if (hasAxes) {
            Axis axisBottom = new Axis(axisXValues);
//                Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
//                    axisBottom.setName("风险等级");
                //是否显示X轴的网格线
                axisBottom.setHasLines(false);
                //分割线颜色
                axisBottom.setTextColor(getResources().getColor(R.color.color_666666));
                axisBottom.setLineColor(Color.parseColor("#ff0000"));
                //距离各标签之间的距离,包括离Y轴间距 (0-32之间)
                axisBottom.setMaxLabelChars(5);
                axisBottom.setHasSeparationLine(true);
//                axisBottom.setName("重点检查完成率（百分比）");
//                    axisY.setValues(axisYValues);
                axisY.setHasLines(false);
                axisY.setTextColor(getResources().getColor(R.color.color_666666));
//                    axisY.setName("风险数量");
            }
            //设置x轴在底部显示
            data.setAxisXBottom(axisBottom);
            data.setAxisYLeft(axisY);
            data.setFillRatio(0.40F);//参数即为柱子宽度的倍数，范围只能在0到1之间
//            data.setValueLabelBackgroundEnabled(false);
            data.setValueLabelTypeface(Typeface.DEFAULT);
            data.setValueLabelsTextColor(Color.WHITE);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        columnview.setAlpha(0.8f);//设置透明度
        columnview.setZoomEnabled(false);////设置是否支持缩放
        columnview.setMinimumWidth(3);
        columnview.setZoomType(ZoomType.HORIZONTAL);
        columnview.setColumnChartData(data);
    }
    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line(pointList);
        line.setColor(getResources().getColor(R.color.tilebar_color));
        line.setShape(ValueShape.CIRCLE);// 点的形状
        line.setCubic(true);// 点之间的连线要不要弧度
        line.setFilled(true);// 是否填充
        line.setHasLabels(true);// 点上是否显示点具体对应的Y轴值
        line.setHasLabelsOnlyForSelected(true);// 是否是只在选中点的时候显示对应的Y轴值
        line.setHasLines(true);// 是否画线
        line.setHasPoints(true);// 是否画点
        line.setStrokeWidth(2);// 连线的宽度，DP单位
        lines.add(line);
        LineChartData data = new LineChartData(lines);
        Axis axisX = new Axis();
        axisX.setValues(axisValues);
        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
//        axisX.setMaxLabelChars(7); //最多几个x轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        Axis axisY = new Axis().setHasLines(true);
        axisY.setHasLines(false);
        axisY.setValues(axisValuesY);
//        axisX.setName("日期(" + year + "年" + (month + 1) + "月)");
        if(rbType == 1){
            axisY.setName("使用人数");
        }else {
            axisY.setName("风险数量");
        }
//        axisX.setTextSize(10);//设置字体大小
//        axisY.setMaxLabelChars(7); //最多几个y轴坐标，意思就是你的缩放让X轴上数据的个数7<=y<=mAxisyValues.length
        data.setAxisXBottom(axisX); //x 轴在底部  （顶部底部一旦设置就意味着x轴）
        data.setAxisYLeft(axisY);
        data.setValueLabelBackgroundEnabled(false);
        if(mchartview != null){
            mchartview.setLineChartData(data);
            mchartview.setZoomType(ZoomType.HORIZONTAL);
            mchartview.setZoomEnabled(false);
        }
    }
    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            int level = columnIndex + 1; //风险等级
            if(level == 1){
                startActivity(new Intent(Act_RankDetail2.this,Act_Supervisor.class)
                        .putExtra("projectId",projectId).putExtra("type",1));//建设单位
            }
            if(level == 2){
                startActivity(new Intent(Act_RankDetail2.this,Act_Supervisor.class)
                        .putExtra("projectId",projectId).putExtra("type",3));//监理单位
            }
            if(level == 3){
                startActivity(new Intent(Act_RankDetail2.this,Act_Supervisor.class)
                        .putExtra("projectId",projectId).putExtra("type",2));//施工单位
            }
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
    @Override
    public void processClick(View v) {

    }
    private class ChartValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            int index = arcIndex;
            if(value.getValue()!= -1){
                if(index == 0){
                    startActivity(new Intent(Act_RankDetail2.this, Act_SafetyQualityCivilization.class) //安全
                            .putExtra("projectName",projectName)
                            .putExtra("projectId",projectId)
                            .putExtra("rectType",1));
                }
                if(index == 1){
                    startActivity(new Intent(Act_RankDetail2.this, Act_SafetyQualityCivilization.class) //质量
                            .putExtra("projectName",projectName)
                            .putExtra("projectId",projectId)
                            .putExtra("rectType",2));
                }
                if(index == 2){
                    startActivity(new Intent(Act_RankDetail2.this, Act_SafetyQualityCivilization.class)//文明
                            .putExtra("projectName",projectName)
                            .putExtra("projectId",projectId)
                            .putExtra("rectType",3));
                }
            }
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
    /**
     */
    private void showList() {
        final String[] items = {"建设单位", "监理单位", "施工单位"};
        builder = new AlertDialog.Builder(Act_RankDetail2.this).setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int index = i+1;
                        if ( index == 1) {
                            startActivity(new Intent(Act_RankDetail2.this, Act_Supervisor.class)
                                    .putExtra("projectId", projectId).putExtra("type", 1));//建设单位
                        }
                        if ( index == 2) {
                            startActivity(new Intent(Act_RankDetail2.this, Act_Supervisor.class)
                                    .putExtra("projectId", projectId).putExtra("type", 3));//监理单位
                        }
                        if ( index == 3) {
                            startActivity(new Intent(Act_RankDetail2.this, Act_Supervisor.class)
                                    .putExtra("projectId", projectId).putExtra("type", 2));//施工单位
                        }
                    }
                });
        builder.create().show();
    }
}
