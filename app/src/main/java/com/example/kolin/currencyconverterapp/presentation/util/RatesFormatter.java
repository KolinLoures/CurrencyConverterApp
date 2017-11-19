package com.example.kolin.currencyconverterapp.presentation.util;

/**
 * Created by kolin on 19.11.2017.
 */

public class RatesFormatter {

    public static String formatRate(float f) {
        return String.format("%.2f", f);
    }

    public static String formatRate(float from, float to, String delimiter) {
        return formatRate(from) + delimiter + formatRate(to);
    }

    public static String formatRate(float from, float to, String nameFrom, String nameTo, String delimiter){
        return formatRate(from)+ " " + nameFrom + delimiter + nameTo + " " + formatRate(to);
    }

}
