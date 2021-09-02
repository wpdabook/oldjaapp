package com.a21zhewang.constructionapp.ui.nfile;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.FileBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 场景一：A
 列表页     单子待处理状态
 详情页     显示处理按钮        回复内容为空不显示
 处理页面 下发|回复

 场景二：B
 列表页    单子已下发状态（未处理）
 详情页    显示处理按钮         回复内容为空不显示
 处理页面 只能回复  （单子状态变已完成）

 场景三：AB
 列表页    单子已完成状态      回复内容有值显示
 详情页    不显示处理按钮
 * Created by Administrator on 2020/12/21.
 */

public class Act_NoticeDetail extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.creat_time)
    TextView time;
    @BindView(R.id.send_unit)
    TextView send_unit;
    @BindView(R.id.receive_company)
    TextView receive_company;
    @BindView(R.id.notice_content)
    TextView notice_content;
    @BindView(R.id.notice_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.todo_recyclerview)
    RecyclerView ToDoRecyclerView;
    @BindView(R.id.unit_name)
    TextView unit_name;
    @BindView(R.id.reply_user)
    TextView reply_user;
    @BindView(R.id.reply_content)
    TextView reply_content;
    @BindView(R.id.reply_time)
    TextView reply_time;
    @BindView(R.id.notice_more_detail)
    TextView more;
    @BindView(R.id.lin_reply)
    LinearLayout lin_reply;
    private String Nid;
    private List<FileBean> imgfiles1;
    private List<FileBean> imgfiles2;
    private CommonAdapter<FileBean> imgsAdapter1;
    private CommonAdapter<FileBean> imgsAdapter2;
    private String HandleUnitName;
    private String TaskStatus;
    private String AreaTaskID;
    private int CanOperate;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_noticefile_detail;
    }

    @Override
    public void initViews() {
        Nid = getIntent().getStringExtra("Nid");
        HandleUnitName = getIntent().getStringExtra("HandleUnitName");
        TaskStatus = getIntent().getStringExtra("TaskStatus");
        AreaTaskID = getIntent().getStringExtra("AreaTaskID");
        CanOperate = getIntent().getIntExtra("CanOperate",0);
        righttext.setTextSize(14);
        if(CanOperate == 0){  //不能处理
            righttext.setVisibility(View.GONE);
        }else if(CanOperate == 1){
            righttext.setVisibility(View.VISIBLE);
        }
        initRecyclerViewDate();
    }

    @Override
    public void initListener() {
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_NoticeDetail.this,Act_NoticeReplyDetail.class).putExtra("Nid",Nid));
            }
        });
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_NoticeDetail.this,Act_NoticeFileToDo.class)
                        .putExtra("Nid",Nid).putExtra("taskStatus",TaskStatus)
                        .putExtra("HandleUnitName",HandleUnitName).putExtra("AreaTaskID",AreaTaskID));
            }
        });
    }

    @Override
    public void initData() {
        XUtil.postjsondatasj(JsonUtils.getjsonobj("{\"Nid\":\"" + Nid + "\",\"HandleUnitName\":\"" + HandleUnitName + "\"}"), "GetNotificationDetail", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResule(String res) {
                try {
                    final JSONObject jsonObject = new JSONObject(res);
                    title.setText(jsonObject.optString("Title"));
                    send_unit.setText(jsonObject.optString("DocSource"));
                    time.setText(jsonObject.optString("CreateTime"));
                    receive_company.setText(jsonObject.optString("ReceiveUnitName"));
                    notice_content.setText(jsonObject.optString("TaskContent"));
                    if(!TextUtils.isEmpty(jsonObject.optString("HandleUnitName"))){
                        unit_name.setText("区站名称："+jsonObject.optString("HandleUnitName"));
                    }
                    if(!TextUtils.isEmpty(jsonObject.optString("HandleUserName"))){
                        reply_user.setText("回复人员："+jsonObject.optString("HandleUnitName")+jsonObject.optString("HandleUserName"));
                    }
                    if(!TextUtils.isEmpty(jsonObject.optString("HandleContent"))){
                        reply_content.setText("回复内容："+jsonObject.optString("HandleContent"));
                    }
                    if(!TextUtils.isEmpty(jsonObject.optString("LastOperateTime"))){
                        reply_time.setText("回复时间："+jsonObject.optString("LastOperateTime"));
                    }
                    JSONArray array = jsonObject.optJSONArray("SendFilesList");
                    JSONArray replyarray = jsonObject.optJSONArray("ReplyFileList");
                    String LastOperateTime = jsonObject.optString("LastOperateTime");
                    if(TextUtils.isEmpty(LastOperateTime)){
                        lin_reply.setVisibility(View.GONE);
                    }else {
                        lin_reply.setVisibility(View.VISIBLE);
                    }

                    for(int i=0;i<array.length();i++){
                        JSONObject  object = array.optJSONObject(i);
                        FileBean FileBean = new FileBean();
                        FileBean.setFileBrief(object.optString("fileBrief"));
                        FileBean.setUrl(object.optString("url"));
                        FileBean.setViewUrl(object.optString("viewUrl"));
                        imgfiles1.add(FileBean);
                    }
                    for(int i=0;i<replyarray.length();i++){
                        JSONObject  object = replyarray.optJSONObject(i);
                        FileBean FileBean = new FileBean();
                        FileBean.setFileBrief(object.optString("fileBrief"));
                        FileBean.setUrl(object.optString("url"));
                        FileBean.setViewUrl(object.optString("viewUrl"));
                        imgfiles2.add(FileBean);
                    }
                    recyclerView.setAdapter(imgsAdapter1);
                    ToDoRecyclerView.setAdapter(imgsAdapter2);
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
    public void initRecyclerViewDate(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        imgfiles1 = new ArrayList<FileBean>();
        imgsAdapter1 = new CommonAdapter<FileBean>(this, R.layout.faqigtxt_imglist_item3, imgfiles1) {

            @Override
            protected void convert(ViewHolder holder, FileBean s, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img3);
                Glide.with(Act_NoticeDetail.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<FileBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<FileBean>() {

                            @Override
                            public String getstrs(int index, FileBean obj) {
                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, FileBean obj) {
                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(Act_NoticeDetail.this, position, imgfiles1, pagerAdapterAPI);
                    }
                });
            }
        };
        initReplyRecyclerViewDate();
    }
    public void initReplyRecyclerViewDate(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ToDoRecyclerView.setLayoutManager(linearLayoutManager);
        imgfiles2 = new ArrayList<FileBean>();
        imgsAdapter2 = new CommonAdapter<FileBean>(this, R.layout.faqigtxt_imglist_item3, imgfiles2) {

            @Override
            protected void convert(ViewHolder holder, FileBean s, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img3);
                Glide.with(Act_NoticeDetail.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img3, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<FileBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<FileBean>() {

                            @Override
                            public String getstrs(int index, FileBean obj) {
                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, FileBean obj) {
                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(Act_NoticeDetail.this, position, imgfiles2, pagerAdapterAPI);
                    }
                });
            }
        };
    }
    @Override
    public void processClick(View v) {

    }
}
