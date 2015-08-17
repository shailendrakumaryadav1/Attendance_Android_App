package com.example.sky.attendance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

// both passwords are password
public class MainActivity extends ActionBarActivity {

    LinearLayout background;
    EditText name,rollno,yr,sem,colname;
    Button cont;
    private static final String PREFS = "prefs";
    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);

        if(mSharedPreferences.getString("initialized","null").equals("true"))
        {
            finish();
            Intent intent=new Intent(MainActivity.this,displaycurrentattendancejava.class);
            startActivity(intent);
            return;
        }


        /*
        setContentView(R.layout.welcome);
        try {
            Thread.sleep(0);
        }
        catch (Exception e){}
        */

        getSupportActionBar().setTitle("Personal Details");
        setContentView(R.layout.activity_main);

        cont=(Button) findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=(EditText)findViewById(R.id.name);
                rollno=(EditText)findViewById(R.id.rollno);
                yr=(EditText)findViewById(R.id.yr);
                sem=(EditText)findViewById(R.id.sem);
                colname=(EditText)findViewById(R.id.colname);
                background=(LinearLayout)findViewById(R.id.background);

                //if(false)
                    //name.length()==0||rollno.length()==0||yr.length()==0||
                    //sem.length()==0||colname.length()==0
                if(name.length()==0||rollno.length()==0||yr.length()==0||
                        sem.length()==0||colname.length()==0)
                {

                    Context context = getApplicationContext();
                    CharSequence text = "Fields can't be left empty";



                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();



                }
                else
                {

                    saveDetails();

                    background.setBackgroundColor(Color.parseColor("#ff0000"));
                    for(long i=0;i<100000;i++);
                    Intent intent=new Intent(MainActivity.this,inputsubjectjava.class);
                    startActivity(intent);

                }
            }

        });


    }
    public void saveDetails()
    {
        SharedPreferences.Editor e = mSharedPreferences.edit();
        e.putString("name", name.getText().toString());
        e.putString("rollno", rollno.getText().toString());
        e.putString("sem", sem.getText().toString());
        e.putString("yr", yr.getText().toString());
        e.putString("colname", colname.getText().toString());
        e.putInt("notification",0);
        e.commit();
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // Get the Camera instance as the activity achieves full user focus
        if(mSharedPreferences.getString("initialized","null").equals("true"))
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notification_settings) {
            notification_settings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void notification_settings() {
        Intent i = new Intent(this, notificationsettingsjava.class);
        startActivity(i);
    }
}
