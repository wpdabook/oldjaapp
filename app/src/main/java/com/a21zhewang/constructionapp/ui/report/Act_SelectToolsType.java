package com.a21zhewang.constructionapp.ui.report;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyMachineryAdapter;
import com.a21zhewang.constructionapp.adapter.MyScaffoldAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.MachineryData;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ReportDate;
import com.a21zhewang.constructionapp.bean.ScaffoldData;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 风险上报-添加-选择风险类型
 * Created by WP-PC on 2019/5/30.
 */

public class Act_SelectToolsType extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    @BindView(R.id.lvListView)
    ListView listview;
    @BindView(R.id.title_righttextview)
    TextView titleBarRighttext;
    public final static int RESQUST_CODE_SELECT_FLOOR_SCAFFOLD = 567;
    public final static int RESQUST_CODE_SELECT_MACHINERY = 568;
    private MyScaffoldAdapter adapter1 = null;
    private MyMachineryAdapter adapter2 = null;
    private StringBuffer sb1,sb2;
    private  String dicId,temp_dicId1,temp_dicId2;
    private int has;
    private String sendType;
    private ReportDate reportDate;
    private List<ScaffoldData> reportItemLists1;
    private List<MachineryData> reportItemLists2;
    private List<ScaffoldData> reportScaffoldDataList;
    private List<MachineryData> reportMachineryDataList;
    private String ConstructionDicID;
    @Override
    public void beforesetContentView() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.act_selecttools_type_layout;
    }

    @Override
    public void initViews() {
        sendType = getIntent().getStringExtra("sendType");
        ConstructionDicID = getIntent().getStringExtra("ConstructionDicID");
        if("scaffo".equals(sendType)){
            reportItemLists1 = getIntent().getParcelableArrayListExtra("list");
        }
        if("machinery".equals(sendType)){
            reportItemLists2 = getIntent().getParcelableArrayListExtra("list");
        }
        titleBarRighttext.setVisibility(View.VISIBLE);
        titleBarRighttext.setTextSize(16);
        titleBarRighttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("scaffo".equals(sendType)){
                    sb1 = new StringBuffer();
                    sb2 = new StringBuffer();
                    for(int i=0;i<reportScaffoldDataList.size();i++){
                        if(reportScaffoldDataList.get(i).isChecked() == true){
                            sb1.append(reportScaffoldDataList.get(i).getConfigName()+"|");
                            sb2.append(reportScaffoldDataList.get(i).getConfigName()+"，");
                        }
                    }
                    Intent data = new Intent();
                    if(sb1 != null && sb1.length()>0){
                        data.putExtra("strs1",sb1.deleteCharAt(sb1.length() - 1).toString());
                    }
                    if(sb2 != null && sb2.length()>0){
                        data.putExtra("strs2",sb2.deleteCharAt(sb2.length() - 1).toString());
                    }
                    data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) reportScaffoldDataList);
                    setResult(RESULT_OK, data);
                    finish();
                }
                if("machinery".equals(sendType)){
                    sb1 = new StringBuffer();
                    sb2 = new StringBuffer();
                    for(int i=0;i<reportMachineryDataList.size();i++){
                        if(reportMachineryDataList.get(i).isChecked() == true){
                            sb1.append(reportMachineryDataList.get(i).getConfigName()+"|");
                            sb2.append(reportMachineryDataList.get(i).getConfigName()+"，");
                        }
                    }
                    Intent data = new Intent();
                    data.putExtra("strs1",sb1.deleteCharAt(sb1.length() - 1).toString());
                    data.putExtra("strs2",sb2.deleteCharAt(sb2.length() - 1).toString());
                    data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) reportMachineryDataList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("scaffo".equals(sendType)){
                    if (view.getTag() instanceof MyScaffoldAdapter.ViewHolder) {
                        MyScaffoldAdapter.ViewHolder viewHolder = (MyScaffoldAdapter.ViewHolder) view.getTag();
                        if (viewHolder.cbCheck.isChecked()) {
                            viewHolder.cbCheck.setChecked(false);
                            reportScaffoldDataList.get(position).setChecked(false);//改变状态并保存下来
                        }
                        else {
                            viewHolder.cbCheck.setChecked(true);
                            reportScaffoldDataList.get(position).setChecked(true);
                        }
                    }
                }
                if("machinery".equals(sendType)){
                    if (view.getTag() instanceof MyMachineryAdapter.ViewHolder) {
                        MyMachineryAdapter.ViewHolder viewHolder = (MyMachineryAdapter.ViewHolder) view.getTag();
                        if (viewHolder.cbCheck.isChecked()) {
                            viewHolder.cbCheck.setChecked(false);
                            reportMachineryDataList.get(position).setChecked(false);//改变状态并保存下来
                        }
                        else {
                            viewHolder.cbCheck.setChecked(true);
                            reportMachineryDataList.get(position).setChecked(true);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void initData() {
        getDicIDate(ConstructionDicID);
    }
    /**
     * 根据施工阶段DicID获取对应脚手架和机械设备列表
     */
    public void getDicIDate(String constructionDicID){
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"ConstructionDicID\":\"" + constructionDicID + "\"}"), "GetScaffoldAndMachineryByCons", new MyCallBack() {
            @Override
            public void onResule(String res) {
                reportDate = JsonUtils.getbean(res,ReportDate.class);
                reportScaffoldDataList = reportDate.getScaffoldData();
                reportMachineryDataList = reportDate.getMachineryData();
                if("scaffo".equals(sendType)){
                    adapter1 = new MyScaffoldAdapter(Act_SelectToolsType.this, reportScaffoldDataList);
                    if(reportItemLists1 != null){
                        for(int j=0;j<reportItemLists1.size();j++){
                            temp_dicId1 = reportItemLists1.get(j).getCID();
                            for(int i=0;i<reportScaffoldDataList.size();i++){
                                dicId = reportScaffoldDataList.get(i).getCID();
                                if(temp_dicId1.equals(dicId)){
                                    has = 1;
                                    reportScaffoldDataList.get(i).setChecked(true);
                                }
                            }
                        }
                        adapter1.setOnChildItemLongClickListener(new MyScaffoldAdapter.OnChildItemLongClickListener() {
                            @Override
                            public void onChildItemLongClick(CheckBox box, int Position) {
                                if(reportScaffoldDataList.get(Position).isChecked() == true){
                                    box.setChecked(true);
                                }else {
                                    box.setChecked(false);
                                }
                            }
                        });
                     }
                    listview.setAdapter(adapter1);
                }
                if("machinery".equals(sendType)){
                    adapter2 = new MyMachineryAdapter(Act_SelectToolsType.this, reportMachineryDataList);
                    if(reportItemLists2 != null){
                        for(int j=0;j<reportItemLists2.size();j++){
                            temp_dicId2 = reportItemLists2.get(j).getCID();
                            for(int i=0;i<reportMachineryDataList.size();i++){
                                dicId = reportMachineryDataList.get(i).getCID();
                                if(temp_dicId2.equals(dicId)){
                                    has = 1;
                                    reportMachineryDataList.get(i).setChecked(true);
                                }
                            }
                        }
                        adapter2.setOnChildItemLongClickListener(new MyMachineryAdapter.OnChildItemLongClickListener() {
                            @Override
                            public void onChildItemLongClick(CheckBox box, int Position) {
                                if(reportMachineryDataList.get(Position).isChecked() == true){
                                    box.setChecked(true);
                                }else {
                                    box.setChecked(false);
                                }
                            }
                        });
                    }
                    listview.setAdapter(adapter2);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

}
