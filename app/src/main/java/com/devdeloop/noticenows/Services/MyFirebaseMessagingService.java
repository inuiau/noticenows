package com.devdeloop.noticenows.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.devdeloop.noticenows.Activities.PengumumanDetailActivity;
import com.devdeloop.noticenows.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static java.lang.Integer.parseInt;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String title = remoteMessage.getData().get("judul");
        String id_artikel = remoteMessage.getData().get("id");
        String time = remoteMessage.getData().get("tanggal");
        String category=  remoteMessage.getData().get("kategori");
        String posted = remoteMessage.getData().get("postedby");

        Log.d("Judul", title);
        Log.d("ID", id_artikel);
        Log.d("Time", time);
        Log.d("Kategori", category);
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(id_artikel,title, time,category, posted);
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private void sendNotification(String id,String title, String time,String category, String posted) {
        Intent intent = new Intent(this, PengumumanDetailActivity.class);
        intent.setAction(Long.toString(System.currentTimeMillis()));

        intent.putExtra("id_artikel", id);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setCategory(category)
                .setContentText(posted)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                ;


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(parseInt(id) /* ID of notification */, notificationBuilder.build());
    }
    private void scheduleJob() {
    }
}
