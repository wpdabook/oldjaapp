package com.a21zhewang.constructionapp.ui.gtxtiu;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AddgtxtinitBean;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.customview.LinkageSpinner;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ZF2Activity extends BaseActivity {


    @BindView(R.id.activity_zf2_LinkageSpinner)
    LinkageSpinner activityZf2LinkageSpinner;
    @BindView(R.id.activity_zf2_EditText_clyj)
    EditText activityZf2EditTextClyj;
    private String msgid,mehtod;
    private Dialog loadingDialog;

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
        return R.layout.activity_zf2;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "处理中...");
        msgid = getIntent().getStringExtra("msgid");
        mehtod = getIntent().getStringExtra("method");
       // activityZf2LinkageSpinner.setpjbean();
        if (TextUtils.isEmpty(mehtod))activityZf2LinkageSpinner.setMaxETPCount(-1);
        activityZf2LinkageSpinner.setMaxCount(-1);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        String getCoordinateMsgTransmit = "GetCoordinateMsgTransmit";
        if (!TextUtils.isEmpty(mehtod)){
            getCoordinateMsgTransmit= mehtod;
        }
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"btnID\":\"cmBtn7\",\"recordID\":\""+msgid+"\"}"),
                getCoordinateMsgTransmit, new MyCallBack() {

            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                AddgtxtinitBean addgtxtinitBean = JsonUtils.getbean(res, AddgtxtinitBean.class);
                List<Etp> transEtpList = addgtxtinitBean.getTransEtpList();
                if (transEtpList!=null&&transEtpList.size()>0){
                    List<Etp> eplist=  new ArrayList<Etp>();
                    eplist.addAll(transEtpList);
                   Project pjobj = new Project();
                    pjobj.setEtpInfoList(eplist);
                    activityZf2LinkageSpinner.setpjbean(pjobj);
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



    @OnClick({R.id.activity_zf2_tv_tj, R.id.activity_zf2_tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_zf2_tv_tj:
                String trim = activityZf2EditTextClyj.getText().toString().trim();
                if (trim.length() == 0) {
                    PublicUtils.toast("请输入转发描述");
                    break;
                }
                List<Etp> infoList = activityZf2LinkageSpinner.getInfoList();
                if (infoList.size()==0){
                    PublicUtils.toast("请添加接收单位和接收人");
                    break;
                }

                loadingDialog.show();

                JSONArray receiversjson = new JSONArray();
                for (int j = 0; j < infoList.size(); j++) {
                    Etp eIListBean = infoList.get(j);
                    List<User> uslist = eIListBean.getUserInfoList();
                    for (int o = 0; o< uslist.size();o++){
                        User bean = uslist.get(o);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("userID", bean.getUserID());
                            jsonObject.put("userName", bean.getUserName());
                            receiversjson.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                postdata(trim, receiversjson.toString());
                break;
            case R.id.activity_zf2_tv_back:
                finish();
                break;
        }
    }
    public void postdata(String str, String names) {
        String method = "GetCoordinateMsgDetailsButton";
        if ("GetSafetyMsgTransmit".equals(mehtod)){
            method = "GetSafetyMsgDetailsButton";
        }else if ("GetQualityMsgTransmit".equals(mehtod)){
            method = "GetQualityMsgDetailsButton";
        }else if ("GetCivilizationMsgTransmit".equals(mehtod)){
            method = "GetCivilizedMsgDetailsButton";
        }
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"createUserName\":\"" + PublicUtils.userBean.getUserName() + "\",\"receivers\":"+names+",\"dealContent\":\"" + str + "\",\"btnID\":\"cmBtn7\",\"createUserID\":\"" + PublicUtils.UserID + "\",\"recordID\":\"" + msgid + "\"}");

        XUtil.postjsondata(getjsonobj, method, new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                PublicUtils.toast("转发成功");
                finish();
            }

            /**
             * code 不等于 0回调
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
}
