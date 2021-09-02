package com.a21zhewang.constructionapp.ui.map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.MapBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class Act_MapSearch extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.report_mlistview)
    ListView mListView;
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private int page = 0;
    private String keyword="";
    private JSONArray reportArray = new JSONArray();
    private MyBaseAdapter adapter;
    private Dialog loadingDialog;
    private List<MapBean> mapBeenlist = new ArrayList<>();
    public  final static int MAP_RESQUST_CODE_SELECT = 2001;  // 安全，质量，文明
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_map_search_layout;
    }

    @Override
    public void initViews() {
        title.setText("项目搜索");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_MapSearch.this, "加载中...");
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                final JSONObject object = reportArray.optJSONObject(position);
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(
                            R.layout.act_searchproject_item_layout, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView name = MViewHolder.get(arg1, R.id.risktype_name);
                if(object.length()>0){
                    name.setText(object.optString("shortName"));
                }
                arg1.setTag(R.id.tag_first, object);
                return arg1;
            }
            @Override
            public int getCount() {
                return reportArray.length();
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final JSONObject object = reportArray.optJSONObject(position);
                Intent data = new Intent();
                data.putExtra("projectID",object.optString("projectID"));
                data.putExtra("shortName",object.optString("shortName"));
                data.putExtra("XPoint",object.optString("XPoint"));
                data.putExtra("YPoint",object.optString("YPoint"));
                data.putExtra("address",object.optString("address"));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                if (reportArray==null)return;
                reportArray = new JSONArray();
                adapter.notifyDataSetChanged();
                page=0;
                keyword=edittext;
//                getlistdata(0,10);
                loadData();
            }
        });
    }

    @Override
    public void initData() {
    }
    public void loadData(){
        loadingDialog.show();
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"projectName\":\"" + keyword + "\"}");
        XUtil.postjsondata(getjsonobj, "GetMapProjectData", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject object = new JSONObject(res);
                    if(object.length()>0) {
                        if (page == 0) {
                            reportArray = new JSONArray();
                        }
                        JSONArray jsonArray = object.optJSONArray("mapProjectLists");
                        if(jsonArray.length() == 0){
                            PublicUtils.toast("暂无结果");
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject tempobject = jsonArray.optJSONObject(i);
                            String XPoint = tempobject.optString("XPoint");
                            String YPoint = tempobject.optString("YPoint");
                            if(!"0".equals(XPoint) && !"0".equals(YPoint) && !TextUtils.isEmpty(XPoint) && !TextUtils.isEmpty(YPoint)){
                                reportArray.put(jsonArray.optJSONObject(i));
                            }
                        }
                        if (reportArray.length() == 0) {
                            placeHolder.setVisibility(View.VISIBLE);
                        } else {
                            placeHolder.setVisibility(View.GONE);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                } catch (Exception e) {
                    e.printStackTrace();
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

    @Override
    public void processClick(View v) {

    }
}
