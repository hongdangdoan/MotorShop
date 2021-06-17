package com.example.motorshop.datasrc;

public class MotorInfo {
    private Integer id;
    private String name;

    public MotorInfo() { }

    public MotorInfo(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
