package com.a21zhewang.constructionapp.ui.inspection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/11/24.
 */

public class ProjectRegisterActivity extends BaseActivity{
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout righttext;
    @BindView(R.id.et_projectname)
    EditText et_projectname;
    @BindView(R.id.et_adress)
    EditText et_adress;
    @BindView(R.id.et_manager)
    EditText et_manager;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.project_type)
    TextView project_type;
    @BindView(R.id.tv_bulide_companys)
    TextView tv_bulide_companys;
    @BindView(R.id.tv_construction_companys)
    TextView tv_construction_companys;
    @BindView(R.id.tv_supervision_companys)
    TextView tv_supervision_companys;
    @BindView(R.id.edit_des)
    EditText desc;
    @BindView(R.id.start_time)
    TimeTextView start_time;
    @BindView(R.id.end_time)
    TimeTextView end_time;
    @BindView(R.id.maps)
    ImageView maps;
    @BindView(R.id.ll_district_station_name)
    LinearLayout ll_district_station_name;
    @BindView(R.id.tv_district_station_name)
    TextView tv_district_station_name;
    @BindView(R.id.et_project_number)
    EditText et_project_number;
    private String etpID="";
    private JSONObject comitObject = new JSONObject();
    private List<Map<String, String>> typeList = new ArrayList<Map<String, String>>();
    private String Address = "";
    private String Province = "";
    private String City = "";
    private String Area = "";
    private Double latitude;
    private Double longitude;
    private String DicId = "";
    private String BuildUnitId = "";
    private String ConstructionUnitId = "";
    private String SupervisorUnitId = "";
    private AlertDialog.Builder builder;
    private String checkArea;
    public  final static int RESQUST_CODE_SELECT = 8906;  // 安全，质量，文明
    private  final String[] items =
            {"江汉区", "江岸区", "洪山区","青山区","硚口区","蔡甸区","武昌区","东湖高新区","经开(汉南)区","东西湖区",
            "汉阳区","江夏区","黄陂区","新洲区","市安全站","东湖风景区"};
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_project_register;
    }

    @Override
    public void initViews() {
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProjectRegisterActivity.this,MapActivity.class).putExtra("isUpDateAdress",false),MapActivity.MAP_RESQUST_CODE);
            }
        });
        loadProjectType();
    }
    @OnClick({R.id.ll_project_type,
            R.id.ll_bulide_companys,
            R.id.ll_construction_companys,
            R.id.ll_district_station_name,
            R.id.ll_supervision_companys
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_project_type:
                String[] str = new String[typeList.size()];
                for(int i=0;i<typeList.size();i++){
                    str[i] = typeList.get(i).get("dicName");
                }
                showFourTextDialog(project_type,str); //C01002001
                break;
            case R.id.ll_district_station_name:
                showTypeList();
                break;
            case R.id.ll_bulide_companys:
                startActivityForResult(new Intent(ProjectRegisterActivity.this,SelectCompanyActivity.class)
                        .putExtra("companyType","1"),SelectCompanyActivity.RESQUST_CODE_BUILD_SELECT_TYPE);
                break;
            case R.id.ll_construction_companys:
                startActivityForResult(new Intent(ProjectRegisterActivity.this,SelectCompanyActivity.class)
                        .putExtra("companyType","2"),SelectCompanyActivity.RESQUST_CODE_CONSTRUCTION_SELECT_TYPE);
                break;
            case R.id.ll_supervision_companys:
                startActivityForResult(new Intent(ProjectRegisterActivity.this,SelectCompanyActivity.class)
                        .putExtra("companyType","3"),SelectCompanyActivity.RESQUST_CODE_SUPERVISION_SELECT_TYPE);
                break;

        }
    }
    private void showTypeList() {
        builder = new AlertDialog.Builder(ProjectRegisterActivity.this).setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        checkArea = items[i];
                        tv_district_station_name.setText(checkArea);
                    }
                });
        builder.create().show();
    }
    public void submit(){

        if(TextUtils.isEmpty(et_projectname.getText().toString())){
            showToast("请输入项目名称");
            return;
        }
//        if(TextUtils.isEmpty(et_adress.getText().toString())){
//            showToast("请输入项目地址");
//            return;
//        }
        if(TextUtils.isEmpty(tv_district_station_name.getText().toString())){
            showToast("请输入所属区监管");
            return;
        }
//        if(TextUtils.isEmpty(et_adress.getText().toString())){
//            showToast("请输入项目地址");
//            return;
//        }
//        if(TextUtils.isEmpty(et_manager.getText().toString())){
//            showToast("请输入负责人");
//            return;
//        }
//        if(TextUtils.isEmpty(et_phone.getText().toString())){
//            showToast("请输入手机号码");
//            return;
//        }
        if(TextUtils.isEmpty(project_type.getText().toString())){
            showToast("请输入工程类型");
            return;
        }
//        if (TimeUtils.timeCompare(start_time.getText().toString(),end_time.getText().toString()) == 1){
//            showToast("竣工日期不能小于开工日期");
//            return;
//        }
//        if (TimeUtils.timeCompare(start_time.getText().toString(),end_time.getText().toString()) == 2){
//            showToast("开工日期不能等于竣工日期");
//            return;
//        }
//        if(TextUtils.isEmpty(tv_bulide_companys.getText().toString())){
//            showToast("请选择建设单位");
//            return;
//        }
        if(TextUtils.isEmpty(tv_construction_companys.getText().toString())){
            showToast("请选择施工单位");
            return;
        }
//        if(TextUtils.isEmpty(tv_supervision_companys.getText().toString())){
//            showToast("请选择监理单位");
//            return;
//        }
//        if(TextUtils.isEmpty(desc.getText().toString())){
//            showToast("请输入项目描述情况");
//            return;
//        }
        getjsonArray();
        XUtil.postjsondatasj(comitObject, "AddBroadCastProject", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    Intent data = new Intent();
                    data.putExtra("projectId",object.optString("projectId"));
                    data.putExtra("projectName",et_projectname.getText().toString());
                    setResult(RESULT_OK, data);
                    ProjectRegisterActivity.this.finish();
                } catch (Exception e) {
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
    private String getjsonArray() {
        try {
            comitObject.put("projectName",et_projectname.getText().toString());
            comitObject.put("address",Address);
            comitObject.put("province",Province);
            comitObject.put("city",City);
//            comitObject.put("area",Area); // 地图获取的区
            comitObject.put("area",checkArea); //手动选择
            comitObject.put("XPoint",longitude);
            comitObject.put("YPoint",latitude);
            comitObject.put("licenseKey",et_project_number.getText().toString());
            comitObject.put("manager",et_manager.getText().toString());
            comitObject.put("managerPhone",et_phone.getText().toString());
            comitObject.put("status","10");
            comitObject.put("projectTypeId",DicId);
            comitObject.put("startDate",start_time.getText());
            comitObject.put("endDate",end_time.getText());
            comitObject.put("buildUnitId",BuildUnitId);
            comitObject.put("constructionUnitId",ConstructionUnitId);
            comitObject.put("supervisorUnitId",SupervisorUnitId);
            comitObject.put("desc",desc.getText().toString());
        }catch (JSONException e){
            e.printStackTrace();
        }

        return comitObject.toString();
    }
    /**
     * 获取工程类型
     */
    public void loadProjectType(){
        final JSONObject object = JsonUtils.getjsonobj("");
        XUtil.postjsondatasj(object, "GetBroadCastProjectType", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        Map map = new HashMap();
                        map.put("dicId",object.optString("dicId"));
                        map.put("dicName",object.optString("dicName"));
                        typeList.add(map);
                    }
                } catch (Exception e) {
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
    /**
     * 安全负责人所属单位选择
     */
    public void showFourTextDialog(final TextView textView,String[] item){
        AlertDialog alertDialog = new AlertDialog
                .Builder(ProjectRegisterActivity.this)
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        for(int i=0;i<typeList.size();i++){
                            if(which == i){
                                textView.setText(typeList.get(i).get("dicName"));
                                DicId = typeList.get(i).get("dicId");
                            }
                        }
                    }
                }).create();
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectCompanyActivity.RESQUST_CODE_BUILD_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            BuildUnitId = data.getStringExtra("etpID");
            String etpFullName = data.getStringExtra("etpFullName");
            tv_bulide_companys.setText(etpFullName);
        }
        if (requestCode == SelectCompanyActivity.RESQUST_CODE_CONSTRUCTION_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            ConstructionUnitId = data.getStringExtra("etpID");
            String etpFullName = data.getStringExtra("etpFullName");
            tv_construction_companys.setText(etpFullName);
        }
        if (requestCode == SelectCompanyActivity.RESQUST_CODE_SUPERVISION_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            SupervisorUnitId = data.getStringExtra("etpID");
            String etpFullName = data.getStringExtra("etpFullName");
            tv_supervision_companys.setText(etpFullName);
        }
        if (requestCode == MapActivity.MAP_RESQUST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            Address = data.getStringExtra("address");
            Province = data.getStringExtra("province");
            City = data.getStringExtra("city");
            Area = data.getStringExtra("area");
            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);
            et_adress.setText(Address);
    }
//     if (TimeUtils.lessThanLong(DateUtils.getTodayDateLongM(),tv_select_project_date.getText().toString())){
//        showToast("检查时间不能小于当前时间");
//        return;
//     }

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
