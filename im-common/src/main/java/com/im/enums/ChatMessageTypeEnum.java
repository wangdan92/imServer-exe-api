package com.im.enums;

public enum ChatMessageTypeEnum {
    //1-文本2-图片3-视频4-音频5-文件
    TEXT((short)1,"user"),
    IMG((short)2,"system");

    private Short code;
    private String name;

    ChatMessageTypeEnum(Short code, String name) {
        this.code = code;
        this.name = name;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
