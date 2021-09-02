package com.a21zhewang.constructionapp.ui.jygz;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.EtpAdapter;
import com.a21zhewang.constructionapp.adapter.UnitAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.EptBean;
import com.a21zhewang.constructionapp.bean.JygzUnitInfo;
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

public class Act_JygzSelectUnit extends BaseActivity {

    @BindView(R.id.lvListView)
    ListView listview;
    public final static int RESQUST_CODE_SELECTTYPE=5240;
    /*执法类别*/
    private List<JygzUnitInfo> jygzUnitInfos = new ArrayList<>();
    private UnitAdapter adapter = null;
    private String projectId;
    private int etpTypeId = 0;
    private int status = 0;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_select_unit_layout;
    }

    @Override
    public void initViews() {
      status = getIntent().getIntExtra("status",0);
      projectId = getIntent().getStringExtra("projectId");
      etpTypeId = getIntent().getIntExtra("etpTypeId",0);
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                data.putExtra("etpShortName",jygzUnitInfos.get(position).getEtpShortName());
                data.putExtra("etpID",jygzUnitInfos.get(position).getEtpID());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        JSONObject object =new JSONObject();
        if(etpTypeId == 4){
            object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"etpTypeId\":\"" +
                    etpTypeId + "\",\"issueType\":\"" + 2 + "\"}");
        }else {
            object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"etpTypeId\":\"" + etpTypeId + "\"}");
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    jygzUnitInfos = JsonUtils.jsonToList(array.toString(),JygzUnitInfo[].class);
                    adapter = new UnitAdapter(Act_JygzSelectUnit.this, jygzUnitInfos);
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
            XUtil.postjsondata(object, "GetBroadCastNoticeEtpUserList", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeEtpUserList", callback);
        }
    }

    @Override
    public void processClick(View v) {

    }
}
