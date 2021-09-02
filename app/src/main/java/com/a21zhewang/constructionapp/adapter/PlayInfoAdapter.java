package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.PlayInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyf on 2019/3/1.
 * 描述：是二级显示列表的adapter
 */
public class PlayInfoAdapter extends BaseExpandableListAdapter {
    private List<PlayInfo> playlist = new ArrayList<>();
    private Context context;

    public PlayInfoAdapter(Context context, List<PlayInfo> playlist){
        this.context = context;
        this.playlist = playlist;
    }
    @Override
    public int getGroupCount() {
        return playlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return playlist.get(groupPosition).getUserList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return playlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return playlist.get(groupPosition).getUserList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getChildView(final int groupPosition,
                             final int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.peopleinfo_adapter_child_item,parent,false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView focus = (TextView) convertView.findViewById(R.id.focus);
        TextView produl = (TextView) convertView.findViewById(R.id.produl);
        name.setText(playlist.get(groupPosition).getUserList().get(childPosition).getUserName());
        focus.setText(playlist.get(groupPosition).getUserList().get(childPosition).getUserName());
        if(playlist.get(groupPosition).getUserList().get(childPosition).getIsKey() == 1){
            focus.setText("✔");
        }else {
            focus.setText("✖");
        }
        if(playlist.get(groupPosition).getUserList().get(childPosition).getIsProg()== 1){
            produl.setText("✔");
        }else {
            produl.setText("✖");
        }
        return convertView;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.peopleinfo_adapter_group_item,parent,false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.tv_grid_item_title);
        title.setText(playlist.get(groupPosition).getEtpFullName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}