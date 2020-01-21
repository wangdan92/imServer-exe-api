package com.im.enums;

/**
 * @author ZhangPeng
 * @date 2018/4/16
 */

/**
 * 用于返回状态的类
 */
public enum CommonEnum {
    SUCCESS(1, "成功！"),
    FAIL(0, "失败！");

    private Integer code;
    private String  msg;

    CommonEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
