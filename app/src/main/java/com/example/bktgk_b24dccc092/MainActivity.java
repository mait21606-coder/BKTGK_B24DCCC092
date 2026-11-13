package com.example.bktgk_b24dccc092;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listViewSanBong;
    private Button buttonThemSan;
    private ArrayList<String> danhSachSan;
    private ArrayList<String> danhSachMaSan;
    private ArrayList<Integer> danhSachTrangThai;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSanBong = findViewById(R.id.listViewSanBong);
        buttonThemSan = findViewById(R.id.buttonThemSan);
        databaseHelper = new DatabaseHelper(this);
        danhSachSan = new ArrayList<>();
        danhSachMaSan = new ArrayList<>();
        danhSachTrangThai = new ArrayList<>();

        taiDanhSachSan();

        buttonThemSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSanActivity.class);
                startActivity(intent);
            }
        });

        listViewSanBong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int trangThai = danhSachTrangThai.get(position);
                if (trangThai == 1) {
                    Toast.makeText(MainActivity.this, "Sân này đã được đặt, không thể chọn!", Toast.LENGTH_SHORT).show();
                } else {
                    String maSanChon = danhSachMaSan.get(position);
                    Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                    intent.putExtra("MASAN", maSanChon);
                    startActivity(intent);
                }
            }
        });

        listViewSanBong.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String maSanXoa = danhSachMaSan.get(position);
                boolean kq = databaseHelper.xoaSan(maSanXoa);
                if (kq) {
                    Toast.makeText(MainActivity.this, "Đã xóa sân thành công", Toast.LENGTH_SHORT).show();
                    taiDanhSachSan();
                } else {
                    Toast.makeText(MainActivity.this, "Xóa sân thất bại", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    private void taiDanhSachSan() {
        danhSachSan.clear();
        danhSachMaSan.clear();
        danhSachTrangThai.clear();

        Cursor cursor = databaseHelper.layTatCaSan();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(1);
            String ten = cursor.getString(2);
            String diaChi = cursor.getString(3);
            double gia = cursor.getDouble(4);
            int trangThai = cursor.getInt(5);

            danhSachMaSan.add(ma);
            danhSachTrangThai.add(trangThai);

            String trangThaiText = (trangThai == 0) ? "Còn trống" : "Đã được đặt";
            String thongTin = "Mã: " + ma + " | Tên: " + ten + "\nĐịa chỉ: " + diaChi + "\nGiá: " + String.format("%,.0f", gia) + " VNĐ/h - Trạng thái: " + trangThaiText;
            danhSachSan.add(thongTin);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhSachSan);
        listViewSanBong.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taiDanhSachSan();
    }
}