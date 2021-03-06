package com.a21zhewang.constructionapp.ui.check;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.AqscxqBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProceduralInspectionlItem;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WP-PC on 2019/11/12.
 */

public class Act_ProceduralHistoryDetail extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    private String checkRecordID;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_reason)
    TextView tv_reason;
    private int type = 0;
    @BindView(R.id.item_detail_recyclerview)
    RecyclerView recyclerView;
    public CommonAdapter<AqscxqBean.FilesBean> imgsAdapter;
    public List<AqscxqBean.FilesBean> imgfiles;
    private  AqscxqBean.FilesBean  Tbean1;
    private  AqscxqBean.FilesBean  Tbean2;
    private  AqscxqBean.FilesBean  bean;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_procedural_history_detail;
    }

    @Override
    public void initViews() {
        title.setText("??????????????????");
        //????????????
        imgfiles = new ArrayList<>();
        checkRecordID = getIntent().getStringExtra("checkRecordID");
        type = getIntent().getIntExtra("type",0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        imgsAdapter = new CommonAdapter<AqscxqBean.FilesBean>(this, R.layout.faqigtxt_imglist_item, imgfiles) {

            @Override
            protected void convert(ViewHolder holder, AqscxqBean.FilesBean s, final int position) {
//                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(Act_ProceduralHistoryDetail.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<AqscxqBean.FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<AqscxqBean.FilesBean>() {

                            @Override
                            public String getstrs(int index, AqscxqBean.FilesBean obj) {

                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, AqscxqBean.FilesBean obj) {

                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(Act_ProceduralHistoryDetail.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        recyclerView.setAdapter(imgsAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        JSONObject object = JsonUtils.getjsonobj("{\"checkRecordID\":\"" + checkRecordID + "\"}");
        XUtil.postjsondata(object, "GetInspectionRecordDetail", new MyCallBack() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("recordList");
                    JSONArray fileArray = object.optJSONArray("files");
                    for(int i=0 ; i<array.length();i++){
                        JSONObject tempobject = array.optJSONObject(i);
                        if(type == 0 ){
                            tv_reason.setVisibility(View.VISIBLE);
                            tv_reason.setText(tempobject.optString("reason"));
                            tv_content.setText(tempobject.optString("content"));
                        }else {
                            tv_reason.setVisibility(View.GONE);
                            tv_content.setText(tempobject.optString("content"));
                        }
                    }
//                    Tbean1 = new AqscxqBean.FilesBean();
//                    Tbean1.setUrl("https://wuhan.21theone.cn:8100/upload/images/jpg201909091131453101.jpg");
//                    Tbean1.setFileBrief("?????????");
//                    imgfiles.add(Tbean1);
//
//                    Tbean2 = new AqscxqBean.FilesBean();
//                    Tbean2.setUrl("https://wuhan.21theone.cn:8100/upload/images/jpg201909080443016201.jpg");
//                    Tbean2.setFileBrief("?????????");
//                    imgfiles.add(Tbean2);
                    for(int j=0; j<fileArray.length();j++){
                        bean = new AqscxqBean.FilesBean();
                        JSONObject tempFileobject = fileArray.optJSONObject(j);
                        bean.setUrl(tempFileobject.optString("url"));
                        bean.setFileBrief(tempFileobject.optString("fileBrief"));
                        imgfiles.add(bean);
                    }
                    if(imgfiles.size()>0){
                        recyclerView.setVisibility(View.VISIBLE);
                    }else {
                        recyclerView.setVisibility(View.GONE);
                    }
                    imgsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
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
