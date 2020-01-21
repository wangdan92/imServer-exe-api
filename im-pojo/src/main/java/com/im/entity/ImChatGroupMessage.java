package com.im.entity;

import com.im.model.ImChatGroupMessageModel;

import java.util.Date;

public class ImChatGroupMessage extends ImChatGroupMessageModel {
    private Integer id;
    /**发送内容*/
    private String content;
    /**发送时间*/
    private Date sendTime;
    /**发送人*/
    private Integer fromId;
    /**群组编号*/
    private Integer chatGroupId;
    /**内容类型：1-文本2-图片3-视频4-音频5-文件*/
    private Short contentType;
    /**消息类别 1-普通消息2-系统消息*/
    private Short messageType;
    /** 关联编号*/
    private Integer refId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Integer chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Short getContentType() {
        return contentType;
    }

    public void setContentType(Short contentType) {
        this.contentType = contentType;
    }

    public Short getMessageType() {
        return messageType;
    }

    public void setMessageType(Short messageType) {
        this.messageType = messageType;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }
}
