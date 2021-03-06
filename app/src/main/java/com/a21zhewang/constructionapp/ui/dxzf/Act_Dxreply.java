package com.a21zhewang.constructionapp.ui.dxzf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.SendImageUtil;
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
import com.luck.picture.lib.tools.PictureFileUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * ????????????-????????????
 * Created by Administrator on 2020/8/18.
 */

 public  class Act_Dxreply extends BaseActivity {
    @BindView(R.id.dx_commit)
    RelativeLayout commit;
    @BindView(R.id.dx_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.reply_content)
    EditText reply_content;
    private JSONObject commitObject;
    private JSONArray  FileArray = new JSONArray();
    private Dialog loadingDialog;
    private List<String> files;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private RequestOptions requestOptions;
    private int selectImageIndex;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private String proShortName;
    private String xPoint,yPoint;
    private int position = 0;
    private int selectTag = 0;
    private String id;
    private String createUserName;
    private int checkType;
    private String CheckStatus;
    public final static int RESQUST_CODE_REPLY = 5620;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_dxreply;
    }

    @Override
    public void initViews() {
        initViewRecylerViewData();
        id = getIntent().getStringExtra("id");
        checkType = getIntent().getIntExtra("checkType",-1);
        CheckStatus = getIntent().getStringExtra("CheckStatus");
        proShortName = getIntent().getStringExtra("proShortName");
        createUserName = getIntent().getStringExtra("createUserName");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "?????????...");
    }
    /*
       * ??????????????????
       */
    public void initViewRecylerViewData(){
        getLocation();
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
    public void initAlert(){
        new ActionSheetDialog(Act_Dxreply.this)
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
        PictureSelector.create(Act_Dxreply.this).openGallery(PictureMimeType.ofImage())
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
        PictureSelector.create(Act_Dxreply.this).openCamera(PictureMimeType.ofImage())
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
    /**
     * ????????????
     * @return
     */
    private JSONObject getjsonArray() {
        try {
            commitObject = new JSONObject();
            commitObject.put("id", id);
            commitObject.put("reply", reply_content.getText().toString());
            files = new ArrayList<String>();
            if (baseQuickAdapter.getData().size() > 0) {
                for (int i = 0; i < baseQuickAdapter.getData().size()-1; i++) {
                    JSONObject object = new JSONObject();
                    String path = baseQuickAdapter.getData().get(i).getCompressPath();
                    if(selectTag == 1){
                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_Dxreply.this,
                                    path,"???????????????"+proShortName+"\n"+"???????????????"+createUserName+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }else {
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_Dxreply.this,
                                    path,"???????????????"+proShortName+"\n"+"???????????????"+createUserName+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }
                    }
                    if(selectTag == 2){
                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_Dxreply.this,
                                    path,"???????????????"+proShortName+"\n"+"???????????????"+createUserName+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }else {
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_Dxreply.this,
                                    path,"???????????????"+proShortName+"\n"+"???????????????"+createUserName+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }
                    }
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    object.put("fileBrief", "???" + (i + 1) + "???");
                    object.put("fileName", fileName);
                    files.add(path);
                    FileArray.put(object);
                }
            }
            commitObject.put("files", FileArray);
        }catch (JSONException e){
            return commitObject;
        }
        return commitObject;
    }
    /**
     * JSON+FILE??????
     * object ??????????????????
     * status ???????????????
     */
    private void postJsonDate(String json, List<String> files, String method) {
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                Act_Dxreply.this.finish();
                showToast("????????????");
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*** ????????????????????????????????????view?????????????????????????????????* */
        if(data == null && requestCode != GraffitiActivity.REQ_CODE_GRAFFITI){
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 166 && data != null) {
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
            files = new ArrayList<String>();
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if(requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == Activity.RESULT_OK && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia media = new LocalMedia();
            media.setPath(imgpath);
            media.setCompressPath(imgpath);
            baseQuickAdapter.setData(selectImageIndex,media);
        }
    }
    @Override
    public void initListener() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject object = getjsonArray();
                if(TextUtils.isEmpty(reply_content.getText().toString())){
                    showToast("?????????????????????");
                    return;
                }
                if(FileArray.length() == 0){
                    showToast("???????????????");
                    return;
                }
                loadingDialog.show();
                postJsonDate(object.toString(),files,"EditSuperviseManage_Reply");
            }
        });
    }

    @Override
    public void initData() {
    }
    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_Dxreply.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }
}
