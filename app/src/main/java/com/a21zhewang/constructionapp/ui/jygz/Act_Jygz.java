package com.a21zhewang.constructionapp.ui.jygz;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.bean.JygzAreaData;
import com.a21zhewang.constructionapp.bean.JygzBean;
import com.a21zhewang.constructionapp.bean.JygzStatusData;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.receiver.PushMessageReceiver;
import com.a21zhewang.constructionapp.utils.Act_BaseListView5;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;



public class Act_Jygz extends Act_BaseListView5<JygzAreaData, JygzStatusData,JygzBean> {
    private String RegionValue="";//类型ID（如果不传则是空字符串）
    private String typeFatherID="";//父类类型ID（如果不传则是空字符串）
    private String typeGraFaID="";//祖类类型ID（如果不传则是空字符串）
    private String StatusValue="";//处理情况（如果不传则是空字符串）
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
    public void spinner1setinit(JygzAreaData name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getRegionName());
    }

    @Override
    public void spinner2setinit(JygzStatusData name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getStatusName());
    }

    @Override
    public void thisconvert(ViewHolder holder, final JygzBean jygzBean, final int position) {
        holder.setText(R.id.tv_status,jygzBean.getStatusName());
        holder.setText(R.id.project_name,jygzBean.getProjectName());
        holder.setText(R.id.project_type,"所在区域："+jygzBean.getRegion());
        holder.setText(R.id.complate_people,"检查人员："+jygzBean.getCreateName());
        holder.setText(R.id.rank_rate,"告知时间："+jygzBean.getCreateDate());
        holder.setText(R.id.tv_unit,"整改单位："+jygzBean.getRectEtpName());
        if(jygzBean.getStaffDay()<0){
            String fg = jygzBean.getStaffDay()+"天";
            String day = fg.replace("-","");
            holder.setText(R.id.tv_end_staffday,"已超期："+day);
            holder.setVisible(R.id.tv_end_staffday,true);
        }else {
            holder.setVisible(R.id.tv_end_staffday,false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=position;
                Intent intent = new Intent(Act_Jygz.this, Act_JygzDetail.class);
                intent.putExtra("recordID",jygzBean.getRecordId());
                startActivity(intent);
            }
        });
    }

    /**
     * @return 设置标题文字
     */
    @Override
    public String settiltile() {
        return "简易告知";
    }

    /**
     * @return 设置第一个下拉文字
     */
    @Override
    public String setspinner1str() {
        return "所在区域";
    }

    /**
     * @return 设置第二个下拉文字
     */
    @Override
    public String setspinner2str() {
        return "状态";
    }

    @Override
    public MySpinner.MySpinnerAPI setspinner1onclick() {
        MySpinner.MySpinnerAPI mySpinnerAPI=new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                listBeenlist.clear();
                JygzAreaData jygzAreaData = spinner1Beenlist.get(position);
                RegionValue = jygzAreaData.getRegionValue();
                String typeName = jygzAreaData.getRegionName();
                if (typeName.equals("默认")) {
                    typeName="所在区域";
                    RegionValue="";
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
                JygzStatusData jygzStatusData = spinner2Beenlist.get(position);
                StatusValue = jygzStatusData.getStatusValue();
                String status = jygzStatusData.getStatusName();
                if (status.equals("默认")) {
                    status="状态";
                    StatusValue="";
                }
                getlistdata();
                return status;
            }
        };
        return mySpinnerAPI;
    }

    @Override
    public String setaddbtnvisiable() {
        return"EasyNoticeAddBtn";
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
//                startActivity(new Intent(AqscActivity.this,AqscAddActivity.class));
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
        if (0 != PushMessageReceiver.count) {
            //角标清空
            PushMessageReceiver.count = 0;
            AppShortCutUtil.setCount(PushMessageReceiver.count, Act_Jygz.this);
        }
    }

    /**
     * 获取下拉列表数据
     */
    private void getspinnerdata() {
        JSONObject object = JsonUtils.getjsonobj("{}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    spinner1Beenlist.clear();
                    List<JygzAreaData> jygzArealist = JsonUtils.jsonToList(array.toString(),JygzAreaData[].class);
                    if (jygzArealist!=null&&jygzArealist.size()>0){
                        spinner1Beenlist.addAll(jygzArealist);
                    }
                    JygzAreaData data = new JygzAreaData();
                    data.setRegionName("默认");
                    spinner1Beenlist.add(data);
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
                spinner1Adpater.notifyDataSetChanged();
            }
        };

        JSONObject object2 = JsonUtils.getjsonobj("{}");
        MyCallBack callback2 = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    spinner2Beenlist.clear();
                    List<JygzStatusData> jygzStatuslist = JsonUtils.jsonToList(array.toString(),JygzStatusData[].class);
                    if (jygzStatuslist!=null&&jygzStatuslist.size()>0){
                        spinner2Beenlist.addAll(jygzStatuslist);
                    }
                    JygzStatusData data = new JygzStatusData();
                    data.setStatusName("默认");
                    spinner2Beenlist.add(data);
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
                spinner2Adpater.notifyDataSetChanged();
            }
        };

        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(object, "GetBroadCastNoticeRegion", callback);
            XUtil.postjsondata(object2, "GetBroadCastNoticeStatus", callback2);
        }else {
            XUtil.postjsondatasj(object, "GetBroadCastNoticeRegion", callback);
            XUtil.postjsondatasj(object2, "GetBroadCastNoticeStatus", callback2);
        }
        getdataok();
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
        String  obj = "{\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"key\":\""+keyword+"\"}";
        if (RegionValue != "" && StatusValue != "") {
            obj = "{\"region\":\"" + RegionValue + "\",\"status\":\"" + StatusValue + "\",\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"key\":\""+keyword+"\"}";
        }else if (StatusValue != ""){
            obj = "{\"status\":\"" + StatusValue + "\",\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"key\":\""+keyword+"\"}";
        }else if(RegionValue != ""){
            obj = "{\"region\":\"" + RegionValue + "\",\"pageIndex\":\""+pageIndex+"\",\"pageSize\":\""+pageSize+"\",\"key\":\""+keyword+"\"}";
        }
        JSONObject getjsonobj = JsonUtils.getjsonobj(obj);
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<JygzBean> getbean = JsonUtils.jsonToList(recordList.toString(), JygzBean[].class);

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
        };
        if("com.a21zhewang.constructionapp3".equals(AppUtils.getPackageName(getApplicationContext()))
                || "com.a21zhewang.constructionapp.cqjg".equals(AppUtils.getPackageName(getApplicationContext()))){
            XUtil.postjsondata(getjsonobj, "GetBroadCastNoticeList", callback);
        }else {
            XUtil.postjsondatasj(getjsonobj, "GetBroadCastNoticeList", callback);
        }
    }

}
