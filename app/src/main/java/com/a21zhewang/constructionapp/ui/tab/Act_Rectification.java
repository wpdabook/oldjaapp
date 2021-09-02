package com.a21zhewang.constructionapp.ui.tab;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.adapter.NamesSpinnerAdpater;
import com.a21zhewang.constructionapp.base.BaseListViewActivity3;
import com.a21zhewang.constructionapp.bean.AqscSpinnerBean;
import com.a21zhewang.constructionapp.bean.AqsclistBean;
import com.a21zhewang.constructionapp.bean.MsgStatusBean;
import com.a21zhewang.constructionapp.bean.MsgType;
import com.a21zhewang.constructionapp.bean.ObjBean;
import com.a21zhewang.constructionapp.customview.MySpinner;
import com.a21zhewang.constructionapp.customview.SearchView;
import com.a21zhewang.constructionapp.ui.aqsc.AqscAddActivity;
import com.a21zhewang.constructionapp.ui.aqsc.AqscxqActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgActivity;
import com.a21zhewang.constructionapp.ui.wmsg.WmsgxqActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdActivity;
import com.a21zhewang.constructionapp.ui.zljd.ZljdxqActivity;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.MyCallBack;
import com.a21zhewang.constructionapp.utils.XUtil;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;


public class Act_Rectification extends BaseListViewActivity3<MsgType, MsgStatusBean,AqsclistBean> {
    private String typeID="";//类型ID（如果不传则是空字符串）
    private String typeFatherID="";//父类类型ID（如果不传则是空字符串）
    private String typeGraFaID="";//祖类类型ID（如果不传则是空字符串）
    private String msgStatus="";//处理情况（如果不传则是空字符串）
    private String etpID="";//整改企业ID
    private String keyword="";
    private int pageIndex=0;//页数，从0开始
    private int onClickIndex=-1;
    private String projectId;
    private int rectType;
    private String statusName;
    private int level;
    private String typeGraFaName;
    private String projectAreaName;
    private int dateType;


    /**
     * 刷新
     */
    @Override
    public void OnRefreshing() {
        pageIndex=0;keyword="";
        commonAdapter.getDatas().clear();
        commonAdapter.notifyDataSetChanged();
        getlistdata();
    }

    /**
     * 加载
     */
    @Override
    public void OnLoading() {
        pageIndex++;
        getlistdata();

    }

    @Override
    public void thisconvert(ViewHolder holder, final AqsclistBean aqsclistBean, final int position) {
        if (aqsclistBean.isRead()){
            holder.setVisible(R.id.gtxt_list_item_tv_isread,false);
        }else{
            holder.setVisible(R.id.gtxt_list_item_tv_isread,true);
        }

        holder.setText(R.id.gtxt_list_item_tv_createTime, "时间："+aqsclistBean.getCreateTime());
        holder.setText(R.id.gtxt_list_item_tv_etpShortName, "单位：" + aqsclistBean.getRecEtpShortName());
        holder.setText(R.id.gtxt_list_item_tv_createUserName, "发起人："+aqsclistBean.getCreateUserName());
        holder.setText(R.id.gtxt_list_item_tv_msgTitle, "内容："+aqsclistBean.getTitle());
        holder.setText(R.id.gtxt_list_item_tv_msgTypeID, "区域：" + aqsclistBean.getProjectAreaName());
        String msgStatus = aqsclistBean.getStatus();
        holder.setText(R.id.gtxt_list_item_tv_msgStatus, msgStatus);
        String color_str="#ffffff";
        for (int i=0;i<spinner2Beenlist.size();i++){
            MsgStatusBean msgStatusBean = spinner2Beenlist.get(i);
            String status = msgStatusBean.getStatus();
            if (status.equals(msgStatus)){
                color_str ="#"+ msgStatusBean.getColor();
            }
        }
        GradientDrawable gradientDrawable   = (GradientDrawable) holder.getView(R.id.gtxt_list_item_img_msgStatus).getBackground();
        gradientDrawable.setColor(Color.parseColor(color_str));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIndex=position;
                if(rectType == 1){
                    Intent intent = new Intent(Act_Rectification.this, AqscxqActivity.class);
                    intent.putExtra("recordID",aqsclistBean.getRecordID());
                    startActivity(intent);
                }
                if(rectType == 2){
                    Intent intent = new Intent(Act_Rectification.this, ZljdxqActivity.class);
                    intent.putExtra("recordID",aqsclistBean.getRecordID());
                    startActivity(intent);
                }
                if(rectType == 3){
                    Intent intent = new Intent(Act_Rectification.this, WmsgxqActivity.class);
                    intent.putExtra("recordID",aqsclistBean.getRecordID());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * @return 设置标题文字
     */
    @Override
    public String settiltile() {
        rectType = getIntent().getIntExtra("rectType",0);
        if(rectType == 1){
            return "安全检查";
        }
        if(rectType == 2){
            return "质量隐患";
        }
        if(rectType == 3){
            return "文明施工";
        }
        return "";
    }

    @Override
    public void setaddbtnonclick() {

    }

    @Override
    public String setaddbtnvisiable() {
        return null;
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        SearchView_top.setiSearchView(new SearchView.ISearchView() {
            @Override
            public void onSearchListener(String edittext) {
                if (listBeenlist==null)return;
                listBeenlist.clear();
                commonAdapter.notifyDataSetChanged();
                pageIndex=0;
                keyword=edittext;
                getlistdata(0,10);

            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        dateType = getIntent().getIntExtra("dateType",0);
        projectId = getIntent().getStringExtra("projectId");
        rectType = getIntent().getIntExtra("rectType",0);
        statusName = getIntent().getStringExtra("statusName");
        typeGraFaName = getIntent().getStringExtra("typeGraFaName");
        projectAreaName = getIntent().getStringExtra("projectAreaName");
        level = getIntent().getIntExtra("level",0);
        if (listBeenlist==null)return;
        listBeenlist.clear();
        commonAdapter.notifyDataSetChanged();
        pageIndex=0;
        getlistdata(pageIndex,10);
    }

    /**
     * 获取获取数据成功
     */
    @Override
    public void getdataok() {
        getlistdata();
    }

    /**
     * 页面显示
     */
    @Override
    public void resume() {
        if (onClickIndex!=-1){
            if (listBeenlist==null)return;
            listBeenlist.clear();
            commonAdapter.notifyDataSetChanged();
            getlistdata(0,(pageIndex+1)*10);
        }
    }


    /**
     * 获取页面列表数据
     */
    private void getlistdata() {
        getlistdata(pageIndex,10);
    }

    /**
     * 获取页面列表数据
     * dateType: 1近一周  2近一月
     */
    private void getlistdata(int pageIndex,int pageSize) {
        JSONObject getjsonobj = JsonUtils.getjsonobj("{\"dateType\":\""+ dateType+"\",\"projectId\":\""+projectId+"\"," +
                "\"rectType\":\""+rectType+"\",\"statusName\":\""+statusName+"\",\"pageIndex\":\""+pageIndex+"\"," +
                "\"pageSize\":\""+pageSize+"\",\"keyword\":\""+keyword+"\",\"level\":\"" + level + "\",\"typeGraFaName\":\"" + typeGraFaName + "\"" +
                ",\"projectAreaName\":\"" + projectAreaName + "\"}");
        XUtil.postjsondata(getjsonobj, "GetDataTotal2_rectListMsg", new MyCallBack() {
            @Override
            public void onResule(String res) {
                if("".equals(res)){

                }
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    JSONArray recordList = jsonObject.getJSONArray("recordList");
                    List<AqsclistBean> getbean = JsonUtils.jsonToList(recordList.toString(), AqsclistBean[].class);

                    if (getbean != null && getbean.size() > 0) {
                        listBeenlist.addAll(getbean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(ObjBean getbean) {

            }

            @Override
            public void onWrong() {

            }

            @Override
            public void onFinished() {
                commonAdapter.notifyDataSetChanged();
                activityBaselistviewSWPullRecyclerLayout.refreshok();
                if (onClickIndex!=-1){
                    activityBaselistviewSWPullRecyclerLayout.setRecyclerViewScrollToPosition(onClickIndex);
                    onClickIndex=-1;
                }
            }
        });
    }

}
