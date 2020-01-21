package com.im.model;

import com.im.entity.User;

import java.io.Serializable;
import java.util.List;

public class DepartmentModel extends BaseModel implements Serializable {
    private String bureauName;
    private String realName;
    private String aid;
    private List<User> list;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getRealName() {

        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public String getBureauName() {
        return bureauName;
    }

    public void setBureauName(String bureauName) {
        this.bureauName = bureauName;
    }
}
