package com.library.utilities.datetime;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import static java.lang.String.format;

public class DateTimeUtils {

    public static final String TAG = DateTimeUtils.class.getSimpleName();

    long yearSeconds = 31536000L; //365 * 24 * 60 * 60;
    long monthSeconds = 2592000L; //30 * 24 * 60 * 60;
    long daySeconds = 86400L; //24 * 60 * 60;
    long hourSeconds = 3600L; //60 * 60;
    long minuteSeconds = 60L;

    /**
     * 1 milliseconds equals 0.001 second
     *
     * Question : How many seconds in 1 Millisecond?
     * Answer :
     * 1 ms = 0.001 sec
     * 2 ms = 0.002 sec
     * 9 ms = 0.009 sec
     *
     * 10 ms = 0.01 sec
     * 20 ms = 0.02 sec
     * 90 ms = 0.09 sec
     *
     * 100 ms = 0.1 sec
     * 200 ms = 0.2 sec
     * 900 ms = 0.9 sec
     *
     * 1000 ms = 1 sec
     *
     * Question : How to convert milliseconds to seconds?
     * Answer : 1 Millisecond is equal to 0.001 second. To convert milliseconds to seconds, multiply the millisecond value by 0.001 or divide by 1000.
     *
     * Milliseconds to seconds formula :
     * second = millisecond * 0.001
     * e.g., 5 * 0.001 = 0.005
     * e.g., 10 * 0.001 = 0.01
     * e.g., 100 * 0.001 = 0.1
     * e.g., 1000 * 0.001 = 1
     *
     * second = millisecond / 1000
     * e.g., 5 / 1000 = 0.005
     * e.g., 10 / 1000 = 0.01
     * e.g., 100 / 1000 = 0.1
     * e.g., 1000 / 1000 = 1
     */
    public static final double ONE_MILLISECONDS_IN_SECOND = 0.001;

    /**
     * Question : How to convert seconds to milliseconds?
     * Answer : 1 Second is equal to 1000 milliseconds. To convert seconds to milliseconds, multiply the second value by 1000.
     *
     * Seconds to Milliseconds formula :
     * milliseconds = second * 1000
     * e.g., 5 * 1000 = 5000
     * e.g., 10 * 1000 = 10000
     *
     * 1000 milliseconds equals 1 second
     *
     * Example :
     * 1 sec  = 1000 ms
     * 2 sec  = 2000 ms
     * 60 sec = 60000 ms
     */
    public static final int ONE_SECOND_MILLISECONDS = 1000;

    /**
     * 60000 milliseconds equals 1 Minute
     *
     * Example :
     * 1 min  = 60000 ms
     * 1 min  = 60 sec
     *
     * Here 1 minute equal to 60 second
     */
    public static final int ONE_MINUTE_MILLISECONDS = 1000 * 60;

    /**
     * 3600000 milliseconds equals 1 Hour
     *
     * Example :
     * 1 hour  = 3600000 ms
     * 1 hour  = 3600 sec
     * 1 hour  = 60 min
     *
     * Here 1 hour equal to 60 minute
     */
    public static final int ONE_HOUR_MILLISECONDS = 1000 * 60 * 60;

    /**
     * 86400000 milliseconds equals 1 Day
     *
     * Example :
     * 1 day  = 86400000 ms
     * 1 day  = 86400 sec
     * 1 day  = 1440 min
     * 1 day  = 24 hour
     *
     * Here 1 hour equal to 3600 second
     */
    public static final long ONE_DAY_MILLISECONDS = 1000 * 60 * 60 * 24;

    public enum TimeUnit {
        MILLISECONDS,
        SECONDS,
        MINUTE,
        HOUR,
        DAY
    }

    /**
     * Date and Time Format
     *
     * https://developer.android.com/reference/java/text/SimpleDateFormat
     * https://developer.android.com/reference/android/icu/text/SimpleDateFormat
     */
    public static final String DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss"; /* OUTPUT : 2018-12-05 10:37:43 */
    public static final String DATE_TIME_FORMAT_2 = "dd/MM/yyyy HH:mm:ss"; /* OUTPUT :  */
    public static final String DATE_TIME_FORMAT_3 = "dd-MM-YYYY HH.mm.ss"; /* OUTPUT :  */
    public static final String DATE_TIME_FORMAT_4 = "h:mm a dd MMMM yyyy"; /* OUTPUT : 10:37 am 05 December 2018 */
    public static final String DATE_TIME_FORMAT_5 = "E, dd MMM yyyy HH:mm:ss z"; /* OUTPUT : Wed, 05 Dec 2018 10:37:43 UTC */
    public static final String DATE_TIME_FORMAT_6 = "EEE, d MMM yyyy HH:mm:ss Z"; /* OUTPUT : Wed, 5 Dec 2018 10:37:43 +0000 */
    public static final String DATE_TIME_FORMAT_7 = "yyyy-MM-dd HH:mm:ss"; /* OUTPUT :  */

    public static final String DATE_FORMAT_1 = "dd/MM/yyyy"; /* OUTPUT :  */
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd"; /* OUTPUT : 2018-12-05 */
    public static final String DATE_FORMAT_3 = "dd-MMMM-yyyy"; /* OUTPUT : 05-December-2018 */
    public static final String DATE_FORMAT_4 = "dd MMMM yyyy"; /* OUTPUT : 05 December 2018 */
    public static final String DATE_FORMAT_5 = "dd MMMM yyyy zzzz"; /* OUTPUT : 05 December 2018 UTC */
    public static final String DATE_FORMAT_6 = "EEE, MMM d, ''yy"; /* OUTPUT : Wed, Dec 5, '18 */
    public static final String DATE_FORMAT_7 = "dd-MMM-yyyy"; /* OUTPUT : 05-Dec-2018 */

    public static final String TIME_FORMAT_1 = "HH:mm:ss"; /* OUTPUT :  */
    public static final String TIME_FORMAT_2 = "hh:mm a"; /* OUTPUT : 10:37 am */
    public static final String TIME_FORMAT_3 = "h:mm a"; /* OUTPUT : 10:37 am */
    public static final String TIME_FORMAT_4 = "K:mm a, z"; /* OUTPUT : 10:37 am, UTC */
    public static final String TIME_FORMAT_5 = "hh 'o''clock' a, zzzz"; /* OUTPUT : 10 o'clock am, UTC */

    private static SimpleDateFormat formatDate      = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat formatDay       = new SimpleDateFormat("d", Locale.getDefault());
    private static SimpleDateFormat formatMonthDay  = new SimpleDateFormat("M-d", Locale.getDefault());
    private static SimpleDateFormat formatDateTime  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private DateTimeUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Get the current TimeZone
     *
     * @return
     */
    public static TimeZone getTimeZone() {
        Calendar calendar = Calendar.getInstance();

        TimeZone timeZone = calendar.getTimeZone();
        Log.i(TAG, timeZone.getDisplayName());

        return timeZone;
    }

    /**
     * Get the current date
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        simpleDateFormat.setTimeZone(getTimeZone());

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        return simpleDateFormat.format(date);
    }

    /**
     * Get the current time
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT_1);
        simpleDateFormat.setTimeZone(getTimeZone());

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        return simpleDateFormat.format(date);
    }

    /**
     * Get the yesterday date
     *
     * @return
     */
    public static String getYesterdayDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        simpleDateFormat.setTimeZone(getTimeZone());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterdayDate = calendar.getTime();

        return simpleDateFormat.format(yesterdayDate);
    }

    /**
     * Get the tomorrow date
     *
     * @return
     */
    public static String getTomorrowDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        simpleDateFormat.setTimeZone(getTimeZone());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrowDate = calendar.getTime();

        return simpleDateFormat.format(tomorrowDate);
    }

    /**
     * Get the current year
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Get year
     *
     * @param date Date object
     * @return year
     */
    public static int getYearOfThisDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Get the current month
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Get month
     *
     * @param date Date object
     * @return month
     */
    public static int getMonthOfThisDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Get the current day
     *
     * @return
     */
    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the day
     *
     * @param date Date object
     * @return day
     */
    public static int getDayOfThisDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the current time in the first few weeks of the month
     *
     * @return WeekOfMonth
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week;
    }

    /**
     * Get the current time on the day of the week
     *
     * @return DayOfWeek
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }

    /**
     * Convert time from one format to another format.
     *
     * @param oldFormat old format(such as: yyyy-MM-dd HH:mm:ss)
     * @param newFormat new format(such as: yyyy/MM/dd HH:mm:ss)
     * @param time      old format time
     * @return new format time
     */
    public static String convertFormat(String oldFormat, String newFormat, String time) {
        SimpleDateFormat oldSimpleDateFormat = new SimpleDateFormat(oldFormat);
        SimpleDateFormat newSimpleDateFormat = new SimpleDateFormat(newFormat);

        try {
            return newSimpleDateFormat.format(oldSimpleDateFormat.parse(time));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Convert milliseconds to date.
     *
     * @param milliSeconds Date in milliseconds ( long timeInMilliseconds = date.getTime(); )
     * @param dateFormat Date format
     * @return String representing date in specified format
     */
    public static String getDate(long milliSeconds, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Date date = calendar.getTime();
        return simpleDateFormat.format(date);
    }

    /**
     * Convert date to milliseconds
     *
     * @param dateString Date in string
     * @param dateFormat Date format
     * @return milliseconds
     */
    public static long milliseconds(String dateString, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_3);
        try {
            Date date = simpleDateFormat.parse(dateString);
            long timeInMilliseconds = date.getTime();
            System.out.println("Date in milli : " + timeInMilliseconds);
            return timeInMilliseconds;
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return 0;
    }

    /**
     * converting long duration to  hh:mm:ss
     *
     * @param millie
     */
    public static String convertMillieToHMmSs(long millie) {
        String audioTime;

        long seconds = (millie / 1000);
        long second = seconds % 60;
        long minute = (seconds / 60) % 60;
        long hour = (seconds / (60 * 60)) % 24;

        if (hour > 0) {
            audioTime = format("%02d:%02d:%02d", hour, minute, second);
        } else {
            audioTime = format("%02d:%02d" , minute, second);
        }
        return audioTime;
    }
}
