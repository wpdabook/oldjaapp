package com.a21zhewang.constructionapp.ui.wdjc;

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
import com.a21zhewang.constructionapp.bean.TypeList;
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
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
 * η»Όεζε
 * Created by WP-PC on 2019/8/13.
 */

public class WdjcFragment extends Fragment{
    private View v;
    private boolean hasInitData;
    public MyBaseAdapter adapter;
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
        page = 0;
        MobclickAgent.onPageStart("ιηΉζ£ζ₯");
        loadData(true,status,type,keyword);
    }

    public void initData(String keyword) {
        if (!hasInitData) {
            MobclickAgent.onPageStart("ιηΉζ£ζ₯");
            loadData(true,status,type,keyword);
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
            keyword = getArguments().getString("keyword");
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
                    loadData(true,status,type, PublicUtils.getspstring("WD_keyword"));
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
                    loadData(false,status,type, PublicUtils.getspstring("WD_keyword"));
                }

                @Override
                public void onLoadMore() {
                    page++;
                    loadData(false,status,type, PublicUtils.getspstring("WD_keyword"));
                }
            });
            adapter = new MyBaseAdapter() {
                @Override
                public View getView(int position, View arg1, ViewGroup parent) {
                    final JSONObject object = dataArray.optJSONObject(position);
                    //safe_rank_fragment_item
                    if (arg1 == null) {
                        arg1 = getActivity().getLayoutInflater().inflate(
                                R.layout.wdjcfragment_listview_item, null);
                    }
                    arg1.setVisibility(View.VISIBLE);
                    TextView tv_status_type = MViewHolder.get(arg1, R.id.tv_status_type_tag);
                    TextView tv_type = MViewHolder.get(arg1, R.id.tv_type);
                    TextView tv_status_type_tag = MViewHolder.get(arg1, R.id.tv_status_type_tag);
                    TextView tv_check_user = MViewHolder.get(arg1, R.id.zxjc_list_item_people);
                    TextView title = MViewHolder.get(arg1, R.id.zxjc_list_item_tv_pjtitle);
                    LinearLayout checktime_layout = MViewHolder.get(arg1, R.id.checktime_layout);
                    TextView name = MViewHolder.get(arg1, R.id.zxjc_list_item_tv_pjname);
                    TextView checkstation = MViewHolder.get(arg1, R.id.zxjc_list_item_station);
                    TextView plantime = MViewHolder.get(arg1, R.id.zxjc_list_item_tv_checkname);
                    TextView checktime = MViewHolder.get(arg1, R.id.wdjc_list_item_time);
                    TextView type = MViewHolder.get(arg1, R.id.project_type);
                    TextView rate = MViewHolder.get(arg1, R.id.rank_rate);
                    TextView risk_number = MViewHolder.get(arg1, R.id.risk_number);
                    TextView people = MViewHolder.get(arg1,R.id.complate_people);
                    HorzinonlChartView scrollseekbar = MViewHolder.get(arg1,R.id.scrollseekbar);
                    final int inspectionProgressNum = object.optInt("inspectionProgress");
                    if(object.length()>0){
//                        int sortnum = position + 1;
//                        tv_sort.setText(sortnum+"");
                        tv_check_user.setText(object.optString("createUserName"));
                        if(object.optInt("checkType")== 1){
                            tv_status_type.setText("ζ₯ζ₯ε" +" "+object.optInt("checkRate")+"%");
                        }else if(object.optInt("checkType")==2){
                            tv_status_type.setText("ε¨ζ₯ε" +" "+object.optInt("checkRate")+"%");
                        }else if(object.optInt("checkType")==3){
                            tv_status_type.setText("ζζ₯ε" +" "+object.optInt("checkRate")+"%");
                        }
                        if("1".equals(object.optString("status"))){
                            tv_type.setText("ζͺζ£ζ₯");
                            tv_type.setTextColor( Color.rgb(245, 36, 55));
                            tv_status_type_tag.setBackgroundResource(R.drawable.checkitem_shape_left_red);
                            checktime_layout.setVisibility(View.GONE);
                        }else if("2".equals(object.optString("status"))){
                            tv_type.setText("ζ£ζ₯δΈ­");
                            tv_type.setTextColor(Color.rgb(0, 132, 207));
                            tv_status_type_tag.setBackgroundResource(R.drawable.checkitem_shape_left_blue);
                            checktime_layout.setVisibility(View.VISIBLE);
                        }else if("3".equals(object.optString("status"))){
                            tv_type.setText("ε·²ε?ζ");
                            tv_type.setTextColor(Color.rgb(24, 182, 129));
                            tv_status_type_tag.setBackgroundResource(R.drawable.checkitem_shape_left);
                            checktime_layout.setVisibility(View.VISIBLE);
                        }
                        title.setText(object.optString("title"));
                        name.setText(object.optString("proShortName"));
                        checkstation.setText(object.optString("checkRegionName"));
                        plantime.setText(object.optString("planTime"));
                        checktime.setText(object.optString("checkTime"));
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
//    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
//        @Override
//        void onNoDoubleClick(View v) {
//            JSONObject object = (JSONObject) v.getTag(R.id.tag_first);
//            Intent i = new Intent();
//            i.putExtra("recordID",object.optString("recordID"));
//            i.putExtra("CheckStatus",status); //2:εΎζ£ζ₯  3οΌε·²ζδΊ€
//            i.putExtra("proShortName", object.optString("proShortName"));
//            i.putExtra("createUserName",object.optString("createUserName"));
//            i.putExtra("checkType",object.optInt("checkType")); //ζ₯ζ₯οΌε¨ζ₯οΌζε·?
//            i.setClass(getActivity(), WdjcxqActivity3.class);
//            startActivity(i);
//            MobclickAgent.onPageEnd("ιηΉζ£ζ₯");
//        }
//    };
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
    @Override
    void onNoDoubleClick(View v) {
        JSONObject object = (JSONObject) v.getTag(R.id.tag_first);
        Intent i = new Intent();
         if("com.a21zhewang.constructionapp.hyzj".equals(AppUtils.getPackageName(getActivity()))
                 ||"com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(getActivity()))){
            i.putExtra("recordID",object.optString("recordID"));
            i.putExtra("CheckStatus",status); //2:εΎζ£ζ₯  3οΌε·²ζδΊ€
            i.putExtra("proShortName", object.optString("proShortName"));
            i.putExtra("createUserName",object.optString("createUserName"));
            i.putExtra("checkType",object.optInt("checkType")); //ζ₯ζ₯οΌε¨ζ₯οΌζε·?
            i.setClass(getActivity(), WdjcxqActivity3.class);
        }else {
             i.putExtra("recordID",object.optString("recordID"));
             i.putExtra("CheckStatus",status); //2:εΎζ£ζ₯  3οΌε·²ζδΊ€
             i.putExtra("proShortName", object.optString("proShortName"));
             i.putExtra("createUserName",object.optString("createUserName"));
             i.putExtra("checkType",object.optInt("checkType")); //ζ₯ζ₯οΌε¨ζ₯οΌζε·?
        /*
         * οΌδΈοΌεΎζ£ζ₯
         * 1.ε¦ζζδΊ€ζͺζ½ε·₯δΏ‘ζ―ε°±η΄ζ₯ε°ζ£ζ₯θ?°ε½
         * 2.ε¦ζζδΊ€ε·²ζ½ε·₯δΏ‘ζ―ε°±η΄ζ₯ε°ζ£ζ₯ι‘΅ι’
         * 3.ε¦ζζͺζδΊ€ζ½ε·₯δΏ‘ζ―ε°±θ·³θ½¬ε°ζδΊ€ζ½ε·₯δΏ‘ζ―ι‘΅ι’
         * οΌδΊοΌζ£ζ₯θ?°ε½
         * θ―¦ζι‘΅ζ·»ε ζ½ε·₯δΏ‘ζ―ε₯ε£οΌδΎη¨ζ·ζ₯ηγ
         */
             if("".equals(object.optString("areaStatus"))){
                 i.setClass(getActivity(), WdjcProjectInfoActivity.class);
             }else {
                 i.setClass(getActivity(), WdjcxqActivity3.class);
             }
         }
        startActivity(i);
        MobclickAgent.onPageEnd("ιηΉζ£ζ₯");
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
    private void loadData(boolean needDialog, String listType, int listStatus,String keyword) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"listType\":\"" + listType + "\",\"listStatus\":\"" + listStatus + "\",\"keyword\":\"" + keyword +  "\"," +
                "\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}"), "GetListKeyExamination", new MyCallBack() {

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
