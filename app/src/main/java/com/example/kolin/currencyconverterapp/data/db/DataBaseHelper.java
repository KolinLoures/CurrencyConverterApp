package com.example.kolin.currencyconverterapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.kolin.currencyconverterapp.data.db.tables.CurrencyCatalogTable;
import com.example.kolin.currencyconverterapp.data.db.tables.HistoryTable;

/**
 * Created by kolin on 26.10.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "com.example.kolin.currencyconverterapp.db_change_history";
    private static final int DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CurrencyCatalogTable.createTable());
        sqLiteDatabase.execSQL(HistoryTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CurrencyCatalogTable.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryTable.TABLE_NAME);
            this.onCreate(sqLiteDatabase);
        }
    }

    public Cursor getCursor(@NonNull String sql) {
        return this.getReadableDatabase().rawQuery(sql, null);
    }

    public Cursor getCursor(@NonNull String sql, @NonNull String[] selectionArgs) {
        return this.getReadableDatabase().rawQuery(sql, selectionArgs);
    }

    public void insert(@NonNull String tableName, @NonNull ContentValues contentValues) {
        this.getWritableDatabase().insert(tableName, null, contentValues);
    }

    public void update(@NonNull String tableName, @NonNull ContentValues contentValues, int id) {
        this.getWritableDatabase().update(tableName, contentValues, "ID = ?", new String[]{Integer.toString(id)});
    }

    public void writeToDB(@NonNull String tableName, @NonNull ContentValues contentValues, int id) {
        if (id == -1)
            this.insert(tableName, contentValues);
        else
            this.update(tableName, contentValues, id);
    }

    public void executeSQL(@NonNull String sql) {
        this.getWritableDatabase().execSQL(sql);
    }

}
