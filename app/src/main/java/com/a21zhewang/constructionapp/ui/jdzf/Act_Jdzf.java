package com.a21zhewang.constructionapp.ui.jdzf;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.base.BaseListViewActivity;
import com.a21zhewang.constructionapp.base.BaseListViewActivity2;
import com.a21zhewang.constructionapp.bean.JdzfType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.RecordDate;
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

/**
 * 监督执法
 * Created by WP-PC on 2019/6/3.
 */

public class Act_Jdzf extends BaseListViewActivity2<JdzfType.JdzfStatus, JdzfType.MsgStatusBean, RecordDate> {
    private int onClickIndex=-1;
    private String typeID="";
    private String msgStatus="";
    private String keyword="";
    private int pageIndex=0;
    private String lawStatusValue = "";

    @Override
    public void OnRefreshing() {
        getlistdata();
        pageIndex=0;
        keyword="";
        commonAdapter.getDatas().clear();
        commonAdapter.notifyDataSetChanged();
    }

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
        getlistdata(pageIndex,10,typeID,lawStatusValue);
    }
    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                pageIndex=0;
                commonAdapter.getDatas().clear();
                commonAdapter.notifyDataSetChanged();
                keyword=edittext;
                getlistdata(0,10,typeID,lawStatusValue);
            }
        });
    }

    @Override
    public void initData() {
        if (listBeenlist==null)return;
        listBeenlist.clear();
        commonAdapter.notifyDataSetChanged();
        pageIndex=0;
        getspinnerdata();

    }

    private void getspinnerdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"lawTypes\"}"), "GetSuperviseTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                JdzfType spinnerBean = JsonUtils.getbean(res, JdzfType.class);
                spinner1Beenlist.clear();
                List<JdzfType.JdzfStatus> msgTypes = spinnerBean.getBackdata();
                if (msgTypes!=null&&msgTypes.size()>0){
                    spinner1Beenlist.addAll(msgTypes);
                }
                JdzfType.JdzfStatus typesBean = new JdzfType.JdzfStatus();
                typesBean.setTypeName("全部");
                spinner1Beenlist.add(typesBean);

                spinner2Beenlist.clear();
                List<JdzfType.MsgStatusBean> msgStatus = spinnerBean.getMsgStatusBeen();
                if (msgStatus!=null&&msgStatus.size()>0){
                    spinner2Beenlist.addAll(msgStatus);
                }
                JdzfType.MsgStatusBean statusbean1 = new JdzfType.MsgStatusBean();
                statusbean1.setStatus("处理中");
                statusbean1.setColor("FC676D");
                statusbean1.setValue("1");
                JdzfType.MsgStatusBean statusbean2 = new JdzfType.MsgStatusBean();
                statusbean2.setStatus("已闭合");
                statusbean2.setColor("0A9D7E");
                statusbean2.setValue("2");
                JdzfType.MsgStatusBean statusbean3 = new JdzfType.MsgStatusBean();
                statusbean3.setStatus("全部");
                statusbean3.setColor("666666");
                statusbean3.setValue("3");

                spinner2Beenlist.add(statusbean1);
                spinner2Beenlist.add(statusbean2);
                spinner2Beenlist.add(statusbean3);

            }
            @Override
            public void onFail(ObjBean getbean) {

            }
            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                super.onFinished();
                spinner2Adpater.notifyDataSetChanged();
                spinner1Adpater.notifyDataSetChanged();
                getdataok();
            }
        });
    }
    /**
     * 获取页面列表数据
     */
    private void getlistdata(int pageIndex,int pageSize,String lawType,String lawStatus) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"type\":\"2\",\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\"," +
                "\"key\":\""+keyword+"\",\"lawType\":\"" + lawType + "\",\"lawStatus\":\"" + lawStatus + "\"}");
        XUtil.postjsondata(getjsonobj, "GetSuperviseList", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<RecordDate> getbean = JsonUtils.jsonToList(recordList.toString(), RecordDate[].class);
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

    @Override
    public void spinner1setinit(JdzfType.JdzfStatus name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getTypeName());
    }

    @Override
    public void spinner2setinit(JdzfType.MsgStatusBean name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getStatus());
    }

    @Override
    public void thisconvert(ViewHolder holder, final RecordDate recordDate, final int position) {
        holder.setText(R.id.gtxt_list_item_tv_etpShortName, "建管："+recordDate.getConManager());
        holder.setText(R.id.gtxt_list_item_tv_msgTitle, "标题："+recordDate.getTitle());
        holder.setText(R.id.gtxt_list_item_tv_createTime, "时间："+recordDate.getCreateDate());
        String color_str="#ffffff";
        if(recordDate.getStatus() == 1){
            holder.setText(R.id.gtxt_list_item_tv_msgStatus, "处理中");
            color_str="#FC676D";
            holder.setTextColorRes(R.id.gtxt_list_item_tv_msgStatus,R.color.tab_color_true);
        }else if(recordDate.getType()==2 && recordDate.getStatus() == 2){
            holder.setText(R.id.gtxt_list_item_tv_msgStatus, "已复工");
            color_str="#0A9D7E";
            holder.setTextColorRes(R.id.gtxt_list_item_tv_msgStatus,R.color.color_qq);
        }else if(recordDate.getType()==1 && recordDate.getStatus() == 2){
            holder.setText(R.id.gtxt_list_item_tv_msgStatus, "已收到整改回复");
            color_str="#0A9D7E";
            holder.setTextColorRes(R.id.gtxt_list_item_tv_msgStatus,R.color.color_qq);
        }else if(recordDate.getType()==3 && recordDate.getStatus() == 2){
            holder.setText(R.id.gtxt_list_item_tv_msgStatus, "罚款已交");
            color_str="#0A9D7E";
            holder.setTextColorRes(R.id.gtxt_list_item_tv_msgStatus,R.color.color_qq);
        }else if(recordDate.getType()==4 && recordDate.getStatus() == 2){
            holder.setText(R.id.gtxt_list_item_tv_msgStatus, "已结案");
            color_str="#0A9D7E";
            holder.setTextColorRes(R.id.gtxt_list_item_tv_msgStatus,R.color.color_qq);
        }
        /**
         * -1、发起	2：未读	3：已读
         */
        if (recordDate.getReadStatus() == 2) {
            holder.setVisible(R.id.gtxt_list_item_img_msgStatus, true);
        } else {
            holder.setVisible(R.id.gtxt_list_item_img_msgStatus, false);
        }
        for (int i=0;i<spinner2Beenlist.size();i++){
            JdzfType.MsgStatusBean msgStatusBean = spinner2Beenlist.get(i);
            String status = msgStatusBean.getStatus();
            if (status.equals(msgStatus)){
                color_str ="#"+ msgStatusBean.getColor();
//                holder.setTextColor(R.id.gtxt_list_item_tv_msgStatus,Integer.parseInt(color_str));
            }
        }
        GradientDrawable gradientDrawable   = (GradientDrawable) holder.getView(R.id.gtxt_list_item_img_msgStatus).getBackground();
        gradientDrawable.setColor(Color.parseColor(color_str));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex = position;
                Intent intent = new Intent(Act_Jdzf.this, Act_JdzfDetail.class);
                intent.putExtra("id",recordDate.getId());
                intent.putExtra("str","0");
                intent.putExtra("type",recordDate.getType());
                startActivity(intent);
            }
        });
    }

    @Override
    public MySpinner.MySpinnerAPI setspinner1onclick() {
        MySpinner.MySpinnerAPI mySpinnerAPI=new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                listBeenlist.clear();
                JdzfType.JdzfStatus msgTypesBean = spinner1Beenlist.get(position);
                typeID = msgTypesBean.getTypeId();
                String typeName = msgTypesBean.getTypeName();
                if (typeName.equals("全部")) {
                    typeName="全部";
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
                JdzfType.MsgStatusBean msgStatusBean = spinner2Beenlist.get(position);
                msgStatus=msgStatusBean.getStatus();
                lawStatusValue = msgStatusBean.getValue();
                String status = msgStatusBean.getStatus();
                if (status.equals("全部")) {
                    status="全部";
                    msgStatus="";
                    lawStatusValue = "";
                }
                getlistdata();
                return status;
            }
        };
        return mySpinnerAPI;
    }

    @Override
    public String setaddbtnvisiable()
    {
        return "";
    }

    @Override
    public void setaddbtnonclick() {
//        activityBaselistviewRelativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickIndex=0;
//                startActivity(new Intent(Act_Jdzf.this,Act_JdzfAddInfo.class));
//            }
//        });
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
            getlistdata(0,(pageIndex+1)*10,typeID,lawStatusValue);
        }
    }
    @Override
    public String settiltile() {
        return "执法单";
    }

    @Override
    public String setspinner1str() {
        return "整改类别";
    }

    @Override
    public String setspinner2str() {
        return "整改情况";
    }
}
