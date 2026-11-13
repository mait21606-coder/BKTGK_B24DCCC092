package com.example.bktgk_b24dccc092;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quanlysanbong.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSan = "CREATE TABLE sanbong (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "masan TEXT UNIQUE, " +
                "tensan TEXT, " +
                "diachi TEXT, " +
                "giathue REAL, " +
                "trangthai INTEGER)"; // 0: trống, 1: đã đặt
        db.execSQL(createTableSan);

        String createTableDatSan = "CREATE TABLE dondatsan (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tennguoidat TEXT, " +
                "sodienthoai TEXT, " +
                "masan TEXT, " +
                "tensan TEXT, " +
                "ngaythue TEXT, " +
                "giobatdau TEXT, " +
                "sogiothue REAL, " +
                "tongtien REAL)";
        db.execSQL(createTableDatSan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sanbong");
        db.execSQL("DROP TABLE IF EXISTS dondatsan");
        onCreate(db);
    }

    public boolean themSan(String maSan, String tenSan, String diaChi, double giaThue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("masan", maSan);
        values.put("tensan", tenSan);
        values.put("diachi", diaChi);
        values.put("giathue", giaThue);
        values.put("trangthai", 0);
        long result = db.insert("sanbong", null, values);
        return result != -1;
    }

    public Cursor layTatCaSan() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM sanbong", null);
    }

    public boolean xoaSan(String maSan) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("sanbong", "masan = ?", new String[]{maSan});
        return result > 0;
    }

    public boolean themDonDat(String tenNguoiDat, String soDienThoai, String maSan, String tenSan, String ngayThue, String gioBatDau, double soGioThue, double tongTien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tennguoidat", tenNguoiDat);
        values.put("sodienthoai", soDienThoai);
        values.put("masan", maSan);
        values.put("tensan", tenSan);
        values.put("ngaythue", ngayThue);
        values.put("giobatdau", gioBatDau);
        values.put("sogiothue", soGioThue);
        values.put("tongtien", tongTien);

        long result = db.insert("dondatsan", null, values);
        if (result != -1) {
            ContentValues updateValues = new ContentValues();
            updateValues.put("trangthai", 1);
            db.update("sanbong", updateValues, "masan = ?", new String[]{maSan});
            return true;
        }
        return false;
    }

    public Cursor layTatCaDonDat() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM dondatsan", null);
    }

    public boolean huyDonDat(int idDon, String maSan) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("dondatsan", "id = ?", new String[]{String.valueOf(idDon)});
        if (result > 0) {
            ContentValues updateValues = new ContentValues();
            updateValues.put("trangthai", 0);
            db.update("sanbong", updateValues, "masan = ?", new String[]{maSan});
            return true;
        }
        return false;
    }
}