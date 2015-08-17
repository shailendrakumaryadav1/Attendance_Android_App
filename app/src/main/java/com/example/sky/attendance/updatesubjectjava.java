package com.example.sky.attendance;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Created by SKY on 5/22/2015.
 */
public class updatesubjectjava extends ActionBarActivity {

    SharedPreferences shp;
    SharedPreferences.Editor e;
    int n,z,ind,year,month,day;

    EditText sn[];
    Button sd[];
    EditText sp[];
    EditText st[];
    Button save;
    static final int DATE_PICKER_ID = 1234;
    private static final String PREFS = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView sv = new ScrollView(this);
        setContentView(sv);
        shp = getSharedPreferences(PREFS, MODE_PRIVATE);
        e=shp.edit();
        int i,maxLenSN=15,maxLenStSp=6;
        n = Integer.parseInt(shp.getString("noofsubj", "0"));

        LinearLayout lll = new LinearLayout(this);
        lll.setOrientation(LinearLayout.VERTICAL);

        sv.addView(lll);

        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 20, 20, 20);
        lp.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;

        LinearLayout.LayoutParams lpv = new
                LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 5);

        LinearLayout ll[] = new LinearLayout[n];
        LinearLayout ll1[] = new LinearLayout[n];
        LinearLayout ll2[] = new LinearLayout[n];
        TextView sh[] = new TextView[n];
        sn = new EditText[n];
        sd = new Button[n];
        sp = new EditText[n];
        st = new EditText[n];
        TextView ss[] = new TextView[n];


        View v=new View(this);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        v.setLayoutParams(lpv);
        lll.addView(v);
        for (i = 0; i < n; i++) {

            v=new View(this);
            v.setBackgroundColor(Color.parseColor("#c0c0c0"));
            v.setLayoutParams(lpv);
            lll.addView(v);

            sh[i] = new TextView(this);
            sn[i] = new EditText(this);
            sd[i] = new Button(this);
            sp[i] = new EditText(this);
            st[i] = new EditText(this);
            ss[i] = new TextView(this);

            ll[i] = new LinearLayout(this);
            ll[i].setOrientation(LinearLayout.VERTICAL);
            ll[i].addView(sh[i]);
            lll.addView(ll[i]);

            ll1[i] = new LinearLayout(this);
            ll1[i].setOrientation(LinearLayout.HORIZONTAL);
            ll[i].addView(ll1[i]);

            ll2[i] = new LinearLayout(this);
            ll2[i].setOrientation(LinearLayout.HORIZONTAL);
            ll[i].addView(ll2[i]);

            sh[i].setText("Subject " + (i + 1) + ":");
            sh[i].setLayoutParams(lp);

            ss[i].setText("/");
            ss[i].setTextSize(30);
            ss[i].setLayoutParams(lp);

            //sn[i].setBackgroundColor(Color.parseColor("#652FFF0C"));
            sn[i].setHint("Subject " + (i + 1));
            sn[i].setHintTextColor(Color.parseColor("#678765"));
            sn[i].setTextColor(Color.parseColor("#0000ff"));
            sn[i].setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenSN)});
            sn[i].setGravity(Gravity.CENTER);
            sn[i].setText(shp.getString("subject" + (i + 1), "null"));
            sn[i].setLayoutParams(lp);
            sn[i].setMinimumWidth(400);
            sn[i].setBackgroundResource(R.drawable.button_format);


            sd[i].setText(shp.getString("_day" + (i + 1), "1") + "/" + shp.getString("_month" + (i + 1), "1") + "/" +
                    shp.getString("_year" + (i + 1), "2010"));
            sd[i].setLayoutParams(lp);


            sp[i].setHint("Classes Present (0)");
            sp[i].setHintTextColor(Color.parseColor("#678765"));
            sp[i].setBackgroundColor(Color.parseColor("#FFFFF0F5"));
            sp[i].setTextColor(Color.parseColor("#0000ff"));
            sp[i].setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLenStSp)});
            sp[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            sp[i].setText(Integer.toString(shp.getInt("p" +(i + 1), 0)));
            sp[i].setLayoutParams(lp);

            st[i].setHint("Total Classes (0)");
            st[i].setHintTextColor(Color.parseColor("#678765"));
            st[i].setBackgroundColor(Color.parseColor("#FFFFF0F5"));
            st[i].setTextColor(Color.parseColor("#0000ff"));
            st[i].setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLenStSp)});
            st[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            st[i].setText(Integer.toString(shp.getInt("t" +(i + 1), 0)));
            st[i].setLayoutParams(lp);

            ll1[i].addView(sn[i]);
            ll1[i].addView(sd[i]);
            ll2[i].addView(sp[i]);
            ll2[i].addView(ss[i]);
            ll2[i].addView(st[i]);



        }
        v=new View(this);
        v.setBackgroundColor(Color.parseColor("#c0c0c0"));
        v.setLayoutParams(lpv);
        lll.addView(v);

        save=new Button(this);
        lll.addView(save);
        save.setText("SAVE");
        save.setBackgroundColor(Color.GREEN);
        save.setLayoutParams(lp);


        //initialize date,month,year so that date picker dialog opens on that date else 1/1/1900.
        Calendar cal=Calendar.getInstance();
        day=cal.get(Calendar.DAY_OF_MONTH);
        month=cal.get(Calendar.MONTH);
        year=cal.get(Calendar.YEAR);


        for(z=0;z<n;z++)//good code part
        {
            sd[z].setOnClickListener(new View.OnClickListener() {
                int item = z;

                @Override
                public void onClick(View v) {
                    ind=item;
                    showDialog(DATE_PICKER_ID);

                    Toast.makeText(getApplicationContext(), (item+1)+" Clicked", Toast.LENGTH_LONG).show();

                }
            });

        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check_and_save_fields();
            }});



    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
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
            day   = selectedDay;
            // Show selected date
            sd[ind].setText(day+"/"+(month+1)+"/"+year);
        }
    };

    public void check_and_save_fields()
    {

        int i,a,b;
        String key,value,date,tot,pre,d,m,y;
        for(i=0;i<n;i++)
        {

            key="subject"+(i+1);
            value=sn[i].getText().toString();
            if(value.length()==0)
                value="Subject "+(i+1);
            e.putString(key, value);


            date=sd[i].getText().toString();
            a=date.indexOf("/");
            d=date.substring(0,a);
            b=date.indexOf("/",a+1);
            m=date.substring(a+1,b);
            y=date.substring(b+1);
            e.putString("_month"+(i+1),m);
            e.putString("_day"+(i+1),d);
            e.putString("_year"+(i+1),y);


            tot = st[i].getText().toString();
            pre = sp[i].getText().toString();
            if (tot.length() == 0)
                tot = "0";
            if (pre.length() == 0)
                pre = "0";
            if (Integer.parseInt(pre) > Integer.parseInt(tot)) {
                Toast.makeText(getApplicationContext(),
                        "Classes Present can't be more than Total Classes", Toast.LENGTH_LONG).show();
                //st[i].setText("");
                //sp[i].setText("");
                sp[i].requestFocus();
                return;
            }
            e.putInt("t"+(i+1),Integer.parseInt(tot));
            e.putInt("p"+(i+1),Integer.parseInt(pre));

        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Attention!");
        alertDialogBuilder
                .setMessage("Are you sure to update with these values?")
                .setIcon(R.drawable.ic_action_warning)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                e.commit();
                                Toast.makeText(getApplicationContext(),
                                        "Updated Successfully", Toast.LENGTH_LONG).show();
                                //update _dateMIN
                                update_dateMIN();
                                onBackPressed();

                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        return;
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public void update_dateMIN()
    {

        int i,d_minNew,m_minNew,y_minNew;
        Calendar date_minNew,date_minTemp;

        d_minNew=Integer.parseInt(shp.getString("_dayMIN","1"));
        m_minNew=Integer.parseInt(shp.getString("_monthMIN","1"));
        y_minNew=Integer.parseInt(shp.getString("_yearMIN","2010"));

        for(i=0;i<n;i++)
        {
            date_minNew=Calendar.getInstance();
            date_minNew.set(Calendar.YEAR,y_minNew);
            date_minNew.set(Calendar.MONTH,m_minNew-1);
            date_minNew.set(Calendar.DAY_OF_MONTH,d_minNew);

            date_minTemp=Calendar.getInstance();
            date_minTemp.set(Calendar.YEAR,Integer.parseInt(shp.getString("_year"+(i+1),"2010")));
            date_minTemp.set(Calendar.MONTH,Integer.parseInt(shp.getString("_month"+(i+1),"1"))-1);
            date_minTemp.set(Calendar.DAY_OF_MONTH,Integer.parseInt(shp.getString("_day"+(i+1),"1")));

            if(date_minNew.compareTo(date_minTemp)>0)
            {
                //date_minNew=date_minTemp;
                d_minNew=date_minTemp.get(Calendar.DAY_OF_MONTH);
                m_minNew=date_minTemp.get(Calendar.MONTH)+1;
                y_minNew=date_minTemp.get(Calendar.YEAR);
            }
        }
        Toast.makeText(getApplicationContext(), d_minNew+"/"+m_minNew+"/"+y_minNew
                , Toast.LENGTH_LONG).show();
        e.putString("_monthMIN",Integer.toString(m_minNew));
        e.putString("_dayMIN",Integer.toString(d_minNew));
        e.putString("_yearMIN",Integer.toString(y_minNew));
        e.commit();
    }
}

