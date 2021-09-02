package com.a21zhewang.constructionapp.ui.inspection;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.ProceduralHostoryAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by WP-PC on 2020/3/11.
 */

public class Act_NewInspectionHistory extends BaseActivity{
    @BindView(R.id.rv_pic_list)
    ListView mListView;
    private int page = 0;
    private int totalPage = 1;
    private JSONArray dataArray = new JSONArray();
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private String id;
    private ProceduralHostoryAdapter mAdapter;
    private List<NineGridModel> mList;
    private String recordID;
    private String sectionID;
    private String itemID;
    private Dialog loadingDialog;
    private String recordRLID = "";
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_new_inspection_history;
    }

    @Override
    public void initViews() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
        recordID = getIntent().getStringExtra("recordId");
        sectionID = getIntent().getStringExtra("sectionId");
        itemID = getIntent().getStringExtra("itemID");
        recordRLID = getIntent().getStringExtra("recordRLID");
        mAdapter = new ProceduralHostoryAdapter(this);
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
        loadingDialog.show();
        JSONObject object = JsonUtils.getjsonobj("{\"recordID\":\"" + recordID + "\"," +
                "\"sectionID\":\"" + sectionID + "\",\"itemID\":\"" + itemID + "\",\"recordRLID\":\"" + recordRLID + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    JSONObject object = new JSONObject(res);
                    mList = new ArrayList<NineGridModel>();
                    if(object.length()>0){
                        dataArray = object.optJSONArray("recordList");
                        for (int i = 0; i < dataArray.length(); i++) {//
                            JSONObject tempobject = dataArray.optJSONObject(i);
                            NineGridModel model = new NineGridModel();
                            model.setContent(tempobject.optString("content"));
                            model.setCreateTime(tempobject.optString("createTime"));
                            model.setStatus(tempobject.optInt("type"));
//                            if(tempobject.optInt("type") == 1){
//                                model.setUser("安全记录");
//                            }else{
//                                model.setUser("有隐患记录");
//                            }
                            JSONArray array = tempobject.optJSONArray("files");
                            List<String> imglist = new ArrayList<String>();
                            for(int j=0;j<array.length();j++){
                                JSONObject tempobject2 = array.optJSONObject(j);
                                imglist.add(tempobject2.optString("url"));
                                model.setUrlList(imglist);
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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "GetBroadCastDetailCollection", callback);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastDetailCollection", callback);
        }
    }
    @Override
    public void processClick(View v) {

    }
}
