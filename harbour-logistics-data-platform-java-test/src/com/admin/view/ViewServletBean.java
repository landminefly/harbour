package com.admin.view;

public class ViewServletBean {
    private Object[] originArgs;
    private long pageNum;

    public ViewServletBean() {
    }

    public ViewServletBean(Object[] originArgs, long pageNum) {
        this.originArgs = originArgs;
        this.pageNum = pageNum;
    }

    public Object[] getOriginArgs() {
        return originArgs;
    }

    public void setOriginArgs(Object[] originArgs) {
        this.originArgs = originArgs;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
