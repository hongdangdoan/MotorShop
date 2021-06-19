package com.example.motorshop.activity.bill;

public class HangHoa {

    private int maSP;
    private String tenSP;
    private int donGia;
    private int soLuong;

    public HangHoa(int maSP, String tenSP, int donGia, int soLuong) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public HangHoa() {
    }


    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaSP() {
        return maSP;
    }

    public int getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }



}
