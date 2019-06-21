package com.samuelbernard147.smartvillage.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.samuelbernard147.smartvillage.MainActivity;
import com.samuelbernard147.smartvillage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class PanicJobService extends JobService {
    public static final String TAG = PanicJobService.class.getSimpleName();
    final String URL = "http://smartvillage.starbhaktefa.com/public/api/security";
    public static final String EXTRA_PANIC = "extra_panic";

    public void gerCurrentStatus(final JobParameters job) {
        Bundle extras = job.getExtras();
        if (extras == null) {
            jobFinished(job, false);
            return;
        } else if (extras.isEmpty()) {
            jobFinished(job, false);
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray arrayPanic = responseObject.getJSONArray("panik");

                    for (int i = 0; i < arrayPanic.length(); i++) {
                        String kepala_keluarga = arrayPanic.getJSONObject(i).getString("kepala_keluarga");
                        String alamat = arrayPanic.getJSONObject(i).getString("alamat");
                        int status = arrayPanic.getJSONObject(i).getInt("status");

                        if (status == 1) {
                            String title = getResources().getString(R.string.panic_title);
                            String message = String.format(getResources().getString(R.string.panic_message), kepala_keluarga);
                            int notifId = 100 + i;

                            showPanicNotification(getApplicationContext(), title, message, notifId);
                            jobFinished(job, false);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                jobFinished(job, true);
            }
        });
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        gerCurrentStatus(job);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    // Memunculkan notifikasi
    private void showPanicNotification(Context context, String title, String message, int notifId) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManagerCompat = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(notifId))
                .setSmallIcon(R.drawable.alert)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(pendingIntent);

//        Android oreo keatas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(String.valueOf(notifId),
                    title + " Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(String.valueOf(notifId));

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }
}