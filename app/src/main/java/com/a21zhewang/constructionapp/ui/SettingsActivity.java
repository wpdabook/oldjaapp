package com.a21zhewang.constructionapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcel;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.download.UpDateAppUtils;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.set.FeedbackActivity;
import com.a21zhewang.constructionapp.ui.set.HelpWordActivity;
import com.a21zhewang.constructionapp.ui.set.PeopleInfoActivity;
import com.a21zhewang.constructionapp.ui.set.PlayInfoActivity;
import com.a21zhewang.constructionapp.ui.set.ProjectRegisterActivity;
import com.a21zhewang.constructionapp.ui.set.SelectProblemActivity;
import com.a21zhewang.constructionapp.ui.usercenter.UpPasswordActivity;
import com.a21zhewang.constructionapp.update.UpDateUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by WP-PC on 2019/3/12.
 */

public class SettingsActivity extends BaseActivity {
    private boolean isVisibleImgLinearlayout = true;
    @BindView(R.id.username)
    TextView username;

    @BindView(R.id.phone)
    TextView phone;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.version)
    TextView version;

    @BindView(R.id.qr_img)
    ImageView qr_img;

    @BindView(R.id.qr_lin)
    LinearLayout qr_lin;

    @BindView(R.id.qr_text)
    TextView qr_text;

    @BindView(R.id.bswitch)
    Switch aSwitch;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.setting_layout;
    }  //1.settings_activity_layout  2.setting_layout

    @Override
    public void initViews() {
        if(!TextUtils.isEmpty(PublicUtils.userBean.getUserType())){
            username.setText(PublicUtils.userBean.getUserType()+"");
        }
        if(!TextUtils.isEmpty(PublicUtils.userBean.getUserID())){
            phone.setText(PublicUtils.userBean.getUserID());
        }
        if(null == PublicUtils.userBean.getEtpShortName()){
            email.setText(PublicUtils.userBean.getUserName());
        }else {
            email.setText(PublicUtils.userBean.getEtpShortName()+" "+PublicUtils.userBean.getUserName());
        }
        getVersionIsUpdate();
    }
    @Override
    public void initListener() {
//        btn_exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    JPushInterface.clearAllNotifications(MyAppCon.appcontext);
                    //PublicUtils.putspboolean("autologin", false);
                    PublicUtils.putspstring("user", "");
                    PublicUtils.putspstring("password","");
                    PublicUtils.putspstring("tab_tag_one","");
                    PublicUtils.putspstring("tab_tag_two","");
                    PublicUtils.putspstring("tab_tag_third","");
                    PublicUtils.putspstring("tab_tag_four","");
                    PublicUtils.putspstring("tab_tag_five","");
                    PublicUtils.putspstring("tab_tag_six","");
                    PublicUtils.putspstring("tab_two_projectId","");
                    startActivity(new Intent(SettingsActivity.this, LoginActivity2.class));
                    aSwitch.setSwitchTextAppearance(SettingsActivity.this,R.style.s_true);
                    CloseActivityClass.exitClient();
//                    System.exit(0);
                }else{
                    aSwitch.setSwitchTextAppearance(SettingsActivity.this,R.style.s_false);
                    CloseActivityClass.exitClient();
//                    System.exit(0);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }

    @OnClick({R.id.ll_password_change,
            R.id.update_version_liner,
            R.id.call_phone,
            R.id.app_qr_lin,
            R.id.ll_problem,
            R.id.ll_helpword,
            R.id.ll_video,
            R.id.ll_play_info,
            R.id.ll_project_register

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_password_change:
                startActivity(new Intent(SettingsActivity.this, UpPasswordActivity.class));
                break;
            case R.id.update_version_liner:
                if(PublicUtils.IS_DEBUG_MODE) {
                    Toast.makeText(SettingsActivity.this, PublicUtils.URL, Toast.LENGTH_SHORT).show();
                }else {
                    if ("请及时更新".equals(version.getText().toString())) {
                        new UpDateUtils().Update(SettingsActivity.this);
                    }else{
                        Toast.makeText(SettingsActivity.this,"当前版本已最新",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.call_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "02783560918");
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.app_qr_lin:
                if(isVisibleImgLinearlayout){
                    qr_lin.setVisibility(View.VISIBLE);
                    isVisibleImgLinearlayout = false;
                    qr_text.setVisibility(View.VISIBLE);
                }else {
                    qr_lin.setVisibility(View.GONE);
                    isVisibleImgLinearlayout = true;
                    qr_text.setVisibility(View.GONE);
                }
                qr_img.setScaleType(ImageView.ScaleType.CENTER);
                if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr1);
                    qr_text.setText("安全生产APP");  //地产控股
                }
                else if("com.a21zhewang.constructionapp.wc".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr5);
                    qr_text.setText("武昌建安APP");
                }
                else if("com.a21zhewang.constructionapp.jh".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr6);
                    qr_text.setText("江汉建安APP");
                }
                else if("com.a21zhewang.constructionapp.ja".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr7);
                    qr_text.setText("江岸建安APP");
                }
                else if("com.a21zhewang.constructionapp.qk".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr8);
                    qr_text.setText("硚口建安APP");
                }
                else if("com.a21zhewang.constructionapp.hyzj".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr2);
                    qr_text.setText("汉阳建安APP");
                }
                else if("com.a21zhewang.constructionapp.hs".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr9);
                    qr_text.setText("洪山建安APP");
                }
                else if("com.a21zhewang.constructionapp.qsja".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr4);
                    qr_text.setText("青山建安APP");
                }
                else if("com.a21zhewang.constructionapp.dxh".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr10);
                    qr_text.setText("东西湖建安APP");
                }
                else if("com.a21zhewang.constructionapp.cdja".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr11);
                    qr_text.setText("蔡甸建安APP");
                }
                else if("com.a21zhewang.constructionapp.jxja".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr12);
                    qr_text.setText("江夏建安APP");
                }
                else if("com.a21zhewang.constructionapp.hp".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr13);
                    qr_text.setText("黄陂建安APP");
                }
                else if("com.a21zhewang.constructionapp.xz".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr14);
                    qr_text.setText("新洲建安APP");
                }
                else if("com.a21zhewang.constructionapp.hn".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr15);
                    qr_text.setText("经开（汉南）建安APP");
                }
                else if("com.a21zhewang.constructionapp.dhgx".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr16);
                    qr_text.setText("东新建安APP");
                }
                else if("com.a21zhewang.constructionapp.saqz".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr3);
                    qr_text.setText("武汉建安APP");
                }else if("com.a21zhewang.constructionapp.vipa".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.n1);
                    qr_text.setText("安卓建安-V1APP");
                }
                else if("com.a21zhewang.constructionapp.vipb".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.n2);
                    qr_text.setText("安卓建安-V2APP");
                }
                else if("com.a21zhewang.constructionapp.gdjc".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr20);
                    qr_text.setText("广电巡检APP");
                }
                else if("com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr21);
                    qr_text.setText("重庆建工APP");
                }
                else if("com.a21zhewang.constructionapp.qt".equals(AppUtils.getPackageName(getApplicationContext()))){
                    qr_img.setImageResource(R.mipmap.qr22);
                    qr_text.setText("琴台安管APP");
                }
                break;
            case R.id.ll_problem:
                startActivity(new Intent(SettingsActivity.this,FeedbackActivity.class));
                break;
            case R.id.ll_helpword:
                startActivity(new Intent(SettingsActivity.this,HelpWordActivity.class));
                break;
            case R.id.ll_play_info: //PeopleInfoActivity
                startActivity(new Intent(SettingsActivity.this,PeopleInfoActivity.class)); //1.反馈建议分类 2.视频分类
                break;
            case R.id.ll_video:
                startActivity(new Intent(SettingsActivity.this,SelectProblemActivity.class).putExtra("type","2").putExtra("title","选择视频内容")); //1.反馈建议分类 2.视频分类
                break;
            case R.id.ll_project_register:
                startActivity(new Intent(SettingsActivity.this,ProjectRegisterActivity.class));
                break;
    }
    }
    /**
     * 与本地版本比较判断是否需要更新
     */
    private void getVersionIsUpdate(){
        PackageManager pm = getPackageManager();
        try {
            //获取包的详细信息
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            //获取版本号和版本名称
            int serverVersion = PublicUtils.getspint("serverVersion");
            int localVersion = 1;
            localVersion = info.versionCode;
            if(serverVersion == localVersion){
                version.setText("当前最新："+ MyAppCon.getVersionName());
            }else {
                version.setText("请及时更新");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
