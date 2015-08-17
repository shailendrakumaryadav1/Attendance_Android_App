package com.example.sky.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by SKY on 5/24/2015.
 */
public class removesubjectjava extends ActionBarActivity {
    SharedPreferences shp;
    SharedPreferences.Editor e;
    Spinner sp;
    Button del;
    int nd,n;
    public static final String PREFS = "prefs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removesubject);

        int i;

        shp = getSharedPreferences(PREFS, MODE_PRIVATE);
        e = shp.edit();
        n=Integer.parseInt(shp.getString("noofsubj", "1"));
        sp=(Spinner) findViewById(R.id.subjno);
        del=(Button) findViewById(R.id.del);




        List< String> namelist = new ArrayList< String>();

        for(i=0;i<n;i++)
        namelist.add(shp.getString("subject" + (i + 1), "null"));

        ArrayAdapter< String> aasp =new ArrayAdapter< String>
                (this, android.R.layout.simple_spinner_item,namelist);
        aasp.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aasp);
        sp.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onNothingSelected(AdapterView<?> arg0) { }
                    public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
//  Code that does something when the Spinner value changes
                        //Toast.makeText(getApplicationContext(),position+" choosen",Toast.LENGTH_LONG).show();
                        nd=position+1;
                    }
                });


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(n==1)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(removesubjectjava.this);
                    alertDialogBuilder.setTitle("Attention!");
                    alertDialogBuilder
                            .setMessage("The Only Subject can't be Deleted.\nAtleast One Subject is must for your Records")
                            .setIcon(R.drawable.ic_action_warning)
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(removesubjectjava.this);
                    alertDialogBuilder.setTitle("Attention!");
                    alertDialogBuilder
                            .setMessage("Delete Subject- "+shp.getString("subject"+nd,"null"))
                            .setIcon(R.drawable.ic_action_warning)
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {


                                            delete_subject();
                                            Toast.makeText(getApplicationContext(),
                                                    "Subject Deleted Successfully", Toast.LENGTH_LONG).show();

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

            }
        });

    }
    public void delete_subject()
    {

        Calendar date_i,date_f;
        String key,value;
        int i,j;

        date_i=Calendar.getInstance();
        date_i.set(Calendar.YEAR,Integer.parseInt(shp.getString("_yearMIN","2010")));
        date_i.set(Calendar.MONTH,Integer.parseInt(shp.getString("_monthMIN","1"))-1);
        date_i.set(Calendar.DAY_OF_MONTH,Integer.parseInt(shp.getString("_dayMIN","1")));

        date_f=Calendar.getInstance();
        date_f.set(Calendar.YEAR,Integer.parseInt(shp.getString("_yearMAX","2010")));
        date_f.set(Calendar.MONTH,Integer.parseInt(shp.getString("_monthMAX","1"))-1);
        date_f.set(Calendar.DAY_OF_MONTH,Integer.parseInt(shp.getString("_dayMAX","1")));

        for(;date_f.compareTo(date_i)>=0;date_i.add(Calendar.DATE,1))
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setCalendar(date_i);
            key="A"+dateFormat.format(date_i.getTime());
            value=shp.getString(key,"0");
            for(j=value.length();j<n;j++)
                value=value+"0";

            value=value.substring(0,nd-1)+value.substring(nd);
            e.putString(key,value);
            e.commit();
        }

        for(i=nd;i<n;i++)
        {
            e.putString("subject"+i, shp.getString("subject"+(i+1),"null") );
            e.putInt("t"+i, shp.getInt("t"+(i+1),0) );
            e.putInt("p"+i, shp.getInt("p"+(i+1),0) );
            e.putString("_day"+i, shp.getString("_day"+(i+1),"1") );
            e.putString("_month"+i, shp.getString("_month"+(i+1),"1") );
            e.putString("_year"+i, shp.getString("_year"+(i+1),"2010") );
            e.commit();

        }
        e.remove("subject"+n);
        e.remove("t"+n);
        e.remove("p"+n);
        e.remove("_day"+n);
        e.remove("_month"+n);
        e.remove("_year"+n);
        e.commit();

        n=n-1;
        e.putString("noofsubj",Integer.toString(n));
        e.commit();

    }
}
