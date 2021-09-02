package com.a21zhewang.constructionapp.ui.report;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ItemReadList;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskDetailInfo;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WP-PC on 2020/4/16.
 */

public class Act_ProgressReportDetail extends BaseActivity{
    private String PID = "";
    @BindView(R.id.tv_project_name)
    TextView project_name;
    @BindView(R.id.tv_now_region)
    TextView region;
    @BindView(R.id.tv_now_stage)
    TextView now_stage;
    @BindView(R.id.tv_now_floor)
    TextView now_floor;
    @BindView(R.id.tv_week_floor_s)
    TextView tv_week_floor_s;
    @BindView(R.id.tv_weeks_tools_2)
    TextView tv_weeks_tools_2;
    @BindView(R.id.tv_jixie_tools)
    TextView tv_jixie_tools;
    @BindView(R.id.des_text)
    TextView des_text;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_progress_report_detail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PID = getIntent().getStringExtra("PID");
        getdata(PID);
    }
    private void getdata(String PID) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"PID\":\"" + PID + "\"}"), "GetProgressReportDetail", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try{
                    JSONObject object = new JSONObject(res);
                    project_name.setText(object.optString("ProjectName"));
                    region.setText(object.optString("CheckRegionName"));
                    now_stage.setText(object.optString("Construction"));
                    now_floor.setText(object.optInt("BeginFloor")+"");
                    tv_week_floor_s.setText(object.optInt("PlanEndFloor")+"");
                    tv_weeks_tools_2.setText(object.optString("Scaffold"));
                    tv_jixie_tools.setText(object.optString("Machinery"));
                    des_text.setText(object.optString("Description"));
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
    @Override
    public void initViews() {
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
