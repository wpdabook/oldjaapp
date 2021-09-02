package com.a21zhewang.constructionapp.ui.check;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.a21zhewang.constructionapp.bean.RecordListBean;
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

public class Act_ProceduralHistory extends BaseActivity {
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
    private ProceHistoryAdapter adapter;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_procedural_history;
    }

    @Override
    public void initViews() {
        title.setText("检查记录");
        recordList = new ArrayList<>();
        recordID = getIntent().getStringExtra("recordId");
        sectionID = getIntent().getStringExtra("sectionId");
        itemID = getIntent().getStringExtra("itemID");
        adapter = new ProceHistoryAdapter(Act_ProceduralHistory.this);
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
                      startActivity(new Intent(Act_ProceduralHistory.this,Act_ProceduralHistoryDetail.class)
                              .putExtra("checkRecordID",recordList.get(position).getCheckRecordID())
                              .putExtra("type",recordList.get(position).getType()));
            }
        });

    }
    @Override
    public void initData() {
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordID + "\"," +
                "\"sectionID\":\"" + sectionID + "\",\"itemID\":\"" + itemID + "\"}");
          XUtil.postjsondata(object, "GetInspectionRecordList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        });
    }
    private class ProceHistoryAdapter extends FreeAdapter {

        private Context context;
        public ProceHistoryAdapter(Context context) {
            super(context);
            this.context = context;
        }
        @Override
        public int getCount() {
            return recordList.size();
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            view = LayoutInflater.from(context).inflate(R.layout.act_procedural_history_item, null);
            TextView type_name =  findViewById(view,R.id.check_item_type_name);

            int type = recordList.get(position).getType();
            if(type == 0){
                type_name.setText("无此项记录");
            }
            if(type == 1){
                type_name.setText("安全记录");
            }
            if(type == 2){
                type_name.setText("有隐患记录");
            }
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
