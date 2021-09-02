package com.a21zhewang.constructionapp.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ProjectArea;
import com.a21zhewang.constructionapp.bean.SubProjectBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 10430 on 2018/3/9.
 */

public class SelectSGBWActivity  extends BaseActivity {
    @BindView(R.id.activity_select_project_tv_title)
    TextView activitySelectProjectTvTitle;
    @BindView(R.id.activity_select_project_RecyclerView)
    RecyclerView activitySelectProjectRecyclerView;
    private BaseQuickAdapter<SubProjectBean, BaseViewHolder> baseQuickAdapter;
    private final List<String> strs=new ArrayList<>();//文字拼接
    private final List<String> ids=new ArrayList<>();//文字拼接
    private final List<List<SubProjectBean>> pjlists=new ArrayList<>();//记录每个层级的集合
    private final List<SubProjectBean> pjs=new ArrayList<>();//记录点击的值
    public final static int RESQUST_CODE_SELECTSGBW=104;

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
        baseQuickAdapter = new BaseQuickAdapter<SubProjectBean, BaseViewHolder>
                (R.layout.checklist_childitem, null) {


            @Override
            protected void convert(BaseViewHolder helper, final SubProjectBean item) {
                helper.setText(R.id.checklist_childitem_tv,item.getSubProjectName());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<SubProjectBean> list = item.getSubProjectList();
                        strs.add(item.getSubProjectName());
                        ids.add(item.getSubProjectID());
                        pjlists.add(list);
                        pjs.add(item);
                        if (list !=null&& list.size()>0){
                            activitySelectProjectTvTitle.setText(getstr(strs));
                            replaceData(list);
                        }else{
                            Intent data = new Intent();
                            //  data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list2Tree(pjs));
                            data.putExtra("subProject", item);
                            if (pjs.size()>0)
                                data.putParcelableArrayListExtra("subProjectList", (ArrayList<? extends Parcelable>) pjs.get(pjs.size()-1).getSubProjectList());
                            data.putExtra("strs",getstr(strs));
                            data.putExtra("ids",getstr(ids));
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

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        ArrayList<SubProjectBean> parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("list");
        pjlists.add(parcelableArrayListExtra);
        baseQuickAdapter.replaceData(parcelableArrayListExtra);
    }
    private List<ProjectArea> list2Tree(List<ProjectArea> checks) {
        List<ProjectArea> pjs = new ArrayList<>();
        for (int i = 0; i < checks.size(); i++) {
            ProjectArea pj = new ProjectArea();
            ProjectArea checkType = checks.get(i);
            pj.setProjectAreaID(checkType.getProjectAreaID());
            pj.setProjectAreaName(checkType.getProjectAreaName());
            pj.setEtpInfoList(checkType.getEtpInfoList());
            haschild(pjs).add(pj);
        }
        return pjs;
    }

    private List<ProjectArea> haschild(List<ProjectArea> msgType) {



        if (msgType.size() > 0) {
            ProjectArea type = msgType.get(0);
            List<ProjectArea> msgTypes = type.getProjectAreaList();
            if (msgTypes == null) {
                msgTypes = new ArrayList<>();
                type.setProjectAreaList(msgTypes);
                return msgTypes;
            }
            return haschild(msgTypes);
        }

        return msgType;
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
}
