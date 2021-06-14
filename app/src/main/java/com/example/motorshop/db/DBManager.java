package com.example.motorshop.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.motorshop.datasrc.BoPhan;
import com.example.motorshop.datasrc.ChiTietSanPhamDonHang;
import com.example.motorshop.datasrc.DonHang;
import com.example.motorshop.datasrc.KhachHang;
import com.example.motorshop.datasrc.NhaCungCap;
import com.example.motorshop.datasrc.NhanVien;
import com.example.motorshop.datasrc.PhuTung;
import com.example.motorshop.datasrc.Xe;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static final String TAG = "DBManager";

    public DBManager(Context context) {
        super(context, "dbMOTORSTORE.db", null, 1);
        Log.d(TAG, "Create DB: ");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ArrayList<String> createTables = new ArrayList<>();
        createTables.add("CREATE TABLE IF NOT EXISTS BOPHAN (MABP text PRIMARY KEY, TENBP text not null)");
        createTables.add("CREATE TABLE IF NOT EXISTS NHANVIEN(MANV text PRIMARY KEY, HOTEN text not null, SDT text not null, MABP text not null, CONSTRAINT FK_NHANVIEN_BOPHAN FOREIGN KEY (MABP) REFERENCES BOPHAN(MABP))");
        createTables.add("CREATE TABLE IF NOT EXISTS KHACHHANG (CMND text PRIMARY KEY, HOTEN text not null, DIACHI text not null, SDT text null)");
        createTables.add("CREATE TABLE IF NOT EXISTS NHACUNGCAP (MANCC text PRIMARY KEY, TENNCC text not null, DIACHI text not null, SDT text not null, EMAIL text null, LOGO int not null)");
        createTables.add("CREATE TABLE IF NOT EXISTS XE (MAXE text PRIMARY KEY, TENXE text not null, SOLUONG int not null, DONGIA int not null, HANBAOHANH int not null, HINHANH int not null, MANCC text not null, CONSTRAINT FK_XE_NHACUNGCAP FOREIGN KEY (MANCC) REFERENCES NHACUNGCAP(MANCC))");
        createTables.add("CREATE TABLE IF NOT EXISTS PHUTUNG (MAPT text PRIMARY KEY, TENPT text not null, SOLUONG int not null, DONGIA int not null, HANBAOHANH int not null, HINHANH int not null, MANCC text not null, CONSTRAINT FK_PHUTUNG_NHACUNGCAP FOREIGN KEY (MANCC) REFERENCES NHACUNGCAP(MANCC))");
        createTables.add("CREATE TABLE IF NOT EXISTS THONGSOXE (MATS int PRIMARY KEY, TENTS text not null)");
        createTables.add("CREATE TABLE IF NOT EXISTS CHITIETTHONGSOXE (MAXE text not null, MATS int not null, NOIDUNGTS text not null, CONSTRAINT FK_CHITIETTHONGSOXE_XE FOREIGN KEY (MAXE) REFERENCES XE(MAXE), CONSTRAINT FK_CHITIETTHONGSOXE_THONGSOXE FOREIGN KEY (MATS) REFERENCES THONGSOXE(MATS), PRIMARY KEY (MAXE, MATS))");
        createTables.add("CREATE TABLE IF NOT EXISTS THONGSOPHUTUNG (MAPT text not null, MAXE text not null, DONGIA int not null, CONSTRAINT FK_THONGSOPHUTUNG_PHUTUNG FOREIGN KEY (MAPT) REFERENCES PHUTUNG(MAPT), CONSTRAINT FK_THONGSOPHUTUNG_XE FOREIGN KEY (MAXE) REFERENCES XE(MAXE), PRIMARY KEY (MAPT, MAXE))");
        createTables.add("CREATE TABLE IF NOT EXISTS DONDATHANG (MADH text PRIMARY KEY, NGAYDAT text not null, CMND text not null, MANV text not null, CONSTRAINT FK_DONDATHANG_KHACHHANG FOREIGN KEY (CMND) REFERENCES KHACHHANG(CMND), CONSTRAINT FK_DONDATHANG_NHANVIEN FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV))");
        createTables.add("CREATE TABLE IF NOT EXISTS CHITIETDONDATXE (MADH text not null, MAXE text not null, SOLUONG int not null, DONGIABAN int not null, CONSTRAINT FK_CHITIETDONDATXE_DONDATHANG FOREIGN KEY (MADH) REFERENCES DONDATHANG(MADH), CONSTRAINT FK_CHITIETDONDATXE_XE FOREIGN KEY (MAXE) REFERENCES XE(MAXE), PRIMARY KEY (MADH, MAXE))");
        createTables.add("CREATE TABLE IF NOT EXISTS CHITIETDONDATPHUTUNG (MADH text not null, MAPT text not null, SOLUONG int not null, DONGIABAN int not null, CONSTRAINT FK_CHITIETDONDATPHUTUNG_DONDATHANG FOREIGN KEY (MADH) REFERENCES DONDATHANG(MADH), CONSTRAINT FK_CHITIETDONDATPHUTUNG_PHUTUNG FOREIGN KEY (MAPT) REFERENCES PHUTUNG(MAPT), PRIMARY KEY (MADH, MAPT))");
        createTables.add("CREATE TABLE IF NOT EXISTS BAOHANH (MABH text PRIMARY KEY, MADH text not null, NGAYBH text not null, MANV text not null, CONSTRAINT FK_BAOHANH_DONDATHANG FOREIGN KEY (MADH) REFERENCES DONDATHANG(MADH), CONSTRAINT FK_BAOHANH_NHANVIEN FOREIGN KEY (MANV) REFERENCES NHANVIEN(MANV))");
        createTables.add("CREATE TABLE IF NOT EXISTS CHITIETBAOHANHXE (MABH text not null, MAXE text not null, NOIDUNGBH text not null, PHIBH int null, CONSTRAINT FK_CHITIETBAOHANHXE_BAOHANH FOREIGN KEY (MABH) REFERENCES BAOHANH(MABH), CONSTRAINT FK_CHITIETBAOHANHXE_XE FOREIGN KEY (MAXE) REFERENCES XE(MAXE), PRIMARY KEY (MABH, MAXE, NOIDUNGBH))");
        createTables.add("CREATE TABLE IF NOT EXISTS CHITIETBAOHANHPHUTUNG (MABH text not null, MAPT text not null, NOIDUNGBH text not null, PHIBH int null, CONSTRAINT FK_CHITIETBAOHANHPHUTUNG_BAOHANH FOREIGN KEY (MABH) REFERENCES BAOHANH(MABH), CONSTRAINT FK_CHITIETBAOHANHPHUTUNG_PHUTUNG FOREIGN KEY (MAPT) REFERENCES PHUTUNG(MAPT), PRIMARY KEY (MABH, MAPT, NOIDUNGBH))");

        for (String str : createTables) {
            db.execSQL(str);
            Log.d(TAG, "onCreate DB: " + str);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade DB: ");
    }


    //USED IN COMMONS
    public boolean ifIDExist(String IDColumnName, String tableName, String condition) {
        boolean exist = false;
        String query = "select " + IDColumnName + " from " + tableName + " where " + IDColumnName + " = " + condition;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            String id_tmp_check = cursor.getString(0);                              //
            cursor.close();
            db.close();
            Log.d(TAG, "Check MaBP ifIDExist: " + IDColumnName + "=" + id_tmp_check);
            return true;
        } else {
            cursor.close();
            db.close();
            Log.d(TAG, "Check MaBP ifIDExist: not exist");
            return false;
        }
    }


    //BO PHAN (DEPARTMENT)
    public void insertDP(BoPhan department) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MABP", department.getMaBP());
        values.put("TENBP", department.getTenBP());
        db.insert("BOPHAN", null, values);
        db.close();
        Log.d(TAG, "Insert DEPARTMENT: ");
    }

    public void updateDP(BoPhan department) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Update  BOPHAN  set ";
        query += " TENBP  = '" + department.getTenBP() + "' ";
        query += " WHERE MABP  = '" + department.getMaBP() + "'";
        db.execSQL(query);
        db.close();
        Log.d(TAG, "Update DEPARTMENT: ");
    }

    public void deleteDP(BoPhan department) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM BOPHAN WHERE MABP = '" + department.getMaBP() + "'";
        db.execSQL(query);
        db.close();
        Log.d(TAG, "Delete DEPARTMENT: ");
    }

    public void deleteDP(String departmentID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM BOPHAN WHERE MABP = '" + departmentID + "'";
        db.execSQL(query);
        db.close();
        Log.d(TAG, "Delete DEPARTMENT with departmentID: ");
    }

    public void loadDPList(ArrayList<BoPhan> departmentList) {
        departmentList.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from BOPHAN";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                BoPhan department = new BoPhan();
                department.setMaBP(cursor.getString(0));
                department.setTenBP(cursor.getString(1));
                departmentList.add(department);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "Load DEPARTMENT LIST: ");
    }


    //NHAN VIEN (STAFF)
    public void insertST() {
    }

    public void updateST() {
    }

    public void deleteST() {
    }

    public void loadSTList() {
    }


    //KHACH HANG (CUSTOMER)
    public void insertCTM(String cmnd, String hoTen, String diaChi, String sdt) {
        String sqlKH = "INSERT INTO KHACHHANG (CMND,HOTEN,DIACHI,SDT) VALUES(" +
                "'" + cmnd + "'," + "'" + hoTen + "'," + "'" + diaChi + "'," + "'" + sdt + "'" + ")";
        SQLiteDatabase db = getWritableDatabase();
        System.out.println(sqlKH);
        db.execSQL(sqlKH);

    }

    public void updateCTM() {
    }

    public void deleteCTM() {
    }

    public void loadCTMList() {

    }

    public List<KhachHang> loadDsKH() {
        List<KhachHang> listKH = new ArrayList<KhachHang>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM KHACHHANG ", null);

        if (c.moveToFirst()) {
            KhachHang kh = new KhachHang();
            int i = 0;
            do {
                kh = new KhachHang();
                kh.setCmnd(c.getString(0));
                kh.setHoTen(c.getString(1));
                kh.setDiaChi(c.getString(2));
                kh.setSdt(c.getString(3));
                listKH.add(kh);

            } while (c.moveToNext());

        }
        return listKH;
    }

    //NHA CUNG CAP (BRAND)
    public void insertBR(NhaCungCap brand) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MANCC", brand.getMaNCC());
        values.put("TENNCC", brand.getTenNCC());
        values.put("DIACHI", brand.getDiaChi());
        values.put("SDT", brand.getSdt());
        values.put("EMAIL", brand.getEmail());
        values.put("LOGO", brand.getLogo());
        db.insert("NHACUNGCAP", null, values);
        db.close();
        Log.d(TAG, "Insert BRAND: ");
    }

    public void updateBR(NhaCungCap brand) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Update  NHACUNGCAP  set ";
        query += " TENNCC  = '" + brand.getTenNCC() + "', ";
        query += " DIACHI  = '" + brand.getDiaChi() + "', ";
        query += " SDT  = '" + brand.getSdt() + "', ";
        query += " EMAIL  = '" + brand.getEmail() + "', ";
        query += " LOGO  = '" + brand.getLogo() + "' ";
        query += " WHERE MANCC  = '" + brand.getMaNCC() + "'";
        db.execSQL(query);
        db.close();
        Log.d(TAG, "Update BRAND: ");
    }

    public void deleteBR(NhaCungCap brand) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM NHACUNGCAP WHERE MANCC = '" + brand.getMaNCC() + "'";
        db.execSQL(query);
        db.close();
        Log.d(TAG, "Delete BRAND: ");
    }

    public void deleteBR(String brandName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM NHACUNGCAP WHERE TENNCC = '" + brandName + "'";
        db.execSQL(query);
        db.close();
        Log.d(TAG, "Delete BRAND with brandID: ");
    }

    public void loadBRList(ArrayList<NhaCungCap> brandList) {
        brandList.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM NHACUNGCAP";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                NhaCungCap brand = new NhaCungCap();
                brand.setMaNCC(cursor.getString(0));
                brand.setTenNCC(cursor.getString(1));
                brand.setDiaChi(cursor.getString(2));
                brand.setSdt(cursor.getString(3));
                brand.setEmail(cursor.getString(4));
                brand.setLogo(cursor.getInt(5));
                brandList.add(brand);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "Load BRAND: ");
    }


    //XE
    public void insertXe(Xe xe) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO XE (MAXE,TENXE,SOLUONG,DONGIA,HANBAOHANH,HINHANH,MANCC) VALUES(" +
                "'" + xe.getMaSP() + "'," + "'" + xe.getTenSP() + "'," + "'" + xe.getSoLuong() + "',"
                + "'" + xe.getDonGia() + "'," + "'" + xe.getHanBH() + "',"
                + "'" + xe.getHanBH() + "'," + "'" + xe.getTenNCC() + "'" + ")";
        db.execSQL(sql);


    }

    public List<Xe> loadDSXE() {


        List<Xe> listX = new ArrayList<Xe>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM XE ", null);

        if (c.moveToFirst()) {
            Xe xe = new Xe();
            int i = 0;
            do {
                xe = new Xe();
                xe.setMaSP(c.getString(0));
                xe.setTenSP(c.getString(1));
                xe.setSoLuong(Integer.parseInt(c.getString(2)));
                xe.setDonGia(Integer.parseInt(c.getString(3)));
                xe.setHanBH(Integer.parseInt(c.getString(4)));
                xe.setHinhAnh(Integer.parseInt(c.getString(5)));
                xe.setTenNCC(c.getString(6));  //MANCC
                listX.add(xe);

            } while (c.moveToNext());

        }
        return listX;


    }

    public void updateSLXe(String maSP, int sL) {
        String sql = "UPDATE XE SET SOLUONG =" +"'"+sL+"'" +
                "WHERE MAXE="+"'"+maSP+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);

    }

    public void loadXe() {
    }

    public void deleteXe() {
    }


    //PHU TUNG


    public void insertPT(PhuTung pt) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO PHUTUNG (MAPT,TENPT,SOLUONG,DONGIA,HANBAOHANH,HINHANH,MANCC) VALUES(" +
                "'" + pt.getMaSP() + "'," + "'" + pt.getTenSP() + "'," + "'" + pt.getSoLuong() + "',"
                + "'" + pt.getDonGia() + "'," + "'" + pt.getHanBH() + "',"
                + "'" + pt.getHanBH() + "'," + "'" + pt.getTenNCC() + "'" + ")";
        db.execSQL(sql);


    }


    public void updateSLPT(String maSP, int sL) {
        String sql = "UPDATE PHUTUNG SET SOLUONG =" +"'"+sL+"'" +
                "WHERE MAPT="+"'"+maSP+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);

    }

    public List<PhuTung> loadDSPT() {


        List<PhuTung> listP = new ArrayList<PhuTung>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM PHUTUNG ", null);

        if (c.moveToFirst()) {
            PhuTung pt = new PhuTung();
            int i = 0;
            do {
                pt = new PhuTung();
                pt.setMaSP(c.getString(0));
                pt.setTenSP(c.getString(1));
                pt.setSoLuong(Integer.parseInt(c.getString(2)));
                pt.setDonGia(Integer.parseInt(c.getString(3)));
                pt.setHanBH(Integer.parseInt(c.getString(4)));
                pt.setHinhAnh(Integer.parseInt(c.getString(5)));
                pt.setTenNCC(c.getString(6));  //MANCC
                listP.add(pt);

            } while (c.moveToNext());

        }
        return listP;
    }


        public void insertPT() {
    }

    public void updatePT() {
    }

    public void loadPT() {
    }

    public void deletePT() {
    }


    //THONG SO XE
    public void insertTSX() {
    }

    public void updateTSX( ) {

    }

    public void loadTSX() {
    }

    public void deleteTSX() {
    }


    //CHI TIET THONG SO XE
    public void insertCTTSX() {
    }

    public void updateCTTSX() {
    }

    public void loadCTTSX() {
    }

    public void deleteCTTSX() {
    }


    //THONG SO PHU TUNG
    public void insertTSPT() {
    }

    public void updateTSPT() {
    }

    public void loadTSPT() {
    }

    public void deleteTSPT() {
    }


    //DON HANG & CHI TIET DON HANG


    public void insertDH(String maDDH, String nd, String cmnd, String maNV) {

        String sqlDDH = "INSERT INTO DONDATHANG (MADH,NGAYDAT,CMND,MANV) VALUES(" +
                "'" + maDDH + "'," + "'"+nd+"'," + "'" + cmnd + "'," + "'" + maNV + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(sqlDDH);
        db.execSQL(sqlDDH);

    }


    public List<DonHang> loadDSDH() {


        List<DonHang> listDh = new ArrayList<DonHang>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM DONDATHANG ", null);

        if (c.moveToFirst()) {
            DonHang ddh = new DonHang();
            int i = 0;
            do {
                ddh = new DonHang();
                ddh.setMaDH(c.getString(0));
                ddh.setNgayDat(c.getString(1));
                ddh.setCmnd(c.getString(2));
                ddh.setTenNV(c.getString(3)); //manv
                listDh.add(ddh);

            } while (c.moveToNext());

        }
        return listDh;


    }


    //CHITIETDONDATXE


    public void insertCTDDX(String maDDH, String maSP, String sl, String dgb) {

        String sqlCTDDX = "INSERT INTO CHITIETDONDATXE(MADH,MAXE,SOLUONG,DONGIABAN) VALUES(" +
                "'" + maDDH + "'," + "'" + maSP + "',"
                + "'" + sl + "'," + "'" + dgb + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlCTDDX);

    }


    public List<ChiTietSanPhamDonHang> loadDSDDX() {
        List<ChiTietSanPhamDonHang> listDDX = new ArrayList<ChiTietSanPhamDonHang>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM CHITIETDONDATXE ", null);

        if (c.moveToFirst()) {
            ChiTietSanPhamDonHang cTDDX = new ChiTietSanPhamDonHang();
            int i = 0;
            do {
                cTDDX = new ChiTietSanPhamDonHang();
                cTDDX.setMaDH(c.getString(0));
                cTDDX.setTenSP(c.getString(1)); //MAXE
                cTDDX.setSoLuong(Integer.parseInt(c.getString(2)));
                cTDDX.setDonGiaBan(Integer.parseInt(c.getString(3)));
                listDDX.add(cTDDX);

            } while (c.moveToNext());

        }
        return listDDX;

    }


    //public void insertDH() { }      ->        public void insertCTDH() { }
    public void loadDH() {
    }


    //CHITIETDONDATPHUTUNG
    public List<ChiTietSanPhamDonHang> loadDSDDPT() {
        List<ChiTietSanPhamDonHang> listDDX = new ArrayList<ChiTietSanPhamDonHang>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM CHITIETDONDATPHUTUNG ", null);

        if (c.moveToFirst()) {
            ChiTietSanPhamDonHang cTDDX = new ChiTietSanPhamDonHang();
            int i = 0;
            do {
                cTDDX = new ChiTietSanPhamDonHang();
                cTDDX.setMaDH(c.getString(0));
                cTDDX.setTenSP(c.getString(1)); //MAXE
                cTDDX.setSoLuong(Integer.parseInt(c.getString(2)));
                cTDDX.setDonGiaBan(Integer.parseInt(c.getString(3)));
                listDDX.add(cTDDX);

            } while (c.moveToNext());

        }
        return listDDX;

    }

    public void insertCTDDPT(String maDDH, String maSP, String sl, String dgb) {

        String sqlCTDDX = "INSERT INTO CHITIETDONDATPHUTUNG(MADH,MAPT,SOLUONG,DONGIABAN) VALUES(" +
                "'" + maDDH + "'," + "'" + maSP + "',"
                + "'" + sl + "'," + "'" + dgb + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlCTDDX);

    }


//CAC PHAN CON LAI TUONG TU


    ///////HAM TEST

    public void testInsCTDDX() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlCTDDX = "INSERT INTO CHITIETDONDATXE(MADH,MAXE,SOLUONG,DONGIABAN) VALUES(" +
                "'HD0','X003'," + 1 + "," + 22000000 + ")";
        db.execSQL(sqlCTDDX);
    }

    public void testDltCTDDX() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM CHITIETDONDATXE WHERE MADH='HD1'";
        db.execSQL(sql);

    }

}
