package com.a21zhewang.constructionapp.ui.nfile;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.NoiceFile;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 通知文件
 * Created by WP-PC on 2019/6/26.
 */

public class Act_NoticeFile extends BaseActivity implements OnTouchUpListener {
    @BindView(R.id.activity_tzgg_SWPullRecyclerLayout)
    SWPullRecyclerLayout activityTzggSWPullRecyclerLayout;
    @BindView(R.id.activity_tzgg_MyTitleBar)
    MyTitleBar myTitleBar;
    @BindView(R.id.activity_tzgg_ImageView_addimg)
    ImageView activityTzggImageViewAddimg;
    @BindView(R.id.activity_tzgg)
    LinearLayout activity_tzgg;
    @BindView(R.id.activity_tzgg_SearchView)
    SearchView activityTzggSearchView;
    private List<NoiceFile> noiceFileList;
    private CommonAdapter<NoiceFile> adapter;
    private int pageindex = 0;
    private String keyWord="";
    private int onClickIndex=-1;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_noicefile;
    }

    @Override
    public void initViews() {
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons != null) {
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals("NotificationDocumentAddBtn")) {
                    activityTzggImageViewAddimg.setVisibility(View.VISIBLE);
                    activityTzggImageViewAddimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickIndex=0;
                            Intent intent = new Intent(Act_NoticeFile.this, Act_AddNoticeFile.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
        myTitleBar.setTitlenametv("通知文件");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noiceFileList = new ArrayList<>();
        int layoutid = R.layout.sgtx_list_item;
        activityTzggSearchView.setVisibility(View.VISIBLE);
        layoutid = R.layout.act_noticefile_item;
        activity_tzgg.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);//4个参数按顺序分别是左上右下
        activityTzggSWPullRecyclerLayout.setLayoutParams(layoutParams);

        adapter = new CommonAdapter<NoiceFile>(this, layoutid, noiceFileList) {
            @Override
            protected void convert(ViewHolder holder, final NoiceFile NoiceFile, final int position) {
                holder.setText(R.id.tzgg_list_item_tv_title, NoiceFile.getTitle());
                holder.setText(R.id.tzgg_list_item_tv_from,  "来自："+NoiceFile.getDocSource());
                holder.setText(R.id.tzgg_list_item_tv_time, NoiceFile.getCreateTime());
                holder.setText(R.id.notice_status, NoiceFile.getTaskStatus());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(Act_NoticeFile.this, Act_NoticeDetail.class);
                        intent.putExtra("Nid", NoiceFile.getNid());
                        intent.putExtra("DocSource",NoiceFile.getDocSource());
                        intent.putExtra("HandleUnitName",NoiceFile.getHandleUnitName());
                        intent.putExtra("TaskStatus",NoiceFile.getTaskStatus());
                        intent.putExtra("AreaTaskID",NoiceFile.getAreaTaskID());
                        intent.putExtra("CanOperate",NoiceFile.getCanOperate());
//                        intent.putExtra("readStatus",noticeBean.getReadStatus());
                        startActivity(intent);
                    }
                });
            }
        };
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
        activityTzggSWPullRecyclerLayout.addHeaderView(header, 100);
        activityTzggSWPullRecyclerLayout.addFooterView(footer, 100);
        activityTzggSWPullRecyclerLayout.setMyRecyclerView(linearLayoutManager, adapter);
        activityTzggSWPullRecyclerLayout.addOnTouchUpListener(this);
    }

    @Override
    public void initListener() {
        activityTzggSearchView.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String str) {
                keyWord=str;
                noiceFileList.clear();
                pageindex = 0;
                getdata();
            }
        });
    }
    private void getdata() {
        getdata(pageindex,10);
    }
    private void getdata(int pageindex,int pageSize) {
        JSONObject object = JsonUtils.getjsonobj("{\"pageIndex\":\""+pageindex+"\",\"pageSize\":\""+pageSize+"\"," +
                "\"title\":\""+keyWord+"\",\"getType\":\"" + "all" + "\"}");
        MyCallBack callback = new MyCallBack() {

            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)) {
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        Object recordList = jsonObject.get("recordList");
                        if (recordList != null) {
                            List<NoiceFile> NoiceFiles = JsonUtils.jsonToList(recordList.toString(), NoiceFile[].class);
                            if (NoiceFiles != null && NoiceFiles.size() > 0) {
                                noiceFileList.addAll(NoiceFiles);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                activityTzggSWPullRecyclerLayout.refreshok();
                if (onClickIndex!=-1){
                    activityTzggSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        };
        callback.setIstoast(false);
        XUtil.postjsondatasj(object, "GetAreaNotificationList", callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (noiceFileList!=null)
            noiceFileList.clear();
        if (adapter!=null)
            adapter.notifyDataSetChanged();
        pageindex = 0;
        getdata();
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void OnRefreshing() {
        keyWord="";
        pageindex = 0;
        adapter.getDatas().clear();
        adapter.notifyDataSetChanged();
        getdata();
    }

    @Override
    public void OnLoading() {
        pageindex++;
        getdata();
    }

    @Override
    public void getdataok() {

    }
}
