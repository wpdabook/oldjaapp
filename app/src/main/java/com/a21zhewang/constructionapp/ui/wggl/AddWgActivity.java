package com.a21zhewang.constructionapp.ui.wggl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AddTzgginitBean;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.RiskTypeInfo;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReportAdd;
import com.a21zhewang.constructionapp.ui.risk.Act_SelectRiskType;
import com.a21zhewang.constructionapp.utils.DateUtils;
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

public class AddWgActivity extends BaseActivity {

    @BindView(R.id.activity_add_tzgg_MyTitleBar)
    MyTitleBar activityAddTzggMyTitleBar;
    @BindView(R.id.activity_add_tzgg_EditText_xxbt)
    TextView activityAddTzggEditTextXxbt;
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
    @BindView(R.id.lin_wg_type)
    LinearLayout lin_wg_type;
    private List<LocalMedia> imglist;
    private ImageOptions imageOptions;
    private CommonAdapter<LocalMedia> commonAdapter;
    private List<Etp> etpInfoList;
    private CanyurenView.CanyurenViewAPI<User> canyurenapi;
    private List<User> userInfoList;
    private List<Etp> addInfoList; //?????????list
    private Etp etpInfoListBean;
    private com.zhy.adapter.abslistview.CommonAdapter<Etp> addadapter;
    private MySpinner.MySpinnerAPI dwonClickListener;
    private NamesSpinnerAdpater<Etp> etpInfoListBeanNamesSpinnerAdpater;
    private Project pjbean;
    private Dialog loadingDialog;
    private String typeID;
    private PictureSelectionModel pictureSelectionModel;
    private int selectImageIndex;
    private JSONArray elementArray = new JSONArray();
    private JSONArray itemCompanyArray = new JSONArray();

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
        return R.layout.add_wggl_layout;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        activityAddTzggMySpinnerJsr.setCanSeleltAll(false);
        activityAddTzggMyTitleBar.setTitlenametv("???????????????");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "????????????");
        imglist = new ArrayList<>();

        pictureSelectionModel = PictureSelector.create(this).openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)// ???????????????????????? int
                .imageSpanCount(4)// ?????????????????? int
                .selectionMode(PictureConfig.MULTIPLE)// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// ????????????????????? true or false
                .previewVideo(false)// ????????????????????? true or false
                .enablePreviewAudio(false) // ????????????????????? true or false
                //   .compressGrade(Luban.CUSTOM_GEAR)// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
                .isCamera(true)// ???????????????????????? true or false
                .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                .sizeMultiplier(0.5f)// glide ?????????????????? 0~1?????? ????????? .glideOverride()??????
                .enableCrop(false)// ???????????? true or false
                .compress(true)// ???????????? true or false
                //  .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//???????????? or ???????????? PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
                .isGif(false)// ????????????gif?????? true or false
                .showCropFrame(false)// ?????????????????????????????? ???????????????????????????false   true or false
                .showCropGrid(false)// ?????????????????????????????? ???????????????????????????false    true or false
                .openClickSound(false)// ???????????????????????? true or false
                .selectionMedia(imglist)// ???????????????????????? List<LocalMedia> list
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
                        GraffitiActivity.startActivityForResult(AddWgActivity.this,s.getCompressPath());
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
//                if (etpShortName.equals("??????")) {
//                    activityAddTzggMySpinnerJsr.settextviewstr("??????");
//                    activityAddTzggMySpinnerJsr.setClickable(false);
//                    addInfoList.clear();
//                    addadapter.notifyDataSetChanged();
//                    return etpShortName;
//                }
                activityAddTzggMySpinnerJsr.setClickable(true);


                userInfoList = etpInfoListBean.getUserInfoList();
//                if(!etpInfoListBean.getUserInfoList().get(0).getUserName().equals("??????")){
//                    User e = new User();
//                    e.setUserID("all");
//                    e.setUserName("??????");
//                    userInfoList.add(0,e);
//                }
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

        //?????????
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
        lin_wg_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pjbean != null){
                    startActivityForResult(new Intent(AddWgActivity.this,WGSelectTypeActivity.class)
                            .putExtra("projectId",pjbean.getProjectID()), WGSelectTypeActivity.RESQUST_CODE_SELECT_TYPE);
                }else {
                    showToast("???????????????");
                }
            }
        });
        /**
         * ????????????
         */
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "projects" + "\"}"), "GetSocialSuperviseTypes", new MyCallBack() {
            /**
             * ????????????code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                AddTzgginitBean addTzgginitBean = JsonUtils.getbean(res, AddTzgginitBean.class);
                final List<Project> projectList = addTzgginitBean.getProjectList();
                if (projectList != null && projectList.size() > 0) {
                    activityAddTzggTextViewPjname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AddWgActivity.this, SelectProjectActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    });
                }
            }

            /**
             * ????????????code ????????? 0??????
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * ??????????????????
             */
            @Override
            public void onWrong() {

            }
        });
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {

    }

    /**
     * ???????????????
     */
    @Override
    public void initData() {

    }

    /**
     * @param v ?????????????????????
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
                    PublicUtils.toast("???????????????");
                    break;
                }
//                else if (etpInfoListBean.getEtpShortName().equals("??????")) {
//                    PublicUtils.toast("???????????????");
//                    break;
//                }

                Set<Integer> list = activityAddTzggMySpinnerJsr.getList();
                if (list == null || list.size() == 0) {
                    PublicUtils.toast("??????????????????");
                    break;
                }
                List<Integer> addList = new ArrayList<>(list);
                if (addInfoList.contains(etpInfoListBean)) {
                    for (int i = 0; i < addInfoList.size(); i++) {
                        Etp etpListBean = addInfoList.get(i);
                        if (etpListBean.equals(etpInfoListBean)) {
                            List<User> beanList = etpListBean.getUserInfoList();
                            beanList.clear();

                            for (int j = 0; j < addList.size(); j++) {
                                List<User> listBeen = etpInfoListBean.getUserInfoList();
                                User Bean = listBeen.get(addList.get(j));
//                                if ("all".equals(Bean.getUserID()))
//                                    continue;
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
            PublicUtils.toast("???????????????");
            return;
        }
        String xxbttoString = activityAddTzggEditTextXxbt.getText().toString().trim();
        if (xxbttoString.length() == 0) {
            PublicUtils.toast("?????????????????????");
            return;
        }
        String nrtoString = activityAddTzggEditTextNr.getText().toString().trim();
        if (nrtoString.length() == 0) {
            PublicUtils.toast("???????????????");
            return;
        }
        String dwtext = activityAddTzggMySpinnerDw.getTextViewtext();
        if (dwtext.equals("????????????")) {
            PublicUtils.toast("???????????????");
            return;
        }
        if (activityAddTzggMySpinnerJsr.getTextstr().equals("???????????????")) {
            PublicUtils.toast("???????????????");
            return;
        }

        if (!dwtext.equals("??????") && addInfoList.size() == 0) {
            PublicUtils.toast("???????????????+?????????????????????");
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
                imgfile.put("fileBrief", "???" + (i + 1) + "?????????");
                imgfiles.put(imgfile);
            }
            jsonObject.put("files", imgfiles);
//            jsarray = JsonUtils.getjsonobjs(addInfoList);
//            if (!dwtext.equals("??????")) {
//                jsarray = JsonUtils.getjsonobjs(addInfoList);
//            } else {
//                jsarray = JsonUtils.getjsonobjs(pjbean.getEtpInfoList());
//            }
            for(int i=0;i<addInfoList.size();i++){
                 List<User> userList = addInfoList.get(i).getUserInfoList();
                for(int j=0;j<userList.size();j++){
                    JSONObject object = new JSONObject();
                    object.put("companyId",addInfoList.get(i).getEtpID());
                    object.put("companyName",addInfoList.get(i).getEtpShortName());
                    object.put("userId",userList.get(j).getUserID());
                    object.put("userName",userList.get(j).getUserName());
                    itemCompanyArray.put(object);
                }
            }
            jsonObject.put("projectId", pjbean.getProjectID());
            jsonObject.put("projectName", pjbean.getProjectShortName());
            jsonObject.put("inspectDate", DateUtils.getTodayDate());
            jsonObject.put("remark", nrtoString);
            jsonObject.put("elementList", elementArray);
            jsonObject.put("itemCompanyList", itemCompanyArray);

        } catch (JSONException e) {
            PublicUtils.toast("json?????????????????????");
        }
        loadingDialog.show();
        MyCallBack callback = new MyCallBack() {
            /**
             * ????????????code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                WeiboDialogUtils.closeDialog(loadingDialog);
                finish();
            }

            /**
             * ????????????code ????????? 0??????
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {
                loadingDialog.dismiss();
            }

            /**
             * ??????????????????
             */
            @Override
            public void onWrong() {
                loadingDialog.dismiss();
            }
        };

        XUtil.updatas(jsonObject, imgs, "EditSocialSupervise", callback);
    }
    /**
     * --????????????????????????
     * @param projectId
     */
    public void getProjectType(final String projectId){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "Social" + "\",\"projectId\":\"" + projectId + "\"}"), "GetSocialSuperviseTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                getCompanys(projectId);//???????????????????????????
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
     * --?????????????????????
     * @param projectId
     */
    public void getCompanys(String projectId){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "companys" + "\",\"projectId\":\"" + projectId + "\"}"), "GetSocialSuperviseTypes", new MyCallBack() {
            /**
             * ????????????code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                try{
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("etpInfoList");
                    List<Etp> eIList = new ArrayList<Etp>();
                    for(int i=0;i<array.length();i++){
                        JSONObject tempobject = array.optJSONObject(i);
                        Etp etp = new Etp();
                        etp.setEtpID(tempobject.optString("etpID"));
                        etp.setEtpShortName(tempobject.optString("etpShortName"));
                        List<User> userInfoList = new ArrayList<User>();
                        JSONArray temparray = tempobject.optJSONArray("userInfoList");
                        for(int j=0;j<temparray.length();j++){
                            JSONObject object1 = temparray.optJSONObject(j);
                            User user = new User();
                            user.setUserID(object1.optString("userID"));
                            user.setUserName(object1.optString("userName"));
                            userInfoList.add(user);
                            etp.setUserInfoList(userInfoList);
                        }
                        eIList.add(etp);
                    }
                    if (eIList != null && eIList.size() > 0) {
                        etpInfoListBeanNamesSpinnerAdpater.adddata(eIList);
                        activityAddTzggMySpinnerDw.setTextViewtext(dwonClickListener.onclick(0));
                    }
                }catch (JSONException e){

                }

            }

            /**
             * ????????????code ????????? 0??????
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * ??????????????????
             */
            @Override
            public void onWrong() {

            }
        });
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
            getProjectType(pjbean.getProjectID());
        }
        if (requestCode == WGSelectTypeActivity.RESQUST_CODE_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            String dicId = data.getStringExtra("dicId");
            String fatherId = data.getStringExtra("fatherId");
            activityAddTzggEditTextXxbt.setText(strs);
//            List<RiskTypeInfo> riskTypelist = data.getParcelableArrayListExtra("list");
            try{
                JSONObject object = new JSONObject();
                object.put("dicId",dicId);
                object.put("dicName",strs);
                object.put("fatherId",fatherId);
                elementArray.put(object);
            }catch (JSONException e){
                e.printStackTrace();
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
