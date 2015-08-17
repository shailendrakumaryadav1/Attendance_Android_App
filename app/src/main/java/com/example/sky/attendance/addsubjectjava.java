package com.example.sky.attendance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SKY on 5/24/2015.
 */
public class addsubjectjava extends ActionBarActivity {
    SharedPreferences shp;
    SharedPreferences.Editor e;
    EditText sn,sp,st;
    Button sd,add;
    int n,date,month,year;
    private static final String PREFS = "prefs";
    static final int DATE_PICKER_ID = 1234;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsubject);

        shp = getSharedPreferences(PREFS, MODE_PRIVATE);
        e=shp.edit();

        n = Integer.parseInt(shp.getString("noofsubj", "0"));
        sn=(EditText) findViewById(R.id.sn);
        sp=(EditText) findViewById(R.id.sp);
        st=(EditText) findViewById(R.id.st);
        sd=(Button) findViewById(R.id.sd);
        add=(Button) findViewById(R.id.add);

        sn.setHint("Subject " + (n + 1));
        Calendar c=Calendar.getInstance();
        date=c.get(Calendar.DAY_OF_MONTH);
        month=c.get(Calendar.MONTH);
        year=c.get(Calendar.YEAR);
        sd.setText(date + "/" + (month + 1) + "/" + year);

        sd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_ID);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_and_add_subject();
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, pickerListener, year,month,date);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            date   = selectedDay;
            // Show selected date
            sd.setText(date + "/" + (month + 1) + "/" + year);
        }
    };
    public void check_and_add_subject()
    {

        String key,value,date,d,m,y,tot,pre;
        int a,b;
        tot = st.getText().toString();
        pre = sp.getText().toString();
        if (tot.length() == 0)
            tot = "0";
        if (pre.length() == 0)
            pre = "0";
        if (Integer.parseInt(pre) > Integer.parseInt(tot)) {
            Toast.makeText(getApplicationContext(),
                    "Classes Present can't be more than Total Classes", Toast.LENGTH_LONG).show();
            sp.requestFocus();
            return;
        }
        e.putInt("t"+(n+1),Integer.parseInt(tot));
        e.putInt("p"+(n+1),Integer.parseInt(pre));

        key="subject"+(n+1);
        value=sn.getText().toString();
        if(value.length()==0)
            value="Subject "+(n+1);
        e.putString(key, value);

        date=sd.getText().toString();
        a=date.indexOf("/");
        d=date.substring(0,a);
        b=date.indexOf("/",a+1);
        m=date.substring(a+1,b);
        y=date.substring(b+1);
        e.putString("_month"+(n+1),m);
        e.putString("_day"+(n+1),d);
        e.putString("_year"+(n+1),y);




        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Attention!");
        alertDialogBuilder
                .setMessage("Are you sure to add this subject?")
                .setIcon(R.drawable.ic_action_warning)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                n=n+1;
                                e.putString("noofsubj",Integer.toString(n));
                                e.commit();
                                Toast.makeText(getApplicationContext(),
                                        "oyee noofsubj updated to "+n, Toast.LENGTH_LONG).show();

                                Toast.makeText(getApplicationContext(),
                                        "Subject Added Successfully", Toast.LENGTH_LONG).show();
                                //update _dateMIN and A fields
                                update_dateMIN_and_A_fields();


                                onBackPressed();

                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void update_dateMIN_and_A_fields()
    {

        Calendar date_start,date_today,date_n,date_min;
        int j,d_start,m_start,y_start;
        String key,value;
        date_today=Calendar.getInstance();

        date_min=Calendar.getInstance();
        date_min.set(Calendar.YEAR,Integer.parseInt(shp.getString("_yearMIN","2010")));
        date_min.set(Calendar.MONTH,Integer.parseInt(shp.getString("_monthMIN","1"))-1);
        date_min.set(Calendar.DAY_OF_MONTH,Integer.parseInt(shp.getString("_dayMIN","1")));

        date_n=Calendar.getInstance();
        date_n.set(Calendar.YEAR,Integer.parseInt(shp.getString("_year"+n,"2010")));
        date_n.set(Calendar.MONTH,Integer.parseInt(shp.getString("_month"+n,"1"))-1);
        date_n.set(Calendar.DAY_OF_MONTH,Integer.parseInt(shp.getString("_day"+n,"1")));

        if(date_n.compareTo(date_min)>0)
        {
            d_start=date_min.get(Calendar.DAY_OF_MONTH);
            m_start=date_min.get(Calendar.MONTH)+1;
            y_start=date_min.get(Calendar.YEAR);
        }
        else
        {
            e.putString("_dayMIN",Integer.toString(date_n.get(Calendar.DAY_OF_MONTH)));
            e.putString("_monthMIN",Integer.toString(date_n.get(Calendar.MONTH)+1));
            e.putString("_yearMIN",Integer.toString(date_n.get(Calendar.YEAR)));
            e.commit();

            Toast.makeText(getApplicationContext(),"Min date is: "+Integer.toString(date_n.get(Calendar.DAY_OF_MONTH))+"/"
                    +Integer.toString(date_n.get(Calendar.MONTH)+1)+"/"+
                    Integer.toString(date_n.get(Calendar.YEAR)), Toast.LENGTH_LONG).show();

            d_start=date_n.get(Calendar.DAY_OF_MONTH);
            m_start=date_n.get(Calendar.MONTH)+1;
            y_start=date_n.get(Calendar.YEAR);
        }

        date_start=Calendar.getInstance();
        date_start.set(Calendar.YEAR,y_start);
        date_start.set(Calendar.MONTH,m_start-1);
        date_start.set(Calendar.DAY_OF_MONTH,d_start);

        for(;date_today.compareTo(date_start)>=0;date_start.add(Calendar.DATE,1))
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setCalendar(date_start);
            key="A"+dateFormat.format(date_start.getTime());
            value=shp.getString(key,"0");
            for(j=value.length();j<n;j++)
                value=value+"0";
            e.putString(key,value);
            e.commit();
        }
    }
}