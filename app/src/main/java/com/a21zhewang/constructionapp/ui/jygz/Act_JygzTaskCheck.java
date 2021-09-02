package com.a21zhewang.constructionapp.ui.jygz;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * Created by Administrator on 2021/3/26.
 */

public class Act_JygzTaskCheck extends BaseActivity {
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.edit_des)
    EditText edit_des;
    @BindView(R.id.btn_passed)
    Button btn_passed;
    @BindView(R.id.btn_no_passed)
    Button btn_no_passed;
    private String recordId;
    private Dialog loadingDialog;
    private int status = 0;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_jygz_taskcheck_layout;
    }

    @Override
    public void initViews() {
        righttext.setTextSize(16);
        status = getIntent().getIntExtra("status",0);
        recordId = getIntent().getStringExtra("recordId");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        btn_passed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(1);
            }
        });
        btn_no_passed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(2);
            }
        });
    }
    public  void postData(int audit) {
        if (TextUtils.isEmpty(edit_des.getText().toString())) {
            PublicUtils.toast("请输入您的回复内容");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",recordId);
            jsonObject.put("content",edit_des.getText().toString());
            jsonObject.put("type",status);
            jsonObject.put("auditResult",audit); //1.通过   2.不通过
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                showToast("提交成功");
                righttext.setVisibility(View.VISIBLE);
                Act_JygzTaskCheck.this.finish();
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
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(jsonObject, "DownSendBroadCastNotice", callback);
        }else {
            XUtil.postjsondatasj(jsonObject,"DownSendBroadCastNotice", callback);
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
}
