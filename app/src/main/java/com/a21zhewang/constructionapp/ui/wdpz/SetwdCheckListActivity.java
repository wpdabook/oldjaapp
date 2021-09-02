package com.a21zhewang.constructionapp.ui.wdpz;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.CheckTypeThree;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.UPJsonBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.zxjc.SimpleDividerDecoration;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.utils.dbUtils;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetwdCheckListActivity extends BaseActivity {

    @BindView(R.id.activity_set_check_list_RecyclerView)
    RecyclerView activity_set_check_list_RecyclerView;
    @BindView(R.id.activity_set_check_list_save)
    TextView activity_set_check_list_save;
    public static CheckType checkType = null;
    @BindView(R.id.activity_set_check_list_RelativeLayout_sumbit)
    RelativeLayout activitySetCheckListRelativeLayoutSumbit;
    private wdCheckListAdapter adapter;
    private Project pj;
    private Dialog loadingDialog;
    private String recordID="";
    private List<CheckTypeOne> checkTypesones;
    private String status;
    private String projectName;
    private String checkRegionId,dicID;
    private String checkRegionName,dicName;
    private String fromWhere;
    private String projectId;

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_set_wdcheck_list;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
        pj = getIntent().getParcelableExtra("pj");
        fromWhere = getIntent().getStringExtra("fromWhere");
        if("2".equals(fromWhere)){ //暂存传过来的
            projectId = getIntent().getStringExtra("id");
        }else {
            projectId = pj.getProjectID();
        }
//        recordID = getIntent().getStringExtra("id");
        status = getIntent().getStringExtra("status");
        projectName = getIntent().getStringExtra("projectName");
        checkRegionId = getIntent().getStringExtra("checkRegionId");
        dicID = getIntent().getStringExtra("dicID");
        checkRegionName = getIntent().getStringExtra("checkRegionName");
        dicName = getIntent().getStringExtra("dicName");
        if("1".equals(status)){
            activity_set_check_list_save.setVisibility(View.GONE);
        }
        checkTypesones = (List<CheckTypeOne>) getIntent().getSerializableExtra("checkItemList"); //选中的checkItemList
        activity_set_check_list_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activity_set_check_list_RecyclerView.addItemDecoration(new SimpleDividerDecoration("#e3e3e3", 2));

       // if (PZWDActivity.checkList != null) {

          //  adapter = new wdCheckListAdapter(this, PZWDActivity.checkList);

      //      activity_set_check_list_RecyclerView.setAdapter(adapter);
       // }
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        activity_set_check_list_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.collapseAll();
                submitdata("1");
              //  List<CheckType> setchindseletors = setchindseletors(adapter.getData());
              //  String objtojson = JsonUtils.objtojson(setchindseletors);
                //  PublicUtils.log(objtojson);
                //PublicUtils.toast(setchindseletors.size() + "");
//                try {
//                    int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "save"));
//                    //     List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();
//
//                } catch (DbException e) {
//                    PublicUtils.log("清除缓存检查项失败！");
//                }
             //   dbUtils.savajson(objtojson, "save", "", null);
              //  finish();
            }
        });

activitySetCheckListRelativeLayoutSumbit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        adapter.collapseAll();
        submitdata("2");
    }
});
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"projectID\":\"" + projectId + "\"}");
        XUtil.postjsondata(getjsonobj, "RiskParentLibraryInitialize", new MyCallBack() {
            @Override
            public void onResule(String res) {
                JSONArray array = null;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("checkItemList");
                    JSONObject jsobobj = jsonArray.getJSONObject(0);
                    array = jsobobj.getJSONArray("checkItemList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                List<CheckTypeOne> checkItemList = JsonUtils.jsonToArrayList(array.toString(), CheckTypeOne.class);

                adapter = new wdCheckListAdapter(SetwdCheckListActivity.this, checkItemList);

                     activity_set_check_list_RecyclerView.setAdapter(adapter);

               // SetwdCheckListActivity.checkType.setCheckItemList(checkItemList);
               // checkItemAdapter = new CheckwdItemAdapter(SetwdCheckChildActivity.this,
                   //     SetwdCheckListActivity.checkType.getCheckItemList());
            //    activity_set_check_child_RecyclerView.setAdapter(checkItemAdapter);
                adapter.expandAll(); //默认展开
                if(checkItemList.size()>0){
                    adapter.notifyDataSetChanged();
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

    private List<CheckType> setchindseletors(List<? extends CheckType> childList) {
        List<CheckType> checkTypes = null;
        if (childList != null) {
            checkTypes = new ArrayList<>();
            for (int i = 0; i < childList.size(); i++) {
                CheckType checkType = childList.get(i);
                if (checkType.getStatus()>0) {
                    CheckTypeOne typeOne = new CheckTypeOne();
                    typeOne.setDicID(checkType.getDicID());
                    typeOne.setDicName(checkType.getDicName());
                    typeOne.setlevel(checkType.getlevel());
                    typeOne.setStartTime(checkType.getStartTime());
                    typeOne.setEndTime(checkType.getEndTime());
                    checkTypes.add(typeOne);
                    List list = checkType.getCheckItemList();
                    typeOne.setCheckItemList(setchindseletors(list));

                }

            }
        }
        return checkTypes;
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }
    private void submitdata(String status){
        JSONObject jsonObject=new JSONObject();
        if(objToJson(setchindseletors(adapter.getData())).length() == 0){
            showToast("请配置检查表");
            return;
        }else {
            try {
                loadingDialog.show();
                jsonObject.put("recordID",recordID);
                if (pj!=null) {
                    jsonObject.put("projectID",pj.getProjectID());
                    jsonObject.put("projectName",pj.getProjectShortName());
                }else {
                    jsonObject.put("projectID",projectId);
                    jsonObject.put("projectName",projectName);
                }
                jsonObject.put("recordType","weida");
                jsonObject.put("createUserID",PublicUtils.userBean.getUserID());
                jsonObject.put("createUserName",PublicUtils.userBean.getUserName());
                //1-暂存 2-提交
                jsonObject.put("status",status);
//                jsonObject.put("checkRegionId",checkRegionId);
//                jsonObject.put("checkRegionName",checkRegionName);
//                jsonObject.put("dicID",dicID);
    //                jsonObject.put("dicName",dicName);
                if(objToJson(setchindseletors(adapter.getData())).length() == 0){
                    showToast("请配置检查表");
                    return;
                }
                jsonObject.put("checkItemList",objToJson(setchindseletors(adapter.getData())));
            } catch (JSONException e) {
                //e.printStackTrace();
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
            XUtil.postjsondata(jsonObject, "AddProjectRisk", new MyCallBack() {
                @Override
                public void onResule(String res) {
                    finish();
                }

                @Override
                public void onFail(ObjBean getbean) {

                }

                @Override
                public void onWrong() {

                }

                @Override
                public void onFinished() {
                    WeiboDialogUtils.closeDialog(loadingDialog);
                }
            });
        }

    }
    private JSONArray objToJson(List<? extends CheckType> childList){
        JSONArray jsonArray=new JSONArray();
        if (childList!=null&&childList.size()>0){
            for (int i = 0; i < childList.size(); i++) {
                CheckType type = childList.get(i);
                JSONObject jsonObject=new JSONObject();
                try {
                jsonObject.put("dicID",type.getDicID());
                jsonObject.put("dicName",type.getDicName());
                    int status = type.getStatus();
                    if (status==CheckType.Status.NOSELECT){
                        jsonObject.put("status", "0");
                    }else if (status==CheckType.Status.SOMESELECT){
                        jsonObject.put("status", "2");
                    }else{
                        jsonObject.put("status", "1");
                    }

                jsonObject.put("planStartDate",type.getStartTime());
                jsonObject.put("planEndDate",type.getEndTime());
              //  jsonObject.put("isUsed","");
                jsonObject.put("checkItemList",objToJson(type.getCheckItemList()));


                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                   // e.printStackTrace();
                }

            }
        }
   return jsonArray;
    }

    @Override
    public void resume() {
        if (checkType != null) {
            if (adapter != null) adapter.hasChildSelet(checkType);
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();

        super.resume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkType = null;
    }


}
