package com.a21zhewang.constructionapp.ui.inspection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.FreeAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralDetailItem;
import com.a21zhewang.constructionapp.bean.RecordListBean;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 广电检查详情
 * Created by WP-PC on 2019/10/28.
 */

public class Act_InspectionDetail extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.procedural_list)
    ListView listView;
    @BindView(R.id.project_name)
    TextView progectName;
    @BindView(R.id.people_name)
    TextView name;
    @BindView(R.id.project_surface)
    TextView tablename;
//    n_jygz;@BindView(R.id.btn_submit)
//    Button submit;
//    @BindView(R.id.btn_jygz)
//    Button bt
    @BindView(R.id.proce_detail_seekbar)
    SeekBar seekBar;
    @BindView(R.id.table_type)
    TextView table_type;
    private String projectID;
    private String recordId;
    private ProceduralDetailItem item;
    private List<RecordListBean> recordList;
    private ProceAdapter adapter;
    private String proShortName;
    private String creatorName;
    private String status;
    private String Content;
    private String area;
    private boolean isAddNotice;
    private boolean canMakeEasyNotice;
    private String sectionId = "";


    @Override
    public void beforesetContentView() {

    }
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 0:
//                    submit.setBackgroundResource(R.drawable.dialog_gray_btn_bg);
//                    submit.setEnabled(false);
//                    break;
//            }
//        }
//    };
    @Override
    public int getLayoutId() {
        return R.layout.act_inspection_detail;
    }

    @Override
    public void initViews() {
        title.setText("专项巡查");
        status = getIntent().getStringExtra("status");
        recordId = getIntent().getStringExtra("recordId");
        projectID = getIntent().getStringExtra("projectID");
        proShortName = getIntent().getStringExtra("proShortName");
        creatorName = getIntent().getStringExtra("creatorName");
        area = getIntent().getStringExtra("area");
        isAddNotice = getIntent().getBooleanExtra("isAddNotice",true);
        canMakeEasyNotice = getIntent().getBooleanExtra("canMakeEasyNotice",true);
        progectName.setText(proShortName);
        table_type.setText(getIntent().getStringExtra("createTime"));
        name.setText(getIntent().getStringExtra("checkCategory"));
        sectionId = getIntent().getStringExtra("sectionId");
//        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
//        if("3".equals(status)) {
//            submit.setVisibility(View.GONE);
//            if (buttons != null) {
//                for (ButtonBean btn : buttons) {
//                    if ("EasyNoticeAddBtn".equals(btn.getBtnID())) {
//                        btn_jygz.setVisibility(View.VISIBLE);
//                        break;
//                    }
//                }
//            }
//        }else {
//            submit.setVisibility(View.VISIBLE);
//            btn_jygz.setVisibility(View.GONE);
//        }
        recordList = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                sendDate();
//                showAlertEditText();
//            }
//        });
//        btn_jygz.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Act_InspectionDetail.this, Act_JygzAdd.class)
//                        .putExtra("recordId",recordId).putExtra("projectId",projectID)
//                        .putExtra("projectName",proShortName).putExtra("area",area));
//                Act_InspectionDetail.this.finish();
//            }
//        });
        adapter = new ProceAdapter(Act_InspectionDetail.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Act_InspectionDetail.this,Act_GroupInspectionDetail.class)
                        .putExtra("status",status)
                        .putExtra("projectID",projectID)
                        .putExtra("proShortName",proShortName)
                        .putExtra("recordId",recordId)
                        .putExtra("recordRLID",recordList.get(position).getSectionID())
                        .putExtra("typeName",recordList.get(position).getSectionName())
                        .putExtra("sectionId",recordList.get(position).getSectionID()));
            }
        });
    }
    public void showAlertEditText(){
        LayoutInflater factory = LayoutInflater.from(Act_InspectionDetail.this);//提示框
        final View view = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
        final EditText edit=(EditText)view.findViewById(R.id.editText1);//获得输入框对象
        edit.setHint("请输入存在问题");//输入框默认值
        new AlertDialog.Builder(Act_InspectionDetail.this)
                .setTitle("存在问题描述")
                .setView(view)
                .setPositiveButton("确定",
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Content = edit.getText().toString();
                                sendDate(Content);
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(edit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                dialog.dismiss();
                            }
                        }).create().show();
                      // }).setNegativeButton("取消", null).create().show();
    }
    public void  sendDate(String content){
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"content\":\"" + content + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    Act_InspectionDetail.this.finish();
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
            XUtil.postjsondata(object, "SaveBroadCastDoneResult", callback);
        }else {
            XUtil.postjsondatasj(object, "SaveBroadCastDoneResult", callback);
        }
    }
    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

        @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (recordList!=null)
            recordList.clear();
        if (adapter!=null)
            adapter.notifyDataSetChanged();
            getTypeList(recordId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void getTypeList(String recordId){
        JSONObject twoobject = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\"}");
        JSONObject threeobject = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"dicId\":\"" + sectionId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)){
                    try {
                        JSONObject object = new JSONObject(res);
                        item = JsonUtils.getbean(res,ProceduralDetailItem.class);
                        recordList = item.getRecordList();
                        int NotFitCountTotal = 0;
                        if(recordList.size() != 0){
                            for(int i=0;i<recordList.size();i++){
//                                if(recordList.get(i).getUncheckCount() == 0){
//                                    submit.setBackgroundResource(R.drawable.custom_btn_blue_back);
//                                    submit.setEnabled(true);
//                                }else {
//                                    Message message = Message.obtain(handler,0);
//                                    message.sendToTarget();
//                                }
                                NotFitCountTotal = recordList.get(i).getNotFitCount()+NotFitCountTotal;
                            }
                        }
                        /**
                         * 1.isAddNotice 是否已经添加过简易告知
                         * 2.canMakeEasyNotice 仅根据检查单是否为当前用户所在专班来判断
                         * 3.不符合项不能为0
                         */
//                        if(isAddNotice == false &&  canMakeEasyNotice == true && NotFitCountTotal != 0){
//                            btn_jygz.setBackgroundResource(R.drawable.custom_btn_blue_back);
//                            btn_jygz.setEnabled(true);
//                        }else {
//                            btn_jygz.setBackgroundResource(R.drawable.dialog_gray_btn_bg);
//                            btn_jygz.setEnabled(false);
//                        }
//                    tablename.setText("已完成 "+ b.intValue() +"%");
                        Double b = object.optDouble("checkRate");
                        tablename.setText(creatorName);
                        seekBar.setProgress(b.intValue());
                        adapter.setResouce(recordList);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            if("0".equals(sectionId)){
                XUtil.postjsondata(twoobject, "GetBroadCastGroupList", callback);
            }else {
                XUtil.postjsondata(threeobject, "GetBroadCastGroupList", callback);
            }
        }else {
            if("0".equals(sectionId)){
                XUtil.postjsondatasj(twoobject, "GetBroadCastGroupList", callback);
            }else {
                XUtil.postjsondatasj(threeobject, "GetBroadCastGroupList", callback);
            }
        }
    }

    private class ProceAdapter extends FreeAdapter {

        private Context context;

        public ProceAdapter(Context context) {
            super(context);
            this.context = context;
        }
        @Override
        public int getCount() {
            return recordList.size();
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = LayoutInflater.from(context).inflate(R.layout.procedural_detail_item, null);
            TextView type_name =  findViewById(view,R.id.type_name);
            TextView type_number = findViewById(view,R.id.type_number);
            type_name.setText(recordList.get(position).getSectionName());
            int uncheckCount = recordList.get(position).getUncheckCount();
            int notfitcount = recordList.get(position).getNotFitCount();
            if("3".equals(status)) {
                type_number.setText("不符合"+"（"+ notfitcount +"）");
                type_number.setTextColor(getResources().getColor(R.color.color_theme));
            }else {
                type_number.setText("未检查"+"（"+ uncheckCount +"）");
            }
            return view;
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

}
