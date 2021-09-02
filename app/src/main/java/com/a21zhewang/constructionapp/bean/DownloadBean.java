package com.a21zhewang.constructionapp.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2021/7/18.
 */

public class DownloadBean implements Serializable{
    public String CreateTime;
    /**用于汉阳与其他版本区分的标记*/
    public int versionSign;

    /**汉阳建安*/
    public String version_name;
    public int version_code;
    public String version_desc;
    public String version_path;

    /**业主版（市安全站版本）*/
    public double Version;
    public String VerDisc;
    public String VerPath;



    public static DownloadBean parse(String response) {
        try {
            JSONObject repjson = new JSONObject(response);
            String CreateTime = repjson.optString("CreateTime");
            String version_name = repjson.optString("version_name");
            int version_code = repjson.optInt("version_code");
            String version_desc = repjson.optString("version_desc");
            String version_path = repjson.optString("version_path");
            Double Version = repjson.optDouble("Version");
            String VerDisc = repjson.optString("VerDisc");
            String VerPath = repjson.optString("VerPath");


            DownloadBean bean = new DownloadBean();
            bean.CreateTime = CreateTime;
            bean.version_name = version_name;
            bean.version_code = version_code;
            bean.version_desc = version_desc;
            bean.version_path = version_path;
            bean.Version = Version;
            bean.VerDisc = VerDisc;
            bean.VerPath = VerPath;

            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getVersionSign() {
        return versionSign;
    }

    public void setVersionSign(int versionSign) {
        this.versionSign = versionSign;
    }
}
