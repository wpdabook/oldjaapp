package com.a21zhewang.constructionapp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.constructionapp.download.UpDateAppUtils;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 *
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.activity_login_ed_id)
    EditText activityLoginEdId;//用户名输入框
    @BindView(R.id.activity_login_ed_pw)
    EditText activityLoginEdPw;//密码输入框
    @BindView(R.id.activity_login_CheckBox_rbid)
    CheckBox activityLoginCheckBoxRbid;//记住密码 选择框
    @BindView(R.id.activity_login_CheckBox_autologin)
    CheckBox activityLoginCheckBoxAutologin;//自动登录选择框
    @BindView(R.id.activity_login_TextView_login)
    TextView activityLoginTextViewLogin;//登录按钮
    @BindView(R.id.activity_login_TextView_forgetpw)
    TextView activityLoginTextViewForgetpw;//忘记密码
    @BindView(R.id.activity_login_imageview_icon)
    ImageView activityLoginImageviewIcon;
    private Callback.Cancelable cancelable;
    private long firstTime = 0;
    private Dialog loadingDialog;
    private Bundle obj;

    @Override
    public void beforesetContentView() {
//        //透明状态栏
//       Window window = getWindow();
//       window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {
       // PublicUtils.toast(PublicUtils.URL);
        Intent intent = getIntent();
        if (intent!=null){
            obj = intent.getBundleExtra("obj");
        }
//        //停止推送
//        JPushInterface.stopPush(MyAppCon.appcontext);
if (JPushInterface.isPushStopped(this)){
    JPushInterface.resumePush(this);
}

        //设置别名
        JPushInterface.setAlias(MyAppCon.appcontext,"", new TagAliasCallback() {
            @Override
            public void gotResult(int responseCode, String s, Set<String> set) {
                if (responseCode==0){

                }else{
                  //  PublicUtils.toastFalse("别名设置失败！错误吗："+responseCode);
                }
            }
        });


        if (PublicUtils.URL.equals("http://106.14.167.156:9001/API/SGGL.aspx")){
            activityLoginImageviewIcon.setImageResource(R.mipmap.ditelogo);
        }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "登录中...");
        isrbpw();
        isautologin();
    }

    @Override
    public void initListener() {
        activityLoginTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_str = activityLoginEdId.getText().toString();
                String pw_str = activityLoginEdPw.getText().toString();
                login(id_str, pw_str);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    /**
     * 联网登陆
     *
     * @param id 用户id
     * @param pw 密码
     */
    private void login(String id, String pw) {

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(pw)) {
            PublicUtils.toast("请输入用户名或者密码！");
            return;
        }
        if (activityLoginCheckBoxRbid.isChecked()) {
            PublicUtils.putspboolean("isrbid", true);
            PublicUtils.putspstring("id", id);
            PublicUtils.putspstring("pw", pw);
        } else {
            PublicUtils.putspboolean("isrbid", false);
            PublicUtils.putspstring("id", "");
            PublicUtils.putspstring("pw", "");
        }
        if (activityLoginCheckBoxAutologin.isChecked()) {
            PublicUtils.putspboolean("autologin", true);
        } else {
            PublicUtils.putspboolean("autologin", false);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", id);
            jsonObject.put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // PublicUtils.log(res);
// PublicUtils.log(getjsonobj.toString());
/**
 * code 不等于 0回调
 *
 * @param getbean
 */ /**
         * 联网失败回调
         *
         */
        if (cancelable != null)
            cancelable.cancel();
        loadingDialog.show();
        activityLoginTextViewLogin.setClickable(false);
        cancelable = XUtil.postjsondata(jsonObject, "CheckLogin", new MyCallBack() {

            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                // PublicUtils.log(res);
                PublicUtils.userBean = (UserBean) JsonUtils.getbean(res, UserBean.class);
                PublicUtils.UserID = PublicUtils.userBean.getUserID();
                PublicUtils.sign = PublicUtils.userBean.getSign();
                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                intent.putExtra("obj",obj);
                startActivity(intent);
                overridePendingTransition(com.a21zhewang.photolibrary.R.anim.ap2, com.a21zhewang.photolibrary.R.anim.ap1);
                finish();

                // PublicUtils.log(getjsonobj.toString());

            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {
                PublicUtils.putspboolean("autologin", false);
            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

                PublicUtils.putspboolean("autologin", false);
            }

            @Override
            public void onFinished() {
                activityLoginTextViewLogin.setClickable(true);
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }

    /**
     * 是否记住账号
     */
    public void isrbpw() {
        String id = PublicUtils.getspstring("id");
        if (PublicUtils.getspboolean("isrbid") && !(TextUtils.isEmpty(id))) {
            activityLoginEdId.setText(id);
            activityLoginCheckBoxRbid.setChecked(true);
        }
    }

    /**
     * 是否自动登录
     */
    public void isautologin() {
        String id = PublicUtils.getspstring("id");
        String pw = PublicUtils.getspstring("pw");
        if (PublicUtils.getspboolean("autologin")
                && !(TextUtils.isEmpty(id))
                && !(TextUtils.isEmpty(pw))) {
            activityLoginEdPw.setText(pw);
            activityLoginCheckBoxAutologin.setChecked(true);
            login(id, pw);
        } else {
//            MyAppCon.getversion(this);
        }
    }


    @OnClick({R.id.activity_login_TextView_rbid, R.id.activity_login_TextView_autologin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_login_TextView_rbid:
                if (activityLoginCheckBoxRbid.isChecked()) {
                    activityLoginCheckBoxRbid.setChecked(false);
                } else {
                    activityLoginCheckBoxRbid.setChecked(true);
                }
                break;
            case R.id.activity_login_TextView_autologin:
                if (activityLoginCheckBoxAutologin.isChecked()) {
                    activityLoginCheckBoxAutologin.setChecked(false);
                } else {
                    activityLoginCheckBoxAutologin.setChecked(true);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                CloseActivityClass.exitClient();
                System.exit(0);
            } else {
                PublicUtils.toast("再按一次退出程序");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
