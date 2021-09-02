package com.a21zhewang.constructionapp.ui.dxzf;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyCompanyAdapter;
import com.a21zhewang.constructionapp.adapter.MyDXCompanyAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.JdzfCompanys;
import com.a21zhewang.constructionapp.bean.JdzfCompanysBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 执法监督-发起-选择执法类型
 * Created by WP-PC on 2019/5/30.
 */

public class Act_DXSelectCompany extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    @BindView(R.id.lvListView)
    ListView listview;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    public final static int RESQUST_CODE_SELECTCOMPANY=521;
    /*执法类别*/
    private JdzfCompanysBean jdzfCompanysBean;
    private List<JdzfCompanys> jdzfCompanysList = new ArrayList<>();
    private MyDXCompanyAdapter adapter = null;
    private StringBuffer sb;
    private String projectId;
    @Override
    public void beforesetContentView() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.act_dx_selectcompany;
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
                for(int i=0;i<jdzfCompanysList.size();i++){
                    if(jdzfCompanysList.get(i).isChecked() == true){
                        sb.append(jdzfCompanysList.get(i).getEtpShortName()+"，");
                    }

                }
                Intent data = new Intent();
                if(sb != null && sb.length()>0){
                    data.putExtra("strs",sb.deleteCharAt(sb.length() - 1).toString());
                }
//                data.putExtra("strs",sb.toString());
                data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) jdzfCompanysList);
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
                        jdzfCompanysList.get(position).setChecked(false);//改变状态并保存下来
                    }
                    else {
                        viewHolder.cbCheck.setChecked(true);
                        jdzfCompanysList.get(position).setChecked(true);
                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"companys\",\"projectId\":\"" + projectId + "\"}"), "GetSuperviseManageTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                jdzfCompanysBean = JsonUtils.getbean(res,JdzfCompanysBean.class);
                jdzfCompanysList = jdzfCompanysBean.getBackdata();
                adapter = new MyDXCompanyAdapter(Act_DXSelectCompany.this, jdzfCompanysList);
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
        if (view.getTag() instanceof MyCompanyAdapter.ViewHolder) {
            MyCompanyAdapter.ViewHolder viewHolder = (MyCompanyAdapter.ViewHolder) view.getTag();
            if (viewHolder.cbCheck.isChecked()) {
                viewHolder.cbCheck.setChecked(false);
                jdzfCompanysList.get(position).setChecked(false);//改变状态并保存下来
            }
            else {
                viewHolder.cbCheck.setChecked(true);
                jdzfCompanysList.get(position).setChecked(true);
            }
            // 会自动出发CheckBox的checked事件
            viewHolder.cbCheck.toggle();
        }
    }
}
