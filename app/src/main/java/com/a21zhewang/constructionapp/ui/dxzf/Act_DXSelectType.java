package com.a21zhewang.constructionapp.ui.dxzf;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyDXAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.JdzfStatus;
import com.a21zhewang.constructionapp.bean.JdzfType;
import com.a21zhewang.constructionapp.bean.JdzfTypeBean;
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

public class Act_DXSelectType extends BaseActivity {

    @BindView(R.id.lvListView)
    ListView listview;
    public final static int RESQUST_CODE_SELECTTYPE=520;
    /*执法类别*/
    private JdzfTypeBean jdzfTypeBean;
    private List<JdzfStatus> jdzfStatuslist = new ArrayList<>();
    private MyDXAdapter adapter = null;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_dx_selecttype;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent data = new Intent();
                data.putExtra("strs",jdzfStatuslist.get(position).getTypeName());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"lawTypes\"}"), "GetSuperviseManageTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                jdzfTypeBean = JsonUtils.getbean(res,JdzfTypeBean.class);
                jdzfStatuslist = jdzfTypeBean.getBackdata();
                adapter = new MyDXAdapter(Act_DXSelectType.this, jdzfStatuslist);
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
}
