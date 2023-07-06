package com.mvc.bean;

import java.sql.Date;

//集装箱动态
public class ContainerStatusBean {
    //集装箱动态id，自增列
    private int id;
    //堆存港口
    private String port;
    //集装箱箱号
    private String container_number;
    //箱尺寸
    private int size;
    //提单号
    private String lading_number;
    //堆场位置
    private String yard;
    //操作，0表示出库，1表示入库
    private String operation;
    //操作日期
    private Date date;

    public ContainerStatusBean() {
    }

    public ContainerStatusBean(int id, String port, String container_number, int size, String lading_number, String yard, String operation, Date date) {
        this.id = id;
        this.port = port;
        this.container_number = container_number;
        this.size = size;
        this.lading_number = lading_number;
        this.yard = yard;
        this.operation = operation;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContainer_number() {
        return container_number;
    }

    public void setContainer_number(String container_number) {
        this.container_number = container_number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLading_number() {
        return lading_number;
    }

    public void setLading_number(String lading_number) {
        this.lading_number = lading_number;
    }

    public String getYard() {
        return yard;
    }

    public void setYard(String yard) {
        this.yard = yard;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
