package com.a21zhewang.constructionapp.ui.kqgl;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.KqBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.map.Act_MapSearch;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonArrayRequest;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import butterknife.BindView;

import static android.R.id.input;

/**
 * Created by Administrator on 2021/4/27.
 */

public class Act_Kqgl extends BaseActivity{
    private Dialog loadingDialog;
    private String BaseUrl = "";
    private String ProjectListUrl = "";
    private String AttendanceUrl = "https://xmgl.glodon.com/glm/services/worker-service/enterprise/digging/human/index?&orgFullId=441489374863360&prjStatus=1&companyType=-1&postType=-1";
    private List<KqBean> kqBeanList = new ArrayList<>();
    private MyBaseAdapter adapter;
    @BindView(R.id.kqgl_mlistview)
    ListView mListView;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    @BindView(R.id.attendance_number)
    TextView attendance_number;
    @BindView(R.id.attendance_today)
    TextView attendance_today;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_kqgl_layout;
    }

    @Override
    public void initViews() {
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))){
            BaseUrl = "https://whdc-safe.telsafe.com.cn:9003/";
        }else if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            BaseUrl = "https://data-cqjg.telsafe.com.cn:9001/";
        }else {
            BaseUrl = "https://data-cqjg.telsafe.com.cn:9001/";
        }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_Kqgl.this, "加载中...");
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {

                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(
                            R.layout.act_kqgl_item_layout, null);
                }
                TextView name = MViewHolder.get(arg1, R.id.tv_projectname);
                TextView rate = MViewHolder.get(arg1, R.id.tv_rate);
                TextView tv_sort = MViewHolder.get(arg1, R.id.tv_sort_one);
                if(kqBeanList.size()>0){
                    name.setText(kqBeanList.get(position).getGroupDisplay());
                    DecimalFormat df2 = new DecimalFormat("######0.00");
                    Double b = kqBeanList.get(position).getAttendanceRate();
                    rate.setText(df2.format(b*100.f)+"%");
                    int sortnum = position + 1;
                    tv_sort.setText(sortnum+"");
                }
                arg1.setTag(R.id.tag_first, kqBeanList);
                return arg1;
            }
            @Override
            public int getCount() {
                return kqBeanList.size();
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              startActivity(new Intent(Act_Kqgl.this,Act_KqglDetail.class)
                      .putExtra("GroupId",kqBeanList.get(position).getGroupId())
              .putExtra("BaseUrl",BaseUrl));
            }
        });
    }

    @Override
    public void initListener() {

    }

    public void loadData(){
        loadingDialog.show();
        ProjectListUrl = BaseUrl+"v1/bigscreen/etp/Attendance?etpId=QY2020112370b2f&&dashBoadType=TOB";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                ProjectListUrl,null,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray array) {
                try{
                    loadingDialog.dismiss();
                    if(kqBeanList.size()>0){
                        kqBeanList.clear();
                    }
                    for(int i=0;i<array.length();i++){
                        JSONObject object = (JSONObject)array.opt(i);
                        KqBean kqBean = new KqBean();
                        kqBean.setProjectName(object.optString("ProjectName"));
                        kqBean.setGroupId(object.optString("GroupId"));
                        kqBean.setGroupName(object.optString("GroupName"));
                        kqBean.setCompanyTypeId(object.optString("CompanyTypeId"));
                        kqBean.setGroupDisplay(object.optString("GroupDisplay"));
                        kqBean.setEnterCount(object.optInt("EnterCount"));
                        kqBean.setAttendanceCount(object.optInt("AttendanceCount"));
                        kqBean.setUnAttendanceCount(object.optInt("UnAttendanceCount"));
                        kqBean.setAttendanceRate(object.optDouble("AttendanceRate"));
                        kqBeanList.add(kqBean);

                    }
                    int TotalCount = 0; //应考勤人数
                    int TotalAttendanceCount = 0; //今日考勤人数
                    for(int j=0;j<kqBeanList.size();j++){
                        /**
                         * 应考勤人数=出勤人数+缺勤人数
                         */
                        TotalCount += kqBeanList.get(j).getAttendanceCount()+kqBeanList.get(j).getUnAttendanceCount();
                        TotalAttendanceCount += kqBeanList.get(j).getAttendanceCount();
                        attendance_number.setText("应考勤人数："+TotalCount+"");
                        attendance_today.setText("今日考勤人数："+TotalAttendanceCount+"");
                    }
                    if (kqBeanList.size() == 0) {
                        placeHolder.setVisibility(View.VISIBLE);
                    } else {
                        placeHolder.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
//                loadAttendanceData();
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("请求失败");
            }
        });
        MyAppCon.getHttpQueue().add(request);
    }
//    public void loadAttendanceData(){
//        StringRequest request = new StringRequest(Request.Method.GET,
//                AttendanceUrl,null,new Response.Listener<String>(){
//            @Override
//            public void onResponse(final String str) {
//                str = "";
////                try{
////                    Object tempobject = object.get("data");
////                    JSONObject jsonObject = new JSONObject(tempobject.toString());
////                    attendance_number.setText("应考勤人数："+jsonObject.optInt("attendanceCount")+"");
////                    attendance_today.setText("今日考勤人数："+jsonObject.optInt("onDutyCount")+"");
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
//            }
//        },new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                showToast("请求失败");
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap <String,String> map = new HashMap<>();
//                map.put("Cookie","org='499443220161024'; __gm_ici=a9b96286b50b4b95985e7343f57a9026; .CLOUD_ACCESS_TOKEN=cn-fa7a32ca-5cc6-4cbf-bad7-6106fd344cb5; .ENDPOINT=WEB; serverRootUrl='https://xmgl.glodon.com'; .CLOUDT_TENANT_ID=441489374102016; tenantManager=true; companyProduct=new; orgId=441489374863360; projectId=null; acw_tc=2760822216196619549287473ec952761ce35420134fb3b8383c504eda8a77; .JAVA.LABOR.CLOUD.AUTH=dc351801-b754-420a-83e7-8c8dfa2e5543; __gm.t=eyJhbGciOiJIUzUxMiJ9.WlhsS2FHSkhZMmxQYVVwcllWaEphVXhEU214aWJVMXBUMmxLUWsxVVNUUlJNRXBFVEZWb1ZFMXFWVEpKYmpBdUxreDRUV2hIY2xGSVpuZGxaRVp5ZGpKeWNqUmFkbmN1VW1wdFlqSjNaVzFqYVdnNFNHeFNUMmhqZVZFdE5tdHdOM2c0UVRkNmVrcFhSbmQyVDFGa1kxZEVRWEpmVEVaMFUydFpNRXBIVUhKT2NUbFhVMGRWYzBwMFNuQXlhRk01YlZwQkxUSjZiM0pTYlVWS1FVdHBkVko1UkhSTU1HdDNUeTFDTlZaVFJtb3hhalZvV0ZCdFJqRlRUbDl4Um10dVFYWnVXRzVIVDNsNFZXaDNTMlJMVVZaNFJGSnlhVEJHUmxCWWJYaE9lRTFMUzJsSU1tUlJhbU5LY1ZwbmRHZFdlVTlYWkhKeVZFRTJVV05HVG5GdlNGbDVNSEZQUjA0MVgya3RRamROVW1vemJrWjRiRzB6VEZSNU0ydFJNR1ZtTFdkMlExSmFjbmhvZUVSSVFXOVZhV1EwTUd0M2RuWnZkemxzV0c1cWFYSjFOa2QxU0cxU01uTjZha0pVV0VKRmNGbGpjV1ZaVTJoR1IxaGhVMmRuWldrek9VSm1RbGRqTW05WmN6TlJVemRwVVhscVRqRmFTa2xXTWxoUVkxVnZOVEJ2T0VkNlEyWXVVbUpoTTFkQ1dqWkVkMDQxTFRkSFJHbHpMV2RJZHc9PQ.42-r-z76VJcb60fL1kYGlw4ZPCVqef47incK7TBCh2kZvwC_5my6lrJYdXEgjascz00SXuzTaS9mIpX9jEMs2A; .JAVA.CLOUD.AUTH=447a2e86-f897-4590-ba13-3a30c679a9bf");
//                return map;
//            }
//        };
//        MyAppCon.getHttpQueue().add(request);
//    }
    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }

    @Override
    public void processClick(View v) {

    }
}
