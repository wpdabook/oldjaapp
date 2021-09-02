package com.a21zhewang.constructionapp.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
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
 * Created by WP-PC on 2019/5/28.
 */

public abstract class Act_BaseListView6<A, B, C> extends BaseActivity implements OnTouchUpListener {
    @BindView(R.id.activity_baselistview_MyTitleBar)
    MyTitleBar activityBaselistviewMyTitleBar;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    public RelativeLayout activityBaselistviewRelativeLayout;
    @BindView(R.id.activity_baselistview_MySpinner_XTLX)
    MySpinner activityBaselistviewMySpinnerXTLX;//第一个下拉
    @BindView(R.id.activity_baselistview_MySpinner_XTLX_child)
    MySpinner activityBaselistviewMySpinnerXTLXChild;//子下拉
    @BindView(R.id.SearchView_top)
    public SearchView SearchView_top;//子下拉
    @BindView(R.id.activity_baselistview_MySpinner_CLQK)
    MySpinner activityBaselistviewMySpinnerCLQK;//第二个下拉
    @BindView(R.id.activity_baselistview_SWPullRecyclerLayout)
    public SWPullRecyclerLayout activityBaselistviewSWPullRecyclerLayout;//litview
    public List<A> spinner1Beenlist;
    public List<B> spinner2Beenlist;
    public List<C> listBeenlist;
    public CommonAdapter<C> commonAdapter;
    public NamesSpinnerAdpater<B> spinner2Adpater;
    public NamesSpinnerAdpater<A> spinner1Adpater;

    /**
     * 第一个下拉实现
     *
     * @param name
     * @param mViewHolder
     */
    public abstract void spinner1setinit(A name, NamesSpinnerAdpater.ViewHolder mViewHolder);

    /**
     * 第二个下拉实现
     *
     * @param name
     * @param mViewHolder
     */
    public abstract void spinner2setinit(B name, NamesSpinnerAdpater.ViewHolder mViewHolder);

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
     * @return 设置第一个下拉文字
     */
    public abstract String setspinner1str();

    /**
     * @return 设置第二个下拉文字
     */
    public abstract String setspinner2str();

    /**
     * @return 设置第一个下拉的点击事件
     */
    public abstract MySpinner.MySpinnerAPI setspinner1onclick();

    /**
     * @return 设置第二个下拉的点击事件
     */
    public abstract MySpinner.MySpinnerAPI setspinner2onclick();

    /**
     * @return 设置新增按钮是否显示
     */
    public abstract String setaddbtnvisiable();

    /**
     * @param //设置新增按钮的点击事件
     */
    public abstract void setaddbtnonclick();

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
        return R.layout.act_baselistview;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        /***简易告知没有发起功能*/
//        activityBaselistviewRelativeLayout.setVisibility(View.GONE);
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
        activityBaselistviewMySpinnerXTLX.setTextViewtext(setspinner1str());
        activityBaselistviewMySpinnerCLQK.setTextViewtext(setspinner2str());
        activityBaselistviewMySpinnerXTLX.setlistviewitemonclick(setspinner1onclick());
        activityBaselistviewMySpinnerCLQK.setlistviewitemonclick(setspinner2onclick());
        spinner1Beenlist = new ArrayList<A>();
        spinner2Beenlist = new ArrayList<B>();
        listBeenlist = new ArrayList<C>();
        spinner1Adpater = new NamesSpinnerAdpater<A>(this, spinner1Beenlist) {
            @Override
            public void setinit(A name, ViewHolder mViewHolder) {
                spinner1setinit(name, mViewHolder);
            }
        };
        activityBaselistviewMySpinnerXTLX.setmyspinnerlistadpater(spinner1Adpater);
        spinner2Adpater = new NamesSpinnerAdpater<B>(this, spinner2Beenlist) {
            @Override
            public void setinit(B name, ViewHolder mViewHolder) {
                spinner2setinit(name, mViewHolder);
            }
        };
        activityBaselistviewMySpinnerCLQK.setmyspinnerlistadpater(spinner2Adpater);
        commonAdapter = new CommonAdapter<C>(this,
                R.layout.act_zddb_home_item, listBeenlist) {

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
