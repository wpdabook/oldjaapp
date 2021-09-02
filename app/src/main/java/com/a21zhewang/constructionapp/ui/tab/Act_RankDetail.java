package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 单项目详情
 */
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ChatLineDate;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RankDetailBean;
import com.a21zhewang.constructionapp.bean.TableDate;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReportAdd;
import com.a21zhewang.constructionapp.ui.xmxx.fragment.zongbaoFragment;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

public class Act_RankDetail extends BaseActivity {
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
    @BindView(R.id.tv_rote_1)
    TextView rote1;
    @BindView(R.id.tv_rote_2)
    TextView rote2;
    @BindView(R.id.tv_rote_3)
    TextView rote3;
    @BindView(R.id.tv_rote_4)
    TextView rote4;
    @BindView(R.id.tv_rank)
    TextView tv_rank;
    @BindView(R.id.today_risk_number)
    TextView today_risk_number;
    @BindView(R.id.risk_safe_number)
    TextView risk_safe_number;
    @BindView(R.id.risk_quality_number)
    TextView risk_quality_number;
    @BindView(R.id.risk_civilized_number)
    TextView risk_civilized_number;
    @BindView(R.id.total_risk_number)
    TextView total_risk_number;
    @BindView(R.id.safe_risk_number)
    TextView safe_risk_number;
    @BindView(R.id.quality_cum_total)
    TextView quality_cum_total;
    @BindView(R.id.civilized_cum_total)
    TextView civilized_cum_total;
    @BindView(R.id.risk_query_rate)
    TextView risk_query_rate;
    @BindView(R.id.safety_query_rate)
    TextView safety_query_rate;
    @BindView(R.id.quality_query_rate)
    TextView quality_query_rate;
    @BindView(R.id.civilized_query_rate)
    TextView civilized_query_rate;
    @BindView(R.id.key_check_rate)
    TextView key_check_rate;
    @BindView(R.id.hidden_troub)
    TextView hidden_troub;
    @BindView(R.id.risk_danger)
    TextView risk_danger;
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
    @BindView(R.id.supervisor_lin)
    LinearLayout supervisor_lin;
    @BindView(R.id.workplace_lin)
    LinearLayout workplace_lin;
    @BindView(R.id.lin_risk_safe_number)
    LinearLayout lin_risk_safe_number;
    @BindView(R.id.lin_risk_quality_number)
    LinearLayout lin_risk_quality_number;
    @BindView(R.id.lin_risk_civilized_number)
    LinearLayout lin_risk_civilized_number;
    private List<ChatLineDate> tableList = new ArrayList<ChatLineDate>();
    private List<PointValue> pointList = new ArrayList<PointValue>();
    private List<AxisValue> axisValues;
    private ArrayList<AxisValue> axisValuesY;//定义Y轴刻度值的数据集合
    private Calendar calendar;
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

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_rankdetail;
    }

    @Override
    public void initViews() {
        tag = getIntent().getIntExtra("tag",0);
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
        /*监理单位*/
        supervisor_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail.this,Act_Supervisor.class)
                        .putExtra("projectId",projectId).putExtra("type",3));
            }
        });
        /*施工单位*/
        workplace_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail.this,Act_Supervisor.class)
                        .putExtra("projectId",projectId).putExtra("type",2));
            }
        });
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
        getDate();
    }

    public void getDate(){
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
                    totalscore.setText(object.optInt("total")+"");
                    BaseRateValues(rote1,object.optDouble("buildRate"));
                    BaseRateValues(rote2,object.optDouble("superRate"));
                    BaseRateValues(rote3,object.optDouble("constrRate"));
                    BaseRateValues(rote4,object.optDouble("keyExamItemRate"));
                    tv_rank.setText("当前排名：第"+object.optInt("rank")+"名");
                    today_risk_number.setText(object.optInt("riskQueryTotal")+"");
                    risk_safe_number.setText(object.optInt("safetyTotal")+"");
                    risk_quality_number.setText(object.optInt("qualityTotal")+"");
                    risk_civilized_number.setText(object.optInt("civilizedTotal")+"");
                    if(object.optInt("safetyTotal") == 0){
                        lin_risk_safe_number.setEnabled(false);
                    }
                    if(object.optInt("qualityTotal") == 0){
                        lin_risk_quality_number.setEnabled(false);
                    }
                    if(object.optInt("civilizedTotal") == 0){
                        lin_risk_civilized_number.setEnabled(false);
                    }
                    total_risk_number.setText(object.optInt("riskQueryCumTotal")+"");
                    safe_risk_number.setText(object.optInt("safetyCumTotal")+"");
                    quality_cum_total.setText(object.optInt("qualityCumTotal")+"");
                    civilized_cum_total.setText(object.optInt("civilizedCumTotal")+"");
                    BaseRateValues(risk_query_rate,object.optDouble("riskQueryRate"));
                    BaseRateValues(safety_query_rate,object.optDouble("safetyQueryRate"));
                    BaseRateValues(quality_query_rate,object.optDouble("qualityQueryRate"));
                    BaseRateValues(civilized_query_rate,object.optDouble("civilizedQueryRate"));
                    BaseRateValues(key_check_rate,object.optDouble("keyCheckRate"));
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


    @Override
    public void initListener() {
        liner_saferank_second_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail.this,Act_RiskHiddenDanger.class).putExtra("projectId",projectId));
            }
        });
        lin_risk_safe_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail.this, Act_SafetyQualityCivilization.class)
                        .putExtra("projectName",projectName)
                        .putExtra("projectId",projectId)
                        .putExtra("rectType",1));
            }
        });
        lin_risk_quality_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail.this, Act_SafetyQualityCivilization.class)
                        .putExtra("projectName",projectName)
                        .putExtra("projectId",projectId)
                        .putExtra("rectType",2));
            }
        });
        lin_risk_civilized_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_RankDetail.this, Act_SafetyQualityCivilization.class)
                        .putExtra("projectName",projectName)
                        .putExtra("projectId",projectId)
                        .putExtra("rectType",3));
            }
        });
    }

    @Override
    public void initData() {
        calendar = Calendar.getInstance(Locale.CHINA);
        year = calendar.get(Calendar.YEAR); // 获取Calendar对象中的年
        month = calendar.get(Calendar.MONTH);// 获取Calendar对象中的月
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
        axisX.setName("日期(" + year + "年" + (month + 1) + "月)");
        if(rbType == 1){
            axisY.setName("使用人数");
        }else {
            axisY.setName("风险数量");
        }
//        axisX.setTextSize(10);//设置字体大小
//        axisY.setMaxLabelChars(7); //最多几个y轴坐标，意思就是你的缩放让X轴上数据的个数7<=y<=mAxisyValues.length
        data.setAxisXBottom(axisX); //x 轴在底部  （顶部底部一旦设置就意味着x轴）
        data.setAxisYLeft(axisY);
        if(mchartview != null){
            mchartview.setLineChartData(data);
            mchartview.setZoomType(ZoomType.HORIZONTAL);
            mchartview.setZoomEnabled(false);
        }
    }

    @Override
    public void processClick(View v) {

    }
}
