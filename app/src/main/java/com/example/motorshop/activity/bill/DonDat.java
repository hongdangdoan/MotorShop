package com.example.motorshop.activity.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motorshop.activity.R;
import com.example.motorshop.datasrc.DonHang;
import com.example.motorshop.datasrc.KhachHang;
import com.example.motorshop.datasrc.Xe;
import com.example.motorshop.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DonDat extends AppCompatActivity {

    TextView txtViewMaSP, txtViewTenSP;
    AutoCompleteTextView atcpChonSP;

    TableLayout tbLayout;
    Button btnXong, btnCapNhat, btnCheck;
    EditText edtTongTien, edtMaDDH, edtHoTen, edtCmnd, edtDiaChi, edtHoaDon_ThanhTien;

    String billType;
    String billM = "XE";
    String billA = "PT";

    List<Xe> dsXe = new ArrayList<Xe>();
    List<KhachHang> dsKH = new ArrayList<KhachHang>();
    DBManager dbR = new DBManager(this);
    int cart[] = new int[10];
    int cartIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_dat);
        setControl();
        Intent inten = getIntent();
        billType = inten.getStringExtra("loai_DD");
        init_DonDat(billType);
        showMotoList();
        initDDH();
        setEvent();
    }

    public void setControl() {
        txtViewMaSP = (TextView) findViewById(R.id.txtViewHoaDon_Ma);
        txtViewTenSP = (TextView) findViewById(R.id.txtViewHoaDon_Ten);
        atcpChonSP = (AutoCompleteTextView) findViewById(R.id.autcplDanhSach);

        tbLayout = (TableLayout) findViewById(R.id.tblayoutBang);

        btnCheck = (Button)findViewById(R.id.btnHoaDon_Check);
        btnXong = (Button) findViewById(R.id.btnXong);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhap);

        edtMaDDH = (EditText) findViewById(R.id.edtTextHoaDon_MaHD);
        edtHoTen = (EditText) findViewById(R.id.edtTextHoaDon_HoTenKH);
        edtCmnd = (EditText) findViewById(R.id.edtTextHoaDon_CMNDKH);
        edtDiaChi = (EditText) findViewById(R.id.edtTextHoaDon_DiaChi);
        edtTongTien = (EditText) findViewById(R.id.edtHoaDon_ThanhTien);
        edtHoaDon_ThanhTien = (EditText) findViewById(R.id.edtHoaDon_ThanhTien);
    }


    public void setEvent() {
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniInfKH();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            int totalPrice;

            @Override
            public void onClick(View v) {

                Toast.makeText(DonDat.this, "Cap Nhat", Toast.LENGTH_SHORT).show();
                addMotoToTable();
                for (int i = 0; i < cartIndex; i++) {
                    totalPrice += dsXe.get(cart[i]).getDonGia();
                }

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

                dbR.insertCTM(cmnd, hoTen, diaChi, "");
                dbR.insertDH(maDDH, "", cmnd, maNV);

                for (int i = 0; i < cartIndex; i++) {
                    int sl = 1;
                    dbR.insertCTDDX(maDDH, dsXe.get(cart[i]).getMaSP(),
                            String.valueOf(sl), String.valueOf(dsXe.get(cart[i]).getDonGia()));
                    giamSLXe(dsXe.get(cart[i]).getMaSP());

                }

                Toast.makeText(DonDat.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDDH() {

        String maDDH = "HD" + String.valueOf(countDDH() + 1);
        edtMaDDH.setText(maDDH);

    }

    private int countDDH() {

        List<DonHang> listDDH = new ArrayList<DonHang>();
        SQLiteDatabase db = new DBManager(getApplicationContext()).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM DONDATHANG ", null);

        if (c.moveToFirst()) {
            DonHang ddh = new DonHang();
            int i = 0;
            do {
                ddh = new DonHang();
                ddh.setMaDH(c.getString(0));
                ddh.setNgayDat(c.getString(1));
                ddh.setCmnd(c.getString(2));
                ddh.setTenNV(c.getString(3));
                listDDH.add(ddh);

            } while (c.moveToNext());

        }
        if (listDDH.size() == 0) return -1;
        else {
            String[] stt = listDDH.get(listDDH.size()-1).getMaDH().split("D");
            return Integer.parseInt(stt[1].toString());
        }
    }

    public void init_DonDat(String billType) {
        if (billType.equals(billM)) init_DonDat_Xe();
        if (billType.equals(billA)) init_DonDat_PT();
        dsKH = dbR.loadDsKH();

    }

    private void iniInfKH() {
        System.out.println("kiem tra");
        System.out.println(dsKH.size());
        for(int i=0;i<dsKH.size();i++){
            if(edtCmnd.getText().equals(dsKH.get(i).getCmnd())){

                edtHoTen.setText(dsKH.get(i).getHoTen());
                edtDiaChi.setText(dsKH.get(i).getDiaChi());
            }
        }

    }

    public void init_DonDat_Xe() {
        dsXe = dbR.loadDSXE();

        txtViewMaSP.setText("Mã Xe");
        txtViewTenSP.setText("Tên Xe");
        atcpChonSP.setHint("Chọn xe");

    }

    public void init_DonDat_PT() {

        txtViewMaSP.setText("Mã phụ tùng");
        txtViewTenSP.setText("Tên phụ tùng");
        atcpChonSP.setHint("Chọn phụ tùng");

    }

    public List<Xe> loadDSXeFromDB() {

        List<Xe> dsXe = new ArrayList<Xe>();
        SQLiteDatabase db = new DBManager(getApplicationContext()).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM XE ", null);

        if (c.moveToFirst()) {
            Xe moto = new Xe();
            int i = 0;
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

    private void showMotoList() {


        String arr[] = new String[dsXe.size()];
        for (int i = 0; i < dsXe.size(); i++) {

            arr[i] = dsXe.get(i).getTenSP();

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_list_item, R.id.textView_listItem, arr);

        atcpChonSP.setAdapter(adapter);
        atcpChonSP.setThreshold(1);
    }

    private Xe getMotoFromActv() {
        String name = atcpChonSP.getText().toString();

        for (int i = 0; i < dsXe.size(); i++) {
            if (dsXe.get(i).getTenSP().equals(name)) {
                cart[cartIndex] = i;
                cartIndex++;
                System.out.println("chon xe");
                System.out.println(dsXe.get(i).getDonGia());
                return dsXe.get(i);
            }
        }
        return null;


    }

    private void addMotoToTable() {
        Xe moto = new Xe();
        moto = getMotoFromActv();

        TableRow tbRow = new TableRow(getApplicationContext());
        TextView txtvCode = new TextView(getApplicationContext());
        TextView txtvName = new TextView(getApplicationContext());
        TextView txtvPrice = new TextView(getApplicationContext());
        TextView txtvCount = new TextView(getApplicationContext());


        txtvCode.setText(moto.getMaSP());
        txtvName.setText(moto.getTenSP());
        txtvPrice.setText(String.valueOf(moto.getDonGia()));
        txtvCount.setText("1");


        tbRow.addView(txtvCode, 0);
        tbRow.addView(txtvName, 1);
        tbRow.addView(txtvCount, 2);
        tbRow.addView(txtvPrice, 3);


        tbLayout.addView(tbRow);
        edtHoaDon_ThanhTien.setText(String.valueOf(moto.getDonGia()));

    }

    public void giamSLXe(String maSP){
        for(int i=0;i<dsXe.size();i++){
            if(dsXe.get(i).getMaSP().equals(maSP)){
                System.out.println("test2");
                dsXe.get(i).setSoLuong(dsXe.get(i).getSoLuong()-1);
                dbR.updateSLXe(dsXe.get(i).getMaSP(),dsXe.get(i).getSoLuong()-1);
                System.out.println(dsXe.get(i).getSoLuong()-1);
            }
        }
    }
}