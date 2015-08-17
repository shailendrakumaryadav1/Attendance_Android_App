package com.example.sky.attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
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

/**
 * Created by SKY on 3/29/2015.
 */
public class displaypercentattendancejava extends ActionBarActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("prefs", MODE_PRIVATE);
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
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        p.setMargins(10,10,10,10);
        ll.addView(name,p);
        ll.addView(status,p);


        TableRow.LayoutParams lp = new
                TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 20, 20, 20);

        TableLayout tl=new TableLayout(this);
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);
        //tl.setBackgroundColor(Color.parseColor("#ffff00"));
        ll.addView(tl);
        int i,n,num,den;
        double ans;
        n=Integer.parseInt(sp.getString("noofsubj", "1"));
        TableRow tr[]=new TableRow[n];

        for(i=0;i<n;i++) {
            tr[i] = new TableRow(this);
            TextView sntv=new TextView(this);

            TextView per=new TextView(this);
            sntv.setText(sp.getString("subject" + (i + 1), "null"));

            num = sp.getInt("p" + (i + 1), 0);
            den = sp.getInt("t" + (i + 1), 0);
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
        Button btnFin=new Button(this);
        btnFin.setText("FINISH");
        //btnFin.setText(sp.getString("initialized","null").equals("true")+"-"+"ABCabc");
        btnFin.setTextColor(Color.parseColor("#000000"));
        btnFin.setBackgroundColor(Color.parseColor("#00ff00"));
        tl.addView(btnFin);
        btnFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action();
            }
        });
    }
    public void action()
    {
        SharedPreferences.Editor e=sp.edit();

        e.putString("initialized","true");
        e.commit();
        //Intent intent=new Intent(displaypercentattendancejava.this,displaycurrentattendancejava.class);
        //startActivity(intent);
        onBackPressed();

    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        if(sp.getString("initialized","null").equals("true"))
            finish();
    }
}
