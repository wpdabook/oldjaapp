package com.a21zhewang.constructionapp.bean;

import com.a21zhewang.constructionapp.ui.map.ClusterItem;
import com.amap.api.maps.model.LatLng;

/**
 * Created by yiyi.qi on 16/10/10.
 */

public class RegionItem implements ClusterItem {
    @Override
    public String toString() {
        return "RegionItem{" +
                "mLatLng=" + mLatLng +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }

    private LatLng mLatLng;
    private String mTitle;

    public RegionItem(LatLng latLng, String title) {
        mLatLng=latLng;
        mTitle=title;
    }

    @Override
    public LatLng getPosition() {
        // TODO Auto-generated method stub
        return mLatLng;
    }

    public String getTitle(){
        return mTitle;
    }

}
