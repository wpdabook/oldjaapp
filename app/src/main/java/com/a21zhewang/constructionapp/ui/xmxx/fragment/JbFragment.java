package com.a21zhewang.constructionapp.ui.xmxx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.Inf.ISetData;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseFragment;
import com.a21zhewang.constructionapp.bean.FbBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.ui.SelectSgqyActivity;
import com.a21zhewang.constructionapp.ui.set.MapActivity;
import com.a21zhewang.constructionapp.ui.set.ProjectRegisterActivity;
import com.a21zhewang.constructionapp.ui.xmxx.XmxxActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import org.xutils.common.Callback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class JbFragment extends BaseFragment implements ISetData{


    @BindView(R.id.fragment_jb_xmmc)
    TextView fragmentJbXmmc;
    @BindView(R.id.fragment_jb_xmdz)
    TextView fragmentJbXmdz;
    @BindView(R.id.fragment_jb_fzr)
    TextView fragmentJbFzr;
    @BindView(R.id.fragment_jb_dh)
    TextView fragmentJbDh;
    @BindView(R.id.fragment_jb_xmzt)
    TextView fragmentJbXmzt;
    @BindView(R.id.fragment_jb_kgrr)
    TextView fragmentJbKgrr;
    @BindView(R.id.fragment_jb_jgrr)
    TextView fragmentJbJgrr;
    @BindView(R.id.fragment_jb_xmqk)
    TextView fragmentJbXmqk;
    @BindView(R.id.maps)
    ImageView maps;
    private Unbinder bind;
    private Callback.Cancelable ancelable;
    private String ProjectID = "";
    private String Address = "";
    private String Province = "";
    private String City = "";
    private String Area = "";
    private Double latitude;
    private Double longitude;

    public JbFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static JbFragment newInstance() {
        JbFragment fragment = new JbFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setData(FbBean fbBean) {
        String shortName = fbBean.getShortName();
        fragmentJbXmmc.setText(TextUtils.isEmpty(shortName) ? "" : shortName);

        String address = fbBean.getAddress();
        if (address != null)
            fragmentJbXmdz.setText(address);

        String manager = fbBean.getManager();
        if (manager != null)
            fragmentJbFzr.setText(manager);

        String managerPhone = fbBean.getManagerPhone();
        if (managerPhone != null)
            fragmentJbDh.setText(managerPhone);

        String status = fbBean.getStatus();
        if (status != null)
            fragmentJbXmzt.setText(status);

        String startDate = fbBean.getStartDate();
        if (startDate != null)
            fragmentJbKgrr.setText(startDate);

        String endDate = fbBean.getEndDate();
        if (endDate != null)
            fragmentJbJgrr.setText(endDate);

        String desc = fbBean.getDesc();
        if (desc != null)
            fragmentJbXmqk.setText(desc);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jb;
    }

    @Override
    public void initViews() {
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(),MapActivity.class).putExtra("isUpDateAdress",true)
                        .putExtra("projectID",((XmxxActivity)getActivity()).getProjectID()),MapActivity.MAP_RESQUST_CODE);
            }
        });
//        if (ancelable!=null){
//            ancelable.cancel();
//        }
//        ancelable = XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectID\":\"GC10001\"}"), "GetProjectDetails", new MyCallBack() {
//            @Override
//            public void onResule(String res) {
//                if ("\"无数据\"".equals(res)) return;
//                FbBean fbBean = JsonUtils.getbean(res, FbBean.class);
//                setData(fbBean);
//            }
//
//            @Override
//            public void onFail(ObjBean getbean) {
//
//            }
//
//            @Override
//            public void onWrong() {
//
//            }
//        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MapActivity.MAP_RESQUST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            Address = data.getStringExtra("address");
            Province = data.getStringExtra("province");
            City = data.getStringExtra("city");
            Area = data.getStringExtra("area");
            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);
            fragmentJbXmdz.setText(Address);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {

    }






}
