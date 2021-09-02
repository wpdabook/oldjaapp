package com.a21zhewang.constructionapp.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.usercenter.UpPasswordActivity;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
//import com.a21zhewang.epclibrary.UHFActivity;
//import com.handheld.UHF.UhfManager;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

public class PersonCenterActivity extends BaseActivity {


    @BindView(R.id.activity_person_center_tv_versionname)
    TextView activityPersonCenterTvVersionname;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_bumen)
    TextView tvUserBumen;
    @BindView(R.id.tv_user_tel)
    TextView tvUserTel;
    @BindView(R.id.activity_person_center_lin_UHF)
    LinearLayout activityPersonCenterLinUHF;
    @BindView(R.id.activity_person_center_lin)
    TextView activityPersonCenterLin;
  //  private UhfManager instance;


    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_person_center;
    }

    @Override
    public void initViews() {
        tvUserName.setText(PublicUtils.userBean.getUserName());
        tvUserBumen.setText(PublicUtils.userBean.getUserMail());
        tvUserTel.setText(PublicUtils.userBean.getUserPhone());
        activityPersonCenterTvVersionname.setText("版本:" + MyAppCon.getVersionName());
    }

    @Override
    public void initListener() {

    }

    /**
     * 页面显示
     */
    @Override
    public void resume() {
        try {
          //  instance = UhfManager.getInstance();
        } catch (Exception e) {

        }

//        if (instance != null) {
//            activityPersonCenterLinUHF.setVisibility(View.VISIBLE);
//            activityPersonCenterLin.setVisibility(View.VISIBLE);
//        }
    }

    /**
     * 页面销毁
     */
    @Override
    public void destroy() {
//        if (instance != null) {
//            instance.close();
//        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_person_center_btn_exit,
            R.id.activity_person_center_lin_uppw,
            R.id.activity_person_center_lin_version,
            R.id.activity_person_center_lin_UHF
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_person_center_btn_exit:
                JPushInterface.clearAllNotifications(MyAppCon.appcontext);
                //PublicUtils.putspboolean("autologin", false);
                PublicUtils.putspstring("user", "");
                PublicUtils.putspstring("password","");
                startActivity(new Intent(PersonCenterActivity.this, LoginActivity2.class));
                CloseActivityClass.exitClient();
                break;
            case R.id.activity_person_center_lin_uppw:

                startActivity(new Intent(PersonCenterActivity.this, UpPasswordActivity.class));

                break;
            case R.id.activity_person_center_lin_version:
//                MyAppCon.getversion1(this);
                break;
            case R.id.activity_person_center_lin_UHF:
             //   startActivity(new Intent(PersonCenterActivity.this, UHFActivity.class));
                break;
        }
    }



}
