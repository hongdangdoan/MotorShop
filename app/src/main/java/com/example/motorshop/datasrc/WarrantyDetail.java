package com.example.motorshop.datasrc;

public class WarrantyDetail {
    private Integer id;
    private Integer warrantyId;
    private Integer motorId;
    private Integer accessoryId;
    private String content;
    private Integer price;

    public WarrantyDetail() { }

    public WarrantyDetail(Integer id, Integer warrantyId, Integer motorId, Integer accessoryId, String content, Integer price) {
        this.id = id;
        this.warrantyId = warrantyId;
        this.motorId = motorId;
        this.accessoryId = accessoryId;
        this.content = content;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(Integer warrantyId) {
        this.warrantyId = warrantyId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
