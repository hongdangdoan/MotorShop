package com.example.motorshop.datasrc;

public class BillDetail {
    private Integer id;
    private Integer billId;
    private Integer motorId;
    private Integer accessoryId;
    private Integer amount;
    private Integer price;

    public BillDetail() { }

    public BillDetail(Integer id, Integer billId, Integer motorId, Integer accessoryId, Integer amount, Integer price) {
        this.id = id;
        this.billId = billId;
        this.motorId = motorId;
        this.accessoryId = accessoryId;
        this.amount = amount;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getMotorId() {
        return motorId;
    }

    public void setMotorId(Integer motorId) {
        this.motorId = motorId;
    }

    public Integer getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Integer accessoryId) {
        this.accessoryId = accessoryId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
