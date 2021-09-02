package com.a21zhewang.constructionapp.ui.jdsb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.Dic;
import com.a21zhewang.constructionapp.bean.DicBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.ProjectPhase;
import com.a21zhewang.constructionapp.bean.ProjectRoot;
import com.a21zhewang.constructionapp.customview.TimeTextView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.SelectProjectActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ljw.custompop.CustomPop;
import com.ljw.customseekbar.CustomSeekBar;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ADDJDlistActivity extends BaseActivity {

    @BindView(R.id.tv_pj)
    TextView tvPj;
    @BindView(R.id.tv_jd)
    TextView tvJd;
    @BindView(R.id.tv_jhks)
    TimeTextView tvJhks;
    @BindView(R.id.tv_jhjs)
    TimeTextView tvJhjs;
    @BindView(R.id.tv_sjks)
    TimeTextView tvSjks;
    @BindView(R.id.tv_mqjz)
    TimeTextView tvMqjz;
    @BindView(R.id.csb_sjwc)
    CustomSeekBar csbSjwc;
    @BindView(R.id.ed_qksm)
    EditText edQksm;
    @BindView(R.id.btn_save)
    Button btnSave;

    private CustomPop popWindow;
    private BaseQuickAdapter<Dic, BaseViewHolder> ckAdapter;
    private Dic jdDic;
    private String pos;

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
        return R.layout.activity_addjdlist;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        Intent intent = getIntent();
        tvPj.setText( intent.getStringExtra("pjname"));
        pos = intent.getStringExtra("position");
        ProjectPhase projectPhase= intent.getParcelableExtra("bean");
        if (!TextUtils.isEmpty(pos)&&projectPhase!=null){
          initset(projectPhase);
        }

        ckAdapter = new BaseQuickAdapter<Dic, BaseViewHolder>(R.layout.spinner_item, null) {
            @Override
            protected void convert(BaseViewHolder helper, Dic item) {
                TextView textView = (TextView) helper.itemView;
                textView.setText(item.getDicName());
            }


        };
        ckAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                jdDic = ckAdapter.getItem(position);
                tvJd.setText(jdDic.getDicName()
                );
                popWindow.dissmiss();
            }
        });
        popWindow = new CustomPop.PopupWindowBuilder(this)
                .getRecyclerView(ckAdapter, new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.WHITE)
                        .sizeResId(R.dimen.y1)
                        .marginResId(R.dimen.x5, R.dimen.x5).build())
                .size(getResources().getDimensionPixelSize(R.dimen.x600), 400) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .setBgDrawable()
                .create();
        tvJd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.showAsDropDown(v);
            }
        });
    }

    private void initset(ProjectPhase projectPhase) {
        jdDic=new Dic(projectPhase.getDicID(),projectPhase.getDicName());
        tvJd.setText(jdDic.getDicName());
        tvJhks.setText(projectPhase.getPlanStartDate());
        tvJhjs.setText(projectPhase.getPlanEndDate());
        tvSjks.setText(projectPhase.getActuallyStartDate());
        tvMqjz.setText(projectPhase.getActuallyEndDate());
        csbSjwc.setPosstion(Float.parseFloat(projectPhase.getCompletionRate().replace("%","")));
        edQksm.setText(projectPhase.getRecordDesc());
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {


    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (check()){
                ProjectPhase projectPhase=new ProjectPhase();
                projectPhase.setDicID(jdDic.getDicID());
                projectPhase.setDicName(jdDic.getDicName());
                projectPhase.setSelfIndex(jdDic.getSelfIndex());
                projectPhase.setPlanStartDate(tvJhks.getText().toString());
                projectPhase.setPlanEndDate(tvJhjs.getText().toString());
                projectPhase.setActuallyStartDate(tvSjks.getText().toString());
                projectPhase.setActuallyEndDate(tvMqjz.getText().toString());
                int posstion = (int) csbSjwc.getPosstion();
                projectPhase.setCompletionRate(posstion+"%");
                projectPhase.setRecordDesc(edQksm.getText().toString());
                projectPhase.setLaggingdays(projectPhase.getLaggingdays());
                Intent intent = new Intent();
                if (!TextUtils.isEmpty(pos)){
                    intent.putExtra("pos",pos);
                }
                intent.putExtra("data",projectPhase);
                setResult(RESULT_OK,intent);
            finish();
        }
        }
    });
}




    private boolean check(){
        if (jdDic==null){
            PublicUtils.toast("请选择阶段");
            return false;
        }
        if (TimeUtils.lessThan(tvJhks.getText().toString(),tvJhjs.getText().toString())){
            PublicUtils.toast("计划结束时间不能小于开始时间");
            return false;
        }
        if (TimeUtils.lessThan(tvSjks.getText().toString(),tvMqjz.getText().toString())){
            PublicUtils.toast("计划截止时间不能小于实际开始时间");
            return false;
        }


        return  true;
    }
    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        //获取
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetAllProjectPhase", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray itemList = jsonObject.getJSONArray("checkItemList");
                    List<Dic> dicBeans = JsonUtils.jsonToArrayList(itemList.toString(), Dic.class);
                    ckAdapter.replaceData(dicBeans);
                } catch (JSONException e) {
                    //  e.printStackTrace();
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

    }


}
