package com.a21zhewang.constructionapp.ui.nfile;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NineGridAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;

/**
 * Created by WP-PC on 2020/3/11.
 */

public class Act_NoticeReplyDetail extends BaseActivity{
    @BindView(R.id.rv_pic_list)
    XListView mListView;
    private int page = 0;
    private int totalPage = 1;
    private JSONArray dataArray = new JSONArray();
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String Nid;
    private NineGridAdapter mAdapter;
    private List<NineGridModel> mList;
    private List<String> imglist;
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_noticefile_replydetail;
    }

    @Override
    public void initViews() {
        Nid = getIntent().getStringExtra("Nid");
        imglist = new ArrayList<>();
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData(false);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData(false);
            }
        });
        mAdapter = new NineGridAdapter(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        loadData(true);
    }
    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog) {
        XUtil.postjsondatasj(JsonUtils.getjsonobj("{\"Nid\":\"" + Nid + "\"}"), "GetNotificationAreaReply", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
//                    initListData();
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
                        mList = new ArrayList<NineGridModel>();
                    }
                    totalPage = object.optInt("total");
                    if (totalPage > 0) {
                        mListView.setPullLoadEnable(true);
                    } else {
                        mListView.setPullLoadEnable(false);
                    }
                    if (page > totalPage) {
                        page = totalPage;
                        mListView.stopLoadMore();
                        Toast.makeText(Act_NoticeReplyDetail.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(object.length()>0){
                        dataArray = object.optJSONArray("recordList");
                        for (int i = 0; i < dataArray.length(); i++) {
                            NineGridModel model = new NineGridModel();
                            JSONObject tempobject = dataArray.optJSONObject(i);
                            model.setUser("回复人员："+tempobject.optString("UnitName")+tempobject.optString("HandleUserName"));
                            model.setContent("回复内容："+tempobject.optString("HandleContent"));
                            model.setCreateTime(tempobject.optString("LastOperateTime"));
                            JSONArray fileArray = tempobject.optJSONArray("fileList");
                              for(int j=0;j<fileArray.length();j++){
                                  JSONObject object1 = fileArray.optJSONObject(j);
                                  imglist.add(object1.optString("url"));
                                  model.setUrlList(imglist);
                              }
                            mList.add(model);
                        }
                    }
                    if (dataArray.length() == 0) {
                        placeHolder.setVisibility(View.VISIBLE);
                    } else {
                        placeHolder.setVisibility(View.GONE);
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

            @Override
            public void onFinished() {
                super.onFinished();
                mListView.stopRefresh();
                mListView.stopLoadMore();
                mListView.setRefreshTime(" " + new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .format(new Date()));
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
    public void processClick(View v) {

    }
}
