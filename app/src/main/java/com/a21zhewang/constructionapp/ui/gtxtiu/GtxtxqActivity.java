package com.a21zhewang.constructionapp.ui.gtxtiu;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.GtxtxqBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.MyTitleBar;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.sgrz.SgrzActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GtxtxqActivity extends BaseActivity {


    @BindView(R.id.activity_gtxtxq_RecyclerView_imgs)
    RecyclerView activityGtxtxqRecyclerViewImgs;
    @BindView(R.id.activity_gtxtxq_MyListView_cyjls)
    MyListView activityGtxtxqMyListViewCyjls;
    @BindView(R.id.activity_gtxtxq_MyListView_cljls)
    MyListView activityGtxtxqMyListViewCljls;
    @BindView(R.id.activity_gtxtxq_tv_title)
    TextView activityGtxtxqTvTitle;
    @BindView(R.id.activity_gtxtxq_tv_pjname)
    TextView activityGtxtxqTvPjname;
    @BindView(R.id.activity_gtxtxq_tv_xtlb)
    TextView activityGtxtxqTvXtlb;
    @BindView(R.id.activity_gtxtxq_tv_sgqy)
    TextView activityGtxtxqTvSgqy;

    @BindView(R.id.activity_gtxtxq_tv_sqr)
    TextView activityGtxtxqTvSqr;
    @BindView(R.id.activity_gtxtxq_tv_sqsj)
    TextView activityGtxtxqTvSqsj;
    @BindView(R.id.activity_gtxtxq_tv_nrms)
    TextView activityGtxtxqTvNrms;
    @BindView(R.id.activity_gtxtxq_tv_gx)
    TextView activityGtxtxqTvGx;
    @BindView(R.id.activity_gtxtxq_tv_lc)
    TextView activityGtxtxqTvLc;
    @BindView(R.id.activity_gtxtxq_tv_lcAndbg)
    TextView activityGtxtxqTvLcAndbg;
    @BindView(R.id.activity_gtxtxq_tv_sgdw)
    TextView activityGtxtxqTvSgdw;
    @BindView(R.id.activity_gtxtxq_MyTitleBar)
    MyTitleBar activityGtxtxqMyTitleBar;
    @BindView(R.id.activity_gtxtxq_EditText_cljg)
    EditText activityGtxtxqEditTextCljg;
    @BindView(R.id.activity_gtxtxq_TextView_bh)
    TextView activityGtxtxqTextViewBh;
    @BindView(R.id.activity_gtxtxq_TextView_ycl)
    TextView activityGtxtxqTextViewYcl;
    @BindView(R.id.activity_gtxtxq_TextView_zf)
    TextView activityGtxtxqTextViewZf;
    @BindView(R.id.activity_gtxtxq_TextView_hf)
    TextView activityGtxtxqTextViewHf;
    @BindView(R.id.activity_gtxtxq_TextView_cb)
    TextView activityGtxtxqTextViewCb;

    @BindView(R.id.activity_gtxtxq_LinearLayout_cljg)
    LinearLayout activityGtxtxqLinearLayoutCljg;
    @BindView(R.id.activity_gtxtxq_tv_ckrz)
    TextView activityGtxtxqTvCkrz;
    @BindView(R.id.activity_gtxtxq_ImageView_cyjls)
    ImageView activityGtxtxqImageViewCyjls;
    @BindView(R.id.activity_gtxtxq_TextView_zf2)
    TextView activityGtxtxqTextViewZf2;

    private CommonAdapter commonAdapter;
    private List<GtxtxqBean.FilesBean> imglist;
    private List<String> imgslist;
    private List<String> imgsms;
    private TextView showimage_textView;
    private RecyclerView imagesviews;
    private PopupWindow imagespopupWindow;
    private LinearLayoutManager layoutManager;
    private View rootView;
    private String msgID;
    private List<GtxtxqBean.ReadLogsBean> cyjls;
    private com.zhy.adapter.abslistview.CommonAdapter<GtxtxqBean.ReadLogsBean> cyjlBeanCommonAdapter;
    private List<GtxtxqBean.DealLogsBean> cljls;
    private com.zhy.adapter.abslistview.CommonAdapter<GtxtxqBean.DealLogsBean> cljlBeanCommonAdapter;
    private String isycl = "";
    private View.OnTouchListener touchListener;
    private MovementMethod scrollinstance;
private boolean isScroll=true;
    private GtxtxqBean gtxtxqBean;

    /**
     * setContentView之前调用
     */
    @Override
    public void beforesetContentView() {

    }

    /**
     * @return 设置布局文件
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_gtxtxq;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {

        msgID = getIntent().getStringExtra("msgID");//详细id
        imglist = new ArrayList<>();
        imgsms = new ArrayList<>();
        imgslist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        activityGtxtxqRecyclerViewImgs.setLayoutManager(linearLayoutManager);
        commonAdapter = new CommonAdapter<GtxtxqBean.FilesBean>(this, R.layout.faqigtxt_imglist_item, imglist) {

            @Override
            protected void convert(ViewHolder holder, GtxtxqBean.FilesBean s, final int position) {
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(GtxtxqActivity.this).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        PictureConfig.getPictureConfig().init(options).openPhoto(AddGtxtActivity.this, resultCall);
                        //  showimage_textView.setText(position + 1 + "/" + imglist.size());
                        //  layoutManager.scrollToPosition(position);
                        //imagespopupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                        PhotoViewActivity.statPhotoViewActivity(GtxtxqActivity.this, position, imgslist, imgsms);
                    }
                });
                holder.setText(R.id.faqigtxt_imglist_item_ms,s.getFileBrief());
            }
        };
        activityGtxtxqRecyclerViewImgs.setAdapter(commonAdapter);
//查阅纪录初始化
        cyjls = new ArrayList<>();
        View cyjlheadview = LayoutInflater.from(this).inflate(R.layout.gtxyxq_cyjl_list_headview, null, false);
        activityGtxtxqMyListViewCyjls.addHeaderView(cyjlheadview);
        cyjlBeanCommonAdapter = new com.zhy.adapter.abslistview.CommonAdapter<GtxtxqBean.ReadLogsBean>(this
                , R.layout.gtxtxq_cyjl_list_item, cyjls) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final GtxtxqBean.ReadLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, item.getStatus());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, item.getTime());
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PublicUtils.call(item.getUserPhone(),GtxtxqActivity.this);
                    }
                });
            }
        };

        activityGtxtxqMyListViewCyjls.setAdapter(cyjlBeanCommonAdapter);
//处理记录初始化
        cljls = new ArrayList<>();
        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);

                switch (event.getAction()) {

                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        };
        scrollinstance = ScrollingMovementMethod.getInstance();
        cljlBeanCommonAdapter = new com.zhy.adapter.abslistview.CommonAdapter<GtxtxqBean.DealLogsBean>(this
                , R.layout.gtxtxq_cljl_item, cljls) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, GtxtxqBean.DealLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cljl_tv_dw, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_nr, item.getContent());
                final TextView textView= viewHolder.getView(R.id.gtxtxq_cljl_tv_nr);
                textView.post(new Runnable() {
                    @Override
                    public void run() {
//获取textView的行数
                        int txtPart = textView.getLineCount();
                        if (txtPart > 3) {
//大于三行做的操作
                            textView.setMovementMethod(scrollinstance);
                            textView.setOnTouchListener(touchListener);
//显示查看更多
                        } else {
 textView.setMovementMethod(null);
                            textView.setOnTouchListener(null);
                        }
                    }
                });

                viewHolder.setText(R.id.gtxtxq_cljl_tv_zfsj, item.getTime());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_zfr, item.getUserName());
            }
        };
        activityGtxtxqMyListViewCljls.setAdapter(cljlBeanCommonAdapter);
//        initshowimages();
    }

    @Override
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        //  getdata(msgID);
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }

//    private void initshowimages() {
//        View imagesview = View.inflate(this, R.layout.showimages, null);
//        imagespopupWindow = new PopupWindow(imagesview, LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        layoutManager = new LinearLayoutManager(this);
//        imagesviews = (RecyclerView) imagesview.findViewById(R.id.showimage_RecyclerView);
//        showimage_textView = (TextView) imagesview.findViewById(R.id.showimage_TextView);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            imagesviews.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                    //获取最后一个可见view的位置
//                    int lastItemPosition = layoutManager.findFirstVisibleItemPosition();
//                    showimage_textView.setText(lastItemPosition + 1 + "/" + imglist.size());
//
//                }
//            });
//        } else {
//            showimage_textView.setVisibility(View.INVISIBLE);
//        }
//
//        rootView = ((Activity) this).getWindow().getDecorView().findViewById(android.R.id.content).getRootView();
//
////        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
////        imagesviews.setLayoutManager(staggeredGridLayoutManager);
//        layoutManager = new LinearLayoutManager(this);
//
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        imagesviews.setLayoutManager(layoutManager);
//        RecyclerViewAdapter2 recyclerViewAdapter2 = new RecyclerViewAdapter2(imglist, imagespopupWindow, GtxtxqActivity.this);
//        imagesviews.setAdapter(new CommonAdapter<GtxtxqBean.FilesBean>(this,R.layout.tupian_yulan_item, imglist) {
//
//
//            @Override
//            protected void convert(ViewHolder holder, GtxtxqBean.FilesBean filesBean, int position) {
//                String key = filesBean.getUrl();
//                PhotoView photoView = holder.getView(R.id.tupian_yulan_imageview);
//                Glide.with(GtxtxqActivity.this).load(key).into(photoView);
//                // holder.imageView.setOnDoubleTapListener(null);
//                photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//                    @Override
//                    public void onViewTap(View view, float x, float y) {
//                        imagespopupWindow.dismiss();
//                    }
//                });
//            }
//        });
//        imagespopupWindow.setBackgroundDrawable(new ColorDrawable(0xee000000));
//        imagespopupWindow.setFocusable(true);
//        imagespopupWindow.setOutsideTouchable(true);
//
//    }

    private void getdata(String msgID) {
        JSONObject jsonObject = JsonUtils.getjsonobj("{\"recordID\":\"" + msgID + "\"}");
        XUtil.postjsondata(jsonObject, "GetCoordinateMsgDetails", new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                gtxtxqBean = (GtxtxqBean) JsonUtils.getbean(res, GtxtxqBean.class);

                //关于xxxx项目的不可抗力生产协调
                String subProjectName = gtxtxqBean.getTypeName();
                String projectShortName = gtxtxqBean.getProjectShortName();

                activityGtxtxqTvTitle.setText("关于" + projectShortName + "的" + subProjectName + "生产协调");
                activityGtxtxqTvPjname.setText(projectShortName);
                activityGtxtxqTvXtlb.setText(subProjectName);
                activityGtxtxqTvSgqy.setText(gtxtxqBean.getProjectAreaName());
                activityGtxtxqTvSgdw.setText(gtxtxqBean.getEtpShortName());
                activityGtxtxqTvGx.setText(gtxtxqBean.getSubProjectName());
                activityGtxtxqTvSqr.setText(gtxtxqBean.getCreateUserName());
                activityGtxtxqTvSqsj.setText(gtxtxqBean.getCreateTime());
                activityGtxtxqTvNrms.setText(gtxtxqBean.getContent());

                String floor = gtxtxqBean.getFloor();
                if (TextUtils.isEmpty(floor)) {
                    activityGtxtxqTvLcAndbg.setText("1".equals(gtxtxqBean.getProjectType())?"标高":"位置");
                    activityGtxtxqTvLc.setText(gtxtxqBean.getElevation());
                } else {
                    activityGtxtxqTvLcAndbg.setText("1".equals(gtxtxqBean.getProjectType())?"楼层":"里程");
                    activityGtxtxqTvLc.setText(floor);
                }
                activityGtxtxqTvCkrz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GtxtxqActivity.this, SgrzActivity.class);

                        intent.putExtra("year", gtxtxqBean.getCreateYear() + "-" + gtxtxqBean.getCreateTime() + ":00");
                        startActivity(intent);


                    }
                });
                cyjls.clear();
                //查阅记录
                List<GtxtxqBean.ReadLogsBean> cyjlBeanList = gtxtxqBean.getReadLogs();
                if (cyjlBeanList != null && cyjlBeanList.size() > 0) {
                    cyjls.addAll(cyjlBeanList);
                    cyjlBeanCommonAdapter.notifyDataSetChanged();
                }
                cljls.clear();
                //处理记录
                List<GtxtxqBean.DealLogsBean> cljlBeanList = gtxtxqBean.getDealLogs();
                if (cljlBeanList != null && cljlBeanList.size() > 0) {
                    cljls.addAll(cljlBeanList);
                    cljlBeanCommonAdapter.notifyDataSetChanged();
                }
                imglist.clear();
                imgslist.clear();
                imgsms.clear();
                //图片
                List<GtxtxqBean.FilesBean> filesBeanList = gtxtxqBean.getFiles();

                if (filesBeanList != null && filesBeanList.size() > 0) {
                    for (int i = 0; i < filesBeanList.size(); i++) {

                        GtxtxqBean.FilesBean e = filesBeanList.get(i);
                        imglist.add(e);
                        imgslist.add(e.getUrl());
                        imgsms.add(e.getFileBrief());
                    }
                    //  imglist.addAll(filesBeanList);
                    commonAdapter.notifyDataSetChanged();
                    activityGtxtxqRecyclerViewImgs.setVisibility(View.VISIBLE);
                } else {
                    activityGtxtxqRecyclerViewImgs.setVisibility(View.GONE);
                }
                //btn显示
                List<ButtonBean> buttons = gtxtxqBean.getButtons();
                activityGtxtxqTextViewCb.setVisibility(View.GONE);
                activityGtxtxqTextViewBh.setVisibility(View.GONE);
                activityGtxtxqTextViewYcl.setVisibility(View.GONE);
                activityGtxtxqTextViewZf.setVisibility(View.GONE);
                activityGtxtxqTextViewZf2.setVisibility(View.GONE);
                activityGtxtxqTextViewHf.setVisibility(View.GONE);
                activityGtxtxqTvCkrz.setVisibility(View.GONE);

                if (buttons != null && buttons.size() > 0) {
                    activityGtxtxqLinearLayoutCljg.setVisibility(View.VISIBLE);
                } else {
                    activityGtxtxqLinearLayoutCljg.setVisibility(View.GONE);
                }

                if (buttons != null) {

                    for (int i = 0; i < buttons.size(); i++) {
                        ButtonBean buttonBean = buttons.get(i);
                        String btnID = buttonBean.getBtnID();
                        String btnName = buttonBean.getBtnName();
                        if (btnID.equals("cmBtn1")) {
                            activityGtxtxqTextViewCb.setText(btnName);
                            activityGtxtxqTextViewCb.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn2")) {
                            activityGtxtxqTextViewBh.setText(btnName);

                            activityGtxtxqTextViewBh.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn3") || btnID.equals("cmBtn6")) {
                            isycl = btnID;
                            activityGtxtxqTextViewYcl.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn4")) {
                            activityGtxtxqTextViewZf.setText(btnName);

                            activityGtxtxqTextViewZf.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn5")) {
                            activityGtxtxqTextViewHf.setText(btnName);
                            activityGtxtxqTextViewHf.setVisibility(View.VISIBLE);

                        }
                        if (btnID.equals("cmBtn7")) {
                            activityGtxtxqTextViewZf2.setText(btnName);
                            activityGtxtxqTextViewZf2.setVisibility(View.VISIBLE);

                        }
                        if (btnID.equals("cmBtnDaily")) {
                            activityGtxtxqTvCkrz.setText(btnName);
                            activityGtxtxqTvCkrz.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {

            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {

            }
        });

    }

    public void postdata(final String btnname) {
        String trim = activityGtxtxqEditTextCljg.getText().toString().trim();
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"createUserName\":\"" + PublicUtils.userBean.getUserName() + "\",\"dealContent\":\"" + trim + "\",\"btnID\":\"" + btnname + "\",\"createUserID\":\"" + PublicUtils.userBean.getUserID() + "\",\"recordID\":\"" + msgID + "\"}");
        XUtil.postjsondata(getjsonobj, "GetCoordinateMsgDetailsButton", new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                //PublicUtils.toast(btntotext(btnname) + "成功！");
                activityGtxtxqEditTextCljg.setText("");
                getdata(msgID);
            }

            /**
             * code 不等于 0回调
             *
             * @param getbean
             */
            @Override
            public void onFail(ObjBean getbean) {
                //  PublicUtils.toast(btnname + "失败！");
            }

            /**
             * 联网失败回调
             */
            @Override
            public void onWrong() {
                //  PublicUtils.toast(btnname + "失败！");
            }

            /**
             * 总是回调用的方法
             */
            @Override
            public void onFinished() {
                activityGtxtxqTextViewCb.setEnabled(true);
                activityGtxtxqTextViewBh.setEnabled(true);
                activityGtxtxqTextViewYcl.setEnabled(true);
                activityGtxtxqTextViewHf.setEnabled(true);
            }
        });
    }


    @OnClick({R.id.activity_gtxtxq_TextView_bh,
            R.id.activity_gtxtxq_TextView_ycl,
            R.id.activity_gtxtxq_TextView_zf,
            R.id.activity_gtxtxq_TextView_hf,
            R.id.activity_gtxtxq_TextView_cb,
            R.id.activity_gtxtxq_TextView_zf2
            , R.id.activity_gtxtxq_LinearLayout_cyjls})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_gtxtxq_TextView_bh:
                if (panduan()) break;
                activityGtxtxqTextViewBh.setEnabled(false);
                postdata("cmBtn2");
                break;
            case R.id.activity_gtxtxq_TextView_ycl:
                if (isycl.equals("cmBtn6")) {
                    if (panduan()) break;
                    postdata("cmBtn6");
                    break;
                }
                // activityGtxtxqTextViewYcl.setEnabled(false);
                intent.setClass(GtxtxqActivity.this, YclActivity.class);
                intent.putExtra("projectID", gtxtxqBean.getProjectID());
                intent.putExtra("msgid", msgID);
                startActivity(intent);

                break;
            case R.id.activity_gtxtxq_TextView_zf:
                intent.setClass(GtxtxqActivity.this, GtxtzfActivity.class);
                if (gtxtxqBean!=null)
                intent.putExtra("projectID", gtxtxqBean.getProjectID());
                intent.putExtra("msgid", msgID);
                startActivity(intent);
//                postdata("闭合");
                break;
            case R.id.activity_gtxtxq_TextView_zf2:
                intent.setClass(GtxtxqActivity.this, ZF2Activity.class);
                intent.putExtra("msgid", msgID);
                if (gtxtxqBean!=null)
                intent.putExtra("projectID", gtxtxqBean.getProjectID());
                startActivity(intent);
//                postdata("闭合");
                break;
            case R.id.activity_gtxtxq_TextView_hf:
                if (panduan()) break;
                activityGtxtxqTextViewHf.setEnabled(false);
                postdata("cmBtn5");
                break;
            case R.id.activity_gtxtxq_TextView_cb:
                if (panduan()) break;
                activityGtxtxqTextViewCb.setEnabled(false);
                postdata("cmBtn1");
                break;
            case R.id.activity_gtxtxq_LinearLayout_cyjls:
                if (activityGtxtxqMyListViewCyjls.getVisibility() == View.VISIBLE) {
                    activityGtxtxqMyListViewCyjls.setVisibility(View.GONE);
                    activityGtxtxqImageViewCyjls.setImageResource(R.mipmap.arrowdown);
                } else {
                    activityGtxtxqMyListViewCyjls.setVisibility(View.VISIBLE);
                    activityGtxtxqImageViewCyjls.setImageResource(R.mipmap.arrowup);
                }
                break;
        }
    }

    private boolean panduan() {
        String trim = activityGtxtxqEditTextCljg.getText().toString().trim();
        if (trim.length() == 0) {
            PublicUtils.toast("请输入处理结果！");
            return true;
        }
        return false;
    }


    @Override
    protected void onResume() {
        getdata(msgID);
        super.onResume();

    }



}
