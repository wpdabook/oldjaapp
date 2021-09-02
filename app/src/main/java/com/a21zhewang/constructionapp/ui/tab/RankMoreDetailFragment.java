package com.a21zhewang.constructionapp.ui.tab;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 综合排名
 * Created by WP-PC on 2019/8/13.
 */

public class RankMoreDetailFragment extends Fragment{
    private View v;
    private MyBaseAdapter adapter;
    private String id;
    private LinearLayout rank_search_lin1,rank_search_lin2;
    private LinearLayout rank_search_lin3,rank_search_lin4;
    private TextView text_rote;
    private int page = 1;
    private int totalPage = 1;
    private LinearLayout placeHolder;
    private JSONArray dataArray = new JSONArray();
    private RadioGroup rg1,rg2,rg3,rg4;
    private String sortType = "desc"; //正序 asc-倒序
    private int unit = 1;
    private XListView mListView;
    private Dialog loadingDialog;



    public void initData() {
        id = getArguments().getString("id");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        init(true,unit);
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
            v = inflater.inflate(R.layout.rank_more_detail_layout, null, false);
            rank_search_lin1 = (LinearLayout) v.findViewById(R.id.rank_search_lin1);
            rank_search_lin2 = (LinearLayout) v.findViewById(R.id.rank_search_lin2);
            rank_search_lin3 = (LinearLayout) v.findViewById(R.id.rank_search_lin3);
            rank_search_lin4 = (LinearLayout) v.findViewById(R.id.rank_search_lin4);
            text_rote = (TextView)v.findViewById(R.id.text_rote);
            rg1 = (RadioGroup)v.findViewById(R.id.rg1);
            rg2 = (RadioGroup)v.findViewById(R.id.rg2);
            rg3 = (RadioGroup)v.findViewById(R.id.rg3);
            rg4 = (RadioGroup)v.findViewById(R.id.rg4);
            rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedId == R.id.rb_top_five_1) {
                        sortType = "desc";
                        page = 1;
                        loadData(true,sortType,unit);
                    }else if(checkedId == R.id.rb_last_five_1){
                        sortType = "asc";
                        page = 1;
                        loadData(true,sortType,unit);
                    }
                }
            });
            rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedId == R.id.rb_js_unit) {
                        unit = 1;
                        page = 1;
                        loadData(true,sortType,unit);
                    }else if(checkedId == R.id.rb_sg_unit){
                        unit = 2;
                        page = 1;
                        loadData(true,sortType,unit);
                    }else if(checkedId == R.id.rb_jl_unit){
                        unit = 3;
                        page = 1;
                        loadData(true,sortType,unit);
                    }
                }
            });
            rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedId == R.id.rb_top_five_2) {
                        sortType = "desc";
                        page = 1;
                        loadData(true,sortType,unit);
                    }else if(checkedId == R.id.rb_last_five_2){
                        sortType = "asc";
                        page = 1;
                        loadData(true,sortType,unit);
                    }
                }
            });
            rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if (checkedId == R.id.rb_top_five_3) {
                        sortType = "desc";
                        page = 1;
                        loadData(true,sortType,unit);
                    }else if(checkedId == R.id.rb_last_five_3){
                        sortType = "asc";
                        page = 1;
                        loadData(true,sortType,unit);
                    }
                }
            });
            placeHolder = (LinearLayout)v.findViewById(R.id.placeholder);
            mListView = (XListView)v.findViewById(R.id.rank_xlistview);
            mListView.setPullLoadEnable(true);
            mListView.setPullRefreshEnable(true);
            mListView.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    page = 1;
                    loadData(false,sortType,unit);
                }

                @Override
                public void onLoadMore() {
                    page++;
                    loadData(false,sortType,unit);
                }
            });
            adapter = new MyBaseAdapter() {
                @Override
                public View getView(int position, View arg1, ViewGroup parent) {
                    final JSONObject object = dataArray.optJSONObject(position);
                    if (arg1 == null) {
                        arg1 = getActivity().getLayoutInflater().inflate(
                                R.layout.rank_more_detail_item__fragment, null);
                    }
                    arg1.setVisibility(View.VISIBLE);
                    TextView name = MViewHolder.get(arg1, R.id.project_name);
                    TextView end = MViewHolder.get(arg1, R.id.project_type);
                    TextView add = MViewHolder.get(arg1, R.id.project_check_number);
                    TextView rate = MViewHolder.get(arg1, R.id.rate);
                    LinearLayout item_linearlayout = MViewHolder.get(arg1, R.id.item_linearlayout);

                    TextView tv_sort = MViewHolder.get(arg1, R.id.tv_sort_one);
                    item_linearlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!TextUtils.isEmpty(object.optString("projectID"))){
                                startActivity(new Intent(getActivity(),Act_RankDetail2.class)
                                        .putExtra("projectId",object.optString("projectID"))
                                        .putExtra("tag",1));
                            }
                        }
                    });
                    if(object.length()>0){
                        int sortnum = position + 1;
                        tv_sort.setText(sortnum+"");
                        name.setText(object.optString("projectName"));

//                        Double b = object.optDouble("keyCompleteRate");
//                        String number = Integer.parseInt(new DecimalFormat("0").format(b*100))+"%";
//                        add.setText("重点检查完成率：" + number);
                        DecimalFormat df2 = new DecimalFormat("######0.00");
                        Double b = object.optDouble("keyCompleteRate");
                        add.setText("重点检查完成率：" + df2.format(b)+"%");

                        DecimalFormat df = new DecimalFormat("######0.00");
                        rate.setText(df2.format(b)+"%");
                        if("4".equals(id)){
                            rank_search_lin1.setVisibility(View.GONE);
                            rank_search_lin2.setVisibility(View.GONE);
                            rank_search_lin3.setVisibility(View.GONE);
                            rank_search_lin4.setVisibility(View.VISIBLE);
//                            end.setText("排查项："+object.optInt("sore"));
//                            score.setVisibility(View.GONE);
                            text_rote.setText("完成率");
                            add.setVisibility(View.GONE);
                            end.setVisibility(View.GONE);
                            rate.setText(df2.format(b)+"%");
                        }
                        if("5".equals(id)){
                            rank_search_lin1.setVisibility(View.GONE);
                            rank_search_lin2.setVisibility(View.GONE);
                            rank_search_lin3.setVisibility(View.GONE);
                            rank_search_lin4.setVisibility(View.VISIBLE);
//                            end.setText("排查项："+object.optInt("sore"));
//                            score.setVisibility(View.GONE);
                            text_rote.setText("整改率");
                            add.setVisibility(View.GONE);
                            end.setVisibility(View.GONE);
                            rate.setText(df2.format(b)+"%");
                        }
                        if("6".equals(id)){
                            rank_search_lin1.setVisibility(View.GONE);
                            rank_search_lin2.setVisibility(View.GONE);
                            rank_search_lin3.setVisibility(View.GONE);
                            rank_search_lin4.setVisibility(View.VISIBLE);
//                            end.setText("排查项："+object.optInt("sore"));
//                            score.setVisibility(View.GONE);
                            text_rote.setText("参与率");
                            add.setVisibility(View.GONE);
                            end.setVisibility(View.GONE);
                            rate.setText(df2.format(b)+"%");
                        }
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
        }else {
            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
        }
        return v;
    }

    private void init(boolean needDialog,int unit) {
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"paramType\":\"" + id + "\",\"orderBy\":\"" + "desc" +  "\",\"unit\":\"" + unit +
                "\",\"pageIndex\":\"" +  String.valueOf(page) + "\"}"), "GetDataTotal_syntheticalrankingTotal", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject object = new JSONObject(res);
                    if (page == 1) {
                        dataArray = new JSONArray();
                    }
                    JSONArray jsonArray = object.optJSONArray("secrecunre_secModels");
                    for (int i = 0; i < jsonArray.length(); i++) {//
                        dataArray.put(jsonArray.optJSONObject(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(adapter != null){
                    adapter.notifyDataSetChanged();
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
    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog, String sortType, int unit) {
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"paramType\":\"" + id + "\",\"orderBy\":\"" + sortType +"\",\"unit\":\"" + unit +
                "\",\"pageIndex\":\"" +  String.valueOf(page) + "\"}"), "GetDataTotal_syntheticalrankingTotal", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject object = new JSONObject(res);
                    if (page == 1) {
                        dataArray = new JSONArray();
                    }
                    totalPage = object.optInt("total");
                    if (totalPage > 1) {
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
                    JSONArray jsonArray = object.optJSONArray("secrecunre_secModels");
                    for (int i = 0; i < jsonArray.length(); i++) {//
                        dataArray.put(jsonArray.optJSONObject(i));
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
}
