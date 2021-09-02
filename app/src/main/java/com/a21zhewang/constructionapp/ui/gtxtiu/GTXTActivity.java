package com.a21zhewang.constructionapp.ui.gtxtiu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.BaseAdapter;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.DicBean;
import com.a21zhewang.constructionapp.bean.GTXTlistBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.customview.WrapContentLinearLayoutManager;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GTXTActivity extends BaseActivity implements OnTouchUpListener {


    @BindView(R.id.activity_gtxt_SWPullRecyclerLayout)
    SWPullRecyclerLayout activityGtxtSWPullRecyclerLayout;
    List<GTXTlistBean> OtherBeans;
    @BindView(R.id.activity_gtxt_MySpinner_XTLX)
    MySpinner activityGtxtMySpinnerXTLX;
    @BindView(R.id.activity_gtxt_MySpinner_CLQK)
    MySpinner activityGtxtMySpinnerCLQK;
    @BindView(R.id.activity_gtxt_RelativeLayout)
    RelativeLayout activityGtxtRelativeLayout;
    @BindView(R.id.activity_gtxt_MySpinner_XTLX_child)
    MySpinner activityGtxtMySpinnerXTLXChild;
    @BindView(R.id.activity_gtxt_MyTitleBar)
    MyTitleBar activityGtxtMyTitleBar;

    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;
    private BaseAdapter<GTXTlistBean> otherBeanCommonAdapter;
    private List<MsgType> xtlxlist;
    private List<MsgType> xtlxChildlist;
    private List<DicBean.MsgStatusBean> MsgTypeFatherBeanlist;
    private NamesSpinnerAdpater xtlxnamesSpinnerAdpater;
    private int page_index = 0,onClickIndex=-1;//list页码
    private String keyword="";
    private String TypeID_str;
    private String msgStatus = "";
    private String submsgTypeID = "";
    private NamesSpinnerAdpater<MsgType> childBeanNamesSpinnerAdpater;

    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_gtxt;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        activityGtxtMyTitleBar.setTitlenametv("生产协调");
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
        for (ButtonBean btn : buttons) {
          if (btn.getBtnID().equals("cmAddBtn")) {
              activityGtxtRelativeLayout.setVisibility(View.VISIBLE);
            }
        } }

        TypeID_str = "";
        //协调类型数组
        xtlxlist = new ArrayList<>();
        xtlxChildlist = new ArrayList<>();
        MsgTypeFatherBeanlist=new ArrayList<>();
        xtlxnamesSpinnerAdpater = new NamesSpinnerAdpater<MsgType>(this, xtlxlist) {
            @Override
            public void setinit(MsgType name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getTypeName());
            }

        };
        activityGtxtMySpinnerXTLX.setmyspinnerlistadpater(xtlxnamesSpinnerAdpater);
        activityGtxtMySpinnerXTLX.setTextViewtext("协调类别");
        activityGtxtMySpinnerCLQK.setTextViewtext("处理情况");
        childBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<MsgType>(this, xtlxChildlist) {

            @Override
            public void setinit(MsgType name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getTypeName());
            }
        };
        activityGtxtMySpinnerXTLXChild.setmyspinnerlistadpater(childBeanNamesSpinnerAdpater);

    }

    @Override
    public void initListener() {
        activityGtxtMySpinnerXTLX.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                otherBeanCommonAdapter.deleteall();
                xtlxChildlist.clear();
                MsgType msgTypeFatherBean = xtlxlist.get(position);
                List<MsgType> childs = msgTypeFatherBean.getMsgTypes();
                if (childs != null && childs.size() > 0) {
                    xtlxChildlist.addAll(childs);

                }
                childBeanNamesSpinnerAdpater.notifyDataSetChanged();
                String anObject = msgTypeFatherBean.getTypeName();
                if (anObject.equals("默认")) {
                    TypeID_str = "";
                    activityGtxtMySpinnerXTLX.setTextViewtext("协调类别");
                } else {
                    activityGtxtMySpinnerXTLX.setTextViewtext(anObject);
                    TypeID_str = msgTypeFatherBean.getTypeID();
                }
                submsgTypeID = "";
                activityGtxtMySpinnerXTLXChild.setTextViewtext("协调子类别");
                getdata(TypeID_str, page_index = 0, msgStatus, submsgTypeID);

                activityGtxtMySpinnerXTLX.popdismiss();
            }
        });

        activityGtxtMySpinnerXTLXChild.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OtherBeans.clear();
                MsgType childBean = xtlxChildlist.get(position);
                submsgTypeID = childBean.getTypeID();
                activityGtxtMySpinnerXTLXChild.setTextViewtext(childBean.getTypeName());
                getdata(TypeID_str, page_index = 0, msgStatus, submsgTypeID);
                activityGtxtMySpinnerXTLXChild.popdismiss();
            }
        });

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        OtherBeans = new ArrayList<>();
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
        activityGtxtSWPullRecyclerLayout.addHeaderView(header, 100);
        activityGtxtSWPullRecyclerLayout.addFooterView(footer, 100);
        //                        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, otherBean.getMsgTypeID());
        otherBeanCommonAdapter = new BaseAdapter<GTXTlistBean>(this,
                R.layout.gtxt_list_item2, OtherBeans) {

            @Override
            protected void convert(ViewHolder holder, final GTXTlistBean otherBean, final int position) {
                if (otherBean.isRead()){
                    holder.setVisible(R.id.gtxt_list_item_tv_isread,false);
                }else{
                    holder.setVisible(R.id.gtxt_list_item_tv_isread,true);
                }
                holder.setText(R.id.gtxt_list_item_tv_createTime, "时间："+otherBean.getCreateTime());
//                        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, otherBean.getMsgTypeID());
                holder.setText(R.id.gtxt_list_item_tv_etpShortName, "单位："
                        + otherBean.getEtpShortName());
                holder.setText(R.id.gtxt_list_item_tv_createUserName, "申请："
                        + otherBean.getCreateUserName());
                holder.setText(R.id.gtxt_list_item_tv_msgTitle, "标题："+otherBean.getTitle());
                holder.setText(R.id.gtxt_list_item_tv_msgTypeID, "类别："+otherBean.getTypeFatherName()
                );
                String msgStatus = otherBean.getStatus();
                holder.setText(R.id.gtxt_list_item_tv_msgStatus, msgStatus);
                String color_str="#ffffff";
                for (int i=0;i<MsgTypeFatherBeanlist.size();i++){
                    DicBean.MsgStatusBean msgStatusBean = MsgTypeFatherBeanlist.get(i);
                    String status = msgStatusBean.getStatus();
                    if (status.equals(msgStatus)){
                         color_str ="#"+ msgStatusBean.getColor();
                    }
                }
//Color.parseColor(color_str)
                GradientDrawable gradientDrawable   = (GradientDrawable) holder.getView(R.id.gtxt_list_item_img_msgStatus).getBackground();
                gradientDrawable.setColor(Color.parseColor(color_str));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(GTXTActivity.this, GtxtxqActivity.class);
                        intent.putExtra("msgID", otherBean.getRecordID());
                        startActivity(intent);
                    }
                });
            }
        };
        activityGtxtSWPullRecyclerLayout.setMyRecyclerView(new WrapContentLinearLayoutManager(this),
                otherBeanCommonAdapter);
        activityGtxtSWPullRecyclerLayout.addOnTouchUpListener(this);

        if (otherBeanCommonAdapter!=null)
            otherBeanCommonAdapter.deleteall();
        // otherBeanCommonAdapter.notifyDataSetChanged();

        //进入页面首先获取 协调类型  获取完成后 回调 getdataok 方法
        getxtlxdata();
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                otherBeanCommonAdapter.deleteall();
                keyword=edittext;
                getdata(TypeID_str, page_index = 0, msgStatus, submsgTypeID);
            }
        });
    }

    /**
     * 获取list 数据
     *
     * @param msgTypeID 协调类型
     * @param index     页码
     */
    private void getdata(String msgTypeID, int index, String msgStatus, String submsgTypeID){
        getdata(msgTypeID,index,10,msgStatus,submsgTypeID);
    }
    private void getdata(String msgTypeID, int index,int pageSize, String msgStatus, String submsgTypeID) {
        JSONObject jsonobj = JsonUtils.getjsonobj("{\"typeID\":\"" + msgTypeID + "\",\"submsgTypeID\":\"" + submsgTypeID + "\",\"pageIndex\":\"" + index + "\",\"msgStatus\":\"" + msgStatus + "\",\"pageSize\":\""+pageSize+"\",\"keyword\":\""+keyword+"\"}");
        XUtil.postjsondata(jsonobj, "GetListCoordinateMsg", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<GTXTlistBean> getbean = JsonUtils.jsonToList(recordList.toString(), GTXTlistBean[].class);


                    if (getbean != null && getbean.size() > 0) {
                        otherBeanCommonAdapter.addall(getbean);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /**
             * code 不等于 0回调
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

            @Override
            public void onFinished() {
                activityGtxtSWPullRecyclerLayout.refreshok();
               // otherBeanCommonAdapter.notifyDataSetChanged();
                if (onClickIndex!=-1){
                    activityGtxtSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        });
    }

    /**
     * 获取协调类型键值对
     */
    private void getxtlxdata() {
        JSONObject jsonobj = JsonUtils.getjsonobj("{}");
        XUtil.postjsondata(jsonobj, "GetAllCoordinateMsgXTLX", new MyCallBack() {


            @Override
            public void onResule(String res) {
                DicBean dicBean = JsonUtils.getbean(res, DicBean.class);
                List<MsgType> msgTypeFather = dicBean.getMsgTypes();
                xtlxnamesSpinnerAdpater.replcedata(msgTypeFather);

                MsgType bean = new MsgType();
                bean.setTypeName("默认");
                xtlxlist.add(bean);
                xtlxnamesSpinnerAdpater.notifyDataSetChanged();

                final List<DicBean.MsgStatusBean> clqk = dicBean.getMsgStatus();
                if (clqk != null && clqk.size() > 0) {
                    clqk.add(new DicBean.MsgStatusBean("默认","#ffffff"));
                    activityGtxtMySpinnerCLQK.setmyspinnerlistadpater(new NamesSpinnerAdpater<DicBean.MsgStatusBean>(GTXTActivity.this, clqk) {
                        @Override
                        public void setinit(DicBean.MsgStatusBean name, ViewHolder mViewHolder) {
                            mViewHolder.nametextview.setText(name.getStatus());
                        }
                    });
                    activityGtxtMySpinnerCLQK.setlistviewitemonclick(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            otherBeanCommonAdapter.deleteall();
                            String s = clqk.get(position).getStatus();
                            activityGtxtMySpinnerCLQK.setTextViewtext(s);
                            if (s.equals("默认")) {
                                s = "";
                                activityGtxtMySpinnerCLQK.setTextViewtext("处理情况");
                            }
                            msgStatus = s;
                            getdata(TypeID_str, page_index = 0, msgStatus, submsgTypeID);
                            activityGtxtMySpinnerCLQK.popdismiss();
                        }
                    });
                }
                List<DicBean.MsgStatusBean> msgStatus = dicBean.getMsgStatus();
                if (msgStatus!=null&&msgStatus.size()>0)
                MsgTypeFatherBeanlist.addAll(msgStatus);
                getdataok();
            }

            /**
             * code 不等于 0回调
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

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @Override
    public void OnRefreshing() {
        //OtherBeans.clear();
        keyword="";
        otherBeanCommonAdapter.deleteall();

        getdata(TypeID_str, page_index = 0, msgStatus, submsgTypeID);
    }

    @Override
    public void OnLoading() {
        getdata(TypeID_str, ++page_index, msgStatus, submsgTypeID);
    }

    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {
        getdata(TypeID_str, page_index = 0, msgStatus, submsgTypeID);
    }


    @OnClick(R.id.activity_gtxt_RelativeLayout)
    public void onViewClicked() {
        onClickIndex=0;
        startActivity(new Intent(this, AddGtxtActivity.class));
    }


    /**
     * 页面销毁
     */
    @Override
    public void resume() {
        PublicUtils.log("回到页面刷新！");
       // if (OtherBeans==null)return;
      //  OtherBeans.clear();
if (onClickIndex!=-1){
    otherBeanCommonAdapter.deleteall();
    getdata(TypeID_str, 0,(page_index+1)*10, msgStatus, submsgTypeID);
}
    }


}
