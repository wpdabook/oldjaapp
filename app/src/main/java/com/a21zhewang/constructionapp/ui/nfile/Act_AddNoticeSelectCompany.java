package com.a21zhewang.constructionapp.ui.nfile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.SelectCompanyAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NoticeTaskBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TaskBean;
import com.a21zhewang.constructionapp.bean.TaskCompany;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Administrator on 2021/8/2.
 */

public class Act_AddNoticeSelectCompany extends BaseActivity{
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.rwcheck_elistView)
    ExpandableListView elistview;
    @BindView(R.id.notice_rightBtn)
    TextView rightBtn;
    @BindView(R.id.checkAll)
    TextView checkAllBtn;
    public  final static int RESQUST_CODE_SELECT_CONTENT = 2796;
    private SelectCompanyAdapter adapter;
    private Dialog loadingDialog;
    private boolean selectTag = false;
    private List<TaskBean> taskBeanList = new ArrayList<>();
    private List<TaskCompany> taskCompanylist = new ArrayList<>();
    private NoticeTaskBean noticeTaskBean;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_addnotice_selectcompany_layout;
    }

    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_AddNoticeSelectCompany.this, "加载中...");
        elistview.setOnChildClickListener(adapter);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setTextSize(15);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<taskBeanList.size();i++){
                    taskCompanylist = taskBeanList.get(i).getCompanyList();
                    if(taskCompanylist.size()>0){
                        for(int j=0;j<taskCompanylist.size();j++){
                            if(taskCompanylist.get(j).isChecked()){
                                selectTag = true;
                                break;
                            }
                        }
                    }
                }
                if(selectTag){
                    Intent data = new Intent();
                    data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) taskBeanList);
                    setResult(RESULT_OK, data);
                    Act_AddNoticeSelectCompany.this.finish();
                }else {
                    showToast("请选择检查项");
                }
            }
        });
        checkAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllBtn.getText().toString().trim().equals("全选")) {
                    // 所有项目全部选中
                    adapter.initDate(true);
                    adapter.notifyDataSetChanged();
                    checkAllBtn.setText("全不选");
                } else {
                    // 所有项目全部不选中
                    adapter.initDate(false);
                    adapter.notifyDataSetChanged();
                    checkAllBtn.setText("全选");
                }

            }
        });
//        elistview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Intent data = new Intent();
//                //data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) taskBeanList);
//                data.putExtra("etpId",taskBeanList.get(groupPosition).getCompanyList().get(childPosition).getEtpId());
//                data.putExtra("etpName",taskBeanList.get(groupPosition).getCompanyList().get(childPosition).getEtpName());
//                setResult(RESULT_OK, data);
//                Act_AddNoticeSelectCompany.this.finish();
//                return false;
//            }
//        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Act_AddNoticeSelectCompany.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        loadingDialog.show();
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)) {
                    try {
                        loadingDialog.dismiss();
                        JSONObject jsonObject = new JSONObject(res);
                        noticeTaskBean = JsonUtils.getbean(res,NoticeTaskBean.class);
                        taskBeanList = noticeTaskBean.getObjList();
                        adapter = new SelectCompanyAdapter(Act_AddNoticeSelectCompany.this,taskBeanList);
                        elistview.setAdapter(adapter);
                        int groupCount = elistview.getCount();
                        for (int i = 0; i < groupCount; i++) {
                            elistview.expandGroup(i);
                        }
                        if(taskBeanList.size()>0){
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
            @Override
            public void onFinished() {
            }
        };
        XUtil.postjsondatasj(JsonUtils.getjsonobj(""), "GetTaskTypeAndUnitList", callback);
    }
    @Override
    public void initListener() {

    }
    @Override
    public void processClick(View v) {

    }
}
