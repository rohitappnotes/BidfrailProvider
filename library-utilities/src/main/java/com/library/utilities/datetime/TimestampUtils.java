package com.library.utilities.datetime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtils {

    private TimestampUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Convert String to TimeStamp
     * @param value The format of the String to be converted must be yyyy-mm-dd hh:mm:ss[.f...] This
     *              format, brackets indicate optional, otherwise an error will be reported
     * @return java.sql.Timestamp
     */
    public static Timestamp string2Timestamp(String value){
        if(value == null && !"".equals(value.trim())){
            return null;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp = Timestamp.valueOf(value);
        return timestamp;
    }

    /**
     * Convert Timestamp to String type, if format is null, use the default format yyyy-MM-dd HH:mm:ss
     *
     * @param value Timestamp to be converted
     * @param format String format
     * @return java.lang.String
     */
    public static String timestamp2String(Timestamp value,String format){
        if(null == value){
            return "";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(value);
    }

    /**
     * Date converted to Timestamp
     *
     * @param date Date to be converted
     * @return java.sql.Timestamp
     */
    public static Timestamp date2Timestamp(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * Convert Timestamp to Date
     *
     * @param time Timestamp to be converted
     * @return java.util.Date
     */
    public static Date timestamp2Date(Timestamp time){
        return time == null ? null : time;
    }
}
