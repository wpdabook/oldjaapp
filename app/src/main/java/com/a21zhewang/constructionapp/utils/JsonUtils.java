package com.a21zhewang.constructionapp.utils;

import com.a21zhewang.constructionapp.publicContent.PublicUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhewang_ljw on 2016/11/23.
 * json解析工具类
 */

public class JsonUtils {
    private static Gson gson=null;
    private JsonUtils(){

    }
    public  static Gson instance(){
        if (gson==null)
            gson =new Gson();
        return gson;
    }
    /**
     * JSON转单个对象  | 如果要处理的JSON字符串只包含一个JSON对象，则可以直接使用fromJson获取一个User对象：
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T jsonEntity(String json, Class<T> clazz)
    {
        T t = null;
        try
        {
            t = new Gson().fromJson(json, clazz);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }
    //解析 成任意对象
    public static <obj extends Object>obj getbean(String text, Class objclass){
        JsonReader jsonReader = new JsonReader(new StringReader(text));//其中jsonContext为String类型的Json数据
        jsonReader.setLenient(true);
        return  (obj)instance().fromJson(jsonReader,objclass);
    }
    //解析 成任意对象
    public static Object getbean(Object text, Class objclass){
        JsonReader jsonReader = new JsonReader(new StringReader(objtojson(text)));//其中jsonContext为String类型的Json数据
        jsonReader.setLenient(true);
        return  instance().fromJson(jsonReader,objclass);
    }
    //解析 成任意对象
    public static String objtojson(Object obj){
        return  instance().toJson(obj);
    }
    public static JSONArray List2jsonArray(Object obj){
        try {
            return  new JSONArray(instance().toJson(obj));
        } catch (JSONException e) {
            e.printStackTrace();
           return  null;
        }
    }

    //解析 成任意对象
    public static JSONObject getjsonobj(Object obj){
        JSONObject jsonObject=null;
        try {
            if (obj instanceof String)
                jsonObject=new JSONObject(obj.toString());
            else
             jsonObject=new JSONObject(objtojson(obj));

        } catch (JSONException e) {
            PublicUtils.log("Object 转 JsonObject出错！");
            return  new JSONObject();
        }
        return  jsonObject;
    }

    //解析 成任意对象
    public static JSONArray getjsonobjs(Object obj){
        JSONArray jsonObject=null;
        try {
            if (obj instanceof String)
                jsonObject=new JSONArray(obj.toString());
            else
                jsonObject=new JSONArray(objtojson(obj));

        } catch (JSONException e) {
            PublicUtils.log("Object 转 JsonObject出错！");
        }
        return  jsonObject;
    }
    /**
     * @author I321533
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz)
    {
        Gson gson = new Gson();

        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
