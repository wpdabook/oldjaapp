package com.a21zhewang.constructionapp.ui.gxys;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AqscSpinnerBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectTypesActivity;
import com.a21zhewang.constructionapp.utils.FileUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.compress.Luban;
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

public class AddZgjlActivity extends BaseActivity {


    @BindView(R.id.activity_add_zgjl_MyTitleBar)
    MyTitleBar activityAddZgjlMyTitleBar;
//    @BindView(R.id.activity_add_zgjl_MySpinner_zgxm1)
//    MySpinner activityAddZgjlMySpinnerZgxm1;
//    @BindView(R.id.activity_add_zgjl_MySpinner_zgxm2)
//    MySpinner activityAddZgjlMySpinnerZgxm2;
    @BindView(R.id.activity_base_add_TextView_jcxm)
    TextView textView;
    @BindView(R.id.activity_add_zgjl_EditText_nrms)
    EditText activityAddZgjlEditTextNrms;
    @BindView(R.id.activity_add_zgjl_RecyclerView_imgs)
    RecyclerView activityAddZgjlRecyclerViewImgs;
    private String recordID;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private List<LocalMedia> imglist;
//
//    //风险类别 spinner 1
//    private NamesSpinnerAdpater<MsgType> msgTypesadapter;
   private List<MsgType> msgTypes;
//
//    private List<MsgType> msgsTypesList;
//    private MySpinner.MySpinnerAPI jcxmspinner2onClickListener;
//    private NamesSpinnerAdpater<MsgType> msgsTypesBeanNamesSpinnerAdpater;
//
//
//    private MsgType typesBean;
//    private MsgType msgTypesBean;
//    private MySpinner.MySpinnerAPI spinner1onclick;
    private Dialog loadingDialog;
    private PictureSelectionModel pictureSelectionModel;
    private int selectImageIndex;

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_zgjl;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        recordID = getIntent().getStringExtra("recordID");
        initselectorimg();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "添加中....");
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        msgTypes = new ArrayList<>();
        //风险类别下拉 1
//        msgTypesadapter = new NamesSpinnerAdpater<MsgType>(this, msgTypes) {
//            @Override
//            public void setinit(MsgType name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getTypeName());
//            }
//        };
//        activityAddZgjlMySpinnerZgxm1.setmyspinnerlistadpater(msgTypesadapter);
//        spinner1onclick = new MySpinner.MySpinnerAPI() {
//            @Override
//            public String onclick(int position) {
//                msgTypesBean = msgTypes.get(position);
//                String typeName = msgTypesBean.getTypeName();
//                List<MsgType> msgsTypesBeanList = msgTypesBean.getMsgTypes();
//                msgsTypesList.clear();
//                if (msgsTypesBeanList != null && msgsTypesBeanList.size() > 0) {
//                    msgsTypesList.addAll(msgsTypesBeanList);
//                    activityAddZgjlMySpinnerZgxm2.setTextViewtext(jcxmspinner2onClickListener.onclick(0));
//                } else {
//                    activityAddZgjlMySpinnerZgxm2.setTextViewtext("选择");
//                }
//                msgsTypesBeanNamesSpinnerAdpater.notifyDataSetChanged();
//                return typeName;
//            }
//        };
//        activityAddZgjlMySpinnerZgxm1.setlistviewitemonclick(spinner1onclick);
//
//        //风险类别spinner 2
//        msgsTypesList = new ArrayList<>();
//        msgsTypesBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<MsgType>(this, msgsTypesList) {
//            @Override
//            public void setinit(MsgType name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getTypeName());
//            }
//        };
//        activityAddZgjlMySpinnerZgxm2.setmyspinnerlistadpater(msgsTypesBeanNamesSpinnerAdpater);
//        jcxmspinner2onClickListener = new MySpinner.MySpinnerAPI() {
//            @Override
//            public String onclick(int position) {
//                typesBean = msgsTypesList.get(position);
//                String msgsTypesBeanTypeName = typesBean.getTypeName();
//                List<MsgType> msgsTypesMsgTypes = typesBean.getMsgTypes();
//                return msgsTypesBeanTypeName;
//            }
//        };
//        activityAddZgjlMySpinnerZgxm2.setlistviewitemonclick(jcxmspinner2onClickListener);
    }
    private void initselectorimg() {
        imglist = new ArrayList<>();

        pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
               // .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
                .isCamera(true)// 是否显示拍照按钮 true or false
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
              //  .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// 是否显示gif图片 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(imglist)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityAddZgjlRecyclerViewImgs.setLayoutManager(layoutManager);
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
        commonAdapter = new CommonAdapter<LocalMedia>(this, R.layout.faqigtxt_imglist_item, imglist) {

            @Override
            protected void convert(ViewHolder holder, final LocalMedia s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);

                x.image().bind(imageView, s.getPath(), imageOptions);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex = position;
                        GraffitiActivity.startActivityForResult(AddZgjlActivity.this,s.getCompressPath());
                    }
                });
                holder.setVisible(R.id.faqigtxt_imglist_item_img_del, true);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imglist.remove(position);
                        commonAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        activityAddZgjlRecyclerViewImgs.setAdapter(commonAdapter);
    }
    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        getspinnerdata();
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }



    @OnClick({R.id.activity_add_zgjl_ImageView_addimg, R.id.activity_add_zgjl_TextView_submit, R.id.activity_add_zgjl_TextView_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_add_zgjl_ImageView_addimg:
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);

                break;
            case R.id.activity_add_zgjl_TextView_submit:
                if (textView.getText().equals("请选择")){
                    PublicUtils.toast("请选择整改项！");
                    break;
                }


                if (activityAddZgjlEditTextNrms.getText().toString().trim().length()==0)
                {
                    PublicUtils.toast("请输入内容描述！");
                    break;
                }
                postjson();
                break;
            case R.id.activity_add_zgjl_TextView_cancel:
                break;
        }
    }
    /**
     * 获取下拉列表数据
     */
    private void getspinnerdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetQualityMsgType", new MyCallBack() {
            @Override
            public void onResule(String res) {

                AqscSpinnerBean spinnerBean = JsonUtils.getbean(res, AqscSpinnerBean.class);
               // msgTypes.clear();
                final List<MsgType> typesBeanList = spinnerBean.getMsgTypes();
                if (typesBeanList != null && typesBeanList.size() > 0) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddZgjlActivity.this, SelectTypesActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) typesBeanList);
                            startActivityForResult(intent, SelectTypesActivity.RESQUST_CODE_SELECTTYPE);
                        }
                    });
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
                //msgTypesadapter.notifyDataSetChanged();
            }
        });
    }

    public  void postjson(){
        JSONObject jsonObject=new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
           // jsonObject.put("typeFatherID",msgTypesBean.getTypeID());
           // jsonObject.put("typeID",typesBean.getTypeID());
           // jsonObject.put("typeFatherName",msgTypesBean.getTypeName());
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
           // jsonObject.put("typeName",typesBean.getTypeName());
            jsonObject.put("recordID",recordID);
            jsonObject.put("content",activityAddZgjlEditTextNrms.getText().toString().trim());
            jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
            jsonObject.put("msgTypes", new JSONArray(JsonUtils.objtojson(msgTypes)));
            JSONArray imgfiles = new JSONArray();
            for (int i = 0; i < imglist.size(); i++) {
                LocalMedia localMedia = imglist.get(i);
                JSONObject imgfile = new JSONObject();
                String path = localMedia.getCompressPath();
                imgs.add(path);
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                imgfile.put("fileName", fileName);
                imgfile.put("fileBrief", "第" + (i + 1) + "张图片");
                imgfiles.put(imgfile);
            }
            jsonObject.put("files",imgfiles);
        } catch (JSONException e) {
            PublicUtils.toast("生成数据错误！");
            return;
        }
        loadingDialog.show();
        XUtil.updatas(jsonObject,imgs,"AddProcessAcceptanceRefc", new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                finish();
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

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    imglist.clear();
                    if (list != null && list.size() > 0) {
                        imglist.addAll(list);
                        commonAdapter.notifyDataSetChanged();
                        activityAddZgjlRecyclerViewImgs.scrollToPosition(imglist.size() - 1);
                    }
                    break;
            }
        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == RESULT_OK
                && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia localMedia = commonAdapter.getDatas().get(selectImageIndex);
            localMedia.setPath(imgpath);
            localMedia.setCompressPath(imgpath);
            commonAdapter.notifyItemChanged(selectImageIndex);
        }
        if (requestCode == SelectTypesActivity.RESQUST_CODE_SELECTTYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            textView.setText(strs);
            msgTypes = data.getParcelableArrayListExtra("list");
            //  areaListBeanNamesSpinnerAdpater.replcedata(list);

            // PublicUtils.log(JsonUtils.objtojson(msgTypes));

        }
    }

    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }
}
