package com.a21zhewang.constructionapp.ui.task;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
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

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * 任务督办
 * Created by Administrator on 2021/8/2.
 */

public class Act_AddTask extends BaseActivity{
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout rightsubmit;
    @BindView(R.id.edit_title)
    TextView title;
    @BindView(R.id.li_select_check_company)
    LinearLayout li_select_check_company;
    @BindView(R.id.tv_check_company)
    TextView tv_check_company;
    @BindView(R.id.li_select_check_user)
    LinearLayout li_select_check_user;
    @BindView(R.id.tv_check_user)
    TextView tv_check_user;
    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.noticefile_recyclerView)
    RecyclerView recyclerview;
    private String EtpId = "";
    private String UserId = "";
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private RequestOptions requestOptions;
    private int selectImageIndex;
    private int position = 0;
    private int selectTag = 0;
    private JSONArray  FileArray = new JSONArray();
    private Dialog loadingDialog;


    @Override
    public int getLayoutId() {
        return R.layout.act_addtask_layout;
    }

    @Override
    public void initViews() {
        requestOptions = new RequestOptions().centerCrop();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
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
        rightsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
    }

    @Override
    public void initData() {

    }
    @OnClick({
            R.id.li_select_check_company,
            R.id.li_select_check_user

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_check_company:  //督办单位
                if(TextUtils.isEmpty(title.getText().toString())){
                    PublicUtils.toast("请输入任务督办标题");
                    return;
                }
                startActivityForResult(new Intent(Act_AddTask.this,Act_AddTaskSelectCompany.class)
                        ,Act_AddTaskSelectCompany.RESQUST_CODE_SELECT_CONTENT);
                break;
            case R.id.li_select_check_user:  //人员
                if(TextUtils.isEmpty(title.getText().toString())){
                    PublicUtils.toast("请输入任务督办标题");
                    return;
                }
                if(TextUtils.isEmpty(tv_check_company.getText().toString())){
                    PublicUtils.toast("请选择督办单位");
                    return;
                }
                startActivityForResult(new Intent(Act_AddTask.this,Act_AddTaskSelectUser.class).putExtra("EtpId",EtpId)
                        ,Act_AddTaskSelectUser.RESQUST_CODE_SELECT_USER);
                break;
        }
    }
    public void initAlert(){
        new ActionSheetDialog(Act_AddTask.this)
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
        if (requestCode == Act_AddTaskSelectCompany.RESQUST_CODE_SELECT_CONTENT
                && resultCode == RESULT_OK
                && data != null) {
            EtpId = data.getStringExtra("etpId");
            String etpName = data.getStringExtra("etpName");
            tv_check_company.setText(etpName);
        }
        if (requestCode == Act_AddTaskSelectUser.RESQUST_CODE_SELECT_USER
                && resultCode == RESULT_OK
                && data != null) {
            UserId = data.getStringExtra("userId");
            String userName = data.getStringExtra("userName");
            tv_check_user.setText(userName);
        }
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
    public  void postData(){
        if(TextUtils.isEmpty(title.getText().toString())){
            PublicUtils.toast("请输入任务督办标题");
            return;
        }
        if(TextUtils.isEmpty(tv_check_company.getText().toString())){
            PublicUtils.toast("请选择督办单位");
            return;
        }
        if(TextUtils.isEmpty(tv_check_user.getText().toString())){
            PublicUtils.toast("请选择督办人");
            return;
        }
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
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_RwdbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+ DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }else {
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_RwdbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }
//                    }
//                    if(selectTag == 2){
//                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_RwdbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }else {
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_RwdbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }
//                    }
                    imgs.add(path);
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    imgfile.put("fileName", fileName);
                    imgfile.put("fileBrief", "第" + (i + 1) + "张");
                    FileArray.put(imgfile);
                }

            }
            jsonObject.put("rectEtpId",EtpId);   //用户选择的企业ID
            jsonObject.put("rectUserId",UserId); //用户选择的用户ID
            jsonObject.put("title",title.getText().toString());
            jsonObject.put("content",content.getText().toString());
            jsonObject.put("files", FileArray);
        } catch (JSONException e) {
            PublicUtils.toast("json数据生产错误！");
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                rightsubmit.setVisibility(View.VISIBLE);
                showToast("提交成功");
                Act_AddTask.this.finish();
            }
            @Override
            public void onFail(ObjBean getbean) {
                rightsubmit.setVisibility(View.VISIBLE);
                loadingDialog.dismiss();
            }
            @Override
            public void onWrong() {
                loadingDialog.dismiss();
            }
        };
        if(TextUtils.isEmpty(content.getText().toString()) && imgs.size() == 0){
            PublicUtils.toast("请输入您要描述的文字信息或上传图片");
            return;
        }
        loadingDialog.show();
        rightsubmit.setVisibility(View.GONE);
        XUtil.updatassj(jsonObject, imgs, "AddTaskSupervise", callback);
    }
    @Override
    public void beforesetContentView() {

    }
    @Override
    public void processClick(View v) {

    }
}
