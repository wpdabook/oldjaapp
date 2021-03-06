package com.a21zhewang.constructionapp.ui.check;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import com.a21zhewang.constructionapp.customview.NoScrollListView;
import com.a21zhewang.constructionapp.customview.SearchView;
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

import static com.a21zhewang.constructionapp.R.id.SearchView_top;


/**
 * η»Όεζε
 * Created by WP-PC on 2019/8/13.
 */

public class ProceduralFragment extends Fragment{
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


    public void refresh() {
        MobclickAgent.onPageStart("η¨εΊζ£ζ₯");
        page = 0;
        loadData(true,status,type);

    }

    public void initData() {
        MobclickAgent.onPageStart("η¨εΊζ£ζ₯");
//        if (!hasInitData) {
//            loadData(true,status,type,keyword);
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (v == null) {
            typeLists = getArguments().getParcelableArrayList("typeLists");
            statusLists = getArguments().getParcelableArrayList("statusLists");
            status = getArguments().getString("status");
            statusName = getArguments().getString("statusName");
            type = getArguments().getInt("type"); //ι»θ?€ζΎη€Ίη¬¬ε ηΊ§
            v = inflater.inflate(R.layout.layout_saferankfragment, null, false);
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
                    loadData(true,status,type);
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
                    loadData(true,status,type);
                }

                @Override
                public void onLoadMore() {
                    page++;
                    loadData(true,status,type);
                }
            });
            adapter = new MyBaseAdapter() {
                @Override
                public View getView(int position, View arg1, ViewGroup parent) {
                    final JSONObject object = dataArray.optJSONObject(position);
                    //safe_rank_fragment_item
                    if (arg1 == null) {
                        arg1 = getActivity().getLayoutInflater().inflate(
                                R.layout.fragment_procedural_fragment, null);
                    }
                    arg1.setVisibility(View.VISIBLE);
                    TextView name = MViewHolder.get(arg1, R.id.project_name);
                    TextView type = MViewHolder.get(arg1, R.id.project_type);
                    TextView rate = MViewHolder.get(arg1, R.id.rank_rate);
                    TextView risk_number = MViewHolder.get(arg1, R.id.risk_number);
                    TextView people = MViewHolder.get(arg1,R.id.complate_people);
                    HorzinonlChartView scrollseekbar = MViewHolder.get(arg1,R.id.scrollseekbar);
                    SeekBar seekBar = MViewHolder.get(arg1,R.id.seekbar);
                    final int inspectionProgressNum = object.optInt("inspectionProgress");
                    if(object.length()>0){
//                        int sortnum = position + 1;
//                        tv_sort.setText(sortnum+"");
                        seekBar.setSecondaryProgress(inspectionProgressNum);
                        name.setText(object.optString("proShortName"));
                        type.setText("ζ£ζ₯η±»εοΌ"+object.optString("dataType"));
                        if("2".equals(status) || "4".equals(status)){ //2:εΎζ£ζ₯  4οΌζεδ»»ε‘
                            rate.setText("ζ¨ιζΆι΄οΌ"+object.optString("createTime"));
                        }else{
                            rate.setText("ζ£ζ₯ζΆι΄οΌ"+object.optString("checkTime"));
                        }
                        risk_number.setText("ι£ι©ζ°ιοΌ"+object.optInt("riskCount")+"ζ‘");
                        scrollseekbar.setNumber(object.optInt("inspectionProgress"));
//                        people.setText("θ΄θ΄£δΊΊοΌ"+object.optString("etpContacts"));

//                        people.setText(object.optString("creatorUnit")+":"+object.optString("creatorName"));
                        people.setText("ζ£ζ₯δΊΊεοΌ"+object.optString("creatorName")); //+object.optString("etpTypeName") εδ½ζ§θ΄¨
//                        Double b = object.optDouble("keyCompleteRate");
//                        String number = Integer.parseInt(new DecimalFormat("0").format(b*100))+"%";
//                        add.setText("ιηΉζ£ζ₯ε?ζηοΌ" + number);

//                        DecimalFormat df2 = new DecimalFormat("######0.00");
//                        Double b = object.optDouble("keyCompleteRate");
//                        rate.setText("ιηΉζ£ζ₯ε?ζηοΌ" + df2.format(b)+"%");

//                        DecimalFormat df = new DecimalFormat("######0.00");
//                        number.setText(df.format(object.optDouble("sore"))+"ε");
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
            i.putExtra("status",status);
            i.putExtra("projectID",object.optString("projectID"));
            i.putExtra("recordId",object.optString("recordID"));
            i.putExtra("proShortName",object.optString("proShortName"));
            i.putExtra("creatorUnit",object.optString("creatorUnit"));
            i.putExtra("creatorName",object.optString("creatorName"));
            i.putExtra("dataType",object.optString("dataType"));
            i.putExtra("riskCount",object.optInt("riskCount"));
            i.putExtra("inspectionProgress",object.optInt("inspectionProgress"));
            i.setClass(getActivity(), Act_ProceduralDetail.class);
            startActivity(i);
            MobclickAgent.onPageEnd("η¨εΊζ£ζ₯");

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
     * ε¨ζζ·»ε RadioButtonδΊηΊ§ζ ι’
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
                tempButton.setBackgroundResource(R.drawable.blue_select_middle_bg);	// θ?Ύη½?RadioButtonηθζ―εΎη
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
     * 1:θΏδΈε¨ 2οΌθΏδΈζ
     * @param needDialog
     */
    private void loadData(boolean needDialog, String listType, int listStatus) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"listType\":\"" + listType + "\",\"keyword\":\"" + ((Act_Procedural)getActivity()).getKeyWord() + "\",\"listStatus\":\"" + listStatus +  "\"," +
                "\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "GetListProgExamination", new MyCallBack() {

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
                        Toast.makeText(getActivity(), "ε·²ζ ζ΄ε€ζ°ζ?", Toast.LENGTH_SHORT).show();
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
