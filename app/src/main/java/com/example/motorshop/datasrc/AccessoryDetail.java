package com.example.motorshop.datasrc;

public class AccessoryDetail {
    private Integer id;
    private Integer accessoryId;
    private Integer motorId;
    private Integer price;

    public AccessoryDetail() { }

    public AccessoryDetail(Integer id, Integer accessoryId, Integer motorId, Integer price) {
        this.id = id;
        this.accessoryId = accessoryId;
        this.motorId = motorId;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Integer accessoryId) {
        this.accessoryId = accessoryId;
    }

    public Integer getMotorId() {
        return motorId;
    }

    public void setMotorId(Integer motorId) {
        this.motorId = motorId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
