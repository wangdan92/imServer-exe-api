package com.im.entity;


import com.im.model.ImChatGroupUserModel;

import java.util.Date;

public class ImChatGroupUser extends ImChatGroupUserModel {
    private Integer id;
    /**群id*/
    private Integer chatGroupId;
    /**用户id*/
    private Integer userId;
    /**入群时间*/
    private Date createTime;
    /**最后发言时间*/
    private Date updateTime;
    /**创建者*/
    private Integer createId;
    /** 屏蔽消息标识，false-不屏蔽，true-屏蔽*/
    private Boolean silenceFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Integer chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getSilenceFlag() {
        return silenceFlag;
    }

    public void setSilenceFlag(Boolean silenceFlag) {
        this.silenceFlag = silenceFlag;
    }
}
