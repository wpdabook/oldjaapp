package com.a21zhewang.constructionapp.ui.zxjc;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.ErrorMsgBean;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Zxjcxqbean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

public class ZxjcxqActivity extends BaseActivity {

    @BindView(R.id.activity_zxjcxq_RecyclerView)
    RecyclerView activityZxjcxqRecyclerView;
    private BaseQuickAdapter<CheckType, BaseViewHolder> baseQuickAdapter;
    private String recordID;
    private View header;

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
        return R.layout.activity_zxjcxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {

        recordID = getIntent().getStringExtra("recordID");
        if (TextUtils.isEmpty(recordID)){
            return;
        }

        activityZxjcxqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        baseQuickAdapter = new BaseQuickAdapter<CheckType, BaseViewHolder>(R.layout.zxjcxq_item, null) {


            @Override
            protected void convert(BaseViewHolder helper, final CheckType item) {
                TextView textView = helper.getView(R.id.check_item_checkstate);
                GradientDrawable myGrad = (GradientDrawable) textView.getBackground();
                myGrad.setColor(color(item.getCheckResult(), textView));
                helper.setText(R.id.zxjc_item_header,item.getDicFatherName());
                helper.setText(R.id.check_item_content,item.getDicName());
//                .setText(R.id.check_item__zgl,item.getErrorFinishPercent());
                helper.setText(R.id.need_dispose_question,"待处理问题("+item.getErrorMsgList().size()+")");
                helper.getView(R.id.need_dispose_question_look).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("安全".equals(item.getCheckResult())){
                            String checkContent = item.getCheckContent();
                            List<FilesBean> files = item.getFiles();
                            Intent intent=new Intent(ZxjcxqActivity.this,AQXQActivity.class);
                            intent.putExtra("checkContent", checkContent);
                            intent.putExtra("files", (Serializable) files);
                            startActivity(intent);
                        }else{
                            List<ErrorMsgBean> errorMsgList = item.getErrorMsgList();
                            if (errorMsgList.size()>0){
                                Intent intent=new Intent(ZxjcxqActivity.this,SuspendingQuestionActivity.class);
                                intent.putExtra("list", (Serializable)errorMsgList);
                                startActivity(intent);
                            }else {
                                PublicUtils.toast("待处理问题为0条。");
                            }
                        }

                    }
                });
            }
        };
        header = LayoutInflater.from(this).inflate(R.layout.zxjcxq_header, null, false);

        baseQuickAdapter.addHeaderView(header);
        activityZxjcxqRecyclerView.setAdapter(baseQuickAdapter);
    }
    private int color(String str, TextView view) {
        view.setText(str);
        if ("有隐患".equals(str)) {
            return ContextCompat.getColor(ZxjcxqActivity.this, R.color.color_yyh);
        } else if ("安全".equals(str)) {
            return ContextCompat.getColor(ZxjcxqActivity.this, R.color.color_qq);
        }
        return ContextCompat.getColor(ZxjcxqActivity.this, R.color.color_wjc);
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
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"recordID\":\""+recordID
                +"\"}"), "GetSpecialExaminationDetails", new MyCallBack() {
            @Override
            public void onResule(String res) {
                Zxjcxqbean getbean = JsonUtils.getbean(res, Zxjcxqbean.class);
                List<CheckTypeOne> checkItemList = getbean.getCheckItemList();
                TextView title= (TextView) header.findViewById(R.id.zxjcxq_header_title);
                title.setText(getbean.getTitle());
                TextView pjname= (TextView) header.findViewById(R.id.zxjcxq_header_pjname);
                pjname.setText(getbean.getProShortName());
                TextView time= (TextView) header.findViewById(R.id.zxjcxq_header_time);
                time.setText(getbean.getCheckTime());

                TextView user= (TextView) header.findViewById(R.id.zxjcxq_header_user);
                user.setText(getbean.getCreateUserName());

                TextView bm= (TextView) header.findViewById(R.id.zxjcxq_header_bm);
                bm.setText(getbean.getEtpShortName());
                try {

                    baseQuickAdapter.replaceData(getbean.getCheckItemList());
                }catch (Exception e){

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



}
