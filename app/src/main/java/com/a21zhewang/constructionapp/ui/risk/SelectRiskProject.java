package com.a21zhewang.constructionapp.ui.risk;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.RiskAreaAndStage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 风险上报
 * @auto
 */

public class SelectRiskProject  extends BaseActivity{
    @BindView(R.id.activity_select_project_tv_title)
    TextView activitySelectProjectTvTitle;
    @BindView(R.id.activity_select_project_RecyclerView)
    RecyclerView activitySelectProjectRecyclerView;
    private BaseQuickAdapter<RiskAreaAndStage, BaseViewHolder> baseQuickAdapter;
    private final List<String> strs=new ArrayList<>();//文字拼接
    private final List<String> ids=new ArrayList<>();//文字拼接
    private final List<List<RiskAreaAndStage>> pjlists=new ArrayList<>();//记录每个层级的集合
    private final List<RiskAreaAndStage> pjs=new ArrayList<>();//记录点击的值
    public final static int RESQUST_CODE_SELECT_AREA=389;
    public final static int RESQUST_CODE_SELECT_STAGE=390;
    public final static int RESQUST_CODE_SELECT_FLOOR_SCAFFOLD=391;
    private String sendType;

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
        baseQuickAdapter = new BaseQuickAdapter<RiskAreaAndStage, BaseViewHolder>
                (R.layout.checklist_childitem, null) {


            @Override
            protected void convert(BaseViewHolder helper, final RiskAreaAndStage item) {
                if("Area".equals(sendType)){  //施工区域
                    helper.setText(R.id.checklist_childitem_tv,item.getRegionName());
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<RiskAreaAndStage> list = item.getRiskAreaList();
                            strs.add(item.getRegionName());
                            ids.add(item.getId());
                            pjlists.add(list);
                            pjs.add(item);
                            if (list !=null&& list.size()>0){
                                activitySelectProjectTvTitle.setText(getstr(strs));
                                replaceData(list);
                            }else{
                                Intent data = new Intent();
                                data.putExtra("RiskAreaAndStage", item);
                                data.putExtra("strs",getstr(strs));
                                data.putExtra("ids",getstr(ids));
                                setResult(RESULT_OK, data);
                                finish();
                            }
                        }
                    });
                }else if("Stage".equals(sendType)){ //施工阶段
                    helper.setText(R.id.checklist_childitem_tv,item.getDicName());
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            List<RiskAreaAndStage> list = item.getRiskAreaList();
                            strs.add(item.getDicName());
                            ids.add(item.getDicID());
                            pjlists.add(list);
                            pjs.add(item);
                            if (list !=null&& list.size()>0){
                                activitySelectProjectTvTitle.setText(getstr(strs));
                                replaceData(list);
                            }else{
                                Intent data = new Intent();
                                data.putExtra("RiskAreaAndStage", item);
                                data.putExtra("strs",getstr(strs));
                                data.putExtra("ids",getstr(ids));
                                setResult(RESULT_OK, data);
                                finish();
                            }
                        }
                    });
                }

            }
        };
        activitySelectProjectRecyclerView.setAdapter(baseQuickAdapter);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        activitySelectProjectTvTitle.setOnClickListener(
                new View.OnClickListener() {
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
        ArrayList<RiskAreaAndStage> parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("list");
        sendType = getIntent().getStringExtra("sendType");
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
}
