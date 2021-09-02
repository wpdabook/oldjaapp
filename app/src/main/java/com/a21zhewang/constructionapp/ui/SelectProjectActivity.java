package com.a21zhewang.constructionapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.Project;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectProjectActivity extends BaseActivity {

    @BindView(R.id.activity_select_project_tv_title)
    TextView activitySelectProjectTvTitle;
    @BindView(R.id.activity_select_project_RecyclerView)
    RecyclerView activitySelectProjectRecyclerView;
    private BaseQuickAdapter<Project, BaseViewHolder> baseQuickAdapter;
    private final List<String> strs=new ArrayList<>();//文字拼接
    private final List<List<Project>> pjlists=new ArrayList<>();//记录每个层级的集合
    private final List<Project> pjs=new ArrayList<>();//记录点击的值
    public final static int RESQUST_CODE_SELECTPROJECT=739;  //项目
    public final static int RESQUST_CODE_SELECT_PROJECT_SEA=740; //施工区域
    public static  ArrayList<Project> parcelableArrayListExtra=null;
    public String StrProjectName;
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
        return R.layout.activity_select_project;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {


        activitySelectProjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        baseQuickAdapter = new BaseQuickAdapter<Project, BaseViewHolder>
                (R.layout.checklist_childitem, null) {


            @Override
            protected void convert(BaseViewHolder helper, final Project item) {
                helper.setText(R.id.checklist_childitem_tv,item.getProjectShortName());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<Project> list = item.getProjectList();
                        strs.add(item.getProjectShortName());
                        StrProjectName = item.getProjectShortName();
                        pjlists.add(list);
                        pjs.add(item);
                        if (list !=null&& list.size()>0){
                            activitySelectProjectTvTitle.setText(getstr(strs));
                            replaceData(list);
                        }else{

                                Intent data = new Intent();
                                data.putExtra("project", item);
                                data.putExtra("StrProjectName",StrProjectName); //只取最后一级
                                data.putExtra("id",item.getProjectID());
//                                if (pjs.size()>0) {
//                                    ArrayList<? extends Parcelable> areaList = (ArrayList<? extends Parcelable>) pjs.get(pjs.size() - 1).getProjectAreaList();
//                                    data.putParcelableArrayListExtra("projectAreaList", areaList);
//                                }
                                data.putExtra("strs",getstr(strs));
                                setResult(RESULT_OK, data);
                                finish();


                        }
                    }
                });
            }
        };
        activitySelectProjectRecyclerView.setAdapter(baseQuickAdapter);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        activitySelectProjectTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pjlists.size() > 1) {
                    pjlists.remove(pjlists.size() - 1);

                }
                if (strs.size() > 0)
                    strs.remove(strs.size() - 1);
                activitySelectProjectTvTitle.setText(getstr(strs));
                // PublicUtils.toast(Types.size()+"");

                if (pjs.size() > 0) {
                    pjs.remove(pjs.size() - 1);
                }
                baseQuickAdapter.replaceData(pjlists.get(pjlists.size()-1));
            }
        });

    }
    private List<Project> list2Tree(List<Project> checks) {
        List<Project> pjs = new ArrayList<>();
        for (int i = 0; i < checks.size(); i++) {
            Project pj = new Project();
            Project checkType = checks.get(i);
            pj.setProjectID(checkType.getProjectID());
            pj.setProjectShortName(checkType.getProjectShortName());
            pj.setProjectAreaList(checkType.getProjectAreaList());
            haschild(pjs).add(pj);
        }
        return pjs;
    }

    private List<Project> haschild(List<Project> msgType) {



        if (msgType.size() > 0) {
            Project type = msgType.get(0);
            List<Project> msgTypes = type.getProjectList();
            if (msgTypes == null) {
                msgTypes = new ArrayList<>();
                type.setProjectList(msgTypes);
                return msgTypes;
            }
            return haschild(msgTypes);
        }

        return msgType;
    }
    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        ArrayList<Project> parcelableArrayList = getIntent().getParcelableArrayListExtra("list");
        if (parcelableArrayList!=null)
        parcelableArrayListExtra = parcelableArrayList;

        pjlists.add(parcelableArrayListExtra);
        baseQuickAdapter.replaceData(parcelableArrayListExtra);
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

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

    @Override
    public void destroy() {
        parcelableArrayListExtra=null;
        super.destroy();
    }
}
