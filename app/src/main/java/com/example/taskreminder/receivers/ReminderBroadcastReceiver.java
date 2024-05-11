package com.example.taskreminder.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.taskreminder.utils.NotificationHelper;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskTitle = intent.getStringExtra("task_title");
        if (taskTitle != null) {
            // Show notification
            NotificationHelper.showNotification(context, taskTitle);
        }
    }
}
