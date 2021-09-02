package com.a21zhewang.constructionapp.ui.wdjc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ProjectStatus;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 重点检查-施工信息页
 * Created by WP-PC on 2020/3/17.
 */

public class ProjectStatusActivity extends BaseActivity {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.reason)
    TextView reason;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.lin_resion2)
    LinearLayout lin_resion2;
    @BindView(R.id.lin_resion3)
    LinearLayout lin_resion3;
    private List<FilesBean> filelist;
    @BindView(R.id.item__recyclerview)
    RecyclerView recyclerView;
    public CommonAdapter<FilesBean> imgsAdapter;
    private String recordID;
    private ProjectStatus projectStatus;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.projectstatus_layout;
    }

    @Override
    public void initViews() {
        title.setText("施工信息");
        recordID = getIntent().getStringExtra("recordID");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProjectStatusActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getFiles();
    }
    public void getFiles(){
        JSONObject object = JsonUtils.getjsonobj("{\"recordId\":\"" + recordID + "\"}");
        XUtil.postjsondata(object, "GetKeyExamination_Area", new MyCallBack() {
            @Override
            public void onResule(String res) {
                projectStatus = JsonUtils.getbean(res,ProjectStatus.class);
                filelist = projectStatus.getFiles();
                status.setText(projectStatus.getAreaStatus());
                if(TextUtils.isEmpty(projectStatus.getNoConstrType())){
                    lin_resion2.setVisibility(View.GONE);
                }
                if(TextUtils.isEmpty(projectStatus.getNoConstrReason())){
                     lin_resion3.setVisibility(View.GONE);
                }
                reason.setText(projectStatus.getNoConstrType());
                content.setText(projectStatus.getNoConstrReason());
                imgsAdapter = new CommonAdapter<FilesBean>(ProjectStatusActivity.this, R.layout.faqigtxt_imglist_item, filelist) {
                    @Override
                    protected void convert(ViewHolder holder, FilesBean s, final int position) {
//                      holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                        ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                        Glide.with(ProjectStatusActivity.this).load(s.getUrl()).into(imageView);
                        holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                PhotoViewActivity.PagerAdapterAPI<FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<FilesBean>() {

                                    @Override
                                    public String getstrs(int index, FilesBean obj) {

                                        return obj.getUrl();
                                    }

                                    @Override
                                    public String getms(int index, FilesBean obj) {

                                        return obj.getFileBrief();
                                    }
                                };
                                PhotoViewActivity.statPhotoViewActivity(ProjectStatusActivity.this, position, filelist, pagerAdapterAPI);
                            }
                        });
                    }
                };
                recyclerView.setAdapter(imgsAdapter);
                imgsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
