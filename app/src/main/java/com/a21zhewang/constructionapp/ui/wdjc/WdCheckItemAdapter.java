package com.a21zhewang.constructionapp.ui.wdjc;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/7.
 *
 * @auto
 */

public class WdCheckItemAdapter <T extends CheckType> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public static final int LEVEL_THREE = 2;
    public static final int LEVEL_FOUR = 3;
    public WdCheckItemAdapter(Context context,List<T> data) {
        super(data);
        openLoadAnimation();
       // isFirstOnly(false);
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
//        if (data!=null){
//            for (int i = 0; i < data.size(); i++) {
//                T checkTypeOne = data.get(i);
//                checkTypeOne.setSubItems(checkTypeOne.getCheckItemList());
//            }
//        }
        addItemType(LEVEL_THREE, R.layout.setchild_item);
        addItemType(LEVEL_FOUR, R.layout.setchilditem_item);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(final BaseViewHolder helper, final T item) {
        switch (item.getItemType()){
            case LEVEL_THREE:
                hasChildSelet(item);
                if (item.isExpanded()){
                    helper.setImageResource(R.id.setchild_item_isExpand,R.mipmap.jianhao);
                }else{
                    helper.setImageResource(R.id.setchild_item_isExpand,R.mipmap.addico);
                }
                helper.setChecked(R.id.setchild_item_CheckBox,item.getStatus()>0);
                helper.setText(R.id.setchild_item_title,item.getDicName());
                final CheckBox checkBox=  helper.getView(R.id.setchild_item_CheckBox);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = checkBox.isChecked();
                        item.setStatus(checked?1:0);
                        setchindseletors(item.getCheckItemList(),checked);

                    }
                });
                if (item.getStatus()== CheckType.Status.SOMESELECT){
                    checkBox.setButtonDrawable(mContext.getDrawable(R.drawable.someselect));
                }else{
                    checkBox.setButtonDrawable(mContext.getDrawable(R.drawable.allselect));
                    // checkBox.setBackgroundResource(R.drawable.ic_someselect);
                }

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  PublicUtils.toast("点击了");
                        if (item.isExpanded()){
                            helper.setImageResource(R.id.setchild_item_isExpand,R.mipmap.jianhao);
                        }else{
                            helper.setImageResource(R.id.setchild_item_isExpand,R.mipmap.addico);
                        }
                        setExpand(helper.getAdapterPosition(),item);

                    }
                });

                break;
            case LEVEL_FOUR:
                helper.setText(R.id.setchild_item_title,item.getDicName());
                final CheckBox box=helper.getView(R.id.setchilditem_item_CheckBox);
                box.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = box.isChecked();
                        item.setStatus(checked?1:0);
                        notifyDataSetChanged();
                    }
                });
                helper.setChecked(R.id.setchilditem_item_CheckBox,item.getStatus()>0);
                break;
            default:
                break;
        }
    }

    /**
     * @param pos  展开 或收起
     * @param item
     */
    private void setExpand(int pos, T item) {
        if (item.isExpanded()) {
            collapse(pos, true);

        } else {
            expand(pos, true);

        }
    }

    public void collapseAll() {
        for (int i = mData.size() - 1; i >= 0 + getHeaderLayoutCount(); i--) {
            collapse(i, false, true);
        }
    }

    @NonNull
    @Override
    public List<T> getData() {
        return super.getData();
    }

    /**
     * 设置list全选反选
     *
     * @param childList
     * @param selector
     */
    private void setchindseletors(List<? extends CheckType> childList, boolean selector) {
        if (childList != null) {
            for (int i = 0; i < childList.size(); i++) {
                childList.get(i).setStatus(selector?1:0);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 判断又没有选中的子项 如果有就为选中状态
     *
     * @param checkType
     */
    public void hasChildSelet(CheckType checkType) {

        List<? extends CheckType> childrens = checkType.getCheckItemList();
        int j=0;
        if (childrens != null) {
            int size = childrens.size();
            for (int i = 0; i < size; i++) {

                Boolean aBoolean = childrens.get(i).getStatus()>0;
                if (aBoolean) {
                    j++;
                    //  break;
                }

//                if (i == size - 1 && !aBoolean) {
//                    checkType.setSelector(false);
//                }else{
//                    checkType.setSelector(true);
//                }
            }
            if (j==size){
          //      checkType.setSelector(true);
                checkType.setStatus(CheckType.Status.ALLSELECT);

            }else if (j>0){
             //   checkType.setSelector(true);
                checkType.setStatus(CheckType.Status.SOMESELECT);
            }else{
              //  checkType.setSelector(false);
                checkType.setStatus(CheckType.Status.NOSELECT);
            }
        } else {
          //  checkType.setSelector(false);
            checkType.setStatus(CheckType.Status.NOSELECT);
        }

    }
}
