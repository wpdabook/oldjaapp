package com.a21zhewang.constructionapp.ui.set;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.PlayInfoTitleAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.PlayInfoDate;
import com.a21zhewang.constructionapp.bean.PlayUserList;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/11/5.
 */

public class PlayInfoActivity extends BaseActivity {
    @BindView(R.id.li_project_name)
    LinearLayout li_project_name;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.rg)
    RadioGroup radioGroup;
    @BindView(R.id.project_name)
    TextView name;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int type = 1;
    private String projectId;
    private PlayInfoDate playInfoDate;
    private PlayInfoTitleAdapter adapter;
    private List<PlayUserList> playUserLists = new ArrayList<>();
    private List<String> Companys = new ArrayList<>();

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.play_info;
    }

    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,  int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(group.getCheckedRadioButtonId());
                if(radioButton.isChecked()){
                    radioButton.setChecked(true);
                }else {
                    radioButton.setChecked(false);
                }
                String answer = radioButton.getText().toString();
                if("建设单位".equals(answer)){
                    type = 1;
                }
                if("施工单位".equals(answer)){
                    type = 2;
                }
                if("监理单位".equals(answer)){
                    type = 3;
                }
                getProject(projectId,type);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //position: 插入item的位置，注意这里的下标是包含title的（title算一个item，并且所有item随着插入的title的增多而改变），
        //即该position参数可以理解为包含title的所有item中title所处于的插入的位置
//        adapter.addTitle(0, "第一个标题");
//        adapter.addTitle(4, "第二个标题");
//        adapter.addTitle(13, "第三个标题");

    }
    @OnClick({R.id.img_search
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                startActivityForResult(new Intent(PlayInfoActivity.this,Act_SearchProject.class),Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
                break;

        }
    }
    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }
    private void getProject(String projectID, int type) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectID + "\",\"unitType\":\"" + type + "\"}"),
                "getPerCenterIsCheck", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        try {
                            if(playUserLists.size()>0){
                                playUserLists.clear();
                            }
                            if(Companys.size()>0){
                                Companys.clear();
                            }
                            JSONArray array = new JSONArray(res);
                            int index = 0;
                            for(int i=0;i<array.length();i++){
                                index = i;
                                JSONObject object = array.optJSONObject(i);
                                Companys.add(object.optString("etpFullName"));
                                playInfoDate = JsonUtils.getbean(object.toString(),PlayInfoDate.class);
                                playUserLists = playInfoDate.getUserList();
                            }
                            adapter = new PlayInfoTitleAdapter(PlayInfoActivity.this, playUserLists);
                            for(int j=0;j<Companys.size();j++){
                                adapter.addTitle(index, Companys.get(j));
                            }
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Act_SearchProject.RESQUST_CODE_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            projectId = data.getStringExtra("projectId");
            String projectName = data.getStringExtra("projectName");
            if(TextUtils.isEmpty(projectName)){
                li_project_name.setVisibility(View.GONE);
            }else {
                li_project_name.setVisibility(View.VISIBLE);
            }
            name.setText(projectName);
            getProject(projectId,type);
        }
    }
    @Override
    public void processClick(View v) {

    }
}
