package com.a21zhewang.constructionapp.ui.xmxx;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.FbBean;
import com.a21zhewang.constructionapp.bean.FbBean.*;
import com.a21zhewang.constructionapp.bean.FbBean.EtpInfoListBean.UserInfoListBean;
import com.zhy.autolayout.utils.AutoUtils;


import java.util.List;

/**
 * Created by zhewang_ljw on 2017/5/25.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public  abstract  class FbAdapter extends BaseExpandableListAdapter {

    private List<FbBean.EtpInfoListBean> etpInfoList;
    private Context mContext;

   public FbAdapter( Context mContext,List<FbBean.EtpInfoListBean> etpInfoList){
        this.mContext=mContext;
       this.etpInfoList=etpInfoList;
    }
    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return etpInfoList.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getUserInfoList().size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public EtpInfoListBean getGroup(int groupPosition) {
        return etpInfoList.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public UserInfoListBean getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getUserInfoList().get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     *
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     *
     * @param groupPosition the position of the group for which the View is
     *                      returned
     * @param isExpanded    whether the group is expanded or collapsed
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getGroupView(int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=  LayoutInflater.from(mContext).inflate(R.layout.group_item,null,false);
            convertView.setTag(new Groupviewholder(convertView));
        }
        initGroupviewholder(getGroup(groupPosition),(Groupviewholder) convertView.getTag(),isExpanded);
        AutoUtils.auto(convertView);
        return convertView;
    }

    private void initGroupviewholder(EtpInfoListBean etpInfoListBean,Groupviewholder groupviewholder,boolean is) {

        String etpShortName = etpInfoListBean.getEtpShortName();
        List<UserInfoListBean> userInfoList = etpInfoListBean.getUserInfoList();

        groupviewholder.textView.setText(etpShortName +"("+ userInfoList.size()+")");
        if(is){
            groupviewholder.img.setImageResource(R.mipmap.arrowup);
        }else{
            groupviewholder.img.setImageResource(R.mipmap.arrowdown);
        }
    }

    class Groupviewholder{
         TextView textView;
         ImageView img;
        public Groupviewholder(View view){
            textView= (TextView) view.findViewById(R.id.group_item_text);
            img= (ImageView) view.findViewById(R.id.group_item_img);
        }

}
    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     *                      returned) within the group
     * @param isLastChild   Whether the child is the last child within the group
     * @param convertView   the old view to reuse, if possible. You should check
     *                      that this view is non-null and of an appropriate type before
     *                      using. If it is not possible to convert this view to display
     *                      the correct data, this method can create a new view. It is not
     *                      guaranteed that the convertView will have been previously
     *                      created by
     *                      {@link #getChildView(int, int, boolean, View, ViewGroup)}.
     * @param parent        the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=  LayoutInflater.from(mContext).inflate(R.layout.child_item,null,false);
            convertView.setTag(new Childviewholder(convertView));
        }
        initChildviewholder(getChild(groupPosition,childPosition),(Childviewholder) convertView.getTag());
        AutoUtils.auto(convertView);
        return convertView;
    }

    public abstract  void initChildviewholder(UserInfoListBean userInfoListBean,Childviewholder childviewholder);

    public class Childviewholder{
        public  TextView tv1;
        public TextView tv2;
        public  TextView tv3;
        public  TextView tv4;
        public  TextView line;

        Childviewholder(View view){
            tv1= (TextView) view.findViewById(R.id.child_item_tv1);
            tv2= (TextView) view.findViewById(R.id.child_item_tv2);
            tv3= (TextView) view.findViewById(R.id.child_item_tv3);
            tv4= (TextView) view.findViewById(R.id.child_item_tv4);
            line= (TextView) view.findViewById(R.id.child_item_tv_line);

        }

    }
    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
