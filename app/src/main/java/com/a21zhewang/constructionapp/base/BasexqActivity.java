package com.a21zhewang.constructionapp.base;


import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.bean.AqscxqBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.receiver.PushMessageReceiver;
import com.a21zhewang.constructionapp.ui.IndexActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.GtxtxqActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.GtxtzfActivity;
import com.a21zhewang.constructionapp.ui.gtxtiu.ZF2Activity;
import com.a21zhewang.constructionapp.ui.wdjc.WdjcProjectInfoActivity;
import com.a21zhewang.constructionapp.utils.AppShortCutUtil;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.LocationUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.lodindshowlibrary.WeiboDialogUtils;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public abstract class BasexqActivity extends BaseActivity {


    @BindView(R.id.activity_basexq_MyTitleBar)
    public MyTitleBar activityBasexqMyTitleBar;
    @BindView(R.id.activity_basexq_tv_title)
    public TextView activityBasexqTvTitle;
    @BindView(R.id.activity_basexq_headerview)
    public LinearLayout activityBasexqHeaderview;
    @BindView(R.id.activity_basexq_tv_ckrz)
    public TextView activityBasexqTvCkrz;
    @BindView(R.id.activity_basexq_tv_nrms)
    public TextView activityBasexqTvNrms;
    @BindView(R.id.activity_basexq_RecyclerView_imgs)
    public RecyclerView activityBasexqRecyclerViewImgs;
    @BindView(R.id.activity_basexq_MyListView_cyjls)
    public MyListView activityBasexqMyListViewCyjls;
    @BindView(R.id.activity_basexq_MyListView_cljls)
    public MyListView activityBasexqMyListViewCljls;
    @BindView(R.id.activity_basexq_EditText_cljg)
    public EditText activityBasexqEditTextCljg;
    @BindView(R.id.activity_basexq_LinearLayout_cljg)
    public LinearLayout activityBasexqLinearLayoutCljg;
    @BindView(R.id.activity_basexq_tv_hf)
    public TextView activityBasexqTvHf;
    @BindView(R.id.activity_basexq_tv_xf)
    public TextView activityBasexqTvxf;
    @BindView(R.id.activity_basexq_tv_zf)
    public TextView activityBasexqTvzf;
    @BindView(R.id.activity_basexq_tv_yzg)
    public TextView activityBasexqTvYzg;
    @BindView(R.id.activity_basexq_tv_fytg)
    public TextView activityBasexqTvFytg;
    @BindView(R.id.activity_basexq_tv_fybtg)
    public TextView activityBasexqTvFybtg;
    @BindView(R.id.activity_basexq_tv_xmmc)
    TextView activityBasexqTvXmmc;
    @BindView(R.id.activity_basexq_tv_wtbw)
    TextView activityBasexqTvWtbw;
    @BindView(R.id.activity_basexq_tv_sgqy)
    TextView activityBasexqTvSgqy;
    @BindView(R.id.activity_basexq_tv_lc)
    TextView activityBasexqTvLc;
    @BindView(R.id.activity_basexq_tv_fxlb)
    TextView activityBasexqTvfxlb;
    @BindView(R.id.activity_basexq_LinearLayout_fxdj)
    LinearLayout activityBasexqLinearLayoutFxdj;
    @BindView(R.id.activity_basexq_tv_zgjz)
    TextView activityBasexqTvZgjz;
    @BindView(R.id.activity_basexq_tv_zgdw)
    TextView activityBasexqTvZgdw;
    @BindView(R.id.activity_basexq_LinearLayout_bw)
    LinearLayout activity_basexq_LinearLayout_bw;

    @BindView(R.id.activity_basexq_tv_bw)
    TextView activity_basexq_tv_bw;
    private Dialog loadingDialog;



    /**
     * fileBrief : 这是测试的图片描述
     * url : http://localhost:12224/upload/images/xx.jpg
     */
    public List<AqscxqBean.FilesBean> imgfiles;

    public String recordID = "";
    /**
     * 如果不为空，说明是从其他地方同步过来的数据，只允许用户查看，不能操作
     */
    public String ApiUrl = "";
    /**
     * userName : 张三
     * etpShortName : 总承包
     * status : 已读
     * time : 05-16 10:10
     */

    public List<AqscxqBean.ReadLogsBean> readLogs;
    /**
     * userName : 发起人：李四
     * etpShortName : 总承包
     * content : 李四发起协调请求
     * time : 05-10 17:13
     */

    public List<AqscxqBean.DealLogsBean> dealLogs;
    /**
     * btnID : cmBtn5
     * btnName : 回复
     */

    public List<AqscxqBean.ButtonsBean> buttons;
    public CommonAdapter<AqscxqBean.FilesBean> imgsAdapter;
    public com.zhy.adapter.abslistview.CommonAdapter<AqscxqBean.ReadLogsBean> readLogsAdapter;
    public com.zhy.adapter.abslistview.CommonAdapter<AqscxqBean.DealLogsBean> dealLogsAdapter;
    @BindView(R.id.activity_basexq_tv_lctv)
    TextView activityBasexqTvLctv;
    @BindView(R.id.activity_basexq_ImageView_cyjls)
    ImageView activityBasexqImageViewCyjls;
    private String projectShortName="";
    private String xPoint,yPoint;




    /**
     * @return 设置标题文字
     */
    public abstract String settitlebar();


    /**
     * @return 请求方法
     */
    public abstract String getdatamethod();

    /**
     * @return postdata方法
     */
    public abstract String postdatamethod();
//    /**
//     * @return 请求参数
//     */
//    public abstract String getdataparameter();


    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_basexq3;
    }

    public String getdataparameter() {
        String recordID = getIntent().getStringExtra("recordID");
        return recordID;
    }
    public String getApiUrl() {
        String ApiUrl = getIntent().getStringExtra("ApiUrl");
//        String ApiUrl = "https://wuhan.21theone.cn:8889/API/SGGL.aspx";
        return ApiUrl;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        getLocation();
        loadingDialog = WeiboDialogUtils.createLoadingDialog(this, "加载中...");
        recordID = getdataparameter();
        ApiUrl = getApiUrl();
        imgfiles = new ArrayList<>();
        readLogs = new ArrayList<>();
        dealLogs = new ArrayList<>();
        buttons = new ArrayList<>();
        activityBasexqMyTitleBar.setTitlenametv(settitlebar());

        //图片列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityBasexqRecyclerViewImgs.setLayoutManager(linearLayoutManager);
        imgsAdapter = new CommonAdapter<AqscxqBean.FilesBean>(this, R.layout.faqigtxt_imglist_item, imgfiles) {

            @Override
            protected void convert(ViewHolder holder, AqscxqBean.FilesBean s, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(BasexqActivity.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<AqscxqBean.FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<AqscxqBean.FilesBean>() {

                            @Override
                            public String getstrs(int index, AqscxqBean.FilesBean obj) {

                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, AqscxqBean.FilesBean obj) {

                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(BasexqActivity.this, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        activityBasexqRecyclerViewImgs.setAdapter(imgsAdapter);
        //查阅纪录初始化
        View cyjlheadview = LayoutInflater.from(this).inflate(R.layout.gtxyxq_cyjl_list_headview, null, false);
        activityBasexqMyListViewCyjls.addHeaderView(cyjlheadview);
        readLogsAdapter = new com.zhy.adapter.abslistview.CommonAdapter<AqscxqBean.ReadLogsBean>(this
                , R.layout.gtxtxq_cyjl_list_item, readLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final AqscxqBean.ReadLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, item.getStatus());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, item.getTime());
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PublicUtils.call(item.getUserPhone(),BasexqActivity.this);
                    }
                });
            }
        };
        activityBasexqMyListViewCyjls.setAdapter(readLogsAdapter);
        //处理记录初始化

        dealLogsAdapter = new com.zhy.adapter.abslistview.CommonAdapter<AqscxqBean.DealLogsBean>(this
                , R.layout.gtxtxq_cljl_item, dealLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, AqscxqBean.DealLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cljl_tv_dw, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_nr, item.getContent());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_zfsj, item.getTime());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_zfr, item.getUserName());
            }
        };
        activityBasexqMyListViewCljls.setAdapter(dealLogsAdapter);

    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        //  getdata(msgID);
    }


    public String btntotext(String btn) {
        if (btn.equals("cmBtn1")) {
            return "催办";
        } else if (btn.equals("cmBtn2")) {
            return "闭合";
        } else if (btn.equals("cmBtn3")) {
            return "已处理";
        } else if (btn.equals("cmBtn4")) {
            return "转发";
        } else if (btn.equals("cmBtn5")) {
            return "回复";
        } else return "";

    }

    /**
     * 页面显示
     */
    @Override
    public void resume() {
        loadingDialog.show();
        if (0 != PushMessageReceiver.count) {
            //角标清空
            PushMessageReceiver.count = 0;
            AppShortCutUtil.setCount(PushMessageReceiver.count, BasexqActivity.this);
        }
        getdata();
    }

    private void getdata() {
        XUtil.postjsondata(JsonUtils.getjsonobj("{\"recordID\":\"" + recordID + "\"}"), getdatamethod(), new MyCallBack() {
            @Override
            public void onResule(String res) {
                final AqscxqBean aqscxqBean = JsonUtils.getbean(res, AqscxqBean.class);
                loadingDialog.dismiss();
                projectShortName = aqscxqBean.getProjectShortName();
                activityBasexqTvTitle.setText(aqscxqBean.getTitle());
                activityBasexqTvXmmc.setText(aqscxqBean.getProjectShortName());//项目名称
                String floor = aqscxqBean.getFloor();
                if (TextUtils.isEmpty(floor)) {
                    activityBasexqTvLctv.setText("1".equals(aqscxqBean.getProjectType())?"标高":"施工位置");
                    activityBasexqTvLc.setText(aqscxqBean.getElevation());
                } else {
                    activityBasexqTvLctv.setText("1".equals(aqscxqBean.getProjectType())?"楼层":"里程");
                    activityBasexqTvLc.setText(floor);//楼层
                }
                String subProjectName = aqscxqBean.getSubProjectName();
                if (!TextUtils.isEmpty(subProjectName)){
                    activity_basexq_LinearLayout_bw.setVisibility(View.VISIBLE);
                    activity_basexq_tv_bw.setText(subProjectName);
                }
                activityBasexqTvSgqy.setText(aqscxqBean.getProjectAreaName());
                activityBasexqTvZgjz.setText(aqscxqBean.getCutoffTime());
                activityBasexqTvNrms.setText(aqscxqBean.getContent());
                String typeGraFaName = aqscxqBean.getTypeGraFaName();
                String fxlb_str = TextUtils.isEmpty(typeGraFaName) ? "" : typeGraFaName + "->\n";
                activityBasexqTvfxlb.setText(
                        aqscxqBean.getTypeName());
                activityBasexqTvZgdw.setText(aqscxqBean.getRecEtpShortName());
                //查阅记录
                readLogs.clear();
                List<AqscxqBean.ReadLogsBean> cyjlBeanList = aqscxqBean.getReadLogs();
                if (cyjlBeanList != null && cyjlBeanList.size() > 0) {
                    readLogs.addAll(cyjlBeanList);
                }
                readLogsAdapter.notifyDataSetChanged();


                //处理记录
                dealLogs.clear();
                List<AqscxqBean.DealLogsBean> cljlBeanList = aqscxqBean.getDealLogs();
                if (cljlBeanList != null && cljlBeanList.size() > 0) {
                    dealLogs.addAll(cljlBeanList);
                }
                dealLogsAdapter.notifyDataSetChanged();

                //图片
                imgfiles.clear();
                List<AqscxqBean.FilesBean> filesBeanList = aqscxqBean.getFiles();

                if (filesBeanList != null && filesBeanList.size() > 0) {

                    imgfiles.addAll(filesBeanList);
                    activityBasexqRecyclerViewImgs.setVisibility(View.VISIBLE);
                } else {
                    activityBasexqRecyclerViewImgs.setVisibility(View.GONE);
                }
                imgsAdapter.notifyDataSetChanged();
                //btn显示
                List<AqscxqBean.ButtonsBean> buttons = aqscxqBean.getButtons();

                //activityGtxtxqTextViewHf.setVisibility(View.GONE);
                // activityGtxtxqTvCkrz.setVisibility(View.GONE);
                if (TextUtils.isEmpty(ApiUrl) && buttons != null && buttons.size() > 0) {
                    activityBasexqLinearLayoutCljg.setVisibility(View.VISIBLE);
                    activityBasexqTvYzg.setVisibility(View.GONE);
                    activityBasexqTvFytg.setVisibility(View.GONE);
                    activityBasexqTvFybtg.setVisibility(View.GONE);
                    activityBasexqTvHf.setVisibility(View.GONE);
                    activityBasexqTvxf.setVisibility(View.GONE);
                    activityBasexqTvzf.setVisibility(View.GONE);
                    for (int i = 0; i < buttons.size(); i++) {
                        AqscxqBean.ButtonsBean buttonsBean = buttons.get(i);
                        String btnID = buttonsBean.getBtnID();
                        String btnName = buttonsBean.getBtnName();
                        if (btnID.equals("cmBtn1")) {
                            //   activityGtxtxqTextViewCb.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn2")) {
                            activityBasexqTvYzg.setText(btnName);
                            activityBasexqTvYzg.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn3")) {
                            activityBasexqTvFytg.setText(btnName);
                            activityBasexqTvFytg.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn4")) {
                            activityBasexqTvFybtg.setText(btnName);
                            activityBasexqTvFybtg.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn5")) {
                            activityBasexqTvHf.setText(btnName);
                            activityBasexqTvHf.setVisibility(View.VISIBLE);

                        }
                        if (btnID.equals("cmBtn8")) {
                            activityBasexqTvxf.setText(btnName);
                            activityBasexqTvxf.setVisibility(View.VISIBLE);

                        }
                        //转发按钮隐藏
//                        if (btnID.equals("cmBtn7")) {
//                            activityBasexqTvzf.setText(btnName);
//                            activityBasexqTvzf.setVisibility(View.VISIBLE);
//
//                        }
                        if (btnID.equals("cmBtnDaily")) {
                            //    activityGtxtxqTvCkrz.setVisibility(View.VISIBLE);

                        }
                    }
                } else {
                    activityBasexqLinearLayoutCljg.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                setenable();
            }
        });
    }

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {

    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }


    @OnClick({R.id.activity_basexq_tv_hf,
            R.id.activity_basexq_tv_yzg,
            R.id.activity_basexq_tv_fytg,
            R.id.activity_basexq_tv_fybtg,
            R.id.activity_basexq_LinearLayout_cyjls,
            R.id.activity_basexq_tv_xf,
            R.id.activity_basexq_tv_zf
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_basexq_tv_hf:
                if (panduan()) break;
                activityBasexqTvHf.setEnabled(false);
                postjsondata("cmBtn5");
                break;
            case R.id.activity_basexq_tv_yzg:
//                if (panduan()) break;
//                activityBasexqTvYzg.setEnabled(false);
//                postjsondata("cmBtn2");
                Intent intent = new Intent(BasexqActivity.this, YzgActivity.class);
                intent.putExtra("recordID",recordID);
                intent.putExtra("projectShortName",projectShortName);
                intent.putExtra("postdatamethod",postdatamethod());
                intent.putExtra("xPoint",xPoint);
                intent.putExtra("yPoint",yPoint);
                startActivity(intent);
                break;
            case R.id.activity_basexq_tv_fytg:
                if (panduan()) break;
                activityBasexqTvFytg.setEnabled(false);
                postjsondata("cmBtn3");
                break;
            case R.id.activity_basexq_tv_fybtg:
                //  if (panduan()) break;
                activityBasexqTvFybtg.setEnabled(false);
                postjsondata("cmBtn4");
                break;
            case R.id.activity_basexq_LinearLayout_cyjls:
                if (activityBasexqMyListViewCyjls.getVisibility()==View.VISIBLE){
                    activityBasexqMyListViewCyjls.setVisibility(View.GONE);
                    activityBasexqImageViewCyjls.setImageResource(R.mipmap.arrowdown);
                }else{
                    activityBasexqMyListViewCyjls.setVisibility(View.VISIBLE);
                    activityBasexqImageViewCyjls.setImageResource(R.mipmap.arrowup);
                }
                break;

            case R.id.activity_basexq_tv_zf:
                Intent zfintent=new Intent();
                zfintent.setClass(BasexqActivity.this, ZF2Activity.class);
                zfintent.putExtra("msgid", recordID);
                if ("安全检查".equals(settitlebar())){
                    zfintent.putExtra("method","GetSafetyMsgTransmit" );
                }else if ("质量检查".equals(settitlebar())){
                    zfintent.putExtra("method","GetQualityMsgTransmit" );
                }else if ("文明施工".equals(settitlebar())){
                    zfintent.putExtra("method","GetCivilizationMsgTransmit" );
                }
                startActivity(zfintent);
                break;
            case R.id.activity_basexq_tv_xf:
                Intent minIntent=new Intent();
                minIntent.setClass(BasexqActivity.this, XFActivity.class);
                minIntent.putExtra("msgid", recordID);
                minIntent.putExtra("isxf", true);
                if ("安全检查".equals(settitlebar())){
                    minIntent.putExtra("method","GetSafetyMsgTransmit" );
                }else if ("质量检查".equals(settitlebar())){
                    minIntent.putExtra("method","GetQualityMsgTransmit" );
                }else if ("文明施工".equals(settitlebar())){
                    minIntent.putExtra("method","GetCivilizationMsgTransmit" );
                }

                startActivity(minIntent);
                break;
        }
    }

    private boolean panduan() {
        String content = activityBasexqEditTextCljg.getText().toString();
        if (content.length() == 0) {

            PublicUtils.toast("请输入处理结果");
            return true;
        }
        return false;
    }

    public void postjsondata(String btnid) {
        String content = activityBasexqEditTextCljg.getText().toString();
        XUtil.postjsondata(
                JsonUtils.getjsonobj("{\"btnID\":\"" + btnid + "\",\"createUserName\":\"" + PublicUtils.userBean.getUserName() + "\",\"dealContent\":\"" + content + "\",\"createUserID\":\"" + PublicUtils.userBean.getUserID() + "\",\"recordID\":\"" + recordID + "\"}"),
                postdatamethod(),
                new MyCallBack() {
                    @Override
                    public void onResule(String res) {
                        activityBasexqEditTextCljg.setText("");
                        getdata();
                    }

                    @Override
                    public void onFail(ObjBean getbean) {
                        setenable();
                    }

                    @Override
                    public void onWrong() {
                        setenable();
                    }


                });
    }

    private void setenable() {
        activityBasexqTvYzg.setEnabled(true);
        activityBasexqTvFytg.setEnabled(true);
        activityBasexqTvFybtg.setEnabled(true);
        activityBasexqTvHf.setEnabled(true);
    }

    public void getLocation(){
        Location location = LocationUtils.getInstance(BasexqActivity.this ).showLocation();
        if (location != null) {
            double x = location.getLongitude();
            double y = location.getLatitude();
            xPoint = String.format("%.3f", x);
            yPoint = String.format("%.3f", y);
        }
    }

}
