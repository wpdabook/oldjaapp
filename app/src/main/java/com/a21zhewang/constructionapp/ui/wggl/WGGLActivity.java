package com.a21zhewang.constructionapp.ui.wggl;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.TzgglistBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhewang_ljw on 2017/5/23.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public abstract class WGGLActivity extends BaseActivity implements OnTouchUpListener {

    @BindView(R.id.activity_tzgg_SWPullRecyclerLayout)
    SWPullRecyclerLayout activityTzggSWPullRecyclerLayout;
    @BindView(R.id.activity_tzgg_MyTitleBar)
    MyTitleBar myTitleBar;
    @BindView(R.id.activity_tzgg_ImageView_addimg)
    ImageView activityTzggImageViewAddimg;
    @BindView(R.id.activity_tzgg)
    LinearLayout activity_tzgg;
    @BindView(R.id.activity_tzgg_SearchView)
    SearchView activityTzggSearchView;
    private List<TzgglistBean> tzgglistBeanList;
    private CommonAdapter<TzgglistBean> listviewadapter;
    private int pageindex = 0;
    private String keyWord="";
    private int onClickIndex=-1;


    /**
     * @return 设置请求数据的方法
     */
    public abstract String setgetdatamethod();

    public abstract String settitle();

    public abstract String setaddimgisvisiable();

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
        return R.layout.activity_gzgg;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons != null) {
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals(setaddimgisvisiable())) {
                    activityTzggImageViewAddimg.setVisibility(View.VISIBLE);
                    activityTzggImageViewAddimg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickIndex=0;
                            Intent intent = new Intent(WGGLActivity.this, AddWgActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        }


        myTitleBar.setTitlenametv(settitle());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        tzgglistBeanList = new ArrayList<>();
        int layoutid = R.layout.sgtx_list_item;
        // if (setmethod().equals("notice2")) {
        activityTzggSearchView.setVisibility(View.VISIBLE);
        layoutid = R.layout.tzgg_list_item2;
        activity_tzgg.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);//4个参数按顺序分别是左上右下
        activityTzggSWPullRecyclerLayout.setLayoutParams(layoutParams);

        //}
        listviewadapter = new CommonAdapter<TzgglistBean>(this, layoutid, tzgglistBeanList) {

            @Override
            protected void convert(ViewHolder holder, final TzgglistBean tzgglistBean, final int position) {
                holder.setText(R.id.tzgg_list_item_tv_title, tzgglistBean.getTitle());
//                holder.setText(R.id.tzgg_list_item_tv_time, tzgglistBean.getEtpShortName() +" "+ tzgglistBean.getCreateUserName());
                holder.setText(R.id.tzgg_list_item_tv_from, tzgglistBean.getCreateTime());
                if (tzgglistBean.getRead()) {
                    holder.setVisible(R.id.tzgg_list_item_tv_isread, false);

                } else {
                    holder.setVisible(R.id.tzgg_list_item_tv_isread, true);
                }


                //  holder.setBackgroundColor(R.id.sgtx_list_item_tv_isread, Color.parseColor("#ffffff"));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickIndex=position;
                        Intent intent = new Intent(WGGLActivity.this, WgxqActivity.class);
                        intent.putExtra("recordID", tzgglistBean.getRecordID());
                        intent.putExtra("title", "网格员管理");
                        startActivity(intent);
                    }
                });
            }
        };
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
        //  View pop = LayoutInflater.from(this).inflate(R.layout.popup, null);
        activityTzggSWPullRecyclerLayout.addHeaderView(header, 100);
        activityTzggSWPullRecyclerLayout.addFooterView(footer, 100);
        // activityTzggSWPullRecyclerLayout.addHeader(pop);
        activityTzggSWPullRecyclerLayout.setMyRecyclerView(linearLayoutManager, listviewadapter);
        activityTzggSWPullRecyclerLayout.addOnTouchUpListener(this);
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        activityTzggSearchView.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String str) {
                //   PublicUtils.toast("点击-->SEARCH"+str);
                keyWord=str;
                tzgglistBeanList.clear();
                pageindex = 0;
                getdata();

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        if (tzgglistBeanList!=null)
            tzgglistBeanList.clear();
        if (listviewadapter!=null)
            listviewadapter.notifyDataSetChanged();
        pageindex = 0;
        getdata();
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }

    /**
     * 刷新
     */
    @Override
    public void OnRefreshing() {
        keyWord="";
        pageindex = 0;
        listviewadapter.getDatas().clear();
        listviewadapter.notifyDataSetChanged();
        getdata();
    }

    /**
     * 加载
     */
    @Override
    public void OnLoading() {

        pageindex++;
        getdata();
    }

    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {

    }

    /**
     * 页面销毁
     */
    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * 页面显示
     */
    @Override
    public void resume() {
        if (onClickIndex!=-1){
            pageindex = 0;
            listviewadapter.getDatas().clear();
            listviewadapter.notifyDataSetChanged();
            getdata(0,(pageindex+1)*10);
        }
    }

    private void getdata() {

        getdata(pageindex,10);
    }

    private void getdata(int pageindex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"pageIndex\":\"" + pageindex + "\",\"keyWord\":\"" + keyWord+ "\",\"pageSize\":\""+pageSize+"\"}");
        MyCallBack callback = new MyCallBack() {

            /**
             * 请求成功code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    Object recordList = jsonObject.get("recordList");
                    if (recordList != null) {
                        List<TzgglistBean> tzgglistBeens = JsonUtils.jsonToList(recordList.toString(), TzgglistBean[].class);
                        if (tzgglistBeens != null && tzgglistBeens.size() > 0) {
                            tzgglistBeanList.addAll(tzgglistBeens);
                        }
                    }
                    listviewadapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /**
             * 请求成功code 不等于 0回调
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

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                activityTzggSWPullRecyclerLayout.refreshok();
                if (onClickIndex!=-1){
                    activityTzggSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        };
        callback.setIstoast(false);
        XUtil.postjsondata(getjsonobj, setgetdatamethod(), callback);
    }

}
