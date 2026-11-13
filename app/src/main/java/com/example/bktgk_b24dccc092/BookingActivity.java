package com.example.bktgk_b24dccc092;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {
    private TextView textThongTinSan, textTongTien;
    private EditText editHoTen, editSoDienThoai, editNgayThue, editGioBatDau, editSoGioThue;
    private Button buttonXacNhanDat, buttonQuayLai;
    private DatabaseHelper databaseHelper;
    private String maSan, tenSan;
    private double giaThue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        textThongTinSan = findViewById(R.id.textThongTinSan);
        textTongTien = findViewById(R.id.textTongTien);
        editHoTen = findViewById(R.id.editHoTen);
        editSoDienThoai = findViewById(R.id.editSoDienThoai);
        editNgayThue = findViewById(R.id.editNgayThue);
        editGioBatDau = findViewById(R.id.editGioBatDau);
        editSoGioThue = findViewById(R.id.editSoGioThue);
        buttonXacNhanDat = findViewById(R.id.buttonXacNhanDat);
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        databaseHelper = new DatabaseHelper(this);

        maSan = getIntent().getStringExtra("MASAN");
        layThongTinSan();

        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editSoGioThue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tinhToanTongTien();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        buttonXacNhanDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = editHoTen.getText().toString().trim();
                String sdt = editSoDienThoai.getText().toString().trim();
                String ngayThue = editNgayThue.getText().toString().trim();
                String gioBatDau = editGioBatDau.getText().toString().trim();
                String soGioStr = editSoGioThue.getText().toString().trim();

                if (hoTen.isEmpty() || sdt.isEmpty() || ngayThue.isEmpty() || gioBatDau.isEmpty() || soGioStr.isEmpty()) {
                    Toast.makeText(BookingActivity.this, "Vui lòng nhập đầy đủ thông tin đặt sân!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double soGio = Double.parseDouble(soGioStr);
                double tongTien = soGio * giaThue;

                boolean kq = databaseHelper.themDonDat(hoTen, sdt, maSan, tenSan, ngayThue, gioBatDau, soGio, tongTien);
                if (kq) {
                    Toast.makeText(BookingActivity.this, "Đặt sân thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(BookingActivity.this, "Đặt sân thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void layThongTinSan() {
        Cursor cursor = databaseHelper.layTatCaSan();
        while (cursor.moveToNext()) {
            if (cursor.getString(1).equals(maSan)) {
                tenSan = cursor.getString(2);
                giaThue = cursor.getDouble(4);
                textThongTinSan.setText("Sân: " + tenSan + " (Mã: " + maSan + ")\nGiá thuê: " + String.format("%,.0f", giaThue) + " VNĐ/h");
                break;
            }
        }
        cursor.close();
    }

    private void tinhToanTongTien() {
        String soGioStr = editSoGioThue.getText().toString().trim();
        if (!soGioStr.isEmpty()) {
            try {
                double soGio = Double.parseDouble(soGioStr);
                double tongTien = soGio * giaThue;
                textTongTien.setText("Tổng tiền: " + String.format("%,.0f", tongTien) + " VNĐ");
            } catch (NumberFormatException e) {
                textTongTien.setText("Tổng tiền: 0 VNĐ");
            }
        } else {
            textTongTien.setText("Tổng tiền: 0 VNĐ");
        }
    }
}