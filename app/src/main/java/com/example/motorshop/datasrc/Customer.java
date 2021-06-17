package com.example.motorshop.datasrc;

public class Customer {
    private String id;
    private String identityId;
    private String name;
    private String address;
    private String phone;
    private String passWord;
    private String createdDate;

    public Customer() { }

    public Customer(String id, String identityId, String name, String address, String phone, String passWord, String createdDate) {
        this.id = id;
        this.identityId = identityId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.passWord = passWord;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}