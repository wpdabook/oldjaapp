package com.a21zhewang.constructionapp.ui.gtxtiu;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AddgtxtinitBean;
import com.a21zhewang.constructionapp.bean.DicBean;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.GtxtspinnerBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.customview.LinkageSpinner;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.SelectSGBWActivity;
import com.a21zhewang.constructionapp.ui.SelectSgqyActivity;
import com.a21zhewang.constructionapp.ui.SelectTypesActivity;
import com.a21zhewang.constructionapp.utils.FileUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

public class AddGtxtActivity extends BaseActivity {


    @BindView(R.id.activity_add_gtxt_RecyclerView_imglist)
    RecyclerView activityAddGtxtRecyclerViewImglist;
    @BindView(R.id.activity_add_gtxt_TextView_pjname)
    TextView activityAddGtxtTextViewPjname;
    @BindView(R.id.activity_add_gtxt_TextView_sgqy)
    TextView activityAddGtxtTextViewSgqy;
    @BindView(R.id.activity_add_gtxt_MySpinner_scgx)
    MySpinner activityAddGtxtMySpinnerScgx;
    @BindView(R.id.activity_add_gtxt_EditText_lc)
    EditText activityAddGtxtEditTextLc;

    @BindView(R.id.activity_add_gtxt_EditText_nrms)
    EditText activityAddGtxtEditTextNrms;
    @BindView(R.id.activity_add_gtxt_img_addimg)
    ImageView activityAddGtxtImgAddimg;
    @BindView(R.id.activity_add_gtxt_img_getphoto)
    ImageView activityAddGtxtImgGetphoto;
    @BindView(R.id.activity_add_gtxt_CanyurenView_cyr)
    CanyurenView activityAddGtxtCanyurenViewCyr;
    @BindView(R.id.activity_add_gtxt_CheckBox_zjjsr)
    CheckBox activityAddGtxtCheckBoxZjjsr;
    @BindView(R.id.activity_add_gtxt_tv_zjjsr)
    TextView activityAddGtxtTvZjjsr;

    @BindView(R.id.activity_add_gtxt_MySpinner_lc)
    MySpinner activityAddGtxtMySpinnerLc;
    @BindView(R.id.activity_add_gtxt_MySpinner_bg)
    MySpinner activity_add_gtxt_MySpinner_bg;
    @BindView(R.id.activity_add_gtxt_EditText_bg)
    EditText activityAddGtxtEditTextBg;
    @BindView(R.id.activity_add_gtxt_EditText_scgx)
    EditText activityAddGtxtEditTextScgx;
    @BindView(R.id.activity_add_gtxt_img_LinkageSpinner1)
    LinkageSpinner activityAddGtxtImgLinkageSpinner1;
    @BindView(R.id.activity_add_gtxt_img_LinkageSpinner2)
    LinkageSpinner activityAddGtxtImgLinkageSpinner2;
    @BindView(R.id.activity_add_gtxt_TextView_xtlb)
    TextView activityAddGtxtTextViewXtlb;
    private List<LocalMedia> imglist;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private int selectImageIndex;
    private List<ProjectArea> projectAreaList;
    private List<SubProjectBean> subProjectList;


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
    // private NamesSpinnerAdpater<SubProjectBean> subProjectListBeanNamesSpinnerAdpater;


    private Project pjbean;
    private ProjectArea sgqybean;
    private SubProjectBean scgxbean;
    private List<AddgtxtinitBean.UserInfoListBean> addgtxtinitBeanSendMan;


    private AlertDialog alertDialog;
    private HashSet<Integer> integers;
    private List<AddgtxtinitBean.UserInfoListBean> userInfoListBeanList;

    private PictureSelectionModel pictureSelectionModel;
    private ArrayList<MsgType> msgTypes;
    private String ids;
    private String subids;
    private String[] lc, strs2, bg, bg2;
    ;

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
        return R.layout.activity_add_gtxt;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        integers = new HashSet<Integer>();
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        subProjectList = new ArrayList<>();

        addgtxtinitBeanSendMan = new ArrayList<>();
        userInfoListBeanList = new ArrayList<>();

        activityAddGtxtImgLinkageSpinner1.setMaxCount(-1);
        activityAddGtxtImgLinkageSpinner2.setMaxCount(-1);


        //??????????????????
//        subProjectListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<SubProjectBean>(AddGtxtActivity.this, subProjectList) {
//            @Override
//            public void setinit(SubProjectBean name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getSubProjectName());
//            }
//        };
//        activityAddGtxtMySpinnerScgx.setmyspinnerlistadpater(subProjectListBeanNamesSpinnerAdpater);
//        AdapterView.OnItemClickListener scgxonClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SubProjectBean subProjectListBean = subProjectList.get(position);
//                String subProjectName = subProjectListBean.getSubProjectName();
//                 getcsdw(subProjectListBean.getSubProjectID());
//                scgxbean = subProjectListBean;
//                activityAddGtxtMySpinnerScgx.setTextViewtext(subProjectName);
//                activityAddGtxtMySpinnerScgx.popdismiss();
//            }
//        };
        //activityAddGtxtMySpinnerScgx.setlistviewitemonclick(scgxonClickListener);


        //
        XUtil.postjsondata("", "GetCoordinateMsgInitialize", new MyCallBack() {


            /**
             * code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                PublicUtils.log("?????????????????????" + res);
                AddgtxtinitBean addgtxtinitBean = JsonUtils.getbean(res, AddgtxtinitBean.class);
//                List<AddgtxtinitBean.UserInfoListBean> sendMans = addgtxtinitBean.getUserInfoList();
//                //????????? ?????????list
//                if (sendMans != null && sendMans.size() > 0) {
//                    List<String> sendManstrs = new ArrayList<>();
//
//                    for (AddgtxtinitBean.UserInfoListBean sd : sendMans) {
//                        sendManstrs.add(sd.getUserName());
//                        addgtxtinitBeanSendMan.add(sd);//???????????????
//                    }
//                    activityAddGtxtCanyurenViewCyr.setList(sendManstrs);
//                    userInfoListBeanList.addAll(addgtxtinitBean.getRecentUserList());//???????????????????????????
//                    int j = 0;
//                    String zjjsr = "";
//                    for (int i = 0; i < addgtxtinitBeanSendMan.size(); i++) {
//                        if (i < userInfoListBeanList.size()) {
//                            AddgtxtinitBean.UserInfoListBean infoListBean = userInfoListBeanList.get(i);
//                            zjjsr = zjjsr + infoListBean.getUserName() + ",";
//                        }
//
//                        if (j < userInfoListBeanList.size() && addgtxtinitBeanSendMan.get(i).getUserID().equals(userInfoListBeanList.get(j).getUserID())) {
//                            integers.add(i);
//                            j++;
//                        }
//
//                    }
//                    activityAddGtxtTvZjjsr.setText("???????????????:" + zjjsr);
//                }
                //????????????
                final List<Project> others = addgtxtinitBean.getProjectList();
                if (others != null && others.size() > 0) {
                    activityAddGtxtTextViewPjname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddGtxtActivity.this, SelectProjectActivity.class);
                            //  intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) others);
                            SelectProjectActivity.parcelableArrayListExtra = (ArrayList<Project>) others;
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    });


                }
                //     List<Etp> etpList = addgtxtinitBean.getEtpList();


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
        //????????????????????????
        getxtlxdata();

        //????????????
        lc = new String[]{"F", "B"};
        strs2 = new String[]{"DK", "K"};
        bg = new String[]{"???"};
        bg2 = new String[]{"???", "???", "???", "???", "???", "???"};

        spinner_lc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, strs2) {
            @Override
            public void setinit(String name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name);
            }
        });
        spinner_lc.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_lc.setTextViewtext(strs2[position]);
                spinner_lc.popdismiss();
            }
        });
        spinner_lc.setTextViewtext(strs2[0]);
        spinner_wz.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg2) {
            @Override
            public void setinit(String name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name);
            }
        });
        spinner_wz.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinner_wz.setTextViewtext(bg2[position]);
                spinner_wz.popdismiss();
            }
        });
        spinner_wz.setTextViewtext(bg2[0]);
    }

    @Override
    public void initListener() {
        activityAddGtxtTextViewSgqy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectAreaList != null) {
                    Intent intent = new Intent(AddGtxtActivity.this, SelectSgqyActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectAreaList);
                    startActivityForResult(intent, SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ);
                } else {
                    PublicUtils.toast("???????????????");
                }
            }
        });
        activityAddGtxtCheckBoxZjjsr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //  PublicUtils.toast(integers.size()+"");
                    activityAddGtxtCanyurenViewCyr.setselsctor(integers);
                } else {
                    activityAddGtxtCanyurenViewCyr.remeselsctor(integers);
                }
            }
        });
        activityAddGtxtCanyurenViewCyr.setCanyurenViewAPI(new CanyurenView.CanyurenViewAPI<Object>() {
            @Override
            public void setstr(int size, TextView textView, Set<Integer> ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(addgtxtinitBeanSendMan.get(integers.get(0)).getUserName());
                } else if (size == 2) {
                    textView.setText(addgtxtinitBeanSendMan.get(integers.get(0)).getUserName() + "," + addgtxtinitBeanSendMan.get(integers.get(1)).getUserName());
                } else if (size > 2) {
                    textView.setText(addgtxtinitBeanSendMan.get(integers.get(0)).getUserName() + "," + addgtxtinitBeanSendMan.get(integers.get(1)).getUserName() + "...");
                }
            }

            @Override
            public String getstr(int i, Object o) {
                return null;
            }
        });
    }

    /**
     * ???????????????????????????
     */
    private void getxtlxdata() {
        JSONObject jsonobj = JsonUtils.getjsonobj("{}");
        XUtil.postjsondata(jsonobj, "GetAllCoordinateMsgXTLX", new MyCallBack() {

            @Override
            public void onResule(String res) {
                DicBean dicBean = JsonUtils.getbean(res, DicBean.class);
                final List<MsgType> msgTypes = dicBean.getMsgTypes();
                if (msgTypes != null && msgTypes.size() > 0)
                    activityAddGtxtTextViewXtlb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddGtxtActivity.this, SelectTypesActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) msgTypes);
                            startActivityForResult(intent, SelectTypesActivity.RESQUST_CODE_SELECTTYPE);
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

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {


        imglist = new ArrayList<>();


        pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// ???????????????????????? int
                .imageSpanCount(4)// ?????????????????? int
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// ????????????????????? true or false
                .previewVideo(false)// ????????????????????? true or false
                .enablePreviewAudio(false) // ????????????????????? true or false
                // .compressGrade(Luban.CUSTOM_GEAR)// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
                .isCamera(true)// ???????????????????????? true or false
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .enableCrop(false)// ???????????? true or false
                .compress(true)// ???????????? true or false
                //.compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// ????????????gif?????? true or false
                .showCropFrame(false)// ?????????????????????????????? ???????????????????????????false   true or false
                .showCropGrid(false)// ?????????????????????????????? ???????????????????????????false    true or false
                .openClickSound(false)// ???????????????????????? true or false
                .selectionMedia(imglist)// ???????????????????????? List<LocalMedia> list
                .previewEggs(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityAddGtxtRecyclerViewImglist.setLayoutManager(layoutManager);
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
//                        pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex = position;
                        GraffitiActivity.startActivityForResult(AddGtxtActivity.this, s.getCompressPath());
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
        activityAddGtxtRecyclerViewImglist.setAdapter(commonAdapter);


    }

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_add_gtxt_img_addimg, R.id.activity_add_gtxt_img_getphoto, R.id.activity_add_gtxt_tv_tj, R.id.activity_add_gtxt_tv_zjjsr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_add_gtxt_tv_zjjsr:
                if (activityAddGtxtCheckBoxZjjsr.isChecked()) {
                    activityAddGtxtCheckBoxZjjsr.setChecked(false);
                } else {
                    activityAddGtxtCheckBoxZjjsr.setChecked(true);
                }
                break;
            case R.id.activity_add_gtxt_img_addimg:
                if (imglist.size() >= 5) {
                    PublicUtils.toast("???????????????5????????????");
                    break;
                }
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.activity_add_gtxt_img_getphoto:
                if (imglist.size() >= 5) {
                    PublicUtils.toast("???????????????5????????????");
                    break;
                }
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.activity_add_gtxt_tv_tj:

//                1????????????????????????Method = AddCoordinateMsg
//                2???Data??????????????????
//                ????????????	????????????	??????
//                projectID	String	????????????ID
//                projectAreaName	String	??????????????????
//                subProjectID	String	??????ID
//                msgTypeID	String	????????????ID
//                floor	String	??????
//                msgContent	String	????????????
//                createUserID	String	?????????ID
//                createUserName	String	???????????????
//                etpID	String	?????????????????????ID
//                etpShortName	String	???????????????????????????
//                recUserName	String	????????????????????????????????????,??????,?????????
//                recUserID	String	?????????ID????????????zhangsan,lisi,wangwu???
//                if (activityAddGtxtMySpinnerPjname.getTextViewtext().equals("????????????")) {
//                    PublicUtils.toast("???????????????");
//                    break;
//                } else
//                if (activityAddGtxtMySpinnerSgqy.getTextViewtext().equals("????????????")) {
//                    PublicUtils.toast("?????????????????????");
//                    break;
//                }
                if (activityAddGtxtEditTextScgx.getVisibility() == View.VISIBLE) {
                    if (activityAddGtxtEditTextScgx.getText().toString().trim().length() == 0) {
                        PublicUtils.toast("?????????????????????");
                        break;
                    }
                } else {
                    if (activityAddGtxtMySpinnerScgx.getTextViewtext().equals("????????????")) {
                        PublicUtils.toast("?????????????????????");
                        break;
                    }
                }


                String louceng = activityAddGtxtEditTextLc.getText().toString();
                String bg_str = activityAddGtxtEditTextBg.getText().toString();
                String lc1 = ed_lc1.getText().toString().trim();
                String lc2 = ed_lc2.getText().toString().trim();
                String wz = ed_wz.getText().toString().trim();
                boolean b = lin_lc.getVisibility() == View.VISIBLE;
                if (b) {
                    if (TextUtils.isEmpty(louceng.trim()) && bg_str.length() == 0) {
                        PublicUtils.toast("???????????????????????????");
                        return;
                    }
                } else {
                    if (TextUtils.isEmpty(wz) && (TextUtils.isEmpty(lc1) || TextUtils.isEmpty(lc2))) {
                        PublicUtils.toast("???????????????????????????????????????");
                        return;
                    }
                }
//                if (louceng.trim().length() == 0 && bg_str.length() == 0) {
//                    PublicUtils.toast("????????????????????????");
//                    break;
//                }
//                if (!PublicUtils.isMobileNO(bg_str) && TextUtils.isEmpty(louceng.trim())) {
//                    PublicUtils.toast("?????????????????????");
//                    break;
//                }
                if (msgTypes == null) {
                    PublicUtils.toast("?????????????????????");
                    break;
                }
//                }else if (activityAddGtxtMySpinnerXtlbchild.getTextViewtext().equals("???????????????")) {
//                    PublicUtils.toast("????????????????????????");
//                    break;
//                }

                String nrms_str = activityAddGtxtEditTextNrms.getText().toString();
                if (nrms_str.trim().length() == 0) {
                    PublicUtils.toast("?????????????????????");
                    break;
                }
//                Set<Integer> activityAddGtxtCanyurenViewCyrList = activityAddGtxtCanyurenViewCyr.getList();
//                Iterator<Integer> iterator = activityAddGtxtCanyurenViewCyrList.iterator();
//                List<AddgtxtinitBean.UserInfoListBean> sendmansBeens = new ArrayList<>();
//                if (iterator.hasNext()) {
//
//                    while (iterator.hasNext()) {
//                        Integer next = iterator.next();
//                        sendmansBeens.add(addgtxtinitBeanSendMan.get(next));
//                    }
//                } else {
//                   // PublicUtils.toast("??????????????????");
//                   // break;
//                }
                if (activityAddGtxtImgLinkageSpinner1.getInfoList().size() == 0) {
                    PublicUtils.toast("?????????????????????????????????");
                    break;
                }

                JSONObject upjsonobj = new JSONObject();
                try {
//                    upjsonobj.put("msgTypeID", childBean.getChildID());
                    upjsonobj.put("msgTypeID", "");
                    upjsonobj.put("createUserName", PublicUtils.userBean.getUserName());

                    JSONArray imgfiles = new JSONArray();
                    List<String> imgs = new ArrayList<>();
                    if (imglist.size() > 0) {
                        int i = 0;
                        for (LocalMedia s : imglist) {
                            JSONObject imgfile = new JSONObject();
                            String path = s.getCompressPath();
                            imgs.add(path);
                            String fileName = path.substring(path.lastIndexOf("/") + 1);
                            imgfile.put("fileName", fileName);
                            imgfile.put("fileBrief", "???" + (i + 1) + "?????????");
                            imgfiles.put(imgfile);
                        }
                    }
                    if (b){
                        if (!TextUtils.isEmpty(louceng.trim()))
                            upjsonobj.put("floor", louceng + activityAddGtxtMySpinnerLc.getTextViewtext());
                        if (!TextUtils.isEmpty(bg_str)) {
                            upjsonobj.put("elevation", bg_str+activity_add_gtxt_MySpinner_bg.getTextViewtext());
                        }

                    }else{
                        if (!TextUtils.isEmpty(wz)) {
                            upjsonobj.put("elevation", wz + spinner_wz.getTextViewtext());
                        }

                        if (!TextUtils.isEmpty(lc1)&&!TextUtils.isEmpty(lc2))
                            upjsonobj.put("floor", spinner_lc.getTextViewtext() +lc1+"+"+lc2);
                    }

                    upjsonobj.put("files", imgfiles);

                    //  upjsonobj.put("msgStatus","");
                    upjsonobj.put("createUserID", PublicUtils.userBean.getUserID());
                    upjsonobj.put("subProjectID", subids);
                    if (activityAddGtxtEditTextScgx.getVisibility() == View.VISIBLE) {
                        upjsonobj.put("subProjectID", sgqybean.getSubProjectList().get(0).getSubProjectID());
                        upjsonobj.put("subProjectName", activityAddGtxtEditTextScgx.getText().toString().trim());
                    } else {
                        upjsonobj.put("subProjectName", activityAddGtxtMySpinnerScgx.getTextViewtext());
                    }
                    upjsonobj.put("msgTypes", JsonUtils.List2jsonArray(msgTypes));


                    upjsonobj.put("projectID", pjbean.getProjectID());
                    upjsonobj.put("projectShortName", pjbean.getProjectShortName());

                    upjsonobj.put("etpShortName", PublicUtils.userBean.getEtpShortName());
                    //  upjsonobj.put("recordID",PublicUtils.userBean.);
                    upjsonobj.put("projectAreaName", activityAddGtxtTextViewSgqy.getText().toString());
                    upjsonobj.put("projectAreaID", ids);
                    upjsonobj.put("projectID", pjbean.getProjectID());
                    upjsonobj.put("content", nrms_str);
                    upjsonobj.put("etpID", PublicUtils.userBean.getEtpID());
                    JSONArray receiversjson = new JSONArray();
                    List<Etp> infoList = activityAddGtxtImgLinkageSpinner1.getInfoList();
                    for (int j = 0; j < infoList.size(); j++) {
                        Etp eIListBean = infoList.get(j);
                        List<User> uslist = eIListBean.getUserInfoList();
                        if (uslist != null && uslist.size() > 0)
                            for (int o = 0; o < uslist.size(); o++) {
                                User bean = uslist.get(o);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("userID", bean.getUserID());
                                jsonObject.put("userName", bean.getUserName());
                                receiversjson.put(jsonObject);
                            }
                    }
                    upjsonobj.put("receivers", receiversjson);

                    JSONArray copyReceivers = new JSONArray();
                    List<Etp> infoList2 = activityAddGtxtImgLinkageSpinner2.getInfoList();
                    for (int j = 0; j < infoList2.size(); j++) {
                        Etp eIListBean = infoList2.get(j);
                        List<User> uslist = eIListBean.getUserInfoList();
                        for (int o = 0; o < uslist.size(); o++) {
                            User bean = uslist.get(o);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("userID", bean.getUserID());
                            jsonObject.put("userName", bean.getUserName());
                            copyReceivers.put(jsonObject);
                        }
                    }
                    upjsonobj.put("copyReceivers", copyReceivers);
                    alertDialog.setMessage("????????????...");
                    alertDialog.show();
                    XUtil.updatas(upjsonobj, imgs, "AddCoordinateMsg", new MyCallBack() {
                        /**
                         * code ?????? 0??????
                         *
                         * @param res
                         */
                        @Override
                        public void onResule(String res) {
                            alertDialog.dismiss();
                            PublicUtils.toast("????????????");
                            finish();
                        }

                        /**
                         * code ????????? 0??????
                         *
                         * @param getbean
                         */
                        @Override
                        public void onFail(ObjBean getbean) {
                            alertDialog.setMessage("???????????????");
                            alertDialog.setCancelable(true);
                            alertDialog.setCanceledOnTouchOutside(true);
                        }

                        /**
                         * ??????????????????
                         */
                        @Override
                        public void onWrong() {
                            alertDialog.setMessage("???????????????");
                            alertDialog.setCancelable(true);
                            alertDialog.setCanceledOnTouchOutside(true);
                        }


                    });

                } catch (JSONException e) {
                    PublicUtils.log("json???????????????");
                    // e.printStackTrace();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {


            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            imglist.clear();
            if (list != null && list.size() > 0) {
                imglist.addAll(list);
                commonAdapter.notifyDataSetChanged();
                activityAddGtxtRecyclerViewImglist.scrollToPosition(imglist.size() - 1);
            }


        }
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
//            try {
            String strs = data.getStringExtra("strs");

            activityAddGtxtTextViewPjname.setText(strs);
            pjbean = data.getParcelableExtra("project");
            projectAreaList = pjbean.getProjectAreaList();


            sgqybean = null;
            activityAddGtxtTextViewSgqy.setText(getString(R.string.sgqy_str));
            activityAddGtxtMySpinnerScgx.setTextViewtext("????????????");
//            }catch (Exception e){
//               e.printStackTrace();
//            }
            if ("1".equals(pjbean.getProjectType())) {
                lin_lc.setVisibility(View.VISIBLE);
                activityAddGtxtMySpinnerLc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, lc) {
                    @Override
                    public void setinit(String name, ViewHolder mViewHolder) {
                        mViewHolder.nametextview.setText(name);
                    }
                });
                activityAddGtxtMySpinnerLc.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        activityAddGtxtMySpinnerLc.setTextViewtext(lc[position]);
                        activityAddGtxtMySpinnerLc.popdismiss();
                    }
                });
                activityAddGtxtMySpinnerLc.setTextViewtext(lc[0]);
                activity_add_gtxt_MySpinner_bg.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg) {
                    @Override
                    public void setinit(String name, ViewHolder mViewHolder) {
                        mViewHolder.nametextview.setText(name);
                    }
                });
                activity_add_gtxt_MySpinner_bg.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        activity_add_gtxt_MySpinner_bg.setTextViewtext(bg[position]);
                        activity_add_gtxt_MySpinner_bg.popdismiss();
                    }
                });
                activity_add_gtxt_MySpinner_bg.setTextViewtext(bg[0]);
                activityAddGtxtEditTextLc.setHint("??????");
                activityAddGtxtEditTextBg.setHint("??????");
            } else {
                lin_lc.setVisibility(View.GONE);
               // activityAddGtxtEditTextLc.setHint("??????");
              //  activityAddGtxtEditTextBg.setHint("");

            }

        }
        if (requestCode == SelectTypesActivity.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            activityAddGtxtTextViewXtlb.setText(strs);
            msgTypes = data.getParcelableArrayListExtra("list");


        }
        if (requestCode == SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            ids = data.getStringExtra("ids");
            activityAddGtxtTextViewSgqy.setText(strs);
            sgqybean = data.getParcelableExtra("projectArea");

            final List<SubProjectBean> subProjectListBeen = sgqybean.getSubProjectList();
            List<Etp> etpList = sgqybean.getEtpInfoList();
//            if (etpList != null && etpList.size() > 0) {
//                List<Etp> eplist = new ArrayList<Etp>();
//                eplist.addAll(etpList);
//                Project pjobj = new Project();
//                pjobj.setEtpInfoList(eplist);
//              //  activityAddGtxtImgLinkageSpinner1.setpjbean(pjobj, 1);
//               // activityAddGtxtImgLinkageSpinner2.setpjbean(pjobj);
//            }
            activityAddGtxtMySpinnerScgx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddGtxtActivity.this, SelectSGBWActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) subProjectListBeen);
                    startActivityForResult(intent, SelectSGBWActivity.RESQUST_CODE_SELECTSGBW);
                }
            });
            //subProjectListBeanNamesSpinnerAdpater.replcedata(subProjectListBeen);
            activityAddGtxtMySpinnerScgx.setTextViewtext("????????????");
            String projectAreaName = sgqybean.getProjectAreaName();
            if (projectAreaName.equals("?????????") || projectAreaName.equals("??????")) {
                scgxbean = subProjectList.get(0);
                activityAddGtxtMySpinnerScgx.setVisibility(View.INVISIBLE);
                activityAddGtxtEditTextScgx.setVisibility(View.VISIBLE);
            } else {
                activityAddGtxtEditTextScgx.setVisibility(View.INVISIBLE);
                activityAddGtxtMySpinnerScgx.setVisibility(View.VISIBLE);
            }
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
        if (requestCode == SelectSGBWActivity.RESQUST_CODE_SELECTSGBW
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            subids = data.getStringExtra("ids");
            activityAddGtxtMySpinnerScgx.setTextViewtext(strs);
            SubProjectBean subProjectListBean = data.getParcelableExtra("subProject");
            getcsdw(subProjectListBean.getSubProjectID());
            scgxbean = subProjectListBean;
        }
    }

    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }


    private void getcsdw(String subProjectID) {

        XUtil.postjsondata(JsonUtils.getjsonobj("{\"subProjectID\":\"" + subProjectID + "\"}"),
                "GetCoordinateMsgInitializeEtp", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        GtxtspinnerBean gtxtspinnerBean = JsonUtils.getbean(res, GtxtspinnerBean.class);
                        activityAddGtxtImgLinkageSpinner1.setlist(gtxtspinnerBean.getEtpList(), 1);
                        activityAddGtxtImgLinkageSpinner2.setlist(gtxtspinnerBean.getCopyEtpList(), 0);
                    }

                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    @Override
                    public void onWrong() {

                    }
                });
    }
}
