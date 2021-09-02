package com.a21zhewang.constructionapp.ui.aqsc;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

import android.location.Location;
import android.view.View;


import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;

import com.a21zhewang.constructionapp.base.BaseAddActivity;
import com.a21zhewang.constructionapp.base.BaseListViewActivity;
import com.a21zhewang.constructionapp.bean.AqscSpinnerBean;
import com.a21zhewang.constructionapp.bean.AqsclistBean;

import com.a21zhewang.constructionapp.bean.MsgStatusBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 *
 * 重庆建工添加接口：AddSafetyMsg_CQJG
 * 琴台添加接口：AddSafetyMsg_QinTai
 * 其他 添加接口：AddSafetyMsg
 *
 *
 */
public class AqscActivity extends BaseListViewActivity<MsgType,
        MsgStatusBean,AqsclistBean>{
    private String typeID="";//类型ID（如果不传则是空字符串）
    private String typeFatherID="";//父类类型ID（如果不传则是空字符串）
    private String typeGraFaID="";//祖类类型ID（如果不传则是空字符串）
    private String msgStatus="";//处理情况（如果不传则是空字符串）
    private String etpID="";//整改企业ID
    private String keyword="";
    private int pageIndex=0;//页数，从0开始
    private int onClickIndex=-1;

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

    @Override
    public void spinner1setinit(MsgType name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getTypeName());
    }

    @Override
    public void spinner2setinit(MsgStatusBean name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getStatus());
    }

    @Override
    public void thisconvert(ViewHolder holder, final AqsclistBean aqsclistBean, final int position) {
        if (aqsclistBean.isRead()){
            holder.setVisible(R.id.gtxt_list_item_tv_isread,false);
        }else{
            holder.setVisible(R.id.gtxt_list_item_tv_isread,true);
        }

        holder.setText(R.id.gtxt_list_item_tv_createTime, "时间："+aqsclistBean.getCreateTime());
//                        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, otherBean.getMsgTypeID());
        holder.setText(R.id.gtxt_list_item_tv_etpShortName, "单位："
                + aqsclistBean.getRecEtpShortName());
        holder.setText(R.id.gtxt_list_item_tv_createUserName, "区域："
                + aqsclistBean.getProjectAreaName());
        holder.setText(R.id.gtxt_list_item_tv_msgTitle,
                "标题："+aqsclistBean.getTitle());
        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, "类别："+aqsclistBean.getTypeFatherName());
        String msgStatus = aqsclistBean.getStatus();
        holder.setText(R.id.gtxt_list_item_tv_msgStatus, msgStatus);
        String color_str="#ffffff";
        for (int i=0;i<spinner2Beenlist.size();i++){
           MsgStatusBean msgStatusBean = spinner2Beenlist.get(i);
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
                Intent intent = new Intent(AqscActivity.this, AqscxqActivity.class);
                intent.putExtra("recordID",aqsclistBean.getRecordID());
                intent.putExtra("ApiUrl",aqsclistBean.getApiUrl());
                startActivity(intent);
            }
        });
    }
    /**
     * @return 设置标题文字
     */
    @Override
    public String settiltile() {
        return "安全检查";
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
                MsgType msgTypesBean = spinner1Beenlist.get(position);
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
                MsgStatusBean msgStatusBean = spinner2Beenlist.get(position);
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
           return"SafetyAddBtn";
    }

    /**
     * @param // 设置新增按钮的点击事件
     */
    @Override
    public void setaddbtnonclick() {
        activityBaselistviewRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PublicUtils.toast("点击了");
                onClickIndex=0;
                /**
                 * 琴台建安 && 重庆建工安全检查发起扔使用老的页面
                 * 其他区站使用新的页面
                 */
                startActivity(new Intent(AqscActivity.this,AqscAddActivity.class));
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

    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {
        getlistdata();
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

    /**
     * 获取下拉列表数据
     */
    private void getspinnerdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetSafetyMsgType", new MyCallBack() {
            @Override
            public void onResule(String res) {

                AqscSpinnerBean spinnerBean = JsonUtils.getbean(res, AqscSpinnerBean.class);

                spinner1Beenlist.clear();
                List<MsgType> msgTypes = spinnerBean.getMsgTypes();
                if (msgTypes!=null&&msgTypes.size()>0){
                    spinner1Beenlist.addAll(msgTypes);
                }
                MsgType typesBean = new MsgType();
                typesBean.setTypeName("默认");
                spinner1Beenlist.add(typesBean);

                spinner2Beenlist.clear();
                List<MsgStatusBean> msgStatus = spinnerBean.getMsgStatus();
                if (msgStatus!=null&&msgStatus.size()>0){
                    spinner2Beenlist.addAll(msgStatus);
                }
                MsgStatusBean statusbean = new MsgStatusBean();
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
        XUtil.postjsondata(getjsonobj, "GetListSafetyMsg", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<AqsclistBean> getbean = JsonUtils.jsonToList(recordList.toString(), AqsclistBean[].class);

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

}
