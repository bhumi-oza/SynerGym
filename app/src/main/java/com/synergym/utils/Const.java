package com.synergym.utils;

import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Const {

    public static String currentDate() {
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());
        String date = s.toString();
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String changeDateFormate(String apiData) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime result = ZonedDateTime.parse(apiData, formatter);
        String date  =  result.getYear()+"-"+result.getMonthValue()+"-"+result.getDayOfMonth();
        return date;
    }

}
