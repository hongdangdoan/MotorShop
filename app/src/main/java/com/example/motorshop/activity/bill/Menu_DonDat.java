package com.example.motorshop.activity.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.motorshop.activity.R;

public class Menu_DonDat extends AppCompatActivity {

    Button btnHDX, btnHDPt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_don_dat);
        setControl();
        setEvent();
    }

    private void setControl() {
        btnHDX = (Button)findViewById(R.id.btnHDX);
        btnHDPt = (Button)findViewById(R.id.btnHDPT);

    }

    private void setEvent() {
        btnHDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_DonDat.this,QuanLy_DonDat.class);
                intent.putExtra("loai_DD","XE");
                startActivity(intent);
            }
        });
        btnHDPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_DonDat.this,QuanLy_DonDat.class);
                intent.putExtra("loai_DD","PT");
                startActivity(intent);
            }
        });
    }


}