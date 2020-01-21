package com.im.entity;


import java.util.Date;

/**
 * 基础类别
 *
 * @author 乐天
 * @since 2018-10-07
 */
public class BaseEntity {

    /**
     * 说明
     */
    private String remarks;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private String delFlag;

    public void preInsert() {
        this.createDate = new Date();
        this.updateDate = new Date();
        this.delFlag = "0";
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
