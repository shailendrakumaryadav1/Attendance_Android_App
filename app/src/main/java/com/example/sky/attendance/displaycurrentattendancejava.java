package com.example.sky.attendance;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class displaycurrentattendancejava extends ActionBarActivity {

    private boolean _doubleBackToExitPressedOnce = false;

    SharedPreferences sp;
    SharedPreferences.Editor e;
    Calendar ob;
    public static final String PREFS="prefs";
    String date_heading[],today;
    LinearLayout lll;
    GridLayout gl;
    TextView tv[];
    int n,z;
    Button past,next,help,ext,viewPer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getSupportActionBar().setTitle("oyee2");
        setContentView(R.layout.displaycurrentattendance);



        sp=getSharedPreferences(PREFS,MODE_PRIVATE);
        e=sp.edit();
        getSupportActionBar().setTitle(sp.getString("name","null")+"@Attendance");



        past=(Button) findViewById(R.id.past);
        past.setFocusable(true);

        next=(Button) findViewById(R.id.next);
        help=(Button) findViewById(R.id.help);
        ext=(Button) findViewById(R.id.ext);
        viewPer=(Button) findViewById(R.id.viewPer);


        lll=(LinearLayout) findViewById(R.id.lll);
        lll.setBackgroundColor(Color.parseColor("#FFB5FCFF"));
        gl = new GridLayout(this);
        lll.addView(gl);
        //gl.setLayoutParams(new LayoutParams
        //       (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        gl.setOrientation(GridLayout.HORIZONTAL);
        gl.setPadding(30,30,30,30);

        LayoutParams lp = gl.getLayoutParams();
        lp.width = LayoutParams.MATCH_PARENT;
        lp.height = LayoutParams.MATCH_PARENT;
        gl.requestLayout();
        date_heading=new String[8];
        n=Integer.parseInt(sp.getString("noofsubj","1"));
        //n=10;
        //String[] letters =
        int i,r,c;
        r=n+1;
        c=8;
        gl.setColumnCount(c);
        gl.setRowCount(r);
        tv=new TextView[r*c];

        for(i=0;i<(r*c);i++)
        {
            tv[i]=new TextView(this);
            GridLayout.LayoutParams p=new GridLayout.LayoutParams();
            p.height=80;
            if(i%8==0)
                p.width=160;
            else
                p.width=100;
            p.setMargins(10,10,10,10);
            tv[i].setBackgroundResource(R.drawable.button_format);
            gl.addView(tv[i],p);
        }

        populate_subject();

        tv[0].setText("###");


        ob=Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setCalendar(ob);

        today=dateFormat.format(ob.getTime());

        populate_date(ob);

        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob.add(Calendar.DATE, -8);
                //populate_subject();
                populate_date(ob);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ob.add(Calendar.DATE,7);
                //populate_subject();
                populate_date(ob);
            }
        });
        viewPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(displaycurrentattendancejava.this,showcurrentattendancepercentjava.class);
                startActivity(intent);

            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater=(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.help, null);
                final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});
                popupWindow.showAsDropDown(help,0,-500);
            }});

        for(z=0;z<r*c;z++)
        {
            tv[z].setOnClickListener(new View.OnClickListener() {
                int item = z;

                @Override
                public void onClick(View v) {


                    if (item < 8 || item % 8 == 0) {

                    } else {
                        int row, col,j;
                        String date, str;
                        Character ch;
                        row = item / 8;
                        col = item % 8;
                        date = date_heading[col];
                        str = sp.getString("A" + date, "0");
                        for(j=str.length();j<n;j++)
                            str=str+"0";
                        ch = str.charAt(row - 1);
                        if (ch == '0')
                        {
                            ch = '1';
                            //tv[item].setText("1");
                            tv[item].setBackgroundResource(R.drawable.button_format_tick);


                        }
                        else if (ch == '1') {
                            ch = '2';
                            //tv[item].setText("2");
                            tv[item].setBackgroundResource(R.drawable.button_format_cross);
                        }
                        else if (ch == '2')
                        {
                            ch = '0';
                            //tv[item].setText("0");
                            tv[item].setBackgroundResource(R.drawable.button_format);
                        }
                        str = str.substring(0, row - 1) + ch + str.substring(row);
                        e.putString("A" + date, str);
                        e.commit();
                    }

                }
            });

        }

    }
    public void populate_subject()
    {
        String s;
        int i;
        for(i=0;i<=n;i++)
        {
            s=sp.getString("subject"+i,"null");
            tv[i*8].setText(s);
            tv[i*8].setTextColor(Color.parseColor("#0000ff"));

            tv[i*8].setTypeface(Typeface.DEFAULT_BOLD);
            tv[i*8].setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            //tv[i*8].setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        }
    }
    public void populate_date(Calendar ob)
    {
        String dayarr[]={"","Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        int pos,i;
        String s;
        pos=ob.get(Calendar.DAY_OF_WEEK);
        if(pos==1)
            pos=7;
        else
            pos--;
        ob.add(Calendar.DATE,-(pos-1));
        for(i=1;i<8;i++)
        {
            tv[i].setTextColor(Color.parseColor("#ffff0000"));
            tv[i].setTypeface(Typeface.DEFAULT_BOLD);
            //tv[i].setGravity(Gravity.CENTER);
            tv[i].setGravity(View.TEXT_ALIGNMENT_GRAVITY);
            //tv[i].setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);


            //update _dateMIN and _dateMAX
            Calendar date_min,date_max;
            date_min=Calendar.getInstance();
            date_min.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sp.getString("_dayMIN","1")) );
            date_min.set(Calendar.MONTH, Integer.parseInt(sp.getString("_monthMIN","1"))-1 );
            date_min.set(Calendar.YEAR, Integer.parseInt(sp.getString("_yearMIN","2010")) );

            date_max=Calendar.getInstance();
            date_max.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sp.getString("_dayMAX","1")) );
            date_max.set(Calendar.MONTH, Integer.parseInt(sp.getString("_monthMAX","1"))-1 );
            date_max.set(Calendar.YEAR, Integer.parseInt(sp.getString("_yearMAX","2010")) );

            if(ob.compareTo(date_max)>0)
            {
                e.putString("_dayMAX",Integer.toString(ob.get(Calendar.DAY_OF_MONTH)));
                e.putString("_monthMAX",Integer.toString( ob.get(Calendar.MONTH)+1 ));
                e.putString("_yearMAX",Integer.toString(ob.get(Calendar.YEAR)));
                e.commit();
            }
            if(date_min.compareTo(ob)>0)
            {
                e.putString("_dayMIN",Integer.toString(ob.get(Calendar.DAY_OF_MONTH)));
                e.putString("_monthMIN",Integer.toString( ob.get(Calendar.MONTH)+1 ));
                e.putString("_yearMIN",Integer.toString(ob.get(Calendar.YEAR)));
                e.commit();
            }


           /* d=ob.get(Calendar.DAY_OF_MONTH);
            m=ob.get(Calendar.MONTH);
            y=ob.get(Calendar.YEAR);

            //s=d+"/"+(m+1)+"/"+y+"\n   "+dayarr[pos];*/
            pos=ob.get(Calendar.DAY_OF_WEEK);


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setCalendar(ob);

            s=dateFormat.format(ob.getTime());
            date_heading[i]=s;
            tv[i].setText(s+"\n"+dayarr[pos]);

            if(date_heading[i].equals(today))
                tv[i].setBackgroundResource(R.drawable.button_format_currentdate);
            else
                tv[i].setBackgroundResource(R.drawable.button_format);

            populate_attendance("A"+date_heading[i],i);

            //Toast.makeText(this,"date head "+i+" ="+date_heading[i]+"\ntoday= "+today ,Toast.LENGTH_LONG).show();

            ob.add(Calendar.DATE,1);
        }
    }
    public void populate_attendance(String d,int k)
    {
        int i;
        Character ch;
        //String st=sp.getString(d,"0");
        String st=sp.getString(d,"0");
        for(i=st.length();i<n;i++)
            st=st+"0";
        for(i=1;i<=n;i++)
        {
            ch=st.charAt(i-1);
            //tv[i*8+k].setText(ch+"");
            if(ch=='0')
                tv[i*8+k].setBackgroundResource(R.drawable.button_format);
            else if(ch=='1')
                tv[i*8+k].setBackgroundResource(R.drawable.button_format_tick);
            else if(ch=='2')
                tv[i*8+k].setBackgroundResource(R.drawable.button_format_cross);
        }
        e.putString(d,st);
        e.commit();
    }
    @Override
    public void onBackPressed() {
        if (_doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this._doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press Again to Quit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                _doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.basic_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId())
        {
            case R.id.action_update_date:
                update_Date();
                return true;

            case R.id.action_update_personal_details:
                update_personal_details();
                return true;
            case R.id.action_add_subject:
                add_subject();
                return true;
            case R.id.action_remove_subject:
                remove_subject();
                return true;
            case R.id.action_delete_all_stats:
                delete_all_stats();
                return true;
            case R.id.action_notification_settings:
                notification_settings();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void update_Date() {
        Intent i = new Intent(displaycurrentattendancejava.this, updatesubjectjava.class);
        startActivity(i);
    }
    public void update_personal_details()
    {
        Intent i = new Intent(displaycurrentattendancejava.this, updatepersonaldetailsjava.class);
        startActivity(i);
    }
    public void add_subject() {
        Intent i = new Intent(displaycurrentattendancejava.this, addsubjectjava.class);
        startActivity(i);
    }
    public void remove_subject() {
        Intent i = new Intent(displaycurrentattendancejava.this, removesubjectjava.class);
        startActivity(i);
    }
    public void delete_all_stats() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete Everything?");
        alertDialogBuilder
                .setMessage("Are you Sure to Delete Everything regarding your Attendance!!!")
                .setIcon(R.drawable.ic_action_warning)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                e.clear();
                                e.commit();
                                //moveTaskToBack(true);
                                //android.os.Process.killProcess(android.os.Process.myPid());
                                //System.exit(1);
                                onBackPressed();
                                onBackPressed();
                                //dialog.cancel();
                                //onRestart();
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
    public void notification_settings() {
        Intent i = new Intent(displaycurrentattendancejava.this, notificationsettingsjava.class);
        startActivity(i);
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        Intent i = new Intent(displaycurrentattendancejava.this, displaycurrentattendancejava.class);
        startActivity(i);
        finish();
    }

}
