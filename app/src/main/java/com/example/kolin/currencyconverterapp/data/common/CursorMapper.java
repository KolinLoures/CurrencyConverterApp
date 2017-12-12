package com.example.kolin.currencyconverterapp.data.common;

import android.database.Cursor;

import com.example.kolin.currencyconverterapp.data.db.tables.CurrencyCatalogTable;
import com.example.kolin.currencyconverterapp.data.db.tables.HistoryTable;
import com.example.kolin.currencyconverterapp.data.db.tables.PreferenceTable;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyEntity;
import com.example.kolin.currencyconverterapp.data.model.entity.CurrencyHistoryEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Helper for cursors
 */

public class CursorMapper {

    /**
     * Map cursor to preference value
     *
     * @param cursor with preference values
     * @return value of preference
     */
    public static String cursorToPreference(Cursor cursor){
        String s = null;

        if (cursor != null && cursor.moveToFirst())
            s = cursor.getString(cursor.getColumnIndex(PreferenceTable.VALUE));

        return s;
    }

    /**
     * Map cursor to {@link List<CurrencyEntity>} object
     *
     * @param cursor to map
     * @return {@link List<CurrencyEntity>} object
     */
    public static List<CurrencyEntity> cursorToCurrencyEntityClass(Cursor cursor) {
        List<CurrencyEntity> temp = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())
            try {

                do {
                    int id = cursor.getInt(cursor.getColumnIndex(CurrencyCatalogTable.ID));
                    String name = cursor.getString(cursor.getColumnIndex(CurrencyCatalogTable.NAME));
                    boolean isFavorite = cursor.getInt(cursor.getColumnIndex(CurrencyCatalogTable.IS_FAVORITE)) != 0;
                    long lastUse = cursor.getLong(cursor.getColumnIndex(CurrencyCatalogTable.TIME));

                    CurrencyEntity obj = new CurrencyEntity();

                    obj.setId(id);
                    obj.setName(name);
                    obj.setFavorite(isFavorite);
                    obj.setLastUse(lastUse);

                    temp.add(obj);

                } while (cursor.moveToNext());

                cursor.close();

                return temp;

            } finally {
                cursor.close();
            }

        return temp;
    }

    /**
     * Map cursor to {@link List<CurrencyHistoryEntity>} object
     *
     * @param cursor to map
     * @return {@link List<CurrencyHistoryEntity>} object
     */
    public static List<CurrencyHistoryEntity> cursorToCurrencyHistoryEntity(Cursor cursor) {
        List<CurrencyHistoryEntity> temp = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())
            try {

                do {

                    int id = cursor.getInt(cursor.getColumnIndex(HistoryTable.ID));
                    int idCurrencyFrom = cursor.getInt(cursor.getColumnIndex(HistoryTable.ID_CURRENCY_FROM));
                    int idCurrencyTo = cursor.getInt(cursor.getColumnIndex(HistoryTable.ID_CURRENCY_TO));
                    String from = cursor.getString(cursor.getColumnIndex(HistoryTable.AS_NAME_CURR_FROM));
                    String to = cursor.getString(cursor.getColumnIndex(HistoryTable.AS_NAME_CURR_TO));
                    int sumFrom = cursor.getInt(cursor.getColumnIndex(HistoryTable.SUM_FROM));
                    int sumTo = cursor.getInt(cursor.getColumnIndex(HistoryTable.SUM_TO));
                    float rate = cursor.getInt(cursor.getColumnIndex(HistoryTable.RATE));
                    long time = cursor.getLong(cursor.getColumnIndex(HistoryTable.TIME));

                    CurrencyHistoryEntity obj = new CurrencyHistoryEntity();

                    obj.setId(id);
                    obj.setIdCurrencyFrom(idCurrencyFrom);
                    obj.setIdCurrencyTo(idCurrencyTo);
                    obj.setCurrencyFrom(from);
                    obj.setCurrencyTo(to);
                    obj.setSumFrom(sumFrom);
                    obj.setSumTo(sumTo);
                    obj.setRate(rate);
                    obj.setTime(time);

                    temp.add(obj);

                } while (cursor.moveToNext());

                return temp;

            } finally {
                cursor.close();
            }
        return temp;
    }


    /**
     * Map cursor of currency names to {@link List<String>} object
     *
     * @param cursor to map
     * @return {@link List<String>} object
     */
    public static List<String> cursorToNamesList(Cursor cursor) {
        List<String> temp = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst())
            try {
                do
                    temp.add(cursor.getString(cursor.getColumnIndex(CurrencyCatalogTable.NAME)));
                while (cursor.moveToNext());
            } finally {
                cursor.close();
            }

        return temp;
    }
}
