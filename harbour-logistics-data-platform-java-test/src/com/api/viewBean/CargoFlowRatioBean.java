package com.api.viewBean;

public class CargoFlowRatioBean {
    private String cargo;
    private String port;
    private int weight;

    public CargoFlowRatioBean() {
    }

    public CargoFlowRatioBean(String cargo, String port, int weight) {
        this.cargo = cargo;
        this.port = port;
        this.weight = weight;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
