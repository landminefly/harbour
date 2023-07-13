package com.mvc.bean;

public class SourcesBean {
    private String source_type;
    private String address;
    private String usr;
    private String pwd;

    public SourcesBean() {
    }

    public SourcesBean(String address) {
        this.address = address;
    }

    public SourcesBean(String source_type, String address, String usr, String pwd) {
        this.source_type = source_type;
        this.address = address;
        this.usr = usr;
        this.pwd = pwd;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
