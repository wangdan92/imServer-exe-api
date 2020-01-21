package com.im.model;


import com.im.entity.ImChatGroupUser;

import java.util.List;

public class ImChatGroupModel extends BaseModel {
    /**姓名*/
    private String realName;
    /**群主*/
    private String masterName;
    /**头像地址*/
    private String photoPath;
    /**成员列表*/
    private List<ImChatGroupUser> userList;
    private Integer userNum;
    private String noticeUpdateTimeString;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public List<ImChatGroupUser> getUserList() {
        return userList;
    }

    public void setUserList(List<ImChatGroupUser> userList) {
        this.userList = userList;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getNoticeUpdateTimeString() {
        return noticeUpdateTimeString;
    }

    public void setNoticeUpdateTimeString(String noticeUpdateTimeString) {
        this.noticeUpdateTimeString = noticeUpdateTimeString;
    }
}
