package com.a21zhewang.constructionapp.ui.zxjc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseAddActivity;
import com.a21zhewang.constructionapp.base.BaseFragment;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.Etp;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import cn.hzw.graffiti.GraffitiActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ADDYHWTFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ADDYHWTFragment extends BaseFragment {

    @BindView(R.id.activity_new_check_qestion_RecyclerView)
    RecyclerView activityNewCheckQestionRecyclerView;
   // @BindView(R.id.activity_new_check_qestion_sava)
   // RelativeLayout activityNewCheckQestionSava;
  // @BindView(R.id.spinner_lc)
   MySpinner spinner_lc;
 //   @BindView(R.id.spinner_wz)
    MySpinner spinner_wz;
 //   @BindView(R.id.ed_lc1)
    EditText ed_lc1;
  //  @BindView(R.id.ed_lc2)
    EditText ed_lc2;
 //   @BindView(R.id.ed_wz)
    EditText ed_wz;
 //   @BindView(R.id.lin_lc)
    LinearLayout lin_lc;
 //   @BindView(R.id.tv_title)
    TextView tv_title;

    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imglist = new ArrayList<>();
    private Context mContext;
    private RequestOptions requestOptions;
    private EditText contentinput;
    private List<Etp> etpInfoList;
    private List<com.a21zhewang.constructionapp.bean.User> userInfoList;
    private TextView sgqyTextView;
    private MySpinner zgdwMyspinner;
    private MySpinner zrrMyspinner;
   // private EditText lcEditText;
    private TimeTextView jzsjEditText;

    private Etp etpInfoListBean;
    private User User;
    private NamesSpinnerAdpater<Etp> etpInfoListBeanNamesSpinnerAdpater;
    private NamesSpinnerAdpater<User> UserNamesSpinnerAdpater;
    private List<CheckType> checkTypes;
    private Project pj;
    private Dialog loadingDialog;
    private ErrorMsgBean errorMsgBean = new ErrorMsgBean();
    private ProjectArea projectArea;
    private int selectImageIndex;
    private String ids;
    private String strs;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View.OnClickListener onClickListener;

    private TextView tv_name;
    private EditText ed_lc,ed_bg;
    private MySpinner sp_lc,sp_bg;
    private String[] strs1,strs2,bg,bg2;
    public ADDYHWTFragment() {
        // Required empty public constructor
    }

    public void initV(View view){
        spinner_lc= (MySpinner) view.findViewById(R.id.spinner_lc);
        spinner_wz= (MySpinner) view.findViewById(R.id.spinner_wz);
        ed_lc1= (EditText) view.findViewById(R.id.ed_lc1);
        ed_lc2= (EditText) view.findViewById(R.id.ed_lc2);
        ed_wz= (EditText) view.findViewById(R.id.ed_wz);
        lin_lc= (LinearLayout) view.findViewById(R.id.lin_lc);
        tv_title= (TextView) view.findViewById(R.id.tv_title);

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ADDYHWTFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ADDYHWTFragment newInstance(String param1, String param2) {
        ADDYHWTFragment fragment = new ADDYHWTFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private void getprojectArea(final String what, String proid){
        String obj ="";
        if ("area".equals(what)) {
            obj = "{\"getDataType\":\"" + what + "\",\"projectID\":\"" + proid + "\"}";
        }else if ("subProject".equals(what)){
            obj = "{\"getDataType\":\"" + what + "\",\"projectAreaID\":\"" + proid + "\"}";
        }else if ("etp".equals(what)) {
            obj = "{\"getDataType\":\"" + what + "\",\"projectAreaID\":\"" + proid + "\"}";
        }
        JSONObject getjsonobj = JsonUtils.getjsonobj(obj);
        XUtil.postjsondata(getjsonobj, "SpecialExaminationType", new MyCallBack() {

            @Override
            public void onResule(String res) {
                if ("area".equals(what)){
                    final Project baseAddinitBean = JsonUtils.getbean(res, Project.class);
                    sgqyTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SelectSgqyActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>)baseAddinitBean.getProjectAreaList());
                            startActivityForResult(intent, SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ);
                        }
                    });
                }else if ("subProject".equals(what)){

                    ProjectArea getbean = JsonUtils.getbean(res, ProjectArea.class);
                    final List<SubProjectBean> subProjectList = getbean.getSubProjectList();

                }else if ("etp".equals(what)) {
                    ProjectArea getbean = JsonUtils.getbean(res, ProjectArea.class);
                    List<Etp> etpList = getbean.getEtpInfoList();
                    etpInfoListBeanNamesSpinnerAdpater.replcedata(etpList);
                    onclickzgdw(0);
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
    public void lazyLoad() {
        if (mListener!=null)
        mListener.onFragmentInteraction(onClickListener);
    }

    /**
     * ???????????????
     */
    @Override
    public void initListener() {

          mContext = getActivity();


        etpInfoList = new ArrayList<>();
        etpInfoListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<Etp>(mContext, etpInfoList) {
            @Override
            public void setinit(Etp name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getEtpShortName());
            }
        };
        zgdwMyspinner.setmyspinnerlistadpater(etpInfoListBeanNamesSpinnerAdpater);
        zgdwMyspinner.setlistviewitemonclick(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onclickzgdw(position);
            }
        });


        userInfoList = new ArrayList<>();
        UserNamesSpinnerAdpater = new NamesSpinnerAdpater<User>(mContext, userInfoList) {
            @Override
            public void setinit(User name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getUserName());
            }
        };
        zrrMyspinner.setmyspinnerlistadpater(UserNamesSpinnerAdpater);
        zrrMyspinner.setlistviewitemonclick(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setzrr(position);
            }
        });

    }

    private void setsgqy(String str, ProjectArea projectArea) {
        sgqyTextView.setText(str);
        // etpInfoListBeanNamesSpinnerAdpater.replcedata(projectArea.getEtpInfoList());
        //onclickzgdw(0);
    }

    private void setzrr(int position) {
        if (userInfoList != null && userInfoList.size() > position) {
            User = userInfoList.get(position);
            zrrMyspinner.setTextViewtext(User.getUserName());
        }
        zrrMyspinner.popdismiss();
    }

    private void onclickzgdw(int position) {
        if (etpInfoList != null && etpInfoList.size() > position) {
            etpInfoListBean = etpInfoList.get(position);
            zgdwMyspinner.setTextViewtext(etpInfoListBean.getEtpShortName());
            userInfoList.clear();
            userInfoList.addAll(etpInfoListBean.getUserInfoList());
            UserNamesSpinnerAdpater.notifyDataSetChanged();
            setzrr(0);
        }
        zgdwMyspinner.popdismiss();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_addyhwt;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        //????????????
        strs1 = new String[]{"F", "B"};
        strs2 = new String[]{"DK", "K"};
        bg = new String[]{"???"};
        bg2 = new String[]{"???","???","???","???","???","???"};
        mContext=getActivity();
        ((Activity)mContext).setResult(Activity.RESULT_OK, null);
        Intent intent = ((Activity)mContext).getIntent();
        checkTypes = (List<CheckType>) intent.getSerializableExtra("data");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(mContext, "????????????????????????...");
        pj = intent.getParcelableExtra("pj");
        if (pj == null) return;

        //????????????onActivityResult code
        requestOptions = new RequestOptions().centerCrop();
        activityNewCheckQestionRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        baseQuickAdapter = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(imglist) {
            @Override
            protected void convert(final BaseViewHolder helper, final LocalMedia imgpath) {
                ImageView imgview = helper.getView(R.id.fiv);
                if ("addimg".equals(imgpath.getPath())) {
                    imgview.setImageResource(R.mipmap.addimgs);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remove(getData().size()-1);
                            PictureSelector.create(ADDYHWTFragment.this).openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(9)// ???????????????????????? int
                                    .imageSpanCount(4)// ?????????????????? int
                                    .selectionMode(PictureConfig.MULTIPLE)// ?????? or ?????? PictureConfig.MULTIPLE or PictureConfig.SINGLE
                                    .previewImage(true)// ????????????????????? true or false
                                    .previewVideo(false)// ????????????????????? true or false
                                    .enablePreviewAudio(false) // ????????????????????? true or false
                                    //.compressGrade(Luban.CUSTOM_GEAR)// luban?????????????????????3??? Luban.THIRD_GEAR???Luban.FIRST_GEAR???Luban.CUSTOM_GEAR
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
                                    .cropCompressQuality(10)
                                    .previewEggs(true).forResult(PictureConfig.CHOOSE_REQUEST);
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

        View footer = layoutInflater.inflate(R.layout.newcheck_footer, null, false);
        initV(footer);
        sgqyTextView = (TextView) footer.findViewById(R.id.newcheck_footer_TextView_sgqy);
        zgdwMyspinner = (MySpinner) footer.findViewById(R.id.newcheck_footer_MySpinner_zgdw);
        zrrMyspinner = (MySpinner) footer.findViewById(R.id.newcheck_footer_MySpinner_zrr);
    //    lcEditText = (EditText) footer.findViewById(R.id.newcheck_footer_EditText_lc);
        jzsjEditText = (TimeTextView) footer.findViewById(R.id.newcheck_footer_TimeTextView_jzsj);

        tv_name=(TextView) footer.findViewById(R.id.tv_name);
        sp_lc=(MySpinner) footer.findViewById(R.id.activity_base_add_MySpinner_lc);
        sp_bg=(MySpinner) footer.findViewById(R.id.activity_base_add_MySpinner_bg);
        ed_lc= (EditText) footer.findViewById(R.id.activity_base_add_EditText_lc);
        ed_bg= (EditText) footer.findViewById(R.id.activity_base_add_EditText_bg);
//        if ("1".equals(pj.getProjectType())){
//
//        }
        baseQuickAdapter.addHeaderView(header);
        baseQuickAdapter.addFooterView(footer);
        activityNewCheckQestionRecyclerView.setAdapter(baseQuickAdapter);
        getprojectArea("area",pj.getProjectID());
        if ("1".contains(pj.getProjectType())){
            lin_lc.setVisibility(View.VISIBLE);
            tv_name.setText("??????");
          //  activityBaseAddEditTextlc.setHint("??????");
          //  ed_lc.setHint("??????");
       //     ed_bg.setHint("??????");
            sp_lc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(getContext(), strs1) {
                @Override
                public void setinit(String name, ViewHolder mViewHolder) {
                    mViewHolder.nametextview.setText(name);
                }
            });
            sp_lc.setTextViewtext(strs1[0]);
            sp_lc.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                @Override
                public String onclick(int position) {

                    return strs1[position];
                }
            });
            sp_bg.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(getContext(), bg) {
                @Override
                public void setinit(String name, ViewHolder mViewHolder) {
                    mViewHolder.nametextview.setText(name);
                }
            });
            sp_bg.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                @Override
                public String onclick(int position) {

                    return bg[position];
                }
            });
            sp_bg.setTextViewtext(bg[0]);
        }else{
            lin_lc.setVisibility(View.GONE);

            tv_name.setText("??????");
       //     ed_lc.setHint("??????");
        //    ed_bg.setHint("??????");

            spinner_lc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(getContext(), strs2) {
                @Override
                public void setinit(String name, ViewHolder mViewHolder) {
                    mViewHolder.nametextview.setText(name);
                }
            });
            spinner_lc.setTextViewtext(strs2[0]);
            spinner_lc.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                @Override
                public String onclick(int position) {

                    return strs2[position];
                }
            });
            spinner_wz.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(getContext(), bg2) {
                @Override
                public void setinit(String name, ViewHolder mViewHolder) {
                    mViewHolder.nametextview.setText(name);
                }
            });
            spinner_wz.setTextViewtext(bg2[0]);
            spinner_wz.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                @Override
                public String onclick(int position) {

                    return bg2[position];
                }
            });
        }
    }


    private List<MsgType> haschild(List<MsgType> msgType) {

        if (msgType.size() > 0) {
            MsgType type = msgType.get(0);
            List<MsgType> msgTypes = type.getMsgTypes();
            if (msgTypes == null) {
                msgTypes = new ArrayList<>();
                type.setMsgTypes(msgTypes);
                return msgTypes;
            }
            return haschild(msgTypes);
        }

        return msgType;
    }

    /**
     * ????????????
     *
     * @param json
     * @param files
     * @param method
     */
    private void postjson(String json, List<String> files, String method) {
        loadingDialog.show();
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            /**
             * ????????????code ?????? 0??????
             *
             * @param res
             */
            @Override
            public void onResule(String res) {

                Intent isok = new Intent().putExtra("ErrorMsgBean", errorMsgBean);
                ((Activity)mContext).setResult(Activity.RESULT_OK, isok);
                ((Activity)mContext).finish();
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

            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }
    @Override
    public void initData() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getjson = getjson();
                String method = getmethod();
                if (!TextUtils.isEmpty(getjson) && !TextUtils.isEmpty(method)) {
                    if ("AddSafetyMsg".equals(method)) {
                        errorMsgBean.setRelationTable("SG_SafetyMsg");
                    } else if ("AddQualityMsg".equals(method)) {
                        errorMsgBean.setRelationTable("SG_QualityMsg");
                    } else if ("AddCivilizedMsg".equals(method)) {
                        errorMsgBean.setRelationTable("SG_CivilizedMsg");
                    }
                    List<String> files = new ArrayList<String>();
                    List<LocalMedia> data = baseQuickAdapter.getData();
                    for (LocalMedia datum : data) {
                        files.add(datum.getCompressPath());
                    }

                    files.remove(files.size() - 1);
                    postjson(getjson, files, method);
                }
            }
        };
        int isSafe = getActivity().getIntent().getIntExtra("isSafe", -1);
        if(isSafe == 1){
            lazyLoad();
        }
    }
    /**
     * @return ??????json
     */
    private String getjson() {
        JSONObject jsonObject = new JSONObject();
        try {
            String value = UUID.randomUUID().toString();
            jsonObject.put("recordID", value);//??????UUID
            errorMsgBean.setRelationID(value);
            jsonObject.put("projectID", pj.getProjectID());
            jsonObject.put("projectShortName", pj.getProjectShortName());
            if (projectArea == null) {
                PublicUtils.toast("?????????????????????");
                return "";
            }
            //  String projectAreaName = projectArea.getProjectAreaName();
            errorMsgBean.setProjectAreaName(strs);
            errorMsgBean.setProjectAreaID(ids);
            jsonObject.put("projectAreaName", strs);
            jsonObject.put("projectAreaID", ids);
            String trim = ed_lc.getText().toString().trim();
            String bgtrim = ed_bg.getText().toString().trim();
            String lc1 = ed_lc1.getText().toString().trim();
            String lc2 = ed_lc2.getText().toString().trim();
            String wz = ed_wz.getText().toString().trim();
            boolean b = lin_lc.getVisibility() == View.VISIBLE;
            if (b) {
                if (TextUtils.isEmpty(trim.trim()) && bgtrim.length() == 0) {
                    PublicUtils.toast("????????????????????????");
                    return "";
                }
                errorMsgBean.setFloor(trim);
                errorMsgBean.setElevation(bgtrim);
                if (!TextUtils.isEmpty(trim))
                    jsonObject.put("floor", trim+sp_lc.getTextViewtext());
                if (!TextUtils.isEmpty(bgtrim))
                    jsonObject.put("elevation", bgtrim+sp_bg.getTextViewtext());
            } else {
                if (TextUtils.isEmpty(wz) && (TextUtils.isEmpty(lc1) || TextUtils.isEmpty(lc2))) {
                    PublicUtils.toast("????????????????????????????????????");
                    return "";
                }


                if (!TextUtils.isEmpty(wz)) {
                    String value1 = wz + spinner_wz.getTextViewtext();
                    jsonObject.put("elevation", value1);
                    errorMsgBean.setElevation(value1);
                }

                if (!TextUtils.isEmpty(lc1)&&!TextUtils.isEmpty(lc2)){

                    String value1 = spinner_lc.getTextViewtext() + lc1 + "+" + lc2;
                    errorMsgBean.setFloor(value1);
                    jsonObject.put("floor", value1);
                }

            }
//
//            errorMsgBean.setFloor(trim);
//            errorMsgBean.setElevation(bgtrim);
//            if (!TextUtils.isEmpty(trim))
//            jsonObject.put("floor", trim+sp_lc.getTextViewtext());
//            if (!TextUtils.isEmpty(bgtrim))
//            jsonObject.put("elevation", bgtrim+sp_bg.getTextViewtext());
            if (checkTypes == null || checkTypes.size() == 0) {
                PublicUtils.toast("?????????????????????");
                return "";
            }
            List<MsgType> msgTypes = new ArrayList<>();

            for (int i = 0; i < checkTypes.size(); i++) {
                MsgType msgType = new MsgType();
                CheckType checkType = checkTypes.get(i);
                msgType.setTypeID(checkType.getDicID());
                msgType.setTypeName(checkType.getDicName());
                List<MsgType> haschild = haschild(msgTypes);
                haschild.add(msgType);
            }
            jsonObject.put("msgTypes", new JSONArray(JsonUtils.objtojson(msgTypes)));
            String content = contentinput.getText().toString().trim();
//            if (TextUtils.isEmpty(content)) {
//                PublicUtils.toast("?????????????????????");
//                return "";
//            }

            jsonObject.put("content", content);
            jsonObject.put("cutoffTime", jzsjEditText.getText());
            if (etpInfoListBean == null) {
                PublicUtils.toast("?????????????????????");
                return "";
            }
            String etpID = etpInfoListBean.getEtpID();
            errorMsgBean.setRecEtpID(etpID);
            jsonObject.put("recEtpID", etpID);
            String etpShortName = etpInfoListBean.getEtpShortName();
            errorMsgBean.setRecEtpShortName(etpShortName);
            jsonObject.put("recEtpShortName", etpShortName);
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
            jsonObject.put("createUserTypeID", PublicUtils.userBean.getUserTypeID());
            jsonObject.put("createUserType", PublicUtils.userBean.getUserType());
            jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
            if (User == null) {
                PublicUtils.toast("??????????????????");
                return "";
            }
            JSONArray jsonArray = new JSONArray();
            JSONObject user = new JSONObject();
            user.put("userID", User.getUserID());
            user.put("userName", User.getUserName());
            jsonArray.put(user);
            jsonObject.put("receivers", jsonArray);//???????????????
            JSONArray files = new JSONArray();
            List<LocalMedia> list = baseQuickAdapter.getData();
            if (list.size() > 0) {
                for (int i = 0; i < list.size() - 1; i++) {
                    JSONObject object = new JSONObject();
                    String path = list.get(i).getCompressPath();
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    object.put("fileBrief", "???" + (i + 1) + "???");
                    object.put("fileName", fileName);
                    files.put(object);
                }
            }
            jsonObject.put("files", files);//????????????

        } catch (JSONException e) {
            PublicUtils.toast("??????????????????");
        }
        return jsonObject.toString();
    }
    private String getmethod() {
        CheckType checkType = checkTypes.get(checkTypes.size() - 1);
        String c = String.valueOf(checkType.getDicID().charAt(3));
        if ("S".equals(c)||"W".equals(c)) {
            return "AddSafetyMsg";//????????????
        } else if ("Q".equals(c)) {
            return "AddQualityMsg";//????????????
        } else if ("C".equals(c)) {
            return "AddCivilizedMsg";//????????????
        }
        return "";
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
        if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);

            baseQuickAdapter.replaceData(list);
//            String objtojson = JsonUtils.objtojson(list);
//            PublicUtils.log("json:"+ objtojson);
//            for (LocalMedia localMedia : list) {
//                long length = new File(localMedia.getPath()).length();
//                PublicUtils.log("???????????????"+ length);
//                PublicUtils.log("??????????????????"+new File(localMedia.getCompressPath()).length()); ;
//            }
            //PublicUtils.log("???????????????" + list.size() + "???");

        }if (requestCode == PictureConfig.CHOOSE_REQUEST){
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (requestCode == SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ
                && resultCode == Activity.RESULT_OK
                && data != null) {
            strs = data.getStringExtra("strs");
            ids = data.getStringExtra("ids");
            projectArea = data.getParcelableExtra("projectArea");
            setsgqy(strs, projectArea);
            getprojectArea("etp",projectArea.getProjectAreaID());

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
