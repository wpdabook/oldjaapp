package com.a21zhewang.constructionapp.ui.inspection;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.InspectionOneLevel;
import com.a21zhewang.constructionapp.bean.InspectionTwoLevel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 第三方巡检新增信息页
 * Created by Administrator on 2020/10/8.
 */

public class Act_AddInspection2 extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    //    @BindView(R.id.title_righttextview)
//    TextView right_text;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.li_select_project)
    LinearLayout li_select_project;
    @BindView(R.id.li_select_project_type)
    LinearLayout li_select_project_type;
    @BindView(R.id.li_check_project)
    LinearLayout li_check_project;
    //    @BindView(R.id.li_select_project_class)
//    LinearLayout li_select_project_class;
    @BindView(R.id.li_select_project_date)
    LinearLayout li_select_project_date;
    @BindView(R.id.tv_risk_project)
    TextView tv_risk_project;
    @BindView(R.id.tv_risk_project_type)
    TextView tv_risk_project_type;
    @BindView(R.id.tv_risk_project_class)
    TextView tv_risk_project_class;
    @BindView(R.id.tv_select_project_date)
    TimeTextView tv_select_project_date;
    @BindView(R.id.add_btn_inspection)
    Button add_btn_inspection;
    @BindView(R.id.li_select_project_content)
    LinearLayout li_select_project_content;
    @BindView(R.id.tv_check_content)
    TextView tv_check_content;
    @BindView(R.id.lin_add_project)
    RelativeLayout lin_add_project;
    @BindView(R.id.tv_project_stage)
    TextView tv_project_stage;
    @BindView(R.id.check_projectname)
    EditText check_projectname;
    @BindView(R.id.tv_ownercontact)
    TextView tv_ownercontact;
    @BindView(R.id.tv_supervisioncontact)
    TextView tv_supervisioncontact;
    @BindView(R.id.tv_constructioncontact)
    TextView tv_constructioncontact;
    @BindView(R.id.tv_area)
    TextView tv_area;
    private  StringBuffer sb;
    private String checkCategory;
    private int StageType = 0;
    private String approveType;
    private String approveTypeName;
    private AlertDialog.Builder builder;
    private List<String> typelist;
    private List<String> stagelist;
    private List<String> typestatus;
    private List<Integer> stagetype;
    private String projectId;
    private String Content;
    private ArrayList<InspectionTwoLevel> groups;
    private String projectName;
    /**项目阶段中新增“已完工”，当选择已完工时，检查类型和检查内容隐藏不填，可以提交*/
    private boolean SelectTag = true;
    private JSONArray CheckItemArray = new JSONArray();
    @OnClick({R.id.li_select_project,
            R.id.li_check_project,
            R.id.li_select_project_type,
            R.id.li_select_project_class,
            R.id.li_select_project_content,
            R.id.li_select_project_date,
            R.id.add_btn_inspection,
            R.id.li_select_project_stage

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_project:  //选择项目
                tv_risk_project_type.setText("");
                tv_check_content.setText("");
                if(CheckItemArray.length()>0){
                    CheckItemArray = new JSONArray();
                }
//                startActivityForResult(new Intent(Act_AddInspection2.this,Act_SearchProject.class), Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
                startActivityForResult(new Intent(Act_AddInspection2.this,Act_SearchProject.class).putExtra("SJ_TAG",1), Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
                break;
            case R.id.li_select_project_stage: //项目阶段
                if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
                    showToast("请选择项目");
                    return;
                }
                showStageList();
                break;
            case R.id.li_check_project: //核实项目
                check_projectname.requestFocus();
                break;
            case R.id.li_select_project_type: //检查类型
                if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
                    showToast("请选择项目");
                    return;
                }
                if(TextUtils.isEmpty(tv_project_stage.getText().toString())){
                    showToast("请选择项目阶段");
                    return;
                }
                showTypeList();
                break;
            case R.id.li_select_project_content:
                if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
                    showToast("请选择项目");
                    return;
                }
                if(TextUtils.isEmpty(tv_project_stage.getText().toString())){
                    showToast("请选择项目阶段");
                    return;
                }
                if(SelectTag == true){
                    if(TextUtils.isEmpty(tv_risk_project_type.getText().toString())){
                        showToast("请选择检查类型");
                        return;
                    }
                }
                if(CheckItemArray.length()>0){
                    CheckItemArray = new JSONArray();
                }
                //startActivityForResult(new Intent(Act_AddInspection2.this,Act_CheckGroupItem.class).putExtra("projectID",projectId),Act_CheckGroupItem.RESQUST_CODE_SELECT_CONTENT);
                startActivityForResult(new Intent(Act_AddInspection2.this,Act_CheckTabList.class).putExtra("projectID",projectId),Act_CheckTabList.RESQUST_PROBLEM_TABLE_TYPE);
                break;
            case R.id.li_select_project_date: //检查日期
//                tv_checkdate.setVisibility(View.GONE);
                break;
            case R.id.add_btn_inspection:
                if(TextUtils.isEmpty(tv_risk_project.getText().toString())){
                    showToast("请选择项目");
                    return;
                }
                if(TextUtils.isEmpty(tv_project_stage.getText().toString())){
                    showToast("请选择项目阶段");
                    return;
                }
                if(SelectTag == true){
                    if(TextUtils.isEmpty(tv_risk_project_type.getText().toString())){
                        showToast("请选择检查类型");
                        return;
                    }
                    if(TextUtils.isEmpty(tv_check_content.getText().toString())){
                        showToast("请选择检查内容");
                        return;
                    }
                }

//                if (TimeUtils.lessThanLong(DateUtils.getTodayDateLongM(),tv_select_project_date.getText().toString())){
//                    showToast("检查时间不能小于当前时间");
//                    return;
//                }
                JSONObject object = new JSONObject();
                try {
                    object.put("projectId",projectId);
                    object.put("PubProjectName",check_projectname.getText().toString());
                    object.put("stage",StageType);
                    if(SelectTag == true){
                        object.put("checkCategory",checkCategory);
                        object.put("checkItem",CheckItemArray);
                    }
                    MyCallBack callback = new MyCallBack() {
                        @Override
                        public void onResule(String res) {
                            try {
                                JSONObject object = new JSONObject(res);
                                startActivity(new Intent(Act_AddInspection2.this,Act_InspectionTabDetail.class)
                                        .putExtra("proShortName",object.optString("projectName"))
                                        .putExtra("createTime",object.optString("checkTime")) //待检查：发起时间   检查记录：是检查时间
                                        .putExtra("checkCategory",object.optString("checkCategoryName"))
                                        .putExtra("creatorName",object.optString("checkUserName"))
                                        .putExtra("recordId",object.optString("id"))
                                        .putExtra("status","2") //待检查：2   检查记录：3  不可编辑
                                        .putExtra("projectID",projectId));
                                Act_AddInspection2.this.finish();
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
                    };
                    if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                            || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
                        XUtil.postjsondata(object, "CreateBroadCastExamination", callback);
                    }else {
                        XUtil.postjsondatasj(object, "CreateBroadCastExamination", callback);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_addinspection2_layout;
    }
    @Override
    public void initViews() {
        title.setText("专项巡查");
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Act_AddInspection2.this,Act_Inspection.class).putExtra("HiddentTitleTag",false)); //用于直接跳转检查记录标志位
                startActivity(new Intent(Act_AddInspection2.this,Act_InspectionList.class).putExtra("HiddentTitleTag",false));
            }
        });
        lin_add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Act_AddInspection2.this,ProjectRegisterActivity.class), ProjectRegisterActivity.RESQUST_CODE_SELECT);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_AddInspection2.this.finish();

            }
        });

    }

    /**
     *检查类型
     */
    private void showTypeList() {
        typelist = new ArrayList<>();
        typestatus = new ArrayList<>();
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"checkCategory\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    //final String[] items = {"一般检查", "专项检查", "其他"};
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        typelist.add(object.optString("statusName"));
                        typestatus.add(object.optString("status"));
                    }
                    String[] items = typelist.toArray(new String[typelist.size()]);
                    builder = new AlertDialog.Builder(Act_AddInspection2.this).setIcon(R.mipmap.ic_launcher)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    checkCategory = typestatus.get(i);
                                    tv_risk_project_type.setText(typelist.get(i));
                                }
                            });
                    builder.create().show();
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
        }
    }
    /**
     *项目阶段
     */
    private void showStageList() {
        stagelist = new ArrayList<>();
        stagetype = new ArrayList<>();
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"stage\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    //final String[] items = {"一般检查", "专项检查", "其他"};
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        stagelist.add(object.optString("typeName"));
                        stagetype.add(object.optInt("type"));
                    }
                    String[] items = stagelist.toArray(new String[stagelist.size()]);
                    builder = new AlertDialog.Builder(Act_AddInspection2.this).setIcon(R.mipmap.ic_launcher)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    StageType = stagetype.get(i);
                                    tv_project_stage.setText(stagelist.get(i));
                                    if(StageType ==9 || StageType == 10){ //,{"type":9,"typeName":"未施工"},{"type":10,"typeName":"已完工"}
                                        li_select_project_type.setVisibility(View.GONE);
                                        li_select_project_content.setVisibility(View.GONE);
                                        tv_risk_project_type.setText("");
                                        tv_check_content.setText("");
                                        SelectTag = false;
                                    }else {
                                        li_select_project_type.setVisibility(View.VISIBLE);
                                        li_select_project_content.setVisibility(View.VISIBLE);
                                        tv_risk_project_type.setText("");
                                        tv_check_content.setText("");
                                        SelectTag = true;
                                    }
                                }
                            });
                    builder.create().show();
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
        }
    }
    private void getProjectInfo(String projectId){
        JSONObject object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    tv_ownercontact.setText(object.optString("owner"));
                    tv_supervisioncontact.setText(object.optString("supervision"));
                    tv_constructioncontact.setText(object.optString("construction"));
//                    for(int i=0;i<array.length();i++){
//                        JSONObject object = array.optJSONObject(i);
//                        typelist.add(object.optString("statusName"));
//                        typestatus.add(object.optString("status"));
//                    }
//                    String[] items = typelist.toArray(new String[typelist.size()]);
//                    builder = new AlertDialog.Builder(Act_AddInspection2.this).setIcon(R.mipmap.ic_launcher)
//                            .setItems(items, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    approveType = typestatus.get(i);
//                                    approveTypeName = typelist.get(i);
//                                    tv_risk_project_class.setText(typelist.get(i));
//                                }
//                            });
//                    builder.create().show();
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "GetProjectInfoSupply", callback);
        }else {
            XUtil.postjsondatasj(object, "GetProjectInfoSupply", callback);
        }
    }
    /**
     * 检查类别
     */
    private void showClassList() {
        typelist = new ArrayList<>();
        typestatus = new ArrayList<>();
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"approveType\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        typelist.add(object.optString("statusName"));
                        typestatus.add(object.optString("status"));
                    }
                    String[] items = typelist.toArray(new String[typelist.size()]);
                    builder = new AlertDialog.Builder(Act_AddInspection2.this).setIcon(R.mipmap.ic_launcher)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    approveType = typestatus.get(i);
                                    approveTypeName = typelist.get(i);
                                    tv_risk_project_class.setText(typelist.get(i));
                                }
                            });
                    builder.create().show();
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Act_SearchProject.RESQUST_CODE_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            projectId = data.getStringExtra("projectId");
            projectName = data.getStringExtra("projectName");
            String area = data.getStringExtra("area");
            tv_area.setText(area);
            tv_risk_project.setText(projectName);
            getProjectInfo(projectId);
        }
        /**项目注册*/
        if (requestCode == ProjectRegisterActivity.RESQUST_CODE_SELECT
                && resultCode == RESULT_OK
                && data != null) {
            projectId = data.getStringExtra("projectId");
            projectName = data.getStringExtra("projectName");
            tv_risk_project.setText(projectName);
        }
        if (requestCode == Act_CheckTabList.RESQUST_PROBLEM_TABLE_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            sb = new StringBuffer();
            ArrayList<InspectionOneLevel> groups = data.getParcelableArrayListExtra("list");
            if(groups != null){
                for(int i=0;i<groups.size();i++){
                    JSONObject XObj = new JSONObject();
                    JSONArray twolevelarray = new JSONArray();
                    try {
                        XObj.put("dicId",groups.get(i).getDicId());
                        XObj.put("dicName",groups.get(i).getDicName());
                        XObj.put("itemCount",groups.get(i).getItemCount());
                        XObj.put("fatherId",groups.get(i).getFatherId());
                        if(groups.get(i).getChild().size()>0){
                            for(int j=0;j<groups.get(i).getChild().size();j++){
                                JSONArray thirdlevelarray = new JSONArray();
                                JSONObject twoobject = new JSONObject();
                                twoobject.put("dicId",groups.get(i).getChild().get(j).getDicId());
                                twoobject.put("dicName",groups.get(i).getChild().get(j).getDicName());
                                for(int h=0;h<groups.get(i).getChild().get(j).getChild().size();h++){
                                    JSONObject thirdobject = new JSONObject();
                                    thirdobject.put("dicId",groups.get(i).getChild().get(j).getChild().get(h).getDicId());
                                    thirdobject.put("dicName",groups.get(i).getChild().get(j).getChild().get(h).getDicName());
                                    thirdlevelarray.put(thirdobject);
                                }
                                twoobject.put("child",thirdlevelarray);
                                twolevelarray.put(twoobject);
                            }
                            XObj.put("child",twolevelarray);
                            CheckItemArray.put(XObj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(groups.get(i).getChild().size()>0){
                        sb.append(groups.get(i).getDicName()+",");
                    }
                }
            }
            if(sb != null){
                tv_check_content.setText(sb.deleteCharAt(sb.length() - 1).toString());
            }
        }

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
