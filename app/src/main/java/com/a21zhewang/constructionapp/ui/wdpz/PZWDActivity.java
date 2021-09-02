package com.a21zhewang.constructionapp.ui.wdpz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.Project2CheckItem;
import com.a21zhewang.constructionapp.bean.RiskAreaAndStage;
import com.a21zhewang.constructionapp.bean.RiskProject;
import com.a21zhewang.constructionapp.bean.UPJsonBean;
import com.a21zhewang.constructionapp.bean.ZxjcInitBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReportAdd;
import com.a21zhewang.constructionapp.ui.risk.SelectRiskProject;
import com.a21zhewang.constructionapp.ui.xmxx.fragment.zongbaoFragment;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.utils.dbUtils;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PZWDActivity extends BaseActivity {

    @BindView(R.id.activity_addzxjc_tv_jcbm)
    TextView activityAddzxjcTvJcbm;
    @BindView(R.id.activity_addzxjc_tv_jcr)
    TextView activityAddzxjcTvJcr;
    @BindView(R.id.activity_addzxjc_tv_jcsj)
    TextView activityAddzxjcTvJcsj;
    @BindView(R.id.activity_addzxjc_TextView_pj)
    TextView activityAddzxjcTextViewPj;
    @BindView(R.id.wdjc_pz_area)
    TextView wdjc_pz_area;
    @BindView(R.id.wdjc_pz_stage)
    TextView wdjc_pz_stage;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.lin_area)
    LinearLayout lin_area;
    @BindView(R.id.lin_stage)
    LinearLayout lin_stage;
    @BindView(R.id.activity_addzxjc_tv_StartCheck)
    TextView activityAddzxjcTvStartCheck;
    private Project selectProjectBean = null;
    public static List<CheckTypeOne> checkList = null;
    private String projectId="";
    private RiskProject riskProject;
    private String dicID,dicName;
    private List<RiskAreaAndStage> riskAreaAndStageList;
    private String checkRegionId,checkRegionName;
    private String company_strs;

    //请求码
    private static final int REQUESTCODE = 133;
    private ZxjcInitBean zxjcInitBean;

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
        return R.layout.act_wdpz_layout;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        lin.setVisibility(View.GONE);
        activityAddzxjcTvStartCheck.setText("开始上报");
        activityAddzxjcTvJcbm.setText(PublicUtils.userBean.getEtpShortName());
        activityAddzxjcTvJcr.setText(PublicUtils.userBean.getUserName());
        activityAddzxjcTvJcsj.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));


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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"getDataType\":\"project\"}"), "SpecialExaminationType", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "init"));
                } catch (DbException e) {
                    PublicUtils.log("清除专项检查数据失败！");
                }
                dbUtils.savajson(res, "init", "", null);

                setdata(res);

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {
                try {
                    List<UPJsonBean> upJsonBeen = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "init")).findAll();
                    if (upJsonBeen != null && upJsonBeen.size() > 0) {
                        UPJsonBean upJsonBean = upJsonBeen.get(0);
                        setdata(upJsonBean.getDatatext());
                    }
                } catch (DbException e) {
                    PublicUtils.log("获取专项检查数据失败！");
                }
            }
        });
    }

    private void setdata(String res) {
        if (TextUtils.isEmpty(res)) return;
        zxjcInitBean = JsonUtils.getbean(res, ZxjcInitBean.class);

        final List<Project> projectList = zxjcInitBean.getProjectList();
        if (projectList != null && projectList.size() > 0) {
            activityAddzxjcTextViewPj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PZWDActivity.this, SelectProjectActivity.class);
                    SelectProjectActivity.parcelableArrayListExtra = (ArrayList<Project>) projectList;
                    startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                    // intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                    // startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                }
            });
            //施工区域
            wdjc_pz_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getProjectAreas();
                }
            });
           //施工阶段
            wdjc_pz_stage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getStage();
                }
            });
        }
    }
    /**
     * 选择区域
     */
    public void getProjectAreas(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"area\",\"projectId\":\"" + projectId + "\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskProject = JsonUtils.getbean(res,RiskProject.class);
                riskAreaAndStageList = riskProject.getBackdata();

                if (riskAreaAndStageList != null) {
                    Intent intent = new Intent(PZWDActivity.this, SelectRiskProject.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) riskAreaAndStageList);
                    intent.putExtra("sendType","Area");
                    startActivityForResult(intent, SelectRiskProject.RESQUST_CODE_SELECT_AREA);
                } else {
                    PublicUtils.toast("请选择施工区域");
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
     * 获取施工阶段
     */
    public void getStage(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"stage\",\"projectId\":\"" + projectId + "\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskProject = JsonUtils.getbean(res,RiskProject.class);
                riskAreaAndStageList = riskProject.getBackdata();
                if (riskAreaAndStageList != null) {
                    Intent intent = new Intent(PZWDActivity.this, SelectRiskProject.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) riskAreaAndStageList);
                    intent.putExtra("sendType","Stage");
                    startActivityForResult(intent, SelectRiskProject.RESQUST_CODE_SELECT_STAGE);
                } else {
                    PublicUtils.toast("请选择施工阶段");
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


    @OnClick({R.id.activity_addzxjc_tv_pz, R.id.activity_addzxjc_tv_StartCheck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_addzxjc_tv_pz:
                if (checkList == null||selectProjectBean == null) {
                    PublicUtils.toast("请选择项目");
                    break;
                }

                Intent intent = new Intent(PZWDActivity.this, SetwdCheckListActivity.class);
                intent.putExtra("pj", selectProjectBean);
              //  startActivityForResult(intent, REQUESTCODE);
                startActivity(intent);
                break;
            case R.id.activity_addzxjc_tv_StartCheck:
                if (selectProjectBean == null) {
                    PublicUtils.toast("请选择项目");
                    break;
                }
//                if (TextUtils.isEmpty(wdjc_pz_area.getText().toString())) {
//                    PublicUtils.toast("请选择施工区域");
//                    break;
//                }
//                if (TextUtils.isEmpty(wdjc_pz_stage.getText().toString())) {
//                    PublicUtils.toast("请选择施工阶段");
//                    break;
//                }
                Intent mIntent = new Intent(PZWDActivity.this, SetwdCheckListActivity.class);
                mIntent.putExtra("pj", selectProjectBean);
//                mIntent.putExtra("id",selectProjectBean.getProjectID());
//                mIntent.putExtra("checkRegionId",checkRegionId);
//                mIntent.putExtra("checkRegionName",checkRegionName);
//                mIntent.putExtra("dicID",dicID);
//                mIntent.putExtra("dicName",dicName);
                mIntent.putExtra("fromWhere","1");
                startActivity(mIntent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            company_strs = data.getStringExtra("strs");
            projectId = data.getStringExtra("id");
            activityAddzxjcTextViewPj.setText(company_strs);
            selectProjectBean = data.getParcelableExtra("project");
            wdjc_pz_area.setText("");
            wdjc_pz_stage.setText("");
            List<Project2CheckItem> tableList = zxjcInitBean.getCheckTableList();
            if (tableList != null) {
                for (int i = 0; i < tableList.size(); i++) {
                    Project2CheckItem item = tableList.get(i);
                    if (selectProjectBean.getProjectID().equals(item.getProjectID())) {
                        checkList = item.getCheckItemList();
                        break;
                    }
                }
            }

            //  List<ProjectArea> projectAreaList = data.getParcelableArrayListExtra("projectAreaList");
            //areaListBeanNamesSpinnerAdpater.replcedata(list);

            //  PublicUtils.log(JsonUtils.objtojson(list));

        }
        //施工区域
        if (requestCode == SelectRiskProject.RESQUST_CODE_SELECT_AREA
                && resultCode == RESULT_OK
                && data != null) {
            checkRegionName = data.getStringExtra("strs");
            checkRegionId = data.getStringExtra("ids");
            if(company_strs == null || company_strs == ""){
                PublicUtils.toast("请选择项目");
                return;
            }
            wdjc_pz_area.setText(checkRegionName);
        }
        //施工阶段
        if (requestCode == SelectRiskProject.RESQUST_CODE_SELECT_STAGE
                && resultCode == RESULT_OK
                && data != null) {
            dicName = data.getStringExtra("strs");
            dicID = data.getStringExtra("ids");
            if(company_strs == null || company_strs == ""){
                PublicUtils.toast("请选择项目");
                return;
            }
            wdjc_pz_stage.setText(dicName);
        }
    }


}
