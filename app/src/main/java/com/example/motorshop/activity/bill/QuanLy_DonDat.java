package com.example.motorshop.activity.bill;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.motorshop.datasrc.Bill;
import com.example.motorshop.datasrc.BillDetail;
import com.example.motorshop.datasrc.Customer;
import com.example.motorshop.datasrc.Motor;
import com.example.motorshop.datasrc.Depart;
import com.example.motorshop.datasrc.Motor;
import com.example.motorshop.helper.Helper;
import com.example.motorshop.helper.Icon_Manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuanLy_DonDat extends AppCompatActivity {


    TextView tvLoc, tvThemDD, tvCTDD;
    CheckBox cbTTTang, cbTTGiam, cbNgay;
    TableLayout tableLayout;
    TableRow tableRow;
    EditText edtMaDDH;

    String billType;
    String billM = "XE";
    String billA = "PT";

    List<Motor> dsXe = new ArrayList<Motor>();
    List<HoaDon> dsHD = new ArrayList<HoaDon>();
    List<Bill> dsDh = new ArrayList<Bill>();
    List<Customer> dsKh = new ArrayList<Customer>();
    List<BillDetail> dsDDX = new ArrayList<BillDetail>();
    List<BillDetail> dsDDPT = new ArrayList<BillDetail>();
    Helper helper = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_dat);
        setControl();

        tvLoc.setTypeface(Icon_Manager.get_icons("fonts/fa-solid-900.ttf", getApplicationContext()));
        tvThemDD.setTypeface(Icon_Manager.get_icons("fonts/fa-solid-900.ttf", getApplicationContext()));
        tvCTDD.setTypeface(Icon_Manager.get_icons("fonts/fa-solid-900.ttf", getApplicationContext()));
//        init_DonDat(billType);

        showTheOrderList();
//        loadDSXeFromDB();

        setEven();

    }

    private void setControl() {

        tvThemDD = (TextView) findViewById(R.id.tvThemDD);
        tvCTDD = (TextView) findViewById(R.id.tvCTDD);
        tvLoc = (TextView) findViewById(R.id.qlddLoc);
        tableLayout = (TableLayout) findViewById(R.id.tblayoutBang);

        cbTTTang = (CheckBox) findViewById(R.id.chbTongTienTangDan);
        cbTTGiam = (CheckBox) findViewById(R.id.chbTongTienGiamDan);
        cbNgay = (CheckBox) findViewById(R.id.chbDonTrongNgay);
        edtMaDDH = (EditText) findViewById(R.id.edtTextnhapMaDDH);
    }

    private void setEven() {

        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clicked = v.getId();
                System.out.println("click");
                System.out.println(clicked);
            }
        });


        tvLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbNgay.isChecked()) {
                    for (int i = tableLayout.getChildCount() - 1; i > 0; i--) {
                        tableLayout.removeViewAt(i);
                    }
                    filterByDay();
//                    showDDXList();
//                    init_DonDat(billType);
                }
                if (cbTTTang.isChecked()) {

                    for (int i = tableLayout.getChildCount() - 1; i > 0; i--) {
                        tableLayout.removeViewAt(i);
                    }
                    sortMoneyAscending();
                    showTheOrderList();
                    System.out.println("Sap xep tang dan");
                }

                if (cbTTGiam.isChecked()) {


                    for (int i = tableLayout.getChildCount() - 1; i > 0; i--) {
                        tableLayout.removeViewAt(i);
                    }
                    sortMoneyDecrease();
                    showTheOrderList();
                    System.out.println("Sap xep giam dan");
                }
                if (!edtMaDDH.getText().toString().equals("")) {
                    for (int i = tableLayout.getChildCount() - 1; i > 0; i--) {
                        tableLayout.removeViewAt(i);
                    }
                    filterTheOrder();
                } else {
                    for (int i = tableLayout.getChildCount() - 1; i > 0; i--) {
                        tableLayout.removeViewAt(i);
                    }
                    showTheOrderList();
                }

            }
        });

        tvThemDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLy_DonDat.this, DonDat.class);
                intent.putExtra("loai_DD", billType);
                startActivity(intent);
            }
        });
        tvCTDD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String maHD = edtMaDDH.getText().toString();
                String tmp = billType.concat("@");
                tmp = tmp.concat(maHD);
                Intent intent = new Intent(QuanLy_DonDat.this, ChiTietDonDat.class);
                intent.putExtra("loai_DD", tmp);
                startActivity(intent);
            }
        });
    }

    public void sortMoneyAscending() {
        int n = dsHD.size();
        for (int i = 0; i < n - 1; i++) {

            HoaDon temp;
            for (int j = 0; j < n - i - 1; j++)
                if (dsHD.get(j).getTongTien() > dsHD.get(j + 1).getTongTien()) {
                    temp = new HoaDon();
                    temp.setMaHD(dsHD.get(j).getMaHD());
                    temp.setTongTien(dsHD.get(j).getTongTien());
                    dsHD.get(j).setMaHD(dsHD.get(j + 1).getMaHD());
                    dsHD.get(j).setTongTien(dsHD.get(j + 1).getTongTien());
                    dsHD.get(j + 1).setMaHD(temp.getMaHD());
                    dsHD.get(j + 1).setTongTien(temp.getTongTien());
                }
        }
    }

    public void sortMoneyDecrease() {
        int n = dsHD.size();
        HoaDon temp;
        for (int i = 0; i < n - 1; i++)
            for (int j = i + 1; j < n; j++)
                if (dsHD.get(i).getTongTien() < dsHD.get(j).getTongTien()) {
                    temp = new HoaDon();
                    temp.setMaHD(dsHD.get(j).getMaHD());
                    temp.setTongTien(dsHD.get(j).getTongTien());
                    dsHD.get(j).setMaHD(dsHD.get(i).getMaHD());
                    dsHD.get(j).setTongTien(dsHD.get(i).getTongTien());
                    dsHD.get(i).setMaHD(temp.getMaHD());
                    dsHD.get(i).setTongTien(temp.getTongTien());
                }
    }

    public void init_DonDat(String billType) {

        dsDh = loadBillList();
//        dsKh = loadCustomerList();
        if (billType.equals(billM)) init_DonDat_Xe();
        if (billType.equals(billA)) init_DonDat_PT();
    }

    public void init_DonDat_Xe() {

        if (billType.equals(billM)) dsDDX = loadBillDetailList();
//        addInfoToDDX();
        initDSHD();


//        btnThemDD.setText("Thêm DDX");
//        btnCTDD.setText("CT DDX");

    }

    public void initDSHD() {
        System.out.println("check init DSHD");
        System.out.println(dsDDX.size());
        int index = 0;
        if (billType.equals(billM)) {

            for (int i = 0; i < dsDDX.size(); i++) {
                if (i == 0) {
                    dsHD.add(new HoaDon(dsDDX.get(i).getBillId(),
                            dsDDX.get(i).getPrice()));


                } else {
                    if (dsHD.get(index).getMaHD()==(dsDDX.get(i).getBillId())) ;
                    else {

                        index++;
                        dsHD.add(new HoaDon(dsDDX.get(i).getBillId(),
                                dsDDX.get(i).getPrice()));
                    }

                }

            }
        }
        if (billType.equals(billA)) {
            for (int i = 0; i < dsDDPT.size(); i++) {
                if (i == 0) {
                    dsHD.add(new HoaDon(dsDDPT.get(i).getBillId(),
                            dsDDX.get(i).getPrice()));


                } else {
                    if (dsHD.get(index).getMaHD()==(dsDDPT.get(i).getBillId())) ;
                    else {

                        index++;
                        dsHD.add(new HoaDon(dsDDPT.get(i).getBillId(),
                                dsDDX.get(i).getPrice()));
                    }

                }

            }
        }

    }

//    public void addInfoToDDX() {
//        if (billType.equals(billM)) {
//
//            for (int i = 0; i < dsDDX.size(); i++) {
//                dsDDX.get(i).setCmnd(findDHByMADH(dsDDX.get(i).getMaDH()).getCmnd());
//                dsDDX.get(i).setNgayDat(findDHByMADH(dsDDX.get(i).getMaDH()).getNgayDat());
//                dsDDX.get(i).setTenNV(findDHByMADH(dsDDX.get(i).getMaDH()).getTenNV());
//
//            }
//        }
//        if (billType.equals(billA)) {
//            for (int i = 0; i < dsDDPT.size(); i++) {
//                dsDDPT.get(i).setCmnd(findDHByMADH(dsDDPT.get(i).getMaDH()).getCmnd());
//                dsDDPT.get(i).setNgayDat(findDHByMADH(dsDDPT.get(i).getMaDH()).getNgayDat());
//                dsDDPT.get(i).setTenNV(findDHByMADH(dsDDPT.get(i).getMaDH()).getTenNV());
//
//            }
//        }
//
//    }


    public void init_DonDat_PT() {

        /*dsDDPT = dbR.loadDSDDPT();*/

//        addInfoToDDX();
        initDSHD();
//        btnThemDD.setText("Thêm DDPT");
//        btnCTDD.setText("CT DDPT");

    }

    private Customer findKHByBillID(int billID) {
        if (dsKh.size() == 0) {

            return null;
        } else {
            for(int k=0;k<dsDh.size();k++){
                if(dsDh.get(k).getId()==(billID)){
                    for (int i = 0; i < dsKh.size(); i++) {
                        if (dsKh.get(i).getId()==dsDh.get(k).getCustomerId()) {
                            return dsKh.get(i);
                        }
                }
            }

            }
        }

        return null;
    }

    private Bill findDHByMADH(int maDH) {
        if (dsDh.size() == 0) {

            return null;
        } else {
            for (int i = 0; i < dsDh.size(); i++) {
                if (dsDh.get(i).getId()==(maDH)) {
                    return dsDh.get(i);
                }
            }

        }

        return null;

    }

    private void TestShowTheOrderList() {
        System.out.println("check bill");
        System.out.println(billType);
        System.out.println(dsHD.size());


            //
            List<Bill> listBill = new ArrayList<Bill>();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.1.8:8080/api/motorshop/bills";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Response is: " + response.toString());
                            try {
                                JSONArray json = new JSONArray(response.toString());
                                System.out.println("Length");
                                System.out.println(json.length());
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject object = json.getJSONObject(i);
                                    System.out.println("Show value");
                                    System.out.println(object.get("id").toString());
                                    Bill bill = new Bill();
                                    bill.setCreatedDate(object.get("createdDate").toString());
                                    bill.setCustomerId(object.get("customerId").toString());
                                    bill.setId(Integer.parseInt(object.get("id").toString()));
                                    bill.setStaffId(object.get("staffId").toString());
                                    dsDh.add(bill);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            //Customer

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            String url ="http://192.168.1.8:8080/api/motorshop/customers";
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
                                                    JSONObject object = json.getJSONObject(i);
                                                    System.out.println("Show value");
                                                    System.out.println(object.get("id").toString());
                                                    Customer ctm = new Customer();
                                                    ctm.setCreatedDate(object.get("createdDate").toString());
                                                    ctm.setIdentityId(object.get("identityId").toString());
                                                    ctm.setId(object.get("id").toString());
                                                    ctm.setAddress(object.get("address").toString());
                                                    ctm.setPhone(object.get("phone").toString());
                                                    ctm.setName(object.get("name").toString());
                                                    ctm.setPassWord(object.get("passWord").toString());
                                                    dsKh.add(ctm);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            //BillDetail
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            String url ="http://192.168.1.8:8080/api/motorshop/billDetails";
                                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {

                                                            try {
                                                                JSONArray json = new JSONArray(response.toString());
                                                                System.out.println("Length");
                                                                System.out.println(json.length());
                                                                for(int i=0;i<json.length();i++){

                                                                    JSONObject object = json.getJSONObject(i);
                                                                    System.out.println("Show value billDetails");
                                                                    System.out.println(object.get("id").toString());
                                                                    BillDetail bilD = new BillDetail();
                                                                    bilD.setBillId(Integer.parseInt(object.get("billId").toString()));
                                                                    bilD.setId(Integer.parseInt(object.get("id").toString()));
                                                                    String motoId = object.get("motorId").toString();
                                                                    if(!motoId.equals("null"))bilD.setMotorId(Integer.
                                                                            parseInt(motoId));

                                                                    String acId = object.get("accessoryId").toString();
                                                                    if(!acId.equals("null"))bilD.setAccessoryId(Integer.
                                                                            parseInt(acId));

                                                                    bilD.setAmount(Integer.parseInt(object.get("amount").
                                                                            toString()));
                                                                    bilD.setPrice(Integer.parseInt(object.get("price").toString()));
                                                                    dsDDX.add(bilD);

                                                                }


                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
//process
                                                            TableRow tbRow = null;
                                                            for (int i = 0; i < dsDDX.size(); i++) {
                                                                int maHD = dsDDX.get(i).getId();
                                                                TextView txtMa = new TextView(getApplicationContext());
                                                                txtMa.setText(String.valueOf(maHD));
                                                                TextView txtND = new TextView(getApplicationContext());
                                                                TextView txtHoTen = new TextView(getApplicationContext());
                                                                TextView txtTongTien = new TextView(getApplicationContext());
                                                                txtTongTien.setText(String.valueOf(dsDDX.get(i).getPrice()));
                                                                txtMa.setText(String.valueOf(maHD));
                                                                //get createDate and customerName
                                                                for(int j=0;j<dsDDX.size();j++){
                                                                    if(dsDh.get(j).getId()==dsDDX.get(j).getBillId()){
                                                                        txtND.setText(String.valueOf(dsDh.get(j).getCreatedDate()));
                                                                        for(int k=0;k<dsDh.size();k++){
                                                                            if(dsDh.get(j).getCustomerId()==dsKh.get(k).getId()){
                                                                                txtHoTen.setText(dsKh.get(k).getName().toString());
                                                                                break;
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }

                                                                tbRow = new TableRow(getApplicationContext());
                                                                tbRow.setId(i);
                                                                tbRow.addView(txtMa, 0);
                                                                tbRow.addView(txtND, 1);
                                                                tbRow.addView(txtHoTen, 2);
                                                                tbRow.addView(txtTongTien);
                                                                tbRow.setOnClickListener((new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        int clicked = v.getId();
                                                                        int maHD = dsHD.get(clicked).getMaHD();
                                                                        String maHDtmp = String.valueOf(maHD);
                                                                        String tmp = billType.concat("@");
                                                                        tmp = tmp.concat(maHDtmp);
                                                                        Intent intent = new Intent(QuanLy_DonDat.this, ChiTietDonDat.class);
                                                                        intent.putExtra("loai_DD", tmp);
                                                                        startActivity(intent);

                                                                    }
                                                                }));
                                                                tableLayout.addView(tbRow);
                                                            }


                                                        }

                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    System.out.println("That error in billDetails");
                                                    error.printStackTrace();
                                                }
                                            });


                                            queue.add(stringRequest);
                                            //
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("That error");
                                    error.printStackTrace();
                                }
                            });
                            queue.add(stringRequest);
                            //



                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That error");
                    error.printStackTrace();
                }
            });
            queue.add(stringRequest);

            //

        }


    public int totalPriceOfAOrder(int maHD) {
        int tongTien = 0;
        if (billType.equals(billM)) {

            for (int i = 0; i < dsDDX.size(); i++) {
                if (dsDDX.get(i).getBillId().equals(maHD)) {
                    tongTien += dsDDX.get(i).getPrice() * dsDDX.get(i).getAmount();
                }

            }
        }
        if (billType.equals(billA)) {
            for (int i = 0; i < dsDDPT.size(); i++) {
                if (dsDDPT.get(i).getBillId().equals(maHD)) {
                    tongTien += dsDDPT.get(i).getAmount() * dsDDPT.get(i).getAmount();
                }

            }
        }

        return tongTien;
    }




    public void filterTheOrder() {

        String maHD = edtMaDDH.getText().toString();

        List<HoaDon> tmp1 = new ArrayList<HoaDon>();
        List<HoaDon> tmp2 = new ArrayList<HoaDon>();
        tmp2 = dsHD;
        for (int i = 0; i < dsHD.size(); i++) {
            if (String.valueOf(dsHD.get(i).getMaHD()).equals(maHD)) {
                tmp1.add(dsHD.get(i));
                break;

            }
        }
        dsHD = tmp1;
        showTheOrderList();
        dsHD = tmp2;

    }

    public String getCurrentDate() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        String tmp[] = date.toString().split("-");
        String nd = tmp[0] + "/" + tmp[1] + "/" + tmp[2];
        return nd.toString();
    }

    public void filterByDay() {
        String nd = getCurrentDate();
        String tmp[] = nd.split("/");
        String ngay = tmp[2];
        List<Bill> ltmp = new ArrayList<Bill>();
        for (int i = 0; i < dsDh.size(); i++) {
            String ndd = dsDh.get(i).getCreatedDate().toString();
            String str[] = ndd.split("/");

            if (str[2].equals(ngay)) {
                ltmp.add(dsDh.get(i));
            }
        }
        System.out.println("kiem tra");

        List<BillDetail> dsDDXT = new ArrayList<BillDetail>();
        for (int i = 0; i < ltmp.size(); i++) {
            for (int j = 0; j < dsDDX.size(); j++) {
                if (ltmp.get(i).getId()==(dsDDX.get(j).getBillId())) {
                    dsDDXT.add(dsDDX.get(j));
                }
            }

        }

        List<BillDetail> l = new ArrayList<BillDetail>();
        l = dsDDX;
        dsDDX = new ArrayList<BillDetail>();
        dsDDX = dsDDXT;
        initDSHD();
        showTheOrderList();
        dsDDX = l;
//        init_DonDat(billType);


    }

    public List<Bill> loadBillList(){
        List<Bill> listBill = new ArrayList<Bill>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.8:8080/api/motorshop/bills";
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
                                JSONObject object = json.getJSONObject(i);
                                System.out.println("Show value");
                                System.out.println(object.get("id").toString());
                                Bill bill = new Bill();
                                bill.setCreatedDate(object.get("createdDate").toString());
                                bill.setCustomerId(object.get("customerId").toString());
                                bill.setId(Integer.parseInt(object.get("id").toString()));
                                bill.setStaffId(object.get("staffId").toString());
                                listBill.add(bill);
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
        return listBill;
    }



    public List<BillDetail> loadBillDetailList(){
       final List<BillDetail> listBD = new ArrayList<BillDetail>();
        List<BillDetail> tmp = new ArrayList<BillDetail>();

        final String[] res = new String[1];
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.8:8080/api/motorshop/billDetails";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        res[0] = String.valueOf(response.toString());
                        System.out.println("Response is: "+ response.toString());
                        try {
                            JSONArray json = new JSONArray(response.toString());
                            System.out.println("Length");
                            System.out.println(json.length());
                            for(int i=0;i<json.length();i++){

                                JSONObject object = json.getJSONObject(i);
                                System.out.println("Show value billDetails");
                                System.out.println(object.get("id").toString());
                                BillDetail bilD = new BillDetail();
                                bilD.setBillId(Integer.parseInt(object.get("billId").toString()));
                                bilD.setId(Integer.parseInt(object.get("id").toString()));
                                String motoId = object.get("motorId").toString();
                                if(!motoId.equals("null"))bilD.setMotorId(Integer.
                                        parseInt(motoId));

                                String acId = object.get("accessoryId").toString();
                                if(!acId.equals("null"))bilD.setAccessoryId(Integer.
                                        parseInt(acId));

                                bilD.setAmount(Integer.parseInt(object.get("amount").
                                        toString()));
                                bilD.setPrice(Integer.parseInt(object.get("price").toString()));
                                listBD.add(bilD);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That error in billDetails");
                error.printStackTrace();
            }
        });


        queue.add(stringRequest);
        return listBD;
    }
    public void showTheOrderList(){
        System.out.println("test api");
        List<Customer> listCtm = new ArrayList<Customer>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.8:8080/api/motorshop/billDetails/getOrderDetail";
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
                                TextView txtMa = null;
                                TextView txtND = null;
                                TextView txtHoTen = null;
                                TextView txtTongTien = null;
                                tbRow = new TableRow(getApplicationContext());
                                JSONArray object = json.getJSONArray(i);
                                System.out.println("Show value api");
                                System.out.println(object.toString());
                                String[] tmp = object.toString().split(",");
                                for(int j=0;j<tmp.length;j++) {

                                    if (j == 0) {

                                        txtMa = new TextView(getApplicationContext());
                                        tmp[j] = helper.deleteCharAtIndex(tmp[j].toString(), 0);
                                        txtMa.setText(tmp[j].toString());
                                        tbRow.addView(txtMa, 0);
                                    }
                                    if (j == 1) {

                                        txtND = new TextView(getApplicationContext());
                                        tmp[j] = helper.deleteCharAtIndex(tmp[j], 0);
                                        tmp[j] = helper.formatDate(tmp[j]);
                                        txtND.setText(tmp[j].toString());
                                        tbRow.addView(txtND, 1);
                                    }
                                    if (j == 2) {

                                        txtHoTen = new TextView(getApplicationContext());
                                        tmp[j] = helper.deleteCharAtIndex(tmp[j], 0);
                                        tmp[j] = helper.deleteCharAtIndex(tmp[j], tmp[j].length() - 1);
                                        txtHoTen.setText(tmp[j].toString());
                                        tbRow.addView(txtHoTen, 2);
                                    }
                                    if (j == 3) {

                                        txtTongTien = new TextView(getApplicationContext());
                                        tmp[j] = helper.deleteCharAtIndex(tmp[j], tmp[j].length() - 1);
                                        txtTongTien.setText(tmp[j].toString());
                                        tbRow.addView(txtTongTien, 3);
                                    }
                                }
                                    //


                                    tbRow.setId(i);
                                    tableLayout.addView(tbRow);
                                    tbRow.setOnClickListener((new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int clicked = v.getId();
                                            JSONArray object = null;
                                            try {
                                                object = json.getJSONArray(clicked);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            String[] str = object.toString().split(",");
                                            int maHD = Integer.parseInt(helper.deleteCharAtIndex(str[0],0));
                                            String maHDtmp = String.valueOf(maHD);
//                                            String tmp = billType.concat("@");
//                                            tmp = tmp.concat(maHDtmp);
                                            Intent intent = new Intent(QuanLy_DonDat.this, ChiTietDonDat.class);
                                            intent.putExtra("BillId", maHDtmp);
                                            startActivity(intent);

                                        }
                                    }));

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