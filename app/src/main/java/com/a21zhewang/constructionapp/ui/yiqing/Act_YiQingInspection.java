package com.a21zhewang.constructionapp.ui.yiqing;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.FreeAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.YQInspectionBean;
import com.a21zhewang.constructionapp.bean.YiQingInspectionBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static cn.forward.androids.base.BaseApplication.showToast;

/**
 * 程序化检查项-页面
 * Created by WP-PC on 2019/10/29.
 * 0：无此项  1安全  2有隐患
 */

public class Act_YiQingInspection extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.procedural_inspection_list)
    ListView listView;
    private String recordId;
    private String projectID;
    private YiQingInspectionBean item;
    private List<YQInspectionBean> recordList;
    private ProceInspectAdapter adapter;
    @BindView(R.id.btn_yiqing_save_checkitem)
    Button BtnSave;
    private String proShortName;
    private JSONObject comitObject = new JSONObject();
    private JSONArray itemListArray = new JSONArray();
    private static final int PROCEDURAL_REQUESTCODE = 1462;
    private String typeName;
    private String sectionId;
    private String status;
    private String checkContent = "";

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
        return R.layout.act_yiqing_inspection;
    }

    @Override
    public void initViews() {
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
        adapter = new ProceInspectAdapter(Act_YiQingInspection.this);
        listView.setAdapter(adapter);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("{}".equals(comitObject.toString())){
                    showToast("请选择检查项");
                    return;
                }
                postDate();
            }
        });
    }
    public void postDate(){
        XUtil.postjsondata(comitObject, "SaveResumWorkInspectionItemsStatus", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    showToast("保存成功");
                    Act_YiQingInspection.this.finish();
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
    /**
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getjsonArray(String itemId, String  checkResult) {
        Message message = Message.obtain(handler,0);
        JSONObject tempObject = new JSONObject();
        try{
            comitObject.put("recordID", recordId);
            comitObject.put("sectionID",sectionId);
            tempObject.put("itemId", itemId);
            tempObject.put("checkResult",checkResult);
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
            message.sendToTarget();
        }catch (JSONException e){
            return "";
        }
        return comitObject.toString();
    }
    public void getData(String recordId,String sectionId){
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"sectionID\":\"" + sectionId + "\"}");
        XUtil.postjsondata(object, "GetResumWorkCheckingGroupItemList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    item = JsonUtils.getbean(res,YiQingInspectionBean.class);
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
        });
    }
    private class ProceInspectAdapter extends FreeAdapter {
        //定义成员变量mTouchItemPosition,用来记录手指触摸的EditText的位置
        private int mTouchItemPosition = -1;
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
            view = LayoutInflater.from(context).inflate(R.layout.act_yiqing_inspection_item, null);
            LinearLayout layout1 = findViewById(view,R.id.linear1); //文本
            final EditText ed_input_text = findViewById(view,R.id.ed_input_text);
            LinearLayout layout2 = findViewById(view,R.id.linear2); //是-否
            RadioGroup rg_type = findViewById(view,R.id.rg_type);
            final RadioButton rb_yes = findViewById(view,R.id.rb_yes);
            final RadioButton rb_no = findViewById(view,R.id.rb_no);
            TextView name1 = findViewById(view,R.id.check_item_name1);
            TextView name2 = findViewById(view,R.id.check_item_name2);
            final String RiskValueType = recordList.get(position).getRiskValueType();
            /**
             * RiskValueType 0:文本  1：数字  2.合格型（单选框） 3：是否型（单选框）
             */

            if("0".equals(RiskValueType)){
                Invisible_EditText(layout1,layout2,name1,position,ed_input_text,InputType.TYPE_CLASS_TEXT);
            }
            if("1".equals(RiskValueType)){ //只能输入数字和小数
                Invisible_EditText(layout1,layout2,name1,position,ed_input_text,InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
            if("2".equals(RiskValueType)){
                Invisible_RadioButton_Text(layout1,layout2,rg_type,name2,rb_yes,rb_no,"合格","不合格",position);
            }
            if("3".equals(RiskValueType)){
                Invisible_RadioButton_Text(layout1,layout2,rg_type,name2,rb_yes,rb_no,"是","否",position);
            }
            if("3".equals(status)){
                disableRadioGroup(rg_type,ed_input_text);
            }else {
                enableRadioGroup(rg_type,ed_input_text);
            }
            ed_input_text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        //注意，此处必须使用getTag的方式，不能将position定义为final，写成mTouchItemPosition = position
                        mTouchItemPosition = (Integer) v.getTag();
                    }
                    return false;
                }
            });
            rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if("2".equals(RiskValueType)){
                        if(rb_yes.isChecked()){
                            checkContent = "合格";
                            rb_yes.requestFocus();
                            rb_no.clearFocus();
                            recordList.get(position).setCheckContent("合格");
                        }else {
                            checkContent = "不合格";
                            rb_yes.clearFocus();
                            rb_no.requestFocus();
                            recordList.get(position).setCheckContent("不合格");
                        }
                    }
                    if("3".equals(RiskValueType))    {
                        if(rb_yes.isChecked()){
                            checkContent = "是";
                            rb_yes.requestFocus();
                            rb_no.clearFocus();
                            recordList.get(position).setCheckContent("是");
                        }else {
                            checkContent = "否";
                            rb_yes.clearFocus();
                            rb_no.requestFocus();
                            recordList.get(position).setCheckContent("否");
                        }
                    }
                    getjsonArray(recordList.get(position).getItemID(),checkContent);
                }
            });
            ed_input_text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    getjsonArray(recordList.get(mTouchItemPosition).getItemID(),s.toString());
                    recordList.get(mTouchItemPosition).setCheckContent(s.toString());
                }

            });
            ed_input_text.setTag(position);
            if (mTouchItemPosition == position) {
                ed_input_text.requestFocus();
                ed_input_text.setSelection(ed_input_text.getText().length());
            } else {
                ed_input_text.clearFocus();
            }
            return view;
        }
    }
    private void Invisible_EditText(LinearLayout layout1,LinearLayout layout2, TextView name,int position,EditText editText,int type){
        layout1.setVisibility(View.VISIBLE);
        name.setText(recordList.get(position).getItemName());
        editText.setInputType(type);
        editText.setText(recordList.get(position).getCheckContent());
        layout2.setVisibility(View.GONE);
    }
    private void Invisible_RadioButton_Text(LinearLayout layout1,LinearLayout layout2,RadioGroup rg,
                                            TextView textView,RadioButton rb1,RadioButton rb2,String str1,String str2,int position){

        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
        rg.setVisibility(View.VISIBLE);
        textView.setText(recordList.get(position).getItemName());
        rb1.setText(str1);
        rb2.setText(str2);
        if(str1.equals(recordList.get(position).getCheckContent())){
            rb1.setChecked(true);
            rb2.setChecked(false);
        }
        if(str2.equals(recordList.get(position).getCheckContent())){
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
    }
    public void disableRadioGroup(RadioGroup testRadioGroup,EditText editText) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(false);
            editText.setEnabled(false);
        }
    }
    public void enableRadioGroup(RadioGroup testRadioGroup,EditText editText) {
        for (int i = 0; i < testRadioGroup.getChildCount(); i++) {
            testRadioGroup.getChildAt(i).setEnabled(true);
            editText.setEnabled(true);
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
