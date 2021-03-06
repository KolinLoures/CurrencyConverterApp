package com.example.kolin.currencyconverterapp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.kolin.currencyconverterapp.data.db.tables.CurrencyCatalogTable;
import com.example.kolin.currencyconverterapp.data.db.tables.HistoryTable;
import com.example.kolin.currencyconverterapp.data.db.tables.PreferenceTable;

/**
 * Data base helper for SQLITE
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "com.example.kolin.currencyconverterapp.db_change_history";
    private static final int DB_VERSION = 1;

    private static DataBaseHelper instance = null;

    private DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DataBaseHelper getInstance() {
        return instance;
    }

    public static void initWithContext(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CurrencyCatalogTable.createTable());
        sqLiteDatabase.execSQL(HistoryTable.createTable());
        sqLiteDatabase.execSQL(PreferenceTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CurrencyCatalogTable.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryTable.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PreferenceTable.TABLE_NAME);
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

    public void update(@NonNull String tableName, @NonNull ContentValues contentValues, String whereClause, String[] whereArgs) {
        this.getWritableDatabase().update(tableName, contentValues, whereClause, whereArgs);
    }

    public void executeSQL(@NonNull String sql) {
        this.getWritableDatabase().execSQL(sql);
    }

}
