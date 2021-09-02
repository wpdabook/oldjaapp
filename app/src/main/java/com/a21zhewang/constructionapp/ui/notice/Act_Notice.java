package com.a21zhewang.constructionapp.ui.notice;

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
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.NoticeBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.receiver.PushMessageReceiver;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
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
 * Created by WP-PC on 2019/6/26.
 */

public class Act_Notice extends BaseActivity implements OnTouchUpListener {
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
    private List<NoticeBean> noticeBeanList;
    private CommonAdapter<NoticeBean> adapter;
    private int pageindex = 0;
    private String keyWord="";
    private int onClickIndex=-1;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_notice;
    }

    @Override
    public void initViews() {
        myTitleBar.setTitlenametv("通知简报");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        noticeBeanList = new ArrayList<>();
        int layoutid = R.layout.sgtx_list_item;
        activityTzggSearchView.setVisibility(View.VISIBLE);
        layoutid = R.layout.tzgg_list_item2;
        activity_tzgg.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);//4个参数按顺序分别是左上右下
        activityTzggSWPullRecyclerLayout.setLayoutParams(layoutParams);

        adapter = new CommonAdapter<NoticeBean>(this, layoutid, noticeBeanList) {
            @Override
            protected void convert(ViewHolder holder, final NoticeBean noticeBean, final int position) {
                holder.setText(R.id.tzgg_list_item_tv_title, noticeBean.getTitle());
                holder.setText(R.id.tzgg_list_item_tv_from,  "来自："+noticeBean.getCreateUserName());
                holder.setText(R.id.tzgg_list_item_tv_time, noticeBean.getCreateDate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(Act_Notice.this, Act_NoticeDetail.class);
                        intent.putExtra("id", noticeBean.getId());
                        startActivity(intent);
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(Act_Notice.this, Act_NoticeDetail.class);
                        intent.putExtra("id", noticeBean.getId());
                        intent.putExtra("createUserId",noticeBean.getCreateUserId());
                        intent.putExtra("readStatus",noticeBean.getReadStatus());
                        startActivity(intent);
                    }
                });
                /**
                 * -1、发起	2：未读	3：已读
                 */
                if (noticeBean.getReadStatus() == 2) {
                    holder.setVisible(R.id.tzgg_list_item_tv_isread, true);
                } else {
                    holder.setVisible(R.id.tzgg_list_item_tv_isread, false);
                }
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
                noticeBeanList.clear();
                pageindex = 0;
                getdata();
            }
        });
    }
    private void getdata() {
        getdata(pageindex,10);
    }
    private void getdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"pageIndex\":\"" + pageindex + "\",\"projectName\":\"" + keyWord+ "\",\"pageSize\":\""+pageSize+"\"}");
        MyCallBack callback = new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    Object recordList = jsonObject.get("recordList");
                    if (recordList != null) {
                        List<NoticeBean> noticeBeen = JsonUtils.jsonToList(recordList.toString(), NoticeBean[].class);
                        if (noticeBeen != null && noticeBeen.size() > 0) {
                            noticeBeanList.addAll(noticeBeen);
                        }
                    }
                    adapter.notifyDataSetChanged();
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
        XUtil.postjsondata(getjsonobj, "GetJGNoticeList", callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (noticeBeanList!=null)
            noticeBeanList.clear();
        if (adapter!=null)
            adapter.notifyDataSetChanged();
        if (0 != PushMessageReceiver.count) {
            //角标清空
            PushMessageReceiver.count = 0;
            AppShortCutUtil.setCount(PushMessageReceiver.count, Act_Notice.this);
        }
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
