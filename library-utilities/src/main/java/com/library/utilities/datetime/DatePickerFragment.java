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
 * DatePickerFragment.DatePickerListener datePickerListener = new DatePickerFragment.DatePickerListener() {
 *                     @Override
 *                     public void onDateSet(String selectedDate, String year, String month, String day) {
 *                         System.out.println("SELECTED FULL DATE : "+selectedDate+" YEAR : "+year+" MONTH : "+month+" DAY : "+day);
 *                     }
 *                 };
 *
 * DatePickerFragment datePickerFragment = new DatePickerFragment(datePickerListener);
 * datePickerFragment.show(getSupportFragmentManager(), "tag");
 */
public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener{

    final Calendar calendar = Calendar.getInstance();
    private DatePickerListener datePickerListener;

    public DatePickerFragment(DatePickerListener datePickerListener) {
        this.datePickerListener = datePickerListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), DatePickerFragment.this, year, month, day);
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
