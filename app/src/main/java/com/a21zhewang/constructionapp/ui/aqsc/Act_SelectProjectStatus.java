package com.a21zhewang.constructionapp.ui.aqsc;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.ui.task.Act_TaskCheck;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;

/**
 * 选择问题类别
 * Created by WP-PC on 2020/3/10.
 */

public class Act_SelectProjectStatus extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    public  final static int RESQUST_CODE_SELECT_STATUS = 9896;
    public  final static int RESQUST_CODE_SELECT_NUMBER = 9296;
    @BindView(R.id.seListView)
    ListView listview;
    private MyBaseAdapter adapter;
    private int tag = 0;
    private JSONArray dataArray = new JSONArray();
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_aqsc_projectstatus_layout;
    }

    @Override
    public void initViews() {
        tag = getIntent().getIntExtra("tag",0);
        adapter = new MyBaseAdapter() {
            @Override
            public int getCount() {
                return dataArray.length();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final JSONObject object = dataArray.optJSONObject(position);
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.problem_item, null);
                }
                TextView text = MViewHolder.get(view, R.id.tvTitle);

                text.setText(object.optString("value"));
                view.setTag(R.id.tag_first, object);
                return view;
            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final JSONObject object = dataArray.optJSONObject(position);
                Intent data = new Intent();
                data.putExtra("value",object.optString("value"));
                data.putExtra("key",object.optString("key"));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        JSONObject object = new JSONObject();
        if(tag == 1){
            object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "hiddenType" + "\"}");
        }else {
            object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "hiddenRange" + "\"}");
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try{
                    dataArray = new JSONArray(res);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                }
            }
            @Override
            public void onFail(ObjBean getbean) {
            }
            @Override
            public void onWrong() {
            }
        };
        XUtil.postjsondata(object,"GetSafetyMsgInitialize", callback);
    }

    @Override
    public void processClick(View v) {

    }
}
