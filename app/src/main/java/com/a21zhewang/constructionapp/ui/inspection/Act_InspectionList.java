package com.a21zhewang.constructionapp.ui.inspection;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.SeekBar;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.bean.InspectionListBean;
import com.a21zhewang.constructionapp.bean.JygzProjectType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.utils.Act_BaseListView;
import com.a21zhewang.constructionapp.utils.Act_BaseListView6;
import com.a21zhewang.constructionapp.utils.AppUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.umeng.analytics.MobclickAgent;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class Act_InspectionList extends Act_BaseListView<JygzProjectType, JygzProjectType,InspectionListBean> {
    private String ProjectTypeKey ="";//类型ID（如果不传则是空字符串）
    private String typeFatherID="";//父类类型ID（如果不传则是空字符串）
    private String typeGraFaID="";//祖类类型ID（如果不传则是空字符串）
    private String platformKey="";//处理情况（如果不传则是空字符串）
    private String etpID="";//整改企业ID
    private String keyword="";
    private int pageIndex=0;//页数，从0开始
    private int onClickIndex=-1;
    private Dialog loadingDialog;


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
    public void spinner1setinit(JygzProjectType name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getValue());
    }

    @Override
    public void spinner2setinit(JygzProjectType name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getValue());
    }

    @Override
    public void thisconvert(ViewHolder holder, final InspectionListBean listBean, final int position) {
        holder.setText(R.id.project_name,listBean.getProShortName());
        holder.setText(R.id.project_type,"检查类型："+listBean.getCheckCategory());
        holder.setText(R.id.rank_rate,"检查时间："+listBean.getCreateTime());
        holder.setText(R.id.complate_people,"检查人员："+listBean.getCreatorName());
//        final int inspectionProgressNum = listBean.getInspectionProgress();
//        holder.setText(R.id.seekbar,inspectionProgressNum+"");
//        seekBar.setSecondaryProgress(inspectionProgressNum);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=position;
                Intent i;
                if(listBean.getDepotLevel() == 3){
                    i = new Intent(Act_InspectionList.this,Act_InspectionTabDetail.class);
                }else {
                    i = new Intent(Act_InspectionList.this,Act_InspectionDetail.class);
                    i.putExtra("sectionId","0");// 0 二级  !0 三级
                }
                i.putExtra("status","3");
                i.putExtra("projectID",listBean.getProjectID());
                i.putExtra("recordId",listBean.getRecordID());
                i.putExtra("proShortName",listBean.getProShortName());
                i.putExtra("createTime",listBean.getCreateTime());
                i.putExtra("checkCategory",listBean.getCheckCategory());
                i.putExtra("approveType",listBean.getApproveType());
                i.putExtra("creatorUnit",listBean.getCreatorUnit());
                i.putExtra("creatorName",listBean.getCreatorName());
                i.putExtra("dataType",listBean.getDataType());
                i.putExtra("riskCount",listBean.getRiskCount());
                i.putExtra("area",listBean.getArea());
                i.putExtra("isAddNotice",listBean.isAddNotice());
                i.putExtra("canMakeEasyNotice",listBean.isCanMakeEasyNotice());
                i.putExtra("inspectionProgress",listBean.getInspectionProgress());
                startActivity(i);
                MobclickAgent.onPageEnd("专项巡查");
            }
        });
    }

    /**
     * @return 设置标题文字
     */
    @Override
    public String settiltile() {
        return "专项巡查";
    }

    /**
     * @return 设置第一个下拉文字
     */
    @Override
    public String setspinner1str() {
        return "项目类型";
    }

    /**
     * @return 设置第二个下拉文字
     */
    @Override
    public String setspinner2str() {
        return "平台公司";
    }

    @Override
    public MySpinner.MySpinnerAPI setspinner1onclick() {
        MySpinner.MySpinnerAPI mySpinnerAPI=new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                listBeenlist.clear();
                JygzProjectType jygzProjectType = spinner1Beenlist.get(position);
                ProjectTypeKey = jygzProjectType.getKey();
                String ProjectTypeValue = jygzProjectType.getValue();
                if (ProjectTypeValue.equals("默认")) {
                    ProjectTypeValue="项目类型";
                    ProjectTypeKey="";
                }
                getlistdata();
                return ProjectTypeValue;
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
                JygzProjectType jygzProjectType = spinner2Beenlist.get(position);
                platformKey = jygzProjectType.getKey();
                String platformValue = jygzProjectType.getValue();
                if (platformValue.equals("默认")) {
                    platformValue="平台公司";
                    platformKey="";
                }
                getlistdata();
                return platformValue;
            }
        };
        return mySpinnerAPI;
    }

    @Override
    public String setaddbtnvisiable() {
        return"";
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
    }

    /**
     * 获取下拉列表数据
     */
    private void getspinnerdata() {
        JSONObject object = JsonUtils.getjsonobj("{\"getDataType\":\"" + "projectType" + "\"}");
        JSONObject object2 = JsonUtils.getjsonobj("{\"getDataType\":\"" + "platform" + "\"}");
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    spinner1Beenlist.clear();
                    List<JygzProjectType> jygzProjectTypes = JsonUtils.jsonToList(array.toString(),JygzProjectType[].class);
                    if (jygzProjectTypes!=null&&jygzProjectTypes.size()>0){
                        spinner1Beenlist.addAll(jygzProjectTypes);
                    }
                    JygzProjectType data = new JygzProjectType();
                    data.setValue("默认");
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
        MyCallBack callback2 = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    spinner2Beenlist.clear();
                    List<JygzProjectType> jygzProjectPlatform = JsonUtils.jsonToList(array.toString(),JygzProjectType[].class);
                    if (jygzProjectPlatform!=null&&jygzProjectPlatform.size()>0){
                        spinner2Beenlist.addAll(jygzProjectPlatform);
                    }
                    JygzProjectType data = new JygzProjectType();
                    data.setValue("默认");
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
            XUtil.postjsondata(object, "BroadCastExaminationType", callback);
            XUtil.postjsondata(object2, "BroadCastExaminationType", callback2);
        }else {
            XUtil.postjsondatasj(object, "BroadCastExaminationType", callback);
            XUtil.postjsondatasj(object2, "BroadCastExaminationType", callback2);
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
     * listType   2：待检查  3:检查记录
     * listStatus 0:全部 1：日 2：周 3：月 4：季度 5：半年  6：全年
     */
    private void getlistdata(int pageIndex,int pageSize) {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        loadingDialog.show();
        String obj = "{\"listType\":\"" + 3 + "\",\"keyword\":\"" + keyword + "\",\"listStatus\":\"" + 0 +  "\"," +
                "\"pageIndex\":\"" +  pageIndex + "\",\"pageSize\":\"" +  pageSize + "\",\"projectType\":\"" + ProjectTypeKey + "\",\"etpId\":\"" + platformKey + "\"}";
        JSONObject getjsonobj = JsonUtils.getjsonobj(obj);
        MyCallBack callback = new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    if(!"{}".equals(res)){
                        JSONObject jsonObject = new JSONObject(res);
                        JSONArray recordList = jsonObject.getJSONArray("recordList");
                        List<InspectionListBean> getbean = JsonUtils.jsonToList(recordList.toString(), InspectionListBean[].class);
                        if (getbean != null && getbean.size() > 0) {
                            listBeenlist.addAll(getbean);
                        }
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
            XUtil.postjsondata(getjsonobj, "GetListBroadCastExamination", callback);
        }else {
            XUtil.postjsondatasj(getjsonobj, "GetListBroadCastExamination", callback);
        }
    }


}
