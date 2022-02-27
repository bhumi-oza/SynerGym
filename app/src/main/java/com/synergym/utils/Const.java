package com.synergym.utils;

import android.text.format.DateFormat;

import java.util.Date;

public class Const {

    public static String currentDate() {
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());
        String date = s.toString();
        return date;
    }
}
