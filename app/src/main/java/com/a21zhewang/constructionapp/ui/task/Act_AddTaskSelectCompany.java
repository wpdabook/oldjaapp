package com.a21zhewang.constructionapp.ui.task;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.TaskAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TaskBean;
import com.a21zhewang.constructionapp.bean.TaskCompany;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Administrator on 2021/8/2.
 */

public class Act_AddTaskSelectCompany extends BaseActivity{
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.rwcheck_elistView)
    ExpandableListView elistview;
    public  final static int RESQUST_CODE_SELECT_CONTENT = 796;
    private TaskAdapter adapter;
    private Dialog loadingDialog;
    private boolean selectTag = false;
    private List<TaskBean> rwdbBeanList = new ArrayList<>();
    private List<TaskCompany> rwdbInfos = new ArrayList<>();
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_addtask_selectcompany_layout;
    }

    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_AddTaskSelectCompany.this, "加载中...");
        elistview.setOnChildClickListener(adapter);
        elistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent data = new Intent();
                //data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) rwdbBeanList);
                data.putExtra("etpId",rwdbBeanList.get(groupPosition).getCompanyList().get(childPosition).getEtpId());
                data.putExtra("etpName",rwdbBeanList.get(groupPosition).getCompanyList().get(childPosition).getEtpName());
                setResult(RESULT_OK, data);
                Act_AddTaskSelectCompany.this.finish();
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_AddTaskSelectCompany.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        loadingDialog.show();
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "etp" + "\"}");
        XUtil.postjsondatasj(object, "GetTaskSuperviseTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    rwdbBeanList = JsonUtils.jsonToList(res.toString(),TaskBean[].class);
                    adapter = new TaskAdapter(Act_AddTaskSelectCompany.this,rwdbBeanList);
                    elistview.setAdapter(adapter);
                    int groupCount = elistview.getCount();
                    for (int i = 0; i < groupCount; i++) {
                        elistview.expandGroup(i);
                    }
                    if(rwdbBeanList.size()>0){
                        adapter.notifyDataSetChanged();
                    }
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
    public void initListener() {

    }
    @Override
    public void processClick(View v) {

    }
}
