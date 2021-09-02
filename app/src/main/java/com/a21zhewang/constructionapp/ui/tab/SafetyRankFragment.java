package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.SaveStatus;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.ui.check.Act_ProceduralDetail;
import com.a21zhewang.constructionapp.ui.check.ProceduralFragment;
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

import butterknife.BindView;


/**
 * 综合排名
 * Created by WP-PC on 2019/8/13.
 */

public class SafetyRankFragment extends Fragment{
    private View v;
    private boolean hasInitData;
    private MyBaseAdapter adapter;
    private String id;
    private int page = 0;
    private int totalPage = 1;
    private LinearLayout placeHolder;
    private JSONArray dataArray = new JSONArray();
    private RadioGroup radiogroup;
    private String sortType = "desc"; //正序 asc-倒序
    private int unit = 1;
    private XListView mListView;
    private String statusName;
    private int level = 0;
    private List<SaveStatus> safeList = new ArrayList<>();
    private int dateType;


    public void refresh() {
        page = 0;
        loadData(true,statusName,level);
    }

    public void initData() {
        if (!hasInitData) {
            loadData(true,statusName,level);
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
            safeList = getArguments().getParcelableArrayList("safeList");
            dateType = getArguments().getInt("dateType",0);
            statusName = getArguments().getString("statusName");
            level = getArguments().getInt("level"); //默认显示第几级
            v = inflater.inflate(R.layout.layout_saferankfragment, null, false);
            radiogroup = (RadioGroup)v.findViewById(R.id.radiogroup);
            initGroupView(radiogroup,level);
            radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group,  int checkedId) {
                    RadioButton radioButton = (RadioButton) radiogroup.findViewById(group.getCheckedRadioButtonId());
//                    int id = group.getCheckedRadioButtonId();
//                    level = id;
                    if(radioButton.isChecked()){
                        radioButton.setChecked(true);
                    }else {
                        radioButton.setChecked(false);
                    }
//                    String answer = radioButton.getText().toString().substring(0,1);
                    String answer = radioButton.getText().toString();
                    if("一级风险".equals(answer)){
                        level = 1;
                    }
                    if("二级风险".equals(answer)){
                        level = 2;
                    }
                    if("三级风险".equals(answer)){
                        level = 3;
                    }
                    if("四级风险".equals(answer)){
                        level = 4;
                    }
                    if("五级风险".equals(answer)){
                        level = 5;
                    }
//                    level = Integer.valueOf(answer);
//                    Toast.makeText(getActivity(),level+"",Toast.LENGTH_SHORT).show();
                    page = 0;
                    loadData(true,statusName,level);
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
                    loadData(false,statusName,level);
                }

                @Override
                public void onLoadMore() {
                    page++;
                    loadData(false,statusName,level);
                }
            });
            adapter = new MyBaseAdapter() {
                @Override
                public View getView(int position, View arg1, ViewGroup parent) {
                    final JSONObject object = dataArray.optJSONObject(position);
                    if (arg1 == null) {
                        arg1 = getActivity().getLayoutInflater().inflate(
                                R.layout.safety_rank_fragment_layout_item1, null);
                    }
                    arg1.setVisibility(View.VISIBLE);
                    TextView name = MViewHolder.get(arg1, R.id.project_name);
                    TextView type = MViewHolder.get(arg1, R.id.project_type);
                    TextView rate = MViewHolder.get(arg1, R.id.rank_rate);
                    TextView risk_number = MViewHolder.get(arg1, R.id.risk_number);
                    TextView people = MViewHolder.get(arg1,R.id.complate_people);

                    TextView tv_sort = MViewHolder.get(arg1, R.id.tv_sort_one);
                    if(object.length()>0){
                        int sortnum = position + 1;
                        tv_sort.setText(sortnum+"");
                        name.setText(object.optString("projectName"));
                        type.setText(object.optString("projectTypeName"));
                        risk_number.setText(object.optInt("num")+"");
//                        rate.setText("整改完成率："+object.optInt("ractRate")+"%");
//                        if(!TextUtils.isEmpty(object.optString("etpContacts"))){
//                            people.setText("负责人："+object.optString("etpContacts"));
//                        }

//                        people.setText("负责人：店小二");
//                        Double b = object.optDouble("keyCompleteRate");
//                        String number = Integer.parseInt(new DecimalFormat("0").format(b*100))+"%";
//                        add.setText("重点检查完成率：" + number);

//                        DecimalFormat df2 = new DecimalFormat("######0.00");
//                        Double b = object.optDouble("keyCompleteRate");
//                        rate.setText("重点检查完成率：" + df2.format(b)+"%");

//                        DecimalFormat df = new DecimalFormat("######0.00");
//                        number.setText(df.format(object.optDouble("sore"))+"分");
//                          name.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(new Intent(getActivity(),Act_RiskHiddenDanger.class)
//                                        .putExtra("projectId",object.optString("projectId")).putExtra("projectName",object.optString("projectName")));
//                            }
//                        });

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
            Intent i = new Intent();
            i.putExtra("projectId",object.optString("projectId"));
            i.putExtra("rectType",1);
            i.putExtra("dateType",dateType);
            i.putExtra("level",level);
            i.putExtra("typeGraFaName",""); //施工类型
            i.putExtra("projectAreaName",""); //施工区域
            i.putExtra("statusName",statusName);
            i.putExtra("projectName",object.optString("projectName"));
            i.setClass(getActivity(), Act_Rectification.class);
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
     * 动态添加RadioButton二级标题
     * @param group
     * @param level
     */
    private void initGroupView(RadioGroup group,int level){
        for(int i=0; i<safeList.size(); i++)
        {
            RadioButton tempButton = new RadioButton(getActivity());
            if(i == 0){
                tempButton.setBackgroundResource(R.drawable.blue_select_left_bg);
            }else if(i == safeList.size() - 1){
                tempButton.setBackgroundResource(R.drawable.blue_select_right_bg);
            }else {
                tempButton.setBackgroundResource(R.drawable.blue_select_middle_bg);	// 设置RadioButton的背景图片
            }
            tempButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            tempButton.setTextColor(getActivity().getResources().getColorStateList(R.color.blue_select_text_bg));
            tempButton.setGravity( Gravity.CENTER );
            tempButton.setTextSize(13);
            tempButton.setPadding(30, 10, 30, 10);
            tempButton.setText(safeList.get(i).getLevelName());
            group.addView(tempButton, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(level - 1 == i ){ //因为风险等级是从1开始的
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
    private void loadData(boolean needDialog, String statusName, int level) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + dateType + "\",\"statusName\":\"" + statusName +  "\",\"level\":\"" + level +
                "\",\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "GetDataTotal2_rectProjectRectTotal", new MyCallBack() {

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
                    JSONArray jsonArray = object.optJSONArray("recordList");
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
