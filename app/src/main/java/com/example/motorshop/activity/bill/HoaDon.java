package com.example.motorshop.activity.bill;

public class HoaDon {
    private int maHD;
    private int tongTien;

    public HoaDon(){}

    public HoaDon(int maHD, int tongTien) {
        this.maHD = maHD;
        this.tongTien = tongTien;
    }

    public int getMaHD() {
        return maHD;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
