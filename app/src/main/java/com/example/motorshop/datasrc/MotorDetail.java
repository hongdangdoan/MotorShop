package com.example.motorshop.datasrc;

public class MotorDetail {
    private Integer id;
    private Integer motorId;
    private Integer motorInfoId;
    private String content;

    public MotorDetail() { }

    public MotorDetail(Integer id, Integer motorId, Integer motorInfoId, String content) {
        this.id = id;
        this.motorId = motorId;
        this.motorInfoId = motorInfoId;
        this.content = content;
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

    public Integer getMotorInfoId() {
        return motorInfoId;
    }

    public void setMotorInfoId(Integer motorInfoId) {
        this.motorInfoId = motorInfoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
