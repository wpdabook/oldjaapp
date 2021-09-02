package com.a21zhewang.constructionapp.ui.kqgl;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.HiddenBean;
import com.a21zhewang.constructionapp.bean.KqglDetailBean;
import com.a21zhewang.constructionapp.customview.HorizontalListView;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.utils.JsonArrayRequest;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Administrator on 2021/4/27.
 */

public class Act_KqglDetail extends BaseActivity{
    private String GroupId;
    private Dialog loadingDialog;
    private String ProjectDetailListUrl = null;
    private String ProjectDetailListNearlyUrl = null;
    private String BaseUrl = "";
    private List<KqglDetailBean> kqglDetailBeanList = new ArrayList<>();
    private List<KqglDetailBean> kqglDetailNearBeanList = new ArrayList<>();
    @BindView(R.id.kqgldetail_list)
    MyListView mListView;
    @BindView(R.id.horizontal_listview)
    HorizontalListView listView;
    private MyBaseAdapter adapter;
    private MyHorizontalAdapter hadapter;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_kqgldetail_layout;
    }

    @Override
    public void initViews() {
        GroupId = getIntent().getStringExtra("GroupId");
        BaseUrl = getIntent().getStringExtra("BaseUrl");
        ProjectDetailListUrl = BaseUrl+"v1/bigscreen/etp/AttendanceByGroup?etpId=QY2020112370b2f&&dashBoadType=TOB&groupId="+GroupId;
        ProjectDetailListNearlyUrl = BaseUrl+"v1/bigscreen/etp/AttendanceNearly?etpId=QY2020112370b2f&&dashBoadType=TOB&groupId="+GroupId;
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_KqglDetail.this, "加载中...");
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(
                            R.layout.act_kqgldetail_item_layout, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView user = MViewHolder.get(arg1, R.id.tv_user);
                TextView days = MViewHolder.get(arg1, R.id.tv_days);
                TextView rate = MViewHolder.get(arg1, R.id.tv_rate);
                TextView tv_sort = MViewHolder.get(arg1, R.id.tv_sort_one);
                if(kqglDetailBeanList.size()>0){
                    user.setText(kqglDetailBeanList.get(position).getName());
                    days.setText(kqglDetailBeanList.get(position).getAttendanceCount()+"");

                    DecimalFormat df2 = new DecimalFormat("######0.00");
                    Double b = kqglDetailBeanList.get(position).getAttendanceRate();
                    rate.setText(df2.format(b*100.f)+"%");

                    int sortnum = position + 1;
                    tv_sort.setText(sortnum+"");
                }
                arg1.setTag(R.id.tag_first, kqglDetailBeanList);
                return arg1;
            }
            @Override
            public int getCount() {
                return kqglDetailBeanList.size();
            }
        };
        hadapter = new MyHorizontalAdapter(kqglDetailNearBeanList);
        mListView.setAdapter(adapter);
        listView.setAdapter(hadapter);
    }

    /**
     * （二）获取班组的详细人员考勤信息
     */
    public void loadAttendanceByGroup(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                ProjectDetailListUrl,null,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray array) {
                try{
                    if(kqglDetailBeanList.size()>0){
                        kqglDetailBeanList.clear();
                    }
                    for(int i=0;i<array.length();i++){
                        JSONObject object = (JSONObject)array.opt(i);
                        KqglDetailBean kqglDetailBean = new KqglDetailBean();
                        kqglDetailBean.setName(object.optString("Name"));
                        kqglDetailBean.setAttendanceCount(object.optInt("AttendanceCount"));
                        kqglDetailBean.setUnAttendanceCount(object.optInt("UnAttendanceCount"));
                        kqglDetailBean.setAttendanceRate(object.optDouble("AttendanceRate"));
                        kqglDetailBean.setAttendance(object.optBoolean("IsAttendance"));
                        kqglDetailBean.setWorkTypeName(object.optString("WorkTypeName"));
                        kqglDetailBeanList.add(kqglDetailBean);
                    }
                    loadAttendanceNearly();
                }catch (Exception e){
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("请求失败");
            }
        });
        MyAppCon.getHttpQueue().add(request);
    }

    /**
     * （三）获取最近考勤人员打卡信息
     */
    public void loadAttendanceNearly(){
        loadingDialog.show();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                ProjectDetailListNearlyUrl,null,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray array) {
                try{
                    loadingDialog.dismiss();
                    if(kqglDetailNearBeanList.size()>0){
                        kqglDetailNearBeanList.clear();
                    }
                    for(int i=0;i<array.length();i++){
                        JSONObject object = (JSONObject)array.opt(i);
                        KqglDetailBean kqglDetailBean = new KqglDetailBean();
                        kqglDetailBean.setName(object.optString("Name"));
                        kqglDetailBean.setRecentPhoto(object.optString("RecentPhoto"));
                        kqglDetailBean.setLastAttendanceTime(object.optString("LastAttendanceTime"));
                        kqglDetailNearBeanList.add(kqglDetailBean);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                hadapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("请求失败");
            }
        });
        MyAppCon.getHttpQueue().add(request);
    }

    public class MyHorizontalAdapter extends MyBaseAdapter {
        private List<KqglDetailBean> NearBeanList;

        private MyHorizontalAdapter(List<KqglDetailBean> list) {
            this.NearBeanList = list;
        }
        @Override
        public int getCount() {
            return NearBeanList.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getLayoutInflater().inflate(
                        R.layout.act_kqgldetail_nearly_item, null);
            }
            ImageView image = MViewHolder.get(view, R.id.rel_item_image);
            TextView name = MViewHolder.get(view, R.id.tv_name);
            TextView time = MViewHolder.get(view, R.id.tv_time);
            String url = NearBeanList.get(position).getRecentPhoto();
            if(NearBeanList.size()>0){
                name.setText(NearBeanList.get(position).getName());
                time.setText(NearBeanList.get(position).getLastAttendanceTime());
            }
            if (!TextUtils.isEmpty(url)) {
                Glide.with(Act_KqglDetail.this).load(url).into(image);
            } else {
                image.setImageResource(R.mipmap.launcher);
            }
            view.setTag(R.id.tag_first, NearBeanList);
            return view;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadAttendanceByGroup();
    }

    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
