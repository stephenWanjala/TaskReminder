package com.example.taskreminder.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.taskreminder.domain.model.Task;
import com.example.taskreminder.receivers.ReminderBroadcastReceiver;

public class ReminderUtils {

    public static void scheduleReminder(Context context, Task task) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("task_title", task.getTitle()); // Pass task title as extra data
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Schedule the alarm
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getDueDateMillis(), pendingIntent);
                } else {
                    Toast.makeText(context, "App does not have permission to schedule exact alarms", Toast.LENGTH_SHORT).show();
                }
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getDueDateMillis(), pendingIntent);
            }
        }
    }

    public static void cancelReminder(Context context, Task task) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Cancel the alarm
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
