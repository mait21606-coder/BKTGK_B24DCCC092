package com.example.bktgk_b24dccc092;

public class SanBong {
    private int id;
    private String maSan;
    private String tenSan;
    private String diaChi;
    private double giaThue;
    private int trangThai;

    public SanBong(int id, String maSan, String tenSan, String diaChi, double giaThue, int trangThai) {
        this.id = id;
        this.maSan = maSan;
        this.tenSan = tenSan;
        this.diaChi = diaChi;
        this.giaThue = giaThue;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public String getMaSan() {
        return maSan;
    }

    public String getTenSan() {
        return tenSan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public int getTrangThai() {
        return trangThai;
    }
}