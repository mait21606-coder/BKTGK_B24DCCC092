package com.example.bktgk_b24dccc092;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BookingListActivity extends AppCompatActivity {
    private ListView listViewDonDat;
    private Button buttonQuayLai;
    private ArrayList<String> danhSachDon;
    private ArrayList<Integer> danhSachIdDon;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        listViewDonDat = findViewById(R.id.listViewDonDat);
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        databaseHelper = new DatabaseHelper(this);
        danhSachDon = new ArrayList<>();
        danhSachIdDon = new ArrayList<>();

        taiDanhSachDon();

        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listViewDonDat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idDon = danhSachIdDon.get(position);
                Intent intent = new Intent(BookingListActivity.this, BookingDetailActivity.class);
                intent.putExtra("ID_DON", idDon);
                startActivity(intent);
            }
        });
    }

    private void taiDanhSachDon() {
        danhSachDon.clear();
        danhSachIdDon.clear();

        Cursor cursor = databaseHelper.layTatCaDonDat();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenNguoiDat = cursor.getString(1);
            String sdt = cursor.getString(2);
            String tenSan = cursor.getString(4);
            String ngayThue = cursor.getString(5);
            String gioBatDau = cursor.getString(6);
            double tongTien = cursor.getDouble(8);

            danhSachIdDon.add(id);

            String thongTin = "Người đặt: " + tenNguoiDat + " (" + sdt + ")\nSân: " + tenSan + "\nNgày: " + ngayThue + " - Giờ: " + gioBatDau + "\nTổng tiền: " + String.format("%,.0f", tongTien) + " VNĐ";
            danhSachDon.add(thongTin);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhSachDon);
        listViewDonDat.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taiDanhSachDon();
    }
}