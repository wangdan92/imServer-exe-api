package com.im.entity;


import com.im.model.ImBadWordsModel;

import java.util.Date;

/**
 * @author WD
 * @title: ImBadWords
 * @projectName wjw-back
 * @description: 敏感词实体类
 * @date 2019/12/25--14:58
 */
public class ImBadWords extends ImBadWordsModel {

    private Long id;
    private String badWord;
    private Integer createId;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBadWord() {
        return badWord;
    }

    public void setBadWord(String badWord) {
        this.badWord = badWord;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
