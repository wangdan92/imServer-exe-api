package com.im.entity;


import com.im.model.ImChatGroupModel;

import java.util.Date;

public class ImChatGroup extends ImChatGroupModel {
    private Integer id;
    /**群名称*/
    private String name;
    /**创建时间*/
    private Date createTime;
    /**创建者*/
    private Integer createId;
    /**群公告*/
    private String notice;
    /**群主*/
    private Integer master;
    /**群组状态：0-未结束，1-已结束*/
    private Integer status;
    /**关联任务编号*/
    private Integer refId;
    /**类型 1普通 2任务群组*/
    private Integer groupType;
    private Date noticeUpdateTime;
    /**true 表示前端展示群成员,false,表示前端不展示群成员*/
    private boolean expansion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public Date getNoticeUpdateTime() {
        return noticeUpdateTime;
    }

    public void setNoticeUpdateTime(Date noticeUpdateTime) {
        this.noticeUpdateTime = noticeUpdateTime;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
    }
}
