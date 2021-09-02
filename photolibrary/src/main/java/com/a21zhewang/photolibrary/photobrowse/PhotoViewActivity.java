package com.a21zhewang.photolibrary.photobrowse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.a21zhewang.photolibrary.R;

import java.util.ArrayList;
import java.util.List;

public class
PhotoViewActivity<T extends Parcelable> extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = PhotoViewActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount,tv_image_ms;
//    private TextView mTvSaveImage;
    private List<String> Urls;
    private List<String>  strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_photo_view);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
       mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
        tv_image_ms = (TextView) findViewById(R.id.tv_image_ms);

//        mTvSaveImage = (TextView) findViewById(R.id.tv_save_image_photo);
       // mTvSaveImage.setOnClickListener(this);

    }
    public interface PagerAdapterAPI<T>
    {
        String getstrs(int index,T obj);
        String getms(int index,T obj);
    }
    private void initData() {

        Urls = new ArrayList<>();
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        List<String> stringArrayListExtra = intent.getStringArrayListExtra("strs");
        strs =  intent.getStringArrayListExtra("ms");
        if (stringArrayListExtra!=null&&stringArrayListExtra.size()>0)
            Urls.addAll(stringArrayListExtra);
        adapter = new MyImageAdapter(Urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition+1 + "/" + Urls.size());
        if (strs!=null&&strs.size()>0){
                tv_image_ms.setText(strs.get(0));
        }
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + Urls.size());
                if (strs!=null&&strs.size()>0){
                    if (!(position>strs.size()))
                    tv_image_ms.setText(strs.get(position));
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
                //save image
//                break;
        }
    }

    /**
     * @param context 上下文
     * @param position 图片位置
     * @param list 图片数组
     */
    public static void statPhotoViewActivity(Context context,int position,List<String> list,List<String> strs) {
        if (list==null||list.size()==0)return;
        Intent intent = new Intent(context, PhotoViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("strs", (ArrayList)list);
        bundle.putStringArrayList("ms", (ArrayList)strs);
        intent.putExtras(bundle);
        intent.putExtra("currentPosition", position);
        (context).startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.ap2,R.anim.ap1);
    }

    /**
     * @param context 上下文
     * @param position 位置
     * @param list 实体数组
     * @param pagerAdapterAPI  从实体里拿到 图片url
     * @param <T>
     */
    public static <T>void statPhotoViewActivity(Context context,int position,List<T>  list,PagerAdapterAPI<T>  pagerAdapterAPI) {
        if (list==null||list.size()==0)return;
        List<String> strings=new ArrayList<>();
        List<String> ms=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            T obj = list.get(i);
            strings.add(pagerAdapterAPI.getstrs(i, obj));
            ms.add(pagerAdapterAPI.getms(i, obj));
        }
        statPhotoViewActivity(context,position,strings,ms);
    }

//    /**
//     * @param context 上下文
//     * @param imageurl 图片url
//     * @param
//     */
//    public static void statPhotoViewActivity(Context context,String  imageurl) {
//        if (TextUtils.isEmpty(imageurl))return;
//        List<String> strings=new ArrayList<>();
//            strings.add(imageurl);
//        statPhotoViewActivity(context,0,strings,new );
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        overridePendingTransition(R.anim.ap2,R.anim.ap1);
        return true;
    }
}