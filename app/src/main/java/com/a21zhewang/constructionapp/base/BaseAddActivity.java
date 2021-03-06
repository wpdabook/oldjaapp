package com.a21zhewang.constructionapp.base;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.bean.AqscSpinnerBean;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.ProjectRoot;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.a21zhewang.constructionapp.bean.TagBean;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.SelectSGBWActivity;
import com.a21zhewang.constructionapp.ui.SelectSgqyActivity;
import com.a21zhewang.constructionapp.ui.SelectTypesActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.AddGtxtActivity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcProjectInfoActivity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcxqActivity2;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.AnimUtil;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.FileUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.SendImageUtil;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.forward.androids.utils.DateUtil;
import cn.hzw.graffiti.GraffitiActivity;

//import com.a21zhewang.epclibrary.ReadTestActivity;
//import com.a21zhewang.epclibrary.Util;
//import com.handheld.UHF.UhfManager;
//
// import cn.pda.serialport.Tools;

public abstract class BaseAddActivity extends BaseActivity {

    @BindView(R.id.activity_base_add_MyTitleBar)
    MyTitleBar activityBaseAddMyTitleBar;
    @BindView(R.id.activity_base_add_TextView_pjname)
    TextView activityBaseAddMyTextViewPjname;

    @BindView(R.id.activity_base_add_EditText_lc)
    EditText activityBaseAddEditTextlc;
    @BindView(R.id.activity_base_add_MySpinner_lc)
    MySpinner activityBaseAddMySpinnerLc;
    @BindView(R.id.activity_base_add_MySpinner_bg)
    MySpinner activity_base_add_MySpinner_bg;
    @BindView(R.id.activity_base_add_MySpinner_sgqy)
    MySpinner activity_base_add_MySpinner_sgqy;
    @BindView(R.id.activity_base_add_MySpinner_zgdw)
    MySpinner activityBaseAddMySpinnerZgdw;
    @BindView(R.id.activity_base_add_CanyurenView_zgdw)
    CanyurenView activityBaseAddCanyurenViewZgdw;
    @BindView(R.id.activity_base_add_EditText_bg)
    EditText activityBaseAddEditTextBg;
    @BindView(R.id.activity_base_add_TextView_submit)
    TextView activityBaseAddTextViewSubmit;
    @BindView(R.id.activity_base_add_TextView_writetag)
    TextView activity_base_add_TextView_writetag;
    @BindView(R.id.activity_base_add_TextView_sgqy)
    TextView activityBaseAddTextViewSgqy;
    @BindView(R.id.activity_base_add_TextView_jcxm)
    TextView activityBaseAddTextViewJcxm;
    @BindView(R.id.activity_base_add_EditText_scgx)
    TextView activity_base_add_EditText_scgx;
    @BindView(R.id.li_submit)
    LinearLayout li_submit;
    @BindView(R.id.spinner_lc)
    MySpinner spinner_lc;
    @BindView(R.id.spinner_wz)
    MySpinner spinner_wz;
    @BindView(R.id.ed_lc1)
    EditText ed_lc1;
    @BindView(R.id.ed_lc2)
    EditText ed_lc2;
    @BindView(R.id.ed_wz)
    EditText ed_wz;
    @BindView(R.id.lin_lc)
    LinearLayout lin_lc;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private List<Etp> etpInfoList;
    private NamesSpinnerAdpater<Etp> etpInfoListBeanNamesSpinnerAdpater;
    private MySpinner.MySpinnerAPI zgdwonClickListener;
    private List<User> userInfoList;
    private CanyurenView.CanyurenViewAPI<User> canyurenViewAPI;
    private Etp etpBean;
    private ProjectArea areaBean;
    private Project pjbean;
    private Dialog loadingDialog;
    private Dialog setTagLoading;
    private ReadThread readThread;
    private Handler handler;
    private int music;
    private MediaPlayer mp;

    private String tagID = null;
    private PictureSelectionModel pictureSelectionModel;


    @BindView(R.id.activity_base_add_MyTimeSpinner_zgjz)
    TimeTextView activityBaseAddMyTimeSpinnerZgjz;
    @BindView(R.id.activity_base_add_EditText_nrms)
    EditText activityBaseAddEditTextNrms;
    @BindView(R.id.activity_base_add_RecyclerView_imgs)
    RecyclerView activityBaseAddRecyclerViewImgs;
    @BindView(R.id.activity_base_add_ImageView_addimg)
    ImageView activityBaseAddImageViewAddimg;
    @BindView(R.id.activity_base_add_ImageView_Help)
    TextView activityBaseAddImageViewHelp;
    @BindView(R.id.activity_base_add_search)
    TextView base_add_search;
    private StringBuffer sb;


    private ImageOptions imageOptions;
    private List<LocalMedia> imglist;
    private int selectImageIndex;
    private CommonAdapter<LocalMedia> commonAdapter;
    private String[] strs, strs2, bg, bg2;

    // private ReadTestActivity.ReadThread readThread;
    // private UhfManager uhfManager;
    private boolean isread;


    //??????????????????
//    private UhfReader uhfReader;
    //???????????????
    private String serialportPath = "/dev/ttyMT1";
    private int port = 13;
    private List<ProjectArea> projectAreaList;//????????????
    private List<MsgType> msgTypes;
    private String ids;
    private List<SubProjectBean> subProjectList;
    private SubProjectBean subProjectListBean;
    // private NamesSpinnerAdpater<SubProjectBean> subProjectListBeanNamesSpinnerAdpater;
    private List<MsgType> typesBeanList;
    private String subids;

    private PopupWindow mPopupWindow;
    private TextView tv_1;
    private AnimUtil animUtil;
    private float bgAlpha = 1f;
    private boolean bright = false;
    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;
    private int position = 0;
    private int selectTag = 0;
    private String projectName = "";
    private String xPoint,yPoint;
    private String StrProjectName;

    /**
     * @return ?????????????????????
     */
    public abstract String setActivityBaseAddMyTitleBar();

    /**
     * @return ??????????????????
     */
    public abstract String setgetdatamethod();

    /**
     * @return ??????????????????????????????
     */
    public abstract String setgetinitdatamethod();

    /**
     * ???????????????
     */
    public abstract void init();

    /**
     * setContentView????????????
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return ??????????????????
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_base_add;
    }

    /**
     * ?????????rfid??????
     */

    private void initRFID() {
//        try {
//            uhfReader = UhfReader.getInstance(serialportPath, port);
//            // ????????????
//            uhfReader.setOutPower(Short.valueOf("3000"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        getLocation();
        Bundle extras = getIntent().getExtras();
        mPopupWindow = new PopupWindow(this);
        mPopupWindow.setContentView(LayoutInflater.from(BaseAddActivity.this).inflate(R.layout.pop_add, null));
        tv_1 = (TextView) mPopupWindow.getContentView().findViewById(R.id.tv_1);
        tv_1.setOnClickListener(this);
        animUtil = new AnimUtil();
        if (extras != null)
            tagID = extras.getString("tagid");

        activityBaseAddMyTitleBar.setTitlenametv(setActivityBaseAddMyTitleBar());

        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "????????????");
        setTagLoading = WeiboDialogUtils.createLoadingDialog(this, "???????????????????????????");
        setTagLoading.setCancelable(true);
        setTagLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (readThread != null)
                    readThread.start = false;
            }
        });


        try {

            //   uhfManager = UhfManager.getInstance();

//            uhfReader = UhfReader.getInstance(serialportPath, port);
            // ????????????
//            uhfReader.setOutPower(Short.valueOf("3000"));
        } catch (Exception e) {

        } finally {
            //  if (uhfManager != null) {
//            if (uhfReader != null) {
                //???????????????????????????????????????
                //??????????????????
                readThread = new ReadThread();
                //??????????????????
                readThread.start();
                activity_base_add_TextView_writetag.setVisibility(View.GONE);
                //  activityBaseAddMyTitleBar.setTitle_rightimgSrc(R.mipmap.saoyisao);
//                activityBaseAddMyTitleBar.setTitle_rightimgOnClick(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        isread = true;
//                        if (readThread == null)
//                            return;
//                        //????????????
//                        if (readThread.start) {
//                            readThread.start = false;
//                        } else {
//                            readThread.start = true;
//                            setTagLoading.show();
//                        }
//                    }
//                });
//            }
        }
        // Util.initSoundPool(getApplicationContext());
        mp = MediaPlayer.create(this, R.raw.ding);//
        //???????????????????????????res/raw?????????2?????????????????????????????????3????????????????????????


        //??????????????????id ????????????
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //??????????????????id ????????????
                if (msg.obj != null) {

                    if (isread) {
                        onTagGetData(msg.obj.toString());
                    } else {
                        writeTag(msg.obj.toString());
                    }

                }
            }
        };


        //????????????
        etpInfoList = new ArrayList<>();
        etpInfoListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<Etp>(this, etpInfoList) {
            @Override
            public void setinit(Etp name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getEtpShortName());
            }
        };
        canyurenViewAPI = new CanyurenView.CanyurenViewAPI<User>() {
            @Override
            public void setstr(int size, TextView textView, Set ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName());
                } else if (size == 2) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName() + "," + userInfoList.get(integers.get(1)).getUserName());
                } else if (size > 2) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName() + "," + userInfoList.get(integers.get(1)).getUserName() + "...");
                }
            }

            @Override
            public String getstr(int i, User o) {
                return o.getUserName();
            }
        };

        activityBaseAddMySpinnerZgdw.setmyspinnerlistadpater(etpInfoListBeanNamesSpinnerAdpater);
        zgdwonClickListener = new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {

                etpBean = etpInfoList.get(position);
                userInfoList = etpBean.getUserInfoList();
                //???????????????
                activityBaseAddCanyurenViewZgdw.setList(userInfoList, canyurenViewAPI);

                return etpBean.getEtpShortName();
            }
        };
        activityBaseAddMySpinnerZgdw.setlistviewitemonclick(zgdwonClickListener);

        activityBaseAddCanyurenViewZgdw.setCanyurenViewAPI(canyurenViewAPI);

        //????????????
        strs = new String[]{"F", "B"};
        strs2 = new String[]{"DK", "K"};
        bg = new String[]{"???"};
        bg2 = new String[]{"???", "???", "???", "#???", "???", "#???","???","???"};


        subProjectList = new ArrayList<>();
        // spinner_lc.setPopWidth(100);
        spinner_lc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, BaseAddActivity.this.strs2) {
            @Override
            public void setinit(String name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name);
            }
        });
        spinner_lc.setTextViewtext(strs2[0]);
        spinner_lc.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                Log.d("?????????","position");
                return BaseAddActivity.this.strs2[position];
            }
        });
        // spinner_wz.setPopWidth(100);
        spinner_wz.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg2) {
            @Override
            public void setinit(String name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name);
            }
        });
        spinner_wz.setTextViewtext(bg2[0]);
        spinner_wz.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {

                return bg2[position];
            }
        });
        //??????????????????
//        subProjectListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<SubProjectBean>(BaseAddActivity.this, subProjectList) {
//            @Override
//            public void setinit(SubProjectBean name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getSubProjectName());
//            }
//        };
        // activity_base_add_MySpinner_sgqy.setmyspinnerlistadpater(subProjectListBeanNamesSpinnerAdpater);
//        AdapterView.OnItemClickListener scgxonClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                subProjectListBean = subProjectList.get(position);
//                String subProjectName = subProjectListBean.getSubProjectName();
//              //  getcsdw(subProjectListBean.getSubProjectID());
//
//                activity_base_add_MySpinner_sgqy.setTextViewtext(subProjectName);
//                activity_base_add_MySpinner_sgqy.popdismiss();
//            }
//        };
        // activity_base_add_MySpinner_sgqy.setlistviewitemonclick(scgxonClickListener);

        init();
        activityBaseAddCanyurenViewZgdw.setMaxSelect(1);
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {
        activityBaseAddTextViewSgqy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectAreaList != null) {
                    Intent intent = new Intent(BaseAddActivity.this, SelectSgqyActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectAreaList);
                    startActivityForResult(intent, SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ);
                } else {
                    PublicUtils.toast("???????????????");
                }
            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {
        initselectorimg();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"getDataType\":\"project\"}"), setgetdatamethod(), new MyCallBack() {
            @Override
            public void onResule(String res) {
                ProjectRoot baseAddinitBean = JsonUtils.getbean(res, ProjectRoot.class);
                final List<Project> pjList = baseAddinitBean.getProjectList();
                if (pjList != null && pjList.size() > 0) {
                    activityBaseAddMyTextViewPjname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BaseAddActivity.this, SelectProjectActivity.class);

                            //    Bundle bundle = new Bundle();
                            //       bundle.putParcelableArrayList("list",(ArrayList<? extends Parcelable>) pjList);
                            //      intent.putExtra("list", bundle);
                            SelectProjectActivity.parcelableArrayListExtra = (ArrayList<Project>) pjList;
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
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
        });

    }

    private void getprojectArea(final String what, String proid) {
        String obj = "";
        if ("area".equals(what)) {
            obj = "{\"getDataType\":\"" + what + "\",\"projectID\":\"" + proid + "\"}";
        } else if ("subProject".equals(what)) {
            obj = "{\"getDataType\":\"" + what + "\"," + "\"projectID\":\"" + pjbean.getProjectID() + "\"," + "\"projectAreaID\":\"" + proid + "\"}";
        } else if ("etp".equals(what)) {
            obj = "{\"getDataType\":\"" + what + "\"," + "\"projectID\":\"" + pjbean.getProjectID() + "\"," + "\"projectAreaID\":\"" + proid + "\"}";
        }
        JSONObject getjsonobj = JsonUtils.getjsonobj(obj);
        XUtil.postjsondata(getjsonobj, setgetdatamethod(), new MyCallBack() {

            @Override
            public void onResule(String res) {
                if ("area".equals(what)) {
                    Project baseAddinitBean = JsonUtils.getbean(res, Project.class);
                    projectAreaList = baseAddinitBean.getProjectAreaList();
                } else if ("subProject".equals(what)) {
                    ProjectArea getbean = JsonUtils.getbean(res, ProjectArea.class);
                    final List<SubProjectBean> subProjectList = getbean.getSubProjectList();
                    if (subProjectList != null && subProjectList.size() > 0 && setpostmethod().equals("AddQualityMsg") && !"??????".equals(strs) && !"?????????".equals(strs)) {
                        //subProjectListBeanNamesSpinnerAdpater.getNames().addAll(subProjectList);
                        activity_base_add_EditText_scgx.setVisibility(View.INVISIBLE);
                        activity_base_add_MySpinner_sgqy.setVisibility(View.VISIBLE);
                        // subProjectListBeanNamesSpinnerAdpater.replcedata(subProjectList);
                        //subProjectListBeanNamesSpinnerAdpater.notifyDataSetChanged();
                        //subProjectListBean = subProjectList.get(0);
                        //   String subProjectName = subProjectListBean.getSubProjectName();
                        //  getcsdw(subProjectListBean.getSubProjectID());
                        activity_base_add_MySpinner_sgqy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BaseAddActivity.this, SelectSGBWActivity.class);
                                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) subProjectList);
                                startActivityForResult(intent, SelectSGBWActivity.RESQUST_CODE_SELECTSGBW);
                            }
                        });
                        activity_base_add_MySpinner_sgqy.setTextViewtext("????????????");
                        //activity_base_add_MySpinner_sgqy.popdismiss();
                    } else if (subProjectList != null && subProjectList.size() > 0 && setpostmethod().equals("AddQualityMsg") && ("??????".equals(strs) || "?????????".equals(strs))) {
                        //subProjectListBeanNamesSpinnerAdpater.getNames().addAll(subProjectList);
                        activity_base_add_MySpinner_sgqy.setVisibility(View.INVISIBLE);
                        activity_base_add_EditText_scgx.setVisibility(View.VISIBLE);
                        //   subProjectListBeanNamesSpinnerAdpater.replcedata(subProjectList);
                        //   subProjectListBeanNamesSpinnerAdpater.notifyDataSetChanged();
                        subProjectListBean = subProjectList.get(0);
                        String subProjectName = subProjectListBean.getSubProjectName();
                        //  getcsdw(subProjectListBean.getSubProjectID());

                        activity_base_add_MySpinner_sgqy.setTextViewtext(subProjectName);
                        activity_base_add_MySpinner_sgqy.popdismiss();
                    } else {
                        activity_base_add_MySpinner_sgqy.setVisibility(View.GONE);

                    }
                } else if ("etp".equals(what)) {
                    ProjectArea getbean = JsonUtils.getbean(res, ProjectArea.class);
                    List<Etp> etpList = getbean.getEtpInfoList();
                    etpInfoListBeanNamesSpinnerAdpater.replcedata(etpList);
                    if (etpList != null && etpList.size() > 0)
                        activityBaseAddMySpinnerZgdw.setTextViewtext(zgdwonClickListener.onclick(0));
                    else {
                        activityBaseAddMySpinnerZgdw.setTextViewtext("????????????");
                        etpBean = null;
                    }
                }


            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }

    private void initselectorimg() {
        imglist = new ArrayList<>();
        //????????????????????????
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityBaseAddRecyclerViewImgs.setLayoutManager(layoutManager);
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
        commonAdapter = new CommonAdapter<LocalMedia>(this, R.layout.faqigtxt_imglist_item, imglist) {

            @Override
            protected void convert(ViewHolder holder, final LocalMedia s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);

                x.image().bind(imageView, s.getCompressPath(), imageOptions);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex = position;
                        GraffitiActivity.startActivityForResult(BaseAddActivity.this, s.getCompressPath());
                    }
                });
                holder.setVisible(R.id.faqigtxt_imglist_item_img_del, true);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imglist.remove(position);
                        commonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        activityBaseAddRecyclerViewImgs.setAdapter(commonAdapter);
    }

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_base_add_TextView_submit,

            R.id.activity_base_add_ImageView_addimg,

            R.id.activity_base_add_TextView_writetag,

            R.id.activity_base_add_ImageView_Help,

            R.id.activity_base_add_search
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_base_add_TextView_submit:
                postdata();
                break;
//            case R.id.activity_base_add_TextView_cancel:
//                finish();
//                break;
            case R.id.activity_base_add_ImageView_addimg:
//                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                if(selectTag == 0){
                    initAlert();
                }
                if(selectTag == 1){
                    PictureSelector();
                }
                if(selectTag == 2){
                    PictureCarmerSelector();
                }
//            case R.id.activity_base_add_TextView_readtag:
//                isread = true;
//                if (readThread == null)
//                    break;
//                //????????????
//                if (readThread.start) {
//                    readThread.start = false;
//                } else {
//                    readThread.start = true;
//                    setTagLoading.show();
//                }
                break;
            case R.id.activity_base_add_TextView_writetag:
                isread = false;
                if (!judgement())
                    break;
                if (readThread == null)
                    break;
                //????????????
                if (readThread.start) {
                    readThread.start = false;
                } else {
                    readThread.start = true;
                    setTagLoading.show();
                }
                break;
            case R.id.activity_base_add_ImageView_Help:
                if (msgTypes == null) {
                    PublicUtils.toast("?????????????????????");
                    return;
                }
                showPop();
                toggleBright();
                break;
            case R.id.activity_base_add_search:
            //case R.id.activity_base_add_TextView_jcxm:
                if(pjbean == null){
                    PublicUtils.toast("???????????????");
                    return;
                }
                if("????????????????????????".equals(setActivityBaseAddMyTitleBar())){
                    startActivityForResult(new Intent(BaseAddActivity.this,BaseAddSearchActivity.class)
                            .putExtra("method","GetSafetyMsgTypeSearch").putExtra("projectID",pjbean.getProjectID()),BaseAddSearchActivity.RESQUST_CODE_SELECT_TYPE);
                }else if("????????????????????????".equals(setActivityBaseAddMyTitleBar())){
                    startActivityForResult(new Intent(BaseAddActivity.this,BaseAddSearchActivity.class)
                            .putExtra("method","GetCivilizedMsgTypeSearch").putExtra("projectID",pjbean.getProjectID()),BaseAddSearchActivity.RESQUST_CODE_SELECT_TYPE);
                }else { //????????????????????????
                    startActivityForResult(new Intent(BaseAddActivity.this,BaseAddSearchActivity.class)
                            .putExtra("method","GetQualityMsgTypeSearch").putExtra("projectID",pjbean.getProjectID()),BaseAddSearchActivity.RESQUST_CODE_SELECT_TYPE);
                }
                break;

        }
    }
    public void initAlert(){
        new ActionSheetDialog(BaseAddActivity.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("??????", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                PictureSelector();
                            }
                        })
                .addSheetItem("??????", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                PictureCarmerSelector();
                            }
                        }).show();
    }
    /**
     * ????????????
     */
    public void PictureSelector(){
         PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// ???????????????????????? int
                .imageSpanCount(4)// ?????????????????? int
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// ????????????????????? true or false
                .previewVideo(false)// ????????????????????? true or false
                .enablePreviewAudio(false) // ????????????????????? true or false
                //.compressGrade(Luban.FIRST_GEAR)// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
                .isCamera(true)// ???????????????????????? true or false
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .enableCrop(false)// ???????????? true or false
                .compress(true)// ???????????? true or false
                // .compressMode(PictureConfig.)//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// ????????????gif?????? true or false
                .showCropFrame(false)// ?????????????????????????????? ???????????????????????????false   true or false
                .showCropGrid(false)// ?????????????????????????????? ???????????????????????????false    true or false
                .openClickSound(false)// ???????????????????????? true or false
                .selectionMedia(imglist)// ???????????????????????? List<LocalMedia> list
                .previewEggs(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
    /**
     * ????????????
     */
    public void PictureCarmerSelector(){
        PictureSelector.create(this).openCamera(PictureMimeType.ofImage())
                .maxSelectNum(9)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .previewVideo(false)
                .enablePreviewAudio(false)
                .isCamera(false)
                .isZoomAnim(true)
                .sizeMultiplier(0.5f)
                .enableCrop(false)
                .compress(true)
                .isGif(false)
                .showCropFrame(false)
                .showCropGrid(false)
                .openClickSound(false)
                .selectionMedia(imglist)
                .previewEggs(true)
                .compress(true).forResult(PictureConfig.CAMERA);
    }
    /**
     * ????????????????????????
     */
    private void getspinnerdata(String projectID) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectID\":\"" + projectID + "\"}"), setgetinitdatamethod(), new MyCallBack() {
            @Override
            public void onResule(String res) {
                AqscSpinnerBean spinnerBean = JsonUtils.getbean(res, AqscSpinnerBean.class);
                typesBeanList = spinnerBean.getMsgTypes();
                if (typesBeanList != null && typesBeanList.size() > 0) {
                    activityBaseAddTextViewJcxm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BaseAddActivity.this, SelectTypesActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) typesBeanList);
                            startActivityForResult(intent, SelectTypesActivity.RESQUST_CODE_SELECTTYPE);
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

                if (!TextUtils.isEmpty(tagID)) {
                    onTagGetData(tagID);
                }
            }
        });
    }

    public void postdata() {
        sb = new StringBuffer();
        if (pjbean == null) {
            PublicUtils.toast("??????????????????");
            return;
        }
        if (areaBean == null) {
            PublicUtils.toast("????????????????????????");
            return;
        }
        if (activity_base_add_MySpinner_sgqy.getTextViewtext().equals("????????????") && activity_base_add_MySpinner_sgqy.getVisibility() == View.VISIBLE) {
            PublicUtils.toast("????????????????????????");
            return;
        }
        if (activity_base_add_EditText_scgx.getText().toString().trim().length() == 0 && activity_base_add_EditText_scgx.getVisibility() == View.VISIBLE) {
            PublicUtils.toast("????????????????????????");
            return;
        }
        String lc_str = activityBaseAddEditTextlc.getText().toString();
        String bg_str = activityBaseAddEditTextBg.getText().toString();
        String lc1 = ed_lc1.getText().toString().trim();
        String lc2 = ed_lc2.getText().toString().trim();
        String wz = ed_wz.getText().toString().trim();
        boolean b = lin_lc.getVisibility() == View.VISIBLE;
        if (b) {
            if (TextUtils.isEmpty(lc_str.trim()) && bg_str.length() == 0) {
                PublicUtils.toast("???????????????????????????");
                return;
            }
        } else {
            if (TextUtils.isEmpty(wz) && (TextUtils.isEmpty(lc1) || TextUtils.isEmpty(lc2))) {
                PublicUtils.toast("???????????????????????????????????????");
                return;
            }
        }


        if (msgTypes == null) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        String value = activityBaseAddEditTextNrms.getText().toString();
        if (TextUtils.isEmpty(value)) {
            //   PublicUtils.toast("????????????????????????");
            // return;
            value = "";
        }
        if (activityBaseAddMySpinnerZgdw.getTextViewtext().equals("????????????")) {
            PublicUtils.toast("????????????????????????");
            return;
        }
        if (activityBaseAddCanyurenViewZgdw.getTextstr().equals("???????????????")) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        List<String> imgs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = JsonUtils.List2jsonArray(msgTypes);
            jsonObject.put("msgTypes", array);

            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
            if (b){
                if (!TextUtils.isEmpty(bg_str)) {
                    jsonObject.put("elevation", bg_str + activity_base_add_MySpinner_bg.getTextViewtext());
                }

                if (!TextUtils.isEmpty(lc_str.trim()))
                    jsonObject.put("floor", lc_str + activityBaseAddMySpinnerLc.getTextViewtext());
            }else{
                if (!TextUtils.isEmpty(wz)) {
                    jsonObject.put("elevation", wz + spinner_wz.getTextViewtext());
                }

                if (!TextUtils.isEmpty(lc1)&&!TextUtils.isEmpty(lc2))
                    jsonObject.put("floor", spinner_lc.getTextViewtext() +lc1+"+"+lc2);
            }

            //  jsonObject.put("typeGraFaID", msgTypesBean.getTypeID());
            jsonObject.put("createUserType", PublicUtils.userBean.getUserType());
            jsonObject.put("recEtpShortName", etpBean.getEtpShortName());
            jsonObject.put("cutoffTime", activityBaseAddMyTimeSpinnerZgjz.getText());
            jsonObject.put("createUserTypeID", PublicUtils.userBean.getUserTypeID());
            if (setfxdjwhat()) {
                // jsonObject.put("level", msgsTypesBean.getLevel());
            } else {
                //  jsonObject.put("level", typesBean.getLevel());
            }
            // jsonObject.put("projectID", pjbean.getProjectID());
            jsonObject.put("projectShortName", pjbean.getProjectShortName());
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());

            jsonObject.put("content", value);
            jsonObject.put("recEtpID", etpBean.getEtpID());

            List<User> getlists = activityBaseAddCanyurenViewZgdw.getlists();
            JSONArray getjsonobjs = JsonUtils.getjsonobjs(getlists);
            jsonObject.put("receivers", getjsonobjs);
            jsonObject.put("projectAreaName", activityBaseAddTextViewSgqy.getText().toString());
            jsonObject.put("projectAreaID", ids);

            if (activity_base_add_EditText_scgx.getVisibility() == View.VISIBLE) {
                jsonObject.put("subProjectID", subProjectListBean.getSubProjectID());
                jsonObject.put("subProjectName", activity_base_add_EditText_scgx.getText().toString().trim());
            } else if (activity_base_add_MySpinner_sgqy.getVisibility() == View.VISIBLE) {
                jsonObject.put("subProjectID", subids);
                jsonObject.put("subProjectName", activity_base_add_MySpinner_sgqy.getTextViewtext());
            }

            jsonObject.put("projectID", pjbean.getProjectID());
            JSONArray imgfiles = new JSONArray();
//            for (int i = 0; i < imglist.size(); i++) {
//                LocalMedia localMedia = imglist.get(i);
//                JSONObject imgfile = new JSONObject();
//                String path = localMedia.getCompressPath();
//                imgs.add(path);
//                String fileName = path.substring(path.lastIndexOf("/") + 1);
//                imgfile.put("fileName", fileName);
//                imgfile.put("fileBrief", "???" + (i + 1) + "?????????");
//                imgfiles.put(imgfile);
//            }
            List<String> imgCreateTimeList = new ArrayList<>();
            for (int i = 0; i < imglist.size(); i++) {
                LocalMedia localMedia = imglist.get(i);
                String path = localMedia.getPath();
                ExifInterface exifInterface = null;
                try {
                    exifInterface = new ExifInterface(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String watermarkTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
                if(TextUtils.isEmpty(watermarkTime)){ //?????????????????????????????????,?????????????????????????????????File????????????????????????
//                    File file = new File(path);
//                    long modifieTime = file.lastModified();
//                    watermarkTime = DateUtils.formatDate(modifieTime);
                    watermarkTime = DateUtils.getTodayDate1Long2();
                }
                imgCreateTimeList.add(watermarkTime);
            }
            /**????????????????????????????????????????????????*/
            for(int j=0;j<imgCreateTimeList.size();j++){
                int index = j+1;
                JSONObject imgfile = new JSONObject();
                String history_time = imgCreateTimeList.get(j).substring(0,10);
                String today_time = DateUtils.getTodayDate2();
                if(!history_time.equals(today_time)){
                    sb.append(index + "???");
                }else {
                    String compressPath = imglist.get(j).getCompressPath();
                    if(selectTag == 1){
                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                            Bitmap bitmap = SendImageUtil.createWatermark(BaseAddActivity.this,
                                    compressPath,"???????????????"+StrProjectName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,compressPath);
                        }else {
                            Bitmap bitmap = SendImageUtil.createWatermark(BaseAddActivity.this,
                                    compressPath,"???????????????"+StrProjectName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,compressPath);
                        }
                    }
                    if(selectTag == 2){
                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                            Bitmap bitmap = SendImageUtil.createWatermark(BaseAddActivity.this,
                                    compressPath,"???????????????"+StrProjectName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,compressPath);
                        }else {
                            Bitmap bitmap = SendImageUtil.createWatermark(BaseAddActivity.this,
                                    compressPath,"???????????????"+StrProjectName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,compressPath);
                        }
                    }
                    imgs.add(compressPath);
                    String fileName = compressPath.substring(compressPath.lastIndexOf("/") + 1);
                    imgfile.put("fileName", fileName);
                    imgfile.put("fileBrief", "???" + (j + 1) + "?????????");
                    imgfiles.put(imgfile);
                }
            }
            /**????????????????????????????????????????????????*/
            if(sb != null && sb.length()>0){
                Toast.makeText(BaseAddActivity.this,"???"+sb.deleteCharAt(sb.length() - 1).toString()+"??????????????????????????????",Toast.LENGTH_SHORT).show();
                return;
            }else {
                jsonObject.put("files", imgfiles);
                jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
                // jsonObject.put("typeGraFaName", msgTypesBean.getTypeName());
                PublicUtils.log(jsonObject.toString());
            }
        } catch (JSONException e) {
            PublicUtils.toast("???????????????????????????");
            return;
        }
        loadingDialog.show();

        XUtil.updatas(jsonObject, imgs, setpostmethod(), new MyCallBack() {
            /**
             * code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                li_submit.setVisibility(View.GONE);
                finish();
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

            /**
             * ????????????????????????
             */
            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });

    }

    /**
     * @return ??????postdata????????????
     */
    protected abstract String setpostmethod();

    /**
     * ???????????????????????????????????? true ???????????? false ?????????
     */
    public abstract boolean setfxdjwhat();


    /**
     * Inventory EPC Thread
     */
    class ReadThread extends Thread {
        // private List<byte[]> epcList;
        private List<String> epcList;
        private boolean start;
        private boolean finish = true;

        @Override
        public void run() {
            super.run();
            while (finish) {
                if (start) {
                    //   if (uhfManager == null) {
//                    if (uhfReader == null) {
//                        Log.e("readuhf", "Util.manager==null");
//                        break;
//                    }
//                    try {
//                        //  epcList = uhfManager.inventoryRealTime(); // inventory real time
//                        epcList = uhfReader.inventoryTag();
//                    } catch (NullPointerException e) {
//                        //PublicUtils.toast();
//                    }


                    if (epcList != null && !epcList.isEmpty()) {
                        // play sound
                        //Util.play(1,0);//
//                        mp.reset();
                        mp.start();
                        //   byte[] bytes = epcList.get(0);
//                        String epcStr = Tools.Bytes2HexString(bytes,
//                                bytes.length);

                        Message msg = new Message();
                        msg.obj = epcList.get(0);

                        epcList = null;
                        start = false;

                        handler.sendMessage(msg);

                    }
                }

            }

        }
    }

    void writeTag(String tagID) {

        if (TextUtils.isEmpty(tagID)) {
            PublicUtils.toast("??????ID?????????");
            return;
        }

        setTagLoading.setCancelable(false);
        setTagLoading.show();
        TagBean tagBean = new TagBean();
        tagBean.setApiName(setgetdatamethod());
        tagBean.setTagDisc(setActivityBaseAddMyTitleBar() + "?????????????????????");
        tagBean.setTagID(tagID);
        tagBean.setTagType("RFID");
        JSONObject jsonObject = new JSONObject();
        JSONObject upjsonobj = null;
        try {

            //   jsonObject.put("projectID", pjbean.getProjectID());
            jsonObject.put("projectAreaName", areaBean.getProjectAreaName());
            jsonObject.put("floor", activityBaseAddEditTextlc.getText().toString().trim() + activityBaseAddMySpinnerLc.getTextViewtext());
            jsonObject.put("elevation", activityBaseAddEditTextBg.getText().toString().trim());
            //  jsonObject.put("TypeOne", msgTypesBean.getTypeID());
            //  jsonObject.put("TypeTwo", typesBean.getTypeID());

            //jsonObject.put("TypeThree", setfxdjwhat() ? msgsTypesBean.getTypeID() : "");
            tagBean.setTagData(jsonObject.toString());
            upjsonobj = new JSONObject(JsonUtils.objtojson(tagBean));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if ("GetSafetyMsgType".equals(setgetinitdatamethod())) {

        }


        XUtil.postjsondata(upjsonobj, "SetTag", new MyCallBack() {

            /**
             * ????????????code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {

            }

            /**
             * ????????????code ????????? 0??????
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

            /**
             * ????????????????????????
             */
            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(setTagLoading);
            }
        });
    }

    private boolean judgement() {
//        if (pjbean == null) {
//            PublicUtils.toast("???????????????");
//            return false;
//        } else if (areaBean == null) {
//            PublicUtils.toast("?????????????????????");
//            return false;
//        }
        String lc_str = activityBaseAddEditTextlc.getText().toString();
        String bg_str = activityBaseAddEditTextBg.getText().toString();
        if (TextUtils.isEmpty(lc_str.trim()) && bg_str.length() == 0) {
            PublicUtils.toast("???????????????????????????");
            return false;
        }
        if (!PublicUtils.isMobileNO(bg_str) && TextUtils.isEmpty(lc_str.trim())) {
            PublicUtils.toast("?????????????????????");
            return false;
        }
        //if (msgTypesBean == null) {
        //   PublicUtils.toast("?????????????????????");
        //   return false;
        // }//typesBean
        // if (typesBean == null) {
        // PublicUtils.toast("?????????????????????");
        //   return false;
        // }//msgsTypesBean
        //  if (msgsTypesBean == null && setfxdjwhat()) {
        // PublicUtils.toast("?????????????????????");
        //   return false;
        //  }
        return true;
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
                try {
                    JSONObject jsonObject = new JSONObject(tagData);
                    String projectID = jsonObject.getString("projectID");
                    //   if (!TextUtils.isEmpty(projectID) && null != projectListBeen) {
//                        for (int i = 0; i < projectListBeen.size(); i++) {
//                            if (projectListBeen.get(i).getProjectID().equals(projectID)) {
//                               // activityBaseAddMySpinnerPjname.setTextViewtext(pjspinneronClickListener.onclick(i));
//                                setsgqy(jsonObject.getString("projectAreaName"));
//                                break;
//                            } else {
//                                // PublicUtils.toast("????????????????????????????????????????????????");
//
//                           }
                    //  }
                    //  }
                    String floor = jsonObject.getString("floor");
                    if (!TextUtils.isEmpty(floor) && floor.contains("B")) {
                        activityBaseAddEditTextlc.setText(floor.replace("B", ""));
                        activityBaseAddMySpinnerLc.setTextViewtext("B");
                    } else if (!TextUtils.isEmpty(floor) && floor.contains("F")) {
                        activityBaseAddEditTextlc.setText(floor.replace("F", ""));
                        activityBaseAddMySpinnerLc.setTextViewtext("F");
                    }
                    String elevation = jsonObject.getString("elevation");
                    if (!TextUtils.isEmpty(elevation)) {
                        activityBaseAddEditTextBg.setText(elevation);
                    }


                } catch (JSONException e) {
                    // e.printStackTrace();
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


    /**
     * ????????????
     */
    @Override
    public void destroy() {

        if (readThread != null) {
            readThread.start = false;
            readThread.finish = false;
            readThread = null;
        }
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
//        if (uhfManager != null) {
//            //????????????
//        //    uhfManager.close();
//        }

        if (mp != null) {
            mp.release();//????????????
        }
        FileUtil.deleteCaCheImage();
        super.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectTag = 1;
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    imglist.clear();
                    if (list != null && list.size() > 0) {
                        imglist.addAll(list);
                        commonAdapter.notifyDataSetChanged();
                        activityBaseAddRecyclerViewImgs.scrollToPosition(imglist.size() - 1);
                    }
                    PublicUtils.log("???????????????" + list.size() + "???");
                    break;
                case PictureConfig.CAMERA:
                    selectTag = 2;
                    List<LocalMedia> clist = PictureSelector.obtainMultipleResult(data);
                    imglist.clear();
                    if (clist != null && clist.size() > 0) {
                        imglist.addAll(clist);
                        commonAdapter.notifyDataSetChanged();
                        activityBaseAddRecyclerViewImgs.scrollToPosition(imglist.size() - 1);
                    }
                    PublicUtils.log("???????????????" + clist.size() + "???");
                    break;
            }
        }
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            projectName = data.getStringExtra("strs");
            StrProjectName = data.getStringExtra("StrProjectName");//?????????????????????????????????
            activityBaseAddMyTextViewPjname.setText(projectName);
            pjbean = data.getParcelableExtra("project");
            String projectType = pjbean.getProjectType();
            if ("1".equals(projectType)) {
                lin_lc.setVisibility(View.VISIBLE);
                tv_title.setText("??????");
                // activityBaseAddEditTextlc.setHint("??????");
                // activityBaseAddEditTextBg.setHint("??????");
                activityBaseAddMySpinnerLc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, BaseAddActivity.this.strs) {
                    @Override
                    public void setinit(String name, ViewHolder mViewHolder) {
                        mViewHolder.nametextview.setText(name);
                    }
                });
                activityBaseAddMySpinnerLc.setTextViewtext(BaseAddActivity.this.strs[0]);
                activityBaseAddMySpinnerLc.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                    @Override
                    public String onclick(int position) {

                        return BaseAddActivity.this.strs[position];
                    }
                });
                activity_base_add_MySpinner_bg.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg) {
                    @Override
                    public void setinit(String name, ViewHolder mViewHolder) {
                        mViewHolder.nametextview.setText(name);
                    }
                });
                activity_base_add_MySpinner_bg.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                    @Override
                    public String onclick(int position) {

                        return bg[position];
                    }
                });
                activity_base_add_MySpinner_bg.setTextViewtext(bg[0]);
            } else {
                tv_title.setText("??????");
                lin_lc.setVisibility(View.GONE);
                // activityBaseAddEditTextlc.setHint("??????");
                //  activityBaseAddEditTextBg.setHint("");

            }
            // projectAreaList = pjbean.getProjectAreaList();
            String projectID = pjbean.getProjectID();
            getprojectArea("area", projectID);
            getspinnerdata(projectID);
            //areaListBeanNamesSpinnerAdpater.replcedata(list);
            //  PublicUtils.log(JsonUtils.objtojson(list));
        }
        if (requestCode == SelectTypesActivity.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            String strs_remark = data.getStringExtra("strs_remark");
            activityBaseAddTextViewJcxm.setText(strs);
            if(TextUtils.isEmpty(strs_remark)){
                tv_1.setText("??????????????????");
            }else {
                tv_1.setText(strs_remark);
            }
            msgTypes = data.getParcelableArrayListExtra("list");
            //  areaListBeanNamesSpinnerAdpater.replcedata(list);

            // PublicUtils.log(JsonUtils.objtojson(msgTypes));

        }
        if (requestCode == SelectSGBWActivity.RESQUST_CODE_SELECTSGBW
                && resultCode == RESULT_OK
                && data != null) {

            String strs = data.getStringExtra("strs");
            subids = data.getStringExtra("ids");
            subProjectListBean = data.getParcelableExtra("subProject");
            activity_base_add_MySpinner_sgqy.setTextViewtext(strs);

        }
        if (requestCode == SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            ids = data.getStringExtra("ids");
            activityBaseAddTextViewSgqy.setText(strs);
            areaBean = data.getParcelableExtra("projectArea");
            String projectAreaID = areaBean.getProjectAreaID();
            getprojectArea("subProject", projectAreaID);
            getprojectArea("etp", projectAreaID);
//            final List<SubProjectBean> subProjectList = areaBean.getSubProjectList();
//            if (subProjectList != null && subProjectList.size() > 0 && setpostmethod().equals("AddQualityMsg") && !"??????".equals(strs) && !"?????????".equals(strs)) {
//                //subProjectListBeanNamesSpinnerAdpater.getNames().addAll(subProjectList);
//                activity_base_add_EditText_scgx.setVisibility(View.INVISIBLE);
//                activity_base_add_MySpinner_sgqy.setVisibility(View.VISIBLE);
//                // subProjectListBeanNamesSpinnerAdpater.replcedata(subProjectList);
//                //subProjectListBeanNamesSpinnerAdpater.notifyDataSetChanged();
//                //subProjectListBean = subProjectList.get(0);
//                //   String subProjectName = subProjectListBean.getSubProjectName();
//                //  getcsdw(subProjectListBean.getSubProjectID());
//                activity_base_add_MySpinner_sgqy.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(BaseAddActivity.this, SelectSGBWActivity.class);
//                        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) subProjectList);
//                        startActivityForResult(intent, SelectSGBWActivity.RESQUST_CODE_SELECTSGBW);
//                    }
//                });
//                activity_base_add_MySpinner_sgqy.setTextViewtext("????????????");
//                //activity_base_add_MySpinner_sgqy.popdismiss();
//            } else if (subProjectList != null && subProjectList.size() > 0 && setpostmethod().equals("AddQualityMsg") && ("??????".equals(strs) || "?????????".equals(strs))) {
//                //subProjectListBeanNamesSpinnerAdpater.getNames().addAll(subProjectList);
//                activity_base_add_MySpinner_sgqy.setVisibility(View.INVISIBLE);
//                activity_base_add_EditText_scgx.setVisibility(View.VISIBLE);
//                //   subProjectListBeanNamesSpinnerAdpater.replcedata(subProjectList);
//                //   subProjectListBeanNamesSpinnerAdpater.notifyDataSetChanged();
//                subProjectListBean = subProjectList.get(0);
//                String subProjectName = subProjectListBean.getSubProjectName();
//                //  getcsdw(subProjectListBean.getSubProjectID());
//
//                activity_base_add_MySpinner_sgqy.setTextViewtext(subProjectName);
//                activity_base_add_MySpinner_sgqy.popdismiss();
//            } else {
//                activity_base_add_MySpinner_sgqy.setVisibility(View.GONE);
//
//            }
            //        ArrayList<Etp> list = data.getParcelableArrayListExtra("etpInfoList");
            // activityBaseAddMySpinnerZgdw.setTextViewtext("xuan");
//            etpInfoListBeanNamesSpinnerAdpater.replcedata(list);
//            if (list != null && list.size() > 0)
//                activityBaseAddMySpinnerZgdw.setTextViewtext(zgdwonClickListener.onclick(0));
//            else {
//                activityBaseAddMySpinnerZgdw.setTextViewtext("????????????");
//                etpBean = null;
//            }
            //  areaListBeanNamesSpinnerAdpater.replcedata(list);

            //   PublicUtils.log(JsonUtils.objtojson(list));

        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == RESULT_OK
                && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia localMedia = commonAdapter.getDatas().get(selectImageIndex);
            localMedia.setPath(imgpath);
            localMedia.setCompressPath(imgpath);
            commonAdapter.notifyItemChanged(selectImageIndex);
        }
        if (requestCode == BaseAddSearchActivity.RESQUST_CODE_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            String strs_remark = data.getStringExtra("strs_remark");
            activityBaseAddTextViewJcxm.setText(strs);
            if(TextUtils.isEmpty(strs_remark)){
                tv_1.setText("??????????????????");
            }else {
                tv_1.setText(strs_remark);
            }
            msgTypes = data.getParcelableArrayListExtra("list");
        }
    }
    private void showPop() {
        // ????????????????????????????????????????????????????????????????????????
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // ??????pop????????????
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x0000));
        // ??????pop????????????
        mPopupWindow.setAnimationStyle(R.style.pop_add1);
        // ??????pop????????????????????????false?????????????????????????????????Activity?????????pop??????Editor?????????focusable????????????true
        mPopupWindow.setFocusable(true);
        // ??????pop???????????????false??????????????????????????????true
        mPopupWindow.setTouchable(true);
        // ????????????pop????????????????????????false??????focusable???true???????????????????????????
        mPopupWindow.setOutsideTouchable(true);
        // ????????? + ??????????????????????????????????????????
        mPopupWindow.showAsDropDown(activityBaseAddImageViewHelp, -100, 40);
        // ??????pop??????????????????????????????????????????
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });
        //tv_1.setText(typesBeanList.get(0).getMsgTypes().get(0).getMsgTypes().get(0).getMsgTypes().get(0).getRemark());
    }
    private void toggleBright() {
        // ????????????????????????????????? ????????? ??????????????????????????????????????????????????????0.5f--1f???
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // ?????????????????????????????????????????????
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }
    /**
     * ???????????????????????????????????????????????????????????????????????????
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // ???????????????????????????????????????????????????????????????
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(BaseAddActivity.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }
}
