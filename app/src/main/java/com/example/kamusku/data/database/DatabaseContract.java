package com.example.kamusku.data.database;

import android.provider.BaseColumns;

/**
 * Created by User on 1/18/2019.
 */

public class DatabaseContract {

    public static String TABLE_INDO_ENGLISH = "TABEL_INDO_INGGRIS";
    public static String TABLE_ENGLISH_INDO = "TABEL_INGGRIS_INDO";

    static final class KamusColumn implements BaseColumns {

        // Mahasiswa nama
        static String KATA = "kata";
        // Mahasiswa nim
        static String TERJEMAHAN = "terjemahan";

    }
}
