package com.a21zhewang.constructionapp.ui.tab;

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

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by wp-pc on 2020/4/4.
 */

public class MechanicalEquipmentFragment extends Fragment {
    ColumnChartView columnview;
    private List<Double> columdate;
    private List<AxisValue> axisXValues;
    private List<Column> columns = new ArrayList<Column>();
    private ColumnChartData data;
    private List<SubcolumnValue> values;
    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    private boolean hasAxesNames = true;
    private boolean hasAxes = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mechanicalequipment_fragment, container, false);
        columnview = (ColumnChartView) view.findViewById(R.id.yq_columnchart);
        return view;
    }

    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "3" + "\",\"dateTime\":\"" + DateUtils.getTodayDate() + "\"}"), "GetResumWorkManageEquipment", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
//                    res = "{\n" +
//                            "    \"resumptionEquipmentList\": [\n" +
//                            "        {\n" +
//                            "            \"equipmentName\": \"?????????\",\n" +
//                            "            \"num\": 100\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"equipmentName\": \"??????\",\n" +
//                            "            \"num\": 80\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"equipmentName\": \"?????????\",\n" +
//                            "            \"num\": 60\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"equipmentName\": \"?????????\",\n" +
//                            "            \"num\": 40\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"equipmentName\": \"?????????\",\n" +
//                            "            \"num\": 20\n" +
//                            "        },\n" +
//                            "        {\n" +
//                            "            \"equipmentName\": \"??????\",\n" +
//                            "            \"num\": 10\n" +
//                            "        }\n" +
//                            "    ]\n" +
//                            "}";
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("resumptionEquipmentList");
                    generateDefaultData(array);
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

    private void generateDefaultData(JSONArray array) {
        int numSubcolumns = 1;
        axisXValues = new ArrayList<>();
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < array.length(); ++i) {
            JSONObject object = array.optJSONObject(i);
            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                //values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
                values.add(new SubcolumnValue((float) object.optInt("num"),getResources().getColor(R.color.color_orange)));
                axisXValues.add(new AxisValue(i).setLabel(object.optString("equipmentName")));
            }
            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);

        if (hasAxes) {
//            Axis axisX = new Axis();
            Axis axisBottom = new Axis(axisXValues);
            axisBottom.setHasTiltedLabels(true);
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
//                axisX.setName("Axis X");
                axisY.setName("??????");
                axisBottom.setTextColor(getResources().getColor(R.color.color_666666));
                axisBottom.setLineColor(Color.parseColor("#ff0000"));
                //??????????????????????????????,?????????Y????????? (0-32??????)
//                axisBottom.setMaxLabelChars(10);
//                axisBottom.setHasSeparationLine(true);
//                axisBottom.setName("????????????????????????????????????");
//                    axisY.setValues(axisYValues);
                axisY.setHasLines(false);
                axisY.setTextColor(getResources().getColor(R.color.color_666666));
                axisY.setTextSize(10);
            }
//            data.setAxisXBottom(axisX);
            data.setAxisXBottom(axisBottom);
            data.setAxisYLeft(axisY);
            data.setFillRatio(0.35F);//???????????????????????????????????????????????????0???1??????
            data.setValueLabelTypeface(Typeface.DEFAULT);
            data.setValueLabelsTextColor(Color.WHITE);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        columnview.setAlpha(0.8f);//???????????????
        columnview.setZoomEnabled(false);////????????????????????????
        columnview.setZoomType(ZoomType.HORIZONTAL);
        columnview.setColumnChartData(data);
    }

}
