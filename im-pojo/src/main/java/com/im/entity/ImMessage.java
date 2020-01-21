package com.im.entity;


import com.im.model.ImMessageModel;

import java.util.Date;

public class ImMessage extends ImMessageModel {
    private Integer id;
    /**用户编号*/
    private Integer userId;
    /**群组编号*/
    private Integer chatGroupId;
    /**创建时间*/
    private Date createTime;
    /**创建者*/
    private Integer createId;
    /**未读消息数量*/
    private Integer unreadNum;
    /**是否有@消息*/
    private Boolean atFlag;
    /**topFlag*/
    private Boolean topFlag;

    private String  updateTimeStr;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

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

    public Integer getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Integer chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(Integer unreadNum) {
        this.unreadNum = unreadNum;
    }

    public Boolean getAtFlag() {
        return atFlag;
    }

    public void setAtFlag(Boolean atFlag) {
        this.atFlag = atFlag;
    }

    public Boolean getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(Boolean topFlag) {
        this.topFlag = topFlag;
    }
}
