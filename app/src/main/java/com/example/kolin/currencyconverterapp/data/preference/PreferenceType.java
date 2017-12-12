package com.example.kolin.currencyconverterapp.data.preference;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.BOOLEAN;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.INTEGER;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.INTEGER_ARRAY_LIST;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.LONG;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.STRING;
import static com.example.kolin.currencyconverterapp.data.preference.PreferenceTypeEnum.STRING_ARRAY_LIST;

@Retention(RetentionPolicy.SOURCE)
@IntDef({BOOLEAN, STRING, INTEGER, INTEGER_ARRAY_LIST, STRING_ARRAY_LIST, LONG})
public @interface PreferenceType {}