package com.a21zhewang.constructionapp.ui.tab;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.ChartAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.RecordProjectList;
import com.a21zhewang.constructionapp.bean.RiskHistory;
import com.a21zhewang.constructionapp.bean.RiskQueryLaunProjectList;
import com.a21zhewang.constructionapp.customview.CircleTextImageView;
import com.a21zhewang.constructionapp.customview.CircularProgressView;
import com.a21zhewang.constructionapp.customview.NoScrollListView;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.bean.RiskQueryLaunTimeList;
import com.a21zhewang.constructionapp.bean.RiskQueryTimeUserList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import static android.app.Activity.RESULT_OK;

/**
 * ??????????????????
 */
public class Act_RiskHistory extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView title_back_img;
    @BindView(R.id.select_risk_project)
    TextView checkprojectTV;
    @BindView(R.id.search_layout)
    RelativeLayout search_layout;
    @BindView(R.id.listchart)
    NoScrollListView listchart;
    @BindView(R.id.rg_date_type)
    RadioGroup radiotype;
    @BindView(R.id.rb_week)
    RadioButton rb1;
    @BindView(R.id.rb_mouth)
    RadioButton rb2;
    @BindView(R.id.linechart_one)
    LineChartView linechart_one;
    @BindView(R.id.linechart_two)
    LineChartView linechart_two;
    @BindView(R.id.safe_tv_1)
    TextView tv_safe_1;
    @BindView(R.id.safe_tv_2)
    TextView tv_safe_2;
    @BindView(R.id.safe_rate)
    CircularProgressView safe_rate;
    @BindView(R.id.quality_tv_1)
    TextView quality_tv_1;
    @BindView(R.id.quality_tv_2)
    TextView quality_tv_2;
    @BindView(R.id.quality_rate)
    CircularProgressView quality_rate;
    @BindView(R.id.civilization_tv_1)
    TextView civilization_tv_1;
    @BindView(R.id.civilization_tv_2)
    TextView civilization_tv_2;
    @BindView(R.id.civilization_rate)
    CircularProgressView civilization_rate;
    @BindView(R.id.daily_check)
    TextView daily_check;
    @BindView(R.id.daily_general_inspection_items)
    TextView daily_general_inspection_items;
    @BindView(R.id.days_rate)
    CircularProgressView days_rate;
    @BindView(R.id.week_of_screening)
    TextView week_of_screening;
    @BindView(R.id.week_of_screening_total)
    TextView week_of_screening_total;
    @BindView(R.id.week_rate)
    CircularProgressView week_rate;
    @BindView(R.id.on_the_screen)
    TextView on_the_screen;
    @BindView(R.id.on_the_screen_total)
    TextView on_the_screen_total;
    @BindView(R.id.month_rate)
    CircularProgressView month_rate;
    @BindView(R.id.risk_number_total)
    TextView risk_number_total;
    @BindView(R.id.risk_number_total_rate)
    TextView risk_number_total_rate;
    @BindView(R.id.risk_total_user_number)
    TextView risk_total_user_number;
    @BindView(R.id.risk_yinhuan_total)
    TextView risk_yinhuan_total;
    @BindView(R.id.riskhistory_title)
    LinearLayout riskhistory_title;
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    private JSONArray projectarray;
    private JSONObject projectObject;
    private int dateType = 1;
    private int launTop = 5; //5:????????????5????????? 0:????????????ID??????????????????
    private RiskHistory riskHistory;
    private ChartAdapter mAdapter;
    private Dialog loadingDialog;
    private List<RiskQueryLaunProjectList> riskQueryLaunProjectList = new ArrayList<>();
    private List<RiskQueryLaunTimeList> riskQueryLaunTimeList = new ArrayList<>();
    private List<RiskQueryTimeUserList> riskQueryTimeUserListList = new ArrayList<>();
    private List<PointValue> pointList1 = new ArrayList<PointValue>();
    private List<AxisValue> axisValues1 = new ArrayList<AxisValue>();
    private List<PointValue> pointList2 = new ArrayList<PointValue>();
    private List<AxisValue> axisValues2 = new ArrayList<AxisValue>();
    private Calendar calendar;
    private int year;
    private int month;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_riskhistory;
    }

    @Override
    public void initViews() {
        title.setText("??????????????????");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
        checkprojectTV.setText("?????????"+DateUtils.getTodayDate());
        title_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Act_RiskHistory.this,Act_SelectRiskProject.class)
                        .putExtra("projectId","123"),Act_SelectRiskProject.RESQUST_CODE_SELECT_NOTICE_COMPANY);
            }
        });
        radiotype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                loadingDialog.show();
                if(rb1.isChecked()){
                    dateType = 1;
                    initData();
                }
                if(rb2.isChecked()){
                    dateType = 2;
                    initData();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    projectarray = new JSONArray();
                    launTop = 5;
                    initData();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //??????
        if (requestCode == Act_SelectRiskProject.RESQUST_CODE_SELECT_NOTICE_COMPANY
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
//            checkprojectTV.setText(strs);
            launTop = 0;
            List<RecordProjectList> noticeCompanylist = data.getParcelableArrayListExtra("list");
            projectarray = new JSONArray();
            for(int i=0;i<noticeCompanylist.size();i++){
                if(noticeCompanylist.get(i).isChecked() == true){
                    projectObject = new JSONObject();
                    try{
                        projectObject.put("projectId",noticeCompanylist.get(i).getProjectId());
                        projectarray.put(projectObject);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            checkBox.setChecked(false);
            initData();


        }
    }

    @Override
    public void initListener() {
        calendar = Calendar.getInstance(Locale.CHINA);
        year = calendar.get(Calendar.YEAR); // ??????Calendar???????????????
        month = calendar.get(Calendar.MONTH);// ??????Calendar???????????????
    }

    @Override
    public void initData() {
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("dateType",dateType);
            jsonObject.put("paramType","1");
            jsonObject.put("launTop",launTop);
            if(projectarray != null && projectarray.length() != 0){
                jsonObject.put("projectIds",projectarray);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
               loadingDialog.dismiss();
                if(riskQueryLaunProjectList.size()>0){
                    riskQueryLaunProjectList.clear();
                }
                riskHistory = JsonUtils.getbean(res, RiskHistory.class);
                riskQueryLaunProjectList = riskHistory.getRiskQueryLaunProjectList();
                if(riskQueryLaunProjectList.size() == 0){
                    riskhistory_title.setVisibility(View.GONE);
                }else {
                    riskhistory_title.setVisibility(View.VISIBLE);
                }
//                RiskQueryLaunProjectList besn = new RiskQueryLaunProjectList();
//                besn.setProjectName("????????????");
//                besn.setRiskQueryLaun(27);
//                besn.setRiskQueryPass(13);
//                riskQueryLaunProjectList.add(besn);
//
//                RiskQueryLaunProjectList besn2 = new RiskQueryLaunProjectList();
//                besn2.setProjectName("????????????");
//                besn2.setRiskQueryLaun(35);
//                besn2.setRiskQueryPass(16);
//                riskQueryLaunProjectList.add(besn2);
//
//                RiskQueryLaunProjectList besn3= new RiskQueryLaunProjectList();
//                besn3.setProjectName("????????????");
//                besn3.setRiskQueryLaun(65);
//                besn3.setRiskQueryPass(73);
//                riskQueryLaunProjectList.add(besn3);
                mAdapter = new ChartAdapter(Act_RiskHistory.this,riskQueryLaunProjectList);
                risk_number_total.setText(riskHistory.getRiskQueryTotal()+"");
                BaseRateValues(risk_number_total_rate,riskHistory.getRiskQueryRate());
//
//                CircleImageBaseRateValues(safe_rate,riskHistory.getSafetyRate());
//                CircleImageBaseRateValues(quality_rate,riskHistory.getQualityRate());
//                CircleImageBaseRateValues(civilization_rate,riskHistory.getCivilizedRate());
//                CircleImageBaseRateValues(days_rate,riskHistory.getKeyExamDayItemRate());
//                CircleImageBaseRateValues(week_rate,riskHistory.getKeyExamWeekItemRate());
//                CircleImageBaseRateValues(month_rate,riskHistory.getKeyExamMonthItemRate());

//                tv_safe_1.setText("???????????????"+riskHistory.getSafetyPass());
//                quality_tv_1.setText("???????????????"+riskHistory.getQualityPass());
//                civilization_tv_1.setText("???????????????"+riskHistory.getCivilizedPass());
//                daily_check.setText("???????????????"+riskHistory.getKeyExamDayItemPass());
//                week_of_screening.setText("???????????????"+riskHistory.getKeyExamWeekItemPass());
//                on_the_screen.setText("???????????????"+riskHistory.getKeyExamMonthItemPass());

//                initCircleDate(safe_rate,"????????????",riskHistory.getSafetyRate());
//                initCircleDate(quality_rate,"????????????",riskHistory.getQualityRate());
//                initCircleDate(civilization_rate,"????????????",riskHistory.getCivilizedRate());
//
//                initCircleDate(days_rate,"????????????",riskHistory.getKeyExamDayItemRate());
//                initCircleDate(week_rate,"????????????",riskHistory.getKeyExamWeekItemRate());
//                initCircleDate(month_rate,"????????????",riskHistory.getKeyExamMonthItemRate());

                safe_rate.setText("?????????",riskHistory.getSafetyPass()+"");
                safe_rate.setProgress((new Double(riskHistory.getSafetyRate())).intValue());
                quality_rate.setText("?????????",riskHistory.getQualityPass()+"");
                quality_rate.setProgress((new Double(riskHistory.getQualityRate())).intValue());
                civilization_rate.setText("?????????",riskHistory.getCivilizedPass()+"");
                civilization_rate.setProgress((new Double(riskHistory.getCivilizedRate())).intValue());
//                civilization_rate.setText("????????????",80+"");
//                civilization_rate.setProgress((new Double(80.69)).intValue());

                days_rate.setText("?????????",riskHistory.getKeyExamDayItemPass()+"");
                days_rate.setProgress((new Double(riskHistory.getKeyExamDayItemRate())).intValue());
                week_rate.setText("?????????",riskHistory.getKeyExamWeekItemPass()+"");
                week_rate.setProgress((new Double(riskHistory.getKeyExamWeekItemRate())).intValue());
                month_rate.setText("?????????",riskHistory.getKeyExamMonthItemPass()+"");
                month_rate.setProgress((new Double(riskHistory.getKeyExamMonthItemRate())).intValue());

                tv_safe_2.setText("???????????????"+riskHistory.getSafetyTotal());
                quality_tv_2.setText("???????????????"+riskHistory.getQualityTotal());
                civilization_tv_2.setText("???????????????"+riskHistory.getCivilizedTotal());

                daily_general_inspection_items.setText("???????????????"+riskHistory.getKeyExamDayItemTotal());
                week_of_screening_total.setText("???????????????"+riskHistory.getKeyExamWeekItemTotal());
                on_the_screen_total.setText("???????????????"+riskHistory.getKeyExamMonthItemTotal());

                if(dateType == 1){
                    risk_yinhuan_total.setText("??????????????????"+riskHistory.getRiskQueryLaunTimeTotal());
                    risk_total_user_number.setText("?????????????????????"+riskHistory.getRiskQueryTimeUserTotal());
                }else {
                    risk_yinhuan_total.setText("??????????????????"+riskHistory.getRiskQueryLaunTimeTotal());
                    risk_total_user_number.setText("?????????????????????"+riskHistory.getRiskQueryTimeUserTotal());
                }
                listchart.setAdapter(mAdapter);
                if(pointList1.size()>0){
                    pointList1.clear();
                }
                if(pointList2.size()>0){
                    pointList2.clear();
                }
                if(axisValues1.size()>0){
                    axisValues1.clear();
                }
                if(axisValues2.size()>0){
                    axisValues2.clear();
                }
                riskQueryLaunTimeList = riskHistory.getRiskQueryLaunTimeList();
                for(int i=0;i<riskQueryLaunTimeList.size();i++){
                    PointValue point = new PointValue(i,riskQueryLaunTimeList.get(i).getRiskQueryTotal()); // ?????????Y??????????????????
                    pointList1.add(point);
                    int week = riskQueryLaunTimeList.get(i).getBeginTime();
//                    String weekdays = week.substring(week.length() - 2, week.length());
//                    axisValues.add(new AxisValue(i).setLabel((weekdays.replace("-", "") + "???").toCharArray()));// ?????????X??????????????????
                    axisValues1.add(new AxisValue(i).setLabel(week + "???"));// ?????????X??????????????????
                }
                riskQueryTimeUserListList = riskHistory.getRiskQueryTimeUserList();
                for(int i=0;i<riskQueryTimeUserListList.size();i++){
                    PointValue point = new PointValue(i,riskQueryTimeUserListList.get(i).getUserTotal()); // ?????????Y??????????????????
                    pointList2.add(point);
                    int week = riskQueryTimeUserListList.get(i).getCreateTime();
                    axisValues2.add(new AxisValue(i).setLabel(week + "???"));// ?????????X??????????????????
                }
                linechatData1();
                linechatData2();
            }
            @Override
            public void onFail(ObjBean getbean) {
            }
            @Override
            public void onWrong() {
            }
        };
        XUtil.postjsondata(jsonObject,"GetDataTotal2_riskstatischeckTotal", callback);
    }
    public void initCircleDate(CircularProgressView view,String str,Double b){
        if(b == 0){
            view.setText(str,0+"%");
            view.setProgress(0);
        }else {
            view.setText(str,b+"%");
            view.setProgress(IntValues(b));
        }
    }
    private void linechatData1() {
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line(pointList1);
        line.setColor(getResources().getColor(R.color.tilebar_color));
        line.setShape(ValueShape.CIRCLE);// ????????????
        line.setCubic(true);// ?????????????????????????????????
        line.setFilled(true);// ????????????
        line.setHasLabels(true);// ????????????????????????????????????Y??????
        line.setHasLabelsOnlyForSelected(true);// ????????????????????????????????????????????????Y??????
        line.setHasLines(true);// ????????????
        line.setHasPoints(false);// ????????????
        line.setStrokeWidth(2);// ??????????????????DP??????
        lines.add(line);
        LineChartData data = new LineChartData(lines);
        Axis axisX = new Axis();
        axisX.setValues(axisValues1);
        axisX.setHasTiltedLabels(false);  //X??????????????????????????????????????????????????????true???????????????
//        axisX.setMaxLabelChars(7); //????????????x???????????????????????????????????????X?????????????????????7<=x<=mAxisXValues.length
        Axis axisY = new Axis().setHasLines(true);
//        axisX.setName("??????(" + year + "???" + (month + 1) + "???)");
        axisY.setName("?????????");
//        axisX.setTextSize(10);//??????????????????
//        axisY.setMaxLabelChars(7); //????????????y???????????????????????????????????????X?????????????????????7<=y<=mAxisyValues.length
        data.setAxisXBottom(axisX); //x ????????????  ???????????????????????????????????????x??????
        data.setAxisYLeft(axisY);
        linechart_one.setLineChartData(data);
        linechart_one.setZoomType(ZoomType.HORIZONTAL);
        linechart_one.setZoomEnabled(false);
    }
    private void linechatData2() {
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line(pointList2);
        line.setColor(getResources().getColor(R.color.tilebar_color));
        line.setShape(ValueShape.CIRCLE);// ????????????
        line.setCubic(true);// ?????????????????????????????????
        line.setFilled(true);// ????????????
        line.setHasLabels(true);// ????????????????????????????????????Y??????
        line.setHasLabelsOnlyForSelected(true);// ????????????????????????????????????????????????Y??????
        line.setHasLines(true);// ????????????
        line.setHasPoints(false);// ????????????
        line.setStrokeWidth(2);// ??????????????????DP??????
        lines.add(line);
        LineChartData data = new LineChartData(lines);
        Axis axisX = new Axis();
        axisX.setValues(axisValues2);
        axisX.setHasTiltedLabels(false);  //X??????????????????????????????????????????????????????true???????????????
//        axisX.setMaxLabelChars(7); //????????????x???????????????????????????????????????X?????????????????????7<=x<=mAxisXValues.length
        Axis axisY = new Axis().setHasLines(true);
//        axisX.setName("??????(" + year + "???" + (month + 1) + "???)");
        axisY.setName("????????????");
//        axisX.setTextSize(10);//??????????????????
//        axisY.setMaxLabelChars(7); //????????????y???????????????????????????????????????X?????????????????????7<=y<=mAxisyValues.length
        data.setAxisXBottom(axisX); //x ????????????  ???????????????????????????????????????x??????
        data.setAxisYLeft(axisY);
        linechart_two.setLineChartData(data);
        linechart_two.setZoomType(ZoomType.HORIZONTAL);
        linechart_two.setZoomEnabled(false);
    }
    public int  IntValues(Double b){
        int i = (new Double(b).intValue());
        return i;
    }
    @Override
    public void processClick(View v) {

    }
}
