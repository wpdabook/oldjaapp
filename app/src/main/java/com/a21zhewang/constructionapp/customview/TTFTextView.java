package com.a21zhewang.constructionapp.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.a21zhewang.constructionapp.utils.MDFontsUtils;

/**
 * Created by 10430 on 2018/6/3.
 */

@SuppressLint("AppCompatCustomView")
public class TTFTextView extends TextView {
    public TTFTextView(Context context) {
        this(context,null);
    }

    public TTFTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TTFTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        MDFontsUtils.setOcticons(this);
    }

}
