package com.example.motorshop.activity.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.motorshop.activity.R;
import com.example.motorshop.datasrc.BillDetail;
import com.example.motorshop.datasrc.Accessory;
import com.example.motorshop.datasrc.Customer;
import com.example.motorshop.datasrc.Motor;
import com.example.motorshop.helper.Helper;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonDat extends AppCompatActivity {

    String billM = "XE";
    String billA = "PT";

    String billType;
    int billId;
    TableLayout tblayoutBang;
    TextView tvHD;
    List<BillDetail> dsDDX = new ArrayList<BillDetail>();
    List<Motor> dsXe = new ArrayList<Motor>();
    List<Accessory> dsPT = new ArrayList<Accessory>();
    List<BillDetail> dsDDPT = new ArrayList<BillDetail>();
    Helper helper = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_dat);

        setControl();
        Intent inten = getIntent();
        String tmp = inten.getStringExtra("BillId").toString();

        billId = Integer.parseInt(tmp);
        tvHD.setText("CHI TIẾT HOÁ ĐƠN " + billId);
        showBillDetail();
    }

    private void setControl() {
        tblayoutBang = (TableLayout) findViewById(R.id.tblayoutBangCT);
        tvHD = (TextView) findViewById(R.id.tvHD);
    }



    public void showBillDetail(){
        System.out.println("test api");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.8:8080/api/motorshop/billDetails/getDetailOfAnOrder?billId="+billId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println("Response is: "+ response.toString());
                        try {
                            JSONArray json = new JSONArray(response.toString());
                            System.out.println("Length");
                            System.out.println(json.length());
                            for(int i=0;i<json.length();i++){
                                TableRow tbRow = null;
                                TextView txtId = null;
                                TextView txtAmount = null;
                                TextView txtName = null;
                                TextView txtPrice = null;
                                tbRow = new TableRow(getApplicationContext());
                                JSONArray object = json.getJSONArray(i);
                                System.out.println("Show value api");
                                System.out.println(object.toString());
                                String[] tmp = object.toString().split(",");
                                for(int j=0;j<tmp.length;j++) {


                                    if(j == 1) {

                                        txtId = new TextView(getApplicationContext());

                                        txtId.setText(tmp[j].toString());
                                        tbRow.addView(txtId, 0);
                                    }
                                    if(j == 2) {

                                        if(!tmp[j].isEmpty()){
                                            txtName = new TextView(getApplicationContext());
                                            tmp[j] = helper.deleteCharAtIndex(tmp[j], 0);
                                            tmp[j] = helper.deleteCharAtIndex(tmp[j], tmp[j].length() - 1);
                                            if(tmp[j].length()>9){

                                            }

                                            txtName.setText(tmp[j].toString());
                                            tbRow.addView(txtName, 1);
                                        }

                                    }
                                    if(j == 3) {
                                        txtAmount = new TextView(getApplicationContext());
//                                        tmp[j] = helper.deleteCharAtIndex(tmp[j], tmp[j].length() - 1);
                                        txtAmount.setText(tmp[j].toString());
                                        tbRow.addView(txtAmount, 2);
                                    }
                                    if(j==4) {
                                        txtPrice = new TextView(getApplicationContext());
                                        tmp[j] = helper.deleteCharAtIndex(tmp[j], tmp[j].length() - 1);
                                        txtPrice.setText(tmp[j].toString());
                                        tbRow.addView(txtPrice, 3);
                                    }


                                }
                                tbRow.setId(i);
                                tblayoutBang.addView(tbRow);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That error");
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);

    }
}