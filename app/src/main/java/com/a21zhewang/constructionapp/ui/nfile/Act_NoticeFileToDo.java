package com.a21zhewang.constructionapp.ui.nfile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.JsonUtils;
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
 * Created by Administrator on 2020/12/21.
 */

public class Act_NoticeFileToDo extends BaseActivity {
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.diff_rg)
    RadioGroup radioGroup;
    @BindView(R.id.rg_1)
    RadioButton rg1;
    @BindView(R.id.rg_2)
    RadioButton rg2;
    @BindView(R.id.lin_toother)
    LinearLayout lin_toother;
    @BindView(R.id.lin_todo)
    LinearLayout lin_todo;
    @BindView(R.id.tv_companyname)
    TextView tv_companyname;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.reply_content)
    EditText et_content;
    @BindView(R.id.diff_recyclerview)
    RecyclerView recyclerview;
    public final static int RESQUST_CODE_REPLY = 5820;
    private List<String> spinnerlist = new ArrayList<String>();
    private List<String> spinner2list = new ArrayList<String>();
    private List<String> spinnerIdlist = new ArrayList<String>();
    private List<String> spinner2Idlist = new ArrayList<String>();
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private String XiaFaUserName1;
    private String XiaFaUserID1;
    private String XiaFaUserName2;
    private String XiaFaUserID2;
    private String Nid = "";
    private int tag = 1; //1:下发  0：回复
    private ImageOptions imageOptions;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private RequestOptions requestOptions;
    private int selectImageIndex;
    private int position = 0;
    private int selectTag = 0;
    private Dialog loadingDialog;
    private String xPoint,yPoint;
    private String taskStatus;
    private String AreaTaskID;
    private JSONArray  FileArray = new JSONArray();
    private String HandleUnitName;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_noticefile_todo;
    }

    @Override
    public void initViews() {
        getLocation();
        Nid = getIntent().getStringExtra("Nid");
        taskStatus = getIntent().getStringExtra("taskStatus");
        HandleUnitName = getIntent().getStringExtra("HandleUnitName");
        AreaTaskID = getIntent().getStringExtra("AreaTaskID");
        if("已下发".equals(taskStatus) || !HandleUnitName.contains("建管站")) { //（平台公司不能下发 只能处理）
            tag = 0;
            radioGroup.setVisibility(View.GONE);
            lin_toother.setVisibility(View.GONE);
            lin_todo.setVisibility(View.VISIBLE);
        }
        righttext.setTextSize(15);
        tv_companyname.setText(PublicUtils.etpShortName);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        adapter1 = new ArrayAdapter<String>(Act_NoticeFileToDo.this,R.layout.item_spinner_defalut,spinnerlist);
        adapter2 = new ArrayAdapter<String>(Act_NoticeFileToDo.this,R.layout.item_spinner_defalut,spinner2list);
        adapter1.setDropDownViewResource(R.layout.item_spinner_dropdown);
        adapter2.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                XiaFaUserName1 = arg0.getItemAtPosition(arg2).toString();
                XiaFaUserID1 = spinnerIdlist.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                XiaFaUserName2 = arg0.getItemAtPosition(arg2).toString();
                XiaFaUserID2 = spinner2Idlist.get(arg2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag == 1){  //1：下发  0：处理
                    postData(1);
                }else {
                    postData(0);
                }
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
                             * 处理相册和相机返回按钮，view中添加图片按钮刷新问题
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

    @Override
    public void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rg_1) {
                    tag = 1;
                    lin_toother.setVisibility(View.VISIBLE);
                    lin_todo.setVisibility(View.GONE);
                    et_content.setText("");
                }else {
                    tag = 0;
                    lin_toother.setVisibility(View.GONE);
                    lin_todo.setVisibility(View.VISIBLE);
                    et_content.setText("");
                }
            }
        });
    }
    public  void postData(int type){
        JSONObject jsonObject = new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
            for (int i = 0; i < imgLists.size(); i++) {
                LocalMedia localMedia = imgLists.get(i);
                JSONObject imgfile = new JSONObject();
                String path = localMedia.getCompressPath();
                if(path != null){
                    imgs.add(path);
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    imgfile.put("fileName", fileName);
                    imgfile.put("fileBrief", XiaFaUserName2);
                    FileArray.put(imgfile);
                }
            }
            jsonObject.put("Nid",Nid);
            jsonObject.put("IsXiaFa",type);
            jsonObject.put("AreaTaskID",AreaTaskID);
            if(type == 1){  //1：下发  0：处理
                jsonObject.put("XiaFaContent",et_content.getText().toString());
                if("".equals(XiaFaUserID1) && "".equals(XiaFaUserName1)){
                    showToast("请选择处理人员");
                    return;
                }
                jsonObject.put("XiaFaUserID",XiaFaUserID1);
                jsonObject.put("XiaFaUserName",XiaFaUserName1);
            }else {
                if(TextUtils.isEmpty(et_content.getText().toString()) && imgs.size() == 0 ){
                    showToast("请添加描述信息或图片");
                    return;
                }
                jsonObject.put("HandleContent",et_content.getText().toString());
                jsonObject.put("files", FileArray);
            }
        } catch (JSONException e) {
            PublicUtils.toast("json数据生产错误！");
        }
        loadingDialog.show();
        righttext.setVisibility(View.GONE);
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                righttext.setVisibility(View.VISIBLE);
                startActivity(new Intent(Act_NoticeFileToDo.this,Act_NoticeFile.class));
                Act_NoticeFileToDo.this.finish();
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
        if(type == 1){ //1：下发  0：处理
            XUtil.postjsondatasj(jsonObject,"HandleNotification", callback);
        }else {
            XUtil.updatassj(jsonObject, imgs, "HandleNotification", callback);
        }
    }
    @Override
    public void initData() {
        XUtil.postjsondatasj(JsonUtils.getjsonobj("{\"HandleUnitName\":\"" + HandleUnitName + "\"}"), "GetNotificationXiaFaList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResule(String res) {
                try{
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("UserList");
                    JSONArray typearray = object.optJSONArray("HandleTypeList");
                    for(int j=0;j<array.length();j++){
                        JSONObject tempobject = array.optJSONObject(j);
                        spinnerlist.add(tempobject.optString("UserName"));
                        spinnerIdlist.add(tempobject.optString("UserID"));
                    }
                    for(int j=0;j<typearray.length();j++){
                        JSONObject tempobject = typearray.optJSONObject(j);
                        spinner2list.add(tempobject.optString("dicName"));
                        spinner2Idlist.add(tempobject.optString("dicID"));
                    }
                    adapter1.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
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
    public void initAlert(){
        new ActionSheetDialog(Act_NoticeFileToDo.this)
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
    /**
     * 跳转图库
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
     * 跳转相机
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
        /*** 处理相册和相机返回按钮，view中添加图片按钮刷新问题* */
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
        Location location = LocationUtils.getInstance(Act_NoticeFileToDo.this ).showLocation();
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
