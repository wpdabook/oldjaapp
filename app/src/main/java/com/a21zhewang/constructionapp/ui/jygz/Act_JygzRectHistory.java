package com.a21zhewang.constructionapp.ui.jygz;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.JygzRectAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.AppUtils;
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

/**
 * Created by WP-PC on 2020/3/11.
 */

public class Act_JygzRectHistory extends BaseActivity{
    @BindView(R.id.rv_pic_list)
    ListView mListView;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private JygzRectAdapter mAdapter;
    private List<NineGridModel> mList;
    private List<String> imglist;
    private String id = "";
    private String detailId = "";
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_jygzrecthistory_layout;
    }

    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
        detailId = getIntent().getStringExtra("detailId");
        mAdapter = new JygzRectAdapter(this);
    }


    @Override
    public void initData() {
        loadData();
    }
    /**
     * 1:近一周 2：近一月
     */
    private void loadData() {
        JSONObject object = JsonUtils.getjsonobj("{\"id\":\"" + id + "\",\"detailId\":\"" + detailId + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    mList = new ArrayList<NineGridModel>();
                    for (int i=0;i<array.length();i++){
                        imglist = new ArrayList<String>();
                        NineGridModel model = new NineGridModel();
                        JSONObject tempobject = array.optJSONObject(i);
                        model.setContent("整改内容："+tempobject.optString("remark"));
                        model.setCreateTime(tempobject.optString("createDate"));
                        JSONArray fileArray = tempobject.optJSONArray("fileInfo1");
                        for(int j=0;j<fileArray.length();j++){
                            JSONObject object1 = fileArray.optJSONObject(j);
                            imglist.add(object1.optString("url"));
                            model.setUrlList(imglist);
                        }
                        mList.add(model);
                    }
                    if (array.length() == 0) {
                        placeHolder.setVisibility(View.VISIBLE);
                    } else {
                        placeHolder.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.setList(mList);
                mListView.setAdapter(mAdapter);
                if(mAdapter != null){
                    mAdapter.notifyDataSetChanged();
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
            XUtil.postjsondata(object, "GetBroadCastNoticeRectList", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeRectList", callback);
        }
    }
    @Override
    public void initListener() {

    }
    @Override
    public void processClick(View v) {

    }
}
