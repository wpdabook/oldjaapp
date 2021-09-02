package com.a21zhewang.constructionapp.ui.inspection;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.InspectionOneLevel;
import com.a21zhewang.constructionapp.bean.InspectionThreeLevel;
import com.a21zhewang.constructionapp.bean.InspectionTwoLevel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import butterknife.BindView;

/**
 * 选择问题类别
 * Created by WP-PC on 2020/3/10.
 */

public class Act_CheckTabList extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_righttextview)
    TextView righttext;
    @BindView(R.id.title_back_img)
    ImageView back;
    public final static int RESQUST_PROBLEM_TABLE_TYPE=389;  //项目
    @BindView(R.id.tablelistview)
    ListView listview;
    private MyBaseAdapter adapter;
    //private JSONArray dataArray = new JSONArray();
    private String projectID="";
    private String dicId = "";
    private String dicName = "";
    private String fatherId="";
    private List<InspectionOneLevel> tabList = new ArrayList<>();

    private ArrayList<InspectionOneLevel> onegroup;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_checktable_list_layout;
    }

    @Override
    public void initViews() {
        projectID = getIntent().getStringExtra("projectID");
        righttext.setTextSize(15);
        onegroup = new ArrayList<InspectionOneLevel>();
        righttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onegroup.size()>0){
                    Intent data = new Intent();
                    data.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) onegroup);
                    setResult(RESULT_OK, data);
                    Act_CheckTabList.this.finish();
                }else {
                    showToast("请选择检查内容");
                }

            }
        });
        adapter = new MyBaseAdapter() {
            @Override
            public int getCount() {
                return tabList.size();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                //final JSONObject object = dataArray.optJSONObject(position);
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.problem_item, null);
                }
                TextView text = MViewHolder.get(view, R.id.tvTitle);
                TextView remind = MViewHolder.get(view,R.id.item_select_number_text);

                //text.setText(object.optString("dicName"));
                text.setText(tabList.get(position).getDicName());
                if(tabList.get(position).getItemCount() == 0){
                    remind.setVisibility(View.GONE);
                }else {
                    remind.setVisibility(View.VISIBLE);
                    remind.setText("已选择"+tabList.get(position).getItemCount()+"项");
                }
                view.setTag(R.id.tag_first, tabList);
                return view;
            }
        };
        listview.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //final JSONObject object = dataArray.optJSONObject(position);
                dicId = tabList.get(position).getDicId();
                dicName = tabList.get(position).getDicName();
                fatherId = tabList.get(position).getFatherId();
                startActivityForResult(new Intent(Act_CheckTabList.this,Act_CheckGroupItem.class)
                                .putExtra("projectID",projectID)
                                .putExtra("dicId",dicId)
                                .putExtra("fatherId",fatherId)
                                //.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) tabList)
                        ,Act_CheckGroupItem.RESQUST_CODE_SELECT_CONTENT);
            }
        });
    }

    @Override
    public void initData() {
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"parentCheckItem\",\"projectID\":\"" + projectID + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try{
                    //dataArray = new JSONArray(res);
                    tabList = JsonUtils.jsonToList(res,InspectionOneLevel[].class);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
        }

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Act_CheckGroupItem.RESQUST_CODE_SELECT_CONTENT
                && resultCode == RESULT_OK
                && data != null) {
            ArrayList<InspectionTwoLevel> twogroup = data.getParcelableArrayListExtra("list");
            InspectionOneLevel oneLevel = new InspectionOneLevel(dicId, dicName,fatherId);
            int itemCount =0;
            for(int i=0;i<twogroup.size();i++){
                int has =0;
                InspectionTwoLevel twolevel = new InspectionTwoLevel(twogroup.get(i).getDicId(), twogroup.get(i).getDicName());
                ArrayList<InspectionThreeLevel> threelist = twogroup.get(i).getChild();
//                for(int j=0;j<threelist.size();j++){
//                    if(threelist.get(j).getChecked() == true){  //第三级子项选中
//                        has =1;
//                        itemCount++;
//                    }
//                }
                //迭代器删除未勾选的检查项，只保留勾选
                Iterator<InspectionThreeLevel> iterator = threelist.iterator();
                while (iterator.hasNext()) {
                    InspectionThreeLevel s = iterator.next();
                    if(s.getChecked() == true){
                        has =1;
                        itemCount++;
                    }else {
                        iterator.remove();
                    }
                }
                if(has==1){
                    twolevel.setChild(twogroup.get(i).getChild());
                    oneLevel.addChildItem(twolevel);
                    oneLevel.setItemCount(oneLevel.getChild().size());
                    oneLevel.setItemCount(itemCount);
                }
            }
//            for(int i=0;i<twogroup.size();i++){
//                InspectionTwoLevel twolevel = new InspectionTwoLevel(twogroup.get(i).getDicId(), twogroup.get(i).getDicName());
//                for(int j=0;j<twogroup.get(i).getChild().size();j++){
//                    InspectionThreeLevel child = twogroup.get(i).getChild().get(j);
//                    if(twogroup.get(i).getChild().get(j).getChecked()){
//                                child.setChecked(true);
//                                twolevel.setChecked(true);
//                                //twolevel.addChildItem(child);
//                                oneLevel.setChecked(true);
//                                oneLevel.addChildItem(twolevel);
//                        }
//                }
            onegroup.add(oneLevel);
            for(int i=0;i<onegroup.size();i++){
                /**去除相同选项，保留最后一次记录*/
                for (int j = i + 1; j < onegroup.size(); j++) {
                    if (onegroup.get(i).getDicId().equals(onegroup.get(j).getDicId())) {
                        onegroup.remove(i);
                    }
                }
            }
            for(int i=0;i<onegroup.size();i++){
                String selectDicID = onegroup.get(i).getDicId();
                int count = onegroup.get(i).getItemCount();
                for(int j=0;j<tabList.size();j++){
                    String AllDicID = tabList.get(j).getDicId();
                    if(AllDicID.equals(selectDicID)){
                        tabList.get(j).setItemCount(count);
                    }
                }

            }
            adapter.notifyDataSetChanged();
        }
    }

}
