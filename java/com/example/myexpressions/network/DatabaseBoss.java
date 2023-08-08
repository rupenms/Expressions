package com.example.myexpressions.network;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DatabaseBoss extends SQLiteOpenHelper {
    private static final String DB_NAME = "expressionInfo";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "expressionData";
    private static final String ID_COL = "id";
    private static final String INPUT_COL = "input";
    private static final String RESULT_COL = "result";
    public static final String DATE_COL = "date";

    public DatabaseBoss(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + INPUT_COL + " TEXT,"
                + RESULT_COL + " TEXT,"
                + DATE_COL + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(String input, String result, long date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(INPUT_COL, input);
        values.put(RESULT_COL, result);
        values.put(DATE_COL, date);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Map<String, String> fetchData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATE_COL, null);
        Map<String, String> results = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                results.put(cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return results;
    }
}
