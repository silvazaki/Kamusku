package com.example.kamusku.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.kamusku.data.database.DatabaseContract.KamusColumn.KATA;
import static com.example.kamusku.data.database.DatabaseContract.KamusColumn.TERJEMAHAN;
import static com.example.kamusku.data.database.DatabaseContract.TABLE_ENGLISH_INDO;
import static com.example.kamusku.data.database.DatabaseContract.TABLE_INDO_ENGLISH;

/**
 * Created by User on 1/18/2019.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "db_kamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_INDO_ENGLISH = "create table " + TABLE_INDO_ENGLISH +
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            TERJEMAHAN + " text not null);";

    public static String CREATE_TABLE_ENGLISH_INDO = "create table " + TABLE_ENGLISH_INDO +
            " (" + _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            TERJEMAHAN + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_INDO_ENGLISH);
        db.execSQL(CREATE_TABLE_ENGLISH_INDO);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDO_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISH_INDO);
        onCreate(db);
    }
}
