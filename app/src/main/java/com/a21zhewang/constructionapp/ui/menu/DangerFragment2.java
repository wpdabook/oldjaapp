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
import com.a21zhewang.constructionapp.ui.tab.Act_YiQingTabMore;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wp-pc on 2020/4/4.
 */

public class DangerFragment2 extends BaseLazyFragment {

    private ListView listView;
    private MyBaseAdapter adapter;
    private int page = 0;
    private JSONArray dataArray = new JSONArray();


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.danger_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.bottom_listview);
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
                    view = getActivity().getLayoutInflater().inflate(R.layout.member_info_fragment_item4, null);
                }
                view.setVisibility(View.VISIBLE);
                TextView yq_project_name = MViewHolder.get(view, R.id.yq_project_name);
                TextView yq_manger = MViewHolder.get(view, R.id.yq_manger);
                TextView yq_work_member = MViewHolder.get(view, R.id.yq_work_member);
                TextView yq_normal = MViewHolder.get(view, R.id.yq_normal);
                if(object.length()>0){
                    yq_project_name.setText(object.optString("projectName"));
                    yq_manger.setText(object.optInt("dangerCount")+"");
                    yq_work_member.setText(object.optInt("normalCount")+"");
                    yq_normal.setText(object.optInt("rectCount")+"");
                }
                view.setTag(R.id.tag_first, object);
                return view;
            }
        };
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Act_YiQingTabMore.class).putExtra("type",3).putExtra("title","项目危大工程信息"));
            }
        });
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "3" + "\",\"dateTime\":\"" + DateUtils.getTodayDate() + "\"" +
                ",\"pageIndex\":\"" + String.valueOf(page) + "\",\"pageSize\":\"" + 5 + "\"}"), "GetResumWorkUserEquipmentDanger", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
                    }
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("DangerList");
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


}
