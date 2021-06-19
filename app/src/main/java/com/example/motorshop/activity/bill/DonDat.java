package com.example.motorshop.activity.bill;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.BarringInfo;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.motorshop.activity.R;
import com.example.motorshop.datasrc.Accessory;
import com.example.motorshop.datasrc.Bill;
import com.example.motorshop.datasrc.BillDetail;
import com.example.motorshop.datasrc.Customer;
import com.example.motorshop.datasrc.Motor;
import com.example.motorshop.helper.Helper;
//import com.example.motorshop.datasrc.DonHang;
//import com.example.motorshop.datasrc.KhachHang;
//import com.example.motorshop.datasrc.PhuTung;
//import com.example.motorshop.datasrc.Xe;
//import com.example.motorshop.db.DBManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DonDat extends AppCompatActivity {

    TextView txtViewMaSP, txtViewTenSP;
    AutoCompleteTextView atcpChonSP;

    List<Motor> listMot = new ArrayList<Motor>();
    List<Accessory> listAc = new ArrayList<Accessory>();
    TableLayout tbLayout;
    Button btnXong, btnCapNhat, btnCheck;
    EditText edtTongTien, edtMaDDH, edtHoTen, edtCmnd, edtDiaChi, edtHoaDon_ThanhTien;
    List<HangHoa> dsHH = new ArrayList<HangHoa>();
    Helper helper = new Helper();
    String billType;
    String staffID = "NV01";
    String billM = "XE";
    String billA = "PT";
    int count = 0;
    int add = 0;

    boolean oldCtm = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dat);
        setControl();
        Intent inten = getIntent();
        billType = inten.getStringExtra("loai_DD");

        showProductList();

        setEvent();
    }
//
    public void setControl() {
        txtViewMaSP = (TextView) findViewById(R.id.txtViewHoaDon_Ma);
        txtViewTenSP = (TextView) findViewById(R.id.txtViewHoaDon_Ten);
        atcpChonSP = (AutoCompleteTextView) findViewById(R.id.autcplDanhSach);

        tbLayout = (TableLayout) findViewById(R.id.tblayoutBang);

        btnCheck = (Button) findViewById(R.id.btnHoaDon_Check);
        btnXong = (Button) findViewById(R.id.btnXong);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhap);

        edtMaDDH = (EditText) findViewById(R.id.edtTextHoaDon_MaHD);
        edtHoTen = (EditText) findViewById(R.id.edtTextHoaDon_HoTenKH);
        edtCmnd = (EditText) findViewById(R.id.edtTextHoaDon_CMNDKH);
        edtDiaChi = (EditText) findViewById(R.id.edtTextHoaDon_DiaChi);
        edtTongTien = (EditText) findViewById(R.id.edtHoaDon_ThanhTien);
        edtHoaDon_ThanhTien = (EditText) findViewById(R.id.edtHoaDon_ThanhTien);
    }
//
//
    public void setEvent() {
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                iniInfKH();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            int totalPrice;

            @Override
            public void onClick(View v) {

                Toast.makeText(DonDat.this, "Cap Nhat", Toast.LENGTH_SHORT).show();
                addProductTable();


            }
        });
        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = "NV01";
                String maDDH = edtMaDDH.getText().toString();
                String hoTen = edtHoTen.getText().toString();
                String cmnd = edtCmnd.getText().toString();
                String diaChi = edtDiaChi.getText().toString();
                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                String tmp[] = date.toString().split("-");
                String nd = tmp[0] + "/" + tmp[1] + "/" + tmp[2];
                BillDetail bd = new BillDetail();


                for (int i = 0; i < dsHH.size(); i++) {
                    bd = new BillDetail();
                    bd.setMotorId(dsHH.get(i).getMaSP());
                    bd.setPrice(dsHH.get(i).getDonGia());
                    bd.setAmount(dsHH.get(i).getSoLuong());
                    bd.setBillId(Integer.parseInt(maDDH));

                    //API
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.1.8:8080/api/motorshop/billDetails"
                            + bd.toString();
                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("That error POST data");
                            error.printStackTrace();
                        }
                    });
                    queue.add(request);
                }
            }
        });


    }


    public void addProductTable(){


            String product = getProductFromActv();

            //API
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.1.8:8080/api/motorshop/billDetails/listPrByName?name="
                    + product;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("Response is: "+ response.toString());
                            try {
                                JSONArray json = new JSONArray(response.toString());
                                System.out.println("Length");
                                System.out.println(json.length());
                                String[] tmp = response.toString().split(",");
                                String id = helper.deleteCharAtIndex(tmp[0],0);
                                id = helper.deleteCharAtIndex(id,0);
                                String name = helper.deleteCharAtIndex(tmp[1],0);
                                name = helper.deleteCharAtIndex(name,name.length()-1);
                                String amount = tmp[2];
                                String price = helper.deleteCharAtIndex(tmp[3],tmp[3].length()-1);
                                price = helper.deleteCharAtIndex(price,price.length()-1);

                                HangHoa hangHoa = new HangHoa(Integer.parseInt(id), name,
                                        Integer.parseInt(price), 1);
                                int cost = 0;
                                count++;
                                boolean addNew = true;
                                if (dsHH.size() <= 0) dsHH.add(hangHoa);
                                else {

                                    for (int i = 0; i < dsHH.size(); i++) {
                                        if (dsHH.get(i).getMaSP()==
                                                Integer.parseInt(id)) {
                                            dsHH.get(i).setSoLuong(dsHH.get(i).getSoLuong() + 1);
                                            addNew = false;

                                        }

                                    }
                                    if (addNew) dsHH.add(hangHoa);
                                }


                                for (int i = 0; i < dsHH.size(); i++) {

                                    TableRow tbRow = new TableRow(getApplicationContext());
                                    TextView txtvCode = new TextView(getApplicationContext());
                                    TextView txtvName = new TextView(getApplicationContext());
                                    TextView txtvPrice = new TextView(getApplicationContext());
                                    TextView txtvCount = new TextView(getApplicationContext());


                                    txtvCode.setText(String.valueOf(dsHH.get(i).getMaSP()));
                                    txtvName.setText(dsHH.get(i).getTenSP());
                                    txtvPrice.setText(String.valueOf(dsHH.get(i).getDonGia()));
                                    txtvCount.setText(String.valueOf(dsHH.get(i).getSoLuong()));


                                    tbRow.addView(txtvCode, 0);
                                    tbRow.addView(txtvName, 1);
                                    tbRow.addView(txtvCount, 2);
                                    tbRow.addView(txtvPrice, 3);
                                    cost += dsHH.get(i).getDonGia() * dsHH.get(i).getSoLuong();


                                    tbLayout.addView(tbRow, i + 1);

                                }

                                System.out.println(tbLayout.getChildCount());
                                if (tbLayout.getChildCount() > 2)
                                    for (int i = tbLayout.getChildCount()-1; i> dsHH.size(); i--) {
                                        tbLayout.removeViewAt(i);
                                    }

                                edtHoaDon_ThanhTien.setText(String.valueOf(cost));

                                //

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
            //

//

    }

    private void showProductList() {

            //API
            System.out.println("test api");
            List<Customer> listCtm = new ArrayList<Customer>();
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url ="http://192.168.1.8:8080/api/motorshop/billDetails/listPrname";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            System.out.println("Response is: "+ response.toString());
                            try {
                                JSONArray json = new JSONArray(response.toString());
                                System.out.println("Length");
                                System.out.println(json.length());
                                String[] tmp = new String[json.length()];
                                for(int i=0;i<json.length();i++){

                                    JSONArray object = json.getJSONArray(i);
                                    System.out.println("Show value api");
                                    System.out.println(object.toString());
                                    tmp[i] = helper.deleteCharAtIndex(object.toString(), 0);
                                    tmp[i] = helper.deleteCharAtIndex(tmp[i], 0);
                                    tmp[i] = helper.deleteCharAtIndex(tmp[i],
                                            tmp[i].length()-1);
                                    tmp[i] = helper.deleteCharAtIndex(tmp[i],
                                            tmp[i].length()-1);
                                    System.out.println("after");
                                        System.out.println(tmp[i]);

                                }
                                for(int i=0;i<tmp.length;i++) {
                                    System.out.println("check tmp");
                                    System.out.println(tmp[i].toString());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        getApplicationContext(),
                                        R.layout.custom_list_item, R.id.textView_listItem, tmp);
                                atcpChonSP.setAdapter(adapter);
                                atcpChonSP.setThreshold(1);

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
//
    private String getProductFromActv() {
        String name = atcpChonSP.getText().toString();
        return name;

    }
//
//    private PhuTung getPTFromActv() {
//        String name = atcpChonSP.getText().toString();
//
//
//        for (int i = 0; i < dsPT.size(); i++) {
//            if (dsPT.get(i).getTenSP().equals(name)) {
//                cart[cartIndex] = i;
//                cartIndex++;
//                System.out.println("chon phu tung");
//                System.out.println(dsPT.get(i).getDonGia());
//                return dsPT.get(i);
//            }
//        }
//        return null;
//
//
//    }
//
//    private void addMotoToTable() {
//
//        if(billType.equals(billM)){
//
//            Xe moto = new Xe();
//            moto = getMotoFromActv();
//            HangHoa hangHoa = new HangHoa(moto.getMaSP(), moto.getTenSP(),
//                    moto.getDonGia(), 1);
//            int cost = 0;
//            count++;
//            boolean addNew = true;
//            if (dsHH.size() <= 0) dsHH.add(hangHoa);
//            else {
//
//                for (int i = 0; i < dsHH.size(); i++) {
//                    if (dsHH.get(i).getMaSP().equals(moto.getMaSP())) {
//                        dsHH.get(i).setSoLuong(dsHH.get(i).getSoLuong() + 1);
//                        addNew = false;
//
//                    }
//
//                }
//                if (addNew) dsHH.add(hangHoa);
//            }
//
//
//            for (int i = 0; i < dsHH.size(); i++) {
//
//                TableRow tbRow = new TableRow(getApplicationContext());
//                TextView txtvCode = new TextView(getApplicationContext());
//                TextView txtvName = new TextView(getApplicationContext());
//                TextView txtvPrice = new TextView(getApplicationContext());
//                TextView txtvCount = new TextView(getApplicationContext());
//
//
//                txtvCode.setText(dsHH.get(i).getMaSP());
//                txtvName.setText(dsHH.get(i).getTenSP());
//                txtvPrice.setText(String.valueOf(dsHH.get(i).getDonGia()));
//                txtvCount.setText(String.valueOf(dsHH.get(i).getSoLuong()));
//
//
//                tbRow.addView(txtvCode, 0);
//                tbRow.addView(txtvName, 1);
//                tbRow.addView(txtvCount, 2);
//                tbRow.addView(txtvPrice, 3);
//                cost += dsHH.get(i).getDonGia() * dsHH.get(i).getSoLuong();
//
//
//                tbLayout.addView(tbRow, i + 1);
//
//            }
//
//            System.out.println(tbLayout.getChildCount());
//            if (tbLayout.getChildCount() > 2)
//                for (int i = tbLayout.getChildCount()-1; i> dsHH.size(); i--) {
//                    tbLayout.removeViewAt(i);
//                }
//
//            edtHoaDon_ThanhTien.setText(String.valueOf(cost));
//
//        }if(billType.equals(billA)){
//            PhuTung pt = new PhuTung();
//            pt = getPTFromActv();
//            HangHoa hangHoa = new HangHoa(pt.getMaSP(), pt.getTenSP(),
//                    pt.getDonGia(), 1);
//            int cost = 0;
//            count++;
//            boolean addNew = true;
//            if (dsHH.size() <= 0) dsHH.add(hangHoa);
//            else {
//
//                for (int i = 0; i < dsHH.size(); i++) {
//                    if (dsHH.get(i).getMaSP().equals(pt.getMaSP())) {
//                        dsHH.get(i).setSoLuong(dsHH.get(i).getSoLuong() + 1);
//                        addNew = false;
//
//                    }
//
//                }
//                if (addNew) dsHH.add(hangHoa);
//            }
//
//
//            for (int i = 0; i < dsHH.size(); i++) {
//
//                TableRow tbRow = new TableRow(getApplicationContext());
//                TextView txtvCode = new TextView(getApplicationContext());
//                TextView txtvName = new TextView(getApplicationContext());
//                TextView txtvPrice = new TextView(getApplicationContext());
//                TextView txtvCount = new TextView(getApplicationContext());
//
//
//                txtvCode.setText(dsHH.get(i).getMaSP());
//                txtvName.setText(dsHH.get(i).getTenSP());
//                txtvPrice.setText(String.valueOf(dsHH.get(i).getDonGia()));
//                txtvCount.setText(String.valueOf(dsHH.get(i).getSoLuong()));
//
//
//                tbRow.addView(txtvCode, 0);
//                tbRow.addView(txtvName, 1);
//                tbRow.addView(txtvCount, 2);
//                tbRow.addView(txtvPrice, 3);
//                cost += dsHH.get(i).getDonGia() * dsHH.get(i).getSoLuong();
//
//
//                tbLayout.addView(tbRow, i + 1);
//
//            }
//
//            System.out.println(tbLayout.getChildCount());
//            if (tbLayout.getChildCount() > 2)
//                for (int i = tbLayout.getChildCount()-1; i> dsHH.size(); i--) {
//                    tbLayout.removeViewAt(i);
//                }
//
//            edtHoaDon_ThanhTien.setText(String.valueOf(cost));
//
//        }
//
//
//    }
//
//    public void giamSLXe(String maSP) {
//
//        for (int i = 0; i < dsXe.size(); i++) {
//            if (dsXe.get(i).getMaSP().equals(maSP)) {
//
//                dsXe.get(i).setSoLuong(dsXe.get(i).getSoLuong() - 1);
//                dbR.updateSLXe(dsXe.get(i).getMaSP(), dsXe.get(i).getSoLuong());
//                System.out.println(dsXe.get(i).getSoLuong() - 1);
//            }
//        }
//    }
//
//    public void giamSLPT(String maSP) {
//
//        for (int i = 0; i < dsPT.size(); i++) {
//            if (dsPT.get(i).getMaSP().equals(maSP)) {
//
//                dsPT.get(i).setSoLuong(dsPT.get(i).getSoLuong() - 1);
//                dbR.updateSLPT(dsPT.get(i).getMaSP(), dsPT.get(i).getSoLuong());
//                System.out.println(dsPT.get(i).getSoLuong() - 1);
//            }
//        }
//    }


}