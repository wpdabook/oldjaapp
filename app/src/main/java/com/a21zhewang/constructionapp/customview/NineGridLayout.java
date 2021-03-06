package com.a21zhewang.constructionapp.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.a21zhewang.constructionapp.base.BasexqActivity;
import com.a21zhewang.constructionapp.bean.AqscxqBean;
import com.a21zhewang.constructionapp.bean.ItemFileList;
import com.a21zhewang.constructionapp.bean.NineGridModel;
import com.a21zhewang.constructionapp.utils.ImageLoaderUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import cn.forward.androids.views.RatioImageView;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/12
 */
public class NineGridLayout extends BaseNineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridLayout(Context context) {
        super(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
    }

    @Override
    protected void onClickImage(final int i, String url, List<String> urlList) {
        //Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
        List<ItemFileList> itemFileLists = new ArrayList<>();
        for(int j=0;j<urlList.size();j++){
            ItemFileList item = new ItemFileList();
            item.setUrl(urlList.get(j));
            itemFileLists.add(item);
        }
        PhotoViewActivity.PagerAdapterAPI<ItemFileList> pagerAdapterAPI;
        pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<ItemFileList>() {

            @Override
            public String getstrs(int index, ItemFileList obj) {
                return obj.getUrl();
            }

            @Override
            public String getms(int index, ItemFileList obj) {
                return obj.getFileBrief();
            }
        };
        PhotoViewActivity.statPhotoViewActivity(mContext, i,itemFileLists,pagerAdapterAPI);
    }
}
