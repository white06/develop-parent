package com.tdu.develop.user.pojo;

/**
 * 分页实体类
 *
 * @author TDu063
 */
public class PageObject {
    /**
     * 当前页
     */
    private int pageNumber = 1;
    /**
     * 每页记录条数
     */
    private int pageSize = 3;
    private int rowCount;
    /**
     * 起始位置
     */
    private int startIndex;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

}
