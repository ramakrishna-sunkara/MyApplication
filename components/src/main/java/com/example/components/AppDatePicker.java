package com.example.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AppDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String SELECTED_YEAR = "SELECTED_YEAR";
    public static final String SELECTED_MONTH = "SELECTED_MONTH";
    public static final String SELECTED_DAY = "SELECTED_DAY";
    private AppDatePickerListener appDatePickerListener;
    private DatePickerDialog datePickerDialog;
    private Calendar maxDateCalender = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        if (getArguments() != null) {
            yy = getArguments().getInt(SELECTED_YEAR);
            mm = getArguments().getInt(SELECTED_MONTH);
            dd = getArguments().getInt(SELECTED_DAY);
        }
        datePickerDialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
        if (maxDateCalender != null) {
            datePickerDialog.getDatePicker().setMaxDate(maxDateCalender.getTimeInMillis());
        }
        return datePickerDialog;
    }

    public void setAppDatePickerListener(AppDatePickerListener appDatePickerListener) {
        this.appDatePickerListener = appDatePickerListener;
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        if (appDatePickerListener != null) {
            appDatePickerListener.onDateSelected(yy, mm, dd);
        }
    }

    public void setMaxDate(Calendar calendar) {
        maxDateCalender = calendar;
    }

    public interface AppDatePickerListener {
        void onDateSelected(int year, int month, int day);
    }
}
