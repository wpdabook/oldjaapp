package com.a21zhewang.constructionapp.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.customview.CircleTextImageView;
import com.a21zhewang.constructionapp.customview.CircularProgressView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AutoLayoutActivity implements View.OnClickListener {

    private SparseArray<View> mViews;
    private Unbinder unbinder;
    public static final int DELAY = 1000;
    private static long lastClickTime = 0;
    private String appSource;

    public String getAppSource() {
        return appSource;
    }

    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }
    /**
     * setContentView之前调用
     */
    public abstract void beforesetContentView();

    /**
     *
     * @return 设置布局文件
     */
    public abstract int getLayoutId();

    /**
     * 初始化界面
     */
    public abstract void initViews();

    /**
     * 初始化事件
     */
    public abstract void initListener();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * @param v 点击事件的实现
     */
    public abstract void processClick(View v);

    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAppSource();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        mViews = new SparseArray<>();
        try {
            beforesetContentView();
            setContentView(getLayoutId());
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
            getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//进入页面默认不弹出软键盘
      //绑定activity
            unbinder = ButterKnife.bind( this );
            CloseActivityClass.activityList.add(this);
        initViews();
        initListener();
        initData();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("错误！",this.getLocalClassName()+"页面出错！--"+e.getMessage());

        }
    }


    public <E extends View> E findView(int viewId) {
        E view = (E) mViews.get(viewId);
        if (view == null) {
            view = (E) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    @Override
    protected void onDestroy() {
        if (mViews!=null){
            mViews.clear();
            mViews=null;
        }
        if (unbinder!=null)
        unbinder.unbind();//解除绑定
        unbinder=null;
        CloseActivityClass.activityList.remove(this);
        destroy();

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
        resume();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    /**
     * 页面显示
     */
    public void resume(){

    }

    /**
     * 页面销毁
     */
    public void destroy(){

    }
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    public void  BaseRateValues(TextView textView, Double b){
        if(b.intValue() == 0){
            textView.setText("0%");
        }else {
            DecimalFormat df = new DecimalFormat("######0.00");
            textView.setText(df.format(b)+"%");
        }
    }
    public void  CircleImageBaseRateValues(CircleTextImageView textView, Double b){
        if(b.intValue() == 0){
            textView.setText("0%");
        }else {
            DecimalFormat df = new DecimalFormat("######0.00");
            textView.setText(df.format(b)+"%");
        }
    }
    public void  CircleImageBaseRateValues(CircularProgressView textView, Double b){
        if(b.intValue() == 0){
            textView.setProgress(0);
        }else {
            DecimalFormat df = new DecimalFormat("######0.00");
//            textView.setText(df.format(b)+"%");
            int s = Integer.parseInt(new DecimalFormat("0").format(b*100));//百分比没有小数点
            textView.setProgress(s);
        }
    }
    public void  BaseRemindRateValues(String remind,TextView textView, Double b){
        if(b.intValue() == 0){
            textView.setText("0%");
        }else {
            DecimalFormat df = new DecimalFormat("######0.00");
            textView.setText(remind + "："+ df.format(b)+"%");
        }
    }
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if(hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void showToast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    public  void clearspValues(){
        PublicUtils.putspstring("tab_tag_one","");
        PublicUtils.putspstring("tab_tag_two","");
        PublicUtils.putspstring("tab_tag_third","");
        PublicUtils.putspstring("tab_tag_four","");
        PublicUtils.putspstring("tab_tag_five","");
    }
     //两整数相除得出百分比（保留两位小数）
     public int getPercentage(int a , int b){

         if(b != 0){
             double d = new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
             int s = Integer.parseInt(new DecimalFormat("0").format(d*100));//百分比没有小数点
             return s;
         }
        return 0;
     }
    public static boolean isNotFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return true;
        }else{
            return false;
        }
    }
    public void SetTextIsNoEmpty(TextView textView,String str){
        if(!TextUtils.isEmpty(str)){
            textView.setText(str);
        }
    }
    public static boolean isPhone(String phone) {
//      Pattern p = Pattern.compile("^(0(\\.(?!0+$)\\d{1,3})?||([1-9][0-9]*(\\.\\d{1,2})?))$");
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }
    public void initAppSource() {
        if("com.a21zhewang.constructionapp3".equals(getPackageName())){
            setAppSource("WHDC");
        }
        else if("com.a21zhewang.constructionapp.wc".equals(getPackageName())){
            setAppSource("WC");
        }
        else if("com.a21zhewang.constructionapp.jh".equals(getPackageName())){
            setAppSource("JH");
        }
        else if("com.a21zhewang.constructionapp.ja".equals(getPackageName())){
            setAppSource("JA");
        }
        else if("com.a21zhewang.constructionapp.qk".equals(getPackageName())){
            setAppSource("QK");
        }
        else if("com.a21zhewang.constructionapp.hyzj".equals(getPackageName())){
            setAppSource("HY");
        }
        else if("com.a21zhewang.constructionapp.hs".equals(getPackageName())){
            setAppSource("HS");
        }
        else if("com.a21zhewang.constructionapp.qsja".equals(getPackageName())){
            setAppSource("QS");
        }
        else if("com.a21zhewang.constructionapp.dxh".equals(getPackageName())){
            setAppSource("DXH");
        }
        else if("com.a21zhewang.constructionapp.cdja".equals(getPackageName())){
            setAppSource("CD");
        }
        else if("com.a21zhewang.constructionapp.jxja".equals(getPackageName())){
            setAppSource("JX");
        }
        else if("com.a21zhewang.constructionapp.hp".equals(getPackageName())){
            setAppSource("HP");
        }
        else if("com.a21zhewang.constructionapp.xz".equals(getPackageName())){
            setAppSource("XZ");
        }
        else if("com.a21zhewang.constructionapp.hn".equals(getPackageName())){
            setAppSource("HN");
        }
        else if("com.a21zhewang.constructionapp.dhgx".equals(getPackageName())){
            setAppSource("DHGX");
        }
        else if("com.a21zhewang.constructionapp.saqz".equals(getPackageName())){
            setAppSource("SAQZ");
        }else if("com.a21zhewang.constructionapp.vipa".equals(getPackageName())){
            setAppSource("QS");
        }
        else if("com.a21zhewang.constructionapp.vipb".equals(getPackageName())){
            setAppSource("VIPB");
        }
        else if("com.a21zhewang.constructionapp.gdjc".equals(getPackageName())){
            setAppSource("GDJC");
        }
        else if("com.a21zhewang.constructionapp.cqjg".equals(getPackageName())){
            setAppSource("CQ");
        }
        else if("com.a21zhewang.constructionapp.qt".equals(getPackageName())){
            setAppSource("QT");
        }
    }
}
