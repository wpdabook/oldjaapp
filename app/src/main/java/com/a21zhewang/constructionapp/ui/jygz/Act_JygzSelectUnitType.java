package com.a21zhewang.constructionapp.ui.jygz;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyJYUnitAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.JygzUnit;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 执法监督-发起-选择执法类型
 * Created by WP-PC on 2019/5/30.
 */

public class Act_JygzSelectUnitType extends BaseActivity {

    @BindView(R.id.lvListView)
    ListView listview;
    public final static int RESQUST_CODE_SELECTTYPE=520;
    /*执法类别*/
    private JygzUnit jygzUnit;
    private List<JygzUnit> jygzUnits = new ArrayList<>();
    private MyJYUnitAdapter adapter = null;
    private int status = 0;
    private String recordId = "";
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_jygzselectunittype_layout;
    }

    @Override
    public void initViews() {
        status = getIntent().getIntExtra("status",0);
        recordId = getIntent().getStringExtra("recordId");
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                data.putExtra("etpTypeName",jygzUnits.get(position).getEtpTypeName());
                data.putExtra("etpTypeId",jygzUnits.get(position).getEtpTypeId());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        JSONObject object = JsonUtils.getjsonobj("{\"status\":\"" + status + "\",\"recordId\":\"" + recordId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    jygzUnits = JsonUtils.jsonToList(array.toString(),JygzUnit[].class);
                    adapter = new MyJYUnitAdapter(Act_JygzSelectUnitType.this, jygzUnits);
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
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
            XUtil.postjsondata(object, "GetBroadCastNoticeEtpType", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeEtpType", callback);
        }
    }

    @Override
    public void processClick(View v) {

    }
}
