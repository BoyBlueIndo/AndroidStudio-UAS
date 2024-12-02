package com.praktikum.myprofile;

import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
//import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Buat extends AppCompatActivity {
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4, text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat);

        // Inisialisasi dbHelper
        dbHelper = new DataHelper(this);

        // Inisialisasi EditText
        text1 = findViewById(R.id.editText1);
        text2 = findViewById(R.id.editText2);
        text3 = findViewById(R.id.editText3);
        text4 = findViewById(R.id.editText4);
        text5 = findViewById(R.id.editText5);

        // Inisialisasi Button
        ton1 = findViewById(R.id.button1);
        ton2 = findViewById(R.id.button2);

        // Listener untuk Button ton1
        ton1.setOnClickListener(view -> {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.execSQL("INSERT INTO biodata(no, nama, tgl, jk, alamat) VALUES('" +
                    text1.getText().toString() + "','" +
                    text2.getText().toString() + "','" +
                    text3.getText().toString() + "','" +
                    text4.getText().toString() + "','" +
                    text5.getText().toString() + "')");
            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
            MainActivity.ma.RefreshList();
            finish();
        });

        // Listener untuk Button ton2
        ton2.setOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
