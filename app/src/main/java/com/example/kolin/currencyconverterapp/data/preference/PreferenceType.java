package com.example.kolin.currencyconverterapp.data.preference;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.kolin.currencyconverterapp.data.preference.PreferenceType.BOOLEAN;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceType.INTEGER_ARRAY_LIST;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceType.INTEGER;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceType.LONG;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceType.STRING;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceType.STRING_ARRAY_LIST;

/**
 * Created by kolin on 08.12.2017.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({BOOLEAN, STRING, INTEGER, INTEGER_ARRAY_LIST, STRING_ARRAY_LIST, LONG})
public @interface PreferenceType {

    int BOOLEAN = 0;
    int STRING = 1;
    int INTEGER = 2;
    int INTEGER_ARRAY_LIST = 3;
    int STRING_ARRAY_LIST = 4;
    int LONG = 5;
}
