package com.a21zhewang.constructionapp.ui.zddb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.image.ImageOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * Created by Administrator on 2020/12/21.
 */

public class Act_ZddbAdd extends BaseActivity{
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout rightsubmit;
    @BindView(R.id.li_tasktype)
    LinearLayout li_tasktype;
    @BindView(R.id.tv_tasktype)
    TextView tv_tasktype;
    @BindView(R.id.li_select_project)
    LinearLayout li_select_project;
    @BindView(R.id.tv_receive_project)
    TextView tv_receive_project;
    @BindView(R.id.edit_title)
    EditText title;
    @BindView(R.id.li_select_check_company)
    LinearLayout li_select_check_company;
    @BindView(R.id.tv_check_company)
    TextView tv_check_company;
    @BindView(R.id.li_select_check_user)
    LinearLayout li_select_check_user;
    @BindView(R.id.tv_check_user)
    TextView tv_check_user;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.edit_content)
    EditText content;
    @BindView(R.id.noticefile_recyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.icon_go)
    ImageView icon_go;
    /**督办单位ID*/
    private String rectEtpId;
    /**督办人员*/
    private String rectUser;
    /**督办人员ID*/
    private String rectUserId;
    private List<Map<String, String>> taskList = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> unitList;
    private String taskTypeID;
    private String taskType;
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
    private Dialog loadingUserDialog;
    private String projectId;
    private String projectName;
    private List<String> userlist;
    private List<String> userIdlist;
    private boolean SelectEtpTag = true;
    private String xPoint,yPoint;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_zddb_add_layout;
    }

    @Override
    public void initViews() {
        getLocation();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        loadingUserDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
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
    @OnClick({
            R.id.li_tasktype,
            R.id.li_select_project,
            R.id.li_select_check_company,
            R.id.li_select_check_user

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.li_tasktype:  //任务类型
                String[] str = new String[taskList.size()];
                for(int i=0;i<taskList.size();i++){
                    str[i] = taskList.get(i).get("name");
                }
                showTaskTypeDialog(tv_tasktype,str);
                break;
            case R.id.li_select_project:  //接收项目
                if(TextUtils.isEmpty(tv_tasktype.getText().toString())){
                    PublicUtils.toast("请选择任务类型");
                    return;
                }
                startActivityForResult(new Intent(Act_ZddbAdd.this, Act_SearchProject.class), Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
                break;
            case R.id.li_select_check_company:  //督办单位
                if(TextUtils.isEmpty(tv_tasktype.getText().toString())){
                    PublicUtils.toast("请选择任务类型");
                    return;
                }
                if(TextUtils.isEmpty(tv_receive_project.getText().toString())){
                    PublicUtils.toast("请选择接收项目");
                    return;
                }
                if(TextUtils.isEmpty(title.getText().toString())){
                    PublicUtils.toast("请输入重点督办标题");
                    return;
                }
                if(unitList != null){
                    String[] unitstr = new String[unitList.size()];
                    for(int i=0;i<unitList.size();i++){
                        unitstr[i] = unitList.get(i).get("etpName");
                    }
                    showUnitList(tv_check_company,unitstr);
                }
                break;
            case R.id.li_select_check_user:  //督办人
                if(TextUtils.isEmpty(tv_tasktype.getText().toString())){
                    PublicUtils.toast("请选择任务类型");
                    return;
                }
                if(TextUtils.isEmpty(tv_receive_project.getText().toString())){
                    PublicUtils.toast("请选择接收项目");
                    return;
                }
                if(TextUtils.isEmpty(title.getText().toString())){
                    PublicUtils.toast("请输入重点督办标题");
                    return;
                }
                if(TextUtils.isEmpty(tv_check_company.getText().toString())){
                    PublicUtils.toast("请选择督办单位");
                    return;
                }
                if(SelectEtpTag == true){
                    showUnitEtpList();
                }
                break;
        }
    }
    public  void postData(){
        if(TextUtils.isEmpty(tv_tasktype.getText().toString())){
            PublicUtils.toast("请选择任务类型");
            return;
        }
        if(TextUtils.isEmpty(tv_receive_project.getText().toString())){
            PublicUtils.toast("请选择接收项目");
            return;
        }
        if(TextUtils.isEmpty(title.getText().toString())){
            PublicUtils.toast("请输入重点督办标题");
            return;
        }
        if("2".equals(taskTypeID)){ //督办管理
            if(TextUtils.isEmpty(tv_check_company.getText().toString())){
                PublicUtils.toast("请选择督办单位");
                return;
            }
            if(TextUtils.isEmpty(tv_check_user.getText().toString())){
                PublicUtils.toast("请选择督办人");
                return;
            }
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
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_ZddbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+ DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }else {
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_ZddbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"地理信息："+xPoint+"  "+yPoint+"\n"+"图片来源："+"手机图库"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }
//                    }
//                    if(selectTag == 2){
//                        if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_ZddbAdd.this,
//                                    path,"工程名称："+projectName+"\n"+"检查人员："+PublicUtils.userName+"\n"+"图片来源："+"现场拍摄"+"\n"+ "检查时间："+DateUtils.getTodayDateLong());
//                            SendImageUtil.saveImage(bitmap,path);
//                        }else {
//                            Bitmap bitmap = SendImageUtil.createWatermark(Act_ZddbAdd.this,
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
            jsonObject.put("taskType",taskTypeID);   //任务类型
            jsonObject.put("projectId",projectId);
            jsonObject.put("rectEtpId",rectEtpId);   //用户选择的企业ID
            jsonObject.put("rectUserId",rectUserId); //用户选择的用户ID
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
                Act_ZddbAdd.this.finish();
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
//        if(imgs.size() == 0){
//            showToast("请上传图片");
//            return;
//        }
        loadingDialog.show();
        rightsubmit.setVisibility(View.GONE);
        XUtil.updatas(jsonObject, imgs, "AddKeySupervise", callback);
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "TaskType" + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)) {
                    try {
                        JSONArray array = new JSONArray(res);
                        for(int i=0;i<array.length();i++){
                            JSONObject object = array.optJSONObject(i);
                            Map map = new HashMap();
                            map.put("name",object.optString("name"));
                            map.put("value",object.optInt("value")+"");
                            taskList.add(map);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
        XUtil.postjsondata(object, "GetKeySuperviseTypes", callback);
    }
    /**
     * 任务类型
     */
    public void showTaskTypeDialog(final TextView textView, String[] item){
        AlertDialog alertDialog = new AlertDialog
                .Builder(Act_ZddbAdd.this)
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        for(int i=0;i<taskList.size();i++){
                            if(which == i){
                                taskTypeID = taskList.get(i).get("value");
                                taskType = taskList.get(i).get("name");
                                textView.setText(taskType);
                                if("2".equals(taskTypeID)){ //督办管理
                                    li_select_project.setEnabled(true);
                                    icon_go.setVisibility(View.VISIBLE);
                                    tv_receive_project.setText("请选择接收项目");
                                    li_select_check_company.setVisibility(View.VISIBLE);
                                    li_select_check_user.setVisibility(View.VISIBLE);
                                    view4.setVisibility(View.VISIBLE);
                                }else { //通知文件
                                    icon_go.setVisibility(View.INVISIBLE);
                                    tv_receive_project.setText("全部");
                                    li_select_project.setEnabled(false);
                                    li_select_check_company.setVisibility(View.GONE);
                                    li_select_check_user.setVisibility(View.GONE);
                                    view4.setVisibility(View.GONE);
                                    projectId = "";
                                    rectEtpId = "";
                                    rectUserId = "";
                                    title.setText("");
                                    content.setText("");
                                    tv_check_company.setText("");
                                    tv_check_user.setText("");
                                }
                            }
                        }
                    }
                }).create();
        alertDialog.show();
    }

    /**
     * 督办单位
     * @param textView
     * @param item
     */
    private void showUnitList(final TextView textView, String[] item) {
        AlertDialog alertDialog = new AlertDialog
                .Builder(Act_ZddbAdd.this)
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        for(int i=0;i<unitList.size();i++){
                            if(which == i){
                                textView.setText(unitList.get(i).get("etpName"));
                                rectEtpId = unitList.get(i).get("etpId");
                                getUnitEtpList(rectEtpId);//督办人
                            }
                        }
                    }
                }).create();
        alertDialog.show();
    }


    public void initAlert(){
        new ActionSheetDialog(Act_ZddbAdd.this)
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
        if (requestCode == Act_SearchProject.RESQUST_CODE_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            projectId = data.getStringExtra("projectId");
            projectName = data.getStringExtra("projectName");
            tv_receive_project.setText(projectName);
            getUnitDate(projectId);
        }
        /*** 处理相册和相机返回按钮，view中添加图片按钮刷新问题* */
//        if(data == null && requestCode != GraffitiActivity.REQ_CODE_GRAFFITI && requestCode != 520 && requestCode != 521 && requestCode != 739){
//            LocalMedia media = new LocalMedia();
//            media.setPath("addimg");
//            baseQuickAdapter.addData(media);
//        }
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

    /**
     * 获取督办单位
     * @param projectId
     */
    public void getUnitDate(String projectId){
        JSONObject object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\"}");
        XUtil.postjsondata(object, "GetEtpListByProjectId", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    unitList = new ArrayList<>();
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("recordList");
                    for(int i=0;i<array.length();i++){
                        JSONObject tempobject = array.optJSONObject(i);
                        Map map = new HashMap();
                        map.put("etpId",tempobject.optString("etpId"));
                        map.put("etpName",tempobject.optString("etpName"));
                        unitList.add(map);
                    }
                    tv_check_company.setText(unitList.get(0).get("etpName"));
                    rectEtpId = unitList.get(0).get("etpId");
                    getUnitEtpList(rectEtpId);//督办人
                } catch (JSONException e) {
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
    public void processClick(View v) {

    }
    /**
     * 选择督办人员
     */
    private void showUnitEtpList(){
        String[] items = userlist.toArray(new String[userlist.size()]);
        builder = new AlertDialog.Builder(Act_ZddbAdd.this).setIcon(R.mipmap.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rectUser = userlist.get(i);
                        rectUserId = userIdlist.get(i);
                        tv_check_user.setText(rectUser);
                    }
                });
        builder.create().show();
    }
    /**
     * 督办人员
     */
    public void getUnitEtpList(String etpId){
        userlist = new ArrayList<>();
        userIdlist = new ArrayList<>();
        loadingUserDialog.show();
        JSONObject object = JsonUtils.getjsonobj("{\"projectId\":\"" + projectId + "\",\"etpId\":\"" + etpId + "\"}");
        XUtil.postjsondata(object, "GetUserListByEtpId", new MyCallBack() {
            @Override
            public void onResule(String res) {
                if(!"{}".equals(res)){
                    SelectEtpTag =true;
                    loadingUserDialog.dismiss();
                    try {
                        JSONObject tempobject = new JSONObject(res);
                        JSONArray array = tempobject.optJSONArray("recordList");
                        if(array.length() == 0){
                            tv_check_company.setText("");
                            tv_check_user.setText("");
                            rectEtpId = "";
                            rectUserId = "";
                        }else
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.optJSONObject(i);
                                tv_check_user.setText(object.optString("userName"));
                                rectUserId = object.optString("userId");
                                userIdlist.add(object.optString("userId"));
                                userlist.add(object.optString("userName"));
                                tv_check_user.setText(userlist.get(0));
                                rectUserId = userIdlist.get(0);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    SelectEtpTag = false;
                    tv_check_user.setText("");
                    rectUserId = "";
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
    public void getLocation(){
        Location location = LocationUtils.getInstance(Act_ZddbAdd.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }
}
