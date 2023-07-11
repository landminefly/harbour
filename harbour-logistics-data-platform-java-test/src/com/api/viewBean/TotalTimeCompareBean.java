package com.api.viewBean;

public class TotalTimeCompareBean {
    private String port;
    private int year;
    private int month;
    private int total_weight;
    private double monthly_growth_rate;
    private double yearly_growth_rate;

    public TotalTimeCompareBean() {
    }

    public TotalTimeCompareBean(String port, int year, int month, int total_weight, double monthly_growth_rate, double yearly_growth_rate) {
        this.port = port;
        this.year = year;
        this.month = month;
        this.total_weight = total_weight;
        this.monthly_growth_rate = monthly_growth_rate;
        this.yearly_growth_rate = yearly_growth_rate;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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

    public double getMonthly_growth_rate() {
        return monthly_growth_rate;
    }

    public void setMonthly_growth_rate(double monthly_growth_rate) {
        this.monthly_growth_rate = monthly_growth_rate;
    }

    public double getYearly_growth_rate() {
        return yearly_growth_rate;
    }

    public void setYearly_growth_rate(double yearly_growth_rate) {
        this.yearly_growth_rate = yearly_growth_rate;
    }
}
