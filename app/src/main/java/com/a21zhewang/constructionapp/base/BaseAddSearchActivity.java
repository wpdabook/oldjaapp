package com.a21zhewang.constructionapp.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
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
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class BaseAddSearchActivity extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.report_xlistview)
    XListView mListView;
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String str_title;
    private int type;
    private int page = 0;
    private String keyword="";
    private JSONArray reportArray = new JSONArray();
    private MyBaseAdapter adapter;
    private int totalPage = 1;
    private String method = "";
    private String projectID = "";
    public  final static int RESQUST_CODE_SELECT_TYPE = 500;  // 安全，质量，文明
    private final List<String> strs = new ArrayList<>();//文字拼接
    private final List<String> strs_remark = new ArrayList<>();//风险类别提示信息
    private final List<MsgType> pjs=new ArrayList<>();//记录点击的值
    private final List<List<MsgType>> pjlists=new ArrayList<>();//记录每个层级的集合
    private Dialog loadingDialog;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.base_add_search_layout;
    }

    @Override
    public void initViews() {
        title.setText("选择风险类别");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        method = getIntent().getStringExtra("method");
        projectID = getIntent().getStringExtra("projectID");
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 0;
                loadData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(int position, View arg1, ViewGroup parent) {
                final JSONObject object = reportArray.optJSONObject(position);
                if (arg1 == null) {
                    arg1 = getLayoutInflater().inflate(
                            R.layout.base_add_search_listlayout, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView name = MViewHolder.get(arg1, R.id.risktype_name);
                if(object.length()>0){
                    name.setText(object.optString("dicNames"));
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
                final JSONObject object = reportArray.optJSONObject(position-1);
                String remark = object.optString("remark");
                String dicId = object.optString("dicId");
                String ids = object.optString("dicIds");
                String dicNames = object.optString("dicNames");
                strs.add(dicNames);
                List<String> idlist = Arrays.asList(ids.split("/"));
                List<String> dicNameslist = Arrays.asList(dicNames.split("/"));
                /***
                 * 按树形选择菜单格式封装返回
                 */
                if(remark!= null && !"NULL".equals(remark)){
                    strs_remark.add(remark);
                }
                List<MsgType> checks = new ArrayList<MsgType>();
                if(idlist.size() == dicNameslist.size()){
                    int size = idlist.size();
                    for(int i=0;i<size;i++){
                        MsgType type = new MsgType();
                        type.setRemark(remark);
                        type.setTypeID(idlist.get(i));
                        type.setTypeName(dicNameslist.get(i));
                        checks.add(type);
                    }
                }
                Intent data = new Intent();
                data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>)list2Tree(checks));
                data.putExtra("dicId",dicId);
                data.putExtra("strs",getstr(strs));
                data.putExtra("strs_remark",getstr(strs_remark));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
    private List<MsgType> list2Tree(List<MsgType> checks) {
        List<MsgType> pjs = new ArrayList<>();
        for (int i = 0; i < checks.size(); i++) {
            MsgType pj = new MsgType();
            MsgType checkType = checks.get(i);
            pj.setTypeID(checkType.getTypeID());
            pj.setTypeName(checkType.getTypeName());
            //0  pj.setMsgTypes(checkType.getMsgTypes());
            haschild(pjs).add(pj);
        }
        return pjs;
    }
    private List<MsgType> haschild(List<MsgType> msgType) {
        if (msgType.size() > 0) {
            MsgType type = msgType.get(0);
            List<MsgType> msgTypes = type.getMsgTypes();
            if (msgTypes == null) {
                msgTypes = new ArrayList<>();
                type.setMsgTypes(msgTypes);
                return msgTypes;
            }
            return haschild(msgTypes);
        }
        return msgType;
    }
    /**
     * 拼接文字
     *
     * @param strs
     * @return
     */
    private String getstr(List<String> strs) {
        if (strs == null || strs.size() == 0) return "";
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < strs.size(); i++) {
            str.append(strs.get(i));
            if (i != strs.size() - 1) {
                str.append("/");
            }
        }
        return str.toString();
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
        JSONObject object = JsonUtils.getjsonobj("{\"projectID\":\"" + projectID + "\"," + "\"pageIndex\":\"" + String.valueOf(page)  + "\"," +
                "\"pageSize\":\"" + 15 + "\",\"key\":\"" + keyword + "\"}");
        XUtil.postjsondata(object, method, new MyCallBack() {
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
                        totalPage = object.optInt("pageCount");
                        JSONArray jsonArray = object.optJSONArray("msgTypes");
                        if(jsonArray.length() == 0){
                            PublicUtils.toast("暂无结果");
                            return;
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            reportArray.put(jsonArray.optJSONObject(i));
                        }
                        if (totalPage > 0) {
                            mListView.setPullLoadEnable(true);
                        } else {
                            mListView.setPullLoadEnable(false);
                        }
                        if (page > totalPage) {
                            page = totalPage;
                            mListView.stopLoadMore();
                            Toast.makeText(getApplicationContext(), "已无更多数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (reportArray.length() == 0) {
                            placeHolder.setVisibility(View.VISIBLE);
                        } else {
                            placeHolder.setVisibility(View.GONE);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(ObjBean getbean) {

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
            public void onWrong() {

            }
        });
    }

    @Override
    public void processClick(View v) {

    }
}
