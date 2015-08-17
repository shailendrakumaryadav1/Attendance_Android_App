package com.example.sky.attendance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by SKY on 3/27/2015.
 */
public class inputinitialattendancejava extends ActionBarActivity {

    SharedPreferences sp;
    int n;

    EditText p[];
    EditText t[];
    Button subBtn;
    private static final String PREFS = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView sv = new ScrollView(this);
        setContentView(sv);
        sp = getSharedPreferences(PREFS, MODE_PRIVATE);
        int i,maxLen=6;
        n = Integer.parseInt(sp.getString("noofsubj", "0"));

        String dmy = sp.getString("_day", "1") + "/" + sp.getString("_month", "1") + "/" + sp.getString("_year", "1990");

        LinearLayout.LayoutParams lp = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 20, 20, 20);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        TextView tv = new TextView(this);
        tv.setText("As discussed earlier enter your initial attendance prior to " + dmy + ".\n\nYou will " +
                "start entering your attendance from this date in further windows\n\nDefault values will be set to 0");
        tv.setTextColor(Color.BLACK);
        tv.setLayoutParams(lp);
        tv.setBackgroundColor(Color.parseColor("#ffff00"));

        ll.addView(tv);
//


        Button b[] = new Button[n];
        p = new EditText[n];
        t = new EditText[n];

        for (i = 0; i < n; i++)
        {
            b[i] = new Button(this);
            p[i] = new EditText(this);
            t[i] = new EditText(this);


            b[i].setLayoutParams(lp);
            b[i].setTextColor(Color.parseColor("#00ff00"));
            b[i].setBackgroundColor(Color.parseColor("#eeeeee"));
            b[i].setText(sp.getString("subject" + (i + 1), "null"));
            ll.addView(b[i]);

            p[i].setHint("Classes Present");
            p[i].setHintTextColor(Color.parseColor("#678765"));
            p[i].setBackgroundColor(Color.parseColor("#FFFFF0F5"));
            p[i].setTextColor(Color.parseColor("#ff0000"));
            p[i].setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLen)});
            p[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            ll.addView(p[i]);


            t[i].setHint("Total Classes");
            t[i].setHintTextColor(Color.parseColor("#678765"));
            t[i].setBackgroundColor(Color.parseColor("#FFFFF0F5"));
            t[i].setTextColor(Color.parseColor("#ff0000"));
            t[i].setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLen)});
            t[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            ll.addView(t[i]);

        }
        subBtn=new Button(this);
        subBtn.setTextColor(Color.parseColor("#00ff00"));
        subBtn.setBackgroundColor(Color.parseColor("#eeeeee"));
        subBtn.setText("SUBMIT");
        ll.addView(subBtn);



        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action();
            }
        });


    }
    public void action()
    {
        int i;
        SharedPreferences.Editor e=sp.edit();
        String pre,tot;

        for(i=0;i<n;i++)
        {
            tot=t[i].getText().toString();
            pre=p[i].getText().toString();
            if(tot.length()==0)
                tot="0";
            if(pre.length()==0)
                pre="0";
            if(Integer.parseInt(pre)>Integer.parseInt(tot))
            {
                Toast.makeText(getApplicationContext(),
                        "Classes Present can't be more than Total Classes", Toast.LENGTH_LONG).show();
                //t[i].setText("");
                //p[i].setText("");
                p[i].requestFocus();
                return;

            }
            e.putInt("t"+(i+1),Integer.parseInt(tot));
            e.putInt("p"+(i+1),Integer.parseInt(pre));
        }
        e.commit();
        Intent intent=new Intent(inputinitialattendancejava.this,displaypercentattendancejava.class);
        startActivity(intent);
        //if(sp.getString("initialized","null").equals("true"))

    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        if(sp.getString("initialized","null").equals("true"))
            finish();
    }

}
