package com.a21zhewang.constructionapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonArrayRequest;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.NetCheckUtil;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity2 extends AppCompatActivity {
    private static final String TAG = "LoginActivity2";
   /* @BindView(R.id.index_icon)
    ImageView index_icon;*/
    @BindView(R.id.activity_login_username)
    EditText userName;
    @BindView(R.id.activity_login_pw)
    EditText passWord;
    @BindView(R.id.login_btn)
    Button loginButton;
    @BindView(R.id.is_forget_password)
    TextView isForgetPassword;
    @BindView(R.id.index_text)
    TextView index_text;
    private Callback.Cancelable cancelable;
    private Bundle obj;
    public final static String TEST_APP_URL="http://wpda.mobi/sendapp/data/select.php";
    private List<Map<String,String>> testapplist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
//        changeIcon("com.a21zhewang.constructionapp.LoginAliasActivity");
        Intent intent = getIntent();
        if (intent!=null){
            obj = intent.getBundleExtra("obj");
        }
        if(PublicUtils.IS_DEBUG_MODE == false){
            //JPushInterface.stopPush(MyAppCon.appcontext);//停止推送
            if (JPushInterface.isPushStopped(this)){
                 JPushInterface.resumePush(this);
            }
           JPushInterface.setAlias(MyAppCon.appcontext,"", new TagAliasCallback() {
            @Override
            public void gotResult(int responseCode, String s, Set<String> set) {
                if (responseCode == 0){

                }else{
                    //  PublicUtils.toastFalse("别名设置失败！错误吗："+responseCode);
                }
            }
        });
        }
        initViews();
    }
    public void initViews(){
        if(PublicUtils.IS_DEBUG_MODE){
            getTestAppUrl(); //测试环境开启,SettingsActivity服务器地址别忘记注释。
        }else {
             isautologin();
        }
        String fonts = "fonts/fangzhengdx.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(),fonts);
        index_text.setTypeface(typeface);
       /* if (PublicUtils.URL.equals("http://106.14.167.156:9001/API/SGGL.aspx")){
            index_icon.setImageResource(R.mipmap.ditelogo);
        }*/
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!NetCheckUtil.checkNet(LoginActivity2.this)){//当前手机无网络
//                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
                login(userName.getText().toString(),passWord.getText().toString());
            }
        });
        isForgetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //忘记密码
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "02783560918");
                intent.setData(data);
                startActivity(intent);
            }
        });
    }
    /**
     * 获取测试版APP_URL
     */
    public void getTestAppUrl(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,
                TEST_APP_URL,null,new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray array) {
                try{
                    if(testapplist.size()>0){
                        testapplist.clear();
                    }
                    for(int i=0;i<array.length();i++){
                        JSONObject object = (JSONObject)array.opt(i);
                        String tversion = object.optString("tversion");
                        if(tversion.equals(AppUtils.getAppName(getApplicationContext()))){
                              PublicUtils.URLload = object.optString("url");
                              PublicUtils.URL = PublicUtils.URLload + "/API/SGGL.aspx";
                              if("true".equals(object.optString("remark"))){
                                  PublicUtils.IS_DEBUG_MODE = false;
                              }else {
                                  PublicUtils.IS_DEBUG_MODE = true;
                              }
                              isautologin();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
        MyAppCon.getHttpQueue().add(request);
    }
    /**
     *
     */
    public  void isautologin(){
        String username = PublicUtils.getspstring("user");
        String pw = PublicUtils.getspstring("password");
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pw)){
            userName.setText(username);passWord.setText(pw);
            login(username,pw);
        }else {
//            MyAppCon.getversion(this);
        }
    }

    public void login(final String username, final String pw) {
//        if(TextUtils.isEmpty(username)){
//            userName.setError("请输入用户名");
//            return;
//        }
//        if(TextUtils.isEmpty(pw)){
//            passWord.setError("请输入密码");
//            return;
//        }
        if(validateAccount(username)&&validatePassword(pw)){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("userID", username);
                jsonObject.put("password", pw);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (cancelable != null)
                cancelable.cancel();
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity2.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("登录中...");
            progressDialog.show();

            loginButton.setClickable(false);
            cancelable = XUtil.postjsondata(jsonObject, "CheckLogin", new MyCallBack() {

                @Override
                public void onResule(String res) {
                    progressDialog.dismiss();
                    PublicUtils.putspstring("user", username);
                    PublicUtils.putspstring("password", pw);
                    PublicUtils.userBean = (UserBean) JsonUtils.getbean(res, UserBean.class);
                    PublicUtils.UserID = PublicUtils.userBean.getUserID();
                    PublicUtils.sign = PublicUtils.userBean.getSign();
                    PublicUtils.etpID = PublicUtils.userBean.getEtpID();
                    PublicUtils.etpShortName = PublicUtils.userBean.getEtpShortName();
                    PublicUtils.userTypeID = PublicUtils.userBean.getUserTypeID();
                    PublicUtils.userType = PublicUtils.userBean.getUserType();
                    PublicUtils.userName = PublicUtils.userBean.getUserName();
                    Intent intent = new Intent(LoginActivity2.this, IndexActivity.class);
                    intent.putExtra("obj",obj);
                    startActivity(intent);
                    overridePendingTransition(com.a21zhewang.photolibrary.R.anim.ap2, com.a21zhewang.photolibrary.R.anim.ap1);
                    finish();
                }

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
                    loginButton.setClickable(true);
                    progressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //禁用返回主activity
        moveTaskToBack(true);
    }
    private boolean validateAccount(String account){
        if(TextUtils.isEmpty(account)){
            userName.setError("请输入用户名");
            return  false;
        }
        if (!Patterns.PHONE.matcher(userName.getText().toString().trim()).matches()) {
            userName.setError("用户名格式错误");
            return false;
        }
        return true;
    }
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            passWord.setError("请输入密码");
            return false;
        }
        return true;
    }

    /**
     * 动态更改APP名称
     * @param activityPath
     */
    public void changeIcon(String activityPath) {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this, activityPath),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}
