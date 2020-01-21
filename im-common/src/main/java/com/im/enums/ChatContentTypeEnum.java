package com.im.enums;

public enum ChatContentTypeEnum {
    //1-文本2-图片3-视频4-音频5-文件
    TEXT((short)1,"文本"),
    IMG((short)2,"图片"),
    VOICE((short)3,"音频"),
    VIDEO((short)4,"视频"),
    FILE((short)5,"文件");

    private Short code;
    private String name;

    ChatContentTypeEnum(Short code, String name) {
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
