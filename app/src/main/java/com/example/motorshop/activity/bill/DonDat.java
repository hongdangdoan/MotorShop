package com.example.motorshop.activity.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.motorshop.activity.R;

public class DonDat extends AppCompatActivity {

    TextView txtViewMaSP,txtViewTenSP;
    AutoCompleteTextView atcpChonSP;

    String billType;
    String billM = "XE";
    String billA = "PT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dat);
        setControl();
        Intent inten = getIntent();
        billType = inten.getStringExtra("loai_DD");
        init_DonDat(billType);
    }

    public void setControl(){
        txtViewMaSP = (TextView)findViewById(R.id.txtViewHoaDon_Ma);
        txtViewTenSP = (TextView)findViewById(R.id.txtViewHoaDon_Ten);
        atcpChonSP = (AutoCompleteTextView)findViewById(R.id.autcplDanhSach);
    }

    public void init_DonDat(String billType){
        if(billType.equals(billM))init_DonDat_Xe();
        if(billType.equals(billA))init_DonDat_PT();
    }

    public void init_DonDat_Xe(){

        txtViewMaSP.setText("Mã Xe");
        txtViewTenSP.setText("Tên Xe");
        atcpChonSP.setHint("Chọn xe");

    }

    public void init_DonDat_PT(){

        txtViewMaSP.setText("Mã phụ tùng");
        txtViewTenSP.setText("Tên phụ tùng");
        atcpChonSP.setHint("Chọn phụ tùng");

    }
}