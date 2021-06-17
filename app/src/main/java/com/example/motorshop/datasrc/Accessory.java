package com.example.motorshop.datasrc;

public class Accessory {
    private Integer id;
    private String name;
    private Integer amount;
    private Integer price;
    private Integer warrantyPeriod;
    private String brandId;

    public Accessory() { }

    public Accessory(Integer id, String name, Integer amount, Integer price, Integer warrantyPeriod, String brandId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.warrantyPeriod = warrantyPeriod;
        this.brandId = brandId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Integer warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}
