package com.a21zhewang.constructionapp.ui.set;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.MyCompanyAdapter;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.check.Act_AddTypeProcedural;
import com.a21zhewang.constructionapp.ui.risk.Act_RiskReportAdd;
import com.a21zhewang.constructionapp.ui.risk.Act_SelectRiskType;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hzw.graffiti.GraffitiActivity;

import static com.a21zhewang.constructionapp.ui.set.SelectProblemActivity.RESQUST_PROBLEM_TYPE;

/**
 * 反馈建议
 * Created by WP-PC on 2020/3/9.
 */

public class FeedbackActivity extends BaseActivity{
    @BindView(R.id.select_problem_type)
    LinearLayout select_problem_type;
    @BindView(R.id.bot_history)
    LinearLayout bot_history;
    @BindView(R.id.notice_project)
    TextView notice_project;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.edit_noitem)
    EditText edit_info;
    private String type = "";
    @BindView(R.id.problem_recyclerview)
    RecyclerView recyclerview;
    private RequestOptions requestOptions;
    private Context context;
    private BaseQuickAdapter<LocalMedia,BaseViewHolder> baseQuickAdapter;
    private List<LocalMedia> imgLists = new ArrayList<>();
    private int selectImageIndex;
    private JSONObject commitObject;
    private JSONArray FileArray = new JSONArray();
    private List<String> files;
    private Dialog loadingDialog;
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_feedback_layout;
    }

    @Override
    public void initViews() {
        initViewRecylerViewData();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "提交中...");
    }

    /*
    * 上传安全图片
    */
    public void initViewRecylerViewData(){
        requestOptions = new RequestOptions().centerCrop();
        recyclerview.setLayoutManager(new GridLayoutManager(context, 4));
        baseQuickAdapter = new BaseQuickAdapter<LocalMedia, BaseViewHolder>(imgLists) {
            @Override
            protected void convert(final BaseViewHolder helper, final LocalMedia imgpath) {
                ImageView imgview = helper.getView(R.id.fiv);
                if ("addimg".equals(imgpath.getPath())) {
                    imgview.setImageResource(R.mipmap.addimgs);
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remove(getData().size()-1);
                            PictureSelector.create(FeedbackActivity.this).openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(9)
                                    .imageSpanCount(4)
                                    .selectionMode(PictureConfig.MULTIPLE)
                                    .previewImage(true)
                                    .previewVideo(false)
                                    .enablePreviewAudio(false)
                                    .isCamera(true)
                                    .isZoomAnim(true)
                                    .sizeMultiplier(0.5f)
                                    .enableCrop(false)
                                    .compress(true)
                                    .isGif(false)
                                    .showCropFrame(false)
                                    .showCropGrid(false)
                                    .openClickSound(false)
                                    .selectionMedia(imgLists)
                                    .previewEggs(true)
                                    .compress(true).forResult(166);
                        }
                    });
                    helper.setVisible(R.id.iv_del, false);
                } else {
                    helper.setVisible(R.id.iv_del, true);
                    helper.getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            remove(helper.getLayoutPosition()-getHeaderLayoutCount());
                        }
                    });
                    imgview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectImageIndex = helper.getLayoutPosition()-getHeaderLayoutCount();
                            GraffitiActivity.startActivityForResult((Activity) mContext, imgpath.getCompressPath());
                        }
                    });
                    Glide.with(mContext)
                            .load(imgpath.getCompressPath()).apply(requestOptions)
                            .into(imgview);
                }
            }
        };
        LocalMedia media = new LocalMedia();
        media.setPath("addimg");
        media.setChecked(true);
        baseQuickAdapter.addData(media);
        MultiTypeDelegate multiTypeDelegate = new MultiTypeDelegate<LocalMedia>() {
            @Override
            protected int getItemType(LocalMedia o) {
                return 0;
            }
        };
        multiTypeDelegate.registerItemType(0, R.layout.newcheckquestion_list_imgitem);
        baseQuickAdapter.setMultiTypeDelegate(multiTypeDelegate);
        recyclerview.setAdapter(baseQuickAdapter);
    }

    @OnClick({R.id.select_problem_type,
            R.id.btn_submit,
            R.id.bot_history,

    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_problem_type:  //选择项目 type-1.反馈建议分类 2.视频分类
                startActivityForResult(new Intent(FeedbackActivity.this,SelectProblemActivity.class).putExtra("type","1").putExtra("title","选择问题类别"),SelectProblemActivity.RESQUST_PROBLEM_TYPE);
                break;
            case R.id.btn_submit:  //选择项目
                JSONObject object = getjsonArray();
                if(TextUtils.isEmpty(notice_project.getText().toString())){
                    showToast("请选择问题类别");
                    return;
                }
                if(TextUtils.isEmpty(edit_info.getText().toString())){
                    showToast("请写下您的建议");
                    return;
                }
                loadingDialog.show();
                postJsonAndFile(object.toString(),files,"submitFeedback");
                break;
            case R.id.bot_history:
                startActivity(new Intent(FeedbackActivity.this,FeedbackHistoryActivity.class));
                break;
        }
    }
    /**
     * 上行参数
     * @return
     */
    private JSONObject getjsonArray() {
        try {
            commitObject = new JSONObject();
            commitObject.put("type", type);
            commitObject.put("feedback", edit_info.getText().toString());
            commitObject.put("files", FileArray);
        }catch (JSONException e){
            return commitObject;
        }
        return commitObject;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectProblemActivity.RESQUST_PROBLEM_TYPE
                && resultCode == RESULT_OK
                && data != null) {
            String typeName = data.getStringExtra("typeName");
            type = data.getStringExtra("type");
            notice_project.setText(typeName);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 166) {
            List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
            baseQuickAdapter.replaceData(list);
            files = new ArrayList<String>();
            List<LocalMedia> imglist2 = baseQuickAdapter.getData();
            try {
                if (list.size() > 0) {
                    for (int i = 0; i < imglist2.size(); i++) {
                        JSONObject object = new JSONObject();
                        String path = imglist2.get(i).getCompressPath();
                        String fileName = path.substring(path.lastIndexOf("/") + 1);
                        object.put("fileBrief", "第" + (i + 1) + "张");
                        object.put("fileName", fileName);
                        files.add(path);
                        FileArray.put(object);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 166){
            LocalMedia media = new LocalMedia();
            media.setPath("addimg");
            baseQuickAdapter.addData(media);
        }
        if (requestCode == GraffitiActivity.REQ_CODE_GRAFFITI
                && resultCode == Activity.RESULT_OK && data != null) {
            String imgpath = data.getStringExtra(GraffitiActivity.KEY_IMAGE_PATH);
            LocalMedia media = new LocalMedia();
            media.setPath(imgpath);
            media.setCompressPath(imgpath);
            baseQuickAdapter.setData(selectImageIndex,media);
        }
    }
    /**
     * 安全|有隐患 JSON+FILE提交
     * object 需提交的数据
     * status 回传的数据
     */
    private void postJsonAndFile(String json, List<String> files, String method) {
        XUtil.updatas(JsonUtils.getjsonobj(json), files, method, new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                showToast("保存成功");
                FeedbackActivity.this.finish();
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
}
