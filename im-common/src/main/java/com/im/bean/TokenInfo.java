package com.im.bean;

import java.io.Serializable;

/**
 * token信息的封装
 */
public class TokenInfo implements Serializable {

    private int userId;

    private String userName;

    private String token;

    private Short role;

    /**0-OA , 1-APP , 2-SERVICE , 3-PC */
    private int type;

    public Short getRole() {
        return role;
    }

    public void setRole(Short role) {
        this.role = role;
    }

    public TokenInfo() {
    }
    public TokenInfo(String userName) {
        this.userName = userName;
    }
    public TokenInfo(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }
    public TokenInfo(String userName, String token,Short role) {
        this.userName = userName;
        this.token = token;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
