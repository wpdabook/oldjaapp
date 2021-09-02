package com.a21zhewang.constructionapp.ui.yiqing;

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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.StatusList;
import com.a21zhewang.constructionapp.bean.TypeList;
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by WP-PC on 2019/8/13.
 */

public class YiQingFragment extends Fragment{
    private View v;
    private boolean hasInitData;
    private MyBaseAdapter adapter;
    private int page = 0;
    private int totalPage = 1;
    private LinearLayout placeHolder;
    private JSONArray dataArray = new JSONArray();
    private RadioGroup radiogroup;
    private XListView mListView;
    private String statusName;
    private int type = 0;
    private String status;
    private List<TypeList> typeLists = new ArrayList<>();
    private List<StatusList> statusLists = new ArrayList<>();
    private String keyword="";


    public void refresh(String keyword) {
        MobclickAgent.onPageStart("疫情复工检查");
        page = 0;
        loadData(true,status,type,keyword);
    }

    public void initData() {
        MobclickAgent.onPageStart("疫情复工检查");
//        if (!hasInitData) {
//            loadData(true,status,type);
//            hasInitData = true;
//        }
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
            keyword = getArguments().getString("keyword");
            typeLists = getArguments().getParcelableArrayList("typeLists");
            statusLists = getArguments().getParcelableArrayList("statusLists");
            status = getArguments().getString("status");
            statusName = getArguments().getString("statusName");
            type = getArguments().getInt("type"); //默认显示第几级
            v = inflater.inflate(R.layout.yiqing_fragment_layout, null, false);
            radiogroup = (RadioGroup)v.findViewById(R.id.radiogroup);
            initGroupView(radiogroup,type);
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group,  int checkedId) {
                    RadioButton radioButton = (RadioButton) radiogroup.findViewById(group.getCheckedRadioButtonId());
                    int id = group.getCheckedRadioButtonId();
                    type = id;
                    if(radioButton.isChecked()){
                        radioButton.setChecked(true);
                    }else {
                        radioButton.setChecked(false);
                    }
                    page = 0;
                    loadData(true,status,type, PublicUtils.getspstring("YQ_keyword"));
                }
            });

            placeHolder = (LinearLayout)v.findViewById(R.id.placeholder);
            mListView = (XListView)v.findViewById(R.id.rank_xlistview);
            mListView.setPullLoadEnable(true);
            mListView.setPullRefreshEnable(true);
            mListView.setXListViewListener(new XListView.IXListViewListener() {
                @Override
                public void onRefresh() {
                    page = 0;
                    loadData(false,status,type, PublicUtils.getspstring("YQ_keyword"));
                }

                @Override
                public void onLoadMore() {
                    page++;
                    loadData(false,status,type, PublicUtils.getspstring("YQ_keyword"));
                }
            });
            adapter = new MyBaseAdapter() {
                @Override
                public View getView(int position, View arg1, ViewGroup parent) {
                    final JSONObject object = dataArray.optJSONObject(position);
                    //safe_rank_fragment_item
                    if (arg1 == null) {
                        arg1 = getActivity().getLayoutInflater().inflate(
                                R.layout.yiqing_fragment_layout_item, null);
                    }
                    arg1.setVisibility(View.VISIBLE);
                    TextView name = MViewHolder.get(arg1, R.id.project_name);
                    TextView type = MViewHolder.get(arg1, R.id.project_type);
                    TextView rate = MViewHolder.get(arg1, R.id.rank_rate);
                    TextView risk_number = MViewHolder.get(arg1, R.id.risk_number);
                    TextView people = MViewHolder.get(arg1,R.id.complate_people);
                    HorzinonlChartView scrollseekbar = MViewHolder.get(arg1,R.id.scrollseekbar);
                    SeekBar seekBar = MViewHolder.get(arg1,R.id.seekbar);
                    final Double inspectionResumWorkress = object.optDouble("inspectionResumWorkress");
                    if(object.length()>0){
//                        int sortnum = position + 1;
//                        tv_sort.setText(sortnum+"");
                        seekBar.setSecondaryProgress(inspectionResumWorkress.intValue());
                        name.setText(object.optString("proShortName"));
                        type.setText("检查类型："+object.optString("dataType"));
                        if("2".equals(status)){
                            rate.setText("推送时间："+object.optString("createTime"));
                        }else {
                            rate.setText("检查时间："+object.optString("checkTime"));
                        }
                        risk_number.setText("风险数量："+object.optInt("riskCount")+"条");
                        scrollseekbar.setNumber(object.optInt("inspectionProgress"));
//                        people.setText("负责人："+object.optString("etpContacts"));

//                        people.setText(object.optString("creatorUnit")+":"+object.optString("creatorName"));
                        people.setText("检查人员："+object.optString("creatorName")); //+object.optString("etpTypeName") 单位性质
//                        Double b = object.optDouble("keyCompleteRate");
//                        String number = Integer.parseInt(new DecimalFormat("0").format(b*100))+"%";
//                        add.setText("重点检查完成率：" + number);

//                        DecimalFormat df2 = new DecimalFormat("######0.00");
//                        Double b = object.optDouble("keyCompleteRate");
//                        rate.setText("重点检查完成率：" + df2.format(b)+"%");

//                        DecimalFormat df = new DecimalFormat("######0.00");
//                        number.setText(df.format(object.optDouble("sore"))+"分");
                    }
                    arg1.setTag(R.id.tag_first, object);
                    arg1.setOnClickListener(noDoubleClickListener);
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
            final Double inspectionResumWorkress = object.optDouble("inspectionResumWorkress");
            Intent i = new Intent();
            i.putExtra("status",status);
            i.putExtra("projectID",object.optString("projectID"));
            i.putExtra("recordId",object.optString("recordID"));
            i.putExtra("proShortName",object.optString("proShortName"));
            i.putExtra("creatorUnit",object.optString("creatorUnit"));
            i.putExtra("creatorName",object.optString("creatorName"));
            i.putExtra("dataType",object.optString("dataType"));
            i.putExtra("riskCount",object.optInt("riskCount"));
            i.putExtra("inspectionProgress",inspectionResumWorkress.intValue());
            i.setClass(getActivity(), Act_YiQingCheckDetail.class);
            startActivity(i);
            MobclickAgent.onPageEnd("疫情复工检查");
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
     * 动态添加RadioButton二级标题
     * @param group
     * @param type
     */
    private void initGroupView(RadioGroup group,int type){
        for(int i=0; i<statusLists.size(); i++)
        {
            RadioButton tempButton = new RadioButton(getActivity());
            if(i == 0){
                tempButton.setBackgroundResource(R.drawable.blue_select_left_bg);
            }else if(i == statusLists.size() - 1){
                tempButton.setBackgroundResource(R.drawable.blue_select_right_bg);
            }else {
                tempButton.setBackgroundResource(R.drawable.blue_select_middle_bg);	// 设置RadioButton的背景图片
            }
            tempButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            tempButton.setTextColor(getActivity().getResources().getColorStateList(R.color.blue_select_text_bg));
            tempButton.setGravity( Gravity.CENTER );
            tempButton.setTextSize(14);
//            tempButton.setPadding(25, 10, 25, 10);
            tempButton.setPadding(30, 10, 30, 10);
            tempButton.setText(statusLists.get(i).getTypeName());
            tempButton.setId(i);
            group.addView(tempButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(type == i){
                tempButton.setChecked(true);
            }else {
                tempButton.setChecked(false);
            }
        }
    }
    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog, String listType, int listStatus,String keyword) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"listType\":\"" + listType + "\",\"keyword\":\"" + keyword + "\",\"listStatus\":\"" + listStatus +  "\"," +
                "\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "GetListResumWorkExamination", new MyCallBack() {

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
}
