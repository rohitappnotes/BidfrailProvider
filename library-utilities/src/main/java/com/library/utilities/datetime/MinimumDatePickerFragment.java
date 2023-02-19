package com.library.utilities.datetime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Used like this :
 *
 * MinimumDatePickerFragment.DatePickerListener datePickerListener = new MinimumDatePickerFragment.DatePickerListener() {
 *                     @Override
 *                     public void onDateSet(String selectedDate, String year, String month, String day) {
 *                         System.out.println("SELECTED FULL DATE : "+selectedDate+" YEAR : "+year+" MONTH : "+month+" DAY : "+day);
 *                     }
 *                 };
 *
 * MinimumDatePickerFragment datePickerFragment = new MinimumDatePickerFragment(datePickerListener);
 * datePickerFragment.show(getSupportFragmentManager(), "tag");
 */
public class MinimumDatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener{

    Calendar calendar;
    private DatePickerListener datePickerListener;

    public MinimumDatePickerFragment(DatePickerListener datePickerListener) {
        this.datePickerListener = datePickerListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);

        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), MinimumDatePickerFragment.this, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+24*60*60*1000);
        datePickerDialog.getDatePicker().setMaxDate((calendar.getTimeInMillis())); // disable all day before current and also current day, select only three day after current day
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        String selectedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(calendar.getTime());

        datePickerListener.onDateSet(selectedDate, String.valueOf(year), String.valueOf(month), String.valueOf(day));
    }

    public interface DatePickerListener {
        void onDateSet(String selectedDate, String year, String month, String day);
    }
}
