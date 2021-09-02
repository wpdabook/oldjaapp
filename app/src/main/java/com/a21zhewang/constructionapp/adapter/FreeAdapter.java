package com.a21zhewang.constructionapp.adapter;

import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 基类适配器
 * <p>
 * 使用该适配器可以只实现getView方法即可<br>
 * 允许setResouce类型为List或org.json.JSONArray
 * </p>
 */
public class FreeAdapter extends BaseAdapter {
    /**
     * context
     */
    protected Context context;

    /**
     * inflater
     */
    protected LayoutInflater inflater;

    /**
     * list List类型源数
     */
    protected List<?> list;

    /**
     * jsonArray org.json.JSONArray类型源数
     */
    protected JSONArray jsonArray;

    /**
     * sourceType 源数据类
     */
    private SourceStatus sourceType;

    public FreeAdapter() {
    }

    /**
     * 源数据类型集
     */
    private enum SourceStatus {
        /**
         * LIST list类型
         */
        LIST,

        /**
         * JSON_ARRAY org.json.JSONArray类型
         */
        JSON_ARRAY
    }

    ;

    /**
     * @param context context
     */
    public FreeAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 设置适配器的元数据，这里用的是list
     *
     * @param list void
     */
    public void setResouce(List<?> list) {
        this.list = list;
        sourceType = SourceStatus.LIST;

    }

    /**
     * 设置适配器的元数据，这里用的是jsonarray
     *
     * @param jsonArray void
     */
    public void setResouce(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        sourceType = SourceStatus.JSON_ARRAY;
    }

    @Override
    public int getCount() {
        if (sourceType == null)
            return 0;
        switch (sourceType) {
            case LIST:
                return list != null ? list.size() : 0;
            case JSON_ARRAY:
                return jsonArray != null ? jsonArray.length() : 0;
            default:
                return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        try {
            switch (sourceType) {
                case LIST:
                    return list.get(position);
                case JSON_ARRAY:
                    return jsonArray.getJSONObject(position);
                default:
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    /**
     * 获取item view
     *
     * @param convertView
     * @param id          item id
     * @return View
     */
    protected View getItemView(View convertView, int id) {
        if (convertView == null)
            convertView = inflater.inflate(id, null);
        return convertView;
    }


    /**
     * 根据传入的控件id获取控件。替代了viewholder
     *
     * @param convertView
     * @param id
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected static <T extends View> T findViewById(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
