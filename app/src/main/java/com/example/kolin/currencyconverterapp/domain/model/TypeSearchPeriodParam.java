package com.example.kolin.currencyconverterapp.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_ALL;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_CUSTOM;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_MONTH;
import static com.example.kolin.currencyconverterapp.domain.model.TypeSearchPeriodParam.PERIOD_WEEK;

/**
 * Created by kolin on 26.11.2017.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({PERIOD_ALL, PERIOD_WEEK, PERIOD_MONTH, PERIOD_CUSTOM})
public @interface TypeSearchPeriodParam {
    int PERIOD_ALL = 0;
    int PERIOD_WEEK = 1;
    int PERIOD_MONTH = 2;
    int PERIOD_CUSTOM = 3;
}
