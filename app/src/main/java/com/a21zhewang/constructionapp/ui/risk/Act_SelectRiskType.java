package com.a21zhewang.constructionapp.ui.risk;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyCompanyAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ItemRiskList;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskTypeBean;
import com.a21zhewang.constructionapp.bean.RiskTypeInfo;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;

/**
 * 风险上报-添加-选择风险类型
 * Created by WP-PC on 2019/5/30.
 */

public class Act_SelectRiskType extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    @BindView(R.id.lvListView)
    ListView listview;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    public final static int RESQUST_CODE_SELECT_RISK_TYPE = 529;
    public final static int RESQUST_CODE_SELECT_FLOOR_SCAFFOLD = 567;
    public final static int RESQUST_CODE_SELECT_MACHINERY = 568;
    /*执法类别*/
    private RiskTypeBean riskTypeBean;
    private List<RiskTypeInfo> riskTypelist = new ArrayList<>();
    private MyCompanyAdapter adapter = null;
    private StringBuffer sb;
    private List<ItemRiskList> itemRiskLists;
    private  String dicId,temp_dicId;
    private int has;
    private String projectId;
    @Override
    public void beforesetContentView() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.act_selectrisktype;
    }

    @Override
    public void initViews() {
        itemRiskLists = getIntent().getParcelableArrayListExtra("itemRiskLists");
        projectId = getIntent().getStringExtra("projectId");
        titleBarRighttext.setVisibility(View.VISIBLE);
        titleBarRighttext.setTextSize(16);
        titleBarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuffer();
                for(int i=0;i<riskTypelist.size();i++){
                    if(riskTypelist.get(i).isChecked() == true){
                        sb.append(riskTypelist.get(i).getDicName()+"，");
                    }
                }
                Intent data = new Intent();
                if(sb != null && sb.length()>0){
                    data.putExtra("strs",sb.deleteCharAt(sb.length() - 1).toString());
                }
                data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) riskTypelist);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() instanceof MyCompanyAdapter.ViewHolder) {
                    MyCompanyAdapter.ViewHolder viewHolder = (MyCompanyAdapter.ViewHolder) view.getTag();
                    if (viewHolder.cbCheck.isChecked()) {
                        viewHolder.cbCheck.setChecked(false);
                        riskTypelist.get(position).setChecked(false);//改变状态并保存下来
                    }
                    else {
                        viewHolder.cbCheck.setChecked(true);
                        riskTypelist.get(position).setChecked(true);

                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"safety\",\"projectId\":\"" + projectId + "\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskTypeBean = JsonUtils.getbean(res,RiskTypeBean.class);
                riskTypelist = riskTypeBean.getBackdata();
                adapter = new MyCompanyAdapter(Act_SelectRiskType.this, riskTypelist);
                /***================= 默认勾选最后一次风险选择项===============================================*/
                if(itemRiskLists != null){
                    for(int j=0;j<itemRiskLists.size();j++){
                        temp_dicId = itemRiskLists.get(j).getDicId();
                        for(int i=0;i<riskTypelist.size();i++){
                            dicId = riskTypelist.get(i).getDicID();
                            if(temp_dicId.equals(dicId)){
                                has = 1;
                                riskTypelist.get(i).setChecked(true);
                            }
                        }
                    }
                    adapter.setOnChildItemLongClickListener(new MyCompanyAdapter.OnChildItemLongClickListener() {
                        @Override
                        public void onChildItemLongClick(CheckBox box, int Position) {
                            if(riskTypelist.get(Position).isChecked() == true){
                                box.setChecked(true);
                            }else {
                                box.setChecked(false);
                            }
                        }
                    });
                }
                /***================= 默认勾选最后一次风险选择项===============================================*/
                listview.setAdapter(adapter);
            }
            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
        });
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

}
