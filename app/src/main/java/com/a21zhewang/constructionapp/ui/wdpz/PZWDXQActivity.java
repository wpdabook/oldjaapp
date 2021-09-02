package com.a21zhewang.constructionapp.ui.wdpz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.CheckTypeThree;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PZWDXQActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    MyTitleBar myTitleBar;
    private WDPZXQAdapter<CheckTypeOne> wdpzxqAdapter;
    private String id;
    List<CheckTypeOne> checkItemList;
    private String projectName;
    private String checkRegionId,checkRegionName;
    private String dicID,dicName;

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_pzwdxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        wdpzxqAdapter = new WDPZXQAdapter<CheckTypeOne>(null);
        HorizontalDividerItemDecoration decoration = new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.parseColor("#EDEDED"))
                .size(2)
                .marginResId(R.dimen.x20, R.dimen.x20).build();
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(wdpzxqAdapter);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        id = getIntent().getStringExtra("id");
        projectName = getIntent().getStringExtra("projectName");
        checkRegionId = getIntent().getStringExtra("checkRegionId");
        checkRegionName = getIntent().getStringExtra("checkRegionName");
        dicID = getIntent().getStringExtra("dicID");
        dicName = getIntent().getStringExtra("dicName");
        final String status = getIntent().getStringExtra("status");
        if (!TextUtils.isEmpty(id)&&!TextUtils.isEmpty(status)){
            getDate(id,status);
        }
        //暂存再编辑
        if (!TextUtils.isEmpty(id)&&"1".equals(status)){
            myTitleBar.setrigthvisiable(View.VISIBLE);
            myTitleBar.setRighttextviewonclick(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(PZWDXQActivity.this, SetwdCheckListActivity.class);
                    mIntent.putExtra("id", id);
                    mIntent.putExtra("status",status);
                    mIntent.putExtra("projectName",projectName);
                    mIntent.putExtra("checkRegionId",checkRegionId);
                    mIntent.putExtra("checkRegionName",checkRegionName);
                    mIntent.putExtra("dicID",dicID);
                    mIntent.putExtra("dicName",dicName);
                    mIntent.putExtra("fromWhere","2");
                    mIntent.putExtra("checkItemList",(Serializable)checkItemList);
                    startActivity(mIntent);
                    finish();
                }
            });
        }

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }

    private void getDate(String projectid,String status){
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"status\":\""+status+"\",\"recordType\":\"weida\",\"projectID\":\""+projectid+"\"}");
        XUtil.postjsondata(getjsonobj, "GetProjectRiskDetailsList", new MyCallBack() {
            @Override
            public void onResule(String res) {
                JSONArray array = null;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                     array = jsonObject.getJSONArray("checkItemList");
                 //   JSONObject jsobobj = jsonArray.getJSONObject(0);
                 //   array = jsobobj.getJSONArray("checkItemList");
                } catch (JSONException e) {
                    PublicUtils.toastFalse("程序发生了预期之外的错误！请重启程序试一试");
                }
                checkItemList = JsonUtils.jsonToArrayList(array.toString(), CheckTypeOne.class);
                wdpzxqAdapter.replaceData(checkItemList);

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
  }
}
