package com.example.kolin.currencyconverterapp.data.db.tables;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Data base table class for {@link com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity}
 *
 * View of table:
 *
 * ________________________________________________________
 * | id_currency | name_currency | is_favorite | last_use |
 * |_____________|_______________|_____________|__________|
 * |             |               |             |          |
 */

public class CurrencyCatalogTable {

    //Table name
    public static final String TABLE_NAME = "currency_catalog_table";
    public static final String ID = "id_currency";
    public static final String NAME = "name_currency";
    public static final String IS_FAVORITE = "is_favorite";
    public static final String TIME = "last_use";


    /**
     * @return SQL String to create table
     */
    public static String createTable() {
        return "CREATE TABLE "
                + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + IS_FAVORITE + " INTEGER DEFAULT 0,"
                + TIME + " LONG);";
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

    public static String selectNames(){
        return "SELECT " + NAME
                + " FROM " + TABLE_NAME;
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

    public static ContentValues getContentValues(String name, long time) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, name);
        contentValues.put(TIME, time);

        return contentValues;
    }



    public static String[] getAllFields() {
        return new String[]{
                ID,
                NAME,
                IS_FAVORITE,
                TIME,
        };
    }
}