package com.a21zhewang.constructionapp.ui.dxzf;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.JdzfCompanys;
import com.a21zhewang.constructionapp.bean.JdzfProjectBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.SelectTypesActivity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcProjectInfoActivity;
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
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
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


/**
 * Created by WP-PC on 2019/5/30.
 */

public class Act_DxzfAddInfo extends BaseActivity {
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    @BindView(R.id.zfjd_project_name)
    TextView zfjd_project_name;
    @BindView(R.id.zfjd_project_type)
    TextView zfjd_project_type;
    @BindView(R.id.zfjd_project_companys)
    TextView zfjd_project_companys;
    @BindView(R.id.addimg)
    ImageView addimg;
    @BindView(R.id.img_recyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.edit_info)
    EditText et_content;
    private Project pjbean;
    private Dialog loadingDialog;
    /*执法类别*/
    private String projectId,projectName;
    private JSONArray  FileArray = new JSONArray();
    private String type;
    private String status = "1";
    private JSONObject companyObject;
    private JSONArray companyarray;
    private String xPoint,yPoint;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private RequestOptions requestOptions;
    private int selectImageIndex;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private int position = 0;
    private int selectTag = 0;


    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_dxzfjd_addinfo;
    }

    @Override
    public void initViews() {
        getLocation();
        titleBarRighttext.setVisibility(View.VISIBLE);
        titleBarRighttext.setTextSize(16);
        titleBarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdata();
            }
        });
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "发送中...");
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
    public void initAlert(){
        new ActionSheetDialog(Act_DxzfAddInfo.this)
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
    private void postdata() {
        if(TextUtils.isEmpty(zfjd_project_name.getText().toString())){
            PublicUtils.toast("请选择项目");
            return;
        }
        if(TextUtils.isEmpty(zfjd_project_type.getText().toString())){
            PublicUtils.toast("请选择整改类型");
            return;
        }
        if(TextUtils.isEmpty(zfjd_project_companys.getText().toString())){
            PublicUtils.toast("请选择责任单位");
            return;
        }
        if(TextUtils.isEmpty(et_content.getText().toString())){
            PublicUtils.toast("请输入通知内容");
            return;
        }
        if(imgLists.size() == 1){
            PublicUtils.toast("请上传图片");
            return;
        }
        loadingDialog.show();
        JSONObject jsonObject = new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
            for (int i = 0; i < imgLists.size(); i++) {
                LocalMedia localMedia = imgLists.get(i);
                JSONObject imgfile = new JSONObject();
                String path = localMedia.getCompressPath();
                if(path != null){
                    if(selectTag == 1){
                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_DxzfAddInfo.this,
                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+ DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }else {
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_DxzfAddInfo.this,
                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }
                    }
                    if(selectTag == 2){
                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_DxzfAddInfo.this,
                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }else {
                            Bitmap bitmap = SendImageUtil.createWatermark(Act_DxzfAddInfo.this,
                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
                            SendImageUtil.saveImage(bitmap,path);
                        }
                    }
                    imgs.add(path);
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    imgfile.put("fileName", fileName);
                    imgfile.put("fileBrief", "第" + (i + 1) + "张图片");
                    FileArray.put(imgfile);
                }
            }
            jsonObject.put("projectId",projectId);
            jsonObject.put("projectName",pjbean.getProjectShortName());
            jsonObject.put("type",type);
            jsonObject.put("status",status); //1：处理中  2：已闭合
            jsonObject.put("itemCompanyList",companyarray);
            jsonObject.put("content",et_content.getText().toString());
            jsonObject.put("files", FileArray);
        } catch (JSONException e) {
            PublicUtils.toast("json数据生产错误！");
        }
        titleBarRighttext.setVisibility(View.GONE);
        MyCallBack callback = new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             */
            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                PublicUtils.toast("发送成功");
                titleBarRighttext.setVisibility(View.VISIBLE);
                finish();
            }

            /**
             * 请求成功code 不等于 0回调
             */
            @Override
            public void onFail(ObjBean getbean) {
                titleBarRighttext.setVisibility(View.VISIBLE);
                loadingDialog.dismiss();
            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {
                loadingDialog.dismiss();
            }
        };
        XUtil.updatas(jsonObject, imgs, "EditSuperviseManage", callback);
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
        //项目选择
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            projectName = data.getStringExtra("strs");
            pjbean = data.getParcelableExtra("project");
            projectId = data.getStringExtra("id");
            zfjd_project_name.setText(projectName);
        }
        if (requestCode == Act_DXSelectType.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            zfjd_project_type.setText(strs);
            //更新处理状态：1：处理中  2：已闭合
            if("限期整改".equals(strs)){
                type = "1";
                status = "1";
                return;
            }
            if("停工整改".equals(strs)){
                type = "2";
                status = "1";
                return;
            }
            if("简易处罚".equals(strs)){
                type = "3";
                status = "1";
                return;
            }
            if("立案处罚".equals(strs)){
                type = "4";
                status = "1";
                return;
            }
            if("扣分告知书".equals(strs)){
                type = "5";
                status = "2"; //扣分告知书不需要处理，直接更改状态为已完成。
                return;
            }

        }
        if (requestCode == Act_DXSelectCompany.RESQUST_CODE_SELECTCOMPANY
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            zfjd_project_companys.setText(strs);
            List<JdzfCompanys> jdzfCompanysList = data.getParcelableArrayListExtra("list");
            companyarray = new JSONArray();
            for(int i=0;i<jdzfCompanysList.size();i++){
                if(jdzfCompanysList.get(i).isChecked() == true){
                    companyObject = new JSONObject();
                    try{
                        companyObject.put("companyId",jdzfCompanysList.get(i).getEtpID());
                        companyObject.put("companyName",jdzfCompanysList.get(i).getEtpShortName());
                        companyarray.put(companyObject);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

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
    @OnClick({R.id.li_select_project,
            R.id.li_select_type,
            R.id.li_select_companys,
            R.id.li_select_picture,
            R.id.addimg
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_select_project:
                XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"projects\"}"), "GetSuperviseManageTypes", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        JdzfProjectBean jdzfProjectBean = JsonUtils.getbean(res, JdzfProjectBean.class);
                        final List<Project> projectList = jdzfProjectBean.getProjectList();
                        if (projectList != null && projectList.size() > 0) {
                            Intent intent = new Intent(Act_DxzfAddInfo.this, SelectProjectActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    }
                    @Override
                    public void onFail(ObjBean getbean) {

                    }
                    @Override
                    public void onWrong() {

                    }
                });
                break;
            case R.id.li_select_type:
                startActivityForResult(new Intent(Act_DxzfAddInfo.this,Act_DXSelectType.class),Act_DXSelectType.RESQUST_CODE_SELECTTYPE);
                break;
            case R.id.li_select_companys:
                startActivityForResult(new Intent(Act_DxzfAddInfo.this,Act_DXSelectCompany.class)
                        .putExtra("projectId",projectId),Act_DXSelectCompany.RESQUST_CODE_SELECTCOMPANY);
                break;
//            case R.id.li_select_picture:
//                initAlert();
//                break;
            case R.id.addimg:
                initAlert();
                break;
        }
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_DxzfAddInfo.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }

}
