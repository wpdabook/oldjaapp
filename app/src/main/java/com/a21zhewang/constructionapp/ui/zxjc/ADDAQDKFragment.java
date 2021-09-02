package com.a21zhewang.constructionapp.ui.zxjc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import cn.hzw.graffiti.GraffitiActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ADDAQDKFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ADDAQDKFragment extends BaseFragment {
    @BindView(R.id.activity_new_check_qestion_RecyclerView)
    RecyclerView activityNewCheckQestionRecyclerView;
    // @BindView(R.id.activity_new_check_qestion_sava)
    // RelativeLayout activityNewCheckQestionSava;

    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgs = new ArrayList<>();
    private Context mContext;
    private RequestOptions requestOptions;
    private EditText contentinput;
    private Dialog loadingDialog;
    private ErrorMsgBean errorMsgBean = new ErrorMsgBean();
    private int selectImageIndex;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View.OnClickListener onClickListener;

    public ADDAQDKFragment() {
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
    public static ADDAQDKFragment newInstance(String param1, String param2) {
        ADDAQDKFragment fragment = new ADDAQDKFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_addaqdk;
    }


    @Override
    public void lazyLoad() {
        if (mListener!=null)
        mListener.onFragmentInteraction(onClickListener);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        lazyLoad();
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
                postFiles(baseQuickAdapter.getData());
          //      List<LocalMedia> list = baseQuickAdapter.getData();
//                List<FilesBean> files=new ArrayList();
//                if (list.size() > 0) {
//                    for (int i = 0; i < list.size() - 1; i++) {
//                        FilesBean object = new FilesBean();
//                        String path = list.get(i).getCompressPath();
//                        String fileName = path.substring(path.lastIndexOf("/") + 1);
//                        object.setUrl(path);
//                        object.setFileBrief("");
//                        object.setFileName(fileName);
//                        files.add(object);
//                    }
//                }


            }
        };
        mListener.onFragmentInteraction(onClickListener);
    }


private void postFiles(List<LocalMedia> imgs){
        if (imgs==null||imgs.size()<1){
            PublicUtils.toast("请选择图片文件");
            return;
        }
        List<String> strings=new ArrayList<>();
    for (int i = 0; i < imgs.size() - 1; i++) {
        String compressPath = imgs.get(i).getCompressPath();
        strings.add(compressPath);
    }
    loadingDialog.show();
    XUtil.updatas(JsonUtils.getjsonobj("{}"),strings,"SendFiles2",new MyCallBack(){

        /**
         * 请求成功code 等于 0回调
         *
         * @param res
         */
        @Override
        public void onResule(String res) {
            JSONObject getjsonobj = JsonUtils.getjsonobj(res);
            try {
                String json = getjsonobj.getJSONArray("urls").toString();
                List<FilesBean> urls = JsonUtils.jsonToArrayList(json, FilesBean.class);
                errorMsgBean.setFilesBeans(urls);
                Intent isok = new Intent().putExtra("aq", errorMsgBean);
                ((Activity)mContext).setResult(Activity.RESULT_OK, isok);
                ((Activity)mContext).finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /**
         * 请求成功code 不等于 0回调
         *
         * @param getbean
         */
        @Override
        public void onFail(ObjBean getbean) {
            if(("无Json数据或无附件上传").equals(getbean.getData())){
                PublicUtils.toast("请上传图片");

            }
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
    } );
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
                             remove(getData().size()-1);
                          PictureSelector.create(ADDAQDKFragment.this).openGallery(PictureMimeType.ofImage())
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
        //adapter.addData();
//        activityNewCheckQestionRecyclerView.addItemDecoration();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View header = layoutInflater.inflate(R.layout.newcheckquestion_list_header, null, false);
        contentinput = (EditText) header;
        baseQuickAdapter.addHeaderView(header);
        activityNewCheckQestionRecyclerView.setAdapter(baseQuickAdapter);

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
      //  super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 133) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter.replaceData(list);

           // String objtojson = JsonUtils.objtojson(list);
          //  PublicUtils.log("json:"+ objtojson);

        }if (requestCode == 133){
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
