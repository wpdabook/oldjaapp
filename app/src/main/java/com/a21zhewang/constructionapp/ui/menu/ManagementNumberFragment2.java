package com.a21zhewang.constructionapp.ui.menu;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by wp-pc on 2020/4/4.
 */

public class ManagementNumberFragment2 extends BaseLazyFragment{
    private PieChartView pieChartView;
    private PieChartData data;
    private List<SliceValue> values;
    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.managementnumber_fragment,container,false);
        pieChartView = (PieChartView) view.findViewById(R.id.piechart);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "2" + "\",\"dateTime\":\"" + DateUtils.getTodayDate() +"\"}"), "GetResumWorkManageEquipment", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try{
//                    res = "{\n" +
//                            "    \"resumptionManager\": [\n" +
//                            "        {\n" +
//                            "            \"buildUnitNum\": 30,\n" +
//                            "            \"supervisorUnitNum\": 20,\n" +
//                            "            \"constructionUnitNum\": 50\n" +
//                            "        }\n" +
//                            "    ]\n" +
//                            "}";
                    JSONObject object = new JSONObject(res);
                    generateData(object);
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

    public void initData() {

    }
    private void generateData(JSONObject object) {
        values = new ArrayList<SliceValue>();
            SliceValue sliceValue1 = new SliceValue(object.optInt("buildUnitNum"),
                    getResources().getColor(R.color.pie_blue));
            sliceValue1.setLabel(object.optInt("buildUnitNum")+"");

            SliceValue sliceValue2 = new SliceValue(object.optInt("constructionUnitNum"),
                    getResources().getColor(R.color.pie_green));
            sliceValue2.setLabel(object.optInt("constructionUnitNum")+"");

            SliceValue sliceValue3 = new SliceValue(object.optInt("supervisorUnitNum"),
                    getResources().getColor(R.color.pie_zi));
            sliceValue3.setLabel(object.optInt("supervisorUnitNum")+"");

            values.add(sliceValue1);
            values.add(sliceValue2);
            values.add(sliceValue3);
        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);
        data.setValueLabelBackgroundEnabled(false);
        data.setValueLabelsTextColor(getResources().getColor(R.color.white));
        if (isExploded) {
            data.setSlicesSpacing(24);
        }
        pieChartView.setPieChartData(data);
    }
}
