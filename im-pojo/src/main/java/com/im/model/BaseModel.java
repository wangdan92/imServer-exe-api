package com.im.model;

import java.io.Serializable;

/**
 * @author ZhangPeng
 * @date 2017/11/27
 */
public class BaseModel implements Serializable {
    private transient Integer pageNum = 1;
    private transient Integer pageSize = 10;
    private boolean pageFlag=true;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isPageFlag() {
        return pageFlag;
    }

    public void setPageFlag(boolean pageFlag) {
        this.pageFlag = pageFlag;
    }
}
