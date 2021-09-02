package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.utils.DensityUtil;


/**
 * Created by zhewang on 2016/11/3.
 */

public class MySpinner extends LinearLayout {

    TextView textView;
    Context thiscContext;
    PopupWindow poplist;
    ListView listview;

    public MySpinner(Context context) {

        super(context);
    }

    public MySpinner(final Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySpinner);
        for (int i = 0; i < typedArray.length(); i++) {
            switch (typedArray.getIndex(i)) {
                case R.styleable.MySpinner_textsize:
                    float size = typedArray.getDimension(R.styleable.MySpinner_textsize, 5);
                    textView.setTextSize(size);
                    break;
                case R.styleable.MySpinner_text:
                    String text = typedArray.getString(R.styleable.MySpinner_text);
                    textView.setText(text);
                    break;
            }
        }
        typedArray.recycle();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showtimespop();
            }
        });

    }

    private void init(Context context) {
        thiscContext = context;
        View.inflate(context, R.layout.myspinner, this);
        textView = (TextView) this.findViewById(R.id.myspinner_text);
        View popview = LayoutInflater.from(thiscContext).inflate(R.layout.poplist_spinner, null);
//        popview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        listview = (ListView) popview.findViewById(R.id.poplist_spinner_list);
        poplist = new PopupWindow(popview,DensityUtil.dip2px(150), WindowManager.LayoutParams.WRAP_CONTENT);
        poplist.setFocusable(true);
        poplist.setOutsideTouchable(true);
        poplist.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_shape));
        setPopWidth();
//        new ColorDrawable(Color.parseColor("#90000000"));

    }

    public void showtimespop() {
        Log.d("显示","showtimespop");
    //    setPopWidth();
        poplist.showAsDropDown(this);

    }

    //给spinner listview设置 设配器
    public void setmyspinnerlistadpater(ListAdapter adapter) {
        listview.setAdapter(adapter);

    }

    //设置spinner 上的文字
    public void setTextViewtext(String text) {
        textView.setText(text);
    }
    public String getTextViewtext() {
       return textView.getText().toString();
    }

    //设置spinner 上item的点击事件
    public void setlistviewitemonclick(AdapterView.OnItemClickListener onClickListener) {
        listview.setOnItemClickListener(onClickListener);
    }
    //设置spinner 上item的点击事件
    public void setlistviewitemonclick(final MySpinnerAPI onClickListener) {

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTextViewtext(onClickListener.onclick(position));
                popdismiss();
            }
        });
    }

    public void popdismiss() {
        poplist.dismiss();
    }

    public void setPopWidth(int width) {
        poplist.setWidth(DensityUtil.dip2px(width));
    }
    public void setPopHeight(int height) {
        poplist.setHeight(DensityUtil.dip2px(height));
    }
    public void setPopWidth() {
        this.post(new Runnable() {
            @Override
            public void run() {
                poplist.setWidth(getWidth());
            }
        });

    }

    public  interface MySpinnerAPI{
        String onclick(int position);
    }
}
