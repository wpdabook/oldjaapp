package com.a21zhewang.constructionapp.ui.wmsg;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseListViewActivity;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ZljdlistBean;
import com.a21zhewang.constructionapp.bean.ZljdspinnerBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WmsgActivity extends BaseListViewActivity<
        ZljdspinnerBean.MsgTypesBean,
        ZljdspinnerBean.MsgStatusBean,
        ZljdlistBean> {


    private String typeID="";
    private String typeFatherID="";
    private String msgStatus="";
    private String etpID="";
    private int pageIndex=0;
    private int onClickIndex=-1;
    private String keyword="";

    /**
     * 第一个下拉实现
     *
     * @param name
     * @param mViewHolder
     */
    @Override
    public void spinner1setinit(ZljdspinnerBean.MsgTypesBean name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getTypeName());
    }

    /**
     * 第二个下拉实现
     *
     * @param name
     * @param mViewHolder
     */
    @Override
    public void spinner2setinit(ZljdspinnerBean.MsgStatusBean name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getStatus());
    }

    /**
     * 设置listviewitem布局
     *
     * @param holder
     * @param zljdspinnerBean
     * @param position
     */
    @Override
    public void thisconvert(ViewHolder holder, final ZljdlistBean zljdspinnerBean, final int position) {
        if (zljdspinnerBean.isRead()){
            holder.setVisible(R.id.gtxt_list_item_tv_isread,false);
        }else{
            holder.setVisible(R.id.gtxt_list_item_tv_isread,true);
        }
        holder.setText(R.id.gtxt_list_item_tv_createTime, "时间："+zljdspinnerBean.getCreateTime());
//                        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, otherBean.getMsgTypeID());
        holder.setText(R.id.gtxt_list_item_tv_etpShortName, "单位："
                + zljdspinnerBean.getRecEtpShortName());
        holder.setText(R.id.gtxt_list_item_tv_createUserName, "区域："
                + zljdspinnerBean.getProjectAreaName());
        holder.setText(R.id.gtxt_list_item_tv_msgTitle,
                "标题："+zljdspinnerBean.getTitle());
        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, "类别："+zljdspinnerBean.getTypeFatherName());
        String msgStatus = zljdspinnerBean.getStatus();
        holder.setText(R.id.gtxt_list_item_tv_msgStatus, msgStatus);
        String color_str="#ffffff";
        for (int i=0;i<spinner2Beenlist.size();i++){
            ZljdspinnerBean.MsgStatusBean msgStatusBean = spinner2Beenlist.get(i);
            String status = msgStatusBean.getStatus();
            if (status.equals(msgStatus)){
                color_str ="#"+ msgStatusBean.getColor();
            }
        }
//        Color.parseColor(color_str)
//        GradientDrawable gradientDrawable   = (GradientDrawable) holder.getView(R.id.gtxt_list_item_img_msgStatus).getBackground();
//        gradientDrawable.setColor(Color.parseColor(color_str));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=position;
                Intent intent = new Intent(WmsgActivity.this, WmsgxqActivity.class);
                intent.putExtra("recordID",zljdspinnerBean.getRecordID());
//                intent.putExtra("ApiUrl",zljdspinnerBean.getApiUrl());
                startActivity(intent);
            }
        });
    }

    /**
     * @return 设置标题文字
     */
    @Override
    public String settiltile() {
        return "文明施工";
    }

    /**
     * @return 设置第一个下拉文字
     */
    @Override
    public String setspinner1str() {
        return "整改类别";
    }

    /**
     * @return 设置第二个下拉文字
     */
    @Override
    public String setspinner2str() {
        return "整改情况";
    }

    @Override
    public MySpinner.MySpinnerAPI setspinner1onclick() {
        MySpinner.MySpinnerAPI mySpinnerAPI=new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                listBeenlist.clear();
                ZljdspinnerBean.MsgTypesBean msgTypesBean = spinner1Beenlist.get(position);
                typeID=msgTypesBean.getTypeID();
                String typeName = msgTypesBean.getTypeName();
                if (typeName.equals("默认")) {
                    typeName="整改类别";
                    typeID="";
                }
                getlistdata();
                return typeName;
            }
        };
        return mySpinnerAPI;
    }

    @Override
    public MySpinner.MySpinnerAPI setspinner2onclick() {
        MySpinner.MySpinnerAPI mySpinnerAPI=new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                listBeenlist.clear();
                ZljdspinnerBean.MsgStatusBean msgStatusBean = spinner2Beenlist.get(position);
                msgStatus=msgStatusBean.getStatus();
                String status = msgStatusBean.getStatus();
                if (status.equals("默认")) {
                    status="整改情况";
                    msgStatus="";
                }
                getlistdata();
                return status;
            }
        };
        return mySpinnerAPI;
    }

    @Override
    public String setaddbtnvisiable() {
          return "CivilizationAddBtn";
    }

    /**
     * @param //activityBaselistviewRelativeLayout 设置新增按钮的点击事件
     */
    @Override
    public void setaddbtnonclick() {
        activityBaselistviewRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=0;
                startActivity(new Intent(WmsgActivity.this,WmsgAddActivity.class));
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
                if (listBeenlist==null)return;
                listBeenlist.clear();
                commonAdapter.notifyDataSetChanged();
                pageIndex=0;
                keyword=edittext;
                getlistdata(0,10);

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

        if (listBeenlist==null)return;
        listBeenlist.clear();
        commonAdapter.notifyDataSetChanged();

        pageIndex=0;
        getspinnerdata();
    }

    private void getspinnerdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetCivilizedMsgType",  new MyCallBack() {
            @Override
            public void onResule(String res) {

                ZljdspinnerBean spinnerBean = JsonUtils.getbean(res, ZljdspinnerBean.class);

                spinner1Beenlist.clear();
                List<ZljdspinnerBean.MsgTypesBean> msgTypes = spinnerBean.getMsgTypes();
                if (msgTypes!=null&&msgTypes.size()>0){
                    spinner1Beenlist.addAll(msgTypes);
                }
                ZljdspinnerBean.MsgTypesBean typesBean = new ZljdspinnerBean.MsgTypesBean();
                typesBean.setTypeName("默认");
                spinner1Beenlist.add(typesBean);

                spinner2Beenlist.clear();
                List<ZljdspinnerBean.MsgStatusBean> msgStatus = spinnerBean.getMsgStatus();
                if (msgStatus!=null&&msgStatus.size()>0){
                    spinner2Beenlist.addAll(msgStatus);
                }
                ZljdspinnerBean.MsgStatusBean statusbean = new ZljdspinnerBean.MsgStatusBean();
                statusbean.setStatus("默认");
                spinner2Beenlist.add(statusbean);

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                spinner2Adpater.notifyDataSetChanged();
                spinner1Adpater.notifyDataSetChanged();
                getdataok();
            }
        });
    }

    /**
     * 刷新
     */
    @Override
    public void OnRefreshing() {
        pageIndex=0;keyword="";
        commonAdapter.getDatas().clear();
        commonAdapter.notifyDataSetChanged();
        getlistdata();
    }

    /**
     * 加载
     */
    @Override
    public void OnLoading() {
        pageIndex++;
        getlistdata();
    }

    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {
        getlistdata();
    }

    /**
     * 获取页面列表数据
     */
    private void getlistdata() {

getlistdata(pageIndex,10);
    }

    /**
     * 获取页面列表数据
     */
    private void getlistdata(int pageIndex,int pageSize) {


        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"typeID\":\""+typeFatherID+"\",\"typeFatherID\":\""+typeID+"\",\"msgStatus\":\""+msgStatus+"\",\"etpID\":\""+etpID+"\",\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"keyword\":\""+keyword+"\"}");

        XUtil.postjsondata(getjsonobj, "GetListCivilizedMsg", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<ZljdlistBean> getbean = JsonUtils.jsonToList(recordList.toString(), ZljdlistBean[].class);

                    if (getbean != null && getbean.size() > 0) {
                        listBeenlist.addAll(getbean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                commonAdapter.notifyDataSetChanged();
                activityBaselistviewSWPullRecyclerLayout.refreshok();
                if (onClickIndex!=-1){
                    activityBaselistviewSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        });
    }
    /**
     * 页面显示
     */
    @Override
    public void resume() {
        if (onClickIndex!=-1){
            if (listBeenlist==null)return;
            listBeenlist.clear();
            commonAdapter.notifyDataSetChanged();
            getlistdata(0,(pageIndex+1)*10);
        }
    }
}
