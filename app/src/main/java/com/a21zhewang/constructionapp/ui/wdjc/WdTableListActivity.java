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
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.UPJsonBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.utils.dbUtils;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;

public class WdTableListActivity extends BaseActivity {

    @BindView(R.id.activity_table_list_checktime)
    TextView activityTableListChecktime;
    @BindView(R.id.activity_table_list_checktypes)
    TextView activityTableListChecktypes;
    @BindView(R.id.activity_table_list_RecyclerView)
    RecyclerView activityTableListRecyclerView;
    private BaseQuickAdapter<CheckType, BaseViewHolder> adapter;
    List<List<? extends CheckType>> Types;
    List<CheckType> checks;
    List<String> Typesstr;
    private Project pj;
    private Context mContext;
    private static final int REQUESTCODE = 182;
    private List<CheckTypeOne> allcheckTypes;
    private View footer;
    private Dialog loadingDialog;
  //  private  List<String> files=new ArrayList<>();

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
        return R.layout.activity_table_list;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        mContext = this;
        pj = getIntent().getParcelableExtra("pj");
        checks = new ArrayList<>();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
        activityTableListChecktime.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy年MM月dd日")) + "专项检查");
        footer = LayoutInflater.from(this).inflate(R.layout.table_list_footer, null, false);
        Button savebtn = (Button) footer.findViewById(R.id.table_list_footer_save);
        Button submitbtn = (Button) footer.findViewById(R.id.table_list_footer_submit);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CheckType> data = adapter.getData();
                if (data == null || data.size() == 0) return;
                for (int i = 0; i < data.size(); i++) {
                    if ("未检查".equals(data.get(i).getCheckResult())) {
                        PublicUtils.toast("还有未检查的检查项！");
                        return;
                    }
                }

                List<CheckType> newchecks = new ArrayList<>();
                if (allcheckTypes != null && allcheckTypes.size() > 0) {
                    for (int i = 0; i < allcheckTypes.size(); i++) {
                        newchecks.addAll(allcheckTypes.get(i).getCheckItemList());
                    }
                }

                if (hasNoCheck(newchecks)) {
                    PublicUtils.toast("还有未检查的检查项！可以先保存");
                    return;
                }
                loadingDialog.show();
               // savedata();
                String json = getjson();
                if (!TextUtils.isEmpty(json)) {
                    postdata(JsonUtils.getjsonobj(json), "AddKeyExamination");
                } else {
                    WeiboDialogUtils.closeDialog(loadingDialog);
                }
            }
        });
        initList();
    }

    private void savedata() {
        try {
            int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "save"));
            //     List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();

        } catch (DbException e) {
            PublicUtils.log("清除缓存检查项失败！");
            WeiboDialogUtils.closeDialog(loadingDialog);
        }
        dbUtils.savajson(JsonUtils.objtojson(allcheckTypes), "save", "", null);
        PublicUtils.toast("保存成功");
    }

    private List<CheckType> haschild(List<CheckType> msgType) {


        if (msgType.size() > 0) {
            CheckType type = msgType.get(0);
            List<CheckType> msgTypes = type.getCheckItemList();
            if (msgTypes == null) {
                msgTypes = new ArrayList<>();
                type.setCheckItemList(msgTypes);
                return msgTypes;
            }
            return haschild(msgTypes);
        }

        return msgType;
    }

    private String getjson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
            jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
            jsonObject.put("projectID", pj.getProjectID());
            //   List<CheckType> newchecks=new ArrayList<>();
            //  newchecks.addAll(checks);
            // newchecks.remove(0);
            //  List<CheckType> tree = list2Tree(newchecks);
            //   List<CheckType> list = haschild(tree);
            // list.addAll(adapter.getData());
            if (allcheckTypes != null && allcheckTypes.size() > 0) {
                   List<CheckType> newchecks=new ArrayList<>();
                for (int i = 0; i < allcheckTypes.size(); i++) {
                    newchecks.addAll(allcheckTypes.get(i).getCheckItemList());
                }
                jsonObject.put("checkItemList", new JSONArray(JsonUtils.objtojson(newchecks)));
            }


            jsonObject.put("recordID", UUID.randomUUID().toString());
            jsonObject.put("content", "");
            jsonObject.put("title", activityTableListChecktime.getText());
            jsonObject.put("status", "0");
            jsonObject.put("checkTime", TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
            jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
            jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
            jsonObject.put("proShortName", pj.getProjectShortName());


        } catch (JSONException e) {
            return "";
        }
        return jsonObject.toString();
    }


    private boolean hasNoCheck(List<CheckType> checks) {

        if (checks != null && checks.size() > 0) {
            for (int i = 0; i < checks.size(); i++) {
                List itemList = checks.get(i).getCheckItemList();
                if (itemList != null && itemList.size() > 0&&hasNoCheck(itemList)) {
                      return true;
                }
               if (itemList != null && itemList.size() > 0)continue;
                if ("未检查".equals(checks.get(i).getCheckResult())) {
                    return true;
                }

            }
        }
            return false;

    }

    private void postdata(JSONObject obj, String method) {
        XUtil.postjsondata(obj,method, new MyCallBack() {
            @Override
            public void onResule(String res) {
//                try {
//                    int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "save"));
//                    //     List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();
//
//                } catch (DbException e) {
//                    PublicUtils.log("清除缓存检查项失败！");
//                    WeiboDialogUtils.closeDialog(loadingDialog);
//                }
                finish();
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

    private List<CheckType> list2Tree(List<CheckType> checks) {
        List<CheckType> msgTypes = new ArrayList<>();
        for (int i = 0; i < checks.size(); i++) {
            CheckTypeOne msgType = new CheckTypeOne();
            CheckType checkType = checks.get(i);
            msgType.setDicID(checkType.getDicID());
            msgType.setDicName(checkType.getDicName());
            msgType.setRecordRLID();
            haschild(msgTypes).add(msgType);
        }
        return msgTypes;
    }

    /**
     * 设置recyclerView
     */
    private void initList() {
        Types = new ArrayList<>();
        Typesstr = new ArrayList<>();
        if (pj != null) {
            Typesstr.add(pj.getProjectShortName());
            activityTableListChecktypes.setText(getstr(Typesstr));
        }
        activityTableListChecktypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Types.size() > 1) {
                    Types.remove(Types.size() - 1);

                }
                if (Typesstr.size() > 1)
                    Typesstr.remove(Typesstr.size() - 1);
                activityTableListChecktypes.setText(getstr(Typesstr));
                // PublicUtils.toast(Types.size()+"");
                adapter.replaceData(Types.get(Types.size() - 1));
                if (checks.size() > 0) {
                    checks.remove(checks.size() - 1);
                }

            }
        });

        activityTableListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // activityTableListRecyclerView.addItemDecoration(new SimpleDividerDecoration("#d6d6d6", 1));

        adapter = new BaseQuickAdapter<CheckType, BaseViewHolder>(null) {
            @Override
            public void convert(final BaseViewHolder helper, final CheckType item) {
                switch (helper.getItemViewType()) {
                    case 0:
                        if (footer.getVisibility() != View.INVISIBLE) {
                            footer.setVisibility(View.INVISIBLE);
                        }
                        helper.setText(R.id.checklist_childitem_tv, item.getDicName());
                        List<CheckType> List = item.getCheckItemList();

                        helper.setText(R.id.checklist_childitem_tv_nocheck, hasnocheck(List));
                        helper.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checks.add(item);
                                Typesstr.add(item.getDicName());
                                activityTableListChecktypes.setText(getstr(Typesstr));
                                List<CheckType> checkItemList = item.getCheckItemList();
                                if (checkItemList == null) {
                                    checkItemList = new ArrayList<CheckType>();
                                }
                                Types.add(checkItemList);
                                // PublicUtils.toast(Types.size()+"");
                                replaceData(checkItemList);
                            }
                        });
                        break;
                    case 1:
                        if (footer.getVisibility() != View.VISIBLE) {
                            footer.setVisibility(View.VISIBLE);
                        }
                        helper.setText(R.id.check_item_content, item.getDicName());
                        TextView textView = helper.getView(R.id.check_item_checkstate);
                        GradientDrawable myGrad = (GradientDrawable) textView.getBackground();
                        myGrad.setColor(color(item.getCheckResult(), textView));
                        helper.getView(R.id.check_item_checkstate).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                createDropAnimator(v, item.getCheckResult(), item).start();
                            }
                        });
                        helper.getView(R.id.need_dispose_question_look).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                List<ErrorMsgBean> errorMsgList = item.getErrorMsgList();
                                if (errorMsgList.size() > 0) {
                                    Intent intent = new Intent(WdTableListActivity.this, WdSuspendingQuestionActivity.class);
                                    intent.putExtra("list", (Serializable) errorMsgList);
                                    startActivity(intent);
                                } else {
                                    PublicUtils.toast("待处理问题为0条。");
                                }
                            }
                        });
                        helper.setText(R.id.need_dispose_question, "待处理问题(" + item.getErrorMsgList().size() + ")");
                     //   if (textView.getText().equals("未检查")){
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
                                    ((Activity) mContext).startActivityForResult(intent, REQUESTCODE);
                                }
                            });
                      //  }else{
                           // helper.getView(R.id.check_item_addNewCheck).setClickable(false);
                      //  }

                        break;
                }


            }
        };

        adapter.setMultiTypeDelegate(new MultiTypeDelegate<CheckType>() {
            @Override
            protected int getItemType(CheckType checkType) {
                List list = checkType.getCheckItemList();
                if (list == null || list.size() == 0) return 1;
                return 0;
            }
        });
        MultiTypeDelegate<CheckType> multiTypeDelegate = adapter.getMultiTypeDelegate();
        multiTypeDelegate.registerItemType(0, R.layout.checklist_childitem);
        multiTypeDelegate.registerItemType(1, R.layout.check_item);
        adapter.addFooterView(footer);

        activityTableListRecyclerView.setAdapter(adapter);
        try {
            List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();
            if (upJsonBeens != null && upJsonBeens.size() > 0) {
                UPJsonBean upJsonBean = upJsonBeens.get(0);
                allcheckTypes = JsonUtils.jsonToArrayList(upJsonBean.getDatatext(), CheckTypeOne.class);
              //  String json=JsonUtils.objtojson(allcheckTypes);
                this.Types.add(allcheckTypes);
                adapter.replaceData(allcheckTypes);
            }
        } catch (DbException e) {
            PublicUtils.log("获取专项检查数据失败！");
        }


    }

    private String hasnocheck(List<CheckType> list) {
        if (list != null && list.size() > 0) {
            List<CheckType> itemList = list.get(0).getCheckItemList();
            if (itemList == null || itemList.size() == 0) {
                int nocheck = 0;
                for (int i = 0; i < list.size(); i++) {
                    if ("未检查".equals(list.get(i).getCheckResult())) {
                        nocheck++;
                    }
                }
                return "未检查(" + nocheck + ")";
            }
            return "";
        }
        return "";
    }

    /**
     * 拼接文字
     *
     * @param strs
     * @return
     */
    private String getstr(List<String> strs) {
        if (strs == null || strs.size() == 0) return "";
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < strs.size(); i++) {
            str.append(strs.get(i));
            if (i != strs.size() - 1) {
                str.append("/");
            }
        }
        return str.toString();
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

    private int color(String str, TextView view) {
        view.setText(str);
        if ("有隐患".equals(str)) {
            return ContextCompat.getColor(mContext, R.color.color_yyh);
        } else if ("安全".equals(str)) {
            return ContextCompat.getColor(mContext, R.color.color_qq);
        }
        return ContextCompat.getColor(mContext, R.color.color_wjc);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && resultCode == RESULT_OK && data == null) {
            checks.remove(checks.size() - 1);
        } else if (requestCode == REQUESTCODE && resultCode == RESULT_OK && data != null) {
            ErrorMsgBean errorMsgBean = (ErrorMsgBean) data.getSerializableExtra("ErrorMsgBean");
            ErrorMsgBean aqMsgBean = (ErrorMsgBean) data.getSerializableExtra("aq");
            if (errorMsgBean != null) {
                CheckType checkType = checks.get(checks.size() - 1);
                checkType.getErrorMsgList().add(errorMsgBean);
                checkType.setCheckResult("有隐患");
                checks.remove(checks.size() - 1);
                adapter.notifyDataSetChanged();
                try {
                    int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "save"));
                    //     List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();

                } catch (DbException e) {
                    PublicUtils.log("清除缓存检查项失败！");
                }
                dbUtils.savajson(JsonUtils.objtojson(allcheckTypes), "save", "", null);
                // finish();
            } else if (aqMsgBean!=null){
                CheckType checkType = checks.get(checks.size() - 1);
                checkType.setCheckResult("安全");
                checkType.setCheckContent(aqMsgBean.getCheckContent());
                List<FilesBean> filesBeans = aqMsgBean.getFilesBeans();
                //for (FilesBean filesBean : filesBeans) {
               // files.add(filesBean.getUrl());
               // }
                checkType.setFiles(filesBeans);

//                }
                checks.remove(checks.size() - 1);
                adapter.notifyDataSetChanged();
                try {
                    int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "save"));
                    //     List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();

                } catch (DbException e) {
                    PublicUtils.log("清除缓存检查项失败！");
                }
                dbUtils.savajson(JsonUtils.objtojson(allcheckTypes), "save", "", null);
            }
        }
    }


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
}
