package com.a21zhewang.constructionapp.ui.gxys.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseFragment;
import com.a21zhewang.constructionapp.bean.ButtonBean;
import com.a21zhewang.constructionapp.bean.DealLogsBean;
import com.a21zhewang.constructionapp.bean.FilesBean;
import com.a21zhewang.constructionapp.bean.GxysXqBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.bean.ReadLogsBean;
import com.a21zhewang.constructionapp.customview.MyListView;
import com.a21zhewang.constructionapp.customview.TimeBtn;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.ui.gxys.AddZgjlActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.TimeUtils;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.a21zhewang.photolibrary.photobrowse.PhotoViewActivity;
import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GxysxqgxxxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GxysxqgxxxFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "recordID";
    @BindView(R.id.fragment_gxxx_tv_title)
    TextView fragmentGxxxTvTitle;
    @BindView(R.id.fragment_gxxx_tv_pjname)
    TextView fragmentGxxxTvPjname;

    @BindView(R.id.fragment_gxxx_tv_sgqy)
    TextView fragmentGxxxTvSgqy;
    @BindView(R.id.fragment_gxxx_tv_gx)
    TextView fragmentGxxxTvGx;
    @BindView(R.id.fragment_gxxx_tv_lc)
    TextView fragmentGxxxTvLc;
    @BindView(R.id.fragment_gxxx_tv_sgdw)
    TextView fragmentGxxxTvSgdw;
    @BindView(R.id.fragment_gxxx_tv_sqsj)
    TextView fragmentGxxxTvSqsj;
    @BindView(R.id.fragment_gxxx_tv_sqr)
    TextView fragmentGxxxTvSqr;
    @BindView(R.id.fragment_gxxx_tv_ckrz)
    TextView fragmentGxxxTvCkrz;
    @BindView(R.id.fragment_gxxx_tv_nrms)
    TextView fragmentGxxxTvNrms;
    @BindView(R.id.fragment_gxxx_tv_yssj)
    TextView fragmentGxxxTvYssj;
    @BindView(R.id.fragment_gxxx_RecyclerView_imgs)
    RecyclerView fragmentGxxxRecyclerViewImgs;
    @BindView(R.id.fragment_gxxx_ImageView_cyjls)
    ImageView fragmentGxxxImageViewCyjls;
    @BindView(R.id.fragment_gxxx_LinearLayout_cyjls)
    LinearLayout fragmentGxxxLinearLayoutCyjls;
    @BindView(R.id.fragment_gxxx_MyListView_cyjls)
    MyListView fragmentGxxxMyListViewCyjls;
    @BindView(R.id.fragment_gxxx_MyListView_cljls)
    MyListView fragmentGxxxMyListViewCljls;
    @BindView(R.id.fragment_gxxx_EditText_cljg)
    EditText fragmentGxxxEditTextCljg;
    @BindView(R.id.fragment_gxxx_LinearLayout_cljg)
    LinearLayout fragmentGxxxLinearLayoutCljg;
    @BindView(R.id.fragment_gxxx_tvbg)
    TextView fragment_gxxx_tvbg;

    @BindView(R.id.fragment_gxxx_tvlc)
    TextView fragment_gxxx_tvlc;
    /**
     * fileBrief : 这是测试的图片描述
     * url : http://localhost:12224/upload/images/xx.jpg
     */
    public List<FilesBean> imgfiles;
    /**
     * userName : 张三
     * etpShortName : 总承包
     * status : 已读
     * time : 05-16 10:10
     */

    public List<ReadLogsBean> readLogs;
    /**
     * userName : 发起人：李四
     * etpShortName : 总承包
     * content : 李四发起协调请求
     * time : 05-10 17:13
     */

    public List<DealLogsBean> dealLogs;
    /**
     * btnID : cmBtn5
     * btnName : 回复
     */

    public List<ButtonBean> buttons;
    public CommonAdapter<FilesBean> imgsAdapter;
    public com.zhy.adapter.abslistview.CommonAdapter<ReadLogsBean> readLogsAdapter;
    public com.zhy.adapter.abslistview.CommonAdapter<DealLogsBean> dealLogsAdapter;
    @BindView(R.id.fragment_gxxx_TextView_tg)
    TextView fragmentGxxxTextViewTg;
    @BindView(R.id.fragment_gxxx_TextView_btg)
    TextView fragmentGxxxTextViewBtg;
    @BindView(R.id.fragment_gxxx_TextView_sj)
    TimeBtn fragmentGxxxTextViewSj;
    @BindView(R.id.fragment_gxxx_TextView_fy)
    TextView fragmentGxxxTextViewFy;
    @BindView(R.id.fragment_gxxx_TextView_yzg)
    TextView fragmentGxxxTextViewYzg;
    @BindView(R.id.fragment_gxxx_TextView_tj)
    TextView fragmentGxxxTextViewTj;
    @BindView(R.id.fragment_gxxx_tv_bg)
    TextView fragmentGxxxTvBg;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String recordID;
    private Context activity;
    private String timestr = "";
    private long reformmillis=0;

    public GxysxqgxxxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GxysxqgxxxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GxysxqgxxxFragment newInstance(String recordID) {
        GxysxqgxxxFragment fragment = new GxysxqgxxxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, recordID);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_gxysxqgxxx;
    }

    @Override
    public void initViews() {
        recordID = getArguments().getString(ARG_PARAM1);
    }

    @Override
    public void initListener() {
        fragmentGxxxTextViewSj.setTvClearVisiable(false);
        fragmentGxxxTextViewSj.setTimeBtnOkAndClear(new TimeBtn.TimeBtnOkAndClear() {
            @Override
            public void ok(String time) {

                if (TimeUtils.string2Millis(time)>=reformmillis&&reformmillis!=0) {
                   // long nowMills = TimeUtils.getNowMills();
                    timestr = time;
                    postdata("cmBtn6");

                }else{
                    PublicUtils.toastFalse("验收时间不能早于申请时间");
                }


            }

            @Override
            public void clear(String time) {
                timestr = "";

            }
        });
    }

    @Override
    public void initData() {
        imgfiles = new ArrayList<>();
        readLogs = new ArrayList<>();
        dealLogs = new ArrayList<>();
        buttons = new ArrayList<>();

        //图片列表
        activity = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        fragmentGxxxRecyclerViewImgs.setLayoutManager(linearLayoutManager);
        imgsAdapter = new CommonAdapter<FilesBean>(activity, R.layout.faqigtxt_imglist_item, imgfiles) {

            @Override
            protected void convert(ViewHolder holder, FilesBean s, final int position) {
                holder.setText(R.id.faqigtxt_imglist_item_ms, s.getFileBrief());
                ImageView imageView = holder.getView(R.id.faqigtxt_imglist_item_img);
                Glide.with(activity).load(s.getUrl()).into(imageView);
                holder.setOnClickListener(R.id.faqigtxt_imglist_item_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhotoViewActivity.PagerAdapterAPI<FilesBean> pagerAdapterAPI = new PhotoViewActivity.PagerAdapterAPI<FilesBean>() {

                            @Override
                            public String getstrs(int index, FilesBean obj) {

                                return obj.getUrl();
                            }

                            @Override
                            public String getms(int index, FilesBean obj) {

                                return obj.getFileBrief();
                            }
                        };
                        PhotoViewActivity.statPhotoViewActivity(activity, position, imgfiles, pagerAdapterAPI);
                    }
                });
            }
        };
        fragmentGxxxRecyclerViewImgs.setAdapter(imgsAdapter);
        //查阅纪录初始化
        View cyjlheadview = LayoutInflater.from(activity).inflate(R.layout.gtxyxq_cyjl_list_headview, null, false);
        fragmentGxxxMyListViewCyjls.addHeaderView(cyjlheadview);
        readLogsAdapter = new com.zhy.adapter.abslistview.CommonAdapter<ReadLogsBean>(activity
                , R.layout.gtxtxq_cyjl_list_item, readLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, final ReadLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_name, item.getUserName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_position, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_status, item.getStatus());
                viewHolder.setText(R.id.gtxtxq_cyjl_list_item_tv_time, item.getTime());
                viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PublicUtils.call(item.getUserPhone(),getActivity());
                    }
                });

            }
        };
        fragmentGxxxMyListViewCyjls.setAdapter(readLogsAdapter);
        //处理记录初始化

        dealLogsAdapter = new com.zhy.adapter.abslistview.CommonAdapter<DealLogsBean>(activity
                , R.layout.gtxtxq_cljl_item, dealLogs) {
            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, DealLogsBean item, int position) {
                viewHolder.setText(R.id.gtxtxq_cljl_tv_dw, item.getEtpShortName());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_nr, item.getContent());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_zfsj, item.getTime());
                viewHolder.setText(R.id.gtxtxq_cljl_tv_zfr, item.getUserName());
            }
        };
        fragmentGxxxMyListViewCljls.setAdapter(dealLogsAdapter);
        getdata();
    }

    @Override
    public void processClick(View v) {

    }

    private void getdata() {

        XUtil.postjsondata(JsonUtils.getjsonobj("{\"recordID\":\"" + recordID + "\"}"), "GetProcessAcceptanceDetails", new MyCallBack() {
            @Override
            public void onResule(String res) {
                final GxysXqBean aqscxqBean = JsonUtils.getbean(res, GxysXqBean.class);
                if ("1".equals(aqscxqBean.getProjectType())){
                    fragmentGxxxTvLc.setText("楼层");
                    fragmentGxxxTvBg.setText("标高");
                }else{
                    fragmentGxxxTvLc.setText("里程");
                    fragmentGxxxTvBg.setText("位置");
                }
                fragmentGxxxTvTitle.setText(aqscxqBean.getTitle());
                fragmentGxxxTvPjname.setText(aqscxqBean.getProjectShortName());//项目名称
                String floor = aqscxqBean.getFloor();

                String elevation = aqscxqBean.getElevation();
                if (elevation !=null)
                fragmentGxxxTvBg.setText(elevation);

                   if (floor!=null)
                fragmentGxxxTvLc.setText(floor);//楼层

                fragmentGxxxTvGx.setText(aqscxqBean.getSubProjectName());
                fragmentGxxxTvSgqy.setText(aqscxqBean.getProjectAreaName());
                fragmentGxxxTvSgdw.setText(aqscxqBean.getEtpShortName());
                fragmentGxxxTvSqr.setText(aqscxqBean.getCreateUserName());
                String applyTime = aqscxqBean.getApplyTime();
                fragmentGxxxTvSqsj.setText(applyTime);
                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date date = null;
                try {
                    date = sdf.parse(applyTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    fragmentGxxxTextViewSj.getPvCustomTime().setDate(calendar);
                } catch (ParseException e) {
                   // e.printStackTrace();
                }

                reformmillis = TimeUtils.string2Millis(applyTime, sdf);



                //fragmentGxxxTvZgjz.setText(aqscxqBean.getCutoffTime());
                fragmentGxxxTvNrms.setText(aqscxqBean.getContent());

              //  DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String reformtime = aqscxqBean.getReformtime();

                fragmentGxxxTvYssj.setText(reformtime);
//                String typeGraFaName = aqscxqBean.getTypeGraFaName();
//                String fxlb_str = TextUtils.isEmpty(typeGraFaName) ? "" : typeGraFaName + "->\n";
//                fragmentGxxxTvfxlb.setText(fxlb_str
//                        + aqscxqBean.getTypeFatherName() + "->\n"
//                        + aqscxqBean.getTypeName());
//                fragmentGxxxTvZgdw.setText(aqscxqBean.getRecEtpShortName());
                //查阅记录
                readLogs.clear();
                List<ReadLogsBean> cyjlBeanList = aqscxqBean.getReadLogs();
                if (cyjlBeanList != null && cyjlBeanList.size() > 0) {
                    readLogs.addAll(cyjlBeanList);
                }
                readLogsAdapter.notifyDataSetChanged();


                //处理记录
                dealLogs.clear();
                List<DealLogsBean> cljlBeanList = aqscxqBean.getDealLogs();
                if (cljlBeanList != null && cljlBeanList.size() > 0) {
                    dealLogs.addAll(cljlBeanList);
                }
                dealLogsAdapter.notifyDataSetChanged();

                //图片
                imgfiles.clear();
                List<FilesBean> filesBeanList = aqscxqBean.getFiles();

                if (filesBeanList != null && filesBeanList.size() > 0) {

                    imgfiles.addAll(filesBeanList);
                    fragmentGxxxRecyclerViewImgs.setVisibility(View.VISIBLE);
                } else {
                    fragmentGxxxRecyclerViewImgs.setVisibility(View.GONE);
                }
                imgsAdapter.notifyDataSetChanged();
                //btn显示
                List<ButtonBean> buttons = aqscxqBean.getButtons();

                //activityGtxtxqTextViewHf.setVisibility(View.GONE);
                // activityGtxtxqTvCkrz.setVisibility(View.GONE);
                if (buttons != null && buttons.size() > 0) {
                    fragmentGxxxLinearLayoutCljg.setVisibility(View.VISIBLE);
                    fragmentGxxxTextViewTg.setVisibility(View.GONE);
                    fragmentGxxxTextViewTj.setVisibility(View.GONE);
                    fragmentGxxxTextViewBtg.setVisibility(View.GONE);
                    fragmentGxxxTextViewYzg.setVisibility(View.GONE);
                    fragmentGxxxTextViewSj.setVisibility(View.GONE);
                    fragmentGxxxTextViewFy.setVisibility(View.GONE);
                    for (int i = 0; i < buttons.size(); i++) {
                        ButtonBean buttonsBean = buttons.get(i);
                        String btnID = buttonsBean.getBtnID();
                        String btnName = buttonsBean.getBtnName();
                        if (btnID.equals("cmBtn1")) {
                            //   activityGtxtxqTextViewCb.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn2")) {
                            fragmentGxxxTextViewYzg.setText(btnName);
                            fragmentGxxxTextViewYzg.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn4")) {
                            fragmentGxxxTextViewTg.setText(btnName);
                            fragmentGxxxTextViewTg.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn3")) {
                            fragmentGxxxTextViewBtg.setText(btnName);
                            fragmentGxxxTextViewBtg.setVisibility(View.VISIBLE);
                        }
                        if (btnID.equals("cmBtn5")) {
                            fragmentGxxxTextViewFy.setText(btnName);
                            fragmentGxxxTextViewFy.setVisibility(View.VISIBLE);

                        }
                        if (btnID.equals("cmBtn6")) {
                            fragmentGxxxTextViewSj.setText(btnName);
                            fragmentGxxxTextViewSj.setVisibility(View.VISIBLE);

                        }
                        if (btnID.equals("cmBtn7")) {
                            fragmentGxxxTextViewTj.setText(btnName);
                            fragmentGxxxTextViewTj.setVisibility(View.VISIBLE);

                        }

                        if (btnID.equals("cmBtnDaily")) {
                            //    activityGtxtxqTvCkrz.setVisibility(View.VISIBLE);

                        }
                    }
                } else {
                    fragmentGxxxLinearLayoutCljg.setVisibility(View.GONE);
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

    private void setenable() {
        fragmentGxxxTextViewTg.setEnabled(true);
        fragmentGxxxTextViewBtg.setEnabled(true);
        fragmentGxxxTextViewFy.setEnabled(true);
        fragmentGxxxTextViewYzg.setEnabled(true);
        fragmentGxxxTextViewTj.setEnabled(true);

    }


    @OnClick({R.id.fragment_gxxx_TextView_tg,
            R.id.fragment_gxxx_TextView_btg,
            // R.id.fragment_gxxx_TextView_sj,
            R.id.fragment_gxxx_TextView_fy,
            R.id.fragment_gxxx_TextView_yzg,
            R.id.fragment_gxxx_TextView_tj,
            R.id.fragment_gxxx_LinearLayout_cyjls
    })
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.fragment_gxxx_TextView_tg:
                //  if (panduan()) break;
                view.setEnabled(false);
                postdata("cmBtn4");
                break;
            case R.id.fragment_gxxx_TextView_btg:
                // if (panduan()) break;
                view.setEnabled(false);
                postdata("cmBtn3");
                break;
//            case R.id.fragment_gxxx_TextView_sj:
//                if (panduan()) break;
//                view.setEnabled(false);
//                postdata("cmBtn6");
//                break;
            case R.id.fragment_gxxx_TextView_fy:
                if (panduan()) break;
                view.setEnabled(false);
                postdata("cmBtn5");
                break;
            case R.id.fragment_gxxx_TextView_yzg:

                view.setEnabled(false);
                postdata("cmBtn2");
                break;
            case R.id.fragment_gxxx_TextView_tj:
                Intent tjintent = new Intent(activity, AddZgjlActivity.class);
                tjintent.putExtra("recordID", recordID);
               // tjintent.putExtra("postdatamethod", "GetProcessAcceptanceDetailsButton");
                startActivity(tjintent);

                break;
            case R.id.fragment_gxxx_LinearLayout_cyjls:
                if (fragmentGxxxMyListViewCyjls.getVisibility() == View.VISIBLE) {
                    fragmentGxxxMyListViewCyjls.setVisibility(View.GONE);
                    fragmentGxxxImageViewCyjls.setImageResource(R.mipmap.arrowdown);
                } else {
                    fragmentGxxxMyListViewCyjls.setVisibility(View.VISIBLE);
                    fragmentGxxxImageViewCyjls.setImageResource(R.mipmap.arrowup);
                }
                break;
        }
    }

    private boolean panduan() {
        String trim = fragmentGxxxEditTextCljg.getText().toString().trim();
        if (trim.length() == 0) {
            PublicUtils.toast("请输入处理结果！");
            return true;
        }
        return false;
    }

    public void postdata(final String btnname) {
        String trim = fragmentGxxxEditTextCljg.getText().toString().trim();
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"createUserName\":\"" + PublicUtils.userBean.getUserName() + "\",\"dealContent\":\"" + trim + "\",\"btnID\":\"" + btnname + "\",\"createUserID\":\"" + PublicUtils.userBean.getUserID() + "\",\"recordID\":\"" + recordID + "\",\"reformTime\":\"" + timestr + "\"}");
        XUtil.postjsondata(getjsonobj, "GetProcessAcceptanceDetailsButton", new MyCallBack() {
            /**
             * code 等于 0回调
             *
             * @param res
             */
            @Override
            public void onResule(String res) {
                //PublicUtils.toast(btntotext(btnname) + "成功！");
                fragmentGxxxEditTextCljg.setText("");
                getdata();
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
                setenable();
            }
        });
    }


}
