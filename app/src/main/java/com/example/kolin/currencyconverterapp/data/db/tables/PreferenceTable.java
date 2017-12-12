package com.example.kolin.currencyconverterapp.data.db.tables;

import android.content.ContentValues;

/**
 * Data base table for work with preference
 *
 * View of table:
 *
 *  __________________________________
 * | key_preference| value_preference |
 * |_______________|__________________|
 * |               |                  |
 */

public class PreferenceTable {

    public static final String TABLE_NAME = "main_preference_table";

    public static String KEY = "key_preference";
    public static String VALUE = "value_preference";

    public static String createTable(){
        return "CREATE TABLE "
                + TABLE_NAME + "("
                + KEY + " TEXT PRIMARY KEY,"
                + VALUE + " TEXT)";
    }

    public static String getPreference(String key){
        return " SELECT " + VALUE + " FROM " + TABLE_NAME
                + " WHERE " + KEY + " = '" + key + "'";
    }

    public static String getKey(String key){
        return " SELECT " + KEY + " FROM " + TABLE_NAME
                + " WHERE " + KEY + " = '" + key + "'";
    }

    public static ContentValues getContentValues(String key, String value){
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY, key);
        contentValues.put(VALUE, value);

        return contentValues;
    }

}
