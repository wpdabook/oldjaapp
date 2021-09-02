package com.a21zhewang.constructionapp.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseLazyFragment;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 检查完成率
 * Created by wp-pc on 2020/4/4.
 */

public class InspectionCompletionFragment extends Fragment {

    private ListView listView;
    private TextView titlename;
    private MyBaseAdapter adapter;
    private int page = 0;
    private JSONArray dataArray = new JSONArray();
    private String areaKey = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inspection_completion_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.bottom_listview);
        titlename = (TextView) view.findViewById(R.id.title_name);
        areaKey = ((Act_Menu)getActivity()).getAreaKey();
        final LinearLayout more = (LinearLayout)view.findViewById(R.id.lin_more6);
        adapter = new MyBaseAdapter() {
            @Override
            public int getCount() {
                return dataArray.length();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final JSONObject object = dataArray.optJSONObject(position);
                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.inspection_completion_fragment_item2, null);
                }
                view.setVisibility(View.VISIBLE);
                TextView yq_project_name = MViewHolder.get(view, R.id.area_name);
                TextView days = MViewHolder.get(view, R.id.day_completion_rate);
                TextView weeks = MViewHolder.get(view, R.id.week_completion_rate);
                TextView month = MViewHolder.get(view, R.id.month_completion_rate);
                if(object.length()>0){
                    yq_project_name.setText(object.optString("areaName"));
                    days.setText(object.optDouble("dayRate")+"%");
                    weeks.setText(object.optDouble("weekRate")+"%");
                    month.setText(object.optDouble("monthRate")+"%");
                }
                view.setTag(R.id.tag_first, object);
                return view;
            }
        };
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Act_CityMoreInfo.class).putExtra("type",3).putExtra("title","检查完成率").putExtra("areaKey",areaKey));
            }
        });
        listView.setAdapter(adapter);
        isEmpty();
        return view;
    }
    protected void loadData(String method,int num) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "3" + "\",\"areaKey\":\"" + ((Act_Menu)getActivity()).getAreaKey() + "\"," +
                "\"pageIndex\":\"" + String.valueOf(page) + "\",\"pageSize\":\"" + num + "\",\"key\":\"" + "" + "\"}"), method, new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
                    }
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("checkRateList");
                        for (int i = 0; i < jsonArray.length(); i++) {//
                            dataArray.put(jsonArray.optJSONObject(i));
                        }
                    }
                    if(adapter != null){
                        adapter.notifyDataSetChanged();
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
        });
    }
    private void isEmpty(){
        if(areaKey == null || "NULL".equals(areaKey)|| "ALL".equals(areaKey) || "".equals(areaKey)){
            titlename.setText("区站名称");
            loadData("GetCitySiteRate",3);
        }else {
            titlename.setText("项目名称");
            loadData("GetAreaSiteRate",3);
        }
    }

}
