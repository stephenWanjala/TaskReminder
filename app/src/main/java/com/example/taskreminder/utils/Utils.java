package com.example.taskreminder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
//    format date from milliseconds as feb 2, 2022 12:00 PM
    public static String formatDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault());
        return sdf.format(new Date(millis));

    }

}
