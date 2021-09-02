package com.a21zhewang.constructionapp.utils;

import android.content.Context;

import com.a21zhewang.constructionapp.bean.UPJsonBean;
import com.google.gson.Gson;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhewang_ljw on 2016/12/20.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class dbUtils {
    private static DbManager db;
    private dbUtils(Context context) {
    }
    public static DbManager Instance() {
        if (db == null) {
            db =createdb();
        }
        return db;
    }
    private static DbManager createdb() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("data.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
//                .setDbDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath())) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
        return x.getDb(daoConfig);
    }


    /**
     *
     *
     * @param savejson 保存的json数据
     * @param Datatype url请求参数
     * @param Datatypename 保存的表的名字
     * @param filepaths  存储文件路径的集合
     * */
    public static void savajson(String savejson, String Datatype,String Datatypename, List<String> filepaths) {
        try {
            DbManager dbManager = dbUtils.Instance();
            UPJsonBean upJsonBean = new UPJsonBean();//
            upJsonBean.setDatatype(Datatype);//上传的请求参数
            if (filepaths!=null)
            upJsonBean.setFilepaths( new Gson().toJson(filepaths));//上传文件路径集合
            upJsonBean.setDatatypename(Datatypename);//上传表名字
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            upJsonBean.setAddtime(str);//保存到离线事务的是
            upJsonBean.setDatatext(savejson);//上传json数据
            dbManager.save(upJsonBean);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    public static void savaobj(Object obj) {
        try {
            DbManager dbManager = dbUtils.Instance();
            dbManager.save(obj);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
