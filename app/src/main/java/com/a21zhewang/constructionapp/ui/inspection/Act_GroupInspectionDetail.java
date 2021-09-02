package com.a21zhewang.constructionapp.ui.inspection;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.FreeAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.InspectionBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralInspectionlItem;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 广电检查项-页面
 * Created by WP-PC on 2019/10/29.
 * 0：无此项  1安全  2有隐患
 */

public class Act_GroupInspectionDetail extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.procedural_inspection_list)
    ListView listView;
    private String recordId;
    private String projectID;
    private ProceduralInspectionlItem item;
    private List<InspectionBean> recordList;
    private ProceInspectAdapter adapter;
    @BindView(R.id.btn_save_checkitem)
    Button BtnSave;
    private String proShortName;
    private JSONObject comitObject = new JSONObject();
    private JSONArray itemListArray = new JSONArray();
    private static final int PROCEDURAL_REQUESTCODE = 1462;
    private String typeName;
    private String sectionId;
    private String status;
    private String xPoint,yPoint;
    /**
     * 用于控制按钮可点击与不可点击状态
     *  1.检查项有变化可点击
     *  2.新增返回不可点击
     */
    private int SAVE_TAG = 0;
    @Override
    public void beforesetContentView() {

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    BtnSave.setBackgroundResource(R.drawable.custom_btn_blue_back);
                    BtnSave.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.act_group_inspection;
    }

    @Override
    public void initViews() {
        getLocation();
        title.setText("检查项列表");
        recordList = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        status = getIntent().getStringExtra("status");
        projectID = getIntent().getStringExtra("projectID");
        recordId = getIntent().getStringExtra("recordId");
        proShortName = getIntent().getStringExtra("proShortName");
        typeName = getIntent().getStringExtra("typeName");
        sectionId = getIntent().getStringExtra("sectionId");
        if(!TextUtils.isEmpty(recordId)&& !TextUtils.isEmpty(sectionId)){
            getData(recordId,sectionId);
        }
        if("3".equals(status)){
            BtnSave.setVisibility(View.GONE);
        }else {
            BtnSave.setVisibility(View.VISIBLE);
        }
        adapter = new ProceInspectAdapter(Act_GroupInspectionDetail.this);
        listView.setAdapter(adapter);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("{}".equals(comitObject.toString())){
                    showToast("请选择检查项");
                    return;
                }
                if(SAVE_TAG == 0){
                    postDate();
                }
            }
        });
    }
    public void postDate(){
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
//                   showToast("保存成功");
//                   Act_GroupInspectionDetail.this.finish();
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
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(comitObject, "SaveBroadCastItemsStatus", callback);
        }else {
            XUtil.postjsondatasj(comitObject, "SaveBroadCastItemsStatus", callback);
        }
    }
    /**
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getjsonArray(int tag,String itemId, int status) {
        JSONObject tempObject = new JSONObject();
        try{
            comitObject.put("recordID", recordId);
            comitObject.put("sectionID",sectionId);
            tempObject.put("itemId", itemId);
            tempObject.put("status",status);
            if(xPoint != null && yPoint != null){
                tempObject.put("xPoint",xPoint);
                tempObject.put("yPoint",yPoint);
            }
            //相同的检查项不重复提交，以最后一次修改为准。
            if(itemListArray.length()>0){
                for(int i=0;i<itemListArray.length();i++){
                    JSONObject object = itemListArray.optJSONObject(i);
                    for(int j=0;j<object.length();j++){
                        if(itemId.equals(object.optString("itemId"))){
                            itemListArray.remove(i);
                        }
                        break;
                    }
                }
            }
            itemListArray.put(tempObject);
            comitObject.put("itemList", itemListArray);
            if(tag == 0){
                Message message = Message.obtain(handler,0);
                message.sendToTarget();
            }
        }catch (JSONException e){
            return "";
        }
        return comitObject.toString();
    }
    public void getData(String recordId,String sectionId){
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"sectionID\":\"" + sectionId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    item = JsonUtils.getbean(res,ProceduralInspectionlItem.class);
                    recordList = item.getRecordList();
                    adapter.setResouce(recordList);
                    adapter.notifyDataSetChanged();
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
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "GetBroadCastGroupItemList", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastGroupItemList", callback);
        }
    }
    private class ProceInspectAdapter extends FreeAdapter {

        private Context context;
        public ProceInspectAdapter(Context context) {
            super(context);
            this.context = context;
        }
        @Override
        public int getCount() {
            return recordList.size();
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            view = LayoutInflater.from(context).inflate(R.layout.act_groupinspecationdetail_item, null);
            TextView name =  findViewById(view,R.id.check_item_name);
            TextView problem = findViewById(view,R.id.check_item_problem);
            RadioGroup radioGroup = findViewById(view,R.id.radiogroup);
            ImageView im_add = findViewById(view,R.id.check_item_ick);
            LinearLayout itemLayout = findViewById(view,R.id.auto_linear);
            final RadioButton rb1 = findViewById(view,R.id.risk_rb1);
            final RadioButton rb2= findViewById(view,R.id.risk_rb2);
            final RadioButton rb3 = findViewById(view,R.id.risk_rb3);
            name.setText(recordList.get(position).getItemName());
            if(recordList.get(position).getTroubleCount() == 0){
                problem.setVisibility(View.INVISIBLE);
            }else {
                problem.setVisibility(View.VISIBLE);
//                problem.setText("检查记录"+"（"+recordList.get(position).getTroubleCount()+"）");
                problem.setText("检查记录");
                //历史记录
                itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Act_GroupInspectionDetail.this,Act_NewInspectionHistory.class)
                                .putExtra("recordId",recordId)
                                .putExtra("sectionId",sectionId)
                                .putExtra("recordRLID",recordList.get(position).getRecordRLID())
                                .putExtra("itemID",recordList.get(position).getItemID()));
                    }
                });
            }
            if(recordList.get(position).getTroubleCount() == 0){
                problem.setEnabled(false);
            }
            final int checkStatus = recordList.get(position).getCheckStatus();
            if(checkStatus == 0){
                rb1.setChecked(true);
            }
            if(checkStatus == 1 || checkStatus == -1){
                rb2.setChecked(true);
                /**默认符合时开启  默认不选中隐藏该代码*/
                Message message = Message.obtain(handler,0);
                message.sendToTarget();
                recordList.get(position).setCheckStatus(1);
                getjsonArray(SAVE_TAG,recordList.get(position).getItemID(),recordList.get(position).getCheckStatus());
            }
            if(checkStatus == 2){
                rb3.setChecked(true);
            }
            if("3".equals(status)){
                disableRadioGroup(radioGroup);
                im_add.setVisibility(View.GONE);
            }else {
                enableRadioGroup(radioGroup);
                im_add.setVisibility(View.VISIBLE);
            }
            im_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Act_GroupInspectionDetail.this, Act_AddTypeInspection.class);
                    i.putExtra("position", position)
                            .putExtra("projectID", projectID)
                            .putExtra("recordId", recordId)
                            .putExtra("typeName", typeName)
                            .putExtra("sectionId", sectionId)
                            .putExtra("checkStatus", recordList.get(position).getCheckStatus())
                            .putExtra("itemName", recordList.get(position).getItemName())
                            .putExtra("itemID", recordList.get(position).getItemID())
                            .putExtra("recordRLID",recordList.get(position).getRecordRLID())
                            .putExtra("proShortName", proShortName);
                    startActivityForResult(i, PROCEDURAL_REQUESTCODE);
//                  startActivity(new Intent(Act_ProceduralInspection.this,Act_AddTypeProcedural.class)
//                            .putExtra("projectID",projectID)
//                            .putExtra("recordId",recordId)
//                            .putExtra("typeName",typeName)
//                            .putExtra("sectionId",sectionId)
//                            .putExtra("checkStatus",status)
//                            .putExtra("itemName",recordList.get(position).getItemName())
//                            .putExtra("itemID",recordList.get(position).getItemID())
//                            .putExtra("proShortName",proShortName));
                }
            });
//            //有隐患信息
//            problem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(Act_GroupInspectionDetail.this,Act_InspectionProblem.class)
//                            .putExtra("recordId",recordId)
//                            .putExtra("sectionId",sectionId)
//                            .putExtra("itemID",recordList.get(position).getItemID()));
//                }
//            });
//            //历史记录
//            itemLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(Act_GroupInspectionDetail.this,Act_InspectionHistory.class)
//                            .putExtra("recordId",recordId)
//                            .putExtra("sectionId",sectionId)
//                            .putExtra("itemID",recordList.get(position).getItemID()));
//                }
//            });
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    SAVE_TAG = 0;
                    if(rb1.isChecked()){
                        recordList.get(position).setCheckStatus(0);
                    }
                    if(rb2.isChecked()){
                        recordList.get(position).setCheckStatus(1);
                    }
                    if(rb3.isChecked()){
                        recordList.get(position).setCheckStatus(2);
                        Intent i = new Intent(Act_GroupInspectionDetail.this, Act_AddTypeInspection.class);
                        i.putExtra("position", position)
                                .putExtra("projectID", projectID)
                                .putExtra("recordId", recordId)
                                .putExtra("typeName", typeName)
                                .putExtra("sectionId", sectionId)
                                .putExtra("checkStatus", recordList.get(position).getCheckStatus())
                                .putExtra("itemName", recordList.get(position).getItemName())
                                .putExtra("itemID", recordList.get(position).getItemID())
                                .putExtra("recordRLID",recordList.get(position).getRecordRLID())
                                .putExtra("proShortName", proShortName);
                        startActivityForResult(i, PROCEDURAL_REQUESTCODE);
                    }
                    getjsonArray(SAVE_TAG,recordList.get(position).getItemID(),recordList.get(position).getCheckStatus());
                }
            });
            return view;
        }
    }
    public void disableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
        }
    }
    public void enableRadioGroup(RadioGroup testRadioGroup) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
        }
    }
    @Override
    public void initListener() {

    }
    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PROCEDURAL_REQUESTCODE == requestCode && resultCode == RESULT_OK && data != null) {
            //SAVE_TAG = 1;//默认未选中任何按钮时时不改变暂存状态时使用
            SAVE_TAG = 0;
            int status = data.getIntExtra("status",0);
            int position = data.getIntExtra("position",0);
            recordList.get(position).setCheckStatus(status);
            getData(recordId,sectionId);
            getjsonArray(SAVE_TAG,recordList.get(position).getItemID(),recordList.get(position).getCheckStatus());
            adapter.notifyDataSetChanged();
        }
    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_GroupInspectionDetail.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
