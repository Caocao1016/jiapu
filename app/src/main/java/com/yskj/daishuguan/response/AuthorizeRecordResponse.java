package com.yskj.daishuguan.response;

import java.util.ArrayList;
import java.util.List;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: BannerResponse
 * @Description:
 */

public class AuthorizeRecordResponse {

    private int totalCount ;
    private int pageSize ;
    private int totalPage ;
    private int currentPage ;
    private List<AuthorizeResponse> list ;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<AuthorizeResponse> getList() {
        return list;
    }

    public void setList(List<AuthorizeResponse> list) {
        this.list = list;
    }
}
