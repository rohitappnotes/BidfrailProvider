package com.library.utilities.datetime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Used like this :
 *
 * TimePickerFragment.TimePickerListener timePickerListener = new TimePickerFragment.TimePickerListener() {
 *                     @Override
 *                     public void onTimeSet(String selectedTime, String hour, String minute) {
 *                         System.out.println("SELECTED FULL TIME : "+selectedTime+" HOUR : "+hour+" MINUTE : "+minute);
 *                     }
 *                 };
 *
 * TimePickerFragment timePickerFragment = new TimePickerFragment(timePickerListener);
 * timePickerFragment.show(getSupportFragmentManager(), "tag");
 */
public class TimePickerFragment extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {

    final Calendar calendar = Calendar.getInstance();
    private TimePickerListener timePickerListener;

    public TimePickerFragment(TimePickerListener timePickerListener) {
        this.timePickerListener = timePickerListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);

        String currentHourWithAmOrPm = calendar.get(Calendar.HOUR) + ((calendar.get(Calendar.AM_PM) == Calendar.AM) ? "am" : "pm");
        String selectedTime = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH).format(calendar.getTime());

        timePickerListener.onTimeSet(selectedTime, currentHourWithAmOrPm, String.valueOf(minute));
    }

    public interface TimePickerListener {
        void onTimeSet(String selectedTime, String hour, String minute);
    }
}
