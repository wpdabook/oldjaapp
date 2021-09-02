package com.a21zhewang.constructionapp.ui.jygz;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2021/8/17.
 */

public class Jygz_SendTask extends BaseActivity {

    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.li_select_unit_type)
    LinearLayout unit_type;
    @BindView(R.id.tv_unit_type)
    TextView tv_unit_type;
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_people)
    TextView tv_people;
    private int etpTypeId = 0;
    private String projectId = "";
    private String recordId = "";
    private String etpID = "";
    private String userId = "";
    private Dialog loadingDialog;
    private int status = 0;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.jygz_sendtask_layout;
    }

    @OnClick({R.id.li_select_unit_type,
            R.id.li_select_company,
            R.id.lin_select_people
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_unit_type:
                startActivityForResult(new Intent(Jygz_SendTask.this, Act_JygzSelectUnitType.class)
                                .putExtra("status",status)
                                .putExtra("recordId",recordId),
                        Act_JygzSelectUnitType.RESQUST_CODE_SELECTTYPE);
                break;
            case R.id.li_select_company:
                startActivityForResult(new Intent(Jygz_SendTask.this, Act_JygzSelectUnit.class)
                        .putExtra("projectId",projectId)
                        .putExtra("status",status)
                        .putExtra("etpTypeId",etpTypeId),
                        Act_JygzSelectUnit.RESQUST_CODE_SELECTTYPE);
                break;
            case R.id.lin_select_people:
                startActivityForResult(new Intent(Jygz_SendTask.this, Act_JygzSelectPeople.class)
                                .putExtra("projectId",projectId)
                                .putExtra("status",status)
                                .putExtra("etpTypeId",etpTypeId),
                        Act_JygzSelectPeople.RESQUST_CODE_SELECTTYPE);
                break;
        }
    }

    @Override
    public void initViews() {
        righttext.setTextSize(15);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        status = getIntent().getIntExtra("status",0);
        projectId = getIntent().getStringExtra("projectId");
        recordId = getIntent().getStringExtra("recordId");
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendDate();
            }
        });
    }

    public void SendDate(){
        if(TextUtils.isEmpty(tv_unit_type.getText().toString())){
            PublicUtils.toast("请选择单位类型");
            return;
        }
        if(TextUtils.isEmpty(tv_unit.getText().toString())){
            PublicUtils.toast("请选择接收单位");
            return;
        }
        if(TextUtils.isEmpty(tv_people.getText().toString())){
            PublicUtils.toast("请选择接收人员");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",recordId);
            jsonObject.put("type","2");
            jsonObject.put("rectEtpType",etpTypeId); //整改单位类型
            jsonObject.put("rectEtpId",etpID); //整改单位
            jsonObject.put("rectUserId",userId); //整改人
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                righttext.setVisibility(View.VISIBLE);
                showToast("提交成功");
                Jygz_SendTask.this.finish();
            }
            @Override
            public void onFail(ObjBean getbean) {
                righttext.setVisibility(View.VISIBLE);
                loadingDialog.dismiss();
            }
            @Override
            public void onWrong() {
                loadingDialog.dismiss();
            }
        };
        loadingDialog.show();
        righttext.setVisibility(View.GONE);
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(jsonObject, "DownSendBroadCastNotice", callback);
        }else {
            XUtil.postjsondatasj(jsonObject, "DownSendBroadCastNotice", callback);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //单位类型
        if (requestCode == Act_JygzSelectUnitType.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String etpTypeName = data.getStringExtra("etpTypeName");
            etpTypeId = data.getIntExtra("etpTypeId",0);
            tv_unit_type.setText(etpTypeName);

        }
        //接收单位
        if (requestCode == Act_JygzSelectUnit.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String etpShortName = data.getStringExtra("etpShortName");
            etpID = data.getStringExtra("etpID");
            tv_unit.setText(etpShortName);
        }
        //接收人员
        if (requestCode == Act_JygzSelectPeople.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String userName = data.getStringExtra("userName");
            userId = data.getStringExtra("userId");
            tv_people.setText(userName);

        }
    }
}
