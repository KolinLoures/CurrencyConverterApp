package com.example.kolin.currencyconverterapp.presentation.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Util class to format values
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

    public static String formateDate(DateFormat sdf, long time){
        return sdf.format(new Date(time));
    }

    public static String formateDateRange (DateFormat sdf, long timeFrom, long timeTo, String delimiter){
        return String.format("%s %s %s", formateDate(sdf, timeFrom), delimiter, formateDate(sdf, timeTo));
    }

    public static Date getDate (int year, int monthOfYear, int dayOfMonth){
        Calendar now = Calendar.getInstance();

        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, monthOfYear);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        return now.getTime();
    }

}
