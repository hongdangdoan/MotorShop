package com.example.motorshop.activity.bill;

public class HangHoa {

    private String maSP;
    private String tenSP;
    private int donGia;
    private int soLuong;

    public HangHoa(String maSP, String tenSP, int donGia, int soLuong) {
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


    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaSP() {
        return maSP;
    }

    public int getDonGia() {
        return donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }



}
