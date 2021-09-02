package com.a21zhewang.constructionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.RiskQueryLaunProjectList;
import com.a21zhewang.constructionapp.customview.HorzinonlChartView;

import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class ChartAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<RiskQueryLaunProjectList> mCities;

    public ChartAdapter(Context mContext, List<RiskQueryLaunProjectList> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
    }




    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public RiskQueryLaunProjectList getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.lv_item, parent, false);
            holder = new CityViewHolder();
            holder.number1 = (TextView) view.findViewById(R.id.tv_number_item1);
            holder.number2 = (TextView) view.findViewById(R.id.tv_number_item2);
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            holder.en1 = (HorzinonlChartView) view.findViewById(R.id.hor1);
            holder.en2 = (HorzinonlChartView) view.findViewById(R.id.hor2);
            holder.send_linear =(LinearLayout)view.findViewById(R.id.send_linear);
            holder.action_linear =(LinearLayout)view.findViewById(R.id.action_linear);
            view.setTag(holder);
        } else {
            holder = (CityViewHolder) view.getTag();
        }
        final RiskQueryLaunProjectList city = mCities.get(position);
        holder.name.setText(city.getProjectName());
//        if (city.getRiskQueryLaun() == 0) {
//            holder.en1.setNumber(10);
//            holder.number1.setText(0);
//        }
//        else if (city.getRiskQueryPass() == 0) {
//            holder.en2.setNumber(10);
//            holder.number2.setText(0);
//        } else
        holder.en1.setNumber(city.getRiskQueryLaun());
        holder.en2.setNumber(city.getRiskQueryPass());
        holder.number1.setText(city.getRiskQueryLaun() + "");
        holder.number2.setText(city.getRiskQueryPass() + "");
        return view;
    }

    public static class CityViewHolder {
        TextView number1;
        TextView number2;
        TextView name;
        HorzinonlChartView en1;
        HorzinonlChartView en2;
        LinearLayout send_linear;
        LinearLayout action_linear;
    }
}
