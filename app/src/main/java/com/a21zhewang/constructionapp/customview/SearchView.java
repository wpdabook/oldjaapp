package com.a21zhewang.constructionapp.customview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.KeyboardChangeListener;

/**
 * Created by zhewang_ljw on 2017/6/20.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class SearchView extends LinearLayout {
    private LinearLayout searchviewTopLintopChildview;
    private LinearLayout searchviewTopLintop;
    private LinearLayout searchviewTopLinEdittext;
    private EditText searchview_top_edittext;
    private ImageView searchview_top_imageView;
    private Context mContext;
    float endindex;
    private ISearchView iSearchView;
    public void setiSearchView(ISearchView iSearchView) {
        this.iSearchView = iSearchView;
    }


    public interface  ISearchView{
        void onSearchListener (String edittext);
    }
    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

                init(context);


    }

    private void init(Context context) {
        mContext = context;
        View.inflate(context, R.layout.searchview, this);
        searchviewTopLintopChildview= (LinearLayout) findViewById(R.id.searchview_top_lintop_childview);
        searchviewTopLintop=(LinearLayout) findViewById(R.id.searchview_top_lintop);
        searchviewTopLinEdittext=(LinearLayout) findViewById(R.id.searchview_top_lin_edittext);
        searchview_top_edittext= (EditText) findViewById(R.id.searchview_top_edittext);
        searchview_top_imageView= (ImageView) findViewById(R.id.searchview_top_imageView);
        searchview_top_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean isOK = true;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        if (iSearchView!=null){
                          //  PublicUtils.toast("点击-->SEARCH"+searchview_top_edittext.getText().toString().trim());
//                              /*隐藏软键盘*/
//                            InputMethodManager imm = (InputMethodManager) v
//                                    .getContext().getSystemService(
//                                            Context.INPUT_METHOD_SERVICE);
//                            if (imm.isActive()) {
//                                imm.hideSoftInputFromWindow(
//                                        v.getApplicationWindowToken(), 0);
//                            }
                            iSearchView.onSearchListener(searchview_top_edittext.getText().toString().trim());
                        }
                       // PublicUtils.toast("点击-->SEARCH");
                        break;
                    default:
                        isOK = false;
                        break;
                }
                return isOK;
            }
        });
        searchviewTopLintop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                startcurTranslationX(searchviewTopLintopChildview);
            }
        });

        new KeyboardChangeListener((Activity) mContext).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (!isShow){
                   // searchview_top_edittext.setText("");
                    searchviewTopLinEdittext.setVisibility(GONE);
                    searchviewTopLintop.setVisibility(INVISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(searchviewTopLintopChildview, "translationX",endindex, 0.0f);
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            searchviewTopLintop.setVisibility(VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animator.setDuration(500);
                    animator.start();
                }

            }
        });
    }
    private void startcurTranslationX(final View view){

        if (endindex==0){
            endindex=searchviewTopLintop.getPaddingLeft()-searchviewTopLintopChildview.getX();
        }
      //  PublicUtils.log("curTranslationX:"+curTranslationX);
      //  PublicUtils.log("getPaddingLeft:"+searchviewTopLinEdittext.getPaddingLeft());
        PublicUtils.log("endindex:"+endindex);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX",0.0f,endindex);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                searchviewTopLintop.setVisibility(GONE);
                searchviewTopLintop.setEnabled(true);
                searchviewTopLinEdittext.setVisibility(VISIBLE);
                showSoftInputFromWindow(mContext,searchview_top_edittext);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }
/**
    * EditText获取焦点并显示软键盘
    */
    public static void showSoftInputFromWindow(Context activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
     //  ((Activity)activity).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //打开软键盘
        InputMethodManager imm = (InputMethodManager)(activity.getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
