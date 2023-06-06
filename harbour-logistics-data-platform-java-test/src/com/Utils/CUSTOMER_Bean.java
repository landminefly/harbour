package com.Utils;

public class CUSTOMER_Bean {
    int CUSTOMER_ID;
    String CUSTOMER_NAME;
    String CUSTOMER_NUMBER;
    String CUSTOMER_PHONE;
    String CUSTOMER_ADDRESS_PROVINCE;
    String CUSTOMER_ADDRESS_CITY;

    public CUSTOMER_Bean() {}

    public CUSTOMER_Bean(int CUSTOMER_ID, String CUSTOMER_NAME, String CUSTOMER_NUMBER, String CUSTOMER_PHONE, String CUSTOMER_ADDRESS_PROVINCE, String CUSTOMER_ADDRESS_CITY) {
        this.CUSTOMER_ID = CUSTOMER_ID;
        this.CUSTOMER_NAME = CUSTOMER_NAME;
        this.CUSTOMER_NUMBER = CUSTOMER_NUMBER;
        this.CUSTOMER_PHONE = CUSTOMER_PHONE;
        this.CUSTOMER_ADDRESS_PROVINCE = CUSTOMER_ADDRESS_PROVINCE;
        this.CUSTOMER_ADDRESS_CITY = CUSTOMER_ADDRESS_CITY;
    }

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getCUSTOMER_NUMBER() {
        return CUSTOMER_NUMBER;
    }

    public void setCUSTOMER_NUMBER(String CUSTOMER_NUMBER) {
        this.CUSTOMER_NUMBER = CUSTOMER_NUMBER;
    }

    public String getCUSTOMER_PHONE() {
        return CUSTOMER_PHONE;
    }

    public void setCUSTOMER_PHONE(String CUSTOMER_PHONE) {
        this.CUSTOMER_PHONE = CUSTOMER_PHONE;
    }

    public String getCUSTOMER_ADDRESS_PROVINCE() {
        return CUSTOMER_ADDRESS_PROVINCE;
    }

    public void setCUSTOMER_ADDRESS_PROVINCE(String CUSTOMER_ADDRESS_PROVINCE) {
        this.CUSTOMER_ADDRESS_PROVINCE = CUSTOMER_ADDRESS_PROVINCE;
    }

    public String getCUSTOMER_ADDRESS_CITY() {
        return CUSTOMER_ADDRESS_CITY;
    }

    public void setCUSTOMER_ADDRESS_CITY(String CUSTOMER_ADDRESS_CITY) {
        this.CUSTOMER_ADDRESS_CITY = CUSTOMER_ADDRESS_CITY;
    }

    @Override
    public String toString() {
        return "records{" +
                "CUSTOMER_ID=" + CUSTOMER_ID +
                ", CUSTOMER_NAME='" + CUSTOMER_NAME + '\'' +
                ", CUSTOMER_NUMBER='" + CUSTOMER_NUMBER + '\'' +
                ", CUSTOMER_PHONE='" + CUSTOMER_PHONE + '\'' +
                ", CUSTOMER_ADDRESS_PROVINCE='" + CUSTOMER_ADDRESS_PROVINCE + '\'' +
                ", CUSTOMER_ADDRESS_CITY='" + CUSTOMER_ADDRESS_CITY + '\'' +
                '}';
    }
}
