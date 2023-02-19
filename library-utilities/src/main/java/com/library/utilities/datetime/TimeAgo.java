package com.library.utilities.datetime;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo {

    /**
     * try {
     *             // get last modified time
     *             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     *             Date startDate = simpleDateFormat.parse("2022-11-27 15:30:00");
     *             TimeAgo timeAgo =new TimeAgo();
     *             System.out.println("==========="+timeAgo.getTimeAgo(startDate.getTime()));
     *         } catch (ParseException e) {
     *             e.printStackTrace();
     *         }
     *
     *
     * @param duration
     * @return
     */
    public String getTimeAgo(long duration){
        Date now = new Date();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - duration);
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - duration);
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - duration);

        if (seconds < 60){
            return "just now";
        }
        else if (minutes == 1){
            return "a minute ago";
        }
        else if (minutes > 1 && minutes < 60){
            return minutes+"minutes ago";
        }
        else if (hours == 1){
            return "an hour ago";
        }
        else if (hours > 1 && hours < 24){
            return hours + "hours ago";
        }
        else if (days == 1){
            return "a day ago";
        }
        else {
            return days + "days ago";
        }

    }

    public String convertFromLongToTime(long duration){
        Date now = new Date();

        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - duration);
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - duration);
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - duration);

        if(seconds < 60)
            return "just now";
        else if (minutes == 1)
            return "a minute ago";
        else if (minutes > 1 && minutes < 60)
            return minutes + " minutes ago";
        else if (hours == 1)
            return "an hour ago";
        else if (hours > 1 && hours < 24)
            return hours + " hours ago";
        else if (days == 1)
            return "a day ago";
        else
            return days + " days ago";


    }

}
