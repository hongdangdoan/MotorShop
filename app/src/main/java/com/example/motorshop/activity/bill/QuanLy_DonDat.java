package com.example.motorshop.activity.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.motorshop.activity.R;
import com.example.motorshop.datasrc.ChiTietSanPhamDonHang;
import com.example.motorshop.datasrc.DonHang;
import com.example.motorshop.datasrc.KhachHang;
import com.example.motorshop.datasrc.NhaCungCap;
import com.example.motorshop.datasrc.Xe;
import com.example.motorshop.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class QuanLy_DonDat extends AppCompatActivity {

    Button btnThemDD,btnCTDD;
    TableLayout tableLayout;

    String billType;
    String billM = "XE";
    String billA = "PT";

    List<Xe> dsXe = new ArrayList<Xe>();
    List<DonHang> dsDh = new ArrayList<DonHang>();
    List<KhachHang> dsKh = new ArrayList<KhachHang>();
    List<ChiTietSanPhamDonHang> dsDDX = new ArrayList<ChiTietSanPhamDonHang>();
    List<ChiTietSanPhamDonHang> dsDDPT = new ArrayList<ChiTietSanPhamDonHang>();

    DBManager dbR = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_dat);
        setControl();
        Intent inten = getIntent();
        billType = inten.getStringExtra("loai_DD");
        init_DonDat(billType);

//        dbR.testDltCTDDX();
//        dbR.testInsCTDDX();
        testShowDDH();
        testShowDDX();

        showDDXList();

//        testInsertDBNCC();
//        testInsertDBXE();

        loadDSXeFromDB();

        setEven();
    }

    private void setControl(){

        btnThemDD = (Button)findViewById(R.id.btnThemDD);
        btnCTDD = (Button)findViewById(R.id.btnCTDD);
        tableLayout = (TableLayout)findViewById(R.id.tblayoutBang);
    }

    private void setEven() {


        btnThemDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLy_DonDat.this,DonDat.class);
                intent.putExtra("loai_DD",billType);
                startActivity(intent);
            }
        });
    }

    public void init_DonDat(String billType){

        if(billType.equals(billM))init_DonDat_Xe();
        if(billType.equals(billA))init_DonDat_PT();
    }

    public void init_DonDat_Xe(){


        dsDh = dbR.loadDSDH();
        dsKh = dbR.loadDsKH();
        dsDDX = dbR.loadDSDDX();
        addInfoToDDX();

        btnThemDD.setText("Thêm DDX");
        btnCTDD.setText("CT DDX");

    }

    public void addInfoToDDX(){
        for(int i=0;i<dsDDX.size();i++){
            dsDDX.get(i).setCmnd(findDHByMADH(dsDDX.get(i).getMaDH()).getCmnd());
            dsDDX.get(i).setNgayDat(findDHByMADH(dsDDX.get(i).getMaDH()).getNgayDat());
            dsDDX.get(i).setTenNV(findDHByMADH(dsDDX.get(i).getMaDH()).getTenNV());

        }
    }



    public void init_DonDat_PT(){

        btnThemDD.setText("Thêm DDPT");
        btnCTDD.setText("CT DDPT");

    }

    private KhachHang findKHByCMND(String cmnd){
        if(dsKh.size()==0){

            return null;
        }else{
            for(int i=0;i<dsKh.size();i++){
                if(dsKh.get(i).getCmnd().equals(cmnd)){
                    return dsKh.get(i);
                }
            }
        }

        return null;


    }

    private DonHang findDHByMADH(String maDH){
        if(dsDh.size()==0){

            return null;
        }else{
            for(int i=0;i<dsDh.size();i++){
                if(dsDh.get(i).getMaDH().equals(maDH)){
                    return dsDh.get(i);
                }
            }

        }

        return null;

    }

    private void showDDXList(){


        TableRow tbRow = null;

        for(int i=0;i<dsDDX.size();i++){

            String maHD = dsDDX.get(i).getMaDH();
            TextView txtMa = new TextView(getApplicationContext());
            txtMa.setText(maHD);
            TextView txtND = new TextView(getApplicationContext());
            txtND.setText(dsDDX.get(i).getNgayDat());
            TextView txtHoTen = new TextView(getApplicationContext());


            txtHoTen.setText(findKHByCMND(dsDDX.get(i).getCmnd()).getHoTen());
            TextView txtTongTien = new TextView(getApplicationContext());

            txtTongTien.setText(String.valueOf(dsDDX.get(i).getSoLuong()*dsDDX.get(i).getDonGiaBan()));
            tbRow = new TableRow(getApplicationContext());
            tbRow.addView(txtMa,0);
            tbRow.addView(txtND,1);
            tbRow.addView(txtHoTen,2);
            tbRow.addView(txtTongTien);
            tableLayout.addView(tbRow);

        }
    }

    public void testInsertDBXE(){
        Xe xe = new Xe();
        xe.setMaSP("X003");
        xe.setTenSP("Grande");
        xe.setSoLuong(100);
        xe.setDonGia(220000000);
        xe.setHanBH(2);
        xe.setHinhAnh(2);
        xe.setTenNCC("YA");
        DBManager db = new DBManager(getApplicationContext());
        db.insertXe(xe);
    }

    public void testInsertDBNCC(){
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC("HO");
        ncc.setTenNCC("Honda");
        ncc.setDiaChi("quan 9, TP Thu Duc");
        ncc.setSdt("123456780");
        ncc.setEmail("honda@gmail.com");
        ncc.setLogo(1);
    }

    public List<Xe> loadDSXeFromDB(){

        List<Xe> dsXe = new ArrayList<Xe>();
        SQLiteDatabase db = new DBManager(getApplicationContext()).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM XE ", null);

        if (c.moveToFirst()) {
            Xe moto = new Xe();int i=0;
            do {
                moto = new Xe();
                moto.setMaSP(c.getString(0));
                moto.setTenNCC(c.getString(6));
                moto.setTenSP(c.getString(1));
                System.out.println("here");
                System.out.println(moto.getMaSP());
                System.out.println(moto.getTenSP());
                dsXe.add(moto);

            } while (c.moveToNext());

        }
        return dsXe;
    }


    public void testShowKH(){
        for(int i=0;i<dsKh.size();i++){
            System.out.println("here");
            System.out.println(dsKh.get(i).getCmnd());
        }
    }

    public void testShowDDX(){
        System.out.println("show ddx");
        System.out.println(dsDDX.size());
        for(int i=0;i<dsDDX.size();i++){

            System.out.println(dsDDX.get(i).getTenSP());
        }
    }

    public void testShowDDH(){
        System.out.println("show ddh");
        System.out.println(dsDh.size());
        for(int i=0;i<dsDh.size();i++){

            System.out.println(dsDh.get(i).getCmnd());
        }
    }

}