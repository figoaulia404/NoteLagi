package com.example.notelagi.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteUtils {

    public static String dateFromLong(long time){
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy 'at' hh:mm aaa", Locale.US);
        return dateFormat.format(new Date(time));
    }
}
