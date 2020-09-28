package com.example.fcm_example;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {
    public static final String CHANNEL_ID = "FCM";
    Context context;
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString("title");
        String body = getInputData().getString("body");
        sendNotificationUnderVersionOrio(title,body,CHANNEL_ID);
        sendNotificationFromVersionOrio(title,body,CHANNEL_ID);
        return Result.success();
    }

    private void sendNotificationUnderVersionOrio(String title, String body,String CHANNEL_ID){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat mNotificationMgrcmpt = NotificationManagerCompat.from(context);
        mNotificationMgrcmpt.notify(1,mBuilder.build());
    }

    private void sendNotificationFromVersionOrio(String title, String body, String CHANNEL_ID){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "FCM Service",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This is firebase cloud messaging service...");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();
            notificationManager.notify(1,notification);
        }
    }
}
