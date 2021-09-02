package com.a21zhewang.constructionapp.ui.tab;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by wp-pc on 2020/4/4.
 */

public class MemberInfoFragment extends Fragment {

    private ListView listView;
    private MyBaseAdapter adapter;
    private int page = 0;
    private JSONArray dataArray = new JSONArray();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_info_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.bottom_listview);
        final LinearLayout more = (LinearLayout)view.findViewById(R.id.lin_more4);
        adapter = new MyBaseAdapter() {
            @Override
            public int getCount() {
                return dataArray.length();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                final JSONObject object = dataArray.optJSONObject(position);
                if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.member_info_fragment_item2, null);
                }
                view.setVisibility(View.VISIBLE);
                TextView yq_project_name = MViewHolder.get(view, R.id.yq_project_name);
                TextView yq_manger = MViewHolder.get(view, R.id.yq_manger);
                TextView yq_work_member = MViewHolder.get(view, R.id.yq_work_member);
                TextView yq_normal = MViewHolder.get(view, R.id.yq_normal);
                if(object.length()>0){
                    yq_project_name.setText(object.optString("projectName"));
                    yq_manger.setText(object.optInt("managerUserCount")+"");
                    yq_work_member.setText(object.optInt("labourUserCount")+"");
                    yq_normal.setText(object.optInt("noUserCount")+"");
                }
                view.setTag(R.id.tag_first, object);
                return view;
            }
        };
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Act_YiQingTabMore.class).putExtra("type",1).putExtra("title","项目人员信息"));
            }
        });
        listView.setAdapter(adapter);
        return view;
    }
    public void initData() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "1" + "\",\"dateTime\":\"" + DateUtils.getTodayDate() + "\"" +
                ",\"pageIndex\":\"" + String.valueOf(page) + "\",\"pageSize\":\"" + 5 + "\"}"), "GetResumWorkUserEquipmentDanger", new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
                    }
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("ProjectUserList");
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
