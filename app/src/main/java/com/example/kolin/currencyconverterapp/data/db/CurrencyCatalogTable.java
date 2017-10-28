package com.example.kolin.currencyconverterapp.data.db;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Created by kolin on 26.10.2017.
 */

public class CurrencyCatalogTable {

    public static final String TABLE_NAME = "currency_catalog_table";

    public static final String ID = "id_currency";
    public static final String NAME = "name_currency";
    public static final String IS_FAVORITE = "is_favorite";
    public static final String TIME = "last_use";


    public static String createTable() {
        return "CREATE TABLE "
                + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT UNIQUE,"
                + IS_FAVORITE + " INTEGER DEFAULT 0,"
                + TIME + "LONG);";
    }

    public static String selectAllCurrencies() {
        return "SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectAllFavoritesCurrencies() {
        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + IS_FAVORITE + " = 1"
                + " ORDER BY " + TIME + " DESC";
    }

    public static String addCurrencyToFavorite(int id) {
        return "UPDATE " + TABLE_NAME
                + " SET " + IS_FAVORITE + " = 1"
                + " WHERE " + ID + " = " + id;
    }

    public static String removeCurrencyFromFavorite(int id) {
        return "UPDATE " + TABLE_NAME
                + " SET " + IS_FAVORITE + " = 0"
                + " WHERE " + ID + " = " + id;
    }

    public static String selectCurrencyIdByName(String name) {
        return "SELECT " + ID
                + " FROM " + TABLE_NAME
                + " WHERE " + NAME + " = '" + name + "'";
    }

    public static String updateCurrencyTime(int id) {
        return "UPDATE " + TABLE_NAME
                + " SET " + TIME + " = " + Calendar.getInstance().getTimeInMillis()
                + " WHERE " + ID + " = " + id;
    }

    public static ContentValues getContentValues(String name) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);
        contentValues.put(TIME, Calendar.getInstance().getTimeInMillis());

        return contentValues;
    }
}