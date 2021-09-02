package com.a21zhewang.constructionapp.ui.wdjc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseFragment;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectSgqyActivity;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
import com.a21zhewang.constructionapp.ui.zxjc.NewCheckQuestionActivity;
import com.a21zhewang.constructionapp.utils.ActionSheetDialog;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
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
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.hzw.graffiti.GraffitiActivity;
import cn.hzw.graffiti.GraffitiParams;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link WdADDAQDKFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WdADDAQDKFragment extends BaseFragment {
    @BindView(R.id.activity_new_check_qestion_RecyclerView)
    RecyclerView activityNewCheckQestionRecyclerView;
    // @BindView(R.id.activity_new_check_qestion_sava)
    // RelativeLayout activityNewCheckQestionSava;

    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgs = new ArrayList<>();
    private Context mContext;
    private RequestOptions requestOptions;
    private EditText contentinput;
    private ErrorMsgBean errorMsgBean = new ErrorMsgBean();
    private ErrorMsgBean aqMsgBean = new ErrorMsgBean();
    private int selectImageIndex;
    private ArrayList<String> files;
    private List<FilesBean> filesBeenlist;
    private StringBuffer sb;
    private int checkType;
    private int position = 0;
    private int selectTag = 0;
    private Dialog loadingDialog;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WdOnFragmentInteractionListener mListener;
    private View.OnClickListener onClickListener;

    public WdADDAQDKFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ADDAQDKFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WdADDAQDKFragment newInstance(String param1, String param2) {
        WdADDAQDKFragment fragment = new WdADDAQDKFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_wdaddaqdk;
    }


    @Override
    public void lazyLoad() {
        if (mListener!=null){
            MobclickAgent.onPageStart("新增安全");
            mListener.onFragmentInteraction(onClickListener);
        }
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        lazyLoad();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "提交中...");
        checkType = getActivity().getIntent().getIntExtra("checkType",-1);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在保存到服务器...");
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = contentinput.getText().toString().trim();
//                if (TextUtils.isEmpty(trim)){
//                    PublicUtils.toast("请输入内容");
//                    return;
//                }
                errorMsgBean.setCheckContent(TextUtils.isEmpty(trim)?"":trim);
                if(checkType == 1){ //日查
                    postCheckTypeOneFiles(baseQuickAdapter.getData(),v);
                }else {
                    postOtherTypeFiles(baseQuickAdapter.getData(),v);
                }
            }
        };
        mListener.onFragmentInteraction(onClickListener);
    }
    /**
     * 提交日查单文字与图片信息
     */
    private  void   postCheckTypeOneFiles(List<LocalMedia> imgs,View v){
    sb = new StringBuffer();
    filesBeenlist = new ArrayList<>();
    if (imgs == null || imgs.size() == 1 && TextUtils.isEmpty(contentinput.getText().toString().trim())) {
        PublicUtils.toast("请选择输入文字或上传图片");
        return;
    }
    List<String> strings = new ArrayList<>();
    List<String> imgCreateTimeList = new ArrayList<>();
    files = new ArrayList<>();
    for (int i = 0; i < imgs.size() - 1; i++) {
        String path = imgs.get(i).getPath();
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String watermarkTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
        if(TextUtils.isEmpty(watermarkTime)){ //不是手机自主拍摄的照片,获取不到时间，可以通过File获取最后修改时间
//            File file = new File(path);
//            long modifieTime = file.lastModified();
//            watermarkTime = DateUtils.formatDate(modifieTime);
            watermarkTime = DateUtils.getTodayDate1Long2();
        }
        imgCreateTimeList.add(watermarkTime);
    }
    /**判断是否当日图片，只保存当日图片*/
    for(int j=0;j<imgCreateTimeList.size();j++){
        int index = j+1;
        FilesBean filesBean = new FilesBean();
        String history_time = imgCreateTimeList.get(j).substring(0,10);
        String today_time = DateUtils.getTodayDate2();
        if(!history_time.equals(today_time)){
            sb.append(index + "，");
        }else {
            String compressPath = imgs.get(j).getCompressPath();
            String fileName = compressPath.substring(compressPath.lastIndexOf("/") + 1);
            String des = "第" + (j + 1) + "张";
            strings.add(compressPath);
            filesBean.setUrl(compressPath);
            filesBean.setFileName(fileName);
            filesBean.setFileBrief(des);
            filesBeenlist.add(filesBean);
        }
    }
   /**判断是否当日图片，只上传当日图片*/
    if(sb != null && sb.length()>0){
        Toast.makeText(getActivity(),"第"+sb.deleteCharAt(sb.length() - 1).toString()+"张非当日图片，请删除",Toast.LENGTH_SHORT).show();
        return;
    }else {
        v.findViewById(R.id.activity_new_check_qestion_sava).setVisibility(View.GONE);
        aqMsgBean.setSelectTag(selectTag);
        aqMsgBean.setCheckContent(contentinput.getText().toString().trim());
        aqMsgBean.setFilesBeans(filesBeenlist);
        Intent isok = new Intent().putExtra("aq", aqMsgBean);
        ((Activity)mContext).setResult(Activity.RESULT_OK, isok);
        ((Activity)mContext).finish();
        MobclickAgent.onPageEnd("新增安全");
    }
  }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    /**
     * 提交周查/月查单文字与图片信息
     */
    private void postOtherTypeFiles(List<LocalMedia> imgs,View v){
        filesBeenlist = new ArrayList<>();
        if (imgs == null || imgs.size() == 1 && TextUtils.isEmpty(contentinput.getText().toString().trim())) {
            PublicUtils.toast("请选择输入文字或上传图片");
            return;
        }
        List<String> strings = new ArrayList<>();
        files = new ArrayList<>();
        for (int i = 0; i < imgs.size() - 1; i++) {
            FilesBean filesBean = new FilesBean();
            String compressPath = imgs.get(i).getCompressPath();
            String fileName = compressPath.substring(compressPath.lastIndexOf("/") + 1);
            String des = "第" + (i + 1) + "张";
            strings.add(compressPath);
            filesBean.setUrl(compressPath);
            filesBean.setFileName(fileName);
            filesBean.setFileBrief(des);
            filesBeenlist.add(filesBean);
        }
        v.findViewById(R.id.activity_new_check_qestion_sava).setVisibility(View.GONE);
        aqMsgBean.setSelectTag(selectTag);
        aqMsgBean.setCheckContent(contentinput.getText().toString().trim());
        aqMsgBean.setFilesBeans(filesBeenlist);
        Intent isok = new Intent().putExtra("aq", aqMsgBean);
        ((Activity)mContext).setResult(Activity.RESULT_OK, isok);
        ((Activity)mContext).finish();
        MobclickAgent.onPageEnd("新增安全");
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        mContext=getActivity();
        requestOptions = new RequestOptions().centerCrop();
        activityNewCheckQestionRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        baseQuickAdapter = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(imgs) {
            @Override
            protected void convert(final BaseViewHolder helper, final LocalMedia imgpath) {
                ImageView imgview = helper.getView(R.id.fiv);
                if ("addimg".equals(imgpath.getPath())) {
                    imgview.setImageResource(R.mipmap.addimgs);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
//                            PictureSelector.create(NewCheckQuestionActivity.this)
//                                    .externalPicturePreview(helper.getAdapterPosition() - getHeaderLayoutCount(),
//                                            imglist);

//                            selectImageIndex = helper.getLayoutPosition()-getHeaderLayoutCount();
//                            GraffitiActivity.startActivityForResult((Activity) mContext, imgpath.getCompressPath());

                              selectImageIndex = helper.getLayoutPosition()-getHeaderLayoutCount();
                              Intent intent = new Intent((Activity) mContext, GraffitiActivity.class);
                              GraffitiParams params = new GraffitiParams(imgpath.getCompressPath());
                              intent.putExtra(GraffitiActivity.KEY_PARAMS, params);
                              startActivityForResult(intent, 158);
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
        //adapter.addData();
//        activityNewCheckQestionRecyclerView.addItemDecoration();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View header = layoutInflater.inflate(R.layout.newcheckquestion_list_header, null, false);
        contentinput = (EditText) header;
        baseQuickAdapter.addHeaderView(header);
        activityNewCheckQestionRecyclerView.setAdapter(baseQuickAdapter);

    }
    public void initAlert(){
        new ActionSheetDialog(mContext)
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
        PictureSelector.create(WdADDAQDKFragment.this).openGallery(PictureMimeType.ofImage())
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
                .selectionMedia(imgs)
                .previewEggs(true)
                .compress(true).forResult(133);
        baseQuickAdapter.remove(position);
    }
    /**
     * 跳转相机
     */
    public void PictureCarmerSelector(){
        PictureSelector.create(WdADDAQDKFragment.this).openCamera(PictureMimeType.ofImage())
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
                .selectionMedia(imgs)
                .previewEggs(true)
                .compress(true).forResult(PictureConfig.CAMERA);
        baseQuickAdapter.remove(position);
    }



    /**
     * 上传数据
     *
     * @param json
     * @param files
     * @param method
     */
    private void postjson(String json, List<String> files, String method) {
        loadingDialog.show();
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {


            }

            /**
             * 请求成功code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }
    @Override
    public void initData() {

    }


    @Override
    public void processClick(View v) {

    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WdOnFragmentInteractionListener) {
            mListener = (WdOnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*** 处理相册和相机返回按钮，view中添加图片按钮刷新问题* */
        if(data == null && requestCode != GraffitiActivity.REQ_CODE_GRAFFITI){
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 133 && data != null) {
            selectTag = 1;
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter.replaceData(list);
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (requestCode == PictureConfig.CAMERA && resultCode == Activity.RESULT_OK && data != null) {
            selectTag = 2;
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter.replaceData(list);
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == Activity.RESULT_OK
                && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia media = new LocalMedia();
            media.setPath(imgpath);
            media.setCompressPath(imgpath);
            baseQuickAdapter.setData(selectImageIndex,media);

        }
    }


}
