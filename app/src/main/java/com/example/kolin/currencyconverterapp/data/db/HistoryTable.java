package com.example.kolin.currencyconverterapp.data.db;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Created by kolin on 26.10.2017.
 */

public class HistoryTable {

    public static final String TABLE_NAME = "exchange_history_table";

    public static final String ID = "id_exchange";
    public static final String CURRENCY_FROM = "currency_from";
    public static final String CURRENCY_TO = "currency_to";
    public static final String SUM_FROM = "sum_from";
    public static final String SUM_TO = "sum_to";
    public static final String RATE = "rate";
    public static final String TIME = "time_exchange";


    public static String createTable() {
        return "CREATE TABLE "
                + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + CURRENCY_FROM + " TEXT,"
                + CURRENCY_TO + " TEXT,"
                + SUM_FROM + " INTEGER,"
                + SUM_TO + " INTEGER,"
                + RATE + " FLOAT,"
                + TIME + " LONG);";
    }

    public static String selectHistory() {
        return "SELECT * FROM " + TABLE_NAME
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(long timeFrom, long timeTo) {
        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.TIME + " BETWEEN " + timeFrom + " AND " + timeTo
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(String currency) {
        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.CURRENCY_FROM + " = '" + currency + "'"
                + " OR " + HistoryTable.CURRENCY_TO + " = '"+ currency + "'"
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(String[] currencyNames) {

        String in = createConditionIn(currencyNames);

        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.CURRENCY_FROM + " IN " + in
                + " OR " + HistoryTable.CURRENCY_TO + " IN " + in
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(String[] currencyNames, long timeFrom, long timeTo) {

        String in = createConditionIn(currencyNames);

        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + "(" + HistoryTable.CURRENCY_FROM + " IN " + in
                + " OR " + HistoryTable.CURRENCY_TO + " IN " + in + " AND "
                + "(" + HistoryTable.TIME + " BETWEEN " + timeFrom + " AND " + timeTo + ")"
                + " ORDER BY " + TIME + " DESC";
    }

    public static String clearHistory(){
        return "DELETE FROM " + TABLE_NAME;
    }

    public static ContentValues getContentValues(String currencyFrom,
                                                 String currencyTo,
                                                 int sumFrom,
                                                 int sumTo,
                                                 float rate) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(CURRENCY_FROM, currencyFrom);
        contentValues.put(CURRENCY_TO, currencyTo);
        contentValues.put(SUM_FROM, sumFrom);
        contentValues.put(SUM_TO, sumTo);
        contentValues.put(RATE, rate);
        contentValues.put(TIME, Calendar.getInstance().getTimeInMillis());

        return contentValues;
    }

    private static String createConditionIn(String[] conditions){
        StringBuilder sb = new StringBuilder("(");

        for (String s: conditions){
            sb.append("'");
            sb.append(s);
            sb.append("'");
            sb.append(",");
        }

        sb.append(")");

        return sb.toString();
    }

    public static String[] getAllFields(){
        return new String[]{
                ID,
                CURRENCY_FROM,
                CURRENCY_TO,
                SUM_FROM,
                SUM_TO,
                RATE,
                TIME
        };
    }
}

