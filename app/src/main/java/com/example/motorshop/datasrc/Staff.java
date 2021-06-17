package com.example.motorshop.datasrc;

public class Staff {
    private String id;
    private String name;
    private String phone;
    private String password;
    private String createdDate;
    private String departId;

    public Staff() { }

    public Staff(String id, String name, String phone, String password, String createdDate, String departId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.createdDate = createdDate;
        this.departId = departId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }
}