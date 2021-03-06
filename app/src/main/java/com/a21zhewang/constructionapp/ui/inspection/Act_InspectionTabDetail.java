package com.a21zhewang.constructionapp.ui.inspection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
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
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.InspectionBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralDetailItem;
import com.a21zhewang.constructionapp.bean.ProceduralInspectionlItem;
import com.a21zhewang.constructionapp.bean.RecordListBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.jygz.Act_JygzAdd;
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
 * Created by WP-PC on 2019/10/28.
 */

public class Act_InspectionTabDetail extends BaseActivity {
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
    @BindView(R.id.btn_submit)
    Button submit;
    @BindView(R.id.btn_jygz)
    Button btn_jygz;
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


    @Override
    public void beforesetContentView() {

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(submit != null) {
                        submit.setBackgroundResource(R.drawable.dialog_gray_btn_bg);
                        submit.setEnabled(false);
                    }
                    break;
            }
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.act_inspectiontab_detail_layout;
    }

    @Override
    public void initViews() {
        title.setText("????????????");
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
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if("3".equals(status)) {
            submit.setVisibility(View.GONE);
            if (buttons != null) {
                for (ButtonBean btn : buttons) {
                    if ("EasyNoticeAddBtn".equals(btn.getBtnID())) {
                        btn_jygz.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }else {
            submit.setVisibility(View.VISIBLE);
            btn_jygz.setVisibility(View.GONE);
        }
        recordList = new ArrayList<>();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendDate();
                showAlertEditText();
            }
        });
        btn_jygz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Act_InspectionTabDetail.this, Act_JygzAdd.class)
                        .putExtra("recordId",recordId).putExtra("projectId",projectID)
                        .putExtra("projectName",proShortName).putExtra("area",area));
                Act_InspectionTabDetail.this.finish();
            }
        });
        adapter = new ProceAdapter(Act_InspectionTabDetail.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Act_InspectionTabDetail.this,Act_InspectionDetail.class)
                        .putExtra("status",status)
                        .putExtra("projectID",projectID)
                        .putExtra("recordId",recordId)
                        .putExtra("proShortName",proShortName)
                        .putExtra("createTime",getIntent().getStringExtra("createTime"))
                        .putExtra("checkCategory",getIntent().getStringExtra("checkCategory"))
                        .putExtra("creatorName",creatorName)
                        .putExtra("area",area)
                        .putExtra("isAddNotice",isAddNotice)
                        .putExtra("canMakeEasyNotice",canMakeEasyNotice)
                        .putExtra("typeName",recordList.get(position).getSectionName())
                        .putExtra("sectionId",recordList.get(position).getSectionID()));

//                Intent i = new Intent();
//                i.putExtra("status",status);
//                i.putExtra("projectID",object.optString("projectID"));
//                i.putExtra("recordId",object.optString("recordID"));
//                i.putExtra("proShortName",object.optString("proShortName"));
//                i.putExtra("createTime",object.optString("createTime"));
//                i.putExtra("checkCategory",object.optString("checkCategory"));
//                i.putExtra("creatorName",object.optString("creatorName"));
//                i.putExtra("area",object.optString("area"));
//                i.putExtra("isAddNotice",object.optBoolean("isAddNotice"));
//                i.putExtra("canMakeEasyNotice",object.optBoolean("canMakeEasyNotice"));
//                i.setClass(getActivity(), Act_InspectionTabDetail.class);
//                startActivity(i);
            }
        });
    }
    public void showAlertEditText(){
        LayoutInflater factory = LayoutInflater.from(Act_InspectionTabDetail.this);//?????????
        final View view = factory.inflate(R.layout.editbox_layout, null);//???????????????final???
        final EditText edit=(EditText)view.findViewById(R.id.editText1);//?????????????????????
        edit.setHint("?????????????????????");//??????????????????
        new AlertDialog.Builder(Act_InspectionTabDetail.this)
                .setTitle("??????????????????")
                .setView(view)
                .setPositiveButton("??????",
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
        // }).setNegativeButton("??????", null).create().show();
    }
    public void  sendDate(String content){
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"content\":\"" + content + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    Act_InspectionTabDetail.this.finish();
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
     * ????????????
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
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\"}");
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
                                if(recordList.get(i).getUncheckCount() == 0){
                                    submit.setBackgroundResource(R.drawable.custom_btn_blue_back);
                                    submit.setEnabled(true);
                                }else {
                                    Message message = Message.obtain(handler,0);
                                    message.sendToTarget();
                                }
                                NotFitCountTotal = recordList.get(i).getNotFitCount()+NotFitCountTotal;
                            }
                        }
                        /**
                         * 1.isAddNotice ?????????????????????????????????
                         * 2.canMakeEasyNotice ????????????????????????????????????????????????????????????
                         * 3.?????????????????????0
                         */
                        if(btn_jygz != null){
                            if(isAddNotice == false &&  canMakeEasyNotice == true && NotFitCountTotal != 0){
                                btn_jygz.setBackgroundResource(R.drawable.custom_btn_blue_back);
                                btn_jygz.setEnabled(true);
                            }else {
                                btn_jygz.setBackgroundResource(R.drawable.dialog_gray_btn_bg);
                                btn_jygz.setEnabled(false);
                            }
                        }
//                    tablename.setText("????????? "+ b.intValue() +"%");
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
            XUtil.postjsondata(object, "GetBroadCastParentGroupList", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastParentGroupList", callback);
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
                type_number.setText("?????????"+"???"+ notfitcount +"???");
                type_number.setTextColor(getResources().getColor(R.color.color_theme));
            }else {
                type_number.setText("?????????"+"???"+ uncheckCount +"???");
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
