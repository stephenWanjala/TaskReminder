package com.example.taskreminder.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ReminderService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String taskTitle = intent.getStringExtra("task_title");
        String taskDescription = intent.getStringExtra("task_description");

        // Show notification
        NotificationHelper.showNotification(this, taskTitle, taskDescription);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
