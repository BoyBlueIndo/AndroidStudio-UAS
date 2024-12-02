package com.praktikum.myprofile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] daftar;
    ListView ListView01;
    protected Cursor cursor;
    DataHelper dbcenter;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(arg0 -> {
            // Menuju ke activity Buat
            Intent inte = new Intent(MainActivity.this, Buat.class);
            startActivity(inte);
        });

        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList(); // Menyegarkan daftar
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1); // Ambil nama dari kolom kedua
        }

        ListView01 = findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            final String selection = daftar[arg2];

            final CharSequence[] dialogitem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Pilihan");
            builder.setItems(dialogitem, (dialog, item) -> {
                switch (item) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), Lihat_Bio.class);
                        i.putExtra("nama", selection);
                        startActivity(i);
                        break;
                    case 1:
                        Intent in = new Intent(getApplicationContext(), Update.class);
                        in.putExtra("nama", selection);
                        startActivity(in);
                        break;
                    case 2:
                        SQLiteDatabase db1 = dbcenter.getWritableDatabase();
                        db1.execSQL("DELETE FROM biodata WHERE nama = ?", new String[]{selection});
                        RefreshList();  // Menyegarkan daftar setelah penghapusan
                        break;
                }
            });
            builder.create().show();
        });

        // Menutup cursor dalam blok finally
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((ArrayAdapter<?>) ListView01.getAdapter()).notifyDataSetChanged(); // Update adapter untuk ListView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
