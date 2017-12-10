package com.example.kolin.currencyconverterapp.data.preference;

import android.database.Cursor;
import android.util.Log;

import com.example.kolin.currencyconverterapp.data.common.CursorMapper;
import com.example.kolin.currencyconverterapp.data.db.DataBaseHelper;
import com.example.kolin.currencyconverterapp.data.db.tables.PreferenceTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.BOOLEAN;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.INTEGER;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.INTEGER_ARRAY_LIST;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.LONG;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.STRING;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.STRING_ARRAY_LIST;

/**
 * Created by kolin on 27.10.2017.
 */

public class PreferenceManager implements BasePreference {

    public static final String TAG = PreferenceManager.class.getSimpleName();

    private DataBaseHelper db;
    private Gson gson;

    private Type listIntType = new TypeToken<ArrayList<Integer>>() {}.getType();
    private Type listStringType = new TypeToken<ArrayList<String>>() {}.getType();

    public PreferenceManager() {
        db = DataBaseHelper.getInstance();
        gson = new Gson();
    }


    @Override
    public Object getFromPreference(@PreferenceKeys String key, @PreferenceType int type, Object defValue) {
        Object temp = null;
        String s = CursorMapper.cursorToPreference(db.getCursor(PreferenceTable.getPreference(key)));

        switch (type) {
            case BOOLEAN:
                temp = s != null ? Boolean.valueOf(s) : (Boolean) defValue;
                break;
            case STRING:
                temp = s != null ? s : (String) defValue;
                break;
            case INTEGER:
                temp = s != null ? Integer.valueOf(s) : (Integer) defValue;
                break;
            case INTEGER_ARRAY_LIST:
                temp = s != null ? gson.fromJson(s, listIntType) : (List) defValue;
                break;
            case STRING_ARRAY_LIST:
                temp = s != null ? gson.fromJson(s, listStringType) : (List) defValue;
                break;
            case LONG:
                temp = s != null ? Long.valueOf(s) : (Long) defValue;
                break;
        }

        return temp;
    }

    @Override
    public void putToPreference(@PreferenceKeys String key, @PreferenceType int type, Object value) {
        switch (type) {
            case BOOLEAN:
                updateOrInsertPreference(key, value.toString());
                break;
            case STRING:
                updateOrInsertPreference(key, value.toString());
                break;
            case INTEGER:
                updateOrInsertPreference(key, value.toString());
                break;
            case INTEGER_ARRAY_LIST:
                updateOrInsertPreference(key, gson.toJson(value, listIntType));
                break;
            case STRING_ARRAY_LIST:
                updateOrInsertPreference(key, gson.toJson(value, listStringType));
                break;
            case LONG:
                updateOrInsertPreference(key, value.toString());
                break;
        }
    }

    private <T> void checkOrThrowValueType(Class<T> tClass, Object value) {
        if (!tClass.isInstance(value))
            throw new RuntimeException(TAG + ". Value " + value + " can not be cast to " + tClass.toString() + "!");
    }

    private void updateOrInsertPreference(@PreferenceKeys String key, String value) {
        Cursor cursor = db.getCursor(PreferenceTable.getKey(key));

        String temp = null;

        if (cursor != null && cursor.moveToFirst())
            try {
                temp = cursor.getString(cursor.getColumnIndex(PreferenceTable.KEY));
            } finally {
                cursor.close();
            }

        Log.i(TAG, "updateOrInsertPreference: " + temp);

        if (temp != null)
            db.update(PreferenceTable.TABLE_NAME, PreferenceTable.getContentValues(key, value), PreferenceTable.KEY + " = '?'", new String[]{key});
        else
            db.insert(PreferenceTable.TABLE_NAME, PreferenceTable.getContentValues(key, value));

    }
}
