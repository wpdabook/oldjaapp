package com.a21zhewang.constructionapp.ui.wdjc;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectStatus;
import com.a21zhewang.constructionapp.bean.Zxjcxqbean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.SendImageUtil;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;


/**
 * ????????????
 * @author WuPeng 2019/4/25
 *
 */
public class WdjcxqActivity2 extends BaseActivity {

    @BindView(R.id.activity_wdjc_commit)
    RelativeLayout commit_btn;
    @BindView(R.id.activity_zxjcxq_RecyclerView)
    RecyclerView activityZxjcxqRecyclerView;

    private BaseQuickAdapter<CheckType, BaseViewHolder> baseQuickAdapter;
    private String recordID;
    private View header;
    private Context mContext;
    private View footer;
    private static final int JC_REQUESTCODE = 1912;
    List<CheckType> checks = new ArrayList<>();
    private Zxjcxqbean wdbean;
    private Project pj = new Project();
    private JSONObject comitObject = new JSONObject();
    private JSONObject safetyObject = new JSONObject();
    private Dialog loadingDialog;
    private List<String> files; //????????????????????????
    private JSONArray FileArray; //??????????????????
    private JSONArray ErrorMsgArray = new JSONArray(); //??????????????????
    private String CheckStatus;
    private int checkType;
    public final static int RESQUST_CODE_SELECT_DELETE_ITEM = 109;
    private String xPoint,yPoint;
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
        return R.layout.activity_wdjcxq;
    }

    /**
     * ???????????????
     */
    @Override
    public void initViews() {
        mContext = this;
        getLocation();
        recordID = getIntent().getStringExtra("recordID");
        CheckStatus = getIntent().getStringExtra("CheckStatus");
        if("3".equals(CheckStatus)) { //2:?????????  3????????????
            commit_btn.setVisibility(View.VISIBLE);
        }else {
            commit_btn.setVisibility(View.GONE);
        }
        checkType = getIntent().getIntExtra("checkType",1);
        if (TextUtils.isEmpty(recordID)){
            return;
        }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "????????????...");
        footer = LayoutInflater.from(this).inflate(R.layout.table_list_footer, null, false);
        final Button savebtn = (Button) footer.findViewById(R.id.table_list_footer_save);
        Button submitbtn = (Button) footer.findViewById(R.id.table_list_footer_submit);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                savedata();
            }
        });
        commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                List<CheckType> data = baseQuickAdapter.getData();
//                if (data == null || data.size() == 0) return;
//                for (int i = 0; i < data.size(); i++) {
//                    if ("?????????".equals(data.get(i).getCheckResult())) {
//                        PublicUtils.toast("????????????????????????");
//                        return;
//                    }
//                }
//                loadingDialog.show();
//                if (!TextUtils.isEmpty(comitObject.toString())) {
//                    submitCheckData(JsonUtils.getjsonobj(comitObject.toString()), "EditKeyExamination");
//                }
                startActivity(new Intent(WdjcxqActivity2.this,ProjectStatusActivity.class)
                        .putExtra("recordID",recordID)
                );
            }
        });
//        submitbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingDialog.show();
//                if (!TextUtils.isEmpty(comitObject.toString())) {
//                    submitCheckData(JsonUtils.getjsonobj(comitObject.toString()), "EditKeyExamination");
//                }
//            }
//        });
        activityZxjcxqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        baseQuickAdapter = new BaseQuickAdapter<CheckType, BaseViewHolder>(R.layout.wdjcxq_item, null) {


            @Override
            protected void convert(BaseViewHolder helper, final CheckType item) {
                TextView textView = helper.getView(R.id.check_item_checkstate);
                GradientDrawable myGrad = (GradientDrawable) textView.getBackground();
                myGrad.setColor(color(item.getCheckResult(), textView));
                helper.setText(R.id.zxjc_item_header,item.getDicFatherName());
                helper.setText(R.id.check_item_content,item.getDicName())
                        .setText(R.id.check_item__zgl,item.getErrorFinishPercent());
                if("2".equals(CheckStatus)){ //2:?????????  3????????????
                    helper.getView(R.id.check_item_checkstate).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createDropAnimator(v, item.getCheckResult(), item).start();
                        }
                    });
                    /**???????????????????????????*/
                    helper.getView(R.id.check_item_addNewCheck).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checks.add(item);
                            Intent intent = new Intent(mContext, WdNewCheckQuestionActivity.class);
                            if ("??????".equals(item.getCheckResult())){
                                intent.putExtra("isSafe", 0);
                            }
                            if ("?????????".equals(item.getCheckResult())){
                                intent.putExtra("isSafe", 1);
                            }
                            intent.putExtra("data", (Serializable) checks);
                            intent.putExtra("pj", pj);
                            intent.putExtra("CreatUserName",wdbean.getCreateUserName());
                            intent.putExtra("xPoint",xPoint);
                            intent.putExtra("yPoint",yPoint);
                            intent.putExtra("rectPosition",wdbean.getRectPosition());
                            intent.putExtra("checkType",checkType); //??????????????? 1?????????
                            ((Activity) mContext).startActivityForResult(intent, JC_REQUESTCODE);
                        }

                    });
                    helper.getView(R.id.check_item_adddelete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WdjcxqActivity2.this,DeleteItemActivity.class);
                            intent.putExtra("recordRLID",item.getRecordRLID());
                            startActivityForResult(intent, RESQUST_CODE_SELECT_DELETE_ITEM);
                        }
                    });
                }else {
                    helper.getView(R.id.check_item_addNewCheck).setVisibility(View.GONE);
                    helper.getView(R.id.check_item_adddelete).setVisibility(View.GONE);
                }
                final List<ErrorMsgBean> errorMsgList = item.getErrorMsgList();
                final List<FilesBean> fileList = item.getFiles();
                    if("?????????".equals(item.getCheckResult()) || "?????????".equals(item.getCheckResult())){ //????????? ,?????????
                        if(item.getErrorNumber() == 0){
                            helper.getView(R.id.error_item_layout).setVisibility(View.GONE);
                        }else{
                            helper.getView(R.id.error_item_layout).setVisibility(View.VISIBLE);
                            helper.setText(R.id.need_dispose_question, "???????????????(" + item.getErrorMsgList().size() + ")");
                        }
                    }
                    if("??????".equals(item.getCheckResult())){
                        if(fileList.size() == 0 && "".equals(item.getCheckContent())){
                            helper.getView(R.id.error_item_layout).setVisibility(View.GONE);
                        }else {
                            helper.setText(R.id.need_dispose_question, "????????????" );
                            helper.getView(R.id.error_item_layout).setVisibility(View.VISIBLE);
                        }
                    }
                    helper.getView(R.id.need_dispose_question_look).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if("?????????".equals(item.getCheckResult())){
                                Intent intent = new Intent(WdjcxqActivity2.this, WdSuspendingQuestionActivity.class);//???????????????
                                intent.putExtra("list", (Serializable) errorMsgList);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(WdjcxqActivity2.this, SafeInfoActivity.class);//??????????????????
                                intent.putExtra("content",item.getCheckContent());
                                intent.putExtra("filelist", (Serializable) fileList);
                                startActivity(intent);
                            }
                        }
                    });
//                helper.getView(R.id.need_dispose_question_look).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        List<ErrorMsgBean> errorMsgList = item.getErrorMsgList();
//                        if (errorMsgList.size() > 0) {
//                            Intent intent = new Intent(WdjcxqActivity.this, SuspendingQuestionActivity.class);
//                            intent.putExtra("list", (Serializable) errorMsgList);
//                            startActivity(intent);
//                        }
//                    }
//                });

            }
        };
        header = LayoutInflater.from(this).inflate(R.layout.wdjcxq_header, null, false);
        baseQuickAdapter.addHeaderView(header);
//        baseQuickAdapter.addFooterView(footer);
//        footer.setVisibility(View.GONE);
        baseQuickAdapter.setHasStableIds(true);
        activityZxjcxqRecyclerView.setAdapter(baseQuickAdapter);
    }

    @Override
    protected void onActivityResult(int JC_REQUESTCODE, int resultCode, Intent data) {
        super.onActivityResult(JC_REQUESTCODE, resultCode, data);
        if (JC_REQUESTCODE == JC_REQUESTCODE && resultCode == RESULT_OK && data == null) {
            checks.remove(checks.size() - 1);
        } else if (JC_REQUESTCODE == JC_REQUESTCODE && resultCode == RESULT_OK && data != null) {
            ErrorMsgBean errorMsgBean = (ErrorMsgBean) data.getSerializableExtra("ErrorMsgBean");
            ErrorMsgBean aqMsgBean = (ErrorMsgBean) data.getSerializableExtra("aq");
            if (errorMsgBean != null) {
//                footer.setVisibility(View.VISIBLE);
                CheckType checkType = checks.get(checks.size() - 1);
                checkType.getErrorMsgList().add(errorMsgBean);
                checkType.setCheckResult("?????????");
                checks.remove(checks.size() - 1);
                getjson(checkType,errorMsgBean);
                loadingDialog.show();
                if (!TextUtils.isEmpty(comitObject.toString())) {
                    submitCheckData(JsonUtils.getjsonobj(comitObject.toString()), "EditKeyExamination");
                }
            } else if (aqMsgBean!=null){
                FileArray = new JSONArray();
                CheckType checkType = checks.get(checks.size() - 1);
                checkType.setCheckResult("??????");
                checkType.setCheckContent(aqMsgBean.getCheckContent());
                List<FilesBean> filesBeans = aqMsgBean.getFilesBeans();
                files = new ArrayList<String>();
                int selectTag = aqMsgBean.getSelectTag();
                try{
                    for(int i=0;i<filesBeans.size();i++){
                        JSONObject object = new JSONObject();
                        object.put("fileBrief",filesBeans.get(i).getFileBrief());
                        object.put("fileName",filesBeans.get(i).getFileName());
                        String url = filesBeans.get(i).getUrl();
                        if(selectTag == 1){
                            if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                                Bitmap bitmap = SendImageUtil.createWatermark(WdjcxqActivity2.this,
                                        url,"???????????????"+wdbean.getProShortName()+"\n"+"???????????????"+wdbean.getCreateUserName()+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,url);
                            }else {
                                Bitmap bitmap = SendImageUtil.createWatermark(WdjcxqActivity2.this,
                                        url,"???????????????"+wdbean.getProShortName()+"\n"+"???????????????"+wdbean.getCreateUserName()+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,url);
                            }
                        }
                        if(selectTag == 2){
                            if(TextUtils.isEmpty(xPoint) && TextUtils.isEmpty(yPoint)){
                                Bitmap bitmap = SendImageUtil.createWatermark(WdjcxqActivity2.this,
                                        url,"???????????????"+wdbean.getProShortName()+"\n"+"???????????????"+wdbean.getCreateUserName()+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,url);
                            }else {
                                Bitmap bitmap = SendImageUtil.createWatermark(WdjcxqActivity2.this,
                                        url,"???????????????"+wdbean.getProShortName()+"\n"+"???????????????"+wdbean.getCreateUserName()+"\n"+"???????????????"+xPoint+"  "+yPoint+"\n"+"???????????????"+"????????????"+"\n"+ "???????????????"+DateUtils.getTodayDateLong());
                                SendImageUtil.saveImage(bitmap,url);
                            }
                        }
                        files.add(url);
                        FileArray.put(object);
                    }
                    checkType.setFiles(filesBeans);
                    checks.remove(checks.size() - 1);
                    getSafejsonArray(checkType);
                    if (!TextUtils.isEmpty(safetyObject.toString())) {
                        postJsonAndFile(safetyObject.toString().toString(),files,"EditKeyExamination");
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }else if (JC_REQUESTCODE == RESQUST_CODE_SELECT_DELETE_ITEM && resultCode == RESULT_OK && data != null) { //?????????
                   initData();
            }
        }
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
        if("2".equals(CheckStatus)){
            XUtil.postjsondata(JsonUtils.getjsonobj("{\"recordID\":\""+recordID
                    +"\"}"), "GetKeyExaminationDetails", new MyCallBack() {
                @Override
                public void onResule(String res) {
                    wdbean = JsonUtils.getbean(res, Zxjcxqbean.class);
                    TextView title= (TextView) header.findViewById(R.id.wdjcxq_header_title);
                    title.setText(wdbean.getTitle());
                    TextView pjname= (TextView) header.findViewById(R.id.wdjcxq_header_pjname);
                    pjname.setText(wdbean.getProShortName());
                    TextView time= (TextView) header.findViewById(R.id.wdjcxq_header_time);
//                  time.setText(wdbean.getCheckTime());
                    time.setText(TimeUtils.getTodayDate().toString());
                    TextView user= (TextView) header.findViewById(R.id.wdjcxq_header_user);
                    user.setText(wdbean.getCreateUserName());
//                    user.setText(PublicUtils.userBean.getUserName());
                    TextView bm= (TextView) header.findViewById(R.id.wdjcxq_header_bm);
                    bm.setText("??????"+wdbean.getErrorNumber()+"??????"); //????????????

                    pj.setProjectID(wdbean.getProjectID());
                    pj.setProjectShortName(wdbean.getProShortName());
                    pj.setProjectType(wdbean.getProjectType());
                    try {
                        baseQuickAdapter.replaceData(wdbean.getCheckItemList());
                    }catch (Exception e){

                    }
                }
                @Override
                public void onFail(ObjBean getbean) {

                }

                @Override
                public void onWrong() {

                }
            });
        }else {
            XUtil.postjsondata(JsonUtils.getjsonobj("{\"recordID\":\""+recordID
                    +"\"}"), "GetKeyExaminationDetails1", new MyCallBack() {
                @Override
                public void onResule(String res) {
                    wdbean = JsonUtils.getbean(res, Zxjcxqbean.class);
                    TextView title= (TextView) header.findViewById(R.id.wdjcxq_header_title);
                    title.setText(wdbean.getTitle());
                    TextView pjname= (TextView) header.findViewById(R.id.wdjcxq_header_pjname);
                    pjname.setText(wdbean.getProShortName());
                    TextView time= (TextView) header.findViewById(R.id.wdjcxq_header_time);
//                time.setText(wdbean.getCheckTime());
                    time.setText(TimeUtils.getTodayDate().toString());
                    TextView user= (TextView) header.findViewById(R.id.wdjcxq_header_user);
                    user.setText(wdbean.getCreateUserName());
//                    user.setText(PublicUtils.userBean.getUserName());
                    TextView bm= (TextView) header.findViewById(R.id.wdjcxq_header_bm);
                    bm.setText("??????"+wdbean.getErrorNumber()+"??????"); //????????????

                    pj.setProjectID(wdbean.getProjectID());
                    pj.setProjectShortName(wdbean.getProShortName());
                    pj.setProjectType(wdbean.getProjectType());
                    try {
                        baseQuickAdapter.replaceData(wdbean.getCheckItemList());
                    }catch (Exception e){

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

    }
    /**
     * ???????????????????????????(???????????????)
     * ?????????-?????????????????????
     * */
    private JSONObject getjson(CheckType checkType,ErrorMsgBean errorMsgBean) {
        JSONArray array1 = new JSONArray();
        JSONObject object1 = new JSONObject();
        JSONArray array2 = new JSONArray();
        JSONObject object2 = new JSONObject();
        try {
            object1.put("recordRLID", checkType.getRecordRLID());
            object1.put("checkResult",checkType.getCheckResult());
            object1.put("rectPosition",wdbean.getRectPosition());
            if(xPoint != null && yPoint != null){
                object1.put("xPoint",xPoint);
                object1.put("yPoint",yPoint);
            }
            object2.put("elevation",errorMsgBean.getElevation());
            object2.put("floor",errorMsgBean.getFloor());
            object2.put("projectAreaID",errorMsgBean.getProjectAreaID());
            object2.put("projectAreaName",errorMsgBean.getProjectAreaName());
            object2.put("recEtpID",errorMsgBean.getRecEtpID());
            object2.put("recEtpShortName",errorMsgBean.getRecEtpShortName());
            object2.put("relationID",errorMsgBean.getRelationID());
            object2.put("relationTable",errorMsgBean.getRelationTable());
            object2.put("recEtpShortName",errorMsgBean.getRecEtpShortName());
            array2.put(object2);
            object1.put("errorMsgList",array2);
            array1.put(object1);
            comitObject.put("item", array1);
            PublicUtils.log(comitObject.toString());
        } catch (JSONException e) {
        }
        return comitObject;
    }

    /**
     * ????????????
     */
    private AnimatorSet createDropAnimator(final View view, final String str, final CheckType checkType) {
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator toRight = ObjectAnimator.ofFloat(view, "translationX", 0, view.getWidth());
        toRight.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                GradientDrawable myGrad = (GradientDrawable) view.getBackground();
                myGrad.setColor(getColorAndText(str, (TextView) view, checkType));
                String json = getjsonArray(checkType); //????????????
                if (!TextUtils.isEmpty(json)) {
                    submitItemCheck(JsonUtils.getjsonobj(json), "EditKeyExamination");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ValueAnimator toleft = ObjectAnimator.ofFloat(view, "translationX", view.getWidth(), 0);
        animatorSet.play(toRight).before(toleft);
        animatorSet.setDuration(350);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                view.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return animatorSet;
    }

    public void submitCheckData(JSONObject json, String method) {
        XUtil.postjsondata(json,method, new MyCallBack() {
            @Override
            public void onResule(String res) {
                PublicUtils.toast("????????????");
                initData();
//                baseQuickAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }
    public void submitItemCheck(JSONObject json, String method) {
        XUtil.postjsondata(json,method, new MyCallBack() {
            @Override
            public void onResule(String res) {

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                WeiboDialogUtils.closeDialog(loadingDialog);
            }
        });
    }
    /**
     *  ???????????????????????????(???????????????)
     * ?????????-?????????????????????
     * */
    private void postJsonAndFile(String json, List<String> files, String method) {
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            @Override
            public void onResule(String res) {
                showToast("????????????");
                baseQuickAdapter.notifyDataSetChanged();
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
        });
    }
    /**
     * ????????????
     * @param checkType
     * @return
     */
    private String getjsonArray(final CheckType checkType) {
        JSONObject jsonObject = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        try{
            if(xPoint != null && yPoint != null){
                jsonObject.put("xPoint",xPoint);
                jsonObject.put("yPoint",yPoint);
            }
            jsonObject.put("recordRLID", checkType.getRecordRLID());
            jsonObject.put("checkResult",checkType.getCheckResult());
            array.put(jsonObject);
            obj.put("item", array);
        }catch (JSONException e){
            return "";
        }
        return obj.toString();
    }
    /**
     * ????????????????????????
     * ?????????-?????????????????????
     * */
    private String getSafejsonArray(final CheckType checkType) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try{
            safetyObject.put("status","1");
            safetyObject.put("recordID",recordID);
            if(xPoint != null && yPoint != null){
                jsonObject.put("xPoint",xPoint);
                jsonObject.put("yPoint",yPoint);
            }
            jsonObject.put("checkContent",checkType.getCheckContent());
            jsonObject.put("errorNumber","0");
            jsonObject.put("errorFinishPercent","");
            jsonObject.put("dicID",checkType.getDicID());
            jsonObject.put("recordRLID", checkType.getRecordRLID());
            jsonObject.put("dicFatherName", checkType.getDicFatherName());
            jsonObject.put("dicName", checkType.getDicName());
            jsonObject.put("level", checkType.getLevel());
            jsonObject.put("checkResult",checkType.getCheckResult());
            jsonObject.put("dicFatherID",checkType.getDicFatherID());
            jsonObject.put("files",FileArray);
            jsonObject.put("errorMsgList",ErrorMsgArray);
            array.put(jsonObject);
            safetyObject.put("item", array);
        }catch (JSONException e){
            return "";
        }
        return safetyObject.toString();
    }
    private int color(String str, TextView view) {
        view.setText(str);
        if ("?????????".equals(str)) {
            return ContextCompat.getColor(WdjcxqActivity2.this, R.color.color_yyh);
        } else if ("??????".equals(str)) {
            return ContextCompat.getColor(WdjcxqActivity2.this, R.color.color_qq);
        } else if ("?????????".equals(str)) {
            return ContextCompat.getColor(WdjcxqActivity2.this, R.color.foot_color);
        }
        return ContextCompat.getColor(WdjcxqActivity2.this, R.color.color_wjc);
    }

    private int getColorAndText(String str, TextView view, CheckType checkType) {
        if ("?????????".equals(str)) {
            view.setText("?????????");
            checkType.setCheckResult("?????????");
            return ContextCompat.getColor(mContext, R.color.color_wjc);
        } else if ("??????".equals(str)) {
            view.setText("?????????");
            checkType.setCheckResult("?????????");
            return ContextCompat.getColor(mContext, R.color.color_yyh);
        }
        view.setText("??????");
        checkType.setCheckResult("??????");
        return ContextCompat.getColor(mContext, R.color.color_qq);

    }
    /**
     * ?????????114.227561>>>>>>>?????????30.626568
     ?????????114.227561>>>>>>>?????????30.626568
     ?????????114.227561>>>>>>>?????????30.626568
     ?????????114.227561>>>>>>>?????????30.626568
     ?????????114.227561>>>>>>>?????????30.626568
     ?????????114.227756>>>>>>>?????????30.626079
     */
    public void getLocation(){
        Location location = LocationUtils.getInstance(WdjcxqActivity2.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
//            String address2 = "?????????" + xPoint+">>>>>>>" + "?????????" + yPoint;
//            Log.d( "FLY.LocationUtils", address2 );
        }
    }
    /**
     * @param v ?????????????????????
     */
    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
