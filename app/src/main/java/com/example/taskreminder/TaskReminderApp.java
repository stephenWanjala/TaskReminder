package com.example.taskreminder;

import android.app.Application;

import com.example.taskreminder.utils.NotificationHelper;

public class TaskReminderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createNotificationChannel(this);
    }
}
