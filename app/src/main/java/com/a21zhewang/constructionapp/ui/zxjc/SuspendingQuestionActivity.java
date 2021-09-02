package com.a21zhewang.constructionapp.ui.zxjc;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

public class SuspendingQuestionActivity extends BaseActivity {

    @BindView(R.id.activity_suspending_question_RecyclerView)
    RecyclerView activitySuspendingQuestionRecyclerView;
    private BaseQuickAdapter<ErrorMsgBean, BaseViewHolder> quickAdapter;

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
        return R.layout.activity_suspending_question;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        activitySuspendingQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

         quickAdapter = new BaseQuickAdapter<ErrorMsgBean, BaseViewHolder>(R.layout.suspending_question_list_item,null) {



             @Override
             protected void convert(final BaseViewHolder helper, final ErrorMsgBean item) {
                 helper.setText(R.id.suspending_question_tv_bm,item.getRecEtpShortName());
                 helper.setText(R.id.suspending_question_tv_qy,item.getProjectAreaName()+"-" +item.getFloor()+"楼");
                 helper.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                  //       PublicUtils.toast("点击了");
                         String method = item.getRelationTable();

                         if ("SG_SafetyMsg".equals(method)) {//安全检查
                             Intent intent=new Intent(SuspendingQuestionActivity.this, AqscxqActivity.class);
                             intent.putExtra("recordID",item.getRelationID());
                             startActivity(intent);
                         } else if ("SG_QualityMsg".equals(method)) {//质量检查

                             Intent intent=new Intent(SuspendingQuestionActivity.this, ZljdxqActivity.class);
                             intent.putExtra("recordID",item.getRelationID());
                             startActivity(intent);
                         } else if ("SG_CivilizedMsg".equals(method)) {//文明施工
                             Intent intent=new Intent(SuspendingQuestionActivity.this, WmsgxqActivity.class);
                             intent.putExtra("recordID",item.getRelationID());
                             startActivity(intent);
                         }
                     }
                 });
             }
         };

        quickAdapter.openLoadAnimation();
        quickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        activitySuspendingQuestionRecyclerView.addItemDecoration(new SimpleDividerDecoration("#ededed",1));
        activitySuspendingQuestionRecyclerView.setAdapter(quickAdapter);
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
        List<ErrorMsgBean> list = (List<ErrorMsgBean>) getIntent().getSerializableExtra("list");
        if (list!=null&&list.size()>0){
            quickAdapter.addData(list);
        }
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }



}
