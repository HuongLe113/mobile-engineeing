package course.labs.gallery;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import course.labs.gallery.utils.Constant;

public class NotificationPublisher extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    Notification notification = intent.getParcelableExtra(Constant.NOTIFICATION);
    int notificationId = intent.getIntExtra(Constant.NOTIFICATION_ID, 1);
    notificationManager.notify(notificationId, notification);
  }

}
