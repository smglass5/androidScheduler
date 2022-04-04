package android.sgstudentscheduler.Util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.sgstudentscheduler.R;
import android.sgstudentscheduler.UI.MainActivity;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    static int notificationId;
    public int alertNumber;
    String CHANNEL_ID = "SG_STUDENT_SCHEDULER_REMINDER";

    @Override
    public void onReceive(Context context, Intent intent) {
        createChannel(context, CHANNEL_ID);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent reminderIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ++alertNumber, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_alert)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_alert))
                .setContentTitle("Scheduler Reminder")
                .setContentText(intent.getStringExtra("message"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("message")))
                .setAutoCancel(true);
        notificationManager.notify(notificationId++, builder.build());
    }

    private void createChannel(Context context, String CHANNEL_ID) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String title = context.getString(R.string.scheduler_notification);
            String description = context.getString(R.string.notifier);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, title, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
