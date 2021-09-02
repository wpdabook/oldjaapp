package com.a21zhewang.constructionapp.ui.jygz;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.JygzDetailAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.FileBean;
import com.a21zhewang.constructionapp.bean.JygzNineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RectObj;
import com.a21zhewang.constructionapp.customview.MyScrollListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2021/3/9.
 * [
 {
 "statusName": "创建",
 "statusValue": "1"
 },
 {
 "statusName": "下发",
 "statusValue": "2"
 },
 {
 "statusName": "待处理",
 "statusValue": "3"
 },
 {
 "statusName": "待审核",
 "statusValue": "4"
 },
 {
 "statusName": "审核未通过",
 "statusValue": "5"
 },
 {
 "statusName": "完成",
 "statusValue": "6"
 }
 ]
 */
public class Act_JygzDetail extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.project_name)
    TextView projectname;
    @BindView(R.id.text_area)
    TextView text_area;
    @BindView(R.id.cc_time)
    TextView time;
    @BindView(R.id.unit)
    TextView unit;
    @BindView(R.id.tv_people)
    TextView people;
    @BindView(R.id.scroll_list)
    MyScrollListView scroll_list;
    @BindView(R.id.qr_image)
    ImageView qr_img;
    @BindView(R.id.rect_people)
    LinearLayout rect_people;
    @BindView(R.id.btn_all_submit)
    Button btn_all_submit;
    @BindView(R.id.lin_nopass_reason)
    LinearLayout lin_nopass_reason;
    @BindView(R.id.tv_nopass_reason)
    TextView tv_nopass_reason;
    private String recordID;
    private List<JygzNineGridModel> mList;
    private JygzDetailAdapter mAdapter;
    private List<FileBean> imgfiles = new ArrayList<>();
    private RectObj rectObj;
    private String projectName;
    private String projectId;
    private String region;
    private int status = 0;
    private boolean isIssue = true;
    private boolean isAudit = true;


    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_jygz_detail_layout;
    }

    @Override
    public void initViews() {
        title.setText("简易告知详情");
        recordID = getIntent().getStringExtra("recordID");
        mAdapter = new JygzDetailAdapter(this);
        final String qrcode_url = PublicUtils.getspstring("preAddress")+recordID;
        qr_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_JygzDetail.this,Act_JygzQrCode.class)
                        .putExtra("qrcode_url",qrcode_url)
                        .putExtra("projectName",projectName)
                        .putExtra("region",region));
            }
        });
        btn_all_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAlertWindow();
                postAll("","");
            }
        });
        righttext.setTextSize(15);
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("下发".equals(righttext.getText().toString())){
                    startActivity(new Intent(Act_JygzDetail.this,Jygz_SendTask.class)
                            .putExtra("projectId",projectId).putExtra("recordId",recordID).putExtra("status",status));
                }else if("审核".equals(righttext.getText().toString())){
                    startActivity(new Intent(Act_JygzDetail.this,Act_JygzTaskCheck.class)
                            .putExtra("projectId",projectId).putExtra("recordId",recordID).putExtra("status",status));
                }
            }
        });
    }
    public void loadDetail(){
        JSONObject object = JsonUtils.getjsonobj("{\"id\":\"" + recordID + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    if(imgfiles.size() != 0){
                        imgfiles.clear();
                    }
                    mList = new ArrayList<JygzNineGridModel>();
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("detailObj");
                    projectName = object.optString("projectName");
                    status = object.optInt("status");
                    projectId = object.optString("projectId");
                    projectname.setText(projectName);
                    region = object.optString("region");
                    isIssue = object.optBoolean("isIssue");
                    isAudit = object.optBoolean("isAudit");
                    text_area.setText(region);
                    time.setText(object.optString("rectEndDate"));
                    unit.setText(object.optString("rectEtpName"));
                    if(isIssue){
                        righttext.setText("下发");
                        righttext.setVisibility(View.VISIBLE);
                    }else if(isAudit){
                        righttext.setText("审核");
                        righttext.setVisibility(View.VISIBLE);
                    }else {
                        righttext.setText("");
                        righttext.setVisibility(View.GONE);
                    }
                    if("".equals(object.optString("rectUserName"))){
                        rect_people.setVisibility(View.GONE);
                    }else {
                        rect_people.setVisibility(View.VISIBLE);
                        people.setText(object.optString("rectUserName"));
                    }
                    String nopass_reason = object.optString("auditReson");
                    if(!TextUtils.isEmpty(nopass_reason) && status == 5){
                        lin_nopass_reason.setVisibility(View.VISIBLE);
                        tv_nopass_reason.setText(nopass_reason);
                    }else {
                        lin_nopass_reason.setVisibility(View.GONE);
                    }
                    for(int i=0;i<array.length();i++){
                        /**问题列表*/
                        List<String> imglist = new ArrayList<String>();
                        /**针对问题的回复内容*/
                        List<String> filelist = new ArrayList<String>();
                        JSONObject obj = (JSONObject) array.get(i);
                        JSONArray questionarray = obj.optJSONArray("questions");
                        JygzNineGridModel model = new JygzNineGridModel();
                        model.setRectUserId(object.optString("rectUserId"));
                        model.setDefaultId(recordID);
                        Object objFiles = obj.get("rectObj");
                        //JSONArray filearray = obj.optJSONArray("fileInfo");
                        if("{}".equals(objFiles.toString())){
                            model.setRectObjTag(true);
                            btn_all_submit.setVisibility(View.GONE);
                        }else {
                            imgfiles.clear();
                            model.setRectObjTag(false);
                            if(PublicUtils.UserID.equals(object.optString("rectUserId"))
                                    && !"4.0".equals(object.optString("status"))&& !"6.0".equals(object.optString("status"))){ //不是当前用户整改或已回复
                                model.setRectObjTag(true);
                                btn_all_submit.setVisibility(View.VISIBLE);
                            }else {
                                model.setRectObjTag(false);
                                btn_all_submit.setVisibility(View.GONE);
                            }
                            rectObj = JsonUtils.getbean(objFiles.toString(),RectObj.class);
                            imgfiles = rectObj.getFileInfo1();
                            if(imgfiles != null){
                                for(int j=0;j<imgfiles.size();j++){
                                    filelist.add(imgfiles.get(j).getUrl());
                                    model.setRequestUrlList(filelist);
                                }
                            }
                            model.setRemark(rectObj.getRemark());
                        }
                        model.setProjectName(projectName);
                        model.setDicName(obj.optString("itemName"));
                        model.setRecordId(obj.optString("recordId"));
                        model.setStatus(object.optString("status"));
                        StringBuffer sb = new StringBuffer();
                        for(int j=0;j<questionarray.length();j++){
                            JSONObject fileobject = questionarray.optJSONObject(j);
                            sb.append(fileobject.optString("checkContent")+"|");
                            JSONArray filearray = fileobject.optJSONArray("fileInfo");
                            for(int h=0;h<filearray.length();h++){
                                JSONObject questobject = filearray.optJSONObject(h);
                                imglist.add(questobject.optString("url"));
                                model.setQuestUrlList(imglist);
                            }
                        }
                        model.setQuestionContent(sb.deleteCharAt(sb.length() - 1).toString());
//                        for(int j=0;j<filearray.length();j++){
//                            JSONObject fileobject = filearray.optJSONObject(j);
//                            imglist.add(fileobject.optString("url"));
//                            model.setQuestUrlList(imglist);
//                        }
                        mList.add(model);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAdapter.setList(mList);
                scroll_list.setAdapter(mAdapter);
                if(mAdapter != null){
                    mAdapter.notifyDataSetChanged();
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
            XUtil.postjsondata(object, "GetBroadCastNoticeDetail", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeDetail", callback);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDetail();
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

    public void postAll(String submitUserName,String submitUserId){
        JSONObject object = JsonUtils.getjsonobj("{\"noticeId\":\"" + recordID + "\"," +
                "\"submitUserName\":\"" + submitUserName + "\"," +
                "\"submitUserId\":\"" + submitUserId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    showToast("保存成功");
                    Act_JygzDetail.this.finish();
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "SubmitBroadCastNoticeRect", callback);
        }else {
            XUtil.postjsondatasj(object, "SubmitBroadCastNoticeRect", callback);
        }

    }


}
