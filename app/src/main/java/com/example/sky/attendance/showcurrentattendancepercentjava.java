package com.example.sky.attendance;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by SKY on 4/5/2015.
 */

public class showcurrentattendancepercentjava extends ActionBarActivity {

    SharedPreferences sp;
    SharedPreferences.Editor e;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("prefs", MODE_PRIVATE);
        e=sp.edit();
        ScrollView sv = new ScrollView(this);
        sv.setFillViewport(true);
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setBackgroundColor(Color.parseColor("#000000"));
        sv.addView(ll);

        setContentView(sv);

        Button name,status;
        name=new Button(this);
        status=new Button(this);
        name.setText(sp.getString("name", "null"));
        name.setBackgroundColor(Color.parseColor("#43FFFD"));
        name.setTextColor(Color.parseColor("#ff0000"));
        name.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        status.setText("Current Percent Stats");
        status.setBackgroundColor(Color.parseColor("#43FFFD"));
        status.setTextColor(Color.parseColor("#ff0000"));
        status.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(10,10,10,10);
        ll.addView(name,llp);
        ll.addView(status,llp);


        TableRow.LayoutParams lp = new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 20, 20, 20);

        TableLayout tl=new TableLayout(this);
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);
        //tl.setBackgroundColor(Color.parseColor("#ffff00"));
        ll.addView(tl);
        int i,j,n,num,den;
        double ans;
        String s,st;
        Character ch;
        n=Integer.parseInt(sp.getString("noofsubj", "1"));

        int t[]=new int[n];
        int p[]=new int[n];
        for(i=1;i<=n;i++)
        {
            p[i-1]= sp.getInt("p" + i, 0);
            t[i-1]= sp.getInt("t"+i,0);
        }

        Calendar cali,calf;
        calf=Calendar.getInstance();
        cali=Calendar.getInstance();
        cali.set(Calendar.YEAR,Integer.parseInt(sp.getString("_yearMIN","2010")));
        cali.set(Calendar.MONTH,Integer.parseInt(sp.getString("_monthMIN","1"))-1);
        cali.set(Calendar.DAY_OF_MONTH,Integer.parseInt(sp.getString("_dayMIN","1")));


        for(;calf.compareTo(cali)>=0;cali.add(Calendar.DATE,1))
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setCalendar(cali);
            s=dateFormat.format(cali.getTime());
            st="A"+s;
            s=sp.getString(st,"0");
            for(j=s.length();j<n;j++)
                s=s+"0";
            e.putString(st,s);
            e.commit();
        }

        for(i=0;i<n;i++)
        {
            calf=Calendar.getInstance();
            cali=Calendar.getInstance();
            cali.set(Calendar.YEAR,Integer.parseInt(sp.getString("_year"+(i+1),"2010")));
            cali.set(Calendar.MONTH,Integer.parseInt(sp.getString("_month"+(i+1),"1"))-1);
            cali.set(Calendar.DAY_OF_MONTH,Integer.parseInt(sp.getString("_day"+(i+1),"1")));

            for(;calf.compareTo(cali)>=0;cali.add(Calendar.DATE,1))
            {

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setCalendar(cali);
                s=dateFormat.format(cali.getTime());
                st="A"+s;
                //s=hm.get(st);

                s=sp.getString(st,"0");
                for(j=s.length();j<n;j++)
                    s=s+"0";
                ch=s.charAt(i);
                if(ch=='0')
                {
                }
                else if(ch=='1')
                {
                    p[i]++;
                    t[i]++;
                }
                else if(ch=='2')
                {
                    t[i]++;
                }
                e.putString(st,s);
                e.commit();
            }

        }

        TableRow tr[]=new TableRow[n];
        for(i=0;i<n;i++) {
            tr[i] = new TableRow(this);
            TextView sntv=new TextView(this);
            TextView per=new TextView(this);

            sntv.setText(sp.getString("subject" + (i + 1), "null"));

            num = p[i];
            den = t[i];
            if (den == 0)
                ans = 0.0;
            else
                ans = num * 100.0 / den;

            ans=(Math.rint(ans*100))/100;


            per.setText(ans + "%");
            //sntv.setLayoutParams(lp);
            //per.setLayoutParams(lp);
            sntv.setBackgroundColor(Color.parseColor("#ffff00"));
            per.setBackgroundColor(Color.parseColor("#ffff00"));
            sntv.setTextSize(20);
            per.setTextSize(20);
            tr[i].addView(sntv,lp);
            tr[i].addView(per,lp);
            tl.addView(tr[i]);
        }
    }
}
