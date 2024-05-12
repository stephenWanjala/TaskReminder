package com.example.taskreminder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.taskreminder.data.repository.TaskRepository;
import com.example.taskreminder.domain.model.Task;

public class TaskViewViewModel extends AndroidViewModel {
    private final TaskRepository taskRepository;
    private LiveData<Task> task;

    public TaskViewViewModel(Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }

    public LiveData<Task> getTask(long taskId) {
        if (task == null) {
            task = taskRepository.getTask(taskId);
        }
        return task;
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }

    public void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }
}