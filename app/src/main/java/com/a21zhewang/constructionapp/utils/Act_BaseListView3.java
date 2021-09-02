package com.a21zhewang.constructionapp.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.angel.interfaces.OnTouchUpListener;
import com.angel.layout.SWPullRecyclerLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by zhewang_ljw on 2017/5/15.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public abstract class Act_BaseListView3<A, B, C> extends BaseActivity implements OnTouchUpListener {
    @BindView(R.id.activity_baselistview_MyTitleBar)
    MyTitleBar activityBaselistviewMyTitleBar;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    public RelativeLayout activityBaselistviewRelativeLayout;
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;//子下拉
    @BindView(R.id.activity_baselistview_SWPullRecyclerLayout)
    public SWPullRecyclerLayout activityBaselistviewSWPullRecyclerLayout;//litview
    public List<A> spinner1Beenlist;
    public List<B> spinner2Beenlist;
    public List<C> listBeenlist;
    public CommonAdapter<C> commonAdapter;


    /**
     * 设置listviewitem布局
     *
     * @param holder
     * @param c
     * @param position
     */
    public abstract void thisconvert(ViewHolder holder, C c, int position);

    /**
     * @return 设置标题文字
     */
    public abstract String settiltile();

    /**
     * @param //设置新增按钮的点击事件
     */
    public abstract void setaddbtnonclick();
    /**
     * @return 设置新增按钮是否显示
     */
    public abstract String setaddbtnvisiable();
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
        return R.layout.activity_baselistview3;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        List<ButtonBean> buttons = PublicUtils.userBean.getButtons();
        if (buttons != null) {
            for (ButtonBean btn : buttons) {
                if (btn.getBtnID().equals(setaddbtnvisiable())) {
                    activityBaselistviewRelativeLayout.setVisibility(View.VISIBLE);
                    setaddbtnonclick();
                    break;
                }
            }
        }
        activityBaselistviewMyTitleBar.setTitlenametv(settiltile());
        spinner1Beenlist = new ArrayList<A>();
        spinner2Beenlist = new ArrayList<B>();
        listBeenlist = new ArrayList<C>();
        commonAdapter = new CommonAdapter<C>(this,
                R.layout.gtxt_list_item2, listBeenlist) {

            @Override
            protected void convert(ViewHolder holder, C c, int position) {
                thisconvert(holder, c, position);
            }
        };
        View header = LayoutInflater.from(this).inflate(R.layout.header, null);
        View footer = LayoutInflater.from(this).inflate(R.layout.footer, null);
        activityBaselistviewSWPullRecyclerLayout.addHeaderView(header, 100);
        activityBaselistviewSWPullRecyclerLayout.addFooterView(footer, 100);
        activityBaselistviewSWPullRecyclerLayout.setMyRecyclerView(new LinearLayoutManager(this),
                commonAdapter);
        activityBaselistviewSWPullRecyclerLayout.addOnTouchUpListener(this);
    }


    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {

    }


}
