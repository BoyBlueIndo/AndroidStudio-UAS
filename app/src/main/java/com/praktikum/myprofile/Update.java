package com.praktikum.myprofile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4, text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Inisialisasi
        dbHelper = new DataHelper(this);
        text1 = findViewById(R.id.editTextup1);
        text2 = findViewById(R.id.editTextup2);
        text3 = findViewById(R.id.editTextup3);
        text4 = findViewById(R.id.editTextup4);
        text5 = findViewById(R.id.editTextup5);

        // Mengambil data dari database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            text1.setText(cursor.getString(0)); // ID
            text2.setText(cursor.getString(1)); // Nama
            text3.setText(cursor.getString(2)); // Tanggal Lahir
            text4.setText(cursor.getString(3)); // Jenis Kelamin
            text5.setText(cursor.getString(4)); // Alamat
        } else {
            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Mengatur tombol simpan dan batal
        ton1 = findViewById(R.id.button1);
        ton2 = findViewById(R.id.button2);

        ton1.setOnClickListener(arg0 -> {
            if (text2.getText().toString().isEmpty() || text3.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Nama dan Tanggal Lahir harus diisi", Toast.LENGTH_LONG).show();
            } else {
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                db1.execSQL("UPDATE biodata SET nama = ?, tgl = ?, jk = ?, alamat = ? WHERE no = ?",
                        new String[]{
                                text2.getText().toString(),
                                text3.getText().toString(),
                                text4.getText().toString(),
                                text5.getText().toString(),
                                text1.getText().toString() // ID untuk identifikasi
                        });

                Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        ton2.setOnClickListener(arg0 -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
