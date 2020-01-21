package com.im.model;


import java.util.Date;

public class ImMessageModel extends BaseModel {
    /**群名称*/
    private String chatGroupName;
    /**消息内容*/
    private String content;
    /**内容类型*/
    private Integer contentType;
    /**消息类型*/
    private Integer messageType;
    /**最后更新时间*/
    private Date updateTime;
    /**头像路径*/
    private String url;
    /**群组类型*/
    private Integer groupType;
    /**消息发送人姓名*/
    private String sendName;
    private Boolean silenceFlag;

    public String getChatGroupName() {
        return chatGroupName;
    }

    public void setChatGroupName(String chatGroupName) {
        this.chatGroupName = chatGroupName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public Boolean getSilenceFlag() {
        return silenceFlag;
    }

    public void setSilenceFlag(Boolean silenceFlag) {
        this.silenceFlag = silenceFlag;
    }
}
