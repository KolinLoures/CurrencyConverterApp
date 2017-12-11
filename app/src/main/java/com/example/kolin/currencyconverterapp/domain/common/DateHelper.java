package com.example.kolin.currencyconverterapp.domain.common;

import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by kolin on 11.12.2017.
 */

public class DateHelper {

    public static long getStartOfTheDay(Date date){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime().getTime();
    }

    public static long getEndOfTheDay(Date date){
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        now.set(Calendar.MILLISECOND, 999);
        return now.getTime().getTime();
    }

    public static long getWeekCloseDatePeriod(long timeFrom){
        long date = timeFrom - DateUtils.WEEK_IN_MILLIS;
        return getStartOfTheDay(new Date(date));
    }

    public static long getMonthCloseDatePeriod(long timeFrom){
        return timeFrom - DateUtils.DAY_IN_MILLIS * 30;
    }
}
