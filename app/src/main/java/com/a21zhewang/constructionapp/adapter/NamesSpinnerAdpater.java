package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by zhewang on 2016/10/14.
 */

public abstract class NamesSpinnerAdpater<T> extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
   private List<T> names;

    public List<T> getNames() {
        return names;
    }

    public void setNames(List<T> names) {
        this.names = names;
    }

    public NamesSpinnerAdpater(Context context, List<T> names) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.names = names;
    }

    public NamesSpinnerAdpater(Context context, T[] names) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.names = Arrays.asList(names);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public T getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.namesspinner_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());

        return convertView;
    }

    public abstract void setinit(T name, ViewHolder mViewHolder);

    public class ViewHolder {
       public TextView nametextview;

        public ViewHolder(View view) {
            nametextview = (TextView) view.findViewById(R.id.namesspinner_item_tv);

        }

    }

    public void initializeViews(T name, ViewHolder mViewHolder) {
        setinit(name, mViewHolder);
    }
public void adddata(List<T> names){
    this.names.addAll(names);
    notifyDataSetChanged();
}
    public void adddata(T name){
        this.names.add(name);
        notifyDataSetChanged();
    }
    public void replcedata(List<T> names){
        this.names.clear();
//        notifyDataSetChanged();
        if (names!=null&&names.size()>0)
        this.names.addAll(names);
        notifyDataSetChanged();
    }

}
