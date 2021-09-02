package com.a21zhewang.constructionapp.ui.dbsx;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.LoopPagerAdapter;
import com.a21zhewang.constructionapp.adapter.MyIndexAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ClassBean;
import com.a21zhewang.constructionapp.bean.IndexBean;
import com.a21zhewang.constructionapp.bean.IndexItem;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.constructionapp.customview.AutoTextView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.RecyclerSpace;
import com.a21zhewang.constructionapp.download.UpDateAppUtils;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.Act_WebView;
import com.a21zhewang.constructionapp.ui.aqsc.AqscActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.check.Act_Procedural;
import com.a21zhewang.constructionapp.ui.dbsx.ToDoActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.GTXTActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.GtxtxqActivity;
import com.a21zhewang.constructionapp.ui.gxys.GXYSActivity;
import com.a21zhewang.constructionapp.ui.gxys.GxysxqActivity;
import com.a21zhewang.constructionapp.ui.jdsb.JDSBLISTActivity;
import com.a21zhewang.constructionapp.ui.jdzf.Act_Jdzf;
import com.a21zhewang.constructionapp.ui.menu.Act_Menu;
import com.a21zhewang.constructionapp.ui.notice.Act_Notice;
import com.a21zhewang.constructionapp.ui.report.Act_ProgressReport;
import com.a21zhewang.constructionapp.ui.repository.RepositoryActivity;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReport;
import com.a21zhewang.constructionapp.ui.rollpageview.RollPagerView;
import com.a21zhewang.constructionapp.ui.sgrz.SgrzActivity;
import com.a21zhewang.constructionapp.ui.sgtx.SgtxActivity;
import com.a21zhewang.constructionapp.ui.tzgg.TzggActivity;
import com.a21zhewang.constructionapp.ui.tzgg.TzggxqActivity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcActivity2;
import com.a21zhewang.constructionapp.ui.wdpz.WDPZLISTActivity;
import com.a21zhewang.constructionapp.ui.wggl.WGImLActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.xmxx.XmxxActivity;
import com.a21zhewang.constructionapp.ui.yiqing.Act_YiQingCheck;
import com.a21zhewang.constructionapp.ui.zljd.ZljdActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.ui.zxjc.ZxjcActivity;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2020/9/18.
 */

public class ToDoActivity extends BaseActivity {
    @BindView(R.id.todo_RecyclerView)
    RecyclerView recyclerView;
    /**接口顺序的数据*/
    private List<IndexItem> dataList;
    private IndexItem item;
    private List<ClassBean> mListClass;
    private  int TIELE_ONE_TAG = 0;
    private  int TIELE_TWO_TAG = 0;
    private  int TIELE_THREE_TAG = 0;
    private  int TIELE_FOUR_TAG = 0;
    private  int TIELE_FIVE_TAG = 0;
    private List<String> studentName1;
    private List<String> studentName2;
    private List<String> studentName3;
    private List<String> studentName4;
    private List<String> studentName5;
    private ClassBean bean1 = new ClassBean();
    private ClassBean bean2 = new ClassBean();
    private ClassBean bean3 = new ClassBean();
    private ClassBean bean4 = new ClassBean();
    private ClassBean bean5 = new ClassBean();
    private WateToDoAdapter adapter;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_todo_layout;
    }
    @Override
    public void resume() {
        getdata();
    }
    @Override
    public void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerSpace(2, Color.TRANSPARENT));
    }

    public void getdata() {
        dataList = getIntent().getParcelableArrayListExtra("list");
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetTypeTodoNum", new MyCallBack() {
            @Override
            public void onResule(String res) {
                IndexBean indexBean = JsonUtils.getbean(res, IndexBean.class);
                /**清除缓存数据，刷新最新消息数*/
                for(int i=0;i<dataList.size();i++){
                    String Name = dataList.get(i).getMdName();
                    PublicUtils.putspint(Name,0);
                }
//                getitem(dataList, "Assistant",indexBean.getTypeTodoCount());
                getitem(dataList, "cm",indexBean.getCoordinateNumber());
                getitem(dataList, "Quality",indexBean.getQualityMsgCount());
                getitem(dataList, "Safety",indexBean.getSafetyMsgCount());
                getitem(dataList, "Civilization",indexBean.getCivilizedMsgCount());
                getitem(dataList, "Notice2",indexBean.getNoticeNumber());
                getitem(dataList, "Notice1",indexBean.getAttentionNumber());
                getitem(dataList, "Notice3",indexBean.getJGNoticeNumber());
                getitem(dataList, "daily",indexBean.getDailyLogNumber());
                getitem(dataList, "ProjectMsg",indexBean.getProjectNumber());
                getitem(dataList, "ProAccept",indexBean.getProAcceptanceNmuber());
                getitem(dataList, "EnforcementBook",indexBean.getLawSuperviseNumber());   //执法单
                getitem(dataList, "ResumWork",indexBean.getResumWorkExaminationCount());  //疫情复工
                getitem(dataList, "KeyExamination",indexBean.getKeyExaminationCount());   //重点检查
                getitem(dataList, "ProgExamination",indexBean.getProgExaminationCount()); //程序检查
                for (int i = 1; i < 6; i++) {
                    mListClass = new ArrayList<>();
                    studentName1 = new ArrayList<>();
                    studentName2 = new ArrayList<>();
                    studentName3 = new ArrayList<>();
                    studentName4 = new ArrayList<>();
                    studentName5 = new ArrayList<>();
                    for (int  j = 0; j < dataList.size(); j++) {
                        String ID = dataList.get(j).getMdID();
                        int number = PublicUtils.getspint(dataList.get(j).getMdName());
//                        if ("Assistant".equals(ID) && number != 0 ) {
//                            TIELE_ONE_TAG = 1;
//                            studentName1.add("小助手");
//                        }
                        if ("Statistics".equals(ID)&& number != 0 ) {
                            TIELE_ONE_TAG = 1;
                            studentName1.add("汇总统计");
                        }
                        if("Safety".equals(ID)&& number != 0 ) {
                            TIELE_TWO_TAG = 2;
                            studentName2.add("安全检查");
                        }
                        if("Civilization".equals(ID)&& number != 0 ) {
                            TIELE_TWO_TAG = 2;
                            studentName2.add("文明施工");
                        }
                        if("Quality".equals(ID)&& number != 0 ) {
                            TIELE_TWO_TAG = 2;
                            studentName2.add("质量检查");
                        }
                        if("KeyExamination".equals(ID)&& number != 0 ){
                            TIELE_THREE_TAG = 3;
                            studentName3.add("重点检查");
                        }
                        if("ProgExamination".equals(ID)&& number != 0 ){
                            TIELE_THREE_TAG = 3;
                            studentName3.add("程序检查");
                        }
                        if("ResumWork".equals(ID)&& number != 0 ){
                            TIELE_THREE_TAG = 3;
                            studentName3.add("疫情复工检查");
                        }
                        if("RiskReport".equals(ID)&& number != 0 ){
                            TIELE_FOUR_TAG = 4;
                            studentName4.add("风险上报");
                        }
                        if("MajorRisk".equals(ID)&& number != 0 ){
                            TIELE_FOUR_TAG = 4;
                            studentName4.add("危大动态上报");
                        }
                        if("ProgressReport".equals(ID)&& number != 0 ){
                            TIELE_FOUR_TAG = 4;
                            studentName4.add("进度上报");
                        }
                        if("ProjectMsg".equals(ID)&& number != 0 ) {
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("项目信息");
                        }
                        if("Notice1".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("施工提醒");
                        }
                        if("Repository".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("知识库");
                        }
                        if("mdID".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("施工日志");
                        }
                        if("cm".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("沟通协调");
                        }
                        if("Notice2".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("重点督办");
                        }
                        if("ProAccept".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("工序验收");
                        }
                        if("Examination".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("专项检查");
                        }
                        if("Progress".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("进度填报");
                        }
                        if("DecisionScreen".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("决策小屏");
                        }
                        if("TodayDecisionScreen".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("今日小屏");
                        }
                        if("EnforcementBook".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("执法单");
                        }
                        if("Notice3".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("通知简报");
                        }
                        if("SocialSupervise".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("网格员管理");
                        }
                        if("daily".equals(ID)&& number != 0 ){
                            TIELE_FIVE_TAG = 5;
                            studentName5.add("施工日志");
                        }
                    }
                }
                if(TIELE_ONE_TAG == 1){
                    bean1.className = "第一栏";
                }
                if(TIELE_TWO_TAG == 2){
                    bean2.className = "日常检查";
                }
                if(TIELE_THREE_TAG == 3){
                    bean3.className = "规定动作";
                }
                if(TIELE_FOUR_TAG == 4){
                    bean4.className = "信息上报";
                }
                if(TIELE_FIVE_TAG == 5){
                    bean5.className = "信息浏览";
                }
                bean1.classStudents = studentName1;
                bean2.classStudents = studentName2;
                bean3.classStudents = studentName3;
                bean4.classStudents = studentName4;
                bean5.classStudents = studentName5;
                mListClass.add(bean1);
                mListClass.add(bean2);
                mListClass.add(bean3);
                mListClass.add(bean4);
                mListClass.add(bean5);
                adapter = new WateToDoAdapter(ToDoActivity.this, mListClass);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new WateToDoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ClassBean classBean, int position) {
                        String TitleName = classBean.classStudents.get(position);
                        if ("施工日志".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, SgrzActivity.class));
                        }
                        else if ("沟通协调".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, GTXTActivity.class));;
                        }
                        else if ("质量检查".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, ZljdActivity.class));
                        }
                        else if ("文明施工".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, WmsgActivity.class));
                        }
                        else if ("安全检查".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, AqscActivity.class));
                        }else if ("施工提醒".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, SgtxActivity.class));
                        }else if ("重点督办".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, TzggActivity.class));
                        }else if ("工序验收".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, GXYSActivity.class));
                        }else if ("专项检查".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, ZxjcActivity.class));
                        }else if ("项目信息".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, XmxxActivity.class));
                        }else if ("汇总统计".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_Menu.class));
                        }else if ("进度填报".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, JDSBLISTActivity.class));
                        }else if ("危大动态上报".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, WDPZLISTActivity.class));
                        }else if ("知识库".equals(TitleName)) {
                            if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))){
                                startActivity(new Intent(ToDoActivity.this, RepositoryActivity.class)
                                        .putExtra("url","http://106.15.92.195:9003").putExtra("title","知识库"));
                            }else {
                                startActivity(new Intent(ToDoActivity.this, RepositoryActivity.class)
                                        .putExtra("url","http://safetycore.telsafe.com.cn:8008").putExtra("title","知识库"));
                            }
                        }else if ("重点检查".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, WdjcActivity2.class));
                        }else if ("决策小屏".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_WebView.class).
                                    putExtra("url", "https://datav.aliyun.com/share/d4ff7153cc9ff03cec976173899bad75").putExtra("fromWhere", "a"));
                        }else if ("今日小屏".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_WebView.class).
                                    putExtra("url", "https://datav.aliyun.com/share/4c06d21954b171523758693a710efb0b").putExtra("fromWhere", "z"));
                        }else if ("执法单".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_Jdzf.class));
                        }else if ("通知简报".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_Notice.class));
                        }else if ("风险类型上报".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_RiskReport.class));
                        }else if ("程序检查".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_Procedural.class));
                        }else if ("疫情复工检查".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_YiQingCheck.class));
                        }else if ("进度上报".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, Act_ProgressReport.class));
                        }else if ("网格员管理".equals(TitleName)) {
                            startActivity(new Intent(ToDoActivity.this, WGImLActivity.class));
                        }
                    }
                });
            }
            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
        });
    }
    private void getitem(List<IndexItem> items, String mdID, int number) {
        if (items == null) return;
        for (IndexItem item : items) {
            if (mdID.equals(item.getMdID())) {
                item.setNoRead(number);
                if(number != 0){
                    PublicUtils.putspint(item.getMdName(),number);
                }
            }
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
