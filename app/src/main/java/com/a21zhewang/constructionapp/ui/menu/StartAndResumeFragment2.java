package com.a21zhewang.constructionapp.ui.menu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseLazyFragment;
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
 * Created by wp-pc on 2020/4/4.
 */

public class StartAndResumeFragment2 extends BaseLazyFragment{
    private LineChartView chartview;
    private LineChartData data;
    private List<Line> lines = new ArrayList<Line>();
    private ArrayList<AxisValue> axisValuesX;
    private ArrayList<AxisValue> axisValuesY;
    float[][] randomNumbersTab = new float[2][7];
    @Nullable

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.startandresume_fragment,container,false);
        chartview = (LineChartView) view.findViewById(R.id.yq_chart);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "1" + "\",\"dateTime\":\"" + DateUtils.getTodayDate() +"\"}"), "GetResumWorkManageEquipment", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try{
//                    res = "{\n" +
//                            "    \"resumptionWorkList\": [\n" +
//                            "        {\n" +
//                            "            \"day\": \"01\",\n" +
//                            "            \"addUser\": 2,\n" +
//                            "            \"totalUser\": 4\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"day\": \"02\",\n" +
//                            "            \"addUser\": 1,\n" +
//                            "            \"totalUser\": 6\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"day\": \"03\",\n" +
//                            "            \"addUser\": 3,\n" +
//                            "            \"totalUser\": 8\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"day\": \"04\",\n" +
//                            "            \"addUser\": 4,\n" +
//                            "            \"totalUser\": 2\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"day\": \"05\",\n" +
//                            "            \"addUser\": 1,\n" +
//                            "            \"totalUser\": 7\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"day\": \"06\",\n" +
//                            "            \"addUser\": 3,\n" +
//                            "            \"totalUser\": 5\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"day\": \"07\",\n" +
//                            "            \"addUser\": 2,\n" +
//                            "            \"totalUser\": 3\n" +
//                            "        }\n" +
//                            "    ]\n" +
//                            "}";
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("resumptionWorkList");
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
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < 2; ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            axisValuesX = new ArrayList<AxisValue>();
            axisValuesY = new ArrayList<AxisValue>();
            for (int j = 0; j < array.length(); ++j) {
                JSONObject tempobj = array.optJSONObject(j);
                randomNumbersTab[0][j] = (float)tempobj.optInt("addUser");
                randomNumbersTab[1][j] = (float)tempobj.optInt("totalUser");
                values.add(new PointValue(j, randomNumbersTab[i][j]));
                String week = tempobj.optString("day");
                String weekdays = week.substring(week.length() - 2, week.length());
                axisValuesX.add(new AxisValue(j).setValue(j).setLabel((weekdays.replace("-", "") + "号").toCharArray()));// 为每个X轴点设置别名
            }
            Line line = new Line(values);
            if(i==0){
                line.setColor(getResources().getColor(R.color.color_theme));
                line.setPointColor(getResources().getColor(R.color.color_theme));
            }else {
                line.setColor(getResources().getColor(R.color.color_red));
                line.setPointColor(getResources().getColor(R.color.color_red));
            }
            line.setShape(ValueShape.CIRCLE);
            line.setCubic(true);
            line.setFilled(true);
            line.setHasLabels(true);
            line.setHasLabelsOnlyForSelected(false);
            line.setHasLines(true);
            line.setHasPoints(true);
//            line.setStrokeWidth(1);// 连线的宽度，DP单位
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
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        data.setValueLabelTextSize(10);
        data.setValueLabelsTextColor(getResources().getColor(R.color.color_666666));
        data.setValueLabelBackgroundEnabled(false);
        data.setValueLabelTypeface(Typeface.DEFAULT);
        chartview.setLineChartData(data);
    }
}
