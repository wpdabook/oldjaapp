package com.a21zhewang.constructionapp.ui.menu;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 施工总人数
 * Created by WP-PC on 2020/5/6.
 */

public class ConstructionFragment extends Fragment {
    private LineChartView chartview;
    private LineChartData data;
    private List<Line> lines = new ArrayList<Line>();
    private ArrayList<AxisValue> axisValuesX;
    private ArrayList<AxisValue> axisValuesY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.construct_fragment,container,false);
        chartview = (LineChartView) view.findViewById(R.id.cons_chart);
        loadData();
        return view;
    }
    protected void loadData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + 1 + "\",\"areaKey\":\"" + ((Act_Menu)getActivity()).getAreaKey() + "\"}"), "GetCitySiteTrend", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try{
//                    res = "{\n" +
//                            "    \"constrList\": [\n" +
//                            "        {\n" +
//                            "            \"date\": \"01\",\n" +
//                            "            \"addTotal\": 2,\n" +
//                            "            \"allTotal\": 4\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"date\": \"02\",\n" +
//                            "            \"addTotal\": 1,\n" +
//                            "            \"allTotal\": 6\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"date\": \"03\",\n" +
//                            "            \"addTotal\": 3,\n" +
//                            "            \"allTotal\": 8\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"date\": \"04\",\n" +
//                            "            \"addTotal\": 4,\n" +
//                            "            \"allTotal\": 2\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"date\": \"05\",\n" +
//                            "            \"addTotal\": 1,\n" +
//                            "            \"allTotal\": 7\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"date\": \"06\",\n" +
//                            "            \"addTotal\": 3,\n" +
//                            "            \"allTotal\": 5\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"date\": \"07\",\n" +
//                            "            \"addTotal\": 2,\n" +
//                            "            \"allTotal\": 3\n" +
//                            "        }\n" +
//                            "    ]\n" +
//                            "}";
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("constrList");
                    generateData(array);
                }catch (JSONException e){
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
    private void generateData(JSONArray array) {
        float[][] randomNumbersTab = new float[2][array.length()];
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < 2; ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            axisValuesX = new ArrayList<AxisValue>();
            axisValuesY = new ArrayList<AxisValue>();
            for (int j = 0; j < array.length(); ++j) {
                JSONObject tempobj = array.optJSONObject(j);
                randomNumbersTab[0][j] = (float)tempobj.optInt("addTotal");
                randomNumbersTab[1][j] = (float)tempobj.optInt("allTotal");
                values.add(new PointValue(j, randomNumbersTab[i][j]));
                String week = tempobj.optString("date");
                String weekdays = week.substring(week.length() - 2, week.length());
                axisValuesX.add(new AxisValue(j).setValue(j).setLabel((weekdays.replace("-", "") + "号").toCharArray()));// 为每个X轴点设置别名
            }
            Line line = new Line(values);
            if(i==0){
                line.setColor(Color.argb(255,240,95,76));
                line.setPointColor(Color.argb(255,240,95,76));
            }else {
                line.setColor(Color.argb(255,3,169,244));
                line.setPointColor(Color.argb(255,3,169,244));
            }
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(false); //点上的标注信息，刚才 pointValues里面每个点的标注
            line.setFilled(true);
            line.setHasLabels(true);
            line.setHasLabelsOnlyForSelected(false);
            line.setHasLines(true);
            line.setHasPoints(true);
            line.setStrokeWidth(2);
            // 连线的宽度，DP单位
            lines.add(line);
        }
        data = new LineChartData(lines);
        if (true) {
            Axis axisX = new Axis();
            axisX.setValues(axisValuesX);
//            axisX.setMaxLabelChars(7);
            Axis axisY = new Axis().setHasLines(true);
//            axisY.setValues(axisValuesY);
//            if (true) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
//            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(null);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        if(array.length() != 0){
            data.setBaseValue(Float.NEGATIVE_INFINITY);
            data.setValueLabelTextSize(10);
            data.setValueLabelsTextColor(Color.argb(255,102,102,102));
            data.setValueLabelBackgroundEnabled(false);
            data.setValueLabelTypeface(Typeface.DEFAULT);
            chartview.setAlpha(0.75f);//设置透明度
            chartview.setLineChartData(data);
        }
    }
}
