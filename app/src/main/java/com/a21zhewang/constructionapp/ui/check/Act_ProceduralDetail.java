package com.a21zhewang.constructionapp.ui.check;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.FreeAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralDetailItem;
import com.a21zhewang.constructionapp.bean.RecordListBean;
import com.a21zhewang.constructionapp.ui.check.Act_ProceduralInspection;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 程序化检查详情
 * Created by WP-PC on 2019/10/28.
 */

public class Act_ProceduralDetail extends BaseActivity {
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
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.btn_submit)
    Button submit;
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
    private String dataType;
    private int riskCount;
    private int inspectionProgress;
    private String creatorUnit;
    private String status;
    private String xPoint,yPoint;


    @Override
    public void beforesetContentView() {

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    submit.setBackgroundResource(R.drawable.dialog_gray_btn_bg);
                    submit.setEnabled(false);
                    break;
            }
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.act_procedural_detail;
    }

    @Override
    public void initViews() {
        title.setText("程序检查");
        getLocation();
        status = getIntent().getStringExtra("status");
        recordId = getIntent().getStringExtra("recordId");
        projectID = getIntent().getStringExtra("projectID");
        proShortName = getIntent().getStringExtra("proShortName");
        progectName.setText(proShortName);
        table_type.setText(getIntent().getStringExtra("dataType"));
        name.setText(getIntent().getStringExtra("creatorName"));
        rate.setText(getIntent().getStringExtra("creatorUnit"));
        if("3".equals(status)|| "4".equals(status)) {
            submit.setVisibility(View.GONE);
        }else {
            submit.setVisibility(View.VISIBLE);
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
                sendDate();
            }
        });
        adapter = new ProceAdapter(Act_ProceduralDetail.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("4".equals(status)){  //打回记录
                    startActivity(new Intent(Act_ProceduralDetail.this,Act_ProceduralInspectionReback.class)
                            .putExtra("status",status)
                            .putExtra("projectID",projectID)
                            .putExtra("proShortName",proShortName)
                            .putExtra("recordId",recordId)
                            .putExtra("typeName",recordList.get(position).getSectionName())
                            .putExtra("sectionId",recordList.get(position).getSectionID()));
                }else {
                    startActivity(new Intent(Act_ProceduralDetail.this,Act_ProceduralInspection.class)
                            .putExtra("status",status)
                            .putExtra("projectID",projectID)
                            .putExtra("proShortName",proShortName)
                            .putExtra("recordId",recordId)
                            .putExtra("typeName",recordList.get(position).getSectionName())
                            .putExtra("sectionId",recordList.get(position).getSectionID()));
                }
            }
        });
    }
    public void  sendDate(){
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"xPoint\":\"" + xPoint + "\",\"yPoint\":\"" + yPoint + "\"}");
        XUtil.postjsondata(object, "SaveInspectionDoneResult", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    submit.setVisibility(View.GONE);
                    Act_ProceduralDetail.this.finish();
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
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordId + "\",\"listType\":\"" + status + "\"}");
        XUtil.postjsondata(object, "GetCheckingGroupList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    item = JsonUtils.getbean(res,ProceduralDetailItem.class);
                    recordList = item.getRecordList();
                    for(int i=0;i<recordList.size();i++){
                        if(recordList.get(i).getUncheckCount() == 0){
                            submit.setBackgroundResource(R.drawable.custom_btn_blue_back);
                            submit.setEnabled(true);
                        }else {
                            Message message = Message.obtain(handler,0);
                            message.sendToTarget();
                        }
                    }
                    Double b = object.optDouble("checkRate");
                    tablename.setText("已完成 "+ b.intValue() +"%");
                    seekBar.setProgress(b.intValue());
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
//            if("3".equals(status)) {
//                type_number.setVisibility(View.GONE);
//            }else if("2".equals(status)){
//                type_number.setVisibility(View.VISIBLE);
//                type_number.setText("未检查"+"（"+ uncheckCount +"）");
//            }else if("4".equals(status)) {
//                type_number.setVisibility(View.VISIBLE);
//                type_number.setText("已打回"+"（"+ uncheckCount +"）");
//            }
            if("2".equals(status)) {
                type_number.setText("未检查"+"（"+ uncheckCount +"）");
            }else{
                type_number.setText("已打回"+"（"+ uncheckCount +"）");
            }
            return view;
        }

    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_ProceduralDetail.this ).showLocation();
        if (location != null) {
//            double x = location.getLongitude();
//            double y = location.getLatitude();
//            xPoint = String .format("%.3f", x);
//            yPoint = String.format("%.3f", y);
            xPoint = String.valueOf(location.getLongitude());;
            yPoint = String.valueOf(location.getLatitude());
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
