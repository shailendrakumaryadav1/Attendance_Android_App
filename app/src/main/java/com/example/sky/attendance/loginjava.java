package com.example.sky.attendance;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by SKY on 3/22/2015.
 */
public class loginjava extends FragmentActivity {

    private static final String PREFS = "prefs";

    SharedPreferences mSharedPreferences;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        name = (TextView) findViewById(R.id.name);
        mSharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        name.setText(Integer.toString(mSharedPreferences.getInt("t1", 2500)));

    }

    public void setDate(View view) {
        pickerdialogjava pickerDialogs = new pickerdialogjava();
        pickerDialogs.show(getSupportFragmentManager(), "date_picker");

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setIcon(R.drawable.ic_action_warning)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);

                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
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
    @Override
    protected void onDestroy() {

        finish();

        super.onDestroy();
    }

}

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
         Applicatio
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
    // your code.
}
     */
