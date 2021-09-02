package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.a21zhewang.constructionapp.adapter.MyTabRiskAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import static android.app.Activity.RESULT_OK;
import com.a21zhewang.constructionapp.bean.RecordProjectList;
import com.a21zhewang.constructionapp.bean.RiskProjectListBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.constructionapp.bean.ObjBean;

/**
 * Created by WP-PC on 2019/5/30.
 */

public class Act_SelectRiskProject extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    @BindView(R.id.lvListView)
    ListView listview;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    public final static int RESQUST_CODE_SELECT_NOTICE_COMPANY = 518;
    /*执法类别*/
    private RiskProjectListBean riskProjectListBean;
    private List<RecordProjectList> recordProjectList = new ArrayList<>();
    private MyTabRiskAdapter adapter = null;
    private StringBuffer sb;
    private String projectId;

    @Override
    public int getLayoutId() {
        return R.layout.act_select_risk_project;
    }

    @Override
    public void initViews() {
        projectId = getIntent().getStringExtra("projectId");
        titleBarRighttext.setVisibility(View.VISIBLE);
        titleBarRighttext.setTextSize(16);
        titleBarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuffer();
                for(int i=0;i<recordProjectList.size();i++){
                    if(recordProjectList.get(i).isChecked() == true){
                        sb.append(recordProjectList.get(i).getProjectName()+"，");
                    }
                }
                Intent data = new Intent();
                data.putExtra("strs",sb.toString());
                data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) recordProjectList);
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
                if (view.getTag() instanceof MyTabRiskAdapter.ViewHolder) {
                    MyTabRiskAdapter.ViewHolder viewHolder = (MyTabRiskAdapter.ViewHolder) view.getTag();
                    if (viewHolder.cbCheck.isChecked()) {
                        viewHolder.cbCheck.setChecked(false);
                        recordProjectList.get(position).setChecked(false);//改变状态并保存下来
                    }
                    else {
                        viewHolder.cbCheck.setChecked(true);
                        recordProjectList.get(position).setChecked(true);
                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetProjectListByUserId", new MyCallBack() {
            @Override
            public void onResule(String res) {
                riskProjectListBean = JsonUtils.getbean(res,RiskProjectListBean.class);
                recordProjectList = riskProjectListBean.getRecordList();
                adapter = new MyTabRiskAdapter(Act_SelectRiskProject.this, recordProjectList);
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
        if (view.getTag() instanceof MyTabRiskAdapter.ViewHolder) {
            MyTabRiskAdapter.ViewHolder viewHolder = (MyTabRiskAdapter.ViewHolder) view.getTag();
            if (viewHolder.cbCheck.isChecked()) {
                viewHolder.cbCheck.setChecked(false);
                recordProjectList.get(position).setChecked(false);//改变状态并保存下来
            }
            else {
                viewHolder.cbCheck.setChecked(true);
                recordProjectList.get(position).setChecked(true);
            }
            // 会自动出发CheckBox的checked事件
            viewHolder.cbCheck.toggle();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforesetContentView() {

    }
}
