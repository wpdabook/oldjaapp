package com.a21zhewang.constructionapp.ui.inspection;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.EListAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.InspectionOneLevel;
import com.a21zhewang.constructionapp.bean.InspectionThreeLevel;
import com.a21zhewang.constructionapp.bean.InspectionTwoLevel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import butterknife.BindView;

/**
 * Created by Administrator on 2021/1/3.
 */

public class Act_CheckGroupItem extends BaseActivity {
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.rightBtn)
    TextView rightBtn;
    @BindView(R.id.check_elistView)
    ExpandableListView elistview;
    @BindView(R.id.checkAll)
    TextView checkAllBtn;
    public  final static int RESQUST_CODE_SELECT_CONTENT = 7086;
    private String projectID;
    private String dicId = "";
    private ArrayList<InspectionOneLevel> onegroup;
    private ArrayList<InspectionTwoLevel> twogroup;
    private ArrayList<InspectionThreeLevel> threegroup;
    private EListAdapter adapter;
    private Dialog loadingDialog;
    private boolean selectTag = false;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_checkgroup_item;
    }

    @Override
    public void initViews() {
        projectID = getIntent().getStringExtra("projectID");
        dicId = getIntent().getStringExtra("dicId");
        onegroup = new ArrayList<InspectionOneLevel>();
        twogroup = new ArrayList<InspectionTwoLevel>();
        threegroup = new ArrayList<InspectionThreeLevel>();
        adapter = new EListAdapter(Act_CheckGroupItem.this, twogroup);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_CheckGroupItem.this, "加载中...");
        elistview.setOnChildClickListener(adapter);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setTextSize(15);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<twogroup.size();i++){
                    threegroup = twogroup.get(i).getChild();
                    if(threegroup.size()>0){
                        for(int j=0;j<threegroup.size();j++){
                            if(threegroup.get(j).getChecked()){
                                selectTag = true;
                                break;
                            }
                        }
                    }
                }
                if(selectTag){
                    Intent data = new Intent();
                    data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) twogroup);
                    setResult(RESULT_OK, data);
                    Act_CheckGroupItem.this.finish();
                }else {
                    showToast("请选择检查项");
                }
            }
        });
        checkAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllBtn.getText().toString().trim().equals("全选")) {
                    // 所有项目全部选中
                    adapter.initDate(true);
                    adapter.notifyDataSetChanged();
                    checkAllBtn.setText("全不选");
                } else {
                    // 所有项目全部不选中
                    adapter.initDate(false);
                    adapter.notifyDataSetChanged();
                    checkAllBtn.setText("全选");
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_CheckGroupItem.this.finish();
            }
        });
    }
    @Override
    public void initData() {
        loadingDialog.show();
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"checkItem\",\"projectID\":\"" + projectID + "\",\"dicId\":\"" + dicId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try{
                    loadingDialog.dismiss();
                    JSONArray array = new JSONArray(res);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject groupObj = (JSONObject) array.get(i);
                        InspectionTwoLevel group = new InspectionTwoLevel(groupObj.getString("dicId"), groupObj.getString("dicName"));
                        JSONArray childrenList = groupObj.getJSONArray("child");

                        for (int j = 0; j < childrenList.length(); j++) {
                            JSONObject childObj = (JSONObject) childrenList.get(j);
                            InspectionThreeLevel child = new InspectionThreeLevel(childObj.getString("dicId"), childObj.getString("dicName"));
                            group.addChildItem(child);
                        }
                        twogroup.add(group);
                    }
                    elistview.setAdapter(adapter);
//                    int groupCount = elistview.getCount();
//                    for (int i = 0; i < groupCount; i++) {
//                        elistview.expandGroup(i);
//                    }
                    if(twogroup.size()>0){
                        adapter.notifyDataSetChanged();
                    }
                }catch (JSONException e){

                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
        }

    }
    @Override
    public void initListener() {

    }
    @Override
    public void processClick(View v) {

    }
}
