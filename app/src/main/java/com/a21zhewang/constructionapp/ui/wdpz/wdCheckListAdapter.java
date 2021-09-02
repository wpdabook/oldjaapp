package com.a21zhewang.constructionapp.ui.wdpz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeThree;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.wdpz.SetwdCheckChildActivity;
import com.a21zhewang.constructionapp.ui.wdpz.SetwdCheckListActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/6.
 *
 * @auto
 */

public class wdCheckListAdapter<T extends CheckType> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

    private Context mContext;
    public static final int LEVEL_ONE = 0;
    public static final int LEVEL_TWO = 1;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public wdCheckListAdapter(Context context, List<T> data) {
        super(data);
        this.mContext = context;
        openLoadAnimation();
        // isFirstOnly(false);
        openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        if (data!=null){
//            for (int i = 0; i < data.size(); i++) {
//                T checkTypeOne = data.get(i);
//                checkTypeOne.setSubItems(checkTypeOne.getCheckItemList());
//            }
//        }
        addItemType(LEVEL_ONE, R.layout.setchecklist_level0_item);
        addItemType(LEVEL_TWO, R.layout.setchecklist_level1_item);

    }


    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(final BaseViewHolder helper, final T item) {
        switch (item.getItemType()) {
            case LEVEL_ONE:

                hasChildSelet(item);
                //  item.setSelector(isSelector(item));
                Log.e("select:",item.getStatus()+"") ;
                setimg(helper, item);
                helper.setText(R.id.setchecklist_level0_item_title, item.getDicName());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //  PublicUtils.toast("点击了");
                        setimg(helper, item);
                        setExpand(helper.getAdapterPosition(), item);
                    }
                });

                break;
            case LEVEL_TWO:
                //判断又没有选中的子项 如果有就为选中状态
                hasChildSelet(item);
                Log.e("Childselect:",item.getStatus()+"") ;
                final CheckBox checkBox = helper.getView(R.id.setchecklist_level1_item_checkbox);

                checkBox.setChecked(item.getStatus()>0);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean checked = checkBox.isChecked();
                        //   PublicUtils.toast("是否勾选：" + checked);
                        item.setStatus(checked?1:0);
                        List<T> childList = item.getCheckItemList();
                        SetwdCheckListActivity.checkType = item;
                        if (childList != null) {
                            setchindseletors(childList, checked);
                        }else{
                            if (checked){
                                getlistdata();
                            }else{
                                notifyDataSetChanged();
                            }
                        }



                    }
                });
                if (item.getStatus()== CheckType.Status.SOMESELECT){
                    checkBox.setButtonDrawable(mContext.getDrawable(R.drawable.someselect));
                }else{
                    checkBox.setButtonDrawable(mContext.getDrawable(R.drawable.allselect));
                    // checkBox.setBackgroundResource(R.drawable.ic_someselect);
                }
                helper.setText(R.id.setchecklist_level1_item_title, item.getDicName());
                helper.getView(R.id.setchecklist_level1_item_pz).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetwdCheckListActivity.checkType = item;
                        mContext.startActivity(new Intent(mContext, SetwdCheckChildActivity.class));
                    }
                });
                break;
        }

    }


    private void setimg(BaseViewHolder helper, T item) {
        if (item.isExpanded()){
            helper.setImageResource(R.id.setchecklist_level0_item_ImageView,R.mipmap.ssup);
        }else{
            helper.setImageResource(R.id.setchecklist_level0_item_ImageView,R.mipmap.ssdown);
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

    /**
     * 折叠全部
     */
    public void collapseAll() {
        for (int i = mData.size() - 1; i >= 0 + getHeaderLayoutCount(); i--) {
            collapse(i, false, true);
        }
    }
    /**
     * 展开全部
     */
    public void expandAll() {
        for (int i = mData.size() - 1; i >= 0 + getHeaderLayoutCount(); i--) {
            expand(i, false, true);
        }
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
                CheckType checkType = childList.get(i);
                checkType.setStatus(selector?1:0);
                setchindseletors(checkType.getCheckItemList(),selector);

            }
            notifyDataSetChanged();
        }
    }
    private boolean isSelector(CheckType checkType){
        List<? extends CheckType> childrens = checkType.getCheckItemList();
        if (childrens!=null&&childrens.size()>0){
            int size = childrens.size();
            for (int i = 0; i < size; i++) {
                CheckType type= childrens.get(i);
                Boolean aBoolean = type.getStatus()>0;
                if (aBoolean)return aBoolean;
                isSelector(type);
            }
        }
        return false;


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
                //   checkType.sett(true);
                checkType.setStatus(CheckType.Status.ALLSELECT);

            }else if (j>0){
                // checkType.setSelector(true);
                checkType.setStatus(CheckType.Status.SOMESELECT);
            }else{
                //  checkType.setSelector(false);
                checkType.setStatus(CheckType.Status.NOSELECT);
            }
        } else {
            // checkType.setSelector(false);
            checkType.setStatus(CheckType.Status.NOSELECT);
        }

    }

    private void getlistdata() {
        if (SetwdCheckListActivity.checkType==null)return;
        if (SetwdCheckListActivity.checkType.getCheckItemList()!=null)return;
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"checkItemList\":[{\"dicID\":\"" + SetwdCheckListActivity.checkType.getDicID() + "\"}]}");
        XUtil.postjsondata(getjsonobj, "RiskParentLibraryInitialize", new MyCallBack() {
            @Override
            public void onResule(String res) {
                JSONArray array = null;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray jsonArray = jsonObject.getJSONArray("checkItemList");
                    JSONObject jsobobj = jsonArray.getJSONObject(0);
                    array = jsobobj.getJSONArray("checkItemList");
                } catch (JSONException e) {
                    PublicUtils.toastFalse("程序发生了预期之外的错误！请重启程序试一试");
                }
                List<CheckTypeThree> checkItemList = JsonUtils.jsonToArrayList(array.toString(), CheckTypeThree.class);
                setchindseletors(checkItemList, true);
                SetwdCheckListActivity.checkType.setCheckItemList(checkItemList);
                notifyDataSetChanged();

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {
                notifyDataSetChanged();
            }
        });
    }
}
