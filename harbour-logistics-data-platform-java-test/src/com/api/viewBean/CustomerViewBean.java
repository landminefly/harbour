package com.api.viewBean;

public class CustomerViewBean {
    //客户名称
    private String name;
    //客户身份证号
    private String code;
    //客户手机号
    private String phone;
    //客户地址
    private String address;

    public CustomerViewBean() {
    }

    public CustomerViewBean(String name, String code, String phone, String address) {
        this.name = name;
        this.code = code;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
