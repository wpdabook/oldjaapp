package com.a21zhewang.constructionapp.ui.menu;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.TabFragmentPagerAdapter;
import com.a21zhewang.constructionapp.base.BaseLazyFragment;
import com.a21zhewang.constructionapp.bean.MenuArea;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.DateUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import android.widget.AdapterView.OnItemClickListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wp-pc on 2020/5/6.
 */

public class CityStationFragment extends BaseLazyFragment {
    @BindView(R.id.title_TB_name)
    TextView title;
    @BindView(R.id.title_back_img)
    ImageView back;
    @BindView(R.id.activity_baselistview_RelativeLayout)
    RelativeLayout rightlinlayout;
    @BindView(R.id.righttext)
    TextView righttext;
    @BindView(R.id.rightimg)
    ImageView rightimg;
    @BindView(R.id.tv_one_number)
    TextView one_number;
    @BindView(R.id.tv_two_number)
    TextView two_number;
    @BindView(R.id.tv_three_number)
    TextView three_number;
    @BindView(R.id.tv_four_number)
    TextView four_number;
    @BindView(R.id.tv_five_number)
    TextView five_number;
    @BindView(R.id.tv_six_number)
    TextView six_number;
    @BindView(R.id.tv_seven_number)
    TextView seven_number;
    @BindView(R.id.tv_eight_number)
    TextView eight_number;
    @BindView(R.id.tag_one)
    TextView tag_one;
    @BindView(R.id.tag_two)
    TextView tag_two;
    @BindView(R.id.tag_three)
    TextView tag_three;
    @BindView(R.id.tag_four)
    TextView tag_four;
    @BindView(R.id.tag_five)
    TextView tag_five;
    @BindView(R.id.tv_one)
    TextView tv_one;
    @BindView(R.id.lin_one)
    LinearLayout lin_one;
    @BindView(R.id.lin_two)
    LinearLayout lin_two;
    @BindView(R.id.lin_three)
    LinearLayout lin_three;
    @BindView(R.id.lin_four)
    LinearLayout lin_four;
    @BindView(R.id.city_lin_five)
    LinearLayout lin_five;
    @BindView(R.id.city_lin_six)
    LinearLayout lin_six;
    @BindView(R.id.city_lin_seven)
    LinearLayout lin_seven;
    @BindView(R.id.station_top_viewpager)
    ViewPager TopStationViewPager;
    @BindView(R.id.city__bottom_viewpager)
    ViewPager BottomViewpager;
    @BindView(R.id.diffvalue1)
    TextView diffvalue1;
    @BindView(R.id.diffvalue2)
    TextView diffvalue2;
    @BindView(R.id.diffvalue3)
    TextView diffvalue3;
    @BindView(R.id.diffvalue4)
    TextView diffvalue4;
    @BindView(R.id.diffvalue5)
    TextView diffvalue5;
    private String mTitle;
    private Dialog loadingDialog;
    private static int sOffScreenLimit = 0;
    private TabFragmentPagerAdapter TopAdapter;
    private TabFragmentPagerAdapter bottomAdapter;
    private List<Fragment> list;
    private List<Fragment> bottomlist;
    private List<MenuArea> menuAreaList = new ArrayList<>();
    private List<String> areaList = new ArrayList<>();
    private ListPopupWindow mListPopupWindow;
    private String areaKey = "ALL";
    private int index;
    private boolean isALLAreaCity = true;



    public static CityStationFragment getInstance(String title) {
        CityStationFragment sf = new CityStationFragment();
        sf.mTitle = title;
        return sf;
    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View v = inflater.inflate(R.layout.city_station_fragment_layout, null);
        ButterKnife.bind(this,v);
        title.setText(mTitle);
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getActivity(), "加载中...");
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                getActivity().finish();
            }
        });
        mListPopupWindow=new ListPopupWindow(getActivity());
        mListPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.popwindows_item_text, areaList));
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        mListPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mListPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_shape));
        mListPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                index = position;
                areaKey = menuAreaList.get(index).getKey();
                ((Act_Menu)getActivity()).setAreaKey(areaKey);
                rightimg.setVisibility(View.GONE);
                righttext.setVisibility(View.VISIBLE);
                righttext.setText(areaList.get(position));
                mListPopupWindow.dismiss();
                initData(areaKey);
            }
        });
        rightlinlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListPopupWindow.setAnchorView(v);
                mListPopupWindow.show();
            }
        });
        TopStationViewPager.setOnPageChangeListener(new MyPagerChangeListener()) ;
        BottomViewpager.setOnPageChangeListener(new MyBottomPagerChangeListener()) ;
//        initFragment();
    }
    /**
     * 放上：懒加载
     * 放下：实时加载
     */
    private void initFragment(){
        list = new ArrayList<>();
        bottomlist = new ArrayList<>();
        list.add(new ConstructionFragment());
        list.add(new EquipmentFragment());
        list.add(new CriticalProjectFragment());
        list.add(new HiddenDanger());
        bottomlist.add(new GovernanceFragment());
        bottomlist.add(new SitePersonnelFragment());
        bottomlist.add(new InspectionCompletionFragment());
        sOffScreenLimit = list.size() - 1;
        if(sOffScreenLimit >1){
            TopStationViewPager.setOffscreenPageLimit(sOffScreenLimit);
            BottomViewpager.setOffscreenPageLimit(2);
        }
        TopAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), list);
        bottomAdapter = new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), bottomlist);
        TopStationViewPager.setAdapter(TopAdapter);
        BottomViewpager.setAdapter(bottomAdapter);
        initView();
    }
    private void initView(){
        lin_one.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
        lin_two.setBackgroundColor(getResources().getColor(R.color.white));
        lin_three.setBackgroundColor(getResources().getColor(R.color.white));
        lin_four.setBackgroundColor(getResources().getColor(R.color.white));
        TopStationViewPager.setCurrentItem(0);
        lin_five.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
        lin_six.setBackgroundColor(getResources().getColor(R.color.white));
        lin_seven.setBackgroundColor(getResources().getColor(R.color.white));
        BottomViewpager.setCurrentItem(0);
    }


    @Override
    protected void loadData() {
        ((Act_Menu)getActivity()).setAreaKey(areaKey); //默认值同步
        init_url("https://data-cd.telsafe.com.cn:8090/API/SGGL.aspx","CD","蔡甸区");
        init_url("https://data-dhgx.telsafe.com.cn:8090/API/SGGL.aspx","DHGX","东湖高新区");
        init_url("https://data-dxh.telsafe.com.cn:8090/API/SGGL.aspx","DXH","东西湖区");
        init_url("https://data-hn.telsafe.com.cn:8080/API/SGGL.aspx","HN","汉南区");
        init_url("https://data-hp.telsafe.com.cn:8080/API/SGGL.aspx","HP","黄陂区");
        init_url("https://data-hs.telsafe.com.cn:8080/API/SGGL.aspx","HS","洪山区");
        init_url("https://safe-hy.telsafe.com.cn:8100/API/SGGL.aspx","HY","汉阳区");
        init_url("https://data-ja.telsafe.com.cn:8090/API/SGGL.aspx","JA","江岸区");
        init_url("https://data-jh.telsafe.com.cn:8080/API/SGGL.aspx","JH","江汉区");
        init_url("https://data-jx.telsafe.com.cn:8090/API/SGGL.aspx","JX","江夏区");
        init_url("https://data-qk.telsafe.com.cn:8080/API/SGGL.aspx","QK","硚口区");
        init_url("https://safetycore.telsafe.com.cn:8031/API/SGGL.aspx","QS","青山区");
        init_url("https://data-wc.telsafe.com.cn:8090/API/SGGL.aspx","WC","武昌区");
        init_url("https://data-xz.telsafe.com.cn:8080/API/SGGL.aspx","XZ","新洲区");
        init_url("https://safetycore.telsafe.com.cn:8088/API/SGGL.aspx","SZ","市站");
        if(isALLAreaCity){
            loadAreaData();
        }else {
            initData(areaKey);
        }
    }
    private void loadAreaData(){
        XUtil.postjsondata(JsonUtils.getjsonobj("{}"), "GetCitySiteAreaList", new MyCallBack() {
            @Override
            public void onResule(String res) {
                try {
                    JSONArray array = new JSONArray(res);
                    for(int i=0;i<array.length();i++){
                        JSONObject tempobject = array.optJSONObject(i);
                        areaList.add(tempobject.optString("keyName"));
                        MenuArea area = new MenuArea();
                        area.setKey(tempobject.optString("key"));
                        area.setKeyName(tempobject.optString("keyName"));
                        menuAreaList.add(area);
                    }
                    rightlinlayout.setVisibility(View.VISIBLE);
                    initData(areaKey);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }
    protected void initData(final String areaKey) {
        loadingDialog.show();
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"areaKey\":\"" + areaKey + "\"}"), "GetCitySiteBasics", new MyCallBack() {
            @Override
            public void onResule(String res) {
                loadingDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(res);
                    one_number.setText(object.optInt("cityProjectTotal")+"");
                    two_number.setText(object.optInt("yestReportTotal")+"");
                    three_number.setText(object.optInt("sysConfigUserTotal")+"");
                    four_number.setText(object.optInt("yestUsesTotal")+"");
                    five_number.setText(object.optInt("municEnginTotal")+"");
                    six_number.setText(object.optInt("railTranTotal")+"");
                    seven_number.setText(object.optInt("realEstateTotal")+"");
                    eight_number.setText(object.optInt("nRealEstateTotal")+"");
                    tag_one.setText(object.optInt("rectHidden")+"");
                    tag_two.setText(object.optInt("allRectDanger")+"");
                    tag_three.setText(object.optInt("abnormal")+"");
                    tag_four.setText(object.optInt("mechanics")+"");
                    tag_five.setText(object.optInt("danger")+"");
                    int value1 = object.optInt("rectHidden") - object.optInt("yestRectHidden");
                    setTextCountDiff(diffvalue1,value1);
                    int value2 = object.optInt("allRectDanger")-object.optInt("yestAllRectDanger");
                    setTextCountDiff(diffvalue2,value2);
                    int value3 = object.optInt("abnormal")-object.optInt("yestAbnormal");
                    setTextCountDiff(diffvalue3,value3);
                    int value4 = object.optInt("mechanics")-object.optInt("yestMechanics");
                    setTextCountDiff(diffvalue4,value4);
                    int value5 = object.optInt("danger")-object.optInt("yestDanger");
                    setTextCountDiff(diffvalue5,value5);
                    if(areaKey == null || "NULL".equals(areaKey)|| "ALL".equals(areaKey) || "".equals(areaKey)){
                        tv_one.setText("全市在监项目");
                    }else {
                        tv_one.setText("全区在监项目");
                    }
                    initFragment();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }
        });
    }
    @OnClick({R.id.lin_one,
            R.id.lin_two,
            R.id.lin_three,
            R.id.lin_four,
            R.id.city_lin_five,
            R.id.city_lin_six,
            R.id.city_lin_seven
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_one:
                lin_one.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                TopStationViewPager.setCurrentItem(0);
                break;
            case R.id.lin_two:
                lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                lin_two.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                TopStationViewPager.setCurrentItem(1);
                break;
            case R.id.lin_three:
                lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                lin_three.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                TopStationViewPager.setCurrentItem(2);
                break;
            case R.id.lin_four:
                lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                lin_four.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                TopStationViewPager.setCurrentItem(3);
                break;
            case R.id.city_lin_five:
                lin_five.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                lin_six.setBackgroundColor(getResources().getColor(R.color.white));
                lin_seven.setBackgroundColor(getResources().getColor(R.color.white));
                BottomViewpager.setCurrentItem(0);
                break;
            case R.id.city_lin_six:
                lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                lin_six.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                lin_seven.setBackgroundColor(getResources().getColor(R.color.white));
                BottomViewpager.setCurrentItem(1);
                break;
            case R.id.city_lin_seven:
                lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                lin_six.setBackgroundColor(getResources().getColor(R.color.white));
                lin_seven.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                BottomViewpager.setCurrentItem(2);
                break;
        }
    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
//            list.get(arg0).initData();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    lin_one.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 1:
                    lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_two.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 2:
                    lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_three.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    lin_four.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 3:
                    lin_one.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_two.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_three.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_four.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    break;
            }

        }

    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyBottomPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
//            list.get(arg0).initData();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    lin_five.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    lin_six.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_seven.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 1:
                    lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_six.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    lin_seven.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 2:
                    lin_five.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_six.setBackgroundColor(getResources().getColor(R.color.white));
                    lin_seven.setBackground(getResources().getDrawable(R.drawable.fragment_switch_lin_bg));
                    break;
            }

        }
    }
    /**
     * 默认根据服务器地址判断加载区站/市站信息
     */
    public  void  init_url(String url,String key,String mtitle){
        if(url.equals(PublicUtils.URL)){
            areaKey = key;
            isALLAreaCity = false;
            ((Act_Menu)getActivity()).setAreaKey(areaKey);
            rightlinlayout.setVisibility(View.GONE);
            return;
        }
    }
    private void setTextCountDiff(TextView textView,int value){
        if(value == 0){
            textView.setText("较昨日"+"+"+value);
            textView.setTextColor(getResources().getColor(R.color.color_666666));
        }else if(value > 0){
            textView.setText("较昨日"+"+"+value);
            textView.setTextColor(getResources().getColor(R.color.color_qq));
        }else {
            textView.setText("较昨日"+value);
            textView.setTextColor(getResources().getColor(R.color.color_yyh));
        }
    }
}
