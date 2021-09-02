package com.a21zhewang.constructionapp.ui.sgrz;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.SgrzScqkBean;
import com.a21zhewang.constructionapp.bean.SgrzinitBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MyTimeSpinner;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SgrzlrActivity extends BaseActivity {

    private static final int REQUESTCODE1 = 11;//请求码
    private static final int REQUESTCODE2= 22;//请求码
    @BindView(R.id.activity_sgrzlr_MyListView)
    MyListView activitySgrzlrMyListView;
    @BindView(R.id.activity_sgrzlr_TextView)
    TextView activity_sgrzlr_TextView;
    @BindView(R.id.activity_sgrzlr_MyTimeSpinner)
    MyTimeSpinner activitySgrzlrMyTimeSpinner;

    @BindView(R.id.activity_sgrzlr_img_add)
    ImageView activitySgrzlrImgAdd;
    @BindView(R.id.activity_sgrzlr_EditText_gzjl)
    EditText activitySgrzlrEditTextGzjl;
    @BindView(R.id.activity_sgrzlr_EditText_bz)
    EditText activitySgrzlrEditTextBz;
    @BindView(R.id.activity_sgrzlr_TextView_sgdw)
    TextView activitySgrzlrTextViewSgdw;
    @BindView(R.id.activity_sgrzlr_EditText_tq)
    EditText activitySgrzlrEditTextTq;
    @BindView(R.id.activity_sgrzlr_TextView_tj)
    TextView activitySgrzlrTextViewTj;
    @BindView(R.id.activity_sgrzlr_TextView_back)
    TextView activitySgrzlrTextViewBack;

    private List<SgrzScqkBean> strs;
    private CommonAdapter<SgrzScqkBean> commonAdapter;
    private Project projectBean;
    private AlertDialog alertDialog;
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
        return R.layout.activity_sgrzlr;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        activitySgrzlrTextViewSgdw.setText(PublicUtils.userBean.getEtpShortName());

        strs = new ArrayList<>();

        commonAdapter = new CommonAdapter<SgrzScqkBean>(this, R.layout.sgrzlr_list_item, strs) {
            @Override
            protected void convert(ViewHolder viewHolder, SgrzScqkBean item, int position) {
                viewHolder.setText(R.id.sgrzlr_list_item_tv,(position+1)+"、"+getString(R.string.sgqy_str)+":"+item.getProjectAreaName()
                        +" 工序:"+item.getSubProjectName());
            }
        };
        activitySgrzlrMyListView.setAdapter(commonAdapter);
        activitySgrzlrMyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                strs.remove(position);
                commonAdapter.notifyDataSetChanged();
                return true;
            }
        });
        activitySgrzlrMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (activity_sgrzlr_TextView.getText().equals("请选择项目名称")){
                    PublicUtils.toast("请选择项目名称");
                   return;
                }
                Intent intent = new Intent(SgrzlrActivity.this, ScqkjlActivity.class);
                intent.setAction("item");
                intent.putExtra("thisindex", position);
                intent.putExtra("index", strs.size());
                intent.putExtra("initobj",projectBean);
                intent.putExtra("obj",strs.get(position));
                startActivityForResult(intent, REQUESTCODE2);

            }
        });





    }

    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"userID\":\"" + PublicUtils.UserID + "\"}");
        XUtil.postjsondata(getjsonobj, "GetDailyLogInitialize", new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                SgrzinitBean sgrzinitBean = JsonUtils.getbean(res, SgrzinitBean.class);
                activitySgrzlrEditTextTq.setText(sgrzinitBean.getWeather());
                final List<Project> projectList = sgrzinitBean.getProjectList();
                if (projectList!=null&&projectList.size()>0){
                    activity_sgrzlr_TextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SgrzlrActivity.this, SelectProjectActivity.class);
                            intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    });
                }
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE1 && resultCode == RESULT_OK) {//
            List<SgrzScqkBean> stringArrayListExtra = data.getParcelableArrayListExtra("objs");
            if (stringArrayListExtra.size() > 0) {
                this.strs.addAll(stringArrayListExtra);
                commonAdapter.notifyDataSetChanged();
            }

        }
        if (requestCode == REQUESTCODE2 && resultCode == RESULT_OK) {//
            List<SgrzScqkBean> stringArrayListExtra = data.getParcelableArrayListExtra("objs");
            int thisindex = data.getIntExtra("thisindex", 0);
            if (stringArrayListExtra.size() > 0) {
            SgrzScqkBean sgrzScqkBean = this.strs.get(thisindex);
            SgrzScqkBean sgrzScqkBean1 = stringArrayListExtra.get(0);
            sgrzScqkBean.setSubProjectID(sgrzScqkBean1.getSubProjectID());
            sgrzScqkBean.setSubProjectName(sgrzScqkBean1.getSubProjectName());
            sgrzScqkBean.setRecordDesc(sgrzScqkBean1.getRecordDesc());
                sgrzScqkBean.setElevation(sgrzScqkBean1.getElevation());
            sgrzScqkBean.setNumber(sgrzScqkBean1.getNumber());
            sgrzScqkBean.setFloor(sgrzScqkBean1.getFloor());
            sgrzScqkBean.setProjectAreaName(sgrzScqkBean1.getProjectAreaName());
                sgrzScqkBean.setProjectAreaID(sgrzScqkBean1.getProjectAreaID());
            stringArrayListExtra.remove(0);
                this.strs.addAll(stringArrayListExtra);
                commonAdapter.notifyDataSetChanged();
            }

        }
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            activity_sgrzlr_TextView.setText(strs);
            projectBean=data.getParcelableExtra("project");
       //   List<ProjectArea> AreaList = data.getParcelableArrayListExtra("projectAreaList");
            //areaListBeanNamesSpinnerAdpater.replcedata(list);

            //  PublicUtils.log(JsonUtils.objtojson(list));

        }
    }


    @OnClick({R.id.activity_sgrzlr_TextView_tj, R.id.activity_sgrzlr_TextView_back, R.id.activity_sgrzlr_img_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_sgrzlr_TextView_tj:

                if (activity_sgrzlr_TextView.getText().equals("请选择项目名称")){
                    PublicUtils.toast("请选择项目名称");
                    break;
                }
                String trim2 = activitySgrzlrEditTextTq.getText().toString().trim();
                if (trim2.length()==0){
                    PublicUtils.toast("请输入天气");
                    break;
                }
                String trim1 = activitySgrzlrEditTextGzjl.getText().toString().trim();
                if (trim1.length()==0){
                    PublicUtils.toast("请输入工作记录");
                    break;
                }
                String trim = activitySgrzlrEditTextBz.getText().toString().trim();
//                if (trim.length()==0){
//                    PublicUtils.toast("请输入备注");
//                    break;
//                }
                if (strs.size()==0){
                    PublicUtils.toast("生产情况记录不能为空");
                    break;
                }
//            {
//                "createUserName":"李四",
//                    "projectID":"GC10001",
//                    "recordTime":"2017/4/28",
//                    "dailyRecords":[
//                {
//                    "floor":"3层",
//                        "number":"30",
//                        "projectAreaName":"外框",
//                        "recordDesc":"按计划执行",
//                        "subProjectID":"SubPj10015",
//                        "subProjectName":"外框巨柱模板支架工程"
//                },
//                {
//                    "floor":"3层",
//                        "number":"50",
//                        "projectAreaName":"顶模",
//                        "recordDesc":"按计划执行",
//                        "subProjectID":"SubPj10004",
//                        "subProjectName":"剪力墙砼浇筑"
//                }
//                ],
//                "proShortName":"绿地636项目",
//                    "content":"安全生产台式可控",
//                    "etpID":"QY201704191154",
//                    "weather":"气温：20度，风速：2级",
//                    "createUserID":"lisi",
//                    "etpShortName":"土建一部",
//                    "remarks":"明天计划开始4层施工"
//            }

                JSONObject  jsonObject=   new JSONObject();
                try {
                    jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
                    jsonObject.put("projectID",projectBean.getProjectID());
                    jsonObject.put("recordTime",activitySgrzlrMyTimeSpinner.gettimetext());
                    jsonObject.put("dailyRecords",JsonUtils.getjsonobjs(strs));
                    jsonObject.put("proShortName",projectBean.getProjectShortName());
                    jsonObject.put("content",trim1);
                    jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
                    jsonObject.put("weather",trim2);
                    jsonObject.put("createUserID", PublicUtils.UserID);
                    jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
                    jsonObject.put("remarks",trim);
                    alertDialog.setMessage("上传中...");
                    alertDialog.show();
                    XUtil.postjsondata(jsonObject, "AddDailyLog", new MyCallBack() {
                    /**
                     * code 等于 0回调
                     *
                     * @param res
                     */
                    @Override
                    public void onResule(String res) {
                        alertDialog.dismiss();
                        PublicUtils.toast("新增成功");
                        finish();
                    }

                    /**
                     * code 不等于 0回调
                     *
                     * @param getbean
                     */
                    @Override
                    public void onFail(ObjBean getbean) {
                        alertDialog.setMessage("新增失败！");
                        alertDialog.setCancelable(true);
                        alertDialog.setCanceledOnTouchOutside(true);
                    }

                    /**
                     * 联网失败回调
                     */
                    @Override
                    public void onWrong() {
                        alertDialog.setMessage("新增失败！");
                        alertDialog.setCancelable(true);
                        alertDialog.setCanceledOnTouchOutside(true);
                    }

                        /**
                         * 总是回调用的方法
                         */
                        @Override
                        public void onFinished() {

                        }
                    });
                } catch (JSONException e) {
                   PublicUtils.toast("发生错误，上传失败！");

                }
                break;
            case R.id.activity_sgrzlr_TextView_back:
                break;
            case R.id.activity_sgrzlr_img_add:
                if (activity_sgrzlr_TextView.getText().equals("请选择项目名称")){
                    PublicUtils.toast("请选择项目名称");
                    break;
                }
                Intent intent = new Intent(this, ScqkjlActivity.class);
                intent.setAction("add");
                intent.putExtra("index", strs.size() + 1);
                intent.putExtra("initobj", projectBean);
                startActivityForResult(intent, REQUESTCODE1);
                break;
        }
    }


}
