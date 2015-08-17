package com.example.sky.attendance;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by SKY on 3/24/2015.
 */
public class inputsubjectnamejava extends ActionBarActivity {

    SharedPreferences sp;
    private static final String PREFS="prefs";
    EditText et[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp=getSharedPreferences(PREFS,MODE_PRIVATE);

        ScrollView sv = new ScrollView(this);
        sv.setBackgroundColor(Color.WHITE);
        setContentView(sv);
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        int i,n,maxLen=15;
        n=Integer.parseInt(sp.getString("noofsubj","1"));

        et=new EditText[n];
        for(i=0;i<n;i++) {

            et[i] = new EditText(this);
            et[i].setBackgroundColor(Color.parseColor("#652FFF0C"));
            et[i].setHint("Subject " + (i + 1));
            et[i].setHintTextColor(Color.parseColor("#ff0000"));
            et[i].setTextColor(Color.parseColor("#0000ff"));
            et[i].setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLen)});
            et[i].setGravity(Gravity.CENTER);
            ll.addView(et[i]);
        }

        Button submit=new Button(this);
        submit.setText("SUBMIT");
        submit.setTextColor(Color.parseColor("#f05699"));


        ll.addView(submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SharedPreferences.Editor e= sp.edit();
                int i,n=Integer.parseInt(sp.getString("noofsubj","1"));
                for(i=0;i<n;i++)
                {
                    String key,value;
                    key="subject"+(i+1);
                    value=et[i].getText().toString();

                    if(value.length()==0)
                        value="Subject "+(i+1);
                    e.putString(key, value);
                }
                e.commit();
                Intent intent=new Intent(inputsubjectnamejava.this,enterdatejava.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        if(sp.getString("initialized","null").equals("true"))
            finish();
    }
}
