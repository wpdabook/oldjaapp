package com.a21zhewang.constructionapp.ui.set;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by WP-PC on 2020/3/11.
 */

public class FeedbackHistoryActivity extends BaseActivity{
    @BindView(R.id.feedback_xlistview)
    XListView mListView;
    private int page = 0;
    private int totalPage = 1;
    private JSONArray dataArray = new JSONArray();
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private MyBaseAdapter adapter;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_feedback_history;
    }

    @Override
    public void initViews() {
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
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                final JSONObject object = dataArray.optJSONObject(position);
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(R.layout.feedback_history_item, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView info = MViewHolder.get(arg1, R.id.info);
                TextView request = MViewHolder.get(arg1, R.id.request);
                if(object.length()>0){
                    info.setText(object.optString("feedback"));
                    request.setText(object.optString("reply"));
                }
                arg1.setTag(R.id.tag_first, object);
                return arg1;
            }
            @Override
            public int getCount() {
                return dataArray.length();
            }
        };
        mListView.setAdapter(adapter);
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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "getFeedbackList", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
                    }
                    totalPage = object.optInt("totalPage");
                    if (totalPage > 0) {
                        mListView.setPullLoadEnable(true);
                    } else {
                        mListView.setPullLoadEnable(false);
                    }
                    if (page > totalPage) {
                        page = totalPage;
                        mListView.stopLoadMore();
                        Toast.makeText(FeedbackHistoryActivity.this, "已无更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("recordList");
                        for (int i = 0; i < jsonArray.length(); i++) {//
                            dataArray.put(jsonArray.optJSONObject(i));
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
                if(adapter != null){
                    adapter.notifyDataSetChanged();
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
