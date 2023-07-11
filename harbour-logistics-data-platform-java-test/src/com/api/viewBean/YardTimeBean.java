package com.api.viewBean;

public class YardTimeBean {
    private String cargo;
    private double average_time;

    public YardTimeBean() {
    }

    public YardTimeBean(String cargo, double average_time) {
        this.cargo = cargo;
        this.average_time = average_time;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getAverage_time() {
        return average_time;
    }

    public void setAverage_time(double average_time) {
        this.average_time = average_time;
    }
}
