package com.a21zhewang.constructionapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.LoopPagerAdapter;
import com.a21zhewang.constructionapp.adapter.MyIndexAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.ClassBean;
import com.a21zhewang.constructionapp.bean.IndexBean;
import com.a21zhewang.constructionapp.bean.IndexItem;
import com.a21zhewang.constructionapp.bean.JygzAreaData;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.constructionapp.customview.AutoTextView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.RecyclerSpace;
import com.a21zhewang.constructionapp.download.UpDateAppUtils;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.receiver.PushMessageReceiver;
import com.a21zhewang.constructionapp.ui.aqsc.AqscActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.check.Act_Procedural;
import com.a21zhewang.constructionapp.ui.dbsx.ToDoActivity;
import com.a21zhewang.constructionapp.ui.dxzf.Act_Dxzf;
import com.a21zhewang.constructionapp.ui.gtxtiu.GTXTActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.GtxtxqActivity;
import com.a21zhewang.constructionapp.ui.gxys.GXYSActivity;
import com.a21zhewang.constructionapp.ui.gxys.GxysxqActivity;
import com.a21zhewang.constructionapp.ui.inspection.Act_AddInspection;
import com.a21zhewang.constructionapp.ui.inspection.Act_AddInspection2;
import com.a21zhewang.constructionapp.ui.inspection.Act_Inspection;
import com.a21zhewang.constructionapp.ui.jdsb.JDSBLISTActivity;
import com.a21zhewang.constructionapp.ui.jdzf.Act_Jdzf;
import com.a21zhewang.constructionapp.ui.jygz.Act_Jygz;
import com.a21zhewang.constructionapp.ui.kqgl.Act_Kqgl;
import com.a21zhewang.constructionapp.ui.map.Act_NavigationMap;
import com.a21zhewang.constructionapp.ui.menu.Act_Menu;
import com.a21zhewang.constructionapp.ui.nfile.Act_NoticeFile;
import com.a21zhewang.constructionapp.ui.notice.Act_Notice;
import com.a21zhewang.constructionapp.ui.rcjd.Act_Rcjd;
import com.a21zhewang.constructionapp.ui.report.Act_ProgressReport;
import com.a21zhewang.constructionapp.ui.repository.RepositoryActivity;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReport;
import com.a21zhewang.constructionapp.ui.rollpageview.RollPagerView;
import com.a21zhewang.constructionapp.ui.sgrz.SgrzActivity;
import com.a21zhewang.constructionapp.ui.sgtx.SgtxActivity;
import com.a21zhewang.constructionapp.ui.task.Act_TaskList;
import com.a21zhewang.constructionapp.ui.tzgg.TzggActivity;
import com.a21zhewang.constructionapp.ui.tzgg.TzggxqActivity;
import com.a21zhewang.constructionapp.ui.video.Act_Surveillance;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcActivity2;
import com.a21zhewang.constructionapp.ui.wdpz.WDPZLISTActivity;
import com.a21zhewang.constructionapp.ui.wggl.WGImLActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.xmxx.XmxxActivity;
import com.a21zhewang.constructionapp.ui.yiqing.Act_YiQingCheck;
import com.a21zhewang.constructionapp.ui.zddb.Act_Zddb;
import com.a21zhewang.constructionapp.ui.zddb.Act_ZddbAdd;
import com.a21zhewang.constructionapp.ui.zljd.ZljdActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.ui.zxjc.ZxjcActivity;
import com.a21zhewang.constructionapp.update.UpDateUtils;
import com.a21zhewang.constructionapp.update.UpdateAppShowDialog;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
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

public class IndexActivity extends BaseActivity {
    @BindView(R.id.activity_index_img_sz)
    ImageView activityIndexImgSz;//????????????
    @BindView(R.id.activity_index_tv_tzgg)
    AutoTextView activityIndexTvTzgg;//????????????
    @BindView(R.id.activity_index_tv_dwname)
    TextView activityIndexTvDwname;//????????????
    @BindView(R.id.activity_index_tv_username)
    TextView activityIndexTvUsername;//????????????
    @BindView(R.id.activity_index_MyTitleBar)
    MyTitleBar activityIndexMyTitleBar;
    @BindView(R.id.activity_index_img_sys)
    ImageView activityIndexImgSys;
    @BindView(R.id.view_pager)
    RollPagerView mViewPager;
    @BindView(R.id.index_RecyclerView)
    RecyclerView indexRecyclerView;
//    private BaseQuickAdapter<IndexItem, BaseViewHolder> adapter;
    //??????????????????????????????
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES};
    //??????code
    private static final int OPEN_SET_REQUEST_CODE = 100;
    /**?????????????????????*/
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
    private MyIndexAdapter adapter;
    private int page = 0;
    private static int sequence = 1;
    private String INDEX_TAG = "";
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_index2;
    }
    @Override
    public void resume() {
        getModule();
        if(PublicUtils.IS_DEBUG_MODE == false){
            new UpDateUtils().Update(IndexActivity.this);
            INDEX_TAG = getIntent().getStringExtra("INDEX_TAG");
            if("INDEX_TAG".equals(INDEX_TAG)){
                if (0 != PushMessageReceiver.count) {
                    //????????????
                    PushMessageReceiver.count = 0;
                    AppShortCutUtil.setCount(PushMessageReceiver.count, IndexActivity.this);
                }
            }
        }

    }
    @Override
    public void initViews() {
        initPermissions();
        getJygzRQcd();
        dataList = new ArrayList<>();
        activityIndexMyTitleBar.initIndexTitle();
        mViewPager.setAdapter(new IndexActivity.ImageLoopAdapter(mViewPager));
        mViewPager.setHintView(null);
        indexRecyclerView.setLayoutManager(new GridLayoutManager(IndexActivity.this,3));
        indexRecyclerView.addItemDecoration(new RecyclerSpace(10, Color.TRANSPARENT));
        if(PublicUtils.IS_DEBUG_MODE == false) {
            if (JPushInterface.isPushStopped(MyAppCon.appcontext)) {
                JPushInterface.resumePush(MyAppCon.appcontext);
            }
            JPushInterface.setAlias(MyAppCon.appcontext, PublicUtils.UserID, new TagAliasCallback() {
                @Override
                public void gotResult(int responseCode, String s, Set<String> set) {
                    if (responseCode == 0) {
                    } else if (responseCode == 6002) {
                    } else {
                    }
                }
            });
        }
    }
    @Override
    public void initListener() {
        activityIndexTvTzgg.previous();
        activityIndexTvTzgg.setstr("");
        activityIndexTvTzgg.startloop();
    }
    public void getModule(){
        JSONObject jsonObject = new JSONObject();
        final String username = PublicUtils.getspstring("user");
        String pw = PublicUtils.getspstring("password");
        try {
            jsonObject.put("userID", username);
            jsonObject.put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        XUtil.postjsondata(jsonObject, "CheckLogin", new MyCallBack() {
            @Override
            public void onResule(String res) {
                PublicUtils.userBean = (UserBean) JsonUtils.getbean(res, UserBean.class);
                if (PublicUtils.userBean.getModules() != null) {
                    dataList = PublicUtils.userBean.getModules();
                    for (int i = 1; i < 6; i++) {
                        mListClass = new ArrayList<>();
                        studentName1 = new ArrayList<>();
                        studentName2 = new ArrayList<>();
                        studentName3 = new ArrayList<>();
                        studentName4 = new ArrayList<>();
                        studentName5 = new ArrayList<>();
                        for (int  j = 0; j < dataList.size(); j++) {
                            String ID = dataList.get(j).getMdID();
                            if ("Assistant".equals(ID)) {
                                TIELE_ONE_TAG = 1;
                                studentName1.add("?????????");
                            }
                            if ("Statistics".equals(ID)) {
                                TIELE_ONE_TAG = 1;
                                studentName1.add("????????????");
                            }
                            if("Notice2".equals(ID)){
                                TIELE_ONE_TAG = 1;
                                studentName1.add("????????????");
                            }
                            if("KeySupervise".equals(ID)){
                                TIELE_ONE_TAG = 1;
                                studentName1.add("????????????");
                            }
                            if("Safety".equals(ID)) {
                                TIELE_TWO_TAG = 2;
                                studentName2.add("????????????");
                            }
                            if("Civilization".equals(ID)) {
                                TIELE_TWO_TAG = 2;
                                studentName2.add("????????????");
                            }
                            if("Quality".equals(ID)) {
                                TIELE_TWO_TAG = 2;
                                studentName2.add("????????????");
                            }
                            if("BroadCastExamination".equals(ID)){
                                TIELE_THREE_TAG = 3;
                                studentName3.add("????????????");
                            }
                            if("KeyExamination".equals(ID)){
                                TIELE_THREE_TAG = 3;
                                studentName3.add("????????????");
                            }
                            if("ProgExamination".equals(ID)){
                                TIELE_THREE_TAG = 3;
                                studentName3.add("????????????");
                            }
                            if("ResumWork".equals(ID)){
                                TIELE_THREE_TAG = 3;
                                studentName3.add("??????????????????");
                            }
                            if("EasyNotice".equals(ID)){
                                TIELE_THREE_TAG = 3;
                                studentName3.add("????????????");
                            }
                            if("RiskReport".equals(ID)){
                                TIELE_FOUR_TAG = 4;
                                studentName4.add("??????????????????");
                            }
                            if("MajorRisk".equals(ID)){
                                TIELE_FOUR_TAG = 4;
                                studentName4.add("??????????????????");
                            }
                            if("ProgressReport".equals(ID)){
                                TIELE_FOUR_TAG = 4;
                                studentName4.add("????????????");
                            }
                            if("ProjectMsg".equals(ID)) {
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("Notice1".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("Repository".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("?????????");
                            }
                            if("mdID".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("cm".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("ProAccept".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("Examination".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("Progress".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("DecisionScreen".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("TodayDecisionScreen".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("EnforcementBook".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("?????????");
                            }
                            if("Notice3".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("SocialSupervise".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("???????????????");
                            }
                            if("daily".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("SuperviseManage".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????(?????????)");
                            }
                            if("EngineeringNavigation".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("DayKeySupervise".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("Attendance".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("VideoSurveillance".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if ("TaskSupervise".equals(ID)) {
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                            if("NotificationDocument".equals(ID)){
                                TIELE_FIVE_TAG = 5;
                                studentName5.add("????????????");
                            }
                         }
                    }
                    if(TIELE_ONE_TAG == 1){
                        bean1.className = "?????????";
                    }
                    if(TIELE_TWO_TAG == 2){
                        bean2.className = "????????????";
                    }
                    if(TIELE_THREE_TAG == 3){
                        bean3.className = "????????????";
                    }
                    if(TIELE_FOUR_TAG == 4){
                        bean4.className = "????????????";
                    }
                    if(TIELE_FIVE_TAG == 5){
                        bean5.className = "????????????";
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
                    adapter = new MyIndexAdapter(IndexActivity.this, mListClass);
                    indexRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new MyIndexAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ClassBean classBean, int position) {
//                            Log.e("onItemClick", "onItemClick: " + classBean.classStudents.get(position));
                            String TitleName = classBean.classStudents.get(position);
                            if ("?????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, ToDoActivity.class)
                                        .putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) dataList));
                            }
                            if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, SgrzActivity.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, GTXTActivity.class));;
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, ZljdActivity.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, WmsgActivity.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, AqscActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, SgtxActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, TzggActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, GXYSActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, ZxjcActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, XmxxActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Menu.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, JDSBLISTActivity.class));
                            }else if ("??????????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, WDPZLISTActivity.class));
                            }else if ("?????????".equals(TitleName)) {
                                if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))){
                                    startActivity(new Intent(IndexActivity.this, RepositoryActivity.class)
                                            .putExtra("url","http://106.15.92.195:9003").putExtra("title","?????????"));
                                }else {
                                    startActivity(new Intent(IndexActivity.this, RepositoryActivity.class)
                                            .putExtra("url","http://safetycore.telsafe.com.cn:8008").putExtra("title","?????????"));
                            }
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, WdjcActivity2.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_WebView.class).
                                        putExtra("url", "https://datav.aliyun.com/share/d4ff7153cc9ff03cec976173899bad75").putExtra("fromWhere", "a"));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_WebView.class).
                                        putExtra("url", "https://datav.aliyun.com/share/4c06d21954b171523758693a710efb0b").putExtra("fromWhere", "z"));
                            }else if ("?????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Jdzf.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Notice.class));
                            }else if ("??????????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_RiskReport.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Procedural.class));
                            }else if ("??????????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_YiQingCheck.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_ProgressReport.class));
                            }else if ("???????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, WGImLActivity.class));
                            }else if ("????????????".equals(TitleName)) {
                                getCheckActInspectionDate();
                            }
                            else if ("????????????(?????????)".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Dxzf.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_NavigationMap.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Zddb.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Jygz.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Rcjd.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Kqgl.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_Surveillance.class));
                            }
                            else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_TaskList.class));
                            }else if ("????????????".equals(TitleName)) {
                                startActivity(new Intent(IndexActivity.this, Act_NoticeFile.class));
                            }
                        }
                    });
                }
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
        getdata();
    }

    /**
     * ??????????????????????????????
     * 1.????????????????????????????????????????????????
     * 2.?????????????????????
     */
    private void getCheckActInspectionDate() {
        JSONObject object = JsonUtils.getjsonobj("{\"listType\":\"" + 2 + "\",\"keyword\":\"" + "" + "\",\"listStatus\":\"" + 0 +  "\"," +
                "\"pageIndex\":\"" +  String.valueOf(page) + "\",\"pageSize\":\"" +  10 + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                if("{}".equals(res)){
                    startActivity(new Intent(IndexActivity.this,Act_AddInspection2.class));
                }else {
                    startActivity(new Intent(IndexActivity.this,Act_Inspection.class));
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "GetListBroadCastExamination", callback);
        }else {
            XUtil.postjsondatasj(object, "GetListBroadCastExamination", callback);
        }
    }
    /**
     * ???????????????
     */
    public void getdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetTypeNum", new MyCallBack() {
            @Override
            public void onResule(String res) {
                IndexBean indexBean = JsonUtils.getbean(res, IndexBean.class);
                /**??????????????????????????????????????????*/
                for(int i=0;i<dataList.size();i++){
                    String Name = dataList.get(i).getMdName();
                    PublicUtils.putspint(Name,0);
                }
                getitem(dataList, "Assistant",indexBean.getTypeTodoCount());
                getitem(dataList, "cm",indexBean.getCoordinateNumber());
                getitem(dataList, "Quality",indexBean.getQualityNumber());
                getitem(dataList, "Safety",indexBean.getSafetyNumber());
                getitem(dataList, "Civilization",indexBean.getCivilizedNumber());
                getitem(dataList, "Notice2",indexBean.getNoticeNumber());
                getitem(dataList, "Notice1",indexBean.getAttentionNumber());
                getitem(dataList, "Notice3",indexBean.getJGNoticeNumber());
                getitem(dataList, "daily",indexBean.getDailyLogNumber());
                getitem(dataList, "ProjectMsg",indexBean.getProjectNumber());
                getitem(dataList, "ProAccept",indexBean.getProAcceptanceNmuber());
                getitem(dataList, "EnforcementBook",indexBean.getLawSuperviseNumber()); //?????????
                List<IndexBean.TopBarsBean> topBars = indexBean.getTopBars();
                activityIndexTvTzgg.setstr(topBars, new AutoTextView.AutoTextViewAPI<IndexBean.TopBarsBean>() {
                    @Override
                    public String setstrs(IndexBean.TopBarsBean str) {
                        return str.getMsgTitle();
                    }
                    @Override
                    public void onclick(IndexBean.TopBarsBean obj) {
                        String objMethod = obj.getMethod();
                        if (objMethod.equals("GetCoordinateMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity.this, GtxtxqActivity.class);
                            intent.putExtra("msgID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetQualityMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity.this, ZljdxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetSafetyMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity.this, AqscxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetCivilizedMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity.this, WmsgxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetNoticeMsgDetails")) {// ????????????
                            Intent intent = new Intent(IndexActivity.this, TzggxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetProcessAcceptanceDetails")) {
                            Intent intent = new Intent(IndexActivity.this, GxysxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if(objMethod.equals("GetJGNoticeDetail")){  //????????????
                            Intent intent = new Intent(IndexActivity.this, Act_Notice.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        }else if(objMethod.equals("Get_SuperviseDetail")){  //?????????
                            Intent intent = new Intent(IndexActivity.this, Act_Jdzf.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
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
    @OnClick({
            R.id.activity_index_img_sz
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_index_img_sz:
                startActivity(new Intent(IndexActivity.this, SettingsActivity.class));
                break;
        }
    }
    private class ImageLoopAdapter extends LoopPagerAdapter {
        int[] imgs = new int[]{R.mipmap.nc1, R.mipmap.nc2, R.mipmap.nc3};

        public ImageLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imgs[position]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(position == 0){
//                        startActivity(new Intent(IndexActivity.this,RepositoryActivity.class)
//                                .putExtra("url","http://u11488944.viewer.maka.im/k/6S1GTE4AW11488944?t=1581570360199")
//                                .putExtra("title","???????????? ????????????"));
//                    }
//                    if(position == 1){
//                        startActivity(new Intent(IndexActivity.this,RepositoryActivity.class)
//                                .putExtra("url","http://u11488944.viewer.maka.im/k/XEF9ITAOW11488944?t=1581570605081")
//                                .putExtra("title","????????????"));
//                    }
//                    if(position == 2){
//                        startActivity(new Intent(IndexActivity.this,RepositoryActivity.class)
//                                .putExtra("url","http://u11488944.viewer.maka.im/k/PLEG02UUW11488944?t=1581570799732")
//                                .putExtra("title","??????????????????????????????"));
//                    }
                }
            });
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
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
    public void  initWeather(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{city:??????}"), "GetWeather", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try{
                    JSONObject object = new JSONObject(res);
                    String WeatherText = object.optString("weather");
                    activityIndexMyTitleBar.setLeftTitlenametv(WeatherText);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(ObjBean getbean) {
                PublicUtils.putspboolean("GetWeather", false);
            }

            /**
             * ??????????????????
             */
            @Override
            public void onWrong() {
                PublicUtils.putspboolean("GetWeather", false);
            }

            @Override
            public void onFinished() {

            }
        });
    }
    /**
     * ?????????????????????
     */
    private void getJygzRQcd() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetNoticeAddress", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)){
                    if(!"[{}]".equals(res)){
                        try {
                            JSONObject object = new JSONObject(res);
                            if(object.optString("preAddress") != null){
                                PublicUtils.putspstring("preAddress",object.optString("preAddress"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }
    //???????????????????????????????????????
    private void initPermissions() {
        if (lacksPermission(permissions)) {//????????????????????????
            //?????????????????????????????????String?????????????????????????????????????????????onRequestPermissionsResult ???????????????code????????????
            ActivityCompat.requestPermissions(this, permissions, OPEN_SET_REQUEST_CODE);
        } else {
            //????????????????????????
        }
    }

    //????????????true??????????????????
    public boolean lacksPermission(String[] permissions) {
        for (String permission : this.permissions) {
            //???????????????????????????true=????????????
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }
    @Override
    public void initData() {
       initWeather();
    }

    @Override
    public void processClick(View v) {

    }
}
