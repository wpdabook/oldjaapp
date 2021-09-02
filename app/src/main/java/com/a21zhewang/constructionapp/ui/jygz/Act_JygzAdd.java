package com.a21zhewang.constructionapp.ui.jygz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.JygzAddAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyScrollListView;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 简易告知新增
 * Created by Administrator on 2021/3/4.
 */

public class Act_JygzAdd extends BaseActivity {
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.li_select_project)
    LinearLayout li_select_project;
    @BindView(R.id.tv_select_project)
    TextView tv_select_project;
    @BindView(R.id.lin_unit)
    LinearLayout lin_unit;
    @BindView(R.id.lin_unit_name)
    LinearLayout lin_unit_name;
    @BindView(R.id.lin_user)
    LinearLayout lin_user;
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_unit_name)
    TextView tv_unit_name;
    @BindView(R.id.tv_user)
    TextView tv_user;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.end_time)
    TimeTextView end_time;
    @BindView(R.id.sc_list)
    MyScrollListView mListView;
    private String recordId;
    private String projectId;
    private String projectName;
    private List<String> etplist;
    private List<String> etpIdlist;
    private List<String> userlist;
    private List<String> userIdlist;
    private AlertDialog.Builder builder;
    /**整改单位类型ID（1：建设、2：监理、3：施工、4：区专班）*/
    private String rectEtpType;
    /**整改单位类型（1：建设、2：监理、3：施工、4：区专班）*/
    private String rectEtpTypeName;
    /**整改单位ID*/
    private String rectEtpId;
    /**整改人员ID*/
    private String rectUserId;
    private String rectUser;
    private String area;
    private JSONObject comitObject = new JSONObject();
    private List<NineGridModel> mList;
    private JSONArray dataArray = new JSONArray();
    private JygzAddAdapter mAdapter;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_jygz_add_layout;
    }

    @Override
    public void initViews() {
        righttext.setTextSize(16);
        mAdapter = new JygzAddAdapter(this);
        recordId = getIntent().getStringExtra("recordId");
        projectId = getIntent().getStringExtra("projectId");
        projectName = getIntent().getStringExtra("projectName");
        area = getIntent().getStringExtra("area");
        tv_area.setText(area);
        tv_select_project.setText(projectName);
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtils.timeCompare(DateUtils.getTodayDate(), end_time.getText().toString()) != 3) {
                    showToast("截止时间不能小于或等于当前时间");
                    return;
                }
                if(TextUtils.isEmpty(tv_unit.getText().toString())){
                    showToast("请选择单位类型");
                    return;
                }
                if(TextUtils.isEmpty(tv_unit_name.getText().toString())){
                    showToast("请选择整改单位");
                    return;
                }
                postDate();
            }
        });
    }
    @OnClick({
            R.id.lin_unit,
            R.id.lin_unit_name,
            R.id.lin_user

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_project:  //选择项目
                tv_select_project.setText("");
//                startActivityForResult(new Intent(Act_JygzAdd.this, Act_SearchProject.class), Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
                break;
            case R.id.lin_unit:  //选择整改单位
                showEtpTypeList();
                break;
            case R.id.lin_unit_name:  //选择单位名称
                if(TextUtils.isEmpty(tv_unit_name.getText().toString())){
                    showToast("该整改单位类型下无单位信息");
                }else {
                    showToast("请切换整改单位");
                }
                break;
            case R.id.lin_user:  //整改人员
                if(TextUtils.isEmpty(tv_unit_name.getText().toString())){
                    showToast("请选择整改单位");
                    return;
                }
                if(TextUtils.isEmpty(rectUserId)){
                    showToast("该整改单位下无相关人员信息");
                    return;
                }
                showUnitEtpList();
                break;
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
            tv_select_project.setText(projectName);
        }

    }

    /**
     * 选择整改单位类型
     */
    private void showEtpTypeList() {
        etplist = new ArrayList<>();
        etpIdlist = new ArrayList<>();
        JSONObject object = JsonUtils.getjsonobj("{}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        etplist.add(object.optString("etpTypeName"));
                        etpIdlist.add(object.optString("etpTypeId"));
                    }
                    String[] items = etplist.toArray(new String[etplist.size()]);
                    builder = new AlertDialog.Builder(Act_JygzAdd.this).setIcon(R.mipmap.ic_launcher)
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    rectEtpType = etpIdlist.get(i);
                                    rectEtpTypeName = etplist.get(i);
                                    tv_unit.setText(rectEtpTypeName);
                                    getUnitEtpList();//加载单位
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
            XUtil.postjsondata(object, "GetBroadCastNoticeEtpType", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeEtpType", callback);
        }
    }
    /**
     * 单位名称与整改人员
     */
    public void getUnitEtpList(){
        userlist = new ArrayList<>();
        userIdlist = new ArrayList<>();
        JSONObject object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"etpTypeId\":\"" + rectEtpType + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    if(array.length() == 0){
                        tv_unit_name.setText("");
                        tv_user.setText("");
                        rectEtpId = "";
                        rectUserId = "";
                    }else
                        for(int i=0;i<array.length();i++){
                            JSONObject object = array.optJSONObject(i);
                            JSONArray temparray = object.optJSONArray("userList");
                            tv_unit_name.setText(object.optString("etpShortName"));
                            rectEtpId = object.optString("etpID");
                            if(temparray.length() == 0){
                                tv_user.setText("");
                                rectUserId = "";
                            }
                            for(int j=0;j<temparray.length();j++){
                                JSONObject tempobject = temparray.optJSONObject(j);
                                userIdlist.add(tempobject.optString("userId"));
                                userlist.add(tempobject.optString("userName"));
                                tv_user.setText(userlist.get(0));
                                rectUserId = userIdlist.get(0);
                            }
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "GetBroadCastNoticeEtpUserList", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeEtpUserList", callback);
        }
    }


    /**
     * 选择整改人员
     */
    private void showUnitEtpList(){
        String[] items = userlist.toArray(new String[userlist.size()]);
        builder = new AlertDialog.Builder(Act_JygzAdd.this).setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rectUser = userlist.get(i);
                        rectUserId = userIdlist.get(i);
                        tv_user.setText(rectUser);
                    }
                });
        builder.create().show();
    }
    private void postDate(){
        getjsonArray();
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    String id = object.getString("id");
                    final String qrcode_url = PublicUtils.getspstring("preAddress")+id;
                    startActivity(new Intent(Act_JygzAdd.this,Act_JygzQrCode.class)
                            .putExtra("qrcode_url",qrcode_url)
                            .putExtra("projectName",projectName).putExtra("region",area));
                    Act_JygzAdd.this.finish();
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
            XUtil.postjsondata(comitObject, "AddBroadCastNotice", callback);
        }else {
            XUtil.postjsondatasj(comitObject, "AddBroadCastNotice", callback);
        }
    }
    private String getjsonArray() {
        try {
            comitObject.put("recordID", recordId);
            comitObject.put("rectEndDate",end_time.getText());
            comitObject.put("rectEtpType", rectEtpType);
            comitObject.put("rectEtpId",rectEtpId);
            comitObject.put("rectUserId",rectUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comitObject.toString();
    }
    @Override
    public void initListener() {
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                if(!"[]".equals(res)){
                    try {
                        mList = new ArrayList<NineGridModel>();
                        JSONArray array = new JSONArray(res);
                        for(int i=0;i<array.length();i++){
                            JSONObject object = array.optJSONObject(i);
                            NineGridModel model = new NineGridModel();
                            model.setContent(object.optString("itemName"));
                            JSONArray filearray = object.optJSONArray("fileInfo");
                            List<String> imglist = new ArrayList<String>();
                            for(int j=0;j<filearray.length();j++){
                                JSONObject tempobject = filearray.optJSONObject(j);
                                imglist.add(tempobject.optString("url"));
                                model.setUrlList(imglist);
                            }
                            mList.add(model);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mAdapter.setList(mList);
                    mListView.setAdapter(mAdapter);
                    if(mAdapter != null){
                        mAdapter.notifyDataSetChanged();
                    }
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
            XUtil.postjsondata(object, "GetBroadCastNoticeQuestion", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeQuestion", callback);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
