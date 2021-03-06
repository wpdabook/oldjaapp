package com.a21zhewang.constructionapp.ui.task;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * Created by Administrator on 2021/3/18.
 */

public class Act_TaskToDo extends BaseActivity {

    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.edit_des)
    EditText edit_des;
    @BindView(R.id.zddb_todo_recyclerView)
    RecyclerView recyclerview;
    private ImageOptions imageOptions;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private RequestOptions requestOptions;
    private int selectImageIndex;
    private int position = 0;
    private int selectTag = 0;
    private JSONArray  FileArray = new JSONArray();
    private Dialog loadingDialog;
    private String recordId;
    private String xPoint,yPoint;
    private String projectName;
    private int status;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_tasktodo_layout;
    }

    @Override
    public void initViews() {
        righttext.setTextSize(16);
        getLocation();
        projectName = getIntent().getStringExtra("projectName");
        recordId = getIntent().getStringExtra("recordId");
        status = getIntent().getIntExtra("status",0);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
        requestOptions = new RequestOptions().centerCrop();
        recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        baseQuickAdapter = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(imgLists) {
            @Override
            protected void convert(final BaseViewHolder helper, final LocalMedia imgpath) {
                ImageView imgview = helper.getView(R.id.fiv);
                if ("addimg".equals(imgpath.getPath())) {
                    imgview.setImageResource(R.mipmap.addimgs);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /**
                             * ????????????????????????????????????view?????????????????????????????????
                             * */
                            position = getData().size()-1;
                            if(selectTag == 0){
                                initAlert();
                            }
                            if(selectTag == 1){
                                PictureSelector();
                            }
                            if(selectTag == 2){
                                PictureCarmerSelector();
                            }
                        }
                    });
                    helper.setVisible(R.id.iv_del, false);
                } else {
                    helper.setVisible(R.id.iv_del, true);
                    helper.getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remove(helper.getLayoutPosition()-getHeaderLayoutCount());
                        }
                    });
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectImageIndex = helper.getLayoutPosition()-getHeaderLayoutCount();
                            GraffitiActivity.startActivityForResult((Activity) mContext, imgpath.getCompressPath());
                        }
                    });
                    Glide.with(mContext)
                            .load(imgpath.getCompressPath()).apply(requestOptions)
                            .into(imgview);
                }
            }
        };
        LocalMedia media = new LocalMedia();
        media.setPath("addimg");
        media.setChecked(true);
        baseQuickAdapter.addData(media);
        MultiTypeDelegate multiTypeDelegate = new MultiTypeDelegate<LocalMedia>() {
            @Override
            protected int getItemType(LocalMedia o) {
                return 0;
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.newcheckquestion_list_imgitem);
        baseQuickAdapter.setMultiTypeDelegate(multiTypeDelegate);
        recyclerview.setAdapter(baseQuickAdapter);
    }
    public  void postData(){
//        if(TextUtils.isEmpty(edit_des.getText().toString())){
//            PublicUtils.toast("????????????????????????????????????");
//            return;
//        }
        JSONObject jsonObject = new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
            for (int i = 0; i < imgLists.size(); i++) {
                LocalMedia localMedia = imgLists.get(i);
                JSONObject imgfile = new JSONObject();
                String path = localMedia.getCompressPath();
                if(path != null){
//                    if(selectTag == 1){
//                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_TaskToDo.this,
//                                    path,"???????????????"+projectName+"\n"+"???????????????"+PublicUtils.userName+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+ DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }else {
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_TaskToDo.this,
//                                    path,"???????????????"+projectName+"\n"+"???????????????"+PublicUtils.userName+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }
//                    }
//                    if(selectTag == 2){
//                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_TaskToDo.this,
//                                    path,"???????????????"+projectName+"\n"+"???????????????"+PublicUtils.userName+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }else {
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_TaskToDo.this,
//                                    path,"???????????????"+projectName+"\n"+"???????????????"+PublicUtils.userName+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }
//                    }
                    imgs.add(path);
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    imgfile.put("fileName", fileName);
                    imgfile.put("fileBrief", "???" + (i + 1) + "???");
                    FileArray.put(imgfile);
                }
            }
            jsonObject.put("recordId",recordId);
            jsonObject.put("content",edit_des.getText().toString());
            jsonObject.put("status",status);
            jsonObject.put("files", FileArray);
        } catch (JSONException e) {
            PublicUtils.toast("json?????????????????????");
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                showToast("????????????");
                righttext.setVisibility(View.VISIBLE);
                Act_TaskToDo.this.finish();
            }
            @Override
            public void onFail(ObjBean getbean) {
                righttext.setVisibility(View.VISIBLE);
                loadingDialog.dismiss();
            }
            @Override
            public void onWrong() {
                loadingDialog.dismiss();
            }
        };
//        if(imgs.size() == 0){
//            showToast("???????????????");
//            return;
//        }
        if(TextUtils.isEmpty(edit_des.getText().toString()) && imgs.size() == 0){
            PublicUtils.toast("???????????????????????????????????????????????????");
            return;
        }
        loadingDialog.show();
        righttext.setVisibility(View.GONE);
        XUtil.updatassj(jsonObject, imgs, "AddTaskSuperviseDetail", callback);
    }
    public void initAlert(){
        new ActionSheetDialog(Act_TaskToDo.this)
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
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
    /**
     * ????????????
     */
    public void PictureSelector(){
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .previewVideo(false)
                .enablePreviewAudio(false)
                .isCamera(true)
                .isZoomAnim(true)
                .sizeMultiplier(0.5f)
                .enableCrop(false)
                .compress(true)
                .isGif(false)
                .showCropFrame(false)
                .showCropGrid(false)
                .openClickSound(false)
                .selectionMedia(imgLists)
                .previewEggs(true)
                .compress(true).forResult(166);
        baseQuickAdapter.remove(position);
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
                .selectionMedia(imgLists)
                .previewEggs(true)
                .compress(true).forResult(PictureConfig.CAMERA);
        baseQuickAdapter.remove(position);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*** ????????????????????????????????????view?????????????????????????????????* */
        if(data == null && requestCode != GraffitiActivity.REQ_CODE_GRAFFITI && requestCode != 520 && requestCode != 521 && requestCode != 739){
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (resultCode == RESULT_OK && requestCode == 166 && data != null) {
            selectTag = 1;
            if(FileArray.length()>0){
                FileArray = new JSONArray();
            }
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter.replaceData(list);
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (requestCode == PictureConfig.CAMERA
                && resultCode == Activity.RESULT_OK && data != null) {
            selectTag = 2;
            if(FileArray.length()>0){
                FileArray = new JSONArray();
            }
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter.replaceData(list);
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == RESULT_OK
                && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia media = new LocalMedia();
            media.setPath(imgpath);
            media.setCompressPath(imgpath);
            baseQuickAdapter.setData(selectImageIndex,media);
        }
    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_TaskToDo.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }
    @Override
    public void processClick(View v) {

    }
}
