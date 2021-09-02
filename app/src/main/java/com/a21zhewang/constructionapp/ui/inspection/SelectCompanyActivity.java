package com.a21zhewang.constructionapp.ui.inspection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
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
import java.util.Date;
import butterknife.BindView;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class SelectCompanyActivity extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.report_xlistview)
    XListView mListView;
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private int page = 0;
    private String keyword="";
    private JSONArray reportArray = new JSONArray();
    private MyBaseAdapter adapter;
    private int totalPage = 1;
    private Dialog loadingDialog;
    private String companyType;
    public  final static int RESQUST_CODE_BUILD_SELECT_TYPE = 500;  // 安全，质量，文明
    public  final static int RESQUST_CODE_CONSTRUCTION_SELECT_TYPE = 501;  // 安全，质量，文明
    public  final static int RESQUST_CODE_SUPERVISION_SELECT_TYPE = 502;  // 安全，质量，文明
    private String Content;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_select_company_layout;
    }

    @Override
    public void initViews() {
        title.setText("选择单位");
        righttext.setTextSize(16);
        companyType = getIntent().getStringExtra("companyType");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(SelectCompanyActivity.this, "加载中...");
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
                            R.layout.act_searchproject_item_layout, null);
                }
                arg1.setVisibility(View.VISIBLE);
                TextView name = MViewHolder.get(arg1, R.id.risktype_name);
                if(object.length()>0){
                    name.setText(object.optString("etpFullName"));
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
                Intent data = new Intent();
                data.putExtra("etpID",object.optString("etpID"));
                data.putExtra("etpFullName",object.optString("etpFullName"));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertEditText();
            }
        });
    }
    public void showAlertEditText(){
        LayoutInflater factory = LayoutInflater.from(SelectCompanyActivity.this);//提示框
        final View view = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
        final EditText edit=(EditText)view.findViewById(R.id.editText1);//获得输入框对象
        edit.setHint("请输入");//输入框默认值
        new AlertDialog.Builder(SelectCompanyActivity.this)
                .setTitle("新增单位")
                .setView(view)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Content = edit.getText().toString();
                                if(!TextUtils.isEmpty(Content)){
                                    sendDate(companyType,Content);
                                }
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                dialog.dismiss();
                            }
                        }).create().show();
        // }).setNegativeButton("取消", null).create().show();
    }
    public void  sendDate(String type,String content){
        JSONObject object = JsonUtils.getjsonobj("{\"etpType\":\"" + type + "\",\"etpName\":\"" + content + "\"}");
        XUtil.postjsondatasj(object, "AddBroadCastEtp", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    Intent data = new Intent();
                    data.putExtra("etpID",object.optString("etpID"));
                    data.putExtra("etpFullName",Content);
                    setResult(RESULT_OK, data);
                    SelectCompanyActivity.this.finish();
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
        JSONObject object = JsonUtils.getjsonobj("{\"companyType\":\"" + companyType + "\"," + "\"pageIndex\":\"" + String.valueOf(page)  + "\"," +
                "\"pageSize\":\"" + 15 + "\",\"keyword\":\"" + keyword + "\"}");
        XUtil.postjsondatasj(object, "GetBroadCastUnitCompany", new MyCallBack() {
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
                        JSONArray jsonArray = object.optJSONArray("recordList");
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
