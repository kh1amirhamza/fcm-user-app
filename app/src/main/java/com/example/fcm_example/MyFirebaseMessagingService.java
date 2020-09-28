package com.example.fcm_example;

import android.util.Log;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.concurrent.TimeUnit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServ";


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: called");

        WorkRequest notificationWorker =
                new OneTimeWorkRequest.Builder(NotificationWorker.class)
                        .setInitialDelay(0, TimeUnit.SECONDS)
                        .setInputData(
                                new Data.Builder()
                                        .putString("title", remoteMessage.getData().get("title"))
                                        .putString("body", remoteMessage.getData().get("body"))
                                        .build()
                        )
                        .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(notificationWorker);






/*
                if (remoteMessage.getNotification() != null) {
                    Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                    //send custom notification
                }

             // ...
            //Toast.makeText(this, "Notification received", Toast.LENGTH_SHORT).show();
            // TODO(developer): Handle FCM messages here.
            // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
            Log.d(TAG, "From: " + remoteMessage.getFrom());

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());

                if (*//* Check if data needs to be processed by long running job *//* true) {
                    // For long-running tasks (10 seconds or more) use WorkManager.
                    //  scheduleJob();
                } else {
                    // Handle message within 10 seconds
                    //   handleNow();
                }
            }
            // Check if message contains a notification payload.
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
*/
    }
}