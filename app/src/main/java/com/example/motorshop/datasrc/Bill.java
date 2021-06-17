package com.example.motorshop.datasrc;

public class Bill {
    private Integer id;
    private String createdDate;
    private String customerId;
    private String staffId;

    public Bill() { }

    public Bill(Integer id, String createdDate, String customerId, String staffId) {
        this.id = id;
        this.createdDate = createdDate;
        this.customerId = customerId;
        this.staffId = staffId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
