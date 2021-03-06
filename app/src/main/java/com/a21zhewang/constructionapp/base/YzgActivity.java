package com.a21zhewang.constructionapp.base;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcProjectInfoActivity;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.FileUtil;
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
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

public class YzgActivity extends BaseActivity {

    @BindView(R.id.activity_yzg_EditText_nrms)
    EditText activityYzgEditTextNrms;
    @BindView(R.id.activity_yzg_RecyclerView_imglist)
    RecyclerView activityYzgRecyclerViewImglist;




    private ImageOptions imageOptions;
    private List<LocalMedia> imglist;
    private CommonAdapter<LocalMedia> commonAdapter;
    private int selectImageIndex;
    private String recordID;
    private String postdatamethod;
    private Dialog loadingDialog;
    private PictureSelectionModel pictureSelectionModel;
    private int position = 0;
    private int selectTag = 0;
    private String projectShortName;
    private String xPoint,yPoint;

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
        return R.layout.activity_yzg;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "????????????");
        Intent intent = getIntent();
        recordID =  intent.getStringExtra("recordID");
        xPoint = intent.getStringExtra("xPoint");
        yPoint = intent.getStringExtra("yPoint");
        projectShortName = intent.getStringExtra("projectShortName");
        postdatamethod = intent.getStringExtra("postdatamethod");

    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {
        initselectorimg();
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {

    }

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }

    private void initselectorimg() {

        imglist = new ArrayList<>();
        //????????????????????????
//????????????
//?????????????????????
// ???????????? ????????????
//????????????????????????
        // ???????????????????????? int
// ?????????????????? int
// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
// ????????????????????? true or false
// ????????????????????? true or false
// ????????????????????? true or false
// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
// ???????????????????????? true or false
// ?????????????????? ???????????? ??????true
// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
// ???????????? true or false
// ???????????? true or false
//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
// ????????????gif?????? true or false
// ?????????????????????????????? ???????????????????????????false   true or false
// ?????????????????????????????? ???????????????????????????false    true or false
// ???????????????????????? true or false
// ???????????????????????? List<LocalMedia> list



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityYzgRecyclerViewImglist.setLayoutManager(layoutManager);
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
                      //  pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex=position;
                        GraffitiActivity.startActivityForResult(YzgActivity.this,s.getCompressPath());
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
        activityYzgRecyclerViewImglist.setAdapter(commonAdapter);
    }


    @OnClick({R.id.activity_yzg_img_addimg, R.id.activity_yzg_tv_tj})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.activity_yzg_img_addimg:
                if(selectTag == 0){
                    initAlert();
                }
                if(selectTag == 1){
                    PictureSelector();
                }
                if(selectTag == 2){
                    PictureCarmerSelector();
                }
                break;
            case R.id.activity_yzg_tv_tj:

                if (postdatamethod.equals("GetProcessAcceptanceDetailsButton")){
                    postjsondata("finishBtn");
                    break;
                }
                postjsondata("cmBtn2");
                break;
        }
    }
    public void initAlert(){
        new ActionSheetDialog(YzgActivity.this)
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
                .maxSelectNum(5)// ???????????????????????? int
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
    public void postjsondata(String btnid) {
        String content = activityYzgEditTextNrms.getText().toString();
        if (content.trim().length()==0){
            PublicUtils.toast("????????????????????????");
            return;
        }
        JSONObject jsonObject=new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
            jsonObject.put("btnID", btnid);
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
            jsonObject.put("dealContent", content);
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("recordID", recordID);
            JSONArray imgfiles = new JSONArray();
            for (int i = 0; i < imglist.size(); i++) {
                LocalMedia localMedia = imglist.get(i);
                JSONObject imgfile = new JSONObject();
                String path = localMedia.getCompressPath();
                if(selectTag == 1){
                    if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                        Bitmap bitmap = SendImageUtil.createWatermark(YzgActivity.this,
                                path,"???????????????"+projectShortName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                        SendImageUtil.saveImage(bitmap,path);
                    }else {
                        Bitmap bitmap = SendImageUtil.createWatermark(YzgActivity.this,
                                path,"???????????????"+projectShortName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                        SendImageUtil.saveImage(bitmap,path);
                    }
                }
                if(selectTag == 2){
                    if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                        Bitmap bitmap = SendImageUtil.createWatermark(YzgActivity.this,
                                path,"???????????????"+projectShortName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                        SendImageUtil.saveImage(bitmap,path);
                    }else {
                        Bitmap bitmap = SendImageUtil.createWatermark(YzgActivity.this,
                                path,"???????????????"+projectShortName+"\n"+"???????????????"+PublicUtils.userBean.getUserName()+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                        SendImageUtil.saveImage(bitmap,path);
                    }
                }
                imgs.add(path);
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                imgfile.put("fileName", fileName);
                imgfile.put("fileBrief", "???" + (i + 1) + "?????????");
                imgfiles.put(imgfile);
            }
            jsonObject.put("files", imgfiles);
        }catch (Exception e){
            PublicUtils.toast("?????????????????????");
            return;
        }
        loadingDialog.show();
        XUtil.updatas(
                jsonObject,imgs,postdatamethod,
                new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        WeiboDialogUtils.closeDialog(loadingDialog);
                       finish();
                    }

                    @Override
                    public void onFail(ObjBean getbean) {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onWrong() {
                        loadingDialog.dismiss();
                    }

                });
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
                        activityYzgRecyclerViewImglist.scrollToPosition(imglist.size() - 1);
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
                        activityYzgRecyclerViewImglist.scrollToPosition(imglist.size() - 1);
                    }
                    PublicUtils.log("???????????????" + clist.size() + "???");
                    break;
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
    }

    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }
}
