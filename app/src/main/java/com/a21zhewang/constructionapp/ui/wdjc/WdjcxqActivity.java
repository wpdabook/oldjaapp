package com.a21zhewang.constructionapp.ui.wdjc;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.Zxjcxqbean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.check.Act_AddTypeProcedural;
import com.a21zhewang.constructionapp.ui.zxjc.SuspendingQuestionActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.utils.dbUtils;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 重点检查
 * @author WuPeng 2019/4/25
 *
 */
public class WdjcxqActivity extends BaseActivity {

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
    private List<String> files; //存放文件绝对路径
    private JSONArray FileArray; //文件信息上传
    private JSONArray ErrorMsgArray = new JSONArray(); //文件信息上传
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
        return R.layout.activity_wdjcxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        mContext = this;
        recordID = getIntent().getStringExtra("recordID");
        if (TextUtils.isEmpty(recordID)){
            return;
        }
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
        footer = LayoutInflater.from(this).inflate(R.layout.table_list_footer, null, false);
        Button savebtn = (Button) footer.findViewById(R.id.table_list_footer_save);
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
//                    if ("未检查".equals(data.get(i).getCheckResult())) {
//                        PublicUtils.toast("未检查项不可提交");
//                        return;
//                    }
//                }
//                loadingDialog.show();
//                if (!TextUtils.isEmpty(comitObject.toString())) {
//                    submitCheckData(JsonUtils.getjsonobj(comitObject.toString()), "EditKeyExamination");
//                }

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
                helper.getView(R.id.check_item_checkstate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createDropAnimator(v, item.getCheckResult(), item).start();
                    }
                });
                final List<ErrorMsgBean> errorMsgList = item.getErrorMsgList();
                if(errorMsgList.size() == 0){
                    helper.getView(R.id.error_item_layout).setVisibility(View.GONE);
                }else {
                    helper.setText(R.id.need_dispose_question, "待处理问题(" + item.getErrorMsgList().size() + ")");
                    helper.getView(R.id.error_item_layout).setVisibility(View.VISIBLE);
                }
                helper.getView(R.id.need_dispose_question_look).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(WdjcxqActivity.this, SuspendingQuestionActivity.class);
                        intent.putExtra("list", (Serializable) errorMsgList);
                        startActivity(intent);
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
                /**有隐患时可上传图片*/
                helper.getView(R.id.check_item_addNewCheck).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checks.add(item);
                        Intent intent = new Intent(mContext, WdNewCheckQuestionActivity.class);
                        if ("安全".equals(item.getCheckResult())){
                            intent.putExtra("isSafe", 0);
                        }else if ("有隐患".equals(item.getCheckResult())){
                            intent.putExtra("isSafe", 1);
                        }
                        intent.putExtra("data", (Serializable) checks);
                        intent.putExtra("pj", pj);
                        ((Activity) mContext).startActivityForResult(intent, JC_REQUESTCODE);
                    }

                });
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
                checkType.setCheckResult("有隐患");
                checks.remove(checks.size() - 1);
                getjson(checkType,errorMsgBean);
                loadingDialog.show();
                if (!TextUtils.isEmpty(comitObject.toString())) {
                    submitCheckData(JsonUtils.getjsonobj(comitObject.toString()), "EditKeyExamination");
                }
            } else if (aqMsgBean!=null){
                FileArray = new JSONArray();
                CheckType checkType = checks.get(checks.size() - 1);
                checkType.setCheckResult("安全");
                checkType.setCheckContent(aqMsgBean.getCheckContent());
                List<FilesBean> filesBeans = aqMsgBean.getFilesBeans();
                files = new ArrayList<String>();
                try{
                    for(int i=0;i<filesBeans.size();i++){
                        JSONObject object = new JSONObject();
                        object.put("fileBrief",filesBeans.get(i).getFileBrief());
                        object.put("fileName",filesBeans.get(i).getFileName());
                        String url = filesBeans.get(i).getUrl();
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
            }
        }
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
//                      time.setText(wdbean.getCheckTime());
                time.setText(TimeUtils.getTodayDate().toString());
                TextView user= (TextView) header.findViewById(R.id.wdjcxq_header_user);
                user.setText(wdbean.getCreateUserName());
//                        user.setText(PublicUtils.userBean.getUserName());
                TextView bm= (TextView) header.findViewById(R.id.wdjcxq_header_bm);
                bm.setText("共（"+wdbean.getErrorNumber()+"）条"); //所属部门

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
    /**
     * 提交有隐患相关信息(图片与内容)
     * （检查-安全检查）关联
     * */
    private JSONObject getjson(CheckType checkType,ErrorMsgBean errorMsgBean) {
        JSONArray array1 = new JSONArray();
        JSONObject object1 = new JSONObject();
        JSONArray array2 = new JSONArray();
        JSONObject object2 = new JSONObject();
        try {
            object1.put("recordRLID", checkType.getRecordRLID());
            object1.put("checkResult",checkType.getCheckResult());
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
     * 检查提交
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
                String json = getjsonArray(checkType); //单项检查
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
                PublicUtils.toast("提交成功");
                initData();
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
     *  提交有安全相关信息(图片与内容)
     * （检查-安全检查）关联
     * */
    private void postJsonAndFile(String json, List<String> files, String method) {
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            @Override
            public void onResule(String res) {
                showToast("保存成功");
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
     * 单项提交
     * @param checkType
     * @return
     */
    private String getjsonArray(final CheckType checkType) {
        JSONObject jsonObject = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        try{
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
     * 提交安全相关信息
     * （检查-安全检查）关联
     * */
    private String getSafejsonArray(final CheckType checkType) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try{
            safetyObject.put("status","1");
            safetyObject.put("recordID",recordID);
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
        if ("有隐患".equals(str)) {
            return ContextCompat.getColor(WdjcxqActivity.this, R.color.color_yyh);
        } else if ("安全".equals(str)) {
            return ContextCompat.getColor(WdjcxqActivity.this, R.color.color_qq);
        }
        return ContextCompat.getColor(WdjcxqActivity.this, R.color.color_wjc);
    }

    private int getColorAndText(String str, TextView view, CheckType checkType) {
        if ("有隐患".equals(str)) {
            view.setText("未检查");
            checkType.setCheckResult("未检查");
            return ContextCompat.getColor(mContext, R.color.color_wjc);
        } else if ("安全".equals(str)) {
            view.setText("有隐患");
            checkType.setCheckResult("有隐患");
            return ContextCompat.getColor(mContext, R.color.color_yyh);
        }
        view.setText("安全");
        checkType.setCheckResult("安全");
        return ContextCompat.getColor(mContext, R.color.color_qq);

    }
    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }



}
