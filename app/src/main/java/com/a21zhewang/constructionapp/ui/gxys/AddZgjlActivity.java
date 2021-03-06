package com.a21zhewang.constructionapp.ui.gxys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AqscSpinnerBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectTypesActivity;
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

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

public class AddZgjlActivity extends BaseActivity {


    @BindView(R.id.activity_add_zgjl_MyTitleBar)
    MyTitleBar activityAddZgjlMyTitleBar;
//    @BindView(R.id.activity_add_zgjl_MySpinner_zgxm1)
//    MySpinner activityAddZgjlMySpinnerZgxm1;
//    @BindView(R.id.activity_add_zgjl_MySpinner_zgxm2)
//    MySpinner activityAddZgjlMySpinnerZgxm2;
    @BindView(R.id.activity_base_add_TextView_jcxm)
    TextView textView;
    @BindView(R.id.activity_add_zgjl_EditText_nrms)
    EditText activityAddZgjlEditTextNrms;
    @BindView(R.id.activity_add_zgjl_RecyclerView_imgs)
    RecyclerView activityAddZgjlRecyclerViewImgs;
    private String recordID;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private List<LocalMedia> imglist;
//
//    //???????????? spinner 1
//    private NamesSpinnerAdpater<MsgType> msgTypesadapter;
   private List<MsgType> msgTypes;
//
//    private List<MsgType> msgsTypesList;
//    private MySpinner.MySpinnerAPI jcxmspinner2onClickListener;
//    private NamesSpinnerAdpater<MsgType> msgsTypesBeanNamesSpinnerAdpater;
//
//
//    private MsgType typesBean;
//    private MsgType msgTypesBean;
//    private MySpinner.MySpinnerAPI spinner1onclick;
    private Dialog loadingDialog;
    private PictureSelectionModel pictureSelectionModel;
    private int selectImageIndex;

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
        return R.layout.activity_add_zgjl;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        recordID = getIntent().getStringExtra("recordID");
        initselectorimg();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????....");
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {
        msgTypes = new ArrayList<>();
        //?????????????????? 1
//        msgTypesadapter = new NamesSpinnerAdpater<MsgType>(this, msgTypes) {
//            @Override
//            public void setinit(MsgType name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getTypeName());
//            }
//        };
//        activityAddZgjlMySpinnerZgxm1.setmyspinnerlistadpater(msgTypesadapter);
//        spinner1onclick = new MySpinner.MySpinnerAPI() {
//            @Override
//            public String onclick(int position) {
//                msgTypesBean = msgTypes.get(position);
//                String typeName = msgTypesBean.getTypeName();
//                List<MsgType> msgsTypesBeanList = msgTypesBean.getMsgTypes();
//                msgsTypesList.clear();
//                if (msgsTypesBeanList != null && msgsTypesBeanList.size() > 0) {
//                    msgsTypesList.addAll(msgsTypesBeanList);
//                    activityAddZgjlMySpinnerZgxm2.setTextViewtext(jcxmspinner2onClickListener.onclick(0));
//                } else {
//                    activityAddZgjlMySpinnerZgxm2.setTextViewtext("??????");
//                }
//                msgsTypesBeanNamesSpinnerAdpater.notifyDataSetChanged();
//                return typeName;
//            }
//        };
//        activityAddZgjlMySpinnerZgxm1.setlistviewitemonclick(spinner1onclick);
//
//        //????????????spinner 2
//        msgsTypesList = new ArrayList<>();
//        msgsTypesBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<MsgType>(this, msgsTypesList) {
//            @Override
//            public void setinit(MsgType name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getTypeName());
//            }
//        };
//        activityAddZgjlMySpinnerZgxm2.setmyspinnerlistadpater(msgsTypesBeanNamesSpinnerAdpater);
//        jcxmspinner2onClickListener = new MySpinner.MySpinnerAPI() {
//            @Override
//            public String onclick(int position) {
//                typesBean = msgsTypesList.get(position);
//                String msgsTypesBeanTypeName = typesBean.getTypeName();
//                List<MsgType> msgsTypesMsgTypes = typesBean.getMsgTypes();
//                return msgsTypesBeanTypeName;
//            }
//        };
//        activityAddZgjlMySpinnerZgxm2.setlistviewitemonclick(jcxmspinner2onClickListener);
    }
    private void initselectorimg() {
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
              //  .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// ????????????gif?????? true or false
                .showCropFrame(false)// ?????????????????????????????? ???????????????????????????false   true or false
                .showCropGrid(false)// ?????????????????????????????? ???????????????????????????false    true or false
                .openClickSound(false)// ???????????????????????? true or false
                .selectionMedia(imglist)// ???????????????????????? List<LocalMedia> list
                .previewEggs(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityAddZgjlRecyclerViewImgs.setLayoutManager(layoutManager);
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
        commonAdapter = new CommonAdapter<LocalMedia>(this, R.layout.faqigtxt_imglist_item, imglist) {

            @Override
            protected void convert(ViewHolder holder, final LocalMedia s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);

                x.image().bind(imageView, s.getPath(), imageOptions);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex = position;
                        GraffitiActivity.startActivityForResult(AddZgjlActivity.this,s.getCompressPath());
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
        activityAddZgjlRecyclerViewImgs.setAdapter(commonAdapter);
    }
    /**
     * ???????????????
     */
    @Override
    public void initData() {
        getspinnerdata();
    }

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }



    @OnClick({R.id.activity_add_zgjl_ImageView_addimg, R.id.activity_add_zgjl_TextView_submit, R.id.activity_add_zgjl_TextView_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_add_zgjl_ImageView_addimg:
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);

                break;
            case R.id.activity_add_zgjl_TextView_submit:
                if (textView.getText().equals("?????????")){
                    PublicUtils.toast("?????????????????????");
                    break;
                }


                if (activityAddZgjlEditTextNrms.getText().toString().trim().length()==0)
                {
                    PublicUtils.toast("????????????????????????");
                    break;
                }
                postjson();
                break;
            case R.id.activity_add_zgjl_TextView_cancel:
                break;
        }
    }
    /**
     * ????????????????????????
     */
    private void getspinnerdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetQualityMsgType", new MyCallBack() {
            @Override
            public void onResule(String res) {

                AqscSpinnerBean spinnerBean = JsonUtils.getbean(res, AqscSpinnerBean.class);
               // msgTypes.clear();
                final List<MsgType> typesBeanList = spinnerBean.getMsgTypes();
                if (typesBeanList != null && typesBeanList.size() > 0) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddZgjlActivity.this, SelectTypesActivity.class);
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
                //msgTypesadapter.notifyDataSetChanged();
            }
        });
    }

    public  void postjson(){
        JSONObject jsonObject=new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
           // jsonObject.put("typeFatherID",msgTypesBean.getTypeID());
           // jsonObject.put("typeID",typesBean.getTypeID());
           // jsonObject.put("typeFatherName",msgTypesBean.getTypeName());
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
           // jsonObject.put("typeName",typesBean.getTypeName());
            jsonObject.put("recordID",recordID);
            jsonObject.put("content",activityAddZgjlEditTextNrms.getText().toString().trim());
            jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
            jsonObject.put("msgTypes", new JSONArray(JsonUtils.objtojson(msgTypes)));
            JSONArray imgfiles = new JSONArray();
            for (int i = 0; i < imglist.size(); i++) {
                LocalMedia localMedia = imglist.get(i);
                JSONObject imgfile = new JSONObject();
                String path = localMedia.getCompressPath();
                imgs.add(path);
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                imgfile.put("fileName", fileName);
                imgfile.put("fileBrief", "???" + (i + 1) + "?????????");
                imgfiles.put(imgfile);
            }
            jsonObject.put("files",imgfiles);
        } catch (JSONException e) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        loadingDialog.show();
        XUtil.updatas(jsonObject,imgs,"AddProcessAcceptanceRefc", new MyCallBack() {
            /**
             * ????????????code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                finish();
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
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    imglist.clear();
                    if (list != null && list.size() > 0) {
                        imglist.addAll(list);
                        commonAdapter.notifyDataSetChanged();
                        activityAddZgjlRecyclerViewImgs.scrollToPosition(imglist.size() - 1);
                    }
                    break;
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
        if (requestCode == SelectTypesActivity.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            textView.setText(strs);
            msgTypes = data.getParcelableArrayListExtra("list");
            //  areaListBeanNamesSpinnerAdpater.replcedata(list);

            // PublicUtils.log(JsonUtils.objtojson(msgTypes));

        }
    }

    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }
}
