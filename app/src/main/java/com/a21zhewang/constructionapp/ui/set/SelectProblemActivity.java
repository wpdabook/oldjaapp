package com.a21zhewang.constructionapp.ui.set;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyCompanyAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RiskTypeBean;
import com.a21zhewang.constructionapp.ui.risk.Act_SelectRiskType;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 选择问题类别
 * Created by WP-PC on 2020/3/10.
 */

public class SelectProblemActivity extends BaseActivity{
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    public final static int RESQUST_PROBLEM_TYPE=289;  //项目
    @BindView(R.id.seListView)
    ListView listview;
    private MyBaseAdapter adapter;
    private JSONArray dataArray = new JSONArray();
    private List<Map<String,String>> typelist = new ArrayList<Map<String, String>>();
    private String type = "1";//1.反馈建议分类 2.视频分类
    private String StrTitle = "";
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.select_problem_type_layout;
    }

    @Override
    public void initViews() {
              type = getIntent().getStringExtra("type");
              StrTitle = getIntent().getStringExtra("title");
              title.setText(StrTitle);
              adapter = new MyBaseAdapter() {
                  @Override
                  public int getCount() {
                      return dataArray.length();
                  }

                  @Override
                  public View getView(int position, View view, ViewGroup parent) {
                      final JSONObject object = dataArray.optJSONObject(position);
                      if (view == null) {
                          view = getLayoutInflater().inflate(R.layout.problem_item, null);
                      }
                      TextView text = MViewHolder.get(view, R.id.tvTitle);
                      text.setText(object.optString("value"));
                      view.setTag(R.id.tag_first, object);
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
                final JSONObject object = dataArray.optJSONObject(position);
                //1.反馈建议分类 2.视频分类
                if("1".equals(type)){
                    Intent data = new Intent();
                    data.putExtra("typeName",object.optString("value"));
                    data.putExtra("type",object.optString("key"));
                    setResult(RESULT_OK, data);
                    finish();
                }else {
                    startActivity(new Intent(SelectProblemActivity.this,HelpVideoActivity.class).putExtra("type",object.optString("key")).putExtra("title",object.optString("value")));
                }
                }
        });
    }
    @Override
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + type + "\"}"), "getHelpingModulesType", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try{
                    dataArray = new JSONArray(res);
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
        });
    }

    @Override
    public void processClick(View v) {

    }
}
