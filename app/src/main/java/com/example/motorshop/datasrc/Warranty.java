package com.example.motorshop.datasrc;

public class Warranty {
    private Integer id;
    private String createdDate;
    private Integer billId;
    private String staffId;

    public Warranty() { }

    public Warranty(Integer id, String createdDate, Integer billId, String staffId) {
        this.id = id;
        this.createdDate = createdDate;
        this.billId = billId;
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

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
