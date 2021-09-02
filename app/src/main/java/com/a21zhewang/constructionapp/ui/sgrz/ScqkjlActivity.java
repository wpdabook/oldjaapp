package com.a21zhewang.constructionapp.ui.sgrz;

import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.SgrzScqkBean;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectSGBWActivity;
import com.a21zhewang.constructionapp.ui.SelectSgqyActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.AddGtxtActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class
ScqkjlActivity extends BaseActivity {

    @BindView(R.id.activity_scqkjl_MyTitleBar)
    MyTitleBar activityScqkjlMyTitleBar;
    @BindView(R.id.activity_scqkjl_TextView_sgqy)
    TextView activityScqkjlTextViewSgqy;
    @BindView(R.id.activity_scqkjl_MySpinner_scgx)
    MySpinner activityScqkjlMySpinnerScgx;
    @BindView(R.id.activity_scqkjl_EditText_lc)
    EditText activityScqkjlEditTextLc;
    @BindView(R.id.activity_scqkjl_EditText_rs)
    EditText activityScqkjlEditTextRs;
    @BindView(R.id.activity_scqkjl_EditText_scqk)
    EditText activityScqkjlEditTextScqk;
    @BindView(R.id.activity_add_scqkjl_MySpinner_lc)
    MySpinner activityAddScqkjlMySpinnerLc;

    @BindView(R.id.activity_add_scqkjl_MySpinner_bg)
    MySpinner activity_add_scqkjl_MySpinner_bg;
    @BindView(R.id.activity_scqkjl_EditText_bg)
    EditText activityScqkjlEditTextBg;
    @BindView(R.id.activity_scqkjl_EditText_scgx)
    EditText activityScqkjlEditTextScgx;
    private int index;
    @BindView(R.id.activity_scqkjl_tv_index)
    TextView activityScqkjlTvIndex;

    List<SgrzScqkBean> SgrzScqkBeans;
    private int thisindex;
    private String action;
    private Project projectBean;
    private SgrzScqkBean initobj;
    private SubProjectBean subProjectListBean;
    private ProjectArea projectAreaListBean;
    private List<ProjectArea> projectAreaList;
    private List<SubProjectBean> subProjectListBeans;
    private String subids;
    private String[] lc, strs2, bg, bg2;;
    // private NamesSpinnerAdpater<SubProjectBean> subProjectListBeanNamesSpinnerAdpater;

    @BindView(R.id.spinner_lc)
    MySpinner spinner_lc;
    @BindView(R.id.spinner_wz)
    MySpinner spinner_wz;
    @BindView(R.id.ed_lc1)
    EditText ed_lc1;
    @BindView(R.id.ed_lc2)
    EditText ed_lc2;
    @BindView(R.id.ed_wz)
    EditText ed_wz;
    @BindView(R.id.lin_lc)
    LinearLayout lin_lc;
    @BindView(R.id.tv_title)
    TextView tv_title;
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
        return R.layout.activity_scqkjl;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {

        SgrzScqkBeans = new ArrayList<>();
    }

    @Override
    public void initListener() {
        activityScqkjlMyTitleBar.setback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setbackisok();
            }
        });
        activityScqkjlTextViewSgqy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScqkjlActivity.this, SelectSgqyActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectBean.getProjectAreaList()
                );
                startActivityForResult(intent, SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ);
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        subProjectListBeans = new ArrayList<>();
        Intent intent = getIntent();
        action = intent.getAction();
        projectBean = intent.getParcelableExtra("initobj");
        if (action != null && action.equals("item")) {
            thisindex = intent.getIntExtra("thisindex", 0);
            index = intent.getIntExtra("index", 0);
            initobj = intent.getParcelableExtra("obj");
            activityScqkjlTvIndex.setText((thisindex + 1) + "");
            if ("1".equals(projectBean.getProjectType())){
                String floor = initobj.getFloor();
                if (!TextUtils.isEmpty(floor)) {
                    String substring = floor.substring(0, floor.length() - 1);
                    String lc = floor.substring(floor.length() - 1, floor.length());
                    activityScqkjlEditTextLc.setText(substring);
                    activityAddScqkjlMySpinnerLc.setTextViewtext(lc);
                }
                String elevation = initobj.getElevation();
                if (elevation !=null){


                    String substring = floor.substring(0, elevation.length() - 1);
                    String bg = floor.substring(elevation.length() - 1, elevation.length());
                    activityScqkjlEditTextBg.setText(substring);
                    activity_add_scqkjl_MySpinner_bg.setTextViewtext(bg);
                }
            }else{
//                String floor = initobj.getFloor();
//                if (!TextUtils.isEmpty(floor)) {
//                    String substring = floor.substring(0, floor.length() - 1);
//                    String lc = floor.substring(floor.length() - 1, floor.length());
//                    activityScqkjlEditTextLc.setText(substring);
//                    activityAddScqkjlMySpinnerLc.setTextViewtext(lc);
//                }
//                String elevation = initobj.getElevation();
//                if (elevation !=null){
//
//
//
//                    String substring = floor.substring(0, elevation.length() - 1);
//                    String bg = floor.substring(elevation.length() - 1, elevation.length());
//                    activityScqkjlEditTextBg.setText(substring);
//                    activity_add_scqkjl_MySpinner_bg.setTextViewtext(bg);
//                }
            }

            activityScqkjlEditTextRs.setText(initobj.getNumber());
            activityScqkjlEditTextScqk.setText(initobj.getRecordDesc());
            String projectAreaName = initobj.getProjectAreaName();
            activityScqkjlTextViewSgqy.setText(projectAreaName);
            String subProjectName = initobj.getSubProjectName();
            if (projectAreaName.equals("地下室")||projectAreaName.equals("其他")){
                activityScqkjlMySpinnerScgx.setVisibility(View.INVISIBLE);
                activityScqkjlEditTextScgx.setVisibility(View.VISIBLE);
                activityScqkjlEditTextScgx.setText(subProjectName);
            }
            activityScqkjlMySpinnerScgx.setTextViewtext(subProjectName);
            subids=initobj.getSubProjectID();
            List<ProjectArea> projectAreaList = projectBean.getProjectAreaList();
            int i = projectAreaName.lastIndexOf("/");
            getProjectArea(projectAreaName.substring(i+1), projectAreaList);
            projectAreaListBean.setProjectAreaName(projectAreaName);
            projectAreaListBean.setProjectAreaID(initobj.getProjectAreaID());
            subProjectListBeans.addAll(projectAreaListBean.getSubProjectList());

            for (SubProjectBean b : subProjectListBeans) {
             //   PublicUtils.log("循环：" + b.getSubProjectName());
                String subProjectID = initobj.getSubProjectID();
                int indexOf = subProjectID.lastIndexOf("/");
                if (b.getSubProjectID().equals(subProjectID.substring(indexOf+1))) {
                    subProjectListBean = b;
                  break;
                }
            }
        } else {
            index = intent.getIntExtra("index", 0);
            activityScqkjlTvIndex.setText(index + "");
        }





//        subProjectListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<SubProjectBean>(this, subProjectListBeans) {
//            @Override
//            public void setinit(SubProjectBean name, ViewHolder mViewHolder) {
//                mViewHolder.nametextview.setText(name.getSubProjectName());
//
//            }
//        };
       // activityScqkjlMySpinnerScgx.setmyspinnerlistadpater(subProjectListBeanNamesSpinnerAdpater);
//        activityScqkjlMySpinnerScgx.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                subProjectListBean = subProjectListBeans.get(position);
//                activityScqkjlMySpinnerScgx.setTextViewtext(subProjectListBean.getSubProjectName());
//                activityScqkjlMySpinnerScgx.popdismiss();
//            }
//        });
        //楼层下拉
        lc = new String[]{"F", "B"};
        strs2 = new String[]{"DK", "K"};
        bg = new String[]{"米"};
        bg2 = new String[]{"号", "节", "联", "仓", "环", "桩"};
if ("1".equals(projectBean.getProjectType())){
    lin_lc.setVisibility(View.VISIBLE);
tv_title.setText("楼层");
    activityAddScqkjlMySpinnerLc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, lc) {
        @Override
        public void setinit(String name, ViewHolder mViewHolder) {
            mViewHolder.nametextview.setText(name);
        }
    });
    activityAddScqkjlMySpinnerLc.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            activityAddScqkjlMySpinnerLc.setTextViewtext(lc[position]);
            activityAddScqkjlMySpinnerLc.popdismiss();
        }
    });
    if (action == null && !action.equals("item"))
        activityAddScqkjlMySpinnerLc.setTextViewtext(lc[0]);

    activity_add_scqkjl_MySpinner_bg.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg) {
        @Override
        public void setinit(String name, ViewHolder mViewHolder) {
            mViewHolder.nametextview.setText(name);
        }
    });
    activity_add_scqkjl_MySpinner_bg.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            activity_add_scqkjl_MySpinner_bg.setTextViewtext(bg[position]);
            activity_add_scqkjl_MySpinner_bg.popdismiss();
        }
    });
    if (action == null && !action.equals("item"))
        activity_add_scqkjl_MySpinner_bg.setTextViewtext(bg[0]);
    activityScqkjlEditTextLc.setHint("楼层");
    activityScqkjlEditTextBg.setHint("标高");
}else{
    tv_title.setText("里程");

    lin_lc.setVisibility(View.GONE);
    spinner_lc.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, strs2) {
        @Override
        public void setinit(String name, ViewHolder mViewHolder) {
            mViewHolder.nametextview.setText(name);
        }
    });
    spinner_lc.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            spinner_lc.setTextViewtext(strs2[position]);
            spinner_lc.popdismiss();
        }
    });
    if (action == null && !action.equals("item"))
        spinner_lc.setTextViewtext(strs2[0]);
    spinner_wz.setmyspinnerlistadpater(new NamesSpinnerAdpater<String>(this, bg2) {
        @Override
        public void setinit(String name, ViewHolder mViewHolder) {
            mViewHolder.nametextview.setText(name);
        }
    });
    spinner_wz.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            spinner_wz.setTextViewtext(bg2[position]);
            spinner_wz.popdismiss();
        }
    });
    if (action == null && !action.equals("item"))
        spinner_wz.setTextViewtext(bg2[0]);

  //  ed.setHint("里程");
 //   activityScqkjlEditTextBg.setHint("");
}
    }

    private boolean getProjectArea(String projectAreaName, List<ProjectArea> projectAreaList) {
        if (projectAreaList==null||projectAreaList.size()==0)return false;
        for (ProjectArea b : projectAreaList) {

            if (b.getProjectAreaName().equals(projectAreaName)) {
                projectAreaListBean = b;
               return true;
            }
            if (getProjectArea(projectAreaName,b.getProjectAreaList())){
                return true;
            }
        }
        return false;
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_scqkjl_img_saveandadd, R.id.activity_scqkjl_tv_saveandadd, R.id.activity_scqkjl_tv_save, R.id.activity_scqkjl_tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_scqkjl_img_saveandadd:
                panduan();
                break;
            case R.id.activity_scqkjl_tv_saveandadd:
                panduan();
                break;
            case R.id.activity_scqkjl_tv_save:
                if (TextUtils.isEmpty(panduan())) {
                    break;
                }

                setbackisok();
                break;
            case R.id.activity_scqkjl_tv_back:
                //panduan();
                setbackisok();
                break;
        }
    }

    private void setbackisok() {
        if (SgrzScqkBeans.size() > 0) {

            Intent data = new Intent();
            if (action.equals("item"))
                data.putExtra("thisindex", thisindex);
            data.putParcelableArrayListExtra("objs", (ArrayList<? extends Parcelable>) SgrzScqkBeans);
            setResult(RESULT_OK, data);
        }
        finish();
    }

    public String panduan() {
        String activityScqkjlMySpinnerSgqyTextViewtext = activityScqkjlTextViewSgqy.getText().toString();
        if (activityScqkjlMySpinnerSgqyTextViewtext.equals(getString(R.string.sgqy_str))) {
            PublicUtils.toast("请选择生产区域");
            return null;
        }
        if (activityScqkjlEditTextScgx.getVisibility()==View.VISIBLE){
            if (activityScqkjlEditTextScgx.getText().toString().trim().length()==0){
                PublicUtils.toast("请输入施工部位");
                return null;
            }

        }else{
            String activityScqkjlMySpinnerScgxTextViewtext = activityScqkjlMySpinnerScgx.getTextViewtext();
            if (activityScqkjlMySpinnerScgxTextViewtext.equals("施工部位")) {
                PublicUtils.toast("请选择施工部位");
                return null;
            }
        }



        String trim = activityScqkjlEditTextLc.getText().toString().trim();

        String bg_str = activityScqkjlEditTextBg.getText().toString();

        String lc1 = ed_lc1.getText().toString().trim();
        String lc2 = ed_lc2.getText().toString().trim();
        String wz = ed_wz.getText().toString().trim();
        boolean b = lin_lc.getVisibility() == View.VISIBLE;
        if (b) {
            if (trim.length() == 0 && bg_str.length() == 0) {
                PublicUtils.toast("请输入楼层或标高");
                return null;
            }
            if (!PublicUtils.isMobileNO(bg_str)&&TextUtils.isEmpty(trim.trim())){
                PublicUtils.toast("请正确输入标高");
                return null;
            }
        } else {
            if (TextUtils.isEmpty(wz) && (TextUtils.isEmpty(lc1) || TextUtils.isEmpty(lc2))) {
                PublicUtils.toast("请输入请输入里程或者位置！");
                return null;
            }
        }


        String trim1 = activityScqkjlEditTextRs.getText().toString().trim();
        if (trim1.length() == 0) {
            PublicUtils.toast("请输入人数");
            return null;
        }
        String trim2 = activityScqkjlEditTextScqk.getText().toString().trim();
        if (trim2.length() == 0) {
            PublicUtils.toast("请输入生产情况");
            return null;
        }

        SgrzScqkBean sgrzScqkBean = new SgrzScqkBean();
        if (b){
            if (!TextUtils.isEmpty(trim.trim()))
                sgrzScqkBean.setFloor(trim + activityAddScqkjlMySpinnerLc.getTextViewtext());
            if (!TextUtils.isEmpty(bg_str)){
                sgrzScqkBean.setElevation(bg_str+activity_add_scqkjl_MySpinner_bg.getTextViewtext());
                PublicUtils.log(bg_str);
            }
        }else{
            if (!TextUtils.isEmpty(lc1)&&!TextUtils.isEmpty(lc2))
            sgrzScqkBean.setFloor(spinner_lc.getTextViewtext()+lc1+"+"+lc2);
            if (!TextUtils.isEmpty(wz))
            sgrzScqkBean.setElevation(wz+spinner_wz.getTextViewtext());

        }



        sgrzScqkBean.setNumber(trim1);
        sgrzScqkBean.setRecordDesc(trim2);



            sgrzScqkBean.setProjectAreaID(projectAreaListBean.getProjectAreaID());
            sgrzScqkBean.setProjectAreaName(projectAreaListBean.getProjectAreaName());



        if (activityScqkjlEditTextScgx.getVisibility()==View.VISIBLE){

            sgrzScqkBean.setSubProjectName(activityScqkjlEditTextScgx.getText().toString().trim());
            sgrzScqkBean.setSubProjectID(subProjectListBean.getSubProjectID());

        }else{

            sgrzScqkBean.setSubProjectName(activityScqkjlMySpinnerScgx.getTextViewtext());
            sgrzScqkBean.setSubProjectID(subids);
        }

        SgrzScqkBeans.add(sgrzScqkBean);
        activityScqkjlTvIndex.setText((++index) + "");
        activityScqkjlTextViewSgqy.setText(getString(R.string.sgqy_str));
        activityScqkjlMySpinnerScgx.setTextViewtext("施工部位");
        activityScqkjlEditTextLc.setText("");
        activityScqkjlEditTextBg.setText("");
        activityScqkjlEditTextRs.setText("");
        activityScqkjlEditTextScqk.setText("");
        return "s";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectSgqyActivity.RESQUST_CODE_SELECTSGYQ
                && resultCode == RESULT_OK
                && data != null) {
            String strs = data.getStringExtra("strs");
            String ids = data.getStringExtra("ids");
            activityScqkjlTextViewSgqy.setText(strs);
            projectAreaListBean= data.getParcelableExtra("projectArea");
            projectAreaListBean.setProjectAreaID(ids);
            projectAreaListBean.setProjectAreaName(strs);
           // ArrayList<Etp> list = data.getParcelableArrayListExtra("etpInfoList");
            String projectAreaName = projectAreaListBean.getProjectAreaName();



            if (projectAreaName.equals("地下室")||projectAreaName.equals("其它")){
                if (subProjectListBeans.size()>0)
                    subProjectListBean=subProjectListBeans.get(0);
                activityScqkjlMySpinnerScgx.setVisibility(View.INVISIBLE);
                activityScqkjlEditTextScgx.setVisibility(View.VISIBLE);
            }else{
                activityScqkjlEditTextScgx.setVisibility(View.INVISIBLE);
                activityScqkjlMySpinnerScgx.setVisibility(View.VISIBLE);
            }
            activityScqkjlMySpinnerScgx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScqkjlActivity.this, SelectSGBWActivity.class);
                    intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) projectAreaListBean.getSubProjectList());
                    startActivityForResult(intent, SelectSGBWActivity.RESQUST_CODE_SELECTSGBW);
                }
            });
          //  subProjectListBeanNamesSpinnerAdpater.replcedata(projectAreaListBean.getSubProjectList());
            activityScqkjlMySpinnerScgx.setTextViewtext("施工部位");

        }
        if (requestCode == SelectSGBWActivity.RESQUST_CODE_SELECTSGBW
                && resultCode == RESULT_OK
                && data != null){
            String strs = data.getStringExtra("strs");
            subids = data.getStringExtra("ids");
            activityScqkjlMySpinnerScgx.setTextViewtext(strs);
             subProjectListBean =data.getParcelableExtra("subProject");
           // getcsdw(subProjectListBean.getSubProjectID());
          //  scgxbean = subProjectListBean;
        }
    }
}
