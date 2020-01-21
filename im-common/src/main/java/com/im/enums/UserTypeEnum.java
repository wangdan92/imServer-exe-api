package com.im.enums;

/**
 * @author ZhangPeng
 * @date 2018/12/24
 */
public enum UserTypeEnum {
    OA((short)0, "OA系统用户！"),
    SERVICE((short)1, "服务系统用户！");

    private Short type;
    private String  name;

    UserTypeEnum(Short type, String name) {
        this.type = type;
        this.name = name;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
