package com.im.entity;
import java.util.Date;

public class LoginLog {

    private Integer id;
    private Integer userId;
    private Date loginDatetime;
    private String ip;
    private String v4ip;
    private Integer whereLogin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getLoginDatetime() {
        return loginDatetime;
    }

    public void setLoginDatetime(Date loginDatetime) {
        this.loginDatetime = loginDatetime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getV4ip() {
        return v4ip;
    }

    public void setV4ip(String v4ip) {
        this.v4ip = v4ip;
    }

    public Integer getWhereLogin() {
        return whereLogin;
    }

    public void setWhereLogin(Integer whereLogin) {
        this.whereLogin = whereLogin;
    }
}
