package com.a21zhewang.constructionapp.ui.zxjc;

import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.Project2CheckItem;
import com.a21zhewang.constructionapp.bean.UPJsonBean;
import com.a21zhewang.constructionapp.bean.ZxjcInitBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
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
import butterknife.OnClick;

public class

AddzxjcActivity extends BaseActivity {

    @BindView(R.id.activity_addzxjc_tv_jcbm)
    TextView activityAddzxjcTvJcbm;
    @BindView(R.id.activity_addzxjc_tv_jcr)
    TextView activityAddzxjcTvJcr;
    @BindView(R.id.activity_addzxjc_tv_jcsj)
    TextView activityAddzxjcTvJcsj;
    @BindView(R.id.activity_addzxjc_TextView_pj)
    TextView activityAddzxjcTextViewPj;
    private Project selectProjectBean = null;
    public static List<CheckTypeOne> checkList = null;

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
        return R.layout.activity_addzxjc;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
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
                    Intent intent = new Intent(AddzxjcActivity.this, SelectProjectActivity.class);
                    SelectProjectActivity.parcelableArrayListExtra= (ArrayList<Project>) projectList;
                    startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                   // intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectList);
                   // startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                }
            });
        }
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
                if (selectProjectBean==null){
                    PublicUtils.toast("请选择项目");
                    break;
                }
                if (checkList == null){
                    PublicUtils.toast("请选择项目");
                    break;
                }

                Intent intent = new Intent(AddzxjcActivity.this, SetCheckListActivity.class);
                intent.putExtra("pj", selectProjectBean);
                startActivityForResult(intent, REQUESTCODE);
                break;
            case R.id.activity_addzxjc_tv_StartCheck:
                if (selectProjectBean==null){
                    PublicUtils.toast("请选择项目");
                    break;
                }
                Intent mIntent = new Intent(AddzxjcActivity.this, TableListActivity.class);
                mIntent.putExtra("pj", selectProjectBean);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            activityAddzxjcTextViewPj.setText(strs);
            selectProjectBean = data.getParcelableExtra("project");
            List<Project2CheckItem> tableList = zxjcInitBean.getCheckTableList();
            if (tableList!=null){
                for (int i = 0; i < tableList.size(); i++) {
                    Project2CheckItem item = tableList.get(i);
                    if (selectProjectBean.getProjectID().equals(item.getProjectID())){
                        checkList = item.getCheckItemList();
                        break;
                    }
                }
            }

          //  List<ProjectArea> projectAreaList = data.getParcelableArrayListExtra("projectAreaList");
            //areaListBeanNamesSpinnerAdpater.replcedata(list);

            //  PublicUtils.log(JsonUtils.objtojson(list));

        }
    }
}
