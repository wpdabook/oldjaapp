package com.a21zhewang.constructionapp.ui.gtxtiu;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.EtpBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.FileUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
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
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

public class YclActivity extends BaseActivity {

    @BindView(R.id.activity_ycl_CanyurenView)
    CanyurenView activityYclCanyurenView;
    @BindView(R.id.activity_ycl_EditText_clyj)
    EditText activityYclEditTextClyj;
    private CanyurenView.CanyurenViewAPI<EtpBean> canyurenViewAPI;
    private String msgid,projectID;
    private List<EtpBean> etpBeens;
    private boolean isonclick=false;
    @BindView(R.id.activity_add_gtxt_img_addimg)
    ImageView activityAddGtxtImgAddimg;
    private List<LocalMedia> imglist;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    @BindView(R.id.activity_add_gtxt_RecyclerView_imglist)
    RecyclerView activityAddGtxtRecyclerViewImglist;
    private PictureSelectionModel pictureSelectionModel;
    private int selectImageIndex;

    /**
     * setContentView????????????
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return ??????????????????
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_ycl;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        etpBeens=new ArrayList<>();
        msgid = getIntent().getStringExtra("msgid");
        projectID = getIntent().getStringExtra("projectID");

        activityYclCanyurenView.setTextstr("?????????????????????");
        canyurenViewAPI = new CanyurenView.CanyurenViewAPI<EtpBean>() {
            @Override
            public void setstr(int size, TextView textView, Set ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(etpBeens.get(integers.get(0)).getEtpShortName());
                } else if (size == 2) {
                    textView.setText(etpBeens.get(integers.get(0)).getEtpShortName() + "," + etpBeens.get(integers.get(1)).getEtpShortName());
                } else if (size > 2) {
                    textView.setText(etpBeens.get(integers.get(0)).getEtpShortName() + "," + etpBeens.get(integers.get(1)).getEtpShortName() + "...");
                }
            }

            @Override
            public String getstr(int i, EtpBean o) {
                return o.getEtpShortName();
            }
        };
        activityYclCanyurenView.setCanyurenViewAPI(canyurenViewAPI);
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {
        imglist = new ArrayList<>();


        pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// ???????????????????????? int
                .imageSpanCount(4)// ?????????????????? int
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// ????????????????????? true or false
                .previewVideo(false)// ????????????????????? true or false
                .enablePreviewAudio(false) // ????????????????????? true or false
                // .compressGrade(Luban.CUSTOM_GEAR)// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
                .isCamera(true)// ???????????????????????? true or false
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .enableCrop(false)// ???????????? true or false
                .compress(true)// ???????????? true or false
                //.compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// ????????????gif?????? true or false
                .showCropFrame(false)// ?????????????????????????????? ???????????????????????????false   true or false
                .showCropGrid(false)// ?????????????????????????????? ???????????????????????????false    true or false
                .openClickSound(false)// ???????????????????????? true or false
                .selectionMedia(imglist)// ???????????????????????? List<LocalMedia> list
                .previewEggs(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityAddGtxtRecyclerViewImglist.setLayoutManager(layoutManager);
        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
        commonAdapter = new CommonAdapter<LocalMedia>(this, R.layout.faqigtxt_imglist_item, imglist) {

            @Override
            protected void convert(ViewHolder holder, final LocalMedia s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);

                x.image().bind(imageView, s.getCompressPath(), imageOptions);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex=position;
                        GraffitiActivity.startActivityForResult(YclActivity.this,s.getCompressPath());
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
        activityAddGtxtRecyclerViewImglist.setAdapter(commonAdapter);
        activityAddGtxtImgAddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imglist.size() >= 5) {
                    PublicUtils.toast("???????????????5????????????");
                   return;
                }
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);

            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {

          XUtil.postjsondata(JsonUtils.getjsonobj("{\"getType\":1,\"projectID\":\"\"}"), "GetEtpList", new MyCallBack() {


              @Override
              public void onResule(String res) {
                  try {
                      JSONObject jsonObject=new JSONObject(res);
                      JSONArray recordList = jsonObject.getJSONArray("recordList");
                      List<EtpBean> etpBeanList = JsonUtils.jsonToList(recordList.toString(), EtpBean[].class);
                      if (etpBeanList !=null&& etpBeanList.size()>0) {
                          etpBeens.addAll(etpBeanList);
                      activityYclCanyurenView.setList(etpBeens, canyurenViewAPI);
                  }
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

    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }



    @OnClick({R.id.activity_ycl_tv_tj, R.id.activity_ycl_tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_ycl_tv_tj:
                if (!isonclick){
                    isonclick=true;
                }
                List<EtpBean> getlists = activityYclCanyurenView.getlists();
//                if (getlists.size()==0){
//                    PublicUtils.toast("?????????????????????");
//                    return;
//                }
                String string = activityYclEditTextClyj.getText().toString();
                if (string.length()==0){
                    PublicUtils.toast("??????????????????");
                    return;
                }
                JSONArray imgfiles = new JSONArray();
                List<String> imgs = new ArrayList<>();
                if (imglist.size() > 0) {
                    int i = 0;
                    for (LocalMedia s : imglist) {
                        JSONObject imgfile = new JSONObject();
                        String path = s.getCompressPath();
                        imgs.add(path);
                        String fileName = path.substring(path.lastIndexOf("/") + 1);
                        try {
                            imgfile.put("fileName", fileName);
                            imgfile.put("fileBrief", "???" + (i + 1) + "?????????");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        imgfiles.put(imgfile);
                    }
                }

                String objtojson = JsonUtils.objtojson(getlists);
                postdata(string,objtojson,imgfiles.toString(),imgs,projectID);

                break;
            case R.id.activity_ycl_tv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {


            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            imglist.clear();
            if (list != null && list.size() > 0) {
                imglist.addAll(list);
                commonAdapter.notifyDataSetChanged();
                activityAddGtxtRecyclerViewImglist.scrollToPosition(imglist.size() - 1);
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
    }

    private void postdata(String con, String causeEtps,String files,List<String> Files,String projectID) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"Files\":" + files + ",\"projectID\":\"" + projectID + "\",\"createUserName\":\"" + PublicUtils.userBean.getUserName() + "\",\"causeEtps\":"+causeEtps+",\"dealContent\":\"" + con + "\",\"btnID\":\"cmBtn3\",\"createUserID\":\"" + PublicUtils.UserID + "\",\"recordID\":\"" + msgid + "\"}");

        XUtil.updatas(getjsonobj,Files, "GetCoordinateMsgDetailsButton", new MyCallBack() {

            /**
             * code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {

               // PublicUtils.toast(res);
                finish();
            }

            /**
             * code ????????? 0??????
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {
                PublicUtils.toast(getbean.getMsg());
                //finish();
            }

            /**
             * ??????????????????
             */
            @Override
            public void onWrong() {
                isonclick=false;
               // PublicUtils.toast("???????????????");
               // finish();
            }

            /**
             * ????????????????????????
             */
            @Override
            public void onFinished() {
                isonclick=false;
            }
        });
    }
    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }
}
