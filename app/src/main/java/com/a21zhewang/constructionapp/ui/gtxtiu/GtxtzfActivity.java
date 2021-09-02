package com.a21zhewang.constructionapp.ui.gtxtiu;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.AddgtxtinitBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.CanyurenView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class GtxtzfActivity extends BaseActivity {


    @BindView(R.id.activity_gtxtzf_CheckBox_zjjsr)
    CheckBox activityGtxtzfCheckBoxZjjsr;
    @BindView(R.id.activity_gtxtzf_tv_zjjsr)
    TextView activityGtxtzfTvZjjsr;
    @BindView(R.id.activity_gtxtzf_EditText_clyj)
    EditText activityGtxtzfEditTextClyj;
    @BindView(R.id.activity_gtxtzf_CanyurenView)
    CanyurenView activityGtxtzfCanyurenView;
    @BindView(R.id.MyTitleBar)
    com.a21zhewang.constructionapp.customview.MyTitleBar MyTitleBar;
    private List<AddgtxtinitBean.UserInfoListBean> addgtxtinitBeanSendMan;
    private List<AddgtxtinitBean.UserInfoListBean> recentUserList;
    private HashSet<Integer> integers;
    private String msgid,mehtod;
    private boolean isonclick=false;
    private boolean isxf;
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
        return R.layout.activity_gtxtzf;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        addgtxtinitBeanSendMan = new ArrayList<>();
        msgid = getIntent().getStringExtra("msgid");
        mehtod = getIntent().getStringExtra("method");
        isxf = getIntent().getBooleanExtra("isxf",false);
        if (isxf)
            MyTitleBar.setTitlenametv("下发");
    }

    @Override
    public void initListener() {
        activityGtxtzfCheckBoxZjjsr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //  PublicUtils.toast(integers.size()+"");
                    activityGtxtzfCanyurenView.setselsctor(integers);
                } else {
                    activityGtxtzfCanyurenView.remeselsctor(integers);
                }
            }
        });

        activityGtxtzfCanyurenView.setCanyurenViewAPI(new CanyurenView.CanyurenViewAPI<Object>() {
            @Override
            public void setstr(int size, TextView textView, Set<Integer> ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(addgtxtinitBeanSendMan.get(integers.get(0)).getUserName());
                } else if (size == 2) {
                    textView.setText(addgtxtinitBeanSendMan.get(integers.get(0)).getUserName() + "," + addgtxtinitBeanSendMan.get(integers.get(1)).getUserName());
                } else if (size > 2) {
                    textView.setText(addgtxtinitBeanSendMan.get(integers.get(0)).getUserName() + "," + addgtxtinitBeanSendMan.get(integers.get(1)).getUserName() + "...");
                }
            }

            @Override
            public String getstr(int i, Object o) {
                return null;
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        //
        String cmBtn4 = "cmBtn4";
        String method = "GetCoordinateMsgTransmit";
        if (isxf){
            cmBtn4="cmBtn8";
            method=mehtod;
        }


        XUtil.postjsondata(JsonUtils.getjsonobj("{\"btnID\":\"" + cmBtn4 + "\",\"recordID\":\"" +msgid+"\"}"),
                method, new MyCallBack() {


            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                PublicUtils.log("新增生产协调：" + res);
                AddgtxtinitBean addgtxtinitBean = JsonUtils.getbean(res, AddgtxtinitBean.class);
                List<AddgtxtinitBean.UserInfoListBean> sendMans = addgtxtinitBean.getUserInfoList();
                //初始化 参与人list
                if (sendMans != null && sendMans.size() > 0) {
                    List<String> sendManstrs = new ArrayList<>();

                    for (AddgtxtinitBean.UserInfoListBean sd : sendMans) {
                        sendManstrs.add(sd.getUserName());
                        addgtxtinitBeanSendMan.add(sd);
                    }
                    activityGtxtzfCanyurenView.setList(sendManstrs);
                }

                recentUserList = addgtxtinitBean.getRecentUserList();
                integers = new HashSet<Integer>();
                int j = 0;
                String zjjsr = "";
                for (int i = 0; i < addgtxtinitBeanSendMan.size(); i++) {

                    if (i < recentUserList.size()) {
                        AddgtxtinitBean.UserInfoListBean infoListBean = recentUserList.get(j);
                        zjjsr = zjjsr + infoListBean.getUserName() + ",";
                    }
                    if (j < recentUserList.size() && addgtxtinitBeanSendMan.get(i).getUserID().equals(recentUserList.get(j).getUserID())) {
                        integers.add(i);
                        j++;
                    }

                }
                activityGtxtzfTvZjjsr.setText("最近接收人:" + zjjsr);
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
        });
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_gtxtzf_tv_tj, R.id.activity_gtxtzf_tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_gtxtzf_tv_tj:

                String trim = activityGtxtzfEditTextClyj.getText().toString().trim();
                if (trim.length() == 0) {
                    PublicUtils.toast("请输入处理意见");
                    break;
                }
                Set<Integer> list = activityGtxtzfCanyurenView.getList();
                if (list.size() == 0) {
                    PublicUtils.toast("请选择接收人");
                    break;
                }

                Iterator<Integer> iterator = list.iterator();
                List<AddgtxtinitBean.UserInfoListBean> beanList = new ArrayList<>();
                while (iterator.hasNext()) {
                    Integer integer = iterator.next();
                    beanList.add(addgtxtinitBeanSendMan.get(integer));
                }

                JSONArray receiversjson = new JSONArray();
                for (int j=0;j<beanList.size();j++){
                    AddgtxtinitBean.UserInfoListBean sendManBean = beanList.get(j);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userID",sendManBean.getUserID());
                        jsonObject.put("userName",sendManBean.getUserName());
                        receiversjson.put(jsonObject);
                    } catch (JSONException e) {

                        //isonclick=false;
                       // e.printStackTrace();
                    }

                }
                if (!isonclick){
                    isonclick=true;
                    break;
                }
                postdata(trim, receiversjson.toString());
                break;
            case R.id.activity_gtxtzf_tv_back:
                finish();
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if (activityGtxtzfCanyurenView != null)
            activityGtxtzfCanyurenView.popdiss();
    }

    public void postdata(String str, String names) {
        String cmBtn4 = "cmBtn4";
        if (isxf) {
            cmBtn4="cmBtn8";
        }
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"createUserName\":\"" + PublicUtils.userBean.getUserName() + "\",\"receivers\":"+names+",\"dealContent\":\"" + str + "\",\"btnID\":\"" + cmBtn4 + "\",\"createUserID\":\"" + PublicUtils.UserID + "\",\"recordID\":\"" + msgid + "\"}");

        String method = "GetCoordinateMsgDetailsButton";
        if ("GetSafetyMsgTransmit".equals(mehtod)){
            method = "GetSafetyMsgDetailsButton";
        }else if ("GetQualityMsgTransmit".equals(mehtod)){
            method = "GetQualityMsgDetailsButton";
        }else if ("GetCivilizationMsgTransmit".equals(mehtod)){
            method = "GetCivilizedMsgDetailsButton";
        }

        XUtil.postjsondata(getjsonobj, method, new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                String str1 = "上报成功";
                if (isxf) {
                    str1="下发成功";
                }

                PublicUtils.toast(str1);
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
                isonclick=false;
            }
        });
    }


}
