package com.example.kamusku.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.kamusku.data.model.Data;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.kamusku.data.database.DatabaseContract.KamusColumn.KATA;
import static com.example.kamusku.data.database.DatabaseContract.KamusColumn.TERJEMAHAN;
import static com.example.kamusku.data.database.DatabaseContract.TABLE_ENGLISH_INDO;
import static com.example.kamusku.data.database.DatabaseContract.TABLE_INDO_ENGLISH;

public class KamusHelper {

    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<Data> getDataByName(boolean tabel, String nama) {
        String kamus = "";
        if (tabel) {
            kamus = TABLE_INDO_ENGLISH;
        } else {
            kamus = TABLE_ENGLISH_INDO;
        }
        Cursor cursor = database.query(kamus,null,
                DatabaseContract.KamusColumn.KATA+" LIKE ?",
                new String[]{nama+"%"},null,null,null,"20");
        cursor.moveToFirst();
        ArrayList<Data> arrayList = new ArrayList<>();
        Data data;
        if (cursor.getCount() > 0) {
            do {
                data = new Data();
                data.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                data.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                data.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));

                arrayList.add(data);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Data> getAllData(boolean tabel) {
        String kamus = "";
        if (tabel) {
            kamus = TABLE_INDO_ENGLISH;
        } else {
            kamus = TABLE_ENGLISH_INDO;
        }
        Cursor cursor = database.query(kamus, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Data> arrayList = new ArrayList<>();
        Data data;
        if (cursor.getCount() > 0) {
            do {
                data = new Data();
                data.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                data.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                data.setTerjemahan(cursor.getString(cursor.getColumnIndexOrThrow(TERJEMAHAN)));


                arrayList.add(data);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertIndoEnglish(Data data) {
        String sql = "INSERT INTO " + TABLE_INDO_ENGLISH + " (" + KATA + ", " + TERJEMAHAN
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, data.getKata());
        stmt.bindString(2, data.getTerjemahan());
        stmt.execute();
        stmt.clearBindings();

    }

    public void insertEnglishIndo(Data data) {
        String sql = "INSERT INTO " + TABLE_ENGLISH_INDO + " (" + KATA + ", " + TERJEMAHAN
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, data.getKata());
        stmt.bindString(2, data.getTerjemahan());
        stmt.execute();
        stmt.clearBindings();

    }

    public long insert(Data data, String tabel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KATA, data.getKata());
        initialValues.put(TERJEMAHAN, data.getTerjemahan());
        return database.insert(tabel, null, initialValues);
    }
//    public int update(Data data) {
//        ContentValues args = new ContentValues();
//        args.put(KATA, data.getKata());
//        args.put(TERJEMAHAN, data.getTerjemahan());
//        return database.update(TABLE_INDO_ENGLISH, args, _ID + "= '" + data.getId() + "'", null);
//    }
//
//    public int delete(int id) {
//        return database.delete(TABLE_INDO_ENGLISH, _ID + " = '" + id + "'", null);
//    }
}
