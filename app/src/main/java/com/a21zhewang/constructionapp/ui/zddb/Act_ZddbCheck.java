package com.a21zhewang.constructionapp.ui.zddb;

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
import com.a21zhewang.constructionapp.ui.xmxx.fragment.zongbaoFragment;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

import static com.a21zhewang.constructionapp.R.id.status;

/**
 * Created by Administrator on 2021/3/26.
 */

public class Act_ZddbCheck extends BaseActivity {
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
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_zddb_check_layout;
    }

    @Override
    public void initViews() {
        righttext.setTextSize(16);
        recordId = getIntent().getStringExtra("recordId");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        btn_passed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(3);
            }
        });
        btn_no_passed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData(4);
            }
        });
    }
    public  void postData(int status) {
//        if (TextUtils.isEmpty(edit_des.getText().toString())) {
//            PublicUtils.toast("请输入您要描述的文字信息");
//            return;
//        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("recordId",recordId);
            jsonObject.put("content",edit_des.getText().toString());
            jsonObject.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                showToast("提交成功");
                righttext.setVisibility(View.VISIBLE);
                Act_ZddbCheck.this.finish();
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
        XUtil.postjsondata(jsonObject,"AddKeySuperviseDetail", callback);
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
