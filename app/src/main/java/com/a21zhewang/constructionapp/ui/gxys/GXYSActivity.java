package com.a21zhewang.constructionapp.ui.gxys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.BaseAdapter;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.GxysListBean;
import com.a21zhewang.constructionapp.bean.GxysinitB;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyRx;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.WrapContentLinearLayoutManager;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.angel.interfaces.OnTouchUpListener;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GXYSActivity extends BaseActivity {


    @BindView(R.id.activity_gxys_MySpinner1)
    MySpinner activityGxysMySpinner1;
    @BindView(R.id.activity_gxys_MySpinner2)
    MySpinner activityGxysMySpinner2;
    @BindView(R.id.activity_gxys_MyRx)
    MyRx activityGxysSWPullRecyclerLayout;
    @BindView(R.id.activity_gxys_RelativeLayout)
    RelativeLayout activityGxysRelativeLayout;

    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;
    private List<GxysinitB.MsgStatusBean> msgStatusBeanList;
    private List<GxysinitB.EtpsBean> etpsBeen;
    private NamesSpinnerAdpater<GxysinitB.MsgStatusBean> spinner2adapter;
    private NamesSpinnerAdpater<GxysinitB.EtpsBean> spinner1adapter;
    private BaseAdapter<GxysListBean> listadapter;
    private int pageindex = 0;
    private String msgStatus = "";
    private String etpID = "";
    private int onClickIndex=-1;
    private String keyword="";

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_gxys;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals("cmAddBtn")) {
                    activityGxysRelativeLayout.setVisibility(View.VISIBLE);
                    activityGxysRelativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickIndex=0;
                            startActivity(new Intent(GXYSActivity.this,AddGxysActivity.class));
                        }
                    });
                }
            } }
        initlist();
        //初始化下拉列表
        initspinner();

    }

    private void initlist() {
        List<GxysListBean> listBeans = new ArrayList<>();
        listadapter = new BaseAdapter<GxysListBean>(this, R.layout.gtxt_list_item2, listBeans) {
            @Override
            protected void convert(ViewHolder holder, final GxysListBean gxysListBean, final int position) {
                if (gxysListBean.isIsRead()) {
                    holder.setVisible(R.id.gtxt_list_item_tv_isread, false);
                } else {
                    holder.setVisible(R.id.gtxt_list_item_tv_isread, true);
                }
                holder.setText(R.id.gtxt_list_item_tv_createTime, "时间："+gxysListBean.getCreateTime());
//                        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, otherBean.getMsgTypeID());
                holder.setText(R.id.gtxt_list_item_tv_etpShortName, "单位："
                        + gxysListBean.getEtpShortName());
                holder.setText(R.id.gtxt_list_item_tv_createUserName, "申请："
                        + gxysListBean.getCreateUserName());
                holder.setText(R.id.gtxt_list_item_tv_msgTitle,
                        "标题："+gxysListBean.getTitle());
                holder.setText(R.id.gtxt_list_item_tv_msgTypeID, "区域："+gxysListBean.getProjectAreaName());
                String msgStatus = gxysListBean.getStatus();
                holder.setText(R.id.gtxt_list_item_tv_msgStatus, msgStatus);
                String color_str = "#ffffff";
                for (int i = 0; i < msgStatusBeanList.size(); i++) {
                    GxysinitB.MsgStatusBean msgStatusBean = msgStatusBeanList.get(i);
                    String status = msgStatusBean.getStatus();
                    if (status.equals(msgStatus)) {
                        color_str = "#" + msgStatusBean.getColor();
                    }
                }
//Color.parseColor(color_str)
                GradientDrawable gradientDrawable = (GradientDrawable) holder.getView(R.id.gtxt_list_item_img_msgStatus).getBackground();
                gradientDrawable.setColor(Color.parseColor(color_str));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(GXYSActivity.this, GxysxqActivity.class);
                        intent.putExtra("recordID", gxysListBean.getRecordID());
                        startActivity(intent);
                    }
                });
            }
        };
        activityGxysSWPullRecyclerLayout.setMyRecyclerView(new WrapContentLinearLayoutManager(this), listadapter);
        activityGxysSWPullRecyclerLayout.addOnTouchUpListener(new OnTouchUpListener() {
            @Override
            public void OnRefreshing() {
                pageindex = 0;
                listadapter.deleteall();
                keyword="";
                getlistdata();
            }

            @Override
            public void OnLoading() {
                pageindex++;
                getlistdata();
            }

            @Override
            public void getdataok() {

            }
        });
    }

    private void initspinner() {
        msgStatusBeanList = new ArrayList<>();
        etpsBeen = new ArrayList<>();
        spinner1adapter = new NamesSpinnerAdpater<GxysinitB.EtpsBean>(GXYSActivity.this, etpsBeen) {
            @Override
            public void setinit(GxysinitB.EtpsBean name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getEtpShortName());
            }
        };
        activityGxysMySpinner1.setPopHeight(300);
        activityGxysMySpinner1.setmyspinnerlistadpater(spinner1adapter);
        spinner2adapter = new NamesSpinnerAdpater<GxysinitB.MsgStatusBean>(GXYSActivity.this, msgStatusBeanList) {
            @Override
            public void setinit(GxysinitB.MsgStatusBean name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getStatus());
            }
        };
        activityGxysMySpinner1.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                GxysinitB.EtpsBean etpsBean = etpsBeen.get(position);
                etpID = etpsBean.getEtpID();
                listadapter.deleteall();
                String etpShortName = etpsBean.getEtpShortName();
                if ("全部".equals(etpShortName)) {
                    etpShortName = "分包单位";
                }
                getlistdata();
                return etpShortName;
            }
        });
        activityGxysMySpinner2.setmyspinnerlistadpater(spinner2adapter);
        activityGxysMySpinner2.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                GxysinitB.MsgStatusBean msgStatusBean = msgStatusBeanList.get(position);
                String status = msgStatusBean.getStatus();
                msgStatus = status;
                listadapter.deleteall();
                if ("全部".equals(status)) {
                    status = "验收情况";
                    msgStatus = "";
                }
                getlistdata();
                return status;
            }
        });


    }

    private void initspiner() {
        XUtil.postjsondata("", "GetProcessAcceptanceType", new MyCallBack() {
            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                etpsBeen.clear();
                msgStatusBeanList.clear();

                GxysinitB gxysinitB = JsonUtils.getbean(res, GxysinitB.class);
                List<GxysinitB.EtpsBean> etps = gxysinitB.getEtps();
                List<GxysinitB.MsgStatusBean> msgStatus = gxysinitB.getMsgStatus();

                etpsBeen.addAll(etps);
                msgStatusBeanList.addAll(msgStatus);
                if (etpsBeen.size() > 0) {
                    activityGxysMySpinner1.setVisibility(View.VISIBLE);
                    GxysinitB.EtpsBean e = new GxysinitB.EtpsBean();
                    e.setEtpShortName("全部");
                    e.setEtpID("");
                    etpsBeen.add(e);
                }
                if (msgStatusBeanList.size() > 0) {
                    GxysinitB.MsgStatusBean statusBean = new GxysinitB.MsgStatusBean();
                    statusBean.setColor("#ffffff");
                    statusBean.setStatus("全部");
                    msgStatusBeanList.add(statusBean);
                }
                spinner1adapter.notifyDataSetChanged();
                spinner2adapter.notifyDataSetChanged();
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

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                pageindex = 0;
                listadapter.deleteall();
                getlistdata();
            }
        });
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
         keyword=edittext;
                pageindex = 0;
                listadapter.deleteall();
                getlistdata();

            }
        });
    }

    /**
     * 页面显示
     */
    @Override
    public void resume() {
        if (onClickIndex!=-1){
            listadapter.deleteall();
            getlistdata(0,(pageindex+1)*10);
        }
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        initspiner();
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }
    public void getlistdata(){
        getlistdata(pageindex,10);
    }
    public void getlistdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"pageSize\":\""+pageSize+"\",\"pageIndex\":\"" + pageindex + "\",\"msgStatus\":\"" + msgStatus + "\",\"etpID\":\"" + etpID + "\",\"keyword\":\""+keyword+"\"}");
        XUtil.postjsondata(getjsonobj, "GetListProcessAcceptance", new MyCallBack() {
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
                    List<GxysListBean> getbean = JsonUtils.jsonToList(recordList.toString(), GxysListBean[].class);


                    if (getbean != null && getbean.size() > 0) {
                        listadapter.addall(getbean);

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

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                activityGxysSWPullRecyclerLayout.refreshok();
                if (onClickIndex!=-1){
                    activityGxysSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        });
    }


}
