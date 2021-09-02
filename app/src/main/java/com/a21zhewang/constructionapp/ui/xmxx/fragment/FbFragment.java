package com.a21zhewang.constructionapp.ui.xmxx.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.a21zhewang.constructionapp.Inf.ISetData;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseFragment;
import com.a21zhewang.constructionapp.bean.FbBean;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.ui.xmxx.FbAdapter;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;

import java.util.List;

import butterknife.BindView;


public class FbFragment extends BaseFragment implements ISetData {


    @BindView(R.id.fbFragment_ExpandableListView)
    ExpandableListView expandableListView;

    public FbFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fbFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FbFragment newInstance() {
        FbFragment fragment = new FbFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_fb;
    }

    @Override
    public void initViews() {
//        XUtil.postjsondata(JsonUtils.getjsonobj("{\"projectID\":\"GC10001\"}"), "GetProjectDetails", new MyCallBack() {
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
    public void setData(FbBean fbBean) {
        List<FbBean.EtpInfoListBean> etpInfoList = fbBean.getFenbaooEtpInfoList();
        if (etpInfoList != null && etpInfoList.size() > 0) {
            expandableListView.setAdapter(new FbAdapter(getActivity(), etpInfoList) {
                @Override
                public void initChildviewholder(FbBean.EtpInfoListBean.UserInfoListBean userInfoListBean, Childviewholder childviewholder) {
                    String deptName = userInfoListBean.getDeptName();
                    if (deptName != null)
                        childviewholder.tv1.setText(deptName);

                    String userName = userInfoListBean.getUserName();
                    if (userName != null)
                        childviewholder.tv2.setText(userName);

                    String userId = userInfoListBean.getUserPhone();
                    if (userId != null)
                        childviewholder.tv3.setText(userId);
                }
            });
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    TextView textView = (TextView) v.findViewById(R.id.child_item_tv3);
                    call(textView.getText().toString());
                    return false;
                }
            });
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


    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

}

