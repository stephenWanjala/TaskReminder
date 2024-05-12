package com.example.taskreminder.domain.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private   long id;
    private String title;
    private String description;
   private  boolean completed;
    private long dueDateMillis;

    public Task(long id, String title, String description, boolean completed, long dueDateMillis) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        if (Objects.nonNull(dueDateMillis)) {
            this.dueDateMillis = dueDateMillis;
        } else {
            this.dueDateMillis = System.currentTimeMillis()+ (60L * 60L * 1000L) ; // due 1hrs
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getDueDateMillis() {
        return dueDateMillis;
    }

    public void setDueDateMillis(long dueDateMillis) {
        this.dueDateMillis = dueDateMillis;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", dueDateMillis=" + dueDateMillis +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Task task = (Task) obj;

        if (id != task.id) return false;
        if (completed != task.completed) return false;
        if (dueDateMillis != task.dueDateMillis) return false;
        if (!Objects.equals(title, task.title)) return false;
        return Objects.equals(description, task.description);
    }
}