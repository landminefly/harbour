package com.api.viewBean;

public class PortCargoRatioBean {
    private String cargo;
    private int total_weight;

    public PortCargoRatioBean() {
    }

    public PortCargoRatioBean(String cargo, int total_weight) {
        this.cargo = cargo;
        this.total_weight = total_weight;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(int total_weight) {
        this.total_weight = total_weight;
    }
}
