package com.a21zhewang.constructionapp.ui.dxzf;

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
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
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

public class Act_ReplyDetail extends BaseActivity{
    @BindView(R.id.rv_pic_list)
    XListView mListView;
    private int page = 0;
    private int totalPage = 1;
    private JSONArray dataArray = new JSONArray();
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String id;
    private NineGridAdapter mAdapter;
    private List<NineGridModel> mList;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_replydetail;
    }

    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "GetSuperviseManager_ReplyList", new MyCallBack() {

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
                        Toast.makeText(Act_ReplyDetail.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(object.length()>0){
                        dataArray = object.optJSONArray("recordList");
                        for (int i = 0; i < dataArray.length(); i++) {//
//                            dataArray.put(jsonArray.optJSONObject(i));
                            JSONObject tempobject = dataArray.optJSONObject(i);
                            NineGridModel model = new NineGridModel();
                            model.setUser(tempobject.optString("userName"));
                            model.setContent(tempobject.optString("content"));
                            model.setCreateTime(tempobject.optString("readTime"));
                            model.setStatus(tempobject.optInt("status"));
                            JSONArray array = tempobject.optJSONArray("itemFileList");
                            List<String> imglist = new ArrayList<String>();
                            for(int j=0;j<array.length();j++){
                                JSONObject tempobject2 = array.optJSONObject(j);
                                imglist.add(tempobject2.optString("url"));
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
