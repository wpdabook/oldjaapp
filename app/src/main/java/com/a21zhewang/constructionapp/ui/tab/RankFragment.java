package com.a21zhewang.constructionapp.ui.tab;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.FocusRankInfo;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.ui.menu.ProjectFragment;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MViewHolder;
import com.a21zhewang.constructionapp.utils.MyBaseAdapter;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WP-PC on 2019/8/13.
 */

public class RankFragment extends Fragment{
    private View v;
    private boolean hasInitData;
    private MyBaseAdapter adapter;
    private List<FocusRankInfo>   rankInfos;
    private ListView listview;
    private String id;
    private TextView rank_tv2,rank_tv3;
    private LinearLayout title_remind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.rank_fragment_layout, null, false);
            rankInfos = new ArrayList<FocusRankInfo>();
            listview = (ListView) v.findViewById(R.id.listview);
            title_remind = (LinearLayout) v.findViewById(R.id.title_remind);
            rank_tv2 = (TextView) v.findViewById(R.id.rank_tv2);
            rank_tv3 = (TextView) v.findViewById(R.id.rank_tv3);
            adapter = new MyBaseAdapter() {
                @Override
                public View getView(int position, View arg1, ViewGroup parent) {
                    if (arg1 == null) {
                        arg1 = getActivity().getLayoutInflater().inflate(
                                R.layout.rankfragment_item, null);
                    }
                    arg1.setVisibility(View.VISIBLE);
                    TextView name = MViewHolder.get(arg1, R.id.pj_name);
                    TextView end = MViewHolder.get(arg1, R.id.pj_ending);
                    TextView add = MViewHolder.get(arg1, R.id.pj_adding);
                    if(rankInfos.size()>0){
                        name.setText(rankInfos.get(position).getProjectName());
                        Double b = rankInfos.get(position).getRate();
                        /*one change*/
//                      String number = Integer.parseInt(new DecimalFormat("0").format(b * 100)) + "%";
//                      end.setText(number);

                        /*two change*/
                        DecimalFormat df1 = new DecimalFormat("######0.00");
                        end.setText(df1.format(b)+"%");

                        add.setText(rankInfos.get(position).getSunCount() + "");
                        if("1".equals(id)) {
//                            rank_tv2.setText("检查完成率");
//                            rank_tv3.setText("隐患数");

                            rank_tv2.setText("");
                            rank_tv3.setText("检查完成率");

                            end.setText("");

                            DecimalFormat df0 = new DecimalFormat("######0.00");
                            add.setText(df1.format(b)+"%");
                        }
                        if("2".equals(id)){
                            rank_tv2.setText("整改率");
                            rank_tv3.setText("涨跌幅");
                            add.setTextColor(getResources().getColor(R.color.color_wjc));
                            if(rankInfos.get(position).getMomRate() == 0){
                                add.setText("-");
                            }else {
                                add.setText(rankInfos.get(position).getMomRate()+"%");
                            }
                        }
//                        if("3".equals(id)){
//                            rank_tv2.setText("");
//                            rank_tv3.setText("工程数量");
//                            end.setText("");
//                            add.setText(rankInfos.get(position).getSunCount()+"");
//                        }
                        if("4".equals(id)){
                            rank_tv2.setText("");
                            rank_tv3.setText("参与率");
                            end.setText("");
                            add.setText(df1.format(b)+"%");
                        }
                    }
                    arg1.setTag(R.id.tag_first, rankInfos);
                    return arg1;
                }
                @Override
                public int getCount() {
                    return rankInfos.size();
                }
            };
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!TextUtils.isEmpty(rankInfos.get(position).getProjectID())){
                        if(BaseActivity.isNotFastClick()){
                            startActivity(new Intent(getActivity(),Act_RankDetail2.class)
                                    .putExtra("tag",1)
                                    .putExtra("projectId",rankInfos.get(position).getProjectID()));
                        }

                    }
                }
            });
        }else {
            if (v.getParent() != null) {
                ((ViewGroup) v.getParent()).removeView(v);
            }
        }
        return v;
    }

    public void refresh() {
        loadData(true);
    }
    public void initData() {
        id = getArguments().getString("id");
        if (!hasInitData) {
            loadData(true);
            hasInitData = true;
        }
    }

    /**
     * 1:近一周 2：近一月Get_DataTotal_KeyOrRateOrDangerTotal
     * @param needDialog
     */
    private void loadData(boolean needDialog) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"dateType\":\"" + 2 + "\",\"paramType\":\"" + id + "\"}"),
                "Get_DataTotal_KeyOrRateOrDangerTotal", new MyCallBack() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResule(String res) {
                try {
                    rankInfos.clear();
                    JSONObject object = new JSONObject(res);
                    JSONArray array = object.optJSONArray("totalDatas");
                    if(array.length()>0){
                        title_remind.setVisibility(View.VISIBLE);
                        for(int i=0;i<array.length();i++){
                            FocusRankInfo info = new FocusRankInfo();
                            JSONObject tempobject = array.optJSONObject(i);
                            info.setProjectName(tempobject.optString("projectName"));
                            info.setRate(tempobject.optDouble("rate"));
                            info.setSunCount(tempobject.optInt("sunCount"));
                            info.setProjectID(tempobject.optString("projectId"));
                            info.setProShortName(tempobject.optString("proShortName"));
                            info.setMomRate(tempobject.optInt("momRate"));
                            info.setCount(tempobject.optInt("count"));
                            rankInfos.add(info);
                        }
                    }else {
                        title_remind.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(adapter != null){
                    adapter.notifyDataSetChanged();
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
