package com.mvc.bean;

//物流信息
public class LogisticsInfoBean {
    //物流信息id，自增列
    private int id;
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
    //货重
    private int weight;

    public LogisticsInfoBean() {
    }

    public LogisticsInfoBean(int id, String lading_number, String customer_name, String customer_code, String company, String container_number, String cargo, int weight) {
        this.id = id;
        this.lading_number = lading_number;
        this.customer_name = customer_name;
        this.customer_code = customer_code;
        this.company = company;
        this.container_number = container_number;
        this.cargo = cargo;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
