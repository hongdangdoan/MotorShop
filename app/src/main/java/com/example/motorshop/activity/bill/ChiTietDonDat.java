package com.example.motorshop.activity.bill;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motorshop.activity.R;
import com.example.motorshop.datasrc.ChiTietSanPhamDonHang;
import com.example.motorshop.datasrc.PhuTung;
import com.example.motorshop.datasrc.Xe;
import com.example.motorshop.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonDat extends AppCompatActivity {

    String billM = "XE";
    String billA = "PT";

    String billType, maHD;
    TableLayout tblayoutBang;
    TextView tvHD;
    List<ChiTietSanPhamDonHang> dsDDX = new ArrayList<ChiTietSanPhamDonHang>();
    List<Xe> dsXe = new ArrayList<Xe>();
    List<PhuTung> dsPT = new ArrayList<PhuTung>();
    List<ChiTietSanPhamDonHang> dsDDPT = new ArrayList<ChiTietSanPhamDonHang>();
    DBManager dbR = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_dat);

        setControl();
        Intent inten = getIntent();
        String tmp[] = inten.getStringExtra("loai_DD").toString().split("@");
        billType = tmp[0];
        maHD = tmp[1].toString();
        tvHD.setText("CHI TIẾT HOÁ ĐƠN " + maHD);
        loadHDDX();
        showHD();
    }

    private void setControl() {
        tblayoutBang = (TableLayout) findViewById(R.id.tblayoutBangCT);
        tvHD = (TextView) findViewById(R.id.tvHD);
    }

    public void loadHDDX() {
        if (billType.equals(billM)) {

            dsXe = dbR.loadDSXE();
            List<ChiTietSanPhamDonHang> tmp = new ArrayList<ChiTietSanPhamDonHang>();
            tmp = dbR.loadDSDDX();
            for (int i = 0; i < tmp.size(); i++) {
                if (tmp.get(i).getMaDH().equals(maHD)) {
                    dsDDX.add(tmp.get(i));
                }
            }
        }
        if (billType.equals(billA)) {
            dsPT = dbR.loadDSPT();
            List<ChiTietSanPhamDonHang> tmp = new ArrayList<ChiTietSanPhamDonHang>();
            tmp = dbR.loadDSDDPT();
            for (int i = 0; i < tmp.size(); i++) {
                if (tmp.get(i).getMaDH().equals(maHD)) {
                    dsDDPT.add(tmp.get(i));

                }
            }
        }

    }

    public void showHD() {

        TableRow tbRow = null;

        TextView maSP;
        TextView tenSP = new TextView(getApplicationContext());
        TextView sl = new TextView(getApplicationContext());
        TextView donGia = new TextView(getApplicationContext());
        if (billType.equals(billM)) {
            for (int i = 0; i < dsDDX.size(); i++) {

                sl = new TextView(getApplicationContext());
                donGia = new TextView(getApplicationContext());
                tenSP = new TextView(getApplicationContext());
                maSP = new TextView(getApplicationContext());
                maSP.setText(dsDDX.get(i).getTenSP());
                for (int j = 0; j < dsXe.size(); j++) {
                    if (dsXe.get(j).getMaSP().equals(dsDDX.get(i).getTenSP())) {
                        tenSP.setText(dsXe.get(j).getTenSP());
                        break;
                    }
                }
                sl.setText(String.valueOf(dsDDX.get(i).getSoLuong()));
                donGia.setText(String.valueOf(dsDDX.get(i).getDonGiaBan()));
                tbRow = new TableRow(getApplicationContext());
                tbRow.addView(maSP, 0);
                tbRow.addView(tenSP, 1);
                tbRow.addView(sl, 2);
                tbRow.addView(donGia, 3);
                tblayoutBang.addView(tbRow);
            }

        }
        if (billType.equals(billA)) {
            System.out.println("kiem tra");
            System.out.println(dsDDPT.size());
            for (int i = 0; i < dsDDPT.size(); i++) {
                sl = new TextView(getApplicationContext());
                donGia = new TextView(getApplicationContext());
                tenSP = new TextView(getApplicationContext());
                maSP = new TextView(getApplicationContext());
                maSP.setText(dsDDPT.get(i).getTenSP());
                for (int j = 0; j < dsPT.size(); j++) {
                    if (dsPT.get(j).getMaSP().equals(dsDDPT.get(i).getTenSP())) {
                        tenSP.setText(dsPT.get(j).getTenSP());
                        break;
                    }
                }
                tbRow = new TableRow(getApplicationContext());
                sl.setText(String.valueOf(dsDDPT.get(i).getSoLuong()));
                donGia.setText(String.valueOf(dsDDPT.get(i).getDonGiaBan()));
                tbRow.addView(maSP, 0);
                tbRow.addView(tenSP, 1);
                tbRow.addView(sl, 2);
                tbRow.addView(donGia, 3);
                tblayoutBang.addView(tbRow);
            }
        }
    }

}