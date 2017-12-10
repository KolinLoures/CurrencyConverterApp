package com.example.kolin.currencyconverterapp.presentation.util;

import android.content.Context;
import android.text.format.DateUtils;

/**
 * Created by kolin on 19.11.2017.
 */

public class AppFormatter {

    public static String formatRate(float f) {
        return String.format("%.2f", f);
    }

    public static String formatRate(float from, float to, String delimiter) {
        return formatRate(from) + delimiter + formatRate(to);
    }

    public static String formatRate(float from, float to, String nameFrom, String nameTo, String delimiter){
        return formatRate(from)+ " " + nameFrom + delimiter + nameTo + " " + formatRate(to);
    }

    public static long fromateWeekCloseDatePeriod(Context context, long timeFrom){
        return timeFrom - DateUtils.WEEK_IN_MILLIS;
    }

    public static long fromateMonthCloseDatePeriod(Context context, long timeFrom){
        return timeFrom - DateUtils.DAY_IN_MILLIS * 30;
    }

}
