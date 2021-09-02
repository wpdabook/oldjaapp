package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.bean.Etp;
import com.a21zhewang.constructionapp.bean.GtxtspinnerBean;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.User;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by zhewang_ljw on 2017/5/24.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class LinkageSpinner extends LinearLayout {

    private MySpinner dwspinner;
    private CanyurenView canyurenView;
    private LinearLayout addimgslinearLayout;
    private MyListView myListView;
    private Project pjbean;
    private List<Etp> etpInfoList;
    private MySpinner.MySpinnerAPI dwonClickListener;
    private Etp etpInfoListBean;
    private int maxETPCount=-1;
    public List<Etp> getAddInfoList() {
        return addInfoList;
    }

    public List<Etp> getEtpInfoList() {
        return etpInfoList;
    }
    public String getspinneronetext(){
    return  canyurenView.getTextstr();
}
    public List<Etp> getInfoList() {
        if (dwspinner.getTextViewtext().equals("全部")){
            return etpInfoList;
        }
        return getAddInfoList();
    }
    private List<Etp> addInfoList;
    private CommonAdapter addadapter;
    private List<User> userInfoList;
    private NamesSpinnerAdpater<Etp> etpInfoListBeanNamesSpinnerAdpater;
    private CanyurenView.CanyurenViewAPI canyurenapi;
    private Context mcContext;

    public LinkageSpinner(Context context) {
        this(context, null);
    }

    public LinkageSpinner(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public LinkageSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public void setpjbean(Project pjbean){
        setpjbean(pjbean,0);
    }

    public void setpjbean(Project pjbean,int frist) {
        if (pjbean == null) return;
        setinit(mcContext);
        this.pjbean = pjbean;
        List<Etp> eIList = pjbean.getEtpInfoList();
        etpInfoListBeanNamesSpinnerAdpater.replcedata(null);
        if (eIList != null && eIList.size() > 0) {
            etpInfoList.addAll(eIList);
         //  Etp etpBean = new Etp();
           // etpBean.setEtpShortName("全部");
           // etpInfoList.add(etpBean);
            if (frist==0){
                dwspinner.setTextViewtext(dwonClickListener.onclick(0));
            }else {
            //    dwspinner.setTextViewtext(dwonClickListener.onclick(etpInfoList.size()-1));
            }

            etpInfoListBeanNamesSpinnerAdpater.notifyDataSetChanged();
        }

    }

    public void setlist(List<Etp> eIList,int frist){
        setinit(mcContext);
        etpInfoListBeanNamesSpinnerAdpater.replcedata(null);
        if (eIList != null && eIList.size() > 0) {
            etpInfoList.addAll(eIList);
        //    Etp etpBean = new Etp();
          //  etpBean.setEtpShortName("全部");
          //  etpInfoList.add(etpBean);
            if (frist==0){
                dwspinner.setTextViewtext(dwonClickListener.onclick(0));
            }else {
               // dwspinner.setTextViewtext(dwonClickListener.onclick(etpInfoList.size()-1));
            }

            etpInfoListBeanNamesSpinnerAdpater.notifyDataSetChanged();
        }
    }

    private void init(Context context) {
        this.mcContext = context;
        View inflate = View.inflate(context, R.layout.linkage_spinner_layout, this);
        dwspinner = (MySpinner) inflate.findViewById(R.id.linkage_MySpinner_dw);
        canyurenView = (CanyurenView) inflate.findViewById(R.id.linkage_MySpinner_jsr);
        addimgslinearLayout = (LinearLayout) inflate.findViewById(R.id.linkage_LinearLayout_adduser);
        myListView = (MyListView) inflate.findViewById(R.id.linkage_MyListView_dw);
        etpInfoList = new ArrayList<>();
        addInfoList = new ArrayList<>();
        // setinit(context);
        setMaxETPCount(1);
        setMaxCount(1);
    }
public void setMaxCount(int count){
    canyurenView.setMaxSelect(count);
}
public void setMaxETPCount(int count){
        maxETPCount=count;
    }
    private void setinit(Context context) {
        addimgslinearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maxETPCount!=-1&&addInfoList.size()>maxETPCount){
                    PublicUtils.toast("只能单选。");
                    return;
                }
               // addInfoList.size()
                if (etpInfoListBean == null) {
                    PublicUtils.toast("请选择单位");
                    return;
                } else if (etpInfoListBean.getEtpShortName().equals("全部")) {
                    PublicUtils.toast("已选择全部");
                    return;

                }

                Set<Integer> list = canyurenView.getList();
                if (list == null || list.size() == 0) {
                    PublicUtils.toast("请选择接收人");
                    return;

                }
                List<Integer> addList = new ArrayList<>(list);
                if (addInfoList.contains(etpInfoListBean)) {
                    // PublicUtils.toast("you ");
                    for (int i = 0; i < addInfoList.size(); i++) {
                        Etp etpListBean = addInfoList.get(i);
                        if (etpListBean.equals(etpInfoListBean)) {
                            List<User> beanList = etpListBean.getUserInfoList();
                            beanList.clear();

                            for (int j = 0; j < addList.size(); j++) {
                                List<User> listBeen = etpInfoListBean.getUserInfoList();
                                User Bean = listBeen.get(addList.get(j));
                                beanList.add(Bean);
                            }
                            addadapter.notifyDataSetChanged();
                            break;
                        }
                    }
                } else {
                    Etp eInfoBean = new Etp();
                    eInfoBean.setEtpID(etpInfoListBean.getEtpID());
                    eInfoBean.setEtpShortName(etpInfoListBean.getEtpShortName());
                    List<User> uiList = new ArrayList<>();

                    for (int i = 0; i < addList.size(); i++) {
                        List<User> listBeen = etpInfoListBean.getUserInfoList();
                        User Bean = listBeen.get(addList.get(i));
                        uiList.add(Bean);
                    }
                    eInfoBean.setUserInfoList(uiList);
                    addInfoList.add(eInfoBean);
                    addadapter.notifyDataSetChanged();
                }
            }
        });

        etpInfoListBeanNamesSpinnerAdpater = new NamesSpinnerAdpater<Etp>(context, etpInfoList) {
            @Override
            public void setinit(Etp name, ViewHolder mViewHolder) {
                mViewHolder.nametextview.setText(name.getEtpShortName());
            }
        };
        dwspinner.setmyspinnerlistadpater(etpInfoListBeanNamesSpinnerAdpater);
        dwonClickListener = new MySpinner.MySpinnerAPI() {
            @Override
            public String onclick(int position) {
                etpInfoListBean = etpInfoList.get(position);
                String etpShortName = etpInfoListBean.getEtpShortName();
                if (etpShortName.equals("全部")) {
                    canyurenView.settextviewstr("全部");
                    canyurenView.setClickable(false);
                    addInfoList.clear();
                    addadapter.notifyDataSetChanged();
                    return etpShortName;
                }
                canyurenView.setClickable(true);


                userInfoList = etpInfoListBean.getUserInfoList();
                if (userInfoList != null)
                    canyurenView.setList(userInfoList, canyurenapi);
                Set<Integer> integers = new HashSet<>();
                if (addInfoList.contains(etpInfoListBean)) {
                    for (int i = 0; i < addInfoList.size(); i++) {
                        if (addInfoList.get(i).getEtpShortName().equals(etpInfoListBean.getEtpShortName())) {
                            List<User>been = addInfoList.get(i).getUserInfoList();
                            for (int j = 0; j < userInfoList.size(); j++) {
                                if (been.contains(userInfoList.get(j))) {
                                    integers.add(j);
                                }
                            }
                        }
                    }
                }
                canyurenView.setselsctor(integers);
                return etpShortName;
            }
        };
        dwspinner.setlistviewitemonclick(dwonClickListener);

        //接收人
        canyurenapi = new CanyurenView.CanyurenViewAPI<User>() {
            @Override
            public void setstr(int size, TextView textView, Set ints) {
                List<Integer> integers = new ArrayList<Integer>(ints);
                if (size == 1) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName());
                } else if (size == 2) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName() + "," + userInfoList.get(integers.get(1)).getUserName());
                } else if (size > 2) {
                    textView.setText(userInfoList.get(integers.get(0)).getUserName() + "," + userInfoList.get(integers.get(1)).getUserName() + "...");
                }
            }

            @Override
            public String getstr(int i, User o) {
                return o.getUserName();
            }
        };
        canyurenView.setCanyurenViewAPI(canyurenapi);

        addInfoList = new ArrayList<>();
        addadapter = new CommonAdapter<Etp>(context, R.layout.addtzggdw_item, addInfoList) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, Etp item, final int position) {
                viewHolder.setText(R.id.addtzgg_item_Dw, item.getEtpShortName());
                List<User> beanList = item.getUserInfoList();
                if (beanList != null && beanList.size() > 0) {
//                    if (beanList.size() == 1) {
//                        viewHolder.setText(R.id.addtzgg_item_user, beanList.get(0).getUserName());
//                    } else if (beanList.size() == 2) {
//                        viewHolder.setText(R.id.addtzgg_item_user, beanList.get(0).getUserName() + "," + beanList.get(1).getUserName());
//                    } else {
//                        viewHolder.setText(R.id.addtzgg_item_user, beanList.get(0).getUserName() + "," + beanList.get(1).getUserName() + "...");
//                    }
                    StringBuffer buffer = new StringBuffer();
                    for (int i = 0; i < beanList.size(); i++) {
                        buffer.append(beanList.get(i).getUserName());
                        if (i != beanList.size() - 1)
                            buffer.append(",");
                    }
                    viewHolder.setOnClickListener(R.id.addtzgg_item_ImageView_delete, new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addInfoList.remove(position);
                            addadapter.notifyDataSetChanged();
                        }
                    });
                    viewHolder.setText(R.id.addtzgg_item_user, buffer.toString());
                }


            }
        };
        myListView.setAdapter(addadapter);
    }
}
