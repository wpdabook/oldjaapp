package com.a21zhewang.constructionapp.ui.check;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.inspection.Act_AddTypeInspection;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.GzipUtils;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * 程序检查新增信息页
 * Created by WP-PC on 2019/11/8.
 */

public class Act_AddTypeProcedural extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.save_linear)
    RelativeLayout save_linear;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.rb_noitem)
    RadioButton rb_noitem;
    @BindView(R.id.rb_safe)
    RadioButton rb_safe;
    @BindView(R.id.rb_danger)
    RadioButton rb_danger;
    @BindView(R.id.ll_noitem)
    LinearLayout ll_noitem;
    @BindView(R.id.ll_safe)
    LinearLayout ll_safe;
    @BindView(R.id.ll_danger)
    LinearLayout ll_danger;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.edit_noitem)
    EditText et_noitem;
    @BindView(R.id.et_safe_content)
    EditText et_safe;
    @BindView(R.id.et_danger_content)
    EditText et_danger;
    @BindView(R.id.safe_recyclerview)
    RecyclerView safe_recyclerview;
    @BindView(R.id.danger_recyclerview)
    RecyclerView danger_recyclerview;
    private int status = 1;
    private JSONObject commitObject;
    private JSONArray MsgTypeArray1;
    private JSONArray MsgTypeArray2;
    private JSONArray FileArray2 = new JSONArray();
    private JSONArray FileArray3 = new JSONArray();
    private JSONObject fileObject;
    private JSONObject tempObject1;
    private JSONObject tempObject2;
    //    private int type = 0;
    private String projectID;
    private String proShortName;
    private String recordId;
    private String typeName;
    private String sectionId;
    private String itemName;
    private String itemID;
    private String spinnerValue;
    private RequestOptions requestOptions2;
    private RequestOptions requestOptions3;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter2;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter3;
    private Context context;
    private List<LocalMedia> imgLists2 = new ArrayList<>();
    private List<LocalMedia> imgLists3= new ArrayList<>();
    private List<String> files2;
    private List<String> files3;
    private int selectImageIndex2;
    private int selectImageIndex3;
    private int position;
    private List<String> spinnerlist = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private int selectTag = 0;
    private int one_picitem_position = 0;
    private int two_picitem_position = 0;
    private String xPoint,yPoint;
    private Dialog loadingDialog;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_addtype_procedural;
    }

    @Override
    public void initViews() {
        getSpinner();
        getLocation();
        title.setText("新增记录");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "保存中...");
        rg.setOnCheckedChangeListener(this);
        save_linear.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDate(status);
            }
        });
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.planets_array, R.layout.item_spinner_defalut);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_defalut,spinnerlist);
        adapter = new ArrayAdapter<String>(Act_AddTypeProcedural.this,R.layout.item_spinner_defalut,spinnerlist);
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                spinnerValue = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        initSafeViewRecylerViewData();
        initDangerViewRecylerViewData();
    }
    public void getSpinner(){
        XUtil.postjsondata(JsonUtils.getjsonobj(""), "GetReasonList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try{
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("recordList");
                        for(int j=0;j<array.length();j++){
                            JSONObject tempobject = array.optJSONObject(j);
                            spinnerlist.add(tempobject.optString("dicName"));
                        }
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
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
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (checkedId == R.id.rb_noitem) {
            status = 0;
            ll_noitem.setVisibility(View.VISIBLE);
            ll_safe.setVisibility(View.GONE);
            ll_danger.setVisibility(View.GONE);
        } else if (checkedId == R.id.rb_safe) {
            status = 1;
            ll_noitem.setVisibility(View.GONE);
            ll_safe.setVisibility(View.VISIBLE);
            ll_danger.setVisibility(View.GONE);
        } else if (checkedId == R.id.rb_danger) {
            status = 2;
            ll_noitem.setVisibility(View.GONE);
            ll_safe.setVisibility(View.GONE);
            ll_danger.setVisibility(View.VISIBLE);
        }
    }
    /*
     * 上传安全图片
     */
    public void initSafeViewRecylerViewData(){
        requestOptions2 = new RequestOptions().centerCrop();
        safe_recyclerview.setLayoutManager(new GridLayoutManager(context, 4));
        baseQuickAdapter2 = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(imgLists2) {
            @Override
            protected void convert(final BaseViewHolder helper, final LocalMedia imgpath) {
                ImageView imgview = helper.getView(R.id.fiv);
                if ("addimg2".equals(imgpath.getPath())) {
                    imgview.setImageResource(R.mipmap.addimgs);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            remove(getData().size()-1);
                            one_picitem_position  = getData().size()-1;
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
                            selectImageIndex2 = helper.getLayoutPosition()-getHeaderLayoutCount();
                            GraffitiActivity.startActivityForResult((Activity) mContext, imgpath.getCompressPath());
                        }
                    });
                    Glide.with(mContext)
                            .load(imgpath.getCompressPath()).apply(requestOptions2)
                            .into(imgview);
                }
            }
        };
        LocalMedia media = new LocalMedia();
        media.setPath("addimg2");
        media.setChecked(true);
        baseQuickAdapter2.addData(media);
        MultiTypeDelegate multiTypeDelegate = new MultiTypeDelegate<LocalMedia>() {
            @Override
            protected int getItemType(LocalMedia o) {
                return 0;
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.newcheckquestion_list_imgitem);
        baseQuickAdapter2.setMultiTypeDelegate(multiTypeDelegate);
        safe_recyclerview.setAdapter(baseQuickAdapter2);
    }
    public void initAlert(){
        new ActionSheetDialog(Act_AddTypeProcedural.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("图库", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                PictureSelector();
                            }
                        })
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                PictureCarmerSelector();
                            }
                        }).show();
    }
    public void initDangerAlert(){
        new ActionSheetDialog(Act_AddTypeProcedural.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("图库", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                DangerPictureSelector();
                            }
                        })
                .addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                DangerPictureCarmerSelector();
                            }
                        }).show();
    }
    /**
     * 安全跳转图库
     */
    public void PictureSelector(){
        PictureSelector.create(Act_AddTypeProcedural.this).openGallery(PictureMimeType.ofImage())
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
                .selectionMedia(imgLists2)
                .previewEggs(true)
                .compress(true).forResult(133);
        baseQuickAdapter2.remove(one_picitem_position);
    }
    /**
     * 安全跳转相机
     */
    public void PictureCarmerSelector(){
        PictureSelector.create(Act_AddTypeProcedural.this).openCamera(PictureMimeType.ofImage())
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
                .selectionMedia(imgLists2)
                .previewEggs(true)
                .compress(true).forResult(130);
        baseQuickAdapter2.remove(one_picitem_position);
    }
    /**
     * 有隐患跳转图库
     */
    public void DangerPictureSelector(){
        PictureSelector.create(Act_AddTypeProcedural.this).openGallery(PictureMimeType.ofImage())
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
                .selectionMedia(imgLists3)
                .previewEggs(true)
                .compress(true).forResult(135);
        baseQuickAdapter3.remove(two_picitem_position);
    }
    /**
     * 有隐患跳转相机
     */
    public void DangerPictureCarmerSelector(){
        PictureSelector.create(Act_AddTypeProcedural.this).openCamera(PictureMimeType.ofImage())
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
                .selectionMedia(imgLists3)
                .previewEggs(true)
                .compress(true).forResult(131);
        baseQuickAdapter3.remove(two_picitem_position);
    }
    /**
    /**
     * 上传隐患图片
     */
    public void initDangerViewRecylerViewData(){
        requestOptions3 = new RequestOptions().centerCrop();
        danger_recyclerview.setLayoutManager(new GridLayoutManager(context, 4));
        baseQuickAdapter3 = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(imgLists3) {
            @Override
            protected void convert(final BaseViewHolder helper, final LocalMedia imgpath) {
                ImageView imgview = helper.getView(R.id.fiv);
                if ("addimg3".equals(imgpath.getPath())) {
                    imgview.setImageResource(R.mipmap.addimgs);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            remove(getData().size()-1);
                            two_picitem_position  = getData().size()-1;
                            if(selectTag == 0){
                                initDangerAlert();
                            }
                            if(selectTag == 1){
                                DangerPictureSelector();
                            }
                            if(selectTag == 2){
                                DangerPictureCarmerSelector();
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
                            selectImageIndex3 = helper.getLayoutPosition()-getHeaderLayoutCount();
                            GraffitiActivity.startActivityForResult((Activity) mContext, imgpath.getCompressPath());
                        }
                    });
                    Glide.with(mContext)
                            .load(imgpath.getCompressPath()).apply(requestOptions3)
                            .into(imgview);
                }
            }
        };
        LocalMedia media = new LocalMedia();
        media.setPath("addimg3");
        media.setChecked(true);
        baseQuickAdapter3.addData(media);
        MultiTypeDelegate multiTypeDelegate = new MultiTypeDelegate<LocalMedia>() {
            @Override
            protected int getItemType(LocalMedia o) {
                return 0;
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.newcheckquestion_list_imgitem);
        baseQuickAdapter3.setMultiTypeDelegate(multiTypeDelegate);
        danger_recyclerview.setAdapter(baseQuickAdapter3);

    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        position = getIntent().getIntExtra("position",0);
        status = getIntent().getIntExtra("checkStatus",0);
        projectID = getIntent().getStringExtra("projectID");
        sectionId = getIntent().getStringExtra("sectionId");
        proShortName = getIntent().getStringExtra("proShortName");
        recordId = getIntent().getStringExtra("recordId");
        typeName = getIntent().getStringExtra("typeName");
        itemName = getIntent().getStringExtra("itemName");
        itemID = getIntent().getStringExtra("itemID");
        if(status == 0){
            rb_noitem.setChecked(true);
            status = 0;
        }
        if(status == 1 || status == -1){  //传1：安全 -1：未检出 默认安全选项
            rb_safe.setChecked(true);
            status = 1;
        }
        if(status == 2){
            rb_danger.setChecked(true);
            status = 2;
        }
    }

    /**
     * 保存信息
     * object 需提交的数据
     * status 回传的数据
     */
    public void submitDate(int status){
        JSONObject object = getjsonArray(status);
        if(status == 0){
            if(TextUtils.isEmpty(object.optString("reason"))){
                showToast("请选择无此项原因");
                return;
            }
        }else {
            if(TextUtils.isEmpty(object.optString("content")) && object.optJSONArray("files").length() == 0){
                showToast("请添加描述信息或图片");
                return;
            }
        }
        loadingDialog.show();
        save_linear.setVisibility(View.GONE);
        if(status == 0){
            postjson(object,status);
        }
        if(status == 1){
            postJsonAndFile(object.toString(),files2,"SaveInspectionItemResult",status);
        }
        if(status == 2){
            postJsonAndFile(object.toString(),files3,"SaveInspectionItemResult",status);
        }
    }

    /**
     * 无此项提交
     * object 需提交的数据
     * status 回传的数据
     */
    private void postjson(JSONObject object, final int status){
        XUtil.postjsondata(object, "SaveInspectionItemResult", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
//                showToast("保存成功");
                loadingDialog.dismiss();
                save_linear.setVisibility(View.VISIBLE);
                Intent data = new Intent();
                data.putExtra("status", status);
                data.putExtra("position", position);
                setResult(RESULT_OK, data);
                Act_AddTypeProcedural.this.finish();
            }
            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }
    /**
     * 安全|有隐患 JSON+FILE提交
     * object 需提交的数据
     * status 回传的数据
     */
    private void postJsonAndFile(String json, List<String> files, String method, final int status) {
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            @Override
            public void onResule(String res) {
//                showToast("保存成功");
                loadingDialog.dismiss();
                save_linear.setVisibility(View.VISIBLE);
                Intent data = new Intent();
                data.putExtra("status", status);
                data.putExtra("position", position);
                setResult(RESULT_OK, data);
                Act_AddTypeProcedural.this.finish();
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
    /**
     * 上行参数
     * @param status
     * @return
     */
    private JSONObject getjsonArray(int status) {
        try {
            files2 = new ArrayList<String>();
            files3 = new ArrayList<String>();
            if(FileArray2.length()>0){
                FileArray2 = new JSONArray();
            }
            if(FileArray3.length()>0){
                FileArray3 = new JSONArray();
            }
            commitObject = new JSONObject();
            tempObject1 = new JSONObject();
            tempObject2 = new JSONObject();
            fileObject = new JSONObject();
            MsgTypeArray1 = new JSONArray();
            MsgTypeArray2 = new JSONArray();
            commitObject.put("etpID", PublicUtils.etpID);
            commitObject.put("etpShortName", PublicUtils.etpShortName);
            commitObject.put("createUserTypeID", PublicUtils.userTypeID);
            commitObject.put("createUserType", PublicUtils.userType);
            commitObject.put("createUserID", PublicUtils.UserID);
            commitObject.put("createUserName", PublicUtils.userName);
            commitObject.put("projectID", projectID);
            commitObject.put("projectShortName", proShortName);
            commitObject.put("recordID", recordId);
            commitObject.put("type", status);
            commitObject.put("reason", spinnerValue);
            tempObject2.put("typeName", itemName);
            tempObject2.put("typeID", itemID);
            if(imgLists2.size() != 1){
                for (int i = 0; i < imgLists2.size(); i++) {
                    JSONObject object = new JSONObject();
                    String path = imgLists2.get(i).getCompressPath();
                    if(path != null){
                        if(selectTag == 1){
                            if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+ DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }else {
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }
                        }
                        if(selectTag == 2){
                            if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }else {
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }
                        }
                        String fileName = path.substring(path.lastIndexOf("/") + 1);
                        object.put("fileBrief", "第" + (i + 1) + "张");
                        object.put("fileName", fileName);
                        files2.add(path);
                        FileArray2.put(object);
                    }
                }
            }
            if(imgLists3.size() != 1){
                for (int i = 0; i < imgLists3.size(); i++) {
                    JSONObject object = new JSONObject();
                    String path = imgLists3.get(i).getCompressPath();
                    if(path != null){
                        if(selectTag == 1){
                            if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+ DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }else {
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }
                        }
                        if(selectTag == 2){
                            if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }else {
                                Bitmap bitmap = SendImageUtil.createWatermark(Act_AddTypeProcedural.this,
                                        path,"工程名称："+proShortName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,path);
                            }
                        }
                        String fileName = path.substring(path.lastIndexOf("/") + 1);
                        object.put("fileBrief", "第" + (i + 1) + "张");
                        object.put("fileName", fileName);
                        files3.add(path);
                        FileArray3.put(object);
                    }
                }
            }
            if(status == 0){
                commitObject.put("content", et_noitem.getText().toString());
            }
            if (status == 1) {
                commitObject.put("content", et_safe.getText().toString());
                commitObject.put("files", FileArray2);
            }
            if (status == 2) {
                commitObject.put("content", et_danger.getText().toString());
                commitObject.put("files", FileArray3);
            }
            MsgTypeArray2.put(tempObject2);
            tempObject1.put("msgTypes",MsgTypeArray2);
            tempObject1.put("typeName",typeName);
            tempObject1.put("typeID",sectionId);
            MsgTypeArray1.put(tempObject1);
            commitObject.put("msgTypes", MsgTypeArray1);
        }catch (JSONException e){
            return commitObject;
        }
        return commitObject;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 133) {
            selectTag = 1;
            List<LocalMedia> list2 = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter2.replaceData(list2);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 130) { //安全相机
            selectTag = 2;
            List<LocalMedia> list2 = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter2.replaceData(list2);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 135) {
            selectTag = 1;
            List<LocalMedia> list3 = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter3.replaceData(list3);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 131) { //有隐患相机
            selectTag = 2;
            List<LocalMedia> list3 = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter3.replaceData(list3);
        }
        if (requestCode == 133 || requestCode == 130){
            LocalMedia media2 = new LocalMedia();
            media2.setPath("addimg2");
            baseQuickAdapter2.addData(media2);
        }
        if (requestCode == 135 || requestCode == 131){
            LocalMedia media3 = new LocalMedia();
            media3.setPath("addimg3");
            baseQuickAdapter3.addData(media3);
        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == Activity.RESULT_OK && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia media2 = new LocalMedia();
            media2.setPath(imgpath);
            media2.setCompressPath(imgpath);
            baseQuickAdapter2.setData(selectImageIndex2,media2);
        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == Activity.RESULT_OK && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia media3 = new LocalMedia();
            media3.setPath(imgpath);
            media3.setCompressPath(imgpath);
            baseQuickAdapter3.setData(selectImageIndex3,media3);
        }
    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_AddTypeProcedural.this ).showLocation();
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
