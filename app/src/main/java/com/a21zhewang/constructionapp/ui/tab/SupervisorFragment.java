package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.StatusList;
import com.a21zhewang.constructionapp.bean.SupervisorInfo;
import com.a21zhewang.constructionapp.bean.TypeList;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 综合排名
 * Created by WP-PC on 2019/8/13.
 */

public class SupervisorFragment extends Fragment{
    private View v;
    private boolean hasInitData;
    private MyBaseAdapter adapter;
    private int page = 0;
    private int totalPage = 1;
    private LinearLayout placeHolder;
    private JSONArray dataArray = new JSONArray();
    private XListView mListView;
    private String etpId;
    private String projectId;



    public void refresh() {
        page = 0;
        loadData(true);

    }

    public void initData() {
        if (!hasInitData) {
            loadData(true);
            hasInitData = true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            projectId = getArguments().getString("projectId");
            etpId = getArguments().getString("etpId");
            v = inflater.inflate(R.layout.act_supervisor_fragment, null, false);

            placeHolder = (LinearLayout)v.findViewById(R.id.placeholder);
            mListView = (XListView)v.findViewById(R.id.supervisor_xlistview);
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
                            arg1 = getActivity().getLayoutInflater().inflate(
                                    R.layout.supervisor_fragment_item, null);
                        }
                    arg1.setVisibility(View.VISIBLE);
                    TextView name = MViewHolder.get(arg1, R.id.sp_name);
                    TextView work_position = MViewHolder.get(arg1, R.id.work_position);
                    TextView sp_score = MViewHolder.get(arg1, R.id.sp_score);
                    TextView sp_remind = MViewHolder.get(arg1, R.id.sp_remind);
                    TextView tv_sort = MViewHolder.get(arg1, R.id.tv_sort_one);

                    if(object.length()>0){
                        int sortnum = position + 1;
                        tv_sort.setText(sortnum+"");
                        name.setText(object.optString("userName"));
                        work_position.setText(object.optString("dicName"));
                        BaseRateValues(sp_score,object.optDouble("SumCheckrate"));
                        sp_remind.setText("重点检查完成率");
                    }
                    arg1.setTag(R.id.tag_first, object);
//                    arg1.setOnClickListener(noDoubleClickListener);
                    return arg1;
                }
                @Override
                public int getCount() {
                    return dataArray.length();
                }
            };
            mListView.setAdapter(adapter);
        }else {
            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
        }
        return v;
    }
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        void onNoDoubleClick(View v) {
            JSONObject object = (JSONObject) v.getTag(R.id.tag_first);
            Intent i = new Intent();
            i.putExtra("projectID",object.optString("projectID"));
            i.putExtra("recordId",object.optString("recordID"));
            i.putExtra("proShortName",object.optString("proShortName"));
            i.putExtra("creatorUnit",object.optString("creatorUnit"));
            i.putExtra("creatorName",object.optString("creatorName"));
            i.putExtra("dataType",object.optString("dataType"));
            i.putExtra("riskCount",object.optInt("riskCount"));
            i.putExtra("inspectionProgress",object.optInt("inspectionProgress"));
            i.setClass(getActivity(), SupervisorFragment.class);
            startActivity(i);

        }
    };
    public abstract class NoDoubleClickListener implements View.OnClickListener {

        public static final int MIN_CLICK_DELAY_TIME = 500;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }
        abstract void onNoDoubleClick(View v);
    }

    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"etpId\":\"" + etpId +  "\"," +
                "\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "GetDataTotal2_rectProjectdangerTotal_EtpUserList", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
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
                        Toast.makeText(getActivity(), "已无更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("etpSectionList");
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
    public void  BaseRateValues(TextView textView, Double b){
        if(b.intValue() == 0){
            textView.setText("0%");
        }else {
            DecimalFormat df = new DecimalFormat("######0.00");
            textView.setText(df.format(b)+"%");
        }
    }
}
