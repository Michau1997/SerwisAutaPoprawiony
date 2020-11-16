package com.example.serwisauta2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText car_oil_date;
    public DateDialog(View view) {
        car_oil_date = (EditText)view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this,year,month,day);
    }
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
        String date = day+"-"+(month+1)+"-"+year;
        car_oil_date.setText(date);
    }



}
