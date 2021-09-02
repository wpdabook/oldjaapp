package com.a21zhewang.constructionapp.ui.wdjc;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.BindView;

/**
 * 重点检查-无此项提交信息
 * Created by Administrator on 2020/8/4.
 */

public class DeleteItemActivity extends BaseActivity {
    @BindView(R.id.tv_submit)
    TextView submit;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.edit_content)
    EditText edit_content;
    private String TextContent;
    private String recordRLID;
    private JSONObject comitObject = new JSONObject();

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_deleteitem_layout;
    }

    @Override
    public void initViews() {
        recordRLID = getIntent().getStringExtra("recordRLID");
        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextContent = s.toString();
            }
        });
    }

    @Override
    public void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(TextContent)){
                    showToast("请输入原因");
                    return;
                }
                try{
                    JSONArray array = new JSONArray();
                    JSONObject object = new JSONObject();
                    object.put("recordRLID", recordRLID);
                    object.put("checkResult","无此项");
                    object.put("checkContent",TextContent);
                    array.put(object);
                    comitObject.put("item", array);
                }catch (Exception e){
                    e.printStackTrace();
                }
                XUtil.postjsondata(JsonUtils.getjsonobj(comitObject.toString()),"EditKeyExamination", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        showToast("提交成功");
                        Intent data = new Intent();
                        setResult(RESULT_OK, data);
                        finish();
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
                });
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
