package com.check.gf.gfapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wqd on 2018/1/15.
 */

public class TimeUtils {

    // yyyy-MM-dd HH:mm:ss
    public static final SimpleDateFormat ALLFORMAT_OF_24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 24h
     * get yyyy-MM-dd hh:mm:ss format time string.
     *
     * @param millis current time millis
     * @return 0 if parsing error.
     */
    public static String getAllFormat24Str(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return ALLFORMAT_OF_24.format(calendar.getTime());
    }
}
