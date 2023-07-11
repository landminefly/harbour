package com.api.viewBean;

public class TotalTrendBean {
    private String cargo;
    private int year;
    private int month;
    private int total_weight;

    public TotalTrendBean() {
    }

    public TotalTrendBean(String cargo, int year, int month, int total_weight) {
        this.cargo = cargo;
        this.year = year;
        this.month = month;
        this.total_weight = total_weight;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(int total_weight) {
        this.total_weight = total_weight;
    }
}
