package com.im.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 乐天
 * @since 2018-10-23
 */
public class ImGroup extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String name;

    private List<User> userList;

    private boolean expansion = false;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion(boolean expansion) {
        this.expansion = expansion;
    }
}
