package com.a21zhewang.constructionapp.ui.jdsb;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectPhase;
import com.a21zhewang.constructionapp.bean.ProjectRoot;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljw.customseekbar.CustomSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ADDJDActivity extends BaseActivity {

    @BindView(R.id.img_addjd)
    TextView imgAddjd;
    @BindView(R.id.MyTitleBar)
    com.a21zhewang.constructionapp.customview.MyTitleBar MyTitleBar;
    @BindView(R.id.rv_jdlist)
    RecyclerView jdlist;
    @BindView(R.id.ll_cotent)
    LinearLayout llCotent;
    @BindView(R.id.tv_dw)
    TextView tvDw;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_pj)
    TextView tvPj;
    @BindView(R.id.csb_zczwcb)
    CustomSeekBar csbZczwcb;
    @BindView(R.id.csb_ydczwcb)
    CustomSeekBar csbYdczwcb;
    private BaseQuickAdapter<ProjectPhase, BaseViewHolder> baseQuickAdapter;
    private Project selectProjectBean;
    private String strs;
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
        return R.layout.activity_addjd;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中！");
        tvDw.setText(PublicUtils.userBean.getEtpShortName());
        tvUser.setText(PublicUtils.userBean.getUserName());
        jdlist.setLayoutManager(new LinearLayoutManager(this));
        /**
         * Implement this method and use the helper to adapt the view to the given item.
         *
         * @param helper A fully initialized helper.
         * @param item   The item that needs to be displayed.
         */
        baseQuickAdapter = new BaseQuickAdapter<ProjectPhase, BaseViewHolder>(R.layout.jdsbxq_item, null) {
            /**
             * Implement this method and use the helper to adapt the view to the given item.
             *
             * @param helper A fully initialized helper.
             * @param item   The item that needs to be displayed.
             */
            @Override
            protected void convert(BaseViewHolder helper, ProjectPhase item) {
                helper.setText(R.id.tv_jdname, item.getDicName());
                helper.setText(R.id.tv_double, item.getCompletionRate());
                helper.setText(R.id.tv_day, item.getLaggingdays());
                helper.setText(R.id.tv_content, item.getRecordDesc());
                helper.setText(R.id.tv_jh, item.getPlanStartDate() + "~" + item.getPlanEndDate());
                helper.setText(R.id.tv_sj, item.getActuallyStartDate() + "~" + item.getActuallyEndDate());


            }
        };
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ADDJDActivity.this, ADDJDlistActivity.class);
                intent.putExtra("pjname", tvPj.getText().toString());
                intent.putExtra("position", String.valueOf(position));
                intent.putExtra("bean", baseQuickAdapter.getItem(position));
                startActivityForResult(intent, 177);
            }
        });
        //jdlist.setHasFixedSize(true);
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        jdlist.setNestedScrollingEnabled(false);
        jdlist.setAdapter(baseQuickAdapter);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        imgAddjd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectProjectBean == null) {
                    PublicUtils.toast("请先选择项目");
                    return;
                }

                Intent intent = new Intent(ADDJDActivity.this, ADDJDlistActivity.class);
                intent.putExtra("pjname", tvPj.getText().toString());
                startActivityForResult(intent, 177);
            }
        });

        MyTitleBar.setRighttextviewonclick(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (selectProjectBean == null) {
                    PublicUtils.toast("请先选择项目");
                    return;
                }
                if (baseQuickAdapter.getData().size() == 0) {
                    PublicUtils.toast("你还没有添加工程阶段");
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("createUserID", PublicUtils.userBean.getUserID());
                    jsonObject.put("createUserName", PublicUtils.userBean.getUserName());
                    jsonObject.put("etpID", PublicUtils.userBean.getEtpID());
                    jsonObject.put("etpShortName", PublicUtils.userBean.getEtpShortName());
                    jsonObject.put("projectID", selectProjectBean.getProjectID());
                    jsonObject.put("proShortName", strs);
                    jsonObject.put("title", selectProjectBean.getProjectShortName()+"的"+baseQuickAdapter.getData().get(0).getDicName()+"等进度计划");
                    jsonObject.put("recordDate", TimeUtils.getNowString());
                    int csbZczwcbPosstion = (int) csbZczwcb.getPosstion();
                    jsonObject.put("totalPercentage", csbZczwcbPosstion+"%");
                    int csbYdczwcbPosstion = (int) csbYdczwcb.getPosstion();
                    jsonObject.put("monthPercentage", csbYdczwcbPosstion+"%");
                    jsonObject.put("projectPhase", JsonUtils.List2jsonArray(baseQuickAdapter.getData()));
                    loadingDialog.show();

                    XUtil.postjsondata(jsonObject, "AddScheduleReport", new MyCallBack() {
                        /**
                         * 请求成功code 等于 0回调
                         *
                         * @param res
                         */
                        @Override
                        public void onResule(String res) {
                            finish();
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

                        @Override
                        public void onFinished() {

                            WeiboDialogUtils.closeDialog(loadingDialog);
                        }
                    });
                } catch (JSONException e) {
                    // e.printStackTrace();
                    WeiboDialogUtils.closeDialog(loadingDialog);
                }

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"getDataType\":\"project\"}"), "SpecialExaminationType", new MyCallBack() {
            @Override
            public void onResule(String res) {
                ProjectRoot baseAddinitBean = JsonUtils.getbean(res, ProjectRoot.class);
                final List<Project> pjList = baseAddinitBean.getProjectList();
                if (pjList != null && pjList.size() > 0) {
                    tvPj.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ADDJDActivity.this, SelectProjectActivity.class);

                            //    Bundle bundle = new Bundle();
                            //       bundle.putParcelableArrayList("list",(ArrayList<? extends Parcelable>) pjList);
                            //      intent.putExtra("list", bundle);
                            SelectProjectActivity.parcelableArrayListExtra = (ArrayList<Project>) pjList;
                            startActivityForResult(intent, SelectProjectActivity.RESQUST_CODE_SELECTPROJECT);
                        }
                    });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 177 && resultCode == RESULT_OK) {
            ProjectPhase bean = data.getParcelableExtra("data");
            String pos = data.getStringExtra("pos");
            if (!TextUtils.isEmpty(pos)) {
                int position = Integer.parseInt(pos);
                baseQuickAdapter.getData().set(position, bean);

            } else {
                baseQuickAdapter.addData(bean);

            }
            Collections.sort(baseQuickAdapter.getData());
            baseQuickAdapter.notifyDataSetChanged();
            ;
        }
        if (requestCode == SelectProjectActivity.RESQUST_CODE_SELECTPROJECT
                && resultCode == RESULT_OK
                && data != null) {
            strs = data.getStringExtra("strs");
            tvPj.setText(strs);
            selectProjectBean = data.getParcelableExtra("project");
        }
    }



}
