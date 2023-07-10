package com.api.viewBean;

public class LogisticsCompanyViewBean {
    //物流公司名称
    private String name;
    //客户编号
    private String code;
    //联系人
    private String person;
    //手机号
    private String phone;
    //地址
    private String address;

    public LogisticsCompanyViewBean() {
    }

    public LogisticsCompanyViewBean(String name, String code, String person, String phone, String address) {
        this.name = name;
        this.code = code;
        this.person = person;
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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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
