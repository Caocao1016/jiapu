package com.yskj.daishuguan.response;

import java.util.List;

/**
 * CaoPengFei
 * 2019/2/15
 *
 * @ClassName: ManagementListResponse
 * @Description:
 */

public class ManagementListResponse {

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;
    private List<ManagementListItemResponse> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<ManagementListItemResponse> getList() {
        return list;
    }

    public void setList(List<ManagementListItemResponse> list) {
        this.list = list;
    }
}
