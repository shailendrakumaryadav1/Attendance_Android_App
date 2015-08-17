package com.example.sky.attendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by SKY on 5/23/2015.
 */
public class updatepersonaldetailsjava extends ActionBarActivity {

    EditText name, rollno, yr, sem, colname;
    Button btnUpdate;
    private static final String PREFS = "prefs";
    SharedPreferences shp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepersonaldetails);

        shp = getSharedPreferences(PREFS, MODE_PRIVATE);



        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        name = (EditText) findViewById(R.id.name);
        rollno = (EditText) findViewById(R.id.rollno);
        sem = (EditText) findViewById(R.id.sem);
        yr = (EditText) findViewById(R.id.yr);
        colname = (EditText) findViewById(R.id.colname);

        name.setText(shp.getString("name","null"));
        rollno.setText(shp.getString("rollno","null"));
        sem.setText(shp.getString("sem","null"));
        yr.setText(shp.getString("yr","null"));
        colname.setText(shp.getString("colname","null"));


                btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (name.length() == 0 || rollno.length() == 0 || yr.length() == 0 ||
                        sem.length() == 0 || colname.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Fields can't be left empty",
                            Toast.LENGTH_LONG).show();

                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updatepersonaldetailsjava.this);
                    alertDialogBuilder.setTitle("Attention!");
                    alertDialogBuilder
                            .setMessage("Update Your Personal Details?")
                            .setIcon(R.drawable.ic_action_warning)
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            saveDetails();
                                            Toast.makeText(getApplicationContext(),
                                                    "Updated Successfully", Toast.LENGTH_LONG).show();
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
    public void saveDetails()
    {
        SharedPreferences.Editor e = shp.edit();
        e.putString("name", name.getText().toString());
        e.putString("rollno", rollno.getText().toString());
        e.putString("sem", sem.getText().toString());
        e.putString("yr", yr.getText().toString());
        e.putString("colname", colname.getText().toString());
        e.commit();
    }
    }



