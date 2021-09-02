package com.a21zhewang.constructionapp.ui.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * 机械设备
 * Created by WP-PC on 2020/5/6.
 */

public class EquipmentFragment extends Fragment {
    private PieChartData data;
    private List<SliceValue> values;
    private boolean hasLabels = true;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = true;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;
    public static final int[]  colors = new int[]
            {Color.parseColor("#7cb5ec"), Color.parseColor("#666666"), Color.parseColor("#90ed7d"),
                    Color.parseColor("#f7a35c"), Color.parseColor("#8085e9"),Color.parseColor("#f15c80")
                    ,Color.parseColor("#e4d354"),Color.parseColor("#2b908f"),Color.parseColor("#f45b5b")};
    @BindView(R.id.eqpiechart)
    PieChartView pieChartView;
    @BindView(R.id.tag_01)
    TextView tag_01;
    @BindView(R.id.tag_02)
    TextView tag_02;
    @BindView(R.id.tag_03)
    TextView tag_03;
    @BindView(R.id.tag_04)
    TextView tag_04;
    @BindView(R.id.tag_05)
    TextView tag_05;
    @BindView(R.id.tag_06)
    TextView tag_06;
    @BindView(R.id.tag_07)
    TextView tag_07;
    @BindView(R.id.tag_08)
    TextView tag_08;
    @BindView(R.id.tag_09)
    TextView tag_09;
    @BindView(R.id.lin_one_line)
    LinearLayout lin_one_line;
    @BindView(R.id.lin_two_line)
    LinearLayout lin_two_line;
    @BindView(R.id.lin_three_line)
    LinearLayout lin_three_line;
    @BindView(R.id.lin_one)
    LinearLayout lin_one;
    @BindView(R.id.lin_two)
    LinearLayout lin_two;
    @BindView(R.id.lin_three)
    LinearLayout lin_three;
    @BindView(R.id.lin_four)
    LinearLayout lin_four;
    @BindView(R.id.lin_five)
    LinearLayout lin_five;
    @BindView(R.id.lin_sex)
    LinearLayout lin_sex;
    @BindView(R.id.lin_seven)
    LinearLayout lin_seven;
    @BindView(R.id.lin_eight)
    LinearLayout lin_eight;
    @BindView(R.id.lin_nine)
    LinearLayout lin_nine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equipment_fragment,container,false);
        ButterKnife.bind(this,view);
        loadData();
        return view;
    }

    protected void loadData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + 2 + "\",\"areaKey\":\"" + ((Act_Menu)getActivity()).getAreaKey() + "\"}"), "GetCitySiteTrend", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try{
                 /*   res = "{\n" +
                            "    \"machineList\": [\n" +
                            "        {\n" +
                            "            \"machineTotal\": 65,\n" +
                            "            \"machineName\": 搅拌机\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"machineTotal\": 70,\n" +
                            "            \"machineName\": 推土机\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"machineTotal\": 75,\n" +
                            "            \"machineName\":  斗车\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}";*/
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("machineList");
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
        values = new ArrayList<SliceValue>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            SliceValue sliceValue = new SliceValue(object.optInt("machineTotal"), colors[i]);
            if (i < 3) {
                lin_one_line.setVisibility(View.VISIBLE);
                lin_one.setVisibility(View.VISIBLE);
                tag_01.setText(array.optJSONObject(0).optString("machineName"));
                if (i == 1) {
                    lin_two.setVisibility(View.VISIBLE);
                    tag_02.setText(array.optJSONObject(1).optString("machineName"));
                } else if (i == 2) {
                    lin_two.setVisibility(View.VISIBLE);
                    lin_three.setVisibility(View.VISIBLE);
                    tag_02.setText(array.optJSONObject(1).optString("machineName"));
                    tag_03.setText(array.optJSONObject(2).optString("machineName"));
                }
            } else if (i >= 3 && i < 6) {
                lin_one_line.setVisibility(View.VISIBLE);
                lin_two_line.setVisibility(View.VISIBLE);
                lin_one.setVisibility(View.VISIBLE);
                lin_two.setVisibility(View.VISIBLE);
                lin_three.setVisibility(View.VISIBLE);
                lin_four.setVisibility(View.VISIBLE);
                tag_01.setText(array.optJSONObject(0).optString("machineName"));
                tag_02.setText(array.optJSONObject(1).optString("machineName"));
                tag_03.setText(array.optJSONObject(2).optString("machineName"));
                tag_04.setText(array.optJSONObject(3).optString("machineName"));
                if (i == 4) {
                    lin_five.setVisibility(View.VISIBLE);
                    tag_05.setText(array.optJSONObject(4).optString("machineName"));
                } else if (i == 5) {
                    lin_five.setVisibility(View.VISIBLE);
                    lin_sex.setVisibility(View.VISIBLE);
                    tag_05.setText(array.optJSONObject(4).optString("machineName"));
                    tag_06.setText(array.optJSONObject(5).optString("machineName"));
                }
            } else if (i >= 6 && i < 9) {
                lin_one_line.setVisibility(View.VISIBLE);
                lin_two_line.setVisibility(View.VISIBLE);
                lin_three_line.setVisibility(View.VISIBLE);
                lin_one.setVisibility(View.VISIBLE);
                lin_two.setVisibility(View.VISIBLE);
                lin_three.setVisibility(View.VISIBLE);
                lin_four.setVisibility(View.VISIBLE);
                lin_five.setVisibility(View.VISIBLE);
                lin_sex.setVisibility(View.VISIBLE);
                lin_seven.setVisibility(View.VISIBLE);
                tag_01.setText(array.optJSONObject(0).optString("machineName"));
                tag_02.setText(array.optJSONObject(1).optString("machineName"));
                tag_03.setText(array.optJSONObject(2).optString("machineName"));
                tag_04.setText(array.optJSONObject(3).optString("machineName"));
                tag_05.setText(array.optJSONObject(4).optString("machineName"));
                tag_06.setText(array.optJSONObject(5).optString("machineName"));
                tag_07.setText(array.optJSONObject(6).optString("machineName"));
                if (i == 7) {
                    lin_eight.setVisibility(View.VISIBLE);
                    tag_08.setText(array.optJSONObject(7).optString("machineName"));
                } else if (i == 8) {
                    lin_eight.setVisibility(View.VISIBLE);
                    lin_nine.setVisibility(View.VISIBLE);
                    tag_08.setText(array.optJSONObject(7).optString("machineName"));
                    tag_09.setText(array.optJSONObject(8).optString("machineName"));
                }
            }
            values.add(sliceValue);
        }
        if (array.length() != 0) {
            data = new PieChartData(values);
            data.setHasLabels(hasLabels);
            data.setHasLabelsOnlyForSelected(hasLabelForSelected);
            data.setHasLabelsOutside(hasLabelsOutside);
            data.setHasCenterCircle(hasCenterCircle);
            data.setValueLabelBackgroundEnabled(false);
            data.setValueLabelsTextColor(Color.WHITE);
            if (isExploded) {
                data.setSlicesSpacing(24);
            }
            pieChartView.setAlpha(0.75f);//设置透明度
            pieChartView.setPieChartData(data);
        }
    }
}
