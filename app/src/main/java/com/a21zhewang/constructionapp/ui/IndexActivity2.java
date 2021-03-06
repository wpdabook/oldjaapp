package com.a21zhewang.constructionapp.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.LoopPagerAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.IndexBean;
import com.a21zhewang.constructionapp.bean.IndexItem;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TagBean;
import com.a21zhewang.constructionapp.bean.UserBean;
import com.a21zhewang.constructionapp.customview.AutoTextView;
import com.a21zhewang.constructionapp.customview.MyIndexItem;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.RecyclerSpace;
import com.a21zhewang.constructionapp.download.UpDateAppUtils;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.aqsc.AqscActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscAddActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.check.Act_Procedural;
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
import com.a21zhewang.constructionapp.ui.wmsg.WmsgAddActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.xmxx.XmxxActivity;
import com.a21zhewang.constructionapp.ui.yiqing.Act_YiQingCheck;
import com.a21zhewang.constructionapp.ui.zljd.ZljdActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdAddActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.ui.zxjc.ZxjcActivity;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.CloseActivityClass;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
public class IndexActivity2 extends BaseActivity {


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
    @BindView(R.id.index_RecyclerView)
    RecyclerView indexRecyclerView;
    private long firstTime = 0;
    //  private UhfManager uhfManager;
    private MediaPlayer mp;
    private ReadThread readThread;
    private Dialog setTagLoading;
    private Handler handler;
    //??????????????????
//    private UhfReader uhfReader;
    //???????????????
    private String serialportPath = "/dev/ttyMT1";
    private int port = 13;
    private BaseQuickAdapter<IndexItem, BaseViewHolder> adapter;
    private RollPagerView mViewPager;
    private Callback.Cancelable cancelable;
    private ArrayList<String> statisticsList = new ArrayList<>();
    //??????????????????????????????
    private String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
    //??????code
    private static final int OPEN_SET_REQUEST_CODE = 100;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_index2;
    }

    @Override
    public void initViews() {
        initPermissions();//?????????????????????
        Bundle obj = getIntent().getBundleExtra ("obj");
        if (obj != null) {
            String msgID = obj.getString("msgID");
            String Method = obj.getString("Method");
            Intent mintent = new Intent();

            if ("GetCoordinateMsgDetails".equals(Method)) {//????????????
                mintent.setClass(this, GtxtxqActivity.class);

            } else if ("GetQualityMsgDetails".equals(Method)) {//????????????
                mintent.setClass(this, ZljdxqActivity.class);

            } else if ("GetSafetyMsgDetails".equals(Method)) {//????????????
                mintent.setClass(this, AqscxqActivity.class);

            } else if ("GetCivilizedMsgDetails".equals(Method)) {//????????????
                mintent.setClass(this, WmsgxqActivity.class);

            } else if ("GetNoticeMsgDetails".equals(Method)) {//???????????? ????????????
                mintent.setClass(this, TzggxqActivity.class);

            } else if ("GetProcessAcceptanceDetails".equals(Method)) {
                mintent.setClass(this, GxysxqActivity.class);

            } else if ("GetJGNoticeDetail".equals(Method)) { //????????????
                mintent.setClass(this, Act_Notice.class);

            } else if ("Get_SuperviseDetail".equals(Method)) { //?????????
                mintent.setClass(this, Act_Jdzf.class);

            } else{
                mintent.setClass(this, IndexActivity2.class);
            }

            mintent.putExtra("recordID", msgID);
            mintent.putExtra("msgID", msgID);

            startActivity(mintent);
        }
//        if(PublicUtils.IS_DEBUG_MODE == false) {
//            if (JPushInterface.isPushStopped(MyAppCon.appcontext)) {
//                JPushInterface.resumePush(MyAppCon.appcontext);
//            }
//
//            JPushInterface.setAlias(MyAppCon.appcontext, PublicUtils.UserID, new TagAliasCallback() {
//                @Override
//                public void gotResult(int responseCode, String s, Set<String> set) {
//                    if (responseCode == 0) {
//
//                    } else if (responseCode == 6002) {
//
//                    } else {
//                        //   PublicUtils.log("?????????????????????"+responseCode);
//                    }
//                }
//            });
//        }
        if (PublicUtils.URL.equals("http://106.14.167.156:9001/API/SGGL.aspx")) {
            activityIndexMyTitleBar.setTitlenametv("????????????????????????");
        }
        init();
        activityIndexTvTzgg.previous();
        activityIndexTvTzgg.setstr("");
        activityIndexTvTzgg.startloop();
        activityIndexTvDwname.setText(PublicUtils.userBean.getEtpShortName());
        activityIndexTvUsername.setText(PublicUtils.userBean.getUserName());
//        //?????????????????????
//        if (uhfManager!=null){
//            uhfManager.close();
//            uhfManager=null;
//        }
//        if (uhfReader != null) {
//            uhfReader.close();
//            uhfReader = null;
//        }


        try {
            //uhfManager = UhfManager.getInstance();
//            uhfReader = UhfReader.getInstance(serialportPath, port);
            // ????????????
            //  uhfReader.setOutPower(Short.valueOf("3000"));
        } catch (Exception e) {
            PublicUtils.log("?????????????????????");
            //e.printStackTrace();
        } finally {
            PublicUtils.log("????????????");
            activityIndexImgSys.setVisibility(View.GONE);
//            if (uhfReader != null) {
//                //???????????????????????????????????????
//                //  activityIndexImgSys.setVisibility(View.VISIBLE);
//            } else {
//                activityIndexImgSys.setVisibility(View.GONE);
//            }
        }

    }

    private void init() {
        activityIndexMyTitleBar.initIndexTitle();
        mViewPager = (RollPagerView) findView(R.id.view_pager);
        mViewPager.setAdapter(new ImageLoopAdapter(mViewPager));
        mViewPager.setHintView(null);//???????????????
//        if(PublicUtils.userBean.getModules().size() <= 8){
//            indexRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        }else
        indexRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        indexRecyclerView.addItemDecoration(new RecyclerSpace(10, Color.TRANSPARENT));
        adapter = new BaseQuickAdapter<IndexItem, BaseViewHolder>(R.layout.indexitem2, null) {

            @Override
            protected void convert(BaseViewHolder helper, IndexItem item) {
                MyIndexItem itemView = (MyIndexItem) helper.itemView;
                String mdName = item.getMdName();
                String mdID = item.getMdID();
                itemView.setNametv(mdName);
                itemView.setNumbertv(item.getNoRead() + "");
                if ("daily".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m7);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, SgrzActivity.class));
                        }
                    });
                }else if ("cm".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m1);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, GTXTActivity.class));
                        }
                    });

                } else if ("Quality".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m2);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, ZljdActivity.class));
                        }
                    });
                } else if ("Civilization".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m4);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, WmsgActivity.class));
                        }
                    });
                } else if ("Safety".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m3);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, AqscActivity.class));
                        }
                    });

                } else if ("Notice1".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m6);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, SgtxActivity.class));
                        }
                    });
                } else if ("Notice2".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m5);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, TzggActivity.class));
                        }
                    });
                } else if ("ProAccept".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m8);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, GXYSActivity.class));
                        }
                    });
                } else if ("Examination".equals(mdID)) {//????????????
                    itemView.setImageView(R.mipmap.m10);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, ZxjcActivity.class));
                        }
                    });
                } else if ("ProjectMsg".equals(mdID)) {//????????????XmxxActivity
                    itemView.setImageView(R.mipmap.m9);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, XmxxActivity.class));
                        }
                    });
                } else if ("Statistics".equals(mdID)) {//????????????LdIndexActivity
                    itemView.setImageView(R.mipmap.m11);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_Menu.class));
//                            startActivity(new Intent(IndexActivity2.this, LdIndexActivity.class));
                        }
                    });
                } else if ("Progress".equals(mdID)) { //????????????ct_Tab
                    itemView.setImageView(R.mipmap.m12);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, JDSBLISTActivity.class));
                        }
                    });
                } else if ("MajorRisk".equals(mdID)) { //??????????????????WDPZLISTActivity
                    itemView.setImageView(R.mipmap.m13);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, WDPZLISTActivity.class));
                        }
                    });
                } else if ("Repository".equals(mdID)) { //????????? RepositoryActivity
                    itemView.setImageView(R.mipmap.m14);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))){
                                startActivity(new Intent(IndexActivity2.this, RepositoryActivity.class)
                                        .putExtra("url","http://106.15.92.195:9003").putExtra("title","?????????"));
                            }else {
                                startActivity(new Intent(IndexActivity2.this, RepositoryActivity.class)
                                        .putExtra("url","http://safetycore.telsafe.com.cn:8008").putExtra("title","?????????"));
                            }
                        }
                    });
                } else if ("KeyExamination".equals(mdID)) { //????????????
                    itemView.setImageView(R.mipmap.m15);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, WdjcActivity2.class));
                        }
                    });
                } else if ("DecisionScreen".equals(mdID)) { //????????????
                    itemView.setImageView(R.mipmap.m16);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_WebView.class).
                                    putExtra("url", "https://datav.aliyun.com/share/d4ff7153cc9ff03cec976173899bad75").putExtra("fromWhere", "a"));
                        }
                    });
                } else if ("TodayDecisionScreen".equals(mdID)) { //????????????
                    itemView.setImageView(R.mipmap.m17);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_WebView.class).
                                    putExtra("url", "https://datav.aliyun.com/share/4c06d21954b171523758693a710efb0b").putExtra("fromWhere", "z"));
                        }
                    });
                } else if ("EnforcementBook".equals(mdID)) { //?????????
                    itemView.setImageView(R.mipmap.m19);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_Jdzf.class));
                        }
                    });
                } else if ("Notice3".equals(mdID)) { //????????????
                    itemView.setImageView(R.mipmap.m21);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_Notice.class));
                        }
                    });
                } else if ("RiskReport".equals(mdID)) { //????????????
                    itemView.setImageView(R.mipmap.m22);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_RiskReport.class));
                        }
                    });
                } else if("ProgExamination".equals(mdID)){ //????????????
                    itemView.setImageView(R.mipmap.m23);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_Procedural.class));
                        }
                    });
                }else if ("ResumWork".equals(mdID)) { //"??????????????????
                    itemView.setImageView(R.mipmap.m24);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_YiQingCheck.class));
                        }
                    });
                }else if ("ProgressReport".equals(mdID)) { //????????????
                    itemView.setImageView(R.mipmap.m25);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, Act_ProgressReport.class));
                        }
                    });
                }else if ("SocialSupervise".equals(mdID)) { //???????????????
                    itemView.setImageView(R.mipmap.m26);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(IndexActivity2.this, WGImLActivity.class));
                        }
                    });
                }
            }
        };
//        View headerview = LayoutInflater.from(this).inflate(R.layout.index_header, null, false);
//        activityIndexTvDwname = (TextView) headerview.findViewById(R.id.activity_index_tv_dwname);
//        activityIndexTvUsername = (TextView) headerview.findViewById(R.id.activity_index_tv_username);
//        adapter.addHeaderView(headerview);

//        try{
//            if (PublicUtils.userBean.getModules() != null){
//                for(int i=0;i<PublicUtils.userBean.getModules().size();i++){
//                    if("Statistics_KeyFollow".equals(PublicUtils.userBean.getModules().get(i).getMdID())){
//                        PublicUtils.userBean.getModules().remove(i);
//                        i--;
//                    }
//                }
//            adapter.replaceData(PublicUtils.userBean.getModules());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            CloseActivityClass.exitClient();
//            System.exit(0);
//        }

        indexRecyclerView.setAdapter(adapter);
        // IndexItem data = new IndexItem();
        //   data.setMdName("????????????");
        //  adapter.addData(data);
        initWeather();
    }
    @Override
    public void initListener() {
        //??????????????????id ????????????
        //??????????????????id ????????????
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //??????????????????id ????????????
                if (msg.obj != null) {

                    onTagGetData(msg.obj.toString());


                }
            }
        };
        //
        setTagLoading = WeiboDialogUtils.createLoadingDialog(this, "???????????????????????????");
        setTagLoading.setCancelable(true);
        setTagLoading.setCanceledOnTouchOutside(true);
        setTagLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (readThread != null) {
                    readThread.finish = false;
                }
            }
        });
        mp = MediaPlayer.create(this, R.raw.ding);


        activityIndexImgSys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (readThread != null) {
//                    readThread.finish = false;
//                }
//                //??????????????????
//                readThread = new ReadThread();
//                //??????????????????
//                readThread.start();
//                setTagLoading.show();
            }
        });

    }

    /**
     * ??????id ??????????????????
     */
    void onTagGetData(String tagID) {
        if (TextUtils.isEmpty(tagID)) {
            PublicUtils.toast("??????ID?????????");
            return;
        }

        XUtil.postjsondata(JsonUtils.getjsonobj("{\"tagID\":\"" + tagID + "\"}"), "GetTag", new MyCallBack() {
            @Override
            public void onResule(String res) {
                if ("{}".equals(res)) {
                    PublicUtils.toast("?????????????????????????????????");
                    return;
                }
                TagBean tagBean = JsonUtils.getbean(res, TagBean.class);
                String tagData = tagBean.getTagData();
                if (TextUtils.isEmpty(tagBean.getTagID())) return;

                String apiName = tagBean.getApiName();
                Intent intent = new Intent();

                if ("GetSafetyMsgInitialize".equals(apiName)|| "GetSafetyMsgInitialize_QinTai".equals(apiName)) {//????????????
                    intent.setClass(IndexActivity2.this, AqscAddActivity.class);
                    intent.putExtra("tagid", tagBean.getTagID());
                    startActivity(intent);
                } else if ("GetCivilizedMsgInitialize".equals(apiName)||"GetCivilizedMsgInitialize_QinTai".equals(apiName) ) {//????????????
                    intent.setClass(IndexActivity2.this, WmsgAddActivity.class);
                    intent.putExtra("tagid", tagBean.getTagID());
                    startActivity(intent);
                } else if ("GetQualityMsgInitialize".equals(apiName)||"GetQualityMsgInitialize_QinTai".equals(apiName)) {//????????????
                    intent.setClass(IndexActivity2.this, ZljdAddActivity.class);
                    intent.putExtra("tagid", tagBean.getTagID());
                    startActivity(intent);
                }

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            /**
             * ????????????????????????
             */
            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(setTagLoading);
            }
        });
    }

    @Override
    public void initData() {
//        ShortcutBadger.applyCount(this, 0);
//        if (PublicUtils.userBean.getGraphStart()==1){
//            startActivity(new Intent(this,LdIndexActivity.class));
//        }
    }

    @Override
    public void processClick(View v) {

    }


    @OnClick({
            R.id.activity_index_img_sz
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_index_img_sz:
                startActivity(new Intent(IndexActivity2.this, SettingsActivity.class));
                break;


        }
    }


    public void getdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetTypeNum", new MyCallBack() {
            /**
             * code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                IndexBean indexBean = JsonUtils.getbean(res, IndexBean.class);
                int sum =  indexBean.getCoordinateNumber()+indexBean.getQualityNumber()+indexBean.getSafetyNumber()
                        +indexBean.getCivilizedNumber()+indexBean.getNoticeNumber()+indexBean.getJGNoticeNumber()
                        +indexBean.getAttentionNumber()+indexBean.getDailyLogNumber()+indexBean.getProjectNumber()
                        +indexBean.getProAcceptanceNmuber();

                getitem(adapter.getData(), "cm",indexBean.                          getCoordinateNumber());
                getitem(adapter.getData(), "Quality",indexBean.getQualityNumber());
                getitem(adapter.getData(), "Safety",indexBean.getSafetyNumber());
                getitem(adapter.getData(), "Civilization",indexBean.getCivilizedNumber());
                getitem(adapter.getData(), "Notice2",indexBean.getNoticeNumber());
                getitem(adapter.getData(), "Notice1",indexBean.getAttentionNumber());
                getitem(adapter.getData(), "Notice3",indexBean.getJGNoticeNumber());
                getitem(adapter.getData(), "daily",indexBean.getDailyLogNumber());
                getitem(adapter.getData(), "ProjectMsg",indexBean.getProjectNumber());
                getitem(adapter.getData(), "ProAccept",indexBean.getProAcceptanceNmuber());
                getitem(adapter.getData(), "EnforcementBook",indexBean.getLawSuperviseNumber()); //?????????
                adapter.notifyDataSetChanged();
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
                            Intent intent = new Intent(IndexActivity2.this, GtxtxqActivity.class);
                            intent.putExtra("msgID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetQualityMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity2.this, ZljdxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetSafetyMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity2.this, AqscxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetCivilizedMsgDetails")) {//????????????
                            Intent intent = new Intent(IndexActivity2.this, WmsgxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetNoticeMsgDetails")) {//???????????? ????????????
                            Intent intent = new Intent(IndexActivity2.this, TzggxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if (objMethod.equals("GetProcessAcceptanceDetails")) {
                            Intent intent = new Intent(IndexActivity2.this, GxysxqActivity.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        } else if(objMethod.equals("GetJGNoticeDetail")){  //????????????
                            Intent intent = new Intent(IndexActivity2.this, Act_Notice.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        }else if(objMethod.equals("Get_SuperviseDetail")){  //?????????
                            Intent intent = new Intent(IndexActivity2.this, Act_Jdzf.class);
                            intent.putExtra("recordID", obj.getMsgID());
                            startActivity(intent);
                        }

                    }
                });

            }

            /**
             * code ????????? 0??????
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * ??????????????????
             */
            @Override
            public void onWrong() {

            }
        });
    }

    public void getModule(){
        JSONObject jsonObject = new JSONObject();
        String username = PublicUtils.getspstring("user");
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
                if (PublicUtils.userBean.getModules() != null){
                    adapter.replaceData(PublicUtils.userBean.getModules());
                }

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            /**
             * ????????????????????????
             */
            @Override
            public void onFinished() {

            }
        });
        getdata();
    }
    private void getitem(List<IndexItem> items, String mdID, int number) {
        if (items == null) return;
        for (IndexItem item : items) {
            if (mdID.equals(item.getMdID())) {
                item.setNoRead(number);
            }
        }


    }

    /**
     * ????????????
     */
    @Override
    public void resume() {
//        if(PublicUtils.IS_DEBUG_MODE == false){
//            new UpDateAppUtils().Update(IndexActivity2.this);
//        }
        getModule();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                CloseActivityClass.exitClient();
                System.exit(0);
            } else {
                PublicUtils.toast("????????????????????????");
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * ????????????
     */
    @Override
    public void destroy() {
        LocationUtils.getInstance( this ).removeLocationUpdatesListener();
        if (activityIndexTvTzgg != null)
            activityIndexTvTzgg.removeCallbacks();
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
//        if (uhfManager != null) {
//                uhfManager.close();
//        }
        if (mp != null) {
            mp.release();//????????????
        }
    }


    class ReadThread extends Thread {
        //  private List<byte[]> epcList;
        private List<String> epcList;
        private boolean finish = true;

        @Override
        public void run() {
            super.run();
            while (finish) {
                PublicUtils.log("????????????");
//                    if (uhfManager == null) {
//                        Log.e("readuhf", "Util.manager==null");
//                        break;
//                    }
//                if (uhfReader == null) {
//                    Log.e("readuhf", "Util.manager==null");
//                    break;
//                }
//                try {
//                    //  epcList = uhfManager.inventoryRealTime(); // inventory real time
//                    epcList = uhfReader.inventoryTag();
//                } catch (NullPointerException e) {
////                        uhfManager.inventoryMulti();
//                    //PublicUtils.toast();
//                    Log.e("readuhf", "NullPointerException");
//                }

                if (epcList != null && !epcList.isEmpty()) {
                    // play sound
                    //Util.play(1,0);//
//                        mp.reset();
                    mp.start();
//                        byte[] bytes = epcList.get(0);
//                        String epcStr = Tools.Bytes2HexString(bytes,
//                                bytes.length);

                    Message msg = new Message();
                    //    msg.obj = epcStr;
                    msg.obj = epcList.get(0);
                    epcList = null;
                    finish = false;
                    PublicUtils.log("????????????");
                    if (handler != null)
                        handler.sendMessage(msg);

                }

            }

        }
    }
    private class ImageLoopAdapter extends LoopPagerAdapter {
        int[] imgs = new int[]{
                R.mipmap.nc1,
                R.mipmap.nc2,
                R.mipmap.nc3
        };

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
                    if(position == 0){
                        startActivity(new Intent(IndexActivity2.this,RepositoryActivity.class)
                                .putExtra("url","http://u11488944.viewer.maka.im/k/6S1GTE4AW11488944?t=1581570360199")
                        .putExtra("title","???????????? ????????????"));
                    }
                    if(position == 1){
                        startActivity(new Intent(IndexActivity2.this,RepositoryActivity.class)
                                .putExtra("url","http://u11488944.viewer.maka.im/k/XEF9ITAOW11488944?t=1581570605081")
                        .putExtra("title","????????????"));
                    }
                    if(position == 2){
                        startActivity(new Intent(IndexActivity2.this,RepositoryActivity.class)
                                .putExtra("url","http://u11488944.viewer.maka.im/k/PLEG02UUW11488944?t=1581570799732")
                        .putExtra("title","??????????????????????????????"));
                    }
                }
            });
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
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
    public void  initWeather(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{city:??????}"), "GetWeather", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try{
                    JSONObject object = new JSONObject(res);
                    String weather = object.optString("weather");
                    activityIndexMyTitleBar.setLeftTitlenametv(weather);
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
                WeiboDialogUtils.closeDialog(setTagLoading);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){//??????Code
            case OPEN_SET_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
//                            Toast.makeText(this,"?????????????????????", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    //????????????????????????
                } else {
//                    Toast.makeText(this,"?????????????????????",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
