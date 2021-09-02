package com.a21zhewang.constructionapp.ui.wdjc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckTypeThree;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

public class WdSetCheckChildActivity extends BaseActivity {

    @BindView(R.id.activity_set_check_child_RecyclerView)
    RecyclerView activity_set_check_child_RecyclerView;
    @BindView(R.id.activity_set_check_child_MyTitleBar)
    MyTitleBar activity_set_check_child_MyTitleBar;

    private String dicID = null;
    private String dicName = null;
    private WdCheckItemAdapter checkItemAdapter;
    private Project pj;

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
        return R.layout.activity_set_check_child;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        pj = getIntent().getParcelableExtra("pj");
        activity_set_check_child_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activity_set_check_child_RecyclerView.addItemDecoration(new WdSimpleDividerDecoration("#E3E3E3", 2));

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

        //  if (TextUtils.isEmpty(dicID))return;
        if (WdSetCheckListActivity.checkType.getCheckItemList() != null) {
            checkItemAdapter = new WdCheckItemAdapter(WdSetCheckChildActivity.this, WdSetCheckListActivity.checkType.getCheckItemList());
            activity_set_check_child_RecyclerView.setAdapter(checkItemAdapter);
        }
        else {
            getlistdata(WdSetCheckListActivity.checkType.getDicID());
        }


    }

    private void getlistdata(String dicID) {
        String projectID = pj.getProjectID();
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"checkItemList\":[{\"dicID\":\"" + dicID + "\"}],\"projectID\":\"" + projectID + "\"}");
        XUtil.postjsondata(getjsonobj, "KeyExaminationInitialize", new MyCallBack() {
            @Override
            public void onResule(String res) {
                JSONArray array = null;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("checkItemList");
                    JSONObject jsobobj = jsonArray.getJSONObject(0);
                    array = jsobobj.getJSONArray("checkItemList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                List<CheckTypeThree> checkItemList = JsonUtils.jsonToArrayList(array.toString(), CheckTypeThree.class);

                WdSetCheckListActivity.checkType.setCheckItemList(checkItemList);
                checkItemAdapter = new WdCheckItemAdapter(WdSetCheckChildActivity.this,
                        WdSetCheckListActivity.checkType.getCheckItemList());
                activity_set_check_child_RecyclerView.setAdapter(checkItemAdapter);


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
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @Override
    protected void onStop() {
        if (checkItemAdapter!=null)checkItemAdapter.collapseAll();
        super.onStop();
    }
}
