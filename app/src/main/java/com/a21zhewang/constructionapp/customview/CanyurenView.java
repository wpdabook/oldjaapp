package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zhy.view.flowlayout.TagView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by zhewang_ljw on 2017/4/26.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class CanyurenView<T> extends LinearLayout {

    private boolean isAll=true;
    private boolean canSeleltAll;

    public boolean isCanSeleltAll() {
        return canSeleltAll;
    }

    public void setCanSeleltAll(boolean canSeleltAll) {
        this.canSeleltAll = canSeleltAll;
    }

    public interface CanyurenViewAPI<T> {
        void setstr(int size, TextView textView, Set<Integer> ints);

        String getstr(int i, T t);
    }

    private Context thiscContext;
    private TextView textView;
    private PopupWindow canyupopupwindow;
    private boolean isshowing;
    private HashSet<Integer> savestrs;
    private TagAdapter<String> tagAdapter;
    private CanyurenViewAPI canyurenViewAPI;

    public TagFlowLayout getCanyuren_xuanze_lin() {
        return canyuren_xuanze_lin;
    }

    private TagFlowLayout canyuren_xuanze_lin;
    private LayoutInflater layoutInflater;
    private List<String> strlist;
    private List<T> tlist;
    private String textstr = "选择接收人";

    public String getTextstr() {
        return textView.getText().toString();
    }

    public void settextviewstr(String str) {
        if (str != null)
            textView.setText(str);
    }

    public void setTextstr(String textstr) {
        this.textstr = textstr;
    }


    public CanyurenView(Context context) {
        super(context);
        thiscContext = context;

    }

    public CanyurenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        thiscContext = context;
        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CanyurenView);
        String typedArrayString = typedArray.getString(R.styleable.CanyurenView_str);

        if (!TextUtils.isEmpty(typedArrayString)) {
            textView.setText(typedArrayString);
            textstr = typedArrayString;
        }
        typedArray.recycle();
    }

    public void setMaxSelect(int count) {
        canyuren_xuanze_lin.setMaxSelectCount(count);
    }

    private void init() {
        savestrs = new HashSet<>();
        tlist = new ArrayList<>();
        View.inflate(thiscContext, R.layout.myspinner, this);
        textView = (TextView) this.findViewById(R.id.myspinner_text);
        View popview = LayoutInflater.from(thiscContext).inflate(R.layout.canyuren_xuanze, null);
        canyuren_xuanze_lin = (TagFlowLayout) popview.findViewById(R.id.TagFlowLayout);
        layoutInflater = LayoutInflater.from(thiscContext);
        strlist = new ArrayList<>();
        tagAdapter = new TagAdapter<String>(strlist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) layoutInflater.inflate(R.layout.canyuren_item,
                        canyuren_xuanze_lin, false);
                tv.setText(s);
                return tv;
            }
        };
        canyuren_xuanze_lin.setAdapter(tagAdapter);
        canyupopupwindow = new PopupWindow(popview, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        canyupopupwindow.setOutsideTouchable(true);

        canyupopupwindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strlist != null && strlist.size() > 0) {
                    if (isshowing) {
                        canyupopupwindow.dismiss();
                        isshowing = false;
                    } else {
                        savestrs.clear();
                        savestrs.addAll(canyuren_xuanze_lin.getSelectedList());
                        canyupopupwindow.showAsDropDown(v);
                        isshowing = true;
                    }
                } else {
                    PublicUtils.toast("没有可以选择的数据！");
                }
            }
        });
        canyuren_xuanze_lin.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                if (canSeleltAll) {

                    if ((canyuren_xuanze_lin.getSelectedList().size() == tagAdapter.getCount() - 1) && selectPosSet.contains(0)) {
                        selectPosSet.remove(0);
                        tagAdapter.setSelectedList(selectPosSet);

                    } else if ((canyuren_xuanze_lin.getSelectedList().size() == tagAdapter.getCount() - 1) && !selectPosSet.contains(0)) {
                        selectPosSet.add(0);
                        tagAdapter.setSelectedList(selectPosSet);
                      //  isAll = true;
                    }
                }
            }
        });
        canyuren_xuanze_lin.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (tagAdapter.getItem(position).toString().equals("全部") && canSeleltAll) {
                    selectAll();
                    // ((TagView)view).setChecked(isAll);
                }
                settextsize();
                return false;
            }
        });
        TextView quxiaocy_tv = (TextView) popview.findViewById(R.id.quxiao_canyuren);
        TextView quedingcy_tv = (TextView) popview.findViewById(R.id.queding_canyuren);
        //取消的点击事件
        quxiaocy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagAdapter.setSelectedList(savestrs);
                canyupopupwindow.dismiss();

            }
        });

        //确定的点击事件
        quedingcy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                canyupopupwindow.dismiss();

            }
        });

        canyupopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                savestrs.clear();
                savestrs.addAll(canyuren_xuanze_lin.getSelectedList());
                settextsize();
            }
        });
        setMaxSelect(1);
    }

    private void settextsize() {
        Set<Integer> selectedList = canyuren_xuanze_lin.getSelectedList();
        int size = selectedList.size();
        if (size > 0) {

            if (canyurenViewAPI != null) {
                canyurenViewAPI.setstr(size, textView, selectedList);
            }

        } else {
            textView.setText(textstr);
        }
    }

    /**
     * 全选
     */
    public void selectAll() {

        Set<Integer> integerSet = new HashSet<>();
        if (isAll) {
            for (int i = 0; i < tagAdapter.getCount(); i++) {
                integerSet.add(i);
            }
        }
        tagAdapter.setSelectedList(integerSet);
        isAll = !isAll;
    }

    public void setadapter(TagAdapter adapter) {
        canyuren_xuanze_lin.setAdapter(adapter);
    }

    public void setListener(TagFlowLayout.OnSelectListener onSelectListener) {
        canyuren_xuanze_lin.setOnSelectListener(onSelectListener);
    }

    public void setList(List<String> strs) {
        savestrs.clear();
        tlist.clear();
        strlist.clear();
        if (strs == null || strs.size() == 0) return;
        strlist.addAll(strs);
        tagAdapter.notifyDataChanged();
    }

    public void setList(List<T> strs, CanyurenViewAPI canyurenapi) {
        savestrs.clear();
        tlist.clear();
        strlist.clear();
        if (strs == null || strs.size() == 0) return;
        tlist.addAll(strs);
        for (int i = 0; i < strs.size(); i++) {

            strlist.add(canyurenapi.getstr(i, strs.get(i)));
        }
        textView.setText(textstr);
        tagAdapter.notifyDataChanged();
    }

    public Set<Integer> getList() {
        return canyuren_xuanze_lin.getSelectedList();

    }

    public List<T> getlists() {

        List<T> ts = new ArrayList<>();
        Set<Integer> selectedList = canyuren_xuanze_lin.getSelectedList();
        Iterator<Integer> iterator = selectedList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            ts.add(tlist.get(next));
        }
        return ts;

    }

    public void setselsctor(Set<Integer> integers) {
        savestrs.clear();
        savestrs.addAll(integers);
        tagAdapter.setSelectedList(savestrs);
        settextsize();
    }

    public void remeselsctor(HashSet<Integer> integers) {
        savestrs.removeAll(integers);
        tagAdapter.setSelectedList(savestrs);
        settextsize();
    }

    public void popdiss() {
        canyupopupwindow.dismiss();
    }

    public void setCanyurenViewAPI(CanyurenViewAPI canyurenViewAPI) {
        this.canyurenViewAPI = canyurenViewAPI;
    }
}
