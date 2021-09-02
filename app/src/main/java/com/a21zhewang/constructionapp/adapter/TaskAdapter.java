package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.TaskBean;
import com.a21zhewang.constructionapp.bean.TaskCompany;
import java.util.List;


/**
 * Created by Administrator on 2021/8/2.
 */

public class TaskAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener{
    private Context context;
    private List<TaskBean> TaskBeanList;
    private List<TaskCompany> childgroups;

    public TaskAdapter(Context context, List<TaskBean> groups) {
        this.context = context;
        this.TaskBeanList = groups;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return TaskBeanList.get(groupPosition).getCompanyList().get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return TaskBeanList.get(groupPosition).getChildrenCount();
    }

    public Object getGroup(int groupPosition) {
        return TaskBeanList.get(groupPosition);
    }

    public int getGroupCount() {
        return TaskBeanList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /** 設定 Group 資料 */
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TaskBean TaskBean = (TaskBean) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.adapter_task_group_item_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
        tv.setText(TaskBean.getReceiveTitle());

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbGroup);
        checkBox.setChecked(TaskBean.isChecked());
        // 點擊 CheckBox 時，將狀態存起來
        checkBox.setOnClickListener(new TaskAdapter.Group_CheckBox_Click(groupPosition));

        return convertView;
    }

    /** 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態 */
    class Group_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public void onClick(View v) {
            TaskBeanList.get(groupPosition).toggle();

            // 將 Children 的 isChecked 全面設成跟 Group 一樣
            int childrenCount = TaskBeanList.get(groupPosition).getChildrenCount();
            boolean groupIsChecked = TaskBeanList.get(groupPosition).isChecked();
            for (int i = 0; i < childrenCount; i++)
                TaskBeanList.get(groupPosition).getChildItem(i).setChecked(groupIsChecked);
            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
            notifyDataSetChanged();
        }
    }

    /** 設定 Children 資料 */
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TaskCompany info = TaskBeanList.get(groupPosition).getChildItem(childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_task_child_item_layout, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
        tv.setText(info.getEtpName());

        // 重新產生 CheckBox 時，將存起來的 isChecked 狀態重新設定
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chbChild);
        checkBox.setChecked(info.isChecked());
        // 點擊 CheckBox 時，將狀態存起來
        checkBox.setOnClickListener(new TaskAdapter.Child_CheckBox_Click(groupPosition, childPosition));

        return convertView;
    }

    /** 勾選 Child CheckBox 時，存 Child CheckBox 的狀態 */
    class Child_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public void onClick(View v) {
            handleClick(childPosition, groupPosition);
        }
    }
    /*** 全选、全不选*/
    public void initDate(boolean bool) {
        for (int i = 0; i < TaskBeanList.size(); i++) {
            TaskBeanList.get(i).setChecked(bool);
            childgroups = TaskBeanList.get(i).getCompanyList();
            for(int j=0;j<childgroups.size();j++){
                childgroups.get(j).setChecked(bool);
            }
        }
    }
    public void handleClick(int childPosition, int groupPosition) {
        TaskBeanList.get(groupPosition).getChildItem(childPosition).toggle();

        // 檢查 Child CheckBox 是否有全部勾選，以控制 Group CheckBox
        int childrenCount = TaskBeanList.get(groupPosition).getChildrenCount();
        boolean childrenAllIsChecked = true;
        for (int i = 0; i < childrenCount; i++) {
            if (!TaskBeanList.get(groupPosition).getChildItem(i).isChecked())
                childrenAllIsChecked = false;
        }

        TaskBeanList.get(groupPosition).setChecked(childrenAllIsChecked);

        // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
        notifyDataSetChanged();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        handleClick(childPosition, groupPosition);
        return true;
    }
}
