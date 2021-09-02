package com.a21zhewang.constructionapp.ui.usercenter;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.LoginActivity;
import com.a21zhewang.constructionapp.ui.LoginActivity2;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class UpPasswordActivity extends BaseActivity {


    @BindView(R.id.activity_up_password_EditText_old)
    EditText activityUpPasswordEditTextOld;
    @BindView(R.id.activity_up_password_EditText_new1)
    EditText activityUpPasswordEditTextNew1;
    @BindView(R.id.activity_up_password_EditText_new2)
    EditText activityUpPasswordEditTextNew2;

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
        return R.layout.activity_up_password;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {

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

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }



    @OnClick(R.id.activity_up_password_Button_yes)
    public void onViewClicked() {
        String trim1 = activityUpPasswordEditTextOld.getText().toString().trim();
        if (trim1.length()==0){
            PublicUtils.toast("请输入当前密码！");
            return;
        }
        String trim2 = activityUpPasswordEditTextNew1.getText().toString().trim();
        if (trim2.length()==0){
            PublicUtils.toast("请输入新密码！");
            return;
        }
        String trim3 = activityUpPasswordEditTextNew2.getText().toString().trim();
        if (trim3.length()==0){
            PublicUtils.toast("请再次输入新密码！");
            return;
        }
        if (!trim2.equals(trim3)){
            PublicUtils.toast("两次输入的新密码不相同，请确认！");
            return;
        }
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"userID\":\"" + PublicUtils.userBean.getUserID() + "\",\"newPassword\":\"" + trim3 + "\",\"oldPassword\":\"" + trim1 + "\"}");
        XUtil.postjsondata(getjsonobj, "ModifyPwd", new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                PublicUtils.toast("密码修改成功！");
//                PublicUtils.putspboolean("autologin", false);
                startActivity(new Intent(UpPasswordActivity.this, LoginActivity2.class));
                CloseActivityClass.exitClient();
            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }
        });
    }
}
