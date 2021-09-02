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
 * 隐患排查治理情况
 * Created by wp-pc on 2020/4/4.
 */

public class GovernanceFragment extends Fragment {

    private ListView listView;
    private MyBaseAdapter adapter;
    private int page = 0;
    private JSONArray dataArray = new JSONArray();
    private String areaKey = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.governance_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.bottom_listview);
        areaKey = ((Act_Menu)getActivity()).getAreaKey();
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
                    view = getActivity().getLayoutInflater().inflate(R.layout.governance_fragment_item, null);
                }
                view.setVisibility(View.VISIBLE);
                TextView area_name = MViewHolder.get(view, R.id.area_name);
                TextView project_number = MViewHolder.get(view, R.id.project_number);
                TextView today_risk_number = MViewHolder.get(view, R.id.today_risk_number);
                TextView total_risk_number = MViewHolder.get(view, R.id.total_risk_number);
                TextView risk_rote = MViewHolder.get(view, R.id.risk_rote);
                LinearLayout lin_project_number = MViewHolder.get(view, R.id.lin_project_number);
                if(areaKey == null || "NULL".equals(areaKey)|| "ALL".equals(areaKey) || "".equals(areaKey)){
                    lin_project_number.setVisibility(View.VISIBLE);
                    project_number.setText(object.optInt("projectNum")+"");
                }else {
                    lin_project_number.setVisibility(View.GONE);
                    project_number.setVisibility(View.GONE);
                }
                if(object.length()>0){
                    area_name.setText(object.optString("areaName"));
                    today_risk_number.setText(object.optInt("hidderNum")+"");
                    total_risk_number.setText(object.optInt("allHidderNum")+"");
                    risk_rote.setText(object.optDouble("rectRate")+"%");
                }
                view.setTag(R.id.tag_first, object);
                return view;
            }
        };
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Act_CityMoreInfo.class).putExtra("type",1).putExtra("title","隐患排查治理情况").putExtra("areaKey",areaKey));
            }
        });
        listView.setAdapter(adapter);
        isEmpty();
        return view;
    }

    protected void loadData(String method) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"type\":\"" + "1" + "\",\"areaKey\":\"" + areaKey + "\"," +
                "\"pageIndex\":\"" + String.valueOf(page) + "\",\"pageSize\":\"" + 3 + "\",\"key\":\"" + "" + "\"}"), method, new MyCallBack() {

            @Override
            public void onResule(String res) {
                try {
                    JSONObject object = new JSONObject(res);
                    if (page == 0) {
                        dataArray = new JSONArray();
                    }
                    if(object.length()>0){
                        JSONArray jsonArray = object.optJSONArray("hiddenList");
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
            loadData("GetCitySiteRate");
        }else {
            loadData("GetAreaSiteRate");
        }
    }
}
