package com.a21zhewang.constructionapp.ui.task;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.SeekBar;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TaskListBean;
import com.a21zhewang.constructionapp.bean.TaskTypesStatus;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.utils.Act_TaskBaseListView;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;



public class Act_TaskList extends Act_TaskBaseListView<TaskTypesStatus, TaskTypesStatus,TaskListBean> {
    private String statusValue="-1";//-1：所有
    private String taskTypeValue ="-1";//-1：所 有
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
    public void spinner1setinit(TaskTypesStatus name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getName());
    }

    @Override
    public void spinner2setinit(TaskTypesStatus name, NamesSpinnerAdpater.ViewHolder mViewHolder) {
        mViewHolder.nametextview.setText(name.getName());
    }

    @Override
    public void thisconvert(ViewHolder holder, final TaskListBean listBean, final int position) {
        holder.setText(R.id.tv_title,listBean.getTitle());
//        holder.setText(R.id.tv_receiver_project,listBean.getProjectName());
        if(listBean.getTaskType() == 1){
            holder.setText(R.id.tv_type,"通知文件");
            holder.setVisible(R.id.tv_status,false);
            holder.setText(R.id.tv_people,"发起人："+listBean.getCreateName());
        }else {
            holder.setVisible(R.id.tv_status,true);
            holder.setText(R.id.tv_type,"任务督办");
            holder.setText(R.id.tv_people,"督办人："+listBean.getRectUserName());
        }
        holder.setText(R.id.tv_time,listBean.getCreateDate());
        if(listBean.getStatus() == 1){
            holder.setText(R.id.tv_status,"未读");
        }else if(listBean.getStatus() == 2){
            holder.setText(R.id.tv_status,"处理中");
        }else if(listBean.getStatus() == 3){
            holder.setText(R.id.tv_status,"待审核");
        }else { //4：已闭合
            holder.setText(R.id.tv_status,"已闭合");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=position;
                Intent i = new Intent(Act_TaskList.this,Act_TaskDetail.class);
//                i.putExtra("title",listBean.getTitle());
//                i.putExtra("taskType",listBean.getTaskType());
//                i.putExtra("status",listBean.getStatus());
//                i.putExtra("projectName",listBean.getProjectName());
                i.putExtra("recordId",listBean.getRecordId());
                startActivity(i);
            }
        });
    }

    /**
     * @return 设置标题文字
     */
    @Override
    public String settiltile() {
        return "任务督办";
    }

    /**
     * @return 设置第一个下拉文字
     */
    @Override
    public String setspinner1str() {
        return "督办类型";
    }

    /**
     * @return 设置第二个下拉文字
     */
    @Override
    public String setspinner2str() {
        return "督办状态";
    }

    @Override
    public MySpinner.MySpinnerAPI setspinner1onclick() {
        MySpinner.MySpinnerAPI mySpinnerAPI=new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                listBeenlist.clear();
                TaskTypesStatus TaskTypesStatus = spinner1Beenlist.get(position);
                taskTypeValue = TaskTypesStatus.getValue();
                String taskTypeName = TaskTypesStatus.getName();
                if (taskTypeName.equals("默认")) {
                    taskTypeName="督办类型";
                    taskTypeValue="-1";
                }
                getlistdata();
                return taskTypeName;
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
                TaskTypesStatus TaskTypesStatus = spinner2Beenlist.get(position);
                statusValue = TaskTypesStatus.getValue();
                String statusName = TaskTypesStatus.getName();
                if (statusName.equals("默认")) {
                    statusName="督办状态";
                    statusValue="-1";
                }
                getlistdata();
                return statusName;
            }
        };
        return mySpinnerAPI;
    }

    @Override
    public String setaddbtnvisiable() {
        return"TaskSuperviseAddBtn";
    }

    /**
     * @param // 设置新增按钮的点击事件
     */
    @Override
    public void setaddbtnonclick() {
        activityBaselistviewRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=0;
                startActivity(new Intent(Act_TaskList.this,Act_AddTask.class));
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
        XUtil.postjsondatasj(JsonUtils.getjsonobj("{\"getDataType\":\"" + "TaskType" + "\"}"), "GetTaskSuperviseTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    spinner1Beenlist.clear();
                    List<TaskTypesStatus> TaskTypesStatuss = JsonUtils.jsonToList(array.toString(),TaskTypesStatus[].class);
                    if (TaskTypesStatuss!=null&&TaskTypesStatuss.size()>0){
                        spinner1Beenlist.addAll(TaskTypesStatuss);
                    }
                    TaskTypesStatus data = new TaskTypesStatus();
                    data.setName("默认");
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
        });
        XUtil.postjsondatasj(JsonUtils.getjsonobj("{\"getDataType\":\"" + "status" + "\"}"), "GetTaskSuperviseTypes", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    spinner2Beenlist.clear();
                    List<TaskTypesStatus> jygzProjectPlatform = JsonUtils.jsonToList(array.toString(),TaskTypesStatus[].class);
                    if (jygzProjectPlatform!=null&&jygzProjectPlatform.size()>0){
                        spinner2Beenlist.addAll(jygzProjectPlatform);
                    }
                    TaskTypesStatus data = new TaskTypesStatus();
                    data.setName("默认");
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
        });
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
        String obj = "{\"taskType\":\"" + taskTypeValue + "\",\"key\":\"" + keyword + "\",\"status\":\"" + statusValue +  "\"," +
                "\"pageIndex\":\"" +  pageIndex + "\",\"pageSize\":\"" +  pageSize + "\"}";
        JSONObject getjsonobj = JsonUtils.getjsonobj(obj);
        XUtil.postjsondatasj(getjsonobj,"GetTaskSuperviseList", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    loadingDialog.dismiss();
                    if(!"{}".equals(res)){
                        JSONObject jsonObject = new JSONObject(res);
                        JSONArray recordList = jsonObject.getJSONArray("recordList");
                        List<TaskListBean> getbean = JsonUtils.jsonToList(recordList.toString(), TaskListBean[].class);
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
        });
    }

}
