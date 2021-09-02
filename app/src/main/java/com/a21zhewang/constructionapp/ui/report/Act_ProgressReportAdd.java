package com.a21zhewang.constructionapp.ui.report;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ItemRiskList;
import com.a21zhewang.constructionapp.bean.MachineryData;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.RiskAreaAndStage;
import com.a21zhewang.constructionapp.bean.RiskProject;
import com.a21zhewang.constructionapp.bean.RiskProjectBean;
import com.a21zhewang.constructionapp.bean.RiskTypeInfo;
import com.a21zhewang.constructionapp.bean.ScaffoldData;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.risk.SelectRiskProject;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

import static com.a21zhewang.constructionapp.R.id.ed_begin_floor_number;
import static com.a21zhewang.constructionapp.R.id.tv_tools1;


/**
 * 新增风险上报
 * Created by WP-PC on 2019/6/26.
 */

public class Act_ProgressReportAdd extends BaseActivity {
    @BindView(R.id.activity_risk_index_MyTitleBar)
    MyTitleBar myTitleBar;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    @BindView(R.id.li_select_project)
    LinearLayout li_select_project;
    @BindView(R.id.li_select_project_area)
    LinearLayout li_select_project_area;
    @BindView(R.id.li_select_project_stage)
    LinearLayout li_select_project_stage;
    @BindView(R.id.tv_risk_project)
    TextView tv_risk_project;
    @BindView(R.id.tv_risk_project_area)
    TextView tv_risk_project_area;
    @BindView(R.id.tv_risk_project_stage)
    TextView tv_risk_project_stage;
    @BindView(R.id.tv_risk_project_categories)
    TextView tv_risk_project_categories;
    @BindView(tv_tools1)
    TextView onetools;
    @BindView(R.id.tv_tools2)
    TextView twotools;
    @BindView(R.id.edit_des)
    EditText edit_des;
    @BindView(ed_begin_floor_number)
    EditText et_begin_floor;
    @BindView(R.id.ed_plan_end_floor_number)
    EditText ed_plan_end_floor_number;
    @BindView(R.id.lin_one)
    LinearLayout lin_one;
    @BindView(R.id.lin_two)
    LinearLayout lin_two;
    @BindView(R.id.lin_floor_scaffold)
    LinearLayout lin_floor_scaffold;
    @BindView(R.id.lin_machinery_data)
    LinearLayout lin_machinery_data;
    private String projectId,projectName;
    private Project pjbean;
    private Dialog loadingDialog;
    private View total_view;
    private NumberPicker numberPicker;
    private Button submit_floor_total;
    private RiskProject riskProject;
    private List<RiskAreaAndStage> riskAreaAndStageList;
    private String checkRegionId,checkRegionName;
    private String dicID,dicName;
    private List<ScaffoldData> reportItemLists1;
    private List<MachineryData> reportItemLists2;
    private String Scaffold;
    private String Machinery;
    private int index = 1;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_progressreport_add_layout;
    }

    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "发送中...");
        titleBarRighttext.setVisibility(View.VISIBLE);
        titleBarRighttext.setTextSize(16);
        titleBarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
            }
        });
    }
    private void postdata() {
        if(index == 1){
            if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
                PublicUtils.toast("请选择项目");
                return;
            }
            if(TextUtils.isEmpty(tv_risk_project_area.getText().toString())){
                PublicUtils.toast("请选择施工区域");
                return;
            }
            if(TextUtils.isEmpty(tv_risk_project_stage.getText().toString())){
                PublicUtils.toast("请选择施工阶段");
                return;
            }
        }
        if(index == 2){
            if(TextUtils.isEmpty(onetools.getText().toString())){
                PublicUtils.toast("请选择下周涉及工具式脚手架");
                return;
            }
            if(TextUtils.isEmpty(twotools.getText().toString())){
                PublicUtils.toast("请选择下周涉及机械设备");
                return;
            }
        }
        if(index == 3){
            if(TextUtils.isEmpty(ed_plan_end_floor_number.getText().toString())){
                PublicUtils.toast("请输入下一步内容");
                return;
            }
            if(TextUtils.isEmpty(et_begin_floor.getText().toString())){
                PublicUtils.toast("请输入下一步内容");
                return;
            }
            if(TextUtils.isEmpty(onetools.getText().toString())){
                PublicUtils.toast("请选择下周涉及工具式脚手架");
                return;
            }
            if(TextUtils.isEmpty(twotools.getText().toString())){
                PublicUtils.toast("请选择下周涉及机械设备");
                return;
            }
        }
//        if(TextUtils.isEmpty(edit_des.getText().toString())){
//            PublicUtils.toast("请添加备注信息");
//            return;
//        }
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("ProjectID",projectId);
            jsonObject.put("ProjectName",pjbean.getProjectShortName());
            jsonObject.put("CheckRegionID",checkRegionId);
            jsonObject.put("CheckRegionName",checkRegionName);
            jsonObject.put("Construction",dicName);
            if(TextUtils.isEmpty(et_begin_floor.getText().toString())){
                jsonObject.put("BeginFloor",et_begin_floor.getText().toString());             //为已浇筑楼面
            }else {
                jsonObject.put("BeginFloor",Integer.valueOf(et_begin_floor.getText().toString()).intValue());
            }
            if(TextUtils.isEmpty(ed_plan_end_floor_number.getText().toString())){
                jsonObject.put("PlanEndFloor",ed_plan_end_floor_number.getText().toString()); //为下周计划浇筑到楼面
            }else {
                jsonObject.put("PlanEndFloor",Integer.valueOf(ed_plan_end_floor_number.getText().toString()).intValue());
            }
            jsonObject.put("Scaffold",Scaffold);
            jsonObject.put("Machinery",Machinery);
            jsonObject.put("Description",edit_des.getText().toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        titleBarRighttext.setVisibility(View.GONE);
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                titleBarRighttext.setVisibility(View.VISIBLE);
                PublicUtils.toast("发送成功");
                finish();
            }
            @Override
            public void onFail(ObjBean getbean) {
                titleBarRighttext.setVisibility(View.VISIBLE);
                loadingDialog.dismiss();
            }
            @Override
            public void onWrong() {
                loadingDialog.dismiss();
            }
        };
        XUtil.postjsondata(jsonObject,"SaveProgressReportInfo", callback);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择项目
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            projectName = data.getStringExtra("strs");
            pjbean = data.getParcelableExtra("project");
            projectId = data.getStringExtra("id");
            tv_risk_project.setText(projectName);
        }
        //施工区域
        if (requestCode == SelectRiskProject.RESQUST_CODE_SELECT_AREA
                && resultCode == RESULT_OK
                && data != null) {
            checkRegionName = data.getStringExtra("strs");
            checkRegionId = data.getStringExtra("ids");
            tv_risk_project_area.setText(checkRegionName);
        }
        //施工阶段
        if (requestCode == SelectRiskProject.RESQUST_CODE_SELECT_STAGE
                && resultCode == RESULT_OK
                && data != null) {
            dicName = data.getStringExtra("strs");
            dicID = data.getStringExtra("ids");
            tv_risk_project_stage.setText(dicName);
            if("桩基阶段".equals(dicName)||"基坑支护阶段".equals(dicName)){
                index = 1;
                lin_one.setVisibility(View.GONE);
                lin_two.setVisibility(View.GONE);
                lin_floor_scaffold.setVisibility(View.GONE);
                lin_machinery_data.setVisibility(View.GONE);
                et_begin_floor.getText().clear();
                ed_plan_end_floor_number.getText().clear();
                Scaffold = "";
                Machinery = "";
            }else if("地下室阶段".equals(dicName)||"装饰装修阶段".equals(dicName)){
                index = 2;
                lin_one.setVisibility(View.GONE);
                lin_two.setVisibility(View.GONE);
                lin_floor_scaffold.setVisibility(View.VISIBLE);
                lin_machinery_data.setVisibility(View.VISIBLE);
                et_begin_floor.getText().clear();
                ed_plan_end_floor_number.getText().clear();
            }else { //主体阶段
                index = 3;
                lin_one.setVisibility(View.VISIBLE);
                lin_two.setVisibility(View.VISIBLE);
                lin_floor_scaffold.setVisibility(View.VISIBLE);
                lin_machinery_data.setVisibility(View.VISIBLE);
            }
        }
        //下周涉及工具式脚手架-下周涉及机械设备
        if (requestCode == Act_SelectToolsType.RESQUST_CODE_SELECT_FLOOR_SCAFFOLD
                && resultCode == RESULT_OK
                && data != null) {
                Scaffold = data.getStringExtra("strs1");    //用于提交
                String Scaffold2 = data.getStringExtra("strs2"); //用于本地显示
                onetools.setText(Scaffold2);
            List<ScaffoldData> scaffoldDataList = data.getParcelableArrayListExtra("list");
            if(scaffoldDataList.size()>0){
                reportItemLists1 = new ArrayList<>();
                for(int i=0;i<scaffoldDataList.size();i++){
                    ScaffoldData scaffoldData = new ScaffoldData();
                    if(scaffoldDataList.get(i).isChecked() == true){
                        try{
                            /*同步选择项*/
                            scaffoldData.setCID(scaffoldDataList.get(i).getCID());
                            scaffoldData.setConfigName(scaffoldDataList.get(i).getConfigName());
                            scaffoldData.setChecked(true);
                            reportItemLists1.add(scaffoldData);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if (requestCode == Act_SelectToolsType.RESQUST_CODE_SELECT_MACHINERY
                && resultCode == RESULT_OK
                && data != null) {
                Machinery = data.getStringExtra("strs1"); //用于提交
                String Machinery2 = data.getStringExtra("strs2");//用于本地显示
                twotools.setText(Machinery2);
                reportItemLists2 = data.getParcelableArrayListExtra("list");
                List<MachineryData> machineryDataList = data.getParcelableArrayListExtra("list");
                if(machineryDataList.size()>0){
                reportItemLists2 = new ArrayList<>();
                for(int i=0;i<machineryDataList.size();i++){
                    MachineryData machineryData = new MachineryData();
                    if(machineryDataList.get(i).isChecked() == true){
                        try{
                            /*同步选择项*/
                            machineryData.setCID(machineryDataList.get(i).getCID());
                            machineryData.setConfigName(machineryDataList.get(i).getConfigName());
                            machineryData.setChecked(true);
                            reportItemLists2.add(machineryData);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    @OnClick({R.id.li_select_project,
            R.id.li_select_project_area,
            R.id.li_select_project_stage,
            R.id.lin_floor_scaffold,
            R.id.lin_machinery_data

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_project:  //选择项目
                getProject();
                break;
            case R.id.li_select_project_area: //选择区域
                if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
                    PublicUtils.toast("请选择项目");
                    return;
                }
                getProjectAreas();
                break;
            case R.id.li_select_project_stage: //施工阶段
                if(TextUtils.isEmpty(tv_risk_project_area.getText().toString())){
                    PublicUtils.toast("请选择施工区域");
                    return;
                }
                getStage();
                break;
            case R.id.lin_floor_scaffold: //下周涉及工具式脚手架
                if(TextUtils.isEmpty(tv_risk_project_stage.getText().toString())){
                    PublicUtils.toast("请选择施工阶段");
                    return;
                }
                Intent i = new Intent(Act_ProgressReportAdd.this, Act_SelectToolsType.class);
                i.putExtra("ConstructionDicID",dicID);
                i.putExtra("sendType","scaffo");
                i.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) reportItemLists1);
                startActivityForResult(i, Act_SelectToolsType.RESQUST_CODE_SELECT_FLOOR_SCAFFOLD);
                break;
            case R.id.lin_machinery_data: //下周涉及机械设备
                if(TextUtils.isEmpty(tv_risk_project_stage.getText().toString())){
                    PublicUtils.toast("请选择施工阶段");
                    return;
                }
                Intent intent = new Intent(Act_ProgressReportAdd.this, Act_SelectToolsType.class);
                intent.putExtra("ConstructionDicID",dicID);
                intent.putExtra("sendType","machinery");
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) reportItemLists2);
                startActivityForResult(intent, Act_SelectToolsType.RESQUST_CODE_SELECT_MACHINERY);
                break;
        }
    }

    /**
     * 选择项目
     */
    public void getProject(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"projects\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                RiskProjectBean riskProjectBean = JsonUtils.getbean(res, RiskProjectBean.class);
                final List<Project> projectList = riskProjectBean.getProjectList();
                if (projectList != null && projectList.size() > 0) {
                    Intent intent = new Intent(Act_ProgressReportAdd.this, SelectProjectActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                    startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
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
     * 选择区域
     */
    public void getProjectAreas(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"area\",\"projectId\":\"" + projectId + "\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskProject = JsonUtils.getbean(res,RiskProject.class);
                riskAreaAndStageList = riskProject.getBackdata();
                if (riskAreaAndStageList != null) {
                    Intent intent = new Intent(Act_ProgressReportAdd.this, SelectRiskProject.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) riskAreaAndStageList);
                    intent.putExtra("sendType","Area");
                    startActivityForResult(intent, SelectRiskProject.RESQUST_CODE_SELECT_AREA);
                } else {
                    PublicUtils.toast("请选择施工区域");
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
     * 获取施工阶段
     */
    public void getStage(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetConstruction", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskProject = JsonUtils.getbean(res,RiskProject.class);
                riskAreaAndStageList = riskProject.getBackdata();
                if (riskAreaAndStageList != null) {
                    Intent intent = new Intent(Act_ProgressReportAdd.this, SelectRiskProject.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) riskAreaAndStageList);
                    intent.putExtra("sendType","Stage");
                    startActivityForResult(intent, SelectRiskProject.RESQUST_CODE_SELECT_STAGE);
                } else {
                    PublicUtils.toast("请选择施工阶段");
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
    public void initData() {
    }

    @Override
    public void processClick(View v) {

    }
}
