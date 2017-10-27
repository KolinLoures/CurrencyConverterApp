package com.example.kolin.currencyconverterapp.data.db;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Created by kolin on 26.10.2017.
 */

public class HistoryTable {

    public static final String TABLE_NAME = "exchange_history_table";

    private static final String ID = "id_exchange";
    private static final String ID_CURRENCY_FROM = "id_currency_from";
    private static final String ID_CURRENCY_TO = "id_currency_to";
    private static final String SUM_FROM = "sum_from";
    private static final String SUM_TO = "sum_to";
    private static final String RATE = "rate";
    private static final String TIME = "time_exchange";


    public static String createTable() {
        return "CREATE TABLE "
                + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + ID_CURRENCY_FROM + " TEXT,"
                + ID_CURRENCY_TO + " TEXT,"
                + SUM_FROM + " INTEGER,"
                + SUM_TO + " INTEGER,"
                + RATE + " FLOAT,"
                + TIME + " LONG);";
    }

    public static String selectHistory() {
        return "SELECT * FROM " + TABLE_NAME
                + " INNER JOIN"
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(long timeFrom, long timeTo) {
        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.TIME + " BETWEEN " + timeFrom + " AND " + timeTo
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(String currencyName) {
        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.ID_CURRENCY_FROM + " = '" + currencyName + "'"
                + " OR " + HistoryTable.ID_CURRENCY_TO + " = '"+ currencyName + "'"
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(String[] currencyNames) {

        String in = createConditionIn(currencyNames);

        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.ID_CURRENCY_FROM + " IN " + in
                + " OR " + HistoryTable.ID_CURRENCY_TO + " IN " + in
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(String[] currencyNames, long timeFrom, long timeTo) {

        String in = createConditionIn(currencyNames);

        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + "(" + HistoryTable.ID_CURRENCY_FROM + " IN " + in
                + " OR " + HistoryTable.ID_CURRENCY_TO + " IN " + in + " AND "
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

        contentValues.put(ID_CURRENCY_FROM, currencyFrom);
        contentValues.put(ID_CURRENCY_TO, currencyTo);
        contentValues.put(SUM_FROM, sumFrom);
        contentValues.put(SUM_TO, sumTo);
        contentValues.put(RATE, rate);
        contentValues.put(TIME, Calendar.getInstance().getTimeInMillis());

        return contentValues;
    }

    private static String createConditionIn(String[] conditions){
        StringBuilder temp = new StringBuilder("(");

        for (String s: conditions){
            temp.append("'");
            temp.append(s);
            temp.append("'");
            temp.append(",");
        }

        temp.append(")");

        return temp.toString();
    }
}

