package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.HiddenBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.PieData;
import com.a21zhewang.constructionapp.bean.RiskHidden;
import com.a21zhewang.constructionapp.bean.RiskHiddenBean;
import com.a21zhewang.constructionapp.bean.RiskHiddenPie;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.provider.PieChartDataProvider;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * 风险隐患
 */
public class RiskHiddenDangerFragment extends Fragment {
    LinearLayout lin_columnchart_layout;
    LinearLayout lin_piechart_layout;
    LinearLayout lin_piechart_layout_0;
    private RadioGroup radiogroup;
    private RadioButton rb1,rb2,rb3,rb4;
    private View v;
    private int id;
    private ArrayList<String> tabTitles = new ArrayList<>();
    private String projectId;
    private boolean hasInitData;
    private boolean hasLabels = false;
    private boolean hasPieLabels = false;
    private boolean hasLabelForSelected = false;
    private ColumnChartData data;
    private PieChartView piechart;
    private PieChartView piechart0;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private ColumnChartView columnchart;
    private RiskHidden bean;
    private List<RiskHiddenBean> riskHiddenBeanList = new ArrayList<>();
    private List<HiddenBean> hiddenBeanList = new ArrayList<>();
    private List<Column> columns = new ArrayList<Column>();;
    private List<SubcolumnValue> values;
    private List<AxisValue> axisXValues;
//    private List<AxisValue> axisYValues;
    private String statusName;
    private RiskHiddenPie riskHiddenPie;
    private List<PieData> pieDataList = new ArrayList<>();
    private List<SliceValue> pieValues;
    private boolean hasLabelsOutside = false;
    private PieChartData pieData;
    private boolean hasCenterCircle = false;
    private boolean isExploded = false;
    private boolean hasCenterText1 = true;
    private int level;
    private String projectAreaName="";
    private String typeGraFaName = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.riskhiddendangerfragment, null, false);
            lin_piechart_layout_0 = (LinearLayout)v.findViewById(R.id.lin_piechart_layout_0);
            lin_columnchart_layout = (LinearLayout)v.findViewById(R.id.lin_columnchart_layout);
            lin_piechart_layout = (LinearLayout)v.findViewById(R.id.lin_piechart_layout);
            columnchart = (ColumnChartView) v.findViewById(R.id.columnchart);
            piechart = (PieChartView)v.findViewById(R.id.piechart);
            piechart0 = (PieChartView)v.findViewById(R.id.piechart0);

            rb1 = (RadioButton)v.findViewById(R.id.risk_rb1);
            rb2 = (RadioButton)v.findViewById(R.id.risk_rb2);
            rb3 = (RadioButton)v.findViewById(R.id.risk_rb3);
            rb4 = (RadioButton)v.findViewById(R.id.risk_rb4);
            radiogroup = (RadioGroup)v.findViewById(R.id.radiogroup);
        }else {
            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
        }
        columnchart.setOnValueTouchListener(new ValueTouchListener());
        piechart.setOnValueTouchListener(new PieValueTouchListener());
        piechart0.setOnValueTouchListener(new PieValueTouchListener());
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(hiddenBeanList.size()>0){
//                    hiddenBeanList.clear();
//                }
                if(columns.size()>0){
                    columns.clear();
                }
                if(bean != null){
                    if(rb1.isChecked()){
                        statusName = bean.getStatusList().get(0).getStatusName();
                        hiddenBeanList = bean.getStatusList().get(0).getData();
                    }
                    if(rb2.isChecked()){
                        statusName = bean.getStatusList().get(1).getStatusName();
                        hiddenBeanList = bean.getStatusList().get(1).getData();
                    }
                    if(rb3.isChecked()){
                        statusName = bean.getStatusList().get(2).getStatusName();
                        hiddenBeanList = bean.getStatusList().get(2).getData();
                    }
                    if(rb4.isChecked()){
                        statusName = bean.getStatusList().get(3).getStatusName();
                        hiddenBeanList = bean.getStatusList().get(3).getData();
                    }
                    if(id == 1){
                        lin_piechart_layout.setVisibility(View.INVISIBLE);
                        lin_piechart_layout_0.setVisibility(View.GONE);
                        generateDefaultData(hiddenBeanList);
                    }else {
                        lin_piechart_layout.setVisibility(View.INVISIBLE);
                        lin_piechart_layout_0.setVisibility(View.VISIBLE);
                        toggleLabels0(hiddenBeanList);
                    }
                }

            }
        });
        return v;
    }
    public void initData() {
        id = getArguments().getInt("id",0);
        projectId = getArguments().getString("projectId");
        if (!hasInitData) {
            loadData(true);
            hasInitData = true;
        }
    }
    public void refresh() {
        loadData(true);
    }
    private void loadData(boolean needDialog) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 1 + "\",\"projectId\":\"" + projectId +  "\",\"rectType\":\"" + id + "\"}"),
                "GetDataTotal2_rectCheckRectTotal", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onResule(String res) {
                try {
                    bean = JsonUtils.getbean(res,RiskHidden.class);
                    riskHiddenBeanList = bean.getStatusList();
                    hiddenBeanList = bean.getStatusList().get(0).getData();
                    statusName = bean.getStatusList().get(0).getStatusName();
                    if(hiddenBeanList.size() == 0){
                        radiogroup.setVisibility(View.GONE);
                        lin_columnchart_layout.setVisibility(View.GONE);
                       return;
                    }else {
                        if(id == 1){
                            radiogroup.setVisibility(View.VISIBLE);
                            lin_piechart_layout_0.setVisibility(View.GONE);
                            lin_columnchart_layout.setVisibility(View.VISIBLE);
                            for(int i=0;i<riskHiddenBeanList.size();i++){
                                rb1.setText(bean.getStatusList().get(0).getStatusName());
                                rb2.setText(bean.getStatusList().get(1).getStatusName());
                                rb3.setText(bean.getStatusList().get(2).getStatusName());
                                rb4.setText(bean.getStatusList().get(3).getStatusName());
                            }
                            generateDefaultData(hiddenBeanList);
                        }else {
                            radiogroup.setVisibility(View.VISIBLE);
                            lin_piechart_layout_0.setVisibility(View.VISIBLE);
                            lin_columnchart_layout.setVisibility(View.GONE);
                            for(int i=0;i<riskHiddenBeanList.size();i++){
                                rb1.setText(bean.getStatusList().get(0).getStatusName());
                                rb2.setText(bean.getStatusList().get(1).getStatusName());
                                rb3.setText(bean.getStatusList().get(2).getStatusName());
                                rb4.setText(bean.getStatusList().get(3).getStatusName());
                            }
                            toggleLabels0(hiddenBeanList);
                        }

                    }
//                    bean.getStatusList().get(0).getData().get(0).setNum(2);
//                    bean.getStatusList().get(0).getData().get(1).setNum(4);
//                    bean.getStatusList().get(0).getData().get(2).setNum(6);
//                    bean.getStatusList().get(0).getData().get(3).setNum(8);
//                    bean.getStatusList().get(0).getData().get(4).setNum(10);
//                    bean.getStatusList().get(1).getData().get(0).setNum(1);
//                    bean.getStatusList().get(1).getData().get(1).setNum(3);
//                    bean.getStatusList().get(1).getData().get(2).setNum(5);
//                    bean.getStatusList().get(1).getData().get(3).setNum(7);
//                    bean.getStatusList().get(1).getData().get(4).setNum(9);
//                    bean.getStatusList().get(2).getData().get(0).setNum(1);
//                    bean.getStatusList().get(2).getData().get(1).setNum(2);
//                    bean.getStatusList().get(2).getData().get(2).setNum(3);
//                    bean.getStatusList().get(2).getData().get(3).setNum(4);
//                    bean.getStatusList().get(2).getData().get(4).setNum(5);
//                    bean.getStatusList().get(3).getData().get(0).setNum(6);
//                    bean.getStatusList().get(3).getData().get(1).setNum(7);
//                    bean.getStatusList().get(3).getData().get(2).setNum(8);
//                    bean.getStatusList().get(3).getData().get(3).setNum(9);
//                    bean.getStatusList().get(3).getData().get(4).setNum(10);

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
    private void generateDefaultData(List<HiddenBean> list) {
            int numColumns = list.size();
            axisXValues = new ArrayList<>();
//            axisYValues = new ArrayList<>();
            for (int i = 0; i < numColumns; ++i) {
                values = new ArrayList<SubcolumnValue>();
                values.add(new SubcolumnValue((float) list.get(i).getNum(), getResources().getColor(R.color.color_theme)));
//                axisYValues.add(new AxisValue(i).setValue(list.get(i).getNum()));
                axisXValues.add(new AxisValue(i).setLabel(list.get(i).getLevel()));
                Column column = new Column(values);
                column.setHasLabels(true); //显示Y轴值
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }
            data = new ColumnChartData(columns);
            if (hasAxes) {
                   Axis axisBottom = new Axis(axisXValues);
//                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisBottom.setName("风险等级");
                    //是否显示X轴的网格线
                    axisBottom.setHasLines(false);
                    //分割线颜色
                    axisBottom.setTextColor(getResources().getColor(R.color.color_666666));
                    axisBottom.setLineColor(Color.parseColor("#ff0000"));
                    //距离各标签之间的距离,包括离Y轴间距 (0-32之间)
                    axisBottom.setMaxLabelChars(5);
                    axisBottom.setHasSeparationLine(true);
                    axisBottom.setName("风险等级");
//                    axisY.setValues(axisYValues);
                    axisY.setHasLines(false);
                    axisY.setTextColor(getResources().getColor(R.color.color_666666));
//                    axisY.setName("风险数量");
                }
                //设置x轴在底部显示
                data.setAxisXBottom(axisBottom);
                data.setAxisYLeft(axisY);
                data.setFillRatio(0.5F);
                data.setValueLabelsTextColor(Color.WHITE);
//                data.setValueLabelBackgroundEnabled(false);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }
            columnchart.setZoomEnabled(false);////设置是否支持缩放
            columnchart.setMinimumWidth(3);
            columnchart.setColumnChartData(data);
    }
    private void loadPieData(int level,String name){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 1 + "\",\"projectId\":\"" + projectId +  "\",\"level\":\"" + level + "\",\"statusName\":\"" + name + "\"}"),
                "GetDataTotal2_rectCheckRectGraFaTotal", new MyCallBack() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onResule(String res) {
                        try {
                            if(pieDataList.size()>0){
                                pieDataList.clear();
                            }
                            JSONObject object = new JSONObject(res);
                            riskHiddenPie = JsonUtils.getbean(res,RiskHiddenPie.class);
                            pieDataList = riskHiddenPie.getList();
//                            PieData data = new PieData();
//                            data.setNum(50);
//                            data.setTypeFatherName("地下结构");
//                            pieDataList.add(data);
//
//                            PieData data2 = new PieData();
//                            data2.setNum(60);
//                            data2.setTypeFatherName("土木工程");
//                            pieDataList.add(data2);
//
//                            PieData data3 = new PieData();
//                            data3.setNum(46);
//                            data3.setTypeFatherName("脚手架");
//                            pieDataList.add(data3);
//
//                            PieData data4 = new PieData();
//                            data4.setNum(80);
//                            data4.setTypeFatherName("模板工程");
//                            pieDataList.add(data4);
                            toggleLabels(pieDataList);
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

    /**
     * 安全-柱状关联的饼图
     * @param pieDataList
     */
    private void toggleLabels(List<PieData> pieDataList) {
        hasPieLabels = true;
        pieValues = new ArrayList<SliceValue>();
        for(int i=0;i<pieDataList.size();i++){
            SliceValue sliceValue = new SliceValue(
                    (float) pieDataList.get(i).getNum(),ChartUtils.pickColor());//ChartUtils.pickColor()
            sliceValue.setLabel(pieDataList.get(i).getTypeFatherName()+"("+pieDataList.get(i).getNum()+")");
            pieValues.add(sliceValue);
        }
        pieData = new PieChartData(pieValues);
        pieData.setHasLabels(hasPieLabels);
        pieData.setCenterText1("风险等级");
        pieData.setCenterText1FontSize(18);
        piechart.setViewportCalculationEnabled(true); //自适应大小
        pieData.setCenterText1Color(R.color.color_green_chart);
        pieData.setHasLabelsOnlyForSelected(hasLabelForSelected);
        pieData.setHasLabelsOutside(hasLabelsOutside);
        pieData.setHasCenterCircle(hasCenterCircle);
        pieData.setValueLabelBackgroundEnabled(false);
        if (isExploded) {
            pieData.setSlicesSpacing(24);
        }
        piechart.setPieChartData(pieData);
    }
    /**
     * 质量、文明默认显示饼图
     * @param hiddenBeanList
     */
    private void toggleLabels0( List<HiddenBean> hiddenBeanList) {
        hasPieLabels = true;
        pieValues = new ArrayList<SliceValue>();
        for(int i=0;i<hiddenBeanList.size();i++){
            SliceValue sliceValue = new SliceValue(
                    (float) hiddenBeanList.get(i).getNum(),getResources().getColor(R.color.color_theme));//ChartUtils.pickColor()
            sliceValue.setLabel(hiddenBeanList.get(i).getLevel()+"("+hiddenBeanList.get(i).getNum()+")");
            pieValues.add(sliceValue);
        }
        pieData = new PieChartData(pieValues);
        pieData.setHasLabels(hasPieLabels);
        pieData.setCenterText1("风险分布");
        pieData.setCenterText1FontSize(18);
        pieData.setCenterText1Color(R.color.color_green_chart);
        pieData.setHasLabelsOnlyForSelected(hasLabelForSelected);
        pieData.setHasLabelsOutside(hasLabelsOutside);
        pieData.setHasCenterCircle(hasCenterCircle);
        pieData.setValueLabelBackgroundEnabled(false);
        if (isExploded) {
            pieData.setSlicesSpacing(24);
        }
        piechart0.setPieChartData(pieData);
    }
    /**
     * 质量、文明默认显示饼图
     * @param hiddenBeanList
     */
    private void toggleLabels2( List<HiddenBean> hiddenBeanList) {
        hasPieLabels = true;
        pieValues = new ArrayList<SliceValue>();
        for(int i=0;i<hiddenBeanList.size();i++){
            SliceValue sliceValue = new SliceValue(
                    (float) hiddenBeanList.get(i).getNum(),getResources().getColor(R.color.color_theme));//ChartUtils.pickColor()
            sliceValue.setLabel(hiddenBeanList.get(i).getLevel()+"("+hiddenBeanList.get(i).getNum()+")");
            pieValues.add(sliceValue);
        }
        pieData = new PieChartData(pieValues);
        pieData.setHasLabels(hasPieLabels);
        pieData.setCenterText1("风险分布");
        pieData.setCenterText1FontSize(18);
        pieData.setCenterText1Color(R.color.color_green_chart);
        pieData.setHasLabelsOnlyForSelected(hasLabelForSelected);
        pieData.setHasLabelsOutside(hasLabelsOutside);
        pieData.setHasCenterCircle(hasCenterCircle);
        if (isExploded) {
            pieData.setSlicesSpacing(24);
        }
        piechart.setPieChartData(pieData);
    }

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            lin_piechart_layout.setVisibility(View.VISIBLE);
            level = columnIndex + 1; //风险等级
            loadPieData(level,statusName);
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
    private class PieValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {

            if(id == 1){
                if(pieDataList.size() != 0 ){
                    typeGraFaName = pieDataList.get(arcIndex).getTypeFatherName();
                }
            }else {
                if(hiddenBeanList.size() != 0 ){
                    typeGraFaName = hiddenBeanList.get(arcIndex).getLevel();
                }
            }
            startActivity(new Intent(getActivity(),Act_Rectification.class)
                    .putExtra("dateType",1)
                    .putExtra("projectId",projectId)
                    .putExtra("rectType",id)
                    .putExtra("level",level)
                    .putExtra("typeGraFaName",typeGraFaName)
                    .putExtra("projectAreaName","") //施工区域
                    .putExtra("statusName",statusName));
//            if(pieDataList.size() != 0 ){
//                if(id == 1){
//                    typeGraFaName = pieDataList.get(arcIndex).getTypeFatherName();
//                }
//                if(id == 2){
//                    typeGraFaName = hiddenBeanList.get(arcIndex).getLevel();
//                }
//                if(id == 3){
//                    typeGraFaName = hiddenBeanList.get(arcIndex).getLevel();
//                }
//                startActivity(new Intent(getActivity(),Act_Rectification.class)
//                        .putExtra("projectId",projectId)
//                        .putExtra("rectType",id)
//                        .putExtra("level",level)
//                        .putExtra("typeGraFaName",typeGraFaName)
//                        .putExtra("projectAreaName","") //施工区域
//                        .putExtra("statusName",statusName));
//            }
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }
}
