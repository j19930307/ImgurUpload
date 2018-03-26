package com.example.imgurupload;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Jason on 2018/3/25.
 */

public class UploadNotification {

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private int notificationId = 1;

    public UploadNotification(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_file_upload_black_24dp);
        builder.setContentTitle(MyApplication.getInstance().getString(R.string.start_upload));
    }

    public void onUploading(int total, int progress) {
        builder.setProgress(total, progress, false);
        builder.setContentTitle(MyApplication.getInstance().getString(R.string.uploading_title, progress));
        builder.setContentText(MyApplication.getInstance().getString(R.string.uploading_text, progress, total));
        notificationManager.notify(notificationId, builder.build());
    }

    public void onSuccessUpload() {
        builder.setContentTitle(MyApplication.getInstance().getString(R.string.upload_completed));
        builder.setProgress(0, 0, false);
        notificationManager.notify(notificationId, builder.build());
    }

    public void onFailUpload() {
        builder.setContentTitle(MyApplication.getInstance().getString(R.string.upload_failed));
        builder.setProgress(0, 0, false);
        notificationManager.notify(notificationId, builder.build());
    }

}
