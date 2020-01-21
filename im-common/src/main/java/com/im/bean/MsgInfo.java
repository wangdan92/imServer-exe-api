package com.im.bean;


import com.im.enums.CommonEnum;

/**
 * @Author WD
 * @Description 返回类
 * @Date 14:55 2020/1/21
*/
public class MsgInfo {
    //状态码
    private int code;
    //状态
    private String msg;
    //数据
    private Object data;

    public MsgInfo() {
    }
    public MsgInfo(int code) {
        this.code = code;
    }

    public MsgInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgInfo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public MsgInfo(CommonEnum commonEnum){
        this.code = commonEnum.getCode();
        this.msg = commonEnum.getMsg();
    }

    public void setCodeAndMsg(CommonEnum commonEnum){
        this.code = commonEnum.getCode();
        this.msg = commonEnum.getMsg();
    }

    public void setCodeAndMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
