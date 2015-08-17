package com.example.sky.attendance;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by SKY on 3/23/2015.
 */
public class inputsubjectjava extends ActionBarActivity {

    Spinner sp;
    Button btnSubjSubmit;

    private static final String PREFS = "prefs";
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputsubject);
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        sp=(Spinner) findViewById(R.id.noofsubj);
        btnSubjSubmit=(Button) findViewById(R.id.btnSubjSubmit);
        btnSubjSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CharSequence text = sp.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

                    saveDetails();
                    Intent intent=new Intent(inputsubjectjava.this,inputsubjectnamejava.class);
                    startActivity(intent);

                }
        });
    }
    public void saveDetails()
    {


        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString("noofsubj", sp.getSelectedItem().toString());
        e.commit();
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        if(mSharedPreferences.getString("initialized","null").equals("true"))
            finish();
    }


}
