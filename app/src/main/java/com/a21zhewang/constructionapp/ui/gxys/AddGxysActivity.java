package com.a21zhewang.constructionapp.ui.gxys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AddgtxtinitBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.SelectSGBWActivity;
import com.a21zhewang.constructionapp.ui.SelectSgqyActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.AddGtxtActivity;
import com.a21zhewang.constructionapp.utils.FileUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

public class AddGxysActivity extends BaseActivity {


    @BindView(R.id.activity_add_gxys_TextView_pjname)
    TextView activityAddGxysTextViewPjname;
    @BindView(R.id.activity_add_gxys_TextView_sgqy)
    TextView activityAddGxysTextViewSgqy;
    @BindView(R.id.activity_add_gxys_EditText_scgx)
    EditText activityAddGxysEditTextScgx;
    @BindView(R.id.activity_add_gxys_MySpinner_scgx)
    MySpinner activityAddGxysMySpinnerScgx;
    @BindView(R.id.activity_add_gxys_EditText_lc)
    EditText activityAddGxysEditTextLc;
    @BindView(R.id.activity_add_gxys_MySpinner_lc)
    MySpinner activityAddGxysMySpinnerLc;
    @BindView(R.id.activity_add_gxys_MySpinner_bg)
    MySpinner activity_add_gxys_MySpinner_bg;
    @BindView(R.id.activity_add_gxys_EditText_bg)
    EditText activityAddGxysEditTextBg;
    @BindView(R.id.activity_add_gxys_EditText_nrms)
    EditText activityAddGxysEditTextNrms;
    @BindView(R.id.activity_add_gxys_RecyclerView_imglist)
    RecyclerView activityAddGxysRecyclerViewImglist;
    @BindView(R.id.activity_add_gxys_img_addimg)
    ImageView activityAddGxysImgAddimg;
    @BindView(R.id.activity_add_gxys_img_getphoto)
    ImageView activityAddGxysImgGetphoto;
    @BindView(R.id.activity_add_gxys_img_TimeTextView)
    TimeTextView activityAddGxysImgTimeTextView;
    @BindView(R.id.activity_gtxtzf_CanyurenView)
    CanyurenView activityGtxtzfCanyurenView;
    private List<SubProjectBean> subProjectList;
    //private NamesSpinnerAdpater<SubProjectBean> subadapter;
    private SubProjectBean subProjectBean;
    //  private MySpinner.MySpinnerAPI subonClickListener;
    private List<User> sendMans;
    private List<LocalMedia> imglist;
    private int selectImageIndex;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private Dialog loadingDialog;
    private Project pjBean;
    private ProjectArea areaBean;
    private PictureSelectionModel pictureSelectionModel;
    private String ids;
    private String subids;
    private String[] lc, strs2, bg, bg2;

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
        return R.layout.activity_add_gxys;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "????????????....");
        activityAddGxysImgTimeTextView.setDateFormat("yyyy-MM-dd HH:mm");
        imglist = new ArrayList<>();
        pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// ???????????????????????? int
                .imageSpanCount(4)// ?????????????????? int
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// ????????????????????? true or false
                .previewVideo(false)// ????????????????????? true or false
                .enablePreviewAudio(false) // ????????????????????? true or false
                //  .compressGrade(Luban.CUSTOM_GEAR)// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
                .isCamera(true)// ???????????????????????? true or false
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .enableCrop(false)// ???????????? true or false
                .compress(true)// ???????????? true or false
                // .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// ????????????gif?????? true or false
                .showCropFrame(false)// ?????????????????????????????? ???????????????????????????false   true or false
                .showCropGrid(false)// ?????????????????????????????? ???????????????????????????false    true or false
                .openClickSound(false)// ???????????????????????? true or false
                .selectionMedia(imglist)// ???????????????????????? List<LocalMedia> list
                .previewEggs(true);
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {
        activityAddGxysImgAddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        initselectorimg();
        setspinner();
    }

    private void setspinner() {

        activityAddGxysTextViewSgqy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pjBean == null) {
                    PublicUtils.toast("???????????????");
                    return;
                }
                Intent intent = new Intent(AddGxysActivity.this, SelectSgqyActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) pjBean.getProjectAreaList());
                startActivityForResult(intent, SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ);

            }
        });


        subProjectList = new ArrayList<>();
//        subadapter = new NamesSpinnerAdpater<SubProjectBean>(this, subProjectList) {
//            @Override
//            public void setinit(SubProjectBean name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getSubProjectName());
//            }
//        };
        // activityAddGxysMySpinnerScgx.setmyspinnerlistadpater(subadapter);
//        subonClickListener = new MySpinner.MySpinnerAPI() {
//            @Override
//            public String onclick(int position) {
//                subProjectBean = subProjectList.get(position);
//                return subProjectBean.getSubProjectName();
//            }
//        };
        //  activityAddGxysMySpinnerScgx.setlistviewitemonclick(subonClickListener);
        // activityAddGxysMySpinnerScgx.

        //????????????
        lc = new String[]{"F", "B"};
        strs2 = new String[]{"DK", "K"};
        bg = new String[]{"???"};
        bg2 = new String[]{"???", "???", "???", "???", "???", "???"};


        activityGtxtzfCanyurenView.setMaxSelect(-1);
        //?????????
        activityGtxtzfCanyurenView.setCanyurenViewAPI(new CanyurenView.CanyurenViewAPI<Object>() {
            @Override
            public void setstr(int size, TextView textView, Set<Integer> ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(sendMans.get(integers.get(0)).getUserName());
                } else if (size == 2) {
                    textView.setText(sendMans.get(integers.get(0)).getUserName() + "," + sendMans.get(integers.get(1)).getUserName());
                } else if (size > 2) {
                    textView.setText(sendMans.get(integers.get(0)).getUserName() + "," + sendMans.get(integers.get(1)).getUserName() + "...");
                }
            }

            @Override
            public String getstr(int i, Object o) {
                return null;
            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {
        initspinner();
    }

    private void initspinner() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetProcessAcceptanceInitialize", new MyCallBack() {


            /**
             * code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                AddgtxtinitBean addgtxtinitBean = JsonUtils.getbean(res, AddgtxtinitBean.class);

                final List<Project> List = addgtxtinitBean.getProjectList();
                if (List != null && List.size() > 0) {
                    activityAddGxysTextViewPjname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddGxysActivity.this, SelectProjectActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) List);
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    });
                }
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

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick(R.id.activity_add_gxys_tv_tj)
    public void onViewClicked() {
        if (activityAddGxysTextViewPjname.getText().toString().equals("????????????")) {
            PublicUtils.toast("???????????????");
            return;
        } else if (activityAddGxysTextViewSgqy.getText().toString().equals(getString(R.string.sgqy_str))) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        String activityAddGxysMySpinnerScgxTextViewtext = activityAddGxysMySpinnerScgx.getTextViewtext();
        if (activityAddGxysEditTextScgx.getVisibility() == View.VISIBLE) {
            if (activityAddGxysEditTextScgx.getText().toString().trim().length() == 0) {
                PublicUtils.toast("?????????????????????");
                return;
            }
        } else {
            if (activityAddGxysMySpinnerScgxTextViewtext.equals("????????????")) {
                PublicUtils.toast("?????????????????????");
                return;
            }
        }
        String louceng = activityAddGxysEditTextLc.getText().toString();
        String bg_str = activityAddGxysEditTextBg.getText().toString();
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


//                }else if (activityAddGtxtMySpinnerXtlbchild.getTextViewtext().equals("???????????????")) {
//                    PublicUtils.toast("????????????????????????");
//                    break;
//                }

        String nrms_str = activityAddGxysEditTextNrms.getText().toString().trim();
        if (nrms_str.trim().length() == 0) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        List<User> lists = activityGtxtzfCanyurenView.getlists();
        if (lists.size() == 0) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());

            jsonObject.put("projectShortName", pjBean.getProjectShortName());
            jsonObject.put("subProjectID", subids);
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("content", activityAddGxysEditTextNrms.getText().toString().trim());
            if (activityAddGxysEditTextScgx.getVisibility() == View.VISIBLE) {
                jsonObject.put("subProjectID", subProjectBean.getSubProjectID());
                jsonObject.put("subProjectName", activityAddGxysEditTextScgx.getText().toString().trim());
            } else {
                jsonObject.put("subProjectName", activityAddGxysMySpinnerScgxTextViewtext);
            }
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());

            JSONArray receiversjson = new JSONArray();

            for (int j = 0; j < lists.size(); j++) {
                User infoListBean = lists.get(j);


                JSONObject json = new JSONObject();
                json.put("userID", infoListBean.getUserID());
                json.put("userName", infoListBean.getUserName());
                receiversjson.put(json);

            }
            jsonObject.put("receivers", receiversjson);
            jsonObject.put("projectAreaName", activityAddGxysTextViewSgqy.getText().toString());
            jsonObject.put("projectAreaID", ids);
            jsonObject.put("projectID", pjBean.getProjectID());
            jsonObject.put("applyTime", activityAddGxysImgTimeTextView.getText());
            jsonObject.put("etpID", PublicUtils.userBean.getEtpID());

            if (b){
                if (!TextUtils.isEmpty(louceng.trim()))
                    jsonObject.put("floor", louceng + activityAddGxysMySpinnerLc.getTextViewtext());

                if (!TextUtils.isEmpty(bg_str)) {
                    jsonObject.put("elevation", bg_str+activity_add_gxys_MySpinner_bg.getTextViewtext());
                }
            }else{
                if (!TextUtils.isEmpty(wz)) {
                    jsonObject.put("elevation", wz + spinner_wz.getTextViewtext());
                }

                if (!TextUtils.isEmpty(lc1)&&!TextUtils.isEmpty(lc2))
                    jsonObject.put("floor", spinner_lc.getTextViewtext() +lc1+"+"+lc2);
            }
            JSONArray imgfiles = new JSONArray();

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
            jsonObject.put("files", imgfiles);
        } catch (JSONException e) {
            PublicUtils.toast("?????????????????????");
        }
        loadingDialog.show();
        XUtil.updatas(jsonObject, imgs, "AddProcessAcceptance", new MyCallBack() {
            @Override
            public void onResule(String res) {
                finish();
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
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });

    }

    private void initselectorimg() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityAddGxysRecyclerViewImglist.setLayoutManager(layoutManager);
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
                        //   pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex = position;
                        GraffitiActivity.startActivityForResult(AddGxysActivity.this, s.getCompressPath());
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
        activityAddGxysRecyclerViewImglist.setAdapter(commonAdapter);
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
                activityAddGxysRecyclerViewImglist.scrollToPosition(imglist.size() - 1);
            }


        }
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            activityAddGxysTextViewPjname.setText(strs);
            pjBean = data.getParcelableExtra("project");
            if ("1".equals(pjBean.getProjectType())) {
                lin_lc.setVisibility(View.VISIBLE);
                activityAddGxysMySpinnerLc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, lc) {
                    @Override
                    public void setinit(String name, ViewHolder mViewHolder) {
                        mViewHolder.nametextview.setText(name);
                    }
                });
                activityAddGxysMySpinnerLc.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        activityAddGxysMySpinnerLc.setTextViewtext(lc[position]);
                        activityAddGxysMySpinnerLc.popdismiss();
                    }
                });
                activity_add_gxys_MySpinner_bg.setTextViewtext(lc[0]);

                activity_add_gxys_MySpinner_bg.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg) {
                    @Override
                    public void setinit(String name, ViewHolder mViewHolder) {
                        mViewHolder.nametextview.setText(name);
                    }
                });
                activity_add_gxys_MySpinner_bg.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        activity_add_gxys_MySpinner_bg.setTextViewtext(bg[position]);
                        activity_add_gxys_MySpinner_bg.popdismiss();
                    }
                });
                activity_add_gxys_MySpinner_bg.setTextViewtext(bg[0]);
                activityAddGxysEditTextLc.setHint("??????");
                activityAddGxysEditTextBg.setHint("??????");
            } else {
                lin_lc.setVisibility(View.GONE);
          //      activityAddGxysEditTextLc.setHint("??????");
            //    activityAddGxysEditTextBg.setHint("");
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

        }
        if (requestCode == SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            ids = data.getStringExtra("ids");
            activityAddGxysTextViewSgqy.setText(strs);
            areaBean = data.getParcelableExtra("projectArea");
            sendMans = areaBean.getUserInfoList();
            if (sendMans != null && sendMans.size() > 0) {
                activityGtxtzfCanyurenView.setList(sendMans, new CanyurenView.CanyurenViewAPI<User>() {
                    @Override
                    public void setstr(int size, TextView textView, Set ints) {
                    }

                    @Override
                    public String getstr(int i, User o) {
                        return o.getUserName();
                    }
                });
            }
            final List<SubProjectBean> subList = areaBean.getSubProjectList();
            activityAddGxysMySpinnerScgx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddGxysActivity.this, SelectSGBWActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) subList);
                    startActivityForResult(intent, SelectSGBWActivity.RESQUST_CODE_SELECTSGBW);
                }
            });
            //subadapter.replcedata(subList);
            String projectAreaName = areaBean.getProjectAreaName();
            if (projectAreaName.equals("?????????") || projectAreaName.equals("??????")) {
                subProjectBean = subList.get(0);
                activityAddGxysMySpinnerScgx.setVisibility(View.INVISIBLE);
                activityAddGxysEditTextScgx.setVisibility(View.VISIBLE);
            } else {
                activityAddGxysEditTextScgx.setVisibility(View.INVISIBLE);
                activityAddGxysMySpinnerScgx.setVisibility(View.VISIBLE);
            }

        }
        if (requestCode == SelectSGBWActivity.RESQUST_CODE_SELECTSGBW
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            subids = data.getStringExtra("ids");
            activityAddGxysMySpinnerScgx.setTextViewtext(strs);
            //   subProjectBean = subProjectList.get(position);
            subProjectBean = data.getParcelableExtra("subProject");
            //  getcsdw(subProjectListBean.getSubProjectID());
            //   scgxbean = subProjectListBean;
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
    }

    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }
}
