package com.example.kolin.currencyconverterapp.domain.common;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        long date = timeFrom - 6 * DateUtils.DAY_IN_MILLIS;
        return getStartOfTheDay(new Date(date));
    }

    public static long getTwoWeekCLoaseDatePeriod(long timeFrom){
        long date = timeFrom - 6 * DateUtils.DAY_IN_MILLIS * 2;
        return getStartOfTheDay(new Date(date));
    }

    public static long getMonthCloseDatePeriod(long timeFrom){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(timeFrom);
        now.add(Calendar.MONTH, -1);
        return now.getTimeInMillis();
    }

    public static List<Date> getDaysBetweenTwoDates(long timeFrom, long timeTo){
        long diff = timeTo - timeFrom;
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        List<Date> temp = new ArrayList<>();
        for (int i = 0; i <= days; i++){
            temp.add(new Date(timeFrom));
            timeFrom = timeFrom + DateUtils.DAY_IN_MILLIS;
        }
        return temp;
    }

}
