package course.labs.gallery.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import course.labs.gallery.NotificationPublisher;
import course.labs.gallery.R;
import course.labs.gallery.SplashActivity;

public class AlarmUtil {

  public static void scheduleNotification(Context context, long delay, int notificationId) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "SELFIE")
        .setSmallIcon(R.drawable.ic_svstatus_success)
        .setContentTitle(context.getString(R.string.selfie_daily))
        .setContentText(context.getString(R.string.selfie_daily_content))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    Intent intent = new Intent(context, SplashActivity.class);
    PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    builder.setContentIntent(activity);
    Notification notification = builder.build();

    Intent notificationIntent = new Intent(context, NotificationPublisher.class);
    notificationIntent.putExtra(Constant.NOTIFICATION_ID, notificationId);
    notificationIntent.putExtra(Constant.NOTIFICATION, notification);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 8);
    calendar.set(Calendar.MINUTE, 0);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis() , AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
//    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0 , 1000*60, pendingIntent);
  }

}
