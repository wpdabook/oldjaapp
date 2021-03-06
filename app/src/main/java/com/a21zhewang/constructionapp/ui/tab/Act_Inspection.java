package com.a21zhewang.constructionapp.ui.tab;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TableDate;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

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
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by WP-PC on 2019/8/12.
 */

public class Act_Inspection extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    private List<TableDate> tableList = new ArrayList<TableDate>();
    private List<PointValue> pointList = new ArrayList<PointValue>();
    private List<AxisValue> axisValues = new ArrayList<AxisValue>();
    private Calendar calendar;
    private int year;
    private int month;
    @BindView(R.id.linechart)
    LineChartView chartview;
    @BindView(R.id.riskcount)
    TextView riskcount;
    @BindView(R.id.findriskcount)
    TextView findriskcount;
    @BindView(R.id.tv_saferisk_leve1)
    TextView tv_saferisk_leve1;
    @BindView(R.id.tv_saferisk_leve2)
    TextView tv_saferisk_leve2;
    @BindView(R.id.tv_saferisk_leve3)
    TextView tv_saferisk_leve3;
    @BindView(R.id.quality_tv_leve1)
    TextView quality_tv_leve1;
    @BindView(R.id.quality_tv_leve2)
    TextView quality_tv_leve2;
    @BindView(R.id.quality_tv_leve3)
    TextView quality_tv_leve3;
    @BindView(R.id.civilized_tv_leve1)
    TextView civilized_tv_leve1;
    @BindView(R.id.civilized_tv_leve2)
    TextView civilized_tv_leve2;
    @BindView(R.id.civilized_tv_leve3)
    TextView civilized_tv_leve3;
    @BindView(R.id.personnel_tv_leve1)
    TextView personnel_tv_leve1;
    @BindView(R.id.personnel_tv_leve2)
    TextView personnel_tv_leve2;
    @BindView(R.id.personnel_tv_leve3)
    TextView personnel_tv_leve3;
    @BindView(R.id.check_tv_leve1)
    TextView check_tv_leve1;
    @BindView(R.id.check_tv_leve2)
    TextView check_tv_leve2;
    @BindView(R.id.check_tv_leve3)
    TextView check_tv_leve3;
    private Dialog loadingDialog;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_customtab_inspection;
    }

    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 1 + "\"}"), "GetDataTotal_checksecusecheckTotal", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject tempobject = new JSONObject(res);
                    if(tempobject.length()>0){
                      SetTextIsNoEmpty(riskcount,tempobject.optInt("riskcheckCount")+"");
                      SetTextIsNoEmpty(findriskcount,tempobject.optInt("riskcheckCountY")+"");
                        JSONArray array = tempobject.optJSONArray("daycheckcompList");
                        for(int i=0;i<array.length();i++){
                            JSONObject object = array.optJSONObject(i);
                            TableDate tableDate = new TableDate();
                            String dates = object.optString("date");
                            tableDate.setDate(dates);
                            tableDate.setUserCount(object.optInt("userCount"));
                            tableList.add(tableDate);
                            PointValue point = new PointValue(i,tableList.get(i).getUserCount()); // ?????????Y??????????????????
                            pointList.add(point);
                            String week = tableList.get(i).getDate();
                            String weekdays = week.substring(week.length() - 2, week.length());
                            axisValues.add(new AxisValue(i).setLabel((weekdays.replace("-", "") + "???").toCharArray()));// ?????????X??????????????????
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

    /*
     *1:????????? 2????????????
     */
    @Override
    public void initData() {
        calendar = Calendar.getInstance(Locale.CHINA);
        year = calendar.get(Calendar.YEAR); // ??????Calendar???????????????
        month = calendar.get(Calendar.MONTH);// ??????Calendar???????????????
        title.setText("????????????");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearspValues();
                Act_Inspection.this.finish();
            }
        });
    }
    private void generateData() {
        List<Line> lines = new ArrayList<Line>();
        Line line = new Line(pointList);
        line.setColor(getResources().getColor(R.color.tilebar_color));
        line.setShape(ValueShape.CIRCLE);// ????????????
        line.setCubic(true);// ?????????????????????????????????
        line.setFilled(true);// ????????????
        line.setHasLabels(true);// ????????????????????????????????????Y??????
        line.setHasLabelsOnlyForSelected(false);// ????????????????????????????????????????????????Y??????
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
//        axisX.setName("??????(" + year + "???" + (month + 1) + "???)");
        axisY.setName("????????????");
//        axisX.setTextSize(10);//??????????????????
//        axisY.setMaxLabelChars(7); //????????????y???????????????????????????????????????X?????????????????????7<=y<=mAxisyValues.length
        data.setAxisXBottom(axisX); //x ????????????  ???????????????????????????????????????x??????
        data.setAxisYLeft(axisY);
        data.setValueLabelTextSize(10);
        data.setValueLabelsTextColor(getResources().getColor(R.color.color_666666));
//        data.setValueLabelBackgroundColor(getResources().getColor(R.color.color_theme));//??????????????????????????????????????????
        data.setValueLabelBackgroundEnabled(false);
        data.setValueLabelTypeface(Typeface.DEFAULT);
        if(data != null){
            chartview.setLineChartData(data);
            chartview.setZoomType(ZoomType.HORIZONTAL);
            chartview.setZoomEnabled(false);
        }
        initNumberData();
    }
    public void initNumberData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 1 + "\"}"),
                "GetDataTotal_sechidcivperrecTotal", new MyCallBack() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResule(String res) {
                        try {
                            loadingDialog.dismiss();
                            JSONObject object = new JSONObject(res);
                            if(object.length()> 0){
                                SetTextIsNoEmpty(tv_saferisk_leve1,object.optInt("secModel_rectifiCount")+"");
                                SetTextIsNoEmpty(tv_saferisk_leve2,object.optInt("secModel_rectifiNCount")+"");
                                Double b = object.optDouble("secModel_rate");
                                String number = Integer.parseInt(new DecimalFormat("0").format(b * 100)) + "%";
                                SetTextIsNoEmpty(tv_saferisk_leve3,number);

                                SetTextIsNoEmpty(quality_tv_leve1,object.optInt("quaModel_rectifiCount")+"");
                                SetTextIsNoEmpty(quality_tv_leve2,object.optInt("quaModel_rectifiNCount")+"");
                                Double b2 = object.optDouble("quaModel_rate");
                                String number2 = Integer.parseInt(new DecimalFormat("0").format(b2 * 100)) + "%";
                                SetTextIsNoEmpty(quality_tv_leve3,number2);

                                SetTextIsNoEmpty(civilized_tv_leve1,object.optInt("civModel_rectifiCount")+"");
                                SetTextIsNoEmpty(civilized_tv_leve2,object.optInt("civModel_rectifiNCount")+"");
                                Double b3 = object.optDouble("civModel_rate");
                                String number3 = Integer.parseInt(new DecimalFormat("0").format(b3 * 100)) + "%";
                                SetTextIsNoEmpty(civilized_tv_leve3,number3);

                                SetTextIsNoEmpty(personnel_tv_leve1,object.optInt("userModel_userCount")+"");
                                SetTextIsNoEmpty(personnel_tv_leve2,object.optInt("userModel_useUserCount")+"");
                                Double b4 = object.optDouble("userModel_useRate");
                                String number4 = Integer.parseInt(new DecimalFormat("0").format(b4 * 100)) + "%";
                                SetTextIsNoEmpty(personnel_tv_leve3,number4);

                                SetTextIsNoEmpty(check_tv_leve1,object.optInt("keyModel_userCount")+"");
                                SetTextIsNoEmpty(check_tv_leve2,object.optInt("keyModel_useUserCount")+"");
                                Double b5 = object.optDouble("keyModel_useRate");
                                String number5 = Integer.parseInt(new DecimalFormat("0").format(b5 * 100)) + "%";
                                SetTextIsNoEmpty(check_tv_leve3,number5);
                            }
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

    @Override
    public void initListener() {

    }
    @Override
    public void processClick(View v) {

    }
}
