package com.mvc.bean;

import java.sql.Timestamp;

//装货表
public class LoadBean {
    //装货表id，自增列
    private int id;
    //船公司
    private String ship_company;
    //船名
    private String ship_name;
    //作业开始时间
    private Timestamp start_time;
    //作业结束时间
    private Timestamp end_time;
    //始发时间
    private Timestamp departure_time;
    //到达时间
    private Timestamp arrival_time;
    //作业港口
    private String port;
    //提单号
    private String lading_number;
    //集装箱箱号
    private String container_number;
    //箱尺寸
    private String container_size;
    //启运地
    private String departure;
    //目的地
    private String destination;

    public LoadBean() {
    }

    public LoadBean(int id, String ship_company, String ship_name, Timestamp start_time, Timestamp end_time, Timestamp departure_time, Timestamp arrival_time, String port, String lading_number, String container_number, String container_size, String departure, String destination) {
        this.id = id;
        this.ship_company = ship_company;
        this.ship_name = ship_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
        this.port = port;
        this.lading_number = lading_number;
        this.container_number = container_number;
        this.container_size = container_size;
        this.departure = departure;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShip_company() {
        return ship_company;
    }

    public void setShip_company(String ship_company) {
        this.ship_company = ship_company;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Timestamp getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Timestamp departure_time) {
        this.departure_time = departure_time;
    }

    public Timestamp getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Timestamp arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLading_number() {
        return lading_number;
    }

    public void setLading_number(String lading_number) {
        this.lading_number = lading_number;
    }

    public String getContainer_number() {
        return container_number;
    }

    public void setContainer_number(String container_number) {
        this.container_number = container_number;
    }

    public String getContainer_size() {
        return container_size;
    }

    public void setContainer_size(String container_size) {
        this.container_size = container_size;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
