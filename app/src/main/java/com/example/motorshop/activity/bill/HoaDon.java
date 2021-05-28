package com.example.motorshop.activity.bill;

public class HoaDon {
    private String maHD;
    private int tongTien;

    public HoaDon(){}

    public HoaDon(String maHD, int tongTien) {
        this.maHD = maHD;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
