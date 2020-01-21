package com.im.model;



public class ImChatGroupMessageModel extends BaseModel {
    /**姓名*/
    private String realName;
    /**头像地址*/
    private String photoPath;
    private String sendTimeString;

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

    public String getSendTimeString() {
        return sendTimeString;
    }

    public void setSendTimeString(String sendTimeString) {
        this.sendTimeString = sendTimeString;
    }
}
