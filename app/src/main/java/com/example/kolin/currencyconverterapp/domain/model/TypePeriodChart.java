package com.example.kolin.currencyconverterapp.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart.PERIOD_MONTH;
import static com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart.PERIOD_TWO_WEEK;
import static com.example.kolin.currencyconverterapp.domain.model.TypePeriodChart.PERIOD_WEEK;

/**
 * Created by kolin on 05.12.2017.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({PERIOD_WEEK, PERIOD_TWO_WEEK, PERIOD_MONTH})
public @interface TypePeriodChart {
    int PERIOD_WEEK = 0;
    int PERIOD_TWO_WEEK = 1;
    int PERIOD_MONTH = 2;
}
