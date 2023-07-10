package com.api.viewBean;

import java.sql.Timestamp;

public class LogisticsInfoViewBean {
    //提单号
    private String lading_number;
    //货主名称
    private String customer_name;
    //货主代码
    private String customer_code;
    //物流公司名称
    private String company;
    //集装箱箱号
    private String container_number;
    //货物名称
    private String cargo;
    //启运地
    private String departure;
    //目的地
    private String destination;
    //始发时间
    private Timestamp departure_time;
    //到达时间
    private Timestamp arrival_time;

    public LogisticsInfoViewBean() {

    }

    public LogisticsInfoViewBean(String lading_number, String customer_name, String customer_code, String company, String container_number, String cargo, String departure, String destination, Timestamp departure_time, Timestamp arrival_time) {
        this.lading_number = lading_number;
        this.customer_name = customer_name;
        this.customer_code = customer_code;
        this.company = company;
        this.container_number = container_number;
        this.cargo = cargo;
        this.departure = departure;
        this.destination = destination;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
    }

    public String getLading_number() {
        return lading_number;
    }

    public void setLading_number(String lading_number) {
        this.lading_number = lading_number;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContainer_number() {
        return container_number;
    }

    public void setContainer_number(String container_number) {
        this.container_number = container_number;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
}
