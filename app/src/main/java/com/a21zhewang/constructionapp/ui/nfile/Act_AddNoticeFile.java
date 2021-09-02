package com.a21zhewang.constructionapp.ui.nfile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.a21zhewang.constructionapp.bean.NoticeTaskBean;
import com.a21zhewang.constructionapp.bean.NotifiTypeList;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TaskBean;
import com.a21zhewang.constructionapp.bean.TaskCompany;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.JsonUtils;
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
import java.util.Map;
import butterknife.BindView;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * 市局下发：-》黄陂监管【领导】——》下发+回复
 *          -》黄陂监管【小喽喽】——》回复
 * Created by Administrator on 2020/12/21.
 */

public class Act_AddNoticeFile extends BaseActivity{
    @BindView(R.id.li_type)
    LinearLayout li_type;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.li_select_unit)
    LinearLayout li_select_unit;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout rightsubmit;
    @BindView(R.id.tv_select_unit)
    TextView tv_unit;
    @BindView(R.id.edit_title)
    EditText title;
    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.noticefile_recyclerView)
    RecyclerView recyclerview;
    //private List<Map<String, String>> taskList = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> unitList = new ArrayList<Map<String, String>>();
    private String dicID;
    private String dicName;
    private String UnitName;
    private String UnitID;
    private AlertDialog.Builder builder;
    private ImageOptions imageOptions;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private RequestOptions requestOptions;
    private int selectImageIndex;
    private int position = 0;
    private int selectTag = 0;
    private JSONArray  FileArray = new JSONArray();
    private Dialog loadingDialog;
    private List<NotifiTypeList> notifiTypeLists;
    private NoticeTaskBean noticeTaskBean;
    private JSONArray CompanyArray;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_add_noticefile;
    }

    @Override
    public void initViews() {
        notifiTypeLists = new ArrayList<>();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        li_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str = new String[notifiTypeLists.size()];
                for(int i=0;i<notifiTypeLists.size();i++){
                    str[i] = notifiTypeLists.get(i).getDicName();
                }
                showFourTextDialog(tv_type,str);
            }
        });
        li_select_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] str = new String[unitList.size()];
//                for(int i=0;i<unitList.size();i++){
//                    str[i] = unitList.get(i).get("UnitName");
//                }
//                showUnitList(tv_unit,str);
                startActivityForResult(new Intent(Act_AddNoticeFile.this,Act_AddNoticeSelectCompany.class)
                        ,Act_AddNoticeSelectCompany.RESQUST_CODE_SELECT_CONTENT);
            }
        });
        rightsubmit.setOnClickListener(new View.OnClickListener() {
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
    public  void postData(){
        if(TextUtils.isEmpty(tv_type.getText().toString())){
            PublicUtils.toast("请选择任务类型");
            return;
        }
        if(TextUtils.isEmpty(tv_unit.getText().toString())){
            PublicUtils.toast("请选择接收单位");
            return;
        }
        if(TextUtils.isEmpty(title.getText().toString())){
            PublicUtils.toast("请输入公告标题");
            return;
        }
        if(TextUtils.isEmpty(content.getText().toString())){
            PublicUtils.toast("请输入您要描述的文字信息");
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
                    imgs.add(path);
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    imgfile.put("fileName", fileName);
                    imgfile.put("fileBrief", "第" + (i + 1) + "张");
                    FileArray.put(imgfile);
                }
            }
            jsonObject.put("TaskType",dicName);
            jsonObject.put("Title",title.getText().toString());
            jsonObject.put("Content",content.getText().toString());
            jsonObject.put("files", FileArray);
            jsonObject.put("ReceiveUnits",CompanyArray);
        } catch (JSONException e) {
            PublicUtils.toast("json数据生产错误！");
        }
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                rightsubmit.setVisibility(View.VISIBLE);
                Act_AddNoticeFile.this.finish();
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
        if(imgs.size() == 0){
            showToast("请上传图片");
            return;
        }
        loadingDialog.show();
        rightsubmit.setVisibility(View.GONE);
        XUtil.updatassj(jsonObject, imgs, "NotificationSend", callback);
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)) {
                    noticeTaskBean = JsonUtils.getbean(res,NoticeTaskBean.class);
                    notifiTypeLists = noticeTaskBean.getNotifiTypeList();
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
            }
        };
        XUtil.postjsondatasj(JsonUtils.getjsonobj(""), "GetTaskTypeAndUnitList", callback);
    }
    /**
     * 任务类型
     */
    public void showFourTextDialog(final TextView textView, String[] item){
        AlertDialog alertDialog = new AlertDialog
                .Builder(Act_AddNoticeFile.this)
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        for(int i=0;i<notifiTypeLists.size();i++){
                            if(which == i){
                                dicID = notifiTypeLists.get(i).getDicID();
                                dicName = notifiTypeLists.get(i).getDicName();
                                textView.setText(dicName);
                            }
                        }
                    }
                }).create();
        alertDialog.show();
    }
    private void showUnitList(final TextView textView, String[] item) {
        AlertDialog alertDialog = new AlertDialog
                .Builder(Act_AddNoticeFile.this)
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        for(int i=0;i<unitList.size();i++){
                            if(which == i){
                                textView.setText(unitList.get(i).get("UnitName"));
                                UnitID = unitList.get(i).get("UnitID");
                            }
                        }
                    }
                }).create();
        alertDialog.show();
    }
    public void initAlert(){
        new ActionSheetDialog(Act_AddNoticeFile.this)
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
        if (requestCode == Act_AddNoticeSelectCompany.RESQUST_CODE_SELECT_CONTENT
                && resultCode == RESULT_OK
                && data != null) {
                StringBuffer sb = new StringBuffer();
                ArrayList<TaskBean> taskBeen = data.getParcelableArrayListExtra("list");
                List<TaskCompany> taskCompanyList = new ArrayList<>();
                CompanyArray = new JSONArray();
                for (int i = 0; i < taskBeen.size(); i++) {
                    taskCompanyList = taskBeen.get(i).getCompanyList();
                    for (int j = 0; j < taskCompanyList.size(); j++) {
                        JSONObject object = new JSONObject();
                        if (taskCompanyList.get(j).isChecked() == true) {
                            try {
                                object.put("etpId", taskCompanyList.get(j).getEtpId());
                                object.put("etpName", taskCompanyList.get(j).getEtpName());
                                CompanyArray.put(object);
                                sb.append(taskCompanyList.get(j).getEtpName()+",");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                if(sb != null && CompanyArray.length()>0){
                    tv_unit.setText(sb.deleteCharAt(sb.length() - 1).toString());
                }
            }
        }
        /*** 处理相册和相机返回按钮，view中添加图片按钮刷新问题* */
        if(data == null && requestCode != GraffitiActivity.REQ_CODE_GRAFFITI && requestCode != 520
                && requestCode != 521 && requestCode != 739 && requestCode != 2796){
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
    @Override
    public void processClick(View v) {

    }
}
