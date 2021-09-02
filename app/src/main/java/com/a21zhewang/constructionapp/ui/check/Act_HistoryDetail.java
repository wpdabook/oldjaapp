package com.a21zhewang.constructionapp.ui.check;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.ProceduralHostoryAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;

/**
 * Created by WP-PC on 2020/3/11.
 */

public class Act_HistoryDetail extends BaseActivity{
    @BindView(R.id.rv_pic_list)
    ListView mListView;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    private int page = 0;
    private int totalPage = 1;
    private JSONArray dataArray = new JSONArray();
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String id;
    private ProceduralHostoryAdapter mAdapter;
    private List<NineGridModel> mList;
    private String recordID;
    private String recordRLID;
    private String sectionID;
    private String itemID;
    private boolean isBack;
    private String Content;
    private String Url;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_history_detail;
    }

    @Override
    public void initViews() {
        righttext.setTextSize(16);
        Url = getIntent().getStringExtra("url");
        recordID = getIntent().getStringExtra("recordId");
        recordRLID = getIntent().getStringExtra("recordRLID");
        sectionID = getIntent().getStringExtra("sectionId");
        itemID = getIntent().getStringExtra("itemID");
        isBack = getIntent().getBooleanExtra("isBack",true);
        mAdapter = new ProceduralHostoryAdapter(this);
        if(isBack == true){
            righttext.setVisibility(View.VISIBLE);
        }else {
            righttext.setVisibility(View.GONE);
        }
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertEditText();
            }
        });
    }

    @Override
    public void initListener() {

    }
    public void showAlertEditText(){
        LayoutInflater factory = LayoutInflater.from(Act_HistoryDetail.this);//提示框
        final View view = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
        final EditText edit=(EditText)view.findViewById(R.id.editText1);//获得输入框对象
        edit.setHint("请输入存在问题");//输入框默认值
        new AlertDialog.Builder(Act_HistoryDetail.this)
                .setTitle("存在问题描述")
                .setView(view)
                .setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Content = edit.getText().toString();
                                sendDate(Content,Url);
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                dialog.dismiss();
                            }
                        }).create().show();
        // }).setNegativeButton("取消", null).create().show();
    }
    @Override
    public void initData() {
        loadData(true);
    }
    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog) {
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordID + "\"," +
                "\"sectionID\":\"" + sectionID + "\",\"itemID\":\"" + itemID + "\"}");
        XUtil.postjsondata(object, "GetCheckRecordDetailCollection", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    mList = new ArrayList<NineGridModel>();
                    if(object.length()>0){
                        dataArray = object.optJSONArray("recordList");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject tempobject = dataArray.optJSONObject(i);
                            NineGridModel model = new NineGridModel();
                            model.setContent(tempobject.optString("content"));
                            model.setCreateTime(tempobject.optString("createTime"));
                            model.setStatus(tempobject.optInt("type"));
                            model.setBack(tempobject.optBoolean("isBack"));
//                            if(tempobject.optInt("type") == 1){
//                                model.setUser("安全记录");
//                            }else{
//                                model.setUser("有隐患记录");
//                            }
                            JSONArray array = tempobject.optJSONArray("files");
                            List<String> imglist = new ArrayList<String>();
                            if(array != null){
                                for(int j=0;j<array.length();j++){
                                    JSONObject tempobject2 = array.optJSONObject(j);
                                    imglist.add(tempobject2.optString("url"));
                                    model.setUrlList(imglist);
                                }
                            }
                            mList.add(model);
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
                mAdapter.setList(mList);
                mListView.setAdapter(mAdapter);
                if(mAdapter != null){
                    mAdapter.notifyDataSetChanged();
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
    public void  sendDate(String content,String url){
        JSONObject object = new JSONObject();
        if(TextUtils.isEmpty(url)){
            object = JsonUtils.getjsonobj("{\"recordRLID\":\"" + recordRLID + "\",\"reason\":\"" + content + "\"}");
        }else {
            object = JsonUtils.getjsonobj("{\"appSource\":\"" + getAppSource() + "\",\"recordRLID\":\"" + recordRLID + "\",\"reason\":\"" + content + "\"}");
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    try {
                        Intent data = new Intent();
                        setResult(RESULT_OK, data);
                        Act_HistoryDetail.this.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        };
        if(TextUtils.isEmpty(url)){
            XUtil.postjsondata(object, "EditInspectionRecordBack", callback);
        }else {
            url = url+"/API/SGGL.aspx";
            XUtil.postjsondataother(object,url,"EditInspectionRecordBack", callback);
        }

    }
    @Override
    public void processClick(View v) {

    }
}
