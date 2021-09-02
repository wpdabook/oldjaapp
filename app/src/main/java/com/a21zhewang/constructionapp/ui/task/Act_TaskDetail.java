package com.a21zhewang.constructionapp.ui.task;

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
import com.a21zhewang.constructionapp.adapter.TaskDetailAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.FileBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TaskDetailBean;
import com.a21zhewang.constructionapp.customview.MyScrollListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Administrator on 2021/3/9.
 */

public class Act_TaskDetail extends BaseActivity{
    @BindView(R.id.title_righttextview)
    TextView righttextview;
    @BindView(R.id.title)
    TextView tv_title;
    @BindView(R.id.tv_db_type)
    TextView db_type;
    @BindView(R.id.tv_db_status)
    TextView db_status;
    @BindView(R.id.tv_receiver_project)
    TextView tv_receiver_project;
    @BindView(R.id.db_company)
    TextView db_company;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.scroll_list)
    MyScrollListView scroll_list;
    @BindView(R.id.lin_receiver_project)
    LinearLayout lin_receiver_project;
    @BindView(R.id.lin_check_company)
    LinearLayout lin_check_company;
    @BindView(R.id.lin_check_people)
    LinearLayout lin_check_people;
    @BindView(R.id.lin_status)
    LinearLayout lin_status;
    @BindView(R.id.tv_content_title)
    TextView tv_content_title;
    @BindView(R.id.noice_content)
    TextView noice_content;
    private String recordId;
    private List<TaskDetailBean> mList;
    private TaskDetailAdapter mAdapter;
    private List<FileBean> imgfiles;
    private List<FileBean> requestimgfiles;
    private int status = 0;
    private String rectUserId;


    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_task_detail_layout;
    }

    @Override
    public void initViews() {
        righttextview.setTextSize(16);
        righttextview.setVisibility(View.GONE);
        recordId = getIntent().getStringExtra("recordId");
        mAdapter = new TaskDetailAdapter(this);
        righttextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status == 3){
                        startActivity(new Intent(Act_TaskDetail.this,Act_TaskCheck.class).putExtra("recordId",recordId));
                     }else {
                        startActivity(new Intent(Act_TaskDetail.this,Act_TaskToDo.class)
                                .putExtra("recordId",recordId)
                                .putExtra("status",status));
                 }

            }
        });
    }

    @Override
    public void resume() {
        super.resume();
        loadDetail();
    }

    public void loadDetail(){
        JSONObject object = JsonUtils.getjsonobj("{\"recordId\":\"" + recordId + "\"}");
        XUtil.postjsondatasj(object, "GetTaskSuperviseDetail", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    mList = new ArrayList<TaskDetailBean>();
                    imgfiles = new ArrayList<>();
                    JSONObject object = new JSONObject(res);
                    tv_title.setText(object.optString("title"));
                    rectUserId = object.optString("rectUserId");
                    if(object.optInt("taskType") == 1){
                        db_type.setText("通知文件");
                        lin_status.setVisibility(View.GONE);
                        lin_check_company.setVisibility(View.GONE);
                        lin_check_people.setVisibility(View.GONE);
                        tv_content_title.setText("内容");
                        noice_content.setVisibility(View.VISIBLE);
                        noice_content.setText(object.optString("content"));
                        //tv_receiver_project.setText("全部项目");
                        tv_receiver_project.setText(object.optString("createDate"));
                    }else {
                        db_type.setText("任务督办");
                        lin_receiver_project.setVisibility(View.VISIBLE);
                        lin_check_company.setVisibility(View.VISIBLE);
                        lin_check_people.setVisibility(View.VISIBLE);
                        tv_content_title.setText("过程记录");
                        noice_content.setVisibility(View.GONE);
                        //tv_receiver_project.setText(object.optString("projectName"));
                        tv_receiver_project.setText(object.optString("createDate"));
                    }
                    status = object.optInt("status");
                    if(status == 1){
                        db_status.setText("未读");
                        righttextview.setVisibility(View.GONE);
                    }else if (status == 2){
                        db_status.setText("处理中");
                        righttextview.setText("处理");
                        if(PublicUtils.UserID.equals(object.optString("rectUserId"))){
                            righttextview.setVisibility(View.VISIBLE); //登陆人与处理人一致   显示处理按钮
                        }else {
                            righttextview.setVisibility(View.GONE);
                        }
                    }else if (status == 3){
                        db_status.setText("待审核");
                        righttextview.setText("审核");
                        if(PublicUtils.UserID.equals(object.optString("createId"))){
                            righttextview.setVisibility(View.VISIBLE); //登陆人与审核人人一致   显示审核按钮
                        }else {
                            righttextview.setVisibility(View.GONE);
                        }
                    }else if (status == 4){
                        db_status.setText("已闭合");
                        righttextview.setVisibility(View.GONE);
                    }
                    db_company.setText(object.optString("rectEtpName"));
                    tv_people.setText(object.optString("rectUserName"));
                    JSONArray array = object.optJSONArray("fileInfo");
                    TaskDetailBean questmodel= null;
                    for(int i=0;i<array.length();i++){  //问题只有一个，过程（回复）有多个
                        questmodel= new TaskDetailBean();
                        questmodel.setContent(object.optString("content"));
                        List<String> filelist = new ArrayList<String>();
                        JSONObject  tempobject = array.optJSONObject(i);
                        FileBean FileBean = new FileBean();
                        FileBean.setFileBrief(tempobject.optString("fileBrief"));
                        FileBean.setUrl(tempobject.optString("url"));
                        FileBean.setViewUrl(tempobject.optString("viewUrl"));
                        imgfiles.add(FileBean);
                        if(imgfiles != null){
                            for(int j=0;j<imgfiles.size();j++){
                                filelist.add(imgfiles.get(j).getUrl());
                                questmodel.setQuestUrlList(filelist);
                            }
                        }
                    }
                    if(questmodel != null){
                        mList.add(questmodel);
                    }
                    JSONArray requestarray = object.optJSONArray("objDetailList");
                    TaskDetailBean requestmodel = null;
                    for(int i=0;i<requestarray.length();i++){
                        requestmodel = new TaskDetailBean();
                        List<String> filelist = new ArrayList<String>();
                        JSONObject tempobj = requestarray.optJSONObject(i);
                        requestmodel.setRemark(tempobj.optString("content"));
                        requestmodel.setStatus(tempobj.optInt("status"));
                        requestmodel.setCreateDate(tempobj.optString("createDate"));
                        JSONArray objarray = tempobj.optJSONArray("fileDetailInfo");
                        for(int j=0;j<objarray.length();j++){
                            requestimgfiles = new ArrayList<>();
                            JSONObject fileobj = objarray.optJSONObject(j);
                            FileBean FileBean = new FileBean();
                            FileBean.setFileBrief(fileobj.optString("fileBrief"));
                            FileBean.setUrl(fileobj.optString("url"));
                            FileBean.setViewUrl(fileobj.optString("viewUrl"));
                            requestimgfiles.add(FileBean);
                            if(requestimgfiles != null){
                                for(int h=0;h<requestimgfiles.size();h++){
                                    filelist.add(requestimgfiles.get(h).getUrl());
                                    requestmodel.setRequestUrlList(filelist);
                                }
                            }
                        }
                        if(requestmodel != null){
                            mList.add(requestmodel);
                        }
                    }
                    mAdapter.setList(mList);
                    scroll_list.setAdapter(mAdapter);
                    if(mAdapter != null){
                        mAdapter.notifyDataSetChanged();
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
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }


}
