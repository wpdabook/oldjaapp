package com.a21zhewang.constructionapp.ui.wdjc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.a21zhewang.constructionapp.R;
import com.a21zhewang.constructionapp.base.BaseActivity;
import com.a21zhewang.constructionapp.bean.CheckType;
import com.a21zhewang.constructionapp.bean.CheckTypeOne;
import com.a21zhewang.constructionapp.bean.Project;
import com.a21zhewang.constructionapp.bean.UPJsonBean;
import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.a21zhewang.constructionapp.utils.JsonUtils;
import com.a21zhewang.constructionapp.utils.dbUtils;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class WdSetCheckListActivity extends BaseActivity {

    @BindView(R.id.activity_set_check_list_RecyclerView)
    RecyclerView activity_set_check_list_RecyclerView;
    @BindView(R.id.activity_set_check_list_save)
    TextView activity_set_check_list_save;
    public static CheckType checkType = null;
    private WdCheckListAdapter adapter;
    private Project pj;

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
        return R.layout.activity_set_check_list;
    }

    /**
     * 初始化界面
     */
    @Override
    public void initViews() {
        pj = getIntent().getParcelableExtra("pj");
        activity_set_check_list_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        activity_set_check_list_RecyclerView.addItemDecoration(new WdSimpleDividerDecoration("#e3e3e3", 2));

//        if (WdAddzxjcActivity.checkList != null) {
//
//            adapter = new WdCheckListAdapter(this, WdAddzxjcActivity.checkList,pj);
//
//            activity_set_check_list_RecyclerView.setAdapter(adapter);
//        }
    }

    /**
     * 初始化事件
     */
    @Override
    public void initListener() {
        activity_set_check_list_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.collapseAll();
                 List<CheckType> setchindseletors = setchindseletors(adapter.getData());
                String objtojson = JsonUtils.objtojson(setchindseletors);
              //  PublicUtils.log(objtojson);
                //PublicUtils.toast(setchindseletors.size() + "");
                try {
                    int delete = dbUtils.Instance().delete(UPJsonBean.class, WhereBuilder.b("datatype", "=", "save"));
               //     List<UPJsonBean> upJsonBeens = dbUtils.Instance().selector(UPJsonBean.class).where(WhereBuilder.b("datatype", "=", "save")).findAll();

                } catch (DbException e) {
                    PublicUtils.log("清除缓存检查项失败！");
                }
                dbUtils.savajson(objtojson,"save","",null);
                finish();
            }
        });


    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {

    }

    private List<CheckType> setchindseletors(List<? extends CheckType> childList) {
        List<CheckType> checkTypes = null;
        if (childList != null) {
            checkTypes = new ArrayList<>();
            for (int i = 0; i < childList.size(); i++) {
                CheckType checkType = childList.get(i);
                if (checkType.getStatus()>0) {
                    CheckTypeOne typeOne = new CheckTypeOne();
                    typeOne.setDicID(checkType.getDicID());
                    typeOne.setDicName(checkType.getDicName());
                    typeOne.setlevel(checkType.getlevel());

                    checkTypes.add(typeOne);
                    List list = checkType.getCheckItemList();
                    typeOne.setCheckItemList(setchindseletors(list));

                }

            }
        }
        return checkTypes;
    }

    /**
     * @param v 点击事件的实现
     */
    @Override
    public void processClick(View v) {

    }

    @Override
    public void resume() {
        if (checkType!=null){
            if (adapter!=null) adapter.hasChildSelet(checkType);
        }
        if (adapter!=null)
        adapter.notifyDataSetChanged();
        super.resume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkType=null;
    }
}
