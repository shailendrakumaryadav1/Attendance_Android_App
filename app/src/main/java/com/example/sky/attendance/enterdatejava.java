package com.example.sky.attendance;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by SKY on 3/26/2015.
 */
public class enterdatejava extends FragmentActivity  {

    Button btnDate, btnSubmit;
    SharedPreferences mSharedPreferences;
    private static final String PREFS = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterdate);

        btnDate = (Button) findViewById(R.id.dateButton);
        btnSubmit = (Button) findViewById(R.id.subButton);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month++;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor e = mSharedPreferences.edit();

        int i,n=Integer.parseInt(mSharedPreferences.getString("noofsubj","1"));
        String d,m,y;
        m=Integer.toString(month);
        d=Integer.toString(day);
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
        btnDate.setText(day + "/" + month + "/" + year);


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerdialogjava pickerDialogs = new pickerdialogjava();
                pickerDialogs.setButton(btnDate);
                pickerDialogs.show(getSupportFragmentManager(), "date_picker");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(enterdatejava.this, inputinitialattendancejava.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        if(mSharedPreferences.getString("initialized","null").equals("true"))
            finish();
//System.exit(0) or finish();


    }

}