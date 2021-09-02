package com.a21zhewang.constructionapp.ui.risk;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyCompanyAdapter;
import com.a21zhewang.constructionapp.adapter.MySubdivisionAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ItemRiskList;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskSubdivisionBean;
import com.a21zhewang.constructionapp.bean.RiskTypeBean;
import com.a21zhewang.constructionapp.bean.RiskTypeInfo;
import com.a21zhewang.constructionapp.bean.SubdivisionItem;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2021/4/25.
 */

public class Act_SubdivisionType extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener  {

    @BindView(R.id.lvListView)
    ListView listview;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    public final static int RESQUST_CODE_SELECT_SUBDIVISIONTYPE = 5209;
    /*执法类别*/
    private MySubdivisionAdapter adapter = null;
    private StringBuffer sb;
    private List<SubdivisionItem> subdivisionItems;
    private List<RiskSubdivisionBean> riskSubdivisionBeanList = new ArrayList<>();
    private  String dicId,temp_dicId;
    private int has;
    private String projectId;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_subdivision_type;
    }

    @Override
    public void initViews() {
        subdivisionItems = getIntent().getParcelableArrayListExtra("subdivisionItems");
        projectId = getIntent().getStringExtra("projectId");
        titleBarRighttext.setVisibility(View.VISIBLE);
        titleBarRighttext.setTextSize(16);
        titleBarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuffer();
                for(int i=0;i<riskSubdivisionBeanList.size();i++){
                    if(riskSubdivisionBeanList.get(i).isChecked() == true){
                        sb.append(riskSubdivisionBeanList.get(i).getDicName()+"，");
                    }
                }
                Intent data = new Intent();
                if(sb != null && sb.length()>0){
                    data.putExtra("strs",sb.deleteCharAt(sb.length() - 1).toString());
                }
                data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) riskSubdivisionBeanList);
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
                if (view.getTag() instanceof MySubdivisionAdapter.ViewHolder) {
                    MySubdivisionAdapter.ViewHolder viewHolder = (MySubdivisionAdapter.ViewHolder) view.getTag();
                    if (viewHolder.cbCheck.isChecked()) {
                        viewHolder.cbCheck.setChecked(false);
                        riskSubdivisionBeanList.get(position).setChecked(false);//改变状态并保存下来
                    }
                    else {
                        viewHolder.cbCheck.setChecked(true);
                        riskSubdivisionBeanList.get(position).setChecked(true);

                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"division\",\"projectId\":\"" + projectId + "\"}"), "GetRiskReportTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject object = array.optJSONObject(i);
                        RiskSubdivisionBean riskSubdivisionBean = new RiskSubdivisionBean();
                        riskSubdivisionBean.setDicId(object.optString("dicId"));
                        riskSubdivisionBean.setDicName(object.optString("dicName"));
                        riskSubdivisionBeanList.add(riskSubdivisionBean);
                    }
                    adapter = new MySubdivisionAdapter(Act_SubdivisionType.this, riskSubdivisionBeanList);
                    /***================= 默认勾选最后一次风险选择项===============================================*/
                    if(subdivisionItems != null){
                        for(int j=0;j<subdivisionItems.size();j++){
                            temp_dicId = subdivisionItems.get(j).getDivisionId();
                            for(int i=0;i<riskSubdivisionBeanList.size();i++){
                                dicId = riskSubdivisionBeanList.get(i).getDicId();
                                if(temp_dicId.equals(dicId)){
                                    has = 1;
                                    riskSubdivisionBeanList.get(i).setChecked(true);
                                }
                            }
                        }
                        adapter.setOnChildItemLongClickListener(new MySubdivisionAdapter.OnChildItemLongClickListener() {
                            @Override
                            public void onChildItemLongClick(CheckBox box, int Position) {
                                if(riskSubdivisionBeanList.get(Position).isChecked() == true){
                                    box.setChecked(true);
                                }else {
                                    box.setChecked(false);
                                }
                            }
                        });
                    }
                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public void processClick(View v) {

    }
}
