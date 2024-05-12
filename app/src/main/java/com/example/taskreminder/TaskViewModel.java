package com.example.taskreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskreminder.data.repository.TaskRepository;
import com.example.taskreminder.domain.model.Task;
import com.example.taskreminder.utils.ReminderUtils;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insertTask(Task task) {
//        10 minutes before the task's due date and time
        var  beforehand = 600000;
        repository.insertTask(task);
        ReminderUtils.scheduleReminder(getApplication(), task, beforehand);
    }

    public void updateTask(Task task) {
        repository.updateTask(task);
        ReminderUtils.cancelReminder(getApplication(), task);
        ReminderUtils.scheduleReminder(getApplication(), task, 600000);
    }

    public void deleteTask(Task task) {
        repository.deleteTask(task);
        ReminderUtils.cancelReminder(getApplication(), task);
    }
}