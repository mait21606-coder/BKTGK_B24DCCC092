package com.example.bktgk_b24dccc092;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddSanActivity extends AppCompatActivity {
    private EditText editMaSan, editTenSan, editDiaChi, editGiaThue;
    private Button buttonLuuSan, buttonQuayLai;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_san);

        editMaSan = findViewById(R.id.editMaSan);
        editTenSan = findViewById(R.id.editTenSan);
        editDiaChi = findViewById(R.id.editDiaChi);
        editGiaThue = findViewById(R.id.editGiaThue);
        buttonLuuSan = findViewById(R.id.buttonLuuSan);
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        databaseHelper = new DatabaseHelper(this);

        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonLuuSan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maSan = editMaSan.getText().toString().trim();
                String tenSan = editTenSan.getText().toString().trim();
                String diaChi = editDiaChi.getText().toString().trim();
                String giaStr = editGiaThue.getText().toString().trim();

                if (maSan.isEmpty() || tenSan.isEmpty() || diaChi.isEmpty() || giaStr.isEmpty()) {
                    Toast.makeText(AddSanActivity.this, "Vui lòng nhập đầy đủ các trường!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double giaThue = Double.parseDouble(giaStr);
                boolean kq = databaseHelper.themSan(maSan, tenSan, diaChi, giaThue);
                if (kq) {
                    Toast.makeText(AddSanActivity.this, "Thêm sân thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddSanActivity.this, "Thêm sân thất bại hoặc mã sân bị trùng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}