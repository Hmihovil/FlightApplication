package info.androidhive.flightApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver {
long milliSecond=50000;
String startDate;
String message;

@Override
public void onReceive(final Context context, Intent intent) {
PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
wl.acquire();

Bundle extras = intent.getExtras();
String msg=extras.getString("message");
String id=extras.getString("id");
String time=extras.getString("time");
String formatedTime="";
try {
formatedTime=convertDate(time);
} catch (Exception e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
if(msg.equals(""))
msg="You have an appointment on "+formatedTime+".";

String ns = Context.NOTIFICATION_SERVICE;
NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(ns);
int icon=R.drawable.c_logo;
CharSequence tickerText = "Reminder"; // ticker-text
int uniqueid= (int)System.currentTimeMillis();
long when=System.currentTimeMillis();
Context contxt = context;
CharSequence contentTitle = "Reminder";
CharSequence contentText =msg;
//PendingIntent contentIntent = PendingIntent.getService(context, 0, null, 0);
Notification notification = new Notification(icon, tickerText, when);
notification.flags |= Notification.FLAG_AUTO_CANCEL;
notification.defaults |= Notification.DEFAULT_SOUND;
notification.defaults |= Notification.DEFAULT_VIBRATE;
notification.setLatestEventInfo(contxt, contentTitle, contentText, null);
mNotificationManager.notify((int)when, notification);
Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
wl.release();
}

public void SetAlarm(Context context,String message,int requestCode,Date date,String dateTime)
{

AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
Intent ip = new Intent(context, Alarm.class);
ip.putExtra("message",message);
ip.putExtra("id", requestCode+"");
ip.putExtra("time", dateTime);
PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, ip, PendingIntent.FLAG_UPDATE_CURRENT );
//am.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), milliSecond, pi);
am.set(AlarmManager.RTC_WAKEUP, date.getTime(), pi);

}

public void CancelAlarm(Context context,int requestCode)
{
Intent intent = new Intent(context, Alarm.class);
PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
alarmManager.cancel(sender);
}

public static String convertDate(String input)throws Exception{
SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy HH:mm" );
Date date=df.parse(input);
SimpleDateFormat df1=new SimpleDateFormat( "EEEEE , dd MMMM , yyyy , h:mm aa" );
String s=df1.format(date);
return s;
}

}