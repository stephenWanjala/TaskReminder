package com.example.taskreminder.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.taskreminder.utils.NotificationHelper;
import com.example.taskreminder.utils.ReminderService;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskTitle = intent.getStringExtra("task_title");
        String taskDescription = intent.getStringExtra("task_description");
        if (taskTitle != null) {
            // Start a foreground service
            Intent serviceIntent = new Intent(context, ReminderService.class);
            serviceIntent.putExtra("task_title", taskTitle);
            serviceIntent.putExtra("task_description", taskDescription);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        }
    }
}
