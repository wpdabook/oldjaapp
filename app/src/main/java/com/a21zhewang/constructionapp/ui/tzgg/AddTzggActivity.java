package com.a21zhewang.constructionapp.ui.tzgg;

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
import com.a21zhewang.constructionapp.bean.AddTzgginitBean;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

public class AddTzggActivity extends BaseActivity {

    @BindView(R.id.activity_add_tzgg_MyTitleBar)
    MyTitleBar activityAddTzggMyTitleBar;
    @BindView(R.id.activity_add_tzgg_EditText_xxbt)
    EditText activityAddTzggEditTextXxbt;
    @BindView(R.id.activity_add_tzgg_EditText_nr)
    EditText activityAddTzggEditTextNr;
    @BindView(R.id.activity_add_tzgg_RecyclerView_imgs)
    RecyclerView activityAddTzggRecyclerViewImgs;
    @BindView(R.id.activity_add_tzgg_MySpinner_dw)
    MySpinner activityAddTzggMySpinnerDw;
    @BindView(R.id.activity_add_tzgg_MySpinner_jsr)
    CanyurenView activityAddTzggMySpinnerJsr;
    @BindView(R.id.activity_add_tzgg_MyListView_dw)
    MyListView activityAddTzggMyListViewDw;
    @BindView(R.id.activity_add_tzgg_TextView_pjname)
    TextView activityAddTzggTextViewPjname;



    private List<LocalMedia> imglist;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private List<Etp> etpInfoList;
    private CanyurenView.CanyurenViewAPI<User> canyurenapi;
    private List<User> userInfoList;
    private List<Etp> addInfoList;
    private Etp etpInfoListBean;
    private com.zhy.adapter.abslistview.CommonAdapter<Etp> addadapter;
    private MySpinner.MySpinnerAPI dwonClickListener;
    private NamesSpinnerAdpater<Etp> etpInfoListBeanNamesSpinnerAdpater;
    private Project pjbean;
    private Dialog loadingDialog;
    private String typeID;
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
        return R.layout.activity_add_tzgg;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        activityAddTzggMySpinnerJsr.setCanSeleltAll(true);
        typeID = getIntent().getStringExtra("typeID");
        if (typeID.equals("notice2")) {
            activityAddTzggMyTitleBar.setTitlenametv("发布重点督办");
        } else {
            activityAddTzggMyTitleBar.setTitlenametv("发布施工提醒");

        }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "数据加载中..");
        loadingDialog.show();
        imglist = new ArrayList<>();

        pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// 最大图片选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
             //   .compressGrade(Luban.CUSTOM_GEAR)// luban压缩档次，默认3档 Luban.THIRD_GEAR、Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
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
        activityAddTzggRecyclerViewImgs.setLayoutManager(layoutManager);
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
                        //pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);
                        selectImageIndex=position;
                        GraffitiActivity.startActivityForResult(AddTzggActivity.this,s.getCompressPath());
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
        activityAddTzggRecyclerViewImgs.setAdapter(commonAdapter);

        etpInfoList = new ArrayList<>();
        etpInfoListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<Etp>(this, etpInfoList) {
            @Override
            public void setinit(Etp name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getEtpShortName());
            }
        };
        activityAddTzggMySpinnerDw.setmyspinnerlistadpater(etpInfoListBeanNamesSpinnerAdpater);
        dwonClickListener = new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                etpInfoListBean = etpInfoList.get(position);
                String etpShortName = etpInfoListBean.getEtpShortName();
                if (etpShortName.equals("全部")) {
                    activityAddTzggMySpinnerJsr.settextviewstr("全部");
                    activityAddTzggMySpinnerJsr.setClickable(false);
                    addInfoList.clear();
                    addadapter.notifyDataSetChanged();
                    return etpShortName;
                }
                activityAddTzggMySpinnerJsr.setClickable(true);


                userInfoList = etpInfoListBean.getUserInfoList();
                if(!etpInfoListBean.getUserInfoList().get(0).getUserName().equals("全部")){
                    User e = new User();
                    e.setUserID("all");
                    e.setUserName("全部");
                    userInfoList.add(0,e);
                }
                if (userInfoList != null)
                    activityAddTzggMySpinnerJsr.setList(userInfoList, canyurenapi);
                Set<Integer> integers = new HashSet<>();
                if (addInfoList.contains(etpInfoListBean)) {
                    for (int i = 0; i < addInfoList.size(); i++) {
                        if (addInfoList.get(i).getEtpShortName().equals(etpInfoListBean.getEtpShortName())) {
                            List<User> been = addInfoList.get(i).getUserInfoList();
                            for (int j = 0; j < userInfoList.size(); j++) {
                                if (been.contains(userInfoList.get(j))) {
                                    integers.add(j);
                                }
                            }
                        }
                    }
                }
                activityAddTzggMySpinnerJsr.setselsctor(integers);
                return etpShortName;
            }
        };
        activityAddTzggMySpinnerDw.setlistviewitemonclick(dwonClickListener);

        //接收人
        canyurenapi = new CanyurenView.CanyurenViewAPI<User>() {
            @Override
            public void setstr(int size, TextView textView, Set ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName());
                } else if (size == 2) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName() + "," + userInfoList.get(integers.get(1)).getUserName());
                } else if (size > 2) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName() + "," + userInfoList.get(integers.get(1)).getUserName() + "...");
                }
            }

            @Override
            public String getstr(int i, User o) {
                return o.getUserName();
            }
        };
        activityAddTzggMySpinnerJsr.setMaxSelect(-1);
        activityAddTzggMySpinnerJsr.setCanyurenViewAPI(canyurenapi);

        addInfoList = new ArrayList<>();
        addadapter = new com.zhy.adapter.abslistview.CommonAdapter<Etp>(this, R.layout.addtzggdw_item, addInfoList) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, Etp item, final int position) {
                viewHolder.setText(R.id.addtzgg_item_Dw, item.getEtpShortName());
                List<User> beanList = item.getUserInfoList();
                if (beanList != null && beanList.size() > 0) {
//                    if (beanList.size() == 1) {
//                        viewHolder.setText(R.id.addtzgg_item_user, beanList.get(0).getUserName());
//                    } else if (beanList.size() == 2) {
//                        viewHolder.setText(R.id.addtzgg_item_user, beanList.get(0).getUserName() + "," + beanList.get(1).getUserName());
//                    } else {
//                        viewHolder.setText(R.id.addtzgg_item_user, beanList.get(0).getUserName() + "," + beanList.get(1).getUserName() + "...");
//                    }
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < beanList.size(); i++) {
                        buffer.append(beanList.get(i).getUserName());
                        if (i != beanList.size() - 1)
                            buffer.append(",");
                    }
                    viewHolder.setOnClickListener(R.id.addtzgg_item_ImageView_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addInfoList.remove(position);
                            addadapter.notifyDataSetChanged();
                        }
                    });
                    viewHolder.setText(R.id.addtzgg_item_user, buffer.toString());
                }


            }
        };
        activityAddTzggMyListViewDw.setAdapter(addadapter);
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"typeID\":\"" + typeID + "\"}"), "GetNoticeMsgInitialize", new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                AddTzgginitBean addTzgginitBean = JsonUtils.getbean(res, AddTzgginitBean.class);
                final List<Project> projectList = addTzgginitBean.getProjectList();
                if (projectList != null && projectList.size() > 0) {
                    activityAddTzggTextViewPjname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddTzggActivity.this, SelectProjectActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    });



                }
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
        });
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_add_tzgg_TextView_submit, R.id.activity_add_tzgg_TextView_cancle, R.id.activity_add_tzgg_ImageView_addimgs, R.id.activity_add_tzgg_LinearLayout_adduser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_add_tzgg_ImageView_addimgs:
                pictureSelectionModel.forResult(PictureConfig.CHOOSE_REQUEST);

                break;
            case R.id.activity_add_tzgg_TextView_submit:
                postdata();
                break;
            case R.id.activity_add_tzgg_LinearLayout_adduser:
                if (etpInfoListBean == null) {
                    PublicUtils.toast("请选择单位");
                    break;
                } else if (etpInfoListBean.getEtpShortName().equals("全部")) {
                    PublicUtils.toast("已选择全部");
                    break;
                }

                Set<Integer> list = activityAddTzggMySpinnerJsr.getList();
                if (list == null || list.size() == 0) {
                    PublicUtils.toast("请选择接收人");
                    break;
                }
                List<Integer> addList = new ArrayList<>(list);
                if (addInfoList.contains(etpInfoListBean)) {
                    // PublicUtils.toast("you ");
                    for (int i = 0; i < addInfoList.size(); i++) {
                        Etp etpListBean = addInfoList.get(i);
                        if (etpListBean.equals(etpInfoListBean)) {
                            List<User> beanList = etpListBean.getUserInfoList();
                            beanList.clear();

                            for (int j = 0; j < addList.size(); j++) {
                                List<User> listBeen = etpInfoListBean.getUserInfoList();
                                User Bean = listBeen.get(addList.get(j));
                                if ("all".equals(Bean.getUserID()))
                                    continue;
                                beanList.add(Bean);
                            }
                            addadapter.notifyDataSetChanged();
                            break;
                        }
                    }
                } else {
                    Etp eInfoBean = new Etp();
                    eInfoBean.setEtpID(etpInfoListBean.getEtpID());
                    eInfoBean.setEtpShortName(etpInfoListBean.getEtpShortName());
                    List<User> uiList = new ArrayList<>();

                    for (int i = 0; i < addList.size(); i++) {
                        List<User> listBeen = etpInfoListBean.getUserInfoList();
                        User Bean = listBeen.get(addList.get(i));
                        uiList.add(Bean);
                    }
                    eInfoBean.setUserInfoList(uiList);
                    addInfoList.add(eInfoBean);
                    addadapter.notifyDataSetChanged();
                }


                break;
            case R.id.activity_add_tzgg_TextView_cancle:
                finish();
                break;
        }
    }

    private void postdata() {
        if (pjbean==null){
            PublicUtils.toast("请选择项目");
            return;
        }
        String xxbttoString = activityAddTzggEditTextXxbt.getText().toString().trim();
        if (xxbttoString.length() == 0) {
            PublicUtils.toast("请输入信息标题");
            return;
        }
        String nrtoString = activityAddTzggEditTextNr.getText().toString().trim();
        if (nrtoString.length() == 0) {
            PublicUtils.toast("请输入内容");
            return;
        }
        String dwtext = activityAddTzggMySpinnerDw.getTextViewtext();
        if (dwtext.equals("选择单位")) {
            PublicUtils.toast("请选择单位");
            return;
        }
        if (activityAddTzggMySpinnerJsr.getTextstr().equals("选择接收人")) {
            PublicUtils.toast("选择接收人");
            return;
        }

        if (!dwtext.equals("全部") && addInfoList.size() == 0) {
            PublicUtils.toast("请点击蓝色+号添加接收单位");
            return;
        }
        JSONObject jsonObject = new JSONObject();
        List<String> imgs = new ArrayList<>();
        try {
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
            jsonObject.put("files", imgfiles);
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
            JSONArray jsarray = null;
            if (!dwtext.equals("全部")) {
                jsarray = JsonUtils.getjsonobjs(addInfoList);
            } else {
                jsarray = JsonUtils.getjsonobjs(pjbean.getEtpInfoList());
            }

            jsonObject.put("recEtps", jsarray);
            jsonObject.put("projectID", pjbean.getProjectID());
            jsonObject.put("typeID", typeID);
//            jsonObject.put("recordTime","");
            jsonObject.put("title", xxbttoString);
            jsonObject.put("content", nrtoString);
            jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
            jsonObject.put("proShortName", pjbean.getProjectShortName());

        } catch (JSONException e) {
            PublicUtils.toast("json数据生产错误！");
        }
        loadingDialog.show();
        MyCallBack callback = new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                finish();
            }

            /**
             * 请求成功code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {
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

        XUtil.updatas(jsonObject, imgs, "AddNoticeMsg", callback);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            imglist.clear();
            if (list != null && list.size() > 0) {
                imglist.addAll(list);
                commonAdapter.notifyDataSetChanged();
                activityAddTzggRecyclerViewImgs.scrollToPosition(imglist.size() - 1);
            }
        }
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            activityAddTzggTextViewPjname.setText(strs);
            pjbean=data.getParcelableExtra("project");
            List<Etp> eIList = pjbean.getEtpInfoList();
            if (eIList != null && eIList.size() > 0) {
                etpInfoListBeanNamesSpinnerAdpater.adddata(eIList);
               // Etp etpBean = new Etp();
              //  etpBean.setEtpShortName("全部");
                activityAddTzggMySpinnerDw.setTextViewtext(dwonClickListener.onclick(0));
             //   etpInfoListBeanNamesSpinnerAdpater.adddata(etpBean);
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

    @Override
    public void destroy() {
        FileUtil.deleteCaCheImage();
        super.destroy();
    }
}
