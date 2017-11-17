package com.example.kolin.currencyconverterapp.data.db.tables;

import android.content.ContentValues;

import java.util.Calendar;

/**
 * Created by kolin on 26.10.2017.
 */

public class HistoryTable {

    public static final String TABLE_NAME = "exchange_history_table";

    public static final String ID = "id_exchange";
    public static final String ID_CURRENCY_FROM = "id_currency_from";
    public static final String ID_CURRENCY_TO = "id_currency_to";
    public static final String SUM_FROM = "sum_from";
    public static final String SUM_TO = "sum_to";
    public static final String RATE = "rate";
    public static final String TIME = "time_exchange";


    public static String createTable() {
        return "CREATE TABLE "
                + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY,"
                + ID_CURRENCY_FROM + " INTEGER,"
                + ID_CURRENCY_TO + " INTEGER,"
                + SUM_FROM + " FLOAT,"
                + SUM_TO + " FLOAT,"
                + RATE + " FLOAT,"
                + TIME + " LONG, " +
                " FOREIGN KEY (" + ID_CURRENCY_FROM + ")" +
                " REFERENCES " + CurrencyCatalogTable.TABLE_NAME + "(" + CurrencyCatalogTable.ID + "), " +
                " FOREIGN KEY (" + ID_CURRENCY_TO + ")" +
                " REFERENCES " + CurrencyCatalogTable.TABLE_NAME + "(" + CurrencyCatalogTable.ID + "));";
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

    public static String selectHistory(int currencyId) {
        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.ID_CURRENCY_FROM + " = " + currencyId
                + " OR " + HistoryTable.ID_CURRENCY_TO + " = "+ currencyId
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(int[] currencyIds) {

        String in = createConditionIn(currencyIds);

        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + HistoryTable.ID_CURRENCY_FROM + " IN " + in
                + " OR " + HistoryTable.ID_CURRENCY_TO + " IN " + in
                + " ORDER BY " + TIME + " DESC";
    }

    public static String selectHistory(int[] currencyIds, long timeFrom, long timeTo) {

        String in = createConditionIn(currencyIds);

        return "SELECT * FROM " + TABLE_NAME
                + " WHERE " + "(" + HistoryTable.ID_CURRENCY_FROM + " IN " + in
                + " OR " + HistoryTable.ID_CURRENCY_TO + " IN " + in + " AND "
                + "(" + HistoryTable.TIME + " BETWEEN " + timeFrom + " AND " + timeTo + ")"
                + " ORDER BY " + TIME + " DESC";
    }

    public static String clearHistory(){
        return "DELETE FROM " + TABLE_NAME;
    }

    public static ContentValues getContentValues(int idFrom,
                                                 int idTo,
                                                 float sumFrom,
                                                 float sumTo,
                                                 float rate) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID_CURRENCY_FROM, idFrom);
        contentValues.put(ID_CURRENCY_TO, idTo);
        contentValues.put(SUM_FROM, sumFrom);
        contentValues.put(SUM_TO, sumTo);
        contentValues.put(RATE, rate);
        contentValues.put(TIME, Calendar.getInstance().getTimeInMillis());

        return contentValues;
    }

    private static String createConditionIn(int[] conditions){
        StringBuilder sb = new StringBuilder("(");

        for (int i: conditions){
            sb.append(i);
            sb.append(",");
        }

        sb.append(")");

        return sb.toString();
    }

    public static String[] getAllFields(){
        return new String[]{
                ID,
                ID_CURRENCY_FROM,
                ID_CURRENCY_TO,
                SUM_FROM,
                SUM_TO,
                RATE,
                TIME
        };
    }
}

