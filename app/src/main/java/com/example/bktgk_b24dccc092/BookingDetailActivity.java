package com.example.bktgk_b24dccc092;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BookingDetailActivity extends AppCompatActivity {
    private TextView textChiTietDon;
    private Button buttonHuyDon, buttonQuayLai;
    private DatabaseHelper databaseHelper;
    private int idDon;
    private String maSanDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        textChiTietDon = findViewById(R.id.textChiTietDon);
        buttonHuyDon = findViewById(R.id.buttonHuyDon);
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        databaseHelper = new DatabaseHelper(this);

        idDon = getIntent().getIntExtra("ID_DON", -1);
        hienThiChiTiet();

        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kq = databaseHelper.huyDonDat(idDon, maSanDon);
                if (kq) {
                    Toast.makeText(BookingDetailActivity.this, "Đã hủy đơn đặt sân thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookingDetailActivity.this, "Hủy đơn thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hienThiChiTiet() {
        Cursor cursor = databaseHelper.layTatCaDonDat();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            if (id == idDon) {
                String tenNguoiDat = cursor.getString(1);
                String sdt = cursor.getString(2);
                maSanDon = cursor.getString(3);
                String tenSan = cursor.getString(4);
                String ngayThue = cursor.getString(5);
                String gioBatDau = cursor.getString(6);
                double soGio = cursor.getDouble(7);
                double tongTien = cursor.getDouble(8);

                String chiTiet = "Mã giao dịch: HD_" + id + "\n\n" +
                        "Họ tên người đặt: " + tenNguoiDat + "\n" +
                        "Số điện thoại: " + sdt + "\n\n" +
                        "Sân bóng: " + tenSan + " (Mã: " + maSanDon + ")\n\n" +
                        "Ngày thuê: " + ngayThue + "\n" +
                        "Giờ bắt đầu: " + gioBatDau + "\n" +
                        "Số giờ thuê: " + soGio + " giờ\n\n" +
                        "Tổng số tiền: " + String.format("%,.0f", tongTien) + " VNĐ";

                textChiTietDon.setText(chiTiet);
                break;
            }
        }
        cursor.close();
    }
}