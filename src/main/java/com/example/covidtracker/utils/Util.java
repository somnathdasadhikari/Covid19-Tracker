package com.example.covidtracker.utils;

import java.util.StringJoiner;

public final class Util {

    /**
     * Converts a date of the form yyyy-MM-ddTHH:MM:SSZ
     * @param dateAndTime the String from the API that contains both the date and the time
     * @return a date of the form dd-MM-yyyy
     */
    public static String formatDate(String dateAndTime) {
        String date;

        String[] split = dateAndTime.split("T");
        date = split[0];

        String[] yearMonthDay = date.split("-");
        String year = yearMonthDay[0];
        String month = yearMonthDay[1];
        String day = yearMonthDay[2];
        String[] dayMonthYear = {day, month, year};

        StringJoiner joiner = new StringJoiner("-");
        for(String s : dayMonthYear) {
            joiner.add(s);
        }

        date = joiner.toString();
        return date;
    }
}