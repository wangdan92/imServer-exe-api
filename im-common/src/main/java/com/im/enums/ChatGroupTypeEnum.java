package com.im.enums;

public enum ChatGroupTypeEnum {
    ORDINARYCHAT(0,"点对点聊天"),
    ORDINARYGROUPCHAT(1,"普通群聊"),
    TASKGROUPCHAT(2,"任务群聊"),
    SYSTEM(3,"系统消息群组");

    private Integer code;
    private String name;

    ChatGroupTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
