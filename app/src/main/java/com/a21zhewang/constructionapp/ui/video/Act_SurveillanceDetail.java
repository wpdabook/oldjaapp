package com.a21zhewang.constructionapp.ui.video;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.MyAppCon;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.XListView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.kqgl.Act_KqglDetail;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.XViewHolder;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by Administrator on 2021/5/7.
 */

public class Act_SurveillanceDetail extends BaseActivity{
    @BindView(R.id.video_listview)
    ListView listView;
    @BindView(R.id.placeholder)
    LinearLayout placeHolder;
    private BaseAdapter adapter;
    private String VideoListUrl = "";
    private String ProjectID = "";
    private String BaseUrl = "";
    private Dialog loadingDialog;
    private JSONArray videoArray = new JSONArray();
    @Override
    public void beforesetContentView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.act_surveillance_detail;
    }

    @Override
    public void initViews() {
        ProjectID = getIntent().getStringExtra("ProjectID");
        BaseUrl = getIntent().getStringExtra("BaseUrl");
        loadingDialog = WeiboDialogUtils.createLoadingDialog(Act_SurveillanceDetail.this, "加载中...");
        loadData();
        adapter = new MyBaseAdapter() {
            @Override
            public View getView(final int position, View view, ViewGroup arg2) {
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.act_surveillance_detail_adapter_layout, null);
                }
                LinearLayout layout1 = XViewHolder.get(view, R.id.layout_1);
                LinearLayout layout2 = XViewHolder.get(view, R.id.layout_2);
                initItem(layout1, position, 0);
                initItem(layout2, position, 1);
                return view;
            }

            @Override
            public int getCount() {
                  return (videoArray.length() + 1) / 2;
            }
        };
        listView.setAdapter(adapter);
    }
    public void loadData(){
        loadingDialog.show();
        VideoListUrl = BaseUrl +"v1/bigscreen/video/GetCamerasByProjectId?etpId=QY2020112370b2f&projectId="+ProjectID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                VideoListUrl,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject object) {
                loadingDialog.dismiss();
                try {
                    JSONObject tempobject = new JSONObject(object.toString());
                    JSONArray jsonArray = tempobject.optJSONArray("Datas");
                    if(jsonArray.length() == 0){
//                        PublicUtils.toast("暂无结果");
                        listView.setVisibility(View.GONE);
                        placeHolder.setVisibility(View.VISIBLE);
                        return;
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        videoArray.put(jsonArray.optJSONObject(i));
                    }
                    if (videoArray.length() == 0) {
                        listView.setVisibility(View.GONE);
                        placeHolder.setVisibility(View.VISIBLE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        placeHolder.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast("请求失败");
            }
        });
        MyAppCon.getHttpQueue().add(request);
    }
    @Override
    public void initListener() {

    }


    @Override
    public void initData() {

    }
    private void initItem(View arg1, int position,int childPosition) {
        final JSONObject object = videoArray.optJSONObject(position * 2 + childPosition);
        if (object != null) {
            arg1.setVisibility(View.VISIBLE);
            TextView name = XViewHolder.get(arg1, R.id.name);
            ImageView image = XViewHolder.get(arg1, R.id.image);
            name.setText(object.optString("CameraName"));
            String PictureUrl = object.optString("PictureUrl");
            if (!TextUtils.isEmpty(PictureUrl)) {
                Glide.with(Act_SurveillanceDetail.this).load(PictureUrl).into(image);
            } else {
                image.setImageDrawable(null);
            }
            arg1.setTag(R.id.tag_first, object);
            arg1.setOnClickListener(noDoubleClickListener);
        }else {
            arg1.setVisibility(View.GONE);
        }

    }
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {

        @Override
        void onNoDoubleClick(View v) {
            JSONObject object = (JSONObject) v.getTag(R.id.tag_first);
            Intent i = new Intent();
            i.putExtra("PlayUrl", object.optString("PlayUrl"));
            i.setClass(Act_SurveillanceDetail.this, Act_WebVideo.class);
            startActivity(i);
        }
    };
    public abstract class NoDoubleClickListener implements View.OnClickListener {

        public static final int MIN_CLICK_DELAY_TIME = 500;
        private long lastClickTime = 0;

        @Override
        public void onClick(View v) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                onNoDoubleClick(v);
            }
        }

        abstract void onNoDoubleClick(View v);

    }
    @Override
    public void processClick(View v) {

    }
}
