package com.praktikum.myprofile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class Lihat_Bio extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton2;
    TextView text1, text2, text3, text4, text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_bio);

        // Inisialisasi DB dan view
        dbHelper = new DataHelper(this);
        text1 = findViewById(R.id.textViewliat1);
        text2 = findViewById(R.id.textViewliat2);
        text3 = findViewById(R.id.textViewliat3);
        text4 = findViewById(R.id.textViewliat4);
        text5 = findViewById(R.id.textViewliat5);

        // Membaca data dari database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0)); // ID
            text2.setText(cursor.getString(1)); // Nama
            text3.setText(cursor.getString(2)); // Tanggal Lahir
            text4.setText(cursor.getString(3)); // Jenis Kelamin
            text5.setText(cursor.getString(4)); // Alamat
        }

        // Tombol untuk kembali
        ton2 = findViewById(R.id.button1);
        ton2.setOnClickListener(arg0 -> {
            // Tutup activity
            finish();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu; menambahkan item ke action bar jika ada
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
