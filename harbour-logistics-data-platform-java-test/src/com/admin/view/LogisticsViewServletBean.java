package com.admin.view;

public class LogisticsViewServletBean {
    private Object[] originArgs;
    private long pageNum;

    public LogisticsViewServletBean() {
    }

    public LogisticsViewServletBean(Object[] originArgs, long pageNum) {
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
