package com.example.sky.attendance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Button;

import java.util.Calendar;

/**
 * Created by SKY on 3/24/2015.
 */
public class pickerdialogjava extends DialogFragment {

    Button btnDate;
    public void setButton(Button b)
    {
        btnDate=b;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        datesettingsjava dateSettings=new datesettingsjava(getActivity());
        dateSettings.setButton(btnDate);

        Calendar calendar= Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog;

        dialog=new DatePickerDialog(getActivity(),dateSettings,year,month,day);

        return dialog;
    }
}
