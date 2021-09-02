package com.a21zhewang.constructionapp.ui.risk;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ItemRiskList;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.RiskAreaAndStage;
import com.a21zhewang.constructionapp.bean.RiskDetailInfo;
import com.a21zhewang.constructionapp.bean.RiskProject;
import com.a21zhewang.constructionapp.bean.RiskProjectBean;
import com.a21zhewang.constructionapp.bean.RiskSubdivisionBean;
import com.a21zhewang.constructionapp.bean.RiskTypeInfo;
import com.a21zhewang.constructionapp.bean.SubdivisionItem;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhy.adapter.recyclerview.CommonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 新增风险上报
 * Created by WP-PC on 2019/6/26.
 */

public class Act_RiskReportAdd extends BaseActivity {
    @BindView(R.id.activity_risk_index_MyTitleBar)
    MyTitleBar myTitleBar;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    @BindView(R.id.edit_now_general_floor)
    EditText edit_now_general_floor;
    @BindView(R.id.li_select_project)
    LinearLayout li_select_project;
    @BindView(R.id.li_select_project_area)
    LinearLayout li_select_project_area;
    @BindView(R.id.li_select_project_stage)
    LinearLayout li_select_project_stage;
    @BindView(R.id.li_select_project_categories)
    LinearLayout li_select_project_categories;
    @BindView(R.id.li_select_total_number)
    LinearLayout li_select_total_number;
    @BindView(R.id.lin_edit_now_floor)
    LinearLayout lin_edit_now_floor;
    @BindView(R.id.tv_risk_project)
    TextView tv_risk_project;
    @BindView(R.id.tv_risk_project_area)
    TextView tv_risk_project_area;
    @BindView(R.id.tv_risk_project_stage)
    TextView tv_risk_project_stage;
    @BindView(R.id.tv_risk_project_categories)
    TextView tv_risk_project_categories;
    @BindView(R.id.et_total_floor)
    EditText et_total_floor;
    @BindView(R.id.li_select_subdivision_project)
    LinearLayout li_select_subdivision_project;
    @BindView(R.id.tv_subdivision_project)
    TextView tv_subdivision_project;
    @BindView(R.id.view6)
    View view;
    private String projectId,projectName;
    private Project pjbean;
    private Dialog loadingDialog;
    private List<LocalMedia> imglist;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private List<ProjectArea> projectAreaList;//施工区域
    private List<ProjectArea> projectAreaList2;//施工阶段
    private String[] numbers = {"1层", "2层", "3层", "4层", "5层", "6层", "7层", "8层", "9层", "10层", "11层"};
    private PopupWindow popupWindow;
    private View total_view;
    private int totalFloor = 0;
    private NumberPicker numberPicker;
    private Button submit_floor_total;
    private RiskProject riskProject;
    private List<RiskAreaAndStage> riskAreaAndStageList;
    private String checkRegionId,checkRegionName;
    private String dicId,dicName;
    private JSONArray riskTypeArray = new JSONArray();
    private JSONArray subdivisionTypeArray = new JSONArray();
    private RiskDetailInfo riskDetailInfo;
    private List<ItemRiskList> itemRiskLists;
    private List<SubdivisionItem> subdivisionItems;
    private String projectType = "1";//项目类型 1：房建  2：市政

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_riskreportadd;
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
        et_total_floor.setText(totalFloor+"层");
        initNumberPicker();
        //选择总楼层
        et_total_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindows();
            }
        });
        // 确定总楼层
        submit_floor_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalFloor = numberPicker.getValue();
                et_total_floor.setText(totalFloor+"层");
                popupWindow.dismiss();
            }
        });
    }
    private void postdata() {

        if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
            PublicUtils.toast("请选择项目");
            return;
        }
        if(TextUtils.isEmpty(tv_risk_project_area.getText().toString())){
            PublicUtils.toast("请选择施工区域");
            return;
        }
        if("1".equals(projectType)){//房建
            if(TextUtils.isEmpty(tv_risk_project_stage.getText().toString())){
                PublicUtils.toast("请选择施工阶段");
                return;
            }
            if(!"C01007004".equals(dicId) && !"C01007005".equals(dicId)){
                if(TextUtils.isEmpty(tv_risk_project_categories.getText().toString())){
                    PublicUtils.toast("请选择风险类别");
                    return;
                }
            }
        }
        if("2".equals(projectType)){
            if(TextUtils.isEmpty(tv_subdivision_project.getText().toString())){
                PublicUtils.toast("请选择细分工程");
                return;
            }
            if(TextUtils.isEmpty(tv_risk_project_categories.getText().toString())){
                PublicUtils.toast("请选择风险类别");
                return;
            }
        }
        if("1".equals(projectType)){  //房建
            if("0层".equals(et_total_floor.getText().toString())){
                PublicUtils.toast("请选择总楼层");
                return;
            }
            if(TextUtils.isEmpty(edit_now_general_floor.getText().toString())){
                PublicUtils.toast("请输入当前施工楼层");
                return;
            }
        }
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        try{
//            jsonObject.put("id","P01001001");
            jsonObject.put("projectId",projectId);
            jsonObject.put("projectName",pjbean.getProjectShortName());
            jsonObject.put("checkRegionId",checkRegionId);
            jsonObject.put("checkRegionName",checkRegionName);
            jsonObject.put("dicId",dicId);
            jsonObject.put("dicName",dicName);
            jsonObject.put("totalFloor",totalFloor); //总楼层
            jsonObject.put("currFloor",edit_now_general_floor.getText().toString());  //当前楼层
            jsonObject.put("readUserId",PublicUtils.UserID);
            jsonObject.put("riskList",riskTypeArray);
            if("2".equals(projectType)){
                jsonObject.put("divisionItem",subdivisionTypeArray); //细分工程参数
            }
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
        XUtil.postjsondata(jsonObject,"EditRiskReport", callback);
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
            projectType = pjbean.getProjectType();
//            projectType = "2";
            if("2".equals(projectType)){ //市政
                totalFloor = 0;
                tv_risk_project_area.setText(""); //施工区域
                tv_risk_project_stage.setText(""); //施工阶段
                tv_subdivision_project.setText("");
                tv_risk_project_categories.setText(""); //风险类别
                et_total_floor.setText("0层"); //总楼层
                edit_now_general_floor.setText(""); //当前施工楼层
                li_select_subdivision_project.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                li_select_project_stage.setVisibility(View.GONE);
                li_select_total_number.setVisibility(View.GONE);
                lin_edit_now_floor.setVisibility(View.GONE);
            }else {  //房建
                totalFloor = 0;
                tv_risk_project_area.setText(""); //施工区域
                tv_risk_project_stage.setText(""); //施工阶段
                tv_risk_project_categories.setText(""); //风险类别
                et_total_floor.setText("0层"); //总楼层
                edit_now_general_floor.setText(""); //当前施工楼层
                li_select_subdivision_project.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                li_select_project_stage.setVisibility(View.VISIBLE);
                li_select_total_number.setVisibility(View.VISIBLE);
                lin_edit_now_floor.setVisibility(View.VISIBLE);
            }
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
            dicId = data.getStringExtra("ids");
            if("1".equals(projectType)) { //城建
               if("C01007004".equals(dicId) || "C01007005".equals(dicId)){
                   tv_risk_project_categories.setText("");
                   li_select_project_categories.setVisibility(View.GONE);
                }else {
                   li_select_project_categories.setVisibility(View.VISIBLE);
               }
            }
            tv_risk_project_stage.setText(dicName);
        }
        //细分工程
        if (requestCode == Act_SubdivisionType.RESQUST_CODE_SELECT_SUBDIVISIONTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            tv_subdivision_project.setText(strs);
            List<RiskSubdivisionBean> riskSubdivisionBeanList = data.getParcelableArrayListExtra("list");
            subdivisionTypeArray = new JSONArray();
            if(riskSubdivisionBeanList.size()>0){
                subdivisionItems = new ArrayList<>();
                for(int i=0;i<riskSubdivisionBeanList.size();i++){
                    SubdivisionItem subdivisionItem = new SubdivisionItem();
                    if(riskSubdivisionBeanList.get(i).isChecked() == true){
                        try{
                            /*上传服务器*/
                            JSONObject riskTypeObject = new JSONObject();
                            riskTypeObject.put("divisionId",riskSubdivisionBeanList.get(i).getDicId());
                            riskTypeObject.put("divisionName",riskSubdivisionBeanList.get(i).getDicName());
                            subdivisionTypeArray.put(riskTypeObject);

                            /*同步选择项*/
                            subdivisionItem.setDivisionId(riskSubdivisionBeanList.get(i).getDicId());
                            subdivisionItem.setDivisionName(riskSubdivisionBeanList.get(i).getDicName());
                            subdivisionItems.add(subdivisionItem);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        //风险类别
        if (requestCode == Act_SelectRiskType.RESQUST_CODE_SELECT_RISK_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            tv_risk_project_categories.setText(strs);
            List<RiskTypeInfo> riskTypelist = data.getParcelableArrayListExtra("list");
            riskTypeArray = new JSONArray();
            if(riskTypelist.size()>0){
                itemRiskLists = new ArrayList<>();
                for(int i=0;i<riskTypelist.size();i++){
                    ItemRiskList itemRiskList = new ItemRiskList();
                    if(riskTypelist.get(i).isChecked() == true){
                        try{
                            /*上传服务器*/
                            JSONObject riskTypeObject = new JSONObject();
                            riskTypeObject.put("dicId",riskTypelist.get(i).getDicID());
                            riskTypeObject.put("dicName",riskTypelist.get(i).getDicName());
                            riskTypeObject.put("fatherId",riskTypelist.get(i).getFatherID());
                            riskTypeArray.put(riskTypeObject);

                            /*同步选择项*/
                            itemRiskList.setDicId(riskTypelist.get(i).getDicID());
                            itemRiskList.setDicName(riskTypelist.get(i).getDicName());
                            itemRiskList.setFatherId(riskTypelist.get(i).getFatherID());
                            itemRiskLists.add(itemRiskList);
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
            R.id.li_select_subdivision_project,
            R.id.li_select_project_categories

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_project:  //选择项目
                getProject();
                break;
            case R.id.li_select_project_area: //选择区域
                getProjectAreas();
                break;
            case R.id.li_select_project_stage: //施工阶段
                getStage();
                break;
            case R.id.li_select_subdivision_project: //细分工程
                startActivityForResult(new Intent(Act_RiskReportAdd.this,Act_SubdivisionType.class)
                                .putExtra("projectId",projectId)
                                .putParcelableArrayListExtra("subdivisionItems", (ArrayList<? extends Parcelable>) subdivisionItems),
                        Act_SubdivisionType.RESQUST_CODE_SELECT_SUBDIVISIONTYPE);
                break;
            case R.id.li_select_project_categories: //风险类别
                startActivityForResult(new Intent(Act_RiskReportAdd.this,Act_SelectRiskType.class)
                        .putExtra("projectId",projectId)
                        .putParcelableArrayListExtra("itemRiskLists", (ArrayList<? extends Parcelable>) itemRiskLists),
                        Act_SelectRiskType.RESQUST_CODE_SELECT_RISK_TYPE);
                break;
        }
    }
    public void showPopwindows(){
        // 设置初始值
        numberPicker.setValue(1);
        // 强制隐藏键盘
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        // 填充布局并设置弹出窗体的宽,高
        popupWindow = new PopupWindow(total_view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        popupWindow.setFocusable(true);
        // 设置弹出窗体动画效果
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        // 触屏位置如果在选择框外面则销毁弹出框
        popupWindow.setOutsideTouchable(true);
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.showAtLocation(total_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        // 设置背景透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        // 添加窗口关闭事件
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }

        });
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
                    Intent intent = new Intent(Act_RiskReportAdd.this, SelectProjectActivity.class);
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
                    Intent intent = new Intent(Act_RiskReportAdd.this, SelectRiskProject.class);
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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"stage\",\"projectId\":\"" + projectId + "\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskProject = JsonUtils.getbean(res,RiskProject.class);
                riskAreaAndStageList = riskProject.getBackdata();

                if (riskAreaAndStageList != null) {
                    Intent intent = new Intent(Act_RiskReportAdd.this, SelectRiskProject.class);
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
    /**
     * 初始化滚动框布局
     */
    private void initNumberPicker() {
        //设置需要显示的内容数组
//        numberPicker.setDisplayedValues(numbers);
        total_view = LayoutInflater.from(this).inflate(R.layout.risk_popupwindow, null);
        numberPicker = (NumberPicker) total_view.findViewById(R.id.numberPicker);
        submit_floor_total = (Button) total_view.findViewById(R.id.submit_floor_total);
        numberPicker.setMaxValue(80);
        numberPicker.setMinValue(0);
        numberPicker.setFocusable(false);
        numberPicker.setFocusableInTouchMode(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerDividerColor(numberPicker);
    }

    public void getInitDate(){
        XUtil.postjsondata(JsonUtils.getjsonobj(null), "GetLastRiskReportDetail", new MyCallBack() {

            @Override
            public void onResule(String res) {
                riskDetailInfo = JsonUtils.getbean(res, RiskDetailInfo.class);
                et_total_floor.setText(riskDetailInfo.getTotalFloor()+"层");
                if(riskDetailInfo.getTotalFloor()!= null){
                    totalFloor = Integer.parseInt(riskDetailInfo.getTotalFloor());
                }
                et_total_floor.setText(totalFloor+"层");
                itemRiskLists = new ArrayList<ItemRiskList>();
                itemRiskLists = riskDetailInfo.getItemRiskList();
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
     * 自定义滚动框分隔线颜色
     */
    private void setNumberPickerDividerColor(NumberPicker number) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(number, new ColorDrawable(ContextCompat.getColor(this, R.color.numberpicker_line)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getInitDate();
    }

    @Override
    public void processClick(View v) {

    }
}
