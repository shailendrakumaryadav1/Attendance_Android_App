package com.example.sky.attendance;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class notification1 extends Service
{
    private NotificationManager mManager;
    SharedPreferences shp;
    private static final String PREFS = "prefs";

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {


        super.onStart(intent, startId);

        shp=getSharedPreferences(PREFS, MODE_PRIVATE);
        String msg="Hi "+shp.getString("name","Sir/Ma'am")+", Fill Your Attendance!";


        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),MainActivity.class);

        Notification notification = new Notification(R.drawable.attendance_icon,msg, System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        notification.defaults = Notification.DEFAULT_ALL;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.ledOnMS=1000; //light on in milliseconds
        notification.ledOffMS=4000; //light off in milliseconds
notification.vibrate =new long[] { 1000, 8000};
        notification.setLatestEventInfo(this.getApplicationContext(), "Attendance", msg, pendingNotificationIntent);

        mManager.notify(0, notification);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

}