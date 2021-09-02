package com.a21zhewang.constructionapp.ui.set;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.PlayInfoAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.PlayInfo;
import com.a21zhewang.constructionapp.bean.PlayUserList;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.xmxx.Act_SearchProject;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/11/23.
 */

public class PeopleInfoActivity extends BaseActivity {
    @BindView(R.id.li_project_name)
    LinearLayout li_project_name;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.rg)
    RadioGroup radioGroup;
    @BindView(R.id.project_name)
    TextView name;
    @BindView(R.id.expand_list)
    ExpandableListView expand_list;
    private int type = 1;
    private String projectId;
    private PlayInfo playInfo;
    private List<PlayInfo> playlist;
    private PlayInfoAdapter adapter;
    private String projectName;

    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_peopleinfo;
    }

    @Override
    public void initViews() {
        expand_list.setGroupIndicator(null);
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
        initProject();
    }
    private void getProject(String projectID, int type) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectId\":\"" + projectID + "\",\"unitType\":\"" + type + "\"}"),
                "getPerCenterIsCheck", new MyCallBack() {
                    @Override
                    public void onResule(String res) {
//                        res = "[\n" +
//                                "    {\n" +
//                                "        \"etpId\": \"QY201709211526\",\n" +
//                                "        \"etpFullName\": \"武汉鸿诚工程咨询管理有限责任公司\",\n" +
//                                "        \"userList\": [\n" +
//                                "            {\n" +
//                                "                \"userId\": \"15387088305\",\n" +
//                                "                \"userName\": \"刘雨晨\",\n" +
//                                "                \"isKey\": 1,\n" +
//                                "                \"isProg\": 0\n" +
//                                "            },\n" +
//                                "            {\n" +
//                                "                \"userId\": \"18995584201\",\n" +
//                                "                \"userName\": \"陈思\",\n" +
//                                "                \"isKey\": 1,\n" +
//                                "                \"isProg\": 0\n" +
//                                "            }\n" +
//                                "        ]\n" +
//                                "    },\n" +
//                                "    {\n" +
//                                "        \"etpId\": \"QY201709211526\",\n" +
//                                "        \"etpFullName\": \"武汉鸿诚工程咨询管理有限责任公司2\",\n" +
//                                "        \"userList\": [\n" +
//                                "            {\n" +
//                                "                \"userId\": \"15387088305\",\n" +
//                                "                \"userName\": \"刘雨晨2\",\n" +
//                                "                \"isKey\": 1,\n" +
//                                "                \"isProg\": 0\n" +
//                                "            },\n" +
//                                "            {\n" +
//                                "                \"userId\": \"18995584201\",\n" +
//                                "                \"userName\": \"陈思2\",\n" +
//                                "                \"isKey\": 1,\n" +
//                                "                \"isProg\": 0\n" +
//                                "            }\n" +
//                                "        ]\n" +
//                                "    }\n" +
//                                "]";
                        try {
                            playlist = new ArrayList<>();
                            JSONArray temparray = new JSONArray(res);
                            for(int i=0;i<temparray.length();i++){
                                JSONObject object = temparray.optJSONObject(i);
                                playInfo =  JsonUtils.getbean(object.toString(),PlayInfo.class);
                                playlist.add(playInfo);
                            }
                             if(TextUtils.isEmpty(projectName)){
                                 li_project_name.setVisibility(View.GONE);
                             }else {
                                li_project_name.setVisibility(View.VISIBLE);
                                 name.setText(projectName);
                             }
                            adapter= new PlayInfoAdapter(PeopleInfoActivity.this,playlist);
                            expand_list.setAdapter(adapter);
                            int groupCount = expand_list.getCount();
                            for (int i = 0; i < groupCount; i++) {
                                expand_list.expandGroup(i);
                            }
                            if (playlist.size() > 0) {
                                adapter.notifyDataSetChanged();
                            }
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
    public void initProject(){
        JSONObject object = JsonUtils.getjsonobj("{\"etpID\":\"" + "" + "\"," + "\"pageIndex\":\"" + 0  + "\"," +
                "\"pageSize\":\"" + 10 + "\",\"projectName\":\"" + "" + "\"}");
        XUtil.postjsondata(object, "GetProjectList", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("recordList");
                    JSONObject tempobj = array.optJSONObject(0);
                    projectId = tempobj.optString("projectID");
                    projectName = tempobj.optString("projectName");
//                    name.setText(projectName);
                    getProject(projectId,type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onFinished() {
                super.onFinished();
            }

            @Override
            public void onWrong() {

            }
        });
    }
    @OnClick({R.id.img_search
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                startActivityForResult(new Intent(PeopleInfoActivity.this,Act_SearchProject.class),Act_SearchProject.RESQUST_CODE_SELECT_TYPE);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Act_SearchProject.RESQUST_CODE_SELECT_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            projectId = data.getStringExtra("projectId");
            projectName = data.getStringExtra("projectName");
            getProject(projectId,type);
        }
    }
    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }
}
