package com.im.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.im.bean.BaseConfig;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ToJSON {
    @Autowired
    private static HttpServletRequest request1;
    @Autowired
    private static BaseConfig baseConfig;
    /**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     *
     */
    public static<T> JSONObject objectToJson(T obj){

        JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);

        if (jsonObject==null){
            return null;
        }
        if (jsonObject.getString("auditTime")!=null&&jsonObject.getString("auditTime").length()>0){
            jsonObject.put("auditTime",getString(jsonObject.getString("auditTime")));
        }
        if (jsonObject.getString("executionTime")!=null&&jsonObject.getString("executionTime").length()>0){
            jsonObject.put("executionTime",getString(jsonObject.getString("executionTime")));
        }
        if (jsonObject.getString("deliveryTime")!=null&&jsonObject.getString("deliveryTime").length()>0){
            jsonObject.put("deliveryTime",getString(jsonObject.getString("deliveryTime")));
        }
        if (jsonObject.getString("cancelTime")!=null&&jsonObject.getString("cancelTime").length()>0){
            jsonObject.put("cancelTime",getString(jsonObject.getString("cancelTime")));
        }
        if (jsonObject.getString("payTime")!=null&&jsonObject.getString("payTime").length()>0){
            jsonObject.put("payTime",getString(jsonObject.getString("payTime")));
        }
        if (jsonObject.getString("distributeTime")!=null&&jsonObject.getString("distributeTime").length()>0){
            jsonObject.put("distributeTime",getString(jsonObject.getString("distributeTime")));
        }
        if (jsonObject.getString("createTime")!=null&&jsonObject.getString("createTime").length()>0){
            jsonObject.put("createTime",getString(jsonObject.getString("createTime")));
        }
        if (jsonObject.getString("startTime")!=null&&jsonObject.getString("startTime").length()>0){
            jsonObject.put("startTime",getString(jsonObject.getString("startTime")));
        }
        if (jsonObject.getString("finishedTime")!=null&&jsonObject.getString("finishedTime").length()>0){
            jsonObject.put("finishedTime",getString(jsonObject.getString("finishedTime")));
        }

        if (jsonObject.getString("dispatchTime")!=null&&jsonObject.getString("dispatchTime").length()>0){
            jsonObject.put("dispatchTime",getString(jsonObject.getString("dispatchTime")));
        }
        if (jsonObject.getString("takeTime")!=null&&jsonObject.getString("takeTime").length()>0){
            jsonObject.put("takeTime",getString(jsonObject.getString("takeTime")));
        }
        if (jsonObject.getString("arrivedTime")!=null&&jsonObject.getString("arrivedTime").length()>0){
            jsonObject.put("arrivedTime",getString(jsonObject.getString("arrivedTime")));
        }
        if (jsonObject.getString("repairedTime")!=null&&jsonObject.getString("repairedTime").length()>0){
            jsonObject.put("repairedTime",getString(jsonObject.getString("repairedTime")));
        }

        if (jsonObject.getString("time")!=null&&jsonObject.getString("time").length()>0){
            jsonObject.put("time",getString(jsonObject.getString("time")));
        }
        if (jsonObject.getString("endTime")!=null&&jsonObject.getString("endTime").length()>0){
            Date date = jsonObject.getDate("endTime");
            jsonObject.put("endTime",DateTimeUtil.format(date,"yyyy-MM-dd HH:mm:ss"));
        }
        if (jsonObject.getString("updateTime")!=null&&jsonObject.getString("updateTime").length()>0){
            jsonObject.put("updateTime",getString(jsonObject.getString("updateTime")));
        }
        if (jsonObject.getString("expireTime")!=null&&jsonObject.getString("expireTime").length()>0){
            jsonObject.put("expireTime",getString(jsonObject.getString("expireTime")));
        }
        if (jsonObject.getString("operationTime")!=null&&jsonObject.getString("operationTime").length()>0){
            jsonObject.put("operationTime",getString(jsonObject.getString("operationTime")));
        }
        if (jsonObject.getString("deadline")!=null&&jsonObject.getString("deadline").length()>0){
            jsonObject.put("deadline",getString(jsonObject.getString("deadline")));
        }
        if (jsonObject.getString("docBody")!=null&&jsonObject.getString("docBody").length()>0){
            jsonObject.put("docBody",JSON.parseArray(jsonObject.getString("docBody")));
        }
        if (jsonObject.getString("docBackground")!=null&&jsonObject.getString("docBackground").length()>0){
            jsonObject.put("docBackground",JSON.parseArray(jsonObject.getString("docBackground")));
        }
        if (jsonObject.getString("imagesPath")!=null&&jsonObject.getString("imagesPath").length()>0){
            jsonObject.put("imagesPath",getPath(JSON.parseArray(jsonObject.getString("imagesPath"))));
        }else {
            jsonObject.put("imagesPath",null);
        }
        if (jsonObject.getString("repairImages")!=null&&jsonObject.getString("repairImages").length()>0){
            jsonObject.put("repairImages",getPath(JSON.parseArray(jsonObject.getString("repairImages"))));
        }else {
            jsonObject.put("repairImages",null);
        }
        return jsonObject;
    }

    public static<T> JSONObject objectToJson(T obj, HttpServletRequest request){
        request1 = request;
        JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);

        if (jsonObject==null){
            return null;
        }
        if (jsonObject.getString("auditTime")!=null&&jsonObject.getString("auditTime").length()>0){
            jsonObject.put("auditTime",getString(jsonObject.getString("auditTime")));
        }
        if (jsonObject.getString("executionTime")!=null&&jsonObject.getString("executionTime").length()>0){
            jsonObject.put("executionTime",getString(jsonObject.getString("executionTime")));
        }
        if (jsonObject.getString("deliveryTime")!=null&&jsonObject.getString("deliveryTime").length()>0){
            jsonObject.put("deliveryTime",getString(jsonObject.getString("deliveryTime")));
        }
        if (jsonObject.getString("createTime")!=null&&jsonObject.getString("createTime").length()>0){
            jsonObject.put("createTime",getString(jsonObject.getString("createTime")));
        }
        if (jsonObject.getString("startTime")!=null&&jsonObject.getString("startTime").length()>0){
            jsonObject.put("startTime",getString(jsonObject.getString("startTime")));
        }
        if (jsonObject.getString("finishedTime")!=null&&jsonObject.getString("finishedTime").length()>0){
            jsonObject.put("finishedTime",getString(jsonObject.getString("finishedTime")));
        }

        if (jsonObject.getString("dispatchTime")!=null&&jsonObject.getString("dispatchTime").length()>0){
            jsonObject.put("dispatchTime",getString(jsonObject.getString("dispatchTime")));
        }
        if (jsonObject.getString("takeTime")!=null&&jsonObject.getString("takeTime").length()>0){
            jsonObject.put("takeTime",getString(jsonObject.getString("takeTime")));
        }
        if (jsonObject.getString("arrivedTime")!=null&&jsonObject.getString("arrivedTime").length()>0){
            jsonObject.put("arrivedTime",getString(jsonObject.getString("arrivedTime")));
        }
        if (jsonObject.getString("repairedTime")!=null&&jsonObject.getString("repairedTime").length()>0){
            jsonObject.put("repairedTime",getString(jsonObject.getString("repairedTime")));
        }

        if (jsonObject.getString("time")!=null&&jsonObject.getString("time").length()>0){
            jsonObject.put("time",getString(jsonObject.getString("time")));
        }
        if (jsonObject.getString("endTime")!=null&&jsonObject.getString("endTime").length()>0){
            Date date = jsonObject.getDate("endTime");
            jsonObject.put("endTime",DateTimeUtil.format(date,"yyyy-MM-dd HH:mm:ss"));
        }
        if (jsonObject.getString("updateTime")!=null&&jsonObject.getString("updateTime").length()>0){
            jsonObject.put("updateTime",getString(jsonObject.getString("updateTime")));
        }
        if (jsonObject.getString("expireTime")!=null&&jsonObject.getString("expireTime").length()>0){
            jsonObject.put("expireTime",getString(jsonObject.getString("expireTime")));
        }
        if (jsonObject.getString("operationTime")!=null&&jsonObject.getString("operationTime").length()>0){
            jsonObject.put("operationTime",getString(jsonObject.getString("operationTime")));
        }
        if (jsonObject.getString("deadline")!=null&&jsonObject.getString("deadline").length()>0){
            jsonObject.put("deadline",getString(jsonObject.getString("deadline")));
        }
        if (jsonObject.getString("docBody")!=null&&jsonObject.getString("docBody").length()>0){
            jsonObject.put("docBody",JSON.parseArray(jsonObject.getString("docBody")));
        }
        if (jsonObject.getString("docBackground")!=null&&jsonObject.getString("docBackground").length()>0){
            jsonObject.put("docBackground",JSON.parseArray(jsonObject.getString("docBackground")));
        }
        if (jsonObject.getString("imagesPath")!=null&&jsonObject.getString("imagesPath").length()>0){
            jsonObject.put("imagesPath",getPath(JSON.parseArray(jsonObject.getString("imagesPath"))));
        }else {
            jsonObject.put("imagesPath",null);
        }
        if (jsonObject.getString("repairImages")!=null&&jsonObject.getString("repairImages").length()>0){
            jsonObject.put("repairImages",getPath(JSON.parseArray(jsonObject.getString("repairImages"))));
        }else {
            jsonObject.put("repairImages",null);
        }
        return jsonObject;
    }
    //时间格式化
    public static String getString(String string){
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(string);
        } catch (Exception e) {
            try {
                return DateTimeUtil.format(DateTimeUtil.parse(string,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss");
            } catch (Exception e1) {
                e1.printStackTrace();
                return DateTimeUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
            }
        }
        String s = DateTimeUtil.format(date,"yyyy-MM-dd HH:mm:ss");
        return s;
    }

    //图片路径拼接
    public static JSONArray getPath(JSONArray jsonArray){

        request1 = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (baseConfig==null){
            baseConfig =  ContextHolder.getBean(BaseConfig.class);
        }

        JSONArray jsonArray1 = new JSONArray();
        jsonArray.forEach(item->{
            String base64Str = Base64.encodeBase64URLSafeString(item.toString().getBytes());
            String url = baseConfig.getMultipartUrl() + "/getFileFromEncodeParam?encodePath=" + base64Str;
            jsonArray1.add(url);
        });

        return jsonArray1;
    }
}
