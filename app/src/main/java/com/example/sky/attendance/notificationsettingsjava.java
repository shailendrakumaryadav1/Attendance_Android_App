package com.example.sky.attendance;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;


import java.sql.Time;
import java.util.Calendar;

/**
 * Created by SKY on 5/26/2015.
 */
public class notificationsettingsjava extends ActionBarActivity {

    LinearLayout ll;
    Button btnSave;
    TimePicker timePicker;
    RadioGroup rg;
    RadioButton rb0,rb1;
    AlarmManager am;
    SharedPreferences shp;
    SharedPreferences.Editor e;
    private static final String PREFS = "prefs";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        shp = getSharedPreferences(PREFS, MODE_PRIVATE);
        e=shp.edit();
        //getSupportActionBar().setTitle("oyee2");
        setContentView(R.layout.notificationsettings);
        ll=(LinearLayout) findViewById(R.id.ll);

        timePicker=(TimePicker) findViewById(R.id.timePicker);

        ll.setVisibility(View.GONE);

        rg=(RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.rb0) {
                    ll.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), "choice: No Notification",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.rb1) {
                    ll.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "choice: Once A Day",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        rb0=(RadioButton) findViewById(R.id.rb0);
        rb1=(RadioButton) findViewById(R.id.rb1);
        btnSave=(Button) findViewById(R.id.save);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int selectedId = rg.getCheckedRadioButtonId();

                // find which radioButton is checked by id
                if(selectedId == rb0.getId()) {

                    e.putInt("notification",0);
                    e.commit();
                    Toast.makeText(getApplicationContext(), "oye no noti",
                            Toast.LENGTH_SHORT).show();
                    //setNotification();
                } else if(selectedId == rb1.getId()) {
                    e.putInt("notification",1);
                    e.putInt("notiHrs",timePicker.getCurrentHour());
                    e.putInt("notiMin",timePicker.getCurrentMinute());
                    e.commit();
                    Toast.makeText(getApplicationContext(), "oye .. "+"hr="+timePicker.getCurrentHour()+" min="+timePicker.getCurrentMinute(),
                            Toast.LENGTH_SHORT).show();
                    //setNotification();
                }
                setNotification();
                onBackPressed();
            }
        });

    }
    public void setNotification()
    {

        int notiOp,hrs,min;

        notiOp=shp.getInt("notification",0);
        hrs=shp.getInt("notiHrs",0);
        min=shp.getInt("notiMin",0);

/*
        Intent myIntent = new Intent(displaycurrentattendancejava.this, notificationreceiverjava.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(displaycurrentattendancejava.this,
                12345, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        am  = (AlarmManager)getSystemService(ALARM_SERVICE);
*/
        am=(AlarmManager)getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(this, notificationreceiverjava.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,12345,myIntent,0);


        if(notiOp==0)
        {
            //cancel
            am.cancel(pendingIntent);
            pendingIntent.cancel();
            stopService(myIntent);
        }

        else if(notiOp==1)
        {

            Calendar present,schedule;
            present=Calendar.getInstance();
            schedule=Calendar.getInstance();
            //present.setTimeInMillis(System.currentTimeMillis());
            //schedule.setTimeInMillis(System.currentTimeMillis());

            schedule.set(Calendar.HOUR_OF_DAY,hrs);
            schedule.set(Calendar.MINUTE,min);
            schedule.set(Calendar.SECOND,0);

            if(present.compareTo(schedule)>0)
            {
                schedule.add(Calendar.DATE,1);
                Toast.makeText(getApplicationContext(),"timewasback",Toast.LENGTH_SHORT).show();
            }



            Toast.makeText(getApplicationContext(),"after "+Long.toString(schedule.getTimeInMillis()-present.getTimeInMillis())
                    +"  sys= "+System.currentTimeMillis(),Toast.LENGTH_SHORT).show();

            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + schedule.getTimeInMillis()-present.getTimeInMillis() ,
                    24*60*60*1000,pendingIntent);
            //am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
            //        2*60000,pendingIntent);

///change AlarmManager.INTERVAL_DAY
        }
    }
}