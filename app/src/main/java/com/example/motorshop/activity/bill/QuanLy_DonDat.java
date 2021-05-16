package com.example.motorshop.activity.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.motorshop.activity.R;

public class QuanLy_DonDat extends AppCompatActivity {

    Button btnThemDD,btnCTDD;
    String billType;
    String billM = "XE";
    String billA = "PT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_dat);
        setControl();
        Intent inten = getIntent();
        billType = inten.getStringExtra("loai_DD");
        init_DonDat(billType);

        setEven();
    }

    private void setControl(){

        btnThemDD = (Button)findViewById(R.id.btnThemDD);
        btnCTDD = (Button)findViewById(R.id.btnCTDD);
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

        btnThemDD.setText("Thêm DDX");
        btnCTDD.setText("CT DDX");

    }

    public void init_DonDat_PT(){

        btnThemDD.setText("Thêm DDPT");
        btnCTDD.setText("CT DDPT");

    }
}