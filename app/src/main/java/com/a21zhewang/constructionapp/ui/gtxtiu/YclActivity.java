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
        return R.layout.activity_ycl;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        etpBeens=new ArrayList<>();
        msgid = getIntent().getStringExtra("msgid");
        projectID = getIntent().getStringExtra("projectID");

        activityYclCanyurenView.setTextstr("请选择起因单位");
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
     * 初始化事件
     */
    @Override
    public void initListener() {
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
                //.compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// 是否显示gif图片 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(imglist)// 是否传入已选图片 List<LocalMedia> list
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
                    PublicUtils.toast("一次只能传5张图片！");
                   return;
                }
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);

            }
        });
    }

    /**
     * 初始化数据
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
     * @param v 点击事件的实现
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
//                    PublicUtils.toast("请选择起因单位");
//                    return;
//                }
                String string = activityYclEditTextClyj.getText().toString();
                if (string.length()==0){
                    PublicUtils.toast("输入处理意见");
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
                            imgfile.put("fileBrief", "第" + (i + 1) + "张图片");
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
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {

               // PublicUtils.toast(res);
                finish();
            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {
                PublicUtils.toast(getbean.getMsg());
                //finish();
            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {
                isonclick=false;
               // PublicUtils.toast("联网失败！");
               // finish();
            }

            /**
             * 总是回调用的方法
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
