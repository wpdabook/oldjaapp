package com.a21zhewang.constructionapp.ui.sgrz;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.EtpBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.SgrzBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.MyTimeSpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeConstants;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SgrzActivity extends BaseActivity implements OnTouchUpListener {


    @BindView(R.id.activity_sgrz_SWPullRecyclerLayout)
    SWPullRecyclerLayout activitySgrzSWPullRecyclerLayout;
    @BindView(R.id.activity_sgrz_MyTimeSpinner)
    MyTimeSpinner activitySgrzMyTimeSpinner;
    @BindView(R.id.activity_sgrz_luru_RelativeLayout)
    RelativeLayout activitySgrzLuruRelativeLayout;
    @BindView(R.id.activity_sgrz_MySpinner_fbdw)
    MySpinner activitySgrzMySpinnerFbdw;

    @BindView(R.id.SearchView_top)
    SearchView SearchView_top;
    private List<SgrzBean> sgrzBeens;
    private CommonAdapter<SgrzBean> sgrzBeanCommonAdapter;
    private int page_index = 0;
    private List<EtpBean> etpBeens;
    private String etpid="";
    private String time="";
    private String endyear;
    private int onClickIndex=-1;
    private String keyword="";
    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_sgrz;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        endyear = getIntent().getStringExtra("year");
        if (!TextUtils.isEmpty(endyear)){
            time= TimeUtils.getString(endyear,-5, TimeConstants.DAY);
        }else{
            endyear="";
        }
        PublicUtils.log(endyear);
        etpBeens=new ArrayList<>();

        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons!=null&&buttons.size()>0){
        for (ButtonBean btn : buttons) {
            if (btn.getBtnID().equals("dailyAddBtn")) {
                activitySgrzLuruRelativeLayout.setVisibility(View.VISIBLE);
            }
        }}
        activitySgrzMyTimeSpinner.setMyTimeSpinnerinterface(new MyTimeSpinner.MyTimeSpinnerinterface() {
            @Override
            public void setokListener() {
                sgrzBeens.clear();
                time=activitySgrzMyTimeSpinner.gettime();
                endyear="";
                getdata(time, page_index = 0,etpid);
            }

            @Override
            public void setbackListener() {

            }
        });
        MyCallBack callback = new MyCallBack() {


            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<EtpBean> etpBeen = JsonUtils.jsonToList(recordList.toString(), EtpBean[].class);
                    if (etpBeen != null && etpBeen.size() > 0) {
                        etpBeens.addAll(etpBeen);
                        EtpBean e = new EtpBean();
                        e.setEtpID("");
                        e.setEtpShortName("默认");
                        etpBeens.add(e);
                        activitySgrzMySpinnerFbdw.setVisibility(View.VISIBLE);
                        activitySgrzMySpinnerFbdw.setmyspinnerlistadpater(new NamesSpinnerAdpater<EtpBean>(SgrzActivity.this, etpBeens) {
                            @Override
                            public void setinit(EtpBean name, ViewHolder mViewHolder) {
                                mViewHolder.nametextview.setText(name.getEtpShortName());
                            }
                        });
                        activitySgrzMySpinnerFbdw.setlistviewitemonclick(new MySpinner.MySpinnerAPI() {
                            @Override
                            public String onclick(int position) {
                                EtpBean etpBean = etpBeens.get(position);
                                etpid = etpBean.getEtpID();
                                sgrzBeens.clear();
                                getdata(time, page_index = 0, etpid);

                                if (etpBean.getEtpShortName().equals("默认"))
                                    return "分包单位";
                                return etpBean.getEtpShortName();
                            }
                        });
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
        };
        callback.istoast=false;
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"getType\":2,\"projectID\":\"\"}"), "GetEtpList", callback);
    }

    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                sgrzBeens.clear();
                time="";
                endyear="";
                keyword=edittext;
                getdata(time, page_index = 0,etpid);
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
        activitySgrzSWPullRecyclerLayout.addHeaderView(header, 100);
        activitySgrzSWPullRecyclerLayout.addFooterView(footer, 100);
        sgrzBeens = new ArrayList<>();
        sgrzBeanCommonAdapter = new CommonAdapter<SgrzBean>(this, R.layout.sgrz_list_item, sgrzBeens) {

            @Override
            protected void convert(ViewHolder holder, final SgrzBean s, final int position) {
//                if (s.isRead()){
//                    holder.setVisible(R.id.sgrz_list_item_tv_isread,false);
//                }else{
//                    holder.setVisible(R.id.sgrz_list_item_tv_isread,true);
//                }
                holder.setText(R.id.sgrz_list_item_tv_fbdw, "分包单位：" + s.getEtpShortName());
                holder.setText(R.id.sgrz_list_item_tv_time, s.getRecordTime());
                holder.setText(R.id.sgrz_list_item_tv_ms, s.getProShortName() + s.getRecordTime() + "施工日志");
                holder.setText(R.id.sgrz_list_item_tv_addman, s.getCreateUserName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(SgrzActivity.this, SgrzxqActivity.class);
                        intent.putExtra("obj", s);
                        startActivity(intent);
                    }
                });
            }
        };
        activitySgrzSWPullRecyclerLayout.setMyRecyclerView(new LinearLayoutManager(this), sgrzBeanCommonAdapter);
        activitySgrzSWPullRecyclerLayout.addOnTouchUpListener(this);
//        getdata("", page_index);
        if (sgrzBeens==null)return;
        sgrzBeens.clear();
        sgrzBeanCommonAdapter.notifyDataSetChanged();

        getdata(time, page_index = 0,etpid);
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @Override
    public void OnRefreshing() {
        sgrzBeens.clear();
        sgrzBeanCommonAdapter.notifyDataSetChanged();
        getdata(time, page_index = 0,etpid);

    }

    @Override
    public void OnLoading() {
        getdata(time, ++page_index,etpid);
    }

    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {

    }


    @OnClick({R.id.activity_sgrz_luru_RelativeLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_sgrz_luru_RelativeLayout:
                onClickIndex=0;
                startActivity(new Intent(SgrzActivity.this, SgrzlrActivity.class));
                break;

        }
    }

    public void getdata(String time, int index,String etpID){
        getdata(time,index,10,etpID);
    }
    public void getdata(String time, int index,int pageSize,String etpID) {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"endTime\":\""+time+"\",\"pageIndex\":\"" + index + "\",\"beginTime\":\"" + time + "\",\"pageSize\":\""+pageSize+"\",\"etpID\":\""+etpID+"\",\"keyword\":\""+keyword+"\"}"),
                "GetListDailyLog", new MyCallBack() {
                    /**
                     * code 等于 0回调
                     *
                     * @param res
                     */
                    @Override
                    public void onResule(String res) {
                        try {
                            JSONObject jsonObject=new JSONObject(res);
                            JSONArray recordList = jsonObject.getJSONArray("recordList");
                            List<SgrzBean> sgrzBeen = JsonUtils.jsonToList(recordList.toString(), SgrzBean[].class);
                        if (sgrzBeen.size() > 0) {
                            sgrzBeens.addAll(sgrzBeen);

                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    /**
                     * code 不等于 0回调
                     *
                     * @param getbean
                     */
                    @Override
                    public void onFail(ObjBean getbean) {

                    }

                    /**
                     * 联网失败回调
                     */
                    @Override
                    public void onWrong() {

                    }

                    @Override
                    public void onFinished() {
                        sgrzBeanCommonAdapter.notifyDataSetChanged();
                        activitySgrzSWPullRecyclerLayout.refreshok();
                        if (onClickIndex!=-1){
                            activitySgrzSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                            onClickIndex=-1;
                        }
                    }
                });
    }

    /**
     * 页面销毁
     */
    @Override
    public void resume() {
if (onClickIndex!=-1){
    sgrzBeens.clear();
    sgrzBeanCommonAdapter.notifyDataSetChanged();
    getdata(time, 0,(page_index+1)*10,etpid);
}
    }


}
