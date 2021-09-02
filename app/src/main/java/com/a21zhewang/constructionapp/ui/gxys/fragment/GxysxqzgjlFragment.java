package com.a21zhewang.constructionapp.ui.gxys.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.BaseAdapter;
import com.a21zhewang.constructionapp.base.BaseFragment;
import com.a21zhewang.constructionapp.base.YzgActivity;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ZgjlBean;
import com.a21zhewang.constructionapp.customview.WrapContentLinearLayoutManager;
import com.a21zhewang.constructionapp.ui.gxys.AddZgjlActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GxysxqzgjlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GxysxqzgjlFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "recordID";
    @BindView(R.id.fragment_gxysxqzgjl_RecyclerView)
    RecyclerView fragmentGxysxqzgjlRecyclerView;
    @BindView(R.id.fragment_gxysxqzgjl_ImageView)
    ImageView fragmentGxysxqzgjlImageView;


    // TODO: Rename and change types of parameters
    private String recordID;
    private Context mContext;
    private BaseAdapter<ZgjlBean> baseAdapter;


    public GxysxqzgjlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GxysxqzgjlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GxysxqzgjlFragment newInstance(String recordID) {
        GxysxqzgjlFragment fragment = new GxysxqzgjlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, recordID);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_gxysxqzgjl;
    }

    @Override
    public void initViews() {
        recordID = getArguments().getString(ARG_PARAM1);
        mContext = getActivity();
    }

    @Override
    public void initListener() {
        fragmentGxysxqzgjlImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddZgjlActivity.class);
                intent.putExtra("recordID",recordID);
                startActivity(intent);
            }
        });
        List<ZgjlBean> zgjlBeens=new ArrayList<>();
        fragmentGxysxqzgjlRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(mContext));
        //  holder.setText(R.id.zgjl_item_nrms,zgjlBean.get);
//                        PictureConfig.getPictureConfig().init(options).openPhoto(AddGtxtActivity.this, resultCall);
//  showimage_textView.setText(position + 1 + "/" + imglist.size());
//  layoutManager.scrollToPosition(position);
//imagespopupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        baseAdapter = new BaseAdapter<ZgjlBean>(mContext, R.layout.zgjl_item, zgjlBeens) {
            @Override
            protected void convert(ViewHolder holder, final ZgjlBean zgjlBean, int position) {

              final LinearLayout zgjl_item_lin= holder.getView(R.id.zgjl_item_lin);
                holder.setText(R.id.zgjl_item_TextView, zgjlBean.getTypeName());
                holder.setText(R.id.zgjl_item_nrms,zgjlBean.getContent());
                RecyclerView recyclerView = holder.getView(R.id.zgjl_item_RecyclerView);
                final List<FilesBean> files = zgjlBean.getFiles();
                if (files!=null&&files.size()>0){

                    recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

                    BaseAdapter<FilesBean> adapter = new BaseAdapter<FilesBean>(mContext, R.layout.faqigtxt_imglist_item, files) {
                        @Override
                        protected void convert(ViewHolder holder, FilesBean filesBean, final int position) {
                            ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                            Glide.with(mContext).load(filesBean.getUrl()).into(imageView);
                            holder.setText(R.id.faqigtxt_imglist_item_ms,filesBean.getFileBrief());
                            holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                        PictureConfig.getPictureConfig().init(options).openPhoto(AddGtxtActivity.this, resultCall);
                                    //  showimage_textView.setText(position + 1 + "/" + imglist.size());
                                    //  layoutManager.scrollToPosition(position);
                                    //imagespopupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

                                    PhotoViewActivity.statPhotoViewActivity(mContext, position, files, new PhotoViewActivity.PagerAdapterAPI<FilesBean>() {
                                        @Override
                                        public String getstrs(int index, FilesBean obj) {

                                            return obj.getUrl();
                                        }

                                        @Override
                                        public String getms(int index, FilesBean obj) {
                                            return obj.getFileBrief();
                                        }
                                    });
                                }
                            });
                        }
                    };
                    recyclerView.setAdapter(adapter);
                }else{
                    recyclerView.setVisibility(View.GONE);
                }
                String status = zgjlBean.getStatus();
                holder.setText(R.id.zgjl_item_TextView_stata,status);
                if ("已整改".equals(status)){
                             holder.setVisible(R.id.zgjl_item_nrms_Lin_zgh,true);
                    holder.setTextColor(R.id.zgjl_item_TextView_stata, Color.parseColor("#2c9c0f"));
                             holder.setText(R.id.zgjl_item_nrms_zgh,zgjlBean.getDealResults());

                    }else{
                    holder.setTextColor(R.id.zgjl_item_TextView_stata, Color.parseColor("#FA0703"));
                    holder.setVisible(R.id.zgjl_item_nrms_Lin_zgh,false);
                }
                if (!TextUtils.isEmpty(zgjlBean.getBtnID())){
                    holder.setVisible(R.id.zgjl_item_ImageView,true);
                    holder.setOnClickListener(R.id.zgjl_item_LinearLayout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), YzgActivity.class);
                            intent.putExtra("recordID", zgjlBean.getRecordRLID());
                            intent.putExtra("postdatamethod", "GetProcessAcceptanceDetailsButton");
                            startActivity(intent);
                        }
                    });
                }else{
                    holder.setVisible(R.id.zgjl_item_ImageView,false);
                   holder.setOnClickListener(R.id.zgjl_item_LinearLayout, null);
                }
                final TextView sstv= holder.getView(R.id.zgjl_item_TextView_ss);
                final ImageView ssiv= holder.getView(R.id.zgjl_item_ImageView_ss);
               if (zgjl_item_lin.getVisibility()==View.VISIBLE){
                   sstv.setText("点击收缩");
                   ssiv.setImageResource(R.mipmap.ssup);
               }else{
                   sstv.setText("点击展开");
                   ssiv.setImageResource(R.mipmap.ssdown);
               }
                holder.setOnClickListener(R.id.zgjl_item_LinearLayout_lin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (zgjl_item_lin.getVisibility()==View.VISIBLE){
                            zgjl_item_lin.setVisibility(View.GONE);
                            sstv.setText("点击展开");
                            ssiv.setImageResource(R.mipmap.ssdown);
                        }else{
                            zgjl_item_lin.setVisibility(View.VISIBLE);
                            sstv.setText("点击收缩");
                            ssiv.setImageResource(R.mipmap.ssup);
                        }
                    }
                });

            }
        };
        fragmentGxysxqzgjlRecyclerView.setAdapter(baseAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void lazyLoad() {
     getdata();
    }

    private void getdata() {
        XUtil.postjsondata(
                JsonUtils.getjsonobj("{\"recordID\":\""+recordID+"\"}"),
                "GetListProcessAcceptanceRefc",
                new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<ZgjlBean> getbean = JsonUtils.jsonToList(recordList.toString(), ZgjlBean[].class);

                    baseAdapter.deleteall();
                    if (getbean != null && getbean.size() > 0) {
                        baseAdapter.addall(getbean);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /**
             * 请求成功code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }
        });
    }

    @Override
    public void processClick(View v) {

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link
     * 's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
       // PublicUtils.toast("onResume");
        if (isVisible){
            getdata();
        }

    }
}
