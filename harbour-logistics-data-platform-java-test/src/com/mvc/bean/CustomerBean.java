package com.mvc.bean;

//客户信息
public class CustomerBean {
    //客户id，自增列
    private int id;
    //客户名称
    private String name;
    //客户身份证号
    private String code;
    //客户手机号
    private String phone;
    //客户地址
    private String address;

    public CustomerBean() {
    }

    public CustomerBean(int id, String name, String code, String phone, String address) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "CustomerBean{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", code='" + code + '\'' +
               ", phone='" + phone + '\'' +
               ", address='" + address + '\'' +
               '}';
    }
}
