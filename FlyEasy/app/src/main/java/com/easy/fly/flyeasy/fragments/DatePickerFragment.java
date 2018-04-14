package com.easy.fly.flyeasy.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.easy.fly.flyeasy.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
    //AlertDialog.THEME_DEVICE_DEFAULT_LIGHT theme
        return new DatePickerDialog(getActivity(),R.style.datepicker,(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
    }
}
