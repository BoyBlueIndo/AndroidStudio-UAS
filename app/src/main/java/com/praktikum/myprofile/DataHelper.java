package com.praktikum.myprofile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "biodatadiri.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel users
        String createUsersTable = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, password TEXT NOT NULL);";
        db.execSQL(createUsersTable);

        // Membuat tabel biodata
        String createBiodataTable = "CREATE TABLE biodata (no INTEGER PRIMARY KEY, " +
                "nama TEXT NULL, tgl TEXT NULL, jk TEXT NULL, alamat TEXT NULL);";
        Log.d("Data", "onCreate: " + createBiodataTable);
        db.execSQL(createBiodataTable);

        // Menambahkan data contoh ke dalam tabel biodata
        String insertBiodata = "INSERT INTO biodata (no, nama, tgl, jk, alamat) " +
                "VALUES (1001, 'Fathur', '1994-02-03', 'Laki-laki', 'Jakarta');";
        db.execSQL(insertBiodata);
    }


    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
    {
        // TODO Auto-generated method stub
    }


    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);  // Anda bisa mengenkripsi password sebelum disimpan

        long result = db.insert("users", null, values);
        db.close();
        return result != -1;  // Jika insert berhasil
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;  // Pengguna ditemukan dan login berhasil
        } else {
            cursor.close();
            return false;  // Pengguna tidak ditemukan atau password salah
        }
    }
}