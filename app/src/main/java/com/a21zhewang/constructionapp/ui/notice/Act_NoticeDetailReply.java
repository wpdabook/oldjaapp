package com.a21zhewang.constructionapp.ui.notice;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NoticeReplyAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
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
 * Created by WP-PC on 2020/3/11.
 */

public class Act_NoticeDetailReply extends BaseActivity{
    @BindView(R.id.rv_pic_list)
    ListView mListView;
    private JSONArray dataArray = new JSONArray();
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String id;
    private NoticeReplyAdapter mAdapter;
    private List<NineGridModel> mList;
    private String replyUserId;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_notice_detail_reply;
    }

    @Override
    public void initViews() {
        id = getIntent().getStringExtra("id");
        replyUserId = getIntent().getStringExtra("replyUserId");
        mAdapter = new NoticeReplyAdapter(this);
    }

    @Override
    public void initListener() {

    }
    @Override
    public void initData() {
        loadData(true);
    }
    /**
     * 1:近一周 2：近一月
     * @param needDialog
     */
    private void loadData(boolean needDialog) {
        JSONObject object = JsonUtils.getjsonobj("{\"id\":\"" + id + "\"," + "\"replyUserId\":\"" + replyUserId + "\"}");
        XUtil.postjsondata(object, "GetJGNoticeReply", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    mList = new ArrayList<NineGridModel>();
                    if(array.length()>0){
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.optJSONObject(i);
                            NineGridModel model = new NineGridModel();
                            model.setContent(object.optString("content"));
                            model.setCreateTime(object.optString("replyTime"));
                            model.setUser(object.optString("userName"));
                            JSONArray filearray = object.optJSONArray("files");
                            List<String> imglist = new ArrayList<String>();
                            if (filearray != null) {
                                for (int j = 0; j < filearray.length(); j++) {
                                    JSONObject tempobject = filearray.optJSONObject(j);
                                    imglist.add(tempobject.optString("url"));
                                    model.setUrlList(imglist);
                                }
                            }
                            mList.add(model);
                        }
                    }
                    if (dataArray.length() == 0) {
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
        });
    }
    @Override
    public void processClick(View v) {

    }
}
