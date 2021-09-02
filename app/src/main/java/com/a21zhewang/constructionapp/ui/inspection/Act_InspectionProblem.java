package com.a21zhewang.constructionapp.ui.inspection;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.FreeAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.InspectionBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralInspectionlItem;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by WP-PC on 2019/11/12.
 */

public class Act_InspectionProblem extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.procedural_history_list)
    ListView listView;
    private String recordID;
    private String sectionID;
    private String itemID;
    private ProceduralInspectionlItem item;
    private List<InspectionBean> recordList;
    private List<InspectionBean> recordProblemList;
    private ProceHistoryAdapter adapter;
    private InspectionBean bean;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_inspectionproblem;
    }

    @Override
    public void initViews() {
        title.setText("检查记录");
        recordList = new ArrayList<>();
        recordProblemList = new ArrayList<>();
        recordID = getIntent().getStringExtra("recordId");
        sectionID = getIntent().getStringExtra("sectionId");
        itemID = getIntent().getStringExtra("itemID");
        adapter = new ProceHistoryAdapter(Act_InspectionProblem.this);
        listView.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(Act_InspectionProblem.this,Act_InspectionHistoryDetail.class)
                        .putExtra("checkRecordID",recordProblemList.get(position).getCheckRecordID())
                        .putExtra("type",recordProblemList.get(position).getType()));
            }
        });

    }
    @Override
    public void initData() {
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordID + "\"," +
                "\"sectionID\":\"" + sectionID + "\",\"itemID\":\"" + itemID + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    item = JsonUtils.getbean(res,ProceduralInspectionlItem.class);
                    recordList = item.getRecordList();
                    for(int i = 0;i<recordList.size();i++){
                        int type = recordList.get(i).getType();
                        bean = new InspectionBean();
                        if(type == 2){
                            bean.setType(2);
                            bean.setCheckRecordID(recordList.get(i).getCheckRecordID());
                            bean.setResponsibleUnit(recordList.get(i).getResponsibleUnit());
                            bean.setResponsible(recordList.get(i).getResponsible());
                            recordProblemList.add(bean);
                        }
                    }
                    adapter.setResouce(recordProblemList);
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
            XUtil.postjsondata(object, "GetBroadCastRecordList", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastRecordList", callback);
        }
    }
    private class ProceHistoryAdapter extends FreeAdapter {

        private Context context;
        public ProceHistoryAdapter(Context context) {
            super(context);
            this.context = context;
        }
        @Override
        public int getCount() {
            return recordProblemList.size();
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            view = LayoutInflater.from(context).inflate(R.layout.act_procedural_history_item, null);
            TextView type_name =  findViewById(view,R.id.check_item_type_name);

            int type = recordProblemList.get(position).getType();
            type_name.setText("不符合记录");
            return view;
        }
    }
    @Override
    public void initListener() {

    }
    @Override
    public void processClick(View v) {

    }
}
