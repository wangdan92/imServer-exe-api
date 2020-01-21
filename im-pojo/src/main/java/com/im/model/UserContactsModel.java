package com.im.model;

import java.util.List;

public class UserContactsModel {
    private String fristalphabet;
    private List<UserContactsItemModel> item;

    public String getFristalphabet() {
        return fristalphabet;
    }

    public void setFristalphabet(String fristalphabet) {
        this.fristalphabet = fristalphabet;
    }

    public List<UserContactsItemModel> getItem() {
        return item;
    }

    public void setItem(List<UserContactsItemModel> item) {
        this.item = item;
    }
}
