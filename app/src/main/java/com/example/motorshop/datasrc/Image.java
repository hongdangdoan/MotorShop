package com.example.motorshop.datasrc;

public class Image {
    private Integer id;
    private Integer motorId;
    private Integer accessoryId;
    private byte[] image;

    public Image() { }

    public Image(Integer id, Integer motorId, Integer accessoryId, byte[] image) {
        this.id = id;
        this.motorId = motorId;
        this.accessoryId = accessoryId;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
