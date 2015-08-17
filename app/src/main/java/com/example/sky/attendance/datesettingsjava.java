package com.example.sky.attendance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by SKY on 3/24/2015.
 */
public class datesettingsjava extends Activity implements DatePickerDialog.OnDateSetListener{

    Context context;
    Button btnDate;
    private static final String PREFS = "prefs";
    public datesettingsjava(Context context)
    {
        this.context=context;

    }
    public void setButton(Button b)
    {
        btnDate=b;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        SharedPreferences mSharedPreferences= context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e=mSharedPreferences.edit();
        int i,n=Integer.parseInt(mSharedPreferences.getString("noofsubj","1"));
        String d,m,y;
        m=Integer.toString(monthOfYear);
        d=Integer.toString(dayOfMonth);
        y=Integer.toString(year);

        e.putString("_month",m);
        e.putString("_day",d);
        e.putString("_year",y);
        e.putString("_monthMIN",m);
        e.putString("_dayMIN",d);
        e.putString("_yearMIN",y);
        e.putString("_monthMAX",m);
        e.putString("_dayMAX",d);
        e.putString("_yearMAX",y);

        for(i=0;i<n;i++)
        {
            e.putString("_month"+(i+1),m);
            e.putString("_day"+(i+1),d);
            e.putString("_year"+(i+1),y);
        }
        e.commit();
        Toast.makeText(context,"Selected Date: "+dayOfMonth+"/"+monthOfYear+"/"+year,
                Toast.LENGTH_LONG).show();
        btnDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
    }
}
